(ns atk-ytp-content-service.client.google-sheets
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [atk-ytp-content-service.util.interval :as interval]))

(def sheet-url (str "https://sheets.googleapis.com/v4/spreadsheets/" (:google-sheet-id env)))
(def api-key (:google-api-key env))

(defn get-sheet-request []
  (let [request-data (client/get sheet-url {:query-params {:key api-key :includeGridData true} :as :json})]
    (:body request-data)
    ))

(def data (atom (get-sheet-request)))

(defn get-sheet [] @data)

(interval/start (fn []
                  (println "[INFO] Fetching new version of sheet...")
                  (reset! data (get-sheet-request))) 60000)
