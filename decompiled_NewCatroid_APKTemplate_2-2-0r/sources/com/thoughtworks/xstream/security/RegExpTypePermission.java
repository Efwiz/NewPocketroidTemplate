package com.thoughtworks.xstream.security;

import java.util.regex.Pattern;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/RegExpTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class RegExpTypePermission implements TypePermission {
    private final Pattern[] patterns;

    public RegExpTypePermission(String[] strArr) {
        this(getPatterns(strArr));
    }

    public RegExpTypePermission(Pattern[] patternArr) {
        this.patterns = patternArr == null ? new Pattern[0] : patternArr;
    }

    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) {
        if (cls != null) {
            String name2 = cls.getName();
            int i = 0;
            while (true) {
                Pattern[] patternArr = this.patterns;
                if (i >= patternArr.length) {
                    break;
                }
                if (patternArr[i].matcher(name2).matches()) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    private static Pattern[] getPatterns(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        Pattern[] patternArr = new Pattern[length];
        for (int i = 0; i < length; i++) {
            patternArr[i] = Pattern.compile(strArr[i]);
        }
        return patternArr;
    }
}
