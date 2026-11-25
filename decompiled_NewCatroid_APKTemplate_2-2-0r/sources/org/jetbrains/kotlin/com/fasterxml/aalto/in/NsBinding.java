package org.jetbrains.kotlin.com.fasterxml.aalto.in;

import javax.xml.XMLConstants;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes3.dex */
final class NsBinding {
    public final String mPrefix;
    public String mURI;
    public static final NsBinding XML_BINDING = new NsBinding("xml", "http://www.w3.org/XML/1998/namespace", null);
    public static final NsBinding XMLNS_BINDING = new NsBinding(XMLConstants.XMLNS_ATTRIBUTE, XMLConstants.XMLNS_ATTRIBUTE_NS_URI, null);

    public NsBinding(String str) {
        if (str == "xml" || str == XMLConstants.XMLNS_ATTRIBUTE) {
            throw new RuntimeException("Trying to create non-singleton binding for ns prefix '" + str + FormatHelper.QUOTE);
        }
        this.mPrefix = str;
        this.mURI = null;
    }

    public static final NsBinding createDefaultNs() {
        return new NsBinding(null);
    }

    private NsBinding(String str, String str2, Object obj) {
        this.mPrefix = str;
        this.mURI = str2;
    }

    public boolean isImmutable() {
        return this == XML_BINDING || this == XMLNS_BINDING;
    }
}
