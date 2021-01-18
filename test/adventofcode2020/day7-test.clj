;; Day 7 tests
(ns adventofcode2020.day7-test
  (:require [clojure.test :refer :all]
            [adventofcode2020.day7 :refer [compute convert parse-text clean-line parse-bags]]))

(deftest test-compute
  (is (= #{:br :lr :my :do}
         (compute {:mo #{:br :my}
                   :br #{:lr :do}}
                  [:mo]))))

(deftest test-convert
  (is (= {:mo #{:br :my}
          :br #{:lr :do}}
         (convert {:br #{:mo}
                   :my #{:mo}
                   :lr #{:br}
                   :do #{:br}}))))

(deftest test-clean-line
  (testing "clean-line"
    (testing "bags containing other bags"
      (is (= "pale brown/1 faded fuchsia/2 wavy orange/1 mirrored coral/5 dotted brown"
             (clean-line "pale brown bags contain 1 faded fuchsia bag, 2 wavy orange bags, 1 mirrored coral bag, 5 dotted brown bags."))))
    (testing "bags not containing other bags"
      (is (= "wavy turquoise"
             (clean-line "wavy turquoise bags contain no other bags."))))))

(deftest test-parse-text
  (testing "parse-text"
    (testing "bags containing other bags"
      (is (= {:pale-brown #{:faded-fuchsia :wavy-orange :mirrored-coral :dotted-brown}}
             (parse-text "pale brown/1 faded fuchsia/2 wavy orange/1 mirrored coral/5 dotted brown")))
      (testing "bags not containing other bags"
        (is (= {:wavy-turquoise #{}}
               (parse-text "wavy turquoise")))))))

(deftest test-parse-bags
  (is (= {:pale-brown #{:faded-fuchsia :wavy-orange :mirrored-coral :dotted-brown}
          :vibrant-coral #{:dotted-blue}}
         (parse-bags '("pale brown/1 faded fuchsia/2 wavy orange/1 mirrored coral/5 dotted brown"
                       "vibrant coral/1 dotted blue")))))

