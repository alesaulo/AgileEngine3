package org.AgileEngineExample;

public class Image {
	private byte[] content;
	
	private Image() {}
	
	public Image(byte[] content) {
		this.content = content;
	}
	
	public byte[] getContent() {
		return this.content;
	}
}
