package cn.seocoo.platform.common.publish;

import cn.seocoo.platform.common.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangpan
 * @date 2019/1/2 20:57
 */
public class StringRandomUtils {
    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String RandomStringSm(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String RandomStringSddm(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String RandomStringBig(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }

        return buf.toString();
    }

    public static String RandomNumber(int numinput) {
        String str = "";
        //str += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < numinput; i++) {
            str += (int) (Math.random() * 10);
        }
        return str;
    }

    public String GetEnValue(String envvalue) {
        String ret = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String jsFileName = "lib/env.js";
        FileReader reader;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                ret = (String) invoke.invokeFunction("env", envvalue);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ret;
    }

    private String dump(HttpEntity entity, String encoding) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(entity.getContent(),
                    encoding));
            sb = new StringBuilder();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static class WebClientDevWrapper {
        public static org.apache.http.impl.client.DefaultHttpClient wrapClient(org.apache.http.client.HttpClient base) {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType)
                            throws java.security.cert.CertificateException {
                        // TODO Auto-generated method stub

                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType)
                            throws java.security.cert.CertificateException {
                        // TODO Auto-generated method stub

                    }
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", 443, ssf));
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
                return new DefaultHttpClient(mgr, base.getParams());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static String handleCookies(DefaultHttpClient httpClient) {
        String cookies = "";
        List<Cookie> cookiesget = httpClient.getCookieStore().getCookies();
        if (cookiesget.isEmpty()) {

        } else {
            for (int i = 0; i < cookiesget.size(); i++) {
                Cookie co = cookiesget.get(i);
                String newcooikes = co.getName() + "=" + co.getValue() + "; ";
                cookies += newcooikes;
                //System.out.println(newcooikes);
            }
        }
        System.out.println("cookies:" + cookies);
        return cookies;
    }

    public static String RandomNumber1(int numinput) {
        String str = "";
        //str += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < numinput; i++) {
            str += (int) (Math.random() * 10);
        }
        if (str.equals("0")) {
            str = "1";
        }

        return str;
    }
    // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    public static String GetImageStr(String imgFilePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    public static String read(String filepath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String rests = "";
        try {
            String encoding = "GBK";
            File file = new File(filepath);
            //判断文件是否存在
            if (file.isFile() && file.exists()) {
                //考虑到编码格式
                InputStreamReader read = new InputStreamReader( new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (StringUtils.isNotBlank(lineTxt)) {
                        rests += lineTxt;
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return rests;
    }
}
