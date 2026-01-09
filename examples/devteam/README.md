Dev Team Simulator - The world's most advanced team simulator... that only masters drinking coffee and accumulating tech debt.

How to run

- From project root: `./gradlew :examples:devteam:bootRun`
- Then visit: `http://localhost:8080/actuator/health` or `http://localhost:8080/dev/boost?name=Alice&cups=2`

Sample

- Request: `/dev/Alice/drinks-coffee`
- Response:

```json
{
  "developer" : "Alice",
  "boost" : 40,
  "message" : "1.21 Gigawatts of caffeine! Alice is seeing some serious productivity."
}
```

### How to run the FlowBDD Server (experimental)

Since `flowbdd-server` is included as a dependency, you can also run it to trigger tests via HTTP.

From project root:
```bash
./gradlew :examples:devteam:bootRun -PmainClass=io.techthinking.flowbddserver.FlowBddServerApplication
```
(Note: You might need to adjust the port if 8085 is taken)

Then you can run a test via curl:
```bash
curl -sS -X POST http://localhost:8085/api/tests/run \
  -H 'Content-Type: application/json' \
  -d '{
        "className": "io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest",
        "methodName": "developerDrinksCoffee_getsPerformanceBoost",
        "rerunCount": 1
      }' | jq .
```
