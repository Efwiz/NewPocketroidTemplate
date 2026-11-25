package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.DOMInputSource;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.SAXInputSource;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.StAXInputSource;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

/* JADX INFO: loaded from: classes13.dex */
public final class XMLSchemaFactory extends SchemaFactory {
    private static final String JAXP_SOURCE_FEATURE_PREFIX = "http://javax.xml.transform";
    private static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
    private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    private final DOMEntityResolverWrapper fDOMEntityResolverWrapper;
    private ErrorHandler fErrorHandler;
    private final ErrorHandlerWrapper fErrorHandlerWrapper;
    private LSResourceResolver fLSResourceResolver;
    private SecurityManager fSecurityManager;
    private boolean fUseGrammarPoolOnly;
    private final XMLGrammarPoolWrapper fXMLGrammarPoolWrapper;
    private final XMLSchemaLoader fXMLSchemaLoader;

    static class XMLGrammarPoolImplExtension extends XMLGrammarPoolImpl {
        public XMLGrammarPoolImplExtension() {
        }

        public XMLGrammarPoolImplExtension(int i) {
            super(i);
        }

        int getGrammarCount() {
            return this.fGrammarCount;
        }
    }

    static class XMLGrammarPoolWrapper implements XMLGrammarPool {
        private XMLGrammarPool fGrammarPool;

        XMLGrammarPoolWrapper() {
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void cacheGrammars(String str, Grammar[] grammarArr) {
            this.fGrammarPool.cacheGrammars(str, grammarArr);
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void clear() {
            this.fGrammarPool.clear();
        }

        XMLGrammarPool getGrammarPool() {
            return this.fGrammarPool;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void lockPool() {
            this.fGrammarPool.lockPool();
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription) {
            return this.fGrammarPool.retrieveGrammar(xMLGrammarDescription);
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar[] retrieveInitialGrammarSet(String str) {
            return this.fGrammarPool.retrieveInitialGrammarSet(str);
        }

        void setGrammarPool(XMLGrammarPool xMLGrammarPool) {
            this.fGrammarPool = xMLGrammarPool;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void unlockPool() {
            this.fGrammarPool.unlockPool();
        }
    }

    public XMLSchemaFactory() {
        XMLSchemaLoader xMLSchemaLoader = new XMLSchemaLoader();
        this.fXMLSchemaLoader = xMLSchemaLoader;
        ErrorHandlerWrapper errorHandlerWrapper = new ErrorHandlerWrapper(DraconianErrorHandler.getInstance());
        this.fErrorHandlerWrapper = errorHandlerWrapper;
        DOMEntityResolverWrapper dOMEntityResolverWrapper = new DOMEntityResolverWrapper();
        this.fDOMEntityResolverWrapper = dOMEntityResolverWrapper;
        XMLGrammarPoolWrapper xMLGrammarPoolWrapper = new XMLGrammarPoolWrapper();
        this.fXMLGrammarPoolWrapper = xMLGrammarPoolWrapper;
        xMLSchemaLoader.setFeature(SCHEMA_FULL_CHECKING, true);
        xMLSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", xMLGrammarPoolWrapper);
        xMLSchemaLoader.setEntityResolver(dOMEntityResolverWrapper);
        xMLSchemaLoader.setErrorHandler(errorHandlerWrapper);
        this.fUseGrammarPoolOnly = true;
    }

    private void propagateFeatures(AbstractXMLSchema abstractXMLSchema) {
        abstractXMLSchema.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, this.fSecurityManager != null);
        String[] recognizedFeatures = this.fXMLSchemaLoader.getRecognizedFeatures();
        for (int i = 0; i < recognizedFeatures.length; i++) {
            abstractXMLSchema.setFeature(recognizedFeatures[i], this.fXMLSchemaLoader.getFeature(recognizedFeatures[i]));
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public ErrorHandler getErrorHandler() {
        return this.fErrorHandler;
    }

    @Override // javax.xml.validation.SchemaFactory
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", null));
        }
        if (str.startsWith(JAXP_SOURCE_FEATURE_PREFIX) && (str.equals(StreamSource.FEATURE) || str.equals(SAXSource.FEATURE) || str.equals(DOMSource.FEATURE) || str.equals(StAXSource.FEATURE))) {
            return true;
        }
        if (str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            return this.fSecurityManager != null;
        }
        if (str.equals(USE_GRAMMAR_POOL_ONLY)) {
            return this.fUseGrammarPoolOnly;
        }
        try {
            return this.fXMLSchemaLoader.getFeature(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", null));
        }
        if (str.equals(SECURITY_MANAGER)) {
            return this.fSecurityManager;
        }
        if (str.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{str}));
        }
        try {
            return this.fXMLSchemaLoader.getProperty(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public LSResourceResolver getResourceResolver() {
        return this.fLSResourceResolver;
    }

    @Override // javax.xml.validation.SchemaFactory
    public boolean isSchemaLanguageSupported(String str) {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageNull", null));
        }
        if (str.length() != 0) {
            return str.equals("http://www.w3.org/2001/XMLSchema") || str.equals(Constants.W3C_XML_SCHEMA10_NS_URI);
        }
        throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageLengthZero", null));
    }

    @Override // javax.xml.validation.SchemaFactory
    public Schema newSchema() throws SAXException {
        WeakReferenceXMLSchema weakReferenceXMLSchema = new WeakReferenceXMLSchema();
        propagateFeatures(weakReferenceXMLSchema);
        return weakReferenceXMLSchema;
    }

    public Schema newSchema(XMLGrammarPool xMLGrammarPool) throws SAXException {
        XMLSchema xMLSchema = this.fUseGrammarPoolOnly ? new XMLSchema(new ReadOnlyGrammarPool(xMLGrammarPool)) : new XMLSchema(xMLGrammarPool, false);
        propagateFeatures(xMLSchema);
        return xMLSchema;
    }

    @Override // javax.xml.validation.SchemaFactory
    public Schema newSchema(Source[] sourceArr) throws SAXException {
        AbstractXMLSchema xMLSchema;
        XMLGrammarPoolImplExtension xMLGrammarPoolImplExtension = new XMLGrammarPoolImplExtension();
        this.fXMLGrammarPoolWrapper.setGrammarPool(xMLGrammarPoolImplExtension);
        XMLInputSource[] xMLInputSourceArr = new XMLInputSource[sourceArr.length];
        for (int i = 0; i < sourceArr.length; i++) {
            Source source = sourceArr[i];
            if (source instanceof StreamSource) {
                StreamSource streamSource = (StreamSource) source;
                String publicId = streamSource.getPublicId();
                String systemId = streamSource.getSystemId();
                InputStream inputStream = streamSource.getInputStream();
                Reader reader = streamSource.getReader();
                XMLInputSource xMLInputSource = new XMLInputSource(publicId, systemId, null);
                xMLInputSource.setByteStream(inputStream);
                xMLInputSource.setCharacterStream(reader);
                xMLInputSourceArr[i] = xMLInputSource;
            } else if (source instanceof SAXSource) {
                SAXSource sAXSource = (SAXSource) source;
                InputSource inputSource = sAXSource.getInputSource();
                if (inputSource == null) {
                    throw new SAXException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SAXSourceNullInputSource", null));
                }
                xMLInputSourceArr[i] = new SAXInputSource(sAXSource.getXMLReader(), inputSource);
            } else if (source instanceof DOMSource) {
                DOMSource dOMSource = (DOMSource) source;
                xMLInputSourceArr[i] = new DOMInputSource(dOMSource.getNode(), dOMSource.getSystemId());
            } else {
                if (!(source instanceof StAXSource)) {
                    if (source == null) {
                        throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaSourceArrayMemberNull", null));
                    }
                    throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaFactorySourceUnrecognized", new Object[]{source.getClass().getName()}));
                }
                StAXSource stAXSource = (StAXSource) source;
                XMLEventReader xMLEventReader = stAXSource.getXMLEventReader();
                if (xMLEventReader != null) {
                    xMLInputSourceArr[i] = new StAXInputSource(xMLEventReader);
                } else {
                    xMLInputSourceArr[i] = new StAXInputSource(stAXSource.getXMLStreamReader());
                }
            }
        }
        try {
            this.fXMLSchemaLoader.loadGrammar(xMLInputSourceArr);
            this.fXMLGrammarPoolWrapper.setGrammarPool(null);
            int grammarCount = xMLGrammarPoolImplExtension.getGrammarCount();
            if (!this.fUseGrammarPoolOnly) {
                xMLSchema = new XMLSchema(new ReadOnlyGrammarPool(xMLGrammarPoolImplExtension), false);
            } else if (grammarCount > 1) {
                xMLSchema = new XMLSchema(new ReadOnlyGrammarPool(xMLGrammarPoolImplExtension));
            } else {
                xMLSchema = grammarCount == 1 ? new SimpleXMLSchema(xMLGrammarPoolImplExtension.retrieveInitialGrammarSet("http://www.w3.org/2001/XMLSchema")[0]) : new EmptyXMLSchema();
            }
            propagateFeatures(xMLSchema);
            return xMLSchema;
        } catch (IOException e) {
            SAXParseException sAXParseException = new SAXParseException(e.getMessage(), null, e);
            ErrorHandler errorHandler = this.fErrorHandler;
            if (errorHandler == null) {
                throw sAXParseException;
            }
            errorHandler.error(sAXParseException);
            throw sAXParseException;
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.fErrorHandler = errorHandler;
        ErrorHandlerWrapper errorHandlerWrapper = this.fErrorHandlerWrapper;
        if (errorHandler == null) {
            errorHandler = DraconianErrorHandler.getInstance();
        }
        errorHandlerWrapper.setErrorHandler(errorHandler);
        this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
    }

    @Override // javax.xml.validation.SchemaFactory
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", null));
        }
        if (str.startsWith(JAXP_SOURCE_FEATURE_PREFIX) && (str.equals(StreamSource.FEATURE) || str.equals(SAXSource.FEATURE) || str.equals(DOMSource.FEATURE) || str.equals(StAXSource.FEATURE))) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-read-only", new Object[]{str}));
        }
        if (str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            SecurityManager securityManager = z ? new SecurityManager() : null;
            this.fSecurityManager = securityManager;
            this.fXMLSchemaLoader.setProperty(SECURITY_MANAGER, securityManager);
        } else {
            if (str.equals(USE_GRAMMAR_POOL_ONLY)) {
                this.fUseGrammarPoolOnly = z;
                return;
            }
            try {
                this.fXMLSchemaLoader.setFeature(str, z);
            } catch (XMLConfigurationException e) {
                String identifier = e.getIdentifier();
                if (e.getType() != 0) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[]{identifier}));
                }
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", null));
        }
        if (str.equals(SECURITY_MANAGER)) {
            SecurityManager securityManager = (SecurityManager) obj;
            this.fSecurityManager = securityManager;
            this.fXMLSchemaLoader.setProperty(SECURITY_MANAGER, securityManager);
        } else {
            if (str.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{str}));
            }
            try {
                this.fXMLSchemaLoader.setProperty(str, obj);
            } catch (XMLConfigurationException e) {
                String identifier = e.getIdentifier();
                if (e.getType() != 0) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{identifier}));
                }
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
        }
    }

    @Override // javax.xml.validation.SchemaFactory
    public void setResourceResolver(LSResourceResolver lSResourceResolver) {
        this.fLSResourceResolver = lSResourceResolver;
        this.fDOMEntityResolverWrapper.setEntityResolver(lSResourceResolver);
        this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
    }
}
