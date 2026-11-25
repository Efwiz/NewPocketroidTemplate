package com.thoughtworks.xstream.io;

import com.thoughtworks.xstream.converters.ErrorReporter;
import com.thoughtworks.xstream.converters.ErrorWriter;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/HierarchicalStreamReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface HierarchicalStreamReader extends ErrorReporter {
    @Override // com.thoughtworks.xstream.converters.ErrorReporter
    void appendErrors(ErrorWriter errorWriter);

    void close();

    String getAttribute(int i);

    String getAttribute(String str);

    int getAttributeCount();

    String getAttributeName(int i);

    Iterator getAttributeNames();

    String getNodeName();

    String getValue();

    boolean hasMoreChildren();

    void moveDown();

    void moveUp();

    HierarchicalStreamReader underlyingReader();
}
