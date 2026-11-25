package com.android.tools.r8.dex;

import org.jf.dexlib2.dexbacked.raw.ItemType;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D {
    static final /* synthetic */ boolean d = true;
    public final int a;
    public final int b;
    public final int c;

    public D(int i, int i2, int i3, int i4) {
        this.a = i;
        if (!d && i2 != 0) {
            throw new AssertionError();
        }
        this.b = i3;
        this.c = i4;
    }

    public static String b(int i) {
        switch (i) {
            case 0:
                return "Header";
            case 1:
                return "Strings";
            case 2:
                return "Types";
            case 3:
                return "Protos";
            case 4:
                return "Fields";
            case 5:
                return "Methods";
            case 6:
                return "ClassDefs";
            default:
                switch (i) {
                    case 4096:
                        return "Maps";
                    case 4097:
                        return "TypeLists";
                    case 4098:
                        return "AnnotationSetRefs";
                    case 4099:
                        return "AnnotationSets";
                    default:
                        switch (i) {
                            case 8192:
                                return "ClassData";
                            case ItemType.CODE_ITEM /* 8193 */:
                                return "Code";
                            case 8194:
                                return "StringData";
                            case ItemType.DEBUG_INFO_ITEM /* 8195 */:
                                return "DebugInfo";
                            case 8196:
                                return "Annotation";
                            case 8197:
                                return "EncodedArrays";
                            case ItemType.ANNOTATION_DIRECTORY_ITEM /* 8198 */:
                                return "AnnotationsDirectory";
                            default:
                                return "Unknown";
                        }
                }
        }
    }

    final void a(int i) {
    }

    public final String toString() {
        return b(this.a) + " @" + this.c + " " + this.b;
    }
}
