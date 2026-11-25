package com.reandroid.dex.common;

/* JADX INFO: loaded from: classes7.dex */
public class RegisterType {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f237name;
    public static final RegisterType READ = new RegisterType("READ");
    public static final RegisterType WRITE = new RegisterType("WRITE");
    public static final RegisterType RW = new RegisterType("RW");

    public boolean equals(Object obj) {
        return obj == this;
    }

    private RegisterType(String str) {
        this.f237name = str;
    }

    public boolean is(RegisterType registerType) {
        RegisterType registerType2;
        if (registerType == null) {
            return false;
        }
        return registerType == this || registerType == (registerType2 = RW) || this == registerType2;
    }

    public int hashCode() {
        return this.f237name.hashCode();
    }

    public String toString() {
        return this.f237name;
    }
}
