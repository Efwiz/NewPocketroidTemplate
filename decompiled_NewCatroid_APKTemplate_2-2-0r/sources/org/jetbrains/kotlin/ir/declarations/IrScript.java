package org.jetbrains.kotlin.ir.declarations;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrScript.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\u0005¢\u0006\u0002\u0010\u0006J5\u0010B\u001a\u0002HC\"\u0004\b\u0000\u0010C\"\u0004\b\u0001\u0010D2\u0012\u0010E\u001a\u000e\u0012\u0004\u0012\u0002HC\u0012\u0004\u0012\u0002HD0F2\u0006\u0010G\u001a\u0002HDH\u0016¢\u0006\u0002\u0010HJ/\u0010I\u001a\u00020J\"\u0004\b\u0000\u0010D2\u0012\u0010E\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u0002HD0F2\u0006\u0010G\u001a\u0002HDH\u0016¢\u0006\u0002\u0010KJ)\u0010L\u001a\u00020J\"\u0004\b\u0000\u0010D2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HD0N2\u0006\u0010G\u001a\u0002HDH\u0016¢\u0006\u0002\u0010OR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\"\u0010\u0017\"\u0004\b#\u0010\u0019R\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0014X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010\u0017\"\u0004\b&\u0010\u0019R \u0010'\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0014X¦\u000e¢\u0006\f\u001a\u0004\b(\u0010\u0017\"\u0004\b)\u0010\u0019R\u001e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0014X¦\u000e¢\u0006\f\u001a\u0004\b,\u0010\u0017\"\u0004\b-\u0010\u0019R\u001e\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0014X¦\u000e¢\u0006\f\u001a\u0004\b/\u0010\u0017\"\u0004\b0\u0010\u0019R\u001a\u00101\u001a\u0004\u0018\u00010+X¦\u000e¢\u0006\f\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0012\u00106\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u001a\u00109\u001a\u0004\u0018\u00010:X¦\u000e¢\u0006\f\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001a\u0010?\u001a\u0004\u0018\u00010\u001bX¦\u000e¢\u0006\f\u001a\u0004\b@\u0010\u001d\"\u0004\bA\u0010\u001f¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "()V", "baseClass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getBaseClass", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setBaseClass", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "earlierScripts", "", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getEarlierScripts", "()Ljava/util/List;", "setEarlierScripts", "(Ljava/util/List;)V", "earlierScriptsParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getEarlierScriptsParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setEarlierScriptsParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "explicitCallParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getExplicitCallParameters", "setExplicitCallParameters", "implicitReceiversParameters", "getImplicitReceiversParameters", "setImplicitReceiversParameters", "importedScripts", "getImportedScripts", "setImportedScripts", "providedProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getProvidedProperties", "setProvidedProperties", "providedPropertiesParameters", "getProvidedPropertiesParameters", "setProvidedPropertiesParameters", "resultProperty", "getResultProperty", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setResultProperty", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "targetClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getTargetClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "setTargetClass", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "thisReceiver", "getThisReceiver", "setThisReceiver", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrScript extends IrDeclarationBase implements IrDeclarationParent, IrDeclarationWithName, IrMetadataSourceOwner, IrStatementContainer {
    public abstract IrType getBaseClass();

    public abstract IrConstructor getConstructor();

    public abstract List<IrScriptSymbol> getEarlierScripts();

    public abstract IrValueParameter getEarlierScriptsParameter();

    public abstract List<IrVariable> getExplicitCallParameters();

    public abstract List<IrValueParameter> getImplicitReceiversParameters();

    public abstract List<IrScriptSymbol> getImportedScripts();

    public abstract List<IrPropertySymbol> getProvidedProperties();

    public abstract List<IrValueParameter> getProvidedPropertiesParameters();

    public abstract IrPropertySymbol getResultProperty();

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public abstract IrScriptSymbol getSymbol();

    public abstract IrClassSymbol getTargetClass();

    public abstract IrValueParameter getThisReceiver();

    public abstract void setBaseClass(IrType irType);

    public abstract void setConstructor(IrConstructor irConstructor);

    public abstract void setEarlierScripts(List<? extends IrScriptSymbol> list);

    public abstract void setEarlierScriptsParameter(IrValueParameter irValueParameter);

    public abstract void setExplicitCallParameters(List<? extends IrVariable> list);

    public abstract void setImplicitReceiversParameters(List<? extends IrValueParameter> list);

    public abstract void setImportedScripts(List<? extends IrScriptSymbol> list);

    public abstract void setProvidedProperties(List<? extends IrPropertySymbol> list);

    public abstract void setProvidedPropertiesParameters(List<? extends IrValueParameter> list);

    public abstract void setResultProperty(IrPropertySymbol irPropertySymbol);

    public abstract void setTargetClass(IrClassSymbol irClassSymbol);

    public abstract void setThisReceiver(IrValueParameter irValueParameter);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitScript2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getStatements().iterator();
        while (it.hasNext()) {
            ((IrStatement) it.next()).accept(visitor, data);
        }
        IrValueParameter thisReceiver = getThisReceiver();
        if (thisReceiver != null) {
            thisReceiver.accept(visitor, data);
        }
        Iterator<T> it2 = getExplicitCallParameters().iterator();
        while (it2.hasNext()) {
            ((IrVariable) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = getImplicitReceiversParameters().iterator();
        while (it3.hasNext()) {
            ((IrValueParameter) it3.next()).accept(visitor, data);
        }
        Iterator<T> it4 = getProvidedPropertiesParameters().iterator();
        while (it4.hasNext()) {
            ((IrValueParameter) it4.next()).accept(visitor, data);
        }
        IrValueParameter earlierScriptsParameter = getEarlierScriptsParameter();
        if (earlierScriptsParameter != null) {
            earlierScriptsParameter.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        TransformKt.transformInPlace(getStatements(), transformer, data);
        IrValueParameter thisReceiver = getThisReceiver();
        setThisReceiver(thisReceiver != null ? thisReceiver.transform((IrElementTransformer) transformer, (Object) data) : null);
        setExplicitCallParameters(TransformKt.transformIfNeeded(getExplicitCallParameters(), transformer, data));
        setImplicitReceiversParameters(TransformKt.transformIfNeeded(getImplicitReceiversParameters(), transformer, data));
        setProvidedPropertiesParameters(TransformKt.transformIfNeeded(getProvidedPropertiesParameters(), transformer, data));
        IrValueParameter earlierScriptsParameter = getEarlierScriptsParameter();
        setEarlierScriptsParameter(earlierScriptsParameter != null ? earlierScriptsParameter.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
