package org.jetbrains.kotlin.ir.types;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrCapturedType;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B9\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0003\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\b0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrCapturedTypeSubstitutor;", "Lorg/jetbrains/kotlin/ir/types/BaseIrTypeSubstitutor;", "typeParameters", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "capturedTypes", "Lorg/jetbrains/kotlin/ir/types/impl/IrCapturedType;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "capturedSubstitution", "", "oldSubstitution", "getSubstitutionArgument", "typeParameter", "isEmptySubstitution", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCapturedTypeSubstitutor extends BaseIrTypeSubstitutor {
    private final Map<IrTypeParameterSymbol, IrCapturedType> capturedSubstitution;
    private final Map<IrTypeParameterSymbol, IrTypeArgument> oldSubstitution;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrCapturedTypeSubstitutor(List<? extends IrTypeParameterSymbol> typeParameters, List<? extends IrTypeArgument> typeArguments, List<IrCapturedType> capturedTypes, IrBuiltIns irBuiltIns) {
        super(irBuiltIns);
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(capturedTypes, "capturedTypes");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        typeArguments.size();
        typeParameters.size();
        capturedTypes.size();
        typeParameters.size();
        List<? extends IrTypeParameterSymbol> list = typeParameters;
        this.oldSubstitution = MapsKt.toMap(CollectionsKt.zip(list, typeArguments));
        this.capturedSubstitution = MapsKt.toMap(CollectionsKt.zip(list, capturedTypes));
    }

    @Override // org.jetbrains.kotlin.ir.types.BaseIrTypeSubstitutor
    public IrTypeArgument getSubstitutionArgument(IrTypeParameterSymbol typeParameter) {
        IrTypeProjection irTypeProjectionMakeTypeProjection;
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        IrCapturedType irCapturedType = this.capturedSubstitution.get(typeParameter);
        if (irCapturedType != null && (irTypeProjectionMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(irCapturedType, Variance.INVARIANT)) != null) {
            return irTypeProjectionMakeTypeProjection;
        }
        IrTypeArgument irTypeArgument = this.oldSubstitution.get(typeParameter);
        if (irTypeArgument != null) {
            return irTypeArgument;
        }
        throw new AssertionError("Unsubstituted type parameter: " + RenderIrElementKt.render$default(typeParameter.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null));
    }

    @Override // org.jetbrains.kotlin.ir.types.BaseIrTypeSubstitutor
    public boolean isEmptySubstitution() {
        return this.oldSubstitution.isEmpty();
    }
}
