package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrFieldBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0005\"\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0005\"\u0004\b\u000b\u0010\u0007R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrFieldBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "isExternal", "", "()Z", "setExternal", "(Z)V", "isFinal", "setFinal", "isStatic", "setStatic", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFieldBuilder extends IrDeclarationBuilder {
    private boolean isExternal;
    private boolean isFinal;
    private boolean isStatic;
    private MetadataSource metadata;
    public IrType type;

    public final IrType getType() {
        IrType irType = this.type;
        if (irType != null) {
            return irType;
        }
        Intrinsics.throwUninitializedPropertyAccessException(ModuleXmlParser.TYPE);
        return null;
    }

    public final void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    /* JADX INFO: renamed from: isFinal, reason: from getter */
    public final boolean getIsFinal() {
        return this.isFinal;
    }

    public final void setFinal(boolean z) {
        this.isFinal = z;
    }

    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public final boolean getIsExternal() {
        return this.isExternal;
    }

    public final void setExternal(boolean z) {
        this.isExternal = z;
    }

    /* JADX INFO: renamed from: isStatic, reason: from getter */
    public final boolean getIsStatic() {
        return this.isStatic;
    }

    public final void setStatic(boolean z) {
        this.isStatic = z;
    }

    public final MetadataSource getMetadata() {
        return this.metadata;
    }

    public final void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }

    public final void updateFrom(IrField from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        setType(from.getType());
        this.isFinal = from.isFinal();
        this.isExternal = from.isExternal();
        this.isStatic = from.isStatic();
        this.metadata = from.getMetadata();
    }
}
