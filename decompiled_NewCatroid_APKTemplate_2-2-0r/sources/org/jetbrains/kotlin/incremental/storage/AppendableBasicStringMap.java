package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor;
import org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;

/* JADX INFO: compiled from: BasicMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B%\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB1\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0014¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/AppendableBasicStringMap;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/incremental/storage/AppendableBasicMap;", "", "storageFile", "Ljava/io/File;", "valueExternalizer", "Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "(Ljava/io/File;Lcom/intellij/util/io/KeyDescriptor;Lorg/jetbrains/kotlin/incremental/storage/AppendableDataExternalizer;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpKey", ASTExpr.DEFAULT_MAP_KEY_NAME, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AppendableBasicStringMap<V> extends AppendableBasicMap<String, V> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
    public String dumpKey(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return key;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppendableBasicStringMap(File storageFile, KeyDescriptor<String> keyDescriptor, AppendableDataExternalizer<V> valueExternalizer, IncrementalCompilationContext icContext) {
        super(storageFile, keyDescriptor, valueExternalizer, icContext);
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AppendableBasicStringMap(File storageFile, AppendableDataExternalizer<V> valueExternalizer, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        EnumeratorStringDescriptor INSTANCE = EnumeratorStringDescriptor.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        this(storageFile, INSTANCE, valueExternalizer, icContext);
    }
}
