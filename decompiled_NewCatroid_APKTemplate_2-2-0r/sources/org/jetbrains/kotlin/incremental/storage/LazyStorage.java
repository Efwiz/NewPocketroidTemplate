package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.io.Closeable;
import java.util.Collection;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: LazyStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\fH&J\u0018\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\r\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0012J\u0015\u0010\u0013\u001a\u00020\t2\u0006\u0010\r\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\u00020\t2\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00028\u0001H¦\u0002¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/LazyStorage;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Ljava/io/Closeable;", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Collection;", Constants.ATTR_FILTER_TYPE_CLEAN, "", "close", "contains", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "flush", "memoryCachesOnly", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", ProductAction.ACTION_REMOVE, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LazyStorage<K, V> extends Closeable {
    void clean();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    boolean contains(K key);

    void flush(boolean memoryCachesOnly);

    V get(K key);

    Collection<K> getKeys();

    void remove(K key);

    void set(K key, V value);
}
