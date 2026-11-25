package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.inspector.BooleanValueInspector;
import com.android.tools.r8.inspector.ByteValueInspector;
import com.android.tools.r8.inspector.CharValueInspector;
import com.android.tools.r8.inspector.DoubleValueInspector;
import com.android.tools.r8.inspector.FloatValueInspector;
import com.android.tools.r8.inspector.IntValueInspector;
import com.android.tools.r8.inspector.LongValueInspector;
import com.android.tools.r8.inspector.ShortValueInspector;
import com.android.tools.r8.inspector.StringValueInspector;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W10 implements BooleanValueInspector, ByteValueInspector, CharValueInspector, ShortValueInspector, IntValueInspector, LongValueInspector, FloatValueInspector, DoubleValueInspector, StringValueInspector {
    private final com.android.tools.r8.graph.B2 a;
    private final C0767v2 b;

    public W10(com.android.tools.r8.graph.B2 b2, C0767v2 c0767v2) {
        this.a = b2;
        this.b = c0767v2;
    }

    private static void a(boolean z) {
        if (!z) {
            throw new IllegalStateException("Invalid call on ValueInspector");
        }
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final BooleanValueInspector asBooleanValue() {
        if (this.b.N0()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final ByteValueInspector asByteValue() {
        if (this.b.e.w0() == 'B') {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final CharValueInspector asCharValue() {
        if (this.b.e.w0() == 'C') {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final DoubleValueInspector asDoubleValue() {
        if (this.b.P0()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final FloatValueInspector asFloatValue() {
        if (this.b.Q0()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final IntValueInspector asIntValue() {
        if (this.b.R0()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final LongValueInspector asLongValue() {
        if (this.b.T0()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final ShortValueInspector asShortValue() {
        if (this.b.e.w0() == 'S') {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final StringValueInspector asStringValue() {
        if (isStringValue()) {
            return this;
        }
        return null;
    }

    @Override // com.android.tools.r8.inspector.BooleanValueInspector
    public final boolean getBooleanValue() {
        a(this.b.N0());
        return this.a.w0().k1();
    }

    @Override // com.android.tools.r8.inspector.ByteValueInspector
    public final byte getByteValue() {
        a(this.b.e.w0() == 'B');
        return this.a.x0().k1();
    }

    @Override // com.android.tools.r8.inspector.CharValueInspector
    public final char getCharValue() {
        a(this.b.e.w0() == 'C');
        return this.a.y0().k1();
    }

    @Override // com.android.tools.r8.inspector.DoubleValueInspector
    public final double getDoubleValue() {
        a(this.b.P0());
        return this.a.z0().k1();
    }

    @Override // com.android.tools.r8.inspector.FloatValueInspector
    public final float getFloatValue() {
        a(this.b.Q0());
        return this.a.C0().k1();
    }

    @Override // com.android.tools.r8.inspector.IntValueInspector
    public final int getIntValue() {
        a(this.b.R0());
        return this.a.D0().b;
    }

    @Override // com.android.tools.r8.inspector.LongValueInspector
    public final long getLongValue() {
        a(this.b.T0());
        return this.a.E0().k1();
    }

    @Override // com.android.tools.r8.inspector.ShortValueInspector
    public final short getShortValue() {
        a(this.b.e.w0() == 'S');
        return this.a.J0().k1();
    }

    @Override // com.android.tools.r8.inspector.StringValueInspector
    public final String getStringValue() {
        a(isStringValue());
        return ((C0759u2) this.a.K0().j1()).toString();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final TypeReference getTypeReference() {
        return Reference.typeFromDescriptor(this.b.b1());
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isBooleanValue() {
        return this.b.N0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isByteValue() {
        return this.b.e.w0() == 'B';
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isCharValue() {
        return this.b.e.w0() == 'C';
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isDoubleValue() {
        return this.b.P0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isFloatValue() {
        return this.b.Q0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isIntValue() {
        return this.b.R0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isLongValue() {
        return this.b.T0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isPrimitive() {
        return this.b.W0();
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isShortValue() {
        return this.b.e.w0() == 'S';
    }

    @Override // com.android.tools.r8.inspector.ValueInspector
    public final boolean isStringValue() {
        return this.b.O0() && this.a.e1();
    }
}
