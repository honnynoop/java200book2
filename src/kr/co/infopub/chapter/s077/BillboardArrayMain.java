package kr.co.infopub.chapter.s077;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class BillboardArrayMain {
 public static void main(String[] args) {
	ArrayList<String> htmls=new ArrayList<String>();
	String newUrls="http://www.billboard.com/charts/hot-100/";
	URL url=null;
	try {
		url= new URL(newUrls);    // �ּ� ã��
		// �ּ����� ���� �ȱ�
		BufferedReader reader = new BufferedReader(  
				     new InputStreamReader(url.openStream(), "euc-kr"), 8);
		String line = null;
		while ((line = reader.readLine()) != null){  // ���پ� �о 
			if(!line.trim().equals("")){             // ������ �ƴϸ� ����Ѵ�.
				htmls.add(line.trim());
			}
		}
	} catch (Exception e) {
		System.out.println("Billboard Parsing error !!! ");
	} 
	// ArrayList�� ����� ���ڿ��� ���
	for (String str : htmls) {
		System.out.println(str);
	}
 }
}
