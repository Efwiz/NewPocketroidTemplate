package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;

/* JADX INFO: compiled from: PatchDeclarationParents.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0014¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/PatchDeclarationParentsVisitor;", "Lorg/jetbrains/kotlin/ir/util/DeclarationParentsVisitor;", "containingDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "()V", "handleParent", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "parent", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PatchDeclarationParentsVisitor extends DeclarationParentsVisitor {
    public PatchDeclarationParentsVisitor() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PatchDeclarationParentsVisitor(IrDeclarationParent containingDeclaration) {
        this();
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        getDeclarationParentsStack().push(containingDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.util.DeclarationParentsVisitor
    protected void handleParent(IrDeclaration declaration, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(parent, "parent");
        declaration.setParent(parent);
    }
}
