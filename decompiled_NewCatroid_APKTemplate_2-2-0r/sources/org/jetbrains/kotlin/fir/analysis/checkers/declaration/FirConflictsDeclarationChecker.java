package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirConflictsHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirDeclarationInspector;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: FirConflictsDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\tH\u0002J6\u0010\u0011\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u001c\u0010\u0012\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u00140\u0013H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConflictsDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "inspector", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirDeclarationInspector;", "reportConflicts", "declarationConflictingSymbols", "", "Lorg/jetbrains/kotlin/utils/SmartSet;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConflictsDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirConflictsDeclarationChecker INSTANCE = new FirConflictsDeclarationChecker();

    private FirConflictsDeclarationChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirFile) {
            FirDeclarationInspector firDeclarationInspector = new FirDeclarationInspector(context);
            checkFile((FirFile) declaration, firDeclarationInspector, context);
            reportConflicts(reporter, context, firDeclarationInspector.getDeclarationConflictingSymbols());
            return;
        }
        if (declaration instanceof FirRegularClass) {
            KtSourceElement source = declaration.getSource();
            if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind)) {
                FirConflictsHelpersKt.checkForLocalRedeclarations(((FirRegularClass) declaration).getTypeParameters(), context, reporter);
            }
            FirDeclarationInspector firDeclarationInspector2 = new FirDeclarationInspector(context);
            firDeclarationInspector2.collectClassMembers((FirRegularClass) declaration);
            reportConflicts(reporter, context, firDeclarationInspector2.getDeclarationConflictingSymbols());
            return;
        }
        KtSourceElement source2 = declaration.getSource();
        if (((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind) || !(declaration instanceof FirTypeParameterRefsOwner)) {
            return;
        }
        if (declaration instanceof FirFunction) {
            FirConflictsHelpersKt.checkForLocalRedeclarations(((FirFunction) declaration).getValueParameters(), context, reporter);
        }
        FirConflictsHelpersKt.checkForLocalRedeclarations(((FirTypeParameterRefsOwner) declaration).getTypeParameters(), context, reporter);
    }

    private final void checkFile(FirFile file, FirDeclarationInspector inspector, CheckerContext context) {
        ScopeSession scopeSession = context.getSessionHolder().getScopeSession();
        FqName packageFqName = UtilsKt.getPackageFqName(file);
        ScopeSessionKey<FqName, FirPackageMemberScope> package_member = FirPackageMemberScopeKt.getPACKAGE_MEMBER();
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(packageFqName);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(packageFqName, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object firPackageMemberScope = map2.get(package_member);
        if (firPackageMemberScope == null) {
            firPackageMemberScope = new FirPackageMemberScope(UtilsKt.getPackageFqName(file), context.getSessionHolder().getSession(), null, null, 12, null);
            map2.put(package_member, firPackageMemberScope);
        }
        inspector.collectTopLevel(file, (FirPackageMemberScope) firPackageMemberScope);
        for (FirDeclaration firDeclaration : file.getDeclarations()) {
            if (!(firDeclaration instanceof FirErrorProperty)) {
                boolean z = firDeclaration instanceof FirErrorFunction;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0070 A[EDGE_INSN: B:27:0x0070->B:29:0x007d BREAK  A[LOOP:1: B:22:0x0059->B:38:?]] */
    private final void reportConflicts(DiagnosticReporter reporter, CheckerContext context, Map<FirDeclaration, SmartSet<FirBasedSymbol<?>>> declarationConflictingSymbols) {
        KtDiagnosticFactory1<Collection<FirBasedSymbol<?>>> conflicting_overloads;
        for (Map.Entry<FirDeclaration, SmartSet<FirBasedSymbol<?>>> entry : declarationConflictingSymbols.entrySet()) {
            FirDeclaration key = entry.getKey();
            SmartSet<FirBasedSymbol<?>> value = entry.getValue();
            KtSourceElement source = key.getSource();
            if (!value.isEmpty()) {
                if ((key instanceof FirSimpleFunction) || (key instanceof FirConstructor)) {
                    conflicting_overloads = FirErrors.INSTANCE.getCONFLICTING_OVERLOADS();
                } else if ((key instanceof FirClassLikeDeclaration) && org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt.getContainingDeclaration((FirClassLikeDeclaration) key, context.getSession()) == null) {
                    SmartSet<FirBasedSymbol<?>> smartSet = value;
                    if ((smartSet instanceof Collection) && smartSet.isEmpty()) {
                        conflicting_overloads = FirErrors.INSTANCE.getREDECLARATION();
                        break;
                    }
                    Iterator<FirBasedSymbol<?>> it = smartSet.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next() instanceof FirClassLikeSymbol) {
                                conflicting_overloads = FirErrors.INSTANCE.getPACKAGE_OR_CLASSIFIER_REDECLARATION();
                                break;
                            }
                        } else {
                            conflicting_overloads = FirErrors.INSTANCE.getREDECLARATION();
                            break;
                        }
                    }
                } else {
                    conflicting_overloads = FirErrors.INSTANCE.getREDECLARATION();
                    break;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, conflicting_overloads, value, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
