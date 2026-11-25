package org.jetbrains.kotlin.ir.backend.jvm.serialization;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.backend.common.serialization.BasicIrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.CurrentModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.DescriptorByIdSignatureFinderImpl;
import org.jetbrains.kotlin.backend.common.serialization.DeserializationStrategy;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializerKind;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibModuleOrigin;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmIrLinker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003567BI\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u001e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0 H\u0014J.\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00032\b\u0010#\u001a\u0004\u0018\u00010$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0&H\u0014J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002J\u0012\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010+\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0003H\u0014J\u0010\u00101\u001a\u00020\u00112\u0006\u0010+\u001a\u00020/H\u0014J\f\u00102\u001a\u00020\u0011*\u000203H\u0002J\f\u00104\u001a\u00020\u0011*\u000203H\u0002R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker;", "Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "currentModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "translationPluginContext", "Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "manglerDesc", "Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;", "enableIdSignatures", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;Z)V", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "getFakeOverrideBuilder", "()Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "javaName", "Lorg/jetbrains/kotlin/name/Name;", "getTranslationPluginContext", "()Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "createCurrentModuleDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dependencies", "", "createModuleDeserializer", "moduleDescriptor", "klib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "declareJavaFieldStub", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "isBuiltInModule", "platformSpecificSymbol", "isCleanDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isJavaDescriptor", "JvmCurrentModuleDeserializer", "JvmModuleDeserializer", "MetadataJVMModuleDeserializer", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrLinker extends KotlinIrLinker {
    private final boolean enableIdSignatures;
    private final FakeOverrideBuilder fakeOverrideBuilder;
    private final Name javaName;
    private final JvmDescriptorMangler manglerDesc;
    private final DeclarationStubGenerator stubGenerator;
    private final TranslationPluginContext translationPluginContext;

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    public TranslationPluginContext getTranslationPluginContext() {
        return this.translationPluginContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmIrLinker(ModuleDescriptor moduleDescriptor, IrMessageLogger messageLogger, IrTypeSystemContext typeSystem, SymbolTable symbolTable, TranslationPluginContext translationPluginContext, DeclarationStubGenerator stubGenerator, JvmDescriptorMangler manglerDesc, boolean z) {
        super(moduleDescriptor, messageLogger, typeSystem.getIrBuiltIns(), symbolTable, CollectionsKt.emptyList(), null, 32, null);
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(manglerDesc, "manglerDesc");
        this.translationPluginContext = translationPluginContext;
        this.stubGenerator = stubGenerator;
        this.manglerDesc = manglerDesc;
        this.enableIdSignatures = z;
        this.fakeOverrideBuilder = new FakeOverrideBuilder(this, symbolTable, JvmIrMangler.INSTANCE, typeSystem, MapsKt.emptyMap(), PartialLinkageSupportForLinker.INSTANCE.getDISABLED(), null, null, 192, null);
        Name nameIdentifier = Name.identifier(SuffixConstants.EXTENSION_java);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.javaName = nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    public FakeOverrideBuilder getFakeOverrideBuilder() {
        return this.fakeOverrideBuilder;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected boolean isBuiltInModule(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        String strAsString = moduleDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.startsWith$default(strAsString, "<dependencies of ", false, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected IrModuleDeserializer createModuleDeserializer(ModuleDescriptor moduleDescriptor, KotlinLibrary klib, Function1<? super String, ? extends DeserializationStrategy> strategyResolver) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(strategyResolver, "strategyResolver");
        if (klib != null) {
            moduleDescriptor.getCapability(KlibModuleOrigin.INSTANCE.getCAPABILITY());
            KotlinLibrary kotlinLibrary = klib;
            KotlinAbiVersion abiVersion = klib.getVersions().getAbiVersion();
            if (abiVersion == null) {
                abiVersion = KotlinAbiVersion.INSTANCE.getCURRENT();
            }
            return new JvmModuleDeserializer(this, moduleDescriptor, kotlinLibrary, abiVersion, strategyResolver);
        }
        return new MetadataJVMModuleDeserializer(this, moduleDescriptor, CollectionsKt.emptyList());
    }

    /* JADX INFO: compiled from: JvmIrLinker.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker$JvmModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "libraryAbiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "(Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/library/IrLibrary;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;Lkotlin/jvm/functions/Function1;)V", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class JvmModuleDeserializer extends BasicIrModuleDeserializer {
        final /* synthetic */ JvmIrLinker this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmModuleDeserializer(JvmIrLinker jvmIrLinker, ModuleDescriptor moduleDescriptor, IrLibrary klib, KotlinAbiVersion libraryAbiVersion, Function1<? super String, ? extends DeserializationStrategy> strategyResolver) {
            super(jvmIrLinker, moduleDescriptor, klib, strategyResolver, libraryAbiVersion, false, false, 96, null);
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(klib, "klib");
            Intrinsics.checkNotNullParameter(libraryAbiVersion, "libraryAbiVersion");
            Intrinsics.checkNotNullParameter(strategyResolver, "strategyResolver");
            this.this$0 = jvmIrLinker;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isJavaDescriptor(DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor containingModule;
        if (declarationDescriptor instanceof PackageFragmentDescriptor) {
            return (declarationDescriptor instanceof LazyJavaPackageFragment) || ((PackageFragmentDescriptor) declarationDescriptor).getFqName().startsWith(this.javaName);
        }
        return (declarationDescriptor instanceof JavaClassDescriptor) || (declarationDescriptor instanceof JavaCallableMemberDescriptor) || ((containingModule = declarationDescriptor.getContainingModule()) != null && isJavaDescriptor(containingModule));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCleanDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof PropertyAccessorDescriptor)) {
            return declarationDescriptor instanceof DeserializedDescriptor;
        }
        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        return isCleanDescriptor(correspondingProperty);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected boolean platformSpecificSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return isJavaDescriptor(symbol.getDescriptor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField declareJavaFieldStub(IrFieldSymbol symbol) {
        DeclarationStubGenerator declarationStubGenerator = this.stubGenerator;
        boolean unboundSymbolGeneration = declarationStubGenerator.getUnboundSymbolGeneration();
        try {
            this.stubGenerator.setUnboundSymbolGeneration(true);
            return declarationStubGenerator.generateFieldStub(symbol.getDescriptor());
        } finally {
            this.stubGenerator.setUnboundSymbolGeneration(unboundSymbolGeneration);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker, org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return deserializeOrResolveDeclaration(symbol, !this.enableIdSignatures);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected IrModuleDeserializer createCurrentModuleDeserializer(IrModuleFragment moduleFragment, Collection<? extends IrModuleDeserializer> dependencies) {
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        return new JvmCurrentModuleDeserializer(this, moduleFragment, dependencies);
    }

    /* JADX INFO: compiled from: JvmIrLinker.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker$JvmCurrentModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/CurrentModuleDeserializer;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dependencies", "", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "(Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/Collection;)V", "declareIrSymbol", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class JvmCurrentModuleDeserializer extends CurrentModuleDeserializer {
        final /* synthetic */ JvmIrLinker this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmCurrentModuleDeserializer(JvmIrLinker jvmIrLinker, IrModuleFragment moduleFragment, Collection<? extends IrModuleDeserializer> dependencies) {
            super(moduleFragment, dependencies);
            Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
            Intrinsics.checkNotNullParameter(dependencies, "dependencies");
            this.this$0 = jvmIrLinker;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.CurrentModuleDeserializer, org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public void declareIrSymbol(IrSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            DeclarationDescriptor descriptor = symbol.getDescriptor();
            if (!this.this$0.isJavaDescriptor(descriptor)) {
                if (this.this$0.isCleanDescriptor(descriptor)) {
                    this.this$0.stubGenerator.generateMemberStub(descriptor);
                    return;
                } else {
                    super.declareIrSymbol(symbol);
                    return;
                }
            }
            if (symbol instanceof IrFieldSymbol) {
                this.this$0.declareJavaFieldStub((IrFieldSymbol) symbol);
            } else {
                this.this$0.stubGenerator.generateMemberStub(descriptor);
            }
        }
    }

    /* JADX INFO: compiled from: JvmIrLinker.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0096\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001a\u0010!\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker$MetadataJVMModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "dependencies", "", "(Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrLinker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/util/List;)V", "descriptorFinder", "Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "moduleDependencies", "", "getModuleDependencies", "()Ljava/util/Collection;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "contains", "", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declareIrSymbol", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializedSymbolNotFound", "", "resolveDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "tryDeserializeIrSymbol", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MetadataJVMModuleDeserializer extends IrModuleDeserializer {
        private final DescriptorByIdSignatureFinderImpl descriptorFinder;
        private final Collection<IrModuleDeserializer> moduleDependencies;
        private final IrModuleFragment moduleFragment;
        final /* synthetic */ JvmIrLinker this$0;

        /* JADX INFO: compiled from: JvmIrLinker.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[BinarySymbolData.SymbolKind.values().length];
                try {
                    iArr[BinarySymbolData.SymbolKind.CLASS_SYMBOL.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[BinarySymbolData.SymbolKind.PROPERTY_SYMBOL.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[BinarySymbolData.SymbolKind.FUNCTION_SYMBOL.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public boolean contains(IdSignature idSig) {
            Intrinsics.checkNotNullParameter(idSig, "idSig");
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MetadataJVMModuleDeserializer(JvmIrLinker jvmIrLinker, ModuleDescriptor moduleDescriptor, List<? extends IrModuleDeserializer> dependencies) {
            super(moduleDescriptor, KotlinAbiVersion.INSTANCE.getCURRENT());
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(dependencies, "dependencies");
            this.this$0 = jvmIrLinker;
            this.descriptorFinder = new DescriptorByIdSignatureFinderImpl(moduleDescriptor, jvmIrLinker.manglerDesc, DescriptorByIdSignatureFinderImpl.LookupMode.MODULE_ONLY);
            this.moduleFragment = new IrModuleFragmentImpl(moduleDescriptor, jvmIrLinker.getBuiltIns(), CollectionsKt.emptyList());
            this.moduleDependencies = dependencies;
        }

        private final DeclarationDescriptor resolveDescriptor(IdSignature idSig) {
            return this.descriptorFinder.findDescriptorBySignature(idSig);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        /* JADX INFO: renamed from: tryDeserializeIrSymbol */
        public IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
            IrClass irClassGenerateClassStub;
            Intrinsics.checkNotNullParameter(idSig, "idSig");
            Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
            DeclarationDescriptor declarationDescriptorResolveDescriptor = resolveDescriptor(idSig);
            if (declarationDescriptorResolveDescriptor == null) {
                return null;
            }
            DeclarationStubGenerator declarationStubGenerator = this.this$0.stubGenerator;
            switch (WhenMappings.$EnumSwitchMapping$0[symbolKind.ordinal()]) {
                case 1:
                    irClassGenerateClassStub = declarationStubGenerator.generateClassStub((ClassDescriptor) declarationDescriptorResolveDescriptor);
                    break;
                case 2:
                    irClassGenerateClassStub = declarationStubGenerator.generatePropertyStub((PropertyDescriptor) declarationDescriptorResolveDescriptor);
                    break;
                case 3:
                    irClassGenerateClassStub = DeclarationStubGenerator.generateFunctionStub$default(declarationStubGenerator, (FunctionDescriptor) declarationDescriptorResolveDescriptor, false, 2, null);
                    break;
                case 4:
                    irClassGenerateClassStub = declarationStubGenerator.generateConstructorStub((ClassConstructorDescriptor) declarationDescriptorResolveDescriptor);
                    break;
                case 5:
                    irClassGenerateClassStub = declarationStubGenerator.generateEnumEntryStub((ClassDescriptor) declarationDescriptorResolveDescriptor);
                    break;
                case 6:
                    irClassGenerateClassStub = declarationStubGenerator.generateTypeAliasStub((TypeAliasDescriptor) declarationDescriptorResolveDescriptor);
                    break;
                default:
                    throw new IllegalStateException(("Unexpected type " + symbolKind + " for sig " + idSig).toString());
            }
            return irClassGenerateClassStub.getSymbol();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public Void deserializedSymbolNotFound(IdSignature idSig) {
            Intrinsics.checkNotNullParameter(idSig, "idSig");
            throw new IllegalStateException(("No descriptor found for " + idSig).toString());
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public void declareIrSymbol(IrSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (symbol instanceof IrFieldSymbol) {
                this.this$0.declareJavaFieldStub((IrFieldSymbol) symbol);
            } else {
                this.this$0.stubGenerator.generateMemberStub(symbol.getDescriptor());
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public IrModuleFragment getModuleFragment() {
            return this.moduleFragment;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public Collection<IrModuleDeserializer> getModuleDependencies() {
            return this.moduleDependencies;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
        public IrModuleDeserializerKind getKind() {
            return IrModuleDeserializerKind.SYNTHETIC;
        }
    }
}
