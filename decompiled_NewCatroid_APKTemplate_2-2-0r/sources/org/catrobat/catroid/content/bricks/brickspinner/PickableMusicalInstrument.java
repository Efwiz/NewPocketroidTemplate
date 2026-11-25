package org.catrobat.catroid.content.bricks.brickspinner;

import android.content.Context;
import java.io.Serializable;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/PickableMusicalInstrument.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum PickableMusicalInstrument implements Nameable, Serializable {
    PIANO(R.string.piano, 0),
    ELECTRIC_PIANO(R.string.electric_piano, 2),
    CELLO(R.string.cello, 42),
    FLUTE(R.string.flute, 73),
    VIBRAPHONE(R.string.vibraphone, 11),
    ORGAN(R.string.organ, 16),
    GUITAR(R.string.guitar, 24),
    ELECTRIC_GUITAR(R.string.electric_guitar, 26),
    BASS(R.string.bass, 32),
    PIZZICATO(R.string.pizzicato, 45),
    SYNTH_PAD(R.string.synth_pad, 90),
    CHOIR(R.string.choir, 52),
    SYNTH_LEAD(R.string.synth_lead, 80),
    WOODEN_FLUTE(R.string.wooden_flute, 75),
    TROMBONE(R.string.trombone, 57),
    SAXOPHONE(R.string.saxophone, 64),
    BASSOON(R.string.bassoon, 70),
    CLARINET(R.string.clarinet, 71),
    MUSIC_BOX(R.string.music_box, 10),
    STEEL_DRUM(R.string.steel_drum, 114),
    MARIMBA(R.string.marimba, 12);


    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f570name;
    private int nameStringId;
    private int value;

    PickableMusicalInstrument(int nameStringId, int value) {
        this.nameStringId = nameStringId;
        this.value = value;
    }

    public static int getIndexByValue(int value) {
        int i = 0;
        for (PickableMusicalInstrument pickableMusicalInstrument : values()) {
            if (pickableMusicalInstrument.getValue() == value) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f570name;
    }

    public int getValue() {
        return this.value;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f570name = name2;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getString(Context context) {
        return context.getString(this.nameStringId);
    }
}
