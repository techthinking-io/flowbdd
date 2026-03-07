### Test Suite: Dev team simulator test

**Notes:**
- Dev Team Simulator: Verify developer actions endpoints – the world's most advanced team simulator... that only masters drinking coffee and accumulating tech debt, with predetermined biased outputs ☕🚫

**Summary:** Tests: 2, Passed: 2, Failed: 0, Skipped: 0, Aborted: 0

#### Scenario: Given developer is "Alice" [PASSED]
**Steps:**
Given developer is "Alice"
When developer drinks coffee
Then developer gets performance boost

**Interactions:**

```mermaid
sequenceDiagram
	actor User
	participant Dev Team Simulator
	User->>Dev Team Simulator: /dev/Alice/drinks-coffee
	Dev Team Simulator->>User: {"developer":"Alice","boost":40,"message":"1.21 Gigawatts of caffeine! Alice is seeing some serious productivity."} [200]
```

#### Scenario: Given developer is "Bob" [PASSED]
**Steps:**
Given developer is "Bob"
When developer does no testing
Then developer gets tech dept

**Interactions:**

```mermaid
sequenceDiagram
	actor User
	participant Dev Team Simulator
	User->>Dev Team Simulator: /dev/Bob/does-no-testing
	Dev Team Simulator->>User: {"developer":"Bob","debt":10,"message":"Bob tonight, we dine in Technical Debt! For tomorrow, we push to production"} [200]
```
