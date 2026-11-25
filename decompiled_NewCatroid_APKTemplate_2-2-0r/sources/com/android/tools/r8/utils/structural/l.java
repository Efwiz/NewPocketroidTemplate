package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class l<T> extends z<T, l<T>> {
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    private final Object a;
    private int b = 0;

    private l(Object obj) {
        this.a = obj;
    }

    @Override // com.android.tools.r8.utils.structural.z
    final z a() {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Predicate predicate) {
        return a(Boolean.hashCode(predicate.test(this.a)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z i(Function function) {
        return a(Arrays.hashCode((short[]) function.apply(this.a)));
    }

    public static <T> int a(T t, y<T> yVar) {
        l lVar = new l(t);
        yVar.a(lVar);
        return lVar.b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Function function) {
        return a(Arrays.hashCode((int[]) function.apply(this.a)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    protected final z b(Function function, v vVar, w wVar) {
        Iterator it = (Iterator) function.apply(this.a);
        while (it.hasNext()) {
            a(it.next().hashCode());
        }
        return this;
    }

    private l a(int i) {
        this.b = (this.b * 31) + i;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Predicate predicate) {
        if (c || predicate.test(this.a)) {
            return this;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToIntFunction toIntFunction) {
        return a(Integer.hashCode(toIntFunction.applyAsInt(this.a)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToLongFunction toLongFunction) {
        return a(Long.hashCode(toLongFunction.applyAsLong(this.a)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    protected final z a(Predicate predicate, Function function, v vVar, w wVar) {
        if (predicate.test(this.a)) {
            return a(function.apply(this.a).hashCode());
        }
        return a(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Function function) {
        return a(((AbstractC0744s2) function.apply(this.a)).hashCode());
    }
}
