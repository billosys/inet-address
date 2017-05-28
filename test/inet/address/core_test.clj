(ns inet.address.core-test
  (:require
    [clojure.test :refer :all]
    [inet.address.core :as inet-core]))

(defn make-test-host
  []
  (inet-core/by-address
    "testhost"
    [1 2 3 4]))

(deftest address->string
  (is (= "testhost/1.2.3.4" (str (make-test-host)))))

(deftest equality
  (is (= (make-test-host)
         (make-test-host))))

(deftest hash-code
  (let [address (make-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest loopback?
  (is (inet-core/loopback-address? (inet-core/loopback))))

(deftest address
  (is (= [1 2 3 4] (inet-core/address (make-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (inet-core/host-address (make-test-host)))))

(deftest reachable?
  (is (not (inet-core/reachable? (make-test-host) 0))))
