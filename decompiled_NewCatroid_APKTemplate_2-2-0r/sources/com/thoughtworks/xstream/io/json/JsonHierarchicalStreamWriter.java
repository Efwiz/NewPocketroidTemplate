package com.thoughtworks.xstream.io.json;

import io.netty.util.internal.StringUtil;
import java.io.Writer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JsonHierarchicalStreamWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JsonHierarchicalStreamWriter extends JsonWriter {
    public JsonHierarchicalStreamWriter(Writer writer, char[] cArr, String str) {
        super(writer, cArr, str);
    }

    public JsonHierarchicalStreamWriter(Writer writer, char[] cArr) {
        this(writer, cArr, "\n");
    }

    public JsonHierarchicalStreamWriter(Writer writer, String str, String str2) {
        this(writer, str.toCharArray(), str2);
    }

    public JsonHierarchicalStreamWriter(Writer writer, String str) {
        this(writer, str.toCharArray());
    }

    public JsonHierarchicalStreamWriter(Writer writer) {
        this(writer, new char[]{StringUtil.SPACE, StringUtil.SPACE});
    }
}
