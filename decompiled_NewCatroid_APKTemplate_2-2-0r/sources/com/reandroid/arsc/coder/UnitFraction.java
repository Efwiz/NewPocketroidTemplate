package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class UnitFraction extends ComplexUnit {
    public static final UnitFraction FRACTION;
    public static final UnitFraction FRACTION_PARENT;
    private static final UnitFraction[] VALUES;

    @Override // com.reandroid.arsc.coder.ComplexUnit
    public boolean isFraction() {
        return true;
    }

    static {
        UnitFraction unitFraction = new UnitFraction(0, "%");
        FRACTION = unitFraction;
        UnitFraction unitFraction2 = new UnitFraction(1, "%p");
        FRACTION_PARENT = unitFraction2;
        VALUES = new UnitFraction[]{unitFraction, unitFraction2};
    }

    UnitFraction(int i, String str) {
        super(i, str);
    }

    @Override // com.reandroid.arsc.coder.ComplexUnit
    public ValueType getValueType() {
        return ValueType.FRACTION;
    }

    public static UnitFraction[] values() {
        return (UnitFraction[]) VALUES.clone();
    }

    public static UnitFraction valueOf(int i) {
        for (UnitFraction unitFraction : VALUES) {
            if (i == unitFraction.getFlag()) {
                return unitFraction;
            }
        }
        return null;
    }

    public static UnitFraction valueOf(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        for (UnitFraction unitFraction : VALUES) {
            if (lowerCase.equals(unitFraction.getSymbol())) {
                return unitFraction;
            }
        }
        return null;
    }

    public static UnitFraction fromPostfix(String str) {
        for (UnitFraction unitFraction : VALUES) {
            if (unitFraction.hasPostfix(str)) {
                return unitFraction;
            }
        }
        return null;
    }
}
