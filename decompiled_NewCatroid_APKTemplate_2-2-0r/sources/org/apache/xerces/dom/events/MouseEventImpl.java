package org.apache.xerces.dom.events;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

/* JADX INFO: loaded from: classes13.dex */
public class MouseEventImpl extends UIEventImpl implements MouseEvent {
    private boolean fAltKey;
    private short fButton;
    private int fClientX;
    private int fClientY;
    private boolean fCtrlKey;
    private boolean fMetaKey;
    private EventTarget fRelatedTarget;
    private int fScreenX;
    private int fScreenY;
    private boolean fShiftKey;

    @Override // org.w3c.dom.events.MouseEvent
    public boolean getAltKey() {
        return this.fAltKey;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public short getButton() {
        return this.fButton;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public int getClientX() {
        return this.fClientX;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public int getClientY() {
        return this.fClientY;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public boolean getCtrlKey() {
        return this.fCtrlKey;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public boolean getMetaKey() {
        return this.fMetaKey;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public EventTarget getRelatedTarget() {
        return this.fRelatedTarget;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public int getScreenX() {
        return this.fScreenX;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public int getScreenY() {
        return this.fScreenY;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public boolean getShiftKey() {
        return this.fShiftKey;
    }

    @Override // org.w3c.dom.events.MouseEvent
    public void initMouseEvent(String str, boolean z, boolean z2, AbstractView abstractView, int i, int i2, int i3, int i4, int i5, boolean z3, boolean z4, boolean z5, boolean z6, short s, EventTarget eventTarget) {
        this.fScreenX = i2;
        this.fScreenY = i3;
        this.fClientX = i4;
        this.fClientY = i5;
        this.fCtrlKey = z3;
        this.fAltKey = z4;
        this.fShiftKey = z5;
        this.fMetaKey = z6;
        this.fButton = s;
        this.fRelatedTarget = eventTarget;
        super.initUIEvent(str, z, z2, abstractView, i);
    }
}
