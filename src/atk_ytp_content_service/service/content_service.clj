(ns atk-ytp-content-service.service.content-service
  (:require [atk-ytp-content-service.client.google-sheets :as sheets]))

(defn- parse-info-sheet-data [{:keys [sheets]}]
  (let [data (:data (first sheets))]
    (first data)))

(defn- get-sheet [key {:keys [sheets]}]
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
        selected-sheet (get-sheet day sheet-data)
        data (first (:data selected-sheet))
        mapped-data (map (fn [row]
                           (let [time-col (get (:values row) 0)
                                 time (:formattedValue time-col)
                                 event-name-col (get (:values row) 1)
                                 event-name (:formattedValue event-name-col)
                                 event-place-col (get (:values row) 2)
                                 event-place (:formattedValue event-place-col)] {:time time :eventName event-name :eventPlace event-place})) (:rowData data))]
    mapped-data))

(defn get-team []
  (let [sheet-data (sheets/get-sheet)
        selected-sheet (get-sheet "ihmiset" sheet-data)
        data (first (:data selected-sheet))
        mapped-data (map (fn [row]
                           (let [name-col (get (:values row) 0)
                                 name (:formattedValue name-col)
                                 title-col (get (:values row) 1)
                                 title (:formattedValue title-col)] {:name name :title title})) (:rowData data))]
    mapped-data))

(defn get-accomodation-info []
  (let [sheet-data (sheets/get-sheet)
        selected-sheet (get-sheet "majo" sheet-data)
        data (first (:data selected-sheet))
        majo-row (-> (:rowData data) (first))
        taxi-row (-> (:rowData data) (get 1))
        mapped-data (let [majo-title-col (first (:values majo-row))
                          majo-content-col (get (:values majo-row) 1)
                          taxi-title-col (first (:values taxi-row))
                          taxi-content-col (get (:values taxi-row) 1)]
                      {:mainTitle (:formattedValue majo-title-col)
                       :mainContent (:formattedValue majo-content-col)
                       :taxiTitle (:formattedValue taxi-title-col)
                       :taxiContent (:formattedValue taxi-content-col)})]
    mapped-data))