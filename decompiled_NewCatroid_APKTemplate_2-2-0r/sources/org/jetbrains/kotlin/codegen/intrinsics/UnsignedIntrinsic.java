package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: Java8Unsigned.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/UnsignedIntrinsic;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "targetDescriptor", "", "(Ljava/lang/String;)V", "isApplicableToOverload", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnsignedIntrinsic extends IntrinsicMethod {
    private final String targetDescriptor;

    public UnsignedIntrinsic(String targetDescriptor) {
        Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
        this.targetDescriptor = targetDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    public boolean isApplicableToOverload(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.getPackageFragment() instanceof PackageFragmentDescriptor) {
            return true;
        }
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
        if (valueParameterDescriptor == null) {
            return true;
        }
        ClassifierDescriptor this$0 = valueParameterDescriptor.getType().getConstructor().getThis$0();
        if (this$0 == null) {
            throw new AssertionError("Unexpected descriptor for unsigned intrinsic: " + descriptor);
        }
        return Intrinsics.areEqual(this$0.getShortName().asString(), this.targetDescriptor);
    }
}
