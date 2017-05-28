(ns inet.address
  (:import (java.net InetAddress)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Methods   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn all-by-name
  "Given the name of a host, returns an array of its IP addresses, based on
  the configured name service on the system."
  [host]
  (->> host
       (InetAddress/getAllByName)
       (into [])))

(defn by-address
  "Returns an `InetAddress`. In the arity-1 case, an object is given the
  raw IP address. In the arity-2 case, an object is given based on the
  provided host name and IP address.

  Note: expected type of `addr` is byte array, e.g.:
  ```clj
  (by-address (byte-array [127 0 0 1]))
  ```
  or
  ```clj
  (by-address \"localhost\" (byte-array [127 0 0 1]))
  ```"
  ([addr]
    (InetAddress/getByAddress addr))
  ([host addr]
    (InetAddress/getByAddress host addr)))

(defn by-name
  "Determines the IP address of a host, given the host's name."
  [host]
  (InetAddress/getByName host))

(defn localhost
  "Returns the address of the local host."
  []
  (InetAddress/getLocalHost))

(defn loopback
  "Returns the loopback address."
  []
  (InetAddress/getLoopbackAddress))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def behaviour
  {:x :x})
