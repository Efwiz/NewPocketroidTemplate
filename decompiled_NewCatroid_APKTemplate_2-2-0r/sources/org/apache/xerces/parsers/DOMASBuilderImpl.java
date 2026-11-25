package org.apache.xerces.parsers;

import java.util.Vector;
import org.apache.xerces.dom.ASModelImpl;
import org.apache.xerces.dom3.as.ASModel;
import org.apache.xerces.dom3.as.DOMASBuilder;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.XSGrammarBucket;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.ls.LSInput;

/* JADX INFO: loaded from: classes13.dex */
public class DOMASBuilderImpl extends DOMParserImpl implements DOMASBuilder {
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected ASModelImpl fAbstractSchema;
    protected XSGrammarBucket fGrammarBucket;

    public DOMASBuilderImpl() {
        super(new XMLGrammarCachingConfiguration());
    }

    public DOMASBuilderImpl(XMLGrammarCachingConfiguration xMLGrammarCachingConfiguration) {
        super(xMLGrammarCachingConfiguration);
    }

    public DOMASBuilderImpl(SymbolTable symbolTable) {
        super(new XMLGrammarCachingConfiguration(symbolTable));
    }

    public DOMASBuilderImpl(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        super(new XMLGrammarCachingConfiguration(symbolTable, xMLGrammarPool));
    }

    private void addGrammars(ASModelImpl aSModelImpl, XSGrammarBucket xSGrammarBucket) {
        for (SchemaGrammar schemaGrammar : xSGrammarBucket.getGrammars()) {
            ASModelImpl aSModelImpl2 = new ASModelImpl();
            aSModelImpl2.setGrammar(schemaGrammar);
            aSModelImpl.addASModel(aSModelImpl2);
        }
    }

    private void initGrammarBucket() {
        this.fGrammarBucket.reset();
        ASModelImpl aSModelImpl = this.fAbstractSchema;
        if (aSModelImpl != null) {
            initGrammarBucketRecurse(aSModelImpl);
        }
    }

    private void initGrammarBucketRecurse(ASModelImpl aSModelImpl) {
        if (aSModelImpl.getGrammar() != null) {
            this.fGrammarBucket.putGrammar(aSModelImpl.getGrammar());
        }
        for (int i = 0; i < aSModelImpl.getInternalASModels().size(); i++) {
            initGrammarBucketRecurse((ASModelImpl) aSModelImpl.getInternalASModels().elementAt(i));
        }
    }

    private void initGrammarPool(ASModelImpl aSModelImpl, XMLGrammarPool xMLGrammarPool) {
        SchemaGrammar grammar = aSModelImpl.getGrammar();
        Grammar[] grammarArr = {grammar};
        if (grammar != null) {
            xMLGrammarPool.cacheGrammars(grammarArr[0].getGrammarDescription().getGrammarType(), grammarArr);
        }
        Vector internalASModels = aSModelImpl.getInternalASModels();
        for (int i = 0; i < internalASModels.size(); i++) {
            initGrammarPool((ASModelImpl) internalASModels.elementAt(i), xMLGrammarPool);
        }
    }

    @Override // org.apache.xerces.dom3.as.DOMASBuilder
    public ASModel getAbstractSchema() {
        return this.fAbstractSchema;
    }

    ASModel parseASInputSource(XMLInputSource xMLInputSource) throws Exception {
        if (this.fGrammarBucket == null) {
            this.fGrammarBucket = new XSGrammarBucket();
        }
        initGrammarBucket();
        XMLGrammarCachingConfiguration xMLGrammarCachingConfiguration = (XMLGrammarCachingConfiguration) this.fConfiguration;
        xMLGrammarCachingConfiguration.lockGrammarPool();
        SchemaGrammar xMLSchema = xMLGrammarCachingConfiguration.parseXMLSchema(xMLInputSource);
        xMLGrammarCachingConfiguration.unlockGrammarPool();
        if (xMLSchema == null) {
            return null;
        }
        ASModelImpl aSModelImpl = new ASModelImpl();
        this.fGrammarBucket.putGrammar(xMLSchema, true);
        addGrammars(aSModelImpl, this.fGrammarBucket);
        return aSModelImpl;
    }

    @Override // org.apache.xerces.dom3.as.DOMASBuilder
    public ASModel parseASInputSource(LSInput lSInput) throws Exception {
        try {
            return parseASInputSource(dom2xmlInputSource(lSInput));
        } catch (XNIException e) {
            throw e.getException();
        }
    }

    @Override // org.apache.xerces.dom3.as.DOMASBuilder
    public ASModel parseASURI(String str) throws Exception {
        return parseASInputSource(new XMLInputSource(null, str, null));
    }

    @Override // org.apache.xerces.dom3.as.DOMASBuilder
    public void setAbstractSchema(ASModel aSModel) {
        this.fAbstractSchema = (ASModelImpl) aSModel;
        XMLGrammarPool xMLGrammarPoolImpl = (XMLGrammarPool) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
        if (xMLGrammarPoolImpl == null) {
            xMLGrammarPoolImpl = new XMLGrammarPoolImpl();
            this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", xMLGrammarPoolImpl);
        }
        ASModelImpl aSModelImpl = this.fAbstractSchema;
        if (aSModelImpl != null) {
            initGrammarPool(aSModelImpl, xMLGrammarPoolImpl);
        }
    }
}
