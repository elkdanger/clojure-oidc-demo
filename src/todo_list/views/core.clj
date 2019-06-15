(ns todo-list.views.core
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer [link-to]]))

(defn html-page [title & content]
  (html5
   [:head
    [:title title]
    (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")]
   [:body
    [:div {:class "container"} content]]))

(defn welcome []
  [:div#main-content
   [:h1 "Hello, Clojure!"]
   [:p.lead "This is an Open ID Connect demo!"]
   (link-to {:class "btn btn-primary"} "/login" "Log in to the site")])

(defn not-found []
  [:div
   [:h1 "Page not found"]])

(defn unauthorized []
  [:h1 "Unauthorized"])

(comment
  (html-page "Example" [:h1 "Page not found"]))
