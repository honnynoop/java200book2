package kr.co.infopub.chapter.s086;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class BillboardImageTest3 extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
    ImageFXListRequest  request=new ImageFXListRequest();
    RequestFromBillboardHot rfw=new RequestFromBillboardHot();
    
    @Override
    public void start(Stage primaryStage) {

		String a="https://www.billboard.com/charts/hot-100/";
		String rs=rfw.getTimeDate(a);
		//rs=rfw.toWantedDay(rs);//10주전
		System.out.println(rs+"------------------Billboard Chart !!!");
		rfw.getAllHtml(a);

		String str="<article class=\"chart-row";

		rfw.getBillboardData(str);
		
		ArrayList<Billbaord> bills=rfw.getBillbaords();
		//계속 읽는 것을 방지
		request.getAllImages(bills);  //image를 맵에 저장
		ImageView nimageView = new ImageView();
		nimageView.setFitWidth(250);
		nimageView.setFitHeight(250);
		VBox root = new VBox(10);
        //인터넷
        int col=10; 
        int row=bills.size()/col;
        for(int i=0; i<row; i++){
        	HBox hbox = new HBox(10);            // 한줄에 10개를 묶는다.
            hbox.setPadding(new Insets(5));
            hbox.setAlignment(Pos.CENTER);
        	for (int j = 0; j < col; j++) {      
        		Billbaord b=bills.get(i*col+j);  
            	ImageView imageView = new ImageView(request.loadImage(b.getImagesrc()));
            	imageView.setFitWidth(50);
            	imageView.setFitHeight(50);
            	imageView.setOnMouseClicked(ee->{
            		nimageView.setImage(request.loadImage(b.getImagesrc()));
            	});
            	hbox.getChildren().add(imageView);
			}
        	root.getChildren().add(hbox);        // 아래로 HBox를 붙인다. 
        }
        
        HBox hbox2 = new HBox(10);            // 한줄에 10개를 묶는다.
        hbox2.setPadding(new Insets(5));
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(nimageView);
        root.getChildren().add(hbox2);
        
        primaryStage.setScene(new Scene(root, 60*col, 70*col+260));
        primaryStage.setTitle("Billboard Image");
        primaryStage.show();
    }
    
}

   