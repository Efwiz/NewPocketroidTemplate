package com.google.android.datatransport;

/* JADX INFO: loaded from: classes9.dex */
public final class Encoding {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f142name;

    public static Encoding of(String str) {
        return new Encoding(str);
    }

    public String getName() {
        return this.f142name;
    }

    private Encoding(String str) {
        if (str == null) {
            throw new NullPointerException("name is null");
        }
        this.f142name = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Encoding) {
            return this.f142name.equals(((Encoding) obj).f142name);
        }
        return false;
    }

    public int hashCode() {
        return this.f142name.hashCode() ^ 1000003;
    }

    public String toString() {
        return "Encoding{name=\"" + this.f142name + "\"}";
    }
}
