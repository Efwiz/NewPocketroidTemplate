package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0010\u0010\u0004\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/InjectedAnotherStubTypeConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/OnlyInputTypeConstraintPosition;", "builderInferenceLambdaOfInjectedStubType", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InjectedAnotherStubTypeConstraintPosition<T> extends ConstraintPosition implements OnlyInputTypeConstraintPosition {
    private final T builderInferenceLambdaOfInjectedStubType;

    public InjectedAnotherStubTypeConstraintPosition(T t) {
        super(null);
        this.builderInferenceLambdaOfInjectedStubType = t;
    }

    public String toString() {
        return "Injected from " + this.builderInferenceLambdaOfInjectedStubType + " builder inference call";
    }
}
