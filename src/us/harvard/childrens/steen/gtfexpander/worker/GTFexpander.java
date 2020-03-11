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
	
	public static int expand(String inputfile, String outputfile, ExpansionType type) {
		if(type.equals(ExpansionType.ExonCDS_gtf)) {
			return expandExonCDSgtf(inputfile, outputfile);
		} else if(type.equals(ExpansionType.PlamsoDB_gff)) {
			return expandPlasmoDBgff(inputfile, outputfile);
		}
		return 1;
	}
	
	public static int expandExonCDSgtf(String inputfile, String outputfile) {
		Map<String,Gene> gtflines = new HashMap<String,Gene>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(inputfile)));
			String line;
			while((line=reader.readLine())!=null) {
				String geneid = Utils.findGTFID(line, "gene_id");
				Gene g = null;
				if(gtflines.containsKey(geneid)) {
					g = gtflines.remove(geneid);
				} else {
					g = new Gene();
				}
				if(g!=null) {
					g.addGTF(line);
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
	
	public static int expandPlasmoDBgff(String inputfile, String outputfile) {
		Map<String,Gene> genes = new HashMap<String,Gene>();
		Map<String,String> transcript_gene_table = new HashMap<String,String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(inputfile)));
			String line;
			while((line=reader.readLine())!=null) {
				if(!line.startsWith("#")) {
					if(line.equals("PbANKA_11_v3\tEuPathDB\texon\t674236\t674385\t.\t+\t.\tID=exon_PBANKA_1118100-E1;Parent=PBANKA_1118100.2,PBANKA_1118100.1")) {
						boolean f = true;
						System.out.println(f);
					}
					if(line.contains("\tgene\t")) {
						String geneid = Utils.findGFFID(line, "ID");
						Gene g = null;
						if(genes.containsKey(geneid)) {
							g = genes.remove(geneid);
						} else {
							g = new Gene();
						}
						if(g!=null) {
							g.addGFF(line,"gene");
							genes.put(geneid, g);
						}
					} else if(line.contains("\tmRNA\t") || line.contains("\trRNA\t") || line.contains("\ttRNA\t") || line.contains("\tncRNA\t") || line.contains("\tsnRNA\t") || line.contains("\tsnoRNA\t") || line.contains("\t[a-zA-Z]*RNA\t")) {
						String transcirptid = Utils.findGFFID(line, "ID");
						String geneid = Utils.findGFFID(line, "Parent");
						transcript_gene_table.put(transcirptid, geneid);
						Gene g = null;
						if(genes.containsKey(geneid)) {
							g = genes.remove(geneid);
						} else {
							g = new Gene();
						}
						if(g!=null) {
							g.addGFF(line,"transcript");
							genes.put(geneid, g);
						}
					} else {
						String transcriptid = Utils.findGFFID(line, "Parent");
						String[] transcriptids = transcriptid.split(",");
						for(int i = 0; i < transcriptids.length; ++i) {
							String geneid = transcript_gene_table.get(transcriptids[i]);
							Gene g = null;
							if(genes.containsKey(geneid)) {
								g = genes.remove(geneid);
							} else {
								g = new Gene();
							}
							if(g!=null) {
								g.addGFF(line.replace(transcriptid, transcriptids[i]),"other");
								genes.put(geneid, g);
							}
						}
					}
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
			for(String key : genes.keySet()) {
				if(first==false) {
					writer.write("\n");
				}
				writer.write(genes.get(key).writeTranscript());
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
