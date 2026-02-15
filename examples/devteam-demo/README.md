# Dev Team Simulator Demo Runner

This is work in progress.
1. Dev Team having tests and being executed from this project is problematic
2. Running tests from this project shouldn't be an issue

This application is a demo runner for the Dev Team Simulator. It bundles the `devteam` application with the `flowbdd-server` to provide an interactive test running environment.

## How to run

From project root:
```bash
./gradlew :examples:devteam-demo:bootRun
```

Then visit:
- Dev Team API: `http://localhost:8085/dev-names`
- FlowBDD Test Runner: `http://localhost:8085/runner/index.html`

## Running Tests

Before running tests via the server, ensure that the tests have been compiled:
```bash
./gradlew :examples:devteam:testClasses
```

### Demo Trigger API

Flow BDD server
http://localhost:8085/api/tests/ping

Dev Team
http://localhost:8085/ping
http://localhost:8085/dev-names

Dev Team - Demo
http://localhost:8085/api/demo/ping

You can trigger tests specifically for the devteam using the custom demo controller:

```bash
curl -sS -X POST http://localhost:8085/api/demo/run-devteam-tests
```

Or run any specific test:

```bash
curl -sS -X POST http://localhost:8085/api/demo/run-custom \
  -H 'Content-Type: application/json' \
  -d '{
        "className": "DevTeamSimulatorTest"
      }'
```
