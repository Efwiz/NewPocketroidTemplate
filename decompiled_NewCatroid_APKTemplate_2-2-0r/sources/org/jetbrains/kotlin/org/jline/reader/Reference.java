package org.jetbrains.kotlin.org.jline.reader;

/* JADX INFO: loaded from: classes4.dex */
public class Reference implements Binding {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f935name;

    public Reference(String str) {
        this.f935name = str;
    }

    public String name() {
        return this.f935name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.f935name.equals(((Reference) obj).f935name);
    }

    public int hashCode() {
        return this.f935name.hashCode();
    }

    public String toString() {
        return "Reference[" + this.f935name + ']';
    }
}
