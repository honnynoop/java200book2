package kr.co.infopub.chapter.s088;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class BillboardConvert {
 public static BillbaordProperty toBill(Billbaord b){
	BillbaordProperty bp=new BillbaordProperty();
	bp.setRank(b.getRank());
	bp.setSong(b.getSong());
	bp.setArtist(b.getArtist());
	bp.setImagesrc(b.getImagesrc());
	bp.setLastweek(b.getLastweek());
	return bp;
 }
 public static ArrayList<BillbaordProperty> 
                             toBill(ArrayList<Billbaord> blist){
	ArrayList<BillbaordProperty> bplists=new ArrayList<>();
	for(Billbaord b:blist){
		bplists.add(toBill(b));
	}
	return bplists;
 } 
 public static ObservableList<BillbaordProperty> 
                toBillboard(ArrayList<BillbaordProperty> alists){
	ObservableList<BillbaordProperty> bList =
			FXCollections.observableArrayList(alists);
	return bList;
 }
 public static ObservableList<BillbaordProperty>
                      toObservBill(ArrayList<Billbaord> alists){
	return toBillboard(toBill(alists));
 }
 public static void main(String[] args) {
	RequestFromBillboardHot rfw=new RequestFromBillboardHot();
	ImageFXListRequest  request=new ImageFXListRequest();
	String a="https://www.billboard.com/charts/hot-100/";
	String rs=rfw.getTimeDate(a);
	rs=rfw.toWantedDay(rs, 1); //1주전
	System.out.println(rs+"--------Billboard Chart !!!");
	rfw.getAllHtml(a+rs);
	String str="<article class=\"chart-row";
	rfw.getBillboardData(str);
	ArrayList<Billbaord> bills=rfw.getBillbaords();
	
	request.getAllImages(bills);  //image를 맵에 저장
	ObservableList<BillbaordProperty> obsbills=
			                    BillboardConvert.toObservBill(bills);
	for(BillbaordProperty bp: obsbills){
		System.out.println(bp);
	}
 }
}
