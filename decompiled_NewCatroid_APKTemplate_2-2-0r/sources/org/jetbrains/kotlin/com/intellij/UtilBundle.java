package org.jetbrains.kotlin.com.intellij;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ResourceBundle;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes3.dex */
public final class UtilBundle {
    private static ResourceBundle ourBundle;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "params";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/UtilBundle";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        if (i == 2) {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        } else if (i == 3 || i == 4) {
            objArr[1] = "getUtilBundle";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/UtilBundle";
        }
        if (i != 2 && i != 3 && i != 4) {
            objArr[2] = CrashHianalyticsData.MESSAGE;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String message(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        String strMessageOrDefault = BundleBase.messageOrDefault(getUtilBundle(), str, null, objArr);
        if (strMessageOrDefault == null) {
            $$$reportNull$$$0(2);
        }
        return strMessageOrDefault;
    }

    private static ResourceBundle getUtilBundle() {
        ResourceBundle resourceBundle = ourBundle;
        if (resourceBundle != null) {
            if (resourceBundle == null) {
                $$$reportNull$$$0(3);
            }
            return resourceBundle;
        }
        ResourceBundle bundle = ResourceBundle.getBundle("messages.UtilBundle");
        ourBundle = bundle;
        if (bundle == null) {
            $$$reportNull$$$0(4);
        }
        return bundle;
    }
}
