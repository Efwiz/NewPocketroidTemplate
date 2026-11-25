package org.apache.xerces.dom;

import org.apache.xerces.util.URI;
import org.w3c.dom.DOMException;
import org.w3c.dom.Notation;

/* JADX INFO: loaded from: classes13.dex */
public class NotationImpl extends NodeImpl implements Notation {
    static final long serialVersionUID = -764632195890658402L;
    protected String baseURI;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f471name;
    protected String publicId;
    protected String systemId;

    public NotationImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl);
        this.f471name = str;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getBaseURI() {
        if (needsSyncData()) {
            synchronizeData();
        }
        String str = this.baseURI;
        if (str == null || str.length() == 0) {
            return this.baseURI;
        }
        try {
            return new URI(this.baseURI).toString();
        } catch (URI.MalformedURIException unused) {
            return null;
        }
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.f471name;
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 12;
    }

    @Override // org.w3c.dom.Notation
    public String getPublicId() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.publicId;
    }

    @Override // org.w3c.dom.Notation
    public String getSystemId() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return this.systemId;
    }

    public void setBaseURI(String str) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.baseURI = str;
    }

    public void setPublicId(String str) {
        if (isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        this.publicId = str;
    }

    public void setSystemId(String str) {
        if (isReadOnly()) {
            throw new DOMException((short) 7, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        this.systemId = str;
    }
}
