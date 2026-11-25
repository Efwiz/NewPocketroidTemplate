package com.thoughtworks.xstream.io.xml;

import org.jetbrains.kotlin.name.JvmNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XmlFriendlyReplacer.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XmlFriendlyReplacer extends XmlFriendlyNameCoder {
    public XmlFriendlyReplacer() {
        this("_-", JvmNames.MULTIFILE_PART_NAME_DELIMITER);
    }

    public XmlFriendlyReplacer(String str, String str2) {
        super(str, str2);
    }

    public String escapeName(String str) {
        return super.encodeNode(str);
    }

    public String unescapeName(String str) {
        return super.decodeNode(str);
    }
}
