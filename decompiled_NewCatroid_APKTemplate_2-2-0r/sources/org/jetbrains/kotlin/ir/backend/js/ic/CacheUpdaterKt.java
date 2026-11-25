package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.serialization.IrInterningService;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.backend.js.JsIrCompilerWithIC;
import org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: CacheUpdater.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u008a\u0001\u0010\u0000\u001a&\u0012\u0004\u0012\u00020\u0002\u0012\u001c\u0012\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00030\u00010\u00030\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u000b2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u000e\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0003¨\u0006\u0015"}, d2 = {"rebuildCacheForDirtyFiles", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dependencyGraph", "", "dirtyFiles", "", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "exportedDeclarations", "", "Lorg/jetbrains/kotlin/name/FqName;", "mainArguments", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CacheUpdaterKt {
    /* JADX WARN: Code duplicated, block: B:11:0x0069  */
    /* JADX WARN: Code duplicated, block: B:27:0x012f  */
    public static final Pair<IrModuleFragment, List<Pair<IrFile, List<JsIrProgramFragment>>>> rebuildCacheForDirtyFiles(KotlinLibrary library, CompilerConfiguration configuration, Map<KotlinLibrary, ? extends List<? extends KotlinLibrary>> dependencyGraph, Collection<String> collection, IrFactory irFactory, Set<FqName> exportedDeclarations, List<String> list) {
        Set<KotlinSourceFile> setKeySet;
        List<IrFile> files;
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(dependencyGraph, "dependencyGraph");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(exportedDeclarations, "exportedDeclarations");
        IrInterningService irInterningService = new IrInterningService();
        KotlinSourceFileExports kotlinSourceFileExports = new KotlinSourceFileExports() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdaterKt$rebuildCacheForDirtyFiles$emptyMetadata$1
            private final KotlinSourceFileMap<Set<IdSignature>> inverseDependencies = new KotlinSourceFileMap<>(MapsKt.emptyMap());

            @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
            public KotlinSourceFileMap<Set<IdSignature>> getInverseDependencies() {
                return this.inverseDependencies;
            }
        };
        String strM7151constructorimpl = KotlinLibraryFile.m7151constructorimpl(library);
        if (collection != null) {
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(KotlinSourceFile.m7163boximpl(KotlinSourceFile.m7164constructorimpl((String) it.next())));
            }
            List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
            if (listCompactIfPossible != null) {
                setKeySet = listCompactIfPossible;
            } else {
                setKeySet = new KotlinLoadedLibraryHeader(library, irInterningService).getSourceFileFingerprints().keySet();
            }
        } else {
            setKeySet = new KotlinLoadedLibraryHeader(library, irInterningService).getSourceFileFingerprints().keySet();
        }
        KotlinLibraryFile kotlinLibraryFileM7149boximpl = KotlinLibraryFile.m7149boximpl(strM7151constructorimpl);
        Iterable iterable = setKeySet;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(iterable, 10)), 16));
        for (Object obj : iterable) {
            ((KotlinSourceFile) obj).m7171unboximpl();
            linkedHashMap.put(obj, kotlinSourceFileExports);
        }
        LoadedJsIr loadedJsIrLoadIr = new JsIrLinkerLoader(configuration, dependencyGraph, kotlin.collections.CollectionsKt.emptyList(), irFactory, SetsKt.emptySet()).loadIr(new KotlinSourceFileMap<>(MapsKt.mapOf(TuplesKt.to(kotlinLibraryFileM7149boximpl, linkedHashMap))), true);
        IrModuleFragment irModuleFragment = loadedJsIrLoadIr.getLoadedFragments().get(KotlinLibraryFile.m7149boximpl(strM7151constructorimpl));
        if (irModuleFragment == null) {
            ICUtilsKt.m7124notFoundIcError5T69zGA$default("loaded fragment", strM7151constructorimpl, null, 4, null);
            throw null;
        }
        if (collection != null) {
            Set set = kotlin.collections.CollectionsKt.toSet(collection);
            List<IrFile> files2 = irModuleFragment.getFiles();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : files2) {
                if (set.contains(((IrFile) obj2).getFileEntry().getName())) {
                    arrayList2.add(obj2);
                }
            }
            files = CollectionsKt.compactIfPossible(arrayList2);
            if (files == null) {
                files = irModuleFragment.getFiles();
            }
        } else {
            files = irModuleFragment.getFiles();
        }
        List<IrFile> list2 = files;
        JsIrCompilerWithIC jsIrCompilerWithIC = new JsIrCompilerWithIC(irModuleFragment, configuration, JsGenerationGranularity.PER_MODULE, new PhaseConfig(JsLoweringPhasesKt.getJsPhases(), null, null, null, null, null, null, null, null, null, false, false, false, 8190, null), exportedDeclarations);
        loadedJsIrLoadIr.loadUnboundSymbols();
        irInterningService.clear();
        List<Function0<List<JsIrProgramFragment>>> listCompile = jsIrCompilerWithIC.compile(loadedJsIrLoadIr.getLoadedFragments().values(), list2, list);
        ArrayList arrayList3 = new ArrayList(listCompile.size());
        Iterator<T> it2 = listCompile.iterator();
        while (it2.hasNext()) {
            arrayList3.add((List) ((Function0) it2.next()).invoke());
        }
        return TuplesKt.to(irModuleFragment, kotlin.collections.CollectionsKt.zip(list2, CollectionsKt.compactIfPossible(arrayList3)));
    }
}
