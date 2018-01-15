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
			url= new URL(newUrls);    // �ּ� ã��
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  //�ּ� ����
			inputStream = new BufferedInputStream(urlConnection.getInputStream());  // ���� �ȱ�
			// �о� ���̱�
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"), 8);
			String line = "";
			while ((line = reader.readLine()) != null)  //���پ� �о 
			{
				if(!line.trim().equals("")){  // ������ �ƴϸ� ����Ѵ�.
					//System.out.println(line.trim());
					htmls.add(line.trim());     // ArrayList�� ����
				}
			}
			inputStream.close(); // �ݱ�
		} catch (Exception e) {
			System.out.println("Billboard Parsing error !!! ");
		} 
		// ArrayList�� ����� ���ڿ��� ���
		for (String str : htmls) {
			System.out.println(str);
		}

	}
}
