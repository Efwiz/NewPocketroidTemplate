package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000fR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeBuilder;", "", "()V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "setAbbreviation", "(Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "arguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "setArguments", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "setClassifier", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)V", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "setKotlinType", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "setNullability", "(Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;)V", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "setVariance", "(Lorg/jetbrains/kotlin/types/Variance;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSimpleTypeBuilder {
    private IrTypeAbbreviation abbreviation;
    private IrClassifierSymbol classifier;
    private KotlinType kotlinType;
    private SimpleTypeNullability nullability = SimpleTypeNullability.NOT_SPECIFIED;
    private List<? extends IrTypeArgument> arguments = CollectionsKt.emptyList();
    private List<? extends IrConstructorCall> annotations = CollectionsKt.emptyList();
    private Variance variance = Variance.INVARIANT;

    public final KotlinType getKotlinType() {
        return this.kotlinType;
    }

    public final void setKotlinType(KotlinType kotlinType) {
        this.kotlinType = kotlinType;
    }

    public final IrClassifierSymbol getClassifier() {
        return this.classifier;
    }

    public final void setClassifier(IrClassifierSymbol irClassifierSymbol) {
        this.classifier = irClassifierSymbol;
    }

    public final SimpleTypeNullability getNullability() {
        return this.nullability;
    }

    public final void setNullability(SimpleTypeNullability simpleTypeNullability) {
        Intrinsics.checkNotNullParameter(simpleTypeNullability, "<set-?>");
        this.nullability = simpleTypeNullability;
    }

    public final List<IrTypeArgument> getArguments() {
        return this.arguments;
    }

    public final void setArguments(List<? extends IrTypeArgument> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.arguments = list;
    }

    public final List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    public final void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }

    public final IrTypeAbbreviation getAbbreviation() {
        return this.abbreviation;
    }

    public final void setAbbreviation(IrTypeAbbreviation irTypeAbbreviation) {
        this.abbreviation = irTypeAbbreviation;
    }

    public final Variance getVariance() {
        return this.variance;
    }

    public final void setVariance(Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "<set-?>");
        this.variance = variance;
    }
}
