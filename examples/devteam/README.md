Dev Team Simulator – A small fun BDD example using Flow BDD to generate documentation.

How to run

- From project root: `./gradlew :examples:devteam:bootRun`
- Then visit: `http://localhost:8080/actuator/health` or `http://localhost:8080/dev/boost?name=Alice&cups=2`

Sample

- Request: `/dev/Alice/drinks-coffee`
- Response:

```
{
  "developer" : "Alice",
  "boost" : 40,
  "message" : "1.21 Gigawatts of caffeine! Alice is seeing some serious productivity."
}
```
