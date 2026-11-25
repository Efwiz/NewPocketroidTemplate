package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrField.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\u0005¢\u0006\u0002\u0010\u0006J5\u0010)\u001a\u0002H*\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H+0-2\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u0010/J/\u00100\u001a\u000201\"\u0004\b\u0000\u0010+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002H+0-2\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u00102J)\u00103\u001a\u000201\"\u0004\b\u0000\u0010+2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H+052\u0006\u0010.\u001a\u0002H+H\u0016¢\u0006\u0002\u00106R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000e8&X§\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u0013X¦\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\u0019X¦\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001e\u0010\u001cR\u0012\u0010\u001f\u001a\u00020 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0018\u0010#\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "()V", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "initializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getInitializer", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setInitializer", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "isFinal", "", "()Z", "setFinal", "(Z)V", "isStatic", "setStatic", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrField extends IrDeclarationBase implements IrDeclarationParent, IrDeclarationWithVisibility, IrMetadataSourceOwner, IrPossiblyExternalDeclaration {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract IrPropertySymbol getCorrespondingPropertySymbol();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public abstract PropertyDescriptor getDescriptor();

    public abstract IrExpressionBody getInitializer();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrFieldSymbol getSymbol();

    public abstract IrType getType();

    public abstract boolean isFinal();

    public abstract boolean isStatic();

    public abstract void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol);

    public abstract void setFinal(boolean z);

    public abstract void setInitializer(IrExpressionBody irExpressionBody);

    public abstract void setStatic(boolean z);

    public abstract void setType(IrType irType);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitField(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrExpressionBody initializer = getInitializer();
        if (initializer != null) {
            initializer.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrExpressionBody initializer = getInitializer();
        setInitializer(initializer != null ? initializer.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
