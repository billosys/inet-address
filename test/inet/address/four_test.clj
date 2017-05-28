(ns inet.address.four-test
  (:require [clojure.test :refer :all]
            [inet.address.four :as ipv4]))

(defn make-test-host
  []
  (ipv4/by-address
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
  (is (ipv4/loopback-address? (ipv4/loopback))))

(deftest address
  (is (= [1 2 3 4] (ipv4/address (make-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (ipv4/host-address (make-test-host)))))

(deftest reachable?
  (is (not (ipv4/reachable? (make-test-host) 0))))
