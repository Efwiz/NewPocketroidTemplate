package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineScopeResolver;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SyntheticAccessorLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Companion", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticAccessorLowering implements FileLoweringPass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JvmBackendContext context;

    public SyntheticAccessorLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LinkedHashSet<IrFunction> linkedHashSet = new LinkedHashSet();
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new SyntheticAccessorTransformer(this.context, irFile, linkedHashSet));
        for (IrFunction irFunction : linkedHashSet) {
            IrDeclarationParent parent = irFunction.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            ((IrDeclarationContainer) parent).getDeclarations().add(irFunction);
        }
    }

    /* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J@\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0004¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SyntheticAccessorLowering$Companion;", "", "()V", "isAccessible", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "currentScope", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "inlineScopeResolver", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "withSuper", "thisObjReference", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "fromOtherClassLoader", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isAccessible(IrSymbol irSymbol, JvmBackendContext context, ScopeWithIr scopeWithIr, IrInlineScopeResolver inlineScopeResolver, boolean z, IrClassSymbol irClassSymbol, boolean z2) {
            Intrinsics.checkNotNullParameter(irSymbol, "<this>");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(inlineScopeResolver, "inlineScopeResolver");
            IrSymbolOwner owner = irSymbol.getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility");
            IrField irFieldResolveFakeOverride = (IrDeclarationWithVisibility) owner;
            if (irFieldResolveFakeOverride instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irFieldResolveFakeOverride;
                if (irFunction.getIsInline() || context.getGetIntrinsic().invoke(irFunction.getSymbol()) != null) {
                    return true;
                }
            }
            if ((irFieldResolveFakeOverride instanceof IrConstructor) && IrUtilsKt.isEnumEntry(AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFieldResolveFakeOverride))) {
                return true;
            }
            Integer visibilityAccessFlag = AsmUtil.getVisibilityAccessFlag(irFieldResolveFakeOverride.getVisibility().getDelegate());
            if (visibilityAccessFlag != null && visibilityAccessFlag.intValue() == 1 && !z) {
                return true;
            }
            boolean z3 = irFieldResolveFakeOverride instanceof IrSimpleFunction;
            if (z3) {
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFieldResolveFakeOverride;
                if ((ToArrayLoweringKt.isNonGenericToArray(irSimpleFunction) || ToArrayLoweringKt.isGenericToArray(irSimpleFunction, context)) && ToArrayLoweringKt.isCollectionSubClass(IrUtilsKt.getParentAsClass(irFieldResolveFakeOverride))) {
                    return true;
                }
            }
            boolean z4 = irFieldResolveFakeOverride instanceof IrField;
            if (z4 && JvmIrUtilsKt.isAssertionsDisabledField((IrField) irFieldResolveFakeOverride, context)) {
                return true;
            }
            if (z3) {
                IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default((IrOverridableDeclaration) irFieldResolveFakeOverride, true, null, 2, null);
                Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
                irFieldResolveFakeOverride = irOverridableDeclarationResolveFakeOverride$default;
            } else if (z4) {
                irFieldResolveFakeOverride = SyntheticAccessorLoweringKt.resolveFakeOverride((IrField) irFieldResolveFakeOverride);
            }
            IrDeclarationParent parent = irFieldResolveFakeOverride.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null) {
                return true;
            }
            Intrinsics.checkNotNull(scopeWithIr);
            IrDeclarationContainer irDeclarationContainerFindContainer = inlineScopeResolver.findContainer(scopeWithIr.getIrElement());
            if (irDeclarationContainerFindContainer == null) {
                return false;
            }
            FqName packageFqName = IrUtilsKt.getPackageFragment((IrDeclaration) irClass).getPackageFqName();
            IrPackageFragment packageFragment = IrUtilsKt.getPackageFragment(irDeclarationContainerFindContainer);
            boolean zAreEqual = Intrinsics.areEqual(packageFqName, packageFragment != null ? packageFragment.getPackageFqName() : null);
            if (visibilityAccessFlag != null && visibilityAccessFlag.intValue() == 2) {
                return Intrinsics.areEqual(irClass, irDeclarationContainerFindContainer);
            }
            if (!z && zAreEqual && visibilityAccessFlag != null && visibilityAccessFlag.intValue() == 0) {
                return true;
            }
            if (!z && zAreEqual && !z2) {
                return true;
            }
            if (irDeclarationContainerFindContainer instanceof IrClass) {
                IrClass irClass2 = (IrClass) irDeclarationContainerFindContainer;
                if (IrUtilsKt.isSubclassOf(irClass2, irClass) && (irClassSymbol == null || IrUtilsKt.isSubclassOf(irClassSymbol.getOwner(), irClass2))) {
                    return true;
                }
            }
            return false;
        }
    }
}
