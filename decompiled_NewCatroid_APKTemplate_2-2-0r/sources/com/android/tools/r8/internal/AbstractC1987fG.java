package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1987fG {
    public static ZF a(int i, Object obj) {
        return new C1829dG(i, obj);
    }

    static boolean a(InterfaceC1600aG interfaceC1600aG, Object obj) {
        if (obj == interfaceC1600aG) {
            return true;
        }
        if (obj instanceof InterfaceC1600aG) {
            InterfaceC1600aG interfaceC1600aG2 = (InterfaceC1600aG) obj;
            if (interfaceC1600aG.size() == interfaceC1600aG2.size() && interfaceC1600aG.entrySet().size() == interfaceC1600aG2.entrySet().size()) {
                for (ZF zf : interfaceC1600aG2.entrySet()) {
                    if (interfaceC1600aG.b(zf.b()) != zf.a()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    static boolean a(InterfaceC1600aG interfaceC1600aG, Collection collection) {
        int i = AbstractC3123uM.a;
        interfaceC1600aG.getClass();
        collection.getClass();
        if (collection instanceof InterfaceC1600aG) {
            return a(interfaceC1600aG, (InterfaceC1600aG) collection);
        }
        if (collection.isEmpty()) {
            return false;
        }
        return AbstractC3533zt.a(interfaceC1600aG, collection.iterator());
    }

    private static boolean a(final InterfaceC1600aG interfaceC1600aG, InterfaceC1600aG interfaceC1600aG2) {
        if (interfaceC1600aG2.isEmpty()) {
            return false;
        }
        Objects.requireNonNull(interfaceC1600aG);
        interfaceC1600aG2.a(new ObjIntConsumer() { // from class: com.android.tools.r8.internal.fG$$ExternalSyntheticLambda0
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                interfaceC1600aG.a(obj, i);
            }
        });
        return true;
    }

    static Spliterator a(InterfaceC1600aG interfaceC1600aG) {
        Spliterator spliterator = interfaceC1600aG.entrySet().spliterator();
        return AbstractC3142ub.a(spliterator, new Function() { // from class: com.android.tools.r8.internal.fG$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC1987fG.a((ZF) obj);
            }
        }, (spliterator.characteristics() & 1296) | 64, interfaceC1600aG.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Spliterator a(ZF zf) {
        return Collections.nCopies(zf.a(), zf.b()).spliterator();
    }

    public static AbstractC0836Am a(C3454yk c3454yk) {
        ZF[] zfArr = (ZF[]) c3454yk.entrySet().toArray(new ZF[0]);
        Arrays.sort(zfArr, C1752cG.a);
        List listAsList = Arrays.asList(zfArr);
        int i = AbstractC0836Am.d;
        if (listAsList.isEmpty()) {
            return RS.j;
        }
        return RS.a((Collection) listAsList);
    }
}
