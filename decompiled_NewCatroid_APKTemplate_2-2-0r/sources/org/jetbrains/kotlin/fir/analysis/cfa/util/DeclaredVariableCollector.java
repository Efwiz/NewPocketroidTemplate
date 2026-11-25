package org.jetbrains.kotlin.fir.analysis.cfa.util;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.util.MultimapKt;
import org.jetbrains.kotlin.fir.util.SetMultimap;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: DeclaredVariableCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0005R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/DeclaredVariableCollector;", "", "()V", "declaredVariablesPerElement", "Lorg/jetbrains/kotlin/fir/util/SetMultimap;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getDeclaredVariablesPerElement", "()Lorg/jetbrains/kotlin/fir/util/SetMultimap;", "visitor", "org/jetbrains/kotlin/fir/analysis/cfa/util/DeclaredVariableCollector$visitor$1", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/DeclaredVariableCollector$visitor$1;", "enterCapturingStatement", "", "statement", "exitCapturingStatement", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclaredVariableCollector {
    private final SetMultimap<FirStatement, FirPropertySymbol> declaredVariablesPerElement = MultimapKt.setMultimapOf();
    private final DeclaredVariableCollector$visitor$1 visitor = new FirVisitor<Unit, FirStatement>() { // from class: org.jetbrains.kotlin.fir.analysis.cfa.util.DeclaredVariableCollector$visitor$1
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitAnonymousFunction(FirAnonymousFunction firAnonymousFunction, FirStatement firStatement) {
            visitAnonymousFunction2(firAnonymousFunction, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitAnonymousObject(FirAnonymousObject firAnonymousObject, FirStatement firStatement) {
            visitAnonymousObject2(firAnonymousObject, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop(FirDoWhileLoop firDoWhileLoop, FirStatement firStatement) {
            visitDoWhileLoop2(firDoWhileLoop, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, FirStatement firStatement) {
            visitElement2(firElement, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitProperty(FirProperty firProperty, FirStatement firStatement) {
            visitProperty2(firProperty, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitRegularClass(FirRegularClass firRegularClass, FirStatement firStatement) {
            visitRegularClass2(firRegularClass, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction(FirSimpleFunction firSimpleFunction, FirStatement firStatement) {
            visitSimpleFunction2(firSimpleFunction, firStatement);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitWhileLoop(FirWhileLoop firWhileLoop, FirStatement firStatement) {
            visitWhileLoop2(firWhileLoop, firStatement);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(FirElement element, FirStatement data) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this, data);
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(FirProperty property, FirStatement data) {
            Intrinsics.checkNotNullParameter(property, "property");
            if (property.isLocal()) {
                if (data == null) {
                    throw new IllegalArgumentException("Required value was null.".toString());
                }
                this.this$0.getDeclaredVariablesPerElement().put(data, property.getSymbol());
            }
            visitElement2((FirElement) property, data);
        }

        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public void visitWhileLoop2(FirWhileLoop whileLoop, FirStatement data) {
            Intrinsics.checkNotNullParameter(whileLoop, "whileLoop");
            visitCapturingStatement(whileLoop, data);
        }

        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public void visitDoWhileLoop2(FirDoWhileLoop doWhileLoop, FirStatement data) {
            Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
            visitCapturingStatement(doWhileLoop, data);
        }

        /* JADX INFO: renamed from: visitAnonymousFunction, reason: avoid collision after fix types in other method */
        public void visitAnonymousFunction2(FirAnonymousFunction anonymousFunction, FirStatement data) {
            Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
            visitCapturingStatement(anonymousFunction, data);
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(FirSimpleFunction simpleFunction, FirStatement data) {
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            visitCapturingStatement(simpleFunction, data);
        }

        /* JADX INFO: renamed from: visitRegularClass, reason: avoid collision after fix types in other method */
        public void visitRegularClass2(FirRegularClass regularClass, FirStatement data) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            visitCapturingStatement(regularClass, data);
        }

        /* JADX INFO: renamed from: visitAnonymousObject, reason: avoid collision after fix types in other method */
        public void visitAnonymousObject2(FirAnonymousObject anonymousObject, FirStatement data) {
            Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
            visitCapturingStatement(anonymousObject, data);
        }

        private final void visitCapturingStatement(FirStatement statement, FirStatement parent) {
            visitElement2((FirElement) statement, statement);
            if (parent != null) {
                this.this$0.getDeclaredVariablesPerElement().putAll(parent, this.this$0.getDeclaredVariablesPerElement().get(statement));
            }
        }
    };

    public final SetMultimap<FirStatement, FirPropertySymbol> getDeclaredVariablesPerElement() {
        return this.declaredVariablesPerElement;
    }

    public final Set<FirPropertySymbol> enterCapturingStatement(FirStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        if (!(statement instanceof FirLoop) && !(statement instanceof FirClass)) {
            boolean z = statement instanceof FirFunction;
        }
        if (!this.declaredVariablesPerElement.contains(statement)) {
            statement.accept(this.visitor, null);
        }
        return (Set) this.declaredVariablesPerElement.get(statement);
    }

    public final void exitCapturingStatement(FirStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        if (!(statement instanceof FirLoop) && !(statement instanceof FirClass)) {
            boolean z = statement instanceof FirFunction;
        }
        this.declaredVariablesPerElement.removeKey(statement);
    }
}
