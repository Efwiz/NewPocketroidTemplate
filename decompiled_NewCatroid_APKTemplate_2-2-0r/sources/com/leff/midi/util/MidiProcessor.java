package com.leff.midi.util;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/MidiProcessor.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiProcessor {
    private static final int PROCESS_RATE_MS = 8;
    private MidiTrackEventQueue[] mEventQueues;
    private MetronomeTick mMetronome;
    private MidiFile mMidiFile;
    private long mMsElapsed;
    private int mPPQ;
    private boolean mRunning;
    private double mTicksElapsed;
    private int mMPQN = Tempo.DEFAULT_MPQN;
    private HashMap<Class<? extends MidiEvent>, List<MidiEventListener>> mEventsToListeners = new HashMap<>();
    private HashMap<MidiEventListener, List<Class<? extends MidiEvent>>> mListenersToEvents = new HashMap<>();

    public MidiProcessor(MidiFile input) {
        this.mMidiFile = input;
        this.mPPQ = input.getResolution();
        this.mMetronome = new MetronomeTick(new TimeSignature(), this.mPPQ);
        reset();
    }

    public synchronized void start() {
        if (this.mRunning) {
            return;
        }
        this.mRunning = true;
        new Thread(new Runnable() { // from class: com.leff.midi.util.MidiProcessor.1
            @Override // java.lang.Runnable
            public void run() {
                MidiProcessor.this.process();
            }
        }).start();
    }

    public void stop() {
        this.mRunning = false;
    }

    public void reset() {
        this.mRunning = false;
        this.mTicksElapsed = 0.0d;
        this.mMsElapsed = 0L;
        this.mMetronome.setTimeSignature(new TimeSignature());
        List<MidiTrack> tracks = this.mMidiFile.getTracks();
        if (this.mEventQueues == null) {
            this.mEventQueues = new MidiTrackEventQueue[tracks.size()];
        }
        for (int i = 0; i < tracks.size(); i++) {
            this.mEventQueues[i] = new MidiTrackEventQueue(tracks.get(i));
        }
    }

    public boolean isStarted() {
        return this.mTicksElapsed > 0.0d;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    protected void onStart(boolean fromBeginning) {
        Iterator<MidiEventListener> it = this.mListenersToEvents.keySet().iterator();
        while (it.hasNext()) {
            it.next().onStart(fromBeginning);
        }
    }

    protected void onStop(boolean finished) {
        Iterator<MidiEventListener> it = this.mListenersToEvents.keySet().iterator();
        while (it.hasNext()) {
            it.next().onStop(finished);
        }
    }

    public void registerEventListener(MidiEventListener mel, Class<? extends MidiEvent> event) {
        List<MidiEventListener> list = this.mEventsToListeners.get(event);
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(mel);
            this.mEventsToListeners.put(event, arrayList);
        } else {
            list.add(mel);
        }
        List<Class<? extends MidiEvent>> list2 = this.mListenersToEvents.get(mel);
        if (list2 == null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(event);
            this.mListenersToEvents.put(mel, arrayList2);
            return;
        }
        list2.add(event);
    }

    public void unregisterEventListener(MidiEventListener mel) {
        List<Class<? extends MidiEvent>> list = this.mListenersToEvents.get(mel);
        if (list == null) {
            return;
        }
        Iterator<Class<? extends MidiEvent>> it = list.iterator();
        while (it.hasNext()) {
            this.mEventsToListeners.get(it.next()).remove(mel);
        }
        this.mListenersToEvents.remove(mel);
    }

    public void unregisterEventListener(MidiEventListener mel, Class<? extends MidiEvent> event) {
        List<MidiEventListener> list = this.mEventsToListeners.get(event);
        if (list != null) {
            list.remove(mel);
        }
        List<Class<? extends MidiEvent>> list2 = this.mListenersToEvents.get(mel);
        if (list2 != null) {
            list2.remove(event);
        }
    }

    public void unregisterAllEventListeners() {
        this.mEventsToListeners.clear();
        this.mListenersToEvents.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void dispatch(MidiEvent event) {
        if (event.getClass().equals(Tempo.class)) {
            this.mMPQN = ((Tempo) event).getMpqn();
        } else if (event.getClass().equals(TimeSignature.class)) {
            boolean z = this.mMetronome.getBeatNumber() != 1;
            this.mMetronome.setTimeSignature((TimeSignature) event);
            if (z) {
                dispatch(this.mMetronome);
            }
        }
        sendOnEventForClass(event, event.getClass());
        sendOnEventForClass(event, MidiEvent.class);
    }

    private void sendOnEventForClass(MidiEvent event, Class<? extends MidiEvent> eventClass) {
        List<MidiEventListener> list = this.mEventsToListeners.get(eventClass);
        if (list == null) {
            return;
        }
        Iterator<MidiEventListener> it = list.iterator();
        while (it.hasNext()) {
            it.next().onEvent(event, this.mMsElapsed);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process() {
        boolean z = true;
        onStart(this.mTicksElapsed < 1.0d);
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (this.mRunning) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            long j = jCurrentTimeMillis2 - jCurrentTimeMillis;
            if (j < 8) {
                try {
                    Thread.sleep(8 - j);
                } catch (Exception unused) {
                }
            } else {
                double dMsToTicks = MidiUtil.msToTicks(j, this.mMPQN, this.mPPQ);
                if (dMsToTicks >= 1.0d) {
                    if (this.mMetronome.update(dMsToTicks)) {
                        dispatch(this.mMetronome);
                    }
                    this.mMsElapsed += j;
                    this.mTicksElapsed += dMsToTicks;
                    int i = 0;
                    boolean z2 = false;
                    while (true) {
                        MidiTrackEventQueue[] midiTrackEventQueueArr = this.mEventQueues;
                        if (i >= midiTrackEventQueueArr.length) {
                            break;
                        }
                        MidiTrackEventQueue midiTrackEventQueue = midiTrackEventQueueArr[i];
                        if (midiTrackEventQueue.hasMoreEvents()) {
                            Iterator<MidiEvent> it = midiTrackEventQueue.getNextEventsUpToTick(this.mTicksElapsed).iterator();
                            while (it.hasNext()) {
                                dispatch(it.next());
                            }
                            if (midiTrackEventQueue.hasMoreEvents()) {
                                z2 = true;
                            }
                        }
                        i++;
                    }
                    if (!z2) {
                        this.mRunning = false;
                        onStop(z);
                    }
                    jCurrentTimeMillis = jCurrentTimeMillis2;
                } else {
                    continue;
                }
            }
        }
        z = false;
        this.mRunning = false;
        onStop(z);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/MidiProcessor$MidiTrackEventQueue.class
      classes10.dex
     */
    private class MidiTrackEventQueue {
        private ArrayList<MidiEvent> mEventsToDispatch = new ArrayList<>();
        private Iterator<MidiEvent> mIterator;
        private MidiEvent mNext;
        private MidiTrack mTrack;

        public MidiTrackEventQueue(MidiTrack track) {
            this.mTrack = track;
            this.mIterator = track.getEvents().iterator();
            if (this.mIterator.hasNext()) {
                this.mNext = this.mIterator.next();
            }
        }

        public ArrayList<MidiEvent> getNextEventsUpToTick(double tick) {
            this.mEventsToDispatch.clear();
            while (true) {
                MidiEvent midiEvent = this.mNext;
                if (midiEvent == null || midiEvent.getTick() > tick) {
                    break;
                }
                this.mEventsToDispatch.add(this.mNext);
                if (this.mIterator.hasNext()) {
                    this.mNext = this.mIterator.next();
                } else {
                    this.mNext = null;
                }
            }
            return this.mEventsToDispatch;
        }

        public boolean hasMoreEvents() {
            return this.mNext != null;
        }
    }
}
