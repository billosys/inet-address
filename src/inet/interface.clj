(ns inet.interface
  (:refer-clojure :exclude [index name])
  (:import
    (java.net NetworkInterface)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Methods   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn by-index
  "Get a network interface given its index."
  [idx]
  (NetworkInterface/getByIndex idx))

(defn by-inet-address
  "Convenience method to search for a network interface that has the specified
  Internet Protocol (IP) address bound to it."
  [addr]
  (NetworkInterface/getByInetAddress addr))

(defn by-name
  "Searches for the network interface with the specified name."
  [iface-name]
  (NetworkInterface/getByName iface-name))

(defn network-interfaces
  "Returns all the interfaces on this machine."
  []
  (-> (NetworkInterface/getNetworkInterfaces)
      (enumeration-seq)
      (vec)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Protocol   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defprotocol NetInterface
  (display-name [this]
    "Get the display name of this network interface.")
  (hardware-address [this]
    "Returns the hardware address (usually MAC) of the interface if it has
    one and if it can be accessed given the current privileges.")
  (index [this]
    "Returns the index of this network interface.")
  (inet-addresses [this]
    "Convenience method to return an Enumeration with all or a subset of the
    InetAddresses bound to this network interface.")
  (interface-addresses [this]
    "Get a List of all or a subset of the InterfaceAddresses of this network
    interface.")
  (mtu [this]
    "Returns the Maximum Transmission Unit (MTU) of this interface.")
  (name [this]
    "Get the name of this network interface.")
  (parent [this]
    "Returns the parent NetworkInterface of this interface if this is a
    subinterface, or null if it is a physical (non virtual) interface or has
    no parent.")
  (sub-interfaces [this]
    "Get an Enumeration with all the subinterfaces (also known as virtual
    interfaces) attached to this network interface.")
  (loopback? [this]
    "Returns whether a network interface is a loopback interface.")
  (point-to-point? [this]
    "Returns whether a network interface is a point to point interface.")
  (up? [this]
    "Returns whether a network interface is up and running.")
  (virtual? [this]
    "Returns whether this interface is a virtual interface (also called
    subinterface).")
  (supports-multicast? [this]
    "Returns whether a network interface supports multicasting or not."))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Implementation   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def behaviour
  {:display-name (fn [this] (.getDisplayName this))
   :hardware-address (fn [this] (->> (.getHardwareAddress this)
                                     (map #(format "%02x" %))
                                     (vec)))
   :index (fn [this] (.getIndex this))
   :inet-addresses (fn [this] (-> (.getInetAddresses this)
                                  (enumeration-seq)
                                  (vec)))
   :interface-addresses (fn [this] (.getInterfaceAddresses this))
   :mtu (fn [this] (.getMTU this))
   :name (fn [this] (.getName this))
   :parent (fn [this] (.getParent this))
   :sub-interfaces (fn [this] (-> (.getSubInterfaces this)
                                  (enumeration-seq)
                                  (vec)))
   :loopback? (fn [this] (.isLoopback this))
   :point-to-point? (fn [this] (.isPointToPoint this))
   :up? (fn [this] (.isUp this))
   :virtual? (fn [this] (.isVirtual this))
   :supports-multicast? (fn [this] (.supportsMulticast this))})

(extend NetworkInterface NetInterface behaviour)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Constructors   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; (def create #'by-address)
