package org.AgileEngineExample.model;

import java.util.List;
import java.util.Optional;

public class Pictures {
	private List<PictureBasicData> pictures;

	public List<PictureBasicData> getPictureBasicData() {
		return pictures;
	}

	public void setPictureBasicData(List<PictureBasicData> pictureBasicData) {
		this.pictures = pictureBasicData;
	} 
	
	public Optional<PictureBasicData> getByID(String id) {
		return pictures
		.stream()
		.filter( pbd -> pbd.getId().equals(id))
		.findAny();
	}
}
