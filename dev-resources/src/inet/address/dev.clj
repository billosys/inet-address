(ns inet.address.dev
  (:require
    [clojure.pprint :as pprint]
    [clojure.tools.namespace.repl :as repl]
    [inet.address :as inet]
    [inet.address.core :as inet-core]
    [inet.address.four :as inet4]
    [inet.address.six :as inet6]
    [inet.interface :as interface]
    [trifl.java :refer [show-methods]])
  (:import (java.net InetAddress
                     Inet4Address
                     Inet6Address
                     NetworkInterface)))

(defn run
  []
  :ok)

(defn refresh
  ([]
    (repl/refresh))
  ([& args]
    (apply #'repl/refresh args)))

(defn reset []
  (refresh :after 'inet.address.dev/run))
