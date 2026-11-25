package com.thoughtworks.xstream.io.path;

import com.thoughtworks.xstream.io.AbstractWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.WriterWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/path/PathTrackingWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PathTrackingWriter extends WriterWrapper {
    private final boolean isNameEncoding;
    private final PathTracker pathTracker;

    public PathTrackingWriter(HierarchicalStreamWriter hierarchicalStreamWriter, PathTracker pathTracker) {
        super(hierarchicalStreamWriter);
        this.isNameEncoding = hierarchicalStreamWriter.underlyingWriter() instanceof AbstractWriter;
        this.pathTracker = pathTracker;
    }

    @Override // com.thoughtworks.xstream.io.WriterWrapper, com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        this.pathTracker.pushElement(this.isNameEncoding ? ((AbstractWriter) this.wrapped.underlyingWriter()).encodeNode(str) : str);
        super.startNode(str);
    }

    @Override // com.thoughtworks.xstream.io.WriterWrapper, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        this.pathTracker.pushElement(this.isNameEncoding ? ((AbstractWriter) this.wrapped.underlyingWriter()).encodeNode(str) : str);
        super.startNode(str, cls);
    }

    @Override // com.thoughtworks.xstream.io.WriterWrapper, com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        super.endNode();
        this.pathTracker.popElement();
    }
}
