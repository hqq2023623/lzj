package zj.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具类 .
 *
 * @author lzj 2014/08/19
 */
public class PasswdUtils {

    /**
     * 123456对应的md5 .
     */
    public static final String DEFAULT_PWD = "e10adc3949ba59abbe56e057f20f883e";

    /**
     * md5加密密码 .
     *
     * @param password 密码明文
     * @return 加密后的密码
     */
    public static String md5(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
        md.update(password.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * 使用用户名和密码双重加密 .
     *
     * @param username 用户名
     * @param password 密码
     * @return 加密后的密码串
     */
    public static String md5(String username, String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("密码加密失败");
        }
        md.update(username.getBytes());
        md.update(password.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

}
