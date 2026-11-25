package org.jetbrains.kotlin.incremental.js;

import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncrementalDataProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r0\u0005¢\u0006\u0002\u0010\u000eR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R \u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R \u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProviderImpl;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "headerMetadata", "", "compiledPackageParts", "", "Ljava/io/File;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "metadataVersion", "", "packageMetadata", "", "serializedIrFiles", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "([BLjava/util/Map;[ILjava/util/Map;Ljava/util/Map;)V", "getCompiledPackageParts", "()Ljava/util/Map;", "getHeaderMetadata", "()[B", "getMetadataVersion", "()[I", "getPackageMetadata", "getSerializedIrFiles", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalDataProviderImpl implements IncrementalDataProvider {
    private final Map<File, TranslationResultValue> compiledPackageParts;
    private final byte[] headerMetadata;
    private final int[] metadataVersion;
    private final Map<String, byte[]> packageMetadata;
    private final Map<File, IrTranslationResultValue> serializedIrFiles;

    public IncrementalDataProviderImpl(byte[] headerMetadata, Map<File, TranslationResultValue> compiledPackageParts, int[] metadataVersion, Map<String, byte[]> packageMetadata, Map<File, IrTranslationResultValue> serializedIrFiles) {
        Intrinsics.checkNotNullParameter(headerMetadata, "headerMetadata");
        Intrinsics.checkNotNullParameter(compiledPackageParts, "compiledPackageParts");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(packageMetadata, "packageMetadata");
        Intrinsics.checkNotNullParameter(serializedIrFiles, "serializedIrFiles");
        this.headerMetadata = headerMetadata;
        this.compiledPackageParts = compiledPackageParts;
        this.metadataVersion = metadataVersion;
        this.packageMetadata = packageMetadata;
        this.serializedIrFiles = serializedIrFiles;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public byte[] getHeaderMetadata() {
        return this.headerMetadata;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, TranslationResultValue> getCompiledPackageParts() {
        return this.compiledPackageParts;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public int[] getMetadataVersion() {
        return this.metadataVersion;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<String, byte[]> getPackageMetadata() {
        return this.packageMetadata;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, IrTranslationResultValue> getSerializedIrFiles() {
        return this.serializedIrFiles;
    }
}
