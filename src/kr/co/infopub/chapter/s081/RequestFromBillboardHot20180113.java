package kr.co.infopub.chapter.s081;

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
////20170824까지
public class RequestFromBillboardHot20180113 {
	
	boolean isConnection=false;
	ArrayList<String> htmls=new ArrayList<String>();
	ArrayList<Billbaord> billbaords=new ArrayList<Billbaord>();

	public ArrayList<Billbaord> getBillbaords() {
		return billbaords;
	}


	String timedate="";

	public String getTimedate() {
		return timedate;
	}

	public RequestFromBillboardHot20180113(){
		billbaords.clear();
	}
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
		int count=1;
		for (int i=0; i<htmls.size(); i++) {
			String ss=htmls.get(i);
			if(ss.contains(msg)){
				//System.out.println(count+++":"+ss.trim());

				String rank=ss.substring(ss.indexOf("chart-row--")+"chart-row--".length());
				rank=rank.substring(0,rank.indexOf("js")-1).trim();
				//System.out.println(rank);
				//System.out.println(ss);
				//String song=ss.substring(ss.indexOf("Song Hover-")+"Song Hover-".length());
				//song=song.substring(0,song.indexOf("\"")).trim();
				//System.out.println(replace(song));
				//chart-row__current-week
				int j=1;
				String imageurl=htmls.get(i+j);

				while(true){
					if(imageurl.contains("chart-row__image")){
						//System.out.println("---->:"+imageurl);
						//System.out.println("====>:"+htmls.get(i+j+1));
						//imageurl=htmls.get(i+j+1);
						if(imageurl.contains("https://") && imageurl.contains(".jpg")){
						//if(imageurl.contains("images/pref_images")){
							break;
						}else{
							imageurl="data-imagesrc=\"https://www.billboard.com/"
									+ "images/pref_images/q__________.jpg\"";
							break;
						}
					}else{
						j++;
						imageurl=htmls.get(i+j);
					}
					//System.out.println(j+":"+imageurl);
				}
				//System.out.println("??------"+imageurl);
				imageurl=imageurl.substring(imageurl.indexOf("https://"));
				imageurl=imageurl.substring(0,imageurl.indexOf(".jpg")+".jpg".length());
				//System.out.println(imageurl);
				//2017 08 25 서비스 수정
				//chart-row__song
				
				int kt=1;
				String song=htmls.get(i+j+kt);
				while(true){
					//System.out.println(j+" "+artisturl);
					if(song.contains("chart-row__song")){
						song=htmls.get(i+j+kt);
						//System.out.println(j+"==> "+htmls.get(i+j+k+1));
						//System.out.println(j+"--> "+htmls.get(i+j+k+2));
						break;
					}else{
						j++;
						song=htmls.get(i+j+kt);
					}
				}
				//<h2 class="chart-row__song">Despacito</h2>
				song=song.substring(song.indexOf(">")+1);
				song=song.substring(0, song.indexOf("<"));
				System.out.println(song);
				
				
				int k=1;
				String artisturl=htmls.get(i+j+k);
				while(true){
					//System.out.println(j+" "+artisturl);
					if(artisturl.contains("chart-row__artist")){
						artisturl=htmls.get(i+j+k+1);
						//System.out.println(j+"==> "+htmls.get(i+j+k+1));
						//System.out.println(j+"--> "+htmls.get(i+j+k+2));
						break;
					}else{
						j++;
						artisturl=htmls.get(i+j+k);
					}
				}
				if(artisturl.contains("Featuring")){
					artisturl=artisturl.substring(0,artisturl.indexOf("Featuring"));
				}
				//artisturl=artisturl.substring(artisturl.indexOf("http://"));
				//artisturl=artisturl.substring(0,artisturl.indexOf("\""));
				//System.out.println(replace(artisturl.trim()));
				String artist=artisturl.substring(artisturl.lastIndexOf("/")+1);
				artist=toArtis(artist);
				int m=1;
				String lastweek=htmls.get(i+j+k+m);
				while(true){
					if(lastweek.contains("chart-row__last-week")){
						break;
					}else{
						j++;
						lastweek=htmls.get(i+j+k+m);
					}
				}
				int n=1;
				lastweek=htmls.get(i+j+k+m+n);
				while(true){
					if(lastweek.contains("chart-row__value")){
						break;
					}else{
						j++;
						lastweek=htmls.get(i+j+k+m+n);
					}
				}
				lastweek=lastweek.substring(lastweek.indexOf(">")+1);
				lastweek=lastweek.substring(0,lastweek.indexOf("<")).trim();
				//System.out.println(__toStr(lastweek));

				Billbaord board=new Billbaord(
						toInt(rank), replace(song),
						toInt(__toStr(lastweek)),
						imageurl, replace(artisturl), replace(artist));
				billbaords.add(board);

				//System.out.println("------------------");

			}

		}
	}
	//정해진 날짜 기준 1-> 일주일 전 2-> 이주전
	public String toWantedDay(String ss,int round){
		Calendar cal=todate(ss);
		//-7일을 더하면 일주전
		cal.add(Calendar.DAY_OF_YEAR, -(round * 7));
		//칼렌더를 문자열로
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	public String replace(String msg){
		String ss=msg;
		ss=ss.replaceAll("&#039;", "'");
		ss=ss.replaceAll("&amp;", "&");
		ss=ss.replaceAll("&quot;", "\"");

		return ss.trim();
	}
	private String __toStr(String lastweek){
		return lastweek.contains("--")?101+"":lastweek;
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
		cal.add(Calendar.DAY_OF_YEAR, -(totals-round)*7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static void main(String[] args) {
		RequestFromBillboardHot20180113 rfw=new RequestFromBillboardHot20180113();
//
		String a="https://www.billboard.com/charts/hot-100/";
		//String a="http://www.billboard.com/charts/billboard-200//";
		//String timedate="    <time datetime="2016-08-13">August 13, 2016</time>";
		//String atimedate="<time datetime=";
		String rs=rfw.getTimeDate(a);
		//rs=rfw.toWantedDay(rs, 10);//10주전
		System.out.println(rs);
		rfw.getAllHtml(a+rs);
		//rfw.getAllHtml(a+"2016-08-06");
		//rfw.printHtml();
		String str="<article class=\"chart-row";

		rfw.getBillboardData(str);
		rfw.printBillboard();
	}
}
