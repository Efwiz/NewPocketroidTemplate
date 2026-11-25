package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ClassOneToManyMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0003\b\u0010\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000e\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0014J\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u000e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001c\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0013J\u001f\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u0002H\u0086\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ClassOneToManyMap;", "Lorg/jetbrains/kotlin/incremental/storage/AppendableBasicStringMap;", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", ProductAction.ACTION_ADD, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/name/FqName;", "value", "dumpValue", "get", ProductAction.ACTION_REMOVE, "removeValues", "removed", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, CompoundEntry.NAME_values, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ClassOneToManyMap extends AppendableBasicStringMap<Collection<? extends String>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassOneToManyMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, StringCollectionExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(Collection<String> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return IncrementalJvmCacheKt.dumpCollection(value);
    }

    public final synchronized void add(FqName key, FqName value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        AppendableLazyStorage storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        storage.append(strAsString, CollectionsKt.listOf(value.asString()));
    }

    public final synchronized Collection<FqName> get(FqName key) {
        Set setEmptySet;
        Intrinsics.checkNotNullParameter(key, "key");
        AppendableLazyStorage storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Collection collection = (Collection) storage.get(strAsString);
        if (collection != null) {
            Collection collection2 = collection;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
            Iterator it = collection2.iterator();
            while (it.hasNext()) {
                arrayList.add(new FqName((String) it.next()));
            }
            setEmptySet = arrayList;
        } else {
            setEmptySet = SetsKt.emptySet();
        }
        return setEmptySet;
    }

    public final synchronized void set(FqName key, Collection<FqName> values) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(values, "values");
        if (values.isEmpty()) {
            remove(key);
            return;
        }
        AppendableLazyStorage storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Collection<FqName> collection = values;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((FqName) it.next()).asString());
        }
        storage.set(strAsString, arrayList);
    }

    public final synchronized void remove(FqName key) {
        Intrinsics.checkNotNullParameter(key, "key");
        AppendableLazyStorage storage = getStorage();
        String strAsString = key.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        storage.remove(strAsString);
    }

    public final synchronized void removeValues(FqName key, Set<FqName> removed) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(removed, "removed");
        Collection<FqName> collection = get(key);
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (!removed.contains((FqName) obj)) {
                arrayList.add(obj);
            }
        }
        set(key, arrayList);
    }
}
