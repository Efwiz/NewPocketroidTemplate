package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.linkage.issues.ChecksKt;
import org.jetbrains.kotlin.backend.common.serialization.IrFileDeserializer;
import org.jetbrains.kotlin.ir.backend.js.FunctionTypeInterfacePackages;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsIrLinkerLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B)\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\u0015\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÂ\u0003J\t\u0010\u0019\u001a\u00020\tHÂ\u0003J3\u0010\u001a\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u001e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001f\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b \u0010!J\t\u0010\"\u001a\u00020#HÖ\u0001J\u0006\u0010$\u001a\u00020%J\t\u0010&\u001a\u00020'HÖ\u0001R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR6\u0010\r\u001a*\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000ej\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/LoadedJsIr;", "", "loadedFragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "linker", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "functionTypeInterfacePackages", "Lorg/jetbrains/kotlin/ir/backend/js/FunctionTypeInterfacePackages;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;Lorg/jetbrains/kotlin/ir/backend/js/FunctionTypeInterfacePackages;)V", "getLoadedFragments", "()Ljava/util/Map;", "signatureProvidersImpl", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider;", "Lkotlin/collections/HashMap;", "collectSignatureProviders", "irModule", "collectSymbolsReplacedWithStubs", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "getSignatureProvidersForLib", "lib", "getSignatureProvidersForLib-5h9WozY", "(Ljava/lang/String;)Ljava/util/List;", "hashCode", "", "loadUnboundSymbols", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LoadedJsIr {
    private final FunctionTypeInterfacePackages functionTypeInterfacePackages;
    private final JsIrLinker linker;
    private final Map<KotlinLibraryFile, IrModuleFragment> loadedFragments;
    private final HashMap<KotlinLibraryFile, List<FileSignatureProvider>> signatureProvidersImpl;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LoadedJsIr copy$default(LoadedJsIr loadedJsIr, Map map, JsIrLinker jsIrLinker, FunctionTypeInterfacePackages functionTypeInterfacePackages, int i, Object obj) {
        if ((i & 1) != 0) {
            map = loadedJsIr.loadedFragments;
        }
        if ((i & 2) != 0) {
            jsIrLinker = loadedJsIr.linker;
        }
        if ((i & 4) != 0) {
            functionTypeInterfacePackages = loadedJsIr.functionTypeInterfacePackages;
        }
        return loadedJsIr.copy(map, jsIrLinker, functionTypeInterfacePackages);
    }

    public final Map<KotlinLibraryFile, IrModuleFragment> component1() {
        return this.loadedFragments;
    }

    public final LoadedJsIr copy(Map<KotlinLibraryFile, ? extends IrModuleFragment> loadedFragments, JsIrLinker linker, FunctionTypeInterfacePackages functionTypeInterfacePackages) {
        Intrinsics.checkNotNullParameter(loadedFragments, "loadedFragments");
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(functionTypeInterfacePackages, "functionTypeInterfacePackages");
        return new LoadedJsIr(loadedFragments, linker, functionTypeInterfacePackages);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoadedJsIr)) {
            return false;
        }
        LoadedJsIr loadedJsIr = (LoadedJsIr) other;
        return Intrinsics.areEqual(this.loadedFragments, loadedJsIr.loadedFragments) && Intrinsics.areEqual(this.linker, loadedJsIr.linker) && Intrinsics.areEqual(this.functionTypeInterfacePackages, loadedJsIr.functionTypeInterfacePackages);
    }

    public int hashCode() {
        return (((this.loadedFragments.hashCode() * 31) + this.linker.hashCode()) * 31) + this.functionTypeInterfacePackages.hashCode();
    }

    public String toString() {
        return "LoadedJsIr(loadedFragments=" + this.loadedFragments + ", linker=" + this.linker + ", functionTypeInterfacePackages=" + this.functionTypeInterfacePackages + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LoadedJsIr(Map<KotlinLibraryFile, ? extends IrModuleFragment> loadedFragments, JsIrLinker linker, FunctionTypeInterfacePackages functionTypeInterfacePackages) {
        Intrinsics.checkNotNullParameter(loadedFragments, "loadedFragments");
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(functionTypeInterfacePackages, "functionTypeInterfacePackages");
        this.loadedFragments = loadedFragments;
        this.linker = linker;
        this.functionTypeInterfacePackages = functionTypeInterfacePackages;
        this.signatureProvidersImpl = new HashMap<>();
    }

    public final Map<KotlinLibraryFile, IrModuleFragment> getLoadedFragments() {
        return this.loadedFragments;
    }

    private final List<FileSignatureProvider> collectSignatureProviders(IrModuleFragment irModule) {
        Collection<IrFileDeserializer> collectionFileDeserializers = this.linker.moduleDeserializer(irModule.getDescriptor()).fileDeserializers();
        ArrayList arrayList = new ArrayList(collectionFileDeserializers.size());
        for (IrFileDeserializer irFileDeserializer : collectionFileDeserializers) {
            IrFile file = irFileDeserializer.getFile();
            if (this.functionTypeInterfacePackages.isFunctionTypeInterfacePackageFile(file)) {
                arrayList.add(new FileSignatureProvider.GeneratedFunctionTypeInterface(file));
            } else {
                arrayList.add(new FileSignatureProvider.DeserializedFromKlib(irFileDeserializer));
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: getSignatureProvidersForLib-5h9WozY, reason: not valid java name */
    public final List<FileSignatureProvider> m7189getSignatureProvidersForLib5h9WozY(String lib) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        HashMap<KotlinLibraryFile, List<FileSignatureProvider>> map = this.signatureProvidersImpl;
        KotlinLibraryFile kotlinLibraryFileM7149boximpl = KotlinLibraryFile.m7149boximpl(lib);
        List<FileSignatureProvider> listCollectSignatureProviders = map.get(kotlinLibraryFileM7149boximpl);
        if (listCollectSignatureProviders == null) {
            IrModuleFragment irModuleFragment = this.loadedFragments.get(KotlinLibraryFile.m7149boximpl(lib));
            if (irModuleFragment == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("loaded fragment", lib, null, 4, null);
                throw null;
            }
            listCollectSignatureProviders = collectSignatureProviders(irModuleFragment);
            map.put(kotlinLibraryFileM7149boximpl, listCollectSignatureProviders);
        }
        return listCollectSignatureProviders;
    }

    public final void loadUnboundSymbols() {
        this.signatureProvidersImpl.clear();
        new ExternalDependenciesGenerator(this.linker.getSymbolTable(), CollectionsKt.listOf(this.linker)).generateUnboundSymbolsAsDependencies();
        this.linker.postProcess(true);
        JsIrLinker jsIrLinker = this.linker;
        ChecksKt.checkNoUnboundSymbols(jsIrLinker, jsIrLinker.getSymbolTable(), "at the end of IR linkage process");
        this.linker.clear();
    }

    public final Set<IrSymbol> collectSymbolsReplacedWithStubs() {
        return this.linker.getPartialLinkageSupport().collectAllStubbedSymbols();
    }
}
