package org.catrobat.catroid.common;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.catrobat.catroid.io.StorageOperations;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/SoundInfo.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class SoundInfo implements Cloneable, Nameable, Serializable {
    private static final String TAG = "SoundInfo";
    private static final long serialVersionUID = 1;
    private transient File file;

    @XStreamAsAttribute
    public String fileName;
    boolean midiFile;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    @XStreamAsAttribute
    public String f517name;

    @XStreamAsAttribute
    public String soundId;

    public SoundInfo() {
    }

    public SoundInfo(String name2, File file) {
        this.f517name = name2;
        this.file = file;
        this.fileName = file.getName();
        this.midiFile = false;
        this.soundId = UUID.randomUUID().toString();
    }

    public SoundInfo(String name2, File file, boolean midiFile) {
        this.f517name = name2;
        this.file = file;
        this.fileName = file.getName();
        this.midiFile = midiFile;
        this.soundId = UUID.randomUUID().toString();
    }

    public String getSoundId() {
        return this.soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.soundId, ((SoundInfo) o).soundId);
    }

    public int hashCode() {
        return Objects.hash(this.soundId);
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f517name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f517name = name2;
    }

    public String getXstreamFileName() {
        if (this.file != null) {
            throw new IllegalStateException("This should be used only to deserialize the Object. You should use @getFile() instead.");
        }
        return this.fileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public boolean isMidiFile() {
        return this.midiFile;
    }

    public SoundInfo clone() {
        try {
            return new SoundInfo(this.f517name, StorageOperations.duplicateFile(this.file));
        } catch (IOException unused) {
            throw new RuntimeException(TAG + ": Could not copy file: " + this.file.getAbsolutePath());
        }
    }
}
