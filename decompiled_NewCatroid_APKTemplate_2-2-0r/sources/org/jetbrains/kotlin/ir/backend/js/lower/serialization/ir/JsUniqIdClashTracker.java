package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: JsDeclarationTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0005H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsUniqIdClashTracker;", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureClashTracker;", "()V", "committedIdSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "commit", "", "declaration", "signature", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsUniqIdClashTracker implements IdSignatureClashTracker {
    private final Map<IdSignature, IrDeclaration> committedIdSignatures = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.backend.common.serialization.IdSignatureClashTracker
    public void commit(IrDeclaration declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(signature, "signature");
        if (signature.isPubliclyVisible()) {
            if (this.committedIdSignatures.containsKey(signature)) {
                IrDeclaration irDeclaration = this.committedIdSignatures.get(signature);
                Intrinsics.checkNotNull(irDeclaration);
                IrDeclaration irDeclaration2 = irDeclaration;
                IrDeclarationParent parent = declaration.getParent();
                IrDeclarationParent parent2 = irDeclaration2.getParent();
                if (!(declaration instanceof IrTypeParameter) || !(parent instanceof IrSimpleFunction) || !(parent2 instanceof IrSimpleFunction) || ((IrSimpleFunction) parent).getCorrespondingPropertySymbol() != ((IrSimpleFunction) parent2).getCorrespondingPropertySymbol()) {
                    throw new IllegalStateException(("IdSignature clash: " + signature + "; Existed declaration " + RenderIrElementKt.render$default(irDeclaration2, (DumpIrTreeOptions) null, 1, (Object) null) + " clashed with new " + RenderIrElementKt.render$default(declaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
            this.committedIdSignatures.put(signature, declaration);
        }
    }
}
