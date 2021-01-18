;; Day 7
(ns adventofcode2020.day7
  (:require [clojure.string :as str]))

(defn compute [xs keys]
  (let [results (for [k keys]
                  (k xs))
        unioned (apply clojure.set/union results)]
    (if (< (count unioned) 1)
      unioned
      (clojure.set/union unioned (compute xs unioned)))))

(defn convert [xs]
  (->> (for [k (keys xs)]
         (let [row (k xs)]
           (map (fn [v] {(:bag v) #{k}}) row)))
       flatten
       (apply merge-with into)))

(defn clean-line [line]
  (if (str/includes? line " bags contain no other bags")
    (str/replace line #" bags contain no other bags." "")
    (->> line
         (#(str/replace % #" contain " "/"))
         (#(str/replace % #", " "/"))
         (#(str/replace % #"\." ""))
         (#(str/replace % #" bag[s]?" "")))))

(defn parse-text [line]
  (let [parts (str/split line #"/")
        container-part (first parts)
        bags-parts (rest parts)
        normalized-container (str/replace container-part #"\s" "-")
        normalized-bags (->> bags-parts
                             (map #(re-matches #"(\d{1})\s(\w+\s\w+)" %))
                             (map rest)
                             (map #(vector (Integer/parseInt (first %)) (keyword (str/replace (second %) #"\s" "-"))))
                             (map #(hash-map :bag (second %) :quantity (first %))))]
    {(keyword normalized-container) normalized-bags}))

(defn parse-bags [bags]
  (->> (map parse-text bags)
       (reduce into)))

(defn execute-example! []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day7/example.txt")
       str/split-lines
       (map clean-line)
       parse-bags
       convert
       (#(compute % [:shiny-gold]))))

(defn execute-part-1! []
  (count
   (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day7/input.txt")
        str/split-lines
        (map clean-line)
        parse-bags
        convert
        (#(compute % [:shiny-gold])))))

(defn convert-part-2 [bags key quantity]
  (let [sub-bags (key bags)
        tree (for [{bag :bag
                    quantity :quantity} sub-bags]
               (convert-part-2 bags bag quantity))]
    {:bag key
     :quantity quantity
     :bags tree}))

(defn compute-part-2 [xs]
  (->> (for [{bag :bag quantity :quantity bags :bags} xs]
         (+ quantity
            (* quantity
               (if (< (count bags) 1)
                 0
                 (compute-part-2 bags)))))
       (reduce +)))

(defn execute-example-2! []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day7/example-2.txt")
       str/split-lines
       (map clean-line)
       parse-bags
       (#(convert-part-2 % :shiny-gold nil))
       (#(compute-part-2 (:bags %)))))


(defn execute-part-2! []
     (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day7/input.txt")
          str/split-lines
          (map clean-line)
          parse-bags
          (#(convert-part-2 % :shiny-gold nil))
          (#(compute-part-2 (:bags %)))))
