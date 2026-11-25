package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/CopyrightNotice.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class CopyrightNotice extends TextualMetaEvent {
    public CopyrightNotice(long tick, long delta, String text) {
        super(tick, delta, 2, text);
    }

    public void setNotice(String t) {
        setText(t);
    }

    public String getNotice() {
        return getText();
    }
}
