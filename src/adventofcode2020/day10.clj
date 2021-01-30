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

(defn split-by [pred coll]
  (lazy-seq
   (when-let [s (seq coll)]
     (let [!pred (complement pred)
           [xs ys] (split-with !pred s)]
       (if (seq xs)
         (cons xs (split-by pred ys))
         (let [skip (take-while pred s)
               others (drop-while pred s)
               [xs ys] (split-with !pred others)]
           (cons (concat skip xs)
                 (split-by pred ys))))))))

(defn arrangments
  [coll]
  (let [jolts (complete-jolts coll)
        ds (diffs [] jolts)]
    (->> (split-by (partial = 3) ds)
         (#(map (fn [xs] (filter (partial = 1) xs)) %))
         (#(map count %))
         (filter #(not (= 0 %)))
         (reduce *))))

(defn execute-part-1!
  []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day10/part-1.txt")
       str/split-lines
       (map #(Integer/parseInt %))
       part-1))
