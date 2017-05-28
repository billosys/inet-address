(ns inet.address.four
  (:require
    [inet.address.core :as address-core]
    [potemkin :refer [import-vars]])
  (:import
    (java.net Inet4Address)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Methods   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn all-by-name
  "Given the name of a host, returns an Clojure vector of its IP address
  objects, based on the configured name service on the system."
  [host]
  (->> host
       (Inet4Address/getAllByName)
       (into [])))

(defn by-address
  "Returns an `Inet4Address`. In the arity-1 case, an object is given the
  raw IP address. In the arity-2 case, an object is given based on the
  provided host name and IP address.

  Note: expected type of `addr` is a byte array, e.g.:
  ```clj
  (by-address (byte-array [127 0 0 1]))
  ```
  or
  ```clj
  (by-address \"localhost\" (byte-array [127 0 0 1]))
  ```"
  ([addr]
    (Inet4Address/getByAddress addr))
  ([host addr]
    (Inet4Address/getByAddress host addr)))

(defn by-name
  "Determines the IP address of a host, given the host's name."
  [host]
  (Inet4Address/getByName host))

(defn localhost
  "Returns the address of the local host."
  []
  (Inet4Address/getLocalHost))

(defn loopback
  "Returns the loopback address."
  []
  (Inet4Address/getLoopbackAddress))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(extend Inet4Address address-core/Address address-core/behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Aliases   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(import-vars
  [address-core
   ;; behaviour
   address
   canonical-hostname
   host-address
   hostname
   any-local-address?
   link-local-address?
   loopback-address?
   mc-global?
   mc-link-local?
   mc-node-local?
   mc-org-local?
   mc-site-local?
   multicast-address?
   reachable?
   site-local-address?])
