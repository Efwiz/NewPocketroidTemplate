package org.catrobat.catroid.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ScratchVisibilityState.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public enum ScratchVisibilityState implements Parcelable {
    UNKNOWN(0),
    PRIVATE(1),
    PUBLIC(2);

    public static final Parcelable.Creator<ScratchVisibilityState> CREATOR;
    private static SparseArray<ScratchVisibilityState> visibilityStates = new SparseArray<>();
    private int visibilityState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        for (ScratchVisibilityState scratchVisibilityState : values()) {
            visibilityStates.put(scratchVisibilityState.visibilityState, scratchVisibilityState);
        }
        CREATOR = new Parcelable.Creator<ScratchVisibilityState>() { // from class: org.catrobat.catroid.common.ScratchVisibilityState.1
            @Override // android.os.Parcelable.Creator
            public ScratchVisibilityState createFromParcel(final Parcel source) {
                return ScratchVisibilityState.values()[source.readInt()];
            }

            @Override // android.os.Parcelable.Creator
            public ScratchVisibilityState[] newArray(final int size) {
                return new ScratchVisibilityState[size];
            }
        };
    }

    ScratchVisibilityState(final int visibilityState) {
        this.visibilityState = visibilityState;
    }

    public static ScratchVisibilityState valueOf(int visibilityState) {
        return visibilityStates.get(visibilityState);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }
}
