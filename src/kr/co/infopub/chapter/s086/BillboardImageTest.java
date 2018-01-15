package kr.co.infopub.chapter.s086;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
// image read test 
public class BillboardImageTest extends Application {
    // 이미지 하나 보이기
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        String imageSource=  "https://charts-static.billboard.com/img/2017/01/post-malone.jpg";       
        //String imageSource = "http://www.billboard.com/images/pref_images/q63694v2qz7.jpg";
        //인터넷
        ImageView imageView = new ImageView(new Image(imageSource));
        //디렉토리 내 파일
        //ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("billboard.jpg")));
        Group myGroup = new Group();
        myGroup.getChildren().add(imageView);
        root.getChildren().add(myGroup);
   
        primaryStage.setScene(new Scene(root, 150, 150));
        primaryStage.setTitle("Billboard Image");
        primaryStage.show();
    }
    
}

   