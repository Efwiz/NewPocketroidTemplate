package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0014J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\tH\u0086\u0002J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0002J\u000e\u0010\u0012\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/PackageMetadataMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "get", "packageName", UserMetadata.KEYDATA_FILENAME, "", "put", "", "newMetadata", ProductAction.ACTION_REMOVE, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PackageMetadataMap extends BasicStringMap<byte[]> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageMetadataMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, ByteArrayExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    public final void put(String packageName, byte[] newMetadata) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(newMetadata, "newMetadata");
        getStorage().set(packageName, newMetadata);
    }

    public final void remove(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        getStorage().remove(packageName);
    }

    public final Collection<String> keys() {
        return getStorage().getKeys();
    }

    public final byte[] get(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return (byte[]) getStorage().get(packageName);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(byte[] value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return "Package metadata: " + IncrementalJvmCacheKt.md5(value);
    }
}
