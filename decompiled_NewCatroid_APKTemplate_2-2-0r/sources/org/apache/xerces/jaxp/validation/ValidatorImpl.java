package org.apache.xerces.jaxp.validation;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes13.dex */
final class ValidatorImpl extends Validator implements PSVIProvider {
    private static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
    private static final String JAXP_SOURCE_RESULT_FEATURE_PREFIX = "http://javax.xml.transform";
    private final XMLSchemaValidatorComponentManager fComponentManager;
    private DOMValidatorHelper fDOMValidatorHelper;
    private ValidatorHandlerImpl fSAXValidatorHelper;
    private StAXValidatorHelper fStAXValidatorHelper;
    private StreamValidatorHelper fStreamValidatorHelper;
    private boolean fConfigurationChanged = false;
    private boolean fErrorHandlerChanged = false;
    private boolean fResourceResolverChanged = false;

    public ValidatorImpl(XSGrammarPoolContainer xSGrammarPoolContainer) {
        this.fComponentManager = new XMLSchemaValidatorComponentManager(xSGrammarPoolContainer);
        setErrorHandler(null);
        setResourceResolver(null);
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVI(int i) {
        ValidatorHandlerImpl validatorHandlerImpl = this.fSAXValidatorHelper;
        if (validatorHandlerImpl != null) {
            return validatorHandlerImpl.getAttributePSVI(i);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVIByName(String str, String str2) {
        ValidatorHandlerImpl validatorHandlerImpl = this.fSAXValidatorHelper;
        if (validatorHandlerImpl != null) {
            return validatorHandlerImpl.getAttributePSVIByName(str, str2);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public ElementPSVI getElementPSVI() {
        ValidatorHandlerImpl validatorHandlerImpl = this.fSAXValidatorHelper;
        if (validatorHandlerImpl != null) {
            return validatorHandlerImpl.getElementPSVI();
        }
        return null;
    }

    @Override // javax.xml.validation.Validator
    public ErrorHandler getErrorHandler() {
        return this.fComponentManager.getErrorHandler();
    }

    @Override // javax.xml.validation.Validator
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", null));
        }
        if (str.startsWith(JAXP_SOURCE_RESULT_FEATURE_PREFIX) && (str.equals(StreamSource.FEATURE) || str.equals(SAXSource.FEATURE) || str.equals(DOMSource.FEATURE) || str.equals(StAXSource.FEATURE) || str.equals(StreamResult.FEATURE) || str.equals(SAXResult.FEATURE) || str.equals(DOMResult.FEATURE) || str.equals(StAXResult.FEATURE))) {
            return true;
        }
        try {
            return this.fComponentManager.getFeature(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.Validator
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", null));
        }
        if (CURRENT_ELEMENT_NODE.equals(str)) {
            DOMValidatorHelper dOMValidatorHelper = this.fDOMValidatorHelper;
            if (dOMValidatorHelper != null) {
                return dOMValidatorHelper.getCurrentElement();
            }
            return null;
        }
        try {
            return this.fComponentManager.getProperty(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.Validator
    public LSResourceResolver getResourceResolver() {
        return this.fComponentManager.getResourceResolver();
    }

    @Override // javax.xml.validation.Validator
    public void reset() {
        if (this.fConfigurationChanged) {
            this.fComponentManager.restoreInitialState();
            setErrorHandler(null);
            setResourceResolver(null);
            this.fConfigurationChanged = false;
            this.fErrorHandlerChanged = false;
        } else {
            if (this.fErrorHandlerChanged) {
                setErrorHandler(null);
                this.fErrorHandlerChanged = false;
            }
            if (!this.fResourceResolverChanged) {
                return;
            } else {
                setResourceResolver(null);
            }
        }
        this.fResourceResolverChanged = false;
    }

    @Override // javax.xml.validation.Validator
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.fErrorHandlerChanged = errorHandler != null;
        this.fComponentManager.setErrorHandler(errorHandler);
    }

    @Override // javax.xml.validation.Validator
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", null));
        }
        if (str.startsWith(JAXP_SOURCE_RESULT_FEATURE_PREFIX) && (str.equals(StreamSource.FEATURE) || str.equals(SAXSource.FEATURE) || str.equals(DOMSource.FEATURE) || str.equals(StAXSource.FEATURE) || str.equals(StreamResult.FEATURE) || str.equals(SAXResult.FEATURE) || str.equals(DOMResult.FEATURE) || str.equals(StAXResult.FEATURE))) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-read-only", new Object[]{str}));
        }
        try {
            this.fComponentManager.setFeature(str, z);
            this.fConfigurationChanged = true;
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() != 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.Validator
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", null));
        }
        if (CURRENT_ELEMENT_NODE.equals(str)) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-read-only", new Object[]{str}));
        }
        try {
            this.fComponentManager.setProperty(str, obj);
            this.fConfigurationChanged = true;
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() != 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.Validator
    public void setResourceResolver(LSResourceResolver lSResourceResolver) {
        this.fResourceResolverChanged = lSResourceResolver != null;
        this.fComponentManager.setResourceResolver(lSResourceResolver);
    }

    @Override // javax.xml.validation.Validator
    public void validate(Source source, Result result) throws Throwable {
        if (source instanceof SAXSource) {
            if (this.fSAXValidatorHelper == null) {
                this.fSAXValidatorHelper = new ValidatorHandlerImpl(this.fComponentManager);
            }
            this.fSAXValidatorHelper.validate(source, result);
            return;
        }
        if (source instanceof DOMSource) {
            if (this.fDOMValidatorHelper == null) {
                this.fDOMValidatorHelper = new DOMValidatorHelper(this.fComponentManager);
            }
            this.fDOMValidatorHelper.validate(source, result);
        } else if (source instanceof StAXSource) {
            if (this.fStAXValidatorHelper == null) {
                this.fStAXValidatorHelper = new StAXValidatorHelper(this.fComponentManager);
            }
            this.fStAXValidatorHelper.validate(source, result);
        } else {
            if (!(source instanceof StreamSource)) {
                if (source != null) {
                    throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceNotAccepted", new Object[]{source.getClass().getName()}));
                }
                throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceParameterNull", null));
            }
            if (this.fStreamValidatorHelper == null) {
                this.fStreamValidatorHelper = new StreamValidatorHelper(this.fComponentManager);
            }
            this.fStreamValidatorHelper.validate(source, result);
        }
    }
}
