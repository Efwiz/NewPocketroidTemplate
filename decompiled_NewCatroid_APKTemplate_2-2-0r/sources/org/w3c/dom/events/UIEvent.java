package org.w3c.dom.events;

import org.w3c.dom.views.AbstractView;

/* JADX INFO: loaded from: classes2.dex */
public interface UIEvent extends Event {
    int getDetail();

    AbstractView getView();

    void initUIEvent(String str, boolean z, boolean z2, AbstractView abstractView, int i);
}
