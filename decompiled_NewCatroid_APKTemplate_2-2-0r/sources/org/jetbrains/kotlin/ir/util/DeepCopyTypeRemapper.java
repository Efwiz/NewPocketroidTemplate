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
import org.jetbrains.kotlin.ir.expressions.IrExpression;
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
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: DeepCopyTypeRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeepCopyTypeRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;)V", "deepCopy", "Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "getDeepCopy", "()Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "setDeepCopy", "(Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;)V", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "remapTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "typeArgument", "remapTypeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeAbbreviationImpl;", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeepCopyTypeRemapper implements TypeRemapper {
    public DeepCopyIrTreeWithSymbols deepCopy;
    private final SymbolRemapper symbolRemapper;

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void enterScope(IrTypeParametersContainer irTypeParametersContainer) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public void leaveScope() {
    }

    public DeepCopyTypeRemapper(SymbolRemapper symbolRemapper) {
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        this.symbolRemapper = symbolRemapper;
    }

    public final DeepCopyIrTreeWithSymbols getDeepCopy() {
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this.deepCopy;
        if (deepCopyIrTreeWithSymbols != null) {
            return deepCopyIrTreeWithSymbols;
        }
        Intrinsics.throwUninitializedPropertyAccessException("deepCopy");
        return null;
    }

    public final void setDeepCopy(DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols) {
        Intrinsics.checkNotNullParameter(deepCopyIrTreeWithSymbols, "<set-?>");
        this.deepCopy = deepCopyIrTreeWithSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
    public IrType remapType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof IrSimpleType)) {
            return type;
        }
        IrSimpleType irSimpleType = (IrSimpleType) type;
        IrClassifierSymbol referencedClassifier = this.symbolRemapper.getReferencedClassifier(irSimpleType.getClassifier());
        SimpleTypeNullability nullability = irSimpleType.getNullability();
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(remapTypeArgument((IrTypeArgument) it.next()));
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        List<IrConstructorCall> annotations = type.getAnnotations();
        ArrayList arrayList2 = new ArrayList(annotations.size());
        Iterator<T> it2 = annotations.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            IrExpression irExpressionTransform = ((IrConstructorCall) it2.next()).transform((IrElementTransformer) getDeepCopy(), (Object) null);
            Intrinsics.checkNotNull(irExpressionTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            arrayList2.add((IrConstructorCall) irExpressionTransform);
        }
        List listCompactIfPossible2 = CollectionsKt.compactIfPossible(arrayList2);
        IrTypeAbbreviation abbreviation = irSimpleType.getAbbreviation();
        return new IrSimpleTypeImpl(null, referencedClassifier, nullability, listCompactIfPossible, listCompactIfPossible2, abbreviation != null ? remapTypeAbbreviation(abbreviation) : null);
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
