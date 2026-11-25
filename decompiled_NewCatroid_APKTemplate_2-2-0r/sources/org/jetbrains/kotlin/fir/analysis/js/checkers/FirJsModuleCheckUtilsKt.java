package org.jetbrains.kotlin.fir.analysis.js.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirJsModuleCheckUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001f\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u0015"}, d2 = {"jsModuleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getJsModuleKind", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "jsModuleKindComponent", "Lorg/jetbrains/kotlin/fir/analysis/js/checkers/FirJsModuleKind;", "getJsModuleKindComponent", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/analysis/js/checkers/FirJsModuleKind;", "jsModuleKindComponent$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "checkJsModuleUsage", "", "callee", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "checkers.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJsModuleCheckUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirJsModuleCheckUtilsKt.class, "checkers.js"), "jsModuleKindComponent", "getJsModuleKindComponent(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/analysis/js/checkers/FirJsModuleKind;"))};
    private static final ArrayMapAccessor jsModuleKindComponent$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirJsModuleKind.class), (Object) null, 2, (Object) null);

    /* JADX INFO: compiled from: FirJsModuleCheckUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ModuleKind.values().length];
            try {
                iArr[ModuleKind.UMD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ModuleKind.PLAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final FirJsModuleKind getJsModuleKindComponent(FirSession firSession) {
        return (FirJsModuleKind) jsModuleKindComponent$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    private static final ModuleKind getJsModuleKind(FirSession firSession) {
        return getJsModuleKindComponent(firSession).getModuleKind();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0048  */
    public static final void checkJsModuleUsage(FirBasedSymbol<?> callee, CheckerContext context, DiagnosticReporter reporter, AbstractKtSourceElement abstractKtSourceElement) {
        boolean z;
        FirFileSymbol symbol;
        FirFileSymbol symbol2;
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ModuleKind jsModuleKind = getJsModuleKind(context.getSession());
        FirSession session = callee.getModuleData().getSession();
        FirBasedSymbol<?> rootClassLikeSymbolOrSelf = FirJsHelpersKt.getRootClassLikeSymbolOrSelf(callee, session);
        FirFile containingFile = FirJsHelpersKt.getContainingFile(rootClassLikeSymbolOrSelf, session);
        boolean z2 = false;
        if (FirHelpersKt.getAnnotationStringParameter(rootClassLikeSymbolOrSelf, JsStandardClassIds.Annotations.JsModule, session) != null) {
            z = true;
        } else {
            if (((containingFile == null || (symbol2 = containingFile.getSymbol()) == null) ? null : FirHelpersKt.getAnnotationStringParameter(symbol2, JsStandardClassIds.Annotations.JsModule, session)) != null) {
                z = true;
            } else {
                z = false;
            }
        }
        if (FirAnnotationUtilsKt.hasAnnotation(rootClassLikeSymbolOrSelf, JsStandardClassIds.Annotations.JsNonModule, session) || (containingFile != null && (symbol = containingFile.getSymbol()) != null && FirAnnotationUtilsKt.hasAnnotation(symbol, JsStandardClassIds.Annotations.JsNonModule, session))) {
            z2 = true;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[jsModuleKind.ordinal()];
        if (i == 1) {
            if ((z2 || !z) && (!z2 || z)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, abstractKtSourceElement, FirJsErrors.INSTANCE.getCALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (i != 2) {
            if (z || !z2) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, abstractKtSourceElement, FirJsErrors.INSTANCE.getCALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM(), callee, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (z2 || !z) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, abstractKtSourceElement, FirJsErrors.INSTANCE.getCALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM(), callee, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
