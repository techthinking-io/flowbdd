# Flow Validate

A simple annotation-based validation framework for Java methods.

## Overview

> This is an experimental library for now. APIs and functionality may change in future releases. Please contact me if you have any suggestions, usages, etc. :) 

Flow Validate provides a way to validate method behavior using annotations. It allows you to specify expected input-output relationships directly in your code, making it easy to verify that your methods behave as expected.

## Usage

### Basic Usage

1. Add the `@Validate` annotation to your method, specifying the expected input-output relationship:

```java
@Validate("(5) = 10")
public int plusFive(int value) {
    return value + 5;
}
```

### Multiple Parameters

You can validate methods with multiple parameters:

```java
@Validate("(3, 4) = 12")
public int multiply(int a, int b) {
    return a * b;
}
```

### String Parameters

You can use quoted strings in your validation expressions:

```java
@Validate("(\"Hello\", \"World\") = \"HelloWorld\"")
public String concatenate(String a, String b) {
    return a + b;
}
```

## Validation Expression Format

Validation expressions follow the format `(input) = output`, where:

- `input`: The input value(s) to the method, separated by commas if there are multiple parameters
- `output`: The expected output value

Examples:
- `(5) = 10`: When called with 5, the method should return 10
- `("hello") = "HELLO"`: When called with "hello", the method should return "HELLO"
- `(2, 3) = 5`: When called with 2 and 3, the method should return 5

## Supported Types

Flow Validate supports the following types:
- Integers
- Doubles
- Strings

## License

Flow Validate is licensed under the GNU General Public License v3.0.
