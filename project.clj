(defproject systems.billo/inet-address "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {
    :name "Eclipse Public License"
    :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [potemkin "0.4.3"]]
  :profiles {
    :dev {
      :dependencies [
        [clojusc/trifl "0.1.0-SNAPSHOT"]
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
                    (println (slurp "resources/text/loading.txt")))}}})
