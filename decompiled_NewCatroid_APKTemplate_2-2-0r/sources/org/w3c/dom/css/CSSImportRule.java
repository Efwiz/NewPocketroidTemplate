package org.w3c.dom.css;

import org.w3c.dom.stylesheets.MediaList;

/* JADX INFO: loaded from: classes2.dex */
public interface CSSImportRule extends CSSRule {
    String getHref();

    MediaList getMedia();

    CSSStyleSheet getStyleSheet();
}
