(ns todo-list.auth
  (:require [ring.util.codec :refer [url-encode]]
            [ring.util.response :refer [content-type]]
            [jwt-verify-jwks.core :refer [jwt-validate-jwks]]
            [clojure.data.json :as json]
            [todo-list.views.core :as views]))

(def configs {:auth0 {:client-id "nw1AjlUNOiVfKGQpfvQ69q7k9YQhtZ0M"
                      :authz-endpoint "https://elkdanger.eu.auth0.com/authorize"
                      :issuer "elkdanger.eu.auth0.com"
                      :jwks-uri "https://elkdanger.eu.auth0.com/.well-known/jwks.json"}
              :google {:client-id "1019975522899-sr4hra1temeitlfl04apbvejl1euftav.apps.googleusercontent.com"
                       :authz-endpoint "https://accounts.google.com/o/oauth2/v2/auth"
                       :jwks-uri "https://www.googleapis.com/oauth2/v3/certs"
                       :issuer "https://accounts.google.com"}})

; Change this between :auth0 and :google to try out the different providers
(def config (:google configs))

(defn build-params
  [m]
  (->> (for [[k v] m]
         (str (url-encode k) "=" (url-encode v)))
       (interpose "&")
       (apply str)))

(defn build-url
  []
  (str (:authz-endpoint config) "?" (build-params {"redirect_uri" "http://localhost:3000/callback"
                                                   "response_type" "token id_token"
                                                   "response_mode" "form_post"
                                                   "client_id" (:client-id config)
                                                   "scope" "openid email profile"
                                                   "nonce" "345783923"})))


(defn check-issuer
  [profile]
  (= (:iss profile) (:issuer config)))

(defn check-audience
  [profile]
  (= (:aud profile) (:client-id config)))

(defn validate-token
  [id_token]
  (let [profile (jwt-validate-jwks id_token (:jwks-uri config) "rs256")]
    (if (check-issuer profile) true (throw (Exception. "Invalid issuer")))
    (if (check-audience profile) true (throw (Exception. "Invalid audience")))
    profile))

(defn handle-callback
  [request]
  (let [id_token (get-in request [:params "id_token"])]
    (try (let [profile (validate-token id_token)]
           {:status 200
            :body (json/write-str profile)
            :headers {"content-type" "application/json"}}) (catch Exception e {:status 401 :body (views/html-page "Unauthorized" (views/unauthorized))}))))
