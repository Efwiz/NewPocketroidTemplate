package org.jetbrains.kotlin.org.jline.reader;

/* JADX INFO: loaded from: classes4.dex */
public class Macro implements Binding {
    private final String sequence;

    public String getSequence() {
        return this.sequence;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.sequence.equals(((Macro) obj).sequence);
    }

    public int hashCode() {
        return this.sequence.hashCode();
    }

    public String toString() {
        return "Macro[" + this.sequence + ']';
    }
}
