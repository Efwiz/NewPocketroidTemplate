package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.CityHashKt;
import org.jetbrains.kotlin.backend.common.serialization.FingerprintHash;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IncrementalCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0000\u0018\u0000 J2\u00020\u0001:\u0004IJKLB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J4\u0010(\u001a\u00020)2\u001e\u0010*\u001a\u001a\u0012\u0004\u0012\u00020\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020+0\u00130\u00132\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\u0018\u0010-\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0013J\u0006\u0010.\u001a\u00020/JB\u00100\u001a\u0002012\u0018\u00102\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u00132\u001e\u0010*\u001a\u001a\u0012\u0004\u0012\u00020\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020+0\u00130\u0013H\u0002J.\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00142\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020+0\u0013H\u0002ø\u0001\u0000¢\u0006\u0004\b6\u00107J\u001a\u00108\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0013H\u0002J\u0018\u00109\u001a\u00020\u001e2\u0006\u00105\u001a\u00020\u0014ø\u0001\u0000¢\u0006\u0004\b:\u0010;J\"\u0010<\u001a\u00020\u001e2\u0006\u00105\u001a\u00020\u00142\u0006\u0010=\u001a\u00020\u0011H\u0002ø\u0001\u0000¢\u0006\u0004\b>\u0010?J \u0010@\u001a\u0002012\u0006\u00105\u001a\u00020\u00142\u0006\u0010A\u001a\u00020\u001eø\u0001\u0000¢\u0006\u0004\bB\u0010CJ\u001e\u0010D\u001a\u00020\u0005*\u00020\u00142\u0006\u0010E\u001a\u00020FH\u0002ø\u0001\u0000¢\u0006\u0004\bG\u0010HR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R-\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000f\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u001e0\u001dj\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u001e`\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010 \u001a\u0004\u0018\u00010!8FX\u0086\u0084\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\f\n\u0004\b$\u0010\u000f\u001a\u0004\b\"\u0010#R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00140\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache;", "", "library", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "cacheDir", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;Ljava/io/File;)V", "getCacheDir", "()Ljava/io/File;", "cacheHeaderFile", "cacheHeaderFromDisk", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$CacheHeader;", "getCacheHeaderFromDisk", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$CacheHeader;", "cacheHeaderFromDisk$delegate", "Lkotlin/Lazy;", "cacheHeaderShouldBeUpdated", "", "filesWithStubbedSignatures", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getFilesWithStubbedSignatures", "()Ljava/util/Map;", "filesWithStubbedSignatures$delegate", "idSignatureSerialization", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization;", "kotlinLibrarySourceFileMetadata", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "Lkotlin/collections/HashMap;", "libraryFileFromHeader", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "getLibraryFileFromHeader-y7ymc5g", "()Ljava/lang/String;", "libraryFileFromHeader$delegate", "modifiedSrcFiles", "removedSrcFiles", "stubbedSymbolsFile", "buildAndCommitCacheArtifact", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCacheArtifact;", "signatureToIndexMapping", "", "stubbedSignatures", "collectFilesWithStubbedSignatures", "collectModifiedFiles", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$ModifiedFiles;", "commitFilesWithStubbedSignatures", "", "updatedFilesWithStubbedSignatures", "commitSourceFileMetadata", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "srcFile", "commitSourceFileMetadata-9XGJH6U", "(Ljava/lang/String;Ljava/util/Map;)Lorg/jetbrains/kotlin/ir/backend/js/ic/SourceFileCacheArtifact;", "fetchFilesWithStubbedSymbols", "fetchSourceFileFullMetadata", "fetchSourceFileFullMetadata-wxjXL9s", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "fetchSourceFileMetadata", "loadSignatures", "fetchSourceFileMetadata-9XGJH6U", "(Ljava/lang/String;Z)Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "updateSourceFileMetadata", "sourceFileMetadata", "updateSourceFileMetadata-9XGJH6U", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;)V", "getCacheFile", "suffix", "", "getCacheFile-9XGJH6U", "(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;", "CacheHeader", "Companion", "KotlinSourceFileMetadataFromDisk", "ModifiedFiles", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCache {
    private final File cacheDir;
    private final File cacheHeaderFile;

    /* JADX INFO: renamed from: cacheHeaderFromDisk$delegate, reason: from kotlin metadata */
    private final Lazy cacheHeaderFromDisk;
    private boolean cacheHeaderShouldBeUpdated;

    /* JADX INFO: renamed from: filesWithStubbedSignatures$delegate, reason: from kotlin metadata */
    private final Lazy filesWithStubbedSignatures;
    private final IdSignatureSerialization idSignatureSerialization;
    private final HashMap<KotlinSourceFile, KotlinSourceFileMetadata> kotlinLibrarySourceFileMetadata;
    private final KotlinLibraryHeader library;

    /* JADX INFO: renamed from: libraryFileFromHeader$delegate, reason: from kotlin metadata */
    private final Lazy libraryFileFromHeader;
    private Set<KotlinSourceFile> modifiedSrcFiles;
    private Set<KotlinSourceFile> removedSrcFiles;
    private final File stubbedSymbolsFile;

    public IncrementalCache(KotlinLibraryHeader library, File cacheDir) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(cacheDir, "cacheDir");
        this.library = library;
        this.cacheDir = cacheDir;
        this.cacheHeaderFile = new File(cacheDir, "ic.header.bin");
        this.stubbedSymbolsFile = new File(cacheDir, "ic.stubbed-symbols.bin");
        this.removedSrcFiles = SetsKt.emptySet();
        this.modifiedSrcFiles = SetsKt.emptySet();
        this.kotlinLibrarySourceFileMetadata = new HashMap<>();
        this.idSignatureSerialization = new IdSignatureSerialization(library);
        this.cacheHeaderFromDisk = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<CacheHeader>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$cacheHeaderFromDisk$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IncrementalCache.CacheHeader invoke() {
                File file = this.this$0.cacheHeaderFile;
                if (!file.exists()) {
                    return null;
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
                    Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
                    IncrementalCache.CacheHeader cacheHeaderFromProtoStream = IncrementalCache.CacheHeader.INSTANCE.fromProtoStream(codedInputStreamNewInstance);
                    CloseableKt.closeFinally(fileInputStream, null);
                    return cacheHeaderFromProtoStream;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            }
        });
        this.filesWithStubbedSignatures = LazyKt.lazy(new Function0<Map<KotlinSourceFile, ? extends Set<? extends IdSignature>>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$filesWithStubbedSignatures$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KotlinSourceFile, ? extends Set<? extends IdSignature>> invoke() {
                return this.this$0.fetchFilesWithStubbedSymbols();
            }
        });
        this.libraryFileFromHeader = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<KotlinLibraryFile>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$libraryFileFromHeader$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ KotlinLibraryFile invoke() {
                String strM7143invokey7ymc5g = m7143invokey7ymc5g();
                if (strM7143invokey7ymc5g != null) {
                    return KotlinLibraryFile.m7149boximpl(strM7143invokey7ymc5g);
                }
                return null;
            }

            /* JADX INFO: renamed from: invoke-y7ymc5g, reason: not valid java name */
            public final String m7143invokey7ymc5g() {
                IncrementalCache.CacheHeader cacheHeaderFromDisk = this.this$0.getCacheHeaderFromDisk();
                if (cacheHeaderFromDisk != null) {
                    return cacheHeaderFromDisk.getLibraryFile();
                }
                return null;
            }
        });
    }

    public final File getCacheDir() {
        return this.cacheDir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CacheHeader getCacheHeaderFromDisk() {
        return (CacheHeader) this.cacheHeaderFromDisk.getValue();
    }

    private final Map<KotlinSourceFile, Set<IdSignature>> getFilesWithStubbedSignatures() {
        return (Map) this.filesWithStubbedSignatures.getValue();
    }

    /* JADX INFO: renamed from: getLibraryFileFromHeader-y7ymc5g, reason: not valid java name */
    public final String m7139getLibraryFileFromHeadery7ymc5g() {
        KotlinLibraryFile kotlinLibraryFile = (KotlinLibraryFile) this.libraryFileFromHeader.getValue();
        if (kotlinLibraryFile != null) {
            return kotlinLibraryFile.m7157unboximpl();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IncrementalCache.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B+\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\fJ\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u0019\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0007\u001a\u0004\u0018\u00010\bø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$CacheHeader;", "", "library", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;)V", "libraryFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "libraryFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "sourceFileFingerprints", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;Ljava/util/Map;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getLibraryFile-dAnkW_k", "()Ljava/lang/String;", "Ljava/lang/String;", "getLibraryFingerprint-jverJr4", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "getSourceFileFingerprints", "()Ljava/util/Map;", "toProtoStream", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class CacheHeader {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final String libraryFile;
        private final Hash128Bits libraryFingerprint;
        private final Map<KotlinSourceFile, FingerprintHash> sourceFileFingerprints;

        public /* synthetic */ CacheHeader(String str, Hash128Bits hash128Bits, Map map, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, hash128Bits, map);
        }

        private CacheHeader(String libraryFile, Hash128Bits hash128Bits, Map<KotlinSourceFile, FingerprintHash> sourceFileFingerprints) {
            Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
            Intrinsics.checkNotNullParameter(sourceFileFingerprints, "sourceFileFingerprints");
            this.libraryFile = libraryFile;
            this.libraryFingerprint = hash128Bits;
            this.sourceFileFingerprints = sourceFileFingerprints;
        }

        /* JADX INFO: renamed from: getLibraryFile-dAnkW_k, reason: not valid java name and from getter */
        public final String getLibraryFile() {
            return this.libraryFile;
        }

        /* JADX INFO: renamed from: getLibraryFingerprint-jverJr4, reason: not valid java name and from getter */
        public final Hash128Bits getLibraryFingerprint() {
            return this.libraryFingerprint;
        }

        public final Map<KotlinSourceFile, FingerprintHash> getSourceFileFingerprints() {
            return this.sourceFileFingerprints;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public CacheHeader(KotlinLibraryHeader library) {
            this(library.mo7159getLibraryFiledAnkW_k(), library.mo7160getLibraryFingerprintjverJr4(), library.getSourceFileFingerprints(), null);
            Intrinsics.checkNotNullParameter(library, "library");
        }

        public final void toProtoStream(CodedOutputStream out) throws IOException {
            Unit unit;
            Intrinsics.checkNotNullParameter(out, "out");
            KotlinLibraryFile.m7155toProtoStreamimpl(this.libraryFile, out);
            Hash128Bits hash128Bits = this.libraryFingerprint;
            if (hash128Bits != null) {
                HashCalculatorForICKt.toProtoStream(hash128Bits, out);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("library fingerprint", this.libraryFile, null, 4, null);
                throw null;
            }
            out.writeInt32NoTag(this.sourceFileFingerprints.size());
            for (Map.Entry<KotlinSourceFile, FingerprintHash> entry : this.sourceFileFingerprints.entrySet()) {
                String strM7171unboximpl = entry.getKey().m7171unboximpl();
                Hash128Bits hash128BitsM6130unboximpl = entry.getValue().m6130unboximpl();
                KotlinSourceFile.m7169toProtoStreamimpl(strM7171unboximpl, out);
                HashCalculatorForICKt.toProtoStream(hash128BitsM6130unboximpl, out);
            }
        }

        /* JADX INFO: compiled from: IncrementalCache.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$CacheHeader$Companion;", "", "()V", "fromProtoStream", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$CacheHeader;", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final CacheHeader fromProtoStream(CodedInputStream input) throws IOException {
                Intrinsics.checkNotNullParameter(input, "input");
                String strM7158fromProtoStreamBZAKev0 = KotlinLibraryFile.INSTANCE.m7158fromProtoStreamBZAKev0(input);
                Hash128Bits hash128BitsM6124constructorimpl = FingerprintHash.m6124constructorimpl(HashCalculatorForICKt.readHash128BitsFromProtoStream(input));
                int int32 = input.readInt32();
                HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(int32);
                for (int i = 0; i < int32; i++) {
                    mapNewHashMapWithExpectedSize.put(KotlinSourceFile.m7163boximpl(KotlinSourceFile.INSTANCE.m7172fromProtoStreamjxi6rco(input)), FingerprintHash.m6123boximpl(FingerprintHash.m6124constructorimpl(HashCalculatorForICKt.readHash128BitsFromProtoStream(input))));
                }
                return new CacheHeader(strM7158fromProtoStreamBZAKev0, hash128BitsM6124constructorimpl, mapNewHashMapWithExpectedSize, null);
            }
        }
    }

    /* JADX INFO: compiled from: IncrementalCache.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u00070\u0003¢\u0006\u0002\u0010\tR&\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u00070\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$KotlinSourceFileMetadataFromDisk;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "inverseDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "directDependencies", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;)V", "getDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "getInverseDependencies", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class KotlinSourceFileMetadataFromDisk extends KotlinSourceFileMetadata {
        private final KotlinSourceFileMap<Map<IdSignature, ICHash>> directDependencies;
        private final KotlinSourceFileMap<Set<IdSignature>> inverseDependencies;

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
        public KotlinSourceFileMap<Set<IdSignature>> getInverseDependencies() {
            return this.inverseDependencies;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileMetadata
        public KotlinSourceFileMap<Map<IdSignature, ICHash>> getDirectDependencies() {
            return this.directDependencies;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public KotlinSourceFileMetadataFromDisk(KotlinSourceFileMap<? extends Set<? extends IdSignature>> inverseDependencies, KotlinSourceFileMap<? extends Map<IdSignature, ICHash>> directDependencies) {
            Intrinsics.checkNotNullParameter(inverseDependencies, "inverseDependencies");
            Intrinsics.checkNotNullParameter(directDependencies, "directDependencies");
            this.inverseDependencies = inverseDependencies;
            this.directDependencies = directDependencies;
        }
    }

    /* JADX INFO: renamed from: getCacheFile-9XGJH6U, reason: not valid java name */
    private final File m7137getCacheFile9XGJH6U(String str, String str2) {
        return new File(this.cacheDir, new File(str).getName() + '.' + CityHashKt.cityHash64String(str) + '.' + str2);
    }

    public final IncrementalCacheArtifact buildAndCommitCacheArtifact(Map<KotlinSourceFile, ? extends Map<IdSignature, Integer>> signatureToIndexMapping, Set<? extends IdSignature> stubbedSignatures) {
        Map<KotlinSourceFile, FingerprintHash> sourceFileFingerprints;
        Set<KotlinSourceFile> setKeySet;
        Set<IdSignature> setEmptySet;
        Intrinsics.checkNotNullParameter(signatureToIndexMapping, "signatureToIndexMapping");
        Intrinsics.checkNotNullParameter(stubbedSignatures, "stubbedSignatures");
        if (this.cacheHeaderShouldBeUpdated) {
            CacheHeader cacheHeader = new CacheHeader(this.library);
            File file = this.cacheHeaderFile;
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
                Intrinsics.checkNotNull(codedOutputStreamNewInstance);
                cacheHeader.toProtoStream(codedOutputStreamNewInstance);
                codedOutputStreamNewInstance.flush();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                setKeySet = cacheHeader.getSourceFileFingerprints().keySet();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } else {
            CacheHeader cacheHeaderFromDisk = getCacheHeaderFromDisk();
            if (cacheHeaderFromDisk == null || (sourceFileFingerprints = cacheHeaderFromDisk.getSourceFileFingerprints()) == null || (setKeySet = sourceFileFingerprints.keySet()) == null) {
                ICUtilsKt.m7124notFoundIcError5T69zGA$default("source file fingerprints", this.library.mo7159getLibraryFiledAnkW_k(), null, 4, null);
                throw null;
            }
        }
        Iterator<KotlinSourceFile> it = this.removedSrcFiles.iterator();
        while (it.hasNext()) {
            String strM7171unboximpl = it.next().m7171unboximpl();
            m7137getCacheFile9XGJH6U(strM7171unboximpl, "ast.bin").delete();
            m7137getCacheFile9XGJH6U(strM7171unboximpl, "metadata.bin").delete();
        }
        HashMap map = new HashMap();
        Set<KotlinSourceFile> set = setKeySet;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it2 = set.iterator();
        while (it2.hasNext()) {
            String strM7171unboximpl2 = ((KotlinSourceFile) it2.next()).m7171unboximpl();
            Map<IdSignature, Integer> mapEmptyMap = signatureToIndexMapping.get(KotlinSourceFile.m7163boximpl(strM7171unboximpl2));
            if (mapEmptyMap == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            SourceFileCacheArtifact sourceFileCacheArtifactM7135commitSourceFileMetadata9XGJH6U = m7135commitSourceFileMetadata9XGJH6U(strM7171unboximpl2, mapEmptyMap);
            if (!(sourceFileCacheArtifactM7135commitSourceFileMetadata9XGJH6U instanceof SourceFileCacheArtifact.CommitMetadata)) {
                setEmptySet = getFilesWithStubbedSignatures().get(KotlinSourceFile.m7163boximpl(strM7171unboximpl2));
                if (setEmptySet == null) {
                    setEmptySet = SetsKt.emptySet();
                }
            } else {
                Set<IdSignature> setKeySet2 = mapEmptyMap.keySet();
                HashSet hashSet = new HashSet();
                for (Object obj : setKeySet2) {
                    if (stubbedSignatures.contains((IdSignature) obj)) {
                        hashSet.add(obj);
                    }
                }
                setEmptySet = hashSet;
            }
            if (!setEmptySet.isEmpty()) {
                map.put(KotlinSourceFile.m7163boximpl(strM7171unboximpl2), setEmptySet);
            }
            arrayList.add(sourceFileCacheArtifactM7135commitSourceFileMetadata9XGJH6U);
        }
        commitFilesWithStubbedSignatures(map, signatureToIndexMapping);
        return new IncrementalCacheArtifact(this.cacheDir, !this.removedSrcFiles.isEmpty(), arrayList, this.library.getJsOutputName());
    }

    /* JADX INFO: compiled from: IncrementalCache.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\nJ\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0015\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\u0015\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003JU\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u00062\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IncrementalCache$ModifiedFiles;", "", "addedFiles", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "removedFiles", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "modifiedFiles", "nonModifiedFiles", "(Ljava/util/Collection;Ljava/util/Map;Ljava/util/Map;Ljava/util/Collection;)V", "getAddedFiles", "()Ljava/util/Collection;", "getModifiedFiles", "()Ljava/util/Map;", "getNonModifiedFiles", "getRemovedFiles", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ModifiedFiles {
        private final Collection<KotlinSourceFile> addedFiles;
        private final Map<KotlinSourceFile, KotlinSourceFileMetadata> modifiedFiles;
        private final Collection<KotlinSourceFile> nonModifiedFiles;
        private final Map<KotlinSourceFile, KotlinSourceFileMetadata> removedFiles;

        public ModifiedFiles() {
            this(null, null, null, null, 15, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ModifiedFiles copy$default(ModifiedFiles modifiedFiles, Collection collection, Map map, Map map2, Collection collection2, int i, Object obj) {
            if ((i & 1) != 0) {
                collection = modifiedFiles.addedFiles;
            }
            if ((i & 2) != 0) {
                map = modifiedFiles.removedFiles;
            }
            if ((i & 4) != 0) {
                map2 = modifiedFiles.modifiedFiles;
            }
            if ((i & 8) != 0) {
                collection2 = modifiedFiles.nonModifiedFiles;
            }
            return modifiedFiles.copy(collection, map, map2, collection2);
        }

        public final Collection<KotlinSourceFile> component1() {
            return this.addedFiles;
        }

        public final Map<KotlinSourceFile, KotlinSourceFileMetadata> component2() {
            return this.removedFiles;
        }

        public final Map<KotlinSourceFile, KotlinSourceFileMetadata> component3() {
            return this.modifiedFiles;
        }

        public final Collection<KotlinSourceFile> component4() {
            return this.nonModifiedFiles;
        }

        public final ModifiedFiles copy(Collection<KotlinSourceFile> addedFiles, Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> removedFiles, Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> modifiedFiles, Collection<KotlinSourceFile> nonModifiedFiles) {
            Intrinsics.checkNotNullParameter(addedFiles, "addedFiles");
            Intrinsics.checkNotNullParameter(removedFiles, "removedFiles");
            Intrinsics.checkNotNullParameter(modifiedFiles, "modifiedFiles");
            Intrinsics.checkNotNullParameter(nonModifiedFiles, "nonModifiedFiles");
            return new ModifiedFiles(addedFiles, removedFiles, modifiedFiles, nonModifiedFiles);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ModifiedFiles)) {
                return false;
            }
            ModifiedFiles modifiedFiles = (ModifiedFiles) other;
            return Intrinsics.areEqual(this.addedFiles, modifiedFiles.addedFiles) && Intrinsics.areEqual(this.removedFiles, modifiedFiles.removedFiles) && Intrinsics.areEqual(this.modifiedFiles, modifiedFiles.modifiedFiles) && Intrinsics.areEqual(this.nonModifiedFiles, modifiedFiles.nonModifiedFiles);
        }

        public int hashCode() {
            return (((((this.addedFiles.hashCode() * 31) + this.removedFiles.hashCode()) * 31) + this.modifiedFiles.hashCode()) * 31) + this.nonModifiedFiles.hashCode();
        }

        public String toString() {
            return "ModifiedFiles(addedFiles=" + this.addedFiles + ", removedFiles=" + this.removedFiles + ", modifiedFiles=" + this.modifiedFiles + ", nonModifiedFiles=" + this.nonModifiedFiles + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ModifiedFiles(Collection<KotlinSourceFile> addedFiles, Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> removedFiles, Map<KotlinSourceFile, ? extends KotlinSourceFileMetadata> modifiedFiles, Collection<KotlinSourceFile> nonModifiedFiles) {
            Intrinsics.checkNotNullParameter(addedFiles, "addedFiles");
            Intrinsics.checkNotNullParameter(removedFiles, "removedFiles");
            Intrinsics.checkNotNullParameter(modifiedFiles, "modifiedFiles");
            Intrinsics.checkNotNullParameter(nonModifiedFiles, "nonModifiedFiles");
            this.addedFiles = addedFiles;
            this.removedFiles = removedFiles;
            this.modifiedFiles = modifiedFiles;
            this.nonModifiedFiles = nonModifiedFiles;
        }

        public /* synthetic */ ModifiedFiles(List list, Map map, Map map2, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? kotlin.collections.CollectionsKt.emptyList() : list, (i & 2) != 0 ? MapsKt.emptyMap() : map, (i & 4) != 0 ? MapsKt.emptyMap() : map2, (i & 8) != 0 ? kotlin.collections.CollectionsKt.emptyList() : list2);
        }

        public final Collection<KotlinSourceFile> getAddedFiles() {
            return this.addedFiles;
        }

        public final Map<KotlinSourceFile, KotlinSourceFileMetadata> getRemovedFiles() {
            return this.removedFiles;
        }

        public final Map<KotlinSourceFile, KotlinSourceFileMetadata> getModifiedFiles() {
            return this.modifiedFiles;
        }

        public final Collection<KotlinSourceFile> getNonModifiedFiles() {
            return this.nonModifiedFiles;
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002d  */
    public final ModifiedFiles collectModifiedFiles() {
        Map<KotlinSourceFile, FingerprintHash> mapEmptyMap;
        boolean zM6126equalsimpl0;
        CacheHeader cacheHeaderFromDisk = getCacheHeaderFromDisk();
        if (cacheHeaderFromDisk == null || (mapEmptyMap = cacheHeaderFromDisk.getSourceFileFingerprints()) == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        CacheHeader cacheHeaderFromDisk2 = getCacheHeaderFromDisk();
        Hash128Bits libraryFingerprint = cacheHeaderFromDisk2 != null ? cacheHeaderFromDisk2.getLibraryFingerprint() : null;
        Hash128Bits hash128BitsMo7160getLibraryFingerprintjverJr4 = this.library.mo7160getLibraryFingerprintjverJr4();
        if (libraryFingerprint == null) {
            if (hash128BitsMo7160getLibraryFingerprintjverJr4 == null) {
                zM6126equalsimpl0 = true;
            } else {
                zM6126equalsimpl0 = false;
            }
        } else if (hash128BitsMo7160getLibraryFingerprintjverJr4 == null) {
            zM6126equalsimpl0 = false;
        } else {
            zM6126equalsimpl0 = FingerprintHash.m6126equalsimpl0(libraryFingerprint, hash128BitsMo7160getLibraryFingerprintjverJr4);
        }
        if (zM6126equalsimpl0) {
            return new ModifiedFiles(kotlin.collections.CollectionsKt.emptyList(), MapsKt.emptyMap(), MapsKt.emptyMap(), mapEmptyMap.keySet());
        }
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<KotlinSourceFile, FingerprintHash> entry : this.library.getSourceFileFingerprints().entrySet()) {
            String strM7171unboximpl = entry.getKey().m7171unboximpl();
            Hash128Bits hash128BitsM6130unboximpl = entry.getValue().m6130unboximpl();
            FingerprintHash fingerprintHash = mapEmptyMap.get(KotlinSourceFile.m7163boximpl(strM7171unboximpl));
            Hash128Bits hash128BitsM6130unboximpl2 = fingerprintHash != null ? fingerprintHash.m6130unboximpl() : null;
            if (hash128BitsM6130unboximpl2 == null ? false : FingerprintHash.m6126equalsimpl0(hash128BitsM6130unboximpl2, hash128BitsM6130unboximpl)) {
                arrayList2.add(KotlinSourceFile.m7163boximpl(strM7171unboximpl));
            } else {
                KotlinSourceFile kotlinSourceFileM7163boximpl = KotlinSourceFile.m7163boximpl(strM7171unboximpl);
                if (hash128BitsM6130unboximpl2 == null) {
                    arrayList.add(kotlinSourceFileM7163boximpl);
                } else {
                    map.put(kotlinSourceFileM7163boximpl, m7136fetchSourceFileMetadata9XGJH6U(strM7171unboximpl, false));
                }
            }
        }
        Set setMinus = SetsKt.minus((Set) mapEmptyMap.keySet(), (Iterable) this.library.getSourceFileFingerprints().keySet());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(setMinus, 10)), 16));
        for (Object obj : setMinus) {
            linkedHashMap.put(obj, m7136fetchSourceFileMetadata9XGJH6U(((KotlinSourceFile) obj).m7171unboximpl(), false));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        this.removedSrcFiles = linkedHashMap2.keySet();
        Set<KotlinSourceFile> setKeySet = map.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        this.modifiedSrcFiles = setKeySet;
        this.cacheHeaderShouldBeUpdated = true;
        return new ModifiedFiles(arrayList, linkedHashMap2, map, arrayList2);
    }

    /* JADX INFO: renamed from: fetchSourceFileFullMetadata-wxjXL9s, reason: not valid java name */
    public final KotlinSourceFileMetadata m7138fetchSourceFileFullMetadatawxjXL9s(String srcFile) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        return m7136fetchSourceFileMetadata9XGJH6U(srcFile, true);
    }

    /* JADX INFO: renamed from: updateSourceFileMetadata-9XGJH6U, reason: not valid java name */
    public final void m7140updateSourceFileMetadata9XGJH6U(String srcFile, KotlinSourceFileMetadata sourceFileMetadata) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        Intrinsics.checkNotNullParameter(sourceFileMetadata, "sourceFileMetadata");
        this.kotlinLibrarySourceFileMetadata.put(KotlinSourceFile.m7163boximpl(srcFile), sourceFileMetadata);
    }

    public final Map<KotlinSourceFile, Set<IdSignature>> collectFilesWithStubbedSignatures() {
        return getFilesWithStubbedSignatures();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<KotlinSourceFile, Set<IdSignature>> fetchFilesWithStubbedSymbols() {
        File file = this.stubbedSymbolsFile;
        HashMap map = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
                Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
                int int32 = codedInputStreamNewInstance.readInt32();
                HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(int32);
                for (int i = 0; i < int32; i++) {
                    HashMap map2 = mapNewHashMapWithExpectedSize;
                    String strM7172fromProtoStreamjxi6rco = KotlinSourceFile.INSTANCE.m7172fromProtoStreamjxi6rco(codedInputStreamNewInstance);
                    IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s = this.idSignatureSerialization.m7130getIdSignatureDeserializerwxjXL9s(strM7172fromProtoStreamjxi6rco);
                    if (this.modifiedSrcFiles.contains(KotlinSourceFile.m7163boximpl(strM7172fromProtoStreamjxi6rco)) || this.removedSrcFiles.contains(KotlinSourceFile.m7163boximpl(strM7172fromProtoStreamjxi6rco))) {
                        int int33 = codedInputStreamNewInstance.readInt32();
                        for (int i2 = 0; i2 < int33; i2++) {
                            idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s.skipIdSignature(codedInputStreamNewInstance);
                        }
                    } else {
                        int int34 = codedInputStreamNewInstance.readInt32();
                        HashSet hashSetNewHashSetWithExpectedSize = CollectionsKt.newHashSetWithExpectedSize(int34);
                        for (int i3 = 0; i3 < int34; i3++) {
                            hashSetNewHashSetWithExpectedSize.add(idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s.deserializeIdSignature(codedInputStreamNewInstance));
                        }
                        map2.put(KotlinSourceFile.m7163boximpl(strM7172fromProtoStreamjxi6rco), hashSetNewHashSetWithExpectedSize);
                    }
                }
                HashMap map3 = mapNewHashMapWithExpectedSize;
                CloseableKt.closeFinally(fileInputStream, null);
                map = map3;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        }
        return map == null ? MapsKt.emptyMap() : map;
    }

    private final void commitFilesWithStubbedSignatures(Map<KotlinSourceFile, ? extends Set<? extends IdSignature>> updatedFilesWithStubbedSignatures, Map<KotlinSourceFile, ? extends Map<IdSignature, Integer>> signatureToIndexMapping) {
        if (updatedFilesWithStubbedSignatures.isEmpty()) {
            this.stubbedSymbolsFile.delete();
            return;
        }
        if (Intrinsics.areEqual(updatedFilesWithStubbedSignatures, getFilesWithStubbedSignatures())) {
            return;
        }
        File file = this.stubbedSymbolsFile;
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(fileOutputStream);
            Intrinsics.checkNotNull(codedOutputStreamNewInstance);
            codedOutputStreamNewInstance.writeInt32NoTag(updatedFilesWithStubbedSignatures.size());
            for (Map.Entry<KotlinSourceFile, ? extends Set<? extends IdSignature>> entry : updatedFilesWithStubbedSignatures.entrySet()) {
                String strM7171unboximpl = entry.getKey().m7171unboximpl();
                Set<? extends IdSignature> value = entry.getValue();
                IdSignatureSerialization idSignatureSerialization = this.idSignatureSerialization;
                Map<IdSignature, Integer> mapEmptyMap = signatureToIndexMapping.get(KotlinSourceFile.m7163boximpl(strM7171unboximpl));
                if (mapEmptyMap == null) {
                    mapEmptyMap = MapsKt.emptyMap();
                }
                IdSignatureSerialization.IdSignatureICSerializer idSignatureICSerializerM7131getIdSignatureSerializer9XGJH6U = idSignatureSerialization.m7131getIdSignatureSerializer9XGJH6U(strM7171unboximpl, mapEmptyMap);
                KotlinSourceFile.m7169toProtoStreamimpl(strM7171unboximpl, codedOutputStreamNewInstance);
                codedOutputStreamNewInstance.writeInt32NoTag(value.size());
                Iterator<? extends IdSignature> it = value.iterator();
                while (it.hasNext()) {
                    idSignatureICSerializerM7131getIdSignatureSerializer9XGJH6U.serializeIdSignature(codedOutputStreamNewInstance, it.next());
                }
            }
            codedOutputStreamNewInstance.flush();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: renamed from: fetchSourceFileMetadata-9XGJH6U, reason: not valid java name */
    private final KotlinSourceFileMetadata m7136fetchSourceFileMetadata9XGJH6U(String srcFile, boolean loadSignatures) {
        HashMap<KotlinSourceFile, KotlinSourceFileMetadata> map = this.kotlinLibrarySourceFileMetadata;
        KotlinSourceFile kotlinSourceFileM7163boximpl = KotlinSourceFile.m7163boximpl(srcFile);
        KotlinSourceFileMetadata kotlinSourceFileMetadata = map.get(kotlinSourceFileM7163boximpl);
        if (kotlinSourceFileMetadata == null) {
            IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s = this.idSignatureSerialization.m7130getIdSignatureDeserializerwxjXL9s(srcFile);
            File fileM7137getCacheFile9XGJH6U = m7137getCacheFile9XGJH6U(srcFile, "metadata.bin");
            KotlinSourceFileMetadataFromDisk kotlinSourceFileMetadataFromDisk = null;
            if (fileM7137getCacheFile9XGJH6U.exists()) {
                FileInputStream fileInputStream = new FileInputStream(fileM7137getCacheFile9XGJH6U);
                try {
                    CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(fileInputStream);
                    Intrinsics.checkNotNullExpressionValue(codedInputStreamNewInstance, "newInstance(...)");
                    KotlinSourceFileMetadataFromDisk kotlinSourceFileMetadataFromDisk2 = new KotlinSourceFileMetadataFromDisk(new KotlinSourceFileMap(fetchSourceFileMetadata_9XGJH6U$lambda$12$readInverseDependencies(codedInputStreamNewInstance, loadSignatures, idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s)), new KotlinSourceFileMap(fetchSourceFileMetadata_9XGJH6U$lambda$12$readDirectDependencies(codedInputStreamNewInstance, loadSignatures, idSignatureICDeserializerM7130getIdSignatureDeserializerwxjXL9s)));
                    CloseableKt.closeFinally(fileInputStream, null);
                    kotlinSourceFileMetadataFromDisk = kotlinSourceFileMetadataFromDisk2;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            }
            if (kotlinSourceFileMetadataFromDisk != null) {
                kotlinSourceFileMetadata = kotlinSourceFileMetadataFromDisk;
            } else {
                kotlinSourceFileMetadata = KotlinSourceFileMetadataNotExist.INSTANCE;
            }
            map.put(kotlinSourceFileM7163boximpl, kotlinSourceFileMetadata);
        }
        return kotlinSourceFileMetadata;
    }

    private static final <T> Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> fetchSourceFileMetadata_9XGJH6U$lambda$12$readDependencies(CodedInputStream codedInputStream, Function0<? extends T> function0) throws IOException {
        int int32 = codedInputStream.readInt32();
        HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(int32);
        for (int i = 0; i < int32; i++) {
            HashMap map = mapNewHashMapWithExpectedSize;
            String strM7158fromProtoStreamBZAKev0 = KotlinLibraryFile.INSTANCE.m7158fromProtoStreamBZAKev0(codedInputStream);
            int int33 = codedInputStream.readInt32();
            HashMap mapNewHashMapWithExpectedSize2 = CollectionsKt.newHashMapWithExpectedSize(int33);
            for (int i2 = 0; i2 < int33; i2++) {
                mapNewHashMapWithExpectedSize2.put(KotlinSourceFile.m7163boximpl(KotlinSourceFile.INSTANCE.m7172fromProtoStreamjxi6rco(codedInputStream)), function0.invoke());
            }
            map.put(KotlinLibraryFile.m7149boximpl(strM7158fromProtoStreamBZAKev0), mapNewHashMapWithExpectedSize2);
        }
        return mapNewHashMapWithExpectedSize;
    }

    private static final Map<KotlinLibraryFile, Map<KotlinSourceFile, Map<IdSignature, ICHash>>> fetchSourceFileMetadata_9XGJH6U$lambda$12$readDirectDependencies(final CodedInputStream codedInputStream, final boolean z, final IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer) {
        return fetchSourceFileMetadata_9XGJH6U$lambda$12$readDependencies(codedInputStream, new Function0<Map<IdSignature, ? extends ICHash>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$fetchSourceFileMetadata$1$readDirectDependencies$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IdSignature, ? extends ICHash> invoke() throws IOException {
                int i = 0;
                if (z) {
                    int int32 = codedInputStream.readInt32();
                    IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer2 = idSignatureICDeserializer;
                    CodedInputStream codedInputStream2 = codedInputStream;
                    HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(int32);
                    while (i < int32) {
                        mapNewHashMapWithExpectedSize.put(idSignatureICDeserializer2.deserializeIdSignature(codedInputStream2), ICHash.m7107boximpl(ICHash.INSTANCE.m7116fromProtoStreamTVZkk9w(codedInputStream2)));
                        i++;
                    }
                    return mapNewHashMapWithExpectedSize;
                }
                int int33 = codedInputStream.readInt32();
                IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer3 = idSignatureICDeserializer;
                CodedInputStream codedInputStream3 = codedInputStream;
                while (i < int33) {
                    idSignatureICDeserializer3.skipIdSignature(codedInputStream3);
                    ICHash.INSTANCE.m7116fromProtoStreamTVZkk9w(codedInputStream3);
                    i++;
                }
                return MapsKt.emptyMap();
            }
        });
    }

    private static final Map<KotlinLibraryFile, Map<KotlinSourceFile, Set<IdSignature>>> fetchSourceFileMetadata_9XGJH6U$lambda$12$readInverseDependencies(final CodedInputStream codedInputStream, final boolean z, final IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer) {
        return fetchSourceFileMetadata_9XGJH6U$lambda$12$readDependencies(codedInputStream, new Function0<Set<? extends IdSignature>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$fetchSourceFileMetadata$1$readInverseDependencies$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends IdSignature> invoke() throws IOException {
                int i = 0;
                if (z) {
                    int int32 = codedInputStream.readInt32();
                    IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer2 = idSignatureICDeserializer;
                    CodedInputStream codedInputStream2 = codedInputStream;
                    HashSet hashSetNewHashSetWithExpectedSize = CollectionsKt.newHashSetWithExpectedSize(int32);
                    while (i < int32) {
                        hashSetNewHashSetWithExpectedSize.add(idSignatureICDeserializer2.deserializeIdSignature(codedInputStream2));
                        i++;
                    }
                    return hashSetNewHashSetWithExpectedSize;
                }
                int int33 = codedInputStream.readInt32();
                IdSignatureSerialization.IdSignatureICDeserializer idSignatureICDeserializer3 = idSignatureICDeserializer;
                CodedInputStream codedInputStream3 = codedInputStream;
                while (i < int33) {
                    idSignatureICDeserializer3.skipIdSignature(codedInputStream3);
                    i++;
                }
                return SetsKt.emptySet();
            }
        });
    }

    /* JADX INFO: renamed from: commitSourceFileMetadata-9XGJH6U, reason: not valid java name */
    private final SourceFileCacheArtifact m7135commitSourceFileMetadata9XGJH6U(String srcFile, Map<IdSignature, Integer> signatureToIndexMapping) {
        File fileM7137getCacheFile9XGJH6U = m7137getCacheFile9XGJH6U(srcFile, "ast.bin");
        KotlinSourceFileMetadata kotlinSourceFileMetadata = this.kotlinLibrarySourceFileMetadata.get(KotlinSourceFile.m7163boximpl(srcFile));
        if (kotlinSourceFileMetadata == null) {
            return new SourceFileCacheArtifact.DoNotChangeMetadata(srcFile, fileM7137getCacheFile9XGJH6U, null);
        }
        File fileM7137getCacheFile9XGJH6U2 = m7137getCacheFile9XGJH6U(srcFile, "metadata.bin");
        if (kotlinSourceFileMetadata.isEmpty()) {
            return new SourceFileCacheArtifact.RemoveMetadata(srcFile, fileM7137getCacheFile9XGJH6U, fileM7137getCacheFile9XGJH6U2, null);
        }
        if (kotlinSourceFileMetadata instanceof KotlinSourceFileMetadataFromDisk) {
            return new SourceFileCacheArtifact.DoNotChangeMetadata(srcFile, fileM7137getCacheFile9XGJH6U, null);
        }
        IdSignatureSerialization.IdSignatureICSerializer idSignatureICSerializerM7131getIdSignatureSerializer9XGJH6U = this.idSignatureSerialization.m7131getIdSignatureSerializer9XGJH6U(srcFile, signatureToIndexMapping);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        try {
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(byteArrayOutputStream2);
            Intrinsics.checkNotNull(codedOutputStreamNewInstance);
            commitSourceFileMetadata_9XGJH6U$writeDirectDependencies(codedOutputStreamNewInstance, idSignatureICSerializerM7131getIdSignatureSerializer9XGJH6U, kotlinSourceFileMetadata.getDirectDependencies());
            commitSourceFileMetadata_9XGJH6U$writeInverseDependencies(codedOutputStreamNewInstance, idSignatureICSerializerM7131getIdSignatureSerializer9XGJH6U, kotlinSourceFileMetadata.getInverseDependencies());
            codedOutputStreamNewInstance.flush();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(byteArrayOutputStream2, null);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNull(byteArray);
            return new SourceFileCacheArtifact.CommitMetadata(srcFile, fileM7137getCacheFile9XGJH6U, fileM7137getCacheFile9XGJH6U2, byteArray, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(byteArrayOutputStream2, th);
                throw th2;
            }
        }
    }

    private static final <T> void commitSourceFileMetadata_9XGJH6U$writeDependencies(CodedOutputStream codedOutputStream, KotlinSourceFileMap<? extends T> kotlinSourceFileMap, Function1<? super T, Unit> function1) throws IOException {
        codedOutputStream.writeInt32NoTag(kotlinSourceFileMap.size());
        for (Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, ? extends T>> entry : kotlinSourceFileMap.entrySet()) {
            String strM7157unboximpl = entry.getKey().m7157unboximpl();
            Map<KotlinSourceFile, ? extends T> value = entry.getValue();
            KotlinLibraryFile.m7155toProtoStreamimpl(strM7157unboximpl, codedOutputStream);
            codedOutputStream.writeInt32NoTag(value.size());
            for (Map.Entry<KotlinSourceFile, ? extends T> entry2 : value.entrySet()) {
                String strM7171unboximpl = entry2.getKey().m7171unboximpl();
                T value2 = entry2.getValue();
                KotlinSourceFile.m7169toProtoStreamimpl(strM7171unboximpl, codedOutputStream);
                function1.invoke(value2);
            }
        }
    }

    private static final void commitSourceFileMetadata_9XGJH6U$writeDirectDependencies(final CodedOutputStream codedOutputStream, final IdSignatureSerialization.IdSignatureICSerializer idSignatureICSerializer, KotlinSourceFileMap<? extends Map<IdSignature, ICHash>> kotlinSourceFileMap) throws IOException {
        commitSourceFileMetadata_9XGJH6U$writeDependencies(codedOutputStream, kotlinSourceFileMap, new Function1<Map<IdSignature, ? extends ICHash>, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$commitSourceFileMetadata$writeDirectDependencies$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Map<IdSignature, ? extends ICHash> map) throws IOException {
                invoke2((Map<IdSignature, ICHash>) map);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Map<IdSignature, ICHash> it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                codedOutputStream.writeInt32NoTag(it.size());
                for (Map.Entry<IdSignature, ICHash> entry : it.entrySet()) {
                    IdSignature key = entry.getKey();
                    Hash128Bits hash128BitsM7115unboximpl = entry.getValue().m7115unboximpl();
                    idSignatureICSerializer.serializeIdSignature(codedOutputStream, key);
                    ICHash.m7113toProtoStreamimpl(hash128BitsM7115unboximpl, codedOutputStream);
                }
            }
        });
    }

    private static final void commitSourceFileMetadata_9XGJH6U$writeInverseDependencies(final CodedOutputStream codedOutputStream, final IdSignatureSerialization.IdSignatureICSerializer idSignatureICSerializer, KotlinSourceFileMap<? extends Set<? extends IdSignature>> kotlinSourceFileMap) throws IOException {
        commitSourceFileMetadata_9XGJH6U$writeDependencies(codedOutputStream, kotlinSourceFileMap, new Function1<Set<? extends IdSignature>, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IncrementalCache$commitSourceFileMetadata$writeInverseDependencies$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Set<? extends IdSignature> set) throws IOException {
                invoke2(set);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Set<? extends IdSignature> it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                codedOutputStream.writeInt32NoTag(it.size());
                Iterator<? extends IdSignature> it2 = it.iterator();
                while (it2.hasNext()) {
                    idSignatureICSerializer.serializeIdSignature(codedOutputStream, it2.next());
                }
            }
        });
    }
}
