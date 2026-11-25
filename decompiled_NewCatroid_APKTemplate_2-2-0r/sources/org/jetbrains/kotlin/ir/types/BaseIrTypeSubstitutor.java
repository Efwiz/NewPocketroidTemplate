package org.jetbrains.kotlin.ir.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u000e\u0010\u0012\u001a\u0004\u0018\u00010\b*\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/BaseIrTypeSubstitutor;", "Lorg/jetbrains/kotlin/ir/types/AbstractIrTypeSubstitutor;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getSubstitutionArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "typeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "isEmptySubstitution", "", "substitute", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "substituteType", "irType", "substituteTypeArgument", "typeArgument", "typeParameterConstructor", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseIrTypeSubstitutor extends AbstractIrTypeSubstitutor {
    private final IrBuiltIns irBuiltIns;

    public abstract IrTypeArgument getSubstitutionArgument(IrTypeParameterSymbol typeParameter);

    public abstract boolean isEmptySubstitution();

    public BaseIrTypeSubstitutor(IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.irBuiltIns = irBuiltIns;
    }

    private final IrTypeParameterSymbol typeParameterConstructor(IrType irType) {
        if (!(irType instanceof IrSimpleType)) {
            return null;
        }
        IrClassifierSymbol classifier = ((IrSimpleType) irType).getClassifier();
        if (classifier instanceof IrTypeParameterSymbol) {
            return (IrTypeParameterSymbol) classifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.types.AbstractIrTypeSubstitutor
    public final IrType substitute(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return isEmptySubstitution() ? type : substituteType(type);
    }

    private final IrType substituteType(IrType irType) {
        IrType irTypeAddAnnotations;
        IrTypeParameterSymbol irTypeParameterSymbolTypeParameterConstructor = typeParameterConstructor(irType);
        if (irTypeParameterSymbolTypeParameterConstructor != null) {
            IrTypeArgument substitutionArgument = getSubstitutionArgument(irTypeParameterSymbolTypeParameterConstructor);
            if (substitutionArgument instanceof IrStarProjection) {
                irTypeAddAnnotations = this.irBuiltIns.getAnyNType();
            } else {
                if (!(substitutionArgument instanceof IrTypeProjection)) {
                    throw new NoWhenBranchMatchedException();
                }
                irTypeAddAnnotations = IrTypesKt.addAnnotations(IrTypesKt.mergeNullability(((IrTypeProjection) substitutionArgument).getType(), irType), irType.getAnnotations());
            }
        } else {
            irTypeAddAnnotations = null;
        }
        if (irTypeAddAnnotations != null) {
            return irTypeAddAnnotations;
        }
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
            List<IrTypeArgument> arguments = irSimpleType.getArguments();
            ArrayList arrayList = new ArrayList(arguments.size());
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                arrayList.add(substituteTypeArgument((IrTypeArgument) it.next()));
            }
            builder.setArguments(CollectionsKt.compactIfPossible(arrayList));
            return IrSimpleTypeImplKt.buildSimpleType(builder);
        }
        if (irType instanceof IrDynamicType ? true : irType instanceof IrErrorType) {
            return irType;
        }
        throw new IllegalStateException(("Unexpected type: " + irType).toString());
    }

    private final IrTypeArgument substituteTypeArgument(IrTypeArgument typeArgument) {
        if (typeArgument instanceof IrStarProjection) {
            return typeArgument;
        }
        if (!(typeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) typeArgument;
        return IrSimpleTypeImplKt.makeTypeProjection(substituteType(irTypeProjection.getType()), irTypeProjection.getVariance());
    }
}
