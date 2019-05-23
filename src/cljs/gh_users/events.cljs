(ns gh-users.events
  (:require
   [re-frame.core :as re-frame]
   [gh-users.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
