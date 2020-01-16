package us.harvard.childrens.steen.gtfexpander.worker;

public class Utils {
	public static String findID(String line, String keyword) {
		String id = "";
		int idx = line.indexOf(keyword);
		idx = idx + keyword.length();
		idx = line.indexOf("\"", idx) + 1;
		while(line.charAt(idx)!='\"') {
			id = id + line.charAt(idx);
			++idx;
		}
		return id;
	}
}
