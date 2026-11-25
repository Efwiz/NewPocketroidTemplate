package org.jetbrains.kotlin.types.checker;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: KotlinTypeRefiner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0003\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/Ref;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", "setValue", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ref<T> {
    private T value;

    public Ref(T value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final T getValue() {
        return this.value;
    }

    public final void setValue(T t) {
        Intrinsics.checkNotNullParameter(t, "<set-?>");
        this.value = t;
    }
}
