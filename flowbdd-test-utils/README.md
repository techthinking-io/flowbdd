Purpose:
* To work with smart-bdd
* Consistent 
* Fluent
* Provide default values
* Provide default objects
* Expose the fields that are under test
* Validate builders linting/reflect
* Choose the style/level of builder that suites your project

Rules:
* Use on only builders for own objects with more than one field
* Builders have no logic
* Factories provide pre-built default builders
* All builders implement Builder<T>
* All Collections are final 

Goals:
* Create and highlight the rules on a website - Github and comments?
* Figure out how to enforce the rules/style?

More details:
* smart-bdd works best with a fluent builders.
  * It describes what is under test - giving you a concise and consistent view layer 
  * It is productive - as you easily set the state for the test
  * You can add more test coverage - you don't have to worry about your framework not supporting your test scenario
  * You have less code - you don't have to repeat or duplicate code that sets state

Interesting projects that we could do articles on:
* Lombok - https://www.baeldung.com/lombok-builder
* CallBuilder - https://github.com/google/CallBuilder
* make-it-easy - https://github.com/npryce/make-it-easy


Builder working progress
```
class public ParentItem build() { 
    return new ParentItem(build(items), childString); 
}
```

```
private List<ItemBuilder> testCases; build(items)
```
* Pros - only need to implement build()
* Cons - you might end up with complex build(Map<String, List<Integer>)
```
private ItemListBuilder items; items.build()
```
* Pros - can handle any datatype
* Cons - have to create extra collection builders


Collections considerations
```
public ParentItem withItems(List<ItemBuilder> itemBuilders){ 
    this.itemBuilders.clear();
    this.itemBuilders.addAll(itemBuilders); return this; 
}

public ParentItem addItem(ItemBuilder itemBuilder) {
    this.itemBuilders.add(itemBuilder);
    return this;
 }
````
Or expose itemBuilders or have a CollectionsBuilder?
```
CollectionsBuilder
withItem().add(itemBuilder)
withItem().remove(itemBuilder)
```



Appendix:
Other projects:
* Lombok - https://www.baeldung.com/lombok-builder
* CallBuilder - https://github.com/google/CallBuilder
```
public class Person {
  @CallBuilder
  Person(
      String familyName,
      String givenName,
      List<String> addressLines,
      @Nullable Integer age) {
    // ...
  }
}

Person friend = new PersonBuilder()
    .setAddressLines(Arrays.asList("1123 Easy Street", "Townplace, XZ"))
    .setAge(22)
    .setGivenName("John")
    .setFamilyName("Doe")
    .build();
```  

* make-it-easy https://github.com/npryce/make-it-easy
``` 
Maker<Apple> appleWith2Leaves = an(Apple, with(2, leaves));
Maker<Apple> ripeApple = appleWith2Leaves.but(with(ripeness, 0.9));
Maker<Apple> unripeApple = appleWith2Leaves.but(with(ripeness, 0.125));

Apple apple1 = make(ripeApple);
Apple apple2 = make(unripeApple);

Banana defaultBanana = make(a(Banana));
Banana straightBanana = make(a(Banana, with(curve, 0.0)));
Banana squishyBanana = make(a(Banana, with(ripeness, 1.0)));
```
