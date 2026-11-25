package org.jetbrains.kotlin.utils.kapt;

import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: MemoryLeakDetector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001f\u0010\u000b\u001a\u0010\u0012\f\u0012\n \r*\u0004\u0018\u00010\u00030\u00030\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/kapt/ClassLoaderData;", "", "classLoader", "Ljava/lang/ClassLoader;", "(Ljava/lang/ClassLoader;)V", "age", "", "getAge", "()I", "setAge", "(I)V", "ref", "Ljava/lang/ref/WeakReference;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getRef", "()Ljava/lang/ref/WeakReference;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassLoaderData {
    private volatile int age;
    private final WeakReference<ClassLoader> ref;

    public ClassLoaderData(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.ref = new WeakReference<>(classLoader);
    }

    public final WeakReference<ClassLoader> getRef() {
        return this.ref;
    }

    public final int getAge() {
        return this.age;
    }

    public final void setAge(int i) {
        this.age = i;
    }
}
