(ns adventofcode2020.day11
  (:require [clojure.string :as str]))

(defn adjacent-seats
  [i j seats]
   (for [r (range (dec i) (+ 2 i))
         c (range (dec j) (+ 2 j))
         :when (and (>= r 0)
                    (>= c 0)
                    (not (and (= r i)
                              (= c j)))
                    (< r (count seats))
                    (< c (count (nth seats 0))))]
       (nth (nth seats r) c)))

(defn round
  [seats]
  (for [i (range 0 (count seats))]
    (let [row (nth seats i)]
      (for [j (range 0 (count row))]
        (let [seat (nth row j)
              adjacents (adjacent-seats i j seats)
              occupied-adjacent-seats (count (filter (partial = \#) adjacents))]
          (cond
            (and (= \L seat) (= 0 occupied-adjacent-seats)) \#
            (and (= \# seat) (< 3 occupied-adjacent-seats)) \L
            :else seat))))))

(defn count-occupied-seats
  [matrix]
  (->> (flatten matrix)
       (filter (partial = \#))
       count))

(defn part-1
  [seats]
  (let [seats' (round seats)]
    (if (= seats seats')
      (count-occupied-seats seats')
      (part-1 seats'))))

(defn execute-part-1!
  []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day11/input.txt")
       str/split-lines
       (map vec)
       part-1
       ))
