package org.apache.xerces.parsers;

import org.apache.xerces.util.ShadowedSymbolTable;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

/* JADX INFO: loaded from: classes13.dex */
public class CachingParserPool {
    public static final boolean DEFAULT_SHADOW_GRAMMAR_POOL = false;
    public static final boolean DEFAULT_SHADOW_SYMBOL_TABLE = false;
    protected boolean fShadowGrammarPool;
    protected boolean fShadowSymbolTable;
    protected XMLGrammarPool fSynchronizedGrammarPool;
    protected SymbolTable fSynchronizedSymbolTable;

    public static final class ShadowedGrammarPool extends XMLGrammarPoolImpl {
        private XMLGrammarPool fGrammarPool;

        public ShadowedGrammarPool(XMLGrammarPool xMLGrammarPool) {
            this.fGrammarPool = xMLGrammarPool;
        }

        @Override // org.apache.xerces.util.XMLGrammarPoolImpl, org.apache.xerces.xni.grammars.XMLGrammarPool
        public void cacheGrammars(String str, Grammar[] grammarArr) {
            super.cacheGrammars(str, grammarArr);
            this.fGrammarPool.cacheGrammars(str, grammarArr);
        }

        @Override // org.apache.xerces.util.XMLGrammarPoolImpl
        public boolean containsGrammar(XMLGrammarDescription xMLGrammarDescription) {
            return super.containsGrammar(xMLGrammarDescription);
        }

        @Override // org.apache.xerces.util.XMLGrammarPoolImpl
        public Grammar getGrammar(XMLGrammarDescription xMLGrammarDescription) {
            if (super.containsGrammar(xMLGrammarDescription)) {
                return super.getGrammar(xMLGrammarDescription);
            }
            return null;
        }

        @Override // org.apache.xerces.util.XMLGrammarPoolImpl, org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription) {
            Grammar grammarRetrieveGrammar = super.retrieveGrammar(xMLGrammarDescription);
            return grammarRetrieveGrammar != null ? grammarRetrieveGrammar : this.fGrammarPool.retrieveGrammar(xMLGrammarDescription);
        }

        @Override // org.apache.xerces.util.XMLGrammarPoolImpl, org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar[] retrieveInitialGrammarSet(String str) {
            Grammar[] grammarArrRetrieveInitialGrammarSet = super.retrieveInitialGrammarSet(str);
            return grammarArrRetrieveInitialGrammarSet != null ? grammarArrRetrieveInitialGrammarSet : this.fGrammarPool.retrieveInitialGrammarSet(str);
        }
    }

    public static final class SynchronizedGrammarPool implements XMLGrammarPool {
        private XMLGrammarPool fGrammarPool;

        public SynchronizedGrammarPool(XMLGrammarPool xMLGrammarPool) {
            this.fGrammarPool = xMLGrammarPool;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void cacheGrammars(String str, Grammar[] grammarArr) {
            synchronized (this.fGrammarPool) {
                this.fGrammarPool.cacheGrammars(str, grammarArr);
            }
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void clear() {
            synchronized (this.fGrammarPool) {
                this.fGrammarPool.clear();
            }
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void lockPool() {
            synchronized (this.fGrammarPool) {
                this.fGrammarPool.lockPool();
            }
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription) {
            Grammar grammarRetrieveGrammar;
            synchronized (this.fGrammarPool) {
                grammarRetrieveGrammar = this.fGrammarPool.retrieveGrammar(xMLGrammarDescription);
            }
            return grammarRetrieveGrammar;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar[] retrieveInitialGrammarSet(String str) {
            Grammar[] grammarArrRetrieveInitialGrammarSet;
            synchronized (this.fGrammarPool) {
                grammarArrRetrieveInitialGrammarSet = this.fGrammarPool.retrieveInitialGrammarSet(str);
            }
            return grammarArrRetrieveInitialGrammarSet;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void unlockPool() {
            synchronized (this.fGrammarPool) {
                this.fGrammarPool.unlockPool();
            }
        }
    }

    public CachingParserPool() {
        this(new SymbolTable(), new XMLGrammarPoolImpl());
    }

    public CachingParserPool(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this.fShadowSymbolTable = false;
        this.fShadowGrammarPool = false;
        this.fSynchronizedSymbolTable = new SynchronizedSymbolTable(symbolTable);
        this.fSynchronizedGrammarPool = new SynchronizedGrammarPool(xMLGrammarPool);
    }

    public DOMParser createDOMParser() {
        return new DOMParser(this.fShadowSymbolTable ? new ShadowedSymbolTable(this.fSynchronizedSymbolTable) : this.fSynchronizedSymbolTable, this.fShadowGrammarPool ? new ShadowedGrammarPool(this.fSynchronizedGrammarPool) : this.fSynchronizedGrammarPool);
    }

    public SAXParser createSAXParser() {
        return new SAXParser(this.fShadowSymbolTable ? new ShadowedSymbolTable(this.fSynchronizedSymbolTable) : this.fSynchronizedSymbolTable, this.fShadowGrammarPool ? new ShadowedGrammarPool(this.fSynchronizedGrammarPool) : this.fSynchronizedGrammarPool);
    }

    public SymbolTable getSymbolTable() {
        return this.fSynchronizedSymbolTable;
    }

    public XMLGrammarPool getXMLGrammarPool() {
        return this.fSynchronizedGrammarPool;
    }

    public void setShadowSymbolTable(boolean z) {
        this.fShadowSymbolTable = z;
    }
}
