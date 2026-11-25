package org.jetbrains.kotlin.contracts.model.visitors;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.structure.CallComputation;
import org.jetbrains.kotlin.contracts.model.structure.ESAnd;
import org.jetbrains.kotlin.contracts.model.structure.ESBooleanType;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESEqual;
import org.jetbrains.kotlin.contracts.model.structure.ESIs;
import org.jetbrains.kotlin.contracts.model.structure.ESNot;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESReceiver;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;

/* JADX INFO: compiled from: Substitutor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B)\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020\u0005H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/visitors/Substitutor;", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "substitutions", "", "Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;)V", "visitAnd", "and", "Lorg/jetbrains/kotlin/contracts/model/structure/ESAnd;", "visitConstant", "esConstant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "visitEqual", "equal", "Lorg/jetbrains/kotlin/contracts/model/structure/ESEqual;", "visitIs", "isOperator", "Lorg/jetbrains/kotlin/contracts/model/structure/ESIs;", "visitLambda", "lambda", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "visitNot", "not", "Lorg/jetbrains/kotlin/contracts/model/structure/ESNot;", "visitOr", "or", "Lorg/jetbrains/kotlin/contracts/model/structure/ESOr;", "visitReceiver", "Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiver;", "esReceiver", "visitVariable", "esVariable", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Substitutor implements ESExpressionVisitor<Computation> {
    private final Reducer reducer;
    private final Map<ESVariable, Computation> substitutions;
    private final ESTypeSubstitution typeSubstitution;

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitReceiver(ESReceiver esReceiver) {
        Intrinsics.checkNotNullParameter(esReceiver, "esReceiver");
        return esReceiver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Substitutor(Map<ESVariable, ? extends Computation> substitutions, ESTypeSubstitution typeSubstitution, Reducer reducer) {
        Intrinsics.checkNotNullParameter(substitutions, "substitutions");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(reducer, "reducer");
        this.substitutions = substitutions;
        this.typeSubstitution = typeSubstitution;
        this.reducer = reducer;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitIs(ESIs isOperator) {
        Intrinsics.checkNotNullParameter(isOperator, "isOperator");
        Computation computation = (Computation) isOperator.getLeft().accept(this);
        if (computation == null) {
            return null;
        }
        return new CallComputation(ESBooleanType.INSTANCE, isOperator.getFunctor().invokeWithArguments(computation, this.typeSubstitution));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitNot(ESNot not) {
        Intrinsics.checkNotNullParameter(not, "not");
        Computation computation = (Computation) not.getArg().accept(this);
        if (computation == null) {
            return null;
        }
        return new CallComputation(ESBooleanType.INSTANCE, not.getFunctor().invokeWithArguments(computation));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitEqual(ESEqual equal) {
        Computation computation;
        Intrinsics.checkNotNullParameter(equal, "equal");
        Substitutor substitutor = this;
        Computation computation2 = (Computation) equal.getLeft().accept(substitutor);
        if (computation2 == null || (computation = (Computation) equal.getRight().accept(substitutor)) == null) {
            return null;
        }
        return new CallComputation(ESBooleanType.INSTANCE, equal.getFunctor().invokeWithArguments(CollectionsKt.listOf((Object[]) new Computation[]{computation2, computation}), this.typeSubstitution, this.reducer));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitAnd(ESAnd and) {
        Computation computation;
        Intrinsics.checkNotNullParameter(and, "and");
        Substitutor substitutor = this;
        Computation computation2 = (Computation) and.getLeft().accept(substitutor);
        if (computation2 == null || (computation = (Computation) and.getRight().accept(substitutor)) == null) {
            return null;
        }
        return new CallComputation(ESBooleanType.INSTANCE, and.getFunctor().invokeWithArguments(computation2, computation));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitOr(ESOr or) {
        Computation computation;
        Intrinsics.checkNotNullParameter(or, "or");
        Substitutor substitutor = this;
        Computation computation2 = (Computation) or.getLeft().accept(substitutor);
        if (computation2 == null || (computation = (Computation) or.getRight().accept(substitutor)) == null) {
            return null;
        }
        return new CallComputation(ESBooleanType.INSTANCE, or.getFunctor().invokeWithArguments(computation2, computation));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitVariable(ESVariable esVariable) {
        Intrinsics.checkNotNullParameter(esVariable, "esVariable");
        Computation computation = this.substitutions.get(esVariable);
        return computation == null ? esVariable : computation;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitConstant(ESConstant esConstant) {
        Intrinsics.checkNotNullParameter(esConstant, "esConstant");
        return esConstant;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public Computation visitLambda(ESValue lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        return lambda;
    }
}
