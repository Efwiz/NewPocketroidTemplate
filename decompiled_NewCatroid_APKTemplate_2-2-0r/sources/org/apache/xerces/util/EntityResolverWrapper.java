package org.apache.xerces.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
public class EntityResolverWrapper implements XMLEntityResolver {
    protected EntityResolver fEntityResolver;

    public EntityResolverWrapper() {
    }

    public EntityResolverWrapper(EntityResolver entityResolver) {
        setEntityResolver(entityResolver);
    }

    public EntityResolver getEntityResolver() {
        return this.fEntityResolver;
    }

    @Override // org.apache.xerces.xni.parser.XMLEntityResolver
    public XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException {
        EntityResolver entityResolver;
        String publicId = xMLResourceIdentifier.getPublicId();
        String expandedSystemId = xMLResourceIdentifier.getExpandedSystemId();
        if ((publicId != null || expandedSystemId != null) && (entityResolver = this.fEntityResolver) != null && xMLResourceIdentifier != null) {
            try {
                InputSource inputSourceResolveEntity = entityResolver.resolveEntity(publicId, expandedSystemId);
                if (inputSourceResolveEntity != null) {
                    String publicId2 = inputSourceResolveEntity.getPublicId();
                    String systemId = inputSourceResolveEntity.getSystemId();
                    String baseSystemId = xMLResourceIdentifier.getBaseSystemId();
                    InputStream byteStream = inputSourceResolveEntity.getByteStream();
                    Reader characterStream = inputSourceResolveEntity.getCharacterStream();
                    String encoding = inputSourceResolveEntity.getEncoding();
                    XMLInputSource xMLInputSource = new XMLInputSource(publicId2, systemId, baseSystemId);
                    xMLInputSource.setByteStream(byteStream);
                    xMLInputSource.setCharacterStream(characterStream);
                    xMLInputSource.setEncoding(encoding);
                    return xMLInputSource;
                }
            } catch (SAXException e) {
                e = e;
                Exception exception = e.getException();
                if (exception != null) {
                    e = exception;
                }
                throw new XNIException(e);
            }
        }
        return null;
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.fEntityResolver = entityResolver;
    }
}
