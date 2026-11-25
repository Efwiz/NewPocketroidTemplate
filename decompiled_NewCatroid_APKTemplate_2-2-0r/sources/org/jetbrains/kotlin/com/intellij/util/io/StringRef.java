package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInput;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public final class StringRef {
    public static final StringRef[] EMPTY_ARRAY = new StringRef[0];

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private int f722id;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f723name;
    private final AbstractStringEnumerator store;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
                objArr[0] = "store";
                break;
            case 2:
                objArr[0] = "out";
                break;
            case 5:
            case 7:
                objArr[0] = "in";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/StringRef";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "writeTo";
                break;
            case 4:
                objArr[2] = "getId";
                break;
            case 5:
            case 6:
                objArr[2] = "fromStream";
                break;
            case 7:
            case 8:
                objArr[2] = "stringFromStream";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private StringRef(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.f723name = str;
        this.f722id = -1;
        this.store = null;
    }

    private StringRef(int i, AbstractStringEnumerator abstractStringEnumerator) {
        if (abstractStringEnumerator == null) {
            $$$reportNull$$$0(1);
        }
        this.f722id = i;
        this.store = abstractStringEnumerator;
    }

    public String getString() {
        String str = this.f723name;
        if (str != null) {
            return str;
        }
        try {
            String strValueOf = this.store.valueOf(this.f722id);
            this.f723name = strValueOf;
            return strValueOf;
        } catch (IOException e) {
            this.store.markCorrupted();
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return getString();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof StringRef) && toString().equals(obj.toString()));
    }

    public static String toString(StringRef stringRef) {
        if (stringRef != null) {
            return stringRef.getString();
        }
        return null;
    }

    public static StringRef fromString(String str) {
        if (str == null) {
            return null;
        }
        return new StringRef(str);
    }

    public static StringRef fromStream(DataInput dataInput, AbstractStringEnumerator abstractStringEnumerator) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(5);
        }
        if (abstractStringEnumerator == null) {
            $$$reportNull$$$0(6);
        }
        int i = DataInputOutputUtil.readINT(dataInput);
        if (i != 0) {
            return new StringRef(i, abstractStringEnumerator);
        }
        return null;
    }

    public static String stringFromStream(DataInput dataInput, AbstractStringEnumerator abstractStringEnumerator) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(7);
        }
        if (abstractStringEnumerator == null) {
            $$$reportNull$$$0(8);
        }
        int i = DataInputOutputUtil.readINT(dataInput);
        if (i != 0) {
            return abstractStringEnumerator.valueOf(i);
        }
        return null;
    }

    public static StringRef[] createArray(int i) {
        return i == 0 ? EMPTY_ARRAY : new StringRef[i];
    }
}
