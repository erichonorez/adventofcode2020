;; Day 3

(ns adventofcode2020.day3
  (:require [clojure.string :as str]))

(defn load-input! [file-name]
  (slurp file-name))

(defn parse-row [row]
  "Map a row (sequence of chars) in the file to a sequence of bool. . is mapped to False. True otherwise."
  (for [c row]
    (cond (= c \.) false
          :else true)))

(defn parse-file
  "Map the content of the file to a [[Bool]]"
  [file-content]
  (for [row (str/split-lines file-content)]
    (parse-row row)))

(defn is-tree?
  [map pos]
  (let [len (count map)
        idx (cond
              (> pos (dec len)) (rem pos len)
              :else pos)]
    (true? (nth map idx))))

(defn build-sets [rows]
  (for [row rows]
    (partial is-tree? row)))

(defn compute [right down sets]
  (for [r (range down (count sets))
        :when (= (rem r down) 0)]
    (let [f (nth sets r)]
      (apply f [(* (/ r down) right)]))))

(defn execute! []
  (->> (load-input! "/Users/eric/Developments/adventofcode2020/resources/day3/input.txt")
       parse-file
       build-sets
       (compute 3 1)
       (filter true?)
       count))

(defn part2! []
  (let [sets (->> (load-input! "/Users/eric/Developments/adventofcode2020/resources/day3/input.txt")
                  parse-file
                  build-sets)]
    (->> (for [x [{:right 1 :down 1}
                  {:right 3 :down 1}
                  {:right 5 :down 1}
                  {:right 7 :down 1}
                  {:right 1 :down 2}]]
           (->> (compute (:right x) (:down x) sets)
                (filter true?)
                count))
         (reduce *))))
