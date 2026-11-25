package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionFieldContext;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.FieldReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class e extends c implements DefinitionFieldContext {
    private final FieldReference b;

    private e(FieldReference fieldReference, Origin origin) {
        super(origin);
        this.b = fieldReference;
    }

    public static a a() {
        return new a();
    }

    @Override // com.android.tools.r8.diagnostic.DefinitionFieldContext
    public final FieldReference getFieldReference() {
        return this.b;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends b<a> {
        static final /* synthetic */ boolean d = true;
        private FieldReference c;

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
        public e a() {
            boolean z = d;
            if (!z) {
                if (!b.b && this.a == null) {
                    throw new AssertionError();
                }
                if (!z && this.c == null) {
                    throw new AssertionError();
                }
            }
            return new e(this.c, this.a);
        }

        public a a(FieldReference fieldReference) {
            this.c = fieldReference;
            return this;
        }
    }
}
