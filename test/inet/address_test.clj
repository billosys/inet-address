(ns inet.address-test
  (:require
    [clojure.test :refer :all]
    [inet.address :as inet]
    [inet.address.six :as inet6]))

(defn make-inet-test-host
  []
  (inet/by-address
    "testhost"
    [1 2 3 4]))

(defn make-inet6-test-host
  []
  (inet6/by-address
    "testhost"
    [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
    0))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   IPv4 Tests   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest address->string
  (is (= "testhost/1.2.3.4" (str (make-inet-test-host)))))

(deftest equality
  (is (= (make-inet-test-host)
         (make-inet-test-host))))

(deftest hash-code
  (let [address (make-inet-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest loopback?
  (is (inet/loopback-address? (inet/loopback))))

(deftest address
  (is (= [1 2 3 4] (inet/address (make-inet-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (inet/host-address (make-inet-test-host)))))

(deftest reachable?
  (is (not (inet/reachable? (make-inet-test-host) 0))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   IPv6 Tests   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest inet6-address
  (is (= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
         (inet/address (make-inet6-test-host)))))

(deftest inet6-host-address
  (is (= "1:203:405:607:809:a0b:c0d:e0f%0"
         (inet/host-address (make-inet6-test-host)))))

(deftest inet6-reachable?
  (is (not (inet/reachable? (make-inet6-test-host) 0))))

(deftest inet6-scope-id
  (is (= 0 (inet/scope-id (make-inet6-test-host)))))
