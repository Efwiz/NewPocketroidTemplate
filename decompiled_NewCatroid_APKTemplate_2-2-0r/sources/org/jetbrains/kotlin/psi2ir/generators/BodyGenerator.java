package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptorKt;
import org.jetbrains.kotlin.psi2ir.intermediate.VariableLValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: BodyGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020'H\u0002J\u0018\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0016\u0010.\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010/\u001a\u00020-J\u0018\u00100\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010/\u001a\u00020-H\u0002J \u00101\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u0002022\u0006\u00103\u001a\u00020-H\u0002J\u000e\u00104\u001a\u0002052\u0006\u00106\u001a\u000207J\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u000207J\u0016\u0010;\u001a\u0002092\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?J\u0016\u0010@\u001a\u0002092\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DJ \u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020H2\u0006\u0010J\u001a\u00020)H\u0002J\u000e\u0010K\u001a\u0002092\u0006\u0010&\u001a\u00020'J\u000e\u0010L\u001a\u0002092\u0006\u0010&\u001a\u00020'J \u0010M\u001a\u00020\u001f2\u0006\u00103\u001a\u00020-2\u0006\u0010C\u001a\u00020D2\u0006\u0010%\u001a\u00020!H\u0002J\u0018\u0010N\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010A\u001a\u00020BH\u0002J\u0012\u0010O\u001a\u0004\u0018\u00010\u000e2\u0006\u0010P\u001a\u000207H\u0016J\u0016\u0010Q\u001a\u00020\u001f2\u0006\u0010P\u001a\u00020\r2\u0006\u0010R\u001a\u00020\u000eJ$\u0010S\u001a\u00020)*\u00020\u001d2\u000e\u0010T\u001a\n\u0012\u0006\b\u0001\u0012\u00020V0U2\u0006\u0010*\u001a\u00020+H\u0002J\f\u0010W\u001a\u00020X*\u00020YH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "Lorg/jetbrains/kotlin/psi2ir/generators/LoopResolver;", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "parentLoopResolver", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/psi2ir/generators/LoopResolver;)V", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "loopTable", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lkotlin/collections/HashMap;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "scopeOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getScopeOwner", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getScopeOwnerSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createStatementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "generateAnySuperConstructorCall", "", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "generateDelegatingConstructorCall", "irBlockBody", "ktConstructor", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "generateEnumConstructorCallOrSuperCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ktEnumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "enumClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "generateEnumEntryInitializer", "enumEntryDescriptor", "generateEnumEntrySuperConstructorCall", "generateEnumSuperConstructorCall", "Lorg/jetbrains/kotlin/psi/KtElement;", "classDescriptor", "generateExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generateFunctionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "ktBody", "generateLambdaBody", "ktFun", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "lambdaDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "generatePrimaryConstructorBody", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "generateReturnExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "startOffset", "", "endOffset", "returnValue", "generateSecondaryConstructorBody", "generateSecondaryConstructorBodyWithNestedInitializers", "generateSetContextReceiverFieldForPrimaryConstructorBody", "generateSuperConstructorCall", "getLoop", "expression", "putLoop", "irLoop", "generateEnumConstructorCall", "constructorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BodyGenerator implements GeneratorWithScope, LoopResolver {
    private final GeneratorContext context;
    private final HashMap<KtLoopExpression, IrLoop> loopTable;
    private final LoopResolver parentLoopResolver;
    private final Scope scope;
    private final IrSymbol scopeOwnerSymbol;
    private final TypeTranslator typeTranslator;

    /* JADX INFO: compiled from: BodyGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BodyGenerator(IrSymbol scopeOwnerSymbol, GeneratorContext context, LoopResolver loopResolver) {
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        this.scopeOwnerSymbol = scopeOwnerSymbol;
        this.context = context;
        this.parentLoopResolver = loopResolver;
        this.typeTranslator = getContext().getTypeTranslator();
        this.scope = new Scope(scopeOwnerSymbol);
        this.loopTable = new HashMap<>();
    }

    public final IrSymbol getScopeOwnerSymbol() {
        return this.scopeOwnerSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.context;
    }

    public final DeclarationDescriptor getScopeOwner() {
        return this.scopeOwnerSymbol.getDescriptor();
    }

    private final IrType toIrType(KotlinType kotlinType) {
        return this.typeTranslator.translateType(kotlinType);
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorWithScope
    public Scope getScope() {
        return this.scope;
    }

    public final IrBody generateFunctionBody(KtExpression ktBody) {
        Intrinsics.checkNotNullParameter(ktBody, "ktBody");
        KtExpression ktExpression = ktBody;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression));
        if (getContext().getConfiguration().getSkipBodies()) {
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
            int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
            IrType nothingType = getContext().getIrBuiltIns().getNothingType();
            String simpleName = ktBody.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
            IrErrorExpressionImpl irErrorExpressionImpl = new IrErrorExpressionImpl(startOffsetSkippingComments, endOffset, nothingType, simpleName);
            irBlockBodyCreateBlockBody.getStatements().add(generateReturnExpression(irErrorExpressionImpl.getEndOffset(), irErrorExpressionImpl.getEndOffset(), irErrorExpressionImpl));
            return irBlockBodyCreateBlockBody;
        }
        StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
        if (ktBody instanceof KtBlockExpression) {
            List<KtExpression> statements = ((KtBlockExpression) ktBody).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statementGeneratorCreateStatementGenerator.generateStatements(statements, irBlockBodyCreateBlockBody);
        } else {
            KtExpression ktExpression2 = ktBody;
            IrReturnImpl irReturnImplGenerateStatement = statementGeneratorCreateStatementGenerator.generateStatement(ktExpression2);
            List<IrStatement> statements2 = irBlockBodyCreateBlockBody.getStatements();
            if (BindingContextUtilsKt.isUsedAsExpression(ktExpression2, getContext().getBindingContext()) && (irReturnImplGenerateStatement instanceof IrExpression)) {
                irReturnImplGenerateStatement = generateReturnExpression(irReturnImplGenerateStatement.getEndOffset(), irReturnImplGenerateStatement.getEndOffset(), (IrExpression) irReturnImplGenerateStatement);
            }
            statements2.add(irReturnImplGenerateStatement);
        }
        return irBlockBodyCreateBlockBody;
    }

    public final IrExpressionBody generateExpressionBody(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        return IrFactoryHelpersKt.createExpressionBody(getContext().getIrFactory(), createStatementGenerator().generateExpression(ktExpression));
    }

    public final IrBody generateLambdaBody(KtFunctionLiteral ktFun, SimpleFunctionDescriptor lambdaDescriptor) {
        Intrinsics.checkNotNullParameter(ktFun, "ktFun");
        Intrinsics.checkNotNullParameter(lambdaDescriptor, "lambdaDescriptor");
        StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
        KtBlockExpression bodyExpression = ktFun.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        KtBlockExpression ktBlockExpression = bodyExpression;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression), PsiUtilsKt.getEndOffset(ktBlockExpression));
        for (KtParameter ktParameter : ktFun.getValueParameters()) {
            KtDestructuringDeclaration destructuringDeclaration = ktParameter.getDestructuringDeclaration();
            if (destructuringDeclaration != null) {
                WritableSlice<KtParameter, VariableDescriptor> VALUE_PARAMETER = BindingContext.VALUE_PARAMETER;
                Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
                VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(this, VALUE_PARAMETER, ktParameter);
                GeneratorContext context = getContext();
                KtDestructuringDeclaration ktDestructuringDeclaration = destructuringDeclaration;
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDestructuringDeclaration);
                int endOffset = PsiUtilsKt.getEndOffset(ktDestructuringDeclaration);
                DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
                Intrinsics.checkNotNull(variableDescriptor);
                IrValueSymbol irValueSymbolReferenceValue = descriptorExtension.referenceValue(variableDescriptor);
                KotlinType type = variableDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                VariableLValue variableLValue = new VariableLValue(context, startOffsetSkippingComments, endOffset, irValueSymbolReferenceValue, toIrType(type), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE);
                statementGeneratorCreateStatementGenerator.declareComponentVariablesInBlock(destructuringDeclaration, irBlockBodyCreateBlockBody, variableLValue, variableLValue);
            }
        }
        List<KtExpression> statements = bodyExpression.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        if (!statements.isEmpty()) {
            for (KtExpression ktExpression : CollectionsKt.dropLast(statements, 1)) {
                List<IrStatement> statements2 = irBlockBodyCreateBlockBody.getStatements();
                Intrinsics.checkNotNull(ktExpression);
                statements2.add(statementGeneratorCreateStatementGenerator.generateStatement(ktExpression));
            }
            KtExpression ktExpression2 = (KtExpression) CollectionsKt.last((List) statements);
            Intrinsics.checkNotNull(ktExpression2);
            IrReturnImpl irReturnImplGenerateStatement = statementGeneratorCreateStatementGenerator.generateStatement(ktExpression2);
            List<IrStatement> statements3 = irBlockBodyCreateBlockBody.getStatements();
            KotlinType returnType = lambdaDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            if (!TypeUtilsKt.isUnit(returnType) && (irReturnImplGenerateStatement instanceof IrExpression) && !(irReturnImplGenerateStatement instanceof IrReturn) && !(irReturnImplGenerateStatement instanceof IrThrow)) {
                irReturnImplGenerateStatement = generateReturnExpression(irReturnImplGenerateStatement.getStartOffset(), irReturnImplGenerateStatement.getEndOffset(), (IrExpression) irReturnImplGenerateStatement);
            }
            statements3.add(irReturnImplGenerateStatement);
        } else {
            irBlockBodyCreateBlockBody.getStatements().add(generateReturnExpression(PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression), PsiUtilsKt.getEndOffset(ktBlockExpression), new IrGetObjectValueImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression), PsiUtilsKt.getEndOffset(ktBlockExpression), getContext().getIrBuiltIns().getUnitType(), getContext().getIrBuiltIns().getUnitClass())));
        }
        return irBlockBodyCreateBlockBody;
    }

    private final IrReturnImpl generateReturnExpression(int startOffset, int endOffset, IrExpression returnValue) {
        IrSymbolOwner owner = this.scopeOwnerSymbol.getOwner();
        IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
        if (irFunction == null) {
            throw new AssertionError("'return' in a non-callable: " + getScopeOwner());
        }
        return new IrReturnImpl(startOffset, endOffset, getContext().getIrBuiltIns().getNothingType(), irFunction.getSymbol(), returnValue);
    }

    public final IrBody generateSecondaryConstructorBody(KtSecondaryConstructor ktConstructor) {
        Intrinsics.checkNotNullParameter(ktConstructor, "ktConstructor");
        KtSecondaryConstructor ktSecondaryConstructor = ktConstructor;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getStartOffsetSkippingComments(ktSecondaryConstructor), PsiUtilsKt.getEndOffset(ktSecondaryConstructor));
        generateDelegatingConstructorCall(irBlockBodyCreateBlockBody, ktConstructor);
        KtBlockExpression bodyExpression = ktConstructor.getBodyExpression();
        if (bodyExpression != null) {
            StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
            List<KtExpression> statements = bodyExpression.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statementGeneratorCreateStatementGenerator.generateStatements(statements, irBlockBodyCreateBlockBody);
        }
        return irBlockBodyCreateBlockBody;
    }

    private final void generateDelegatingConstructorCall(IrBlockBody irBlockBody, KtSecondaryConstructor ktConstructor) {
        DeclarationDescriptor scopeOwner = getScopeOwner();
        Intrinsics.checkNotNull(scopeOwner, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) scopeOwner;
        StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
        KtConstructorDelegationCall delegationCall = ktConstructor.getDelegationCall();
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, delegationCall);
        if (resolvedCall == null) {
            ClassDescriptor containingDeclaration = classConstructorDescriptor.getContainingDeclaration();
            if (containingDeclaration.getKind() == ClassKind.ENUM_CLASS) {
                generateEnumSuperConstructorCall(irBlockBody, ktConstructor, containingDeclaration);
                return;
            } else {
                generateAnySuperConstructorCall(irBlockBody, ktConstructor);
                return;
            }
        }
        KtConstructorDelegationCall ktConstructorDelegationCall = delegationCall;
        irBlockBody.getStatements().add(new CallGenerator(statementGeneratorCreateStatementGenerator).generateDelegatingConstructorCall(PsiUtilsKt.getStartOffsetSkippingComments(ktConstructorDelegationCall), PsiUtilsKt.getEndOffset(ktConstructorDelegationCall), ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall)));
    }

    public final StatementGenerator createStatementGenerator() {
        return new StatementGenerator(this, getScope());
    }

    public final void putLoop(KtLoopExpression expression, IrLoop irLoop) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(irLoop, "irLoop");
        this.loopTable.put(expression, irLoop);
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.LoopResolver
    public IrLoop getLoop(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrLoop irLoop = this.loopTable.get(expression);
        if (irLoop != null) {
            return irLoop;
        }
        LoopResolver loopResolver = this.parentLoopResolver;
        if (loopResolver != null) {
            return loopResolver.getLoop(expression);
        }
        return null;
    }

    public final IrBody generatePrimaryConstructorBody(KtPureClassOrObject ktClassOrObject, IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        Intrinsics.checkNotNullParameter(irConstructor, "irConstructor");
        KtPureClassOrObject ktPureClassOrObject = ktClassOrObject;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getPureStartOffset(ktPureClassOrObject), PsiUtilsKt.getPureEndOffset(ktPureClassOrObject));
        generateSuperConstructorCall(irBlockBodyCreateBlockBody, ktClassOrObject);
        DeclarationDescriptor scopeOwner = getScopeOwner();
        Intrinsics.checkNotNull(scopeOwner, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassDescriptor containingDeclaration = ((ClassConstructorDescriptor) scopeOwner).getContainingDeclaration();
        List<ReceiverParameterDescriptor> contextReceivers = containingDeclaration.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        if (!contextReceivers.isEmpty()) {
            generateSetContextReceiverFieldForPrimaryConstructorBody(containingDeclaration, irConstructor, irBlockBodyCreateBlockBody);
        }
        irBlockBodyCreateBlockBody.getStatements().add(new IrInstanceInitializerCallImpl(PsiUtilsKt.getPureStartOffset(ktPureClassOrObject), PsiUtilsKt.getPureEndOffset(ktPureClassOrObject), getContext().getSymbolTable().getDescriptorExtension().referenceClass(containingDeclaration), getContext().getIrBuiltIns().getUnitType()));
        return irBlockBodyCreateBlockBody;
    }

    public final IrBody generateSecondaryConstructorBodyWithNestedInitializers(KtSecondaryConstructor ktConstructor) {
        Intrinsics.checkNotNullParameter(ktConstructor, "ktConstructor");
        KtSecondaryConstructor ktSecondaryConstructor = ktConstructor;
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(PsiUtilsKt.getStartOffsetSkippingComments(ktSecondaryConstructor), PsiUtilsKt.getEndOffset(ktSecondaryConstructor));
        generateDelegatingConstructorCall(irBlockBodyCreateBlockBody, ktConstructor);
        WritableSlice<PsiElement, ConstructorDescriptor> CONSTRUCTOR = BindingContext.CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR, "CONSTRUCTOR");
        ClassifierDescriptorWithTypeParameters containingDeclaration = ((ConstructorDescriptor) GeneratorKt.getOrFail(this, CONSTRUCTOR, ktConstructor)).getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        irBlockBodyCreateBlockBody.getStatements().add(new IrInstanceInitializerCallImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktSecondaryConstructor), PsiUtilsKt.getEndOffset(ktSecondaryConstructor), getContext().getSymbolTable().getDescriptorExtension().referenceClass((ClassDescriptor) containingDeclaration), getContext().getIrBuiltIns().getUnitType()));
        KtBlockExpression bodyExpression = ktConstructor.getBodyExpression();
        if (bodyExpression != null) {
            StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
            List<KtExpression> statements = bodyExpression.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statementGeneratorCreateStatementGenerator.generateStatements(statements, irBlockBodyCreateBlockBody);
        }
        return irBlockBodyCreateBlockBody;
    }

    private final void generateSuperConstructorCall(IrBlockBody body, KtPureClassOrObject ktClassOrObject) {
        KtSuperTypeList superTypeList;
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        KtPureClassOrObject ktPureClassOrObject = ktClassOrObject;
        ClassDescriptor classDescriptorFindClassDescriptor = SyntheticClassOrObjectDescriptorKt.findClassDescriptor(ktPureClassOrObject, getContext().getBindingContext());
        IrDelegatingConstructorCall irDelegatingConstructorCallCreateCustomSuperConstructorCall = getContext().getExtensions().createCustomSuperConstructorCall(ktClassOrObject, classDescriptorFindClassDescriptor, getContext());
        if (irDelegatingConstructorCallCreateCustomSuperConstructorCall != null) {
            body.getStatements().add(irDelegatingConstructorCallCreateCustomSuperConstructorCall);
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[classDescriptorFindClassDescriptor.getKind().ordinal()];
        if (i == 1) {
            Intrinsics.checkNotNull(ktClassOrObject, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
            generateEnumSuperConstructorCall(body, (KtClassOrObject) ktClassOrObject, classDescriptorFindClassDescriptor);
            return;
        }
        if (i == 2) {
            List<IrStatement> statements = body.getStatements();
            Intrinsics.checkNotNull(ktClassOrObject, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtEnumEntry");
            statements.add(generateEnumEntrySuperConstructorCall((KtEnumEntry) ktClassOrObject, classDescriptorFindClassDescriptor));
            return;
        }
        StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
        KtClassOrObject ktClassOrObject2 = ktClassOrObject instanceof KtClassOrObject ? (KtClassOrObject) ktClassOrObject : null;
        if (ktClassOrObject2 != null && (superTypeList = ktClassOrObject2.getSuperTypeList()) != null) {
            for (KtSuperTypeListEntry ktSuperTypeListEntry : superTypeList.getEntries()) {
                if ((ktSuperTypeListEntry instanceof KtSuperTypeCallEntry) && (resolvedCall = GeneratorKt.getResolvedCall(this, ktSuperTypeListEntry)) != null) {
                    KtSuperTypeListEntry ktSuperTypeListEntry2 = ktSuperTypeListEntry;
                    body.getStatements().add(new CallGenerator(statementGeneratorCreateStatementGenerator).generateDelegatingConstructorCall(PsiUtilsKt.getStartOffsetSkippingComments(ktSuperTypeListEntry2), PsiUtilsKt.getEndOffset(ktSuperTypeListEntry2), ArgumentsGenerationUtilsKt.pregenerateCall(statementGeneratorCreateStatementGenerator, resolvedCall)));
                    return;
                }
            }
        }
        ClassDescriptor superClassOrAny = DescriptorUtilsKt.getSuperClassOrAny(classDescriptorFindClassDescriptor);
        if (getContext().getConfiguration().getGenerateBodies()) {
            KotlinBuiltIns.isAny(superClassOrAny);
        }
        generateAnySuperConstructorCall(body, ktPureClassOrObject);
    }

    private final void generateAnySuperConstructorCall(IrBlockBody body, KtPureElement ktElement) {
        Collection<ClassConstructorDescriptor> constructors = getContext().getIrBuiltIns().getAnyClass().getDescriptor().getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.single(constructors);
        List<IrStatement> statements = body.getStatements();
        IrDelegatingConstructorCallImpl.Companion companion = IrDelegatingConstructorCallImpl.INSTANCE;
        int pureStartOffset = PsiUtilsKt.getPureStartOffset(ktElement);
        int pureEndOffset = PsiUtilsKt.getPureEndOffset(ktElement);
        IrType unitType = getContext().getIrBuiltIns().getUnitType();
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        Intrinsics.checkNotNull(classConstructorDescriptor);
        statements.add(IrDelegatingConstructorCallImpl.Companion.fromSymbolDescriptor$default(companion, pureStartOffset, pureEndOffset, unitType, descriptorExtension.referenceConstructor(classConstructorDescriptor), 0, 0, 48, null));
    }

    private final void generateEnumSuperConstructorCall(IrBlockBody body, KtElement ktElement, ClassDescriptor classDescriptor) {
        Collection<ClassConstructorDescriptor> constructors = getContext().getIrBuiltIns().getEnumClass().getDescriptor().getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.single(constructors);
        List<IrStatement> statements = body.getStatements();
        IrEnumConstructorCallImpl.Companion companion = IrEnumConstructorCallImpl.INSTANCE;
        KtElement ktElement2 = ktElement;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktElement2);
        int endOffset = PsiUtilsKt.getEndOffset(ktElement2);
        IrType unitType = getContext().getIrBuiltIns().getUnitType();
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        Intrinsics.checkNotNull(classConstructorDescriptor);
        IrEnumConstructorCallImpl irEnumConstructorCallImplFromSymbolDescriptor = companion.fromSymbolDescriptor(startOffsetSkippingComments, endOffset, unitType, descriptorExtension.referenceConstructor(classConstructorDescriptor), 1);
        SimpleType defaultType = classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        irEnumConstructorCallImplFromSymbolDescriptor.putTypeArgument(0, toIrType(defaultType));
        statements.add(irEnumConstructorCallImplFromSymbolDescriptor);
    }

    private final IrExpression generateEnumEntrySuperConstructorCall(KtEnumEntry ktEnumEntry, ClassDescriptor enumEntryDescriptor) {
        DeclarationDescriptor containingDeclaration = enumEntryDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return generateEnumConstructorCallOrSuperCall(ktEnumEntry, (ClassDescriptor) containingDeclaration);
    }

    public final IrExpression generateEnumEntryInitializer(KtEnumEntry ktEnumEntry, ClassDescriptor enumEntryDescriptor) {
        Intrinsics.checkNotNullParameter(ktEnumEntry, "ktEnumEntry");
        Intrinsics.checkNotNullParameter(enumEntryDescriptor, "enumEntryDescriptor");
        if (!ktEnumEntry.getDeclarations().isEmpty()) {
            ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = enumEntryDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
            Intrinsics.checkNotNull(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
            KtEnumEntry ktEnumEntry2 = ktEnumEntry;
            return IrEnumConstructorCallImpl.INSTANCE.fromSymbolDescriptor(PsiUtilsKt.getStartOffsetSkippingComments(ktEnumEntry2), PsiUtilsKt.getEndOffset(ktEnumEntry2), getContext().getIrBuiltIns().getUnitType(), getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor), 0);
        }
        DeclarationDescriptor containingDeclaration = enumEntryDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return generateEnumConstructorCallOrSuperCall(ktEnumEntry, (ClassDescriptor) containingDeclaration);
    }

    private final IrExpression generateEnumConstructorCallOrSuperCall(KtEnumEntry ktEnumEntry, ClassDescriptor enumClassDescriptor) {
        StatementGenerator statementGeneratorCreateStatementGenerator = createStatementGenerator();
        List<KtSuperTypeListEntry> superTypeListEntries = ktEnumEntry.getSuperTypeListEntries();
        Intrinsics.checkNotNullExpressionValue(superTypeListEntries, "getSuperTypeListEntries(...)");
        KtSuperTypeListEntry ktSuperTypeListEntry = (KtSuperTypeListEntry) CollectionsKt.firstOrNull((List) superTypeListEntries);
        if (ktSuperTypeListEntry != null) {
            ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktSuperTypeListEntry);
            Intrinsics.checkNotNull(resolvedCall);
            return generateEnumConstructorCall(statementGeneratorCreateStatementGenerator, resolvedCall, ktEnumEntry);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall2 = GeneratorKt.getResolvedCall(this, ktEnumEntry);
        if (resolvedCall2 == null) {
            throw new AssertionError("No default constructor call for enum entry " + enumClassDescriptor);
        }
        return generateEnumConstructorCall(statementGeneratorCreateStatementGenerator, resolvedCall2, ktEnumEntry);
    }

    private final IrExpression generateEnumConstructorCall(StatementGenerator statementGenerator, ResolvedCall<? extends CallableDescriptor> resolvedCall, KtEnumEntry ktEnumEntry) {
        KtEnumEntry ktEnumEntry2 = ktEnumEntry;
        return new CallGenerator(statementGenerator).generateEnumConstructorSuperCall(PsiUtilsKt.getStartOffsetSkippingComments(ktEnumEntry2), PsiUtilsKt.getEndOffset(ktEnumEntry2), ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator, resolvedCall));
    }

    private final void generateSetContextReceiverFieldForPrimaryConstructorBody(ClassDescriptor classDescriptor, IrConstructor irConstructor, IrBlockBody irBlockBody) {
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        int i = 0;
        for (ReceiverParameterDescriptor receiverParameterDescriptor : classDescriptor.getContextReceivers()) {
            int i2 = i + 1;
            IrValueParameter irValueParameter = irConstructor.getValueParameters().get(i);
            List<IrStatement> statements = irBlockBody.getStatements();
            DescriptorStorageForContextReceivers additionalDescriptorStorage = getContext().getAdditionalDescriptorStorage();
            ReceiverValue value = receiverParameterDescriptor.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            IrFieldSymbol symbol = additionalDescriptorStorage.getSyntheticField(value).getSymbol();
            KotlinType type = thisAsReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            statements.add(new IrSetFieldImpl(-1, -1, symbol, new IrGetValueImpl(-1, -1, toIrType(type), getContext().getSymbolTable().getDescriptorExtension().referenceValue(thisAsReceiverParameter), null, 16, null), new IrGetValueImpl(-1, -1, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null), getContext().getIrBuiltIns().getUnitType(), null, null, 192, null));
            i = i2;
        }
    }
}
