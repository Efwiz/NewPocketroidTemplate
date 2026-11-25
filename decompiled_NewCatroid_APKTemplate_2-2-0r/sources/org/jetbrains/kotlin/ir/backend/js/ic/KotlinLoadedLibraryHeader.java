package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.serialization.BasicIrModuleDeserializerKt;
import org.jetbrains.kotlin.backend.common.serialization.FingerprintHash;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrInterningService;
import org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource;
import org.jetbrains.kotlin.backend.common.serialization.IrLibraryFileFromBytes;
import org.jetbrains.kotlin.backend.common.serialization.SerializedIrFileFingerprint;
import org.jetbrains.kotlin.backend.common.serialization.SerializedKlibFingerprint;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFile;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: KotlinLibraryHeader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0016H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u00020\fX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\r\u0010\nR!\u0010\u000f\u001a\u00020\u00108VX\u0096\u0084\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R'\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u00168VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u0019\u0010\u001aR'\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00100\u00168VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0014\u001a\u0004\b\u001d\u0010\u001aR!\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00170 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0014\u001a\u0004\b!\u0010\"\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLoadedLibraryHeader;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;)V", KlibKt.KLIB_PROPERTY_JS_OUTPUT_NAME, "", "getJsOutputName", "()Ljava/lang/String;", "libraryFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "getLibraryFile-dAnkW_k", "Ljava/lang/String;", "libraryFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "getLibraryFingerprint-3sOkxDc", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "libraryFingerprint$delegate", "Lkotlin/Lazy;", "sourceFileDeserializers", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "getSourceFileDeserializers", "()Ljava/util/Map;", "sourceFileDeserializers$delegate", "sourceFileFingerprints", "getSourceFileFingerprints", "sourceFileFingerprints$delegate", "sourceFiles", "", "getSourceFiles", "()[Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "sourceFiles$delegate", "parseFingerprintsFromManifest", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLoadedLibraryHeader implements KotlinLibraryHeader {
    private final IrInterningService internationService;
    private final KotlinLibrary library;
    private final String libraryFile;

    /* JADX INFO: renamed from: libraryFingerprint$delegate, reason: from kotlin metadata */
    private final Lazy libraryFingerprint;

    /* JADX INFO: renamed from: sourceFileDeserializers$delegate, reason: from kotlin metadata */
    private final Lazy sourceFileDeserializers;

    /* JADX INFO: renamed from: sourceFileFingerprints$delegate, reason: from kotlin metadata */
    private final Lazy sourceFileFingerprints;

    /* JADX INFO: renamed from: sourceFiles$delegate, reason: from kotlin metadata */
    private final Lazy sourceFiles;

    public KotlinLoadedLibraryHeader(KotlinLibrary library, IrInterningService internationService) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(internationService, "internationService");
        this.library = library;
        this.internationService = internationService;
        this.libraryFile = KotlinLibraryFile.m7151constructorimpl(library);
        this.libraryFingerprint = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<FingerprintHash>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.KotlinLoadedLibraryHeader$libraryFingerprint$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ FingerprintHash invoke() {
                return FingerprintHash.m6123boximpl(m7162invoke3sOkxDc());
            }

            /* JADX INFO: renamed from: invoke-3sOkxDc, reason: not valid java name */
            public final Hash128Bits m7162invoke3sOkxDc() {
                Hash128Bits serializedKlibFingerprint = KlibKt.getSerializedKlibFingerprint(this.this$0.library);
                return serializedKlibFingerprint == null ? SerializedKlibFingerprint.m6158constructorimpl(new File(this.this$0.getLibraryFile())) : serializedKlibFingerprint;
            }
        });
        this.sourceFileDeserializers = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, ? extends IdSignatureDeserializer>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.KotlinLoadedLibraryHeader$sourceFileDeserializers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KotlinSourceFile, ? extends IdSignatureDeserializer> invoke() {
                int length = this.this$0.getSourceFiles().length;
                final KotlinLoadedLibraryHeader kotlinLoadedLibraryHeader = this.this$0;
                HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(length);
                for (final int i = 0; i < length; i++) {
                    mapNewHashMapWithExpectedSize.put(kotlinLoadedLibraryHeader.getSourceFiles()[i], new IdSignatureDeserializer(new IrLibraryFileFromBytes(new IrLibraryBytesSource() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.KotlinLoadedLibraryHeader$sourceFileDeserializers$2$1$deserializer$1
                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] debugInfo(int index) {
                            return null;
                        }

                        private final Void err() {
                            ICUtilsKt.m7122icError5T69zGA$default("Not supported", null, null, 6, null);
                            throw null;
                        }

                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] irDeclaration(int index) {
                            err();
                            throw null;
                        }

                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] type(int index) {
                            err();
                            throw null;
                        }

                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] signature(int index) {
                            return kotlinLoadedLibraryHeader.library.signature(index, i);
                        }

                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] string(int index) {
                            return kotlinLoadedLibraryHeader.library.string(index, i);
                        }

                        @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryBytesSource
                        public byte[] body(int index) {
                            err();
                            throw null;
                        }
                    }), null, kotlinLoadedLibraryHeader.internationService));
                }
                return mapNewHashMapWithExpectedSize;
            }
        });
        this.sourceFileFingerprints = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KotlinSourceFile, ? extends FingerprintHash>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.KotlinLoadedLibraryHeader$sourceFileFingerprints$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KotlinSourceFile, ? extends FingerprintHash> invoke() {
                Map<KotlinSourceFile, ? extends FingerprintHash> fingerprintsFromManifest = this.this$0.parseFingerprintsFromManifest();
                if (fingerprintsFromManifest != null) {
                    return fingerprintsFromManifest;
                }
                int length = this.this$0.getSourceFiles().length;
                KotlinLoadedLibraryHeader kotlinLoadedLibraryHeader = this.this$0;
                HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(length);
                for (int i = 0; i < length; i++) {
                    mapNewHashMapWithExpectedSize.put(kotlinLoadedLibraryHeader.getSourceFiles()[i], FingerprintHash.m6123boximpl(SerializedIrFileFingerprint.m6144constructorimpl(kotlinLoadedLibraryHeader.library, i)));
                }
                return mapNewHashMapWithExpectedSize;
            }
        });
        this.sourceFiles = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<KotlinSourceFile[]>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.KotlinLoadedLibraryHeader$sourceFiles$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinSourceFile[] invoke() {
                ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
                int iFileCount = this.this$0.library.fileCount();
                KotlinSourceFile[] kotlinSourceFileArr = new KotlinSourceFile[iFileCount];
                for (int i = 0; i < iFileCount; i++) {
                    String name2 = IrFile.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.this$0.library.file(i)), extensionRegistryLiteNewInstance).getFileEntry().getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    kotlinSourceFileArr[i] = KotlinSourceFile.m7163boximpl(KotlinSourceFile.m7164constructorimpl(name2));
                }
                return kotlinSourceFileArr;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<KotlinSourceFile, FingerprintHash> parseFingerprintsFromManifest() {
        List<SerializedIrFileFingerprint> serializedIrFileFingerprints = KlibKt.getSerializedIrFileFingerprints(this.library);
        if (serializedIrFileFingerprints != null) {
            if (serializedIrFileFingerprints.size() != getSourceFiles().length) {
                serializedIrFileFingerprints = null;
            }
            if (serializedIrFileFingerprints != null) {
                Iterable<IndexedValue> iterableWithIndex = ArraysKt.withIndex(getSourceFiles());
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
                for (IndexedValue indexedValue : iterableWithIndex) {
                    Pair pair = TuplesKt.to(indexedValue.getValue(), FingerprintHash.m6123boximpl(serializedIrFileFingerprints.get(indexedValue.getIndex()).m6151unboximpl()));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    /* JADX INFO: renamed from: getLibraryFile-dAnkW_k, reason: from getter */
    public String getLibraryFile() {
        return this.libraryFile;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    /* JADX INFO: renamed from: getLibraryFingerprint-3sOkxDc, reason: not valid java name and merged with bridge method [inline-methods] */
    public Hash128Bits mo7160getLibraryFingerprintjverJr4() {
        return ((FingerprintHash) this.libraryFingerprint.getValue()).m6130unboximpl();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public Map<KotlinSourceFile, IdSignatureDeserializer> getSourceFileDeserializers() {
        return (Map) this.sourceFileDeserializers.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public Map<KotlinSourceFile, FingerprintHash> getSourceFileFingerprints() {
        return (Map) this.sourceFileFingerprints.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public String getJsOutputName() {
        return KlibKt.getJsOutputName(this.library);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinSourceFile[] getSourceFiles() {
        return (KotlinSourceFile[]) this.sourceFiles.getValue();
    }
}
