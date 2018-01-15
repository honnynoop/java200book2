package kr.co.infopub.chapter.s086;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class BillboardImageTest2 extends Application {
 // �̹����� ���ٿ� 10�� ������	
 public static void main(String[] args) {
    launch(args);
 }
 ImageFXListRequest  request=new ImageFXListRequest();
 RequestFromBillboardHot rfw=new RequestFromBillboardHot();
 @Override
 public void start(Stage primaryStage) {
	String a="https://www.billboard.com/charts/hot-100/";
	String rs=rfw.getTimeDate(a);
	//rs=rfw.toWantedDay(rs);//10����
	System.out.println(rs+"------------------Billboard Chart !!!");
	rfw.getAllHtml(a);
	String str="<article class=\"chart-row";
	rfw.getBillboardData(str);
	ArrayList<Billbaord> bills=rfw.getBillbaords();
	//��� �д� ���� ����
	request.getAllImages(bills);  //image�� �ʿ� ����
	VBox root = new VBox(10);     // HBox 10�� ��������
    //���ͳ�
    int col=10; 
    int row=bills.size()/col;
    for(int i=0; i<row; i++){
    	HBox hbox = new HBox(10);          // ���ٿ� 10���� ���´�.
        hbox.setPadding(new Insets(5));
        hbox.setAlignment(Pos.CENTER);
    	for (int j = 0; j < col; j++) {      
    		Billbaord b=bills.get(i*col+j);  
        	ImageView imageView = 
        	  new ImageView(request.loadImage(b.getImagesrc()));
        	imageView.setFitWidth(50);
        	imageView.setFitHeight(50);
        	imageView.setOnMouseClicked(ee->{
        		System.out.println(b.getImagesrc());
        	});
        	hbox.getChildren().add(imageView);
		}
    	root.getChildren().add(hbox);        // �Ʒ��� HBox�� ���δ�. 
    }
    primaryStage.setScene(new Scene(root, 60*col, 70*row));
    primaryStage.setTitle("Billboard Image");
    primaryStage.show();
 }
}