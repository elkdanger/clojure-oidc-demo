(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [todo-list.base-routes :as routes]
            [todo-list.auth :refer [handle-callback]]
            [todo-list.views.core :as views]))

(defroutes app
  (GET "/" [] routes/welcome)
  (GET "/login" [] routes/login)
  (POST "/callback" [] handle-callback)
  (GET "/request-info" [] routes/request-info)
  (not-found (views/html-page "Page not found" (views/not-found))))

(defn -main
  "A very simple web server"
  [port-number]
  (jetty/run-jetty app {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server (development mode)"
  [port-number]
  (jetty/run-jetty (wrap-reload (wrap-params #'app))
                   {:port (Integer. port-number)}))