package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0000J\"\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00002\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u000eJ\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "", "()V", "usedTypeParameters", "", "", "addUsedReifiedParameter", "", "name", "mergeAll", "other", "propagateChildUsagesWithinContext", "child", "reifiedTypeParameterNamesInContext", "Lkotlin/Function0;", "", "wereUsedReifiedParameters", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReifiedTypeParametersUsages {
    private final Set<String> usedTypeParameters = new HashSet();

    public final boolean wereUsedReifiedParameters() {
        return !this.usedTypeParameters.isEmpty();
    }

    public final void addUsedReifiedParameter(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.usedTypeParameters.add(name2);
    }

    public final void propagateChildUsagesWithinContext(ReifiedTypeParametersUsages child, Function0<? extends Set<String>> reifiedTypeParameterNamesInContext) {
        Intrinsics.checkNotNullParameter(child, "child");
        Intrinsics.checkNotNullParameter(reifiedTypeParameterNamesInContext, "reifiedTypeParameterNamesInContext");
        if (child.wereUsedReifiedParameters()) {
            this.usedTypeParameters.addAll(SetsKt.minus((Set) child.usedTypeParameters, (Iterable) reifiedTypeParameterNamesInContext.invoke()));
        }
    }

    public final void mergeAll(ReifiedTypeParametersUsages other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (other.wereUsedReifiedParameters()) {
            this.usedTypeParameters.addAll(other.usedTypeParameters);
        }
    }
}
