;; Day 8
(ns adventofcode2020.day8
  (:require [clojure.string :as str]))

(defrecord Instruction [type arg])

;; domain
(defn run-program [instructions passed-instructions acc nth-instruction]
  (if (or
       (contains? passed-instructions nth-instruction)
       (= (count instructions) nth-instruction))
    acc
    (let [instruction (nth instructions nth-instruction)
          type (:type instruction)
          arg (:arg instruction)
          new-passed-instructions (conj passed-instructions nth-instruction)]
      (cond
        (= type :acc) (run-program instructions new-passed-instructions (+ acc arg) (inc nth-instruction))
        (= type :jmp) (run-program instructions new-passed-instructions acc (+ arg nth-instruction))
        :else (run-program instructions new-passed-instructions acc (inc nth-instruction))))))

(defn infinite-loop? [instructions passed-instructions nth-instruction]
  (cond
    (>= nth-instruction (count instructions)) false
    (contains? passed-instructions nth-instruction) true
    :else (let [{:keys [type arg]} (nth instructions nth-instruction)
                new-passed-instructions (conj passed-instructions nth-instruction)]
            (if
             (= type :jmp) (infinite-loop? instructions new-passed-instructions (+ arg nth-instruction))
             (infinite-loop? instructions new-passed-instructions (inc nth-instruction))))))

(defn generate-alt-programs [instructions]
  (let [indexed-intructions (map-indexed (fn [idx itm] [idx itm]) instructions)
        nops-jmps (filter #(contains? #{:jmp :nop} (:type (second %))) indexed-intructions)]
    (for [[idx {:keys [type arg]}] nops-jmps
          :let [ttype (if (= type :nop) :jmp :nop)]]
      (into
       (conj (apply vector (take idx instructions)) (->Instruction ttype arg))
       (apply vector (drop (inc idx) instructions))))))

(defn fix-program [instructions]
  (first
   (for [alt-program (generate-alt-programs instructions)
         :when (not (infinite-loop? alt-program #{} 0))]
     (run-program alt-program #{} 0 0))))

;; transform file data to domain data structure
(defn map-instructions [instructions]
  (for [instruction instructions
        :let [[operation arg] (str/split instruction #" ")]]
    (->Instruction (keyword operation) (Integer/parseInt arg))))

;; IO at the edge
(defn execute! [file-name]
  (->> (slurp file-name)
       str/split-lines
       map-instructions
       (#(run-program % #{} 0 0))))

(defn execute-example! []
  (execute! "/Users/eric/Developments/adventofcode2020/resources/day8/example.txt"))

(defn execute-part-1! []
  (execute! "/Users/eric/Developments/adventofcode2020/resources/day8/input.txt"))

(defn execute-part-2! []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day8/input.txt")
       str/split-lines
       map-instructions
       (#(fix-program %))))
