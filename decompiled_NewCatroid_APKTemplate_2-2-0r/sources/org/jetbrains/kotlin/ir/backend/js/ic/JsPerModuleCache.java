package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuilt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuiltForCache;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsCached;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleReferences;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModule;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModuleHeader;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsPerModuleCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 $2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002#$B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J(\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00060\u00172\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006H\u0016J\u001c\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u0013\u0010 \u001a\u0004\u0018\u00010\u001c*\u00020\u0002H\u0002¢\u0006\u0002\u0010!J\u000e\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u0007H\u0002R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0002`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerModuleCache;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerModuleCache$CachedModuleInfo;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "moduleArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;)V", "headerToCachedInfo", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "Lkotlin/collections/HashMap;", "commitCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "cacheInfo", "compilationOutputs", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "fetchCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsCached;", "fetchCompiledJsCodeForNullCacheInfo", "", "getMainModuleAndDependencies", "Lkotlin/Pair;", "loadJsIrModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "loadProgramHeadersFromCache", "loadRequiredJsIrModules", "", "crossModuleReferences", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "commitModuleInfo", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerModuleCache$CachedModuleInfo;)Lkotlin/Unit;", "fetchModuleInfo", "CachedModuleInfo", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPerModuleCache extends JsMultiArtifactCache<CachedModuleInfo> {
    private final HashMap<JsIrModuleHeader, CachedModuleInfo> headerToCachedInfo;
    private final List<ModuleArtifact> moduleArtifacts;
    private final ModuleKind moduleKind;

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    /* JADX INFO: renamed from: fetchCompiledJsCodeForNullCacheInfo, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ CompilationOutputs mo7146fetchCompiledJsCodeForNullCacheInfo() {
        return (CompilationOutputs) fetchCompiledJsCodeForNullCacheInfo();
    }

    public JsPerModuleCache(ModuleKind moduleKind, List<ModuleArtifact> moduleArtifacts) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(moduleArtifacts, "moduleArtifacts");
        this.moduleKind = moduleKind;
        this.moduleArtifacts = moduleArtifacts;
        this.headerToCachedInfo = new HashMap<>();
    }

    /* JADX INFO: compiled from: JsPerModuleCache.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\"\u0010\u0006\u001a\u00020\u0007X\u0086\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerModuleCache$CachedModuleInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;", "artifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "jsIrHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "crossModuleReferencesHash", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getArtifact", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "getCrossModuleReferencesHash-Yd-dAqs", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "setCrossModuleReferencesHash-ycJ14Ss", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)V", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getJsIrHeader", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CachedModuleInfo implements JsMultiArtifactCache.CacheInfo {
        private final ModuleArtifact artifact;
        private Hash128Bits crossModuleReferencesHash;
        private final JsIrModuleHeader jsIrHeader;

        public /* synthetic */ CachedModuleInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader, Hash128Bits hash128Bits, DefaultConstructorMarker defaultConstructorMarker) {
            this(moduleArtifact, jsIrModuleHeader, hash128Bits);
        }

        private CachedModuleInfo(ModuleArtifact artifact, JsIrModuleHeader jsIrHeader, Hash128Bits crossModuleReferencesHash) {
            Intrinsics.checkNotNullParameter(artifact, "artifact");
            Intrinsics.checkNotNullParameter(jsIrHeader, "jsIrHeader");
            Intrinsics.checkNotNullParameter(crossModuleReferencesHash, "crossModuleReferencesHash");
            this.artifact = artifact;
            this.jsIrHeader = jsIrHeader;
            this.crossModuleReferencesHash = crossModuleReferencesHash;
        }

        public final ModuleArtifact getArtifact() {
            return this.artifact;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache.CacheInfo
        public JsIrModuleHeader getJsIrHeader() {
            return this.jsIrHeader;
        }

        public /* synthetic */ CachedModuleInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader, Hash128Bits hash128Bits, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(moduleArtifact, jsIrModuleHeader, (i & 4) != 0 ? ICHash.m7109constructorimpl$default(null, 1, null) : hash128Bits, null);
        }

        /* JADX INFO: renamed from: getCrossModuleReferencesHash-Yd-dAqs, reason: not valid java name and from getter */
        public final Hash128Bits getCrossModuleReferencesHash() {
            return this.crossModuleReferencesHash;
        }

        /* JADX INFO: renamed from: setCrossModuleReferencesHash-ycJ14Ss, reason: not valid java name */
        public final void m7148setCrossModuleReferencesHashycJ14Ss(Hash128Bits hash128Bits) {
            Intrinsics.checkNotNullParameter(hash128Bits, "<set-?>");
            this.crossModuleReferencesHash = hash128Bits;
        }
    }

    private final CachedModuleInfo fetchModuleInfo(ModuleArtifact moduleArtifact) {
        File file = new File(moduleArtifact.getArtifactsDir(), "js.module.header.bin");
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (!file.exists()) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
            Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
            Hash128Bits hash128BitsM7116fromProtoStreamTVZkk9w = ICHash.INSTANCE.m7116fromProtoStreamTVZkk9w(codedInputStreamNewInstance);
            String string = codedInputStreamNewInstance.readBool() ? codedInputStreamNewInstance.readString() : null;
            JsMultiArtifactCache.JsIrModuleHeaderNames jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames = fetchJsIrModuleHeaderNames(codedInputStreamNewInstance);
            CachedModuleInfo cachedModuleInfo = new CachedModuleInfo(moduleArtifact, new JsIrModuleHeader(moduleArtifact.getModuleSafeName(), moduleArtifact.getModuleExternalName(), jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component1(), jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component2(), jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component3(), string, null), hash128BitsM7116fromProtoStreamTVZkk9w, defaultConstructorMarker);
            CloseableKt.closeFinally(fileInputStream, null);
            return cachedModuleInfo;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
            }
        }
    }

    private final Unit commitModuleInfo(CachedModuleInfo cachedModuleInfo) {
        File artifactsDir = cachedModuleInfo.getArtifact().getArtifactsDir();
        if (artifactsDir == null) {
            return null;
        }
        File file = new File(artifactsDir, "js.module.header.bin");
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
            Intrinsics.checkNotNull(codedOutputStreamNewInstance);
            ICHash.m7113toProtoStreamimpl(cachedModuleInfo.getCrossModuleReferencesHash(), codedOutputStreamNewInstance);
            String reexportedInModuleWithName = cachedModuleInfo.getJsIrHeader().getReexportedInModuleWithName();
            codedOutputStreamNewInstance.writeBoolNoTag(reexportedInModuleWithName != null);
            if (reexportedInModuleWithName != null) {
                codedOutputStreamNewInstance.writeStringNoTag(reexportedInModuleWithName);
            }
            commitJsIrModuleHeaderNames(codedOutputStreamNewInstance, cachedModuleInfo.getJsIrHeader());
            codedOutputStreamNewInstance.flush();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public JsIrModule loadJsIrModule(CachedModuleInfo cacheInfo) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        return ModuleArtifact.loadJsIrModule$default(cacheInfo.getArtifact(), null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public Pair<CachedModuleInfo, List<CachedModuleInfo>> getMainModuleAndDependencies(List<CachedModuleInfo> cacheInfo) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        return TuplesKt.to(CollectionsKt.last((List) cacheInfo), CollectionsKt.dropLast(cacheInfo, 1));
    }

    public Void fetchCompiledJsCodeForNullCacheInfo() {
        throw new IllegalStateException("Should never happen for per module granularity".toString());
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public CompilationOutputsCached fetchCompiledJsCode(CachedModuleInfo cacheInfo) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        File artifactsDir = cacheInfo.getArtifact().getArtifactsDir();
        if (artifactsDir == null) {
            return null;
        }
        File file = new File(artifactsDir, "module.js");
        if (!file.exists()) {
            file = null;
        }
        File file2 = new File(artifactsDir, "module.js.map");
        if (!file2.exists()) {
            file2 = null;
        }
        File file3 = new File(artifactsDir, "module.d.ts");
        if (!file3.exists()) {
            file3 = null;
        }
        if (file != null) {
            return new CompilationOutputsCached(file, file2, file3);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public CompilationOutputs commitCompiledJsCode(CachedModuleInfo cacheInfo, CompilationOutputsBuilt compilationOutputs) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        Intrinsics.checkNotNullParameter(compilationOutputs, "compilationOutputs");
        File artifactsDir = cacheInfo.getArtifact().getArtifactsDir();
        if (artifactsDir != null) {
            File file = new File(artifactsDir, "module.js");
            File file2 = new File(artifactsDir, "module.js.map");
            File file3 = new File(artifactsDir, "module.d.ts");
            String tsDefinitions = compilationOutputs.getTsDefinitions();
            if (tsDefinitions == null) {
                tsDefinitions = null;
            }
            writeIfNotNull(file3, tsDefinitions);
            CompilationOutputsBuiltForCache compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache = compilationOutputs.writeJsCodeIntoModuleCache(file, file2);
            if (compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache != null) {
                return compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache;
            }
        }
        return compilationOutputs;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0069  */
    /* JADX WARN: Code duplicated, block: B:22:0x006e  */
    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public List<CachedModuleInfo> loadProgramHeadersFromCache() {
        CachedModuleInfo cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo;
        CachedModuleInfo cachedModuleInfoFetchModuleInfo;
        ModuleArtifact moduleArtifact = (ModuleArtifact) CollectionsKt.last((List) this.moduleArtifacts);
        List<ModuleArtifact> list = this.moduleArtifacts;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ModuleArtifact moduleArtifact2 : list) {
            if (moduleArtifact2.getForceRebuildJs()) {
                cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = loadProgramHeadersFromCache$lambda$13$loadModuleInfo(moduleArtifact2, moduleArtifact, this);
            } else {
                List<SrcFileArtifact> fileArtifacts = moduleArtifact2.getFileArtifacts();
                if ((fileArtifacts instanceof Collection) && fileArtifacts.isEmpty()) {
                    cachedModuleInfoFetchModuleInfo = fetchModuleInfo(moduleArtifact2);
                    if (cachedModuleInfoFetchModuleInfo == null) {
                        cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = loadProgramHeadersFromCache$lambda$13$loadModuleInfo(moduleArtifact2, moduleArtifact, this);
                    } else {
                        cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = cachedModuleInfoFetchModuleInfo;
                    }
                } else {
                    Iterator<T> it = fileArtifacts.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (((SrcFileArtifact) it.next()).isModified()) {
                                cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = loadProgramHeadersFromCache$lambda$13$loadModuleInfo(moduleArtifact2, moduleArtifact, this);
                            }
                        } else {
                            cachedModuleInfoFetchModuleInfo = fetchModuleInfo(moduleArtifact2);
                            if (cachedModuleInfoFetchModuleInfo == null) {
                                cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = loadProgramHeadersFromCache$lambda$13$loadModuleInfo(moduleArtifact2, moduleArtifact, this);
                            } else {
                                cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo = cachedModuleInfoFetchModuleInfo;
                            }
                        }
                    }
                }
            }
            this.headerToCachedInfo.put(cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo.getJsIrHeader(), cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo);
            arrayList.add(cachedModuleInfoLoadProgramHeadersFromCache$lambda$13$loadModuleInfo);
        }
        return arrayList;
    }

    private static final CachedModuleInfo loadProgramHeadersFromCache$lambda$13$loadModuleInfo(ModuleArtifact moduleArtifact, ModuleArtifact moduleArtifact2, JsPerModuleCache jsPerModuleCache) {
        String moduleSafeName = moduleArtifact2.getModuleSafeName();
        if (jsPerModuleCache.moduleKind == ModuleKind.ES || moduleArtifact == moduleArtifact2) {
            moduleSafeName = null;
        }
        return new CachedModuleInfo(moduleArtifact, moduleArtifact.loadJsIrModule(moduleSafeName).makeModuleHeader(), null, 4, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public void loadRequiredJsIrModules(Map<JsIrModuleHeader, CrossModuleReferences> crossModuleReferences) {
        Intrinsics.checkNotNullParameter(crossModuleReferences, "crossModuleReferences");
        for (Map.Entry<JsIrModuleHeader, CrossModuleReferences> entry : crossModuleReferences.entrySet()) {
            JsIrModuleHeader key = entry.getKey();
            CrossModuleReferences value = entry.getValue();
            CachedModuleInfo cachedModuleInfo = this.headerToCachedInfo.get(key);
            if (cachedModuleInfo == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("artifact for module " + key.getModuleName(), null, null, 6, null);
                throw null;
            }
            Hash128Bits hash128BitsCrossModuleReferencesHashForIC = HashCalculatorForICKt.crossModuleReferencesHashForIC(value);
            if (key.getAssociatedModule() == null && !ICHash.m7111equalsimpl0(cachedModuleInfo.getCrossModuleReferencesHash(), hash128BitsCrossModuleReferencesHashForIC)) {
                key.setAssociatedModule(ModuleArtifact.loadJsIrModule$default(cachedModuleInfo.getArtifact(), null, 1, null));
            }
            if (key.getAssociatedModule() != null) {
                cachedModuleInfo.m7148setCrossModuleReferencesHashycJ14Ss(hash128BitsCrossModuleReferencesHashForIC);
                commitModuleInfo(cachedModuleInfo);
            }
        }
    }
}
