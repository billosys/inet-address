(ns inet.address.core
  (:require [potemkin :refer [import-vars]])
  (:import (java.net InetAddress)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Methods   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn all-by-name
  "Given the name of a host, returns an Clojure vector of its IP address
  objects, based on the configured name service on the system."
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
;;;   Protocol   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defprotocol Address
  (address [this]
    "Returns the raw IP address of this InetAddress object as a Clojure
    vector.")
  (canonical-hostname [this]
    "Gets the fully qualified domain name for this IP address.")
  (host-address [this]
    "Returns the IP address string in textual presentation.")
  (hostname [this]
    "Gets the host name for this IP address.")
  (any-local-address? [this]
    "Utility routine to check if the InetAddress in a wildcard address.")
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def behaviour
  {:address (fn [this] (into [] (.getAddress this)))
   :canonical-hostname (fn [this] (.getCanonicalHostName this))
   :host-address (fn [this] (.getHostAddress this))
   :hostname (fn [this] (.getHostName this))
   :any-local-address? (fn [this] (.isAnyLocalAddress this))
})

(extend InetAddress Address behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constructors   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def create #'by-address)
