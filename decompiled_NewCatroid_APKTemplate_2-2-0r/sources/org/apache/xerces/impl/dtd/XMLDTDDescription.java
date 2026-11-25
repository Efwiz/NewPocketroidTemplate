package org.apache.xerces.impl.dtd;

import java.util.ArrayList;
import java.util.Vector;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLDTDDescription extends XMLResourceIdentifierImpl implements org.apache.xerces.xni.grammars.XMLDTDDescription {
    protected ArrayList fPossibleRoots;
    protected String fRootName;

    public XMLDTDDescription(String str, String str2, String str3, String str4, String str5) {
        this.fRootName = null;
        this.fPossibleRoots = null;
        setValues(str, str2, str3, str4);
        this.fRootName = str5;
        this.fPossibleRoots = null;
    }

    public XMLDTDDescription(XMLResourceIdentifier xMLResourceIdentifier, String str) {
        this.fRootName = null;
        this.fPossibleRoots = null;
        setValues(xMLResourceIdentifier.getPublicId(), xMLResourceIdentifier.getLiteralSystemId(), xMLResourceIdentifier.getBaseSystemId(), xMLResourceIdentifier.getExpandedSystemId());
        this.fRootName = str;
        this.fPossibleRoots = null;
    }

    public XMLDTDDescription(XMLInputSource xMLInputSource) {
        this.fRootName = null;
        this.fPossibleRoots = null;
        setValues(xMLInputSource.getPublicId(), null, xMLInputSource.getBaseSystemId(), xMLInputSource.getSystemId());
        this.fRootName = null;
        this.fPossibleRoots = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof XMLGrammarDescription) || !getGrammarType().equals(((XMLGrammarDescription) obj).getGrammarType())) {
            return false;
        }
        XMLDTDDescription xMLDTDDescription = (XMLDTDDescription) obj;
        String str = this.fRootName;
        if (str != null) {
            String str2 = xMLDTDDescription.fRootName;
            if (str2 != null && !str2.equals(str)) {
                return false;
            }
            ArrayList arrayList = xMLDTDDescription.fPossibleRoots;
            if (arrayList != null && !arrayList.contains(this.fRootName)) {
                return false;
            }
        } else {
            ArrayList arrayList2 = this.fPossibleRoots;
            if (arrayList2 != null) {
                String str3 = xMLDTDDescription.fRootName;
                if (str3 == null) {
                    if (xMLDTDDescription.fPossibleRoots == null) {
                        return false;
                    }
                    int size = arrayList2.size();
                    boolean zContains = false;
                    for (int i = 0; i < size; i++) {
                        zContains = xMLDTDDescription.fPossibleRoots.contains((String) this.fPossibleRoots.get(i));
                        if (zContains) {
                            break;
                        }
                    }
                    if (!zContains) {
                        return false;
                    }
                } else if (!arrayList2.contains(str3)) {
                    return false;
                }
            }
        }
        if (this.fExpandedSystemId != null) {
            if (!this.fExpandedSystemId.equals(xMLDTDDescription.fExpandedSystemId)) {
                return false;
            }
        } else if (xMLDTDDescription.fExpandedSystemId != null) {
            return false;
        }
        if (this.fPublicId != null) {
            return this.fPublicId.equals(xMLDTDDescription.fPublicId);
        }
        return xMLDTDDescription.fPublicId == null;
    }

    @Override // org.apache.xerces.xni.grammars.XMLGrammarDescription
    public String getGrammarType() {
        return "http://www.w3.org/TR/REC-xml";
    }

    @Override // org.apache.xerces.xni.grammars.XMLDTDDescription
    public String getRootName() {
        return this.fRootName;
    }

    @Override // org.apache.xerces.util.XMLResourceIdentifierImpl
    public int hashCode() {
        String str;
        if (this.fExpandedSystemId != null) {
            str = this.fExpandedSystemId;
        } else {
            if (this.fPublicId == null) {
                return 0;
            }
            str = this.fPublicId;
        }
        return str.hashCode();
    }

    public void setPossibleRoots(ArrayList arrayList) {
        this.fPossibleRoots = arrayList;
    }

    public void setPossibleRoots(Vector vector) {
        this.fPossibleRoots = vector != null ? new ArrayList(vector) : null;
    }

    public void setRootName(String str) {
        this.fRootName = str;
        this.fPossibleRoots = null;
    }
}
