;; Day 9

(ns adventofcode2020.day9
  (:require [clojure.string :as str]))

(defn- find-not-sum-of-previous'
  [previous-coll previous-count current coll]
  (cond
    (nil? current) -1
    (= 0 (count coll)) -1
    (< (count coll) previous-count) -1
    (< (count previous-coll) previous-count) (find-not-sum-of-previous'
                                              (conj previous-coll current)
                                              previous-count
                                              (first coll)
                                              (rest coll))
    :else (let [pairs (for [x previous-coll
                            y previous-coll
                            :when (= (+ x y) current)]
                        [x y])]
            (if (= 0 (count pairs))
              current
              (find-not-sum-of-previous'
               (conj (subvec previous-coll 1) current)
               previous-count
               (first coll)
               (rest coll))))))

(defn find-not-sum-of-previous
  [previous-count coll]
  (find-not-sum-of-previous' [] previous-count (first coll) (rest coll)))

(defn execute-part-1
  []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day9/input.txt")
       str/split-lines
       (map #(Long/parseLong %))
       (apply vector)
       (#(find-not-sum-of-previous 25 %))))
