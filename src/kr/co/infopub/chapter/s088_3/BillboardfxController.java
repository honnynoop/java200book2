package kr.co.infopub.chapter.s088_3;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class BillboardfxController {

	@FXML
	private TableView<BillbaordProperty> billobardTable;
	 
    @FXML
    private TableColumn<BillbaordProperty, String> song;
    
    @FXML
    private TableColumn<BillbaordProperty, String> artist;

    @FXML
    private TableColumn<BillbaordProperty, Integer> lastweek;

    @FXML
    private TableColumn<BillbaordProperty, Integer> rank;

    @FXML
    private TableColumn<BillbaordProperty, String> imagesrc;
    
    @FXML
    private WebView billwebview;
    
    @FXML
    private Button btnAf;

    @FXML
    private Button btnBf;
    
    @FXML
    private Label lbbill;
    
    ImageFXListRequest  request=new ImageFXListRequest();
    
    int count=0;
    @FXML
    void onClickBtnBf(ActionEvent event) {
    	count++;
    	getAllBillboard();
    }

    @FXML
    void onClickBtnAf(ActionEvent event) {
    	count=0;
    	getAllBillboard();
    }
    
    
    
 
    
    @FXML
    void initialize() {
    	rank.setCellValueFactory(cellData -> cellData.getValue().rankProperty().asObject());
    	artist.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
    	song.setCellValueFactory(cellData -> cellData.getValue().songProperty());
    	imagesrc.setCellValueFactory(cellData -> cellData.getValue().imagesrcProperty());
    	lastweek.setCellValueFactory(cellData -> cellData.getValue().lastweekProperty().asObject());
        rank.setStyle("-fx-alignment: CENTER");     
        lastweek.setStyle("-fx-alignment: CENTER");  
    	imagesrc.setCellFactory(new Callback<TableColumn<BillbaordProperty, String>,TableCell<BillbaordProperty, String>>(){        
    		@Override
    		public TableCell<BillbaordProperty, String> call(TableColumn<BillbaordProperty, String> param) {                
    			TableCell<BillbaordProperty, String> cell = new TableCell<BillbaordProperty, String>(){
    				final ImageView imageview = new ImageView();
    				@Override
    				public void updateItem(String item, boolean empty) {                        
    					if(item!=null){                            
    						//System.out.println(item+"------------------------------------------------------------");  
    						imageview.setFitHeight(100);
    						imageview.setFitWidth(100);
    						//imageview.setImage(new Image(item)); 
    						imageview.setImage(request.loadImage(item));
    						setGraphic(imageview);
    					}
    				}
    			};
    			//System.out.println(cell.getIndex());               
    			return cell;
    		}
    	});
    	final WebEngine webEngine = billwebview.getEngine();
    	billobardTable.setOnMouseClicked(e ->{
        	if(billobardTable.getSelectionModel().getSelectedItem()!=null ){
        		BillbaordProperty billboard = (BillbaordProperty)billobardTable.getSelectionModel().getSelectedItem();
        		webEngine.load("https://www.youtube.com/results?search_query="+toP(billboard.getSong()));
        	}
          }
        );
        //빌보드 차트 읽기
    	getAllBillboard();
    }
    public String toP(String msg){
        String ss=msg;
        ss=ss.replaceAll(" ", "+");
        return ss.trim();
    }
    public void getAllBillboard(){
    	//RequestFromBillboardHot rfw=new RequestFromBillboardHot();
		//String a="http://www.billboard.com/charts/hot-100/";
    	RequestFromBillboard200 rfw=new RequestFromBillboard200();
		String a="https://www.billboard.com/charts/billboard-200/";
		String rs=rfw.getTimeDate(a);
		rs=rfw.toWantedDay(rs, count);//10주전
		rfw.getAllHtml(a+rs);

		String str="<article class=\"chart-row";

		rfw.getBillboardData(str);
		
		ArrayList<Billbaord> bills=rfw.getBillbaords();
		//계속 읽는 것을 방지
		request.getAllImages(bills);  //image를 맵에 저장
		ObservableList<BillbaordProperty> obsbills= BillboardConvert.toObservBill(bills);
		populateEmployees(obsbills);
		lbbill.setText("Billboard Chart HOT 200 "+rs);
    }
    
    
    public void populateEmployees (ObservableList<BillbaordProperty> bills)  {
    	billobardTable.setItems(bills);
    }
}
