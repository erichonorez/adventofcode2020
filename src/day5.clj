;; Day 5
(ns adventofcode2020.day5
  (:require [clojure.string :as str]))

(defrecord Passport [byr iyr eyr hgt hcl ecl pid cid])

(defn is-valid? [passport]
  (not (or (nil? (:byr passport))
           (nil? (:iyr passport))
           (nil? (:eyr passport))
           (nil? (:hgt passport))
           (nil? (:hcl passport))
           (nil? (:ecl passport))
           (nil? (:pid passport)))))

(defn is-valid-byr? [byr]
  (and (not (nil? byr))
       (string? byr)
       (not (nil? (re-matches #"(\d){4}" byr)))
       (let [byr-int (Integer/parseInt byr)]
         (and (<= 1920 byr-int) (>= 2002 byr-int)))))

(defn is-valid-iyr? [iyr]
  (and (not (nil? iyr))
       (string? iyr)
       (not (nil? (re-matches #"(\d){4}" iyr)))
       (let [iyr-int (Integer/parseInt iyr)]
         (and (<= 2010 iyr-int) (>= 2020 iyr-int)))))

(defn is-valid-eyr? [eyr]
  (and (not (nil? eyr))
       (string? eyr)
       (not (nil? (re-matches #"(\d){4}" eyr)))
       (let [eyr-int (Integer/parseInt eyr)]
         (and (<= 2020 eyr-int) (>= 2030 eyr-int)))))

(defn is-valid-hgt? [hgt]
  (if (nil? hgt)
    false
    (let [matches (re-matches #"(\d+)(cm|in)" hgt)]
      (and (not (nil? matches))
           (let [quantity (second matches)
                 typed-quantity (Integer/parseInt quantity)
                 scale (last matches)]
             (if (= scale "cm")
               (and (>= typed-quantity 150)
                    (<= typed-quantity 193))
               (and (>= typed-quantity 59)
                    (<= typed-quantity 76))))))))

(defn is-valid-hcl? [hcl]
  (and (not (nil? hcl))
       (not (nil? (re-matches #"\#[A-Za-z0-9]{6}" hcl)))))

(defn is-valid-ecl? [ecl]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))

(defn is-valid-pid? [pid]
  (and (not (nil? pid))
       (not (nil? (re-matches #"[0-9]{9}" pid)))))

(defn is-valid-part-2? [passport]
  (and (is-valid-byr? (:byr passport))
       (is-valid-iyr? (:iyr passport))
       (is-valid-eyr? (:eyr passport))
       (is-valid-hgt? (:hgt passport))
       (is-valid-hcl? (:hcl passport))
       (is-valid-ecl? (:ecl passport))
       (is-valid-pid? (:pid passport))))

(defn parse-file [file-content acc-passports current-passport]
  (if (< (count file-content) 1)
    (conj acc-passports current-passport)
    (let [line (first file-content)]
      (if (empty? line)
        (parse-file (rest file-content) (conj acc-passports current-passport) "")
        (parse-file (rest file-content) acc-passports (str/triml (str current-passport " " line)))))))

(defn map-row [row keys values]
  (if (< (count row) 1)
    (zipmap keys values)
    (let [kv (str/split (first row) #":")]
      (map-row (rest row) (conj keys (keyword (first kv))) (conj values (second kv))))))

(defn execute-part-1! []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day5/input.txt")
       str/split-lines
       (#(parse-file % [] ""))
       (map #(str/split % #" "))
       (map #(map-row % [] []))
       (map is-valid?)
       (filter true?)
       count))

(defn execute-part-2! []
  (->> (slurp "/Users/eric/Developments/adventofcode2020/resources/day5/input.txt")
       str/split-lines
       (#(parse-file % [] ""))
       (map #(str/split % #" "))
       (map #(map-row % [] []))
       (map #(is-valid-part-2? %))
       (filter true?)
       count))

(defn invalid-passports []
  (let [rows ["eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926"
              "iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946"
              "hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277"
              "hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007"]]
    (->> rows
         (map #(str/split % #" "))
         (map #(map-row % [] []))
         (map #(is-valid-part-2? %))
         (zipmap rows))))
