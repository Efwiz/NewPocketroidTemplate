package kotlin;

import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/UIntKt.class
  classes11.dex
 */
/* JADX INFO: compiled from: UInt.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b¢\u0006\u0002\u0010\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0087\b¢\u0006\u0002\u0010\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u0006H\u0087\b¢\u0006\u0002\u0010\u0007\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\bH\u0087\b¢\u0006\u0002\u0010\t\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\nH\u0087\b¢\u0006\u0002\u0010\u000b\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\fH\u0087\b¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {Namer.UINT_FROM_INT, "Lkotlin/UInt;", "", "(B)I", "", "(D)I", "", "(F)I", "", "(I)I", "", "(J)I", "", "(S)I", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UIntKt {
    private static final int toUInt(byte b) {
        return UInt.m2993constructorimpl(b);
    }

    private static final int toUInt(short s) {
        return UInt.m2993constructorimpl(s);
    }

    private static final int toUInt(int i) {
        return UInt.m2993constructorimpl(i);
    }

    private static final int toUInt(long j) {
        return UInt.m2993constructorimpl((int) j);
    }

    private static final int toUInt(float f) {
        return UnsignedKt.doubleToUInt(f);
    }

    private static final int toUInt(double d) {
        return UnsignedKt.doubleToUInt(d);
    }
}
