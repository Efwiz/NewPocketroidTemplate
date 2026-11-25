package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002H\u0014J\u0013\u0010\r\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000e\u001a\u00020\u0004H\u0086\u0002J\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010J&\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014J\u0016\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/TranslationResultMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "storageFile", "Ljava/io/File;", "protoData", "Lorg/jetbrains/kotlin/incremental/ProtoDataProvider;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/ProtoDataProvider;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "get", "sourceFile", UserMetadata.KEYDATA_FILENAME, "", "put", "", "newMetadata", "", "newBinaryAst", "newInlineData", ProductAction.ACTION_REMOVE, "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TranslationResultMap extends BasicStringMap<TranslationResultValue> {
    private final ProtoDataProvider protoData;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TranslationResultMap(File storageFile, ProtoDataProvider protoData, IncrementalCompilationContext icContext) {
        super(storageFile, TranslationResultValueExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(protoData, "protoData");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.protoData = protoData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(TranslationResultValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return "Metadata: " + IncrementalJvmCacheKt.md5(value.getMetadata()) + ", Binary AST: " + IncrementalJvmCacheKt.md5(value.getBinaryAst()) + ", InlineData: " + IncrementalJvmCacheKt.md5(value.getInlineData());
    }

    public final synchronized void put(File sourceFile, byte[] newMetadata, byte[] newBinaryAst, byte[] newInlineData) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(newMetadata, "newMetadata");
        Intrinsics.checkNotNullParameter(newBinaryAst, "newBinaryAst");
        Intrinsics.checkNotNullParameter(newInlineData, "newInlineData");
        getStorage().set(getPathConverter().toPath(sourceFile), new TranslationResultValue(newMetadata, newBinaryAst, newInlineData));
    }

    public final synchronized TranslationResultValue get(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        return (TranslationResultValue) getStorage().get(getPathConverter().toPath(sourceFile));
    }

    public final Collection<File> keys() {
        Collection keys = getStorage().getKeys();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(keys, 10));
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            arrayList.add(getPathConverter().toFile((String) it.next()));
        }
        return arrayList;
    }

    public final synchronized void remove(File sourceFile, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        String path = getPathConverter().toPath(sourceFile);
        Object obj = getStorage().get(path);
        Intrinsics.checkNotNull(obj);
        Iterator<Map.Entry<ClassId, ProtoData>> it = this.protoData.invoke(sourceFile, ((TranslationResultValue) obj).getMetadata()).entrySet().iterator();
        while (it.hasNext()) {
            ChangesCollector.collectProtoChanges$default(changesCollector, it.next().getValue(), null, false, null, 12, null);
        }
        getStorage().remove(path);
    }
}
