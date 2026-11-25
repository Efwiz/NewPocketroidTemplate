package org.jetbrains.kotlin.descriptors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ModuleCapability.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleCapability<T> {
    private final String name;

    public ModuleCapability(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
    }

    public final String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}
