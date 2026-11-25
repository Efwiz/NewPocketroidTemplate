package org.apache.xerces.util;

import com.koushikdutta.async.http.body.DocumentBody;
import java.io.IOException;
import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.readers.SAXCatalogReader;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

/* JADX INFO: loaded from: classes13.dex */
public class XMLCatalogResolver implements XMLEntityResolver, EntityResolver2, LSResourceResolver {
    private Catalog fCatalog;
    private boolean fCatalogsChanged;
    private String[] fCatalogsList;
    private boolean fPreferPublic;
    private CatalogManager fResolverCatalogManager;
    private boolean fUseLiteralSystemId;

    public XMLCatalogResolver() {
        this(null, true);
    }

    public XMLCatalogResolver(String[] strArr) {
        this(strArr, true);
    }

    public XMLCatalogResolver(String[] strArr, boolean z) {
        this.fResolverCatalogManager = null;
        this.fCatalog = null;
        this.fCatalogsList = null;
        this.fCatalogsChanged = true;
        this.fPreferPublic = true;
        this.fUseLiteralSystemId = true;
        init(strArr, z);
    }

    private void attachReaderToCatalog(Catalog catalog) {
        SAXParserFactoryImpl sAXParserFactoryImpl = new SAXParserFactoryImpl();
        sAXParserFactoryImpl.setNamespaceAware(true);
        sAXParserFactoryImpl.setValidating(false);
        SAXCatalogReader sAXCatalogReader = new SAXCatalogReader(sAXParserFactoryImpl);
        sAXCatalogReader.setCatalogParser("urn:oasis:names:tc:entity:xmlns:xml:catalog", "catalog", "org.apache.xml.resolver.readers.OASISXMLCatalogReader");
        catalog.addReader(DocumentBody.CONTENT_TYPE, sAXCatalogReader);
    }

    private void init(String[] strArr, boolean z) {
        this.fCatalogsList = strArr != null ? (String[]) strArr.clone() : null;
        this.fPreferPublic = z;
        CatalogManager catalogManager = new CatalogManager();
        this.fResolverCatalogManager = catalogManager;
        catalogManager.setAllowOasisXMLCatalogPI(false);
        this.fResolverCatalogManager.setCatalogClassName("org.apache.xml.resolver.Catalog");
        this.fResolverCatalogManager.setCatalogFiles("");
        this.fResolverCatalogManager.setIgnoreMissingProperties(true);
        this.fResolverCatalogManager.setPreferPublic(this.fPreferPublic);
        this.fResolverCatalogManager.setRelativeCatalogs(false);
        this.fResolverCatalogManager.setUseStaticCatalog(false);
        this.fResolverCatalogManager.setVerbosity(0);
    }

    private void parseCatalogs() throws IOException {
        if (this.fCatalogsList == null) {
            this.fCatalog = null;
            return;
        }
        Catalog catalog = new Catalog(this.fResolverCatalogManager);
        this.fCatalog = catalog;
        attachReaderToCatalog(catalog);
        int i = 0;
        while (true) {
            String[] strArr = this.fCatalogsList;
            if (i >= strArr.length) {
                return;
            }
            String str = strArr[i];
            if (str != null && str.length() > 0) {
                this.fCatalog.parseCatalog(str);
            }
            i++;
        }
    }

    public final synchronized void clear() {
        this.fCatalog = null;
    }

    public final synchronized String[] getCatalogList() {
        String[] strArr;
        strArr = this.fCatalogsList;
        return strArr != null ? (String[]) strArr.clone() : null;
    }

    @Override // org.xml.sax.ext.EntityResolver2
    public InputSource getExternalSubset(String str, String str2) throws SAXException, IOException {
        return null;
    }

    public final boolean getPreferPublic() {
        return this.fPreferPublic;
    }

    public final boolean getUseLiteralSystemId() {
        return this.fUseLiteralSystemId;
    }

    @Override // org.apache.xerces.xni.parser.XMLEntityResolver
    public XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
        String strResolveIdentifier = resolveIdentifier(xMLResourceIdentifier);
        if (strResolveIdentifier != null) {
            return new XMLInputSource(xMLResourceIdentifier.getPublicId(), strResolveIdentifier, xMLResourceIdentifier.getBaseSystemId());
        }
        return null;
    }

    @Override // org.xml.sax.EntityResolver
    public InputSource resolveEntity(String str, String str2) throws SAXException, IOException {
        String strResolveSystem;
        if (str == null || str2 == null) {
            strResolveSystem = str2 != null ? resolveSystem(str2) : null;
        } else {
            strResolveSystem = resolvePublic(str, str2);
        }
        if (strResolveSystem == null) {
            return null;
        }
        InputSource inputSource = new InputSource(strResolveSystem);
        inputSource.setPublicId(str);
        return inputSource;
    }

    @Override // org.xml.sax.ext.EntityResolver2
    public InputSource resolveEntity(String str, String str2, String str3, String str4) throws SAXException, IOException {
        String strResolveSystem;
        if (!getUseLiteralSystemId() && str3 != null) {
            try {
                str4 = new URI(new URI(str3), str4).toString();
            } catch (URI.MalformedURIException unused) {
            }
        }
        if (str2 == null || str4 == null) {
            strResolveSystem = str4 != null ? resolveSystem(str4) : null;
        } else {
            strResolveSystem = resolvePublic(str2, str4);
        }
        if (strResolveSystem == null) {
            return null;
        }
        InputSource inputSource = new InputSource(strResolveSystem);
        inputSource.setPublicId(str2);
        return inputSource;
    }

    public String resolveIdentifier(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
        String namespace = xMLResourceIdentifier.getNamespace();
        String strResolveURI = namespace != null ? resolveURI(namespace) : null;
        if (strResolveURI != null) {
            return strResolveURI;
        }
        String publicId = xMLResourceIdentifier.getPublicId();
        String literalSystemId = getUseLiteralSystemId() ? xMLResourceIdentifier.getLiteralSystemId() : xMLResourceIdentifier.getExpandedSystemId();
        if (publicId == null || literalSystemId == null) {
            return literalSystemId != null ? resolveSystem(literalSystemId) : strResolveURI;
        }
        return resolvePublic(publicId, literalSystemId);
    }

    public final synchronized String resolvePublic(String str, String str2) throws IOException {
        Catalog catalog;
        if (this.fCatalogsChanged) {
            parseCatalogs();
            this.fCatalogsChanged = false;
        }
        catalog = this.fCatalog;
        return catalog != null ? catalog.resolvePublic(str, str2) : null;
    }

    @Override // org.w3c.dom.ls.LSResourceResolver
    public LSInput resolveResource(String str, String str2, String str3, String str4, String str5) {
        String strResolveURI;
        if (str2 != null) {
            try {
                strResolveURI = resolveURI(str2);
            } catch (IOException unused) {
                strResolveURI = null;
            }
        } else {
            strResolveURI = null;
        }
        try {
            if (!getUseLiteralSystemId() && str5 != null) {
                try {
                    str4 = new URI(new URI(str5), str4).toString();
                } catch (URI.MalformedURIException unused2) {
                }
            }
            if (strResolveURI == null) {
                if (str3 != null && str4 != null) {
                    strResolveURI = resolvePublic(str3, str4);
                } else if (str4 != null) {
                    strResolveURI = resolveSystem(str4);
                }
            }
        } catch (IOException unused3) {
        }
        if (strResolveURI != null) {
            return new DOMInputImpl(str3, strResolveURI, str5);
        }
        return null;
    }

    public final synchronized String resolveSystem(String str) throws IOException {
        Catalog catalog;
        if (this.fCatalogsChanged) {
            parseCatalogs();
            this.fCatalogsChanged = false;
        }
        catalog = this.fCatalog;
        return catalog != null ? catalog.resolveSystem(str) : null;
    }

    public final synchronized String resolveURI(String str) throws IOException {
        Catalog catalog;
        if (this.fCatalogsChanged) {
            parseCatalogs();
            this.fCatalogsChanged = false;
        }
        catalog = this.fCatalog;
        return catalog != null ? catalog.resolveURI(str) : null;
    }

    public final synchronized void setCatalogList(String[] strArr) {
        this.fCatalogsChanged = true;
        this.fCatalogsList = strArr != null ? (String[]) strArr.clone() : null;
    }

    public final void setPreferPublic(boolean z) {
        this.fPreferPublic = z;
        this.fResolverCatalogManager.setPreferPublic(z);
    }

    public final void setUseLiteralSystemId(boolean z) {
        this.fUseLiteralSystemId = z;
    }
}
