package org.mozilla.javascript.tools.idswitch;

/* JADX INFO: loaded from: classes6.dex */
public class IdValuePair {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public final String f1073id;
    public final int idLength;
    private int lineNumber;
    public final String value;

    public IdValuePair(String str, String str2) {
        this.idLength = str.length();
        this.f1073id = str;
        this.value = str2;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int i) {
        this.lineNumber = i;
    }
}
