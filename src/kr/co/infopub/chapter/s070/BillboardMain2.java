package kr.co.infopub.chapter.s070;
public class BillboardMain2 {
	public static void main(String[] args) {
		//Data�� ��ü�� ����
		Billbaord b1 =new Billbaord(1,"Despacito",1,
		  "http://www.billboard.com/images/pref_images/q61808osztw.jpg","luis fonsi");
		//��ü ���
		showAbout(b1); 
		//��ü�� ����
		Billbaord b=b1;    //�ּ� ����
		showAbout(b);      //��ü ������ ���
	}
	public static void showAbout(Billbaord bb ){//��ü�� �����Ͽ� ���
		String sf=String.format("%s, %s, %s, %s, %s",
			bb.getRank(), bb.getSong(),bb.getLastweek(),bb.getImagesrc(),bb.getArtist());
		System.out.println(sf);
	}
}
