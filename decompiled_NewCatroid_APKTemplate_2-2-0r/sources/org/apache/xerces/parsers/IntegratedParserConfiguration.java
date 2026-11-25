package org.apache.xerces.parsers;

import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.dtd.XMLDTDValidator;
import org.apache.xerces.impl.dtd.XMLNSDTDValidator;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLDocumentSource;

/* JADX INFO: loaded from: classes13.dex */
public class IntegratedParserConfiguration extends StandardParserConfiguration {
    protected XMLNSDocumentScannerImpl fNamespaceScanner;
    protected XMLDTDValidator fNonNSDTDValidator;
    protected XMLDocumentScannerImpl fNonNSScanner;

    public IntegratedParserConfiguration() {
        this(null, null, null);
    }

    public IntegratedParserConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public IntegratedParserConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public IntegratedParserConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLGrammarPool, xMLComponentManager);
        this.fNonNSScanner = new XMLDocumentScannerImpl();
        this.fNonNSDTDValidator = new XMLDTDValidator();
        addComponent(this.fNonNSScanner);
        addComponent(this.fNonNSDTDValidator);
    }

    @Override // org.apache.xerces.parsers.StandardParserConfiguration, org.apache.xerces.parsers.DTDConfiguration
    protected void configurePipeline() {
        XMLDocumentSource xMLDocumentSource;
        setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
        configureDTDPipeline();
        if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
            this.fProperties.put("http://apache.org/xml/properties/internal/namespace-binder", this.fNamespaceBinder);
            this.fScanner = this.fNamespaceScanner;
            this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
            if (this.fDTDValidator != null) {
                this.fProperties.put("http://apache.org/xml/properties/internal/validator/dtd", this.fDTDValidator);
                this.fNamespaceScanner.setDTDValidator(this.fDTDValidator);
                this.fNamespaceScanner.setDocumentHandler(this.fDTDValidator);
                this.fDTDValidator.setDocumentSource(this.fNamespaceScanner);
                this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
                if (this.fDocumentHandler != null) {
                    this.fDocumentHandler.setDocumentSource(this.fDTDValidator);
                }
                xMLDocumentSource = this.fDTDValidator;
            } else {
                this.fNamespaceScanner.setDocumentHandler(this.fDocumentHandler);
                this.fNamespaceScanner.setDTDValidator(null);
                if (this.fDocumentHandler != null) {
                    this.fDocumentHandler.setDocumentSource(this.fNamespaceScanner);
                }
                xMLDocumentSource = this.fNamespaceScanner;
            }
        } else {
            this.fScanner = this.fNonNSScanner;
            this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNonNSScanner);
            if (this.fNonNSDTDValidator != null) {
                this.fProperties.put("http://apache.org/xml/properties/internal/validator/dtd", this.fNonNSDTDValidator);
                this.fNonNSScanner.setDocumentHandler(this.fNonNSDTDValidator);
                this.fNonNSDTDValidator.setDocumentSource(this.fNonNSScanner);
                this.fNonNSDTDValidator.setDocumentHandler(this.fDocumentHandler);
                if (this.fDocumentHandler != null) {
                    this.fDocumentHandler.setDocumentSource(this.fNonNSDTDValidator);
                }
                xMLDocumentSource = this.fNonNSDTDValidator;
            } else {
                this.fScanner.setDocumentHandler(this.fDocumentHandler);
                if (this.fDocumentHandler != null) {
                    this.fDocumentHandler.setDocumentSource(this.fScanner);
                }
                xMLDocumentSource = this.fScanner;
            }
        }
        this.fLastComponent = xMLDocumentSource;
        if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE) {
            if (this.fSchemaValidator == null) {
                this.fSchemaValidator = new XMLSchemaValidator();
                this.fProperties.put("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
                addComponent(this.fSchemaValidator);
                if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
                    this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
                }
            }
            this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentSource(this.fLastComponent);
            this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
            if (this.fDocumentHandler != null) {
                this.fDocumentHandler.setDocumentSource(this.fSchemaValidator);
            }
            this.fLastComponent = this.fSchemaValidator;
        }
    }

    @Override // org.apache.xerces.parsers.DTDConfiguration
    protected XMLDTDValidator createDTDValidator() {
        return new XMLNSDTDValidator();
    }

    @Override // org.apache.xerces.parsers.DTDConfiguration
    protected XMLDocumentScanner createDocumentScanner() {
        XMLNSDocumentScannerImpl xMLNSDocumentScannerImpl = new XMLNSDocumentScannerImpl();
        this.fNamespaceScanner = xMLNSDocumentScannerImpl;
        return xMLNSDocumentScannerImpl;
    }
}
