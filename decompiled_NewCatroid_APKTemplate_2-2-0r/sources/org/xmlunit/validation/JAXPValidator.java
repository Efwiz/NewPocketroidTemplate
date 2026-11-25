package org.xmlunit.validation;

import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xmlunit.XMLUnitException;

/* JADX INFO: loaded from: classes4.dex */
public class JAXPValidator extends Validator {
    private final SchemaFactory factory;
    private final String language;
    private Schema schema;

    public JAXPValidator(String str) {
        this(str, null);
    }

    public JAXPValidator(String str, SchemaFactory schemaFactory) {
        this.language = str;
        this.factory = schemaFactory;
    }

    public final void setSchema(Schema schema) {
        this.schema = schema;
    }

    private SchemaFactory getFactory() {
        SchemaFactory schemaFactory = this.factory;
        return schemaFactory == null ? SchemaFactory.newInstance(this.language) : schemaFactory;
    }

    @Override // org.xmlunit.validation.Validator
    public ValidationResult validateSchema() {
        ValidationHandler validationHandler = new ValidationHandler();
        SchemaFactory factory = getFactory();
        factory.setErrorHandler(validationHandler);
        try {
            try {
                try {
                    factory.newSchema(getSchemaSources());
                } catch (SAXParseException e) {
                    validationHandler.error(e);
                }
                factory.setErrorHandler(null);
                return validationHandler.getResult();
            } catch (SAXException e2) {
                throw new XMLUnitException(e2);
            }
        } catch (Throwable th) {
            factory.setErrorHandler(null);
            throw th;
        }
    }

    @Override // org.xmlunit.validation.Validator
    public ValidationResult validateInstance(Source source) {
        try {
            Schema schema = getSchema();
            ValidationHandler validationHandler = new ValidationHandler();
            javax.xml.validation.Validator validatorNewValidator = schema.newValidator();
            validatorNewValidator.setErrorHandler(validationHandler);
            try {
                validatorNewValidator.validate(source);
            } catch (IOException e) {
                throw new XMLUnitException(e);
            } catch (SAXParseException e2) {
                validationHandler.error(e2);
            } catch (SAXException e3) {
                throw new XMLUnitException(e3);
            }
            return validationHandler.getResult();
        } catch (SAXException e4) {
            throw new XMLUnitException("The schema is invalid", e4);
        }
    }

    private Schema getSchema() throws SAXException {
        Schema schema = this.schema;
        if (schema != null) {
            return schema;
        }
        Source[] schemaSources = getSchemaSources();
        return schemaSources.length > 0 ? getFactory().newSchema(schemaSources) : getFactory().newSchema();
    }
}
