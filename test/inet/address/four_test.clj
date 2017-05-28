(ns inet.address.four-test
  (:require [clojure.test :refer :all]
            [inet.address.four :as address-four]))

(defn make-test-host
  []
  (address-four/by-address
    "testhost"
    (byte-array [1 2 3 4])))

(deftest address->string
  (is (= "testhost/1.2.3.4" (str (make-test-host)))))

(deftest equality
  (is (= (make-test-host)
         (make-test-host))))

(deftest hash-code
  (let [address (make-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest local?
  (address-four/link-local-address? (address-four/localhost)))

(deftest loopback?
  (address-four/loopback-address? (address-four/loopback)))

(deftest address
  (is (= [1 2 3 4] (address-four/address (make-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (address-four/host-address (make-test-host)))))

(deftest reachable?
  (is (not (address-four/reachable? (make-test-host) 0))))
