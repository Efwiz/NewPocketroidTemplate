package org.jetbrains.kotlin.ir.util;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyEnumEntryImpl;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyField;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyProperty;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazySymbolTable;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyTypeAlias;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyTypeParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyValueParameter;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DeclarationStubGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0002J\u000e\u00102\u001a\u00020\u00142\u0006\u00100\u001a\u000203J\u000e\u00104\u001a\u0002052\u0006\u00100\u001a\u000206J\u000e\u00107\u001a\u0002082\u0006\u00100\u001a\u000203J\u000e\u00109\u001a\u00020:2\u0006\u00100\u001a\u00020;J\u0018\u0010<\u001a\u00020=2\u0006\u00100\u001a\u00020>2\b\b\u0002\u0010?\u001a\u00020(J\u000e\u0010@\u001a\u00020A2\u0006\u00100\u001a\u000201J\u000e\u0010B\u001a\u00020C2\u0006\u00100\u001a\u00020DJ\u0010\u0010E\u001a\u0004\u0018\u00010\u00142\u0006\u00100\u001a\u000201J\u0015\u0010F\u001a\u00020G2\u0006\u00100\u001a\u00020HH\u0000¢\u0006\u0002\bIJ\u0015\u0010J\u001a\u00020G2\u0006\u00100\u001a\u00020HH\u0000¢\u0006\u0002\bKJ\u000e\u0010L\u001a\u00020M2\u0006\u00100\u001a\u00020;J\u0018\u0010N\u001a\u00020A2\u0006\u0010O\u001a\u00020P2\u0006\u00100\u001a\u000201H\u0002J\u000e\u0010Q\u001a\u00020R2\u0006\u00100\u001a\u00020SJ\u001d\u0010T\u001a\u00020U2\u0006\u00100\u001a\u00020V2\u0006\u0010W\u001a\u00020XH\u0000¢\u0006\u0002\bYJ\u0012\u0010Z\u001a\u0004\u0018\u00010A2\u0006\u0010O\u001a\u00020PH\u0016J\u0010\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u000203H\u0002J\f\u0010^\u001a\u00020_*\u00020`H\u0002R\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0012\u0010#\u001a\u00020$X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R$\u0010)\u001a\u00020(2\u0006\u0010'\u001a\u00020(8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-¨\u0006a"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "extensions", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;)V", "descriptorFinder", "Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "getDescriptorFinder", "()Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "getExtensions", "()Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "facadeClassMap", "", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "lazyTable", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;", "getLazyTable", "()Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "value", "", "unboundSymbolGeneration", "getUnboundSymbolGeneration", "()Z", "setUnboundSymbolGeneration", "(Z)V", "computeOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "generateClassStub", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "generateConstructorStub", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "generateEnumEntryStub", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "generateFieldStub", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateFunctionStub", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "createPropertyIfNeeded", "generateMemberStub", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "generateOrGetEmptyExternalPackageFragmentStub", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "generateOrGetFacadeClass", "generateOrGetScopedTypeParameterStub", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "generateOrGetScopedTypeParameterStub$ir_tree", "generateOrGetTypeParameterStub", "generateOrGetTypeParameterStub$ir_tree", "generatePropertyStub", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "generateStubBySymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "generateTypeAliasStub", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "generateValueParameterStub", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "index", "", "generateValueParameterStub$ir_tree", "getDeclaration", "getEffectiveModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "classDescriptor", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationStubGenerator implements IrProvider {
    private final StubGeneratorExtensions extensions;
    private final Map<DeserializedContainerSource, IrClass> facadeClassMap;
    private final IrBuiltIns irBuiltIns;
    private final IrLazySymbolTable lazyTable;
    private final ModuleDescriptor moduleDescriptor;
    private final SymbolTable symbolTable;

    public abstract DescriptorByIdSignatureFinder getDescriptorFinder();

    public abstract TypeTranslator getTypeTranslator();

    public DeclarationStubGenerator(ModuleDescriptor moduleDescriptor, SymbolTable symbolTable, IrBuiltIns irBuiltIns, StubGeneratorExtensions extensions) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.moduleDescriptor = moduleDescriptor;
        this.symbolTable = symbolTable;
        this.irBuiltIns = irBuiltIns;
        this.extensions = extensions;
        this.lazyTable = symbolTable.getLazyWrapper();
        extensions.registerDeclarations(symbolTable);
        this.facadeClassMap = new LinkedHashMap();
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public /* synthetic */ DeclarationStubGenerator(ModuleDescriptor moduleDescriptor, SymbolTable symbolTable, IrBuiltIns irBuiltIns, StubGeneratorExtensions stubGeneratorExtensions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, symbolTable, irBuiltIns, (i & 8) != 0 ? StubGeneratorExtensions.EMPTY : stubGeneratorExtensions);
    }

    public final StubGeneratorExtensions getExtensions() {
        return this.extensions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final IrLazySymbolTable getLazyTable() {
        return this.lazyTable;
    }

    public final IrLock getLock() {
        return this.symbolTable.getLock();
    }

    public final boolean getUnboundSymbolGeneration() {
        return this.lazyTable.getStubGenerator() != null;
    }

    public final void setUnboundSymbolGeneration(boolean z) {
        this.lazyTable.setStubGenerator(z ? this : null);
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        DeclarationDescriptor descriptor;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (!symbol.getHasDescriptor()) {
            DescriptorByIdSignatureFinder descriptorFinder = getDescriptorFinder();
            IdSignature signature = symbol.getSignature();
            if (signature != null) {
                descriptor = descriptorFinder.findDescriptorBySignature(signature);
            } else {
                throw new IllegalStateException(("Symbol is not public API. Expected signature for symbol: " + symbol.getDescriptor()).toString());
            }
        } else {
            descriptor = symbol.getDescriptor();
        }
        if (descriptor == null) {
            return null;
        }
        return generateStubBySymbol(symbol, descriptor);
    }

    public final IrExternalPackageFragment generateOrGetEmptyExternalPackageFragmentStub(PackageFragmentDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrExternalPackageFragmentSymbol irExternalPackageFragmentSymbolReferenceExternalPackageFragment = this.symbolTable.getDescriptorExtension().referenceExternalPackageFragment(descriptor);
        if (irExternalPackageFragmentSymbolReferenceExternalPackageFragment.isBound()) {
            return irExternalPackageFragmentSymbolReferenceExternalPackageFragment.getOwner();
        }
        return this.symbolTable.getDescriptorExtension().declareExternalPackageFragment(descriptor);
    }

    public final IrClass generateOrGetFacadeClass(DeclarationDescriptor descriptor) {
        DeserializedContainerSource containerSource;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrClass irClass = null;
        PropertyAccessorDescriptor propertyAccessorDescriptor = descriptor instanceof PropertyAccessorDescriptor ? (PropertyAccessorDescriptor) descriptor : null;
        PropertyDescriptor correspondingProperty = propertyAccessorDescriptor != null ? propertyAccessorDescriptor.getCorrespondingProperty() : null;
        if (correspondingProperty != null) {
            descriptor = correspondingProperty;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
        if (packageFragmentDescriptor == null || (containerSource = this.extensions.getContainerSource(descriptor)) == null) {
            return null;
        }
        Map<DeserializedContainerSource, IrClass> map = this.facadeClassMap;
        IrClass irClass2 = map.get(containerSource);
        if (irClass2 == null) {
            IrClass irClassGenerateFacadeClass = this.extensions.generateFacadeClass(this.symbolTable.getIrFactory(), containerSource, this);
            if (irClassGenerateFacadeClass != null) {
                IrExternalPackageFragment irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub = generateOrGetEmptyExternalPackageFragmentStub(packageFragmentDescriptor);
                irClassGenerateFacadeClass.setParent(irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub);
                irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub.getDeclarations().add(irClassGenerateFacadeClass);
                irClass = irClassGenerateFacadeClass;
            }
            map.put(containerSource, irClass);
            irClass2 = irClass;
        }
        return irClass2;
    }

    public final IrDeclaration generateMemberStub(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ClassDescriptor) {
            if (DescriptorUtils.isEnumEntry(descriptor)) {
                return generateEnumEntryStub((ClassDescriptor) descriptor);
            }
            return generateClassStub((ClassDescriptor) descriptor);
        }
        if (descriptor instanceof ClassConstructorDescriptor) {
            return generateConstructorStub((ClassConstructorDescriptor) descriptor);
        }
        if (descriptor instanceof FunctionDescriptor) {
            return generateFunctionStub$default(this, (FunctionDescriptor) descriptor, false, 2, null);
        }
        if (descriptor instanceof PropertyDescriptor) {
            return generatePropertyStub((PropertyDescriptor) descriptor);
        }
        if (descriptor instanceof TypeAliasDescriptor) {
            return generateTypeAliasStub((TypeAliasDescriptor) descriptor);
        }
        if (descriptor instanceof TypeParameterDescriptor) {
            return generateOrGetTypeParameterStub$ir_tree((TypeParameterDescriptor) descriptor);
        }
        throw new AssertionError("Unexpected member descriptor: " + descriptor);
    }

    private final IrDeclaration generateStubBySymbol(IrSymbol symbol, DeclarationDescriptor descriptor) {
        if (symbol instanceof IrFieldSymbol) {
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
            return generateFieldStub((PropertyDescriptor) descriptor);
        }
        if (symbol instanceof IrTypeParameterSymbol) {
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            return generateOrGetTypeParameterStub$ir_tree((TypeParameterDescriptor) descriptor);
        }
        return generateMemberStub(descriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrDeclarationOrigin computeOrigin(DeclarationDescriptor descriptor) {
        IrDeclarationOrigin irDeclarationOriginComputeExternalDeclarationOrigin = this.extensions.computeExternalDeclarationOrigin(descriptor);
        return irDeclarationOriginComputeExternalDeclarationOrigin == null ? IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE : irDeclarationOriginComputeExternalDeclarationOrigin;
    }

    public final IrProperty generatePropertyStub(final PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrPropertySymbol irPropertySymbolReferenceProperty = this.symbolTable.getDescriptorExtension().referenceProperty(descriptor);
        if (irPropertySymbolReferenceProperty.isBound()) {
            return irPropertySymbolReferenceProperty.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        PropertyDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return descriptorExtension.declareProperty(original, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generatePropertyStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                PropertyDescriptor propertyDescriptor = descriptor;
                Name name2 = propertyDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                Modality modality = descriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                boolean zIsVar = descriptor.isVar();
                boolean zIsConst = descriptor.isConst();
                boolean zIsLateInit = descriptor.isLateInit();
                boolean zIsDelegated = descriptor.getIsDelegated();
                boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(descriptor);
                boolean zIsExpect = descriptor.get$isExpect();
                boolean z = Intrinsics.areEqual(irDeclarationOriginComputeOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) || descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyProperty(-1, -1, irDeclarationOrigin, it, propertyDescriptor, name2, visibility, modality, zIsVar, zIsConst, zIsLateInit, zIsDelegated, zIsEffectivelyExternal, zIsExpect, z, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrField generateFieldStub(final PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrFieldSymbol irFieldSymbolReferenceField = this.symbolTable.getDescriptorExtension().referenceField(descriptor);
        if (irFieldSymbolReferenceField.isBound()) {
            return irFieldSymbolReferenceField.getOwner();
        }
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        PropertyDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return SymbolTableExtension.declareField$default(descriptorExtension, -1, -1, irDeclarationOriginComputeOrigin, original, toIrType(type), null, new Function1<IrFieldSymbol, IrField>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generateFieldStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrField invoke(IrFieldSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOriginComputeOrigin2 = DeclarationStubGenerator.this.computeOrigin(descriptor);
                PropertyDescriptor propertyDescriptor = descriptor;
                Name name2 = propertyDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                boolean z = !descriptor.isVar();
                boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(descriptor);
                boolean z2 = descriptor.mo6753getDispatchReceiverParameter() == null;
                DeclarationStubGenerator declarationStubGenerator = DeclarationStubGenerator.this;
                return new IrLazyField(-1, -1, irDeclarationOriginComputeOrigin2, it, propertyDescriptor, name2, visibility, z, zIsEffectivelyExternal, z2, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        }, 32, null);
    }

    public static /* synthetic */ IrSimpleFunction generateFunctionStub$default(DeclarationStubGenerator declarationStubGenerator, FunctionDescriptor functionDescriptor, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: generateFunctionStub");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return declarationStubGenerator.generateFunctionStub(functionDescriptor, z);
    }

    public final IrSimpleFunction generateFunctionStub(final FunctionDescriptor descriptor, boolean createPropertyIfNeeded) {
        final IrDeclarationOrigin.FAKE_OVERRIDE fake_overrideComputeOrigin;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = this.symbolTable.getDescriptorExtension().referenceSimpleFunction(descriptor);
        if (irSimpleFunctionSymbolReferenceSimpleFunction.isBound()) {
            return irSimpleFunctionSymbolReferenceSimpleFunction.getOwner();
        }
        if (createPropertyIfNeeded && (descriptor instanceof PropertyGetterDescriptor)) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) descriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            IrSimpleFunction getter = generatePropertyStub(correspondingProperty).getGetter();
            Intrinsics.checkNotNull(getter);
            return getter;
        }
        if (createPropertyIfNeeded && (descriptor instanceof PropertySetterDescriptor)) {
            PropertyDescriptor correspondingProperty2 = ((PropertySetterDescriptor) descriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty2, "getCorrespondingProperty(...)");
            IrSimpleFunction setter = generatePropertyStub(correspondingProperty2).getSetter();
            Intrinsics.checkNotNull(setter);
            return setter;
        }
        if (descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            fake_overrideComputeOrigin = IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE;
        } else {
            fake_overrideComputeOrigin = computeOrigin(descriptor);
        }
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        FunctionDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return descriptorExtension.declareSimpleFunction(original, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generateFunctionStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = fake_overrideComputeOrigin;
                FunctionDescriptor functionDescriptor = descriptor;
                Name name2 = functionDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                Modality modality = descriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                boolean zIsInline = descriptor.isInline();
                boolean zIsExternal = descriptor.isExternal();
                boolean zIsTailrec = descriptor.isTailrec();
                boolean zIsSuspend = descriptor.isSuspend();
                boolean zIsExpect = descriptor.get$isExpect();
                boolean zAreEqual = Intrinsics.areEqual(fake_overrideComputeOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE);
                boolean zIsOperator = descriptor.isOperator();
                boolean zIsInfix = descriptor.isInfix();
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyFunction(-1, -1, irDeclarationOrigin, it, functionDescriptor, name2, visibility, modality, zIsInline, zIsExternal, zIsTailrec, zIsSuspend, zIsExpect, zAreEqual, zIsOperator, zIsInfix, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrConstructor generateConstructorStub(final ClassConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrConstructorSymbol irConstructorSymbolReferenceConstructor = this.symbolTable.getDescriptorExtension().referenceConstructor(descriptor);
        if (irConstructorSymbolReferenceConstructor.isBound()) {
            return irConstructorSymbolReferenceConstructor.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareConstructor(descriptor.getOriginal(), new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generateConstructorStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrConstructor invoke(IrConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                ClassConstructorDescriptor classConstructorDescriptor = descriptor;
                Name name2 = classConstructorDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                boolean zIsInline = descriptor.isInline();
                boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(descriptor);
                boolean zIsPrimary = descriptor.isPrimary();
                boolean zIsExpect = descriptor.get$isExpect();
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyConstructor(-1, -1, irDeclarationOrigin, it, classConstructorDescriptor, name2, visibility, zIsInline, zIsEffectivelyExternal, zIsPrimary, zIsExpect, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    private final IrType toIrType(KotlinType kotlinType) {
        return getTypeTranslator().translateType(kotlinType);
    }

    public final IrValueParameter generateValueParameterStub$ir_tree(ValueParameterDescriptor descriptor, int index) {
        IrLazyValueParameter irLazyValueParameter;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(descriptor);
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrLazyValueParameter irLazyValueParameter2 = new IrLazyValueParameter(-1, -1, irDeclarationOriginComputeOrigin, irValueParameterSymbolImpl, descriptor, name2, index, type, descriptor.getVarargElementType(), descriptor.getIsCrossinline(), descriptor.getIsNoinline(), false, false, this, getTypeTranslator());
        if (descriptor.declaresDefaultValue()) {
            irLazyValueParameter = irLazyValueParameter2;
            irLazyValueParameter.setDefaultValue(IrUtilsKt.createStubDefaultValue(irLazyValueParameter));
        } else {
            irLazyValueParameter = irLazyValueParameter2;
        }
        return irLazyValueParameter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Modality getEffectiveModality(ClassDescriptor classDescriptor) {
        if (DescriptorUtils.isAnnotationClass(classDescriptor)) {
            return Modality.OPEN;
        }
        Modality modality = classDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        return modality;
    }

    public final IrClass generateClassStub(final ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrClassSymbol irClassSymbolReferenceClass = this.symbolTable.getDescriptorExtension().referenceClass(descriptor);
        if (irClassSymbolReferenceClass.isBound()) {
            return irClassSymbolReferenceClass.getOwner();
        }
        if (irClassSymbolReferenceClass.getHasDescriptor()) {
            descriptor = irClassSymbolReferenceClass.getDescriptor();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareClass(descriptor, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator$generateClassStub$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                ClassDescriptor classDescriptor = descriptor;
                Name name2 = classDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                ClassKind kind = descriptor.getKind();
                Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                Modality effectiveModality = this.getEffectiveModality(descriptor);
                boolean isCompanionObject = descriptor.getIsCompanionObject();
                boolean zIsInner = descriptor.getIsInner();
                boolean zIsData = descriptor.isData();
                boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(descriptor);
                boolean zIsValueClass = InlineClassesUtilsKt.isValueClass(descriptor);
                boolean zIsExpect = descriptor.get$isExpect();
                boolean zIsFun = descriptor.isFun();
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyClass(-1, -1, irDeclarationOrigin, it, classDescriptor, name2, kind, visibility, effectiveModality, isCompanionObject, zIsInner, zIsData, zIsEffectivelyExternal, zIsValueClass, zIsExpect, zIsFun, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrEnumEntry generateEnumEntryStub(final ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrEnumEntrySymbol irEnumEntrySymbolReferenceEnumEntry = this.symbolTable.getDescriptorExtension().referenceEnumEntry(descriptor);
        if (irEnumEntrySymbolReferenceEnumEntry.isBound()) {
            return irEnumEntrySymbolReferenceEnumEntry.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareEnumEntry(descriptor, new Function1<IrEnumEntrySymbol, IrEnumEntry>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generateEnumEntryStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrEnumEntry invoke(IrEnumEntrySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                ClassDescriptor classDescriptor = descriptor;
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyEnumEntryImpl(-1, -1, irDeclarationOrigin, it, classDescriptor, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrTypeParameter generateOrGetTypeParameterStub$ir_tree(final TypeParameterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrTypeParameterSymbol irTypeParameterSymbolReferenceTypeParameter = this.symbolTable.getDescriptorExtension().referenceTypeParameter(descriptor);
        if (irTypeParameterSymbolReferenceTypeParameter.isBound()) {
            return irTypeParameterSymbolReferenceTypeParameter.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareGlobalTypeParameter(descriptor, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator$generateOrGetTypeParameterStub$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                TypeParameterDescriptor typeParameterDescriptor = descriptor;
                Name name2 = typeParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                int index = descriptor.getIndex();
                boolean zIsReified = descriptor.isReified();
                Variance variance = descriptor.getVariance();
                Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyTypeParameter(-1, -1, irDeclarationOrigin, it, typeParameterDescriptor, name2, index, zIsReified, variance, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrTypeParameter generateOrGetScopedTypeParameterStub$ir_tree(final TypeParameterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrTypeParameterSymbol irTypeParameterSymbolReferenceScopedTypeParameter = this.symbolTable.getDescriptorExtension().referenceScopedTypeParameter(descriptor);
        if (irTypeParameterSymbolReferenceScopedTypeParameter.isBound()) {
            return irTypeParameterSymbolReferenceScopedTypeParameter.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareScopedTypeParameter(-1, -1, irDeclarationOriginComputeOrigin, descriptor, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator$generateOrGetScopedTypeParameterStub$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                TypeParameterDescriptor typeParameterDescriptor = descriptor;
                Name name2 = typeParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                int index = descriptor.getIndex();
                boolean zIsReified = descriptor.isReified();
                Variance variance = descriptor.getVariance();
                Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyTypeParameter(-1, -1, irDeclarationOrigin, it, typeParameterDescriptor, name2, index, zIsReified, variance, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }

    public final IrTypeAlias generateTypeAliasStub(final TypeAliasDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IrTypeAliasSymbol irTypeAliasSymbolReferenceTypeAlias = this.symbolTable.getDescriptorExtension().referenceTypeAlias(descriptor);
        if (irTypeAliasSymbolReferenceTypeAlias.isBound()) {
            return irTypeAliasSymbolReferenceTypeAlias.getOwner();
        }
        final IrDeclarationOrigin irDeclarationOriginComputeOrigin = computeOrigin(descriptor);
        return this.symbolTable.getDescriptorExtension().declareTypeAlias(descriptor, new Function1<IrTypeAliasSymbol, IrTypeAlias>() { // from class: org.jetbrains.kotlin.ir.util.DeclarationStubGenerator.generateTypeAliasStub.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeAlias invoke(IrTypeAliasSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationOrigin irDeclarationOrigin = irDeclarationOriginComputeOrigin;
                TypeAliasDescriptor typeAliasDescriptor = descriptor;
                Name name2 = typeAliasDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = descriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                boolean zIsActual = descriptor.isActual();
                DeclarationStubGenerator declarationStubGenerator = this;
                return new IrLazyTypeAlias(-1, -1, irDeclarationOrigin, it, typeAliasDescriptor, name2, visibility, zIsActual, declarationStubGenerator, declarationStubGenerator.getTypeTranslator());
            }
        });
    }
}
