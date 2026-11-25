package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/Lyrics.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class Lyrics extends TextualMetaEvent {
    public Lyrics(long tick, long delta, String lyric) {
        super(tick, delta, 5, lyric);
    }

    public void setLyric(String t) {
        setText(t);
    }

    public String getLyric() {
        return getText();
    }
}
