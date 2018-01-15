package kr.co.infopub.chapter.s088_2;

import java.util.ArrayList;

import java.util.List;

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
	
	public static ArrayList<BillbaordProperty> toBill(ArrayList<Billbaord> blist){
		ArrayList<BillbaordProperty> bplists=new ArrayList<>();
		for(Billbaord b:blist){
			bplists.add(toBill(b));
		}
		return bplists;
	}
	public static ObservableList<BillbaordProperty> toBillboard(ArrayList<BillbaordProperty> alists){
		ObservableList<BillbaordProperty> bList = FXCollections.observableArrayList(alists);
		return bList;
	}
	
	public static ObservableList<BillbaordProperty> toObservBill(ArrayList<Billbaord> alists){
		return toBillboard(toBill(alists));
	}
	
}
