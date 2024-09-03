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

build-uberjar:
	clojure -X:uberjar :sync-pom true

build-config:
	mkdir -p graalvm-config
	java -agentlib:native-image-agent=config-output-dir=graalvm-config -jar build/tools-deps-helper.jar find-versions "com.github.k13labs/futurama"

build-native:
	native-image -cp "$(shell clojure -Spath):classes" \
		"-H:+ReportExceptionStackTraces" \
		"-H:+JNI" \
		"-H:EnableURLProtocols=http,https,jar" \
		"--enable-all-security-services" \
		"-J-Dclojure.spec.skip-macros=true" \
		"-J-Dclojure.compiler.direct-linking=true" \
		"--initialize-at-build-time=clojure,k13labs,org.slf4j" \
		"--initialize-at-build-time=org.eclipse.aether.transport.http.HttpTransporterFactory" \
		"--report-unsupported-elements-at-runtime" \
		"--verbose" \
		"--no-fallback" \
		"--no-server" \
		"--allow-incomplete-classpath" \
		"--trace-object-instantiation=java.lang.Thread" \
		"-H:ConfigurationFileDirectories=graalvm-config" \
		"k13labs.tools.deps.main" \
		"build/tools-deps-helper"

format-check:
	clojure -M:format-check

format-fix:
	clojure -M:format-fix
