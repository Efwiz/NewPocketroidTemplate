package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionMethodContext;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class f extends c implements DefinitionMethodContext {
    private final MethodReference b;

    private f(MethodReference methodReference, Origin origin) {
        super(origin);
        this.b = methodReference;
    }

    public static a a() {
        return new a();
    }

    @Override // com.android.tools.r8.diagnostic.DefinitionMethodContext
    public final MethodReference getMethodReference() {
        return this.b;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends b<a> {
        static final /* synthetic */ boolean d = true;
        private MethodReference c;

        private a() {
        }

        @Override // com.android.tools.r8.diagnostic.internal.b
        public b a(Origin origin) {
            this.a = origin;
            return b();
        }

        @Override // com.android.tools.r8.diagnostic.internal.b
        final b b() {
            return this;
        }

        @Override // com.android.tools.r8.diagnostic.internal.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public f a() {
            boolean z = d;
            if (!z) {
                if (!b.b && this.a == null) {
                    throw new AssertionError();
                }
                if (!z && this.c == null) {
                    throw new AssertionError();
                }
            }
            return new f(this.c, this.a);
        }

        public a a(MethodReference methodReference) {
            this.c = methodReference;
            return this;
        }
    }
}
