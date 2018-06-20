/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject3;

import static SmartXMLParser.Parser.getPathWithinDiffCase;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import java.util.List;
import org.dom4j.DocumentException;

/**
 *
 * @author Saul
 */
public class MainClass {
	/*private static final String DESIRED_ELEMENT_ID = "make-everything-ok-button";*/
	private static final String DESIRED_ELEMENT_ID_SELECTOR = "[@id='make-everything-ok-button']";
	
	public static void main(String args[]) throws DocumentException {
		if(args.length < 2) {
			System.out.printf("You need to specify Original File Source and Diff File Source");
			return;
		}
		
		System.out.println("Path of matched element from diff source: " + getPathWithinDiffCase(DESIRED_ELEMENT_ID_SELECTOR
		, args[0]
		, args[1]));
	}
}
