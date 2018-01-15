package kr.co.infopub.chapter.s078;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BillboardTimeMain2 {

	public static void main(String[] args) {
		String newUrls="http://www.billboard.com/charts/hot-100/";
		String s="";
		InputStream inputStream;
		URL url=null;
		try {
			url= new URL(newUrls);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"), 8);
			
			String line = "";
			while ((line = reader.readLine()) != null){ // 한 줄씩 읽어 들인다.
				if(!line.trim().equals("")){            // 문자열이 있다면
					// <time datetime="2016-08-13">August 13, 2016</time>
                    if(line.trim().contains("<time datetime=")){  // 찾기
					    s=line.trim();
						s=s.substring(0,s.indexOf(">")-1);      // <time datetime="2016-08-13
						s=s.substring(s.indexOf("\"")+1).trim();// 2016-08-13
					    break;
                    }
				}
			}
			inputStream.close();
		} catch (Exception e) {
			System.out.println("Billboard Parsing error !!! ");
		} 
		
	    System.out.println("이번 빌보드 차트 날짜 : "+s);
	}
}
