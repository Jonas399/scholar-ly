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
	private CompactSearch search;
	
	
	public CSVBuilder(CompactSearch search) {
		this.fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		this.search = search;
	}
		


	
	
	public void print() {
		try {
			
			//create dummy-file to know path (FileWriter doesnt have member method getAbsolutePath()). File is just dummy for actual file
			String tempPath = this.fileName+".csv";
			File f = new File(tempPath);
			FileWriter writer = new FileWriter(tempPath);
			
			//Write Metadata in the first two lines of CSV
			CSVUtils.writeLine(writer, Arrays.asList("SearchTerm", "YearBegin", "YearEnd", "Date")); //Line One
			
			//Now Line 2:
			List<String> meta = new ArrayList<>();
			meta.add(this.search.getTerm());
			meta.add(Integer.toString(this.search.getYear_begin()));
			meta.add(Integer.toString(this.search.getYear_end()));
			meta.add(this.search.getTimeStamp());
			CSVUtils.writeLine(writer,meta);
			
			//Write hitsPerYear for the next following lines (one line pear year)
			// Write header first 
			CSVUtils.writeLine(writer, Arrays.asList("Year","Hits"));
			int hitsPerYear[] = this.search.getHitsPerYear(); //Get HitsPerYear from Search
			
			for(int i = this.search.getYear_begin();i<=this.search.getYear_end();i++) {
				List<String> row = new ArrayList<>();
				float hits = hitsPerYear[this.search.getYear_end()-i]; //Extract hits for specific Year
				
				//now fill rows/lines
				row.add(Integer.toString(i)); 
				row.add(Float.toString(hits));
				
				CSVUtils.writeLine(writer, row); //Write to csv
			}
			
			
			writer.flush();
			writer.close();
			
			System.out.println("CSV file crated at "+f.getAbsolutePath()); //Display info where file was saved
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
}
