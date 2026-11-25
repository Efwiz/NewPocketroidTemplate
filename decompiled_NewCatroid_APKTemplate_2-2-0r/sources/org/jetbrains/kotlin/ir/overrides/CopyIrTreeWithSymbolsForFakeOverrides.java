package org.jetbrains.kotlin.ir.overrides;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
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
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.NullDescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.util.SymbolRenamer;
import org.jetbrains.kotlin.ir.util.TypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CopyIrTreeWithSymbolsForFakeOverrides.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\u0012\u001a\u00020\u0003R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/CopyIrTreeWithSymbolsForFakeOverrides;", "", "overridableMember", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "unimplementedOverridesStrategy", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;)V", "copier", "Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideCopier;", "copierMakingExternal", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/overrides/CopyIrTreeWithSymbolsForFakeOverrides$FakeOverrideSymbolRemapperImpl;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "FakeOverrideSymbolRemapperImpl", "FakeOverrideTypeRemapper", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyIrTreeWithSymbolsForFakeOverrides {
    private final FakeOverrideCopier copier;
    private final FakeOverrideCopier copierMakingExternal;
    private final IrOverridableMember overridableMember;
    private final IrClass parent;
    private final FakeOverrideSymbolRemapperImpl symbolRemapper;

    public CopyIrTreeWithSymbolsForFakeOverrides(IrOverridableMember overridableMember, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, IrClass parent, IrUnimplementedOverridesStrategy unimplementedOverridesStrategy) {
        Intrinsics.checkNotNullParameter(overridableMember, "overridableMember");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(unimplementedOverridesStrategy, "unimplementedOverridesStrategy");
        this.overridableMember = overridableMember;
        this.parent = parent;
        FakeOverrideSymbolRemapperImpl fakeOverrideSymbolRemapperImpl = new FakeOverrideSymbolRemapperImpl(typeArguments, NullDescriptorsRemapper.INSTANCE);
        this.symbolRemapper = fakeOverrideSymbolRemapperImpl;
        this.copier = new FakeOverrideCopier(fakeOverrideSymbolRemapperImpl, new FakeOverrideTypeRemapper(this, fakeOverrideSymbolRemapperImpl, typeArguments), SymbolRenamer.DEFAULT.INSTANCE, false, parent, unimplementedOverridesStrategy);
        this.copierMakingExternal = new FakeOverrideCopier(fakeOverrideSymbolRemapperImpl, new FakeOverrideTypeRemapper(this, fakeOverrideSymbolRemapperImpl, typeArguments), SymbolRenamer.DEFAULT.INSTANCE, true, parent, unimplementedOverridesStrategy);
    }

    public final IrOverridableMember copy() {
        IrVisitorsKt.acceptVoid(this.overridableMember, this.symbolRemapper);
        IrElement irElementTransform = this.overridableMember.transform(IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) this.parent) ? this.copierMakingExternal : this.copier, null);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrOverridableMember");
        IrOverridableMember irOverridableMember = (IrOverridableMember) irElementTransform;
        PatchDeclarationParentsKt.patchDeclarationParents(irOverridableMember, this.parent);
        return irOverridableMember;
    }

    /* JADX INFO: compiled from: CopyIrTreeWithSymbolsForFakeOverrides.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/CopyIrTreeWithSymbolsForFakeOverrides$FakeOverrideTypeRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/overrides/CopyIrTreeWithSymbolsForFakeOverrides;Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Ljava/util/Map;)V", "getSymbolRemapper", "()Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "getTypeArguments", "()Ljava/util/Map;", "enterScope", "", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "leaveScope", "remapType", ModuleXmlParser.TYPE, "remapTypeArguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "arguments", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class FakeOverrideTypeRemapper implements TypeRemapper {
        private final SymbolRemapper symbolRemapper;
        final /* synthetic */ CopyIrTreeWithSymbolsForFakeOverrides this$0;
        private final Map<IrTypeParameterSymbol, IrType> typeArguments;

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public void enterScope(IrTypeParametersContainer irTypeParametersContainer) {
            Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
        }

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public void leaveScope() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public FakeOverrideTypeRemapper(CopyIrTreeWithSymbolsForFakeOverrides copyIrTreeWithSymbolsForFakeOverrides, SymbolRemapper symbolRemapper, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments) {
            Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
            Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
            this.this$0 = copyIrTreeWithSymbolsForFakeOverrides;
            this.symbolRemapper = symbolRemapper;
            this.typeArguments = typeArguments;
        }

        public final SymbolRemapper getSymbolRemapper() {
            return this.symbolRemapper;
        }

        public final Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
            return this.typeArguments;
        }

        private final List<IrTypeArgument> remapTypeArguments(List<? extends IrTypeArgument> arguments) {
            IrTypeProjection irTypeProjectionMakeTypeProjection;
            List<? extends IrTypeArgument> list = arguments;
            ArrayList arrayList = new ArrayList(list.size());
            for (IrTypeProjection irTypeProjection : list) {
                IrTypeProjection irTypeProjection2 = irTypeProjection instanceof IrTypeProjection ? (IrTypeProjection) irTypeProjection : null;
                if (irTypeProjection2 != null && (irTypeProjectionMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(remapType(irTypeProjection2.getType()), irTypeProjection2.getVariance())) != null) {
                    irTypeProjection = irTypeProjectionMakeTypeProjection;
                }
                arrayList.add(irTypeProjection);
            }
            return CollectionsKt.compactIfPossible(arrayList);
        }

        @Override // org.jetbrains.kotlin.ir.util.TypeRemapper
        public IrType remapType(IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(type instanceof IrSimpleType)) {
                return type;
            }
            IrSimpleType irSimpleType = (IrSimpleType) type;
            IrType irType = this.typeArguments.get(irSimpleType.getClassifier());
            if (irType instanceof IrDynamicType) {
                return irType;
            }
            if (irType instanceof IrSimpleType) {
                return IrTypesKt.mergeNullability(irType, type);
            }
            CopyIrTreeWithSymbolsForFakeOverrides copyIrTreeWithSymbolsForFakeOverrides = this.this$0;
            IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
            builder.setKotlinType(null);
            builder.setClassifier(this.symbolRemapper.getReferencedClassifier(irSimpleType.getClassifier()));
            builder.setArguments(remapTypeArguments(irSimpleType.getArguments()));
            List<IrConstructorCall> annotations = type.getAnnotations();
            ArrayList arrayList = new ArrayList(annotations.size());
            Iterator<T> it = annotations.iterator();
            while (it.hasNext()) {
                IrExpression irExpressionTransform = ((IrConstructorCall) it.next()).transform((IrElementTransformer) copyIrTreeWithSymbolsForFakeOverrides.copier, (Object) null);
                Intrinsics.checkNotNull(irExpressionTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                arrayList.add((IrConstructorCall) irExpressionTransform);
            }
            builder.setAnnotations(CollectionsKt.compactIfPossible(arrayList));
            return IrSimpleTypeImplKt.buildSimpleType(builder);
        }
    }

    /* JADX INFO: compiled from: CopyIrTreeWithSymbolsForFakeOverrides.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/CopyIrTreeWithSymbolsForFakeOverrides$FakeOverrideSymbolRemapperImpl;", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "descriptorsRemapper", "Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/util/DescriptorsRemapper;)V", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", NativeSymbol.TYPE_NAME, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FakeOverrideSymbolRemapperImpl extends DeepCopySymbolRemapper {
        private final Map<IrTypeParameterSymbol, IrType> typeArguments;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public FakeOverrideSymbolRemapperImpl(Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, DescriptorsRemapper descriptorsRemapper) {
            super(descriptorsRemapper);
            Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
            Intrinsics.checkNotNullParameter(descriptorsRemapper, "descriptorsRemapper");
            this.typeArguments = typeArguments;
        }

        @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
            IrType irType;
            IrClassifierSymbol classifierOrNull;
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            IrClassifierSymbol referencedClassifier = super.getReferencedClassifier(symbol);
            return (!(referencedClassifier instanceof IrTypeParameterSymbol) || (irType = this.typeArguments.get(referencedClassifier)) == null || (classifierOrNull = IrTypesKt.getClassifierOrNull(irType)) == null) ? referencedClassifier : classifierOrNull;
        }
    }
}
