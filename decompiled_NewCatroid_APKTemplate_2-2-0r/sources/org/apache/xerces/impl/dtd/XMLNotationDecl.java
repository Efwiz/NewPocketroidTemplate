package org.apache.xerces.impl.dtd;

/* JADX INFO: loaded from: classes13.dex */
public class XMLNotationDecl {
    public String baseSystemId;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f476name;
    public String publicId;
    public String systemId;

    public void clear() {
        this.f476name = null;
        this.publicId = null;
        this.systemId = null;
        this.baseSystemId = null;
    }

    public void setValues(String str, String str2, String str3, String str4) {
        this.f476name = str;
        this.publicId = str2;
        this.systemId = str3;
        this.baseSystemId = str4;
    }
}
