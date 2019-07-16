(ns atk-ytp-content-service.service.content-service
  (:require [atk-ytp-content-service.client.google-sheets :as sheets]))

(defn- parse-sheet-data [{:keys [sheets]}]
  (let [data (:data (first sheets))]
    (first data)))

(defn get-info-content []
  (let [sheet-data (sheets/get-sheet)
        sheet (parse-sheet-data sheet-data)
        info-row (first (:rowData sheet))
        values (:values info-row)]
    {:info {:title (:formattedValue (get values 0))
            :content (:formattedValue (get values 1))}}
    ))
