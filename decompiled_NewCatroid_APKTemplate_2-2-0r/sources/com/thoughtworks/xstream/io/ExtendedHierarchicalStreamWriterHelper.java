package com.thoughtworks.xstream.io;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/ExtendedHierarchicalStreamWriterHelper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ExtendedHierarchicalStreamWriterHelper {
    public static void startNode(HierarchicalStreamWriter hierarchicalStreamWriter, String str, Class cls) {
        if (hierarchicalStreamWriter instanceof ExtendedHierarchicalStreamWriter) {
            ((ExtendedHierarchicalStreamWriter) hierarchicalStreamWriter).startNode(str, cls);
        } else {
            hierarchicalStreamWriter.startNode(str);
        }
    }
}
