package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/TrackName.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class TrackName extends TextualMetaEvent {
    public TrackName(long tick, long delta, String name2) {
        super(tick, delta, 3, name2);
    }

    public void setName(String name2) {
        setText(name2);
    }

    public String getTrackName() {
        return getText();
    }
}
