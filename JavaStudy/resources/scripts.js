
function add(a, b) {
	return a + b;
}

var f1 = function(name) {
    print("JS: " + name);
    return "Greeting from JS";
}
var f2 = function(obj) {
    print("JS Class: " + Object.prototype.toString.call(obj));
}

var cls = Java.type("nashorn.NashornTest");
print(cls.greeting('Zhijie'));

cls.getCls(123);
//class java.lang.Integer

cls.getCls(49.99);
//class java.lang.Double

cls.getCls(true);
//class java.lang.Boolean

cls.getCls("hi there")
//class java.lang.String

cls.getCls(new Number(23));
//class jdk.nashorn.internal.objects.NativeNumber

cls.getCls(new Date());
//class jdk.nashorn.internal.objects.NativeDate

cls.getCls(new RegExp());
//class jdk.nashorn.internal.objects.NativeRegExp

cls.getCls({foo: 'bar'});
//class jdk.nashorn.internal.scripts.JO4

cls.f({a:'a',b:'b'});

function Person(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.getFullName = function() {
        return this.firstName + " " + this.lastName;
    }
}

var person1 = new Person("Zhijie", "Liu");
cls.callMethodOfJSObject(person1);

