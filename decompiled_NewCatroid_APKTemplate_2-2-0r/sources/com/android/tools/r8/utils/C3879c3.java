package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.o5;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.c3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3879c3 {

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.android.tools.r8.utils.c3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final a b = new a("HEAVY", 0, 2);
        public static final a c = new a("LIGHT", 1, 4);
        private final int a;

        private a(String str, int i, int i2) {
            super(str, i);
            this.a = i2;
        }

        public int a() {
            return this.a;
        }
    }

    public static Future a(final InterfaceC3880d interfaceC3880d, ExecutorService executorService) {
        return executorService.submit(new Callable() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda7
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return C3879c3.a(interfaceC3880d);
            }
        });
    }

    static ExecutorService b(int i) {
        if (i > 2) {
            i = (int) Math.ceil(((double) Integer.min(i, 16)) / 2.0d);
        }
        return Executors.newWorkStealingPool(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3880d interfaceC3880d) {
        interfaceC3880d.a();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3910i3 interfaceC3910i3, Object obj, int i) {
        return interfaceC3910i3.apply(obj);
    }

    public static ArrayList a(Iterable iterable, final InterfaceC3910i3 interfaceC3910i3, ExecutorService executorService) {
        return a(iterable, new InterfaceC3944o3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3944o3
            public final Object a(Object obj, int i) {
                return C3879c3.a(interfaceC3910i3, obj, i);
            }
        }, executorService);
    }

    public static ArrayList a(final Iterable iterable, InterfaceC3944o3 interfaceC3944o3, ExecutorService executorService) {
        Objects.requireNonNull(iterable);
        return a(new InterfaceC3877c1() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                iterable.forEach(consumer);
            }
        }, interfaceC3944o3, executorService);
    }

    public static ArrayList a(InterfaceC3877c1 interfaceC3877c1, final InterfaceC3944o3 interfaceC3944o3, final ExecutorService executorService) {
        final C3932m1 c3932m1 = new C3932m1();
        final ArrayList arrayList = new ArrayList();
        interfaceC3877c1.forEach(new Consumer() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3879c3.a(c3932m1, arrayList, executorService, interfaceC3944o3, obj);
            }
        });
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                try {
                    arrayList2.add(((Future) it.next()).get());
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted while waiting for future.", e);
                }
            } catch (Throwable th) {
                while (it.hasNext()) {
                    ((Future) it.next()).get();
                }
                throw th;
            }
        }
        while (it.hasNext()) {
            ((Future) it.next()).get();
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3932m1 c3932m1, List list, ExecutorService executorService, final InterfaceC3944o3 interfaceC3944o3, final Object obj) {
        final int iB = c3932m1.b();
        list.add(executorService.submit(new Callable() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda9
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return interfaceC3944o3.a(obj, iB);
            }
        }));
    }

    public static void a(Collection collection, final Consumer consumer, ExecutorService executorService) {
        a(collection, new U1() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                consumer.accept(obj);
            }
        }, executorService, a.c);
    }

    public static void a(final Collection collection, final U1 u1, ExecutorService executorService, a aVar) {
        if (collection.size() >= aVar.a()) {
            InterfaceC3877c1 interfaceC3877c1 = new InterfaceC3877c1() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda5
                @Override // com.android.tools.r8.utils.InterfaceC3877c1
                public final void forEach(Consumer consumer) {
                    collection.forEach(consumer);
                }
            };
            Objects.requireNonNull(u1);
            a(interfaceC3877c1, new InterfaceC3939n3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda6
                @Override // com.android.tools.r8.utils.InterfaceC3939n3
                public final void accept(Object obj, int i) {
                    u1.accept(obj, i);
                }
            }, executorService);
        } else {
            Iterator it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                u1.accept(it.next(), i);
                i++;
            }
        }
    }

    public static void a(InterfaceC3877c1 interfaceC3877c1, final InterfaceC3905h3 interfaceC3905h3, ExecutorService executorService) {
        a(interfaceC3877c1, new InterfaceC3939n3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3939n3
            public final void accept(Object obj, int i) throws Throwable {
                interfaceC3905h3.accept(obj);
            }
        }, executorService);
    }

    public static void a(InterfaceC3877c1 interfaceC3877c1, final InterfaceC3939n3 interfaceC3939n3, ExecutorService executorService) {
        a(interfaceC3877c1, new InterfaceC3944o3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3944o3
            public final Object a(Object obj, int i) {
                return C3879c3.a(interfaceC3939n3, obj, i);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3939n3 interfaceC3939n3, Object obj, int i) {
        interfaceC3939n3.accept(obj, i);
        return null;
    }

    public static void a(Map map, final InterfaceC3890e3 interfaceC3890e3, ExecutorService executorService) {
        a(map, new InterfaceC3895f3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3895f3
            public final Object apply(Object obj, Object obj2) {
                return C3879c3.a(interfaceC3890e3, obj, obj2);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3890e3 interfaceC3890e3, Object obj, Object obj2) throws Throwable {
        interfaceC3890e3.accept(obj, obj2);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(InterfaceC3895f3 interfaceC3895f3, Map.Entry entry) {
        return interfaceC3895f3.apply(entry.getKey(), entry.getValue());
    }

    public static void a(Map map, final InterfaceC3895f3 interfaceC3895f3, ExecutorService executorService) {
        a(map.entrySet(), new InterfaceC3910i3() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.InterfaceC3910i3
            public final Object apply(Object obj) {
                return C3879c3.a(interfaceC3895f3, (Map.Entry) obj);
            }
        }, executorService);
    }

    public static void a(C0702m c0702m, final InterfaceC3905h3 interfaceC3905h3, ExecutorService executorService) {
        a(c0702m.f().d(), new Consumer() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3879c3.a(interfaceC3905h3, (C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final InterfaceC3905h3 interfaceC3905h3, C0729q2 c0729q2) {
        Objects.requireNonNull(interfaceC3905h3);
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.utils.c3$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC3905h3.a((o5) obj);
            }
        });
    }

    public static void a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                try {
                    ((Future) it.next()).get();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted while waiting for future.", e);
                }
            } catch (Throwable th) {
                while (it.hasNext()) {
                    ((Future) it.next()).get();
                }
                throw th;
            }
        }
        while (it.hasNext()) {
            ((Future) it.next()).get();
        }
    }

    public static ExecutorService a(int i) {
        if (i == -1) {
            return b(Runtime.getRuntime().availableProcessors());
        }
        return Executors.newWorkStealingPool(i);
    }

    public static ExecutorService a(A1 a1) {
        return a(a1.a0);
    }

    public static int a(ExecutorService executorService) {
        if (executorService instanceof ForkJoinPool) {
            return ((ForkJoinPool) executorService).getParallelism();
        }
        return -1;
    }
}
