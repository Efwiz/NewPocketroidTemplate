package com.android.tools.r8.inspector;

import com.android.tools.r8.references.TypeReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ValueInspector {
    BooleanValueInspector asBooleanValue();

    ByteValueInspector asByteValue();

    CharValueInspector asCharValue();

    DoubleValueInspector asDoubleValue();

    FloatValueInspector asFloatValue();

    IntValueInspector asIntValue();

    LongValueInspector asLongValue();

    ShortValueInspector asShortValue();

    StringValueInspector asStringValue();

    TypeReference getTypeReference();

    boolean isBooleanValue();

    boolean isByteValue();

    boolean isCharValue();

    boolean isDoubleValue();

    boolean isFloatValue();

    boolean isIntValue();

    boolean isLongValue();

    boolean isPrimitive();

    boolean isShortValue();

    boolean isStringValue();
}
