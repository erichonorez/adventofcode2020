(ns adventofcode2020.day11-test
  (:require  [clojure.test :as t]
             [adventofcode2020.day11 :as d11]))

(t/deftest test-count-occupied-seats
  (t/is (= 37
           (d11/part-1 [[\L \. \L \L \. \L \L \. \L \L]
                        [\L \L \L \L \L \L \L \. \L \L]
                        [\L \. \L \. \L \. \. \L \L \L]
                        [\L \L \L \L \. \L \L \. \L \L]
                        [\L \. \L \L \. \L \L \. \L \L]
                        [\L \. \L \L \L \L \L \. \L \L]
                        [\. \. \L \. \L \. \. \. \. \.]
                        [\L \L \L \L \L \L \L \L \L \L]
                        [\L \. \L \L \L \L \L \L \. \L]
                        [\L \. \L \L \L \L \L \. \L \L]]))))
