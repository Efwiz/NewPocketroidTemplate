package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnyTypeRef;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirPrimaryConstructorSuperTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPrimaryConstructorSuperTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkSuperTypeNotInitialized", "primaryConstructorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "regularClass", "checkSupertypeInitializedWithoutPrimaryConstructor", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPrimaryConstructorSuperTypeChecker extends FirDeclarationChecker<FirClass> {
    public static final FirPrimaryConstructorSuperTypeChecker INSTANCE = new FirPrimaryConstructorSuperTypeChecker();

    private FirPrimaryConstructorSuperTypeChecker() {
    }

    private final void checkSuperTypeNotInitialized(FirConstructorSymbol primaryConstructorSymbol, FirClass regularClass, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        KtSourceElement source;
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        Object obj = null;
        if (containingDeclarations instanceof List) {
            int size = containingDeclarations.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    obj = containingDeclarations.get(size);
                    if (obj instanceof FirRegularClass) {
                        obj = obj;
                        break;
                    } else if (i < 0) {
                        break;
                    } else {
                        size = i;
                    }
                }
            }
        } else {
            for (Object obj2 : CollectionsKt.reversed(containingDeclarations)) {
                if (obj2 instanceof FirRegularClass) {
                    obj = obj2;
                    break;
                }
            }
        }
        FirRegularClass firRegularClass = (FirRegularClass) obj;
        FirDelegatedConstructorCall resolvedDelegatedConstructorCall = primaryConstructorSymbol.getResolvedDelegatedConstructorCall();
        if (resolvedDelegatedConstructorCall == null) {
            return;
        }
        FirTypeRef constructedTypeRef = resolvedDelegatedConstructorCall.getConstructedTypeRef();
        if ((constructedTypeRef instanceof FirImplicitAnyTypeRef) || (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(constructedTypeRef), context.getSession())) == null || regularClassSymbol.getClassKind().isSingleton()) {
            return;
        }
        FirClass firClass = regularClass;
        FirRegularClass firRegularClass2 = firRegularClass;
        if (DeclarationUtilsKt.isEffectivelyExpect(firClass, firRegularClass2, context) || DeclarationUtilsKt.isEffectivelyExternal(firClass, firRegularClass2, context) || (source = resolvedDelegatedConstructorCall.getSource()) == null || !(source.getKind() instanceof KtFakeSourceElementKind) || Intrinsics.areEqual(regularClassSymbol.getClassId(), StandardClassIds.INSTANCE.getEnum()) || Intrinsics.areEqual(regularClassSymbol.getClassId(), StandardClassIds.Java.INSTANCE.getRecord()) || Intrinsics.areEqual(source.getElementType(), KtNodeTypes.SUPER_TYPE_CALL_ENTRY)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, constructedTypeRef.getSource(), FirErrors.INSTANCE.getSUPERTYPE_NOT_INITIALIZED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final void checkSupertypeInitializedWithoutPrimaryConstructor(FirClass regularClass, DiagnosticReporter reporter, CheckerContext context) {
        SourceNavigator sourceNavigatorForElement = SourceNavigator.INSTANCE.forElement(regularClass);
        Iterator<FirTypeRef> it = regularClass.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            if (sourceNavigatorForElement.isInConstructorCallee(it.next())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, regularClass.getSource(), FirErrors.INSTANCE.getSUPERTYPE_INITIALIZED_WITHOUT_PRIMARY_CONSTRUCTOR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() == ClassKind.INTERFACE) {
            SourceNavigator sourceNavigatorForElement = SourceNavigator.INSTANCE.forElement(declaration);
            for (FirTypeRef firTypeRef : declaration.getSuperTypeRefs()) {
                if (sourceNavigatorForElement.isInConstructorCallee(firTypeRef)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getSUPERTYPE_INITIALIZED_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            return;
        }
        if (declaration.getClassKind() == ClassKind.ENUM_ENTRY) {
            return;
        }
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.primaryConstructorIfAny(declaration, context.getSession());
        if (firConstructorSymbolPrimaryConstructorIfAny == null) {
            checkSupertypeInitializedWithoutPrimaryConstructor(declaration, reporter, context);
        } else {
            checkSuperTypeNotInitialized(firConstructorSymbolPrimaryConstructorIfAny, declaration, context, reporter);
        }
    }
}
