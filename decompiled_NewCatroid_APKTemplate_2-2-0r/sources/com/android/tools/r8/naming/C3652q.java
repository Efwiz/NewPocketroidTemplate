package com.android.tools.r8.naming;

import com.android.tools.r8.internal.AbstractC2004fU;
import com.android.tools.r8.internal.AbstractC3401y;
import com.android.tools.r8.internal.C0964Eo;
import com.android.tools.r8.internal.C2238iU;
import com.android.tools.r8.internal.C2308jU;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.PK;
import com.android.tools.r8.references.ArrayReference;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.V1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.naming.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3652q {
    static final /* synthetic */ boolean g = true;
    private HashMap a = new HashMap();
    private final ArrayList b = new ArrayList();
    private HashMap c = new HashMap();
    private final HashMap d = new HashMap();
    private final HashMap e = new HashMap();
    private final ArrayList f = new ArrayList();

    private void b(C3652q c3652q, C3605b c3605b) throws MappingComposeException {
        HashSet hashSet = new HashSet();
        HashMap map = new HashMap();
        for (Map.Entry entry : c3652q.a.entrySet()) {
            String str = (String) entry.getKey();
            C3650p c3650pA = (C3650p) entry.getValue();
            hashSet.add(c3650pA.a);
            C3650p c3650p = (C3650p) this.a.get(c3650pA.a);
            if (c3650p != null) {
                a(c3652q, c3650p);
                c3650pA = c3650p.a(c3650pA);
            }
            map.put(str, c3650pA);
        }
        for (Map.Entry entry2 : this.a.entrySet()) {
            if (!hashSet.contains(entry2.getKey())) {
                C3650p c3650p2 = (C3650p) entry2.getValue();
                a(c3652q, c3650p2);
                if (((C3650p) map.put((String) entry2.getKey(), c3650p2)) != null) {
                    throw new MappingComposeException("Duplicate class mapping. Both '" + c3650p2.a() + "' and '" + c3605b.b((String) entry2.getKey()).a + "' maps to '" + c3650p2.b + "'.");
                }
            }
        }
        this.a = map;
    }

    private void c(final C3652q c3652q, C3605b c3605b) {
        final HashMap map = new HashMap();
        final Map<String, String> map2 = c3605b.e().b;
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c3652q, map2, map, (C3641l) obj, (PK) obj2);
            }
        });
        map.putAll(c3652q.c);
        this.c = map;
    }

    private void d(C3652q c3652q, C3605b c3605b) {
        final Map<String, String> map = c3605b.e().b;
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((C2308jU) it.next()).r().forEach(new Consumer() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(map, (AbstractC2004fU) obj);
                }
            });
        }
        this.b.addAll(c3652q.b);
    }

    public final void a(C3652q c3652q, C3605b c3605b) throws MappingComposeException {
        this.f.addAll(c3605b.f());
        b(c3652q, c3605b);
        d(c3652q, c3605b);
        c(c3652q, c3605b);
    }

    private static void a(C3652q c3652q, final C3650p c3650p) {
        Set set = (Set) c3652q.e.get(c3650p.a);
        if (set == null) {
            return;
        }
        set.forEach(new Consumer() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3652q.b(c3650p, (Q.c) obj);
            }
        });
    }

    public final void a(C3650p c3650p, Q.c cVar) {
        ((Set) this.e.computeIfAbsent(c3650p.a, C3888e1.a(new Supplier() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return OU.b();
            }
        }))).add(cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, AbstractC2004fU abstractC2004fU) {
        C2238iU c2238iU = (C2238iU) abstractC2004fU;
        c2238iU.getClass();
        c2238iU.b(b(map, c2238iU.b()).asClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3652q c3652q, Map map, Map map2, C3641l c3641l, PK pk) {
        C3660s c3660s = (C3660s) c3652q.d.get(c3641l);
        String methodName = pk.q().getMethodName();
        if (c3660s != null) {
            final r rVarA = r.a(c3660s.a);
            final C0964Eo c0964Eo = new C0964Eo(0);
            pk.r().forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C3652q.a(rVarA, c0964Eo, (Integer) obj, (Integer) obj2);
                }
            });
            pk.a(c0964Eo);
            methodName = c3660s.b;
        }
        MethodReference methodReferenceQ = pk.q();
        ClassReference classReferenceAsClass = b(map, methodReferenceQ.getHolderClass()).asClass();
        pk.a(Reference.method(classReferenceAsClass, methodName, a(methodReferenceQ.getFormalTypes(), map), b(map, methodReferenceQ.getReturnType())));
        map2.put(new C3641l(classReferenceAsClass.getTypeName(), c3641l.b), pk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C3650p c3650p, Q.c cVar) {
        cVar.getClass();
        if (cVar instanceof Q.a) {
            c3650p.c.remove(cVar.a());
        } else {
            c3650p.e.remove(cVar.b());
            c3650p.d.remove(cVar.b());
        }
    }

    private static TypeReference b(Map map, TypeReference typeReference) {
        if (typeReference == null || typeReference.isPrimitive()) {
            return typeReference;
        }
        if (typeReference.isArray()) {
            ArrayReference arrayReferenceAsArray = typeReference.asArray();
            return Reference.array(b(map, arrayReferenceAsArray.getBaseType()), arrayReferenceAsArray.getDimensions());
        }
        if (!g && !typeReference.isClass()) {
            throw new AssertionError();
        }
        String str = (String) map.get(typeReference.getTypeName());
        return str == null ? typeReference : Reference.classFromTypeName(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(r rVar, final AbstractC3401y abstractC3401y, Integer num, final Integer num2) {
        rVar.a(num.intValue(), new Consumer() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3652q.a(abstractC3401y, num2, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3401y abstractC3401y, Integer num, Integer num2) {
        abstractC3401y.getClass();
        int iIntValue = num2.intValue();
        ((C0964Eo) abstractC3401y).a(iIntValue);
        abstractC3401y.b(iIntValue, num.intValue());
    }

    public final void a(MethodReference methodReference, PK pk) {
        this.c.put(new C3641l(methodReference.getHolderClass().getTypeName(), methodReference.getMethodName()), pk);
    }

    public final C3660s a(String str, String str2, final String str3) {
        return (C3660s) this.d.computeIfAbsent(new C3641l(str, str2), new Function() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3652q.a(str3, (C3641l) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3660s a(String str, C3641l c3641l) {
        return new C3660s(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TypeReference a(Map map, TypeReference typeReference) {
        return b(map, typeReference);
    }

    private List a(List list, final Map map) {
        return V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.naming.q$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(map, (TypeReference) obj);
            }
        });
    }
}
