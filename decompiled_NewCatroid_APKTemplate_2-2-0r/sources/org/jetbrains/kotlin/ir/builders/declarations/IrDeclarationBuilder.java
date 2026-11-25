package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.IrElementBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrDeclarationBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrElementBuilder;", "()V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDeclarationBuilder extends IrElementBuilder {
    public Name name;
    private IrDeclarationOrigin origin = IrDeclarationOrigin.DEFINED.INSTANCE;
    private DescriptorVisibility visibility;

    public IrDeclarationBuilder() {
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        this.visibility = PUBLIC;
    }

    public final IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    public final void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    public final DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    public final void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<set-?>");
        this.visibility = descriptorVisibility;
    }

    public final Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public final void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public final void updateFrom(IrDeclaration from) {
        DescriptorVisibility PUBLIC;
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrElement) from);
        this.origin = from.getOrigin();
        if (from instanceof IrDeclarationWithVisibility) {
            PUBLIC = ((IrDeclarationWithVisibility) from).getVisibility();
        } else {
            PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        }
        this.visibility = PUBLIC;
    }
}
