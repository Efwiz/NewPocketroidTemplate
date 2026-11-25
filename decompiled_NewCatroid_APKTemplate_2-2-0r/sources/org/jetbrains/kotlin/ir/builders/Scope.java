package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Scope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JN\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\u0006J>\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006J\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u001c\u0010\u001b\u001a\u00020\u000e2\b\b\u0002\u0010\u001c\u001a\u00020\u000e2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ\b\u0010\u001d\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/Scope;", "", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "lastTemporaryIndex", "", "getScopeOwnerSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "createTemporaryVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "irExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "nameHint", "", "isMutable", "", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "startOffset", "endOffset", "createTemporaryVariableDeclaration", "getLocalDeclarationParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getNameForTemporary", "inventNameForTemporary", "prefix", "nextTemporaryIndex", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Scope {
    private int lastTemporaryIndex;
    private final IrSymbol scopeOwnerSymbol;

    public Scope(IrSymbol scopeOwnerSymbol) {
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        this.scopeOwnerSymbol = scopeOwnerSymbol;
    }

    public final IrSymbol getScopeOwnerSymbol() {
        return this.scopeOwnerSymbol;
    }

    public final IrDeclarationParent getLocalDeclarationParent() {
        if (!this.scopeOwnerSymbol.isBound()) {
            throw new AssertionError("Unbound symbol: " + this.scopeOwnerSymbol);
        }
        IrSymbolOwner owner = this.scopeOwnerSymbol.getOwner();
        if (owner instanceof IrDeclarationParent) {
            return (IrDeclarationParent) owner;
        }
        if (!(owner instanceof IrDeclaration)) {
            throw new AssertionError("Not a declaration: " + owner);
        }
        return ((IrDeclaration) owner).getParent();
    }

    private final int nextTemporaryIndex() {
        int i = this.lastTemporaryIndex;
        this.lastTemporaryIndex = i + 1;
        return i;
    }

    public static /* synthetic */ String inventNameForTemporary$default(Scope scope, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = Constants.TMP_DIRECTORY_NAME;
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        return scope.inventNameForTemporary(str, str2);
    }

    public final String inventNameForTemporary(String prefix, String nameHint) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        int iNextTemporaryIndex = nextTemporaryIndex();
        return (nameHint != null ? new StringBuilder().append(prefix).append(iNextTemporaryIndex).append('_').append(nameHint) : new StringBuilder().append(prefix).append(iNextTemporaryIndex)).toString();
    }

    private final String getNameForTemporary(String nameHint) {
        return inventNameForTemporary(Constants.TMP_DIRECTORY_NAME, nameHint);
    }

    public static /* synthetic */ IrVariable createTemporaryVariableDeclaration$default(Scope scope, IrType irType, String str, boolean z, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = null;
        }
        String str2 = str;
        if ((i3 & 4) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i3 & 8) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE;
        }
        return scope.createTemporaryVariableDeclaration(irType, str2, z2, irDeclarationOrigin, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrVariable createTemporaryVariableDeclaration(IrType irType, String nameHint, boolean isMutable, IrDeclarationOrigin origin, int startOffset, int endOffset) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Name nameIdentifier = Name.identifier(getNameForTemporary(nameHint));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariableImpl irVariableImpl = new IrVariableImpl(startOffset, endOffset, origin, new IrVariableSymbolImpl(null, 1, 0 == true ? 1 : 0), nameIdentifier, irType, isMutable, false, false);
        irVariableImpl.setParent(getLocalDeclarationParent());
        return irVariableImpl;
    }

    public final IrVariable createTemporaryVariable(IrExpression irExpression, String nameHint, boolean isMutable, IrDeclarationOrigin origin, IrType irType, int startOffset, int endOffset) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        if (irType == null) {
            irType = irExpression.getType();
        }
        IrVariable irVariableCreateTemporaryVariableDeclaration = createTemporaryVariableDeclaration(irType, nameHint, isMutable, origin, startOffset, endOffset);
        irVariableCreateTemporaryVariableDeclaration.setInitializer(irExpression);
        return irVariableCreateTemporaryVariableDeclaration;
    }
}
