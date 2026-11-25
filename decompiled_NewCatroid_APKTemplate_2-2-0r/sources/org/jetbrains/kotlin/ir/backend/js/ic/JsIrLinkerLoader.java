package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinkerImplKt;
import org.jetbrains.kotlin.backend.common.serialization.DeserializationStrategy;
import org.jetbrains.kotlin.backend.common.serialization.FunctionalInterfacesUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.ir.backend.js.FunctionTypeInterfacePackages;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerDesc;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfigKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.generators.TypeTranslatorImpl;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: JsIrLinkerLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0001\u001dBK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00070\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\u001c\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u001e\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0014\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00060\u0005H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrLinkerLoader;", "", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dependencyGraph", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "", "mainModuleFriends", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "stubbedSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Ljava/util/Map;Ljava/util/Collection;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Ljava/util/Set;)V", "mainLibrary", "createLinker", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrLinkerLoader$LinkerContext;", "loadedModules", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "loadIr", "Lorg/jetbrains/kotlin/ir/backend/js/ic/LoadedJsIr;", "modifiedFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileExports;", "loadAllIr", "", "loadModules", "LinkerContext", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrLinkerLoader {
    private final CompilerConfiguration compilerConfiguration;
    private final Map<KotlinLibrary, List<KotlinLibrary>> dependencyGraph;
    private final IrFactory irFactory;
    private final KotlinLibrary mainLibrary;
    private final Collection<KotlinLibrary> mainModuleFriends;
    private final Set<IdSignature> stubbedSignatures;

    /* JADX WARN: Multi-variable type inference failed */
    public JsIrLinkerLoader(CompilerConfiguration compilerConfiguration, Map<KotlinLibrary, ? extends List<? extends KotlinLibrary>> dependencyGraph, Collection<? extends KotlinLibrary> mainModuleFriends, IrFactory irFactory, Set<? extends IdSignature> stubbedSignatures) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(dependencyGraph, "dependencyGraph");
        Intrinsics.checkNotNullParameter(mainModuleFriends, "mainModuleFriends");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(stubbedSignatures, "stubbedSignatures");
        this.compilerConfiguration = compilerConfiguration;
        this.dependencyGraph = dependencyGraph;
        this.mainModuleFriends = mainModuleFriends;
        this.irFactory = irFactory;
        this.stubbedSignatures = stubbedSignatures;
        KotlinLibrary kotlinLibrary = (KotlinLibrary) CollectionsKt.lastOrNull(dependencyGraph.keySet());
        if (kotlinLibrary != null) {
            this.mainLibrary = kotlinLibrary;
        } else {
            ICUtilsKt.m7124notFoundIcError5T69zGA$default("main library", null, null, 6, null);
            throw null;
        }
    }

    /* JADX INFO: compiled from: JsIrLinkerLoader.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsIrLinkerLoader$LinkerContext;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl;", "irBuiltIns", "Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;", "linker", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl;Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;)V", "functionTypeInterfacePackages", "Lorg/jetbrains/kotlin/ir/backend/js/FunctionTypeInterfacePackages;", "getFunctionTypeInterfacePackages", "()Lorg/jetbrains/kotlin/ir/backend/js/FunctionTypeInterfacePackages;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;", "getLinker", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/psi2ir/generators/TypeTranslatorImpl;", "loadFunctionInterfacesIntoStdlib", "", "stdlibModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LinkerContext {
        private final FunctionTypeInterfacePackages functionTypeInterfacePackages;
        private final IrBuiltInsOverDescriptors irBuiltIns;
        private final JsIrLinker linker;
        private final SymbolTable symbolTable;
        private final TypeTranslatorImpl typeTranslator;

        public LinkerContext(SymbolTable symbolTable, TypeTranslatorImpl typeTranslator, IrBuiltInsOverDescriptors irBuiltIns, JsIrLinker linker) {
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
            Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
            Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
            Intrinsics.checkNotNullParameter(linker, "linker");
            this.symbolTable = symbolTable;
            this.typeTranslator = typeTranslator;
            this.irBuiltIns = irBuiltIns;
            this.linker = linker;
            this.functionTypeInterfacePackages = new FunctionTypeInterfacePackages();
        }

        public final SymbolTable getSymbolTable() {
            return this.symbolTable;
        }

        public final TypeTranslatorImpl getTypeTranslator() {
            return this.typeTranslator;
        }

        public final IrBuiltInsOverDescriptors getIrBuiltIns() {
            return this.irBuiltIns;
        }

        public final JsIrLinker getLinker() {
            return this.linker;
        }

        public final FunctionTypeInterfacePackages getFunctionTypeInterfacePackages() {
            return this.functionTypeInterfacePackages;
        }

        public final void loadFunctionInterfacesIntoStdlib(IrModuleFragment stdlibModule) {
            Intrinsics.checkNotNullParameter(stdlibModule, "stdlibModule");
            this.irBuiltIns.setFunctionFactory(new IrDescriptorBasedFunctionFactory(this.irBuiltIns, this.symbolTable, this.typeTranslator, this.functionTypeInterfacePackages.makePackageAccessor(stdlibModule), true));
        }
    }

    private final LinkerContext createLinker(Map<ModuleDescriptor, ? extends KotlinLibrary> loadedModules) {
        SymbolTable symbolTable = new SymbolTable(new IdSignatureDescriptor(JsManglerDesc.INSTANCE), this.irFactory, null, 4, null);
        ModuleDescriptor moduleDescriptor = (ModuleDescriptor) CollectionsKt.last(loadedModules.keySet());
        TypeTranslatorImpl typeTranslatorImpl = new TypeTranslatorImpl(symbolTable, CommonConfigurationKeysKt.getLanguageVersionSettings(this.compilerConfiguration), moduleDescriptor, null, false, null, null, false, 248, null);
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) this.compilerConfiguration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        if (errorTolerancePolicy == null) {
            errorTolerancePolicy = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
        }
        Intrinsics.checkNotNull(errorTolerancePolicy);
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = new IrBuiltInsOverDescriptors(moduleDescriptor.getBuiltIns(), typeTranslatorImpl, symbolTable);
        IrMessageLogger irMessageLogger = IrMessageLoggerKt.getIrMessageLogger(this.compilerConfiguration);
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors2 = irBuiltInsOverDescriptors;
        PartialLinkageSupportForLinker partialLinkageSupportForLinkerCreatePartialLinkageSupportForLinker = PartialLinkageSupportForLinkerImplKt.createPartialLinkageSupportForLinker(PartialLinkageConfigKt.getPartialLinkageConfig(this.compilerConfiguration), errorTolerancePolicy.getAllowErrors(), irBuiltInsOverDescriptors2, irMessageLogger);
        String uniqueName = KotlinLibraryKt.getUniqueName(this.mainLibrary);
        Collection<KotlinLibrary> collection = this.mainModuleFriends;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(KotlinLibraryKt.getUniqueName((KotlinLibrary) it.next()));
        }
        return new LinkerContext(symbolTable, typeTranslatorImpl, irBuiltInsOverDescriptors, new JsIrLinker(null, irMessageLogger, irBuiltInsOverDescriptors2, symbolTable, partialLinkageSupportForLinkerCreatePartialLinkageSupportForLinker, null, null, MapsKt.mapOf(TuplesKt.to(uniqueName, arrayList)), null, 320, null));
    }

    private final Map<ModuleDescriptor, KotlinLibrary> loadModules() {
        HashMap map = new HashMap();
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Set<KotlinLibrary> setKeySet = this.dependencyGraph.keySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setKeySet, 10)), 16));
        for (Object obj : setKeySet) {
            linkedHashMap.put(loadModules$getModuleDescriptor(map, this, objectRef, (KotlinLibrary) obj), obj);
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            ((ModuleDescriptorImpl) ((Map.Entry) it.next()).getKey()).setDependencies(CollectionsKt.toList(linkedHashMap.keySet()));
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList.add(TuplesKt.to(entry.getKey(), entry.getValue()));
        }
        return MapsKt.toMap(arrayList);
    }

    /* JADX WARN: Type inference failed for: r11v7, types: [T, java.lang.Object, org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl] */
    private static final ModuleDescriptorImpl loadModules$getModuleDescriptor(HashMap<KotlinLibrary, ModuleDescriptorImpl> map, JsIrLinkerLoader jsIrLinkerLoader, Ref.ObjectRef<ModuleDescriptorImpl> objectRef, KotlinLibrary kotlinLibrary) {
        HashMap<KotlinLibrary, ModuleDescriptorImpl> map2 = map;
        if (map2.containsKey(kotlinLibrary)) {
            return (ModuleDescriptorImpl) MapsKt.getValue(map2, kotlinLibrary);
        }
        boolean zIsEmpty = KotlinLibraryKt.getUnresolvedDependencies(kotlinLibrary).isEmpty();
        LookupTracker.DO_NOTHING do_nothing = LookupTracker.DO_NOTHING.INSTANCE;
        KlibMetadataModuleDescriptorFactory defaultDeserializedDescriptorFactory = KlibKt.getJsFactories().getDefaultDeserializedDescriptorFactory();
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(jsIrLinkerLoader.compilerConfiguration);
        StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
        Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        ModuleDescriptorImpl moduleDescriptorImpl = objectRef.element;
        ?? CreateDescriptorOptionalBuiltIns = defaultDeserializedDescriptorFactory.createDescriptorOptionalBuiltIns(kotlinLibrary, languageVersionSettings, NO_LOCKS, moduleDescriptorImpl != null ? moduleDescriptorImpl.getBuiltIns() : null, null, do_nothing);
        if (zIsEmpty) {
            objectRef.element = CreateDescriptorOptionalBuiltIns;
        }
        map2.put(kotlinLibrary, CreateDescriptorOptionalBuiltIns);
        return CreateDescriptorOptionalBuiltIns;
    }

    public static /* synthetic */ LoadedJsIr loadIr$default(JsIrLinkerLoader jsIrLinkerLoader, KotlinSourceFileMap kotlinSourceFileMap, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return jsIrLinkerLoader.loadIr(kotlinSourceFileMap, z);
    }

    public final LoadedJsIr loadIr(KotlinSourceFileMap<? extends KotlinSourceFileExports> modifiedFiles, boolean loadAllIr) {
        Intrinsics.checkNotNullParameter(modifiedFiles, "modifiedFiles");
        Map<ModuleDescriptor, KotlinLibrary> mapLoadModules = loadModules();
        LinkerContext linkerContextCreateLinker = createLinker(mapLoadModules);
        Set<Map.Entry<ModuleDescriptor, KotlinLibrary>> setEntrySet = mapLoadModules.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ModuleDescriptor moduleDescriptor = (ModuleDescriptor) entry.getKey();
            KotlinLibrary kotlinLibrary = (KotlinLibrary) entry.getValue();
            String strM7151constructorimpl = KotlinLibraryFile.m7151constructorimpl(kotlinLibrary);
            final DeserializationStrategy deserializationStrategy = (loadAllIr || Intrinsics.areEqual(kotlinLibrary, this.mainLibrary)) ? DeserializationStrategy.ALL : DeserializationStrategy.EXPLICITLY_EXPORTED;
            final Map<KotlinSourceFile, ? extends KotlinSourceFileExports> mapM7177get5h9WozY = modifiedFiles.m7177get5h9WozY(strM7151constructorimpl);
            if (mapM7177get5h9WozY == null) {
                mapM7177get5h9WozY = MapsKt.emptyMap();
            }
            Pair pair = TuplesKt.to(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl), KotlinIrLinker.deserializeIrModuleHeader$default(linkerContextCreateLinker.getLinker(), moduleDescriptor, kotlinLibrary, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsIrLinkerLoader$loadIr$irModules$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final DeserializationStrategy invoke(String it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return mapM7177get5h9WozY.containsKey(KotlinSourceFile.m7163boximpl(KotlinSourceFile.m7164constructorimpl(it2))) ? deserializationStrategy : DeserializationStrategy.WITH_INLINE_BODIES;
                }
            }, null, 8, null));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        String strM7151constructorimpl2 = KotlinLibraryFile.m7151constructorimpl(this.mainLibrary);
        IrModuleFragment irModuleFragment = (IrModuleFragment) linkedHashMap.get(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl2));
        if (irModuleFragment == null) {
            ICUtilsKt.m7124notFoundIcError5T69zGA$default("main module fragment", strM7151constructorimpl2, null, 4, null);
            throw null;
        }
        linkerContextCreateLinker.loadFunctionInterfacesIntoStdlib(ICUtilsKt.findStdlib(irModuleFragment, linkedHashMap).component2());
        linkerContextCreateLinker.getLinker().init(null, CollectionsKt.emptyList());
        if (!loadAllIr) {
            Iterator it2 = modifiedFiles.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                String strM7157unboximpl = ((KotlinLibraryFile) entry2.getKey()).m7157unboximpl();
                Map map = (Map) entry2.getValue();
                IrModuleFragment irModuleFragment2 = (IrModuleFragment) linkedHashMap.get(KotlinLibraryFile.m7149boximpl(strM7157unboximpl));
                if (irModuleFragment2 == null) {
                    ICUtilsKt.m7124notFoundIcError5T69zGA$default("loading fragment", strM7157unboximpl, null, 4, null);
                    throw null;
                }
                IrModuleDeserializer irModuleDeserializerModuleDeserializer = linkerContextCreateLinker.getLinker().moduleDeserializer(irModuleFragment2.getDescriptor());
                Iterator it3 = map.values().iterator();
                while (it3.hasNext()) {
                    for (IdSignature idSignature : ((KotlinSourceFileExports) it3.next()).getExportedSignatures()) {
                        if (FunctionalInterfacesUtilsKt.checkIsFunctionInterface(idSignature)) {
                            irModuleDeserializerModuleDeserializer.mo6122tryDeserializeIrSymbol(idSignature.topLevelSignature(), BinarySymbolData.SymbolKind.CLASS_SYMBOL);
                        } else if (irModuleDeserializerModuleDeserializer.contains(idSignature)) {
                            irModuleDeserializerModuleDeserializer.addModuleReachableTopLevel(idSignature);
                        }
                    }
                }
                for (IdSignature idSignature2 : this.stubbedSignatures) {
                    if (irModuleDeserializerModuleDeserializer.contains(idSignature2)) {
                        irModuleDeserializerModuleDeserializer.addModuleReachableTopLevel(idSignature2);
                    }
                }
            }
        }
        LoadedJsIr loadedJsIr = new LoadedJsIr(linkedHashMap, linkerContextCreateLinker.getLinker(), linkerContextCreateLinker.getFunctionTypeInterfacePackages());
        loadedJsIr.loadUnboundSymbols();
        return loadedJsIr;
    }
}
