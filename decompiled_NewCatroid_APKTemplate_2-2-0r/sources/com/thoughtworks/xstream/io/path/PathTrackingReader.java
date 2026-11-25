package com.thoughtworks.xstream.io.path;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.ReaderWrapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/path/PathTrackingReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PathTrackingReader extends ReaderWrapper {
    private final PathTracker pathTracker;

    public PathTrackingReader(HierarchicalStreamReader hierarchicalStreamReader, PathTracker pathTracker) {
        super(hierarchicalStreamReader);
        this.pathTracker = pathTracker;
        pathTracker.pushElement(getNodeName());
    }

    @Override // com.thoughtworks.xstream.io.ReaderWrapper, com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveDown() {
        super.moveDown();
        this.pathTracker.pushElement(getNodeName());
    }

    @Override // com.thoughtworks.xstream.io.ReaderWrapper, com.thoughtworks.xstream.io.HierarchicalStreamReader
    public void moveUp() {
        super.moveUp();
        this.pathTracker.popElement();
    }

    @Override // com.thoughtworks.xstream.io.ReaderWrapper, com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.ErrorReporter
    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("path", this.pathTracker.getPath().toString());
        super.appendErrors(errorWriter);
    }
}
