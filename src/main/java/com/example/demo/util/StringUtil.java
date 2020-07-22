package com.example.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hp
 */
public class StringUtil {

    static Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");
    /**
     * 获取表达式中${}中的值
     *  @param content
     *  @return
     *  */
    public static String getContentInfo(String content) {

        Matcher matcher = regex.matcher(content);
        StringBuilder sql = new StringBuilder();
        while(matcher.find()) {
            sql.append(matcher.group(1)+",");
        }
        if (sql.length() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }
        return sql.toString();	}

}
