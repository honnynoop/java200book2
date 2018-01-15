package kr.co.infopub.chapter.s078;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BillboardTimeMain {
 public static void main(String[] args) {
	String newUrls="http://www.billboard.com/charts/hot-100/";
	String s="";
	URL url=null;
	try {
		url= new URL(newUrls);    // �ּ� ã��
		// �ּ����� ���� �ȱ�
		BufferedReader reader = new BufferedReader(  
				     new InputStreamReader(url.openStream(), "euc-kr"), 8);
		String line = "";
		while ((line = reader.readLine()) != null){ // �� �پ� �о� ���δ�.
			if(!line.trim().equals("")){            // ���ڿ��� �ִٸ�
				// <time datetime="2016-08-13">August 13, 2016</time>
                if(line.trim().contains("<time datetime=")){  // ã��
				    s=line.trim();
					s=s.substring(0,s.indexOf(">")-1);  // <time datetime="2016-08-13
					s=s.substring(s.indexOf("\"")+1).trim();// 2016-08-13
				    break;
                }
			}
		}
	} catch (Exception e) {
		System.out.println("Billboard Parsing error !!! ");
	} 
    System.out.println("�̹� ������ ��Ʈ ��¥ : "+s);
 }
}