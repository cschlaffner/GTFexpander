package us.harvard.childrens.steen.gtfexpander.worker;

import java.util.ArrayList;
import java.util.List;

public class Transcript {
	private List<String> lines = new ArrayList<String>();
	long start = -1;
	long end = -1;
	String chr = "";
	String transcriptid = "";
	String geneid = "";
	String source = "";
	String strand = "";
	
	public void addline(String line) {
		lines.add(line);
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
		if(transcriptid.equals("")) {
			transcriptid = Utils.findID(line, "transcript_id");
		}
		if(geneid.equals("")) {
			geneid = Utils.findID(line, "gene_id");
		}
		long line_start = Long.parseLong(linesplit[3]);
		long line_end = Long.parseLong(linesplit[4]);
		if(start==-1 || start>line_start) {
			start = line_start;
		}
		if(end==-1 || end<line_end) {
			end = line_end;
		}
	}
	
	public String writeTranscript() {
		String transcriptstr = chr + "\t" + source + "\ttranscript\t" + start + "\t" + end + "\t.\t" + strand + "\t.\tgene_id \"" + geneid + "\"; transcript_id \"" + transcriptid + "\";";
		for(String line : lines) {
			transcriptstr = transcriptstr + "\n" + line;
		}
		return transcriptstr;
	}
	

}
