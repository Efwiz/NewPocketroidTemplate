package org.jetbrains.kotlin.backend.common.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.proto.Actual;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;

/* JADX INFO: compiled from: BasicIrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001ZBM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f¢\u0006\u0002\u0010\u0011J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u000201H\u0016J\u0011\u0010:\u001a\u00020\u000f2\u0006\u00109\u001a\u000201H\u0096\u0002J8\u0010;\u001a\u00020\u00142\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u001b2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00012\u0006\u0010D\u001a\u00020\u000fH\u0002J\b\u0010E\u001a\u000208H\u0016J\u0010\u0010F\u001a\u00020G2\u0006\u00109\u001a\u000201H\u0016J\u000e\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001c0IH\u0016J\u0010\u0010J\u001a\u0002082\u0006\u0010K\u001a\u00020\u0001H\u0016J\u0018\u0010L\u001a\u00020M2\u0006\u0010>\u001a\u00020\u001b2\u0006\u0010N\u001a\u000201H\u0016J\u0018\u0010O\u001a\u00020P2\u0006\u0010>\u001a\u00020\u001b2\u0006\u0010N\u001a\u000201H\u0016J\u0010\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\nH\u0016J\u001a\u0010T\u001a\u0004\u0018\u00010U2\u0006\u00109\u001a\u0002012\u0006\u0010V\u001a\u00020WH\u0016J\f\u0010X\u001a\u000208*\u00020YH\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138DX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R!\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u00138VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b&\u0010\u0016R\u0012\u0010)\u001a\u00060*R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\u00020,X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R0\u0010/\u001a\u001e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u001400j\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0014`2X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "linker", "Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "libraryAbiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "containsErrorCode", "", "shouldSaveDeserializationState", "(Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/library/IrLibrary;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;ZZ)V", "fileDeserializationStates", "", "Lorg/jetbrains/kotlin/backend/common/serialization/FileDeserializationState;", "getFileDeserializationStates", "()Ljava/util/List;", "setFileDeserializationStates", "(Ljava/util/List;)V", "fileToDeserializerMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKlib", "()Lorg/jetbrains/kotlin/library/IrLibrary;", "getLinker", "()Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "moduleDependencies", "getModuleDependencies", "moduleDependencies$delegate", "Lkotlin/Lazy;", "moduleDeserializationState", "Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer$ModuleDeserializationState;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "moduleReversedFileIndex", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/HashMap;", "getModuleReversedFileIndex", "()Ljava/util/HashMap;", "getStrategyResolver", "()Lkotlin/jvm/functions/Function1;", "addModuleReachableTopLevel", "", "idSig", "contains", "deserializeIrFile", "fileProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;", "file", "fileReader", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;", "fileIndex", "", "moduleDeserializer", "allowErrorNodes", "deserializeReachableDeclarations", "deserializedSymbolNotFound", "", "fileDeserializers", "", ConfigConstants.CONFIG_INIT_SECTION, MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "referencePropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "idSignature", "referenceSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "signatureDeserializerForFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", PsiTreeChangeEvent.PROP_FILE_NAME, "tryDeserializeIrSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "deserializeExpectActualMapping", "Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "ModuleDeserializationState", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BasicIrModuleDeserializer extends IrModuleDeserializer {
    private final boolean containsErrorCode;
    private List<FileDeserializationState> fileDeserializationStates;
    private final Map<IrFile, IrFileDeserializer> fileToDeserializerMap;
    private final IrLibrary klib;
    private final KotlinIrLinker linker;

    /* JADX INFO: renamed from: moduleDependencies$delegate, reason: from kotlin metadata */
    private final Lazy moduleDependencies;
    private final ModuleDeserializationState moduleDeserializationState;
    private final IrModuleFragment moduleFragment;
    private final HashMap<IdSignature, FileDeserializationState> moduleReversedFileIndex;
    private final boolean shouldSaveDeserializationState;
    private final Function1<String, DeserializationStrategy> strategyResolver;

    protected final List<FileDeserializationState> getFileDeserializationStates() {
        if (this.shouldSaveDeserializationState) {
            return this.fileDeserializationStates;
        }
        throw new IllegalStateException("File deserialization state are not cached inside the instance because `shouldSaveDeserializationState` was set as `false`".toString());
    }

    public /* synthetic */ BasicIrModuleDeserializer(KotlinIrLinker kotlinIrLinker, ModuleDescriptor moduleDescriptor, IrLibrary irLibrary, Function1 function1, KotlinAbiVersion kotlinAbiVersion, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinIrLinker, moduleDescriptor, irLibrary, function1, kotlinAbiVersion, (i & 32) != 0 ? false : z, (i & 64) != 0 ? true : z2);
    }

    public final KotlinIrLinker getLinker() {
        return this.linker;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrLibrary getKlib() {
        return this.klib;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Function1<String, DeserializationStrategy> getStrategyResolver() {
        return this.strategyResolver;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BasicIrModuleDeserializer(KotlinIrLinker linker, final ModuleDescriptor moduleDescriptor, IrLibrary klib, Function1<? super String, ? extends DeserializationStrategy> strategyResolver, KotlinAbiVersion libraryAbiVersion, boolean z, boolean z2) {
        super(moduleDescriptor, libraryAbiVersion);
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(klib, "klib");
        Intrinsics.checkNotNullParameter(strategyResolver, "strategyResolver");
        Intrinsics.checkNotNullParameter(libraryAbiVersion, "libraryAbiVersion");
        this.linker = linker;
        this.klib = klib;
        this.strategyResolver = strategyResolver;
        this.containsErrorCode = z;
        this.shouldSaveDeserializationState = z2;
        this.fileToDeserializerMap = new LinkedHashMap();
        this.moduleDeserializationState = new ModuleDeserializationState();
        this.fileDeserializationStates = CollectionsKt.emptyList();
        this.moduleReversedFileIndex = new HashMap<>();
        this.moduleDependencies = LazyKt.lazy(new Function0<List<? extends IrModuleDeserializer>>() { // from class: org.jetbrains.kotlin.backend.common.serialization.BasicIrModuleDeserializer$moduleDependencies$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrModuleDeserializer> invoke() {
                List<ModuleDescriptor> allDependencyModules = moduleDescriptor.getAllDependencyModules();
                ModuleDescriptor moduleDescriptor2 = moduleDescriptor;
                ArrayList arrayList = new ArrayList();
                for (Object obj : allDependencyModules) {
                    if (!Intrinsics.areEqual((ModuleDescriptor) obj, moduleDescriptor2)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                BasicIrModuleDeserializer basicIrModuleDeserializer = this;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(basicIrModuleDeserializer.getLinker().resolveModuleDeserializer((ModuleDescriptor) it.next(), null));
                }
                return arrayList3;
            }
        });
        this.moduleFragment = new IrModuleFragmentImpl(moduleDescriptor, linker.getBuiltIns(), CollectionsKt.emptyList());
    }

    protected final void setFileDeserializationStates(List<FileDeserializationState> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.fileDeserializationStates = list;
    }

    protected final HashMap<IdSignature, FileDeserializationState> getModuleReversedFileIndex() {
        return this.moduleReversedFileIndex;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public List<IrModuleDeserializer> getModuleDependencies() {
        return (List) this.moduleDependencies.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrFileDeserializer> fileDeserializers() {
        Collection<IrFileDeserializer> collectionValues = this.fileToDeserializerMap.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionValues) {
            if (!getStrategyResolver().invoke(((IrFileDeserializer) obj).getFile().getFileEntry().getName()).getOnDemand()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void init(IrModuleDeserializer delegate) throws IOException {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        int iFileCount = getKlib().fileCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < iFileCount; i++) {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrFile from = org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(getKlib().file(i)), ExtensionRegistryLite.newInstance());
            IrLibraryFileFromBytes irLibraryFileFromBytes = new IrLibraryFileFromBytes(new IrKlibBytesSource(getKlib(), i));
            IrModuleFragment moduleFragment = getModuleFragment();
            Intrinsics.checkNotNull(from);
            IrFile irFileCreateFile = IrFileDeserializerKt.createFile(irLibraryFileFromBytes, moduleFragment, from);
            arrayList.add(deserializeIrFile(from, irFileCreateFile, irLibraryFileFromBytes, i, delegate, this.containsErrorCode));
            if (!getStrategyResolver().invoke(irFileCreateFile.getFileEntry().getName()).getOnDemand()) {
                getModuleFragment().getFiles().add(irFileCreateFile);
            }
        }
        if (this.shouldSaveDeserializationState) {
            this.fileDeserializationStates = arrayList;
        }
        Iterator<T> it = this.fileToDeserializerMap.values().iterator();
        while (it.hasNext()) {
            deserializeExpectActualMapping(((IrFileDeserializer) it.next()).getSymbolDeserializer());
        }
    }

    private final void deserializeExpectActualMapping(IrSymbolDeserializer irSymbolDeserializer) {
        for (Actual actual : irSymbolDeserializer.getActuals()) {
            long jM6140parseSymbolData9x8F8T0 = irSymbolDeserializer.m6140parseSymbolData9x8F8T0(actual.getExpectSymbol());
            long jM6140parseSymbolData9x8F8T1 = irSymbolDeserializer.m6140parseSymbolData9x8F8T0(actual.getActualSymbol());
            IdSignature idSignatureDeserializeIdSignature = irSymbolDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(jM6140parseSymbolData9x8F8T0));
            IdSignature idSignatureDeserializeIdSignature2 = irSymbolDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(jM6140parseSymbolData9x8F8T1));
            this.linker.getExpectIdSignatureToActualIdSignature().get(idSignatureDeserializeIdSignature);
            this.linker.getExpectIdSignatureToActualIdSignature().put(idSignatureDeserializeIdSignature, idSignatureDeserializeIdSignature2);
            IrModuleDeserializer irModuleDeserializerFindModuleDeserializerForTopLevelId = BasicIrModuleDeserializerKt.findModuleDeserializerForTopLevelId(this, idSignatureDeserializeIdSignature2);
            if (irModuleDeserializerFindModuleDeserializerForTopLevelId != null) {
                this.linker.getTopLevelActualIdSignatureToModuleDeserializer().put(idSignatureDeserializeIdSignature2, irModuleDeserializerFindModuleDeserializerForTopLevelId);
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrSimpleFunctionSymbol referenceSimpleFunctionByLocalSignature(IrFile file, IdSignature idSignature) {
        IrSymbolDeserializer symbolDeserializer;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunctionByLocalSignature;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        IrFileDeserializer irFileDeserializer = this.fileToDeserializerMap.get(file);
        if (irFileDeserializer == null || (symbolDeserializer = irFileDeserializer.getSymbolDeserializer()) == null || (irSimpleFunctionSymbolReferenceSimpleFunctionByLocalSignature = symbolDeserializer.referenceSimpleFunctionByLocalSignature(idSignature)) == null) {
            throw new IllegalStateException(("No deserializer for file " + file + " in module " + getModuleDescriptor().getShortName()).toString());
        }
        return irSimpleFunctionSymbolReferenceSimpleFunctionByLocalSignature;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrPropertySymbol referencePropertyByLocalSignature(IrFile file, IdSignature idSignature) {
        IrSymbolDeserializer symbolDeserializer;
        IrPropertySymbol irPropertySymbolReferencePropertyByLocalSignature;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        IrFileDeserializer irFileDeserializer = this.fileToDeserializerMap.get(file);
        if (irFileDeserializer == null || (symbolDeserializer = irFileDeserializer.getSymbolDeserializer()) == null || (irPropertySymbolReferencePropertyByLocalSignature = symbolDeserializer.referencePropertyByLocalSignature(idSignature)) == null) {
            throw new IllegalStateException(("No deserializer for file " + file + " in module " + getModuleDescriptor().getShortName()).toString());
        }
        return irPropertySymbolReferencePropertyByLocalSignature;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public boolean contains(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        return this.moduleReversedFileIndex.containsKey(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    /* JADX INFO: renamed from: tryDeserializeIrSymbol */
    public IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        IdSignature idSignature = idSig.topLevelSignature();
        FileDeserializationState fileDeserializationState = this.moduleReversedFileIndex.get(idSignature);
        if (fileDeserializationState == null) {
            return null;
        }
        fileDeserializationState.addIdSignature(idSignature);
        this.moduleDeserializationState.enqueueFile(fileDeserializationState);
        return fileDeserializationState.getFileDeserializer().getSymbolDeserializer().deserializeIrSymbol(idSig, symbolKind);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Void deserializedSymbolNotFound(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        throw new IllegalStateException(("No file for " + idSig.topLevelSignature() + " (@ " + idSig + ") in module " + getModuleDescriptor()).toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleFragment getModuleFragment() {
        return this.moduleFragment;
    }

    private final FileDeserializationState deserializeIrFile(org.jetbrains.kotlin.backend.common.serialization.proto.IrFile fileProto, IrFile file, IrLibraryFileFromBytes fileReader, int fileIndex, IrModuleDeserializer moduleDeserializer, boolean allowErrorNodes) {
        DeserializationStrategy deserializationStrategyInvoke = getStrategyResolver().invoke(file.getFileEntry().getName());
        FileDeserializationState fileDeserializationState = new FileDeserializationState(this.linker, fileIndex, file, fileReader, fileProto, deserializationStrategyInvoke.getNeedBodies(), allowErrorNodes, deserializationStrategyInvoke.getInlineBodies(), moduleDeserializer);
        this.fileToDeserializerMap.put(file, fileDeserializationState.getFileDeserializer());
        Iterator<T> it = fileDeserializationState.getFileDeserializer().getReversedSignatureIndex().keySet().iterator();
        while (it.hasNext()) {
            this.moduleReversedFileIndex.putIfAbsent((IdSignature) it.next(), fileDeserializationState);
        }
        if (deserializationStrategyInvoke.getTheWholeWorld()) {
            fileDeserializationState.enqueueAllDeclarations();
        }
        if (deserializationStrategyInvoke.getTheWholeWorld() || deserializationStrategyInvoke.getExplicitlyExported()) {
            this.moduleDeserializationState.enqueueFile(fileDeserializationState);
        }
        return fileDeserializationState;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void addModuleReachableTopLevel(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        this.moduleDeserializationState.addIdSignature(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void deserializeReachableDeclarations() {
        this.moduleDeserializationState.deserializeReachableDeclarations();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IdSignatureDeserializer signatureDeserializerForFile(String fileName) {
        Object next;
        IrFileDeserializer irFileDeserializer;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Iterator<T> it = this.fileToDeserializerMap.entrySet().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrFile) ((Map.Entry) next).getKey()).getFileEntry().getName(), fileName));
        Map.Entry entry = (Map.Entry) next;
        if (entry == null || (irFileDeserializer = (IrFileDeserializer) entry.getValue()) == null) {
            throw new IllegalStateException(("No file deserializer for " + fileName).toString());
        }
        return irFileDeserializer.getSymbolDeserializer().getSignatureDeserializer();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleDeserializerKind getKind() {
        return IrModuleDeserializerKind.DESERIALIZED;
    }

    /* JADX INFO: compiled from: BasicIrModuleDeserializer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u0007J\u000e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer$ModuleDeserializationState;", "", "(Lorg/jetbrains/kotlin/backend/common/serialization/BasicIrModuleDeserializer;)V", "filesWithPendingTopLevels", "", "Lorg/jetbrains/kotlin/backend/common/serialization/FileDeserializationState;", "addIdSignature", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "deserializeReachableDeclarations", "enqueueFile", "fileDeserializationState", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ModuleDeserializationState {
        private final Set<FileDeserializationState> filesWithPendingTopLevels = new LinkedHashSet();

        public ModuleDeserializationState() {
        }

        public final void enqueueFile(FileDeserializationState fileDeserializationState) {
            Intrinsics.checkNotNullParameter(fileDeserializationState, "fileDeserializationState");
            this.filesWithPendingTopLevels.add(fileDeserializationState);
            BasicIrModuleDeserializer.this.getLinker().getModulesWithReachableTopLevels().add(BasicIrModuleDeserializer.this);
        }

        public final void addIdSignature(IdSignature key) {
            Intrinsics.checkNotNullParameter(key, "key");
            FileDeserializationState fileDeserializationState = BasicIrModuleDeserializer.this.getModuleReversedFileIndex().get(key);
            if (fileDeserializationState == null) {
                throw new IllegalStateException(("No file found for key " + key).toString());
            }
            fileDeserializationState.addIdSignature(key);
            enqueueFile(fileDeserializationState);
        }

        public final void deserializeReachableDeclarations() {
            while (!this.filesWithPendingTopLevels.isEmpty()) {
                FileDeserializationState fileDeserializationState = (FileDeserializationState) CollectionsKt.first(this.filesWithPendingTopLevels);
                fileDeserializationState.getFileDeserializer().deserializeFileImplicitDataIfFirstUse();
                fileDeserializationState.deserializeAllFileReachableTopLevel();
                this.filesWithPendingTopLevels.remove(fileDeserializationState);
            }
        }

        public String toString() {
            return BasicIrModuleDeserializer.this.getKlib().toString();
        }
    }
}
