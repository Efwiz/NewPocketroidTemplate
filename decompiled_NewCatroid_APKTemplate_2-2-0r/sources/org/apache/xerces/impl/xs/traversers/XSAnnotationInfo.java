package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
final class XSAnnotationInfo {
    String fAnnotation;
    int fCharOffset;
    int fColumn;
    int fLine;
    XSAnnotationInfo next;

    XSAnnotationInfo(String str, int i, int i2, int i3) {
        this.fAnnotation = str;
        this.fLine = i;
        this.fColumn = i2;
        this.fCharOffset = i3;
    }

    XSAnnotationInfo(String str, Element element) {
        int characterOffset;
        this.fAnnotation = str;
        if (element instanceof ElementImpl) {
            ElementImpl elementImpl = (ElementImpl) element;
            this.fLine = elementImpl.getLineNumber();
            this.fColumn = elementImpl.getColumnNumber();
            characterOffset = elementImpl.getCharacterOffset();
        } else {
            characterOffset = -1;
            this.fLine = -1;
            this.fColumn = -1;
        }
        this.fCharOffset = characterOffset;
    }
}
