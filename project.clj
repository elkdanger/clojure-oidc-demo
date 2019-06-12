(defproject todo-list "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [ring "1.7.1"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]
                 [jwt-verify-jwks "1.0.2"]]

  :repl-options {:init-ns todo-list.core}
  :main todo-list.core/-dev-main

  :profiles {:dev {:main todo-list.core/-dev-main}})
