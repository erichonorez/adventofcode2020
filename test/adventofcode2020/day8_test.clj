;; Day 8 tests
(ns adventofcode2020.day8-test
  (:require [clojure.test :refer :all]
            [adventofcode2020.day8 :as day8]))

(deftest test-run-program
  (is (= 5
         (day8/run-program [(day8/->Instruction :nop 0)
                            (day8/->Instruction :acc 1)
                            (day8/->Instruction :jmp 4)
                            (day8/->Instruction :acc 3)
                            (day8/->Instruction :jmp -3)
                            (day8/->Instruction :acc -99)
                            (day8/->Instruction :acc 1)
                            (day8/->Instruction :jmp -4)
                            (day8/->Instruction :acc 6)]
                           #{}
                           0
                           0))))

(deftest test-map-instructions
  (is (= [(day8/->Instruction :nop 0)
          (day8/->Instruction :acc 1)
          (day8/->Instruction :jmp 4)
          (day8/->Instruction :acc 3)
          (day8/->Instruction :jmp -3)
          (day8/->Instruction :acc -99)
          (day8/->Instruction :acc 1)
          (day8/->Instruction :jmp -4)
          (day8/->Instruction :acc 6)]
         (day8/map-instructions '("nop 0"
                                  "acc +1"
                                  "jmp +4"
                                  "acc +3"
                                  "jmp -3"
                                  "acc -99"
                                  "acc 1"
                                  "jmp -4"
                                  "acc +6")))))

(deftest test-is-infinite-loop
  (testing "is-infinite-loop?"
    (testing "is an infinite-loop"
      (is (= true
             (day8/infinite-loop? [(day8/->Instruction :nop 0)
                                   (day8/->Instruction :acc 1)
                                   (day8/->Instruction :jmp 4)
                                   (day8/->Instruction :acc 3)
                                   (day8/->Instruction :jmp -3)
                                   (day8/->Instruction :acc -99)
                                   (day8/->Instruction :acc 1)
                                   (day8/->Instruction :jmp -4)
                                   (day8/->Instruction :acc 6)] #{} 0))))
    (testing "is not an infinite loop"
      (is (= false
             (day8/infinite-loop? [(day8/->Instruction :nop 0)
                                   (day8/->Instruction :acc 1)
                                   (day8/->Instruction :jmp 4)
                                   (day8/->Instruction :acc 3)
                                   (day8/->Instruction :jmp -3)
                                   (day8/->Instruction :acc -99)
                                   (day8/->Instruction :acc 1)
                                   (day8/->Instruction :nop -4)
                                   (day8/->Instruction :acc 6)] #{} 0))))))

(deftest test-generate-alt-programs
  (is (= '([(day8/->Instruction :jmp 0)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp 4)
            (day8/->Instruction :acc 3)
            (day8/->Instruction :jmp -3)
            (day8/->Instruction :acc -99)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp -4)
            (day8/->Instruction :acc 6)]
           [(day8/->Instruction :nop 0)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :nop 4)
            (day8/->Instruction :acc 3)
            (day8/->Instruction :jmp -3)
            (day8/->Instruction :acc -99)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp -4)
            (day8/->Instruction :acc 6)]
           [(day8/->Instruction :nop 0)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp 4)
            (day8/->Instruction :acc 3)
            (day8/->Instruction :nop -3)
            (day8/->Instruction :acc -99)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp -4)
            (day8/->Instruction :acc 6)]
           [(day8/->Instruction :nop 0)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :jmp 4)
            (day8/->Instruction :acc 3)
            (day8/->Instruction :jmp -3)
            (day8/->Instruction :acc -99)
            (day8/->Instruction :acc 1)
            (day8/->Instruction :nop -4)
            (day8/->Instruction :acc 6)]))
         (day8/generate-alt-programs [(day8/->Instruction :nop 0)
                                      (day8/->Instruction :acc 1)
                                      (day8/->Instruction :jmp 4)
                                      (day8/->Instruction :acc 3)
                                      (day8/->Instruction :jmp -3)
                                      (day8/->Instruction :acc -99)
                                      (day8/->Instruction :acc 1)
                                      (day8/->Instruction :nop -4)
                                      (day8/->Instruction :acc 6)]))))

(deftest test-fix-program
  (is (= 8
         (day8/fix-program [(day8/->Instruction :nop 0)
                            (day8/->Instruction :acc 1)
                            (day8/->Instruction :jmp 4)
                            (day8/->Instruction :acc 3)
                            (day8/->Instruction :jmp -3)
                            (day8/->Instruction :acc -99)
                            (day8/->Instruction :acc 1)
                            (day8/->Instruction :nop -4)
                            (day8/->Instruction :acc 6)]))))
