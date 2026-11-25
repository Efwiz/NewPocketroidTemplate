package org.w3c.dom.html;

import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public interface HTMLElement extends Element {
    String getClassName();

    String getDir();

    String getId();

    String getLang();

    String getTitle();

    void setClassName(String str);

    void setDir(String str);

    void setId(String str);

    void setLang(String str);

    void setTitle(String str);
}
