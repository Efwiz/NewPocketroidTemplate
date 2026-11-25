package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor;
import org.jetbrains.kotlin.com.intellij.util.io.ExternalIntegerKeyDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: IdToFileMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0086\u0002J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0002H\u0014J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0014J\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\u0002H\u0086\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u0002J\u0019\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/IdToFileMap;", "Lorg/jetbrains/kotlin/incremental/storage/NonAppendableBasicMap;", "", "", "file", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "contains", "", "id", "dumpKey", ASTExpr.DEFAULT_MAP_KEY_NAME, "dumpValue", "value", "get", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdToFileMap extends NonAppendableBasicMap<Integer, String> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public /* bridge */ /* synthetic */ String dumpKey(Comparable comparable) {
        return dumpKey(((Number) comparable).intValue());
    }

    public IdToFileMap(File file, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        ExternalIntegerKeyDescriptor externalIntegerKeyDescriptor = new ExternalIntegerKeyDescriptor();
        EnumeratorStringDescriptor INSTANCE = EnumeratorStringDescriptor.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        super(file, externalIntegerKeyDescriptor, INSTANCE, icContext);
    }

    protected String dumpKey(int key) {
        return String.valueOf(key);
    }

    public final File get(int id2) {
        String str = (String) getStorage().get(Integer.valueOf(id2));
        if (str != null) {
            return getPathConverter().toFile(str);
        }
        return null;
    }

    public final boolean contains(int id2) {
        return getStorage().contains(Integer.valueOf(id2));
    }

    public final void set(int id2, File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        getStorage().set(Integer.valueOf(id2), getPathConverter().toPath(file));
    }

    public final void remove(int id2) {
        getStorage().remove(Integer.valueOf(id2));
    }
}
