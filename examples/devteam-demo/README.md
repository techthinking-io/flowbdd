# Dev Team Simulator Demo Runner

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
