package org.catrobat.catroid.userbrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/userbrick/UserDefinedBrickData.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class UserDefinedBrickData {
    UserDefinedBrickDataType type;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/userbrick/UserDefinedBrickData$UserDefinedBrickDataType.class
      classes14.dex
     */
    public enum UserDefinedBrickDataType {
        INPUT,
        LABEL
    }

    public abstract String getName();

    public boolean isInput() {
        return this.type == UserDefinedBrickDataType.INPUT;
    }

    public boolean isLabel() {
        return this.type == UserDefinedBrickDataType.LABEL;
    }

    public UserDefinedBrickDataType getType() {
        return this.type;
    }
}
