package com.android.tools.r8.internal;

import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1201Mz {
    private static final C1201Mz c = new C1201Mz(new HashSet(), new HashSet());
    private final Set a;
    private final Set b;

    private C1201Mz(HashSet hashSet, HashSet hashSet2) {
        this.a = hashSet;
        this.b = hashSet2;
    }

    public static C1201Mz a(KotlinClassMetadata.Class r2, com.android.tools.r8.utils.A1 a1) {
        try {
            C2772pL c2772pL = (C2772pL) new com.android.tools.r8.utils.J2().a("classData$delegate").b().a().b().b().a().a().a(r2);
            C1174Lz c1174Lz = new C1174Lz();
            C1761cO c1761cO = (C1761cO) c2772pL.d();
            c1174Lz.b(c1761cO.s());
            c1174Lz.a(c1761cO.l());
            return new C1201Mz(c1174Lz.a, c1174Lz.b);
        } catch (Exception unused) {
            a1.I1();
            return c;
        }
    }

    public final boolean b(int i) {
        return !this.a.contains(Integer.valueOf(i));
    }

    public static C1201Mz a(KotlinClassMetadata.FileFacade fileFacade, com.android.tools.r8.utils.A1 a1) {
        try {
            C2772pL c2772pL = (C2772pL) new com.android.tools.r8.utils.J2().a("packageData$delegate").b().a().b().b().a().a().a(fileFacade);
            C1174Lz c1174Lz = new C1174Lz();
            c1174Lz.b(((CO) c2772pL.d()).l());
            return new C1201Mz(c1174Lz.a, c1174Lz.b);
        } catch (Exception unused) {
            a1.I1();
            return c;
        }
    }

    public static C1201Mz a(KotlinClassMetadata.MultiFileClassPart multiFileClassPart, com.android.tools.r8.utils.A1 a1) {
        try {
            C2772pL c2772pL = (C2772pL) new com.android.tools.r8.utils.J2().a("packageData$delegate").b().a().b().b().a().a().a(multiFileClassPart);
            C1174Lz c1174Lz = new C1174Lz();
            c1174Lz.b(((CO) c2772pL.d()).l());
            return new C1201Mz(c1174Lz.a, c1174Lz.b);
        } catch (Exception unused) {
            a1.I1();
            return c;
        }
    }

    public static C1201Mz a(KotlinClassMetadata.SyntheticClass syntheticClass, com.android.tools.r8.utils.A1 a1) {
        try {
            C2772pL c2772pL = (C2772pL) new com.android.tools.r8.utils.J2().a("functionData$delegate").b().a().b().b().a().a().a(syntheticClass);
            if (c2772pL == null) {
                return c;
            }
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            if (!((C3354xO) c2772pL.d()).b(AbstractC1543Yu.b)) {
                hashSet.add(0);
            }
            return new C1201Mz(hashSet, hashSet2);
        } catch (Exception unused) {
            a1.I1();
            return c;
        }
    }

    public final boolean a(int i) {
        return !this.b.contains(Integer.valueOf(i));
    }
}
