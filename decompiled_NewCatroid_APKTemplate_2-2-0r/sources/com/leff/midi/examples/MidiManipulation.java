package com.leff.midi.examples;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/examples/MidiManipulation.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiManipulation {
    public static void main(String[] args) {
        File file = new File("example.mid");
        try {
            MidiFile midiFile = new MidiFile(file);
            MidiTrack midiTrack = midiFile.getTracks().get(1);
            ArrayList arrayList = new ArrayList();
            for (MidiEvent midiEvent : midiTrack.getEvents()) {
                if (!midiEvent.getClass().equals(NoteOn.class) && !midiEvent.getClass().equals(NoteOff.class)) {
                    arrayList.add(midiEvent);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                midiTrack.removeEvent((MidiEvent) it.next());
            }
            midiFile.removeTrack(2);
            for (MidiEvent midiEvent2 : midiFile.getTracks().get(0).getEvents()) {
                if (midiEvent2.getClass().equals(Tempo.class)) {
                    Tempo tempo = (Tempo) midiEvent2;
                    tempo.setBpm(tempo.getBpm() / 2.0f);
                }
            }
            try {
                midiFile.writeToFile(file);
            } catch (IOException e) {
                System.err.println("Error writing MIDI file:");
                e.printStackTrace();
            }
        } catch (IOException e2) {
            System.err.println("Error parsing MIDI file:");
            e2.printStackTrace();
        }
    }
}
