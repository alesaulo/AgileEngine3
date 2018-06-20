/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartXMLParser;

import SmartXMLParser.Exceptions.ElementNotFoundException;
import SmartXMLParser.Exceptions.MoreThanOneElementFoundException;
import SmartXMLParser.Exceptions.NoElementMatchFromDiffSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Saul
 */
public class Parser {
	//private static final int MIN_MATCHES
	
	public static String getPathWithinDiffCase(String elementIDFromOriginalCase
		, String fileSourceOriginalCase
		, String fileSourceDiffCase) throws DocumentException {
		SAXReader sourceReader = new SAXReader();
		Document sourceDocument = sourceReader.read(fileSourceOriginalCase);
		List<Node> sourceElements = sourceDocument.selectNodes( "//*"+elementIDFromOriginalCase);
		
		if(sourceElements.isEmpty()) {
			throw new ElementNotFoundException(elementIDFromOriginalCase);
		}
		
		if(sourceElements.size() > 1) {
			throw new MoreThanOneElementFoundException(elementIDFromOriginalCase);
		}
		
		Element node = (Element)sourceElements.get(0);
		
		String[] splittedName = elementIDFromOriginalCase.split("-");
		
		Map<String,String[]> attributesMap = new HashMap<>();
		int attributesCount = node.attributeCount();
		for(Attribute attribute : (List<Attribute>)node.attributes()) {
			attributesMap.put(attribute.getName(), attribute.getValue().split("[- ]"));
		}
		
		SAXReader diffReader = new SAXReader();
		Document diffDocument = diffReader.read(fileSourceDiffCase);

		int maxMatch = 0;
		Element diffElementWithMaxMatch = null;
		List list = diffDocument.selectNodes("//*"); 
		for (Iterator iter = list.iterator(); iter.hasNext(); ) {
			Element diffNode = (Element)iter.next();
			int matches = findMatches(diffNode, attributesMap);
			//xPathToMatchesMap.put(diffNode, matches);
			
			if(matches > maxMatch) {
				maxMatch = matches;
				diffElementWithMaxMatch = diffNode;
			}
		}
		
		if(diffElementWithMaxMatch == null) {
			throw new NoElementMatchFromDiffSource(elementIDFromOriginalCase);
		}
		
		System.out.println("Numbre of total matches for matched element: " + maxMatch + 
			"(these matches consist of splitted attr values from matched element that match to splitted attr values from original source)");
		return diffElementWithMaxMatch.getPath();
	}

	//Compares all the attr values from diffNode to attr values from attr map
	// from source file. The return matches
	//
	// attr values are splitted by - and whitespace
	private static int findMatches(Element diffNode
		, Map<String, String[]> attributesMap) {
		int matches = 0;
		for(String[] attrValuesFromOriginalSource :  attributesMap.values()) {
			for(Attribute attribute : (List<Attribute>)diffNode.attributes()) {
				for(String attrValueFromDiffSource : attribute.getValue().split("[- ]")) {
					for(String attrValueFromOriginalSource : attrValuesFromOriginalSource) {
						if(attrValueFromDiffSource.contains(attrValueFromOriginalSource)) {
							matches++;
						}
					}
				}
			}
		}
		
		return matches;
	}
}
