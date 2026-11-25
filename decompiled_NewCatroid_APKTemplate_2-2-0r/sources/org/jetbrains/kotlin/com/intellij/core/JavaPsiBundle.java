package org.jetbrains.kotlin.com.intellij.core;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.DynamicBundle;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaPsiBundle extends DynamicBundle {
    public static final JavaPsiBundle INSTANCE = new JavaPsiBundle();

    /* JADX WARN: Code duplicated, block: B:24:0x0035  */
    /* JADX WARN: Code duplicated, block: B:25:0x0038  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "params";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/JavaPsiBundle";
        } else if (i == 4) {
            objArr[0] = "params";
        } else if (i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/JavaPsiBundle";
        } else if (i == 6) {
            objArr[0] = "modifier";
        } else if (i != 7) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/core/JavaPsiBundle";
        }
        if (i == 2) {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        } else if (i == 5) {
            objArr[1] = "messagePointer";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/JavaPsiBundle";
        } else {
            objArr[1] = "visibilityPresentation";
        }
        switch (i) {
            case 2:
            case 5:
            case 7:
                break;
            case 3:
            case 4:
                objArr[2] = "messagePointer";
                break;
            case 6:
                objArr[2] = "visibilityPresentation";
                break;
            default:
                objArr[2] = CrashHianalyticsData.MESSAGE;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private JavaPsiBundle() {
        super("messages.JavaPsiBundle");
    }

    public static String message(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        String message = INSTANCE.m6457xe250ffe0(str, objArr);
        if (message == null) {
            $$$reportNull$$$0(2);
        }
        return message;
    }

    public static Supplier<String> messagePointer(String str, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (objArr == null) {
            $$$reportNull$$$0(4);
        }
        Supplier<String> lazyMessage = INSTANCE.getLazyMessage(str, objArr);
        if (lazyMessage == null) {
            $$$reportNull$$$0(5);
        }
        return lazyMessage;
    }

    public static String visibilityPresentation(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str.equals(PsiModifier.PACKAGE_LOCAL)) {
            str = "package-private";
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }
}
