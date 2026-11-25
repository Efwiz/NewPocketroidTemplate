package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3467yx {

    /* JADX INFO: renamed from: com.android.tools.r8.internal.yx$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a extends AbstractC3467yx {
        private final List a;

        public a(ArrayList arrayList) {
            super(0);
            this.a = arrayList;
        }

        public final List<AbstractC3467yx> a() {
            return this.a;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && AbstractC0936Ds.a(this.a, ((a) obj).a);
        }

        public final int hashCode() {
            return this.a.hashCode();
        }

        public final String toString() {
            return "ArrayValue(elements=" + this.a + ')';
        }
    }

    public /* synthetic */ AbstractC3467yx(int i) {
        this();
    }

    private AbstractC3467yx() {
    }
}
