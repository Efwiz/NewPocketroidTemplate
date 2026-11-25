package com.thoughtworks.xstream.io;

import com.thoughtworks.xstream.core.util.Cloneables;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/AbstractWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractWriter implements ExtendedHierarchicalStreamWriter {
    private NameCoder nameCoder;

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public HierarchicalStreamWriter underlyingWriter() {
        return this;
    }

    protected AbstractWriter() {
        this(new NoNameCoder());
    }

    protected AbstractWriter(NameCoder nameCoder) {
        this.nameCoder = (NameCoder) Cloneables.cloneIfPossible(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        startNode(str);
    }

    public String encodeNode(String str) {
        return this.nameCoder.encodeNode(str);
    }

    public String encodeAttribute(String str) {
        return this.nameCoder.encodeAttribute(str);
    }
}
