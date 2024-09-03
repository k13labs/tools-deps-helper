.PHONY: repl test clean format-check format-fix

SHELL := /bin/bash

repl:
	clojure -M:dev:test:repl

test:
	clojure -M:dev:test:runner --focus :unit --reporter kaocha.report/documentation --no-capture-output

clean:
	rm -rf target build

lint:
	clojure -M:dev:test:clj-kondo --copy-configs --dependencies --parallel --lint "$(shell clojure -A:dev:test -Spath)"
	clojure -M:dev:test:clj-kondo --lint "src:test" --fail-level "error"

build:
	clojure -X:uberjar :sync-pom true

format-check:
	clojure -M:format-check

format-fix:
	clojure -M:format-fix
