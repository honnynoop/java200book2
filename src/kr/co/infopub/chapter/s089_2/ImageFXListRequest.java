package kr.co.infopub.chapter.s089_2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;

public class ImageFXListRequest {
	private Map<String, Image> images;
	
	public ImageFXListRequest() {
		images= Collections
				.synchronizedMap(new LinkedHashMap<String, Image>(10, 1.5f, true));
	}
	
	public synchronized Image loadImage(String imagesrc) {
		Image mimage=null;
		if (images.containsKey(imagesrc)) {
			mimage= images.get(imagesrc);
		} else {
			if(imagesrc!=null && !imagesrc.contains("q_____")){
				try{
					mimage= new Image(imagesrc,100,100,true,true);
				}catch (Exception e) {
					mimage=null;;
				}
			}
		}
		return mimage==null ? new Image(getClass().getResourceAsStream("billboard.jpg")):mimage;
	}
	
	public synchronized void getAllImages(ArrayList<Billbaord> bills){
		images.clear();//내용이 많아서 초과할 수 있다. 
		List<Billbaord> newbills=Collections.synchronizedList(bills);
		for(Billbaord bb: newbills){
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					if(!images.containsKey(bb.getImagesrc())){
//						images.put(bb.getImagesrc(), loadImage(bb.getImagesrc()));
//						System.out.println(bb.getImagesrc()+"-------Map에  Image저장---------------->>>>>>>>>>>>>>");  
//					}
//				}
//			}).start();
			new Thread(() ->{
					if(!images.containsKey(bb.getImagesrc())){
						images.put(bb.getImagesrc(), loadImage(bb.getImagesrc()));
						System.out.println(bb.getImagesrc()+"\t\t\t-------Map에  Image저장----------->>>>>>>>>>>>>>");  
					}
			}).start();
		}
	}

}
//q__________.jpg