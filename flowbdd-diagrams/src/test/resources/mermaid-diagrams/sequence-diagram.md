# Sequence Diagram

```mermaid
sequenceDiagram
	participant User
	participant System
	participant Database
	User->>System: Request data
	System->>Database: Query data
	Database-->>System: Return data
	System-->>User: Display data
```
