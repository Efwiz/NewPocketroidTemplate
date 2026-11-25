package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: LocalClassGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/LocalClassGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "generateLocalClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "generateObjectLiteral", "ktObjectLiteral", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalClassGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalClassGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrStatement generateObjectLiteral(KtObjectLiteralExpression ktObjectLiteral) {
        Intrinsics.checkNotNullParameter(ktObjectLiteral, "ktObjectLiteral");
        KtObjectLiteralExpression ktObjectLiteralExpression = ktObjectLiteral;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktObjectLiteralExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktObjectLiteralExpression);
        IrType irType = toIrType(GeneratorKt.getTypeInferredByFrontendOrFail(this, ktObjectLiteral));
        IrBlockImpl irBlockImpl = new IrBlockImpl(startOffsetSkippingComments, endOffset, irType, IrStatementOrigin.OBJECT_LITERAL.INSTANCE);
        DeclarationGenerator declarationGenerator = new DeclarationGenerator(getStatementGenerator().getContext());
        KtObjectDeclaration objectDeclaration = ktObjectLiteral.getObjectDeclaration();
        Intrinsics.checkNotNullExpressionValue(objectDeclaration, "getObjectDeclaration(...)");
        IrClass irClassGenerateClassOrObjectDeclaration = declarationGenerator.generateClassOrObjectDeclaration(objectDeclaration);
        irBlockImpl.getStatements().add(irClassGenerateClassOrObjectDeclaration);
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = irClassGenerateClassOrObjectDeclaration.getDescriptor().mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            throw new AssertionError("Object literal should have a primary constructor: " + irClassGenerateClassOrObjectDeclaration.getDescriptor());
        }
        classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.mo6753getDispatchReceiverParameter();
        classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getExtensionReceiverParameter();
        classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters().size();
        irBlockImpl.getStatements().add(IrConstructorCallImpl.INSTANCE.fromSymbolDescriptor(startOffsetSkippingComments, endOffset, irType, getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor), IrStatementOrigin.OBJECT_LITERAL.INSTANCE));
        return irBlockImpl;
    }

    public final IrStatement generateLocalClass(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        return new DeclarationGenerator(getStatementGenerator().getContext()).generateClassOrObjectDeclaration(ktClassOrObject);
    }
}
