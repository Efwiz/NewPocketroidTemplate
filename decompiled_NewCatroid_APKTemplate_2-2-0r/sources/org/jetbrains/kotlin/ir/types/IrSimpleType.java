package org.jetbrains.kotlin.ir.types;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBase;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u00158FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "Lorg/jetbrains/kotlin/ir/types/impl/IrTypeBase;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getArguments", "()Ljava/util/List;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "hasQuestionMark", "", "getHasQuestionMark$annotations", "()V", "getHasQuestionMark", "()Z", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrSimpleType extends IrTypeBase implements SimpleTypeMarker, TypeArgumentListMarker {
    @Deprecated(level = DeprecationLevel.WARNING, message = "hasQuestionMark has ambiguous meaning. Use isNullable() or isMarkedNullable() instead.")
    public static /* synthetic */ void getHasQuestionMark$annotations() {
    }

    public abstract IrTypeAbbreviation getAbbreviation();

    public abstract List<IrTypeArgument> getArguments();

    public abstract IrClassifierSymbol getClassifier();

    public abstract SimpleTypeNullability getNullability();

    public IrSimpleType(KotlinType kotlinType) {
        super(kotlinType);
    }

    public final boolean getHasQuestionMark() {
        return getNullability() == SimpleTypeNullability.MARKED_NULLABLE;
    }
}
