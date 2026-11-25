package org.w3c.dom.css;

import org.w3c.dom.DOMException;
import org.w3c.dom.stylesheets.MediaList;

/* JADX INFO: loaded from: classes2.dex */
public interface CSSMediaRule extends CSSRule {
    void deleteRule(int i) throws DOMException;

    CSSRuleList getCssRules();

    MediaList getMedia();

    int insertRule(String str, int i) throws DOMException;
}
