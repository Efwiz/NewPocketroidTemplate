package org.jetbrains.kotlin.resolve.annotations;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ThrowUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0007"}, d2 = {"JVM_THROWS_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "KOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME", "getKOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "KOTLIN_THROWS_ANNOTATION_FQ_NAME", "getKOTLIN_THROWS_ANNOTATION_FQ_NAME", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ThrowUtilKt {
    public static final FqName JVM_THROWS_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.Throws");
    private static final FqName KOTLIN_THROWS_ANNOTATION_FQ_NAME = new FqName("kotlin.Throws");
    private static final FqName KOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME = new FqName("kotlin.native.Throws");

    public static final FqName getKOTLIN_THROWS_ANNOTATION_FQ_NAME() {
        return KOTLIN_THROWS_ANNOTATION_FQ_NAME;
    }

    public static final FqName getKOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME() {
        return KOTLIN_NATIVE_THROWS_ANNOTATION_FQ_NAME;
    }
}
