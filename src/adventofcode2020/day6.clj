;; Day 6
(ns adventofcode.day6
  (:require [clojure.string :as str]))

(defn group-yes [answers acc]
  (if (< (count answers) 1)
    acc
    (group-yes
     (rest answers)
     (clojure.set/union acc (set (first answers))))))

(defn groups-sum-count-yes [groups]
  (->> groups
       (map #(group-yes % []))
       (map count)
       (reduce +)))

(defn yes-for-all [answers]
  (apply clojure.set/intersection
         (->> answers
              (map #(set %)))))

(defn parse-file [acc-groups curr-group content]
  (if (< (count content) 1)
    (conj acc-groups curr-group)
    (let [x (first content)
          xs (rest content)]
      (if (empty? x)
        (parse-file (conj acc-groups curr-group) '() xs)
        (parse-file acc-groups (conj curr-group x) xs)))))

(defn read-file [file-name]
  (->> (slurp file-name)
       str/split-lines
       (#(parse-file [] [] %))))

(defn execute-example! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/example.txt")
       (#(groups-sum-count-yes %))))

(defn execute-part-1! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/input.txt")
       (#(groups-sum-count-yes %))))

(defn execute-part-2! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/input.txt")
       (map #(yes-for-all %))
       (map count)
       (reduce +)))
