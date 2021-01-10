;; Day 1
(ns adventofcode2020.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn load-input! [file-name]
  (->> (slurp file-name)             ; string
       str/split-lines               ; [string]
       (map #(Integer/parseInt %)))) ; [int]

(defn compute-part-1 [numbers]
  (for [x numbers
        y numbers
        :when (= (+ x y) 2020)]
    (* x y)))

(defn compute-part-2 [numbers]
  (for [x numbers
        y numbers
        z numbers
        :when (= (+ x y z) 2020)]
    (* x y z)))

(defn execute-part-1! []
  (-> (load-input! "/Users/eric/Developments/adventofcode2020/resources/day1/input.txt")
      compute-part-1))

(defn execute-part-2! []
  (-> (load-input! "/Users/eric/Developments/adventofcode2020/resources/day1/input.txt")
      compute-part-2))
