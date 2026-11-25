package org.jetbrains.kotlin.contracts.model.structure;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.model.ESExpressionVisitor;
import org.jetbrains.kotlin.contracts.model.ESOperator;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.functors.IsFunctor;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J!\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0012H\u0016¢\u0006\u0002\u0010\u0013R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESIs;", "Lorg/jetbrains/kotlin/contracts/model/ESOperator;", "left", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "functor", "Lorg/jetbrains/kotlin/contracts/model/functors/IsFunctor;", "(Lorg/jetbrains/kotlin/contracts/model/ESValue;Lorg/jetbrains/kotlin/contracts/model/functors/IsFunctor;)V", "getFunctor", "()Lorg/jetbrains/kotlin/contracts/model/functors/IsFunctor;", "getLeft", "()Lorg/jetbrains/kotlin/contracts/model/ESValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "getType", "()Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "accept", ExifInterface.GPS_DIRECTION_TRUE, "visitor", "Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpressionVisitor;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESIs implements ESOperator {
    private final IsFunctor functor;
    private final ESValue left;
    private final ESType type;

    public ESIs(ESValue left, IsFunctor functor) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(functor, "functor");
        this.left = left;
        this.functor = functor;
        this.type = getFunctor().getType();
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESOperator
    public IsFunctor getFunctor() {
        return this.functor;
    }

    public final ESValue getLeft() {
        return this.left;
    }

    public final ESType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.contracts.model.ESExpression
    public <T> T accept(ESExpressionVisitor<? extends T> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitIs(this);
    }
}
