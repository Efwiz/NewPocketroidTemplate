package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;
import org.jetbrains.kotlin.incremental.storage.StringToLongMapExternalizer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u00020\u00032\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014J*\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/InlineFunctionsMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "value", "process", "", "srcFile", "newMap", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", ProductAction.ACTION_REMOVE, "sourceFile", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InlineFunctionsMap extends BasicStringMap<Map<String, ? extends Long>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineFunctionsMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, StringToLongMapExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    public final synchronized void process(File srcFile, Map<String, Long> newMap, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        Intrinsics.checkNotNullParameter(newMap, "newMap");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        String path = getPathConverter().toPath(srcFile);
        Map mapEmptyMap = (Map) getStorage().get(path);
        if (mapEmptyMap == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        if (!newMap.isEmpty()) {
            getStorage().set(path, newMap);
        } else {
            getStorage().remove(path);
        }
        for (String str : SetsKt.plus(mapEmptyMap.keySet(), (Iterable) newMap.keySet())) {
            FqName fqNameFromSegments = FqName.fromSegments(StringsKt.split$default((CharSequence) StringsKt.removePrefix(StringsKt.removePrefix(str, (CharSequence) "<get>"), (CharSequence) "<set>"), new String[]{"."}, false, 0, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
            FqName fqNameParent = fqNameFromSegments.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            String strAsString = fqNameFromSegments.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            changesCollector.collectMemberIfValueWasChanged(fqNameParent, strAsString, mapEmptyMap.get(str), newMap.get(str));
        }
    }

    public final synchronized void remove(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        getStorage().remove(getPathConverter().toPath(sourceFile));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(Map<String, Long> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return IncrementalJvmCacheKt.dumpMap(value, new Function1<Long, String>() { // from class: org.jetbrains.kotlin.incremental.InlineFunctionsMap.dumpValue.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ String invoke(Long l) {
                return invoke(l.longValue());
            }

            public final String invoke(long j) {
                String hexString = Long.toHexString(j);
                Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                return hexString;
            }
        });
    }
}
