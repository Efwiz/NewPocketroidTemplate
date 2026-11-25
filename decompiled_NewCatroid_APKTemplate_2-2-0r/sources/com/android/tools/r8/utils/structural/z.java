package com.android.tools.r8.utils.structural;

import com.android.tools.r8.utils.structural.z;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class z<T, V extends z<T, V>> {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Object obj) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Object obj) {
        return true;
    }

    abstract z a();

    public abstract z a(Function function);

    public abstract z a(Predicate predicate);

    protected abstract z a(Predicate predicate, Function function, v vVar, w wVar);

    public abstract V a(ToIntFunction<T> toIntFunction);

    public abstract z a(ToLongFunction toLongFunction);

    public abstract z b(Function function);

    protected abstract z b(Function function, v vVar, w wVar);

    public abstract z b(Predicate predicate);

    public final <S extends x<S>> V c(Function<T, S> function) {
        return (V) a((Function) function, new Predicate() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return z.b(obj);
            }
        });
    }

    public final z d(Function function) {
        return g(function.andThen(new Function() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return z.a((x[]) obj);
            }
        }));
    }

    public final z e(Function function) {
        return b(function.andThen(new Function() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return z.b((x[]) obj);
            }
        }), new v() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.structural.v
            public final int a(Object obj, Object obj2, a aVar) {
                return z.a((x) obj, (x) obj2, aVar);
            }
        }, new w() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.structural.w
            public final void a(Object obj, o oVar) {
                z.a((x) obj, oVar);
            }
        });
    }

    public final z f(Function function) {
        return g(function.andThen(new z$$ExternalSyntheticLambda1()));
    }

    public final z g(Function function) {
        return b(function, new a$$ExternalSyntheticLambda0(), new o$$ExternalSyntheticLambda0());
    }

    public final z h(final Function function) {
        return a(function, new Predicate() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return z.a(function, obj);
            }
        });
    }

    public abstract z i(Function function);

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator b(x[] xVarArr) {
        return Arrays.asList(xVarArr).iterator();
    }

    public final z a(y yVar) {
        yVar.a(this);
        return a();
    }

    public final z a(Function function, v vVar, w wVar) {
        return a(new Predicate() { // from class: com.android.tools.r8.utils.structural.z$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return z.a(obj);
            }
        }, function, vVar, wVar);
    }

    public final z a(Function function, u uVar) {
        return b(function.andThen(new z$$ExternalSyntheticLambda1()), uVar, uVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Function function, Object obj) {
        return function.apply(obj) != null;
    }

    public final z a(Function function, Predicate predicate) {
        return a(predicate, function, new a$$ExternalSyntheticLambda0(), new o$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator a(x[] xVarArr) {
        return Arrays.asList(xVarArr).iterator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(x xVar, x xVar2, a aVar) {
        if (xVar != null && xVar2 != null) {
            return xVar.a(xVar2, aVar);
        }
        return aVar.a(xVar != null, xVar2 != null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(x xVar, o oVar) {
        if (xVar == null) {
            oVar.a(0);
        } else {
            xVar.a(oVar);
        }
    }
}
