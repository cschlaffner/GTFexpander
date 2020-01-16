package us.harvard.childrens.steen.gtfexpander.worker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class GTFexpander {
	
	
	
	public static int expand(String inputfile, String outputfile) {
		Map<String,Gene> gtflines = new HashMap<String,Gene>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(inputfile)));
			String line;
			while((line=reader.readLine())!=null) {
				String geneid = Utils.findID(line, "gene_id");
				Gene g = null;
				if(gtflines.containsKey(geneid)) {
					g = gtflines.remove(geneid);
				} else {
					g = new Gene();
				}
				if(g!=null) {
					g.add(line);
					gtflines.put(geneid, g);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile)));
			boolean first = true;
			for(String key : gtflines.keySet()) {
				if(first==false) {
					writer.write("\n");
				}
				writer.write(gtflines.get(key).writeTranscript());
				first=false;
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
}
