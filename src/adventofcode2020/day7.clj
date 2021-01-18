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
           (map (fn [v] {v #{k}}) row)))
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
                             (map #(str/replace % #"^\d\s" ""))
                             (map #(str/replace % #"\s" "-")))]
    {(keyword normalized-container) (set (map keyword normalized-bags))}))

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
