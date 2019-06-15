(defproject todo-list "0.1.0-SNAPSHOT"
  :description "Demo of how to interact with an OpenID Connect Authorization Server"
  :url "http://github.com/elkdanger"
  :license {:name "MIT"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [ring "1.7.1"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]
                 [jwt-verify-jwks "1.0.2"]
                 [com.cemerick/url "0.1.1"]]

  :repl-options {:init-ns user}
  :main todo-list.core/-dev-main

  :profiles {:dev {:source-paths ["dev" "src"]
                   :main todo-list.core/-dev-main}})
