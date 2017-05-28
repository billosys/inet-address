(ns inet.address.six-test
  (:require [clojure.test :refer :all]
            [inet.address.six :as ipv6]))

(defn make-test-host
  []
  (ipv6/by-address
    "testhost"
    (byte-array [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])
    0))

(deftest address->string
  (is (= "testhost/1:203:405:607:809:a0b:c0d:e0f%0"
         (str (make-test-host)))))

(deftest equality
  (is (= (make-test-host)
         (make-test-host))))

(deftest hash-code
  (let [address (make-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest loopback?
  (is (ipv6/loopback-address? (ipv6/loopback))))

(deftest address
  (is (= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
         (ipv6/address (make-test-host)))))

(deftest host-address
  (is (= "1:203:405:607:809:a0b:c0d:e0f%0"
         (ipv6/host-address (make-test-host)))))

(deftest reachable?
  (is (not (ipv6/reachable? (make-test-host) 0))))

(deftest scope-id
  (is (= 0 (ipv6/scope-id (make-test-host)))))
