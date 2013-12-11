package com.xiaoran27.performance;

public class StringFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testFilterX(args);
	    
	}
	
	private static void testFilterX(String[] args){
		{//filter1
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter1(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter1: "+str);
		    System.out.println(nEnd-nBegin);
		}
	    
		{//filter2
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter2(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter2: "+str);
		    System.out.println(nEnd-nBegin);
		}
		{//filter3
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter3(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter3: "+str);
		    System.out.println(nEnd-nBegin);
		}
	    
		{//filter4
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter4(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter4: "+str);
		    System.out.println(nEnd-nBegin);
		}
		{//filter5
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter5(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter5: "+str);
		    System.out.println(nEnd-nBegin);
		}
		{//filter6
			String oldStr = "D186783E36B721651E8AF96AB1C4000B";
			String str = "";
		    long nBegin = System.currentTimeMillis();
		    for(int i=0; i<1024*1024; i++)
		    {
		      str = filter6(oldStr);  //�˴�����ĳ������Ĺ��˺���
		    }
		    long nEnd = System.currentTimeMillis();
		    System.out.println("filter6: "+str);
		    System.out.println(nEnd-nBegin);
		}
	}
	

	  private static String filter1(String strOld)
	  {
	    String strNew = new String();
	    for(int i=0; i<strOld.length(); i++)
	    {
	      if('0'<=strOld.charAt(i) && strOld.charAt(i)<='9')
	      {
	        strNew += strOld.charAt(i);
	      }
	    }
	    return strNew;
	  }


	 /*
	�����Ĵ��벻�Һ�filter1��ͬ�������Java���׿ɾ����൱����ˣ����ַ���ƴ����Ҫ��StringBuffer���Ż���û�����ס�
	Ϊ�˺ͺ����Աȣ��ȼ���filter1�Ĵ���ʱ�䣬��Լ��8.81-8.90��֮�䡣
	
	��汾2
	��������filter2���������£�
	*/


	  private static String filter2(String strOld)
	  {
	    StringBuffer strNew = new StringBuffer();
	    for(int i=0; i<strOld.length(); i++)
	    {
	      if('0'<=strOld.charAt(i) && strOld.charAt(i)<='9')
	      {
	        strNew.append(strOld.charAt(i));
	      }
	    }
	    return strNew.toString();
	  }
	  
	  /*
������ʵ�ղ�������filter1��ʱ���Ѿ�й¶��filter2�������filter2ͨ��ʹ��StringBuffer���Ż������ַ��������ܡ�ΪʲôStringBuffer�����ַ��������ܱ�String�ã�����Ѿ���������̸���ҾͲ�ϸ˵�ˡ��в������ͬѧ�Լ���Googleһ���֪���ҹ���Ӧ����ͦ��ͬѧ��д������filter2�Ĵ��롣
�������⣬JDK 1.5��������StringBuilder�����ܻ��StringBuffer���ã��������ǵ��п���Ҫ�õ������汾��JDK�����ԱȲ��ԣ����� StringBuilder��StringBuffer֮��Ĳ��첻�Ǳ������۵��ص㣬���Ժ�������Ӷ�ʹ��StringBuffer��ʵ�֡�
����filter2�Ĵ���ʱ���ԼΪ2.14-2.18�룬�����˴�Լ4����

������汾3
�������ſ���filter3���������£�
	   */


	  private static String filter3(String strOld)
	  {
	    StringBuffer strNew = new StringBuffer();
	    int nLen = strOld.length();
	    for(int i=0; i<nLen; i++)
	    {
	      char ch = strOld.charAt(i);
	      if('0'<=ch && ch<='9')
	      {
	        strNew.append(ch);
	      }
	    }
	    return strNew.toString();
	  }
/*
	����էһ��filter3��filter2����������ϸ��һ�ƣ�ԭ���Ȱ�strOld.charAt(i)��ֵ��char��������ʡ���ظ����� charAt()�����Ŀ����������strOld.length()�ȱ���ΪnLen��Ҳ��ʡ���ظ�����length()�Ŀ��������뵽��һ����ͬѧ�������ǱȽ�ϸ�ĵġ�
	����������һ�Ż�������ʱ���ʡΪ1.48-1.52��������Լ30%������charAt()��length()���ڲ�ʵ�ֶ�ͦ�򵥵ģ��������������ܲ�̫���ԡ�

	������汾4
	����Ȼ�󿴿�filter4���������£�
*/

	  private static String filter4(String strOld)
	  {
	    int nLen = strOld.length();
	    StringBuffer strNew = new StringBuffer(nLen);
	    for(int i=0; i<nLen; i++)
	    {
	      char ch = strOld.charAt(i);
	      if('0'<=ch && ch<='9')
	      {
	        strNew.append(ch);
	      }
	    }
	    return strNew.toString();
	  }
/*
	����filter4��filter3���Ҳ��С��Ψһ�������ڵ�����StringBuffer�������Ĺ��캯����ͨ��StringBuffer�Ĺ��캯�����ó�ʼ��������С��������Ч����append()׷���ַ�ʱ���·����ڴ棬�Ӷ�������ܡ�
	����filter4�Ĵ���ʱ���Լ��1.33-1.39�롣Լ���10%����ϧ�����ķ����е�С :-(

	������汾5
	��������������ռ��汾��������õ�filter5��
*/

	  private static String filter5(String strOld)
	  {
	    int nLen = strOld.length();
	    char[] chArray = new char[nLen];
	    int nPos = 0;
	    for(int i=0; i<nLen; i++)
	    {
	      char ch = strOld.charAt(i);
	      if('0'<=ch && ch<='9')
	      {
	        chArray[nPos] = ch;
	        nPos++;
	      }
	    }
	    return new String(chArray, 0, nPos);
	  }
/*
	������һ��������ܻ��룺filter5��ǰ�����汾�Ĳ��Ҳ߯���˰ɣ�filter5��û����StringҲû����StringBuffer���������ַ���������м䴦��
	����filter5�Ĵ���ʱ�䣬ֻ����0.72-0.78�룬�����filter4�����˽���50%��Ϊɶ���ǲ�����Ϊֱ�Ӳ����ַ����飬��ʡ��append(char)�ĵ��ã�ͨ���鿴append(char)��Դ���룬�ڲ���ʵ�ֺܼ򵥣�Ӧ�ò�����������ô�ࡣ
	��������ʲôԭ����
	������Ȼfilter5��һ���ַ�����Ĵ������������������filter4��˵��StringBuffer�Ĺ��캯���ڲ�Ҳ�����ַ�����Ĵ����������������������filter5��filter4�����ʡ��StringBuffer����ʡ�Ĵ������������Խ�Լ�����ܡ�
	  */
	  
	  
	  //my think
	  private static String filter6(String strOld)
	  {
	    char[] chars = strOld.toCharArray();
	    int nLen=chars.length,nPos = 0;
	    char[] chArray = new char[nLen];
	    for(int i=0; i<nLen; i++)
	    {
	      char ch = chars[i];
	      if('0'<=ch && ch<='9')
	      {
	        chArray[nPos] = ch;
	        nPos++;
	      }
	    }
	    return new String(chArray, 0, nPos);
	  }
	  
}
