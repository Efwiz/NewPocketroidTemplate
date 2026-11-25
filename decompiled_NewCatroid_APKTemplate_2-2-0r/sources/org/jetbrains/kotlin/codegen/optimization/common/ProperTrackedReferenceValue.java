package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: TrackedReferenceValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/ProperTrackedReferenceValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/TrackedReferenceValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "descriptor", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "descriptors", "", "getDescriptors", "()Ljava/util/Set;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProperTrackedReferenceValue extends TrackedReferenceValue {
    private final ReferenceValueDescriptor descriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProperTrackedReferenceValue(Type type, ReferenceValueDescriptor descriptor) {
        super(type, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
    }

    public final ReferenceValueDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.TrackedReferenceValue
    public Set<ReferenceValueDescriptor> getDescriptors() {
        return SetsKt.setOf(this.descriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return other == this || ((other instanceof ProperTrackedReferenceValue) && Intrinsics.areEqual(((ProperTrackedReferenceValue) other).descriptor, this.descriptor));
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return this.descriptor.hashCode();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public String toString() {
        return "[" + this.descriptor + ']';
    }
}
