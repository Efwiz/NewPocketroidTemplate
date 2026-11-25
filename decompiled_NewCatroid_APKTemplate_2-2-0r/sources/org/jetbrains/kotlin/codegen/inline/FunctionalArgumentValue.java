package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: MethodInlinerUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgumentValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "functionalArgument", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "basicValue", "(Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;)V", "getFunctionalArgument", "()Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionalArgumentValue extends BasicValue {
    private final FunctionalArgument functionalArgument;

    public final FunctionalArgument getFunctionalArgument() {
        return this.functionalArgument;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionalArgumentValue(FunctionalArgument functionalArgument, BasicValue basicValue) {
        super(basicValue != null ? basicValue.getType() : null);
        Intrinsics.checkNotNullParameter(functionalArgument, "functionalArgument");
        this.functionalArgument = functionalArgument;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public String toString() {
        return String.valueOf(this.functionalArgument);
    }
}
