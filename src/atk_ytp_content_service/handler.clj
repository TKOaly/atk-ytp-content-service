(ns atk-ytp-content-service.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [atk-ytp-content-service.service.content-service :as content-service]))

(defroutes app-routes
  (GET "/api/content/info" []
       (response (content-service/get-info-content)))
  (route/not-found "Not Found"))

(def app (wrap-json-response app-routes))
