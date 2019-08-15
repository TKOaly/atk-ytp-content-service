(ns atk-ytp-content-service.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [ring.middleware.cors :refer [wrap-cors]]
            [atk-ytp-content-service.service.content-service :as content-service]))

(defroutes app-routes
  (GET "/api/content/info" []
       (response (content-service/get-info-content)))
  (GET "/api/content/calendars/:day" [day]
       (response (content-service/get-calendar day)))
  (GET "/api/content/people" []
       (response (content-service/get-team)))
  (route/not-found "Not Found"))

(def app (-> app-routes wrap-json-response (wrap-cors :access-control-allow-origin [#".*"] :access-control-allow-methods [:get])))

