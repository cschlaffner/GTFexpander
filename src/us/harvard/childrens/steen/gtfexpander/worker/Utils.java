package us.harvard.childrens.steen.gtfexpander.worker;

public class Utils {
	public static String findGTFID(String line, String keyword) {
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
	
	public static String findGFFID(String line, String keyword) {
		String id = "";
		keyword = keyword + "=";
		int idx = line.indexOf(keyword);
		idx = idx + keyword.length();
		while(idx < line.length() && line.charAt(idx)!=';') {
			id = id + line.charAt(idx);
			++idx;
		}
		return id;
	}
}
