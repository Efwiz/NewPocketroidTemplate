package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
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

/* JADX INFO: compiled from: SimpleTypeRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\f\u0010\u0010\u001a\u00020\u0011*\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SimpleTypeRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;)V", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "remapTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "typeArgument", "remapTypeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationImpl;", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleTypeRemapper implements TypeRemapper {
    private final SymbolRemapper symbolRemapper;

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void enterScope(IrTypeParametersContainer irTypeParametersContainer) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void leaveScope() {
    }

    public SimpleTypeRemapper(SymbolRemapper symbolRemapper) {
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        this.symbolRemapper = symbolRemapper;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public IrType remapType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof IrSimpleType)) {
            return type;
        }
        IrSimpleTypeImpl irSimpleTypeImpl = (IrSimpleType) type;
        IrClassifierSymbol referencedClassifier = this.symbolRemapper.getReferencedClassifier(irSimpleTypeImpl.getClassifier());
        List<IrTypeArgument> arguments = irSimpleTypeImpl.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(remapTypeArgument((IrTypeArgument) it.next()));
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        if (!Intrinsics.areEqual(referencedClassifier, irSimpleTypeImpl.getClassifier()) || !Intrinsics.areEqual(listCompactIfPossible, irSimpleTypeImpl.getArguments())) {
            SimpleTypeNullability nullability = irSimpleTypeImpl.getNullability();
            List<IrConstructorCall> annotations = type.getAnnotations();
            IrTypeAbbreviation abbreviation = irSimpleTypeImpl.getAbbreviation();
            irSimpleTypeImpl = new IrSimpleTypeImpl(null, referencedClassifier, nullability, listCompactIfPossible, annotations, abbreviation != null ? remapTypeAbbreviation(abbreviation) : null);
        }
        return irSimpleTypeImpl;
    }

    private final IrTypeArgument remapTypeArgument(IrTypeArgument typeArgument) {
        if (typeArgument instanceof IrTypeProjection) {
            IrTypeProjection irTypeProjection = (IrTypeProjection) typeArgument;
            return IrSimpleTypeImplKt.makeTypeProjection(remapType(irTypeProjection.getType()), irTypeProjection.getVariance());
        }
        if (typeArgument instanceof IrStarProjection) {
            return typeArgument;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrTypeAbbreviationImpl remapTypeAbbreviation(IrTypeAbbreviation irTypeAbbreviation) {
        IrTypeAliasSymbol referencedTypeAlias = this.symbolRemapper.getReferencedTypeAlias(irTypeAbbreviation.getTypeAlias());
        boolean hasQuestionMark = irTypeAbbreviation.getHasQuestionMark();
        List<IrTypeArgument> arguments = irTypeAbbreviation.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(remapTypeArgument((IrTypeArgument) it.next()));
        }
        return new IrTypeAbbreviationImpl(referencedTypeAlias, hasQuestionMark, CollectionsKt.compactIfPossible(arrayList), irTypeAbbreviation.getAnnotations());
    }
}
