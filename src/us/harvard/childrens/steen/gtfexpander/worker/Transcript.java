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
	String tag = "";
	
	public void addlineGTF(String line) {
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
			transcriptid = Utils.findGTFID(line, "transcript_id");
		}
		if(geneid.equals("")) {
			geneid = Utils.findGTFID(line, "gene_id");
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
	
	public void addlineGFF(String line, String type) {
		if(type.equals("transcript")) {
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
				transcriptid = Utils.findGFFID(line, "ID");
			}
			if(geneid.equals("")) {
				geneid = Utils.findGFFID(line, "Parent");
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
		} else if(type.equals("other")) {
			String[] linesplit = line.split("\t");
			String newline = "";
			for(int i = 0; i < linesplit.length; ++i) {
				if(i==0 || i==1 || i==3 || i==4 || i==5 || i==6 || i==7) {
					newline = newline + linesplit[i] + "\t";
				} else if(i==2) {
					if(linesplit[i].equals("exon") || linesplit[i].equals("CDS")) {
						newline = newline + linesplit[i] + "\t";
					} else if(linesplit[i].contains("UTR")) {
						newline = newline + "UTR\t";
					} else {
						newline = newline + "unknown\t";
					}
				} else if(i==8) {
					String[] tagsplit = linesplit[8].split(";");
					newline = newline + "gene_id \"" + geneid + "\"; transcript_id \"" + transcriptid + "\";";
					if(tagsplit.length > 2) {
						newline = newline + " tag \"";
						for(int j = 0; j < tagsplit.length; ++j) {
							if(!tagsplit[j].startsWith("ID=") && !tagsplit[j].startsWith("Parent=")) {
								if(j+1!=tagsplit.length) {
									newline = newline + tagsplit[j] + "\"; tag \"";
								} else {
									newline = newline + tagsplit[j] + "\";";
								}
							}
						}
					}
				}
			}
			lines.add(newline);
		}
	}
	
	public String writeTranscript() {
		String transcriptstr = chr + "\t" + source + "\ttranscript\t" + start + "\t" + end + "\t.\t" + strand + "\t.\tgene_id \"" + geneid + "\"; transcript_id \"" + transcriptid + "\";";
		if(!tag.equals("")) {
			transcriptstr = transcriptstr + " " + tag;
		}
		for(String line : lines) {
			transcriptstr = transcriptstr + "\n" + line;
		}
		return transcriptstr;
	}
	

}
