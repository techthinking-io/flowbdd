Dev Team Simulator - The world's most advanced team simulator... that only masters drinking coffee and accumulating tech debt.

How to run

- From project root: `./gradlew :examples:devteam:bootRun`
- Then visit: `http://localhost:8085/actuator/health` or `http://localhost:8080/dev/boost?name=Alice&cups=2`

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

### FlowBDD Server

The `flowbdd-server` integration is provided by the `devteam-demo` module. This project runs the `flowbdd-server` alongside the `devteam` application, allowing you to trigger tests and view results via a web interface.

To run the Dev Team application with the FlowBDD server enabled:

```bash
./gradlew :examples:devteam-demo:bootRun
```

Once running, you can access:
- **Dev Team API**: [http://localhost:8085/dev-names](http://localhost:8085/dev-names)
- **Flow BDD Home**: [http://localhost:8085/](http://localhost:8085/)
- **Flow BDD Test Runner**: [http://localhost:8085/runner/index.html](http://localhost:8085/runner/index.html)

See [examples/devteam-demo/README.md](../devteam-demo/README.md) for more details.
