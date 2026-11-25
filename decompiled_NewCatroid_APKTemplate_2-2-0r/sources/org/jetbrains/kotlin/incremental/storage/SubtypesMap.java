package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: ClassOneToManyMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/SubtypesMap;", "Lorg/jetbrains/kotlin/incremental/storage/ClassOneToManyMap;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubtypesMap extends ClassOneToManyMap {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubtypesMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }
}
