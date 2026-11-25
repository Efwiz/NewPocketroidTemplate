package com.android.tools.r8.internal;

import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class HF {
    public static void a(EF ef, StringConsumer stringConsumer) {
        HF hf = new HF();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("identifier", ef.e().c());
        linkedHashMap.put("configuration_format_version", 100);
        linkedHashMap.put("required_compilation_api_level", Integer.valueOf(ef.e().e().d()));
        linkedHashMap.put("synthesized_library_classes_package_prefix", ef.e().f().replace('/', '.'));
        linkedHashMap.put("support_all_callbacks_from_library", Boolean.valueOf(ef.e().g()));
        linkedHashMap.put("common_flags", hf.d(ef.a()));
        linkedHashMap.put("program_flags", hf.d(ef.d()));
        linkedHashMap.put("library_flags", hf.d(ef.b()));
        linkedHashMap.put("shrinker_config", ef.e().b());
        stringConsumer.accept(new C1862dk().a(linkedHashMap), new GF());
    }

    private TreeMap b(Map map) {
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(treeMap, (AbstractC0594a1) obj, (C0684j2[]) obj2);
            }
        });
        return treeMap;
    }

    private TreeMap c(Map map) {
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(treeMap, (AbstractC0594a1) obj, (AbstractC0594a1) obj2);
            }
        });
        return treeMap;
    }

    private ArrayList d(Map map) {
        ArrayList arrayList = new ArrayList();
        ArrayList<C2822q2> arrayList2 = new ArrayList(map.keySet());
        arrayList2.sort(new Comparator() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return HF.a((C2822q2) obj, (C2822q2) obj2);
            }
        });
        for (C2822q2 c2822q2 : arrayList2) {
            C1297Qk c1297Qk = (C1297Qk) map.get(c2822q2);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("api_level_below_or_equal", Integer.valueOf(c2822q2.a()));
            if (c2822q2.c()) {
                linkedHashMap.put("api_level_greater_or_equal", Integer.valueOf(c2822q2.b()));
            }
            if (!c1297Qk.s().isEmpty()) {
                linkedHashMap.put("rewrite_prefix", new TreeMap(c1297Qk.s()));
            }
            if (!c1297Qk.r().isEmpty()) {
                final TreeMap treeMap = new TreeMap();
                c1297Qk.r().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        HF.a(treeMap, (String) obj, (Map) obj2);
                    }
                });
                linkedHashMap.put("rewrite_derived_prefix", treeMap);
            }
            if (!c1297Qk.h().isEmpty()) {
                ArrayList arrayList3 = new ArrayList(c1297Qk.h());
                Collections.sort(arrayList3);
                linkedHashMap.put("dont_rewrite_prefix", arrayList3);
            }
            if (!c1297Qk.k().isEmpty()) {
                ArrayList arrayList4 = new ArrayList(c1297Qk.k());
                Collections.sort(arrayList4);
                linkedHashMap.put("maintain_prefix", arrayList4);
            }
            if (!c1297Qk.i().isEmpty()) {
                linkedHashMap.put("emulate_interface", c(c1297Qk.i()));
            }
            if (!c1297Qk.g().isEmpty()) {
                linkedHashMap.put("dont_rewrite", a(c1297Qk.g()));
            }
            if (!c1297Qk.q().isEmpty()) {
                linkedHashMap.put("retarget_static_field", c(c1297Qk.q()));
            }
            if (!c1297Qk.p().isEmpty()) {
                linkedHashMap.put("retarget_method", c(c1297Qk.p()));
            }
            if (!c1297Qk.m().isEmpty()) {
                linkedHashMap.put("retarget_method", c(c1297Qk.m()));
            }
            if (!c1297Qk.n().isEmpty()) {
                linkedHashMap.put("retarget_method_with_emulated_dispatch", c(c1297Qk.n()));
            }
            if (!c1297Qk.m().isEmpty()) {
                linkedHashMap.put("retarget_method_with_emulated_dispatch", c(c1297Qk.m()));
            }
            if (!c1297Qk.f().isEmpty()) {
                linkedHashMap.put("dont_retarget", a(c1297Qk.f()));
            }
            if (!c1297Qk.j().isEmpty()) {
                linkedHashMap.put("backport", c(c1297Qk.j()));
            }
            if (!c1297Qk.c().isEmpty()) {
                linkedHashMap.put("api_generic_types_conversion", b(c1297Qk.c()));
            }
            if (!c1297Qk.t().isEmpty()) {
                a(linkedHashMap, c1297Qk.t());
            }
            if (!c1297Qk.e().isEmpty()) {
                linkedHashMap.put("custom_conversion", c(c1297Qk.e()));
            }
            if (!c1297Qk.l().isEmpty()) {
                linkedHashMap.put("never_outline_api", a(c1297Qk.l()));
            }
            if (!c1297Qk.b().isEmpty()) {
                linkedHashMap.put("amend_library_method", a(c1297Qk.b()));
            }
            if (!c1297Qk.a().isEmpty()) {
                linkedHashMap.put("amend_library_method", a(c1297Qk.a()));
            }
            arrayList.add(linkedHashMap);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C2822q2 c2822q2, C2822q2 c2822q3) {
        return -c2822q2.a(c2822q3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(TreeMap treeMap, String str, Map map) {
        treeMap.put(str, new TreeMap(map));
    }

    private void a(LinkedHashMap linkedHashMap, Map map) {
        final ArrayList arrayList = new ArrayList();
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, treeMap, (C0767v2) obj, (Set) obj2);
            }
        });
        if (!arrayList.isEmpty()) {
            linkedHashMap.put("wrapper_conversion", arrayList);
        }
        if (treeMap.isEmpty()) {
            return;
        }
        linkedHashMap.put("wrapper_conversion_excluding", treeMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, Map map, C0767v2 c0767v2, Set set) {
        if (set.isEmpty()) {
            list.add(a(c0767v2));
        } else {
            map.put(a(c0767v2), a(set));
        }
    }

    private ArrayList a(Map map) {
        final ArrayList arrayList = new ArrayList();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, (AbstractC0594a1) obj, (AbstractC0657g) obj2);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AbstractC0594a1 abstractC0594a1, AbstractC0657g abstractC0657g) {
        list.add(abstractC0657g.toString() + " " + a(abstractC0594a1));
    }

    private ArrayList a(Set set) {
        final ArrayList arrayList = new ArrayList();
        set.forEach(new Consumer() { // from class: com.android.tools.r8.internal.HF$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, (AbstractC0594a1) obj);
            }
        });
        Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AbstractC0594a1 abstractC0594a1) {
        list.add(a(abstractC0594a1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, AbstractC0594a1 abstractC0594a1, AbstractC0594a1 abstractC0594a2) {
        map.put(a(abstractC0594a1), a(abstractC0594a2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, AbstractC0594a1 abstractC0594a1, C0684j2[] c0684j2Arr) {
        String strA = a(abstractC0594a1);
        ArrayList arrayList = new ArrayList();
        int length = c0684j2Arr.length - 1;
        if (c0684j2Arr[length] != null) {
            arrayList.add(-1);
            arrayList.add(a(c0684j2Arr[length]));
        }
        for (int i = 0; i < c0684j2Arr.length - 1; i++) {
            if (c0684j2Arr[i] != null) {
                arrayList.add(Integer.valueOf(i));
                arrayList.add(a(c0684j2Arr[i]));
            }
        }
        map.put(strA, arrayList.toArray());
    }

    private static String a(AbstractC0594a1 abstractC0594a1) {
        if (abstractC0594a1 instanceof C0767v2) {
            return abstractC0594a1.toString();
        }
        if (abstractC0594a1 instanceof com.android.tools.r8.graph.Y0) {
            com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) abstractC0594a1;
            return y0.getType() + " " + y0.B0() + "#" + y0.C0();
        }
        if (abstractC0594a1 instanceof C0684j2) {
            C0684j2 c0684j2 = (C0684j2) abstractC0594a1;
            StringBuilder sbAppend = new StringBuilder().append(c0684j2.I0()).append(" ").append(c0684j2.B0()).append("#").append(c0684j2.C0()).append("(");
            for (int i = 0; i < c0684j2.G0().size(); i++) {
                sbAppend.append(c0684j2.k(i));
                if (i != c0684j2.G0().size() - 1) {
                    sbAppend.append(", ");
                }
            }
            sbAppend.append(Constant.AFTER_QUTO);
            return sbAppend.toString();
        }
        throw new C3405y10();
    }
}
