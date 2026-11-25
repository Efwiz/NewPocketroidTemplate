package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J!\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\u0016¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0006\u0010\u0016\u001a\u00020\u0011J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "Lorg/jetbrains/kotlin/contracts/model/structure/AbstractESValue;", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;Lorg/jetbrains/kotlin/contracts/model/structure/ESType;)V", "getConstantReference", "()Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isNullConstant", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESConstant extends AbstractESValue {
    private final ConstantReference constantReference;
    private final ESType type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ESConstant(ConstantReference constantReference, ESType type) {
        super(type);
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        Intrinsics.checkNotNullParameter(type, "type");
        this.constantReference = constantReference;
        this.type = type;
    }

    public final ConstantReference getConstantReference() {
        return this.constantReference;
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.AbstractESValue, org.jetbrains.kotlin.contracts.model.Computation
    public ESType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstant(this);
    }

    public boolean equals(Object other) {
        return (other instanceof ESConstant) && Intrinsics.areEqual(this.constantReference, ((ESConstant) other).constantReference);
    }

    public int hashCode() {
        return Objects.hashCode(this.constantReference);
    }

    public String toString() {
        return this.constantReference.getName();
    }

    public final boolean isNullConstant() {
        return Intrinsics.areEqual(this.constantReference, ConstantReference.INSTANCE.getNULL()) || Intrinsics.areEqual(this.constantReference, ConstantReference.INSTANCE.getNOT_NULL());
    }
}
