(ns user
  (:require [todo-list.core :as core]))

(def server (atom nil))

(defn go
  []
  (reset! server (core/-dev-main 3000)))

(defn stop
  []
  (.stop @server))

(defn reset
  []
  (stop)
  (go))

(comment

  (go)
  @server
  (stop)

  (reset)

  )
