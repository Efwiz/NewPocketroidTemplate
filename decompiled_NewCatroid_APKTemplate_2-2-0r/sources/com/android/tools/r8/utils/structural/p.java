package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class p extends z {
    static final /* synthetic */ boolean c = true;
    private final q a;
    private final Object b;

    private p(Object obj, q qVar) {
        this.b = obj;
        this.a = qVar;
    }

    @Override // com.android.tools.r8.utils.structural.z
    final z a() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Predicate predicate) {
        q qVar = this.a;
        qVar.a.a(predicate.test(this.b));
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z i(Function function) {
        for (short s : (short[]) function.apply(this.b)) {
            this.a.a.a((int) s);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Predicate predicate) {
        if (c || predicate.test(this.b)) {
            return this;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToIntFunction toIntFunction) {
        q qVar = this.a;
        qVar.a.a(toIntFunction.applyAsInt(this.b));
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Function function) {
        for (int i : (int[]) function.apply(this.b)) {
            this.a.a.a(i);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToLongFunction toLongFunction) {
        q qVar = this.a;
        qVar.a.a(toLongFunction.applyAsLong(this.b));
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    protected final z a(Predicate predicate, Function function, v vVar, w wVar) {
        boolean zTest = predicate.test(this.b);
        this.a.a.a(zTest);
        if (zTest) {
            wVar.a(function.apply(this.b), this.a);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    protected final z b(Function function, v vVar, w wVar) {
        q qVar = this.a;
        Iterator it = (Iterator) function.apply(this.b);
        qVar.getClass();
        while (it.hasNext()) {
            wVar.a(it.next(), qVar);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Function function) {
        this.a.a((AbstractC0744s2) function.apply(this.b));
        return this;
    }
}
