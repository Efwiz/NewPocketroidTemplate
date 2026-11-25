package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: AnonymousInitializerGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/AnonymousInitializerGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "generateAnonymousInitializerDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "ktAnonymousInitializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnonymousInitializerGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnonymousInitializerGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    public final IrAnonymousInitializer generateAnonymousInitializerDeclaration(KtAnonymousInitializer ktAnonymousInitializer, IrClass irClass) {
        Intrinsics.checkNotNullParameter(ktAnonymousInitializer, "ktAnonymousInitializer");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        KtAnonymousInitializer ktAnonymousInitializer2 = ktAnonymousInitializer;
        IrAnonymousInitializer irAnonymousInitializerDeclareAnonymousInitializer = getContext().getSymbolTable().getDescriptorExtension().declareAnonymousInitializer(PsiUtilsKt.getStartOffsetSkippingComments(ktAnonymousInitializer2), PsiUtilsKt.getEndOffset(ktAnonymousInitializer2), IrDeclarationOrigin.DEFINED.INSTANCE, irClass.getDescriptor());
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irAnonymousInitializerDeclareAnonymousInitializer);
        IrAnonymousInitializer irAnonymousInitializer = irAnonymousInitializerDeclareAnonymousInitializer;
        irAnonymousInitializer.setParent(irClass);
        StatementGenerator statementGeneratorCreateStatementGenerator = DeclarationGeneratorKt.createBodyGenerator$default(this, irAnonymousInitializer.getSymbol(), null, 2, null).createStatementGenerator();
        KtExpression body = ktAnonymousInitializer.getBody();
        Intrinsics.checkNotNull(body);
        KtExpression ktExpression = body;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression));
        if (getContext().getConfiguration().getGenerateBodies()) {
            if (body instanceof KtBlockExpression) {
                List<KtExpression> statements = ((KtBlockExpression) body).getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statementGeneratorCreateStatementGenerator.generateStatements(statements, irBlockBodyCreateBlockBody);
            } else {
                irBlockBodyCreateBlockBody.getStatements().add(statementGeneratorCreateStatementGenerator.generateStatement(body));
            }
        }
        irAnonymousInitializer.setBody(irBlockBodyCreateBlockBody);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irAnonymousInitializerDeclareAnonymousInitializer);
        return irAnonymousInitializer;
    }
}
