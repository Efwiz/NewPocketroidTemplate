package androidx.profileinstaller;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/profileinstaller-1.3.0.aar:classes.jar:androidx/profileinstaller/FileSectionType.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
enum FileSectionType {
    DEX_FILES(0),
    EXTRA_DESCRIPTORS(1),
    CLASSES(2),
    METHODS(3),
    AGGREGATION_COUNT(4);

    private final long mValue;

    FileSectionType(long j) {
        this.mValue = j;
    }

    public long getValue() {
        return this.mValue;
    }

    static FileSectionType fromValue(long j) {
        FileSectionType[] fileSectionTypeArrValues = values();
        for (int i = 0; i < fileSectionTypeArrValues.length; i++) {
            if (fileSectionTypeArrValues[i].getValue() == j) {
                return fileSectionTypeArrValues[i];
            }
        }
        throw new IllegalArgumentException("Unsupported FileSection Type " + j);
    }
}
