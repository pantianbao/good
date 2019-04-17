package cn.seocoo.platform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author xieheng
 * @Data 2018/5/24
 * @Email xieheng91@163.com
 * @Desc  string工具类
 */
public class StringEx
{
    private static final Logger logger = LoggerFactory.getLogger(StringEx.class);

    public static final String newRid(String serial)
    {
        return newUUID() + serial;
    }

    public static final String newUUID()
    {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static final String safetyChar(String c)
    {
        try
        {
            c = new String(c.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("safetyChar", e);
            return "";
        }

        return c.replace("\"", "").replace("'", "");
    }

    public static final boolean stringIsNullOrEmpty(String s)
    {
        return (s == null) || (s.isEmpty());
    }

    public static final <T> String stringJoin(List<T> array, String separator)
    {
        if (array == null) {
            return null;
        }
        int arraySize = array.size();

        int bufSize = arraySize == 0 ? 0 :
                ((array.get(0) == null ? 16 : array
                        .get(0)
                        .toString().length()) + 1) * arraySize;

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array.get(i) != null) {
                buf.append(array.get(i));
            }
        }
        return buf.toString();
    }

    public static final String stringJoin(String[] array, String separator)
    {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize;
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static List<String> stringToList(String s)
    {
        if ((s == null) || (s.isEmpty())) {
            return new LinkedList();
        }
        List list = new ArrayList();
        String[] str = s.split(";");
        for (String string : str) {
            list.add(string);
        }
        return list;
    }

    public static String removeStartChar(String str, String c)
    {
        if ((str.length() > 1) && (str.startsWith(c))) {
            return str.substring(1);
        }
        return str;
    }

    public static String removeEndChar(String str, String c)
    {
        if ((str.length() > 1) || (str.endsWith(c))) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

    /**
     * 随机的4位数
     * @return
     */
    public static String random4(){
        Random random = new Random();
        StringBuilder result= new StringBuilder(4);
        for(int i=0;i<4;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
    /**
     * 随机的N位数
     * @return
     */
    public static String randomByNum(int num){
        Random random = new Random();
        StringBuilder result= new StringBuilder(num);
        for(int i=0;i<num;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

   /**
   * 随机的N位数字和字母
   * @return
   */
  public static String getRandomString(int length) { //length表示生成字符串的长度
    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    int number = 0;
    for (int i = 0; i < length; i++) {
      number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

    /**
     * 获取指定位数的随机数
     *
     * @param position    最低18位以上
     * @return
     */
    public static String getRandomStringByDate(int position){
        // 获取Long时间
        Date date = new Date();
        String time = date.getTime()+"";
        // 除去生成时间位数 剩下 要生成的 位数
        int pos =  position - time.length()-1;
        long pow = (long) Math.pow(10, pos);
        // 生成 指定位数的随机数
        String random = time + (long) (Math.random() * pow + pow);
        return random;
    }

}