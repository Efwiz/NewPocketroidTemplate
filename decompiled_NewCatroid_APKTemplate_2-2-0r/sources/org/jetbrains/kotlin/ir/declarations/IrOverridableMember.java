package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.Modality;

/* JADX INFO: compiled from: IrOverridableMember.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004R\u0018\u0010\u0005\u001a\u00020\u0006X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrOverridableMember extends IrDeclaration, IrDeclarationWithName, IrDeclarationWithVisibility, IrSymbolOwner {
    Modality getModality();

    void setModality(Modality modality);
}
