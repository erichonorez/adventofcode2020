;; Day 4
(ns adventofcode2020.day4
  (:require [clojure.string :as str]))

(defn find-elem [xs path]
  (let [next-half (first path)]
    (cond (nil? next-half) (first xs)
          :else (let [xs-count (count xs)
                      half-count (/ xs-count 2)
                      next-path (rest path)]
                  (cond (= next-half \L) (find-elem (take half-count xs) next-path)
                        :else (find-elem (drop half-count xs) next-path))))))

(defn map-row-path [path]
  (letfn [(goto [c]
            (cond (= c \F) \L
                  :else \U))]
    (map goto path)))

(defn map-seat-path [path]
  (letfn [(goto [c]
            (cond (= c \R) \U
                  :else \L))]
    (map goto path)))

(defn seat-id [path]
  (let [row (find-elem (range 0 128) (map-row-path (take 7 path)))
        column (find-elem (range 0 8) (map-seat-path (drop 7 path)))]
    (+ column (* 8 row))))

(defn part-1-execute! []
  (let [file-content (slurp "/Users/eric/Developments/adventofcode2020/resources/day4/input.txt")
        boarding-passes (str/split-lines file-content)]
    (apply max (map seat-id boarding-passes))))

(defn find-seat [xs]
  (let [[x y & remaining] xs]
    (cond (nil? y) x
          (= 2 (- y x)) (+ x 1)
          :else (find-seat (rest xs)))))

(defn part-2-execute! []
  (let [file-content (slurp "/Users/eric/Developments/adventofcode2020/resources/day4/input.txt")
        boarding-passes (str/split-lines file-content)]
    (find-seat (sort (map seat-id boarding-passes)))))
