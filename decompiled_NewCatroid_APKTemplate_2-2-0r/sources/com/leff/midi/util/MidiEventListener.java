package com.leff.midi.util;

import com.leff.midi.event.MidiEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/MidiEventListener.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public interface MidiEventListener {
    void onEvent(MidiEvent event, long ms);

    void onStart(boolean fromBeginning);

    void onStop(boolean finished);
}
