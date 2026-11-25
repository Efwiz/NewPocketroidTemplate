package org.jetbrains.kotlin.fir.resolve.transformers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0001\u0010B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH&J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&\u0082\u0001\u0005\u0011\u0012\u0013\u0014\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "", "()V", "computeMissingCases", "", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "isApplicable", "", "AbstractConditionChecker", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnBooleanExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnEnumExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnNothingExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnNullableExhaustivenessChecker;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenOnSealedClassExhaustivenessChecker;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class WhenExhaustivenessChecker {
    public /* synthetic */ WhenExhaustivenessChecker(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void computeMissingCases(FirWhenExpression whenExpression, ConeKotlinType subjectType, FirSession session, Collection<WhenMissingCase> destination);

    public abstract boolean isApplicable(ConeKotlinType subjectType, FirSession session);

    private WhenExhaustivenessChecker() {
    }

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b$\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0005J\u001d\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\nJ\u001d\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker$AbstractConditionChecker;", "D", "", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "", "()V", "visitBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;Ljava/lang/Object;)V", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)V", "visitWhenBranch", "whenBranch", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;Ljava/lang/Object;)V", "visitWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;Ljava/lang/Object;)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static abstract class AbstractConditionChecker<D> extends FirVisitor<Unit, D> {
        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(FirElement element, D data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitBinaryLogicExpression(FirBinaryLogicExpression firBinaryLogicExpression, Object obj) {
            visitBinaryLogicExpression2(firBinaryLogicExpression, obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, Object obj) {
            visitElement2(firElement, obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitWhenBranch(FirWhenBranch firWhenBranch, Object obj) {
            visitWhenBranch2(firWhenBranch, obj);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitWhenExpression(FirWhenExpression firWhenExpression, Object obj) {
            visitWhenExpression2(firWhenExpression, obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhenExpression, reason: avoid collision after fix types in other method */
        public void visitWhenExpression2(FirWhenExpression whenExpression, D data) {
            Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
            Intrinsics.checkNotNullParameter(data, "data");
            Iterator<T> it = whenExpression.getBranches().iterator();
            while (it.hasNext()) {
                ((FirWhenBranch) it.next()).accept(this, data);
            }
        }

        /* JADX INFO: renamed from: visitWhenBranch, reason: avoid collision after fix types in other method */
        public void visitWhenBranch2(FirWhenBranch whenBranch, D data) {
            Intrinsics.checkNotNullParameter(whenBranch, "whenBranch");
            Intrinsics.checkNotNullParameter(data, "data");
            whenBranch.getCondition().accept(this, data);
        }

        /* JADX INFO: renamed from: visitBinaryLogicExpression, reason: avoid collision after fix types in other method */
        public void visitBinaryLogicExpression2(FirBinaryLogicExpression binaryLogicExpression, D data) {
            Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
            Intrinsics.checkNotNullParameter(data, "data");
            if (binaryLogicExpression.getKind() == LogicOperationKind.OR) {
                binaryLogicExpression.acceptChildren(this, data);
            }
        }
    }
}
