package org.catrobat.catroid.pocketmusic.note;

import androidx.collection.LongSparseArray;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/Track.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Track implements Serializable {
    private static final long serialVersionUID = 7483021689872527955L;
    private LongSparseArray<List<NoteEvent>> events;
    private MusicalInstrument instrument;
    private MusicalKey key;
    private long lastTick;

    public Track(MusicalKey key, MusicalInstrument instrument) {
        this.events = new LongSparseArray<>();
        this.instrument = instrument;
        this.key = key;
        this.lastTick = 0L;
    }

    public Track(Track track) {
        this.events = new LongSparseArray<>();
        this.instrument = track.getInstrument();
        this.key = track.getKey();
        this.lastTick = track.getLastTick();
        Iterator<Long> it = track.getSortedTicks().iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            LinkedList linkedList = new LinkedList();
            this.events.put(jLongValue, linkedList);
            Iterator<NoteEvent> it2 = track.getNoteEventsForTick(jLongValue).iterator();
            while (it2.hasNext()) {
                linkedList.add(new NoteEvent(it2.next()));
            }
        }
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public MusicalKey getKey() {
        return this.key;
    }

    public void addNoteEvent(long tick, NoteEvent noteEvent) {
        List<NoteEvent> linkedList;
        if (this.events.get(tick) != null) {
            linkedList = this.events.get(tick);
        } else {
            linkedList = new LinkedList<>();
            this.events.put(tick, linkedList);
        }
        if (!noteEvent.isNoteOn()) {
            this.lastTick = tick;
        }
        if (eventListAlreadyContainsNoteEventWithTick(tick, noteEvent)) {
            return;
        }
        linkedList.add(noteEvent);
    }

    private boolean eventListAlreadyContainsNoteEventWithTick(long tick, NoteEvent noteEvent) {
        for (int i = 0; i < this.events.size(); i++) {
            long jKeyAt = this.events.keyAt(i);
            if (jKeyAt == tick && this.events.get(jKeyAt).contains(noteEvent)) {
                return true;
            }
        }
        return false;
    }

    public List<NoteEvent> getNoteEventsForTick(long tick) {
        List<NoteEvent> list = this.events.get(tick);
        Collections.sort(list, new Comparator<NoteEvent>() { // from class: org.catrobat.catroid.pocketmusic.note.Track.1
            @Override // java.util.Comparator
            public int compare(NoteEvent noteEvent1, NoteEvent noteEvent2) {
                if (noteEvent1.isNoteOn() == noteEvent2.isNoteOn()) {
                    return 0;
                }
                return noteEvent1.isNoteOn() ? 1 : -1;
            }
        });
        return list;
    }

    public Set<Long> getSortedTicks() {
        TreeSet treeSet = new TreeSet();
        for (int i = 0; i < this.events.size(); i++) {
            treeSet.add(Long.valueOf(this.events.keyAt(i)));
        }
        return treeSet;
    }

    public int size() {
        Iterator<Long> it = getSortedTicks().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += this.events.get(it.next().longValue()).size();
        }
        return size;
    }

    public long getLastTick() {
        return this.lastTick;
    }

    public long getTotalTimeInMilliseconds() {
        return NoteLength.tickToMilliseconds(this.lastTick);
    }

    public boolean empty() {
        return size() == 0;
    }

    public int hashCode() {
        return ((((((558 + this.instrument.hashCode()) * 31) + this.events.hashCode()) * 31) + this.key.hashCode()) * 31) + ((int) this.lastTick);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof Track)) {
            Track track = (Track) obj;
            if (track.getInstrument() != getInstrument() || track.getKey() != getKey()) {
                return false;
            }
            Set<Long> sortedTicks = getSortedTicks();
            if (track.getSortedTicks().equals(sortedTicks)) {
                Iterator<Long> it = sortedTicks.iterator();
                while (it.hasNext()) {
                    long jLongValue = it.next().longValue();
                    if (!getNoteEventsForTick(jLongValue).equals(track.getNoteEventsForTick(jLongValue))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "[Track] instrument=" + this.instrument + " key=" + this.key + " size=" + size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
