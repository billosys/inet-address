(ns inet.address.core-test
  (:require [clojure.test :refer :all]
            [inet.address.core :as address-core]))

(defn make-test-host
  []
  (address-core/by-address
    "testhost"
    (byte-array [1 2 3 4])))

(deftest address->string
  (is (= "testhost/1.2.3.4" (str (make-test-host)))))

(deftest hash-code
  (let [address (make-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest local?
  (address-core/local-link-address? (address-core/localhost)))

(deftest loopback?
  (address-core/loopback-address? (address-core/loopback)))

(deftest address
  (is (= [1 2 3 4] (address-core/address (make-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (address-core/host-address (make-test-host)))))

(deftest reachable?
  (is (not (address-core/reachable? (make-test-host) 0))))
