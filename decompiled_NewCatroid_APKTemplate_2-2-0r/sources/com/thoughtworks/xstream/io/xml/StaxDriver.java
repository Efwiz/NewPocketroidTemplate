package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.ReaderWrapper;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/StaxDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class StaxDriver extends AbstractXmlDriver {
    private XMLInputFactory inputFactory;
    private XMLOutputFactory outputFactory;
    private QNameMap qnameMap;

    public StaxDriver() {
        this(new QNameMap());
    }

    public StaxDriver(QNameMap qNameMap) {
        this(qNameMap, new XmlFriendlyNameCoder());
    }

    public StaxDriver(QNameMap qNameMap, NameCoder nameCoder) {
        super(nameCoder);
        this.qnameMap = qNameMap;
    }

    public StaxDriver(NameCoder nameCoder) {
        this(new QNameMap(), nameCoder);
    }

    public StaxDriver(QNameMap qNameMap, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(qNameMap, (NameCoder) xmlFriendlyReplacer);
    }

    public StaxDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(new QNameMap(), (NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return createStaxReader(createParser(reader));
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return createStaxReader(createParser(inputStream));
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            final InputStream inputStreamOpenStream = url.openStream();
            return new ReaderWrapper(createStaxReader(createParser(new StreamSource(inputStreamOpenStream, url.toExternalForm())))) { // from class: com.thoughtworks.xstream.io.xml.StaxDriver.1
                @Override // com.thoughtworks.xstream.io.ReaderWrapper, com.thoughtworks.xstream.io.HierarchicalStreamReader
                public void close() {
                    super.close();
                    try {
                        inputStreamOpenStream.close();
                    } catch (IOException unused) {
                    }
                }
            };
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (XMLStreamException e2) {
            throw new StreamException(e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            return new ReaderWrapper(createStaxReader(createParser(new StreamSource(fileInputStream, file.toURI().toASCIIString())))) { // from class: com.thoughtworks.xstream.io.xml.StaxDriver.2
                @Override // com.thoughtworks.xstream.io.ReaderWrapper, com.thoughtworks.xstream.io.HierarchicalStreamReader
                public void close() {
                    super.close();
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                }
            };
        } catch (FileNotFoundException e) {
            throw new StreamException(e);
        } catch (XMLStreamException e2) {
            throw new StreamException(e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        try {
            return createStaxWriter(getOutputFactory().createXMLStreamWriter(writer));
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        try {
            return createStaxWriter(getOutputFactory().createXMLStreamWriter(outputStream));
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    public AbstractPullReader createStaxReader(XMLStreamReader xMLStreamReader) {
        return new StaxReader(this.qnameMap, xMLStreamReader, getNameCoder());
    }

    public StaxWriter createStaxWriter(XMLStreamWriter xMLStreamWriter, boolean z) throws XMLStreamException {
        return new StaxWriter(this.qnameMap, xMLStreamWriter, z, isRepairingNamespace(), getNameCoder());
    }

    public StaxWriter createStaxWriter(XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        return createStaxWriter(xMLStreamWriter, true);
    }

    public QNameMap getQnameMap() {
        return this.qnameMap;
    }

    public void setQnameMap(QNameMap qNameMap) {
        this.qnameMap = qNameMap;
    }

    public XMLInputFactory getInputFactory() {
        if (this.inputFactory == null) {
            this.inputFactory = createInputFactory();
        }
        return this.inputFactory;
    }

    public XMLOutputFactory getOutputFactory() {
        if (this.outputFactory == null) {
            this.outputFactory = createOutputFactory();
        }
        return this.outputFactory;
    }

    public boolean isRepairingNamespace() {
        return Boolean.TRUE.equals(getOutputFactory().getProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES));
    }

    public void setRepairingNamespace(boolean z) {
        getOutputFactory().setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, z ? Boolean.TRUE : Boolean.FALSE);
    }

    protected XMLStreamReader createParser(Reader reader) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(reader);
    }

    protected XMLStreamReader createParser(InputStream inputStream) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(inputStream);
    }

    protected XMLStreamReader createParser(Source source) throws XMLStreamException {
        return getInputFactory().createXMLStreamReader(source);
    }

    protected XMLInputFactory createInputFactory() {
        XMLInputFactory xMLInputFactoryNewInstance = XMLInputFactory.newInstance();
        xMLInputFactoryNewInstance.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        return xMLInputFactoryNewInstance;
    }

    protected XMLOutputFactory createOutputFactory() {
        return XMLOutputFactory.newInstance();
    }
}
