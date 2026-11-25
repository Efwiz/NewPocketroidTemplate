package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: asm7Util.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"visitAnnotableParameterCount", "", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "paramCount", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Asm7UtilKt {
    public static final void visitAnnotableParameterCount(MethodVisitor mv, int i) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        mv.visitAnnotableParameterCount(i, true);
        mv.visitAnnotableParameterCount(i, false);
    }
}
