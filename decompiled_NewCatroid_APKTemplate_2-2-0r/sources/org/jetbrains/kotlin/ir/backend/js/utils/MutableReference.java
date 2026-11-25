package org.jetbrains.kotlin.ir.backend.js.utils;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: Reference.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0003\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", "setValue", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableReference<T> {
    private T value;

    public MutableReference(T t) {
        this.value = t;
    }

    public final T getValue() {
        return this.value;
    }

    public final void setValue(T t) {
        this.value = t;
    }
}
