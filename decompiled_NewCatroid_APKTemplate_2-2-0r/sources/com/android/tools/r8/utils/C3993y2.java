package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.utils.y2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3993y2 implements ClassFileResourceProvider {
    private final Map a;

    private C3993y2(Map map) {
        this.a = map;
    }

    public static ClassFileResourceProvider a(String str, byte[] bArr) {
        a aVarA = a();
        aVarA.a(str, bArr);
        return aVarA.a();
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final Set getClassDescriptors() {
        return OU.a((Iterable) this.a.keySet());
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final ProgramResource getProgramResource(String str) {
        byte[] bArr = (byte[]) this.a.get(str);
        if (bArr == null) {
            return null;
        }
        return ProgramResource.fromBytes(new C3998z2(str), ProgramResource.Kind.CF, bArr, Collections.singleton(str));
    }

    public final String toString() {
        return this.a.size() + " preloaded resources";
    }

    public static a a() {
        return new a();
    }

    /* JADX INFO: renamed from: com.android.tools.r8.utils.y2$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        static final /* synthetic */ boolean b = true;
        private HashMap a;

        private a() {
            this.a = new HashMap();
        }

        public a a(String str, byte[] bArr) {
            boolean z = b;
            if (!z && this.a == null) {
                throw new AssertionError();
            }
            if (!z && str == null) {
                throw new AssertionError();
            }
            if (!z && bArr == null) {
                throw new AssertionError();
            }
            if (!z && this.a.containsKey(str)) {
                throw new AssertionError();
            }
            this.a.put(str, bArr);
            return this;
        }

        public C3993y2 a() {
            if (!b && this.a == null) {
                throw new AssertionError();
            }
            C3993y2 c3993y2 = new C3993y2(this.a);
            this.a = null;
            return c3993y2;
        }
    }
}
