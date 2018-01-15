package kr.co.infopub.chapter.s089_2;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;

public class BillboardAccordianFxController {
    @FXML
    private Accordion baccordina;
    ObservableList<BillbaordProperty> obsbills;
    ImageFXListRequest  request=new ImageFXListRequest();
    @FXML
    void initialize() {
		getAllBillboard();
		showAllBillboard();
    }
    public void getAllBillboard(){
    	RequestFromBillboardHot rfw=new RequestFromBillboardHot();
		String a="https://www.billboard.com/charts/hot-100/";
		String rs=rfw.getTimeDate(a);
		//rs=rfw.toWantedDay(rs, 1);//1����
		System.out.println(rs);
		rfw.getAllHtml(a+rs);
		String str="<article class=\"chart-row";
		rfw.getBillboardData(str);
	
		ArrayList<Billbaord> bills=rfw.getBillbaords();
		request.getAllImages(bills);  //image�� �ʿ� ����
		obsbills= BillboardConvert.toObservBill(bills);
    }
    public void showAllBillboard(){
    	if(obsbills!=null){
    		for(BillbaordProperty bp: obsbills){
    			TitledPane mytitle = new TitledPane();  //Ÿ��Ʋ�����غ�
    			mytitle.setText(bp.getRank()+". "+bp.getArtist());
        		ImageView theImage = new ImageView(request.loadImage(bp.getImagesrc()));
        		theImage.setOnMouseClicked(event -> {
        			System.out.println(mytitle.getText()+"----------------->>>");//ok
                });
        		theImage.setFitHeight(50);
        		theImage.setFitWidth(50);
        		theImage.setPreserveRatio (false);
        		mytitle.setContent(theImage);       //�̹��� ���̱�
        		baccordina.getPanes().add(mytitle);
    		}
    	}
    }
}
