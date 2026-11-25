package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmBytecodeBinaryVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0011\u0012\n\u0010\u0002\u001a\u00020\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\r\u001a\u00020\u0003J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmBytecodeBinaryVersion;", "", "numbers", "", "", "([I)V", "major", "getMajor", "()I", "minor", "getMinor", "patch", "getPatch", "toArray", "toString", "", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBytecodeBinaryVersion {
    public static final JvmBytecodeBinaryVersion INSTANCE = new JvmBytecodeBinaryVersion(1, 0, 3);
    private final int major;
    private final int minor;
    private final int patch;

    public JvmBytecodeBinaryVersion(int... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        Integer orNull = ArraysKt.getOrNull(numbers, 0);
        this.major = orNull != null ? orNull.intValue() : -1;
        Integer orNull2 = ArraysKt.getOrNull(numbers, 1);
        this.minor = orNull2 != null ? orNull2.intValue() : -1;
        Integer orNull3 = ArraysKt.getOrNull(numbers, 2);
        this.patch = orNull3 != null ? orNull3.intValue() : -1;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final int getPatch() {
        return this.patch;
    }

    public final int[] toArray() {
        return new int[]{this.major, this.minor, this.patch};
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.major);
        if (this.minor != -1) {
            sb.append("." + this.minor);
            if (this.patch != -1) {
                sb.append("." + this.patch);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
