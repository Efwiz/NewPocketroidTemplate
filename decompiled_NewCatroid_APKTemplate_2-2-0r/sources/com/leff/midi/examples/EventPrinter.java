package com.leff.midi.examples;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;
import java.io.File;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/examples/EventPrinter.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class EventPrinter implements MidiEventListener {
    private String mLabel;

    public EventPrinter(String label) {
        this.mLabel = label;
    }

    @Override // com.leff.midi.util.MidiEventListener
    public void onStart(boolean fromBeginning) {
        if (fromBeginning) {
            System.out.println(this.mLabel + " Started!");
        } else {
            System.out.println(this.mLabel + " resumed");
        }
    }

    @Override // com.leff.midi.util.MidiEventListener
    public void onEvent(MidiEvent event, long ms) {
        System.out.println(this.mLabel + " received event: " + event);
    }

    @Override // com.leff.midi.util.MidiEventListener
    public void onStop(boolean finished) {
        if (finished) {
            System.out.println(this.mLabel + " Finished!");
        } else {
            System.out.println(this.mLabel + " paused");
        }
    }

    public static void main(String[] args) {
        try {
            MidiProcessor midiProcessor = new MidiProcessor(new MidiFile(new File("inputmid.mid")));
            EventPrinter eventPrinter = new EventPrinter("Individual Listener");
            midiProcessor.registerEventListener(eventPrinter, Tempo.class);
            midiProcessor.registerEventListener(eventPrinter, NoteOn.class);
            midiProcessor.registerEventListener(new EventPrinter("Listener For All"), MidiEvent.class);
            midiProcessor.start();
            try {
                Thread.sleep(10000L);
                midiProcessor.stop();
                Thread.sleep(10000L);
                midiProcessor.start();
            } catch (Exception unused) {
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
