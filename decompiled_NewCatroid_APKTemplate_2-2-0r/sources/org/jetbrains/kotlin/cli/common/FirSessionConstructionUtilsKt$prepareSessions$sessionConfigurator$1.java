package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.checkers.CheckersContainersKt;
import org.jetbrains.kotlin.fir.session.FirSessionConfigurator;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirSessionConstructionUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "F", "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1 extends Lambda implements Function1<FirSessionConfigurator, Unit> {
    final /* synthetic */ boolean $extendedAnalysisMode;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSessionConstructionUtilsKt$prepareSessions$sessionConfigurator$1(boolean z) {
        super(1);
        this.$extendedAnalysisMode = z;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(FirSessionConfigurator firSessionConfigurator) {
        invoke2(firSessionConfigurator);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "$this$null");
        if (this.$extendedAnalysisMode) {
            CheckersContainersKt.registerExtendedCommonCheckers(firSessionConfigurator);
        }
    }
}
