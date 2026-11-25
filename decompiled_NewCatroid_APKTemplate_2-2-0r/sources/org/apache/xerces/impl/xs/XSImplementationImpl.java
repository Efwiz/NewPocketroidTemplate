package org.apache.xerces.impl.xs;

import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.PSVIDOMImplementationImpl;
import org.apache.xerces.impl.xs.util.LSInputListImpl;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.xs.LSInputList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSException;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.catrobat.catroid.BuildConfig;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.ls.LSInput;

/* JADX INFO: loaded from: classes13.dex */
public class XSImplementationImpl extends PSVIDOMImplementationImpl implements XSImplementation {
    static final XSImplementationImpl singleton = new XSImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.apache.xerces.xs.XSImplementation
    public LSInputList createLSInputList(LSInput[] lSInputArr) {
        int length = lSInputArr != null ? lSInputArr.length : 0;
        return length != 0 ? new LSInputListImpl((LSInput[]) lSInputArr.clone(), length) : LSInputListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSImplementation
    public StringList createStringList(String[] strArr) {
        int length = strArr != null ? strArr.length : 0;
        return length != 0 ? new StringListImpl((String[]) strArr.clone(), length) : StringListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSImplementation
    public XSLoader createXSLoader(StringList stringList) throws XSException {
        XSLoaderImpl xSLoaderImpl = new XSLoaderImpl();
        if (stringList == null) {
            return xSLoaderImpl;
        }
        for (int i = 0; i < stringList.getLength(); i++) {
            if (!stringList.item(i).equals(BuildConfig.VERSION_NAME)) {
                throw new XSException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{stringList.item(i)}));
            }
        }
        return xSLoaderImpl;
    }

    @Override // org.apache.xerces.xs.XSImplementation
    public StringList getRecognizedVersions() {
        return new StringListImpl(new String[]{BuildConfig.VERSION_NAME}, 1);
    }

    @Override // org.apache.xerces.dom.PSVIDOMImplementationImpl, org.apache.xerces.dom.DOMImplementationImpl, org.apache.xerces.dom.CoreDOMImplementationImpl, org.w3c.dom.DOMImplementation
    public boolean hasFeature(String str, String str2) {
        return (str.equalsIgnoreCase("XS-Loader") && (str2 == null || str2.equals(BuildConfig.VERSION_NAME))) || super.hasFeature(str, str2);
    }
}
