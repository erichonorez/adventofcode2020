;; Day 9 tests

(ns adventofcode2020.day9-test
  (:require [clojure.test :refer :all]
            [adventofcode2020.day9 :as day9]))

(deftest test-find-not-sum-of-previous
  (is (= 127
         (day9/find-not-sum-of-previous 5 [35 20 15 25 47 40 62 55 65 95 102 117 150 182 127 219 299 277 309 576]))))
