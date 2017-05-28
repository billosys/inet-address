(ns inet.address
  (:require
    [inet.address.core :as address-core]
    [inet.address.six :as address-six]
    [potemkin :refer [import-vars]]))

(import-vars
  [address-core
   ;; static
   all-by-name
   by-address
   by-name
   localhost
   loopback
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
   site-local-address?
   ;; constructor
   create])

(import-vars
  [address-six
   ;; behaviour
   scoped-interface
   scope-id
   ipv4-compatible-address?])
