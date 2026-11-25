package org.jetbrains.kotlin.fir.analysis;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;

/* JADX INFO: compiled from: FirEmptyOverridesBackwardCompatibilityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/FirEmptyOverridesBackwardCompatibilityHelper;", "Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;", "()V", "overrideCanBeOmitted", "", "overriddenMemberSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEmptyOverridesBackwardCompatibilityHelper extends FirOverridesBackwardCompatibilityHelper {
    public static final FirEmptyOverridesBackwardCompatibilityHelper INSTANCE = new FirEmptyOverridesBackwardCompatibilityHelper();

    @Override // org.jetbrains.kotlin.fir.analysis.FirOverridesBackwardCompatibilityHelper
    public boolean overrideCanBeOmitted(List<? extends FirCallableSymbol<?>> overriddenMemberSymbols, CheckerContext context) {
        Intrinsics.checkNotNullParameter(overriddenMemberSymbols, "overriddenMemberSymbols");
        Intrinsics.checkNotNullParameter(context, "context");
        return false;
    }

    private FirEmptyOverridesBackwardCompatibilityHelper() {
    }
}
