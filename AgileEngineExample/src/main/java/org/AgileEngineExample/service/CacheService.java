package org.AgileEngineExample.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.AgileEngineExample.Image;
import org.AgileEngineExample.model.PictureBasicData;
import org.AgileEngineExample.model.Pictures;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

public class CacheService {	
   private static LoadingCache<String, Image> imageCache;		
		
	public static void initialize(Pictures pictures) {
		if(imageCache == null) {
		
			CacheLoader<String, Image> loader = new CacheLoader<String, Image> () {
				  public Image load(String key) throws Exception {
					  return ImageFetchingService.getImage(key).get();
				  }
				};							
			
			imageCache = CacheBuilder.newBuilder()
				  .expireAfterWrite(2, TimeUnit.HOURS)
				  .maximumWeight(100000)
				  .weigher(new Weigher<String, Image>() {
			          public int weigh(String k, Image g) {
			            return g.getContent().length;
			          }
			        })
				  .build(loader);
			
			for(PictureBasicData pictureBasicData : pictures.getPictureBasicData()) {
				try {
					imageCache.get(pictureBasicData.getId());
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
