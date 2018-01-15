package kr.co.infopub.chapter.s089;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
public class BillboardAccordianFxController {
	
 @FXML
 private Accordion baccordina;
 @FXML
 private TreeView<String> youtrv;
 @FXML
 private WebView webview;
 ObservableList<BillbaordProperty> obsbills;
 ImageFXListRequest  request=new ImageFXListRequest();
 YoutupeParser parser=new YoutupeParser();
 TreeItem<String> root = new TreeItem<String>("Singer ");

 @FXML
 void initialize() {
	getAllBillboard();
	showAllBillboard();
	youtrv.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue) -> {
    			if(newValue!=null){
    				final WebEngine webEngine = webview.getEngine();
    				 //https://www.youtube.com/watch?v=2vjPBrBU-TM
    				String ttt=String.format("https://www.youtube.com/watch?v=%s",
    						           ((TreeItem<String>)newValue).getValue());
    				webEngine.load(ttt);
    			}
    		});
	youtrv.setRoot(root);
 }
 public void loadTreeItems(String key){
	root.getChildren().clear();
	TreeItem<String> abs = new TreeItem<String>(key);
	root.getChildren().add(abs);
	root.setExpanded(true);
	ArrayList<Youtube> youlist=parser.getTitles(key);
	for(Youtube you:youlist){
		TreeItem<String> aa=new TreeItem<String>(you.getUrl());
		abs.getChildren().add(aa);
	   	System.out.println(you.getUrl()+"---------------------------->");
	}
 }
 public void getAllBillboard(){
	RequestFromBillboardHot rfw=new RequestFromBillboardHot();
	String a="https://www.billboard.com/charts/hot-100/";
	String rs=rfw.getTimeDate(a);
	//rs=rfw.toWantedDay(rs, 1);//1주전
	System.out.println(rs);
	rfw.getAllHtml(a+rs);
	String str="<article class=\"chart-row";
	rfw.getBillboardData(str);
	ArrayList<Billbaord> bills=rfw.getBillbaords();
	request.getAllImages(bills);  //image를 맵에 저장
	obsbills= BillboardConvert.toObservBill(bills);
 }
 public void showAllBillboard(){
	//TitledPane firsttitle = new TitledPane();
	if(obsbills!=null){
		for(BillbaordProperty bp: obsbills){
			TitledPane mytitle = new TitledPane();
			mytitle.setText(bp.getRank()+". "+bp.getArtist());
    		ImageView theImage = new ImageView(request.loadImage(bp.getImagesrc()));
    		theImage.setOnMouseClicked(event -> {
    			if(mytitle.getText()!=null || mytitle.getText().indexOf(".")!=-1 ){
    				System.out.println(mytitle.getText()+"----------------->>>");//ok
        			loadTreeItems(mytitle.getText());
    			}
            });
    		theImage.setFitHeight(50);
    		theImage.setFitWidth(50);
    		theImage.setPreserveRatio (false);
    		mytitle.setContent(theImage);
    		baccordina.getPanes().add(mytitle);
		}
	}
 }
}
