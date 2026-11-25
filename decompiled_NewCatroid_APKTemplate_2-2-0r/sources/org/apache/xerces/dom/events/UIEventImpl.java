package org.apache.xerces.dom.events;

import org.w3c.dom.events.UIEvent;
import org.w3c.dom.views.AbstractView;

/* JADX INFO: loaded from: classes13.dex */
public class UIEventImpl extends EventImpl implements UIEvent {
    private int fDetail;
    private AbstractView fView;

    @Override // org.w3c.dom.events.UIEvent
    public int getDetail() {
        return this.fDetail;
    }

    @Override // org.w3c.dom.events.UIEvent
    public AbstractView getView() {
        return this.fView;
    }

    @Override // org.w3c.dom.events.UIEvent
    public void initUIEvent(String str, boolean z, boolean z2, AbstractView abstractView, int i) {
        this.fView = abstractView;
        this.fDetail = i;
        super.initEvent(str, z, z2);
    }
}
