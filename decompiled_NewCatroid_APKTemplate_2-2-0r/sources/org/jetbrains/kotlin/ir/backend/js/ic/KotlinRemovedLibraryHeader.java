package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.FingerprintHash;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureDeserializer;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;

/* JADX INFO: compiled from: KotlinLibraryHeader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\n8VX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinRemovedLibraryHeader;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "libCacheDir", "Ljava/io/File;", "(Ljava/io/File;)V", KlibKt.KLIB_PROPERTY_JS_OUTPUT_NAME, "", "getJsOutputName", "()Ljava/lang/String;", "libraryFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "getLibraryFile-dAnkW_k", "libraryFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "getLibraryFingerprint-jverJr4", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "sourceFileDeserializers", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "getSourceFileDeserializers", "()Ljava/util/Map;", "sourceFileFingerprints", "getSourceFileFingerprints", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinRemovedLibraryHeader implements KotlinLibraryHeader {
    private final File libCacheDir;

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public String getJsOutputName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    /* JADX INFO: renamed from: getLibraryFingerprint-jverJr4 */
    public Hash128Bits mo7160getLibraryFingerprintjverJr4() {
        return null;
    }

    public KotlinRemovedLibraryHeader(File libCacheDir) {
        Intrinsics.checkNotNullParameter(libCacheDir, "libCacheDir");
        this.libCacheDir = libCacheDir;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    /* JADX INFO: renamed from: getLibraryFile-dAnkW_k */
    public String mo7159getLibraryFiledAnkW_k() {
        ICUtilsKt.m7122icError5T69zGA$default("removed library name is unavailable; cache dir: " + this.libCacheDir.getAbsolutePath(), null, null, 6, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public Map<KotlinSourceFile, IdSignatureDeserializer> getSourceFileDeserializers() {
        return MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinLibraryHeader
    public Map<KotlinSourceFile, FingerprintHash> getSourceFileFingerprints() {
        return MapsKt.emptyMap();
    }
}
