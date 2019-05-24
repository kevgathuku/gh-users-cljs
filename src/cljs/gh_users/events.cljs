(ns gh-users.events
  (:require
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [re-frame.core :as re-frame]
            [gh-users.db :as db]))   

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-fx        ;; <-- note the `-fx` extension
  :request-org-members        ;; <-- the event id
  (fn                ;; <-- the handler function
    [{db :db} [_ org-name]]     ;; <-- 1st argument is coeffect, from which we extract db 
   
    ;; we return a map of (side) effects
    {:http-xhrio {:method          :get
                  :uri             (str "https://api.github.com/orgs/" org-name "/members")
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true}) 
                  :on-success      [:process-response]
                  :on-failure      [:bad-response]}
     :db  (assoc db :loading? true)}))


(re-frame/reg-event-db
  :process-response
  (fn
    [db [_ response]]           ;; destructure the response from the event vector
    (-> db
        (assoc :loading? false)
        (assoc :data (js->clj response)))))

(re-frame/reg-event-db
  :bad-response
  (fn
    [db [_ response]]           ;; destructure the response from the event vector
    (-> db
        (assoc :loading? false) ;; take away that "Loading ..." UI 
        (assoc :error (js->clj response :keywordize-keys true)))))  ;; fairly lame processing

