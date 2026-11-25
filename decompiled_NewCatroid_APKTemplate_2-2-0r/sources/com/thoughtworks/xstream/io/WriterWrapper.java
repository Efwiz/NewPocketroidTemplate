package com.thoughtworks.xstream.io;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/WriterWrapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class WriterWrapper implements ExtendedHierarchicalStreamWriter {
    protected HierarchicalStreamWriter wrapped;

    protected WriterWrapper(HierarchicalStreamWriter hierarchicalStreamWriter) {
        this.wrapped = hierarchicalStreamWriter;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        this.wrapped.startNode(str);
    }

    @Override // com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        ((ExtendedHierarchicalStreamWriter) this.wrapped).startNode(str, cls);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        this.wrapped.endNode();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        this.wrapped.addAttribute(str, str2);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        this.wrapped.setValue(str);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
        this.wrapped.flush();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
        this.wrapped.close();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public HierarchicalStreamWriter underlyingWriter() {
        return this.wrapped.underlyingWriter();
    }
}
