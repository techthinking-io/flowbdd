# Complex Sequence Diagram

```mermaid
sequenceDiagram
	actor Customer
	participant WebApp
	participant API
	participant Database
	Customer->>WebApp: Login
	WebApp->>API: Authenticate
	API->>Database: Verify credentials
	Database-->>API: User authenticated
	API-->>WebApp: Authentication successful
	WebApp-->>Customer: Welcome!
```
