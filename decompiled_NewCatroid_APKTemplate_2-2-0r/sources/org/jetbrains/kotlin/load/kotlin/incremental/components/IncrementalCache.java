package org.jetbrains.kotlin.load.kotlin.incremental.components;

import java.util.Collection;
import kotlin.Metadata;

/* JADX INFO: compiled from: IncrementalCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nH&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\nH&J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u0005H&J\u0018\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "", "close", "", "getClassFilePath", "", "internalClassName", "getModuleMappingData", "", "getObsoleteMultifileClasses", "", "getObsoletePackageParts", "getPackagePartData", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/JvmPackagePartProto;", "partInternalName", "getStableMultifileFacadeParts", "facadeInternalName", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IncrementalCache {
    void close();

    String getClassFilePath(String internalClassName);

    byte[] getModuleMappingData();

    Collection<String> getObsoleteMultifileClasses();

    Collection<String> getObsoletePackageParts();

    JvmPackagePartProto getPackagePartData(String partInternalName);

    Collection<String> getStableMultifileFacadeParts(String facadeInternalName);
}
