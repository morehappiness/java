import static java.lang.System.out;
public class InheritanceTest {

	public static void main(String[] args) {
		new Sub(1);
	}

}

class Base {
	public Base() {
		out.println("Base");
	}

	public Base(int i) {
		out.println("Base 1");
	}
}
class Sub extends Base {
	public Sub() {
		out.println("Sub");
	}
	public Sub(int i) {
		out.println("Sub 1");
	}
}
