package org.jetbrains.kotlin.backend.common.linkage.partial;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: PartiallyLinkedMarkerType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0096\u0002J\b\u0010%\u001a\u00020&H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedMarkerType;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "unusableClassifier", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;)V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "arguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getUnusableClassifier", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartiallyLinkedMarkerType extends IrSimpleType {
    private final IrClassSymbol classifier;
    private final ExploredClassifier.Unusable unusableClassifier;

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrTypeAbbreviation getAbbreviation() {
        return null;
    }

    public final ExploredClassifier.Unusable getUnusableClassifier() {
        return this.unusableClassifier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PartiallyLinkedMarkerType(IrBuiltIns builtIns, ExploredClassifier.Unusable unusableClassifier) {
        super(null);
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(unusableClassifier, "unusableClassifier");
        this.unusableClassifier = unusableClassifier;
        this.classifier = builtIns.getAnyClass();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrClassSymbol getClassifier() {
        return this.classifier;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public SimpleTypeNullability getNullability() {
        return SimpleTypeNullability.MARKED_NULLABLE;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrSimpleType
    public List<IrTypeArgument> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public Variance getVariance() {
        return Variance.INVARIANT;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        PartiallyLinkedMarkerType partiallyLinkedMarkerType = other instanceof PartiallyLinkedMarkerType ? (PartiallyLinkedMarkerType) other : null;
        return Intrinsics.areEqual(partiallyLinkedMarkerType != null ? partiallyLinkedMarkerType.unusableClassifier : null, this.unusableClassifier);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return this.unusableClassifier.hashCode();
    }
}
