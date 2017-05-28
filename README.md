# inet-address
[![Build Status][travis-badge]][travis][![Dependencies Status][deps-badge]][deps][![Clojars Project][clojars-badge]][clojars][![Tag][tag-badge]][tag][![Clojure version][clojure-v]](project.clj)

[![][logo]][logo-large]

*Clojure wrapper for Java `InetAddress` (also: `Inet4Address`, `Inet6Address`, & `NetworkInterface`)*


## About

Work with `InetAddress` objects in pure Clojure using Clojure programming
idioms.


## Documentation

* [Source code, docstrings, and code comments](http://billo.systems/inet-address/current/marginalia) - published using [Marginalia](https://github.com/gdeer81/marginalia)
* [API Reference Docs](http://billo.systems/inet-address/current/index) - published using [Codox](https://github.com/weavejester/codox)


## Usage

Require the bits of `inet-address` you will need:

```clj
(require '[clojure.test :refer :all]
         '[inet.address :as inet]
         '[inet.address.six :as inet6]
         '[inet.interface :as netface]))
```

Then make the calls as needed, e.g.:

```clj
=> (inet/by-address "testhost" [1 2 3 4])

```
```clj
#object[java.net.Inet4Address 0x7d5e0016 "testhost/1.2.3.4"]
```

```clj
=> (inet6/by-address
     "testhost"
     [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
     0)
```
```clj
#object[java.net.Inet6Address 0x19487de9 "testhost/1:203:405:607:809:a0b:c0d:e0f%0"]
```
```clj
(inet/reachable? (inet/localhost) 100) ; timeout after 100 milliseconds
```
```clj
true
```
Or, for network interfaces:

```clj
=> (netface/network-interfaces)
```
```clj
[#object[java.net.NetworkInterface 0x2e79299 "name:veth06a443d (veth06a443d)"]
 #object[java.net.NetworkInterface 0x66fe36f4 "name:docker0 (docker0)"]
 #object[java.net.NetworkInterface 0x2eda7437 "name:wlp3s0 (wlp3s0)"]
 #object[java.net.NetworkInterface 0x4916292d "name:lo (lo)"]]
```
```clj
=> (netface/by-name "docker0")
```
```clj
#object[java.net.NetworkInterface 0x69fdab5e "name:docker0 (docker0)"]
```
```clj
=> (netface/up? (netface/by-name "docker0"))
```
```clj
true
```
```clj
=> (netface/hardware-address (netface/by-name "docker0"))
```
```clj
["02" "42" "b4" "b7" "0b" "5f"]
```
```clj
=> (netface/inet-addresses (netface/by-name "docker0"))
```
```clj
[#object[java.net.Inet6Address 0x36fad3e9 "/fe80:0:0:0:42:b4ff:feb7:b5f%docker0"]
 #object[java.net.Inet4Address 0x1dde9ac5 "/172.17.0.1"]]
```


## License

Copyright © 2017 BilloSystems, Ltd. Co.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

<!-- Named page links below: /-->

[travis]: https://travis-ci.org/billosys/inet-address
[travis-badge]: https://travis-ci.org/billosys/inet-address.png?branch=master
[deps]: http://jarkeeper.com/billosys/inet-address
[deps-badge]: http://jarkeeper.com/billosys/inet-address/status.svg
[logo]: resources/images/inet-address-x250.png
[logo-large]: resources/images/inet-address.png
[tag-badge]: https://img.shields.io/github/tag/billosys/inet-address.svg
[tag]: https://github.com/billosys/inet-address/tags
[clojure-v]: https://img.shields.io/badge/clojure-1.8.0-blue.svg
[clojars]: https://clojars.org/systems.billo/inet-address
[clojars-badge]: https://img.shields.io/clojars/v/systems.billo/inet-address.svg
