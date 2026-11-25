package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ConeIdRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H&R\u001e\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "", "()V", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getBuilder", "()Ljava/lang/StringBuilder;", "setBuilder", "(Ljava/lang/StringBuilder;)V", "renderCallableId", "", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "renderClassId", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeIdRenderer {
    public StringBuilder builder;

    public abstract void renderCallableId(CallableId callableId);

    public abstract void renderClassId(ClassId classId);

    public final StringBuilder getBuilder() {
        StringBuilder sb = this.builder;
        if (sb != null) {
            return sb;
        }
        Intrinsics.throwUninitializedPropertyAccessException("builder");
        return null;
    }

    public final void setBuilder(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<set-?>");
        this.builder = sb;
    }
}
