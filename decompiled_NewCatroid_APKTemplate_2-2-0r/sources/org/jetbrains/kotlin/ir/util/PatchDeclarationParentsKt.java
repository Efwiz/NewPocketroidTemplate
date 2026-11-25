package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: PatchDeclarationParents.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a%\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"patchDeclarationParents", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "initialParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PatchDeclarationParentsKt {
    public static /* synthetic */ IrElement patchDeclarationParents$default(IrElement irElement, IrDeclarationParent irDeclarationParent, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclarationParent = null;
        }
        return patchDeclarationParents(irElement, irDeclarationParent);
    }

    public static final <T extends IrElement> T patchDeclarationParents(T t, IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        IrVisitorsKt.acceptVoid(t, irDeclarationParent != null ? new PatchDeclarationParentsVisitor(irDeclarationParent) : new PatchDeclarationParentsVisitor());
        return t;
    }
}
