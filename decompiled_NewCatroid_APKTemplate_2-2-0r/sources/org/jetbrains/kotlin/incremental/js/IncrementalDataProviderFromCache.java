package org.jetbrains.kotlin.incremental.js;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalJsCache;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: IncrementalDataProviderFromCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\f0\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\nR \u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00170\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProviderFromCache;", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/incremental/IncrementalJsCache;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJsCache;)V", "compiledPackageParts", "", "Ljava/io/File;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "getCompiledPackageParts", "()Ljava/util/Map;", "headerMetadata", "", "getHeaderMetadata", "()[B", "metadataVersion", "", "getMetadataVersion", "()[I", "packageMetadata", "", "getPackageMetadata", "serializedIrFiles", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "getSerializedIrFiles", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalDataProviderFromCache implements IncrementalDataProvider {
    private final IncrementalJsCache cache;

    public IncrementalDataProviderFromCache(IncrementalJsCache cache) {
        Intrinsics.checkNotNullParameter(cache, "cache");
        this.cache = cache;
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public byte[] getHeaderMetadata() {
        return this.cache.getHeader();
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, TranslationResultValue> getCompiledPackageParts() {
        return this.cache.nonDirtyPackageParts();
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public int[] getMetadataVersion() {
        return JsMetadataVersion.INSTANCE.getNumbers();
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<String, byte[]> getPackageMetadata() {
        return this.cache.packageMetadata();
    }

    @Override // org.jetbrains.kotlin.incremental.js.IncrementalDataProvider
    public Map<File, IrTranslationResultValue> getSerializedIrFiles() {
        return this.cache.nonDirtyIrParts();
    }
}
