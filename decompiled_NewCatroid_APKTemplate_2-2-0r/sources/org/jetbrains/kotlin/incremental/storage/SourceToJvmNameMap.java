package org.jetbrains.kotlin.incremental.storage;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: SourceToOutputMaps.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/SourceToJvmNameMap;", "Lorg/jetbrains/kotlin/incremental/storage/AbstractSourceToOutputMap;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceToJvmNameMap extends AbstractSourceToOutputMap<JvmClassName> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SourceToJvmNameMap(File storageFile, IncrementalCompilationContext icContext) {
        super(JvmClassNameTransformer.INSTANCE, storageFile, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }
}
