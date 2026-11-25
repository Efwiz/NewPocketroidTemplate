package com.reandroid.xml;

import com.reandroid.common.Namespace;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class XMLNamespace implements Namespace, Cloneable {
    private String prefix;
    private String uri;

    public XMLNamespace(String str, String str2) {
        this.uri = str;
        this.prefix = str2;
    }

    XMLNamespace newCopy(XMLNode xMLNode) {
        XMLNamespace xMLNamespace = new XMLNamespace(getUri(), getPrefix());
        if (xMLNode instanceof XMLElement) {
            ((XMLElement) xMLNode).addNamespace(xMLNamespace);
        }
        return xMLNamespace;
    }

    @Override // com.reandroid.common.Namespace
    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    @Override // com.reandroid.common.Namespace
    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public boolean isEqual(String str, String str2) {
        return Objects.equals(str, getUri()) && Objects.equals(str2, getPrefix());
    }

    static boolean looksNamespace(String str, String str2) {
        return str2 != null && str.startsWith("xmlns:");
    }
}
