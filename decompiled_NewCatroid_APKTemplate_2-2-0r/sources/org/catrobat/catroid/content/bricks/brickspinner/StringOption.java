package org.catrobat.catroid.content.bricks.brickspinner;

import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/StringOption.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class StringOption implements Nameable {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f571name;

    public StringOption(String name2) {
        this.f571name = name2;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f571name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f571name = name2;
    }

    public boolean equals(Object other) {
        if (other instanceof StringOption) {
            return getName().equals(((StringOption) other).getName());
        }
        return false;
    }

    public int hashCode() {
        return getName().hashCode();
    }
}
