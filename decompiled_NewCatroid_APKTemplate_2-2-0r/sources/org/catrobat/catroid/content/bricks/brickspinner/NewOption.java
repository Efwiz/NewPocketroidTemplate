package org.catrobat.catroid.content.bricks.brickspinner;

import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/NewOption.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class NewOption implements Nameable {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f568name;

    public NewOption(String name2) {
        this.f568name = name2;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f568name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f568name = name2;
    }
}
