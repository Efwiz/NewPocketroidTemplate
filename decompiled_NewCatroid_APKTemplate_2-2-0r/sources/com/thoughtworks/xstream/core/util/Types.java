package com.thoughtworks.xstream.core.util;

import java.util.regex.Pattern;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Types.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Types {
    private static final Pattern lambdaPattern = Pattern.compile(".*\\$\\$Lambda\\$[0-9]+/.*");

    public static final boolean isLambdaType(Class<?> cls) {
        return cls != null && cls.isSynthetic() && lambdaPattern.matcher(cls.getSimpleName()).matches();
    }
}
