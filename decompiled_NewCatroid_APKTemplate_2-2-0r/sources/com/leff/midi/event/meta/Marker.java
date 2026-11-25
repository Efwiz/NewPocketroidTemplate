package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/Marker.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class Marker extends TextualMetaEvent {
    public Marker(long tick, long delta, String marker) {
        super(tick, delta, 6, marker);
    }

    public void setMarkerName(String name2) {
        setText(name2);
    }

    public String getMarkerName() {
        return getText();
    }
}
