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
	
	public void add(String line) {
		String transcriptid = Utils.findID(line, "transcript_id");
		Transcript t = null;
		if(transcripts.containsKey(transcriptid)) {
			t = transcripts.remove(transcriptid);
		} else {
			t = new Transcript();
		}
		if(t!=null) {
			t.addline(line);
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
		for(String key : transcripts.keySet()) {
			genestr = genestr + "\n" + transcripts.get(key).writeTranscript();
		}
		return genestr;
	}
}
