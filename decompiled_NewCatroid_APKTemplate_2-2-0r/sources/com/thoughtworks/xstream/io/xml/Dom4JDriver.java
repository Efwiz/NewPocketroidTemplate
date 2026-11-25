package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.File;
import java.io.FilterWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/Dom4JDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Dom4JDriver extends AbstractXmlDriver {
    private DocumentFactory documentFactory;
    private OutputFormat outputFormat;

    public Dom4JDriver() {
        this(new XmlFriendlyNameCoder());
    }

    public Dom4JDriver(NameCoder nameCoder) {
        this(new DocumentFactory(), OutputFormat.createPrettyPrint(), nameCoder);
        this.outputFormat.setTrimText(false);
    }

    public Dom4JDriver(DocumentFactory documentFactory, OutputFormat outputFormat) {
        this(documentFactory, outputFormat, new XmlFriendlyNameCoder());
    }

    public Dom4JDriver(DocumentFactory documentFactory, OutputFormat outputFormat, NameCoder nameCoder) {
        super(nameCoder);
        this.documentFactory = documentFactory;
        this.outputFormat = outputFormat;
    }

    public Dom4JDriver(DocumentFactory documentFactory, OutputFormat outputFormat, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(documentFactory, outputFormat, (NameCoder) xmlFriendlyReplacer);
    }

    public DocumentFactory getDocumentFactory() {
        return this.documentFactory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public OutputFormat getOutputFormat() {
        return this.outputFormat;
    }

    public void setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new Dom4JReader(createReader().read(reader), getNameCoder());
        } catch (DocumentException e) {
            throw new StreamException((Throwable) e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return new Dom4JReader(createReader().read(inputStream), getNameCoder());
        } catch (DocumentException e) {
            throw new StreamException((Throwable) e);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            return new Dom4JReader(createReader().read(url), getNameCoder());
        } catch (DocumentException e) {
            throw new StreamException((Throwable) e);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            return new Dom4JReader(createReader().read(file), getNameCoder());
        } catch (DocumentException e) {
            throw new StreamException((Throwable) e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        final HierarchicalStreamWriter[] hierarchicalStreamWriterArr = new HierarchicalStreamWriter[1];
        hierarchicalStreamWriterArr[0] = new Dom4JXmlWriter(new XMLWriter(new FilterWriter(writer) { // from class: com.thoughtworks.xstream.io.xml.Dom4JDriver.1
            @Override // java.io.FilterWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                hierarchicalStreamWriterArr[0].close();
            }
        }, this.outputFormat), getNameCoder());
        return hierarchicalStreamWriterArr[0];
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        Charset charsetForName = null;
        String encoding = getOutputFormat() != null ? getOutputFormat().getEncoding() : null;
        if (encoding != null && Charset.isSupported(encoding)) {
            charsetForName = Charset.forName(encoding);
        }
        return createWriter(charsetForName != null ? new OutputStreamWriter(outputStream, charsetForName) : new OutputStreamWriter(outputStream));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.dom4j.DocumentException */
    protected SAXReader createReader() throws DocumentException {
        SAXReader sAXReader = new SAXReader();
        try {
            sAXReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            return sAXReader;
        } catch (SAXException e) {
            throw new DocumentException("Cannot disable DOCTYPE processing", e);
        }
    }
}
