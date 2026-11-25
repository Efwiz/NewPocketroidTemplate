package org.jetbrains.kotlin.contracts.model.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.MutableContextInfo;
import org.jetbrains.kotlin.contracts.model.structure.ESAnd;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESEqual;
import org.jetbrains.kotlin.contracts.model.structure.ESIs;
import org.jetbrains.kotlin.contracts.model.structure.ESNot;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESReceiver;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;
import org.jetbrains.kotlin.contracts.model.structure.TypesKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: InfoCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u0014\u0010\f\u001a\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eJ!\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0012H\u0002¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00022\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/visitors/InfoCollector;", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo;", "observedEffect", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/contracts/model/ESEffect;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "isInverted", "", "collectFromEffect", "effect", "collectFromSchema", "schema", "", "inverted", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "visitAnd", "and", "Lorg/jetbrains/kotlin/contracts/model/structure/ESAnd;", "visitConstant", "esConstant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "visitEqual", "equal", "Lorg/jetbrains/kotlin/contracts/model/structure/ESEqual;", "visitIs", "isOperator", "Lorg/jetbrains/kotlin/contracts/model/structure/ESIs;", "visitLambda", "lambda", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "visitNot", "not", "Lorg/jetbrains/kotlin/contracts/model/structure/ESNot;", "visitOr", "or", "Lorg/jetbrains/kotlin/contracts/model/structure/ESOr;", "visitReceiver", "esReceiver", "Lorg/jetbrains/kotlin/contracts/model/structure/ESReceiver;", "visitVariable", "esVariable", "Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InfoCollector implements ESExpressionVisitor<MutableContextInfo> {
    private final KotlinBuiltIns builtIns;
    private boolean isInverted;
    private final ESEffect observedEffect;

    public InfoCollector(ESEffect observedEffect, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(observedEffect, "observedEffect");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.observedEffect = observedEffect;
        this.builtIns = builtIns;
    }

    public final MutableContextInfo collectFromSchema(List<? extends ESEffect> schema) {
        Intrinsics.checkNotNullParameter(schema, "schema");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = schema.iterator();
        while (it.hasNext()) {
            MutableContextInfo mutableContextInfoCollectFromEffect = collectFromEffect((ESEffect) it.next());
            if (mutableContextInfoCollectFromEffect != null) {
                arrayList.add(mutableContextInfoCollectFromEffect);
            }
        }
        MutableContextInfo empty = MutableContextInfo.INSTANCE.getEMPTY();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            empty = empty.and((MutableContextInfo) it2.next());
        }
        return empty;
    }

    private final MutableContextInfo collectFromEffect(ESEffect effect) {
        if (!(effect instanceof ConditionalEffect)) {
            return MutableContextInfo.INSTANCE.getEMPTY().fire(effect);
        }
        ConditionalEffect conditionalEffect = (ConditionalEffect) effect;
        Boolean boolIsImplies = this.observedEffect.isImplies(conditionalEffect.getSimpleEffect());
        if (Intrinsics.areEqual((Object) boolIsImplies, (Object) true)) {
            return (MutableContextInfo) conditionalEffect.getCondition().accept(this);
        }
        if (boolIsImplies != null ? Intrinsics.areEqual((Object) boolIsImplies, (Object) false) : true) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitIs(ESIs isOperator) {
        Intrinsics.checkNotNullParameter(isOperator, "isOperator");
        KotlinType kotlinType = isOperator.getType().toKotlinType(this.builtIns);
        return isOperator.getFunctor().getIsNegated() != this.isInverted ? MutableContextInfo.INSTANCE.getEMPTY().notSubtype(isOperator.getLeft(), kotlinType) : MutableContextInfo.INSTANCE.getEMPTY().subtype(isOperator.getLeft(), kotlinType);
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitEqual(ESEqual equal) {
        Intrinsics.checkNotNullParameter(equal, "equal");
        return equal.getFunctor().getIsNegated() != this.isInverted ? MutableContextInfo.INSTANCE.getEMPTY().notEqual(equal.getLeft(), equal.getRight()) : MutableContextInfo.INSTANCE.getEMPTY().equal(equal.getLeft(), equal.getRight());
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitAnd(ESAnd and) {
        Intrinsics.checkNotNullParameter(and, "and");
        InfoCollector infoCollector = this;
        MutableContextInfo mutableContextInfo = (MutableContextInfo) and.getLeft().accept(infoCollector);
        MutableContextInfo mutableContextInfo2 = (MutableContextInfo) and.getRight().accept(infoCollector);
        return this.isInverted ? mutableContextInfo.or(mutableContextInfo2) : mutableContextInfo.and(mutableContextInfo2);
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitNot(final ESNot not) {
        Intrinsics.checkNotNullParameter(not, "not");
        return (MutableContextInfo) inverted(new Function0<MutableContextInfo>() { // from class: org.jetbrains.kotlin.contracts.model.visitors.InfoCollector.visitNot.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MutableContextInfo invoke() {
                return (MutableContextInfo) not.getArg().accept(this);
            }
        });
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitOr(ESOr or) {
        Intrinsics.checkNotNullParameter(or, "or");
        InfoCollector infoCollector = this;
        MutableContextInfo mutableContextInfo = (MutableContextInfo) or.getLeft().accept(infoCollector);
        MutableContextInfo mutableContextInfo2 = (MutableContextInfo) or.getRight().accept(infoCollector);
        return this.isInverted ? mutableContextInfo.and(mutableContextInfo2) : mutableContextInfo.or(mutableContextInfo2);
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitVariable(ESVariable esVariable) {
        Intrinsics.checkNotNullParameter(esVariable, "esVariable");
        if (!TypesKt.isBoolean(esVariable.getType())) {
            return MutableContextInfo.INSTANCE.getEMPTY();
        }
        return MutableContextInfo.INSTANCE.getEMPTY().equal(esVariable, ESConstants.INSTANCE.booleanValue(!this.isInverted));
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitConstant(ESConstant esConstant) {
        Intrinsics.checkNotNullParameter(esConstant, "esConstant");
        return MutableContextInfo.INSTANCE.getEMPTY();
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitReceiver(ESReceiver esReceiver) {
        Intrinsics.checkNotNullParameter(esReceiver, "esReceiver");
        return MutableContextInfo.INSTANCE.getEMPTY();
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpressionVisitor
    public MutableContextInfo visitLambda(ESValue lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        return MutableContextInfo.INSTANCE.getEMPTY();
    }

    private final <R> R inverted(Function0<? extends R> block) {
        this.isInverted = !this.isInverted;
        R rInvoke = block.invoke();
        this.isInverted = !this.isInverted;
        return rInvoke;
    }
}
