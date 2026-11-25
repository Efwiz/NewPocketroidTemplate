package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import java.util.Comparator;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfo;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class OSAgnosticPathUtil {
    public static final Comparator<String> COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.OSAgnosticPathUtil$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return OSAgnosticPathUtil.lambda$static$0((String) obj, (String) obj2);
        }
    };

    static /* synthetic */ int lambda$static$0(String str, String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        int length = str.length();
        int length2 = str2.length();
        boolean z = !SystemInfo.isFileSystemCaseSensitive;
        for (int i = 0; i < length && i < length2; i++) {
            char cCharAt = str.charAt(i);
            char cCharAt2 = str2.charAt(i);
            if (cCharAt != cCharAt2) {
                if (cCharAt == '/') {
                    if (cCharAt2 != '\\') {
                        return -1;
                    }
                } else if (cCharAt == '\\') {
                    if (cCharAt2 != '/') {
                        return -1;
                    }
                } else {
                    if (cCharAt2 == '/' || cCharAt2 == '\\') {
                        return 1;
                    }
                    int iCompare = StringUtil.compare(cCharAt, cCharAt2, z);
                    if (iCompare != 0) {
                        return iCompare;
                    }
                }
            }
        }
        return Integer.compare(length, length2);
    }
}
