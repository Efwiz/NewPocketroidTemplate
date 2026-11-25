package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.incremental.IncrementalCompilationContext;
import org.jetbrains.kotlin.incremental.storage.LazyStorage;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: BasicMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0004\b\u0001\u0010\u0003*\u0014\b\u0002\u0010\u0004*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00052\u00020\u0006B\u001d\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00028\u0002\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001a\u001a\u00020\u0019J\u0006\u0010\u001b\u001a\u00020\u0019J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0015\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00028\u0000H$¢\u0006\u0002\u0010 J\u0015\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00028\u0001H$¢\u0006\u0002\u0010#J\u000e\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\t\u001a\u00028\u0002X\u0084\u0004¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/BasicMap;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "StorageType", "Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "", "storageFile", "Ljava/io/File;", "storage", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "getIcContext", "()Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "pathConverter", "Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "getPathConverter", "()Lorg/jetbrains/kotlin/incremental/storage/FileToPathConverter;", "getStorage", "()Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "getStorageFile$kotlin_build_common", "()Ljava/io/File;", Constants.ATTR_FILTER_TYPE_CLEAN, "", "close", "closeForTest", ArchiveStreamFactory.DUMP, "", "dumpKey", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Comparable;)Ljava/lang/String;", "dumpValue", "value", "(Ljava/lang/Object;)Ljava/lang/String;", "flush", "memoryCachesOnly", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BasicMap<K extends Comparable<? super K>, V, StorageType extends LazyStorage<K, V>> {
    private final IncrementalCompilationContext icContext;
    private final StorageType storage;
    private final File storageFile;

    protected abstract String dumpKey(K key);

    protected abstract String dumpValue(V value);

    public BasicMap(File storageFile, StorageType storage, IncrementalCompilationContext icContext) {
        Intrinsics.checkNotNullParameter(storageFile, "storageFile");
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.storageFile = storageFile;
        this.storage = storage;
        this.icContext = icContext;
    }

    /* JADX INFO: renamed from: getStorageFile$kotlin_build_common, reason: from getter */
    public final File getStorageFile() {
        return this.storageFile;
    }

    protected final StorageType getStorage() {
        return this.storage;
    }

    protected final IncrementalCompilationContext getIcContext() {
        return this.icContext;
    }

    protected final FileToPathConverter getPathConverter() {
        return this.icContext.getPathConverter();
    }

    public final void clean() {
        this.storage.clean();
    }

    public final void flush(boolean memoryCachesOnly) {
        this.storage.flush(memoryCachesOnly);
    }

    public final void close() {
        this.storage.close();
    }

    public final void closeForTest() {
        close();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String dump() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(sb);
        StringBuilder sb2 = new StringBuilder();
        String name2 = this.storageFile.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        printer.println(sb2.append(StringsKt.substringBefore$default(name2, ".tab", (String) null, 2, (Object) null)).append(" (").append(getClass().getSimpleName()).append(')').toString());
        printer.pushIndent();
        for (Comparable comparable : CollectionsKt.sorted(this.storage.getKeys())) {
            StringBuilder sbAppend = new StringBuilder().append(dumpKey(comparable)).append(" -> ");
            Object obj = this.storage.get(comparable);
            Intrinsics.checkNotNull(obj);
            printer.println(sbAppend.append(dumpValue(obj)).toString());
        }
        printer.popIndent();
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
