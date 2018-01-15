package kr.co.infopub.chapter.s088_3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RequestFromBillboard200old {
	
	ArrayList<String> htmls=new ArrayList<String>();
	ArrayList<Billbaord> billbaords=new ArrayList<Billbaord>();
	public RequestFromBillboard200old() {
		htmls.clear();
		billbaords.clear();
	}
	public ArrayList<Billbaord> getBillbaords() {
		return billbaords;
	}
	boolean isConnection=false;
	
	public void getAllHtml(String newUrls){
		htmls.clear();
		InputStream inputStream;
		URL url=null;
		try {
			url= new URL(newUrls);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"), 8);
	
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				if(!line.trim().equals("")){

					htmls.add(line.trim());
				}
			}
			inputStream.close();
			isConnection=true;

		} catch (Exception e) {
			isConnection = false;
			System.out.println(e);
		} 
	}
	public String getTimeDate(String newUrls){
		String s="";
		InputStream inputStream;
		URL url=null;
		try {
			url= new URL(newUrls);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"), 8);
			
	
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				if(!line.trim().equals("")){
					
                    if(line.trim().contains("<time datetime=")){
                    	//<time datetime="2016-08-13">August 13, 2016</time>
					    s=line.trim();
						s=s.substring(0,s.indexOf(">")-1);//<time datetime="2016-08-13
						s=s.substring(s.indexOf("\"")+1).trim();//2016-08-13
					    break;
                    }
                    
					
				}
			}
			inputStream.close();
			isConnection=true;

		} catch (Exception e) {
			isConnection = false;
			System.out.println(e);
		} 
		return s;
	}
	
	public synchronized void getBillboardData(String msg){
		billbaords.clear();
		int count=0;
		for (int i=0; i<htmls.size(); i++) {
			String ss=htmls.get(i);
			if(ss.contains(msg)){  
				count++ ;
//				if(count>90 && count<105){
				//System.out.println(count+":");
				String rank=ss.substring(ss.indexOf("chart-row--")+"chart-row--".length());
				rank=rank.substring(0,rank.indexOf("js")-1).trim();
				//System.out.println(rank);
				String song=ss.substring(ss.indexOf("Song Hover-")+"Song Hover-".length());
				song=song.substring(0,song.indexOf("\"")).trim();
				//System.out.println(replace(song));
				
//				for(int k=0; k<25; k++){
//					System.out.println(htmls.get(i+k));
//				}
				
				int m=1;
				String lastweek=htmls.get(i+m);
				
				while(m<10 ){
					if(lastweek.contains("chart-row__last-week")){
						break;
					}else{
						m++;
						lastweek=htmls.get(i+m);
					}
				}
				if(lastweek.contains("chart-row__last-week")){
					lastweek=lastweek.substring(lastweek.indexOf(":")+1);
					lastweek=lastweek.substring(0,lastweek.indexOf("<")).trim();
				}else{
					lastweek="--";
				}
				//System.out.println(__toStr(lastweek));
				
				
				int j=1;m=5;
				String imageurl=htmls.get(i+j+m);
				while(j<10){
					if(imageurl.contains("images/album_images")){
						break;
					}else{
						j++;
						imageurl=htmls.get(i+j+m);
					}
				}
				if(imageurl.contains("images/album_images")){
					if(imageurl.contains("http://")){
						imageurl=imageurl.substring(imageurl.indexOf("http://"));
						imageurl=imageurl.substring(0,imageurl.indexOf(".jpg")+".jpg".length());
					}else{
						imageurl="http://www.billboard.com/images/album_images/q__________.jpg";
					}
				}else{
					imageurl="http://www.billboard.com/images/album_images/q__________.jpg";
				}

				//System.out.println(imageurl);
				
				int k=1;m=5;
				String artisturl=htmls.get(i+m+k);
				while(k<20){
					if(artisturl.contains("chart-row__artist")){
						break;
					}else{
						k++;
						artisturl=htmls.get(i+m+k);
					}
				}
				if(artisturl.contains("chart-row__artist")){
					if(artisturl.contains("http://")){
						artisturl=artisturl.substring(artisturl.indexOf("http://"));
						artisturl=artisturl.substring(0,artisturl.indexOf("\""));
					}else{
						artisturl=htmls.get(i+m+k+1);
					}
					
				}else{
				
					artisturl=htmls.get(i+m+k+1);
				}
				
				//System.out.println(artisturl);
				String artist=artisturl;
				if(artisturl.contains("/")){
					artist=artisturl.substring(artisturl.lastIndexOf("/")+1);
					artist=toArtis(artist);
				}

				//System.out.println(artist);

				
				
	
				
				Billbaord board=new Billbaord(
						toInt(rank), replace(song), 
						toInt(__toStr(lastweek)), 
						imageurl, artisturl, artist);
				billbaords.add(board);
				//System.out.println("------------------");
			}
//			}else if(count>105){ break;}
		}
	}
	public String replace(String msg){
		String ss=msg;
		ss=ss.replaceAll("&#039;", "'");
		ss=ss.replaceAll("&amp;", "&");
		ss=ss.replaceAll("&quot;", "\"");
		
		return ss.trim();
	}
	private String __toStr(String lastweek){
		return lastweek.contains("--")?201+"":lastweek;
	}
	private int toInt(String msg){
		return Integer.parseInt(msg==null ?"-1":msg.trim());
	}
	public String toArtis(String msg){
		return msg.replaceAll("-", " ");
	}
	public void printHtml(){
		for (String dto : htmls) {
			System.out.println(dto);
		}
	}
	public void printBillboard(){
		for (Billbaord dto : billbaords) {
			System.out.println(dto);
		}
	}
	
	public Calendar todate(String ss){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date dd=new Date();
		try {
			dd=sdf.parse(ss);
		} catch (ParseException e) {
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(dd);
		return cal;
	}
	
	public String todate(String ss,int totals, int round){
		Calendar cal=todate(ss);
		//Calendar calTemp = (Calendar) cal.clone();
		cal.add(Calendar.DAY_OF_YEAR, -(totals-round)*7);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	
	//정해진 날짜 기준 1-> 일주일 전 2-> 이주전
		public String toWantedDay(String ss,int round){
			Calendar cal=todate(ss);
			//-7일을 더하면 일주전
			cal.add(Calendar.DAY_OF_YEAR, -(round*7));
			//칼렌더를 문자열로 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(cal.getTime());
		}
	
	public static void main(String[] args) {
		RequestFromBillboard200old rfw=new RequestFromBillboard200old();
		String a="http://www.billboard.com/charts/billboard-200/";

		//String timedate="    <time datetime="2016-08-13">August 13, 2016</time>";
		//String atimedate="<time datetime=";
		String rs=rfw.getTimeDate(a);
		//rs=rfw.toWantedDay(rs, 0);//3주전
		System.out.println(rs);
		rfw.getAllHtml(a+rs);
		//rfw.getAllHtml(a+"2016-08-06");
		//rfw.printHtml();
		String str="<article class=\"chart-row";

		rfw.getBillboardData(str);
		rfw.printBillboard();
	}
}
