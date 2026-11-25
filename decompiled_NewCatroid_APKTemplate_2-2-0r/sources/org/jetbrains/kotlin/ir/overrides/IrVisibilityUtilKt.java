package org.jetbrains.kotlin.ir.overrides;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IrVisibilityUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u0001\u001a\n\u0010\b\u001a\u00020\u0005*\u00020\t¨\u0006\n"}, d2 = {"findMemberWithMaxVisibility", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "members", "", "isVisibleForOverride", "", "overriding", "fromSuper", "isEffectivelyPrivate", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrVisibilityUtilKt {
    public static final boolean isVisibleForOverride(IrOverridableMember overriding, IrOverridableMember fromSuper) {
        Intrinsics.checkNotNullParameter(overriding, "overriding");
        Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
        return !DescriptorVisibilities.isPrivate(fromSuper.getVisibility());
    }

    public static final IrOverridableMember findMemberWithMaxVisibility(Collection<? extends IrOverridableMember> members) {
        Integer numCompare;
        Intrinsics.checkNotNullParameter(members, "members");
        members.isEmpty();
        IrOverridableMember irOverridableMember = null;
        for (IrOverridableMember irOverridableMember2 : members) {
            if (irOverridableMember == null || ((numCompare = DescriptorVisibilities.compare(irOverridableMember.getVisibility(), irOverridableMember2.getVisibility())) != null && numCompare.intValue() < 0)) {
                irOverridableMember = irOverridableMember2;
            }
        }
        if (irOverridableMember != null) {
            return irOverridableMember;
        }
        throw new IllegalStateException("Could not find a visible member".toString());
    }

    private static final boolean isEffectivelyPrivate$isNonPrivate(DescriptorVisibility descriptorVisibility) {
        return Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PUBLIC) || Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PROTECTED) || Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.INTERNAL);
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0080  */
    /* JADX WARN: Code duplicated, block: B:37:0x0087  */
    /* JADX WARN: Code duplicated, block: B:48:? A[RETURN, SYNTHETIC] */
    public static final boolean isEffectivelyPrivate(IrDeclarationWithVisibility irDeclarationWithVisibility) {
        IrClass parentClassOrNull;
        boolean zIsEffectivelyPrivate;
        List overriddenSymbols;
        IrDeclarationWithVisibility irDeclarationWithVisibility2;
        Intrinsics.checkNotNullParameter(irDeclarationWithVisibility, "<this>");
        if (isEffectivelyPrivate$isNonPrivate(irDeclarationWithVisibility.getVisibility())) {
            IrClass parentClassOrNull2 = IrUtilsKt.getParentClassOrNull(irDeclarationWithVisibility);
            if (parentClassOrNull2 != null) {
                return isEffectivelyPrivate(parentClassOrNull2);
            }
            return false;
        }
        if (Intrinsics.areEqual(irDeclarationWithVisibility.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            IrOverridableDeclaration irOverridableDeclaration = irDeclarationWithVisibility instanceof IrOverridableDeclaration ? (IrOverridableDeclaration) irDeclarationWithVisibility : null;
            if (irOverridableDeclaration == null || (overriddenSymbols = irOverridableDeclaration.getOverriddenSymbols()) == null) {
                parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclarationWithVisibility);
                if (parentClassOrNull != null) {
                    zIsEffectivelyPrivate = isEffectivelyPrivate(parentClassOrNull);
                } else {
                    zIsEffectivelyPrivate = false;
                }
                if (!zIsEffectivelyPrivate) {
                    return false;
                }
            } else {
                List list = overriddenSymbols;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    do {
                        if (it.hasNext()) {
                            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
                            irDeclarationWithVisibility2 = owner instanceof IrDeclarationWithVisibility ? (IrDeclarationWithVisibility) owner : null;
                        }
                    } while (irDeclarationWithVisibility2 != null && isEffectivelyPrivate(irDeclarationWithVisibility2));
                    parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclarationWithVisibility);
                    if (parentClassOrNull != null) {
                        zIsEffectivelyPrivate = isEffectivelyPrivate(parentClassOrNull);
                    } else {
                        zIsEffectivelyPrivate = false;
                    }
                    if (!zIsEffectivelyPrivate) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
