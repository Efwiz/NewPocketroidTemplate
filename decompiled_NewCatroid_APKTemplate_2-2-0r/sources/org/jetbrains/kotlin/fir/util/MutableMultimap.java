package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: Multimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u000e\b\u0002\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005J\b\u0010\u0006\u001a\u00020\u0007H&J\u001d\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u000bJ#\u0010\f\u001a\u00020\u00072\u0006\u0010\t\u001a\u00028\u00002\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00072\u0006\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u000bJ\u0015\u0010\u0010\u001a\u00028\u00022\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0011ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/MutableMultimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "C", "", "Lorg/jetbrains/kotlin/fir/util/Multimap;", LineReader.CLEAR, "", "put", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "putAll", CompoundEntry.NAME_values, "(Ljava/lang/Object;Ljava/util/Collection;)V", ProductAction.ACTION_REMOVE, "removeKey", "(Ljava/lang/Object;)Ljava/util/Collection;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MutableMultimap<K, V, C extends Collection<? extends V>> extends Multimap<K, V, C> {
    void clear();

    void put(K key, V value);

    void remove(K key, V value);

    C removeKey(K key);

    /* JADX WARN: Multi-variable type inference failed */
    default void putAll(K key, Collection<? extends V> values) {
        Intrinsics.checkNotNullParameter(values, "values");
        Iterator<T> it = values.iterator();
        while (it.hasNext()) {
            put(key, it.next());
        }
    }
}
