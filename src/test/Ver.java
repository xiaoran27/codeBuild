package test;

import java.sql.Timestamp;


public class Ver {


	public Ver() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		
		sb.append("Ver:").append("\r\n");
		sb.append("  ").append(Version.AUTHOR).append("\r\n");
		sb.append("  ").append(Version.DATE).append("\r\n");
		sb.append("  ").append(Version.REVISION).append("\r\n");
		sb.append("  ").append(Version.TAG).append("\r\n");
		sb.append("").append("\r\n");		
		
		sb.append("JAVA:").append("\r\n");
		sb.append("  JDK: 1.5+").append("\r\n");
		sb.append("").append("\r\n");
		
		sb.append("PROTOCOL:").append("\r\n");
		sb.append("    CRBT: BUILD20061212").append("\r\n");
		sb.append("    CNPM: BUILD20060519").append("\r\n");
		sb.append("").append("\r\n");
		
		sb.append("MSGC:").append("\r\n");
		sb.append("    WINDOWS: 573,440BYTE").append("\r\n");
		sb.append("    LINUX  : 718,771BYTE").append("\r\n");
		sb.append("").append("\r\n");
		
		sb.append("说明: ").append("\r\n");
		sb.append("    各个系统使用的PROTOCOL和MSGC的版本或BUILD或文件大小要求一致,否则,不能保证通讯正常.").append("\r\n");
		sb.append("").append("\r\n");
		
		System.out.println(sb);
			
	}

}


class Version {

	final static String HEADER = "$Header: E:/cvsdata/codeBuild/src/test/Ver.java,v 1.1 2007/01/17 05:23:45 xiaoran27 Exp $";
	final static String DATE = "$Date: 2007/01/17 05:23:45 $";
	final static String AUTHOR = "$Author: xiaoran27 $";
	final static String REVISION = "$Revision: 1.1 $";
	
	final static String TAG = "$Name:  $";
	
	public String toString(){
		return AUTHOR+"\r\n"+DATE+"\r\n"+REVISION+"\r\n"+TAG;
	}

}
