package org.jetbrains.kotlin.backend.common.lower.inline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.NullDescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.util.TypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DeepCopyIrTreeWithDescriptors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\n\b\u0000\u0018\u00002\u00020\u0001:\u0002\u0017\u0018B'\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner;", "", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "copier", "org/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$copier$1", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$copier$1;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "symbolRemapper", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$SymbolRemapperImpl;", "getTypeArguments", "()Ljava/util/Map;", "typeRemapper", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$InlinerTypeRemapper;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/ir/IrElement;", "irElement", "InlinerTypeRemapper", "SymbolRemapperImpl", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeepCopyIrTreeWithSymbolsForInliner {
    private final DeepCopyIrTreeWithSymbolsForInliner$copier$1 copier;
    private final IrDeclarationParent parent;
    private final SymbolRemapperImpl symbolRemapper;
    private final Map<IrTypeParameterSymbol, IrType> typeArguments;
    private final InlinerTypeRemapper typeRemapper;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.jetbrains.kotlin.backend.common.lower.inline.DeepCopyIrTreeWithSymbolsForInliner$copier$1] */
    public DeepCopyIrTreeWithSymbolsForInliner(Map<IrTypeParameterSymbol, ? extends IrType> map, IrDeclarationParent irDeclarationParent) {
        this.typeArguments = map;
        this.parent = irDeclarationParent;
        final SymbolRemapperImpl symbolRemapperImpl = new SymbolRemapperImpl(NullDescriptorsRemapper.INSTANCE);
        this.symbolRemapper = symbolRemapperImpl;
        final InlinerTypeRemapper inlinerTypeRemapper = new InlinerTypeRemapper(this, symbolRemapperImpl, map);
        this.typeRemapper = inlinerTypeRemapper;
        this.copier = new DeepCopyIrTreeWithSymbols(symbolRemapperImpl, inlinerTypeRemapper) { // from class: org.jetbrains.kotlin.backend.common.lower.inline.DeepCopyIrTreeWithSymbolsForInliner$copier$1
            {
                super(symbolRemapperImpl, inlinerTypeRemapper);
            }

            private final IrType remapTypeAndErase(IrType irType) {
                return this.this$0.typeRemapper.remapTypeAndOptionallyErase(irType, true);
            }

            @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrTypeOperatorCallImpl visitTypeOperator(IrTypeOperatorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                int startOffset = expression.getStartOffset();
                int endOffset = expression.getEndOffset();
                IrType irTypeRemapTypeAndErase = remapTypeAndErase(expression.getType());
                IrTypeOperator operator = expression.getOperator();
                IrType irTypeRemapTypeAndErase2 = remapTypeAndErase(expression.getTypeOperand());
                IrElement irElementTransform = expression.getArgument().transform((IrElementTransformer<? super Object>) this, (Object) null);
                if (irElementTransform != null) {
                    return (IrTypeOperatorCallImpl) IrDeclarationsKt.copyAttributes(new IrTypeOperatorCallImpl(startOffset, endOffset, irTypeRemapTypeAndErase, operator, irTypeRemapTypeAndErase2, (IrExpression) irElementTransform), expression);
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
        };
    }

    public final Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
        return this.typeArguments;
    }

    public final IrDeclarationParent getParent() {
        return this.parent;
    }

    public final IrElement copy(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        IrVisitorsKt.acceptVoid(irElement, this.symbolRemapper);
        this.symbolRemapper.setTypeArguments(this.typeArguments);
        IrElement irElementTransform = irElement.transform(this.copier, null);
        PatchDeclarationParentsKt.patchDeclarationParents(irElementTransform, this.parent);
        return irElementTransform;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeepCopyIrTreeWithDescriptors.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\u0016\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016J\"\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u00072\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0018H\u0002J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0018H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$InlinerTypeRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner;Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Ljava/util/Map;)V", "getSymbolRemapper", "()Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "getTypeArguments", "()Ljava/util/Map;", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", ModuleXmlParser.TYPE, "remapTypeAndOptionallyErase", "erase", "", "erasedParameters", "", "remapTypeArguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "arguments", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class InlinerTypeRemapper implements TypeRemapper {
        private final SymbolRemapper symbolRemapper;
        final /* synthetic */ DeepCopyIrTreeWithSymbolsForInliner this$0;
        private final Map<IrTypeParameterSymbol, IrType> typeArguments;

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public void enterScope(IrTypeParametersContainer irTypeParametersContainer) {
            Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
        }

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public void leaveScope() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public InlinerTypeRemapper(DeepCopyIrTreeWithSymbolsForInliner deepCopyIrTreeWithSymbolsForInliner, SymbolRemapper symbolRemapper, Map<IrTypeParameterSymbol, ? extends IrType> map) {
            Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
            this.this$0 = deepCopyIrTreeWithSymbolsForInliner;
            this.symbolRemapper = symbolRemapper;
            this.typeArguments = map;
        }

        public final SymbolRemapper getSymbolRemapper() {
            return this.symbolRemapper;
        }

        public final Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
            return this.typeArguments;
        }

        private final List<IrTypeArgument> remapTypeArguments(List<? extends IrTypeArgument> arguments, Set<IrTypeParameterSymbol> erasedParameters) {
            IrStarProjectionImpl irStarProjectionImpl;
            IrTypeProjection irTypeProjectionMakeTypeProjection;
            List<? extends IrTypeArgument> list = arguments;
            ArrayList arrayList = new ArrayList(list.size());
            for (IrTypeArgument irTypeArgument : list) {
                IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
                if (irTypeProjection != null) {
                    IrType irTypeRemapTypeAndOptionallyErase = remapTypeAndOptionallyErase(irTypeProjection.getType(), erasedParameters);
                    if (irTypeRemapTypeAndOptionallyErase != null && (irTypeProjectionMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(irTypeRemapTypeAndOptionallyErase, irTypeProjection.getVariance())) != null) {
                        irStarProjectionImpl = irTypeProjectionMakeTypeProjection;
                    } else {
                        irStarProjectionImpl = IrStarProjectionImpl.INSTANCE;
                    }
                    if (irStarProjectionImpl != null) {
                        irTypeArgument = irStarProjectionImpl;
                    }
                }
                arrayList.add(irTypeArgument);
            }
            return CollectionsKt.compactIfPossible(arrayList);
        }

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public IrType remapType(IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return remapTypeAndOptionallyErase(type, false);
        }

        public final IrType remapTypeAndOptionallyErase(IrType type, boolean erase) {
            Intrinsics.checkNotNullParameter(type, "type");
            IrType irTypeRemapTypeAndOptionallyErase = remapTypeAndOptionallyErase(type, erase ? new LinkedHashSet() : null);
            if (irTypeRemapTypeAndOptionallyErase != null) {
                return irTypeRemapTypeAndOptionallyErase;
            }
            throw new IllegalStateException(("Cannot substitute type " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final IrType remapTypeAndOptionallyErase(IrType type, Set<IrTypeParameterSymbol> erasedParameters) {
            IrTypeParameter owner;
            Object next;
            IrClass owner2;
            if (!(type instanceof IrSimpleType)) {
                return type;
            }
            IrSimpleType irSimpleType = (IrSimpleType) type;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            Map<IrTypeParameterSymbol, IrType> map = this.typeArguments;
            IrType irType = map != null ? map.get(classifier) : null;
            if (erasedParameters != 0 && irType != null) {
                IrTypeParameterSymbol irTypeParameterSymbol = classifier instanceof IrTypeParameterSymbol ? (IrTypeParameterSymbol) classifier : null;
                if (irTypeParameterSymbol != null && (owner = irTypeParameterSymbol.getOwner()) != null && !owner.getIsReified()) {
                    if (erasedParameters.contains(classifier)) {
                        return null;
                    }
                    erasedParameters.add(classifier);
                    List<IrType> superTypes = ((IrTypeParameterSymbol) classifier).getOwner().getSuperTypes();
                    Iterator<T> it = superTypes.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) next);
                        if (classOrNull != null && (owner2 = classOrNull.getOwner()) != null && !IrUtilsKt.isInterface(owner2)) {
                            break;
                        }
                    }
                    IrType irType2 = (IrType) next;
                    if (irType2 == null) {
                        irType2 = (IrType) kotlin.collections.CollectionsKt.first((List) superTypes);
                    }
                    IrType irTypeRemapTypeAndOptionallyErase = remapTypeAndOptionallyErase(irType2, erasedParameters);
                    if (irTypeRemapTypeAndOptionallyErase == null) {
                        throw new IllegalStateException(("Cannot erase upperbound " + RenderIrElementKt.render$default(irType2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    erasedParameters.remove(classifier);
                    return IrTypesKt.mergeNullability(irTypeRemapTypeAndOptionallyErase, type);
                }
            }
            if (irType instanceof IrDynamicType) {
                return irType;
            }
            if (irType instanceof IrSimpleType) {
                return IrTypesKt.mergeNullability(irType, type);
            }
            DeepCopyIrTreeWithSymbolsForInliner deepCopyIrTreeWithSymbolsForInliner = this.this$0;
            IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
            builder.setKotlinType(null);
            builder.setClassifier(this.symbolRemapper.getReferencedClassifier(classifier));
            builder.setArguments(remapTypeArguments(irSimpleType.getArguments(), erasedParameters));
            List<IrConstructorCall> annotations = type.getAnnotations();
            ArrayList arrayList = new ArrayList(annotations.size());
            Iterator<T> it2 = annotations.iterator();
            while (it2.hasNext()) {
                IrExpression irExpressionTransform = ((IrConstructorCall) it2.next()).transform((IrElementTransformer) deepCopyIrTreeWithSymbolsForInliner.copier, (Object) null);
                Intrinsics.checkNotNull(irExpressionTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                arrayList.add((IrConstructorCall) irExpressionTransform);
            }
            builder.setAnnotations(CollectionsKt.compactIfPossible(arrayList));
            return IrSimpleTypeImplKt.buildSimpleType(builder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeepCopyIrTreeWithDescriptors.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016RD\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00062\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0006@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/DeepCopyIrTreeWithSymbolsForInliner$SymbolRemapperImpl;", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "descriptorsRemapper", "Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "(Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;)V", "value", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "typeArguments", "getTypeArguments", "()Ljava/util/Map;", "setTypeArguments", "(Ljava/util/Map;)V", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", NativeSymbol.TYPE_NAME, "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class SymbolRemapperImpl extends DeepCopySymbolRemapper {
        private Map<IrTypeParameterSymbol, ? extends IrType> typeArguments;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SymbolRemapperImpl(DescriptorsRemapper descriptorsRemapper) {
            super(descriptorsRemapper);
            Intrinsics.checkNotNullParameter(descriptorsRemapper, "descriptorsRemapper");
        }

        public final Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
            return this.typeArguments;
        }

        public final void setTypeArguments(Map<IrTypeParameterSymbol, ? extends IrType> map) {
            LinkedHashMap linkedHashMap;
            Sequence<Map.Entry> sequenceAsSequence;
            if (this.typeArguments != null) {
                return;
            }
            if (map == null || (sequenceAsSequence = MapsKt.asSequence(map)) == null) {
                linkedHashMap = null;
            } else {
                linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : sequenceAsSequence) {
                    IrClassifierSymbol referencedClassifier = getReferencedClassifier((IrClassifierSymbol) entry.getKey());
                    Intrinsics.checkNotNull(referencedClassifier, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
                    Pair pair = TuplesKt.to((IrTypeParameterSymbol) referencedClassifier, entry.getValue());
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
            }
            this.typeArguments = linkedHashMap;
        }

        @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
            Map<IrTypeParameterSymbol, ? extends IrType> map;
            IrType irType;
            IrClassifierSymbol classifierOrNull;
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            IrClassifierSymbol referencedClassifier = super.getReferencedClassifier(symbol);
            return (!(referencedClassifier instanceof IrTypeParameterSymbol) || (map = this.typeArguments) == null || (irType = map.get(referencedClassifier)) == null || (classifierOrNull = IrTypesKt.getClassifierOrNull(irType)) == null) ? referencedClassifier : classifierOrNull;
        }
    }
}
