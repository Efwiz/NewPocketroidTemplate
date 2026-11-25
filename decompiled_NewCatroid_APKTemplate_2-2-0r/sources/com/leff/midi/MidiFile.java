package com.leff.midi;

import com.leff.midi.util.MidiUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/MidiFile.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiFile {
    public static final int DEFAULT_RESOLUTION = 480;
    public static final int HEADER_SIZE = 14;
    public static final byte[] IDENTIFIER = {77, 84, 104, 100};
    private int mResolution;
    private int mTrackCount;
    private List<MidiTrack> mTracks;
    private int mType;

    public MidiFile() {
        this(480);
    }

    public MidiFile(int resolution) {
        this(resolution, new ArrayList());
    }

    public MidiFile(int resolution, List<MidiTrack> tracks) {
        this.mResolution = resolution < 0 ? 480 : resolution;
        this.mTracks = tracks != null ? tracks : new ArrayList<>();
        int size = tracks.size();
        this.mTrackCount = size;
        this.mType = size <= 1 ? 0 : 1;
    }

    public MidiFile(File fileIn) throws IOException {
        this(new FileInputStream(fileIn));
    }

    public MidiFile(InputStream rawIn) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(rawIn);
        byte[] bArr = new byte[14];
        bufferedInputStream.read(bArr);
        initFromBuffer(bArr);
        this.mTracks = new ArrayList();
        for (int i = 0; i < this.mTrackCount; i++) {
            this.mTracks.add(new MidiTrack(bufferedInputStream));
        }
    }

    public void setType(int type) {
        if (type < 0) {
            type = 0;
        } else if (type > 2 || (type == 0 && this.mTrackCount > 1)) {
            type = 1;
        }
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }

    public int getTrackCount() {
        return this.mTrackCount;
    }

    public void setResolution(int res) {
        if (res >= 0) {
            this.mResolution = res;
        }
    }

    public int getResolution() {
        return this.mResolution;
    }

    public long getLengthInTicks() {
        Iterator<MidiTrack> it = this.mTracks.iterator();
        long j = 0;
        while (it.hasNext()) {
            long lengthInTicks = it.next().getLengthInTicks();
            if (lengthInTicks > j) {
                j = lengthInTicks;
            }
        }
        return j;
    }

    public List<MidiTrack> getTracks() {
        return this.mTracks;
    }

    public void addTrack(MidiTrack T) {
        addTrack(T, this.mTracks.size());
    }

    public void addTrack(MidiTrack T, int pos) {
        if (pos > this.mTracks.size()) {
            pos = this.mTracks.size();
        } else if (pos < 0) {
            pos = 0;
        }
        this.mTracks.add(pos, T);
        int size = this.mTracks.size();
        this.mTrackCount = size;
        this.mType = size > 1 ? 1 : 0;
    }

    public void removeTrack(int pos) {
        if (pos < 0 || pos >= this.mTracks.size()) {
            return;
        }
        this.mTracks.remove(pos);
        int size = this.mTracks.size();
        this.mTrackCount = size;
        this.mType = size <= 1 ? 0 : 1;
    }

    public void writeToFile(File outFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        fileOutputStream.write(IDENTIFIER);
        fileOutputStream.write(MidiUtil.intToBytes(6, 4));
        fileOutputStream.write(MidiUtil.intToBytes(this.mType, 2));
        fileOutputStream.write(MidiUtil.intToBytes(this.mTrackCount, 2));
        fileOutputStream.write(MidiUtil.intToBytes(this.mResolution, 2));
        Iterator<MidiTrack> it = this.mTracks.iterator();
        while (it.hasNext()) {
            it.next().writeToFile(fileOutputStream);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void initFromBuffer(byte[] buffer) {
        if (!MidiUtil.bytesEqual(buffer, IDENTIFIER, 0, 4)) {
            System.out.println("File identifier not MThd. Exiting");
            this.mType = 0;
            this.mTrackCount = 0;
            this.mResolution = 480;
            return;
        }
        this.mType = MidiUtil.bytesToInt(buffer, 8, 2);
        this.mTrackCount = MidiUtil.bytesToInt(buffer, 10, 2);
        this.mResolution = MidiUtil.bytesToInt(buffer, 12, 2);
    }
}
