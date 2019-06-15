(ns todo-list.views.core
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer [form-to label text-field text-area radio-button submit-button]]
            [hiccup.element :refer [link-to]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

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
   (form-to [:post "/login"]
            (anti-forgery-field)
            [:div.row
             [:div.form-group.col-12
               [:div.form-check.col
                (radio-button {:class "form-check-input"} "auth-type" true "google")
                (label {:class "form-check-label"} "label-google" "Google")]]
             [:div.form-group.col-12
              [:div.form-check.col
                (radio-button {:class "form-check-input"} "auth-type" false "auth0")
                (label {:class "form-check-label"} "label-google" "Auth0")]]]
            (submit-button {:class "btn btn-primary text-center"} "Log in to the site"))])

(defn not-found []
  [:div
   [:h1 "Page not found"]])

(defn unauthorized []
  [:h1 "Unauthorized"])

(comment
  (html-page "Example" [:h1 "Page not found"]))
