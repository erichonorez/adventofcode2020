(ns adventofcode2020.day10-test
  (:require [adventofcode2020.day10 :as sut]
            [clojure.test :as t]))

(t/deftest test-part-1
  (t/is (= 220
           (sut/part-1 [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19 38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3 ]))))

(t/deftest test-arrangements
  (t/is (= 3
           sut/arrangements [0 1 4 5 6 7 10])))

