package org.jetbrains.kotlin.metadata.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Interner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0017\u0012\u0010\b\u0002\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0012\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00028\u0000¢\u0006\u0002\u0010\u0015R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\n0\fj\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\n`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/metadata/serialization/Interner;", ExifInterface.GPS_DIRECTION_TRUE, "", "parent", "(Lorg/jetbrains/kotlin/metadata/serialization/Interner;)V", "allInternedObjects", "", "getAllInternedObjects", "()Ljava/util/List;", "firstIndex", "", "interned", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "isEmpty", "", "()Z", "find", "obj", "(Ljava/lang/Object;)Ljava/lang/Integer;", "intern", "(Ljava/lang/Object;)I", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Interner<T> {
    private final int firstIndex;
    private final HashMap<T, Integer> interned;
    private final Interner<T> parent;

    /* JADX WARN: Multi-variable type inference failed */
    public Interner() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public Interner(Interner<T> interner) {
        this.parent = interner;
        this.firstIndex = interner != null ? interner.interned.size() + interner.firstIndex : 0;
        this.interned = new HashMap<>();
    }

    public /* synthetic */ Interner(Interner interner, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : interner);
    }

    public final List<T> getAllInternedObjects() {
        Set<T> setKeySet = this.interned.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        final HashMap<T, Integer> map = this.interned;
        return CollectionsKt.sortedWith(setKeySet, new Comparator() { // from class: org.jetbrains.kotlin.metadata.serialization.Interner$special$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues((Integer) map.get(t), (Integer) map.get(t2));
            }
        });
    }

    public final boolean isEmpty() {
        Interner<T> interner;
        return this.interned.isEmpty() && ((interner = this.parent) == null || interner.isEmpty());
    }

    private final Integer find(T obj) {
        Integer numFind;
        Interner<T> interner = this.parent;
        if (interner != null) {
            int size = interner.interned.size() + this.parent.firstIndex;
            int i = this.firstIndex;
        }
        Interner<T> interner2 = this.parent;
        return (interner2 == null || (numFind = interner2.find(obj)) == null) ? this.interned.get(obj) : numFind;
    }

    public final int intern(T obj) {
        Integer numFind = find(obj);
        if (numFind != null) {
            return numFind.intValue();
        }
        int size = this.firstIndex + this.interned.size();
        this.interned.put(obj, Integer.valueOf(size));
        return size;
    }
}
