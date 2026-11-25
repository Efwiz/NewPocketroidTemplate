package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: FileToIdMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0014J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\u0002¢\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u0004J\u0019\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0002H\u0086\u0002J\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/FileToIdMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "file", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "get", "(Ljava/io/File;)Ljava/lang/Integer;", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "id", "toMap", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileToIdMap extends BasicStringMap<Integer> {
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public /* bridge */ /* synthetic */ String dumpValue(Object obj) {
        return dumpValue(((Number) obj).intValue());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileToIdMap(File file, IncrementalCompilationContext icContext) {
        super(file, IntExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    protected String dumpValue(int value) {
        return String.valueOf(value);
    }

    public final Integer get(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return (Integer) getStorage().get(getPathConverter().toPath(file));
    }

    public final void set(File file, int id2) {
        Intrinsics.checkNotNullParameter(file, "file");
        getStorage().set(getPathConverter().toPath(file), Integer.valueOf(id2));
    }

    public final void remove(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        getStorage().remove(getPathConverter().toPath(file));
    }

    public final Map<File, Integer> toMap() {
        HashMap map = new HashMap();
        for (String str : getStorage().getKeys()) {
            Integer num = (Integer) getStorage().get(str);
            if (num != null) {
                map.put(getPathConverter().toFile(str), Integer.valueOf(num.intValue()));
            }
        }
        return map;
    }
}
