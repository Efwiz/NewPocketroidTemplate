package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;

/* JADX INFO: loaded from: classes12.dex */
public interface VariableDescriptor extends ValueDescriptor {
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    ConstantValue<?> mo4179getCompileTimeInitializer();

    boolean isConst();

    boolean isLateInit();

    boolean isVar();
}
