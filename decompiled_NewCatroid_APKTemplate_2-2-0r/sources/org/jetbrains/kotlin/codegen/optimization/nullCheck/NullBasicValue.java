package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;

/* JADX INFO: compiled from: nullabilityValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NullBasicValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullBasicValue extends StrictBasicValue {
    public static final NullBasicValue INSTANCE = new NullBasicValue();

    private NullBasicValue() {
        super(AsmTypes.OBJECT_TYPE);
    }
}
