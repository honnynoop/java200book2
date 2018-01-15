package kr.co.infopub.chapter.s074;
// 파일 리더
import java.io.BufferedReader;
import java.io.FileReader;
public class BillFileReader2 {
	public static void main(String[] args) {
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader("billboard\\bill.txt"));
			String msg="";
			while((msg=br.readLine())!=null){
			    System.out.println(msg);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}