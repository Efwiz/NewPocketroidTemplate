package org.jetbrains.kotlin.psi.codeFragmentUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: codeFragmentUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\n\u001a\u00020\u0006*\u00020\u0010\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"(\u0010\n\u001a\u00020\u0006*\u00020\u000b2\u0006\u0010\t\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"DEBUG_TYPE_REFERENCE_STRING", "", "getDEBUG_TYPE_REFERENCE_STRING", "()Ljava/lang/String;", "SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "", "getSUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE", "()Lcom/intellij/openapi/util/Key;", SchemaSymbols.ATTVAL_SKIP, "suppressDiagnosticsInDebugMode", "Lorg/jetbrains/kotlin/psi/KtFile;", "getSuppressDiagnosticsInDebugMode", "(Lorg/jetbrains/kotlin/psi/KtFile;)Z", "setSuppressDiagnosticsInDebugMode", "(Lorg/jetbrains/kotlin/psi/KtFile;Z)V", "Lorg/jetbrains/kotlin/psi/KtElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CodeFragmentUtilKt {
    private static final String DEBUG_TYPE_REFERENCE_STRING;
    private static final Key<Boolean> SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE;

    static {
        Key<Boolean> keyCreate = Key.create("SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE = keyCreate;
        DEBUG_TYPE_REFERENCE_STRING = "DebugTypeKotlinRulezzzz";
    }

    public static final Key<Boolean> getSUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE() {
        return SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE;
    }

    public static final boolean suppressDiagnosticsInDebugMode(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtFile) {
            return getSuppressDiagnosticsInDebugMode((KtFile) ktElement);
        }
        PsiFile containingFile = ktElement.getContainingFile();
        return (containingFile instanceof KtFile) && getSuppressDiagnosticsInDebugMode((KtFile) containingFile);
    }

    public static final boolean getSuppressDiagnosticsInDebugMode(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        if (ktFile instanceof KtCodeFragment) {
            return true;
        }
        Boolean bool = (Boolean) ktFile.getUserData(SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE);
        if (bool == null) {
            bool = false;
        }
        return bool.booleanValue();
    }

    public static final void setSuppressDiagnosticsInDebugMode(KtFile ktFile, boolean z) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        ktFile.putUserData(SUPPRESS_DIAGNOSTICS_IN_DEBUG_MODE, Boolean.valueOf(z));
    }

    public static final String getDEBUG_TYPE_REFERENCE_STRING() {
        return DEBUG_TYPE_REFERENCE_STRING;
    }
}
