package org.jetbrains.kotlin.ir.types;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: IrTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrTypeSubstitutor;", "Lorg/jetbrains/kotlin/ir/types/BaseIrTypeSubstitutor;", "typeParameters", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "allowEmptySubstitution", "", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Z)V", SchemaSymbols.ATTVAL_SUBSTITUTION, "", "getSubstitutionArgument", "typeParameter", "isEmptySubstitution", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeSubstitutor extends BaseIrTypeSubstitutor {
    private final boolean allowEmptySubstitution;
    private final Map<IrTypeParameterSymbol, IrTypeArgument> substitution;

    public /* synthetic */ IrTypeSubstitutor(List list, List list2, IrBuiltIns irBuiltIns, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, irBuiltIns, (i & 8) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrTypeSubstitutor(List<? extends IrTypeParameterSymbol> typeParameters, List<? extends IrTypeArgument> typeArguments, IrBuiltIns irBuiltIns, boolean z) {
        super(irBuiltIns);
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.allowEmptySubstitution = z;
        typeParameters.size();
        typeArguments.size();
        this.substitution = MapsKt.toMap(CollectionsKt.zip(typeParameters, typeArguments));
    }

    @Override // org.jetbrains.kotlin.ir.types.BaseIrTypeSubstitutor
    public IrTypeArgument getSubstitutionArgument(IrTypeParameterSymbol typeParameter) {
        IrTypeParameter owner;
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        IrTypeArgument irTypeArgument = this.substitution.get(typeParameter);
        if (irTypeArgument != null) {
            return irTypeArgument;
        }
        IrTypeParameterSymbol irTypeParameterSymbol = this.allowEmptySubstitution ? typeParameter : null;
        IrSimpleType defaultType = (irTypeParameterSymbol == null || (owner = irTypeParameterSymbol.getOwner()) == null) ? null : IrTypesKt.getDefaultType(owner);
        if (defaultType != null) {
            return defaultType;
        }
        throw new IllegalStateException(("Unsubstituted type parameter: " + RenderIrElementKt.render$default(typeParameter.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    @Override // org.jetbrains.kotlin.ir.types.BaseIrTypeSubstitutor
    public boolean isEmptySubstitution() {
        return this.substitution.isEmpty();
    }
}
