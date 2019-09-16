(ns atk-ytp-content-service.util.interval)
(require '[clojure.core.async :as async])

(def running (atom false))

(defn start [f msec]
  (reset! running true)
  (async/go-loop []
    (async/<! (async/timeout msec))
    (f)
    (when @running (recur))))


(defn stop []
  (reset! running false))
