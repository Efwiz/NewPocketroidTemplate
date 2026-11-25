package org.jetbrains.kotlin.com.intellij.core;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.BundleBase;
import org.jetbrains.kotlin.com.intellij.DynamicBundle;

/* JADX INFO: loaded from: classes3.dex */
public final class CoreBundle {
    private static final DynamicBundle INSTANCE = new DynamicBundle("messages.CoreBundle");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "params";
        } else if (i != 2) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreBundle";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreBundle";
        } else {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        }
        if (i != 2) {
            objArr[2] = CrashHianalyticsData.MESSAGE;
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    private CoreBundle() {
    }

    public static String message(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        String strMessageOrDefault = BundleBase.messageOrDefault(INSTANCE.getResourceBundle(CoreBundle.class.getClassLoader()), str, null, objArr);
        if (strMessageOrDefault == null) {
            $$$reportNull$$$0(2);
        }
        return strMessageOrDefault;
    }
}
