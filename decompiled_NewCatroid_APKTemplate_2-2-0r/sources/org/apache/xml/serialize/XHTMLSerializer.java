package org.apache.xml.serialize;

import java.io.OutputStream;
import java.io.Writer;

/* JADX INFO: loaded from: classes13.dex */
public class XHTMLSerializer extends HTMLSerializer {
    public XHTMLSerializer() {
        super(true, new OutputFormat(Method.XHTML, (String) null, false));
    }

    public XHTMLSerializer(OutputStream outputStream, OutputFormat outputFormat) {
        super(true, outputFormat == null ? new OutputFormat(Method.XHTML, (String) null, false) : outputFormat);
        setOutputByteStream(outputStream);
    }

    public XHTMLSerializer(Writer writer, OutputFormat outputFormat) {
        super(true, outputFormat == null ? new OutputFormat(Method.XHTML, (String) null, false) : outputFormat);
        setOutputCharStream(writer);
    }

    public XHTMLSerializer(OutputFormat outputFormat) {
        super(true, outputFormat == null ? new OutputFormat(Method.XHTML, (String) null, false) : outputFormat);
    }

    @Override // org.apache.xml.serialize.HTMLSerializer, org.apache.xml.serialize.BaseMarkupSerializer, org.apache.xml.serialize.Serializer
    public void setOutputFormat(OutputFormat outputFormat) {
        if (outputFormat == null) {
            outputFormat = new OutputFormat(Method.XHTML, (String) null, false);
        }
        super.setOutputFormat(outputFormat);
    }
}
