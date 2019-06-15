(ns todo-list.base-routes
  (:require [ring.util.response :refer [redirect]]
            [todo-list.auth :as auth]
            [todo-list.views.core :as views]))

(defn welcome [request]
  {:status 200
   :body (views/html-page "Welcome" (views/welcome))
   :headers {}})

(defn login [port]
  (fn [{:keys [form-params]}]
    (let [auth-type (-> form-params (get "auth-type" "google") keyword)]
     (redirect (auth/build-url port auth-type)))))

(defn request-info
  "View the details about the request"
  [request]
  {:status 200
   :body (pr-str request)
   :headers {}})
