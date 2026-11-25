package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.FqNameEqualityChecker;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0096\u0002J\b\u0010!\u001a\u00020\"H\u0016R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrAbstractSimpleType;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "arguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrAbstractSimpleType extends IrSimpleType {
    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public abstract IrTypeAbbreviation getAbbreviation();

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public abstract List<IrConstructorCall> getAnnotations();

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public abstract List<IrTypeArgument> getArguments();

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public abstract IrClassifierSymbol getClassifier();

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public abstract SimpleTypeNullability getNullability();

    public IrAbstractSimpleType(KotlinType kotlinType) {
        super(kotlinType);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public Variance getVariance() {
        return Variance.INVARIANT;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        if (other instanceof IrAbstractSimpleType) {
            IrAbstractSimpleType irAbstractSimpleType = (IrAbstractSimpleType) other;
            if (FqNameEqualityChecker.INSTANCE.areEqual(getClassifier(), irAbstractSimpleType.getClassifier()) && getNullability() == irAbstractSimpleType.getNullability() && Intrinsics.areEqual(getArguments(), irAbstractSimpleType.getArguments())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return (((FqNameEqualityChecker.INSTANCE.getHashCode(getClassifier()) * 31) + getNullability().hashCode()) * 31) + getArguments().hashCode();
    }
}
