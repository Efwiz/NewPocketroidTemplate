package org.jetbrains.kotlin.contracts.model.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.structure.ESAnd;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESEqual;
import org.jetbrains.kotlin.contracts.model.structure.ESIs;
import org.jetbrains.kotlin.contracts.model.structure.ESNot;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESReceiver;
import org.jetbrains.kotlin.contracts.model.structure.ESType;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: Reducer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0002J\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\nJ\u0012\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "reduceEffect", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "effect", "reduceEffects", "", "schema", "visitAnd", "and", "Lorg/jetbrains/kotlin/contracts/model/structure/ESAnd;", "visitConstant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "esConstant", "visitEqual", "equal", "Lorg/jetbrains/kotlin/contracts/model/structure/ESEqual;", "visitIs", "isOperator", "Lorg/jetbrains/kotlin/contracts/model/structure/ESIs;", "visitLambda", "lambda", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "visitNot", "not", "Lorg/jetbrains/kotlin/contracts/model/structure/ESNot;", "visitOr", "or", "Lorg/jetbrains/kotlin/contracts/model/structure/ESOr;", "visitReceiver", "Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiver;", "esReceiver", "visitVariable", "Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "esVariable", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Reducer implements ESExpressionVisitor<ESExpression> {
    private final KotlinBuiltIns builtIns;

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitConstant(ESConstant esConstant) {
        Intrinsics.checkNotNullParameter(esConstant, "esConstant");
        return esConstant;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitLambda(ESValue lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        return null;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitReceiver(ESReceiver esReceiver) {
        Intrinsics.checkNotNullParameter(esReceiver, "esReceiver");
        return esReceiver;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitVariable(ESVariable esVariable) {
        Intrinsics.checkNotNullParameter(esVariable, "esVariable");
        return esVariable;
    }

    public Reducer(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.builtIns = builtIns;
    }

    public final List<ESEffect> reduceEffects(List<? extends ESEffect> schema) {
        Intrinsics.checkNotNullParameter(schema, "schema");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = schema.iterator();
        while (it.hasNext()) {
            ESEffect eSEffectReduceEffect = reduceEffect((ESEffect) it.next());
            if (eSEffectReduceEffect != null) {
                arrayList.add(eSEffectReduceEffect);
            }
        }
        return arrayList;
    }

    private final ESEffect reduceEffect(ESEffect effect) {
        if (!(effect instanceof ConditionalEffect)) {
            return effect;
        }
        ConditionalEffect conditionalEffect = (ConditionalEffect) effect;
        ESExpression eSExpression = (ESExpression) conditionalEffect.getCondition().accept(this);
        if (eSExpression == null || ValuesKt.isFalse(eSExpression)) {
            return null;
        }
        return ValuesKt.isTrue(eSExpression) ? conditionalEffect.getSimpleEffect() : effect;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitIs(ESIs isOperator) {
        Intrinsics.checkNotNullParameter(isOperator, "isOperator");
        Object objAccept = isOperator.getLeft().accept(this);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.model.ESValue");
        ESValue eSValue = (ESValue) objAccept;
        ESType type = eSValue.getType();
        Boolean boolValueOf = null;
        KotlinType kotlinType = type != null ? type.toKotlinType(this.builtIns) : null;
        KotlinType kotlinType2 = isOperator.getFunctor().getType().toKotlinType(this.builtIns);
        if (eSValue instanceof ESConstant) {
            Intrinsics.checkNotNull(kotlinType);
            boolValueOf = Boolean.valueOf(TypeUtilsKt.isSubtypeOf(kotlinType, kotlinType2));
        } else {
            if (!(eSValue instanceof ESVariable ? true : eSValue instanceof ESReceiver)) {
                throw new IllegalStateException("Unknown ESValue: " + eSValue);
            }
            boolean z = false;
            if (kotlinType != null && TypeUtilsKt.isSubtypeOf(kotlinType, kotlinType2)) {
                z = true;
            }
            if (z) {
                boolValueOf = true;
            }
        }
        if (boolValueOf != null) {
            boolValueOf.booleanValue();
            return ESConstants.INSTANCE.booleanValue(isOperator.getFunctor().getIsNegated() ^ boolValueOf.booleanValue());
        }
        return new ESIs(eSValue, isOperator.getFunctor());
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitEqual(ESEqual equal) {
        Intrinsics.checkNotNullParameter(equal, "equal");
        ESValue eSValue = (ESValue) equal.getLeft().accept(this);
        if (eSValue == null) {
            return null;
        }
        ESValue right = equal.getRight();
        if (!(eSValue instanceof ESConstant)) {
            return new ESEqual(eSValue, right, equal.getFunctor().getIsNegated());
        }
        return ESConstants.INSTANCE.booleanValue(equal.getFunctor().getIsNegated() ^ Intrinsics.areEqual(eSValue, right));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitAnd(ESAnd and) {
        ESExpression eSExpression;
        Intrinsics.checkNotNullParameter(and, "and");
        Reducer reducer = this;
        ESExpression eSExpression2 = (ESExpression) and.getLeft().accept(reducer);
        if (eSExpression2 == null || (eSExpression = (ESExpression) and.getRight().accept(reducer)) == null) {
            return null;
        }
        if (ValuesKt.isFalse(eSExpression2) || ValuesKt.isFalse(eSExpression)) {
            return eSExpression2;
        }
        if (ValuesKt.isTrue(eSExpression2)) {
            return eSExpression;
        }
        return ValuesKt.isTrue(eSExpression) ? eSExpression2 : new ESAnd(eSExpression2, eSExpression);
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitOr(ESOr or) {
        ESExpression eSExpression;
        Intrinsics.checkNotNullParameter(or, "or");
        Reducer reducer = this;
        ESExpression eSExpression2 = (ESExpression) or.getLeft().accept(reducer);
        if (eSExpression2 == null || (eSExpression = (ESExpression) or.getRight().accept(reducer)) == null) {
            return null;
        }
        if (ValuesKt.isTrue(eSExpression2) || ValuesKt.isTrue(eSExpression)) {
            return eSExpression2;
        }
        if (ValuesKt.isFalse(eSExpression2)) {
            return eSExpression;
        }
        return ValuesKt.isFalse(eSExpression) ? eSExpression2 : new ESOr(eSExpression2, eSExpression);
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public ESExpression visitNot(ESNot not) {
        Intrinsics.checkNotNullParameter(not, "not");
        ESExpression eSExpression = (ESExpression) not.getArg().accept(this);
        if (eSExpression == null) {
            return null;
        }
        if (ValuesKt.isTrue(eSExpression)) {
            return ESConstants.INSTANCE.getFalseValue();
        }
        return ValuesKt.isFalse(eSExpression) ? ESConstants.INSTANCE.getTrueValue() : eSExpression;
    }
}
