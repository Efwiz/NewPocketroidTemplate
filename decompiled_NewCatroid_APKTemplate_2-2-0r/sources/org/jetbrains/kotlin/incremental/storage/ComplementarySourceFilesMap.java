package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: compiled from: ComplementarySourceFilesMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0014J\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00022\u0006\u0010\f\u001a\u00020\u0005H\u0086\u0002J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00022\u0006\u0010\u000e\u001a\u00020\u0005J\u001f\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002H\u0086\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ComplementarySourceFilesMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "value", "get", "sourceFile", ProductAction.ACTION_REMOVE, "file", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "complementaryFiles", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComplementarySourceFilesMap extends BasicStringMap<Collection<? extends String>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComplementarySourceFilesMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, PathStringDescriptor.INSTANCE, StringCollectionExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    public final void set(File sourceFile, Collection<? extends File> complementaryFiles) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(complementaryFiles, "complementaryFiles");
        getStorage().set(getPathConverter().toPath(sourceFile), FileToPathConverterKt.toPaths(getPathConverter(), complementaryFiles));
    }

    public final Collection<File> get(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        List listEmptyList = (Collection) getStorage().get(getPathConverter().toPath(sourceFile));
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return FileToPathConverterKt.toFiles(getPathConverter(), listEmptyList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(Collection<String> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return IncrementalJvmCacheKt.dumpCollection(value);
    }

    public final Collection<File> remove(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Collection<File> collection = get(file);
        getStorage().remove(getPathConverter().toPath(file));
        return collection;
    }
}
