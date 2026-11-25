package org.catrobat.catroid.pocketmusic.note;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/Project.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Project implements Serializable {
    public static final int DEFAULT_BEATS_PER_MINUTE = 60;
    private static final long serialVersionUID = 7396763540934053008L;
    private MusicalBeat beat;
    private int beatsPerMinute;
    private File file;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f590name;
    private Map<String, Track> tracks;
    public static final MusicalBeat DEFAULT_BEAT = MusicalBeat.BEAT_4_4;
    public static final MusicalInstrument DEFAULT_INSTRUMENT = MusicalInstrument.ACOUSTIC_GRAND_PIANO;

    public Project(String name2, MusicalBeat beat, int beatsPerMinute) {
        this.f590name = name2;
        this.beatsPerMinute = beatsPerMinute;
        this.beat = beat;
        this.tracks = new HashMap();
    }

    public Project(Project project) {
        this.f590name = project.getName();
        this.beatsPerMinute = project.getBeatsPerMinute();
        this.beat = project.getBeat();
        this.tracks = new HashMap();
        for (String str : project.tracks.keySet()) {
            this.tracks.put(str, new Track(project.tracks.get(str)));
        }
    }

    public Project(Project project, String name2) {
        this(project);
        this.f590name = name2;
    }

    public String getName() {
        return this.f590name;
    }

    public void setName(String name2) {
        this.f590name = name2;
    }

    public int getBeatsPerMinute() {
        return this.beatsPerMinute;
    }

    public MusicalBeat getBeat() {
        return this.beat;
    }

    public void putTrack(String trackName, Track track) {
        this.tracks.put(trackName, track);
    }

    public Set<String> getTrackNames() {
        return this.tracks.keySet();
    }

    public Track getTrack(String trackName) {
        return this.tracks.get(trackName);
    }

    public long getTotalTimeInMilliseconds() {
        Iterator<Track> it = this.tracks.values().iterator();
        long j = 0;
        while (it.hasNext()) {
            long totalTimeInMilliseconds = it.next().getTotalTimeInMilliseconds();
            if (totalTimeInMilliseconds > j) {
                j = totalTimeInMilliseconds;
            }
        }
        return j;
    }

    public int size() {
        return this.tracks.size();
    }

    public int hashCode() {
        return ((((((XKeySym.XK_dstroke + this.f590name.hashCode()) * 31) + this.beatsPerMinute) * 31) + this.beat.hashCode()) * 31) + this.tracks.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Project)) {
            return false;
        }
        Project project = (Project) obj;
        if (getName().equals(project.getName()) && getBeatsPerMinute() == project.getBeatsPerMinute() && getBeat() == project.getBeat()) {
            return this.tracks.equals(project.tracks);
        }
        return false;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String toString() {
        return "[Project] name=" + this.f590name + " beatsPerMinute=" + this.beatsPerMinute + " trackCount=" + size();
    }
}
