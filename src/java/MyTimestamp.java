import java.sql.Timestamp;
import java.util.Calendar;


public final class MyTimestamp {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
      if (args.length  >0 && args[0].matches("[-]{1,2}(([Hh][Ee][Ll][Pp])|([Hh]))")) {
        System.out.println("Usage:  java MyTimestamp [datetime] " );
        System.out.println("datetime is String or long.Example: '2010-01-01 12:12:12.12' or 195809961");
//        System.out.println("-Doutfmt={format} default: yyyy-MM-dd HH:mm:ss.fff w");
        System.exit(0);
      }
      
      String outfmt="yyyy-MM-dd HH:mm:ss.fff";
      String param=""+System.currentTimeMillis();
      if (args.length  >0){
    	  param = args[0];
      }
      Timestamp ts = new Timestamp(System.currentTimeMillis());
      if (param.matches("^[0-9]+$")){
    	  ts = new Timestamp(Long.parseLong(param));
      }else{
    	  try {
			ts = Timestamp.valueOf(param);
		} catch (Exception e) {
			System.err.println("format: yyyy-MM-dd HH:mm:ss.fff");
			System.exit(1);
		}
      }
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(ts.getTime());
      System.out.println(ts+" "+cal.get(Calendar.DAY_OF_WEEK));
      
	}

}
