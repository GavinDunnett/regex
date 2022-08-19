import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

// This program takes the filename of a baby1990.html file and returns the data from the file as a single list -- the year string at the start of the list followed by the name-rank strings in alphabetical order. ['2006', 'Aaliyah 91', 'Abagail 895', 'Aaron 57', ...].
// If the flag --summaryfile is present, then for each input file 'foo.html', instead of printing to standard output, a new file 'foo.html.summary' is written that contains the summary text for that file.

public class BabyNames {

	public static List<String> extract_names (String filename) throws IOException {
	//SEQUENCE: FILE > YEAR > NAMES > LIST > PRINT

	//§FILE
	Path path = Path.of(filename);
	String str = Files.readString(path);
	
	//§YEAR
	int year = 0;
	Pattern pat = Pattern.compile("Popularity in (\\d\\d\\d\\d)");
	Matcher mat = pat.matcher(str);
	if (mat.find()) {
		year = Integer.parseInt(mat.group(1));
	} else {
		System.out.println("error: unable to locate year!");
	}
	
	//§NAMES
	HashMap<String, Integer> names = new HashMap<>();
	pat = Pattern.compile("<td>(\\d+)</td><td>(\\w+)</td><td>(\\w+)</td>");
	mat = pat.matcher(str);
	while (mat.find()) {
		int rank = Integer.parseInt(mat.group(1));
		String boy = mat.group(2);
		String girl = mat.group(3);
		names.put(boy, rank);
		names.put(girl, rank);
	}

	//§LIST
	List<String> list = new ArrayList<>();
	for (String key : names.keySet()) {
		String name = key;
		String rank = Integer.toString(names.get(key));
		list.add(name + " " + rank);
	}

	//§PRINT
	Collections.sort(list);
	list.add(0, Integer.toString(year));
	for (String string : list) {
		System.out.println(string);
	}

	return list;
	}
	

	public static void main(String[] args) {

		if (args.length  < 1) {
			System.out.println("error: usage BabyNames [--summaryfile] filename");
			return;
		}
		
		boolean summaryfile = false;
		if (args[0].compareTo("--summaryfile") == 0) {
			summaryfile = true;
		} else {
			summaryfile = false;
		}
		
		List<String> filenames = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			filenames.add(args[i]);
		}

		List<String> list = new ArrayList<>();

		for (String filename : filenames) {
			try {
				list = extract_names(filename);
			} catch (IOException e) {
				System.out.println("File IO Error");
				e.printStackTrace();
			}
			if (summaryfile) {
				try {
					FileWriter fw = new FileWriter(filename + ".summary");
					fw.write(list.get(0) + "\n");
					for (int i = 1; i < list.size(); i++) {
						fw.write(list.get(i) + "\n");
					}
					fw.close();
				} catch (IOException e) {
					System.out.println("error: file write failed");
					e.printStackTrace();
				}	
			}
		}
	}
}