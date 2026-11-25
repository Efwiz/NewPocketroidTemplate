package com.reandroid.xml;

import com.reandroid.xml.kxml2.KXmlSerializer;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/* JADX INFO: loaded from: classes11.dex */
public class CloseableSerializer extends KXmlSerializer implements Closeable {
    private OutputStream outputStream;
    private Writer writer;

    @Override // com.reandroid.xml.kxml2.KXmlSerializer, org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) {
        super.setOutput(writer);
        this.writer = writer;
    }

    @Override // com.reandroid.xml.kxml2.KXmlSerializer, org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IOException {
        super.setOutput(outputStream, str);
        this.outputStream = outputStream;
    }

    @Override // com.reandroid.xml.kxml2.KXmlSerializer, org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException {
        super.endDocument();
        close();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.writer != null) {
            flush();
            this.writer.close();
            this.writer = null;
        }
        OutputStream outputStream = this.outputStream;
        if (outputStream != null) {
            outputStream.close();
            this.outputStream = null;
        }
    }
}
