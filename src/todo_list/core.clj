(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [routes GET POST]]
            [compojure.route :refer [not-found]]
            [todo-list.base-routes :as base-routes]
            [todo-list.auth :refer [handle-callback]]
            [todo-list.views.core :as views]))

(defn app
  [port]
  (routes
   (GET "/" [] base-routes/welcome)
   (POST "/login" [] (base-routes/login port))
   (POST "/callback" [] handle-callback)
   (GET "/request-info" [] base-routes/request-info)
   (not-found (views/html-page "Page not found" (views/not-found)))))

(defn -main
  "A very simple web server"
  [port-number]
  (jetty/run-jetty app {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server (development mode)"
  [port-number]
  (let [port (Integer. port-number)]
   (jetty/run-jetty (wrap-reload (wrap-params (app port)))
                    {:port port
                     :join? false})))
