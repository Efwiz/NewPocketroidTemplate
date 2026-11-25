package com.thoughtworks.xstream.io.copy;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/copy/HierarchicalStreamCopier.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class HierarchicalStreamCopier {
    public void copy(HierarchicalStreamReader hierarchicalStreamReader, HierarchicalStreamWriter hierarchicalStreamWriter) {
        hierarchicalStreamWriter.startNode(hierarchicalStreamReader.getNodeName());
        int attributeCount = hierarchicalStreamReader.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            hierarchicalStreamWriter.addAttribute(hierarchicalStreamReader.getAttributeName(i), hierarchicalStreamReader.getAttribute(i));
        }
        String value = hierarchicalStreamReader.getValue();
        if (value != null && value.length() > 0) {
            hierarchicalStreamWriter.setValue(value);
        }
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            copy(hierarchicalStreamReader, hierarchicalStreamWriter);
            hierarchicalStreamReader.moveUp();
        }
        hierarchicalStreamWriter.endNode();
    }
}
