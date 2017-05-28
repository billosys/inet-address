(ns inet.address.core-test
  (:require [clojure.test :refer :all]
            [inet.address.core :as ip-core]))

(defn make-test-host
  []
  (ip-core/by-address
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
  (is (ip-core/loopback-address? (ip-core/loopback))))

(deftest address
  (is (= [1 2 3 4] (ip-core/address (make-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (ip-core/host-address (make-test-host)))))

(deftest reachable?
  (is (not (ip-core/reachable? (make-test-host) 0))))
