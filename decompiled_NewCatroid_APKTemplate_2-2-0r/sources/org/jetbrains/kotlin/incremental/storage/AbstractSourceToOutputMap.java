package org.jetbrains.kotlin.incremental.storage;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: SourceToOutputMaps.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002B#\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\bJ\u0016\u0010\u0014\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0014J\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000e\u001a\u00020\bH\u0086\u0002J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0006\u0010\u000e\u001a\u00020\bJ\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0004H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/AbstractSourceToOutputMap;", SchemaSymbols.ATTVAL_NAME, "Lorg/jetbrains/kotlin/incremental/storage/AppendableBasicStringMap;", "", "", "nameTransformer", "Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/storage/NameTransformer;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", ProductAction.ACTION_ADD, "", "sourceFile", "className", "(Ljava/io/File;Ljava/lang/Object;)V", "clearOutputsForSource", "contains", "", "dumpValue", "value", "get", "getFqNames", "Lorg/jetbrains/kotlin/name/FqName;", ProductAction.ACTION_REMOVE, "path", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSourceToOutputMap<Name> extends AppendableBasicStringMap<Collection<? extends String>> {
    private final NameTransformer<Name> nameTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractSourceToOutputMap(NameTransformer<Name> nameTransformer, File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, PathStringDescriptor.INSTANCE, StringCollectionExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(nameTransformer, "nameTransformer");
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.nameTransformer = nameTransformer;
    }

    public final void clearOutputsForSource(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        remove(getPathConverter().toPath(sourceFile));
    }

    public final void add(File sourceFile, Name className) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        getStorage().append(getPathConverter().toPath(sourceFile), CollectionsKt.listOf(this.nameTransformer.asString(className)));
    }

    public final boolean contains(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        return getStorage().contains(getPathConverter().toPath(sourceFile));
    }

    public final Collection<Name> get(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        List listEmptyList = (Collection) getStorage().get(getPathConverter().toPath(sourceFile));
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Collection collection = listEmptyList;
        NameTransformer<Name> nameTransformer = this.nameTransformer;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(nameTransformer.asName((String) it.next()));
        }
        return arrayList;
    }

    public final Collection<FqName> getFqNames(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        List listEmptyList = (Collection) getStorage().get(getPathConverter().toPath(sourceFile));
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Collection collection = listEmptyList;
        NameTransformer<Name> nameTransformer = this.nameTransformer;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(nameTransformer.asFqName((String) it.next()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(Collection<String> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return IncrementalJvmCacheKt.dumpCollection(value);
    }

    private final void remove(String path) {
        getStorage().remove(path);
    }
}
