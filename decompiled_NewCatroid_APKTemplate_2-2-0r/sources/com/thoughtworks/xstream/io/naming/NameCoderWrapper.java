package com.thoughtworks.xstream.io.naming;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/naming/NameCoderWrapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NameCoderWrapper implements NameCoder {
    private final NameCoder wrapped;

    public NameCoderWrapper(NameCoder nameCoder) {
        this.wrapped = nameCoder;
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeAttribute(String str) {
        return this.wrapped.decodeAttribute(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String decodeNode(String str) {
        return this.wrapped.decodeNode(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeAttribute(String str) {
        return this.wrapped.encodeAttribute(str);
    }

    @Override // com.thoughtworks.xstream.io.naming.NameCoder
    public String encodeNode(String str) {
        return this.wrapped.encodeNode(str);
    }
}
