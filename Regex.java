import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program demonstrates the ise of regular expressions.
 * August 19, 2022.
 */
public class Regex {
	public static void main(String[] args) {
		Pattern pat = Pattern.compile("([\\w.]+)+@([\\w.]+)");
		Matcher mat = pat.matcher("blah p.gavin@gmail.com yatta @ foo@bar");

		List<String> list = new ArrayList<>();
		while (mat.find()) {
			list.add(mat.group());
		}

		if (list.size() == 0) System.out.println("Not found");

		System.out.println(list);




	}	
}