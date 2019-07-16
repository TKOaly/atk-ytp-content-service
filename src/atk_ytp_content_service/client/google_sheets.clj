(ns atk-ytp-content-service.client.google-sheets
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]))

(def sheet-url (str "https://sheets.googleapis.com/v4/spreadsheets/" (:google-sheet-id env)))
(def api-key (:google-api-key env))

(defn get-sheet []
  (let [request-data (client/get sheet-url {:query-params {:key api-key :includeGridData true} :as :json})]
    (:body request-data)
    ))
