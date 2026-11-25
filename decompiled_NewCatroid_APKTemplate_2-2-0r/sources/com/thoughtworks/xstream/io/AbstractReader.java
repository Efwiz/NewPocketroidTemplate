package com.thoughtworks.xstream.io;

import com.thoughtworks.xstream.core.util.Cloneables;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/AbstractReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractReader implements ExtendedHierarchicalStreamReader {
    private NameCoder nameCoder;

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamReader
    public HierarchicalStreamReader underlyingReader() {
        return this;
    }

    protected AbstractReader() {
        this(new NoNameCoder());
    }

    protected AbstractReader(NameCoder nameCoder) {
        this.nameCoder = (NameCoder) Cloneables.cloneIfPossible(nameCoder);
    }

    public String decodeNode(String str) {
        return this.nameCoder.decodeNode(str);
    }

    public String decodeAttribute(String str) {
        return this.nameCoder.decodeAttribute(str);
    }

    protected String encodeNode(String str) {
        return this.nameCoder.encodeNode(str);
    }

    protected String encodeAttribute(String str) {
        return this.nameCoder.encodeAttribute(str);
    }

    @Override // com.thoughtworks.xstream.io.ExtendedHierarchicalStreamReader
    public String peekNextChild() {
        throw new UnsupportedOperationException("peekNextChild");
    }
}
