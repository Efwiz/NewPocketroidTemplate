package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ClassAttributesMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0010\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0014J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\rH\u0086\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rJ\u0019\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0002H\u0086\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ClassAttributesMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/storage/ICClassesAttributes;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/name/FqName;", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ClassAttributesMap extends BasicStringMap<ICClassesAttributes> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassAttributesMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, ICClassesAttributesExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(ICClassesAttributes value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value.toString();
    }

    public final void set(FqName key, ICClassesAttributes value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        LazyStorage<K, V> storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        storage.set(strAsString, value);
    }

    public final ICClassesAttributes get(FqName key) {
        Intrinsics.checkNotNullParameter(key, "key");
        LazyStorage<K, V> storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return (ICClassesAttributes) storage.get(strAsString);
    }

    public final void remove(FqName key) {
        Intrinsics.checkNotNullParameter(key, "key");
        LazyStorage<K, V> storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        storage.remove(strAsString);
    }
}
