package org.jetbrains.kotlin.utils;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: IntArrayList.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0003H\u0002J\u0011\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003H\u0086\u0002J\u0006\u0010\n\u001a\u00020\u0003J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0006\u0010\u0014\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u000e\u0010\n\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/utils/IntArrayList;", "", "initialCapacity", "", "(I)V", "data", "", "isEmpty", "", "()Z", "size", ProductAction.ACTION_ADD, "", "o", "ensureCapacity", "minCapacity", "get", "index", "toString", "", "trimToSize", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntArrayList {
    private int[] data;
    private int size;

    public IntArrayList(int i) {
        this.data = new int[i];
    }

    public final void trimToSize() {
        int i = this.size;
        int[] iArr = this.data;
        if (i < iArr.length) {
            int[] iArrCopyOf = Arrays.copyOf(iArr, i);
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(...)");
            this.data = iArrCopyOf;
        }
    }

    private final void ensureCapacity(int minCapacity) {
        int[] iArr = this.data;
        if (minCapacity > iArr.length) {
            int[] iArrCopyOf = Arrays.copyOf(iArr, Math.max(((iArr.length * 3) / 2) + 1, minCapacity));
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(...)");
            this.data = iArrCopyOf;
        }
    }

    /* JADX INFO: renamed from: size, reason: from getter */
    public final int getSize() {
        return this.size;
    }

    public final boolean isEmpty() {
        return this.size == 0;
    }

    public final int get(int index) {
        return this.data[index];
    }

    public final void add(int o) {
        ensureCapacity(this.size + 1);
        int[] iArr = this.data;
        int i = this.size;
        this.size = i + 1;
        iArr[i] = o;
    }

    public String toString() {
        return kotlin.collections.CollectionsKt.joinToString$default(RangesKt.until(0, this.size), ", ", "[", "]", 0, null, new Function1<Integer, CharSequence>() { // from class: org.jetbrains.kotlin.utils.IntArrayList.toString.1
            {
                super(1);
            }

            public final CharSequence invoke(int i) {
                return String.valueOf(IntArrayList.this.get(i));
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Integer num) {
                return invoke(num.intValue());
            }
        }, 24, null);
    }
}
