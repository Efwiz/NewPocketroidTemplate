package com.android.tools.r8.shaking;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.i3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3737i3 extends S2 {
    private final EnumC3758l3 r;
    private final C3742j3 s;

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.i3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class a<C extends C3737i3, B extends a<C, B>> extends B2.a<C, B> {
        protected EnumC3758l3 p;
        protected final C3742j3.a q = C3742j3.a();

        protected a() {
        }

        public B a(EnumC3758l3 enumC3758l3) {
            this.p = enumC3758l3;
            return (B) e();
        }
    }

    protected C3737i3(Origin origin, Position position, String str, List list, C3774o2 c3774o2, C3774o2 c3774o3, boolean z, I2 i2, AbstractC3833y2 abstractC3833y2, List list2, G3 g3, boolean z2, List list3, EnumC3758l3 enumC3758l3, C3742j3 c3742j3) {
        super(origin, position, str, list, c3774o2, c3774o3, z, i2, abstractC3833y2, list2, g3, z2, list3);
        this.r = enumC3758l3;
        this.s = c3742j3;
    }

    @Override // com.android.tools.r8.shaking.S2
    final String E() {
        return this.s.toString();
    }

    @Override // com.android.tools.r8.shaking.S2
    String F() {
        return this.r.toString();
    }

    public C3742j3 G() {
        return this.s;
    }

    public EnumC3758l3 H() {
        return this.r;
    }

    @Override // com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    public boolean equals(Object obj) {
        if (!(obj instanceof C3737i3)) {
            return false;
        }
        C3737i3 c3737i3 = (C3737i3) obj;
        if (this.r == c3737i3.r && this.s.equals(c3737i3.s)) {
            return super.equals(c3737i3);
        }
        return false;
    }

    @Override // com.android.tools.r8.shaking.S2, com.android.tools.r8.shaking.B2
    public int hashCode() {
        return ((this.s.hashCode() + (this.r.hashCode() * 3)) * 3) + super.hashCode();
    }
}
