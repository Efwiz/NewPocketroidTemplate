package org.apache.xerces.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/* JADX INFO: loaded from: classes13.dex */
public class DOMEntityResolverWrapper implements XMLEntityResolver {
    private static final String XML_TYPE = "http://www.w3.org/TR/REC-xml";
    private static final String XSD_TYPE = "http://www.w3.org/2001/XMLSchema";
    protected LSResourceResolver fEntityResolver;

    public DOMEntityResolverWrapper() {
    }

    public DOMEntityResolverWrapper(LSResourceResolver lSResourceResolver) {
        setEntityResolver(lSResourceResolver);
    }

    private String getType(XMLResourceIdentifier xMLResourceIdentifier) {
        return ((xMLResourceIdentifier instanceof XMLGrammarDescription) && "http://www.w3.org/2001/XMLSchema".equals(((XMLGrammarDescription) xMLResourceIdentifier).getGrammarType())) ? "http://www.w3.org/2001/XMLSchema" : "http://www.w3.org/TR/REC-xml";
    }

    public LSResourceResolver getEntityResolver() {
        return this.fEntityResolver;
    }

    @Override // org.apache.xerces.xni.parser.XMLEntityResolver
    public XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
        String type;
        String namespace;
        String publicId;
        String literalSystemId;
        String baseSystemId;
        LSResourceResolver lSResourceResolver = this.fEntityResolver;
        if (lSResourceResolver == null) {
            return null;
        }
        if (xMLResourceIdentifier == null) {
            literalSystemId = null;
            baseSystemId = null;
            type = null;
            namespace = null;
            publicId = null;
        } else {
            type = getType(xMLResourceIdentifier);
            namespace = xMLResourceIdentifier.getNamespace();
            publicId = xMLResourceIdentifier.getPublicId();
            literalSystemId = xMLResourceIdentifier.getLiteralSystemId();
            baseSystemId = xMLResourceIdentifier.getBaseSystemId();
        }
        LSInput lSInputResolveResource = lSResourceResolver.resolveResource(type, namespace, publicId, literalSystemId, baseSystemId);
        if (lSInputResolveResource == null) {
            return null;
        }
        String publicId2 = lSInputResolveResource.getPublicId();
        String systemId = lSInputResolveResource.getSystemId();
        String baseURI = lSInputResolveResource.getBaseURI();
        InputStream byteStream = lSInputResolveResource.getByteStream();
        Reader characterStream = lSInputResolveResource.getCharacterStream();
        String encoding = lSInputResolveResource.getEncoding();
        String stringData = lSInputResolveResource.getStringData();
        XMLInputSource xMLInputSource = new XMLInputSource(publicId2, systemId, baseURI);
        if (characterStream != null) {
            xMLInputSource.setCharacterStream(characterStream);
        } else if (byteStream != null) {
            xMLInputSource.setByteStream(byteStream);
        } else if (stringData != null && stringData.length() != 0) {
            xMLInputSource.setCharacterStream(new StringReader(stringData));
        }
        xMLInputSource.setEncoding(encoding);
        return xMLInputSource;
    }

    public void setEntityResolver(LSResourceResolver lSResourceResolver) {
        this.fEntityResolver = lSResourceResolver;
    }
}
