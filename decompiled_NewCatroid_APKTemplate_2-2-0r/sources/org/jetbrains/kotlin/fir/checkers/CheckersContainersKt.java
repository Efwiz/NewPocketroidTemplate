package org.jetbrains.kotlin.fir.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.checkers.CommonDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.CommonExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.CommonLanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.CommonTypeCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.ExtendedDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.ExtendedExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.ExtendedLanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.ExtendedTypeCheckers;
import org.jetbrains.kotlin.fir.analysis.js.checkers.JsDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.js.checkers.JsExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.JvmDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.JvmExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.JvmTypeCheckers;
import org.jetbrains.kotlin.fir.analysis.p003native.checkers.NativeDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.p003native.checkers.NativeExpressionCheckers;
import org.jetbrains.kotlin.fir.session.FirSessionConfigurator;

/* JADX INFO: compiled from: CheckersContainers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002¨\u0006\b"}, d2 = {"registerCommonCheckers", "", "Lorg/jetbrains/kotlin/fir/session/FirSessionConfigurator;", "registerExtendedCommonCheckers", "registerJsCheckers", "registerJvmCheckers", "registerNativeCheckers", "registerWasmCheckers", "entrypoint"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CheckersContainersKt {
    public static final void registerWasmCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
    }

    public static final void registerCommonCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
        firSessionConfigurator.useCheckers(CommonDeclarationCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(CommonExpressionCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(CommonTypeCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(CommonLanguageVersionSettingsCheckers.INSTANCE);
    }

    public static final void registerExtendedCommonCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
        firSessionConfigurator.useCheckers(ExtendedExpressionCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(ExtendedDeclarationCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(ExtendedTypeCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(ExtendedLanguageVersionSettingsCheckers.INSTANCE);
    }

    public static final void registerJvmCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
        firSessionConfigurator.useCheckers(JvmDeclarationCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(JvmExpressionCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(JvmTypeCheckers.INSTANCE);
    }

    public static final void registerJsCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
        firSessionConfigurator.useCheckers(JsDeclarationCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(JsExpressionCheckers.INSTANCE);
    }

    public static final void registerNativeCheckers(FirSessionConfigurator firSessionConfigurator) {
        Intrinsics.checkNotNullParameter(firSessionConfigurator, "<this>");
        firSessionConfigurator.useCheckers(NativeDeclarationCheckers.INSTANCE);
        firSessionConfigurator.useCheckers(NativeExpressionCheckers.INSTANCE);
    }
}
