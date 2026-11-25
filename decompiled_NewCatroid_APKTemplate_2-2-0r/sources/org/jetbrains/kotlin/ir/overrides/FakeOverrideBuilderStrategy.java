package org.jetbrains.kotlin.ir.overrides;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrOverridingUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B'\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0014H$J\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0014H$J\u0016\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fR \u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideBuilderStrategy;", "", "friendModules", "", "", "", "unimplementedOverridesStrategy", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;)V", "fakeOverrideMember", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "superType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "member", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "linkFakeOverride", "", "fakeOverride", "compatibilityMode", "", "linkFunctionFakeOverride", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "manglerCompatibleMode", "linkPropertyFakeOverride", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "postProcessGeneratedFakeOverride", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FakeOverrideBuilderStrategy {
    private final Map<String, Collection<String>> friendModules;
    private final IrUnimplementedOverridesStrategy unimplementedOverridesStrategy;

    protected abstract void linkFunctionFakeOverride(IrFunctionWithLateBinding function, boolean manglerCompatibleMode);

    protected abstract void linkPropertyFakeOverride(IrPropertyWithLateBinding property, boolean manglerCompatibleMode);

    /* JADX WARN: Multi-variable type inference failed */
    public FakeOverrideBuilderStrategy(Map<String, ? extends Collection<String>> friendModules, IrUnimplementedOverridesStrategy unimplementedOverridesStrategy) {
        Intrinsics.checkNotNullParameter(friendModules, "friendModules");
        Intrinsics.checkNotNullParameter(unimplementedOverridesStrategy, "unimplementedOverridesStrategy");
        this.friendModules = friendModules;
        this.unimplementedOverridesStrategy = unimplementedOverridesStrategy;
    }

    public final IrOverridableMember fakeOverrideMember(IrType superType, IrOverridableMember member, IrClass clazz) {
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return IrOverridingUtilKt.buildFakeOverrideMember(superType, member, clazz, this.friendModules, this.unimplementedOverridesStrategy);
    }

    public final void postProcessGeneratedFakeOverride(IrOverridableMember fakeOverride, IrClass clazz) {
        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        this.unimplementedOverridesStrategy.postProcessGeneratedFakeOverride((IrOverridableDeclaration) fakeOverride, clazz);
    }

    public final void linkFakeOverride(IrOverridableMember fakeOverride, boolean compatibilityMode) {
        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
        if (fakeOverride instanceof IrFunctionWithLateBinding) {
            linkFunctionFakeOverride((IrFunctionWithLateBinding) fakeOverride, compatibilityMode);
        } else {
            if (!(fakeOverride instanceof IrPropertyWithLateBinding)) {
                throw new IllegalStateException(("Unexpected fake override: " + fakeOverride).toString());
            }
            linkPropertyFakeOverride((IrPropertyWithLateBinding) fakeOverride, compatibilityMode);
        }
    }
}
