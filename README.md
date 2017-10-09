# JavaQuery
Java library with jQuery functionality on html

Currently supports search and modification of elements.

Current supported methods:
```java
parent();
parent(String);
children();
children(String)
find(String)
getClasses();
getID();
element();
html();
text();
```

Example snippet:
```java
String html = "file:///Users/oscartorres/Dropbox/Homepage/main/main_page.html";
HtmlFile file = new HtmlFile(html);
JQuery jQuery = new JQuery(file);
JQueryObject obj = jQuery.getRoot();

System.out.println(obj.find("div").getClasses());
```

Example usage:
![Alt text](/img/ss1.png?raw=true "Usage")
