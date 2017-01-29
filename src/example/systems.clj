(ns example.systems
  (:require 
   [system.core :refer [defsystem]]
   [com.stuartsierra.component :as component]
   (system.components 
    [h2 :refer [new-h2-database DEFAULT-MEM-SPEC DEFAULT-DB-SPEC]]
    [jetty :refer [new-web-server]]
    [endpoint :refer [new-endpoint]]
    [middleware :refer [new-middleware]]
    [handler :refer [new-handler]])
   [example.handler :refer [authors-routes directors-routes]]
   [example.db :refer [create-tables!]]
   [example.middleware :refer [wrap-not-found]]
   [ring.middleware.format :refer [wrap-restful-format]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
   [environ.core :refer [env]]))

(defn select-database [env]
  (let [dbs {"default-mem-spec" DEFAULT-MEM-SPEC
             "default-db-spec" DEFAULT-DB-SPEC}]
    (get dbs (env :db) DEFAULT-MEM-SPEC)))

(defn base-system []
  (component/system-map
   :db (new-h2-database (select-database env) #(create-tables! {} {:connection %}))
   :authors (component/using
             (new-endpoint authors-routes)
             [:db])
   :directors (component/using
               (new-endpoint directors-routes)
               [:db])
   :middleware (new-middleware {:middleware [[wrap-not-found "<h2>The requested page does not exist.</h2>"]
                                             wrap-restful-format
                                             [wrap-defaults api-defaults]]})
   :handler (component/using
             (new-handler)
             [:authors :directors :middleware])
   :http (component/using
         (new-web-server (Integer. (env :http-port)))
         [:handler])))



