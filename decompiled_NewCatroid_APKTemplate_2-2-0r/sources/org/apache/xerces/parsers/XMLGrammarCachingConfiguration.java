package org.apache.xerces.parsers;

import java.io.IOException;
import org.apache.xerces.impl.dtd.DTDGrammar;
import org.apache.xerces.impl.dtd.XMLDTDLoader;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.jaxp.JAXPConstants;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLGrammarCachingConfiguration extends XIncludeAwareParserConfiguration {
    public static final int BIG_PRIME = 2039;
    protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected XMLDTDLoader fDTDLoader;
    protected XMLSchemaLoader fSchemaLoader;
    protected static final SynchronizedSymbolTable fStaticSymbolTable = new SynchronizedSymbolTable(2039);
    protected static final XMLGrammarPoolImpl fStaticGrammarPool = new XMLGrammarPoolImpl();

    public XMLGrammarCachingConfiguration() {
        this(fStaticSymbolTable, fStaticGrammarPool, null);
    }

    public XMLGrammarCachingConfiguration(SymbolTable symbolTable) {
        this(symbolTable, fStaticGrammarPool, null);
    }

    public XMLGrammarCachingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public XMLGrammarCachingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLGrammarPool, xMLComponentManager);
        XMLSchemaLoader xMLSchemaLoader = new XMLSchemaLoader(this.fSymbolTable);
        this.fSchemaLoader = xMLSchemaLoader;
        xMLSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
        this.fDTDLoader = new XMLDTDLoader(this.fSymbolTable, this.fGrammarPool);
    }

    @Override // org.apache.xerces.parsers.XML11Configuration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkFeature(String str) throws XMLConfigurationException {
        super.checkFeature(str);
    }

    @Override // org.apache.xerces.parsers.XML11Configuration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkProperty(String str) throws XMLConfigurationException {
        super.checkProperty(str);
    }

    public void clearGrammarPool() {
        this.fGrammarPool.clear();
    }

    public void lockGrammarPool() {
        this.fGrammarPool.lockPool();
    }

    DTDGrammar parseDTD(XMLInputSource xMLInputSource) throws IOException {
        XMLEntityResolver entityResolver = getEntityResolver();
        if (entityResolver != null) {
            this.fDTDLoader.setEntityResolver(entityResolver);
        }
        this.fDTDLoader.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        DTDGrammar dTDGrammar = (DTDGrammar) this.fDTDLoader.loadGrammar(xMLInputSource);
        if (dTDGrammar != null) {
            this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[]{dTDGrammar});
        }
        return dTDGrammar;
    }

    public Grammar parseGrammar(String str, String str2) throws IOException, XNIException {
        return parseGrammar(str, new XMLInputSource(null, str2, null));
    }

    public Grammar parseGrammar(String str, XMLInputSource xMLInputSource) throws IOException, XNIException {
        if (str.equals("http://www.w3.org/2001/XMLSchema")) {
            return parseXMLSchema(xMLInputSource);
        }
        if (str.equals("http://www.w3.org/TR/REC-xml")) {
            return parseDTD(xMLInputSource);
        }
        return null;
    }

    SchemaGrammar parseXMLSchema(XMLInputSource xMLInputSource) throws IOException {
        XMLEntityResolver entityResolver = getEntityResolver();
        if (entityResolver != null) {
            this.fSchemaLoader.setEntityResolver(entityResolver);
        }
        if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
            this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
        }
        this.fSchemaLoader.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        this.fSchemaLoader.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", getProperty("http://apache.org/xml/properties/schema/external-schemaLocation"));
        this.fSchemaLoader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", getProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation"));
        this.fSchemaLoader.setProperty(JAXPConstants.JAXP_SCHEMA_SOURCE, getProperty(JAXPConstants.JAXP_SCHEMA_SOURCE));
        this.fSchemaLoader.setFeature(SCHEMA_FULL_CHECKING, getFeature(SCHEMA_FULL_CHECKING));
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fSchemaLoader.loadGrammar(xMLInputSource);
        if (schemaGrammar != null) {
            this.fGrammarPool.cacheGrammars("http://www.w3.org/2001/XMLSchema", new Grammar[]{schemaGrammar});
        }
        return schemaGrammar;
    }

    public void unlockGrammarPool() {
        this.fGrammarPool.unlockPool();
    }
}
