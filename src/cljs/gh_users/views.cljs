(ns gh-users.views
  (:require
   [cljs.pprint :refer [pprint]]
   [re-frame.core :as re-frame]
   [gh-users.subs :as subs]))
   

(def sample-data
  [{:login "a" :avatar_url "https://avatars2.githubusercontent.com/u/8406752?v=4"}
   {:login "b" :avatar_url "https://avatars2.githubusercontent.com/u/20769821?v=4"}
   {:login "c" :avatar_url "https://avatars2.githubusercontent.com/u/20963836?v=4"}
   {:login "d" :avatar_url "https://avatars2.githubusercontent.com/u/16223682?v=4"}])

(defn request-data-button []
  [:button.button.button-primary
   {:on-click #(re-frame/dispatch [:request-org-members "andela"])}
   "Request data"])

(defn table-row [data]
  "Renders an individual table row"
  [:tr 
     [:td
      [:img.avatar {:src (:avatar_url data) :alt (:login data)}]]
     [:td
      [:a {:href "http://example.com"} (:login data)]]])
  

(defn table [rows]
  [:table.u-full-width
   [:thead
    [:tr
     [:th "Avatar"]
     [:th "Name"]]]
   [:tbody
    (for [item rows]
      ^{:key (:login item)} [table-row item])]])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name]) members (re-frame/subscribe [::subs/members])]
    [:div.container
     [:h1 "Hello from " @name]
     [:pre (with-out-str (pprint @members))] 
     [request-data-button]
     [table @members]]))
