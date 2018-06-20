/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartXMLParser.Exceptions;

/**
 *
 * @author Saul
 */
public class ElementNotFoundException extends RuntimeException {
	public ElementNotFoundException(String elementID) {
		super(elementID);
	}
}
