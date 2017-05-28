(ns inet.address.dev
  (:require
    [clojure.pprint :as pprint]
    [clojure.tools.namespace.repl :as repl]
    [inet.address :as ip]
    [inet.address.core :as ip-core]
    [inet.address.four :as ipv4]
    [inet.address.six :as ipv6]
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
