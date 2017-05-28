(ns inet.address.dev
  (:require
    [clojure.tools.namespace.repl :as repl]
    [inet.address :as address]
    [inet.address.four :as address4]
    [inet.address.six :as address6]
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
