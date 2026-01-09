# List of TODOs for the mono repo

## MVP:

- [x] Static webpage for test suites - maybe Thymeleaf
- [x] Static webpage menu
- [x] Add examples
    - [x] Bookstore
- [x] Move learning tests to own project
- [x] General code tidy
- [x] Copy write every class
    - https://choosealicense.com/licenses/mit/ - short and cucumber has this
    - https://choosealicense.com/licenses/gpl-3.0/ - means don't profit from my code
- [ ] Investigate if META-INF/Services FlowBddTestExecutionListener gets exported

## Post MVP:

- [x] Name change maybe; FlowBDD, CaffeineFlow or something else
- [x] New project for mermaid wrapper 
- [x] Update copyright
- [ ] Try scala files
- [ ] Add timestamp, hostname, time (maybe setup, execute and after times)
- [ ] Add error 
- [ ] More examples!!!
- [x] Migrate to github
- [x] Update documentation and housekeeping
- [ ] Parametrised tests (see testCaseResult.setName(...) and verifyThirdTest_paramsWithCustomName). See Notes below.
- [ ] Read the @BeforeEach @BeforeAll etc... and add to the result
- [ ] Handle all test factories, templates and dynamic
- [ ] Apply Capture standard output and standard error.
- [ ] Handle Strings i.e. anything in quotes
- [ ] Additional wordifies i.e. swap words around to create more readable sentences
- [ ] @Given, @When, @Then - These would have to be parameter annotations
- [ ] @Notes - Can add notes for the feature/scenario
- [ ] UnderTest fields - this is to highlight fields, possible change the values
- [ ] Logger than can added extra text in the report - `log.add('some notes')`
- [ ] Re-run tests endpoints
- [ ] Re-run tests ui
- [ ] Update test values in the ui - so that the test can be re-run with different values
- [ ] Store the tests in a DB
- [ ] Explore when and how builders should be the same for ft and unit tests. Is it feasible to sync builders classes
  and packages?
- [ ] Add Java 9 modules?
- [ ] Update all internal tests to JDK 11 or 17.

## Done

- [x] Parse params
- [x] Have a context
- [x] Have failing tests
- [x] Capture the failures
- [x] Stats for passed, skipped, aborted and failed
- [x] Handle names of methods that have params
- [x] Capture standard output and standard error mechanism.
- [x] Create report in Json
- [x] Website prototype
- [x] Use Kotlin for Gradle
- [x] Project structure - need modules/projects
- [x] Sequence diagrams - mermaid
- [x] Test titles to be wordified

# Notes

## Actions

Wrap steps given(), when(), then() etc... body in async actions. This can make test set parallel

### Mutation tests

Given this example that is work in progress:

```java
void countRedBooksBook(@Given Colour firstColour,@Given Colour secondColour, @Then int quantity){
    given(iHave(aBook().withColour(firstColour)));
    and(iHave(anotherBook().withColour(secondColour)));
    when(iCountAllMyBooks());
    then(iShouldHave(quantity, books()).withColour(RED));
}
```

The framework can mutate:

1. Parameters i.e. make the `secondColour` blue and the above test should fail.
2. Mutate what steps are run. Given we have created actions and above we have 4 (given, and, when, then), if we don't
   run the `and` step the above test should fail. This checks if all the `setup` and `step` actions are required.

Notice I mentioned `setup` actions. It's quite common to have setup actions that are superfluous such as resetting
metrics that are not required for every test, the mutation tests aim to identify unnecessary code and improve the
performance and correctness of the tests.

## Notes

Explore feature notes: `notes()`/`doc()` vs `@Notes`/`@Doc` annotations. Currently using ` doc()`. Annotations could
prove very useful.

Explore how data is captured:

```java
sequenceDiagram().add(aMessage().from("BookStore").to("ISBNdb").text(event.getRequest().getUrl()));
sequenceDiagram().add(aMessage().from("ISBNdb").to("BookStore").text(event.getResponse().getBodyAsString()));
sequenceDiagram().add(aMessage().from("BookStore").to("User").text(response.getBody()));
```

Should we capture data agnostic of the notion of a sequence diagram to be used for example:

```java
scenario().request().add(from("BookStore").to("ISBNdb").endpoint(event.getRequest().getUrl()));
```

### Parametrised tests

Need something to reflect a test matrix:

```
ParamTest
* [1] value 1 (PASSED)
  Passing assertion with value 1

* [2] value 2 (PASSED)
  Passing assertion with value 2
```

This means something like:

* `testCaseResult.name` = `[1] value 1`
* `testCaseResult.methodName` = `ParamTest()` should we include the signature???. Could we infer from the params?
* `testCaseResult.parentTest` = `ParamTest()`. Is this needed to group tests??? Is it the same as `methodName`?
* `testCaseResult.args` not empty

#### Options:

* scenario parent and scenario child.
* Or a test is identified with parametrized scenario? `methodName` field relates the parametrized scenarios.

### New args block. With args we can re-run test(s).

```json
{
  "wordify": "Passing assertion with string value 'blah' with int value 1",
  "wordifyTemplate": "Passing assertion with string value '{{ 0 }}' with int value {{ 1 }} ",
  "args": [
    {
      "type": "String",
      "value": "blah"
    },
    {
      "type": "Integer",
      "value": 1
    }
  ],
  "status": "PASSED",
  "methodName": "paramTest",
  "className": "ClassUnderTest",
  "packageName": "shared.undertest"
}
```

### Linting Builders

These ideas centre around builders and features that the user may or may not want. That is why linting would be a good
tool. To achieve consistency in the wordify you'd need the builders to be consistent. Note that wordify can have custom
rules, these would break down if the builders are not consistent.

### Linting Test names

Make style consistent across code

### Try to make some tests more concise

This is using assertJ as normal

```
private void assertTestSuite(TestSuite testSuite) {
    assertThat(testSuite.getName()).isEqualTo("shared.undertest.ClassUnderTest");
    assertThat(testSuite.getClassName()).isEqualTo("ClassUnderTest");
    assertThat(testSuite.getPackageName()).isEqualTo("shared.undertest");
    assertThat(testSuite.getMethodNames()).isEqualTo(asList("testMethod", "paramTest", "paramTest", "paramTest"));
    assertThat(testSuite.getTestCases()).contains(firstTestCase());
    ...
}
```

This is using a builder as normal. This is limiting as you must test all the state. This has the advantage of being more
concise.

```
private TestSuite firstTestSuite() {
    return aTestSuite()
        .withName("shared.undertest.ClassUnderTest")
        .withClassName("ClassUnderTest")
        .withPackageName("shared.undertest")
        .withMethodNames(asList("testMethod", "paramTest", "paramTest", "paramTest"))
        .withTestCases(asList(firstTestCase()))
    ...
    .build();
}
```

from https://assertj.github.io/doc/#assertj-core-assertions-guide
It is possible to use `ignoringFields(String... fieldsToIgnore)` to ignore fields. See dummy objects above.

```
assertThat(sherlock)
  .usingRecursiveComparison()
  .withStrictTypeChecking()
  .isEqualTo(detectiveSherlock);
```

Is something like this possible? It would be more concise?

```
private TestSuite assertFirstTestCase() {
    aTestSuiteAssertionBuilder()
      .withName("shared.undertest.ClassUnderTest") // assumes equality 
      .withMethodNames().contains(..) // can we chain assertJ methods would they have to be hamcrest?
      .withOtherField().startsWith(...) 
}
```

### Closet matching

- Closet matching fields. I.e. 3/4 fields match
    - `assertThat(actual).isEqualTo(expected);`
    - `assertThat(list).contains(item);`

### Reporting ideas:

* Compare log of previous run - show a diff. Can have a cut down version of the log that only shows the dif - hence what
  has gone wrong.
* Send reports to report aggregator on each test, suite, package or at the end
* Keep track of irregular issues

### Dummy Objects

Objects that are not under test, but needed to build objects. Potentially these could be ignored when assertJ checks to
equality. We have defaultBuilders that build a default object

```
private SimpleCarBuilder aDefaultCar() {
    return aCar()
        .withEngine(anEngine()
            .withType(ENGINE_TYPE)
            .withSize(ENGINE_SIZE));
}

// style 1
private SimpleCarBuilder aDummyCar() {
    return aCar()
        .withEngine(anEngine()
            .withType(DUMMY_ENGINE_TYPE)
            .withSize(DUMMY_ENGINE_SIZE));
}
SimpleCar simpleCar = aDummyCar().updateEngine().withSize(anotherEngineSize).build;

// style 2
private SimpleCarBuilder aDummyCar() {
    return aCar().withEngine(aDummyEngine());
}
// updating a dummy engine - it needs to be a real engine with dummy values. This doesn't quite work as it would need lots of boiler plate code.
SimpleCar simpleCar = aDummyCar().updateEngine().withSize(anotherEngineSize).build;


// style 3
private SimpleCarBuilder aDummyCar() {
    SimpleCarBuilder car1 = aCar().withEngine(aDefaultEngine()).setAsDummy() 
    SimpleCarBuilder car2 = dummy(aCar().withEngine(aDefaultEngine()));
    ...
}
// updating a dummy engine - it needs to be a real engine with dummy values. If dummy was an annotation then we could add remove at runtime.
SimpleCar simpleCar = aDummyCar().updateEngine().withSize(anotherEngineSize).build;

// assert
assertThat(getCar().withEngine(anotherEngineSize))).isEqualTo(simpleCarBuilder);
```

### flowbdd projects to do:

* client-side-report or webpage or webapp - dynamic React web app. Have created a Vue.js app, React would be better.
* test-re-runner - rest app to select a test and parse in params. FT's have a dependency on this and can therefore spin
  up Spring Boot app?
* flowbdd-report-shipper - shipping results
    * create webpage?
    * file data json/xml
    * data to db
    * data to rest service
* Create builders from JSON

Temp Bug:
Bug (L = 2 spaces
```java
givenTheFollowingGroceries(List.of(
item("milk", 9),
item("bread", 7),
item("soap", 5)
));
whenIPay(25);
```

```text
expected: <4> but was: <7>
Expected :4
Actual   :7
<Click to see difference>

org.opentest4j.AssertionFailedError: expected: <4> but was: <7>
at app//org.junit.jupiter.api.AssertionFailureBuilder.build(AssertionFailureBuilder.java:151)
```
