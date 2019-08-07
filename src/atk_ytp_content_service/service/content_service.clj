(ns atk-ytp-content-service.service.content-service
  (:require [atk-ytp-content-service.client.google-sheets :as sheets]))

(defn- parse-info-sheet-data [{:keys [sheets]}]
  (let [data (:data (first sheets))]
    (first data)))

(defn- get-calendar-sheet [key {:keys [sheets]}]
  (first (filter #(= (get-in %1 [:properties :title]) key) sheets)))


(defn get-info-content []
  (let [sheet-data (sheets/get-sheet)
        sheet (parse-info-sheet-data sheet-data)
        info-row (first (:rowData sheet))
        values (:values info-row)]
    {:info {:title (:formattedValue (get values 0))
            :content (:formattedValue (get values 1))}}))

(defn get-calendar [day]
  (let [sheet-data (sheets/get-sheet)
        selected-sheet (get-calendar-sheet day sheet-data)
        data (first (:data selected-sheet))
        mapped-data (map (fn [row]
                           (println row)
                           (let [time-col (get (:values row) 0)
                                 time (:formattedValue time-col)
                                 event-name-col (get (:values row) 1)
                                 event-name (:formattedValue event-name-col)
                                 event-place-col (get (:values row) 2)
                                 event-place (:formattedValue event-place-col)] {:time time :eventName event-name :eventPlace event-place})) (:rowData data))]
    (println selected-sheet)
    mapped-data))
