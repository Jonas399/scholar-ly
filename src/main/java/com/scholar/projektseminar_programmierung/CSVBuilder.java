package com.scholar.projektseminar_programmierung;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CSVBuilder {
	private String fileName;
	private Search search;
	
	
	public CSVBuilder(Search search) {
		this.fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		this.search = search;
	}
		


	
	
	public void print() {
		try {
			String tempPath = this.fileName+".csv";
			//create file to know path (FileWriter doesnt have method getAbsolutePath())
			File f = new File(tempPath);
			FileWriter writer = new FileWriter(tempPath);
			
			
			List<Search> searchList = Arrays.asList(this.search);
			
			//Write Metadata
			CSVUtils.writeLine(writer, Arrays.asList("SearchTerm", "YearBegin", "YearEnd", "Date"));
			List<String> meta = new ArrayList<>();
			meta.add(this.search.getTerm());
			meta.add(Integer.toString(this.search.getYear_begin()));
			meta.add(Integer.toString(this.search.getYear_end()));
			meta.add(this.search.getTimeStamp());
			CSVUtils.writeLine(writer,meta);
			
			//Write hitsPerYear
			// Header First
			CSVUtils.writeLine(writer, Arrays.asList("Year","Hits"));
			//Get HitsPerYear from Search
			int hitsPerYear[] = this.search.getHitsPerYear();
			for(int i = this.search.getYear_begin();i<=this.search.getYear_end();i++) {
				List<String> row = new ArrayList<>();
				//Extract hits for specific Year
				float hits = hitsPerYear[this.search.getYear_end()-i];
				row.add(Integer.toString(i));
				//row.add(Integer.toString(hits));
				row.add(Float.toString(hits));
				CSVUtils.writeLine(writer, row);
			}
			
			
			writer.flush();
			writer.close();
			System.out.println("CSV file crated at "+f.getAbsolutePath());
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
}
