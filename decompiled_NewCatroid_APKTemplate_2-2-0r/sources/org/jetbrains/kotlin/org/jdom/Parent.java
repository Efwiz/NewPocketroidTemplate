package org.jetbrains.kotlin.org.jdom;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public interface Parent extends Serializable, Cloneable {
    void canContainContent(Content content, int i, boolean z) throws IllegalAddException;

    Parent getParent();
}
