package javax.xml.stream;

import java.io.OutputStream;
import java.io.Writer;
import javax.xml.transform.Result;

/* JADX INFO: loaded from: classes11.dex */
public abstract class XMLOutputFactory {
    private static final String DEFAULT_FACTORY = "com.ctc.wstx.stax.WstxOutputFactory";
    public static final String IS_REPAIRING_NAMESPACES = "javax.xml.stream.isRepairingNamespaces";
    private static final String PROPERTY_NAME = "javax.xml.stream.XMLOutputFactory";

    protected XMLOutputFactory() {
    }

    public static XMLInputFactory newInstance(String str, ClassLoader classLoader) throws FactoryConfigurationError {
        if (classLoader == null) {
            classLoader = SecuritySupport.getContextClassLoader();
        }
        try {
            return (XMLInputFactory) FactoryFinder.find(str, classLoader, "com.ctc.wstx.stax.WstxInputFactory");
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public static XMLOutputFactory newInstance() throws FactoryConfigurationError {
        try {
            return (XMLOutputFactory) FactoryFinder.find(PROPERTY_NAME, DEFAULT_FACTORY);
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public abstract XMLEventWriter createXMLEventWriter(OutputStream outputStream) throws XMLStreamException;

    public abstract XMLEventWriter createXMLEventWriter(OutputStream outputStream, String str) throws XMLStreamException;

    public abstract XMLEventWriter createXMLEventWriter(Writer writer) throws XMLStreamException;

    public abstract XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException;

    public abstract XMLStreamWriter createXMLStreamWriter(OutputStream outputStream) throws XMLStreamException;

    public abstract XMLStreamWriter createXMLStreamWriter(OutputStream outputStream, String str) throws XMLStreamException;

    public abstract XMLStreamWriter createXMLStreamWriter(Writer writer) throws XMLStreamException;

    public abstract XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException;

    public abstract Object getProperty(String str) throws IllegalArgumentException;

    public abstract boolean isPropertySupported(String str);

    public abstract void setProperty(String str, Object obj) throws IllegalArgumentException;
}
