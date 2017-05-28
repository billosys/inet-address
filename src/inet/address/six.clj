(ns inet.address.six
  (:require
    [inet.address.core :as address-core]
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
  `address-core/by-address` except that a third argument is provided:

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
  (address [this]
    "Returns the raw IP address of this Inet6Address object as a Clojure
    vector.")
  (canonical-hostname [this]
    "Gets the fully qualified domain name for this IP address.")
  (host-address [this]
    "Returns the IP address string in textual presentation.")
  (hostname [this]
    "Gets the host name for this IP address.")
  (scoped-interface [this]
    "Returns the scoped interface, if this instance was created with with a
    scoped interface.")
  (scope-id [this]
    "Returns the numeric scopeId, if this instance is associated with an
    interface.")
  (any-local-address? [this]
    "Utility routine to check if the Inet6Address in a wildcard address.")
  (ipv4-compatible-address? [this]
    "Utility routine to check if the InetAddress is an IPv4 compatible IPv6
    address.")
  (link-local-address? [this]
    "Utility routine to check if the Inet6Address is an link local address.")
  (loopback-address? [this]
    "Utility routine to check if the Inet6Address is a loopback address.")
  (mc-global? [this]
    "Utility routine to check if the multicast address has global scope.")
  (mc-link-local? [this]
    "Utility routine to check if the multicast address has link scope.")
  (mc-node-local? [this]
    "Utility routine to check if the multicast address has node scope.")
  (mc-org-local? [this]
    "Utility routine to check if the multicast address has organization
    scope.")
  (mc-site-local? [this]
    "Utility routine to check if the multicast address has site scope.")
  (multicast-address? [this]
    "Utility routine to check if the Inet6Address is an IP multicast address.")
  (reachable? [this timeout] [this net-iface ttl timeout]
    "Test whether that address is reachable.")
  (site-local-address? [this]
    "Utility routine to check if the Inet6Address is a site local address."))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def behaviour
  (merge
    address-core/behaviour
    {:scoped-interface (fn [this] (.getScopedInterface this))
     :scope-id (fn [this] (.getScopeId this))
     :ipv4-compatible-address? (fn [this] (.isIPv4CompatibleAddress this))}))

(extend Inet6Address IPV6Address behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constructors   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def create #'by-address)

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
