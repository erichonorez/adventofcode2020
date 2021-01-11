;; Day 2
(ns adventofcode2020.day2
  (:require [clojure.string :as str]))

(defn to-char [s]
  (-> s
      char-array
      seq
      first))

(defn xor [a b]
  (or (and a (not b))
      (and (not a) b)))

(defn parse-line [line]
  (let [parts (str/split line #":")
        policy (first parts)
        pwd (str/trim (second parts))
        splitted-policy (str/split policy #" ")
        bounds (str/split (first splitted-policy) #"-")
        letter (to-char (second splitted-policy))
        min (Integer/parseInt (first bounds))
        max (Integer/parseInt (second bounds))]
    {:letter letter
     :min min
     :max max
     :password pwd}))

(defn load-input! [file-name]
  (->> (slurp file-name)
       str/split-lines
       (map parse-line)))

(defn policy-1-is-valid? [combination]
  (let [nbr-of-occur (count (filter #(= (:letter combination) %) (:password combination)))]
    (and (<= (:min combination) nbr-of-occur)
         (>= (:max combination) nbr-of-occur))))

(defn policy-2-is-valid? [combination]
  (let [first-occur (nth (:password combination) (dec (:min combination)))
        second-occur (nth (:password combination) (dec (:max combination)))
        letter (:letter combination)]
    (xor (= first-occur letter)
         (= second-occur letter))))

(defn compute [fn combinations]
  (count (filter fn combinations)))

(defn execute! [is-valid-fn?]
  (->> (load-input! "/Users/eric/Developments/adventofcode2020/resources/day2/input.txt")
       (compute is-valid-fn?)))

(defn executte-part-1! []
  (execute! policy-1-is-valid?))

(defn execute-part-2! []
  (execute! policy-2-is-valid?))
