package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.serialization.FingerprintHash;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureDeserializer;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;

/* JADX INFO: compiled from: KotlinLibraryHeader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b`\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0007X¦\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\b\u0010\u0005R\u001a\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n0\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012ø\u0001\u0002\u0082\u0002\u0011\n\u0005\b¡\u001e0\u0001\n\u0002\b!\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "", KlibKt.KLIB_PROPERTY_JS_OUTPUT_NAME, "", "getJsOutputName", "()Ljava/lang/String;", "libraryFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "getLibraryFile-dAnkW_k", "libraryFingerprint", "Lorg/jetbrains/kotlin/backend/common/serialization/FingerprintHash;", "getLibraryFingerprint-jverJr4", "()Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "sourceFileDeserializers", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "getSourceFileDeserializers", "()Ljava/util/Map;", "sourceFileFingerprints", "getSourceFileFingerprints", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinLibraryHeader {
    String getJsOutputName();

    /* JADX INFO: renamed from: getLibraryFile-dAnkW_k, reason: not valid java name */
    String mo7159getLibraryFiledAnkW_k();

    /* JADX INFO: renamed from: getLibraryFingerprint-jverJr4, reason: not valid java name */
    Hash128Bits mo7160getLibraryFingerprintjverJr4();

    Map<KotlinSourceFile, IdSignatureDeserializer> getSourceFileDeserializers();

    Map<KotlinSourceFile, FingerprintHash> getSourceFileFingerprints();
}
