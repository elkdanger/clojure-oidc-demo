(ns todo-list.base-routes
  (:require [ring.util.response :refer [redirect]]
            [todo-list.auth :as auth]
            [todo-list.views.core :as views]))

(defn welcome [request]
  {:status 200
   :body (views/html-page "Welcome" (views/welcome))
   :headers {}})

(defn login [request]
  (redirect (auth/build-url)))

(defn request-info
  "View the details about the request"
  [request]
  {:status 200
   :body (pr-str request)
   :headers {}})
