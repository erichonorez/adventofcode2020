;; Day 7 tests
(ns adventofcode2020.day7-test
  (:require [clojure.test :refer :all]
            [adventofcode2020.day7 :refer [compute convert parse-text clean-line parse-bags convert-part-2
                                           compute-part-2]]))

(deftest test-compute
  (is (= #{:br :lr :my :do}
         (compute {:mo #{:br :my}
                   :br #{:lr :do}}
                  [:mo]))))

(deftest test-convert
  (is (= {:mo #{:br :my}
          :br #{:lr :do}}
         (convert {:br '({:bag :mo
                          :quantity 1})
                   :my '({:bag :mo
                          :quantity 1})
                   :lr '({:bag :br
                          :quantity 1})
                   :do '({:bag :br
                          :quantity 1})}))))

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
      (is (= {:pale-brown '({:bag :faded-fuchsia
                             :quantity 1}
                            {:bag :wavy-orange
                             :quantity 2}
                            {:bag :mirrored-coral
                             :quantity 1}
                            {:bag :dotted-brown
                             :quantity 5})}
             (parse-text "pale brown/1 faded fuchsia/2 wavy orange/1 mirrored coral/5 dotted brown")))
      (testing "bags not containing other bags"
        (is (= {:wavy-turquoise ()}
               (parse-text "wavy turquoise")))))))

(deftest test-parse-bags
  (is (= {:pale-brown '({:bag :faded-fuchsia
                         :quantity 1}
                        {:bag :wavy-orange
                         :quantity 2}
                        {:bag :mirrored-coral
                         :quantity 1}
                        {:bag :dotted-brown
                         :quantity 5})
          :vibrant-coral '({:bag :dotted-blue
                            :quantity 1})}
         (parse-bags '("pale brown/1 faded fuchsia/2 wavy orange/1 mirrored coral/5 dotted brown"
                       "vibrant coral/1 dotted blue")))))

(deftest test-convert-2
  (is (= {:bag :shiny-gold
          :quantity nil
          :bags '({:bag :dark-olive
                   :quantity 1
                   :bags ({:bag :faded-blue
                           :quantity 3
                           :bags ()}
                          {:bag :dotted-black
                           :quantity 4
                           :bags ()})})}
         (convert-part-2 {:shiny-gold '({:bag :dark-olive
                                         :quantity 1})
                          :dark-olive '({:bag :faded-blue
                                         :quantity 3}
                                        {:bag :dotted-black
                                         :quantity 4})
                          :dotted-black '()
                          :faded-blue '()} :shiny-gold nil))))

(deftest test-compute-part-2
  (is (= 32
         (compute-part-2 '({:bag :dark-olive
                            :quantity 1
                            :bags ({:bag :faded-blue
                                    :quantity 3
                                    :bags ()}
                                   {:bag :dotted-black
                                    :quantity 4
                                    :bags ()})}
                           {:bag :vibrant-plum
                            :quantity 2
                            :bags ({:bag :faded-blue
                                    :quantity 5
                                    :bags ()}
                                   {:bag :dotted-black
                                    :quantity 6
                                    :bags ()})})))))
