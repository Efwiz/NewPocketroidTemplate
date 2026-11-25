package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
final class StreamValidatorHelper implements ValidatorHelper {
    private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
    private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    private final XMLSchemaValidatorComponentManager fComponentManager;
    private SoftReference fConfiguration = new SoftReference(null);
    private SoftReference fParser = new SoftReference(null);
    private final XMLSchemaValidator fSchemaValidator;
    private SerializerFactory fSerializerFactory;

    public StreamValidatorHelper(XMLSchemaValidatorComponentManager xMLSchemaValidatorComponentManager) {
        this.fComponentManager = xMLSchemaValidatorComponentManager;
        this.fSchemaValidator = (XMLSchemaValidator) xMLSchemaValidatorComponentManager.getProperty(SCHEMA_VALIDATOR);
    }

    private XMLParserConfiguration initialize() {
        XML11Configuration xML11Configuration = new XML11Configuration();
        xML11Configuration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
        xML11Configuration.setProperty(ERROR_HANDLER, this.fComponentManager.getProperty(ERROR_HANDLER));
        XMLErrorReporter xMLErrorReporter = (XMLErrorReporter) this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        xML11Configuration.setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter);
        if (xMLErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            xMLErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            xMLErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        xML11Configuration.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
        xML11Configuration.setProperty(VALIDATION_MANAGER, this.fComponentManager.getProperty(VALIDATION_MANAGER));
        xML11Configuration.setProperty(SECURITY_MANAGER, this.fComponentManager.getProperty(SECURITY_MANAGER));
        xML11Configuration.setDocumentHandler(this.fSchemaValidator);
        xML11Configuration.setDTDHandler(null);
        xML11Configuration.setDTDContentModelHandler(null);
        this.fConfiguration = new SoftReference(xML11Configuration);
        return xML11Configuration;
    }

    @Override // org.apache.xerces.jaxp.validation.ValidatorHelper
    public void validate(Source source, Result result) throws SAXException, IOException {
        boolean z;
        OutputStream outputStreamCreateOutputStream;
        SerializerFactory serializerFactory;
        OutputFormat outputFormat;
        Serializer serializerMakeSerializer;
        if (!(result instanceof StreamResult) && result != null) {
            throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{source.getClass().getName(), result.getClass().getName()}));
        }
        StreamSource streamSource = (StreamSource) source;
        StreamResult streamResult = (StreamResult) result;
        XMLInputSource xMLInputSource = new XMLInputSource(streamSource.getPublicId(), streamSource.getSystemId(), null);
        xMLInputSource.setByteStream(streamSource.getInputStream());
        xMLInputSource.setCharacterStream(streamSource.getReader());
        XMLParserConfiguration xMLParserConfigurationInitialize = (XMLParserConfiguration) this.fConfiguration.get();
        if (xMLParserConfigurationInitialize == null) {
            xMLParserConfigurationInitialize = initialize();
            z = true;
        } else {
            if (this.fComponentManager.getFeature(PARSER_SETTINGS)) {
                xMLParserConfigurationInitialize.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
                xMLParserConfigurationInitialize.setProperty(ERROR_HANDLER, this.fComponentManager.getProperty(ERROR_HANDLER));
                xMLParserConfigurationInitialize.setProperty(SECURITY_MANAGER, this.fComponentManager.getProperty(SECURITY_MANAGER));
            }
            z = false;
        }
        this.fComponentManager.reset();
        if (streamResult != null) {
            if (this.fSerializerFactory == null) {
                this.fSerializerFactory = SerializerFactory.getSerializerFactory("xml");
            }
            if (streamResult.getWriter() != null) {
                serializerMakeSerializer = this.fSerializerFactory.makeSerializer(streamResult.getWriter(), new OutputFormat());
            } else {
                if (streamResult.getOutputStream() != null) {
                    serializerFactory = this.fSerializerFactory;
                    outputStreamCreateOutputStream = streamResult.getOutputStream();
                    outputFormat = new OutputFormat();
                } else {
                    if (streamResult.getSystemId() == null) {
                        throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "StreamResultNotInitialized", null));
                    }
                    outputStreamCreateOutputStream = XMLEntityManager.createOutputStream(streamResult.getSystemId());
                    serializerFactory = this.fSerializerFactory;
                    outputFormat = new OutputFormat();
                }
                serializerMakeSerializer = serializerFactory.makeSerializer(outputStreamCreateOutputStream, outputFormat);
            }
            SAXParser sAXParser = (SAXParser) this.fParser.get();
            if (z || sAXParser == null) {
                sAXParser = new SAXParser(xMLParserConfigurationInitialize);
                this.fParser = new SoftReference(sAXParser);
            } else {
                sAXParser.reset();
            }
            xMLParserConfigurationInitialize.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentHandler(sAXParser);
            sAXParser.setContentHandler(serializerMakeSerializer.asContentHandler());
        } else {
            this.fSchemaValidator.setDocumentHandler(null);
        }
        try {
            try {
                xMLParserConfigurationInitialize.parse(xMLInputSource);
                this.fSchemaValidator.setDocumentHandler(null);
            } catch (XMLParseException e) {
                throw Util.toSAXParseException(e);
            } catch (XNIException e2) {
                throw Util.toSAXException(e2);
            }
        } catch (Throwable th) {
            this.fSchemaValidator.setDocumentHandler(null);
            throw th;
        }
    }
}
