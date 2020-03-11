package us.harvard.childrens.steen.gtfexpander.worker;

import java.util.HashMap;
import java.util.Map;

public class Gene {
	private Map<String,Transcript> transcripts = new HashMap<String,Transcript>();
	long start = -1;
	long end = -1;
	String chr = "";
	String geneid = "";
	String source = "";
	String strand = "";
	String tag = "";
	
	public void addGFF(String line, String type) {
		if(type.equals("gene")) {
			String[] linesplit = line.split("\t");
			if(chr.equals("")) {
				chr = linesplit[0];
			}
			if(source.equals("")) {
				source = linesplit[1];
			}
			if(strand.equals("")) {
				strand = linesplit[6];
			}
			if(geneid.equals("")) {
				geneid = Utils.findGFFID(line, "ID");
			}
			long line_start = Long.parseLong(linesplit[3]);
			long line_end = Long.parseLong(linesplit[4]);
			if(start==-1 || start>line_start) {
				start = line_start;
			}
			if(end==-1 || end<line_end) {
				end = line_end;
			}
			if(tag.equals("")) {
				String[] tagsplit = linesplit[8].split(";");
				String tag = "tag \"";
				for(int i = 0; i < tagsplit.length; ++i) {
					if(!tagsplit[i].startsWith("ID=") && !tagsplit[i].startsWith("Parent=")) {
						if(i+1!=tagsplit.length) {
							tag = tag + tagsplit[i] + "\"; tag \"";
						} else {
							tag = tag + tagsplit[i] + "\";";
						}
					}
				}
			}
		} else if(type.equals("transcript")) {
			String transcriptid = Utils.findGFFID(line, "ID");
			Transcript t = null;
			if(transcripts.containsKey(transcriptid)) {
				t = transcripts.remove(transcriptid);
			} else {
				t = new Transcript();
			}
			if(t!=null) {
				t.addlineGFF(line, type);
				transcripts.put(transcriptid, t);
			}
		} else if(type.equals("other")) {
			String transcriptid = Utils.findGFFID(line, "Parent");
			Transcript t = null;
			if(transcripts.containsKey(transcriptid)) {
				t = transcripts.remove(transcriptid);
			} else {
				t = new Transcript();
			}
			if(t!=null) {
				t.addlineGFF(line, type);
				transcripts.put(transcriptid, t);
			}
		}
	}
	
	public void addGTF(String line) {
		String transcriptid = Utils.findGTFID(line, "transcript_id");
		Transcript t = null;
		if(transcripts.containsKey(transcriptid)) {
			t = transcripts.remove(transcriptid);
		} else {
			t = new Transcript();
		}
		if(t!=null) {
			t.addlineGTF(line);
			transcripts.put(transcriptid, t);
			if(chr.equals("")) {
				chr = t.chr;
			}
			if(source.equals("")) {
				source = t.source;
			}
			if(strand.equals("")) {
				strand = t.strand;
			}
			if(geneid.equals("")) {
				geneid = t.geneid;
			}
			long line_start = t.start;
			long line_end = t.end;
			if(start==-1 || start>line_start) {
				start = line_start;
			}
			if(end==-1 || end<line_end) {
				end = line_end;
			}
		}
	}
	
	public String writeTranscript() {
		String genestr = chr + "\t" + source + "\tgene\t" + start + "\t" + end + "\t.\t" + strand + "\t.\tgene_id \"" + geneid + "\"; transcript_id \"" + geneid + "\";";
		if(!tag.equals("")) {
			genestr = genestr + " " + tag;
		}
		for(String key : transcripts.keySet()) {
			genestr = genestr + "\n" + transcripts.get(key).writeTranscript();
		}
		return genestr;
	}
}
