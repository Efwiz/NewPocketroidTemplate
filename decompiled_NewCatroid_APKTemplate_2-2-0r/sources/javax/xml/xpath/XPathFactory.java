package javax.xml.xpath;

/* JADX INFO: loaded from: classes11.dex */
public abstract class XPathFactory {
    public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";
    public static final String DEFAULT_PROPERTY_NAME = "javax.xml.xpath.XPathFactory";
    static /* synthetic */ Class class$javax$xml$xpath$XPathFactory;

    protected XPathFactory() {
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static final XPathFactory newInstance() {
        try {
            return newInstance("http://java.sun.com/jaxp/xpath/dom");
        } catch (XPathFactoryConfigurationException e) {
            throw new RuntimeException(new StringBuffer("XPathFactory#newInstance() failed to create an XPathFactory for the default object model: http://java.sun.com/jaxp/xpath/dom with the XPathFactoryConfigurationException: ").append(e.toString()).toString());
        }
    }

    public static final XPathFactory newInstance(String str) throws XPathFactoryConfigurationException {
        if (str == null) {
            throw new NullPointerException("XPathFactory#newInstance(String uri) cannot be called with uri == null");
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (contextClassLoader == null) {
            Class clsClass$ = class$javax$xml$xpath$XPathFactory;
            if (clsClass$ == null) {
                clsClass$ = class$(DEFAULT_PROPERTY_NAME);
                class$javax$xml$xpath$XPathFactory = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
        }
        XPathFactory xPathFactoryNewFactory = new XPathFactoryFinder(contextClassLoader).newFactory(str);
        if (xPathFactoryNewFactory != null) {
            return xPathFactoryNewFactory;
        }
        throw new XPathFactoryConfigurationException(new StringBuffer("No XPathFctory implementation found for the object model: ").append(str).toString());
    }

    public static XPathFactory newInstance(String str, String str2, ClassLoader classLoader) throws XPathFactoryConfigurationException {
        if (str == null) {
            throw new NullPointerException("XPathFactory#newInstance(String uri) cannot be called with uri == null");
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }
        if (str2 == null) {
            throw new XPathFactoryConfigurationException("factoryClassName cannot be null.");
        }
        if (classLoader == null) {
            classLoader = SecuritySupport.getContextClassLoader();
        }
        XPathFactory xPathFactoryCreateInstance = new XPathFactoryFinder(classLoader).createInstance(str2);
        if (xPathFactoryCreateInstance == null || !xPathFactoryCreateInstance.isObjectModelSupported(str)) {
            throw new XPathFactoryConfigurationException(new StringBuffer("No XPathFctory implementation found for the object model: ").append(str).toString());
        }
        return xPathFactoryCreateInstance;
    }

    public abstract boolean getFeature(String str) throws XPathFactoryConfigurationException;

    public abstract boolean isObjectModelSupported(String str);

    public abstract XPath newXPath();

    public abstract void setFeature(String str, boolean z) throws XPathFactoryConfigurationException;

    public abstract void setXPathFunctionResolver(XPathFunctionResolver xPathFunctionResolver);

    public abstract void setXPathVariableResolver(XPathVariableResolver xPathVariableResolver);
}
