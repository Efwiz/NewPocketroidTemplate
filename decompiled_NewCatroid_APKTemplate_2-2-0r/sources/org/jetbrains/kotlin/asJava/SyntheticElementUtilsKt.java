package org.jetbrains.kotlin.asJava;

import com.reandroid.arsc.value.CompoundEntry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;

/* JADX INFO: compiled from: SyntheticElementUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"isGetEntriesMethod", "", "method", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "isSyntheticValuesOrValueOfMethod", "resolution.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticElementUtilsKt {
    public static final boolean isSyntheticValuesOrValueOfMethod(PsiMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        if (!(method instanceof SyntheticElement)) {
            return false;
        }
        String name2 = method.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if ((!Intrinsics.areEqual(name2, CompoundEntry.NAME_values) && !Intrinsics.areEqual(name2, "valueOf")) || !method.hasModifierProperty("public") || !method.hasModifierProperty("static")) {
            return false;
        }
        int parametersCount = method.getParameterList().getParametersCount();
        if (Intrinsics.areEqual(name2, CompoundEntry.NAME_values)) {
            return parametersCount == 0;
        }
        return Intrinsics.areEqual(name2, "valueOf") && parametersCount == 1;
    }

    public static final boolean isGetEntriesMethod(PsiMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return Intrinsics.areEqual(method.getName(), "getEntries") && method.getParameterList().getParametersCount() == 0 && method.hasModifierProperty("public") && method.hasModifierProperty("static");
    }
}
