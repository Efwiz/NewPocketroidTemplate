package org.jetbrains.kotlin.metadata.deserialization;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinaryVersion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0011\u0012\n\u0010\u0002\u001a\u00020\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u001e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0000J\u001e\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0000J\b\u0010\u0018\u001a\u00020\u0012H\u0017J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0000H\u0004J\b\u0010\u001b\u001a\u00020\u0012H&J\u0006\u0010\u001c\u001a\u00020\u0003J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "", "numbers", "", "", "([I)V", "major", "getMajor", "()I", "minor", "getMinor", "patch", "getPatch", "rest", "", "getRest", "()Ljava/util/List;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "isAtLeast", "version", "isAtMost", "isCompatible", "isCompatibleTo", "ourVersion", "isCompatibleWithCurrentCompilerVersion", "toArray", "toString", "", "Companion", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BinaryVersion {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_LENGTH = 1024;
    private final int major;
    private final int minor;
    private final int[] numbers;
    private final int patch;
    private final List<Integer> rest;

    @JvmStatic
    public static final int[] parseVersionArray(String str) {
        return INSTANCE.parseVersionArray(str);
    }

    public abstract boolean isCompatibleWithCurrentCompilerVersion();

    public BinaryVersion(int... numbers) {
        List<Integer> listEmptyList;
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        this.numbers = numbers;
        Integer orNull = ArraysKt.getOrNull(numbers, 0);
        this.major = orNull != null ? orNull.intValue() : -1;
        Integer orNull2 = ArraysKt.getOrNull(numbers, 1);
        this.minor = orNull2 != null ? orNull2.intValue() : -1;
        Integer orNull3 = ArraysKt.getOrNull(numbers, 2);
        this.patch = orNull3 != null ? orNull3.intValue() : -1;
        if (numbers.length > 3) {
            if (numbers.length > 1024) {
                throw new IllegalArgumentException("BinaryVersion with length more than 1024 are not supported. Provided length " + numbers.length + '.');
            }
            listEmptyList = CollectionsKt.toList(ArraysKt.asList(numbers).subList(3, numbers.length));
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.rest = listEmptyList;
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

    public final List<Integer> getRest() {
        return this.rest;
    }

    @Deprecated(message = "Please use isCompatibleWithCurrentCompilerVersion()", replaceWith = @ReplaceWith(expression = "isCompatibleWithCurrentCompilerVersion()", imports = {}))
    public boolean isCompatible() {
        return isCompatibleWithCurrentCompilerVersion();
    }

    /* JADX INFO: renamed from: toArray, reason: from getter */
    public final int[] getNumbers() {
        return this.numbers;
    }

    protected final boolean isCompatibleTo(BinaryVersion ourVersion) {
        Intrinsics.checkNotNullParameter(ourVersion, "ourVersion");
        int i = this.major;
        if (i == 0) {
            if (ourVersion.major == 0 && this.minor == ourVersion.minor) {
                return true;
            }
        } else if (i == ourVersion.major && this.minor <= ourVersion.minor) {
            return true;
        }
        return false;
    }

    public final boolean isAtLeast(BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return isAtLeast(version.major, version.minor, version.patch);
    }

    public final boolean isAtLeast(int major, int minor, int patch) {
        int i = this.major;
        if (i > major) {
            return true;
        }
        if (i < major) {
            return false;
        }
        int i2 = this.minor;
        if (i2 > minor) {
            return true;
        }
        return i2 >= minor && this.patch >= patch;
    }

    public final boolean isAtMost(BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return isAtMost(version.major, version.minor, version.patch);
    }

    public final boolean isAtMost(int major, int minor, int patch) {
        int i = this.major;
        if (i < major) {
            return true;
        }
        if (i > major) {
            return false;
        }
        int i2 = this.minor;
        if (i2 < minor) {
            return true;
        }
        return i2 <= minor && this.patch <= patch;
    }

    public String toString() {
        int[] numbers = getNumbers();
        ArrayList arrayList = new ArrayList();
        for (int i : numbers) {
            if (i == -1) {
                break;
            }
            arrayList.add(Integer.valueOf(i));
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.isEmpty() ? "unknown" : CollectionsKt.joinToString$default(arrayList2, ".", null, null, 0, null, null, 62, null);
    }

    public boolean equals(Object other) {
        if (other != null && Intrinsics.areEqual(getClass(), other.getClass())) {
            BinaryVersion binaryVersion = (BinaryVersion) other;
            if (this.major == binaryVersion.major && this.minor == binaryVersion.minor && this.patch == binaryVersion.patch && Intrinsics.areEqual(this.rest, binaryVersion.rest)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.major;
        int i2 = i + (i * 31) + this.minor;
        int i3 = i2 + (i2 * 31) + this.patch;
        return i3 + (i3 * 31) + this.rest.hashCode();
    }

    /* JADX INFO: compiled from: BinaryVersion.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion$Companion;", "", "()V", "MAX_LENGTH", "", GrsBaseInfo.CountryCodeSource.UNKNOWN, "parseVersionArray", "", "string", "", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final int[] parseVersionArray(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{"."}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                Integer intOrNull = StringsKt.toIntOrNull((String) it.next());
                if (intOrNull == null) {
                    return null;
                }
                arrayList.add(Integer.valueOf(intOrNull.intValue()));
            }
            return ArraysKt.toIntArray((Integer[]) arrayList.toArray(new Integer[0]));
        }
    }
}
