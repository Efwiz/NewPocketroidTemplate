package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileMethodRuleInfo;
import com.android.tools.r8.profile.art.ArtProfileMethodRuleInfoBuilder;
import java.util.function.IntFunction;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3 implements ArtProfileMethodRuleInfo {
    private static final C3[] b = (C3[]) com.android.tools.r8.utils.B.a(new C3[8], new IntFunction() { // from class: com.android.tools.r8.internal.C3$$ExternalSyntheticLambda0
        @Override // java.util.function.IntFunction
        public final Object apply(int i) {
            return C3.m787$r8$lambda$YivG1O26_9XnQ9_fyeRr3vK9KI(i);
        }
    });
    private final int a;

    /* JADX INFO: renamed from: $r8$lambda$YivG1O26_9-XnQ9_fyeRr3vK9KI, reason: not valid java name */
    public static /* synthetic */ C3 m787$r8$lambda$YivG1O26_9XnQ9_fyeRr3vK9KI(int i) {
        return new C3(i);
    }

    private C3(int i) {
        this.a = i;
    }

    public static a a() {
        return new a();
    }

    public static C3 b() {
        return b[0];
    }

    public final int c() {
        return this.a;
    }

    public final boolean d() {
        return this.a == 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((C3) obj).a;
    }

    public final int hashCode() {
        return this.a;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfo
    public boolean isHot() {
        return B3.a(this.a);
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfo
    public boolean isPostStartup() {
        return B3.b(this.a);
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfo
    public boolean isStartup() {
        return B3.c(this.a);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (isHot()) {
            sb.append('H');
        }
        if (isStartup()) {
            sb.append(Util.C_SHORT);
        }
        if (isPostStartup()) {
            sb.append('P');
        }
        return sb.toString();
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements ArtProfileMethodRuleInfoBuilder {
        static final /* synthetic */ boolean b = true;
        private int a;

        public final void a(C3 c3) {
            this.a = c3.a | this.a;
        }

        final void b() {
            this.a = 0;
        }

        public final int c() {
            return this.a;
        }

        public a d() {
            this.a = B3.a(this.a, true);
            return this;
        }

        public a e() {
            this.a = B3.b(this.a, true);
            return this;
        }

        public a f() {
            this.a = B3.c(this.a, true);
            return this;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfoBuilder
        public final ArtProfileMethodRuleInfoBuilder setIsHot(boolean z) {
            this.a = B3.a(this.a, z);
            return this;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfoBuilder
        public final ArtProfileMethodRuleInfoBuilder setIsPostStartup(boolean z) {
            this.a = B3.b(this.a, z);
            return this;
        }

        @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleInfoBuilder
        public final ArtProfileMethodRuleInfoBuilder setIsStartup(boolean z) {
            this.a = B3.c(this.a, z);
            return this;
        }

        public final void a(A3.a aVar) {
            this.a = aVar.f().a | this.a;
        }

        public C3 a() {
            boolean z = b;
            if (!z && this.a < 0) {
                throw new AssertionError();
            }
            if (z || this.a < C3.b.length) {
                return C3.b[this.a];
            }
            throw new AssertionError();
        }
    }
}
