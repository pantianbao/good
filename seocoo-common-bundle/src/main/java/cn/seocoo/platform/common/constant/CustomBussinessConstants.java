package cn.seocoo.platform.common.constant;

/**
 * @author PanChengHao
 * @date 2018/12/27 11:38
 */
public class CustomBussinessConstants {
  /**
   * 新增商户、代理商账号时，给初始密码
   */
  public static final String DEFAULT_PASSWORD = "123456";

  /**
   * 生成merchantCode的前缀
   */
  public static final String MERCHANT_PREFIX = "merchant_";

  /**
   * 生成proxyCode的前缀
   */
  public static final String PROXY_PREFIX = "proxy_";

  /**
   * 商户角色id
   */
  public static final Long MERCHANT_ROLE_ID = 4L;

  /**
   * 区县代理角色id
   */
  public static final Long COUNTY_PROXY_ROLE_ID = 6L;

  /**
   * 市代理角色id
   */
  public static final Long CITY_PROXY_ROLE_ID = 5L;

  /**
   * 省代理角色id
   */
  public static final Long PROVINCE_PROXY_ROLE_ID = 3L;

  /**
   * 省代理
   */
  public static final String PROVINCE_PROXY_LEVEL = "1";

  /**
   * 市代理
   */
  public static final String CITY_PROXY_LEVEL = "2";

  /**
   * 区县代理
   */
  public static final String COUNTY_PROXY_LEVEL = "3";

  /**
   * 平台角色id
   */
  public static final Long PLATFORM_ROLE_ID = 7L;

  /**
   * 后台新增商户，代理的时候，默认给测试部门id
   */
  public static final Long DEFAULT_DEPT_ID = 104L;

  /**
   * 默认正常状态
   */
  public static final String DEFAULT_NORMAL_STATUS = "1";

  /**
   * 默认禁用状态
   */
  public static final String DEFAULT_FORBID_STATUS = "0";

  /**
   * 未审核状态
   */
  public static final String AUDIT_NOT = "0";

  /**
   * 已审核通过状态
   */
  public static final String AUDIT_PASS = "1";

  /**
   * 审核不通过状态
   */
  public static final String AUDIT_NOPASS = "2";
}
