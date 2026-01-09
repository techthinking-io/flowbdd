### flowbdd-server

#### What is it?
`flowbdd-server` is a small Spring Boot app that lets you run JUnit/FlowBDD tests on demand via HTTP. You can trigger a specific test class or method, optionally re-run failures, and quickly jump to FlowBDD living docs generated on disk.

In future, it will facilitate AI prompts in reference to `devteam` example e.g.:
```
POST /api/agent/chat { "prompt": "Does Alice ever get a 401?" }
POST /api/agent/chat { "prompt": "How does a developer get a productivity boast?" }
```

Note: By default, FlowBDD generates static reports, so this server is required for dynamic behavior.

#### Why?
- Re-run a specific test from a UI or a simple `curl`.
- Parameterize runs (class, method, tags, rerun count).
- AI prompts.

#### Requirements
- Java 11
- Gradle (project already includes wrapper)

#### How it works
The app embeds the JUnit Platform Launcher to programmatically discover and execute tests on the classpath. If your tests use FlowBDD, the FlowBDD JUnit 5 extension will generate reports to its standard locations (by default under `/tmp/flowbdd`). 

Out of the box, the server exposes:
- `POST /api/tests/run` – run tests with parameters
- `GET /api/tests/status` – fetch the last run summary
- `GET /` – a static `index.html` with a simple form that posts to the API

#### Parameters (POST /api/tests/run)
Example payload:
```json
{
  "className": "io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest",
  "methodName": "developerDrinksCoffee_getsPerformanceBoost",
  "tags": ["smoke"],
  "rerunCount": 1
}
```
Notes:
- Omit `methodName` to run the whole class.
- Omit both `className` and `methodName` to run everything on the classpath.
- `tags` filters will include only tests that match any of the provided tags.
- `rerunCount` > 0 will attempt to re-run only failed tests up to that number of times.

#### How to add to your project
If you have a test project – include the dependency in your `build.gradle.kts`:
```kotlin
implementation("io.techthinking:flowbdd-server:0.1.1-SNAPSHOT")
```

Example POM for Maven:
```xml
<groupId>io.techthinking</groupId>
<artifactId>flowbdd-server</artifactId>
<version>0.1.1-SNAPSHOT</version>
```

#### Running locally
You can run from IntelliJ or Gradle.

1) IntelliJ IDEA
- Open the project.
- Run the `FlowBddServerApplication` class.
- Open http://localhost:8085 in your browser.

2) Gradle
- From repo root:
```
./gradlew :flowbdd-server:bootRun
```
- Open http://localhost:8085

3) Plain Java
- From repo root after build:
```
./gradlew :flowbdd-server:build
java -jar flowbdd-server/build/libs/flowbdd-server-0.1.1-SNAPSHOT.jar
```

#### FlowBDD report locations
By default FlowBDD writes to:
- HTML reports: `/tmp/flowbdd/report`
- Data JSON: `/tmp/flowbdd/data`

You can override via JVM system properties when launching the server:
```
-Dflowbdd.report.dir=/your/custom/report
-Dflowbdd.data.dir=/your/custom/data
```

#### Example curl
Run a single method:
```
curl -sS -X POST http://localhost:8085/api/tests/run \
  -H 'Content-Type: application/json' \
  -d '{
        "className": "io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest",
        "methodName": "developerDrinksCoffee_getsPerformanceBoost",
        "rerunCount": 1
      }' | jq .
```

Check last status:
```
curl -sS http://localhost:8085/api/tests/status | jq .
```

#### Next ideas
- Optional token auth for remote usage.
- Store metrics.
- Serve the generated FlowBDD HTML directly from the app.
- Replace static `index.html` with a minimal reactive UI (could be compiled statically by Astro/Vite and served as files).
