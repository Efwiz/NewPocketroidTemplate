package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirConstructorAllowedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstructorAllowedChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstructorChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConstructorAllowedChecker extends FirDeclarationChecker<FirConstructor> {
    public static final FirConstructorAllowedChecker INSTANCE = new FirConstructorAllowedChecker();

    /* JADX INFO: compiled from: FirConstructorAllowedChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirConstructorAllowedChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirConstructor declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirModifierList modifierList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
        FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
        if (firClass == null) {
            return;
        }
        KtSourceElement source = declaration.getSource();
        IElementType elementType = source != null ? source.getElementType() : null;
        if (Intrinsics.areEqual(elementType, KtNodeTypes.PRIMARY_CONSTRUCTOR) || Intrinsics.areEqual(elementType, KtNodeTypes.SECONDARY_CONSTRUCTOR)) {
            int i = WhenMappings.$EnumSwitchMapping$0[firClass.getClassKind().ordinal()];
            if (i == 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCONSTRUCTOR_IN_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (i == 2) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCONSTRUCTOR_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (i == 3) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCONSTRUCTOR_IN_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (i == 4) {
                if (Intrinsics.areEqual(declaration.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_PRIVATE_CONSTRUCTOR_IN_ENUM(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (i != 5) {
                return;
            }
            if (firClass instanceof FirAnonymousObject) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getCONSTRUCTOR_IN_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if ((firClass instanceof FirRegularClass) && firClass.getStatus().getModality() == Modality.SEALED && (modifierList = FirKeywordUtilsKt.getModifierList(source)) != null) {
                List<FirModifier<?>> modifiers = modifierList.getModifiers();
                if ((modifiers instanceof Collection) && modifiers.isEmpty()) {
                    return;
                }
                Iterator<T> it = modifiers.iterator();
                while (it.hasNext()) {
                    KtModifierKeywordToken token = ((FirModifier) it.next()).getToken();
                    if (KtTokens.VISIBILITY_MODIFIERS.contains(token) && !Intrinsics.areEqual(token, KtTokens.PROTECTED_KEYWORD) && !Intrinsics.areEqual(token, KtTokens.PRIVATE_KEYWORD)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_PRIVATE_OR_PROTECTED_CONSTRUCTOR_IN_SEALED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                }
            }
        }
    }
}
