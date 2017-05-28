(ns inet.address.six
  (:require
    [inet.address.core :as ipnet-core]
    [potemkin :refer [import-vars]])
  (:import
    (java.net Inet6Address)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Methods   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn all-by-name
  "Given the name of a host, returns an Clojure vector of its IP address
  objects, based on the configured name service on the system."
  [host]
  (->> host
       (Inet6Address/getAllByName)
       (into [])))

(defn by-address
  "Returns an `Inet6Address`.

  This creates an `Inet6Address` in a similar manner as with the 2-arity
  `ipnet-core/by-address` except that a third argument is provided:

  * either the IPv6 scope id is set by passing an integer represenging the
    scope id, or
  * the IPv6 scope_id is set to the value corresponding to the given
    interface for the address type specified with `addr`.

  Note: as with `InetAddress`, and `Inet4Address`, the expected type of
  `addr` is a vector of integers, e.g.:
  ```clj
  (by-address \"localhost\" [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1] 0)
  ```"
  [host addr scope-or-nif]
  (Inet6Address/getByAddress host (byte-array addr) scope-or-nif))

(defn by-name
  "Determines the IP address of a host, given the host's name."
  [host]
  (Inet6Address/getByName host))

(defn localhost
  "Returns the address of the local host."
  []
  (Inet6Address/getLocalHost))

(defn loopback
  "Returns the loopback address."
  []
  (Inet6Address/getLoopbackAddress))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Protocol   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defprotocol IPV6Address
  (scoped-interface [this]
    "Returns the scoped interface, if this instance was created with with a
    scoped interface.")
  (scope-id [this]
    "Returns the numeric scopeId, if this instance is associated with an
    interface.")
  (ipv4-compatible-address? [this]
    "Utility routine to check if the InetAddress is an IPv4 compatible IPv6
    address."))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def behaviour
  (merge
    ipnet-core/behaviour
    {:scoped-interface (fn [this] (.getScopedInterface this))
     :scope-id (fn [this] (.getScopeId this))
     :ipv4-compatible-address? (fn [this] (.isIPv4CompatibleAddress this))}))

(extend Inet6Address IPV6Address behaviour)
(extend Inet6Address ipnet-core/Address behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constructors   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def create #'by-address)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Aliases   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(import-vars
  [ipnet-core
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
