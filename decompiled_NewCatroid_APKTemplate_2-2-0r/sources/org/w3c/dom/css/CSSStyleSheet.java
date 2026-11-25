package org.w3c.dom.css;

import org.w3c.dom.DOMException;
import org.w3c.dom.stylesheets.StyleSheet;

/* JADX INFO: loaded from: classes2.dex */
public interface CSSStyleSheet extends StyleSheet {
    void deleteRule(int i) throws DOMException;

    CSSRuleList getCssRules();

    CSSRule getOwnerRule();

    int insertRule(String str, int i) throws DOMException;
}
