package org.w3c.dom.css;

import org.w3c.dom.DOMException;

/* JADX INFO: loaded from: classes2.dex */
public interface CSSCharsetRule extends CSSRule {
    String getEncoding();

    void setEncoding(String str) throws DOMException;
}
