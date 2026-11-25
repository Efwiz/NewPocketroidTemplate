package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtBooleanValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.psi.stubs.elements.KtUserTypeElementType;

/* JADX INFO: compiled from: KotlinContractEffectStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J(\u0010\n\u001a\u00020\u00022\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u000e\u001a\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u0011\u001a\u00020\u00022\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00132\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u0014\u001a\u00020\u00022\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00162\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u0017\u001a\u00020\u00022\u0014\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00192\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u001a\u001a\u00020\u00022\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u001c2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010\u001d\u001a\u00020\u00022\u0014\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u001f2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010 \u001a\u00020\u00022\u0014\u0010!\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\"2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010#\u001a\u00020\u00022\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030%2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010&\u001a\u00020\u00022\u0014\u0010'\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030(2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContractSerializationVisitor;", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "", "", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "(Lcom/intellij/psi/stubs/StubOutputStream;)V", "getDataStream", "()Lcom/intellij/psi/stubs/StubOutputStream;", "visitBooleanValueParameterReference", "booleanValueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanValueParameterReference;", "data", "visitCallsEffectDeclaration", "callsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "visitConditionalEffectDeclaration", "conditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/KtConditionalEffectDeclaration;", "visitConstantDescriptor", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "visitIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsInstancePredicate;", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsNullPredicate;", "visitLogicalBinaryOperationContractExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/contracts/description/KtBinaryLogicExpression;", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/KtLogicalNot;", "visitReturnsEffectDeclaration", "returnsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtReturnsEffectDeclaration;", "visitValueParameterReference", "valueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinContractSerializationVisitor extends KtContractDescriptionVisitor {
    private final StubOutputStream dataStream;

    public final StubOutputStream getDataStream() {
        return this.dataStream;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitBooleanValueParameterReference(KtBooleanValueParameterReference ktBooleanValueParameterReference, Object obj) {
        visitBooleanValueParameterReference(ktBooleanValueParameterReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitCallsEffectDeclaration(KtCallsEffectDeclaration ktCallsEffectDeclaration, Object obj) {
        visitCallsEffectDeclaration(ktCallsEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitConditionalEffectDeclaration(KtConditionalEffectDeclaration ktConditionalEffectDeclaration, Object obj) {
        visitConditionalEffectDeclaration(ktConditionalEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitConstantDescriptor(KtConstantReference ktConstantReference, Object obj) throws IOException {
        visitConstantDescriptor(ktConstantReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitIsInstancePredicate(KtIsInstancePredicate ktIsInstancePredicate, Object obj) throws IOException {
        visitIsInstancePredicate(ktIsInstancePredicate, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitIsNullPredicate(KtIsNullPredicate ktIsNullPredicate, Object obj) {
        visitIsNullPredicate(ktIsNullPredicate, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression ktBinaryLogicExpression, Object obj) {
        visitLogicalBinaryOperationContractExpression(ktBinaryLogicExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitLogicalNot(KtLogicalNot ktLogicalNot, Object obj) {
        visitLogicalNot(ktLogicalNot, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitReturnsEffectDeclaration(KtReturnsEffectDeclaration ktReturnsEffectDeclaration, Object obj) throws IOException {
        visitReturnsEffectDeclaration(ktReturnsEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitValueParameterReference(KtValueParameterReference ktValueParameterReference, Object obj) {
        visitValueParameterReference(ktValueParameterReference, (Void) obj);
        return Unit.INSTANCE;
    }

    public KotlinContractSerializationVisitor(StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        this.dataStream = dataStream;
    }

    public void visitConditionalEffectDeclaration(KtConditionalEffectDeclaration conditionalEffect, Void data) {
        Intrinsics.checkNotNullParameter(conditionalEffect, "conditionalEffect");
        this.dataStream.writeInt(KotlinContractEffectType.CONDITIONAL.ordinal());
        KotlinContractSerializationVisitor kotlinContractSerializationVisitor = this;
        conditionalEffect.getEffect().accept(kotlinContractSerializationVisitor, data);
        conditionalEffect.getCondition().accept(kotlinContractSerializationVisitor, data);
    }

    public void visitReturnsEffectDeclaration(KtReturnsEffectDeclaration returnsEffect, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(returnsEffect, "returnsEffect");
        this.dataStream.writeInt(KotlinContractEffectType.RETURNS.ordinal());
        this.dataStream.writeName(returnsEffect.getValue().getName());
    }

    public void visitCallsEffectDeclaration(KtCallsEffectDeclaration callsEffect, Void data) {
        Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
        this.dataStream.writeInt(KotlinContractEffectType.CALLS.ordinal());
        this.dataStream.writeInt(callsEffect.getValueParameterReference().getParameterIndex());
        this.dataStream.writeInt(callsEffect.getKind().ordinal());
    }

    public void visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression binaryLogicExpression, Void data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        this.dataStream.writeInt(KotlinContractEffectType.BOOLEAN_LOGIC.ordinal());
        this.dataStream.writeBoolean(binaryLogicExpression.getKind() == LogicOperationKind.AND);
        KotlinContractSerializationVisitor kotlinContractSerializationVisitor = this;
        binaryLogicExpression.getLeft().accept(kotlinContractSerializationVisitor, data);
        binaryLogicExpression.getRight().accept(kotlinContractSerializationVisitor, data);
    }

    public void visitLogicalNot(KtLogicalNot logicalNot, Void data) {
        Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
        this.dataStream.writeInt(KotlinContractEffectType.NOT.ordinal());
        logicalNot.getArg().accept(this, data);
    }

    public void visitIsInstancePredicate(KtIsInstancePredicate isInstancePredicate, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
        this.dataStream.writeInt(KotlinContractEffectType.IS_INSTANCE.ordinal());
        this.dataStream.writeInt(isInstancePredicate.getArg().getParameterIndex());
        KtUserTypeElementType.serializeType(this.dataStream, (KotlinTypeBean) isInstancePredicate.getType());
        this.dataStream.writeBoolean(isInstancePredicate.getIsNegated());
    }

    public void visitIsNullPredicate(KtIsNullPredicate isNullPredicate, Void data) {
        Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
        this.dataStream.writeInt(KotlinContractEffectType.IS_NULL.ordinal());
        this.dataStream.writeInt(isNullPredicate.getArg().getParameterIndex());
        this.dataStream.writeBoolean(isNullPredicate.getIsNegated());
    }

    public void visitConstantDescriptor(KtConstantReference constantReference, Void data) throws IOException {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        this.dataStream.writeInt(KotlinContractEffectType.CONSTANT.ordinal());
        this.dataStream.writeName(constantReference.getName());
    }

    public void visitValueParameterReference(KtValueParameterReference valueParameterReference, Void data) {
        Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
        this.dataStream.writeInt(KotlinContractEffectType.PARAMETER_REFERENCE.ordinal());
        this.dataStream.writeInt(valueParameterReference.getParameterIndex());
    }

    public void visitBooleanValueParameterReference(KtBooleanValueParameterReference booleanValueParameterReference, Void data) {
        Intrinsics.checkNotNullParameter(booleanValueParameterReference, "booleanValueParameterReference");
        this.dataStream.writeInt(KotlinContractEffectType.BOOLEAN_PARAMETER_REFERENCE.ordinal());
        this.dataStream.writeInt(booleanValueParameterReference.getParameterIndex());
    }
}
