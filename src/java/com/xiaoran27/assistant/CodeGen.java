
/************************ CHANGE REPORT HISTORY *******************************
** Product VERSION,UPDATED BY,UPDATE DATE                                     *
*   DESCRIPTION OF CHANGE                                                     *
*-----------------------------------------------------------------------------*
* Example:
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-3-13
*   create 
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-3-20
* M genCodeByXlsFromOms 解决编译的错误
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-3-24
* M genCodeByXlsFromOms 变量名用小写开始
* + simple implement
* + construct
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-3-25
* M rtn = ProxoolExt.executeUpdateGeneratedKey(sql);
* M manager.close();
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-3-26
* M querySpec(...) null is all; not case sensitive;
* M ProxoolExt -> ProxoolMulti
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-4-3
* + setErrorNum(ERRNUM_EXCEPTION)
*-----------------------------------------------------------------------------*
* V,xiaoran27,2009-9-3
* M convert SQLException error by ErrorDefine
\*************************** END OF CHANGE REPORT HISTORY ********************/


package com.xiaoran27.assistant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class CodeGen {
	

    /**
     * @param args
     */
    public static void main(String[] args) {
    	try {
    		String dir="g:/cvs130/CNMS/cnms_server/doc/";
			genCodeByXlsFromOms(dir+"oms_gui_interface.xls", dir+"gen-src", "com.lj.cnms.jms");
//			genCodeByXlsFromOms("G:/workspace/oms/docs/oms_gui_interface.xls", "G:/workspace/oms/docs/gen-src");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * for interface between GUI AND OMS OF AFLEX-CAP
     * 
     * @param file  excel filename
     * @param outdir  dir of source
     * @param pkgname  package name
     * @throws Exception
     */
    public static void genCodeByXlsFromOms(String file, String outdir, String pkgname)
        throws Exception {
        Map<String, List<String[]>> javaMap = new HashMap<String, List<String[]>>();
        

        //parse xls
        Workbook w = Workbook.getWorkbook(new File(file));
        Sheet sheet = w.getSheet(0);
        int startRow = 11; //11th row

        String genCodeFlag=sheet.getRow(startRow - 1)[12].getContents();  //使用reg表示。*-all，常用{BZTYPE}[YYYYMMDD]
        genCodeFlag=(genCodeFlag==null||genCodeFlag.trim().length()<1)?"*":genCodeFlag;
        
        for (int row = startRow; row < sheet.getRows(); row++) {
            //both actor,operate id,function name,datatype,param,default,notes,datatype,param,default,notes,bztype,gen code
            Cell[] cells = sheet.getRow(row);
            String[] values = new String[11+1+2]; //function name -> java & api

            //skip
            if (!"*".equals(genCodeFlag) && ( cells.length < values.length-1 
            		|| cells.length >= values.length-1 && cells[12].getContents()!=null && !cells[12].getContents().matches(genCodeFlag) ) 
            		){
            	System.out.println(" SKIP - "+cells[1].getContents()+" - "+cells[2].getContents());
            	continue;
            }else if (cells.length < 10){  
            	System.out.println("SKIP - "+cells[1].getContents()+" - "+cells[2].getContents());
            	continue;
            }
            System.out.println("GEN - "+cells[1].getContents()+" - "+cells[2].getContents());
            
            for (int i = 0; i < cells.length; i++) {
                if (2 == i) { //function name
                	values[i] = cells[i].getContents();
                    String[] funcname = values[i].split("[.-]");  //分隔符可能是'.'或'-'
                    values[i] = funcname[0]; //java
                    values[i+1] = funcname[1]; //api
                    
                    //补全函数参数
                    if (funcname[1].indexOf("(") < 0 ){
                    	String param= cells[3].getContents();
                    	String value= cells[3].getContents();
                    	if (param==null||param.trim().length()<1){
                    		values[i+1] = funcname[1]+"()"; //api
                    	}else{
                    		values[i+1] = funcname[1]+"("+param+" "+value+")"; //api
                    	}
                    }
                } else {
                    values[i<2?i:i+1] = (cells[i].getContents()==null||cells[i].getContents().trim().length()<1)?null:cells[i].getContents();
                }
            }

            List<String[]> apiList = javaMap.get(values[2]);

            if (null == apiList) {
                apiList = new ArrayList<String[]>();
            }

            apiList.add(values);
            javaMap.put(values[2], apiList);
        }

        w.close();

        new File(outdir+"/"+pkgname.replaceAll(".","/")+ "/manager/").mkdirs();
        new File(outdir+"/"+pkgname.replaceAll(".","/")+ "/handle/").mkdirs();
        //generate java files
        for (Map.Entry<String, List<String[]>> entry : javaMap.entrySet()) {
            BufferedWriter outManager = new BufferedWriter(new FileWriter(
                        new File(outdir +"/"+pkgname.replaceAll(".","/")+ "/manager/" + entry.getKey() + ".java")));
            outManager.append("\r\n");
            outManager.append(
                "/************************ CHANGE REPORT HISTORY *******************************\r\n");
            outManager.append(
                "** Product VERSION,UPDATED BY,UPDATE DATE                                     *\r\n");
            outManager.append(
                "*   DESCRIPTION OF CHANGE                                                     *\r\n");
            outManager.append(
                "*-----------------------------------------------------------------------------*\r\n");
            outManager.append("* Example:\r\n");
            outManager.append(
                "*-----------------------------------------------------------------------------*\r\n");
            outManager.append("* V,xiaoran27,2009-3-13\r\n");
            outManager.append("*   create\r\n");
            outManager.append(
            "*-----------------------------------------------------------------------------*\r\n");
	        outManager.append("* V,xiaoran27,2009-3-24\r\n");
	        outManager.append("* M  simple implements\r\n");
	        outManager.append(
            "*-----------------------------------------------------------------------------*\r\n");
	        outManager.append("* V,xiaoran27,2009-3-25\r\n");
	        outManager.append("* M rtn = ProxoolMulti.executeUpdateGeneratedKey(sql);\r\n");
	        outManager.append(
            "*-----------------------------------------------------------------------------*\r\n");
	        outManager.append("* V,xiaoran27,2009-3-27\r\n");
	        outManager.append("* M querySpec(...) null is all; not case sensitive;\r\n");
	        outManager.append(
                "\\*************************** END OF CHANGE REPORT HISTORY ********************/\r\n");
            outManager.append("\r\n\r\n");
            outManager.append("package "+pkgname+".manager;\r\n");
            outManager.append("\r\n\r\n");
            outManager.append("import org.apache.log4j.Logger;\r\n");
            outManager.append("import java.sql.SQLException;\r\n");
            outManager.append("import java.util.List;\r\n");
            
            outManager.append("import com.lj.err.ErrorDefine;\r\n");
            outManager.append("import com.lj.dbms.*;\r\n");
            outManager.append("import com.xiaoran27.db.entity.pojo.*;\r\n");
            outManager.append("import com.xiaoran27.db.entity.pojo.ext.*;\r\n");
            outManager.append("\r\n\r\n");
            outManager.append("public class " + entry.getKey() +
                " extends AbstractManager {\r\n");
            outManager.append("\r\n\r\n");
            outManager.append(
                "	private static final Logger logger = Logger.getLogger(" +
                entry.getKey() + ".class);\r\n");
            outManager.append("\r\n\r\n");
            
            //construct
            outManager.append("	public " + entry.getKey() +"() {\r\n");
        	outManager.append("		super();\r\n");
        	outManager.append("		try {\r\n");
        	outManager.append("			if (null==conn || conn.isClosed()){\r\n");
        	outManager.append("				conn = ProxoolMulti.getConnectionByAlias(\"cnmsdb\");\r\n");
//        	outManager.append("				conn = ProxoolMulti.getConnectionByAlias(\""+(entry.getKey().startsWith("Alarm")?"alarmdb":"confdb")+"\");\r\n");
        	outManager.append("			}\r\n");
        	outManager.append("		} catch (SQLException e) {\r\n");
        	outManager.append("			logger.error(\"" + entry.getKey() +"() - e=\"+e,e);\r\n");
        	outManager.append("		}\r\n");
        	outManager.append("	}\r\n\r\n");
            
        	
        	//self define API
            boolean[] defOperate = new boolean[5];  //add,del,modify,queryAll,querySpec from parent
            //both actor,operate id,java,api,datatype,param,default,notes,datatype,param,default,notes
            for (String[] values : entry.getValue()) {
                //api
                if (values[3].startsWith("add(")) {
                	defOperate[0]=true;
                    outManager.append(
                        "	/* (non-Javadoc)                                              \r\n");
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#add(java.lang.Object) \r\n");
                    outManager.append(
                        "	 */                                                           \r\n");
                    outManager.append(
                        "	public int add(Object o) {                                    \r\n\r\n");
                    outManager.append("	reset();\r\n");
                    outManager.append(
                        "		int rtn = -1; //uid                         \r\n");
                    outManager.append("		").append(values[4]).append(" ").append(values[5])
                              .append(" = (").append(values[4])
                              .append(")o;\r\n\r\n");

//                    outManager.append("		//TODO: logic process  \r\n\r\n");
//                    outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//                    outManager.append("		//setError(error)  \r\n\r\n");
					outManager.append("		").append(values[4]).append("Ext ")
						.append(values[5]).append("Ext = new ").append(values[4]).append("Ext(")
						.append(values[5]).append(");\r\n");
					outManager.append("		String sql = ").append(values[5]).append("Ext.toInsertSql();\r\n");
					outManager.append("		\r\n");
					outManager.append("		try {\r\n");
					outManager.append("			//boolean result = ProxoolMulti.executeBatch(sql, true);\r\n");
					outManager.append("			//rtn = result?0:-1;\r\n");
					outManager.append("			rtn = ProxoolMulti.executeUpdateGeneratedKey(sql);\r\n");
					outManager.append("		} catch (SQLException e) {\r\n");
					outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
					outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
					outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
					outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
					outManager.append("			logger.error(\"add(Object) - o=\"+o,e);\r\n");
					outManager.append("		} catch (Exception e) {\r\n");
					outManager.append("			setErrorNum(ERRNUM_EXCEPTION);\r\n");
					outManager.append("			setError(e.getMessage());\r\n");
					outManager.append("			logger.error(\"add(Object) - o=\"+o,e);\r\n");
					outManager.append("		}\r\n");
            		

                    outManager.append(
                        "		return rtn;                                                   \r\n");
                    outManager.append(
                        "	}                                                             \r\n");
                } else if (values[3].startsWith("del(")) {
                	defOperate[1]=true;
                    outManager.append(
                        "	/* (non-Javadoc)                                              \r\n");
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#del(java.lang.Object) \r\n");
                    outManager.append(
                        "	 */                                                           \r\n");
                    outManager.append(
                        "	public int del(Object o) {                                    \r\n\r\n");
                    outManager.append("	reset();\r\n");
                    outManager.append(
                    "		int rtn = -1;                          \r\n");
                    outManager.append("		").append(values[4]).append(" ").append(values[5])
                              .append(" = (").append(values[4])
                              .append(")o;\r\n\r\n");

//                  outManager.append("		//TODO: logic process  \r\n\r\n");
//                  outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//                  outManager.append("		//setError(error)  \r\n\r\n");
					outManager.append("		").append(values[4]).append("Ext ")
						.append(values[5]).append("Ext = new ").append(values[4]).append("Ext(")
						.append(values[5]).append(");\r\n");
					outManager.append("		String sql = ").append(values[5]).append("Ext.toDeleteSql();\r\n");
					outManager.append("		\r\n");
					outManager.append("		try {\r\n");
					outManager.append("			boolean result = ProxoolMulti.executeBatch(sql, true);\r\n");
					outManager.append("			rtn = result?0:-1;\r\n");
					outManager.append("		} catch (SQLException e) {\r\n");
					outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
					outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
					outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
					outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
					outManager.append("			logger.error(\"del(Object) - o=\"+o,e);\r\n");
					outManager.append("		} catch (Exception e) {\r\n");
					outManager.append("			setErrorNum(ERRNUM_EXCEPTION);;\r\n");
					outManager.append("			setError(e.getMessage());\r\n");
					outManager.append("			logger.error(\"del(Object) - o=\"+o,e);\r\n");
					outManager.append("		}\r\n");
                    
                    outManager.append(
                        "		return rtn;                                                   \r\n");
                    outManager.append(
                        "	}                                                             \r\n");
                } else if (values[3].startsWith("modify(")) {
                	defOperate[2]=true;
                    outManager.append(
                        "	/* (non-Javadoc)                                              \r\n");
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#modify(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                             \r\n");
                    outManager.append(
                        "	public int modify(Object o) {                                   \r\n\r\n");
                    outManager.append("	reset();\r\n");
                    outManager.append(
                    "		int rtn = -1;                          \r\n");
                    outManager.append("		").append(values[4]).append(" ").append(values[5])
                              .append(" = (").append(values[4])
                              .append(")o;\r\n\r\n");

//                  outManager.append("		//TODO: logic process  \r\n\r\n");
//                  outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//                  outManager.append("		//setError(error)  \r\n\r\n");
					outManager.append("		").append(values[4]).append("Ext ")
						.append(values[5]).append("Ext = new ").append(values[4]).append("Ext(")
						.append(values[5]).append(");\r\n");
					outManager.append("		String sql = ").append(values[5]).append("Ext.toUpdateSql();\r\n");
					outManager.append("		\r\n");
					outManager.append("		try {\r\n");
					outManager.append("			boolean result = ProxoolMulti.executeBatch(sql, true);\r\n");
					outManager.append("			rtn = result?0:-1;\r\n");
					outManager.append("		} catch (SQLException e) {\r\n");
					outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
					outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
					outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
					outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
					outManager.append("			logger.error(\"modify(Object) - o=\"+o,e);\r\n");
					outManager.append("		} catch (Exception e) {\r\n");
					outManager.append("			setErrorNum(ERRNUM_EXCEPTION);;\r\n");
					outManager.append("			setError(e.getMessage());\r\n");
					outManager.append("			logger.error(\"modify(Object) - o=\"+o,e);\r\n");
					outManager.append("		}\r\n");
                    
                    outManager.append(
                        "		return rtn;                                                     \r\n");
                    outManager.append(
                        "	}                                                               \r\n");
                } else if (values[3].startsWith("queryAll(")) {
                	defOperate[3]=true;
                    outManager.append(
                        "	/* (non-Javadoc)                                                \r\n");
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#queryAll()              \r\n\r\n");
                    outManager.append(
                        "	 */                                                             \r\n");
                    outManager.append(
                        "	public Object[] queryAll() {                                    \r\n");
                    outManager.append("	reset();\r\n");
                    outManager.append("		").append(values[8]).append(" ").append(values[9])
                              .append(" = ").append(values[10])
                              .append(";\r\n\r\n");

                    outManager.append("		").append(values[9])
                    .append(" = (").append(values[8])
                    .append(")querySpec(null);\r\n\r\n");
                    
//                    outManager.append("		//TODO: logic process  \r\n\r\n");
//                    outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//                    outManager.append("		//setError(error)  \r\n\r\n");
                    
                    outManager.append("		return " + values[9] +
                        ";                                                  \r\n");
                    outManager.append(
                        "	}                                                               \r\n");
                } else if (values[3].startsWith("querySpec(")) {
                	defOperate[4]=true;
                    outManager.append(
                        "	/* (non-Javadoc)                                                \r\n");
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#querySpec(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public Object[] querySpec(Object o) {                              \r\n\r\n");
                    outManager.append("	reset();\r\n");
                    outManager.append("		").append(values[8]).append(" ").append(values[9])
                              .append(" = ").append(values[10])
                              .append(";\r\n\r\n");

                    outManager.append("		").append(values[4]).append(" ").append(values[5])
                              .append(" = (").append(values[4])
                              .append(")o;\r\n\r\n");

//                    outManager.append("		//TODO: logic process  \r\n\r\n");
//                    outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//                    outManager.append("		//setError(error)  \r\n\r\n");
                    outManager.append("		String sql = \" select * from ").append(values[5]).append("\";\r\n");
                    outManager.append("		if (null != o){\r\n");
                    outManager.append("			").append(values[4]).append("Ext ")
            			.append(values[5]).append("Ext = new ")
            			.append(values[4]).append("Ext(")
            			.append(values[5]).append(");\r\n");
            		outManager.append("			sql = ").append(values[5]).append("Ext.toQuerySql();\r\n");
            		outManager.append("		}\r\n\r\n");
            		outManager.append("		try {\r\n");
            		outManager.append("			List<Object[]> list = ProxoolMulti.executeQuery(sql);\r\n");
            		outManager.append("			").append(values[9]).append(" = new ").append(values[4]).append("[list.size()];\r\n");
            		outManager.append("			for(int i=0; i<list.size(); i++){\r\n");
            		outManager.append("				").append(values[4]).append(" tmp = ").append(values[4]).append("Ext.get").append(values[4]).append("(list.get(i));\r\n");
            		outManager.append("				").append(values[9]).append("[i] = tmp;\r\n");
            		outManager.append("			}\r\n");
            		outManager.append("		} catch (SQLException e) {\r\n");
            		outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
					outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
					outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
					outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
					outManager.append("			logger.error(\"querySpec(Object) - o=\"+o,e);\r\n");
					outManager.append("		} catch (Exception e) {\r\n");
					outManager.append("			setErrorNum(ERRNUM_EXCEPTION);;\r\n");
					outManager.append("			setError(e.getMessage());\r\n");
					outManager.append("			logger.error(\"querySpec(Object) - o=\"+o,e);\r\n");
					outManager.append("		}\r\n");
                    
                    outManager.append("		return " + values[9] +
                        ";                                                     \r\n");
                    outManager.append(
                        "	}                                                                  \r\n");
                } else {
                    outManager.append(
                        "	/**                                                \r\n");
                    if (null!=values[5]) outManager.append("	 * @param " + values[5] + "\r\n");
                    outManager.append("	 * @return " + (values[9]==null? "rtn": values[9]) + "\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append("	public " + (values[8]==null? "int": values[8]) + " " + values[3] +
                        " {                              \r\n\r\n");
                    outManager.append("	reset();\r\n");
                    
                    if (null!=values[8] && !(values[3].startsWith("ping("))) {
                    	outManager.append("		").append(values[8]).append(" ").append(values[9])
                              .append(" = ").append(values[10])
                              .append(";\r\n\r\n");
                    }else{
                    	outManager.append(
                        "		int rtn = -1;                          \r\n\r\n");
                    }

                    outManager.append("		//TODO: logic process  \r\n");
                    outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
                    outManager.append("		//setError(error)  \r\n\r\n");
                    
                    outManager.append("		return " + (values[9]==null? "rtn": values[9]) +
                        ";                                                     \r\n");
                    outManager.append(
                        "	}                                                                  \r\n");
                }

                outManager.append("\r\n\r\n");

                String api = values[3].split("[(]")[0];
                String javaHandle = entry.getKey() +
                    api.substring(0, 1).toUpperCase() + api.substring(1);
                BufferedWriter outHandle = new BufferedWriter(new FileWriter(
                            new File(outdir +"/"+pkgname.replaceAll(".","/")+ "/handle/" + javaHandle + "Handle.java")));
                outHandle.append("\r\n");
                outHandle.append(
                    "/************************ CHANGE REPORT HISTORY *******************************\r\n");
                outHandle.append(
                    "** Product VERSION,UPDATED BY,UPDATE DATE                                     *\r\n");
                outHandle.append(
                    "*   DESCRIPTION OF CHANGE                                                     *\r\n");
                outHandle.append(
                    "*-----------------------------------------------------------------------------*\r\n");
                outHandle.append("* Example:\r\n");
                outHandle.append(
                    "*-----------------------------------------------------------------------------*\r\n");
                outHandle.append("* V,xiaoran27,2009-3-13\r\n");
                outHandle.append("*   create\r\n");
                outHandle.append(
	                "*-----------------------------------------------------------------------------*\r\n");
	            outHandle.append("* V,xiaoran27,2009-3-25\r\n");
	            outHandle.append("* +  manager.close();\r\n");
                outHandle.append(
                    "\\*************************** END OF CHANGE REPORT HISTORY ********************/\r\n");
                outHandle.append("\r\n\r\n");
                outHandle.append("package "+pkgname+".handle;\r\n");
                outHandle.append("\r\n\r\n");
                outHandle.append("import java.util.Map;\r\n");
                outHandle.append(
                    "import com.lj.comm.handle.AbstractJmsHandle;\r\n");
                outHandle.append(
                    "import com.lj.jms.message.CommMessageData;\r\n");
                outHandle.append("import com.lj.jms.message.*;\r\n");
                outHandle.append("import com.xiaoran27.db.entity.pojo.*;\r\n");
                outHandle.append("import "+pkgname+".manager." +
                    entry.getKey() + ";\r\n");
                outHandle.append("import org.apache.log4j.Logger;\r\n");
                outHandle.append("\r\n\r\n");
                outHandle.append("public class " + javaHandle +
                    "Handle extends AbstractJmsHandle {\r\n");
                outHandle.append("\r\n\r\n");
                outHandle.append(
                    "	private static final Logger logger = Logger.getLogger(" +
                    javaHandle + "Handle.class);\r\n");
                outHandle.append("\r\n\r\n");

                outHandle.append(
                    "	public IMessageData handleReq() throws Exception {                   \r\n");
                outHandle.append(
                    "		IMessageData req = this.getOriMsg();                               \r\n");
                outHandle.append(
                    "		IMessageData rsp = new CommMessageDataRsp();                          \r\n");
                outHandle.append("		" + entry.getKey() + " manager = new " +
                    entry.getKey() + "();                           \r\n");
                outHandle.append(
                    "		                                                                   \r\n");
                outHandle.append(
                    "		if (logger.isInfoEnabled()){                                       \r\n");
                outHandle.append(
                    "			logger.info(\"handleReq() - REQ=\"+req);                           \r\n");
                outHandle.append(
                    "		}                                                                  \r\n");
                outHandle.append(
                    "		                                                                   \r\n");
                outHandle.append(
                    "		rsp.setMessageHeader(req.getMessageHeader().convert());            \r\n\r\n");
                outHandle.append("		//invoke manager."+values[3]+";  \r\n");
                outHandle.append(
                    "		//TODO: modify follow  \r\n");
                
                if (values[8]!=null && !values[3].startsWith("ping(") ){
                	outHandle.append("		").append(values[8]).append(" ").append(values[9])
                	.append(" = ").append(values[10]).append(";\r\n");
                }
            	if (values[4]!=null){
            		outHandle.append("		Object objValue = req.getMessageBody().getObject();\r\n");
            		
            		String[] types = values[4].split("[,]");
                	String[] params = values[5].split("[,]");
                	if (types.length>1){
	                	for (int i=0; i < types.length; i++){
	                		outHandle.append("		").append(types[i]).append(" ").append(params[i])
	                    	.append(" = ").append(types[i].equals("int")?"-1":"null").append(";\r\n");
	                	}
                	}else{
	            		outHandle.append("		").append(values[4]).append(" ").append(values[5])
	                	.append(" = (").append(values[4].equals("int")?"Integer":values[4])
	                	.append(")objValue;\r\n");
                	}
            	}
            	if (values[3].startsWith("query")) {
            		outHandle.append("		").append(values[9]).append(" = (").append(values[8]).append(")manager.")
            			.append(api).append("(").append(values[4]!=null?values[5]:"").append(");\r\n");
            	}else{
            		outHandle.append("		").append(values[9]).append(" = manager.")
        				.append(api).append("(").append(values[4]!=null?values[5]:"").append(");\r\n");
            	}
            	outHandle.append("		MessageBodyRsp messageBodyRsp = (MessageBodyRsp)rsp.getMessageBody();")
            		.append("\r\n");
            	
            	if (values[3].startsWith("add(") 
            		|| values[3].startsWith("del(")
            		|| values[3].startsWith("modify(") 
            		) {
//                	outHandle.append("		if (").append(values[9]).append(" < 0 ) {\r\n");
                	outHandle.append("		  messageBodyRsp.setResult(")
            			.append(values[9]).append("); //convert your error ID\r\n");
                	outHandle.append("		  //messageBodyRsp.setResult(manager.getErrorNum()); //convert your error ID\r\n");
//                	outHandle.append("		  messageBodyRsp.setErrStr(manager.getError()); //convert your error\r\n");
//                	outHandle.append("		}\r\n");
            	}else{
            		outHandle.append("		  messageBodyRsp.setResult(manager.getErrorNum()); //convert your error ID\r\n");
            	}
            	outHandle.append("		  messageBodyRsp.setErrStr(manager.getError()); //convert your error\r\n");
            	
            	outHandle.append("		messageBodyRsp.setObject( ")
            		.append(values[9]).append(" );  \r\n");
            	outHandle.append("		rsp.setMessageBody(messageBodyRsp);")
        			.append("\r\n");
                	
                outHandle.append(
                    "		                                                                   \r\n");
                outHandle.append(
                    "		if (logger.isInfoEnabled()){                                       \r\n");
                outHandle.append(
                    "			logger.info(\"handleReq() - RSP=\"+rsp);                           \r\n");
                outHandle.append(
                    "		}                                                                  \r\n");
                outHandle.append(
                    "		                                                                   \r\n");
                outHandle.append("		manager.close();\r\n");
                outHandle.append(
                    "		return rsp;                                                        \r\n");
                outHandle.append(
                    "	}                                                                      \r\n");
                outHandle.append(
                    "                                                                        \r\n");
                outHandle.append(
                    "	public IMessageData handleRsp() throws Exception {                     \r\n");
                outHandle.append(
                    "		                                                                     \r\n");
                outHandle.append(
                    "		return null;                                                         \r\n");
                outHandle.append(
                    "	}                                                                      \r\n");

                outHandle.append("	/**\r\n");
                outHandle.append("	* @param args\r\n");
                outHandle.append("	*/\r\n");
                outHandle.append("	public static void main(String[] args) {\r\n");
                outHandle.append("	\r\n");
                outHandle.append("	}\r\n");
                outHandle.append("	\r\n");
                outHandle.append("}\r\n");
                outHandle.close();
            }
            
            //inherit API
            String objType = entry.getKey().split("Manage")[0];
            for(int i=0; i<defOperate.length; i++){
            	if (!defOperate[i]){
            		outManager.append("		\r\n\r\n");
            		outManager.append(
                    "	/* (non-Javadoc)                                                \r\n");
            		switch (i){
            		case 0:  //add
                    outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#add(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public int add(Object o) {                              \r\n\r\n");
                    break;
            		case 1:  //del
            			outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#del(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public int del(Object o) {                              \r\n\r\n");
            			break;
            		case 2:  //modify
            			outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#modify(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public int modify(Object o) {                              \r\n\r\n");
            			break;
            		case 3:  //queryAll
            			outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#queryAll()\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public Object[] queryAll() {                              \r\n\r\n");
            			break;
            		case 4:  //querySpec
            			outManager.append(
                        "	 * @see "+pkgname+".manager.IManager#querySpec(java.lang.Object)\r\n");
                    outManager.append(
                        "	 */                                                                \r\n");
                    outManager.append(
                        "	public Object[] querySpec(Object o) {                              \r\n\r\n");
            			break;
            		}
            		
            		outManager.append("	reset();\r\n");
            		if (objType.startsWith("Base") || objType.startsWith("Simple")){
            			if (i<3){
			            	  outManager.append("		return -1; \r\n");
			              }else{
			            	  outManager.append("		return null; \r\n");
			              }
            		}else{
	            		if (i<3){
	            			outManager.append(
	                        "		int rtn = -1;                          \r\n");
	            			outManager.append("		").append(objType).append(" ").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
	                        .append(" = (").append(objType)
	                        .append(")o;\r\n\r\n");
	            		}else{
	            			if (4==i){
	            				outManager.append("		").append(objType).append(" ").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
		                        .append(" = (").append(objType)
		                        .append(")o;\r\n\r\n");
	            			}
	            			outManager.append("		").append(objType).append("[] ").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
	                        .append("s = null;\r\n\r\n");
	            		}
	
//			              outManager.append("		//TODO: logic process  \r\n\r\n");
//		                  outManager.append("		//ProxoolMulti.api(...)  \r\n\r\n");
//		                  outManager.append("		//setError(error)  \r\n\r\n");
		                  
		                  if (i<3){
							outManager.append("		").append(objType).append("Ext ")
								.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
								.append("Ext = new ").append(objType).append("Ext(")
								.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
								.append(");\r\n");
							outManager.append("		String sql = ")
							.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
							.append("Ext.to").append(i==0?"Insert":(i==1)?"Delete":"Update").append("Sql();\r\n");
							outManager.append("		\r\n");
							outManager.append("		try {\r\n");
							outManager.append("			boolean result = ProxoolMulti.executeBatch(sql, true);\r\n");
							outManager.append("			rtn = result?0:-1;\r\n");
							outManager.append("		} catch (SQLException e) {\r\n");
							outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
							outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
							outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
							outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
							outManager.append("			logger.error(\"").append(i==0?"add":(i==1)?"del":"modify").append("(Object) - o=\"+o,e);\r\n");
							outManager.append("		} catch (Exception e) {\r\n");
							outManager.append("			setErrorNum(ERRNUM_EXCEPTION);;\r\n");
							outManager.append("			setError(e.getMessage());\r\n");
							outManager.append("			logger.error(\"").append(i==0?"add":(i==1)?"del":"modify").append("(Object) - o=\"+o,e);\r\n");
							outManager.append("		}\r\n");
		                  }else if (i==4){
								outManager.append("		String sql = \" select * from ").append(objType).append("\";\r\n");
			                    outManager.append("		if (null != o){\r\n");
			                    outManager.append("			").append(objType).append("Ext ")
		            			.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1)).append("Ext = new ")
		            			.append(objType).append("Ext(")
		            			.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1)).append(");\r\n");
			            		outManager.append("			sql = ")
			            		.append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
			            		.append("Ext.toQuerySql();\r\n");
			            		outManager.append("		}\r\n\r\n");
			            		
		            		outManager.append("		try {\r\n");
		            		outManager.append("			List<Object[]> list = ProxoolMulti.executeQuery(sql);\r\n");
		            		outManager.append("			").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1)).append("s = new ").append(objType).append("[list.size()];\r\n");
		            		outManager.append("			for(int i=0; i<list.size(); i++){\r\n");
		            		outManager.append("				").append(objType).append(" tmp = ").append(objType).append("Ext.get").append(objType).append("(list.get(i));\r\n");
		            		outManager.append("				").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1)).append("s[i] = tmp;\r\n");
		            		outManager.append("			}\r\n");
		            		outManager.append("		} catch (SQLException e) {\r\n");
		            		outManager.append("			//setErrorNum(ERRNUM_EXCEPTION);\r\n");
							outManager.append("			//setError(e.getErrorCode()+\" - \"+e.getSQLState());\r\n");
							outManager.append("			setErrorNum(ErrorDefine.getSqlErrorNum(e));\r\n");
							outManager.append("			setError(ErrorDefine.getSqlError(e));\r\n");
							outManager.append("			logger.error(\"querySpec(Object) - o=\"+o,e);\r\n");
		            		outManager.append("		} catch (Exception e) {\r\n");
							outManager.append("			setErrorNum(ERRNUM_EXCEPTION);;\r\n");
							outManager.append("			setError(e.getMessage());\r\n");
							outManager.append("			logger.error(\"querySpec(Object) - o=\"+o,e);\r\n");
		            		outManager.append("		}\r\n");
		                  }else{
			            	  outManager.append("		").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
		                        .append("s = (").append(objType)
		                        .append("[])querySpec(null);\r\n\r\n");
			              }
		                    
		                  if (i<3){
			            	  outManager.append("		return rtn; \r\n");
			              }else{
			            	  outManager.append("		return ").append(objType.substring(0,1).toLowerCase()).append(objType.substring(1))
		                        .append("s; \r\n");
			              }
            		}
		              
		              outManager.append(
		                  "	}                                                                  \r\n");
		      			
            	}
            }

            outManager.append("	/**\r\n");
            outManager.append("	* @param args\r\n");
            outManager.append("	*/\r\n");
            outManager.append("	public static void main(String[] args) {\r\n");
            outManager.append("	\r\n");
            outManager.append("	}\r\n");
            outManager.append("	\r\n");
            outManager.append("}\r\n");
            outManager.close();
        }
    }

}
