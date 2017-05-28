(ns inet.address-test
  (:require [clojure.test :refer :all]
            [inet.address :as ip]
            [inet.address.six :as ipv6]))

(defn make-ip-test-host
  []
  (ip/by-address
    "testhost"
    [1 2 3 4]))

(defn make-ipv6-test-host
  []
  (ipv6/by-address
    "testhost"
    [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
    0))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   IPv4 Tests   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest address->string
  (is (= "testhost/1.2.3.4" (str (make-ip-test-host)))))

(deftest equality
  (is (= (make-ip-test-host)
         (make-ip-test-host))))

(deftest hash-code
  (let [address (make-ip-test-host)]
    (is (= (hash address)
           (.hashCode address)))))

(deftest loopback?
  (is (ip/loopback-address? (ip/loopback))))

(deftest address
  (is (= [1 2 3 4] (ip/address (make-ip-test-host)))))

(deftest host-address
  (is (= "1.2.3.4" (ip/host-address (make-ip-test-host)))))

(deftest reachable?
  (is (not (ip/reachable? (make-ip-test-host) 0))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   IPv6 Tests   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest ipv6-address
  (is (= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
         (ip/address (make-ipv6-test-host)))))

(deftest ipv6-host-address
  (is (= "1:203:405:607:809:a0b:c0d:e0f%0"
         (ip/host-address (make-ipv6-test-host)))))

(deftest ipv6-reachable?
  (is (not (ip/reachable? (make-ipv6-test-host) 0))))

(deftest ipv6-scope-id
  (is (= 0 (ip/scope-id (make-ipv6-test-host)))))
