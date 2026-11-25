package org.jetbrains.kotlin.backend.common.overrides;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.linkage.partial.ImplementAsErrorThrowingStubs;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureSerializer;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrTypeParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy;
import org.jetbrains.kotlin.ir.overrides.IrOverridingUtil;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FakeOverrides.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\r0\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\u0018\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0018H\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,H\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/H\u0002J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020(H\u0002J$\u00106\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u000208072\u0006\u0010-\u001a\u0002092\u0006\u00105\u001a\u00020(H\u0002J$\u0010:\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020;072\u0006\u00100\u001a\u00020<2\u0006\u00105\u001a\u00020(H\u0002J\u001e\u0010=\u001a\u00020>2\u0006\u0010)\u001a\u00020\u00172\u0006\u0010?\u001a\u0002022\u0006\u0010*\u001a\u00020\u0018J\u0018\u0010@\u001a\u00020>2\u0006\u0010-\u001a\u0002092\u0006\u00105\u001a\u00020(H\u0014J\u0018\u0010A\u001a\u00020>2\u0006\u00100\u001a\u00020<2\u0006\u00105\u001a\u00020(H\u0014J\u0006\u0010B\u001a\u00020>J\u0016\u0010B\u001a\u00020>2\u0006\u0010C\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0018R\u001d\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideBuilderStrategy;", "linker", "Lorg/jetbrains/kotlin/backend/common/overrides/FileLocalAwareLinker;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "friendModules", "", "", "", "partialLinkageSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "platformSpecificClassFilter", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;", "fakeOverrideDeclarationTable", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "(Lorg/jetbrains/kotlin/backend/common/overrides/FileLocalAwareLinker;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;)V", "fakeOverrideCandidates", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "getFakeOverrideCandidates", "()Ljava/util/Map;", "haveFakeOverrides", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irOverridingUtil", "Lorg/jetbrains/kotlin/ir/overrides/IrOverridingUtil;", "getLinker", "()Lorg/jetbrains/kotlin/backend/common/overrides/FileLocalAwareLinker;", "getPlatformSpecificClassFilter", "()Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "buildFakeOverrideChainsForClass", "", "clazz", "compatibilityMode", "buildFunctionWithDisambiguatedSignature", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "buildPropertyWithDisambiguatedSignature", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "property", "composeSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "manglerCompatibleMode", "computeFunctionFakeOverrideSymbol", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "computePropertyFakeOverrideSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "enqueueClass", "", "signature", "linkFunctionFakeOverride", "linkPropertyFakeOverride", "provideFakeOverrides", "klass", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideBuilder extends FakeOverrideBuilderStrategy {
    private final Map<IrClass, CompatibilityMode> fakeOverrideCandidates;
    private final DeclarationTable fakeOverrideDeclarationTable;
    private final Set<IrClass> haveFakeOverrides;
    private final IrBuiltIns irBuiltIns;
    private final IrOverridingUtil irOverridingUtil;
    private final FileLocalAwareLinker linker;
    private final PartialLinkageSupportForLinker partialLinkageSupport;
    private final FakeOverrideClassFilter platformSpecificClassFilter;
    private final SymbolTable symbolTable;

    public final FileLocalAwareLinker getLinker() {
        return this.linker;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public /* synthetic */ FakeOverrideBuilder(FileLocalAwareLinker fileLocalAwareLinker, SymbolTable symbolTable, KotlinMangler.IrMangler irMangler, IrTypeSystemContext irTypeSystemContext, Map map, PartialLinkageSupportForLinker partialLinkageSupportForLinker, FakeOverrideClassFilter fakeOverrideClassFilter, DeclarationTable declarationTable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fileLocalAwareLinker, symbolTable, irMangler, irTypeSystemContext, map, partialLinkageSupportForLinker, (i & 64) != 0 ? DefaultFakeOverrideClassFilter.INSTANCE : fakeOverrideClassFilter, (i & 128) != 0 ? new FakeOverrideDeclarationTable(irMangler, null, new Function2<PublicIdSignatureComputer, DeclarationTable, IdSignatureSerializer>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder.1
            @Override // kotlin.jvm.functions.Function2
            public final IdSignatureSerializer invoke(PublicIdSignatureComputer builder, DeclarationTable table) {
                Intrinsics.checkNotNullParameter(builder, "builder");
                Intrinsics.checkNotNullParameter(table, "table");
                return new IdSignatureSerializer(builder, table);
            }
        }, 2, null) : declarationTable);
    }

    public final FakeOverrideClassFilter getPlatformSpecificClassFilter() {
        return this.platformSpecificClassFilter;
    }

    public FakeOverrideBuilder(FileLocalAwareLinker linker, SymbolTable symbolTable, KotlinMangler.IrMangler mangler, IrTypeSystemContext typeSystem, Map<String, ? extends Collection<String>> friendModules, PartialLinkageSupportForLinker partialLinkageSupport, FakeOverrideClassFilter platformSpecificClassFilter, DeclarationTable fakeOverrideDeclarationTable) {
        IrUnimplementedOverridesStrategy.ProcessAsFakeOverrides implementAsErrorThrowingStubs;
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(partialLinkageSupport, "partialLinkageSupport");
        Intrinsics.checkNotNullParameter(platformSpecificClassFilter, "platformSpecificClassFilter");
        Intrinsics.checkNotNullParameter(fakeOverrideDeclarationTable, "fakeOverrideDeclarationTable");
        if (partialLinkageSupport.isEnabled()) {
            implementAsErrorThrowingStubs = new ImplementAsErrorThrowingStubs(partialLinkageSupport);
        } else {
            implementAsErrorThrowingStubs = IrUnimplementedOverridesStrategy.ProcessAsFakeOverrides.INSTANCE;
        }
        super(friendModules, implementAsErrorThrowingStubs);
        this.linker = linker;
        this.symbolTable = symbolTable;
        this.partialLinkageSupport = partialLinkageSupport;
        this.platformSpecificClassFilter = platformSpecificClassFilter;
        this.fakeOverrideDeclarationTable = fakeOverrideDeclarationTable;
        this.haveFakeOverrides = new LinkedHashSet();
        this.irOverridingUtil = new IrOverridingUtil(typeSystem, this);
        this.irBuiltIns = typeSystem.getIrBuiltIns();
        this.fakeOverrideCandidates = new LinkedHashMap();
    }

    public final Map<IrClass, CompatibilityMode> getFakeOverrideCandidates() {
        return this.fakeOverrideCandidates;
    }

    public final void enqueueClass(IrClass clazz, IdSignature signature, CompatibilityMode compatibilityMode) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        this.fakeOverrideDeclarationTable.assumeDeclarationSignature(clazz, signature);
        this.fakeOverrideCandidates.put(clazz, compatibilityMode);
    }

    private final boolean buildFakeOverrideChainsForClass(final IrClass clazz, final CompatibilityMode compatibilityMode) {
        if (this.haveFakeOverrides.contains(clazz)) {
            return true;
        }
        List<IrType> superTypes = clazz.getSuperTypes();
        ArrayList<IrClass> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        for (IrType irType : superTypes) {
            IrClass irClass = IrTypesKt.getClass(irType);
            if (irClass == null) {
                throw new IllegalStateException(("Unexpected super type: " + irType).toString());
            }
            arrayList.add(irClass);
        }
        for (IrClass irClass2 : arrayList) {
            CompatibilityMode compatibilityMode2 = this.fakeOverrideCandidates.get(irClass2);
            if (compatibilityMode2 == null) {
                compatibilityMode2 = compatibilityMode;
            }
            if (buildFakeOverrideChainsForClass(irClass2, compatibilityMode2)) {
                this.haveFakeOverrides.add(irClass2);
            }
        }
        if (!this.platformSpecificClassFilter.needToConstructFakeOverrides(clazz)) {
            return false;
        }
        this.fakeOverrideDeclarationTable.inFile(IrUtilsKt.getFileOrNull(clazz), new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder$buildFakeOverrideChainsForClass$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.irOverridingUtil.buildFakeOverridesForClass(clazz, compatibilityMode.getOldSignatures());
            }
        });
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy
    protected void linkFunctionFakeOverride(final IrFunctionWithLateBinding function, boolean manglerCompatibleMode) {
        Intrinsics.checkNotNullParameter(function, "function");
        Pair<IdSignature, IrSimpleFunctionSymbol> pairComputeFunctionFakeOverrideSymbol = computeFunctionFakeOverrideSymbol(function, manglerCompatibleMode);
        IdSignature idSignatureComponent1 = pairComputeFunctionFakeOverrideSymbol.component1();
        final IrSimpleFunctionSymbol irSimpleFunctionSymbolComponent2 = pairComputeFunctionFakeOverrideSymbol.component2();
        this.symbolTable.declareSimpleFunction(idSignatureComponent1, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder.linkFunctionFakeOverride.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunctionSymbol invoke() {
                return irSimpleFunctionSymbolComponent2;
            }
        }, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder.linkFunctionFakeOverride.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return function.acquireSymbol(it);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy
    protected void linkPropertyFakeOverride(final IrPropertyWithLateBinding property, boolean manglerCompatibleMode) {
        Intrinsics.checkNotNullParameter(property, "property");
        IrPropertySymbolImpl irPropertySymbolImpl = new IrPropertySymbolImpl(null, 1, null);
        irPropertySymbolImpl.bind(property);
        IrSimpleFunction getter = property.getGetter();
        if (getter != null) {
            getter.setCorrespondingPropertySymbol(irPropertySymbolImpl);
        }
        IrSimpleFunction setter = property.getSetter();
        if (setter != null) {
            setter.setCorrespondingPropertySymbol(irPropertySymbolImpl);
        }
        Pair<IdSignature, IrPropertySymbol> pairComputePropertyFakeOverrideSymbol = computePropertyFakeOverrideSymbol(property, manglerCompatibleMode);
        IdSignature idSignatureComponent1 = pairComputePropertyFakeOverrideSymbol.component1();
        final IrPropertySymbol irPropertySymbolComponent2 = pairComputePropertyFakeOverrideSymbol.component2();
        this.symbolTable.declareProperty(idSignatureComponent1, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder.linkPropertyFakeOverride.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPropertySymbol invoke() {
                return irPropertySymbolComponent2;
            }
        }, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder.linkPropertyFakeOverride.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return property.acquireSymbol(it);
            }
        });
        IrSimpleFunction getter2 = property.getGetter();
        if (getter2 != null) {
            getter2.setCorrespondingPropertySymbol(property.getSymbol());
            IrFunctionWithLateBinding irFunctionWithLateBinding = getter2 instanceof IrFunctionWithLateBinding ? (IrFunctionWithLateBinding) getter2 : null;
            if (irFunctionWithLateBinding != null) {
                linkFunctionFakeOverride(irFunctionWithLateBinding, manglerCompatibleMode);
            } else {
                throw new IllegalStateException(("Unexpected fake override getter: " + getter2).toString());
            }
        }
        IrSimpleFunction setter2 = property.getSetter();
        if (setter2 != null) {
            setter2.setCorrespondingPropertySymbol(property.getSymbol());
            IrFunctionWithLateBinding irFunctionWithLateBinding2 = setter2 instanceof IrFunctionWithLateBinding ? (IrFunctionWithLateBinding) setter2 : null;
            if (irFunctionWithLateBinding2 != null) {
                linkFunctionFakeOverride(irFunctionWithLateBinding2, manglerCompatibleMode);
                return;
            }
            throw new IllegalStateException(("Unexpected fake override setter: " + setter2).toString());
        }
    }

    private final IdSignature composeSignature(IrDeclaration declaration, boolean manglerCompatibleMode) {
        return this.fakeOverrideDeclarationTable.getSignaturer().composeSignatureForDeclaration(declaration, manglerCompatibleMode);
    }

    private final Pair<IdSignature, IrSimpleFunctionSymbol> computeFunctionFakeOverrideSymbol(IrFunctionWithLateBinding function, boolean manglerCompatibleMode) {
        IrFunctionWithLateBinding irFunctionWithLateBinding = function;
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irFunctionWithLateBinding);
        IdSignature idSignatureComposeSignature = composeSignature(irFunctionWithLateBinding, manglerCompatibleMode);
        IrClass irClass = parentAsClass;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature = this.linker.tryReferencingSimpleFunctionByLocalSignature(irClass, idSignatureComposeSignature);
        if (irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature == null) {
            irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature = this.symbolTable.referenceSimpleFunction(idSignatureComposeSignature);
        }
        if (this.partialLinkageSupport.isEnabled() && irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature.isBound()) {
            IrSimpleFunction owner = irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature.getOwner();
            if (owner.getIsSuspend() != function.getIsSuspend() || owner.getIsInline() || function.getIsInline()) {
                IdSignature idSignatureComposeSignature2 = composeSignature(buildFunctionWithDisambiguatedSignature(function), manglerCompatibleMode);
                Intrinsics.areEqual(idSignatureComposeSignature2, idSignatureComposeSignature);
                IrSimpleFunctionSymbol irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature2 = this.linker.tryReferencingSimpleFunctionByLocalSignature(irClass, idSignatureComposeSignature2);
                if (irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature2 == null) {
                    irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature2 = this.symbolTable.referenceSimpleFunction(idSignatureComposeSignature2);
                }
                return TuplesKt.to(idSignatureComposeSignature2, irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature2);
            }
        }
        return TuplesKt.to(idSignatureComposeSignature, irSimpleFunctionSymbolTryReferencingSimpleFunctionByLocalSignature);
    }

    private final Pair<IdSignature, IrPropertySymbol> computePropertyFakeOverrideSymbol(IrPropertyWithLateBinding property, boolean manglerCompatibleMode) {
        IrProperty owner;
        IrSimpleFunction getter;
        IrSimpleFunction setter;
        IrSimpleFunction getter2;
        IrSimpleFunction setter2;
        IrPropertyWithLateBinding irPropertyWithLateBinding = property;
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irPropertyWithLateBinding);
        IdSignature idSignatureComposeSignature = composeSignature(irPropertyWithLateBinding, manglerCompatibleMode);
        IrClass irClass = parentAsClass;
        IrPropertySymbol irPropertySymbolTryReferencingPropertyByLocalSignature = this.linker.tryReferencingPropertyByLocalSignature(irClass, idSignatureComposeSignature);
        if (irPropertySymbolTryReferencingPropertyByLocalSignature == null) {
            irPropertySymbolTryReferencingPropertyByLocalSignature = this.symbolTable.referenceProperty(idSignatureComposeSignature);
        }
        if (!this.partialLinkageSupport.isEnabled() || !irPropertySymbolTryReferencingPropertyByLocalSignature.isBound() || (((getter = (owner = irPropertySymbolTryReferencingPropertyByLocalSignature.getOwner()).getGetter()) == null || !getter.getIsInline()) && (((setter = owner.getSetter()) == null || !setter.getIsInline()) && (((getter2 = property.getGetter()) == null || !getter2.getIsInline()) && ((setter2 = property.getSetter()) == null || !setter2.getIsInline()))))) {
            return TuplesKt.to(idSignatureComposeSignature, irPropertySymbolTryReferencingPropertyByLocalSignature);
        }
        IdSignature idSignatureComposeSignature2 = composeSignature(buildPropertyWithDisambiguatedSignature(property), manglerCompatibleMode);
        Intrinsics.areEqual(idSignatureComposeSignature2, idSignatureComposeSignature);
        IrPropertySymbol irPropertySymbolTryReferencingPropertyByLocalSignature2 = this.linker.tryReferencingPropertyByLocalSignature(irClass, idSignatureComposeSignature2);
        if (irPropertySymbolTryReferencingPropertyByLocalSignature2 == null) {
            irPropertySymbolTryReferencingPropertyByLocalSignature2 = this.symbolTable.referenceProperty(idSignatureComposeSignature2);
        }
        return TuplesKt.to(idSignatureComposeSignature2, irPropertySymbolTryReferencingPropertyByLocalSignature2);
    }

    private final IrSimpleFunction buildFunctionWithDisambiguatedSignature(IrSimpleFunction function) {
        IrFactory factory = function.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrSimpleFunction irSimpleFunction = function;
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setName(function.getName());
        irFunctionBuilder.setReturnType(this.irBuiltIns.getUnitType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(function.getParent());
        IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, function);
        IrUtilsKt.copyParameterDeclarationsFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
        List<IrTypeParameter> typeParameters = irSimpleFunctionBuildFunction.getTypeParameters();
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        Name nameIdentifier = Name.identifier("disambiguation type parameter");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irTypeParameterBuilder.setName(nameIdentifier);
        irTypeParameterBuilder.setIndex(irSimpleFunctionBuildFunction.getTypeParameters().size());
        irTypeParameterBuilder.getSuperTypes().add(this.irBuiltIns.getNothingType());
        Unit unit = Unit.INSTANCE;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        irSimpleFunctionBuildFunction.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) typeParameters, DeclarationBuildersKt.buildTypeParameter(irSimpleFunction2.getFactory(), irTypeParameterBuilder, irSimpleFunction2)));
        return irSimpleFunctionBuildFunction;
    }

    private final IrProperty buildPropertyWithDisambiguatedSignature(IrProperty property) {
        IrFactory factory = property.getFactory();
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        irPropertyBuilder.updateFrom(property);
        irPropertyBuilder.setName(property.getName());
        IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
        irPropertyBuildProperty.setParent(property.getParent());
        IrUtilsKt.copyAnnotationsFrom(irPropertyBuildProperty, property);
        IrSimpleFunction getter = property.getGetter();
        irPropertyBuildProperty.setGetter(getter != null ? buildFunctionWithDisambiguatedSignature(getter) : null);
        IrSimpleFunction setter = property.getSetter();
        irPropertyBuildProperty.setSetter(setter != null ? buildFunctionWithDisambiguatedSignature(setter) : null);
        return irPropertyBuildProperty;
    }

    public final void provideFakeOverrides(IrClass klass, CompatibilityMode compatibilityMode) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        buildFakeOverrideChainsForClass(klass, compatibilityMode);
        this.irOverridingUtil.clear();
        this.haveFakeOverrides.add(klass);
    }

    public final void provideFakeOverrides() {
        Set<Map.Entry<IrClass, CompatibilityMode>> setEntrySet = this.fakeOverrideCandidates.entrySet();
        while (!setEntrySet.isEmpty()) {
            Map.Entry entry = (Map.Entry) CollectionsKt.last(setEntrySet);
            setEntrySet.remove(entry);
            provideFakeOverrides((IrClass) entry.getKey(), (CompatibilityMode) entry.getValue());
        }
    }
}
