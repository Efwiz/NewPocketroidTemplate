package org.apache.xerces.parsers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarLoader;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLGrammarPreparser {
    private static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    private static final Hashtable KNOWN_LOADERS;
    private static final String[] RECOGNIZED_PROPERTIES;
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected XMLEntityResolver fEntityResolver;
    protected final XMLErrorReporter fErrorReporter;
    protected XMLGrammarPool fGrammarPool;
    private final Hashtable fLoaders;
    protected Locale fLocale;
    private int fModCount;
    protected final SymbolTable fSymbolTable;

    static class XMLGrammarLoaderContainer {
        public final XMLGrammarLoader loader;
        public int modCount = 0;

        public XMLGrammarLoaderContainer(XMLGrammarLoader xMLGrammarLoader) {
            this.loader = xMLGrammarLoader;
        }
    }

    static {
        Hashtable hashtable = new Hashtable();
        KNOWN_LOADERS = hashtable;
        hashtable.put("http://www.w3.org/2001/XMLSchema", "org.apache.xerces.impl.xs.XMLSchemaLoader");
        hashtable.put("http://www.w3.org/TR/REC-xml", "org.apache.xerces.impl.dtd.XMLDTDLoader");
        RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", ERROR_HANDLER, "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool"};
    }

    public XMLGrammarPreparser() {
        this(new SymbolTable());
    }

    public XMLGrammarPreparser(SymbolTable symbolTable) {
        this.fModCount = 1;
        this.fSymbolTable = symbolTable;
        this.fLoaders = new Hashtable();
        this.fErrorReporter = new XMLErrorReporter();
        setLocale(Locale.getDefault());
        this.fEntityResolver = new XMLEntityManager();
    }

    private void clearModCounts() {
        Enumeration enumerationElements = this.fLoaders.elements();
        while (enumerationElements.hasMoreElements()) {
            ((XMLGrammarLoaderContainer) enumerationElements.nextElement()).modCount = 0;
        }
        this.fModCount = 1;
    }

    public XMLEntityResolver getEntityResolver() {
        return this.fEntityResolver;
    }

    public XMLErrorHandler getErrorHandler() {
        return this.fErrorReporter.getErrorHandler();
    }

    public boolean getFeature(String str, String str2) {
        return ((XMLGrammarLoaderContainer) this.fLoaders.get(str)).loader.getFeature(str2);
    }

    public XMLGrammarPool getGrammarPool() {
        return this.fGrammarPool;
    }

    public XMLGrammarLoader getLoader(String str) {
        XMLGrammarLoaderContainer xMLGrammarLoaderContainer = (XMLGrammarLoaderContainer) this.fLoaders.get(str);
        if (xMLGrammarLoaderContainer != null) {
            return xMLGrammarLoaderContainer.loader;
        }
        return null;
    }

    public Locale getLocale() {
        return this.fLocale;
    }

    public Object getProperty(String str, String str2) {
        return ((XMLGrammarLoaderContainer) this.fLoaders.get(str)).loader.getProperty(str2);
    }

    public Grammar preparseGrammar(String str, XMLInputSource xMLInputSource) throws IOException, XNIException {
        if (!this.fLoaders.containsKey(str)) {
            return null;
        }
        XMLGrammarLoaderContainer xMLGrammarLoaderContainer = (XMLGrammarLoaderContainer) this.fLoaders.get(str);
        XMLGrammarLoader xMLGrammarLoader = xMLGrammarLoaderContainer.loader;
        if (xMLGrammarLoaderContainer.modCount != this.fModCount) {
            xMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
            xMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fEntityResolver);
            xMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
            XMLGrammarPool xMLGrammarPool = this.fGrammarPool;
            if (xMLGrammarPool != null) {
                try {
                    xMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", xMLGrammarPool);
                } catch (Exception unused) {
                }
            }
            xMLGrammarLoaderContainer.modCount = this.fModCount;
        }
        return xMLGrammarLoader.loadGrammar(xMLInputSource);
    }

    public boolean registerPreparser(String str, XMLGrammarLoader xMLGrammarLoader) {
        if (xMLGrammarLoader != null) {
            this.fLoaders.put(str, new XMLGrammarLoaderContainer(xMLGrammarLoader));
            return true;
        }
        Hashtable hashtable = KNOWN_LOADERS;
        if (hashtable.containsKey(str)) {
            try {
                XMLGrammarLoader xMLGrammarLoader2 = (XMLGrammarLoader) ObjectFactory.newInstance((String) hashtable.get(str), ObjectFactory.findClassLoader(), true);
                this.fLoaders.put(str, new XMLGrammarLoaderContainer(xMLGrammarLoader2));
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
        if (this.fEntityResolver != xMLEntityResolver) {
            int i = this.fModCount + 1;
            this.fModCount = i;
            if (i < 0) {
                clearModCounts();
            }
            this.fEntityResolver = xMLEntityResolver;
        }
    }

    public void setErrorHandler(XMLErrorHandler xMLErrorHandler) {
        this.fErrorReporter.setProperty(ERROR_HANDLER, xMLErrorHandler);
    }

    public void setFeature(String str, boolean z) {
        Enumeration enumerationElements = this.fLoaders.elements();
        while (enumerationElements.hasMoreElements()) {
            try {
                ((XMLGrammarLoaderContainer) enumerationElements.nextElement()).loader.setFeature(str, z);
            } catch (Exception unused) {
            }
        }
        if (str.equals(CONTINUE_AFTER_FATAL_ERROR)) {
            this.fErrorReporter.setFeature(CONTINUE_AFTER_FATAL_ERROR, z);
        }
    }

    public void setGrammarPool(XMLGrammarPool xMLGrammarPool) {
        if (this.fGrammarPool != xMLGrammarPool) {
            int i = this.fModCount + 1;
            this.fModCount = i;
            if (i < 0) {
                clearModCounts();
            }
            this.fGrammarPool = xMLGrammarPool;
        }
    }

    public void setLocale(Locale locale) {
        this.fLocale = locale;
        this.fErrorReporter.setLocale(locale);
    }

    public void setProperty(String str, Object obj) {
        Enumeration enumerationElements = this.fLoaders.elements();
        while (enumerationElements.hasMoreElements()) {
            try {
                ((XMLGrammarLoaderContainer) enumerationElements.nextElement()).loader.setProperty(str, obj);
            } catch (Exception unused) {
            }
        }
    }
}
