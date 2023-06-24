package main_package23;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import objects.Figure;
import helper_package.EncodeDecode;
import helper_package.HelperFunctions;

public class VanSuFigureScraper {
	static int figureCount = 0;
	static int homeCount = 0;
	static int dynastyCount = 0;
	static int parentCount = 0;
	static int bitrhDeathCount = 0;

	static int urlCounter = 0;
	
	
	public static List<Object> figures = new ArrayList<Object>();
	static String[] figureAttributes = new String[9];
	public static void main(String[] args) {

		String url;
		Document doc;
		while (true) {
			
			if (urlCounter > 119)
				break;
//			urlCounter=23;
			if (urlCounter == 0)
				url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat";
			else
				url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat?page=" + urlCounter;
//			urlCounter=112;
			try {
				doc = Jsoup.connect(url).get();
				getFiguresPage(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// encode the list to Json, write to a file and decode back to check
		EncodeDecode.encodeToFile(figures, "VanSufiguresTest");
	}

	static void getFiguresPage(Document doc) {
	    Element table = doc.selectFirst("table.ui.selectable.celled.table");
	    
	    System.out.println("			PAGE: " + urlCounter);

	    Elements rows = table.select("tr");
	    for (Element row : rows) {
	        // Get the data from each cell in the row
	        Elements cells = row.select("td");
	        if (!cells.isEmpty()) {
	            figureAttributes[0] = cells.get(0).text().trim();
	            Element cell = cells.get(1);
	            String cellContent = cell.html().replaceAll("<br>", " "); // Replace <br> with a space
//	            figureAttributes[7] = 
//	            String replace = normalizeString();
	            System.out.println(Jsoup.parse(cellContent).text().trim());
	            figureAttributes[6] = extractDynasty(Jsoup.parse(cellContent).text().trim());
	            figureAttributes[7] = cells.get(2).text().trim();
	            //figureAttributes[1] = "Không rõ";
	            Element firstCell = cells.first();
	            Element link = firstCell.selectFirst("a");
	            if (link != null) {
	                String href = link.attr("href");
	                accessDetail(href);
	            }
//	            if (modalElement != null) {
//	                // Extract the content from the modal element
//	                String modalContent = modalElement.text().trim();
//	                System.out.println("Modal Content: " + modalContent);
//	            }
	             System.out.println("\n	Figure COUNT "+figureCount);
	            
	            figureCount++;

	            // Create a new Figure object with the extracted data
	            Figure myFigure = new Figure(figureAttributes[0], figureAttributes[1], figureAttributes[2],
     					figureAttributes[3], figureAttributes[4], figureAttributes[5], figureAttributes[6],
     					figureAttributes[7], figureAttributes[8]);
	            figures.add(myFigure);
	            HelperFunctions.prtFigureAttributes(myFigure);
	        }
	    }

	    urlCounter ++;
	}
	
	private static void accessDetail(String tail) {
				// set initial attributes to unknown
		for (int i = 1; i <= 5; i++)
			figureAttributes[i] = "Không rõ";
		figureAttributes[8] = "Không rõ";
		
        String url = "https://vansu.vn" + tail;
        try {
        	boolean foundName = false;
    		boolean foundBirth = false;

            Document doc = Jsoup.connect(url).get();
            StringBuilder desc = new StringBuilder();
            Element table = doc.selectFirst("table.ui.selectable.celled.table");
            if (table != null) {
            	 Elements rows = table.select("tr");
            	// get other name and birth/death
            	 if (rows.size() >= 2) {
            		 for (int i=0;i<2;i++) {
            			 Element firstRow = rows.get(i);
            			 Element firstCell = firstRow.selectFirst("td");
            			 String firstCellContent = firstCell.text().trim();
            			 Element secondCell;
//                         String secondCellContent = secondCell.text().trim();
                         
            			 if (firstCellContent.equals("Tên khác")) {
            				 secondCell = firstRow.select("td").get(1);
            				 figureAttributes[1] = secondCell.text().trim();
            				 foundName=true;
                             }

                         if (firstCellContent.equals("Năm sinh")) {
                        	 secondCell = firstRow.select("td").get(1);
                        	 extractBirth(secondCell.text().trim());
                        	 foundBirth=true;
                         }
            			 
            		 }

                 }
            	 // get description
                if (!rows.isEmpty()) {
                    Element lastRow = rows.last();
                    Elements cells = lastRow.select("td");

                    for (Element cell : cells) {
                        desc.append(cell.text().trim());
                    }
                }
                
                if (foundName==false)
                	System.out.println("HEHE");
                
                //get parents
                extractParentsName(desc.toString());
                
                //assign description
                figureAttributes[8] = desc.toString();

            }
		} catch (IOException e) {
			return;
		}
	}
	
	private static String extractDynasty(String input) {
		String pattern = "(?<=^-\\s).*?(?=\\s*-\\s\\(|\\s*$)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            String section = matcher.group().trim();
            return section.replaceAll("\\(.*?\\)", "").trim();
        }
        return input.trim();
	    }



	
	private static void extractBirth(String content) {
		String[] parts = content.split("-");

	    if (parts.length == 2) {
	        String firstYear = parts[0].trim();
	        String secondYear = parts[1].trim();

	        // Check if the first year contains ellipsis (…)
	        if (firstYear.endsWith("…")) {
	            figureAttributes[3] = extractYear(firstYear);
	        } else {
	            figureAttributes[2] = extractYear(firstYear);
	        }

	        // Check if the second year contains ellipsis (…)
	        if (secondYear.startsWith("…")) {
	            figureAttributes[2] = extractYear(secondYear);
	        } else {
	            figureAttributes[3] = extractYear(secondYear);
	        }
	    }

	}
		

	private static String extractYear(String text) {
	    String year = text.replaceAll("\\D+", "");
//	    String trailingText = text.replaceAll("Trcn", "TCN");
	    return year.isEmpty() ? "không rõ" : year;
	}
	
	static void extractParentsName(String text) {
	    String[] keywords = {"là con trai của","là con của", "con thứ \\d+ của","con của","con gái","con trai"};

	    String pattern = "(?i)(" + String.join("|", keywords) + ")\\s+([A-ZÀ-Ỹ][a-zà-ỹ\\s]+)";

	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(text);

	    if (matcher.find()) {
	        String dynastyName = matcher.group(2);
	        figureAttributes[4] = dynastyName.trim();
	    }
	}

}