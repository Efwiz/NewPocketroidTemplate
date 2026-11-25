package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class b extends z {
    static final /* synthetic */ boolean e = true;
    private final c a;
    private final Object b;
    private final Object c;
    private int d;

    private b(Object obj, Object obj2, c cVar) {
        this.d = 0;
        this.b = obj;
        this.c = obj2;
        this.a = cVar;
    }

    @Override // com.android.tools.r8.utils.structural.z
    final z a() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Predicate predicate) {
        if (this.d == 0) {
            c cVar = this.a;
            boolean zTest = predicate.test(this.b);
            boolean zTest2 = predicate.test(this.c);
            cVar.getClass();
            this.d = Boolean.compare(zTest, zTest2);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z i(Function function) {
        if (this.d == 0) {
            short[] sArr = (short[]) function.apply(this.b);
            short[] sArr2 = (short[]) function.apply(this.c);
            int iMin = Math.min(sArr.length, sArr2.length);
            for (int i = 0; i < iMin && this.d == 0; i++) {
                c cVar = this.a;
                short s = sArr[i];
                short s2 = sArr2[i];
                cVar.getClass();
                this.d = Integer.compare(s, s2);
            }
            if (this.d == 0) {
                c cVar2 = this.a;
                int length = sArr.length;
                int length2 = sArr2.length;
                cVar2.getClass();
                this.d = Integer.compare(length, length2);
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Predicate predicate) {
        boolean z = e;
        if (!z && !predicate.test(this.b)) {
            throw new AssertionError();
        }
        if (z || predicate.test(this.c)) {
            return this;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToIntFunction toIntFunction) {
        if (this.d == 0) {
            c cVar = this.a;
            int iApplyAsInt = toIntFunction.applyAsInt(this.b);
            int iApplyAsInt2 = toIntFunction.applyAsInt(this.c);
            cVar.getClass();
            this.d = Integer.compare(iApplyAsInt, iApplyAsInt2);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z b(Function function) {
        if (this.d == 0) {
            int[] iArr = (int[]) function.apply(this.b);
            int[] iArr2 = (int[]) function.apply(this.c);
            int iMin = Math.min(iArr.length, iArr2.length);
            for (int i = 0; i < iMin && this.d == 0; i++) {
                c cVar = this.a;
                int i2 = iArr[i];
                int i3 = iArr2[i];
                cVar.getClass();
                this.d = Integer.compare(i2, i3);
            }
            if (this.d == 0) {
                c cVar2 = this.a;
                int length = iArr.length;
                int length2 = iArr2.length;
                cVar2.getClass();
                this.d = Integer.compare(length, length2);
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(ToLongFunction toLongFunction) {
        if (this.d == 0) {
            c cVar = this.a;
            long jApplyAsLong = toLongFunction.applyAsLong(this.b);
            long jApplyAsLong2 = toLongFunction.applyAsLong(this.c);
            cVar.getClass();
            this.d = Long.compare(jApplyAsLong, jApplyAsLong2);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Predicate predicate, Function function, v vVar, w wVar) {
        if (this.d == 0) {
            boolean zTest = predicate.test(this.b);
            boolean zTest2 = predicate.test(this.c);
            if (zTest && zTest2) {
                this.d = vVar.a(function.apply(this.b), function.apply(this.c), this.a);
            } else {
                this.a.getClass();
                this.d = Boolean.compare(zTest, zTest2);
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    protected final z b(Function function, v vVar, w wVar) {
        if (this.d == 0) {
            this.d = this.a.a((Iterator) function.apply(this.b), (Iterator) function.apply(this.c), vVar);
        }
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.z
    public final z a(Function function) {
        if (this.d == 0) {
            this.d = this.a.a((AbstractC0744s2) function.apply(this.b), (AbstractC0744s2) function.apply(this.c));
        }
        return this;
    }
}
