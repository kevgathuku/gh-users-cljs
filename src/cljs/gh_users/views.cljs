(ns gh-users.views
  (:require
   [re-frame.core :as re-frame]
   [gh-users.subs :as subs]))
   

(defn table []
  [:table.u-full-width
   [:thead
    [:tr
     [:th "Avatar"]
     [:th "Name"]]]
   [:tbody
    [:tr 
     [:td
      [:img.avatar {:src "https://ui-avatars.com/api/?name=John+Doe"}]]
     [:td
      [:a {:href "http://example.com"} "John Doe"]]]]])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.container
     [:h1 "Hello from " @name]
     [table]]))
