(defproject systems.billo/inet-address "0.2.0-SNAPSHOT"
  :description "A Clojure wrapper for the family of Java InetAddress classes"
  :url "https://github.com/billosys/inet-address"
  :license {
    :name "Eclipse Public License"
    :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [potemkin "0.4.3"]]
  :profiles {
    :docs {
      :dependencies [
        [systems.billo/superhero-codox-theme "0.3.0"]]
      :plugins [
        [lein-codox "0.10.3"]
        [lein-marginalia "0.9.0"]]
      :codox {
        :project {:name "inet-address"}
        :themes [:superhero]
        :output-path "docs/current"
        :doc-paths ["resources/docs"]
        :metadata {:doc/format :markdown}}}
    :dev {
      :dependencies [
        [clojusc/trifl "0.1.0"]
        [org.clojure/tools.namespace "0.2.11"]]
      :source-paths [
        "dev-resources/src"
        "test"]
      :repl-options {
        :init-ns inet.address.dev
        :prompt (fn [ns] (str "\u001B[35m[\u001B[34m"
                              ns
                              "\u001B[35m]\u001B[33m λ\u001B[m=> "))
        :welcome ~(do
                    (println (slurp "resources/text/banner.txt"))
                    (println (slurp "resources/text/loading.txt")))}}
    :test {
      :plugins [
        [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
        [lein-kibit "0.1.5" :exclusions [org.clojure/clojure]]
        [lein-ancient "0.6.10"]]}}
  :aliases {
    "check-deps" ["with-profile" "+test" "ancient" "check" "all"]
    "lint" ["with-profile" "+test" "kibit"]
    "docs" ["with-profile" "+docs" "do"
      ["codox"]
      ["marg" "--dir" "docs/current"
              "--file" "marginalia.html"
              "--name" "inet-address"]]
    "build" ["with-profile" "+test" "do"
      ["check-deps"]
      ["lint"]
      ["test"]
      ["compile"]
      ["docs"]
      ["uberjar"]]})
