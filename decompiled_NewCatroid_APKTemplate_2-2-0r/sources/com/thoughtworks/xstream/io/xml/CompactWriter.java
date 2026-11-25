package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.Writer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/CompactWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CompactWriter extends PrettyPrintWriter {
    @Override // com.thoughtworks.xstream.io.xml.PrettyPrintWriter
    protected void endOfLine() {
    }

    public CompactWriter(Writer writer) {
        super(writer);
    }

    public CompactWriter(Writer writer, int i) {
        super(writer, i);
    }

    public CompactWriter(Writer writer, NameCoder nameCoder) {
        super(writer, nameCoder);
    }

    public CompactWriter(Writer writer, int i, NameCoder nameCoder) {
        super(writer, i, nameCoder);
    }

    public CompactWriter(Writer writer, XmlFriendlyReplacer xmlFriendlyReplacer) {
        super(writer, xmlFriendlyReplacer);
    }

    public CompactWriter(Writer writer, int i, XmlFriendlyReplacer xmlFriendlyReplacer) {
        super(writer, i, xmlFriendlyReplacer);
    }
}
