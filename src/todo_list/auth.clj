(ns todo-list.auth
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            [cemerick.url :refer [url]]
            [jwt-verify-jwks.core :refer [jwt-validate-jwks]]
            [ring.util.codec :refer [url-encode]]
            [todo-list.views.core :as views]))

(def configs {:auth0 {:client-id "nw1AjlUNOiVfKGQpfvQ69q7k9YQhtZ0M"
                      :authz-endpoint "https://elkdanger.eu.auth0.com/authorize"
                      :issuer "elkdanger.eu.auth0.com"
                      :jwks-uri "https://elkdanger.eu.auth0.com/.well-known/jwks.json"}
              :google {:client-id "1019975522899-sr4hra1temeitlfl04apbvejl1euftav.apps.googleusercontent.com"
                       :authz-endpoint "https://accounts.google.com/o/oauth2/v2/auth"
                       :jwks-uri "https://www.googleapis.com/oauth2/v3/certs"
                       :issuer "accounts.google.com"}})

(def config (atom nil))

(defn build-params
  [m]
  (->> m
       (reduce-kv (fn [acc k v]
                    (conj acc (str (url-encode k) "=" (url-encode v))))
                  [])
       (string/join "&")))

(defn build-url
  [port auth-type]
  (reset! config (auth-type configs))
  (str (:authz-endpoint @config) "?" (build-params {"redirect_uri" (format "http://localhost:%d/callback" port)
                                                    "response_type" "token id_token"
                                                    "response_mode" "form_post"
                                                    "client_id" (:client-id @config)
                                                    "scope" "openid email profile"
                                                    "nonce" "345783923"})))


(defn invalid-issuer?
  [profile]
  (not= (-> profile :iss url :host) (:issuer @config)))

(defn invalid-audience?
  [profile]
  (not= (:aud profile) (:client-id @config)))

(defn validate-token
  [id_token]
  (let [profile (jwt-validate-jwks id_token (:jwks-uri @config) "rs256")]
    (cond
      (invalid-issuer? profile) (throw (ex-info "Invalid issuer"))
      (invalid-audience? profile) (throw (ex-info "Invalid audience"))
      :else profile)))

(defn handle-callback
  [request]
  (let [id_token (get-in request [:params "id_token"])]
    (try
      (let [profile (validate-token id_token)]
        {:status 200
         :body (json/write-str profile)
         :headers {"content-type" "application/json"}})
      (catch Exception _
        {:status 401 :body (views/html-page "Unauthorized" (views/unauthorized))}))))
