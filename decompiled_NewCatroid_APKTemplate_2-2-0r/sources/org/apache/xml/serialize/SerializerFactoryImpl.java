package org.apache.xml.serialize;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.apache.xerces.dom.DOMMessageFormatter;

/* JADX INFO: loaded from: classes13.dex */
final class SerializerFactoryImpl extends SerializerFactory {
    private String _method;

    SerializerFactoryImpl(String str) {
        this._method = str;
        if (!str.equals("xml") && !this._method.equals(Method.HTML) && !this._method.equals(Method.XHTML) && !this._method.equals("text")) {
            throw new IllegalArgumentException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "MethodNotSupported", new Object[]{str}));
        }
    }

    private Serializer getSerializer(OutputFormat outputFormat) {
        if (this._method.equals("xml")) {
            return new XMLSerializer(outputFormat);
        }
        if (this._method.equals(Method.HTML)) {
            return new HTMLSerializer(outputFormat);
        }
        if (this._method.equals(Method.XHTML)) {
            return new XHTMLSerializer(outputFormat);
        }
        if (this._method.equals("text")) {
            return new TextSerializer();
        }
        throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "MethodNotSupported", new Object[]{this._method}));
    }

    @Override // org.apache.xml.serialize.SerializerFactory
    protected String getSupportedMethod() {
        return this._method;
    }

    @Override // org.apache.xml.serialize.SerializerFactory
    public Serializer makeSerializer(OutputStream outputStream, OutputFormat outputFormat) throws UnsupportedEncodingException {
        Serializer serializer = getSerializer(outputFormat);
        serializer.setOutputByteStream(outputStream);
        return serializer;
    }

    @Override // org.apache.xml.serialize.SerializerFactory
    public Serializer makeSerializer(Writer writer, OutputFormat outputFormat) {
        Serializer serializer = getSerializer(outputFormat);
        serializer.setOutputCharStream(writer);
        return serializer;
    }

    @Override // org.apache.xml.serialize.SerializerFactory
    public Serializer makeSerializer(OutputFormat outputFormat) {
        Serializer serializer = getSerializer(outputFormat);
        serializer.setOutputFormat(outputFormat);
        return serializer;
    }
}
