jutt
====

Javascript Unit Test for Templates

Tests
[![Build Status](https://travis-ci.org/erickzanardo/jutt.png)](https://travis-ci.org/erickzanardo/jutt)


```java

// getters, getters and try omitted
@Test
public void test() {
	URL engine = new File("src/main/webapp/js/doT.js").toURI().toURL();
	URL parser = TestClass.class.getResource("/dotParser.js");
	TemplateTest templateHelper = new TemplateTest(engine, parser);

	templateHelper.addAdditionalFile(TestClass.class.getResource("/mockObjects.js"));

	templateHelper.addAdditionalScript("mockObject = {mockField : 'mockValue'};");

	MyService service = new MyService();
	JsonObject result = service.query();
	
	String templateStr = genericFileReadMethod("src/main/webapp/templates/test.xml");
	TestResult templateResult = templateHelper.doTemplateAsResult(templateStr, result);
	assertNotNull(templateResult);

	List<String> contents = templateResult.contents("tbody tr td");
	assertEquals(3, contents.size());

	assertEquals("VALUE 2", contents.get(0));
	assertEquals("VALUE 3", contents.get(1));
	assertEquals("VALUE", contents.get(2));

	String href = result.attr("a", "href");
	assertEquals("bla.html#test", href);
}
```
