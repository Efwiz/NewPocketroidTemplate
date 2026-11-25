package com.thoughtworks.xstream.io;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/HierarchicalStreamWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface HierarchicalStreamWriter {
    void addAttribute(String str, String str2);

    void close();

    void endNode();

    void flush();

    void setValue(String str);

    void startNode(String str);

    HierarchicalStreamWriter underlyingWriter();
}
