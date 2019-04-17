package cn.seocoo.platform.common.publish;

import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

/**
 * @author wangpan
 * @date 2019/1/2 20:38
 */
public class HttpClientUtuils {

    public static DefaultHttpClient handleNewHttpClient(int connectTimeout, int soTimeout){
        DefaultHttpClient httpClient = null ;
        DefaultHttpClient defaultClient;
        HttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(httpParams, 80);
        ConnManagerParams.setTimeout(httpParams, 25000);
        //每个路由的最大链接个数,标志对同一站点的并发请求
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(100);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);

        SchemeRegistry registry = new SchemeRegistry();
//		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParams,registry);
        // httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS,false);
        defaultClient = new DefaultHttpClient(connectionManager, httpParams);
        defaultClient.getParams().setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 20*1024);
        HttpClientParams.setCookiePolicy(defaultClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        //defaultClient.getParams().setCookiePolicy();
        //httpClient = defaultClient ;
        httpClient = WebClientDevWrapper.wrapClient(defaultClient);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,connectTimeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,soTimeout);

        return httpClient ;
    }

    public static class WebClientDevWrapper {
        public static DefaultHttpClient wrapClient(org.apache.http.client.HttpClient base) {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

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
                ctx.init(null, new TrustManager[] { tm }, null);
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
}
