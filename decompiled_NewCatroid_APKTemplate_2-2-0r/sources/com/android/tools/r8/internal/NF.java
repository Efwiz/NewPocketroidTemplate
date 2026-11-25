package com.android.tools.r8.internal;

import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import okhttp3.HttpUrl;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class NF {
    private final C0722p1 a;
    private final TreeMap b = new TreeMap();
    private int c = 0;

    public NF(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    private ArrayList b(Map map) {
        ArrayList arrayList = new ArrayList();
        ArrayList<C2822q2> arrayList2 = new ArrayList(map.keySet());
        arrayList2.sort(new Comparator() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NF.a((C2822q2) obj, (C2822q2) obj2);
            }
        });
        for (C2822q2 c2822q2 : arrayList2) {
            C2597nD c2597nD = (C2597nD) map.get(c2822q2);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("api_level_below_or_equal", Integer.valueOf(c2822q2.a()));
            if (c2822q2.c()) {
                linkedHashMap.put("api_level_greater_or_equal", Integer.valueOf(c2822q2.b()));
            }
            a("rewrite_type", c2597nD.p(), linkedHashMap);
            Set<C0767v2> setL = c2597nD.l();
            if (!setL.isEmpty()) {
                linkedHashMap.put("maintain_type", a(setL));
            }
            a("rewrite_derived_type_only", c2597nD.o(), linkedHashMap);
            a("static_field_retarget", c2597nD.q(), linkedHashMap);
            a("covariant_retarget", c2597nD.e(), linkedHashMap);
            a("static_retarget", c2597nD.r(), linkedHashMap);
            a("non_emulated_virtual_retarget", c2597nD.n(), linkedHashMap);
            Map<C0684j2, C2404kg> mapI = c2597nD.i();
            if (!mapI.isEmpty()) {
                linkedHashMap.put("emulated_virtual_retarget", c(mapI));
            }
            a("emulated_virtual_retarget_through_emulated_interface", c2597nD.j(), linkedHashMap);
            a(c2597nD.d(), linkedHashMap);
            Map<C0767v2, C2556mg> mapH = c2597nD.h();
            if (!mapH.isEmpty()) {
                linkedHashMap.put("emulated_interface", c(mapH));
            }
            LinkedHashMap<C0767v2, P20> linkedHashMapS = c2597nD.s();
            if (!linkedHashMapS.isEmpty()) {
                linkedHashMap.put("wrapper", a((LinkedHashMap) linkedHashMapS));
            }
            a("legacy_backport", c2597nD.k(), linkedHashMap);
            Set<C0767v2> setG = c2597nD.g();
            if (!setG.isEmpty()) {
                linkedHashMap.put("dont_retarget", a(setG));
            }
            Map<C0767v2, C3004sd> mapF = c2597nD.f();
            if (!mapF.isEmpty()) {
                linkedHashMap.put("custom_conversion", c(mapF));
            }
            b("amend_library_method", c2597nD.c(), linkedHashMap);
            b("amend_library_field", c2597nD.b(), linkedHashMap);
            arrayList.add(linkedHashMap);
        }
        return arrayList;
    }

    private TreeMap c(Map map) {
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(treeMap, (AbstractC0594a1) obj, (NV) obj2);
            }
        });
        return treeMap;
    }

    private void b(String str, Map map, LinkedHashMap linkedHashMap) {
        if (map.isEmpty()) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, (AbstractC0594a1) obj, (AbstractC0657g) obj2);
            }
        });
        linkedHashMap.put(str, arrayList);
    }

    private void a(Map map, LinkedHashMap linkedHashMap) {
        if (map.isEmpty()) {
            return;
        }
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(treeMap, (AbstractC0594a1) obj, (C0684j2[]) obj2);
            }
        });
        linkedHashMap.put("api_generic_types_conversion", treeMap);
    }

    public static void a(LF lf, StringConsumer stringConsumer, C0722p1 c0722p1) {
        NF nf = new NF(c0722p1);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        C2835qD c2835qDD = lf.d();
        linkedHashMap.put("identifier", c2835qDD.c());
        linkedHashMap.put("required_compilation_api_level", Integer.valueOf(c2835qDD.e().d()));
        linkedHashMap.put("synthesized_library_classes_package_prefix", c2835qDD.f());
        linkedHashMap.put("support_all_callbacks_from_library", Boolean.valueOf(c2835qDD.g()));
        linkedHashMap.put("shrinker_config", c2835qDD.b());
        linkedHashMap.put("configuration_format_version", 200);
        linkedHashMap.put("common_flags", nf.b(lf.a()));
        linkedHashMap.put("program_flags", nf.b(lf.c()));
        linkedHashMap.put("library_flags", nf.b(lf.b()));
        linkedHashMap.put("package_map", nf.b);
        stringConsumer.accept(new C1862dk().a(linkedHashMap), new MF());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C2822q2 c2822q2, C2822q2 c2822q3) {
        return -c2822q2.a(c2822q3);
    }

    private void a(String str, Map map, LinkedHashMap linkedHashMap) {
        if (map.isEmpty()) {
            return;
        }
        linkedHashMap.put(str, a(map));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TreeMap treeMap, AbstractC0594a1 abstractC0594a1, C0684j2[] c0684j2Arr) {
        String strA = a(abstractC0594a1);
        String[] strArr = new String[c0684j2Arr.length];
        for (int i = 0; i < c0684j2Arr.length; i++) {
            C0684j2 c0684j2 = c0684j2Arr[i];
            strArr[i] = c0684j2 == null ? "" : a(c0684j2);
        }
        treeMap.put(strA, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AbstractC0594a1 abstractC0594a1, AbstractC0657g abstractC0657g) {
        list.add(abstractC0657g.toString() + " " + a(abstractC0594a1));
    }

    private LinkedHashMap a(LinkedHashMap linkedHashMap) {
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(linkedHashMap2, (AbstractC0594a1) obj, (NV) obj2);
            }
        });
        return linkedHashMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(LinkedHashMap linkedHashMap, AbstractC0594a1 abstractC0594a1, NV nv) {
        linkedHashMap.put(a(abstractC0594a1), nv.a(this));
    }

    private TreeMap a(Map map) {
        final TreeMap treeMap = new TreeMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(treeMap, (AbstractC0594a1) obj, (AbstractC0594a1) obj2);
            }
        });
        return treeMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(TreeMap treeMap, AbstractC0594a1 abstractC0594a1, AbstractC0594a1 abstractC0594a2) {
        treeMap.put(a(abstractC0594a1), a(abstractC0594a2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(TreeMap treeMap, AbstractC0594a1 abstractC0594a1, NV nv) {
        treeMap.put(a(abstractC0594a1), nv.a(this));
    }

    private ArrayList a(Collection collection) {
        final ArrayList arrayList = new ArrayList();
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, (AbstractC0594a1) obj);
            }
        });
        arrayList.sort(Comparator.naturalOrder());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, AbstractC0594a1 abstractC0594a1) {
        list.add(a(abstractC0594a1));
    }

    private String a(AbstractC0594a1 abstractC0594a1) {
        if (abstractC0594a1 instanceof C0767v2) {
            return a((C0767v2) abstractC0594a1);
        }
        if (abstractC0594a1 instanceof com.android.tools.r8.graph.Y0) {
            com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) abstractC0594a1;
            return a(y0.getType()) + " " + a(y0.B0()) + "#" + y0.C0();
        }
        if (abstractC0594a1 instanceof C0684j2) {
            C0684j2 c0684j2 = (C0684j2) abstractC0594a1;
            StringBuilder sbAppend = new StringBuilder().append(a(c0684j2.I0())).append(" ").append(a(c0684j2.B0())).append("#").append(c0684j2.C0()).append("(");
            for (int i = 0; i < c0684j2.G0().size(); i++) {
                sbAppend.append(a(c0684j2.k(i)));
                if (i != c0684j2.G0().size() - 1) {
                    sbAppend.append(", ");
                }
            }
            sbAppend.append(Constant.AFTER_QUTO);
            return sbAppend.toString();
        }
        throw new C3405y10();
    }

    private String a(C0767v2 c0767v2) {
        if (!c0767v2.W0() && !c0767v2.V0() && !c0767v2.Y0()) {
            if (c0767v2.M0()) {
                StringBuilder sb = new StringBuilder();
                sb.append(a(c0767v2.b(this.a)));
                for (int i = 0; i < c0767v2.G0(); i++) {
                    sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
                return sb.toString();
            }
            return ((String) this.b.computeIfAbsent(c0767v2.I0(), new Function() { // from class: com.android.tools.r8.internal.NF$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((String) obj);
                }
            })) + c0767v2.K0();
        }
        return c0767v2.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String a(String str) {
        return a();
    }

    private String a() {
        int i = this.c;
        if (i < 66) {
            this.c = i + 1;
            return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789æÆøØ".charAt(i) + "$";
        }
        throw new RuntimeException("MultiAPILevelMachineDesugaredLibrarySpecificationJsonExporter cannot encode the next package because the encoding ran out of characters. Extend the chars sequence or improve the encoding to fix this.");
    }

    public final Object[] a(C3004sd c3004sd) {
        return new Object[]{a(c3004sd.b()), a(c3004sd.a())};
    }

    public final Object[] a(C2630ne c2630ne) {
        return new Object[]{a(c2630ne.c()), Integer.toString(c2630ne.b() == null ? -1 : c2630ne.b().a())};
    }

    public final Object[] a(C2404kg c2404kg) {
        return new Object[]{a(c2404kg.d()), a(c2404kg.b()), a(c2404kg.c()), a(c2404kg.a())};
    }

    public final Object[] a(C2556mg c2556mg) {
        return new Object[]{a((AbstractC0594a1) c2556mg.b()), c(c2556mg.a())};
    }

    public final Object[] a(P20 p20) {
        return new Object[]{a(p20.a()), Boolean.valueOf(p20.c()), a(p20.b())};
    }
}
