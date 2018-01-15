package kr.co.infopub.chapter.s080;
public class BillboardMain5 {

	public static void main(String[] args) {
		RequestFromBillboardHot rfw=new RequestFromBillboardHot();
		String a="https://www.billboard.com/charts/hot-100/";
		//String a="http://www.billboard.com/charts/billboard-200";
		//String timedate="    <time datetime="2016-08-13">August 13, 2016</time>";
		//String atimedate="<time datetime=";
		String rs=rfw.getTimeDate(a);
		rs=RestDay.toWantedDay(rs, 1);//1ÁÖÀü
		System.out.println(rs);
		rfw.getAllHtml(a+rs);
		String str="<article class=\"chart-row";
		rfw.getBillboardData(str);
		rfw.printBillboard();
	}
}
