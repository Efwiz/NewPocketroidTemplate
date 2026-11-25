package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;

/* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\b\u001a\u00020\t*\u00020\nH\u0000\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\u000b"}, d2 = {"parents", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getParents", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lkotlin/sequences/Sequence;", "parentsWithSelf", "getParentsWithSelf", "isLocalNotInner", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LocalDeclarationsLoweringKt {
    public static final Sequence<IrDeclarationParent> getParentsWithSelf(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return SequencesKt.generateSequence(irDeclaration instanceof IrDeclarationParent ? (IrDeclarationParent) irDeclaration : null, new Function1<IrDeclarationParent, IrDeclarationParent>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            public final IrDeclarationParent invoke(IrDeclarationParent it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclaration irDeclaration2 = it instanceof IrDeclaration ? (IrDeclaration) it : null;
                if (irDeclaration2 != null) {
                    return irDeclaration2.getParent();
                }
                return null;
            }
        });
    }

    public static final Sequence<IrDeclarationParent> getParents(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return SequencesKt.generateSequence(irDeclaration.getParent(), new Function1<IrDeclarationParent, IrDeclarationParent>() { // from class: org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt$parents$1
            @Override // kotlin.jvm.functions.Function1
            public final IrDeclarationParent invoke(IrDeclarationParent it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclaration irDeclaration2 = it instanceof IrDeclaration ? (IrDeclaration) it : null;
                if (irDeclaration2 != null) {
                    return irDeclaration2.getParent();
                }
                return null;
            }
        });
    }

    public static final boolean isLocalNotInner(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.LOCAL) && !irClass.getIsInner();
    }
}
