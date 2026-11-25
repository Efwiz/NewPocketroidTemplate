package org.jetbrains.kotlin.fir.analysis.collectors.components;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentKt;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableDeclarationNode;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: ControlFlowAnalysisDiagnosticComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001*B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016J\u0018\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u0017\u001a\u00020\u0013H\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ControlFlowAnalysisDiagnosticComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "declarationCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;)V", "cfaCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "variableAssignmentCheckers", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "analyze", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "data", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "LocalPropertyCollector", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowAnalysisDiagnosticComponent extends AbstractDiagnosticCollectorComponent {
    private final Set<FirControlFlowChecker> cfaCheckers;
    private final Set<AbstractFirPropertyInitializationChecker> variableAssignmentCheckers;

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnonymousObject(FirAnonymousObject firAnonymousObject, CheckerContext checkerContext) {
        visitAnonymousObject2(firAnonymousObject, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitConstructor(FirConstructor firConstructor, CheckerContext checkerContext) {
        visitConstructor2(firConstructor, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitFunction(FirFunction firFunction, CheckerContext checkerContext) {
        visitFunction2(firFunction, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitProperty(FirProperty firProperty, CheckerContext checkerContext) {
        visitProperty2(firProperty, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertyAccessor(FirPropertyAccessor firPropertyAccessor, CheckerContext checkerContext) {
        visitPropertyAccessor2(firPropertyAccessor, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitRegularClass(FirRegularClass firRegularClass, CheckerContext checkerContext) {
        visitRegularClass2(firRegularClass, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction(FirSimpleFunction firSimpleFunction, CheckerContext checkerContext) {
        visitSimpleFunction2(firSimpleFunction, checkerContext);
        return Unit.INSTANCE;
    }

    public /* synthetic */ ControlFlowAnalysisDiagnosticComponent(FirSession firSession, DiagnosticReporter diagnosticReporter, DeclarationCheckers declarationCheckers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, diagnosticReporter, (i & 4) != 0 ? CheckersComponentKt.getCheckersComponent(firSession).getDeclarationCheckers() : declarationCheckers);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ControlFlowAnalysisDiagnosticComponent(FirSession session, DiagnosticReporter reporter, DeclarationCheckers declarationCheckers) {
        super(session, reporter);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(declarationCheckers, "declarationCheckers");
        this.cfaCheckers = declarationCheckers.getControlFlowAnalyserCheckers();
        this.variableAssignmentCheckers = declarationCheckers.getVariableAssignmentCfaBasedCheckers();
    }

    private final void analyze(FirControlFlowGraphOwner declaration, CheckerContext context) {
        ControlFlowGraph controlFlowGraph;
        FirControlFlowGraphReference controlFlowGraphReference = declaration.getControlFlowGraphReference();
        if (controlFlowGraphReference == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null || controlFlowGraph.isSubGraph()) {
            return;
        }
        Iterator<T> it = this.cfaCheckers.iterator();
        while (it.hasNext()) {
            ((FirControlFlowChecker) it.next()).analyze(controlFlowGraph, getReporter(), context);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        controlFlowGraph.traverse(new LocalPropertyCollector(linkedHashSet));
        if (linkedHashSet.isEmpty()) {
            return;
        }
        PropertyInitializationInfoData propertyInitializationInfoData = new PropertyInitializationInfoData(linkedHashSet, null, controlFlowGraph);
        Iterator<T> it2 = this.variableAssignmentCheckers.iterator();
        while (it2.hasNext()) {
            ((AbstractFirPropertyInitializationChecker) it2.next()).analyze(propertyInitializationInfoData, getReporter(), context);
        }
    }

    /* JADX INFO: renamed from: visitRegularClass, reason: avoid collision after fix types in other method */
    public void visitRegularClass2(FirRegularClass regularClass, CheckerContext data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(regularClass, data);
    }

    /* JADX INFO: renamed from: visitAnonymousObject, reason: avoid collision after fix types in other method */
    public void visitAnonymousObject2(FirAnonymousObject anonymousObject, CheckerContext data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(anonymousObject, data);
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(FirProperty property, CheckerContext data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(property, data);
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(FirFunction function, CheckerContext data) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(function, data);
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(FirSimpleFunction simpleFunction, CheckerContext data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(simpleFunction, data);
    }

    /* JADX INFO: renamed from: visitPropertyAccessor, reason: avoid collision after fix types in other method */
    public void visitPropertyAccessor2(FirPropertyAccessor propertyAccessor, CheckerContext data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(propertyAccessor, data);
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(FirConstructor constructor, CheckerContext data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        analyze(constructor, data);
    }

    /* JADX INFO: compiled from: ControlFlowAnalysisDiagnosticComponent.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/ControlFlowAnalysisDiagnosticComponent$LocalPropertyCollector;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", "result", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "(Ljava/util/Set;)V", "visitNode", "", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LocalPropertyCollector extends ControlFlowGraphVisitorVoid {
        private final Set<FirPropertySymbol> result;

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitNode(CFGNode<?> node) {
            Intrinsics.checkNotNullParameter(node, "node");
        }

        public LocalPropertyCollector(Set<FirPropertySymbol> result) {
            Intrinsics.checkNotNullParameter(result, "result");
            this.result = result;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitorVoid
        public void visitVariableDeclarationNode(VariableDeclarationNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            this.result.add(node.getFir().getSymbol());
        }
    }
}
