package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: Multimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u0001*\u0010\b\u0002\u0010\u0003 \u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u00020\u0005J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00028\u00022\u0006\u0010\u000f\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0012R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/Multimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "C", "", "", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Set;", CompoundEntry.NAME_values, "getValues", "()Ljava/util/Collection;", "contains", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "get", "(Ljava/lang/Object;)Ljava/util/Collection;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Multimap<K, V, C extends Collection<? extends V>> {
    boolean contains(K key);

    C get(K key);

    Set<K> getKeys();

    Collection<V> getValues();
}
