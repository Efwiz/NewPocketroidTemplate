package com.reandroid.dex.common;

/* JADX INFO: loaded from: classes7.dex */
public class OperandType {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f235name;
    public static final OperandType NONE = new OperandType("NONE");
    public static final OperandType HEX = new OperandType("HEX");
    public static final OperandType DECIMAL = new OperandType("DECIMAL");
    public static final OperandType LABEL = new OperandType("LABEL");
    public static final OperandType KEY = new OperandType("KEY");

    public boolean equals(Object obj) {
        return obj == this;
    }

    private OperandType(String str) {
        this.f235name = str;
    }

    public int hashCode() {
        return this.f235name.hashCode();
    }

    public String toString() {
        return this.f235name;
    }
}
