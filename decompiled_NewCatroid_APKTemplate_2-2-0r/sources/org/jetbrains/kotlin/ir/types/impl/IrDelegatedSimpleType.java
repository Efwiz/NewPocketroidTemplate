package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrDelegatedSimpleType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrAbstractSimpleType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "arguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getDelegate", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDelegatedSimpleType extends IrAbstractSimpleType {
    /* JADX WARN: Multi-variable type inference failed */
    public IrDelegatedSimpleType() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    protected abstract IrSimpleType getDelegate();

    public IrDelegatedSimpleType(KotlinType kotlinType) {
        super(kotlinType);
    }

    public /* synthetic */ IrDelegatedSimpleType(KotlinType kotlinType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : kotlinType);
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrClassifierSymbol getClassifier() {
        return getDelegate().getClassifier();
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public SimpleTypeNullability getNullability() {
        return getDelegate().getNullability();
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public List<IrTypeArgument> getArguments() {
        return getDelegate().getArguments();
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrTypeAbbreviation getAbbreviation() {
        return getDelegate().getAbbreviation();
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return getDelegate().getAnnotations();
    }
}
