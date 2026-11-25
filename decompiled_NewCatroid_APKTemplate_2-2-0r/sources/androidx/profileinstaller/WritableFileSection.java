package androidx.profileinstaller;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/profileinstaller-1.3.0.aar:classes.jar:androidx/profileinstaller/WritableFileSection.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
class WritableFileSection {
    final byte[] mContents;
    final int mExpectedInflateSize;
    final boolean mNeedsCompression;
    final FileSectionType mType;

    WritableFileSection(FileSectionType fileSectionType, int i, byte[] bArr, boolean z) {
        this.mType = fileSectionType;
        this.mExpectedInflateSize = i;
        this.mContents = bArr;
        this.mNeedsCompression = z;
    }
}
