package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.Version;
import com.android.tools.r8.dex.B$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3453yj extends AbstractC2817q {
    static final /* synthetic */ boolean k = true;

    public C3453yj(String str, String str2, String str3, String str4) throws Exception {
        super(str, str2, str3, str4);
    }

    public static C3453yj a(Path path, Set<Path> set, Path path2, Path path3) throws Exception {
        return new C3453yj(path, set, path2, path3);
    }

    private void b(EnumC3891f enumC3891f, final EnumC3891f enumC3891f2, EY ey) throws IOException {
        final ArrayList arrayList = new ArrayList();
        final C0612b4.a aVarA = AbstractC0681j0.a(this.c, com.android.tools.r8.utils.y3.b());
        ey.a(new Consumer() { // from class: com.android.tools.r8.internal.yj$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(enumC3891f2, arrayList, aVarA, (EY.d) obj);
            }
        });
        arrayList.sort(Comparator.naturalOrder());
        com.android.tools.r8.utils.X0.a(a(enumC3891f, enumC3891f2, Constants.TEXT_FILE_EXTENSION), arrayList);
        C3334x6 c3334x6 = new C3334x6(C0702m.a(C0664h.a(aVarA.b(), com.android.tools.r8.synthesis.y.d())), new com.android.tools.r8.dex.U(com.android.tools.r8.dex.U.b.D8).d(Version.LABEL).a(CompilationMode.DEBUG).a(com.android.tools.r8.dex.U.a.CF));
        ClassFileConsumer.ArchiveConsumer archiveConsumer = new ClassFileConsumer.ArchiveConsumer(a(enumC3891f, enumC3891f2, ".jar"));
        c3334x6.a(archiveConsumer);
        archiveConsumer.finished(this.c.e);
    }

    private C3453yj(Path path, Collection collection, Path path2, Path path3) {
        super(path, collection, path2, path3);
    }

    private void a(AbstractC0674i0 abstractC0674i0, AbstractC0727q0 abstractC0727q0, Collection collection) {
        C0753u c0753u;
        if (collection.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.W0 w0 = (com.android.tools.r8.graph.W0) it.next();
            if (!k && w0.J0() != abstractC0727q0.d) {
                throw new AssertionError();
            }
            if (w0.f.W()) {
                c0753u = null;
            } else {
                C0684j2 reference = w0.getReference();
                c0753u = new C0753u(reference.e, 1, reference.h.f.size() + 1, Arrays.asList(new C1668b7(), new V8()));
            }
            com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.S0().a(w0.getReference()).a(w0.f).a(C0706m3.g.d()).a(c0753u).a(Z8.f).c().a();
            if (w0.E0() || w0.y1()) {
                arrayList.add(w0A);
            } else {
                arrayList2.add(w0A);
            }
        }
        com.android.tools.r8.graph.W0[] w0Arr = new com.android.tools.r8.graph.W0[arrayList.size()];
        com.android.tools.r8.graph.W0[] w0Arr2 = new com.android.tools.r8.graph.W0[arrayList2.size()];
        arrayList.toArray(w0Arr);
        arrayList2.toArray(w0Arr2);
        if (!k && this.c.H) {
            throw new AssertionError();
        }
        B$$ExternalSyntheticLambda1 b$$ExternalSyntheticLambda1 = new B$$ExternalSyntheticLambda1();
        C0767v2 c0767v2 = abstractC0727q0.d;
        Origin originUnknown = Origin.unknown();
        com.android.tools.r8.graph.E e = abstractC0727q0.e;
        C0767v2 c0767v3 = abstractC0727q0.f;
        C0800x2 c0800x2 = abstractC0727q0.g;
        List listEmptyList = Collections.emptyList();
        List listEmptyList2 = Collections.emptyList();
        List listEmptyList3 = Collections.emptyList();
        List listEmptyList4 = Collections.emptyList();
        C0706m3.b bVarF = C0706m3.b.f();
        C0658g0 c0658g0T0 = C0658g0.t0();
        com.android.tools.r8.graph.T0[] t0Arr = com.android.tools.r8.graph.T0.m;
        abstractC0674i0.a(new C0729q2(c0767v2, null, originUnknown, e, c0767v3, c0800x2, null, null, listEmptyList, listEmptyList2, listEmptyList3, null, listEmptyList4, bVarF, c0658g0T0, t0Arr, t0Arr, C0792w4.a.a(w0Arr, w0Arr2), false, b$$ExternalSyntheticLambda1));
    }

    private static String a(EnumC3891f enumC3891f, EnumC3891f enumC3891f2) {
        return "desugared_apis_" + enumC3891f.d() + "_" + enumC3891f2.d();
    }

    private Path a(EnumC3891f enumC3891f, EnumC3891f enumC3891f2, String str) throws IOException {
        Path pathResolve = this.g.resolve("compile_api_level_" + enumC3891f.d());
        Files.createDirectories(pathResolve, new FileAttribute[0]);
        return Paths.get(pathResolve + File.separator + a(enumC3891f, enumC3891f2) + str, new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final EnumC3891f enumC3891f, final List list, C0612b4.a aVar, EY.d dVar) {
        final String strI = C3970u0.i(dVar.d().e.toString());
        if (!dVar.a().e()) {
            dVar.b(new BiConsumer() { // from class: com.android.tools.r8.internal.yj$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(enumC3891f, list, strI, (com.android.tools.r8.graph.W0) obj, (EY.c) obj2);
                }
            });
            dVar.a(new BiConsumer() { // from class: com.android.tools.r8.internal.yj$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C3453yj.a(list, strI, (com.android.tools.r8.graph.T0) obj, (EY.a) obj2);
                }
            });
        } else {
            list.add(strI);
        }
        a(aVar, dVar.b(), dVar.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(EnumC3891f enumC3891f, List list, String str, com.android.tools.r8.graph.W0 w0, EY.c cVar) {
        if (w0.C1() || w0.u1()) {
            return;
        }
        if (cVar != null) {
            if (cVar.a) {
                return;
            }
            if (cVar.e) {
                if (enumC3891f != EnumC3891f.L) {
                    return;
                }
            } else if (!k && !cVar.f) {
                throw new AssertionError();
            }
        }
        list.add(str + "#" + w0.getReference().f + w0.getReference().h.w0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, String str, com.android.tools.r8.graph.T0 t0, EY.a aVar) {
        if (aVar == null || !aVar.a) {
            list.add(str + "#" + t0.getReference().f);
        }
    }

    private void a(EnumC3891f enumC3891f, EnumC3891f enumC3891f2, EY ey) throws IOException {
        System.out.print("  - generating for min API:");
        System.out.print(" " + enumC3891f2);
        b(enumC3891f, enumC3891f2, ey);
    }

    public EnumC3891f a() throws Exception {
        EnumC3891f enumC3891fC = this.d.c();
        EY eyB = new GY(this.c, this.h).b(this.f, this.e);
        System.out.println("Generating lint files for " + this.d.p() + " (compile API " + enumC3891fC + Constant.AFTER_QUTO);
        a(enumC3891fC, EnumC3891f.B, eyB);
        a(enumC3891fC, EnumC3891f.L, eyB);
        System.out.println();
        return enumC3891fC;
    }

    public static void a(String[] strArr) throws Exception {
        AbstractC2817q.a(strArr);
    }
}
