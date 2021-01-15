;; Day 6
(ns adventofcode2020.day6
  (:require [clojure.string :as str]))

(defn all-yes [answers]
  (apply clojure.set/intersection answers))

(defn any-yes [answers]
  (apply clojure.set/union answers))

(defn compute [fn groups]
  (->> (flatten groups)
       (#(map fn %))
       (map count)
       (reduce +)))

(defn parse-file [acc-groups curr-group content]
  (if (< (count content) 1)
    (conj acc-groups (map set curr-group))
    (let [x (first content)
          xs (rest content)]
      (if (empty? x)
        (parse-file (conj acc-groups (map set curr-group)) [] xs)
        (parse-file acc-groups (conj curr-group x) xs)))))

(defn read-file [file-name]
  (->> (slurp file-name)
       str/split-lines
       (#(parse-file [] [] %))))

(defn execute-example! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/example.txt")
       (#(parse-file [] [] %))
       (#(compute any-yes %))))

(defn execute-part-1! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/input.txt")
       (#(parse-file [] [] %))
       (#(compute any-yes %))))

(defn execute-part-2! []
  (->> (read-file "/Users/eric/Developments/adventofcode2020/resources/day6/input.txt")
       (#(parse-file [] [] %))
       (#(compute all-yes %))))
