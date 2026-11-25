package com.leff.midi.util;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.TimeSignature;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/MetronomeTick.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MetronomeTick extends MidiEvent {
    private int mCurrentBeat;
    private int mCurrentMeasure;
    private int mMetronomeFrequency;
    private double mMetronomeProgress;
    private int mResolution;
    private TimeSignature mSignature;

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent o) {
        return 0;
    }

    @Override // com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 0;
    }

    @Override // com.leff.midi.event.MidiEvent
    public int getSize() {
        return 0;
    }

    public MetronomeTick(TimeSignature sig, int resolution) {
        super(0L, 0L);
        this.mResolution = resolution;
        setTimeSignature(sig);
        this.mCurrentMeasure = 1;
    }

    public void setTimeSignature(TimeSignature sig) {
        this.mSignature = sig;
        this.mCurrentBeat = 0;
        setMetronomeFrequency(sig.getMeter());
    }

    public boolean update(double ticksElapsed) {
        double d = this.mMetronomeProgress + ticksElapsed;
        this.mMetronomeProgress = d;
        int i = this.mMetronomeFrequency;
        if (d < i) {
            return false;
        }
        this.mMetronomeProgress = d % ((double) i);
        int numerator = (this.mCurrentBeat + 1) % this.mSignature.getNumerator();
        this.mCurrentBeat = numerator;
        if (numerator == 0) {
            this.mCurrentMeasure++;
        }
        return true;
    }

    public void setMetronomeFrequency(int meter) {
        if (meter == 12) {
            this.mMetronomeFrequency = this.mResolution / 2;
            return;
        }
        if (meter == 24) {
            this.mMetronomeFrequency = this.mResolution;
        } else if (meter == 48) {
            this.mMetronomeFrequency = this.mResolution * 2;
        } else {
            if (meter != 96) {
                return;
            }
            this.mMetronomeFrequency = this.mResolution * 4;
        }
    }

    public int getBeatNumber() {
        return this.mCurrentBeat + 1;
    }

    public int getMeasure() {
        return this.mCurrentMeasure;
    }

    @Override // com.leff.midi.event.MidiEvent
    public String toString() {
        return "Metronome: " + this.mCurrentMeasure + "\t" + getBeatNumber();
    }
}
