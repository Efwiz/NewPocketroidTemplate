package org.xmlunit.transform;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xmlunit.ConfigurationException;
import org.xmlunit.XMLUnitException;
import org.xmlunit.util.TransformerFactoryConfigurer;

/* JADX INFO: loaded from: classes3.dex */
public final class Transformation {
    private ErrorListener errorListener;
    private TransformerFactory factory;
    private final Properties output = new Properties();
    private final Map<String, Object> params = new HashMap();
    private Source source;
    private Source styleSheet;
    private URIResolver uriResolver;

    public Transformation() {
    }

    public Transformation(Source source) {
        setSource(source);
    }

    public void setSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        this.source = source;
    }

    public void setStylesheet(Source source) {
        this.styleSheet = source;
    }

    public void addOutputProperty(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("value must not be null");
        }
        this.output.setProperty(str, str2);
    }

    public void clearOutputProperties() {
        this.output.clear();
    }

    public void addParameter(String str, Object obj) {
        if (str == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.params.put(str, obj);
    }

    public void clearParameters() {
        this.params.clear();
    }

    public void setFactory(TransformerFactory transformerFactory) {
        this.factory = transformerFactory;
    }

    public void setURIResolver(URIResolver uRIResolver) {
        this.uriResolver = uRIResolver;
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public void transformTo(Result result) {
        Transformer transformerNewTransformer;
        if (this.source == null) {
            throw new IllegalStateException("source must not be null");
        }
        if (result == null) {
            throw new IllegalArgumentException("result must not be null");
        }
        try {
            TransformerFactory transformerFactoryConfigure = this.factory;
            if (transformerFactoryConfigure == null) {
                transformerFactoryConfigure = TransformerFactoryConfigurer.Default.configure(TransformerFactory.newInstance());
            }
            Source source = this.styleSheet;
            if (source != null) {
                transformerNewTransformer = transformerFactoryConfigure.newTransformer(source);
            } else {
                transformerNewTransformer = transformerFactoryConfigure.newTransformer();
            }
            URIResolver uRIResolver = this.uriResolver;
            if (uRIResolver != null) {
                transformerNewTransformer.setURIResolver(uRIResolver);
            }
            ErrorListener errorListener = this.errorListener;
            if (errorListener != null) {
                transformerNewTransformer.setErrorListener(errorListener);
            }
            transformerNewTransformer.setOutputProperties(this.output);
            for (Map.Entry<String, Object> entry : this.params.entrySet()) {
                transformerNewTransformer.setParameter(entry.getKey(), entry.getValue());
            }
            transformerNewTransformer.transform(this.source, result);
        } catch (TransformerConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (TransformerException e2) {
            throw new XMLUnitException(e2);
        }
    }

    public String transformToString() {
        StringWriter stringWriter = new StringWriter();
        transformTo(new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public Document transformToDocument() {
        DOMResult dOMResult = new DOMResult();
        transformTo(dOMResult);
        return (Document) dOMResult.getNode();
    }
}
