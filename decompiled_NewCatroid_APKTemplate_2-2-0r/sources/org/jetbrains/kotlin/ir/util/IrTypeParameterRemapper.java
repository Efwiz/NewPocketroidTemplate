package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrTypeAbbreviationImpl;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrTypeParameterRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\f\u0010\u000e\u001a\u00020\u000f*\u00020\u000fH\u0002J\f\u0010\u000e\u001a\u00020\u0010*\u00020\u0011H\u0002J\f\u0010\u000e\u001a\u00020\u0012*\u00020\u0012H\u0002R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrTypeParameterRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "typeParameterMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(Ljava/util/Map;)V", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "remap", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationImpl;", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeParameterRemapper implements TypeRemapper {
    private final Map<IrTypeParameter, IrTypeParameter> typeParameterMap;

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void enterScope(IrTypeParametersContainer irTypeParametersContainer) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void leaveScope() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrTypeParameterRemapper(Map<IrTypeParameter, ? extends IrTypeParameter> typeParameterMap) {
        Intrinsics.checkNotNullParameter(typeParameterMap, "typeParameterMap");
        this.typeParameterMap = typeParameterMap;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public IrType remapType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof IrSimpleType)) {
            return type;
        }
        IrSimpleType irSimpleType = (IrSimpleType) type;
        IrClassifierSymbol irClassifierSymbolRemap = remap(irSimpleType.getClassifier());
        SimpleTypeNullability nullability = irSimpleType.getNullability();
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(remap((IrTypeArgument) it.next()));
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        List<IrConstructorCall> annotations = type.getAnnotations();
        IrTypeAbbreviation abbreviation = irSimpleType.getAbbreviation();
        IrSimpleTypeImpl irSimpleTypeImpl = new IrSimpleTypeImpl(null, irClassifierSymbolRemap, nullability, listCompactIfPossible, annotations, abbreviation != null ? remap(abbreviation) : null);
        Iterator<T> it2 = irSimpleTypeImpl.getAnnotations().iterator();
        while (it2.hasNext()) {
            RemapTypesKt.remapTypes((IrConstructorCall) it2.next(), this);
        }
        return irSimpleTypeImpl;
    }

    private final IrClassifierSymbol remap(IrClassifierSymbol irClassifierSymbol) {
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        IrTypeParameter irTypeParameter = owner instanceof IrTypeParameter ? (IrTypeParameter) owner : null;
        if (irTypeParameter == null) {
            return irClassifierSymbol;
        }
        IrTypeParameter irTypeParameter2 = this.typeParameterMap.get(irTypeParameter);
        IrTypeParameterSymbol symbol = irTypeParameter2 != null ? irTypeParameter2.getSymbol() : null;
        return symbol != null ? symbol : irClassifierSymbol;
    }

    private final IrTypeArgument remap(IrTypeArgument irTypeArgument) {
        if (irTypeArgument instanceof IrTypeProjection) {
            IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
            return IrSimpleTypeImplKt.makeTypeProjection(remapType(irTypeProjection.getType()), irTypeProjection.getVariance());
        }
        if (irTypeArgument instanceof IrStarProjection) {
            return irTypeArgument;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrTypeAbbreviationImpl remap(IrTypeAbbreviation irTypeAbbreviation) {
        IrTypeAliasSymbol typeAlias = irTypeAbbreviation.getTypeAlias();
        boolean hasQuestionMark = irTypeAbbreviation.getHasQuestionMark();
        List<IrTypeArgument> arguments = irTypeAbbreviation.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(remap((IrTypeArgument) it.next()));
        }
        IrTypeAbbreviationImpl irTypeAbbreviationImpl = new IrTypeAbbreviationImpl(typeAlias, hasQuestionMark, CollectionsKt.compactIfPossible(arrayList), irTypeAbbreviation.getAnnotations());
        Iterator<T> it2 = irTypeAbbreviationImpl.getAnnotations().iterator();
        while (it2.hasNext()) {
            RemapTypesKt.remapTypes((IrConstructorCall) it2.next(), this);
        }
        return irTypeAbbreviationImpl;
    }
}
