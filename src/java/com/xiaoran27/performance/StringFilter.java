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
		      str = filter1(oldStr);  //此处调用某个具体的过滤函数
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
		      str = filter2(oldStr);  //此处调用某个具体的过滤函数
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
		      str = filter3(oldStr);  //此处调用某个具体的过滤函数
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
		      str = filter4(oldStr);  //此处调用某个具体的过滤函数
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
		      str = filter5(oldStr);  //此处调用某个具体的过滤函数
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
		      str = filter6(oldStr);  //此处调用某个具体的过滤函数
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
	如果你的代码不幸和filter1雷同，那你的Java功底可就是相当糟糕了，连字符串拼接需要用StringBuffer来优化都没搞明白。
	为了和后续对比，先记下filter1的处理时间，大约在8.81-8.90秒之间。
	
	◇版本2
	再来看看filter2，代码如下：
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
　　其实刚才在评价filter1的时候，已经泄露了filter2的天机。filter2通过使用StringBuffer来优化连接字符串的性能。为什么StringBuffer连接字符串的性能比String好，这个已经是老生常谈，我就不细说了。尚不清楚的同学自己上Google一查便知。我估计应该有挺多同学会写出类似filter2的代码。
　　另外，JDK 1.5新增加了StringBuilder，性能会比StringBuffer更好，不过考虑到有可能要拿到其它版本的JDK上作对比测试，而且 StringBuilder和StringBuffer之间的差异不是本文讨论的重点，所以后面的例子都使用StringBuffer来实现。
　　filter2的处理时间大约为2.14-2.18秒，提升了大约4倍。

　　◇版本3
　　接着看看filter3，代码如下：
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
	　　乍一看filter3和filter2差不多嘛！你再仔细瞧一瞧，原来先把strOld.charAt(i)赋值给char变量，节省了重复调用 charAt()方法的开销；另外把strOld.length()先保存为nLen，也节省了重复调用length()的开销。能想到这一步的同学，估计是比较细心的。
	　　经过此一优化，处理时间节省为1.48-1.52，提升了约30%。由于charAt()和length()的内部实现都挺简单的，所以提升的性能不太明显。

	　　◇版本4
	　　然后看看filter4，代码如下：
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
	　　filter4和filter3差别也很小，唯一差别就在于调用了StringBuffer带参数的构造函数。通过StringBuffer的构造函数设置初始的容量大小，可以有效避免append()追加字符时重新分配内存，从而提高性能。
	　　filter4的处理时间大约在1.33-1.39秒。约提高10%，可惜提升的幅度有点小 :-(

	　　◇版本5
	　　最后来看看终极版本，性能最好的filter5。
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
	　　猛一看，你可能会想：filter5和前几个版本的差别也忒大了吧！filter5既没有用String也没有用StringBuffer，而是拿字符数组进行中间处理。
	　　filter5的处理时间，只用了0.72-0.78秒，相对于filter4提升了将近50%。为啥捏？是不是因为直接操作字符数组，节省了append(char)的调用？通过查看append(char)的源代码，内部的实现很简单，应该不至于提升这么多。
	　　那是什么原因捏？
	　　虽然filter5有一个字符数组的创建开销，但是相对于filter4来说，StringBuffer的构造函数内部也会有字符数组的创建开销。两相抵消。所以filter5比filter4还多节省了StringBuffer对象本省的创建开销。所以节约了性能。
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
