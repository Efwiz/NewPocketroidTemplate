package com.leff.midi.event.meta;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/Text.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class Text extends TextualMetaEvent {
    public Text(long tick, long delta, String text) {
        super(tick, delta, 1, text);
    }

    @Override // com.leff.midi.event.meta.TextualMetaEvent
    public void setText(String t) {
        super.setText(t);
    }

    @Override // com.leff.midi.event.meta.TextualMetaEvent
    public String getText() {
        return super.getText();
    }
}
