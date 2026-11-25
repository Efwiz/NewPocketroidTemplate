package com.reandroid.arsc.coder;

import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ComplexUnit {
    private final int flag;
    private final String symbol;

    public boolean equals(Object obj) {
        return obj == this;
    }

    public abstract ValueType getValueType();

    public boolean isFraction() {
        return false;
    }

    ComplexUnit(int i, String str) {
        this.flag = i;
        this.symbol = str;
    }

    public int getFlag() {
        return this.flag;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public boolean hasPostfix(String str) {
        return str.endsWith(this.symbol);
    }

    public int hashCode() {
        return this.symbol.hashCode();
    }

    public String toString() {
        return getSymbol();
    }
}
