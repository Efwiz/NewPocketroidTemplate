package org.jetbrains.kotlin.backend.common.overrides;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;

/* JADX INFO: compiled from: FakeOverrides.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/overrides/DefaultFakeOverrideClassFilter;", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideClassFilter;", "()V", "needToConstructFakeOverrides", "", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultFakeOverrideClassFilter implements FakeOverrideClassFilter {
    public static final DefaultFakeOverrideClassFilter INSTANCE = new DefaultFakeOverrideClassFilter();

    @Override // org.jetbrains.kotlin.backend.common.overrides.FakeOverrideClassFilter
    public boolean needToConstructFakeOverrides(IrClass clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return true;
    }

    private DefaultFakeOverrideClassFilter() {
    }
}
