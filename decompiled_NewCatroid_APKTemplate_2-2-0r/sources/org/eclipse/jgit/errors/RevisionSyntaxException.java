package org.eclipse.jgit.errors;

/* JADX INFO: loaded from: classes13.dex */
public class RevisionSyntaxException extends IllegalArgumentException {
    private static final long serialVersionUID = 1;
    private final String revstr;

    public RevisionSyntaxException(String str) {
        this.revstr = str;
    }

    public RevisionSyntaxException(String str, String str2) {
        super(str);
        this.revstr = str2;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return String.valueOf(super.toString()) + ":" + this.revstr;
    }
}
