package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionClassContext;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class a extends c implements DefinitionClassContext {
    private final ClassReference b;

    private a(ClassReference classReference, Origin origin) {
        super(origin);
        this.b = classReference;
    }

    public static C0022a a() {
        return new C0022a();
    }

    @Override // com.android.tools.r8.diagnostic.DefinitionClassContext
    public final ClassReference getClassReference() {
        return this.b;
    }

    /* JADX INFO: renamed from: com.android.tools.r8.diagnostic.internal.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class C0022a extends b<C0022a> {
        static final /* synthetic */ boolean d = true;
        private ClassReference c;

        private C0022a() {
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
        public a a() {
            boolean z = d;
            if (!z) {
                if (!b.b && this.a == null) {
                    throw new AssertionError();
                }
                if (!z && this.c == null) {
                    throw new AssertionError();
                }
            }
            return new a(this.c, this.a);
        }

        public C0022a a(ClassReference classReference) {
            this.c = classReference;
            return this;
        }
    }
}
