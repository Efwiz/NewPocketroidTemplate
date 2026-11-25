package org.jetbrains.kotlin.js.translate.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: loaded from: classes5.dex */
public final class ErrorReportingUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 9 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 9 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "e";
                break;
            case 3:
                objArr[0] = FirebaseAnalytics.Param.LOCATION;
                break;
            case 4:
                objArr[0] = "expression";
                break;
            case 5:
                objArr[0] = "messageText";
                break;
            case 6:
            case 9:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/utils/ErrorReportingUtils";
                break;
            case 7:
                objArr[0] = "descriptor";
                break;
            case 8:
                objArr[0] = "explainingMessage";
                break;
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "selector";
                break;
        }
        if (i == 6 || i == 9 || i == 11) {
            objArr[1] = CrashHianalyticsData.MESSAGE;
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/ErrorReportingUtils";
        }
        switch (i) {
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
                objArr[2] = CrashHianalyticsData.MESSAGE;
                break;
            case 6:
            case 9:
            case 11:
                break;
            default:
                objArr[2] = "reportErrorWithLocation";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 9 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ErrorReportingUtils() {
    }

    public static RuntimeException reportErrorWithLocation(KtExpression ktExpression, RuntimeException runtimeException) {
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (runtimeException == null) {
            $$$reportNull$$$0(1);
        }
        return reportErrorWithLocation(runtimeException, PsiDiagnosticUtils.atLocation(ktExpression));
    }

    private static RuntimeException reportErrorWithLocation(RuntimeException runtimeException, String str) {
        if (runtimeException == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        throw new RuntimeException(runtimeException.getMessage() + " at " + str, runtimeException);
    }

    public static String message(PsiElement psiElement, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        String str2 = str + " at " + PsiDiagnosticUtils.atLocation(psiElement) + ".";
        if (str2 == null) {
            $$$reportNull$$$0(6);
        }
        return str2;
    }

    public static String message(DeclarationDescriptor declarationDescriptor, String str) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        String str2 = str + " at " + DiagnosticUtils.atLocation(declarationDescriptor) + ".";
        if (str2 == null) {
            $$$reportNull$$$0(9);
        }
        return str2;
    }

    public static String message(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        String str = "Error at " + PsiDiagnosticUtils.atLocation(psiElement) + ".";
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return str;
    }
}
