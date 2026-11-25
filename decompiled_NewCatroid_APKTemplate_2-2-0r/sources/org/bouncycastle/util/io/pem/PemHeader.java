package org.bouncycastle.util.io.pem;

/* JADX INFO: loaded from: classes13.dex */
public class PemHeader {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f508name;
    private String value;

    public PemHeader(String str, String str2) {
        this.f508name = str;
        this.value = str2;
    }

    private int getHashCode(String str) {
        if (str == null) {
            return 1;
        }
        return str.hashCode();
    }

    private boolean isEqual(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PemHeader)) {
            return false;
        }
        PemHeader pemHeader = (PemHeader) obj;
        return pemHeader == this || (isEqual(this.f508name, pemHeader.f508name) && isEqual(this.value, pemHeader.value));
    }

    public String getName() {
        return this.f508name;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return getHashCode(this.f508name) + (getHashCode(this.value) * 31);
    }
}
