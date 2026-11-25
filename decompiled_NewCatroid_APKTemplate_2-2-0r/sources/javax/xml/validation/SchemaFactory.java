package javax.xml.validation;

import java.io.File;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SchemaFactory {
    static /* synthetic */ Class class$javax$xml$validation$SchemaFactory;

    protected SchemaFactory() {
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static final SchemaFactory newInstance(String str) {
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (contextClassLoader == null) {
            Class clsClass$ = class$javax$xml$validation$SchemaFactory;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.xml.validation.SchemaFactory");
                class$javax$xml$validation$SchemaFactory = clsClass$;
            }
            contextClassLoader = clsClass$.getClassLoader();
        }
        SchemaFactory schemaFactoryNewFactory = new SchemaFactoryFinder(contextClassLoader).newFactory(str);
        if (schemaFactoryNewFactory != null) {
            return schemaFactoryNewFactory;
        }
        throw new IllegalArgumentException(str);
    }

    public static SchemaFactory newInstance(String str, String str2, ClassLoader classLoader) {
        str.getClass();
        if (str2 == null) {
            throw new IllegalArgumentException("factoryClassName cannot be null.");
        }
        if (classLoader == null) {
            classLoader = SecuritySupport.getContextClassLoader();
        }
        SchemaFactory schemaFactoryCreateInstance = new SchemaFactoryFinder(classLoader).createInstance(str2);
        if (schemaFactoryCreateInstance == null || !schemaFactoryCreateInstance.isSchemaLanguageSupported(str)) {
            throw new IllegalArgumentException(str);
        }
        return schemaFactoryCreateInstance;
    }

    public abstract ErrorHandler getErrorHandler();

    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(str);
    }

    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(str);
    }

    public abstract LSResourceResolver getResourceResolver();

    public abstract boolean isSchemaLanguageSupported(String str);

    public abstract Schema newSchema() throws SAXException;

    public Schema newSchema(File file) throws SAXException {
        return newSchema(new StreamSource(file));
    }

    public Schema newSchema(URL url) throws SAXException {
        return newSchema(new StreamSource(url.toExternalForm()));
    }

    public Schema newSchema(Source source) throws SAXException {
        return newSchema(new Source[]{source});
    }

    public abstract Schema newSchema(Source[] sourceArr) throws SAXException;

    public abstract void setErrorHandler(ErrorHandler errorHandler);

    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str != null) {
            throw new SAXNotRecognizedException(str);
        }
        throw new NullPointerException("the name parameter is null");
    }

    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str != null) {
            throw new SAXNotRecognizedException(str);
        }
        throw new NullPointerException("the name parameter is null");
    }

    public abstract void setResourceResolver(LSResourceResolver lSResourceResolver);
}
