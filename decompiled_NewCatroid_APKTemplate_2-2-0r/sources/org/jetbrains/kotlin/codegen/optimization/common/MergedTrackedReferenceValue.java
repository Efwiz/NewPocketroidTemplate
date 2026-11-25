package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: TrackedReferenceValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/MergedTrackedReferenceValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/TrackedReferenceValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "descriptors", "", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "(Lorg/jetbrains/org/objectweb/asm/Type;Ljava/util/Set;)V", "getDescriptors", "()Ljava/util/Set;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MergedTrackedReferenceValue extends TrackedReferenceValue {
    private final Set<ReferenceValueDescriptor> descriptors;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MergedTrackedReferenceValue(Type type, Set<? extends ReferenceValueDescriptor> descriptors) {
        super(type, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        this.descriptors = descriptors;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.TrackedReferenceValue
    public Set<ReferenceValueDescriptor> getDescriptors() {
        return this.descriptors;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return other == this || ((other instanceof MergedTrackedReferenceValue) && Intrinsics.areEqual(((MergedTrackedReferenceValue) other).getDescriptors(), getDescriptors()));
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return getDescriptors().hashCode();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public String toString() {
        return getDescriptors().toString();
    }
}
