package com.android.tools.r8.dex;

import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.AbstractC1249Ot;
import com.android.tools.r8.internal.C1222Nt;
import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1386Tt;
import com.android.tools.r8.internal.C1542Yt;
import com.reandroid.dex.sections.Marker;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class U {
    private static final String c = "~~" + b.D8 + "{";
    private static final String d = "~~" + b.R8 + "{";
    private static final String e = "~~" + b.L8 + "{";
    static final /* synthetic */ boolean f = true;
    private final C1333Rt a;
    private final b b;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        CF,
        DEX
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum b {
        D8,
        R8,
        L8,
        Relocator,
        TraceReferences;

        public static b[] a() {
            return new b[]{D8, R8};
        }
    }

    public U(b bVar) {
        this(bVar, new C1333Rt());
    }

    public U a(long j) {
        if (!f && this.a.b(Marker.MIN_API)) {
            throw new AssertionError();
        }
        this.a.a(Long.valueOf(j));
        return this;
    }

    public String b() {
        return this.a.a(Marker.COMPILATION_MODE).f();
    }

    public String[] c() {
        if (!this.a.b(Marker.DESUGARED_LIBRARY_IDENTIFIERS)) {
            return new String[0];
        }
        C1222Nt c1222NtC = this.a.a(Marker.DESUGARED_LIBRARY_IDENTIFIERS).c();
        String[] strArr = new String[c1222NtC.size()];
        for (int i = 0; i < c1222NtC.size(); i++) {
            strArr[i] = c1222NtC.j(i).f();
        }
        return strArr;
    }

    public U d(String str) {
        if (!f && this.a.b("version")) {
            throw new AssertionError();
        }
        this.a.a("version", str);
        return this;
    }

    public Long e() {
        return Long.valueOf(this.a.a(Marker.MIN_API).e());
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof U)) {
            return false;
        }
        U u = (U) obj;
        return this.b == u.b && this.a.equals(u.a);
    }

    public String f() {
        return this.a.a(Marker.PG_MAP_ID).f();
    }

    public String g() {
        return this.a.a(Marker.R8_MODE).f();
    }

    public b h() {
        return this.b;
    }

    public final int hashCode() {
        return (this.a.hashCode() * 3) + this.b.hashCode();
    }

    public String i() {
        return this.a.a("version").f();
    }

    public boolean j() {
        return this.a.b(Marker.DESUGARED_LIBRARY_IDENTIFIERS);
    }

    public boolean k() {
        return this.a.b(Marker.MIN_API);
    }

    public boolean l() {
        return this.a.b(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR) && this.a.a(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR).a();
    }

    public final boolean m() {
        return this.b == b.D8;
    }

    public boolean n() {
        return k();
    }

    public final boolean o() {
        return this.b == b.L8;
    }

    public final boolean p() {
        return this.b == b.R8;
    }

    public final boolean q() {
        return this.b == b.Relocator;
    }

    public final void r() {
        if (!f && this.a.b(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR)) {
            throw new AssertionError();
        }
        this.a.a(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, Boolean.TRUE);
    }

    public final String toString() {
        final C1333Rt c1333Rt = new C1333Rt();
        this.a.entrySet().stream().sorted(Comparator.comparing(new U$$ExternalSyntheticLambda0())).forEach(new Consumer() { // from class: com.android.tools.r8.dex.U$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                U.a(c1333Rt, (Map.Entry) obj);
            }
        });
        return "~~" + this.b + c1333Rt;
    }

    private U(b bVar, C1333Rt c1333Rt) {
        this.b = bVar;
        this.a = c1333Rt;
    }

    public final void b(String str) {
        if (!f && this.a.b(Marker.R8_MODE)) {
            throw new AssertionError();
        }
        this.a.a(Marker.R8_MODE, str);
    }

    public final void a(String... strArr) {
        if (!f && this.a.b(Marker.DESUGARED_LIBRARY_IDENTIFIERS)) {
            throw new AssertionError();
        }
        C1222Nt c1222Nt = new C1222Nt();
        for (String str : strArr) {
            c1222Nt.a(str);
        }
        this.a.a(Marker.DESUGARED_LIBRARY_IDENTIFIERS, c1222Nt);
    }

    public boolean d() {
        return this.a.a(Marker.HAS_CHECKSUMS).a();
    }

    public final U a(CompilationMode compilationMode) {
        if (!f && this.a.b(Marker.COMPILATION_MODE)) {
            throw new AssertionError();
        }
        this.a.a(Marker.COMPILATION_MODE, compilationMode.toString().toLowerCase());
        return this;
    }

    public final void c(String str) {
        if (!f && this.a.b(Marker.SHA1)) {
            throw new AssertionError();
        }
        this.a.a(Marker.SHA1, str);
    }

    public String a() {
        if (this.a.b(Marker.BACKEND)) {
            return this.a.a(Marker.BACKEND).f();
        }
        return (k() ? a.DEX.name() : "CF").toLowerCase();
    }

    public final U a(a aVar) {
        if (!f && this.a.b(Marker.BACKEND)) {
            throw new AssertionError();
        }
        this.a.a(Marker.BACKEND, aVar.name().toLowerCase());
        return this;
    }

    public final U a(boolean z) {
        if (!f && this.a.b(Marker.HAS_CHECKSUMS)) {
            throw new AssertionError();
        }
        this.a.a(Marker.HAS_CHECKSUMS, Boolean.valueOf(z));
        return this;
    }

    public final void a(String str) {
        if (!f && this.a.b(Marker.PG_MAP_ID)) {
            throw new AssertionError();
        }
        this.a.a(Marker.PG_MAP_ID, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1333Rt c1333Rt, Map.Entry entry) {
        c1333Rt.a((String) entry.getKey(), (AbstractC1249Ot) entry.getValue());
    }

    public static U a(C0759u2 c0759u2) {
        if (!a(c0759u2.e)) {
            return null;
        }
        String string = c0759u2.toString();
        String str = c;
        if (string.startsWith(str)) {
            b bVar = b.D8;
            try {
                AbstractC1249Ot abstractC1249OtA = C1386Tt.a(string.substring(str.length() - 1));
                if (abstractC1249OtA instanceof C1333Rt) {
                    return new U(bVar, abstractC1249OtA.d());
                }
                return null;
            } catch (C1542Yt unused) {
                return null;
            }
        }
        String str2 = d;
        if (string.startsWith(str2)) {
            b bVar2 = b.R8;
            try {
                AbstractC1249Ot abstractC1249OtA2 = C1386Tt.a(string.substring(str2.length() - 1));
                if (abstractC1249OtA2 instanceof C1333Rt) {
                    return new U(bVar2, abstractC1249OtA2.d());
                }
                return null;
            } catch (C1542Yt unused2) {
                return null;
            }
        }
        String str3 = e;
        if (!string.startsWith(str3)) {
            return null;
        }
        b bVar3 = b.L8;
        try {
            AbstractC1249Ot abstractC1249OtA3 = C1386Tt.a(string.substring(str3.length() - 1));
            if (abstractC1249OtA3 instanceof C1333Rt) {
                return new U(bVar3, abstractC1249OtA3.d());
            }
            return null;
        } catch (C1542Yt unused3) {
            return null;
        }
    }

    public static boolean a(byte[] bArr) {
        return bArr.length > 2 && bArr[0] == 126 && bArr[1] == 126;
    }
}
