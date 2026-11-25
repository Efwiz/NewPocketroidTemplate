package org.catrobat.catroid.content.bricks.brickspinner;

import android.content.Context;
import java.io.Serializable;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/PickableDrum.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum PickableDrum implements Nameable, Serializable {
    SNARE_DRUM(R.string.snare_drum, 38),
    BASS_DRUM(R.string.bass_drum, 35),
    SIDE_STICK(R.string.side_stick, 37),
    CRASH_CYMBAL(R.string.crash_cymbal, 49),
    OPEN_HI_HAT(R.string.open_hi_hat, 46),
    CLOSED_HI_HAT(R.string.closed_hi_hat, 42),
    TAMBOURINE(R.string.tambourine, 54),
    HAND_CLAP(R.string.hand_clap, 39),
    CLAVES(R.string.claves, 75),
    WOOD_BLOCK(R.string.wood_block, 76),
    COWBELL(R.string.cowbell, 56),
    TRIANGLE(R.string.triangle, 81),
    BONGO(R.string.bongo, 60),
    CONGA(R.string.conga, 63),
    CABASA(R.string.cabasa, 69),
    GUIRO(R.string.guiro, 73),
    VIBRASLAP(R.string.vibraslap, 58),
    OPEN_CUICA(R.string.open_cuica, 79);

    private static final long serialVersionUID = 1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f569name;
    private int nameStringId;
    private int value;

    PickableDrum(int nameStringId, int value) {
        this.nameStringId = nameStringId;
        this.value = value;
    }

    public static int getIndexByValue(int value) {
        int i = 0;
        for (PickableDrum pickableDrum : values()) {
            if (pickableDrum.getValue() == value) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f569name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f569name = name2;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getString(Context context) {
        return context.getString(this.nameStringId);
    }
}
