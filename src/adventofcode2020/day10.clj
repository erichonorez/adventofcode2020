(ns adventofcode2020.day10
  (:require [clojure.string :as str]))

(defn diffs
  [acc [x y & coll]]
  (let [acc' (conj acc (- (- x y)))]
    (if (nil? coll)
      acc'
      (diffs acc' (conj coll y)))))

(defn complete-jolts
  [coll]
  (let [sorted (sort coll)
        from-0 (conj sorted 0)
        device-jolt (+ 3 (apply max from-0))]
    (conj (vec from-0) device-jolt)))

(defn part-1
  [coll]
  (let [sorted (sort coll)
        from-0 (conj sorted 0)
        device-jolt (+ 3 (apply max from-0))
        jolts (conj (vec from-0) device-jolt)
        ds (diffs [] jolts)
        diff-ones (filter #(= 1 %) ds)
        diff-threes (filter #(= 3 %) ds)]
    (* (count diff-ones) (count diff-threes))))

;; https://github.com/callum-oakley/advent-of-code-2020/blob/master/src/day_10.clj
(defn part-2 [adapters]
  (let [device (+ (apply max adapters) 3)
        routes (reduce
                (fn [r a]
                  (assoc r a
                         (apply + (map #(get r % 0) (range (- a 3) a)))))
                {0 1}
                (sort (conj adapters device)))]
    (get routes device)))

(def data
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day10/part-1.txt")
       str/split-lines
       (map read-string)))

(defn execute-part-1!
  []
  (part-1 data))

(defn execute-part-2!
  []
  (part-2 data))
