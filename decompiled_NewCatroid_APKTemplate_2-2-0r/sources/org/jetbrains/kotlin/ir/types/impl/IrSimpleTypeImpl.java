package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B?\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB?\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u0010BG\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u0013R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeImpl;", "Lorg/jetbrains/kotlin/ir/types/impl/IrAbstractSimpleType;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "annotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;)V", "hasQuestionMark", "", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;ZLjava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;)V", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;)V", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSimpleTypeImpl extends IrAbstractSimpleType {
    private final IrTypeAbbreviation abbreviation;
    private final List<IrConstructorCall> annotations;
    private final List<IrTypeArgument> arguments;
    private final IrClassifierSymbol classifier;
    private final SimpleTypeNullability nullability;

    public /* synthetic */ IrSimpleTypeImpl(KotlinType kotlinType, IrClassifierSymbol irClassifierSymbol, SimpleTypeNullability simpleTypeNullability, List list, List list2, IrTypeAbbreviation irTypeAbbreviation, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinType, irClassifierSymbol, simpleTypeNullability, list, list2, (i & 32) != 0 ? null : irTypeAbbreviation);
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrClassifierSymbol getClassifier() {
        return this.classifier;
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public List<IrTypeArgument> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public IrTypeAbbreviation getAbbreviation() {
        return this.abbreviation;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IrSimpleTypeImpl(KotlinType kotlinType, IrClassifierSymbol classifier, SimpleTypeNullability nullability, List<? extends IrTypeArgument> arguments, List<? extends IrConstructorCall> annotations, IrTypeAbbreviation irTypeAbbreviation) {
        super(kotlinType);
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.classifier = classifier;
        this.arguments = arguments;
        this.annotations = annotations;
        this.abbreviation = irTypeAbbreviation;
        if (!(getClassifier() instanceof IrTypeParameterSymbol) && nullability == SimpleTypeNullability.NOT_SPECIFIED) {
            nullability = SimpleTypeNullability.DEFINITELY_NOT_NULL;
        }
        this.nullability = nullability;
    }

    @Override // org.jetbrains.kotlin.ir.types.impl.IrAbstractSimpleType, org.jetbrains.kotlin.ir.types.IrSimpleType
    public SimpleTypeNullability getNullability() {
        return this.nullability;
    }

    public /* synthetic */ IrSimpleTypeImpl(IrClassifierSymbol irClassifierSymbol, SimpleTypeNullability simpleTypeNullability, List list, List list2, IrTypeAbbreviation irTypeAbbreviation, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irClassifierSymbol, simpleTypeNullability, (List<? extends IrTypeArgument>) list, (List<? extends IrConstructorCall>) list2, (i & 16) != 0 ? null : irTypeAbbreviation);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrSimpleTypeImpl(IrClassifierSymbol classifier, SimpleTypeNullability nullability, List<? extends IrTypeArgument> arguments, List<? extends IrConstructorCall> annotations, IrTypeAbbreviation irTypeAbbreviation) {
        this(null, classifier, nullability, arguments, annotations, irTypeAbbreviation);
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
    }

    public /* synthetic */ IrSimpleTypeImpl(IrClassifierSymbol irClassifierSymbol, boolean z, List list, List list2, IrTypeAbbreviation irTypeAbbreviation, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irClassifierSymbol, z, (List<? extends IrTypeArgument>) list, (List<? extends IrConstructorCall>) list2, (i & 16) != 0 ? null : irTypeAbbreviation);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrSimpleTypeImpl(IrClassifierSymbol classifier, boolean z, List<? extends IrTypeArgument> arguments, List<? extends IrConstructorCall> annotations, IrTypeAbbreviation irTypeAbbreviation) {
        this(null, classifier, SimpleTypeNullability.INSTANCE.fromHasQuestionMark(z), arguments, annotations, irTypeAbbreviation);
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
    }
}
