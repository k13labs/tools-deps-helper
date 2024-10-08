[![Build Status](https://github.com/k13labs/hierarchy/actions/workflows/clojure.yml/badge.svg)](https://github.com/k13labs/hierarchy/actions/workflows/clojure.yml)

# _About_

tools-deps-helper utility

# _Building_

tools-deps-helper is built, tested, and deployed using [Clojure Tools Deps](https://clojure.org/guides/deps_and_cli).

GNU Make is used to simplify invocation of some commands.

Requires graalvm-22.3.1+java11 with native-image installed.

```bash
make build
```

# _Usage_

```bash
build/tools-deps-helper find-versions org.clojure/clojure
```

# _Communication_

- For any other questions or issues about hierarchy free to browse or open a [Github Issue](https://github.com/k13labs/hierarchy/issues).

# Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md)

# YourKit

[![YourKit](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com/)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/dotnet-profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).

# LICENSE

Copyright 2024 Jose Gomez

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

&nbsp;&nbsp;&nbsp;&nbsp;http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
