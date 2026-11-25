package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: TailCallOptimization.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/FromSuspensionPointValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FromSuspensionPointValue extends BasicValue {
    public static final FromSuspensionPointValue INSTANCE = new FromSuspensionPointValue();

    private FromSuspensionPointValue() {
        super(AsmTypes.OBJECT_TYPE);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return other instanceof FromSuspensionPointValue;
    }
}
