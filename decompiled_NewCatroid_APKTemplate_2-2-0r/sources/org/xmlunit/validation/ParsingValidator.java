package org.xmlunit.validation;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import org.apache.xerces.jaxp.JAXPConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlunit.ConfigurationException;
import org.xmlunit.XMLUnitException;
import org.xmlunit.util.Convert;

/* JADX INFO: loaded from: classes4.dex */
public class ParsingValidator extends Validator {
    private final String language;
    private String schemaURI;

    public ParsingValidator(String str) {
        if (!"http://www.w3.org/2001/XMLSchema".equals(str) && !"http://www.w3.org/TR/REC-xml".equals(str)) {
            throw new IllegalArgumentException("only DTD and W3C Schema validation are supported by ParsingValidator");
        }
        this.language = str;
    }

    public void setSchemaURI(String str) {
        this.schemaURI = str;
    }

    protected String getSchemaURI() {
        return this.schemaURI;
    }

    @Override // org.xmlunit.validation.Validator
    public ValidationResult validateSchema() {
        throw new XMLUnitException("Schema validation is not supported by ParsingValidator");
    }

    @Override // org.xmlunit.validation.Validator
    public ValidationResult validateInstance(Source source) {
        return validateInstance(source, SAXParserFactory.newInstance());
    }

    public ValidationResult validateInstance(Source source, SAXParserFactory sAXParserFactory) {
        if (sAXParserFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        try {
            try {
                sAXParserFactory.setNamespaceAware(true);
                sAXParserFactory.setValidating(true);
                SAXParser sAXParserNewSAXParser = sAXParserFactory.newSAXParser();
                if ("http://www.w3.org/2001/XMLSchema".equals(this.language)) {
                    sAXParserNewSAXParser.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, "http://www.w3.org/2001/XMLSchema");
                }
                Source[] schemaSources = getSchemaSources();
                Handler handler = new Handler();
                if (schemaSources.length != 0) {
                    if ("http://www.w3.org/2001/XMLSchema".equals(this.language)) {
                        InputSource[] inputSourceArr = new InputSource[schemaSources.length];
                        for (int i = 0; i < schemaSources.length; i++) {
                            inputSourceArr[i] = Convert.toInputSource(schemaSources[i]);
                        }
                        sAXParserNewSAXParser.setProperty(JAXPConstants.JAXP_SCHEMA_SOURCE, inputSourceArr);
                    } else if (schemaSources.length == 1) {
                        handler.setSchemaSystemId(schemaSources[0].getSystemId());
                    }
                }
                try {
                    sAXParserNewSAXParser.parse(Convert.toInputSource(source), handler);
                } catch (SAXParseException e) {
                    handler.error(e);
                } catch (SAXException e2) {
                    throw new XMLUnitException(e2);
                }
                return handler.getResult();
            } catch (SAXException e3) {
                throw new XMLUnitException(e3);
            }
        } catch (IOException e4) {
            throw new XMLUnitException(e4);
        } catch (ParserConfigurationException e5) {
            throw new ConfigurationException(e5);
        } catch (SAXNotRecognizedException e6) {
            throw new ConfigurationException(e6);
        } catch (SAXNotSupportedException e7) {
            throw new ConfigurationException(e7);
        }
    }

    private static class Properties {
        static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
        static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

        private Properties() {
        }
    }

    private class Handler extends DefaultHandler {
        private String systemId;
        private final ValidationHandler v;

        private Handler() {
            this.v = new ValidationHandler();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
        public void error(SAXParseException sAXParseException) {
            this.v.error(sAXParseException);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
        public void fatalError(SAXParseException sAXParseException) {
            this.v.fatalError(sAXParseException);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ErrorHandler
        public void warning(SAXParseException sAXParseException) {
            this.v.warning(sAXParseException);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSchemaSystemId(String str) {
            this.systemId = str;
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.EntityResolver
        public InputSource resolveEntity(String str, String str2) throws SAXException, IOException {
            if (this.systemId != null && (ParsingValidator.this.getSchemaURI() == null || ParsingValidator.this.getSchemaURI().equals(str))) {
                return new InputSource(this.systemId);
            }
            return super.resolveEntity(str, str2);
        }

        ValidationResult getResult() {
            return this.v.getResult();
        }
    }
}
