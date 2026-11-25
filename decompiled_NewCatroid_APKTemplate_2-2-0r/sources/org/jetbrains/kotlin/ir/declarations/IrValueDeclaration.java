package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrValueDeclaration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u001a\u0010\u0003\u001a\u00020\u00048&X§\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\u0011X¦\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "isAssignable", "", "()Z", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrValueDeclaration extends IrDeclarationWithName, IrSymbolOwner {

    /* JADX INFO: compiled from: IrValueDeclaration.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @ObsoleteDescriptorBasedAPI
        public static /* synthetic */ void getDescriptor$annotations() {
        }
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    ValueDescriptor getDescriptor();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    IrValueSymbol getSymbol();

    IrType getType();

    boolean isAssignable();

    void setType(IrType irType);
}
