package org.w3c.dom.stylesheets;

import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public interface StyleSheet {
    boolean getDisabled();

    String getHref();

    MediaList getMedia();

    Node getOwnerNode();

    StyleSheet getParentStyleSheet();

    String getTitle();

    String getType();

    void setDisabled(boolean z);
}
