package org.apache.xerces.xs;

import org.apache.xerces.xs.datatypes.ObjectList;

/* JADX INFO: loaded from: classes13.dex */
public interface XSMultiValueFacet extends XSObject {
    XSObjectList getAnnotations();

    ObjectList getEnumerationValues();

    short getFacetKind();

    StringList getLexicalFacetValues();
}
