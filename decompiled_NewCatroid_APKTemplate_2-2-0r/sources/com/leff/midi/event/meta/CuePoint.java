package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/CuePoint.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class CuePoint extends TextualMetaEvent {
    public CuePoint(long tick, long delta, String marker) {
        super(tick, delta, 7, marker);
    }

    public void setCue(String name2) {
        setText(name2);
    }

    public String getCue() {
        return getText();
    }
}
