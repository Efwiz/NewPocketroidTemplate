package com.leff.midi.examples;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/examples/MidiFileFromScratch.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiFileFromScratch {
    public static void main(String[] args) {
        MidiTrack midiTrack = new MidiTrack();
        MidiTrack midiTrack2 = new MidiTrack();
        TimeSignature timeSignature = new TimeSignature();
        timeSignature.setTimeSignature(4, 4, 24, 8);
        Tempo tempo = new Tempo();
        tempo.setBpm(228.0f);
        midiTrack.insertEvent(timeSignature);
        midiTrack.insertEvent(tempo);
        int i = 0;
        while (i < 80) {
            int i2 = i + 1;
            int i3 = i * 480;
            long j = i3;
            NoteOn noteOn = new NoteOn(j, 0, i2, 100);
            NoteOff noteOff = new NoteOff(i3 + 120, 0, i2, 0);
            midiTrack2.insertEvent(noteOn);
            midiTrack2.insertEvent(noteOff);
            midiTrack2.insertNote(0, i + 3, 100, j, 120L);
            i = i2;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(midiTrack);
        arrayList.add(midiTrack2);
        try {
            new MidiFile(480, arrayList).writeToFile(new File("exampleout.mid"));
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
