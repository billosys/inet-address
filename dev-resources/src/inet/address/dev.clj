(ns inet.address.dev
  (:require
    [clojure.tools.namespace.repl :as repl]
    [inet.address.core :as address-core]
    [inet.address.four :as address-four]
    [inet.address.six :as address-six]
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
