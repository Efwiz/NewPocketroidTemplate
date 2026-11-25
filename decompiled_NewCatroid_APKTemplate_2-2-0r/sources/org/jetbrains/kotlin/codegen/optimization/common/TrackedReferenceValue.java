package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: TrackedReferenceValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0082\u0001\u0003\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/TrackedReferenceValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "descriptors", "", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "getDescriptors", "()Ljava/util/Set;", "Lorg/jetbrains/kotlin/codegen/optimization/common/MergedTrackedReferenceValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/ProperTrackedReferenceValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/TaintedTrackedReferenceValue;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TrackedReferenceValue extends StrictBasicValue {
    public /* synthetic */ TrackedReferenceValue(Type type, DefaultConstructorMarker defaultConstructorMarker) {
        this(type);
    }

    public abstract Set<ReferenceValueDescriptor> getDescriptors();

    private TrackedReferenceValue(Type type) {
        super(type);
    }
}
