package org.jetbrains.kotlin.psi.stubs;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: StubUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/StubUtils;", "", "()V", "deserializeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serializeClassId", "", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "classId", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubUtils {
    public static final StubUtils INSTANCE = new StubUtils();

    private StubUtils() {
    }

    @JvmStatic
    public static final ClassId deserializeClassId(StubInputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        StringRef name2 = dataStream.readName();
        if (name2 == null) {
            return null;
        }
        return ClassId.fromString(name2.getString());
    }

    @JvmStatic
    public static final void serializeClassId(StubOutputStream dataStream, ClassId classId) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeName(classId != null ? classId.asString() : null);
    }
}
