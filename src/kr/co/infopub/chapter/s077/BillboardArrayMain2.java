package kr.co.infopub.chapter.s077;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BillboardArrayMain2 {

	public static void main(String[] args) {
		
		ArrayList<String> htmls=new ArrayList<String>();
		String newUrls="http://www.billboard.com/charts/hot-100/";
		InputStream inputStream=null;
		URL url=null;
		try {
			url= new URL(newUrls);    // 주소 찾기
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  //주소 연결
			inputStream = new BufferedInputStream(urlConnection.getInputStream());  // 빨대 꽂기
			// 읽어 들이기
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"), 8);
			String line = "";
			while ((line = reader.readLine()) != null)  //한줄씩 읽어서 
			{
				if(!line.trim().equals("")){  // 공백이 아니면 출력한다.
					//System.out.println(line.trim());
					htmls.add(line.trim());     // ArrayList에 저장
				}
			}
			inputStream.close(); // 닫기
		} catch (Exception e) {
			System.out.println("Billboard Parsing error !!! ");
		} 
		// ArrayList에 저장된 문자열을 출력
		for (String str : htmls) {
			System.out.println(str);
		}

	}
}
