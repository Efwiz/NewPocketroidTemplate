package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2264iv {
    private static final C2264iv b = new C2264iv(Collections.emptyMap());
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    private final Map a;

    private C2264iv(Map map) {
        if (!c && map == null) {
            throw new AssertionError();
        }
        this.a = map;
    }

    public static C2187hv a() {
        return new C2187hv();
    }

    public static C2264iv b() {
        return b;
    }

    public final String toString() {
        return "{" + ((String) this.a.entrySet().stream().map(new Function() { // from class: com.android.tools.r8.internal.iv$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2264iv.a((Map.Entry) obj);
            }
        }).collect(Collectors.joining(", ")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        AbstractC2496lv abstractC2496lvD = a(str).a.d();
        return abstractC2496lvD.d() ? b(abstractC2496lvD.a()) : abstractC2496lvD.b().e();
    }

    public final C2117gv a(String str) {
        return (C2117gv) this.a.get(str);
    }

    public final void a(final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.iv$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2264iv.a(biConsumer, (String) obj, (C2117gv) obj2);
            }
        });
    }

    public final boolean a(C0846Aw c0846Aw) {
        return c0846Aw.a(new Predicate() { // from class: com.android.tools.r8.internal.iv$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(Map.Entry entry) {
        return ((String) entry.getKey()) + ContainerUtils.KEY_VALUE_DELIMITER + entry.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(BiConsumer biConsumer, String str, C2117gv c2117gv) {
        biConsumer.accept(str, c2117gv.a);
    }
}
