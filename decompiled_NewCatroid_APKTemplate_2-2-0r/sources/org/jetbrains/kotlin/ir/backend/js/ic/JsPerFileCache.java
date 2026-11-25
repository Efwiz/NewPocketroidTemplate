package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.CityHashKt;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputs;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuilt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsBuiltForCache;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CompilationOutputsCached;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleReferences;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModule;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModuleHeader;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragmentKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.ModuleFragmentToExternalName;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.PerFileEntryPointCompilationOutput;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: JsPerFileCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 M2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0004KLMNB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00020\u0002H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J*\u0010\u001a\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00040\u001b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u0002H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016J\u001c\u0010 \u001a\u00020!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020$0#H\u0016J \u0010%\u001a\u00020\t*\u00020&2\u0006\u0010'\u001a\u00020(2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010(H\u0002J\u0013\u0010*\u001a\u0004\u0018\u00010!*\u00020\u0002H\u0002¢\u0006\u0002\u0010+J\u0014\u0010,\u001a\u00020!*\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u001c\u00100\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0004*\u00020\u00052\u0006\u00101\u001a\u000202H\u0002J\u0016\u00103\u001a\u0004\u0018\u000104*\u0002052\u0006\u00106\u001a\u000207H\u0002J\u000e\u00108\u001a\u0004\u0018\u000109*\u00020\u0005H\u0002J\f\u0010:\u001a\u00020\u0002*\u00020\u0005H\u0002J\u0014\u0010;\u001a\u00020(*\u00020&2\u0006\u0010<\u001a\u00020\u0005H\u0002J\u0014\u0010=\u001a\u00020(*\u00020&2\u0006\u0010<\u001a\u00020\u0005H\u0002J\u0012\u0010>\u001a\u00020?*\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0002J\u001a\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004*\u00020\u00052\u0006\u00101\u001a\u000202H\u0002J\u0014\u0010A\u001a\u00020B*\u0002022\u0006\u0010<\u001a\u00020\u0005H\u0002J#\u0010C\u001a\u0002HD\"\b\b\u0000\u0010D*\u00020\u0002*\u0002052\u0006\u0010.\u001a\u0002HDH\u0002¢\u0006\u0002\u0010EJ2\u0010F\u001a\u0004\u0018\u0001HD\"\u0004\b\u0000\u0010D*\u0002072\u0017\u0010G\u001a\u0013\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u0002HD0H¢\u0006\u0002\bIH\u0002¢\u0006\u0002\u0010JR*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0002`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;", "moduleArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "(Ljava/util/List;)V", "headerToCachedInfo", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "Lkotlin/collections/HashMap;", "moduleFragmentToExternalName", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ModuleFragmentToExternalName;", "cachedFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileArtifacts;", "getCachedFiles", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;)Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileArtifacts;", "commitCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputs;", "cacheInfo", "compilationOutputs", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsBuilt;", "fetchCompiledJsCode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CompilationOutputsCached;", "fetchCompiledJsCodeForNullCacheInfo", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/PerFileEntryPointCompilationOutput;", "getMainModuleAndDependencies", "Lkotlin/Pair;", "", "loadJsIrModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "loadProgramHeadersFromCache", "loadRequiredJsIrModules", "", "crossModuleReferences", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "asIrModuleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "moduleName", "", "reexportedIn", "commitFileInfo", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;)Lkotlin/Unit;", "commitSingleFileInfo", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "cachedFileInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo;", "fetchFileInfoFor", "fileArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "fetchFileInfoForExportedPart", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "mainCachedFileInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$MainFileCachedInfo;", "fetchModuleProxyFileInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo;", "generateModuleProxyFileCachedInfo", "getExportFragmentExternalName", "moduleArtifact", "getMainFragmentExternalName", "hasExportFile", "", "loadFileInfoFor", "loadJsIrModuleHeaders", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$LoadedJsIrModuleHeaders;", "loadSingleCachedFileInfo", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/protobuf/CodedInputStream;Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;)Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;", "readModuleHeaderCache", "f", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$MainFileCachedInfo;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "CachedFileArtifacts", "CachedFileInfo", "Companion", "LoadedJsIrModuleHeaders", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPerFileCache extends JsMultiArtifactCache<CachedFileInfo> {
    private final HashMap<JsIrModuleHeader, CachedFileInfo> headerToCachedInfo;
    private final List<ModuleArtifact> moduleArtifacts;
    private final ModuleFragmentToExternalName moduleFragmentToExternalName;

    public JsPerFileCache(List<ModuleArtifact> moduleArtifacts) {
        Intrinsics.checkNotNullParameter(moduleArtifacts, "moduleArtifacts");
        this.moduleArtifacts = moduleArtifacts;
        this.headerToCachedInfo = new HashMap<>();
        this.moduleFragmentToExternalName = new ModuleFragmentToExternalName(MapsKt.emptyMap());
    }

    /* JADX INFO: compiled from: JsPerFileCache.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0015\u0016\u0017\u0018B\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\"\u0010\u0007\u001a\u00020\bX\u0086\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0001\u0002\u0019\u001a\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsMultiArtifactCache$CacheInfo;", "moduleArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "moduleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "crossFileReferencesHash", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "getCrossFileReferencesHash-Yd-dAqs", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "setCrossFileReferencesHash-ycJ14Ss", "(Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)V", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "jsIrHeader", "getJsIrHeader", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "setJsIrHeader", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "getModuleArtifact", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "ExportFileCachedInfo", "MainFileCachedInfo", "ModuleProxyFileCachedInfo", "SerializableCachedFileInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class CachedFileInfo implements JsMultiArtifactCache.CacheInfo {
        private Hash128Bits crossFileReferencesHash;
        public JsIrModuleHeader jsIrHeader;
        private final ModuleArtifact moduleArtifact;

        public /* synthetic */ CachedFileInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader, DefaultConstructorMarker defaultConstructorMarker) {
            this(moduleArtifact, jsIrModuleHeader);
        }

        private CachedFileInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader) {
            this.moduleArtifact = moduleArtifact;
            this.crossFileReferencesHash = ICHash.m7109constructorimpl$default(null, 1, null);
            if (jsIrModuleHeader != null) {
                setJsIrHeader(jsIrModuleHeader);
            }
        }

        public final ModuleArtifact getModuleArtifact() {
            return this.moduleArtifact;
        }

        /* JADX INFO: renamed from: getCrossFileReferencesHash-Yd-dAqs, reason: not valid java name and from getter */
        public final Hash128Bits getCrossFileReferencesHash() {
            return this.crossFileReferencesHash;
        }

        /* JADX INFO: renamed from: setCrossFileReferencesHash-ycJ14Ss, reason: not valid java name */
        public final void m7145setCrossFileReferencesHashycJ14Ss(Hash128Bits hash128Bits) {
            Intrinsics.checkNotNullParameter(hash128Bits, "<set-?>");
            this.crossFileReferencesHash = hash128Bits;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache.CacheInfo
        public final JsIrModuleHeader getJsIrHeader() {
            JsIrModuleHeader jsIrModuleHeader = this.jsIrHeader;
            if (jsIrModuleHeader != null) {
                return jsIrModuleHeader;
            }
            Intrinsics.throwUninitializedPropertyAccessException("jsIrHeader");
            return null;
        }

        public final void setJsIrHeader(JsIrModuleHeader jsIrModuleHeader) {
            Intrinsics.checkNotNullParameter(jsIrModuleHeader, "<set-?>");
            this.jsIrHeader = jsIrModuleHeader;
        }

        /* JADX INFO: compiled from: JsPerFileCache.kt */
        @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B!\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0002\u0014\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;", "moduleArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "fileArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "moduleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "getFileArtifact", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "filePrefix", "", "getFilePrefix", "()Ljava/lang/String;", "filePrefix$delegate", "Lkotlin/Lazy;", "getArtifactWithName", "Ljava/io/File;", "name", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$MainFileCachedInfo;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static abstract class SerializableCachedFileInfo extends CachedFileInfo {
            private final SrcFileArtifact fileArtifact;

            /* JADX INFO: renamed from: filePrefix$delegate, reason: from kotlin metadata */
            private final Lazy filePrefix;

            public /* synthetic */ SerializableCachedFileInfo(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact, JsIrModuleHeader jsIrModuleHeader, DefaultConstructorMarker defaultConstructorMarker) {
                this(moduleArtifact, srcFileArtifact, jsIrModuleHeader);
            }

            public final SrcFileArtifact getFileArtifact() {
                return this.fileArtifact;
            }

            private SerializableCachedFileInfo(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact, JsIrModuleHeader jsIrModuleHeader) {
                super(moduleArtifact, jsIrModuleHeader, null);
                this.fileArtifact = srcFileArtifact;
                this.filePrefix = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<String>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo$filePrefix$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        String srcFilePath = this.this$0.getFileArtifact().getSrcFilePath();
                        return StringsKt.substringAfterLast$default(srcFilePath, '/', (String) null, 2, (Object) null) + '.' + CityHashKt.cityHash64(srcFilePath);
                    }
                });
            }

            public final File getArtifactWithName(String name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                File artifactsDir = getModuleArtifact().getArtifactsDir();
                if (artifactsDir != null) {
                    return new File(artifactsDir, getFilePrefix() + '.' + name2);
                }
                return null;
            }

            private final String getFilePrefix() {
                return (String) this.filePrefix.getValue();
            }
        }

        /* JADX INFO: compiled from: JsPerFileCache.kt */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0012R\u001d\u0010\u0018\u001a\u0004\u0018\u00010\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0014\u001a\u0004\b\u0019\u0010\u0012¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$MainFileCachedInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo;", "moduleArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "fileArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "moduleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "exportFileCachedInfo", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;", "getExportFileCachedInfo", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;", "setExportFileCachedInfo", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;)V", "jsFileArtifact", "Ljava/io/File;", "getJsFileArtifact", "()Ljava/io/File;", "jsFileArtifact$delegate", "Lkotlin/Lazy;", "moduleHeaderArtifact", "getModuleHeaderArtifact", "moduleHeaderArtifact$delegate", "sourceMapFileArtifact", "getSourceMapFileArtifact", "sourceMapFileArtifact$delegate", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class MainFileCachedInfo extends SerializableCachedFileInfo {
            private ExportFileCachedInfo exportFileCachedInfo;

            /* JADX INFO: renamed from: jsFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy jsFileArtifact;

            /* JADX INFO: renamed from: moduleHeaderArtifact$delegate, reason: from kotlin metadata */
            private final Lazy moduleHeaderArtifact;

            /* JADX INFO: renamed from: sourceMapFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy sourceMapFileArtifact;

            public /* synthetic */ MainFileCachedInfo(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact, JsIrModuleHeader jsIrModuleHeader, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(moduleArtifact, srcFileArtifact, (i & 4) != 0 ? null : jsIrModuleHeader);
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public MainFileCachedInfo(ModuleArtifact moduleArtifact, SrcFileArtifact fileArtifact, JsIrModuleHeader jsIrModuleHeader) {
                super(moduleArtifact, fileArtifact, jsIrModuleHeader, null);
                Intrinsics.checkNotNullParameter(moduleArtifact, "moduleArtifact");
                Intrinsics.checkNotNullParameter(fileArtifact, "fileArtifact");
                this.jsFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$MainFileCachedInfo$jsFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.js");
                    }
                });
                this.moduleHeaderArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$MainFileCachedInfo$moduleHeaderArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("js.module.header.bin");
                    }
                });
                this.sourceMapFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$MainFileCachedInfo$sourceMapFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.js.map");
                    }
                });
            }

            public final ExportFileCachedInfo getExportFileCachedInfo() {
                return this.exportFileCachedInfo;
            }

            public final void setExportFileCachedInfo(ExportFileCachedInfo exportFileCachedInfo) {
                this.exportFileCachedInfo = exportFileCachedInfo;
            }

            public final File getJsFileArtifact() {
                return (File) this.jsFileArtifact.getValue();
            }

            public final File getModuleHeaderArtifact() {
                return (File) this.moduleHeaderArtifact.getValue();
            }

            public final File getSourceMapFileArtifact() {
                return (File) this.sourceMapFileArtifact.getValue();
            }
        }

        /* JADX INFO: compiled from: JsPerFileCache.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0012\u0010\u000eR\u001e\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0018\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ExportFileCachedInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$SerializableCachedFileInfo;", "moduleArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "fileArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "moduleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "tsDeclarationsHash", "", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;Ljava/lang/Long;)V", "dtsFileArtifact", "Ljava/io/File;", "getDtsFileArtifact", "()Ljava/io/File;", "dtsFileArtifact$delegate", "Lkotlin/Lazy;", "jsFileArtifact", "getJsFileArtifact", "jsFileArtifact$delegate", "getTsDeclarationsHash", "()Ljava/lang/Long;", "setTsDeclarationsHash", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ExportFileCachedInfo extends SerializableCachedFileInfo {

            /* JADX INFO: renamed from: dtsFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy dtsFileArtifact;

            /* JADX INFO: renamed from: jsFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy jsFileArtifact;
            private Long tsDeclarationsHash;

            public /* synthetic */ ExportFileCachedInfo(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact, JsIrModuleHeader jsIrModuleHeader, Long l, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(moduleArtifact, srcFileArtifact, (i & 4) != 0 ? null : jsIrModuleHeader, (i & 8) != 0 ? null : l);
            }

            public final Long getTsDeclarationsHash() {
                return this.tsDeclarationsHash;
            }

            public final void setTsDeclarationsHash(Long l) {
                this.tsDeclarationsHash = l;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ExportFileCachedInfo(ModuleArtifact moduleArtifact, SrcFileArtifact fileArtifact, JsIrModuleHeader jsIrModuleHeader, Long l) {
                super(moduleArtifact, fileArtifact, jsIrModuleHeader, null);
                Intrinsics.checkNotNullParameter(moduleArtifact, "moduleArtifact");
                Intrinsics.checkNotNullParameter(fileArtifact, "fileArtifact");
                this.tsDeclarationsHash = l;
                this.jsFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$ExportFileCachedInfo$jsFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.export.js");
                    }
                });
                this.dtsFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$ExportFileCachedInfo$dtsFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.d.ts");
                    }
                });
            }

            public final File getJsFileArtifact() {
                return (File) this.jsFileArtifact.getValue();
            }

            public final File getDtsFileArtifact() {
                return (File) this.dtsFileArtifact.getValue();
            }
        }

        /* JADX INFO: compiled from: JsPerFileCache.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001d\u0010\r\u001a\u0004\u0018\u00010\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u000e\u0010\nR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\f\u001a\u0004\b\u0011\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileInfo;", "moduleArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "moduleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "dtsFileArtifact", "Ljava/io/File;", "getDtsFileArtifact", "()Ljava/io/File;", "dtsFileArtifact$delegate", "Lkotlin/Lazy;", "jsFileArtifact", "getJsFileArtifact", "jsFileArtifact$delegate", "moduleHeaderArtifact", "getModuleHeaderArtifact", "moduleHeaderArtifact$delegate", "getArtifactWithName", "name", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ModuleProxyFileCachedInfo extends CachedFileInfo {

            /* JADX INFO: renamed from: dtsFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy dtsFileArtifact;

            /* JADX INFO: renamed from: jsFileArtifact$delegate, reason: from kotlin metadata */
            private final Lazy jsFileArtifact;

            /* JADX INFO: renamed from: moduleHeaderArtifact$delegate, reason: from kotlin metadata */
            private final Lazy moduleHeaderArtifact;

            public /* synthetic */ ModuleProxyFileCachedInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(moduleArtifact, (i & 2) != 0 ? null : jsIrModuleHeader);
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ModuleProxyFileCachedInfo(ModuleArtifact moduleArtifact, JsIrModuleHeader jsIrModuleHeader) {
                super(moduleArtifact, jsIrModuleHeader, null);
                Intrinsics.checkNotNullParameter(moduleArtifact, "moduleArtifact");
                this.jsFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo$jsFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.js");
                    }
                });
                this.dtsFileArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo$dtsFileArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("file.d.ts");
                    }
                });
                this.moduleHeaderArtifact = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$CachedFileInfo$ModuleProxyFileCachedInfo$moduleHeaderArtifact$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final File invoke() {
                        return this.this$0.getArtifactWithName("js.module.header.bin");
                    }
                });
            }

            public final File getJsFileArtifact() {
                return (File) this.jsFileArtifact.getValue();
            }

            public final File getDtsFileArtifact() {
                return (File) this.dtsFileArtifact.getValue();
            }

            public final File getModuleHeaderArtifact() {
                return (File) this.moduleHeaderArtifact.getValue();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final File getArtifactWithName(String name2) {
                File artifactsDir = getModuleArtifact().getArtifactsDir();
                if (artifactsDir != null) {
                    return new File(artifactsDir, "entry." + name2);
                }
                return null;
            }
        }
    }

    private final String getMainFragmentExternalName(JsIrProgramFragment jsIrProgramFragment, ModuleArtifact moduleArtifact) {
        return this.moduleFragmentToExternalName.getExternalNameFor(jsIrProgramFragment.getName(), jsIrProgramFragment.getPackageFqn(), moduleArtifact.getModuleExternalName());
    }

    private final String getExportFragmentExternalName(JsIrProgramFragment jsIrProgramFragment, ModuleArtifact moduleArtifact) {
        return this.moduleFragmentToExternalName.getExternalNameForExporterFile(jsIrProgramFragment.getName(), jsIrProgramFragment.getPackageFqn(), moduleArtifact.getModuleExternalName());
    }

    static /* synthetic */ JsIrModuleHeader asIrModuleHeader$default(JsPerFileCache jsPerFileCache, JsIrProgramFragment jsIrProgramFragment, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return jsPerFileCache.asIrModuleHeader(jsIrProgramFragment, str, str2);
    }

    private final JsIrModuleHeader asIrModuleHeader(JsIrProgramFragment jsIrProgramFragment, String str, String str2) {
        Set<String> definitions = jsIrProgramFragment.getDefinitions();
        Map<String, JsName> nameBindings = jsIrProgramFragment.getNameBindings();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(nameBindings.size()));
        Iterator<T> it = nameBindings.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), ((JsName) entry.getValue()).toString());
        }
        return new JsIrModuleHeader(str, str, definitions, linkedHashMap, jsIrProgramFragment.getOptionalCrossModuleImports(), str2, null);
    }

    private final LoadedJsIrModuleHeaders loadJsIrModuleHeaders(SrcFileArtifact srcFileArtifact, ModuleArtifact moduleArtifact) {
        List<JsIrProgramFragment> listLoadJsIrFragments = srcFileArtifact.loadJsIrFragments();
        JsIrProgramFragment mainFragment = JsIrProgramFragmentKt.getMainFragment(listLoadJsIrFragments);
        JsIrModuleHeader jsIrModuleHeaderAsIrModuleHeader$default = asIrModuleHeader$default(this, mainFragment, getMainFragmentExternalName(mainFragment, moduleArtifact), null, 2, null);
        JsIrProgramFragment exportFragment = JsIrProgramFragmentKt.getExportFragment(listLoadJsIrFragments);
        return new LoadedJsIrModuleHeaders(jsIrModuleHeaderAsIrModuleHeader$default, exportFragment != null ? asIrModuleHeader(exportFragment, getExportFragmentExternalName(JsIrProgramFragmentKt.getMainFragment(listLoadJsIrFragments), moduleArtifact), moduleArtifact.getModuleExternalName()) : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends CachedFileInfo> T loadSingleCachedFileInfo(CodedInputStream codedInputStream, T t) throws IOException {
        String moduleExternalName;
        String string = codedInputStream.readString();
        t.m7145setCrossFileReferencesHashycJ14Ss(ICHash.INSTANCE.m7116fromProtoStreamTVZkk9w(codedInputStream));
        if (t instanceof CachedFileInfo.ExportFileCachedInfo) {
            ((CachedFileInfo.ExportFileCachedInfo) t).setTsDeclarationsHash(codedInputStream.readBool() ? Long.valueOf(codedInputStream.readInt64()) : null);
            moduleExternalName = t.getModuleArtifact().getModuleExternalName();
        } else {
            moduleExternalName = null;
        }
        JsMultiArtifactCache.JsIrModuleHeaderNames jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames = fetchJsIrModuleHeaderNames(codedInputStream);
        Set<String> setComponent1 = jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component1();
        Map<String, String> mapComponent2 = jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component2();
        Set<String> setComponent3 = jsIrModuleHeaderNamesFetchJsIrModuleHeaderNames.component3();
        Intrinsics.checkNotNull(string);
        t.setJsIrHeader(new JsIrModuleHeader(string, string, setComponent1, mapComponent2, setComponent3, moduleExternalName, null));
        return t;
    }

    private final <T> T readModuleHeaderCache(CachedFileInfo.MainFileCachedInfo mainFileCachedInfo, Function1<? super CodedInputStream, ? extends T> function1) {
        File moduleHeaderArtifact = mainFileCachedInfo.getModuleHeaderArtifact();
        if (moduleHeaderArtifact == null || !moduleHeaderArtifact.exists()) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(moduleHeaderArtifact);
        try {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
            Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
            T tInvoke = function1.invoke(codedInputStreamNewInstance);
            CloseableKt.closeFinally(fileInputStream, null);
            return tInvoke;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
            }
        }
    }

    private final List<CachedFileInfo> fetchFileInfoFor(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact) {
        final CachedFileInfo.MainFileCachedInfo mainFileCachedInfo = new CachedFileInfo.MainFileCachedInfo(moduleArtifact, srcFileArtifact, null, 4, null);
        return (List) readModuleHeaderCache(mainFileCachedInfo, new Function1<CodedInputStream, List<? extends CachedFileInfo.SerializableCachedFileInfo>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache.fetchFileInfoFor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<CachedFileInfo.SerializableCachedFileInfo> invoke(CodedInputStream readModuleHeaderCache) throws IOException {
                Intrinsics.checkNotNullParameter(readModuleHeaderCache, "$this$readModuleHeaderCache");
                CachedFileInfo.MainFileCachedInfo mainFileCachedInfo2 = mainFileCachedInfo;
                JsPerFileCache jsPerFileCache = this;
                mainFileCachedInfo2.setExportFileCachedInfo(jsPerFileCache.fetchFileInfoForExportedPart(readModuleHeaderCache, mainFileCachedInfo2));
                jsPerFileCache.loadSingleCachedFileInfo(readModuleHeaderCache, mainFileCachedInfo2);
                return CollectionsKt.listOfNotNull((Object[]) new CachedFileInfo.SerializableCachedFileInfo[]{mainFileCachedInfo2.getExportFileCachedInfo(), mainFileCachedInfo2});
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final CachedFileInfo.ModuleProxyFileCachedInfo fetchModuleProxyFileInfo(ModuleArtifact moduleArtifact) {
        CachedFileInfo.ModuleProxyFileCachedInfo moduleProxyFileCachedInfo = new CachedFileInfo.ModuleProxyFileCachedInfo(moduleArtifact, null, 2, 0 == true ? 1 : 0);
        File moduleHeaderArtifact = moduleProxyFileCachedInfo.getModuleHeaderArtifact();
        if (moduleHeaderArtifact == null || !moduleHeaderArtifact.exists()) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(moduleHeaderArtifact);
        try {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
            Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
            CachedFileInfo.ModuleProxyFileCachedInfo moduleProxyFileCachedInfo2 = (CachedFileInfo.ModuleProxyFileCachedInfo) loadSingleCachedFileInfo(codedInputStreamNewInstance, moduleProxyFileCachedInfo);
            CloseableKt.closeFinally(fileInputStream, null);
            return moduleProxyFileCachedInfo2;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileInputStream, th);
                throw th2;
            }
        }
    }

    private final void commitSingleFileInfo(CodedOutputStream codedOutputStream, CachedFileInfo.SerializableCachedFileInfo serializableCachedFileInfo) throws IOException {
        codedOutputStream.writeStringNoTag(serializableCachedFileInfo.getJsIrHeader().getExternalModuleName());
        ICHash.m7113toProtoStreamimpl(serializableCachedFileInfo.getCrossFileReferencesHash(), codedOutputStream);
        if (serializableCachedFileInfo instanceof CachedFileInfo.ExportFileCachedInfo) {
            Long tsDeclarationsHash = ((CachedFileInfo.ExportFileCachedInfo) serializableCachedFileInfo).getTsDeclarationsHash();
            codedOutputStream.writeBoolNoTag(tsDeclarationsHash != null);
            if (tsDeclarationsHash != null) {
                codedOutputStream.writeInt64NoTag(tsDeclarationsHash.longValue());
            }
        }
        commitJsIrModuleHeaderNames(codedOutputStream, serializableCachedFileInfo.getJsIrHeader());
    }

    private final Unit commitFileInfo(CachedFileInfo cachedFileInfo) {
        if (cachedFileInfo instanceof CachedFileInfo.MainFileCachedInfo) {
            File moduleHeaderArtifact = ((CachedFileInfo.MainFileCachedInfo) cachedFileInfo).getModuleHeaderArtifact();
            if (moduleHeaderArtifact == null) {
                return null;
            }
            File parentFile = moduleHeaderArtifact.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(moduleHeaderArtifact);
            try {
                CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
                Intrinsics.checkNotNull(codedOutputStreamNewInstance);
                CachedFileInfo.ExportFileCachedInfo exportFileCachedInfo = ((CachedFileInfo.MainFileCachedInfo) cachedFileInfo).getExportFileCachedInfo();
                codedOutputStreamNewInstance.writeBoolNoTag(exportFileCachedInfo != null);
                if (exportFileCachedInfo != null) {
                    commitSingleFileInfo(codedOutputStreamNewInstance, exportFileCachedInfo);
                }
                commitSingleFileInfo(codedOutputStreamNewInstance, (CachedFileInfo.SerializableCachedFileInfo) cachedFileInfo);
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
        if (cachedFileInfo instanceof CachedFileInfo.ModuleProxyFileCachedInfo) {
            File moduleHeaderArtifact2 = ((CachedFileInfo.ModuleProxyFileCachedInfo) cachedFileInfo).getModuleHeaderArtifact();
            if (moduleHeaderArtifact2 == null) {
                return null;
            }
            File parentFile2 = moduleHeaderArtifact2.getParentFile();
            if (parentFile2 != null) {
                parentFile2.mkdirs();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(moduleHeaderArtifact2);
            try {
                CodedOutputStream codedOutputStreamNewInstance2 = CodedOutputStream.newInstance(fileOutputStream2);
                Intrinsics.checkNotNull(codedOutputStreamNewInstance2);
                codedOutputStreamNewInstance2.writeStringNoTag(cachedFileInfo.getJsIrHeader().getExternalModuleName());
                ICHash.m7113toProtoStreamimpl(cachedFileInfo.getCrossFileReferencesHash(), codedOutputStreamNewInstance2);
                commitJsIrModuleHeaderNames(codedOutputStreamNewInstance2, cachedFileInfo.getJsIrHeader());
                codedOutputStreamNewInstance2.flush();
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream2, null);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileOutputStream2, th3);
                    throw th4;
                }
            }
        }
        if (cachedFileInfo instanceof CachedFileInfo.ExportFileCachedInfo) {
            return Unit.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final CachedFileInfo generateModuleProxyFileCachedInfo(ModuleArtifact moduleArtifact) {
        return new CachedFileInfo.ModuleProxyFileCachedInfo(moduleArtifact, IrModuleToJsTransformerKt.generateProxyIrModuleWith(moduleArtifact.getModuleExternalName(), moduleArtifact.getModuleExternalName()).makeModuleHeader());
    }

    private final List<CachedFileInfo> loadFileInfoFor(ModuleArtifact moduleArtifact, SrcFileArtifact srcFileArtifact) {
        String dts;
        LoadedJsIrModuleHeaders loadedJsIrModuleHeadersLoadJsIrModuleHeaders = loadJsIrModuleHeaders(srcFileArtifact, moduleArtifact);
        final CachedFileInfo.MainFileCachedInfo mainFileCachedInfo = new CachedFileInfo.MainFileCachedInfo(moduleArtifact, srcFileArtifact, loadedJsIrModuleHeadersLoadJsIrModuleHeaders.getMainHeader());
        if (loadedJsIrModuleHeadersLoadJsIrModuleHeaders.getExportHeader() != null) {
            JsIrProgramFragment exportFragment = JsIrProgramFragmentKt.getExportFragment(srcFileArtifact.loadJsIrFragments());
            Long lValueOf = (exportFragment == null || (dts = exportFragment.getDts()) == null) ? null : Long.valueOf(CityHashKt.cityHash64(dts));
            CachedFileInfo.ExportFileCachedInfo exportFileCachedInfo = (CachedFileInfo.ExportFileCachedInfo) readModuleHeaderCache(mainFileCachedInfo, new Function1<CodedInputStream, CachedFileInfo.ExportFileCachedInfo>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.JsPerFileCache$loadFileInfoFor$cachedExportFileInfo$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsPerFileCache.CachedFileInfo.ExportFileCachedInfo invoke(CodedInputStream readModuleHeaderCache) {
                    Intrinsics.checkNotNullParameter(readModuleHeaderCache, "$this$readModuleHeaderCache");
                    return this.this$0.fetchFileInfoForExportedPart(readModuleHeaderCache, mainFileCachedInfo);
                }
            });
            if (!Intrinsics.areEqual(exportFileCachedInfo != null ? exportFileCachedInfo.getTsDeclarationsHash() : null, lValueOf)) {
                exportFileCachedInfo = new CachedFileInfo.ExportFileCachedInfo(moduleArtifact, srcFileArtifact, loadedJsIrModuleHeadersLoadJsIrModuleHeaders.getExportHeader(), lValueOf);
            }
            mainFileCachedInfo.setExportFileCachedInfo(exportFileCachedInfo);
        }
        return CollectionsKt.listOfNotNull((Object[]) new CachedFileInfo.SerializableCachedFileInfo[]{mainFileCachedInfo.getExportFileCachedInfo(), mainFileCachedInfo});
    }

    private final CachedFileArtifacts getCachedFiles(CachedFileInfo cachedFileInfo) {
        CachedFileArtifacts cachedFileArtifacts;
        if (cachedFileInfo instanceof CachedFileInfo.MainFileCachedInfo) {
            CachedFileInfo.MainFileCachedInfo mainFileCachedInfo = (CachedFileInfo.MainFileCachedInfo) cachedFileInfo;
            File jsFileArtifact = mainFileCachedInfo.getJsFileArtifact();
            if (jsFileArtifact == null) {
                return null;
            }
            cachedFileArtifacts = new CachedFileArtifacts(jsFileArtifact, mainFileCachedInfo.getSourceMapFileArtifact(), null);
        } else if (cachedFileInfo instanceof CachedFileInfo.ExportFileCachedInfo) {
            CachedFileInfo.ExportFileCachedInfo exportFileCachedInfo = (CachedFileInfo.ExportFileCachedInfo) cachedFileInfo;
            File jsFileArtifact2 = exportFileCachedInfo.getJsFileArtifact();
            if (jsFileArtifact2 == null) {
                return null;
            }
            cachedFileArtifacts = new CachedFileArtifacts(jsFileArtifact2, null, exportFileCachedInfo.getDtsFileArtifact());
        } else {
            if (!(cachedFileInfo instanceof CachedFileInfo.ModuleProxyFileCachedInfo)) {
                throw new NoWhenBranchMatchedException();
            }
            CachedFileInfo.ModuleProxyFileCachedInfo moduleProxyFileCachedInfo = (CachedFileInfo.ModuleProxyFileCachedInfo) cachedFileInfo;
            File jsFileArtifact3 = moduleProxyFileCachedInfo.getJsFileArtifact();
            if (jsFileArtifact3 == null) {
                return null;
            }
            cachedFileArtifacts = new CachedFileArtifacts(jsFileArtifact3, null, moduleProxyFileCachedInfo.getDtsFileArtifact());
        }
        return cachedFileArtifacts;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public Pair getMainModuleAndDependencies(List<? extends CachedFileInfo> cacheInfo) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        return TuplesKt.to(null, cacheInfo);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    /* JADX INFO: renamed from: fetchCompiledJsCodeForNullCacheInfo */
    public PerFileEntryPointCompilationOutput mo7146fetchCompiledJsCodeForNullCacheInfo() {
        return new PerFileEntryPointCompilationOutput();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public CompilationOutputsCached fetchCompiledJsCode(CachedFileInfo cacheInfo) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        CachedFileArtifacts cachedFiles = getCachedFiles(cacheInfo);
        File file = null;
        if (cachedFiles == null) {
            return null;
        }
        File jsCodeFile = cachedFiles.getJsCodeFile();
        File sourceMapFile = cachedFiles.getSourceMapFile();
        File tsDeclarationsFile = cachedFiles.getTsDeclarationsFile();
        if (!jsCodeFile.exists()) {
            jsCodeFile = null;
        }
        if (jsCodeFile == null) {
            return null;
        }
        if (sourceMapFile == null || !sourceMapFile.exists()) {
            sourceMapFile = null;
        }
        if (tsDeclarationsFile != null && tsDeclarationsFile.exists()) {
            file = tsDeclarationsFile;
        }
        return new CompilationOutputsCached(jsCodeFile, sourceMapFile, file);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public CompilationOutputs commitCompiledJsCode(CachedFileInfo cacheInfo, CompilationOutputsBuilt compilationOutputs) {
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        Intrinsics.checkNotNullParameter(compilationOutputs, "compilationOutputs");
        CachedFileArtifacts cachedFiles = getCachedFiles(cacheInfo);
        if (cachedFiles != null) {
            File jsCodeFile = cachedFiles.getJsCodeFile();
            File sourceMapFile = cachedFiles.getSourceMapFile();
            File tsDeclarationsFile = cachedFiles.getTsDeclarationsFile();
            if (tsDeclarationsFile != null) {
                String tsDefinitions = compilationOutputs.getTsDefinitions();
                if (tsDefinitions == null) {
                    tsDefinitions = null;
                }
                writeIfNotNull(tsDeclarationsFile, tsDefinitions);
            }
            CompilationOutputsBuiltForCache compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache = compilationOutputs.writeJsCodeIntoModuleCache(jsCodeFile, sourceMapFile);
            if (compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache != null) {
                return compilationOutputsBuiltForCacheWriteJsCodeIntoModuleCache;
            }
        }
        return compilationOutputs;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public JsIrModule loadJsIrModule(CachedFileInfo cacheInfo) {
        JsIrProgramFragment mainFragment;
        Intrinsics.checkNotNullParameter(cacheInfo, "cacheInfo");
        if (!(cacheInfo instanceof CachedFileInfo.SerializableCachedFileInfo)) {
            return IrModuleToJsTransformerKt.generateProxyIrModuleWith(cacheInfo.getJsIrHeader().getExternalModuleName(), cacheInfo.getJsIrHeader().getExternalModuleName());
        }
        List<JsIrProgramFragment> listLoadJsIrFragments = ((CachedFileInfo.SerializableCachedFileInfo) cacheInfo).getFileArtifact().loadJsIrFragments();
        boolean z = cacheInfo instanceof CachedFileInfo.ExportFileCachedInfo;
        String moduleName = cacheInfo.getJsIrHeader().getModuleName();
        String externalModuleName = cacheInfo.getJsIrHeader().getExternalModuleName();
        if (z) {
            mainFragment = JsIrProgramFragmentKt.getExportFragment(listLoadJsIrFragments);
            Intrinsics.checkNotNull(mainFragment);
        } else {
            mainFragment = JsIrProgramFragmentKt.getMainFragment(listLoadJsIrFragments);
        }
        return new JsIrModule(moduleName, externalModuleName, CollectionsKt.listOf(mainFragment), z ? cacheInfo.getModuleArtifact().getModuleSafeName() : null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public List<CachedFileInfo> loadProgramHeadersFromCache() {
        List<CachedFileInfo> listFetchFileInfoFor;
        List<ModuleArtifact> list = this.moduleArtifacts;
        ArrayList arrayList = new ArrayList();
        for (ModuleArtifact moduleArtifact : list) {
            List<SrcFileArtifact> fileArtifacts = moduleArtifact.getFileArtifacts();
            ArrayList arrayList2 = new ArrayList();
            boolean z = false;
            for (SrcFileArtifact srcFileArtifact : fileArtifacts) {
                List<CachedFileInfo> listLoadFileInfoFor = (srcFileArtifact.isModified() || (listFetchFileInfoFor = fetchFileInfoFor(moduleArtifact, srcFileArtifact)) == null) ? loadFileInfoFor(moduleArtifact, srcFileArtifact) : listFetchFileInfoFor;
                if (!z && hasExportFile(listLoadFileInfoFor)) {
                    z = true;
                }
                CollectionsKt.addAll(arrayList2, listLoadFileInfoFor);
            }
            ArrayList arrayListPlus = arrayList2;
            if (z) {
                ArrayList arrayList3 = arrayListPlus;
                CachedFileInfo.ModuleProxyFileCachedInfo moduleProxyFileCachedInfoFetchModuleProxyFileInfo = fetchModuleProxyFileInfo(moduleArtifact);
                arrayListPlus = CollectionsKt.plus((Collection<? extends CachedFileInfo>) arrayList3, moduleProxyFileCachedInfoFetchModuleProxyFileInfo != null ? moduleProxyFileCachedInfoFetchModuleProxyFileInfo : generateModuleProxyFileCachedInfo(moduleArtifact));
            }
            CollectionsKt.addAll(arrayList, arrayListPlus);
        }
        ArrayList<CachedFileInfo> arrayList4 = arrayList;
        for (CachedFileInfo cachedFileInfo : arrayList4) {
            this.headerToCachedInfo.put(cachedFileInfo.getJsIrHeader(), cachedFileInfo);
        }
        return arrayList4;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.JsMultiArtifactCache
    public void loadRequiredJsIrModules(Map<JsIrModuleHeader, CrossModuleReferences> crossModuleReferences) {
        Intrinsics.checkNotNullParameter(crossModuleReferences, "crossModuleReferences");
        for (Map.Entry<JsIrModuleHeader, CrossModuleReferences> entry : crossModuleReferences.entrySet()) {
            JsIrModuleHeader key = entry.getKey();
            CrossModuleReferences value = entry.getValue();
            CachedFileInfo cachedFileInfo = this.headerToCachedInfo.get(key);
            if (cachedFileInfo == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("artifact for module " + key.getModuleName(), null, null, 6, null);
                throw null;
            }
            Hash128Bits hash128BitsCrossModuleReferencesHashForIC = HashCalculatorForICKt.crossModuleReferencesHashForIC(value);
            if (key.getAssociatedModule() == null && !ICHash.m7111equalsimpl0(cachedFileInfo.getCrossFileReferencesHash(), hash128BitsCrossModuleReferencesHashForIC)) {
                key.setAssociatedModule(loadJsIrModule(cachedFileInfo));
            }
            if (key.getAssociatedModule() != null) {
                cachedFileInfo.m7145setCrossFileReferencesHashycJ14Ss(hash128BitsCrossModuleReferencesHashForIC);
                commitFileInfo(cachedFileInfo);
            }
        }
    }

    /* JADX INFO: compiled from: JsPerFileCache.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J+\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$CachedFileArtifacts;", "", "jsCodeFile", "Ljava/io/File;", "sourceMapFile", "tsDeclarationsFile", "(Ljava/io/File;Ljava/io/File;Ljava/io/File;)V", "getJsCodeFile", "()Ljava/io/File;", "getSourceMapFile", "getTsDeclarationsFile", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class CachedFileArtifacts {
        private final File jsCodeFile;
        private final File sourceMapFile;
        private final File tsDeclarationsFile;

        public static /* synthetic */ CachedFileArtifacts copy$default(CachedFileArtifacts cachedFileArtifacts, File file, File file2, File file3, int i, Object obj) {
            if ((i & 1) != 0) {
                file = cachedFileArtifacts.jsCodeFile;
            }
            if ((i & 2) != 0) {
                file2 = cachedFileArtifacts.sourceMapFile;
            }
            if ((i & 4) != 0) {
                file3 = cachedFileArtifacts.tsDeclarationsFile;
            }
            return cachedFileArtifacts.copy(file, file2, file3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final File getJsCodeFile() {
            return this.jsCodeFile;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final File getSourceMapFile() {
            return this.sourceMapFile;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final File getTsDeclarationsFile() {
            return this.tsDeclarationsFile;
        }

        public final CachedFileArtifacts copy(File jsCodeFile, File sourceMapFile, File tsDeclarationsFile) {
            Intrinsics.checkNotNullParameter(jsCodeFile, "jsCodeFile");
            return new CachedFileArtifacts(jsCodeFile, sourceMapFile, tsDeclarationsFile);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CachedFileArtifacts)) {
                return false;
            }
            CachedFileArtifacts cachedFileArtifacts = (CachedFileArtifacts) other;
            return Intrinsics.areEqual(this.jsCodeFile, cachedFileArtifacts.jsCodeFile) && Intrinsics.areEqual(this.sourceMapFile, cachedFileArtifacts.sourceMapFile) && Intrinsics.areEqual(this.tsDeclarationsFile, cachedFileArtifacts.tsDeclarationsFile);
        }

        public int hashCode() {
            int iHashCode = this.jsCodeFile.hashCode() * 31;
            File file = this.sourceMapFile;
            int iHashCode2 = (iHashCode + (file == null ? 0 : file.hashCode())) * 31;
            File file2 = this.tsDeclarationsFile;
            return iHashCode2 + (file2 != null ? file2.hashCode() : 0);
        }

        public String toString() {
            return "CachedFileArtifacts(jsCodeFile=" + this.jsCodeFile + ", sourceMapFile=" + this.sourceMapFile + ", tsDeclarationsFile=" + this.tsDeclarationsFile + ')';
        }

        public CachedFileArtifacts(File jsCodeFile, File file, File file2) {
            Intrinsics.checkNotNullParameter(jsCodeFile, "jsCodeFile");
            this.jsCodeFile = jsCodeFile;
            this.sourceMapFile = file;
            this.tsDeclarationsFile = file2;
        }

        public final File getJsCodeFile() {
            return this.jsCodeFile;
        }

        public final File getSourceMapFile() {
            return this.sourceMapFile;
        }

        public final File getTsDeclarationsFile() {
            return this.tsDeclarationsFile;
        }
    }

    /* JADX INFO: compiled from: JsPerFileCache.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/JsPerFileCache$LoadedJsIrModuleHeaders;", "", "mainHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "exportHeader", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;)V", "getExportHeader", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "getMainHeader", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class LoadedJsIrModuleHeaders {
        private final JsIrModuleHeader exportHeader;
        private final JsIrModuleHeader mainHeader;

        public static /* synthetic */ LoadedJsIrModuleHeaders copy$default(LoadedJsIrModuleHeaders loadedJsIrModuleHeaders, JsIrModuleHeader jsIrModuleHeader, JsIrModuleHeader jsIrModuleHeader2, int i, Object obj) {
            if ((i & 1) != 0) {
                jsIrModuleHeader = loadedJsIrModuleHeaders.mainHeader;
            }
            if ((i & 2) != 0) {
                jsIrModuleHeader2 = loadedJsIrModuleHeaders.exportHeader;
            }
            return loadedJsIrModuleHeaders.copy(jsIrModuleHeader, jsIrModuleHeader2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final JsIrModuleHeader getMainHeader() {
            return this.mainHeader;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final JsIrModuleHeader getExportHeader() {
            return this.exportHeader;
        }

        public final LoadedJsIrModuleHeaders copy(JsIrModuleHeader mainHeader, JsIrModuleHeader exportHeader) {
            Intrinsics.checkNotNullParameter(mainHeader, "mainHeader");
            return new LoadedJsIrModuleHeaders(mainHeader, exportHeader);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LoadedJsIrModuleHeaders)) {
                return false;
            }
            LoadedJsIrModuleHeaders loadedJsIrModuleHeaders = (LoadedJsIrModuleHeaders) other;
            return Intrinsics.areEqual(this.mainHeader, loadedJsIrModuleHeaders.mainHeader) && Intrinsics.areEqual(this.exportHeader, loadedJsIrModuleHeaders.exportHeader);
        }

        public int hashCode() {
            int iHashCode = this.mainHeader.hashCode() * 31;
            JsIrModuleHeader jsIrModuleHeader = this.exportHeader;
            return iHashCode + (jsIrModuleHeader == null ? 0 : jsIrModuleHeader.hashCode());
        }

        public String toString() {
            return "LoadedJsIrModuleHeaders(mainHeader=" + this.mainHeader + ", exportHeader=" + this.exportHeader + ')';
        }

        public LoadedJsIrModuleHeaders(JsIrModuleHeader mainHeader, JsIrModuleHeader jsIrModuleHeader) {
            Intrinsics.checkNotNullParameter(mainHeader, "mainHeader");
            this.mainHeader = mainHeader;
            this.exportHeader = jsIrModuleHeader;
        }

        public final JsIrModuleHeader getExportHeader() {
            return this.exportHeader;
        }

        public final JsIrModuleHeader getMainHeader() {
            return this.mainHeader;
        }
    }

    private final boolean hasExportFile(List<? extends CachedFileInfo> list) {
        return list.size() > 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CachedFileInfo.ExportFileCachedInfo fetchFileInfoForExportedPart(CodedInputStream codedInputStream, CachedFileInfo.MainFileCachedInfo mainFileCachedInfo) {
        if (!codedInputStream.readBool()) {
            return null;
        }
        return (CachedFileInfo.ExportFileCachedInfo) loadSingleCachedFileInfo(codedInputStream, new CachedFileInfo.ExportFileCachedInfo(mainFileCachedInfo.getModuleArtifact(), mainFileCachedInfo.getFileArtifact(), null, null, 12, null));
    }
}
