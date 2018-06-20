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
public class MoreThanOneElementFoundException extends RuntimeException {
	public MoreThanOneElementFoundException(String elementID) {
		super(elementID);
	}
}
