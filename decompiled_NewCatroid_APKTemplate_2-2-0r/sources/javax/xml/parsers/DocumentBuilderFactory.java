package javax.xml.parsers;

import javax.xml.validation.Schema;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DocumentBuilderFactory {
    private boolean validating = false;
    private boolean namespaceAware = false;
    private boolean whitespace = false;
    private boolean expandEntityRef = true;
    private boolean ignoreComments = false;
    private boolean coalescing = false;

    protected DocumentBuilderFactory() {
    }

    public static DocumentBuilderFactory newInstance() {
        try {
            return (DocumentBuilderFactory) FactoryFinder.find("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public static DocumentBuilderFactory newInstance(String str, ClassLoader classLoader) {
        if (str == null) {
            throw new FactoryConfigurationError("factoryClassName cannot be null.");
        }
        if (classLoader == null) {
            classLoader = SecuritySupport.getContextClassLoader();
        }
        try {
            return (DocumentBuilderFactory) FactoryFinder.newInstance(str, classLoader, false);
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public abstract Object getAttribute(String str) throws IllegalArgumentException;

    public abstract boolean getFeature(String str) throws ParserConfigurationException;

    public Schema getSchema() {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public boolean isCoalescing() {
        return this.coalescing;
    }

    public boolean isExpandEntityReferences() {
        return this.expandEntityRef;
    }

    public boolean isIgnoringComments() {
        return this.ignoreComments;
    }

    public boolean isIgnoringElementContentWhitespace() {
        return this.whitespace;
    }

    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    public boolean isValidating() {
        return this.validating;
    }

    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public abstract DocumentBuilder newDocumentBuilder() throws ParserConfigurationException;

    public abstract void setAttribute(String str, Object obj) throws IllegalArgumentException;

    public void setCoalescing(boolean z) {
        this.coalescing = z;
    }

    public void setExpandEntityReferences(boolean z) {
        this.expandEntityRef = z;
    }

    public abstract void setFeature(String str, boolean z) throws ParserConfigurationException;

    public void setIgnoringComments(boolean z) {
        this.ignoreComments = z;
    }

    public void setIgnoringElementContentWhitespace(boolean z) {
        this.whitespace = z;
    }

    public void setNamespaceAware(boolean z) {
        this.namespaceAware = z;
    }

    public void setSchema(Schema schema) {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public void setValidating(boolean z) {
        this.validating = z;
    }

    public void setXIncludeAware(boolean z) {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }
}
