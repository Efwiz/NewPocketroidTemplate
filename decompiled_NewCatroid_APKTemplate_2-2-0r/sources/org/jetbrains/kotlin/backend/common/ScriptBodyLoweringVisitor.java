package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: Lower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScriptBodyLoweringVisitor;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringVisitor;", "loweringPass", "Lorg/jetbrains/kotlin/backend/common/BodyAndScriptBodyLoweringPass;", "(Lorg/jetbrains/kotlin/backend/common/BodyAndScriptBodyLoweringPass;)V", "visitClass", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptBodyLoweringVisitor extends BodyLoweringVisitor {
    private final BodyAndScriptBodyLoweringPass loweringPass;

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringVisitor, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
        visitClass2(irClass, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptBodyLoweringVisitor(BodyAndScriptBodyLoweringPass loweringPass) {
        super(loweringPass, false);
        Intrinsics.checkNotNullParameter(loweringPass, "loweringPass");
        this.loweringPass = loweringPass;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringVisitor
    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrValueParameter thisReceiver = declaration.getThisReceiver();
        if (thisReceiver != null) {
            thisReceiver.accept(this, declaration);
        }
        Iterator<T> it = declaration.getTypeParameters().iterator();
        while (it.hasNext()) {
            ((IrTypeParameter) it.next()).accept(this, declaration);
        }
        Iterator it2 = new ArrayList(declaration.getDeclarations()).iterator();
        while (it2.hasNext()) {
            ((IrDeclaration) it2.next()).accept(this, declaration);
        }
        if (Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE)) {
            this.loweringPass.lowerScriptBody(declaration, declaration);
        }
    }
}
