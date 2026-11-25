package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: StaticMembersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/StaticMembersLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StaticMembersLowering implements DeclarationTransformer {
    private final JsCommonBackendContext context;

    public StaticMembersLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0024  */
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclarationParent parent = declaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null) {
            boolean isStatic = true;
            if (declaration instanceof IrClass) {
                if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) declaration)) {
                    isStatic = false;
                }
            } else if (declaration instanceof IrSimpleFunction) {
                if (!IrDeclarationsKt.isStaticMethodOfClass((IrFunction) declaration) || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) declaration)) {
                    isStatic = false;
                }
            } else if (declaration instanceof IrField) {
                isStatic = ((IrField) declaration).getIsStatic();
            } else {
                isStatic = false;
            }
            if (isStatic) {
                JsCommonBackendContext jsCommonBackendContext = this.context;
                if ((jsCommonBackendContext instanceof JsIrBackendContext) && ExportModelGeneratorKt.isExported(declaration, (JsIrBackendContext) jsCommonBackendContext)) {
                    ((JsIrBackendContext) this.context).getAdditionalExportedDeclarations().add(declaration);
                }
                IrDeclarationParent parent2 = declaration.getParent();
                IrDeclaration irDeclaration = declaration;
                while ((parent2 instanceof IrDeclaration) && !Intrinsics.areEqual(parent2, IrUtilsKt.getFile(irClass))) {
                    irDeclaration = (IrDeclaration) parent2;
                    parent2 = irDeclaration.getParent();
                }
                IrClass irClass2 = irClass;
                int iIndexOf = IrUtilsKt.getFile(irClass2).getDeclarations().indexOf(irDeclaration);
                if (iIndexOf >= 0) {
                    IrUtilsKt.getFile(irClass2).getDeclarations().add(iIndexOf, declaration);
                } else {
                    IrUtilsKt.getFile(irClass2).getDeclarations().add(declaration);
                }
                declaration.setParent(IrUtilsKt.getFile(irClass2));
                return CollectionsKt.emptyList();
            }
        }
        return null;
    }
}
