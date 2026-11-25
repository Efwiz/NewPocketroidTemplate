package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.js.IrTranslationResultValue;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\n\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0014J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u0004H\u0086\u0002J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eJX\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00122\b\u0010\u001a\u001a\u0004\u0018\u00010\u0012J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0004¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IrTranslationResultMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "get", "sourceFile", UserMetadata.KEYDATA_FILENAME, "", "put", "", "newFiledata", "", "newTypes", "newSignatures", "newStrings", "newDeclarations", "newBodies", "fqn", "newFileMetadata", "debugInfos", ProductAction.ACTION_REMOVE, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrTranslationResultMap extends BasicStringMap<IrTranslationResultValue> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrTranslationResultMap(File storageFile, IncrementalCompilationContext icContext) {
        super(storageFile, IrTranslationResultValueExternalizer.INSTANCE, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpValue(IrTranslationResultValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return "Filedata: " + IncrementalJvmCacheKt.md5(value.getFileData()) + ", Types: " + IncrementalJvmCacheKt.md5(value.getTypes()) + ", Signatures: " + IncrementalJvmCacheKt.md5(value.getSignatures()) + ", Strings: " + IncrementalJvmCacheKt.md5(value.getStrings()) + ", Declarations: " + IncrementalJvmCacheKt.md5(value.getDeclarations()) + ", Bodies: " + IncrementalJvmCacheKt.md5(value.getBodies());
    }

    public final void put(File sourceFile, byte[] newFiledata, byte[] newTypes, byte[] newSignatures, byte[] newStrings, byte[] newDeclarations, byte[] newBodies, byte[] fqn, byte[] newFileMetadata, byte[] debugInfos) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(newFiledata, "newFiledata");
        Intrinsics.checkNotNullParameter(newTypes, "newTypes");
        Intrinsics.checkNotNullParameter(newSignatures, "newSignatures");
        Intrinsics.checkNotNullParameter(newStrings, "newStrings");
        Intrinsics.checkNotNullParameter(newDeclarations, "newDeclarations");
        Intrinsics.checkNotNullParameter(newBodies, "newBodies");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(newFileMetadata, "newFileMetadata");
        getStorage().set(getPathConverter().toPath(sourceFile), new IrTranslationResultValue(newFiledata, newTypes, newSignatures, newStrings, newDeclarations, newBodies, fqn, newFileMetadata, debugInfos));
    }

    public final IrTranslationResultValue get(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        return (IrTranslationResultValue) getStorage().get(getPathConverter().toPath(sourceFile));
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

    public final void remove(File sourceFile) {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        getStorage().remove(getPathConverter().toPath(sourceFile));
    }
}
