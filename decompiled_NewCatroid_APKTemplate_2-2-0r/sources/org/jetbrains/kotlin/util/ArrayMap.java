package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: ArrayMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H&J\u0018\u0010\n\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\u0006H¦\u0002¢\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0010R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0001\u0003\u0011\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/util/ArrayMap;", ExifInterface.GPS_DIRECTION_TRUE, "", "", "()V", "size", "", "getSize", "()I", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "get", "index", "(I)Ljava/lang/Object;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "value", "(ILjava/lang/Object;)V", "Lorg/jetbrains/kotlin/util/ArrayMapImpl;", "Lorg/jetbrains/kotlin/util/EmptyArrayMap;", "Lorg/jetbrains/kotlin/util/OneElementArrayMap;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ArrayMap<T> implements Iterable<T>, KMappedMarker {
    public /* synthetic */ ArrayMap(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ArrayMap<T> copy();

    public abstract T get(int index);

    public abstract int getSize();

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract void set(int index, T value);

    private ArrayMap() {
    }
}
