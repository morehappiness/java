import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class FilenameFilterTest {

	public static void main1(String[] args) {
		File f = new File(".");
		System.out.println(Arrays.toString(f.list((dir, name) -> {
			return args.length <= 0;
		})));
		int a = 1;
		System.out.println(Arrays.toString(f.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return a <= 0;
			}
		})));
	}

	public static FilenameFilter filter(String regex) {
		// Creation of anonymous inner class:
		return new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);

			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
		}; // End of anonymous inner class
	}

	public static void main(String[] args) {
		File path = new File(".");
		String[] list;
		if (args.length == 0)
			list = path.list();
		else
			list = path.list(filter(args[0]));
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list)
			System.out.println(dirItem);
	}
}
