package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FunctionCallNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.QualifiedAccessNode;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirEnumCompanionInEnumConstructorCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u0011\u001a\u0004\u0018\u00010\n*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirEnumCompanionInEnumConstructorCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "analyzeGraph", "", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "companionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "check", "declaration", "getClassSymbol", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEnumCompanionInEnumConstructorCallChecker extends FirDeclarationChecker<FirClass> {
    public static final FirEnumCompanionInEnumConstructorCallChecker INSTANCE = new FirEnumCompanionInEnumConstructorCallChecker();

    /* JADX INFO: compiled from: FirEnumCompanionInEnumConstructorCallChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ControlFlowGraph.Kind.values().length];
            try {
                iArr2[ControlFlowGraph.Kind.AnonymousFunctionCalledInPlace.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ControlFlowGraph.Kind.PropertyInitializer.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ControlFlowGraph.Kind.ClassInitializer.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ControlFlowGraph.Kind.Class.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private FirEnumCompanionInEnumConstructorCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClass firRegularClass;
        FirRegularClassSymbol companionObjectSymbol;
        FirControlFlowGraphReference controlFlowGraphReference;
        ControlFlowGraph controlFlowGraph;
        Object next;
        FirControlFlowGraphReference controlFlowGraphReference2;
        Object next2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        int i = WhenMappings.$EnumSwitchMapping$0[declaration.getClassKind().ordinal()];
        ControlFlowGraph controlFlowGraph2 = null;
        if (i == 1) {
            firRegularClass = (FirRegularClass) declaration;
        } else if (i != 2) {
            firRegularClass = null;
        } else {
            List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
            if (containingDeclarations instanceof List) {
                int size = containingDeclarations.size() - 1;
                if (size < 0) {
                    next2 = null;
                    break;
                }
                while (true) {
                    int i2 = size - 1;
                    next2 = containingDeclarations.get(size);
                    if (next2 instanceof FirRegularClass) {
                        break;
                    }
                    if (i2 < 0) {
                        next2 = null;
                        break;
                    }
                    size = i2;
                }
            } else {
                Iterator it = CollectionsKt.reversed(containingDeclarations).iterator();
                do {
                    if (!it.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it.next();
                } while (!(next2 instanceof FirRegularClass));
            }
            firRegularClass = (FirRegularClass) next2;
        }
        if (firRegularClass == null || (companionObjectSymbol = firRegularClass.getCompanionObjectSymbol()) == null || (controlFlowGraphReference = declaration.getControlFlowGraphReference()) == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null) {
            return;
        }
        analyzeGraph(controlFlowGraph, companionObjectSymbol, context, reporter);
        if (declaration.getClassKind() == ClassKind.ENUM_ENTRY) {
            Iterator<T> it2 = declaration.getDeclarations().iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!(next instanceof FirPrimaryConstructor));
            FirPrimaryConstructor firPrimaryConstructor = (FirPrimaryConstructor) next;
            if (firPrimaryConstructor != null && (controlFlowGraphReference2 = firPrimaryConstructor.getControlFlowGraphReference()) != null) {
                controlFlowGraph2 = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference2);
            }
            if (controlFlowGraph2 != null) {
                analyzeGraph(controlFlowGraph2, companionObjectSymbol, context, reporter);
            }
        }
    }

    private final void analyzeGraph(ControlFlowGraph graph, FirRegularClassSymbol companionSymbol, CheckerContext context, DiagnosticReporter reporter) {
        FirFunctionCall fir;
        Object next;
        for (CFGNode<?> cFGNode : graph.getNodes()) {
            if (cFGNode instanceof CFGNodeWithSubgraphs) {
                for (ControlFlowGraph controlFlowGraph : ((CFGNodeWithSubgraphs) cFGNode).getSubGraphs()) {
                    int i = WhenMappings.$EnumSwitchMapping$1[controlFlowGraph.getKind().ordinal()];
                    if (i == 1 || i == 2 || i == 3) {
                        analyzeGraph(controlFlowGraph, companionSymbol, context, reporter);
                    } else if (i == 4 && (controlFlowGraph.getDeclaration() instanceof FirAnonymousObject)) {
                        analyzeGraph(controlFlowGraph, companionSymbol, context, reporter);
                    }
                }
            }
            if (cFGNode instanceof QualifiedAccessNode) {
                fir = ((QualifiedAccessNode) cFGNode).getFir();
            } else if (cFGNode instanceof FunctionCallNode) {
                fir = ((FunctionCallNode) cFGNode).getFir();
            }
            Iterator<T> it = FirExpressionUtilKt.getAllReceiverExpressions(fir).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(INSTANCE.getClassSymbol((FirExpression) next, context.getSession()), companionSymbol));
            FirExpression firExpression = (FirExpression) next;
            if (firExpression != null) {
                KtSourceElement source = firExpression.getSource();
                if (source == null) {
                    source = fir.getSource();
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUNINITIALIZED_ENUM_COMPANION(), companionSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final FirRegularClassSymbol getClassSymbol(FirExpression firExpression, FirSession firSession) {
        FirRegularClassSymbol boundSymbol;
        if (firExpression instanceof FirResolvedQualifier) {
            boundSymbol = FirHelpersKt.toRegularClassSymbol(firExpression.getTypeRef(), firSession);
        } else {
            FirReference reference = FirExpressionUtilKt.toReference(firExpression);
            FirThisReference firThisReference = reference instanceof FirThisReference ? (FirThisReference) reference : null;
            boundSymbol = firThisReference != null ? firThisReference.getBoundSymbol() : null;
        }
        if (boundSymbol instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) boundSymbol;
        }
        return null;
    }
}
