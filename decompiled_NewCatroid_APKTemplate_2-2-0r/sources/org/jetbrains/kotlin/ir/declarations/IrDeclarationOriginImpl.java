package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "name", "", "isSynthetic", "", "(Ljava/lang/String;Z)V", "()Z", "getName", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDeclarationOriginImpl implements IrDeclarationOrigin {
    private final boolean isSynthetic;
    private final String name;

    public IrDeclarationOriginImpl(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.isSynthetic = z;
    }

    public /* synthetic */ IrDeclarationOriginImpl(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
    /* JADX INFO: renamed from: isSynthetic, reason: from getter */
    public boolean getIsSynthetic() {
        return this.isSynthetic;
    }

    public String toString() {
        return getName();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof IrDeclarationOriginImpl) && Intrinsics.areEqual(getName(), ((IrDeclarationOriginImpl) other).getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }
}
