package org.catrobat.catroid.formulaeditor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/ExternToken.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExternToken {
    private int endIndex;
    private int startIndex;

    public ExternToken(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }
}
