package org.jetbrains.kotlin.ir.declarations;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u0007B\u0005¢\u0006\u0002\u0010\bJ5\u0010H\u001a\u0002HI\"\u0004\b\u0000\u0010I\"\u0004\b\u0001\u0010J2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u0002HI\u0012\u0004\u0012\u0002HJ0L2\u0006\u0010M\u001a\u0002HJH\u0016¢\u0006\u0002\u0010NJ/\u0010O\u001a\u00020P\"\u0004\b\u0000\u0010J2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u0002HJ0L2\u0006\u0010M\u001a\u0002HJH\u0016¢\u0006\u0002\u0010QJ)\u0010R\u001a\u00020P\"\u0004\b\u0000\u0010J2\f\u0010S\u001a\b\u0012\u0004\u0012\u0002HJ0T2\u0006\u0010M\u001a\u0002HJH\u0016¢\u0006\u0002\u0010UR\u001a\u0010\t\u001a\u00020\n8&X§\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\b\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u0018\u0010\u0015\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u0018\u0010\u0017\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u0018\u0010\u0019\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012R\u0018\u0010\u001b\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u0010\"\u0004\b\u001c\u0010\u0012R\u0018\u0010\u001d\u001a\u00020\u001eX¦\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0018\u0010#\u001a\u00020$X¦\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*X¦\u000e¢\u0006\f\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0012\u00100\u001a\u000201X¦\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u001e\u00104\u001a\b\u0012\u0004\u0012\u0002050*X¦\u000e¢\u0006\f\u001a\u0004\b6\u0010-\"\u0004\b7\u0010/R\u0012\u00108\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u001a\u0010;\u001a\u0004\u0018\u00010<X¦\u000e¢\u0006\f\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R \u0010A\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010BX¦\u000e¢\u0006\f\u001a\u0004\bD\u0010E\"\u0004\bF\u0010G¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "()V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDescriptor$annotations", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isCompanion", "", "()Z", "setCompanion", "(Z)V", "isData", "setData", "isExpect", "setExpect", "isFun", "setFun", "isInner", "setInner", "isValue", "setValue", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "sealedSubclasses", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getSealedSubclasses", "()Ljava/util/List;", "setSealedSubclasses", "(Ljava/util/List;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "superTypes", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSuperTypes", "setSuperTypes", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "thisReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getThisReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setThisReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "valueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getValueClassRepresentation", "()Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "setValueClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrClass extends IrDeclarationBase implements IrAttributeContainer, IrDeclarationContainer, IrDeclarationWithVisibility, IrMetadataSourceOwner, IrPossiblyExternalDeclaration, IrTypeParametersContainer {
    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    public abstract ClassDescriptor getDescriptor();

    public abstract ClassKind getKind();

    public abstract Modality getModality();

    public abstract List<IrClassSymbol> getSealedSubclasses();

    public abstract SourceElement getSource();

    public abstract List<IrType> getSuperTypes();

    public abstract IrClassSymbol getSymbol();

    public abstract IrValueParameter getThisReceiver();

    public abstract ValueClassRepresentation<IrSimpleType> getValueClassRepresentation();

    public abstract boolean isCompanion();

    public abstract boolean isData();

    public abstract boolean isExpect();

    public abstract boolean isFun();

    public abstract boolean isInner();

    public abstract boolean isValue();

    public abstract void setCompanion(boolean z);

    public abstract void setData(boolean z);

    public abstract void setExpect(boolean z);

    public abstract void setFun(boolean z);

    public abstract void setInner(boolean z);

    public abstract void setKind(ClassKind classKind);

    public abstract void setModality(Modality modality);

    public abstract void setSealedSubclasses(List<? extends IrClassSymbol> list);

    public abstract void setSuperTypes(List<? extends IrType> list);

    public abstract void setThisReceiver(IrValueParameter irValueParameter);

    public abstract void setValue(boolean z);

    public abstract void setValueClassRepresentation(ValueClassRepresentation<IrSimpleType> valueClassRepresentation);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClass2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getTypeParameters().iterator();
        while (it.hasNext()) {
            ((IrTypeParameter) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getDeclarations().iterator();
        while (it2.hasNext()) {
            ((IrDeclaration) it2.next()).accept(visitor, data);
        }
        IrValueParameter thisReceiver = getThisReceiver();
        if (thisReceiver != null) {
            thisReceiver.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeParameters(TransformKt.transformIfNeeded(getTypeParameters(), transformer, data));
        TransformKt.transformInPlace(getDeclarations(), transformer, data);
        IrValueParameter thisReceiver = getThisReceiver();
        setThisReceiver(thisReceiver != null ? thisReceiver.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
