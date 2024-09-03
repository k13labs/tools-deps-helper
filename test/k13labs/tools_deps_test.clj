(ns k13labs.tools-deps-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.string :as str]
            [k13labs.tools.deps.main :as main]))

(deftest cli-test
  (testing "simple output test"
    (let [out (-> (with-out-str
                   (main/cli ["find-versions" "org.clojure/clojure"]))
                  (str/split #"\n")
                  (set))]
      (is (true? (contains? out "1.11.4"))))))
