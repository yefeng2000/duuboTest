package com.example.demo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName:  NoRepeatSubmit
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 寻找手艺人
 * @email:  maker2win@163.com
 * @date:   2019年5月17日 上午9:08:27
 *
 * @Copyright: 2019 www.maker-win.net Inc. All rights reserved.
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 设置请求锁定时间
     *
     * @return
     */
    int lockTime() default 10;

}
