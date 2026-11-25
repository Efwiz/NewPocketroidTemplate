package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ld, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2475ld {
    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void h(Object obj, final Function function) {
        c(obj, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.a(function, obj2, (C0767v2) obj3);
            }
        });
    }

    com.android.tools.r8.utils.D3 a(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction);

    com.android.tools.r8.utils.D3 a(Object obj, com.android.tools.r8.utils.G3 g3, Object obj2);

    com.android.tools.r8.utils.D3 a(Object obj, Object obj2, BiFunction biFunction);

    Object a();

    default Object a(final Object obj) {
        if (AbstractC2398kd.a || b(obj)) {
            return com.android.tools.r8.utils.E3.a(new Function() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda16
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return this.f$0.c(obj, (Function) obj2);
                }
            });
        }
        throw new AssertionError();
    }

    com.android.tools.r8.utils.D3 b(Object obj, AbstractC3327x1 abstractC3327x1, BiFunction biFunction);

    default boolean b(final Object obj) {
        return com.android.tools.r8.utils.E3.a(1, new Consumer() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.g(obj, (Function) obj2);
            }
        });
    }

    com.android.tools.r8.utils.D3 d(Object obj, BiFunction biFunction);

    default Object e(final Object obj) {
        if (AbstractC2398kd.a || c(obj)) {
            return com.android.tools.r8.utils.E3.a(new Function() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return this.f$0.i(obj, (Function) obj2);
                }
            });
        }
        throw new AssertionError();
    }

    default boolean f(final Object obj) {
        return !com.android.tools.r8.utils.E3.a(0, new Consumer() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.d(obj, (Function) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    default com.android.tools.r8.utils.D3 i(Object obj, final Function function) {
        return b(obj, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.c(function, obj2, obj3);
            }
        });
    }

    default boolean c(final Object obj) {
        return com.android.tools.r8.utils.E3.a(1, new Consumer() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.a(obj, (Function) obj2);
            }
        });
    }

    default boolean d(final Object obj) {
        return !com.android.tools.r8.utils.E3.a(0, new Consumer() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.h(obj, (Function) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 a(Function function, Object obj, C0767v2 c0767v2) {
        return (com.android.tools.r8.utils.D3) function.apply(obj);
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    default void d(Object obj, final Function function) {
        a(obj, (Object) null, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda7
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.d(function, obj2, obj3);
            }
        });
    }

    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    default com.android.tools.r8.utils.D3 g(Object obj, final Function function) {
        return a(obj, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.b(function, obj2, obj3);
            }
        });
    }

    default void f(Object obj, final Function function) {
        d(obj, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.a(function, obj2, obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 b(Function function, Object obj, Object obj2) {
        return (com.android.tools.r8.utils.D3) function.apply(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 c(Function function, Object obj, Object obj2) {
        return (com.android.tools.r8.utils.D3) function.apply(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 d(Function function, Object obj, Object obj2) {
        return (com.android.tools.r8.utils.D3) function.apply(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 a(Function function, Object obj, Object obj2) {
        return (com.android.tools.r8.utils.D3) function.apply(obj);
    }

    default com.android.tools.r8.utils.D3 b(final Object obj, final BiFunction biFunction) {
        return a(obj, (AbstractC3327x1) null, biFunction).a(new Function() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return this.f$0.a(obj, biFunction, (com.android.tools.r8.utils.C3) obj2);
            }
        });
    }

    default void c(Object obj, final BiFunction biFunction) {
        a(obj, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj2, Object obj3, Object obj4) {
                return InterfaceC2475ld.a(biFunction, obj2, (C0767v2) obj3, obj4);
            }
        }, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 a(BiFunction biFunction, Object obj, C0767v2 c0767v2, Object obj2) {
        return (com.android.tools.r8.utils.D3) biFunction.apply(obj, c0767v2);
    }

    default void c(final Consumer consumer, Object obj) {
        b(consumer, obj);
        a(obj, new BiConsumer() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj2, Object obj3) {
                consumer.accept(obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default com.android.tools.r8.utils.D3 a(Object obj, BiFunction biFunction, com.android.tools.r8.utils.C3 c3) {
        return a(obj, c3.j(), biFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default com.android.tools.r8.utils.D3 b(Object obj, final BiFunction biFunction, com.android.tools.r8.utils.C3 c3) {
        return a(obj, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj2, Object obj3, Object obj4) {
                return InterfaceC2475ld.b(biFunction, obj2, (C0767v2) obj3, obj4);
            }
        }, c3.j());
    }

    default com.android.tools.r8.utils.D3 a(final Object obj, final BiFunction biFunction) {
        return d(obj, biFunction).a(new Function() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return this.f$0.b(obj, biFunction, (com.android.tools.r8.utils.C3) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 a(Consumer consumer, Object obj) {
        consumer.accept(obj);
        return com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 b(BiFunction biFunction, Object obj, C0767v2 c0767v2, Object obj2) {
        return (com.android.tools.r8.utils.D3) biFunction.apply(obj, obj2);
    }

    default void a(Object obj, final BiConsumer biConsumer) {
        c(obj, new BiFunction() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return InterfaceC2475ld.a(biConsumer, obj2, (C0767v2) obj3);
            }
        });
    }

    default void b(final Consumer consumer, Object obj) {
        f(obj, new Function() { // from class: com.android.tools.r8.internal.ld$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return InterfaceC2475ld.a(consumer, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ com.android.tools.r8.utils.D3 a(BiConsumer biConsumer, Object obj, C0767v2 c0767v2) {
        biConsumer.accept(obj, c0767v2);
        return com.android.tools.r8.utils.D3.e();
    }
}
