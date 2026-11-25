package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: BoxedBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0000H&R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "descriptor", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "taint", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BoxedBasicValue extends StrictBasicValue {
    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return this == other;
    }

    public abstract BoxedValueDescriptor getDescriptor();

    public abstract BoxedBasicValue taint();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BoxedBasicValue(Type type) {
        super(type);
        Intrinsics.checkNotNullParameter(type, "type");
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
