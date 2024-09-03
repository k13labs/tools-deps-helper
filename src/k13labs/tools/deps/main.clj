(ns k13labs.tools.deps.main
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.tools.deps.util.maven :as maven]
            [clojure.tools.deps.util.session :as session])
  (:import
   [org.apache.maven.settings Settings]
   [org.eclipse.aether RepositorySystem RepositorySystemSession]
   [org.eclipse.aether.resolution VersionRangeRequest])
  (:gen-class))

(def default-repos
  maven/standard-repos)

(defn usage []
  (println
   (str "tools-deps-helper ["
        "help | "
        "find-versions"
        "] ")))

(defn help [[_ f]]
  (println)
  (case f
    "help" (println "help [cmd]\n\nShow help.")

    "find-versions" (println "find-versions <group-id/artifact-id>

Output the versions available for the specified dependency.")

    (println "tools.deps.edn ...

          help  show this help message
 find-versions  output a list of version numbers for the given dependency

Use tools-deps.edn help <cmd> to get more specific help"))
  (println))

(def ^:private version-query "(,]")

(defn find-versions
  [args]
  (if-let [lib-name (first args)]
    (let [lib (symbol lib-name)
          opt-set (set (rest args))
          repos (cond-> default-repos
                  (.exists (io/file "deps.edn"))
                  (merge
                   (try
                     (-> (slurp "deps.edn")
                         (edn/read-string)
                         (update :mvn/repos (fnil identity default-repos))
                         (get :mvn/repos))
                     (catch Exception _
                       default-repos))))
          local-repo maven/default-local-repo
          system ^RepositorySystem (session/retrieve-local :mvn/system #(maven/make-system))
          settings ^Settings (session/retrieve :mvn/settings #(maven/get-settings))
          session ^RepositorySystemSession (session/retrieve-local :mvn/session #(maven/make-session system settings local-repo))
          artifact (maven/coord->artifact lib {:mvn/version version-query})
          req (VersionRangeRequest. artifact (maven/remote-repos repos settings) nil)
          result (.resolveVersionRange system session req)
          versions (->> (.getVersions result)
                        (map str)
                        (reverse)
                        (into []))]
      (cond
        (opt-set "--json")
        (println (json/write-str versions))

        (opt-set "--edn")
        (prn versions)

        :else
        (run! println versions)))
    (throw (ex-info "invalid arguments, <lib-group-id/lib-artifact-id> is required" {}))))

(defn cli [args]
  (if-let [arg (some-> args first keyword)]
    (try
      (case arg
        :find-versions  (find-versions (rest args))
        :help           (help args)
        (usage))
      (catch clojure.lang.ExceptionInfo e
        (binding [*out* *err*]
          (println (.getMessage e)))
        (System/exit 1)))
    (help args)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (cli args))
