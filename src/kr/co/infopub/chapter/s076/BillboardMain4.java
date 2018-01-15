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
		url= new URL(newUrls);    // �ּ� ã��
		//�ּ� ����
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  
		// ���� �ȱ�
		inputStream = new BufferedInputStream(urlConnection.getInputStream());  
		// �о� ���̱� 
		BufferedReader reader = new BufferedReader(  
				              new InputStreamReader(inputStream, "euc-kr"), 8);
		String line = null;
		while ((line = reader.readLine()) != null){  //���پ� �о 
			if(!line.trim().equals("")){             // ������ �ƴϸ� ����Ѵ�.
				System.out.println(line.trim());
			}
		}
		inputStream.close(); // �ݱ�
	} catch (Exception e) {
		System.out.println("Billboard Parsing error !!! ");
	} 
 }
}
