package cn.seocoo.platform.common.config;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import cn.seocoo.platform.common.utils.StringUtils;
import cn.seocoo.platform.common.utils.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局配置类
 * 
 * @author administrator
 */
public class Global
{
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String NAME = "application.yml";

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global()
    {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance()
    {
        if (global == null)
        {
            synchronized (Global.class)
            {
                if (global == null)
                    global = new Global();
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key)
    {
        String value = map.get(key);
        if (value == null)
        {
            Map<?, ?> yamlMap = null;
            try
            {
                yamlMap = YamlUtil.loadYaml(NAME);
                value = String.valueOf(YamlUtil.getProperty(yamlMap, key));
                map.put(key, value != null ? value : StringUtils.EMPTY);
            }
            catch (FileNotFoundException e)
            {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName()
    {
        return StringUtils.nvl(getConfig("seocoo.name"), "admin");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion()
    {
        return StringUtils.nvl(getConfig("seocoo.version"), "3.1.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear()
    {
        return StringUtils.nvl(getConfig("seocoo.copyrightYear"), "2018");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled()
    {
        return Boolean.valueOf(getConfig("seocoo.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile()
    {
        return getConfig("seocoo.profile");
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getConfig("seocoo.profile") + "avatar/";
    }

    /**
     * 获取下载上传路径
     */
    public static String getDownloadPath()
    {
        return getConfig("seocoo.profile") + "download/";
    }

    /**
     * 获取作者
     */
    public static String getAuthor()
    {
        return StringUtils.nvl(getConfig("gen.author"), "administrator");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName()
    {
        return StringUtils.nvl(getConfig("gen.packageName"), "cn.seocoo.platform.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre()
    {
        return StringUtils.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix()
    {
        return StringUtils.nvl(getConfig("gen.tablePrefix"), "sys_");
    }

    /**
     * 获取图片上传大小
     */
    public static Long getImageUploadSize()
    {
        return Long.valueOf(getConfig("file.image.max.size"));
    }
    /**
     * 获取视频上传大小
     */
    public static Long getVideoUploadSize()
    {
        return Long.valueOf(getConfig("file.video.max.size"));
    }
}
