package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.backend.common.serialization.BasicIrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.CurrentModuleWithICDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.DeserializationStrategy;
import org.jetbrains.kotlin.backend.common.serialization.ICData;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrSymbolDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;

/* JADX INFO: compiled from: JsIrLinker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u000267Bm\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u001a\b\u0002\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00130\u0011\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016J\u001e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020*0\u0013H\u0014J.\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020\u00032\b\u0010/\u001a\u0004\u0018\u0001002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020302H\u0014J\u0010\u00104\u001a\u00020%2\u0006\u0010.\u001a\u00020\u0003H\u0014J\u000e\u00105\u001a\u00020*2\u0006\u0010.\u001a\u00020\u0003R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020%*\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "currentModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "partialLinkageSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "translationPluginContext", "Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "icData", "Lorg/jetbrains/kotlin/backend/common/serialization/ICData;", "friendModules", "", "", "", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;Lorg/jetbrains/kotlin/backend/common/serialization/ICData;Ljava/util/Map;Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;)V", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "getFakeOverrideBuilder", "()Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "modules", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModules", "()Ljava/util/List;", "getPartialLinkageSupport", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "getTranslationPluginContext", "()Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "libContainsErrorCode", "", "Lorg/jetbrains/kotlin/library/IrLibrary;", "getLibContainsErrorCode", "(Lorg/jetbrains/kotlin/library/IrLibrary;)Z", "createCurrentModuleDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "moduleFragment", "dependencies", "createModuleDeserializer", "moduleDescriptor", "klib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "strategyResolver", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "isBuiltInModule", "moduleDeserializer", "JsFePluginContext", "JsModuleDeserializer", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrLinker extends KotlinIrLinker {
    private final ModuleDescriptor currentModule;
    private final FakeOverrideBuilder fakeOverrideBuilder;
    private final ICData icData;
    private final PartialLinkageSupportForLinker partialLinkageSupport;
    private final DeclarationStubGenerator stubGenerator;
    private final TranslationPluginContext translationPluginContext;

    public final IrModuleDeserializer moduleDeserializer(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        IrModuleDeserializer irModuleDeserializer = getDeserializersForModules().get(moduleDescriptor.getShortName().asString());
        if (irModuleDeserializer != null) {
            return irModuleDeserializer;
        }
        throw new IllegalStateException(("Deserializer for " + moduleDescriptor + " not found").toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    public PartialLinkageSupportForLinker getPartialLinkageSupport() {
        return this.partialLinkageSupport;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    public TranslationPluginContext getTranslationPluginContext() {
        return this.translationPluginContext;
    }

    public /* synthetic */ JsIrLinker(ModuleDescriptor moduleDescriptor, IrMessageLogger irMessageLogger, IrBuiltIns irBuiltIns, SymbolTable symbolTable, PartialLinkageSupportForLinker partialLinkageSupportForLinker, TranslationPluginContext translationPluginContext, ICData iCData, Map map, DeclarationStubGenerator declarationStubGenerator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, irMessageLogger, irBuiltIns, symbolTable, partialLinkageSupportForLinker, translationPluginContext, (i & 64) != 0 ? null : iCData, (i & 128) != 0 ? MapsKt.emptyMap() : map, (i & 256) != 0 ? null : declarationStubGenerator);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsIrLinker(ModuleDescriptor moduleDescriptor, IrMessageLogger messageLogger, IrBuiltIns builtIns, SymbolTable symbolTable, PartialLinkageSupportForLinker partialLinkageSupport, TranslationPluginContext translationPluginContext, ICData iCData, Map<String, ? extends Collection<String>> friendModules, DeclarationStubGenerator declarationStubGenerator) {
        super(moduleDescriptor, messageLogger, builtIns, symbolTable, CollectionsKt.emptyList(), new Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker.1
            @Override // kotlin.jvm.functions.Function3
            public final IrSymbol invoke(IrSymbolDeserializer irSymbolDeserializer, IrSymbol symbol, IdSignature idSig) {
                Intrinsics.checkNotNullParameter(irSymbolDeserializer, "$this$null");
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(idSig, "idSig");
                if (idSig.isLocal()) {
                    symbol.setPrivateSignature(new IdSignature.CompositeSignature(new IdSignature.FileSignature(irSymbolDeserializer.getFileSymbol()), idSig));
                }
                return symbol;
            }
        });
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(partialLinkageSupport, "partialLinkageSupport");
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        this.currentModule = moduleDescriptor;
        this.partialLinkageSupport = partialLinkageSupport;
        this.translationPluginContext = translationPluginContext;
        this.icData = iCData;
        this.stubGenerator = declarationStubGenerator;
        this.fakeOverrideBuilder = new FakeOverrideBuilder(this, symbolTable, JsManglerIr.INSTANCE, new IrTypeSystemContextImpl(builtIns), friendModules, getPartialLinkageSupport(), null, null, 192, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    public FakeOverrideBuilder getFakeOverrideBuilder() {
        return this.fakeOverrideBuilder;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected boolean isBuiltInModule(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        return moduleDescriptor == moduleDescriptor.getBuiltIns().getBuiltInsModule();
    }

    private final boolean getLibContainsErrorCode(IrLibrary irLibrary) {
        return (irLibrary instanceof KotlinLibrary) && KotlinLibraryKt.getContainsErrorCode((KotlinLibrary) irLibrary);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected IrModuleDeserializer createModuleDeserializer(ModuleDescriptor moduleDescriptor, KotlinLibrary klib, Function1<? super String, ? extends DeserializationStrategy> strategyResolver) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(strategyResolver, "strategyResolver");
        if (klib == null) {
            throw new IllegalArgumentException("Expecting kotlin library".toString());
        }
        KotlinAbiVersion abiVersion = klib.getVersions().getAbiVersion();
        if (abiVersion == null) {
            abiVersion = KotlinAbiVersion.INSTANCE.getCURRENT();
        }
        KotlinAbiVersion kotlinAbiVersion = abiVersion;
        DeclarationStubGenerator declarationStubGenerator = this.stubGenerator;
        if (declarationStubGenerator == null) {
            KotlinLibrary kotlinLibrary = klib;
            return new JsModuleDeserializer(this, moduleDescriptor, kotlinLibrary, strategyResolver, kotlinAbiVersion, getLibContainsErrorCode(kotlinLibrary));
        }
        return new JsLazyIrModuleDeserializer(moduleDescriptor, kotlinAbiVersion, getBuiltIns(), declarationStubGenerator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JsIrLinker.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker$JsModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "libraryAbiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "allowErrorCode", "", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/library/IrLibrary;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;Z)V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class JsModuleDeserializer extends BasicIrModuleDeserializer {
        final /* synthetic */ JsIrLinker this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JsModuleDeserializer(JsIrLinker jsIrLinker, ModuleDescriptor moduleDescriptor, IrLibrary klib, Function1<? super String, ? extends DeserializationStrategy> strategyResolver, KotlinAbiVersion libraryAbiVersion, boolean z) {
            super(jsIrLinker, moduleDescriptor, klib, strategyResolver, libraryAbiVersion, z, false);
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(klib, "klib");
            Intrinsics.checkNotNullParameter(strategyResolver, "strategyResolver");
            Intrinsics.checkNotNullParameter(libraryAbiVersion, "libraryAbiVersion");
            this.this$0 = jsIrLinker;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker
    protected IrModuleDeserializer createCurrentModuleDeserializer(IrModuleFragment moduleFragment, Collection<? extends IrModuleDeserializer> dependencies) {
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        final IrModuleDeserializer irModuleDeserializerCreateCurrentModuleDeserializer = super.createCurrentModuleDeserializer(moduleFragment, dependencies);
        final ICData iCData = this.icData;
        return iCData != null ? new CurrentModuleWithICDeserializer(irModuleDeserializerCreateCurrentModuleDeserializer, getSymbolTable(), getBuiltIns(), iCData.getIcData(), new Function1<IrLibrary, IrModuleDeserializer>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker$createCurrentModuleDeserializer$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrModuleDeserializer invoke(IrLibrary lib) {
                Intrinsics.checkNotNullParameter(lib, "lib");
                return new JsIrLinker.JsModuleDeserializer(this.this$0, irModuleDeserializerCreateCurrentModuleDeserializer.getModuleDescriptor(), lib, irModuleDeserializerCreateCurrentModuleDeserializer.getStrategyResolver(), KotlinAbiVersion.INSTANCE.getCURRENT(), iCData.getContainsErrorCode());
            }
        }) : irModuleDeserializerCreateCurrentModuleDeserializer;
    }

    public final List<IrModuleFragment> getModules() {
        Collection<IrModuleDeserializer> collectionValues = getDeserializersForModules().values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Collection<IrModuleDeserializer> collection = collectionValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrModuleDeserializer) it.next()).getModuleFragment());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((IrModuleFragment) obj).getDescriptor() != this.currentModule) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: compiled from: JsIrLinker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker$JsFePluginContext;", "Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JsFePluginContext implements TranslationPluginContext {
        private final IrBuiltIns irBuiltIns;
        private final ModuleDescriptor moduleDescriptor;
        private final ReferenceSymbolTable symbolTable;
        private final TypeTranslator typeTranslator;

        public JsFePluginContext(ModuleDescriptor moduleDescriptor, ReferenceSymbolTable symbolTable, TypeTranslator typeTranslator, IrBuiltIns irBuiltIns) {
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
            Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
            Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
            this.moduleDescriptor = moduleDescriptor;
            this.symbolTable = symbolTable;
            this.typeTranslator = typeTranslator;
            this.irBuiltIns = irBuiltIns;
        }

        @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContext
        public IrFactory getIrFactory() {
            return TranslationPluginContext.DefaultImpls.getIrFactory(this);
        }

        @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
        public ModuleDescriptor getModuleDescriptor() {
            return this.moduleDescriptor;
        }

        @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
        public ReferenceSymbolTable getSymbolTable() {
            return this.symbolTable;
        }

        @Override // org.jetbrains.kotlin.ir.builders.TranslationPluginContext
        public TypeTranslator getTypeTranslator() {
            return this.typeTranslator;
        }

        @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorContextInterface
        public IrBuiltIns getIrBuiltIns() {
            return this.irBuiltIns;
        }
    }
}
