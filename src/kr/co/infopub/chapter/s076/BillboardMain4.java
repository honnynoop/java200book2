package kr.co.infopub.chapter.s076;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BillboardMain4 {
 public static void main(String[] args) {
	String newUrls="http://www.billboard.com/charts/hot-100/";
	InputStream inputStream=null;
	URL url=null;
	try {
		url= new URL(newUrls);    // 주소 찾기
		//주소 연결
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  
		// 빨대 꽂기
		inputStream = new BufferedInputStream(urlConnection.getInputStream());  
		// 읽어 들이기 
		BufferedReader reader = new BufferedReader(  
				              new InputStreamReader(inputStream, "euc-kr"), 8);
		String line = null;
		while ((line = reader.readLine()) != null){  //한줄씩 읽어서 
			if(!line.trim().equals("")){             // 공백이 아니면 출력한다.
				System.out.println(line.trim());
			}
		}
		inputStream.close(); // 닫기
	} catch (Exception e) {
		System.out.println("Billboard Parsing error !!! ");
	} 
 }
}
