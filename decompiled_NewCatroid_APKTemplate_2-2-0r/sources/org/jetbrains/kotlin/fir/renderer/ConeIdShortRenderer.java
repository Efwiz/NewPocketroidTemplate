package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ConeIdShortRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeIdShortRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "()V", "renderCallableId", "", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "renderClassId", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeIdShortRenderer extends ConeIdRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.ConeIdRenderer
    public void renderClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        getBuilder().append(classId.getRelativeClassName().asString());
    }

    @Override // org.jetbrains.kotlin.fir.renderer.ConeIdRenderer
    public void renderCallableId(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        getBuilder().append(callableId.getCallableName());
    }
}
