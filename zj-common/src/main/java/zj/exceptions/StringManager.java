package zj.exceptions;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * StringManger,每个包一个实例对象(包级别的单例)
 *
 * @author Lzj Created on 2015/12/18.
 */
public class StringManager {

    //可以获取当前类所在的包的资源文件
    private ResourceBundle bundle;

    private StringManager(String packageName) {
        String bundleName = packageName + ".LocalStrings";
        bundle = ResourceBundle.getBundle(bundleName);
    }

    //根据key获取指定的信息
    public String getString(String key) {
        if (key == null) {
            String msg = "key is null";
            throw new NullPointerException(msg);
        }
        String str = null;
        try {
            str = bundle.getString(key);
        } catch (MissingResourceException mre) {
            str = "Cannot find message associated with key '" + key + "'";
        }
        return str;
    }

    /**
     * Get a string from the underlying resource bundle and format
     * it with the given set of arguments.
     *
     * @param key
     * @param args
     */

    public String getString(String key, Object[] args) {
        String iString = null;
        String value = getString(key);

        // this check for the runtime exception is some pre 1.1.6
        // VM's don't do an automatic toString() on the passed in
        // objects and barf out

        try {
            // ensure the arguments are not null so pre 1.2 VM's don't barf
            Object nonNullArgs[] = args;
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    if (nonNullArgs == args) nonNullArgs = (Object[]) args.clone();
                    nonNullArgs[i] = "null";
                }
            }

            iString = MessageFormat.format(value, nonNullArgs);
        } catch (IllegalArgumentException iae) {
            StringBuffer buf = new StringBuffer();
            buf.append(value);
            for (int i = 0; i < args.length; i++) {
                buf.append(" arg[" + i + "]=" + args[i]);
            }
            iString = buf.toString();
        }
        return iString;
    }

    private static Hashtable managers = new Hashtable();

    //保证StringManger是包单例的
    public static StringManager getManager(String packageName) {
        synchronized (managers) {
            StringManager manager = (StringManager) managers.get(packageName);
            if (manager == null) {
                manager = new StringManager(packageName);
                managers.put(packageName, manager);
            }
            return manager;
        }
    }


}
