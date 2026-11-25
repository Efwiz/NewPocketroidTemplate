package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;

/* JADX INFO: compiled from: FirCommonConstructorDelegationIssuesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J$\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r*\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u000e\u0010\u0010\u001a\u0004\u0018\u00010\u000e*\u00020\u000eH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCommonConstructorDelegationIssuesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findCycle", "", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "knownCyclicConstructors", "getDelegated", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCommonConstructorDelegationIssuesChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirCommonConstructorDelegationIssuesChecker INSTANCE = new FirCommonConstructorDelegationIssuesChecker();

    private FirCommonConstructorDelegationIssuesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Object next;
        KtSourceElement source;
        FirDelegatedConstructorCall delegatedConstructor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        if (containingDeclarations instanceof List) {
            int size = containingDeclarations.size() - 1;
            if (size < 0) {
                next = null;
                break;
            }
            while (true) {
                int i = size - 1;
                next = containingDeclarations.get(size);
                if (next instanceof FirRegularClass) {
                    break;
                }
                if (i < 0) {
                    next = null;
                    break;
                }
                size = i;
            }
        } else {
            Iterator it = CollectionsKt.reversed(containingDeclarations).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!(next instanceof FirRegularClass));
        }
        FirRegularClass firRegularClass = declaration;
        if (DeclarationUtilsKt.isEffectivelyExternal(firRegularClass, (FirRegularClass) next, context)) {
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
        boolean zIsEffectivelyExpect = DeclarationUtilsKt.isEffectivelyExpect(firRegularClass, objLastOrNull instanceof FirRegularClass ? (FirRegularClass) objLastOrNull : null, context);
        LinkedHashSet<FirConstructor> linkedHashSet2 = new LinkedHashSet();
        boolean z = false;
        for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
            if (firDeclaration instanceof FirConstructor) {
                FirConstructor firConstructor = (FirConstructor) firDeclaration;
                if (firConstructor.getIsPrimary()) {
                    z = true;
                } else {
                    linkedHashSet2.add(firDeclaration);
                    Set<FirConstructor> setFindCycle = findCycle(firConstructor, linkedHashSet);
                    if (setFindCycle != null) {
                        CollectionsKt.addAll(linkedHashSet, setFindCycle);
                    }
                }
            }
        }
        LinkedHashSet linkedHashSet3 = linkedHashSet;
        CollectionsKt.removeAll((Collection) linkedHashSet2, (Iterable) linkedHashSet3);
        if (z) {
            for (FirConstructor firConstructor2 : linkedHashSet2) {
                if (!zIsEffectivelyExpect && ((delegatedConstructor = firConstructor2.getDelegatedConstructor()) == null || !delegatedConstructor.getIsThis())) {
                    FirDelegatedConstructorCall delegatedConstructor2 = firConstructor2.getDelegatedConstructor();
                    if ((delegatedConstructor2 != null ? delegatedConstructor2.getSource() : null) != null) {
                        FirDelegatedConstructorCall delegatedConstructor3 = firConstructor2.getDelegatedConstructor();
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegatedConstructor3 != null ? delegatedConstructor3.getSource() : null, FirErrors.INSTANCE.getPRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    } else {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firConstructor2.getSource(), FirErrors.INSTANCE.getPRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        } else {
            for (FirConstructor firConstructor3 : linkedHashSet2) {
                FirDelegatedConstructorCall delegatedConstructor4 = firConstructor3.getDelegatedConstructor();
                if ((delegatedConstructor4 != null ? delegatedConstructor4.getCalleeReference() : null) instanceof FirDiagnosticHolder) {
                    FirDelegatedConstructorCall delegatedConstructor5 = firConstructor3.getDelegatedConstructor();
                    if ((((delegatedConstructor5 == null || (source = delegatedConstructor5.getSource()) == null) ? null : source.getKind()) instanceof KtFakeSourceElementKind) && !firConstructor3.getStatus().isExpect()) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firConstructor3.getSource(), FirErrors.INSTANCE.getEXPLICIT_DELEGATION_CALL_REQUIRED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
        Iterator it2 = linkedHashSet3.iterator();
        while (it2.hasNext()) {
            FirDelegatedConstructorCall delegatedConstructor6 = ((FirConstructor) it2.next()).getDelegatedConstructor();
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegatedConstructor6 != null ? delegatedConstructor6.getSource() : null, FirErrors.INSTANCE.getCYCLIC_CONSTRUCTOR_DELEGATION_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final Set<FirConstructor> findCycle(FirConstructor firConstructor, Set<? extends FirConstructor> set) {
        Set<FirConstructor> setMutableSetOf = SetsKt.mutableSetOf(firConstructor);
        FirConstructor delegated = getDelegated(firConstructor);
        while (!firConstructor.getIsPrimary() && delegated != null) {
            if (setMutableSetOf.contains(delegated) || set.contains(delegated)) {
                return setMutableSetOf;
            }
            FirConstructor delegated2 = getDelegated(delegated);
            setMutableSetOf.add(delegated);
            FirConstructor firConstructor2 = delegated;
            delegated = delegated2;
            firConstructor = firConstructor2;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirConstructor getDelegated(FirConstructor firConstructor) {
        FirReference calleeReference;
        FirConstructorSymbol resolvedConstructorSymbol;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firConstructor.getSymbol(), FirResolvePhase.BODY_RESOLVE);
        FirDelegatedConstructorCall delegatedConstructor = firConstructor.getDelegatedConstructor();
        if (delegatedConstructor == null || (calleeReference = delegatedConstructor.getCalleeReference()) == null || (resolvedConstructorSymbol = FirReferenceUtilsKt.toResolvedConstructorSymbol(calleeReference, true)) == null) {
            return null;
        }
        return (FirConstructor) resolvedConstructorSymbol.getFir();
    }
}
