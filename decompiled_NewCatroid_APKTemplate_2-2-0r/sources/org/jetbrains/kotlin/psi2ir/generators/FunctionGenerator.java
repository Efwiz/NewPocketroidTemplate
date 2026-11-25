package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: FunctionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¦\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007JP\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u001f\u0010\u0011\u001a\u001b\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0012¢\u0006\u0002\b\u0015H\u0082\bJ?\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H\u0002¢\u0006\u0002\u0010!J^\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020'0\u000e2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0018\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-2\u0019\u0010\u0011\u001a\u0015\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00140.¢\u0006\u0002\b\u0015H\u0082\bJ \u0010/\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020+2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020*H\u0002J\u001a\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020#H\u0002J\u0016\u00105\u001a\u00020#2\u0006\u0010\u0018\u001a\u0002032\u0006\u0010\u001a\u001a\u00020'J\u0018\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0002J\u0018\u0010<\u001a\u0002012\u0006\u0010=\u001a\u00020>2\u0006\u00104\u001a\u00020#H\u0002J\u0018\u0010?\u001a\u0002012\u0006\u0010@\u001a\u00020A2\u0006\u00104\u001a\u00020#H\u0002J\u0018\u0010B\u001a\u0004\u0018\u00010#2\u0006\u0010C\u001a\u00020+2\u0006\u0010\u001a\u001a\u00020\u000bJ$\u0010D\u001a\u00020#2\u0006\u0010$\u001a\u00020E2\b\u0010,\u001a\u0004\u0018\u00010-2\b\b\u0002\u0010)\u001a\u00020*H\u0007J0\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010\u000b2\b\u0010K\u001a\u0004\u0018\u00010'2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020'0\u000eJ\u0018\u0010M\u001a\u00020#2\u0006\u0010$\u001a\u00020N2\b\u0010,\u001a\u0004\u0018\u00010-J\u0016\u0010O\u001a\u00020\t2\u0006\u0010P\u001a\u00020\u00102\u0006\u0010Q\u001a\u00020RJ \u0010S\u001a\u00020#2\u0006\u0010\u0018\u001a\u0002032\u0006\u0010T\u001a\u00020U2\b\u0010V\u001a\u0004\u0018\u00010WJ\u001a\u0010X\u001a\u0004\u0018\u00010Y2\u0006\u0010Z\u001a\u00020[2\u0006\u00104\u001a\u00020#H\u0002J\"\u0010\\\u001a\u00020\u00172\u0006\u0010]\u001a\u00020^2\b\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0016\u0010_\u001a\u00020\t2\u0006\u0010`\u001a\u00020a2\u0006\u0010Q\u001a\u00020RJ\u000e\u0010b\u001a\u00020G2\u0006\u0010H\u001a\u00020IJ<\u0010c\u001a\u00020G2\u0006\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010\u000b2\b\u0010K\u001a\u0004\u0018\u00010\u000b2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\b\b\u0002\u0010e\u001a\u00020fH\u0002J\u001a\u0010g\u001a\u0004\u0018\u00010h2\u0006\u0010i\u001a\u00020N2\u0006\u0010j\u001a\u00020kH\u0002¨\u0006l"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/FunctionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "declareConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "ktConstructorElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "ktParametersElement", "ktContextReceiversElements", "", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "generateBody", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/psi2ir/generators/BodyGenerator;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "Lkotlin/ExtensionFunctionType;", "declareParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "ktElement", "irOwnerElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;Lorg/jetbrains/kotlin/psi/KtPureElement;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "declareSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "ktReceiver", "Lorg/jetbrains/kotlin/psi/KtElement;", "ktContextReceivers", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parentLoopResolver", "Lorg/jetbrains/kotlin/psi2ir/generators/LoopResolver;", "Lkotlin/Function1;", "declareSimpleFunctionInner", "generateDefaultAccessorBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "irAccessor", "generateDefaultAccessorForPrimaryConstructorParameter", "generateDefaultAnnotationParameterValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "valueExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "generateDefaultGetterBody", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "generateDefaultSetterBody", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "generateFakeOverrideFunction", "functionDescriptor", "generateFunctionDeclaration", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "generateFunctionParameterDeclarationsAndReturnType", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "ktParameterOwner", "ktReceiverParameterElement", "ktContextReceiverParametersElements", "generateLambdaFunctionDeclaration", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "generatePrimaryConstructor", "primaryConstructorDescriptor", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "generatePropertyAccessor", "ktProperty", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "ktAccessor", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "generateReceiverExpressionForDefaultPropertyAccessor", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateReceiverParameterDeclaration", "receiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "generateSecondaryConstructor", "ktConstructor", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "generateSyntheticFunctionParameterDeclarations", "generateValueParameterDeclarations", "ktContextReceiverParameterElements", "withDefaultValues", "", "getCallLabelForLambdaArgument", "", "declaration", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ObsoleteDescriptorBasedAPI
public final class FunctionGenerator extends DeclarationGeneratorExtension {
    public final IrSimpleFunction generateFunctionDeclaration(KtNamedFunction ktFunction, LoopResolver loopResolver) {
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        return generateFunctionDeclaration$default(this, ktFunction, loopResolver, null, 4, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FunctionGenerator(GeneratorContext context) {
        this(new DeclarationGenerator(context));
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public static /* synthetic */ IrSimpleFunction generateFunctionDeclaration$default(FunctionGenerator functionGenerator, KtNamedFunction ktNamedFunction, LoopResolver loopResolver, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.DEFINED.INSTANCE;
        }
        return functionGenerator.generateFunctionDeclaration(ktNamedFunction, loopResolver, irDeclarationOrigin);
    }

    public final IrSimpleFunction generateFunctionDeclaration(KtNamedFunction ktFunction, LoopResolver parentLoopResolver, IrDeclarationOrigin origin) {
        IrBody irBodyGenerateFunctionBody;
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        Intrinsics.checkNotNullParameter(origin, "origin");
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktFunction.mo7393getReceiverTypeReference();
        List<KtContextReceiver> contextReceivers = ktFunction.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            if (ktTypeReferenceTypeReference != null) {
                arrayList.add(ktTypeReferenceTypeReference);
            }
        }
        FunctionGenerator functionGenerator = this;
        WritableSlice<PsiElement, SimpleFunctionDescriptor> FUNCTION = BindingContext.FUNCTION;
        Intrinsics.checkNotNullExpressionValue(FUNCTION, "FUNCTION");
        Object orFail = GeneratorKt.getOrFail(functionGenerator, FUNCTION, ktFunction);
        Intrinsics.checkNotNullExpressionValue(orFail, "getOrFail(...)");
        KtNamedFunction ktNamedFunction = ktFunction;
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionInner = declareSimpleFunctionInner((FunctionDescriptor) orFail, ktNamedFunction, origin);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionInner);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionInner;
        generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction, ktNamedFunction, ktTypeReferenceMo7393getReceiverTypeReference, arrayList);
        BodyGenerator bodyGeneratorCreateBodyGenerator = DeclarationGeneratorKt.createBodyGenerator(functionGenerator, irSimpleFunction.getSymbol(), parentLoopResolver);
        KtExpression bodyExpression = ktFunction.getBodyExpression();
        if (bodyExpression != null) {
            Intrinsics.checkNotNull(bodyExpression);
            irBodyGenerateFunctionBody = bodyGeneratorCreateBodyGenerator.generateFunctionBody(bodyExpression);
        } else {
            irBodyGenerateFunctionBody = null;
        }
        irSimpleFunction.setBody(irBodyGenerateFunctionBody);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionInner);
        return irSimpleFunction;
    }

    public final IrSimpleFunction generateLambdaFunctionDeclaration(KtFunctionLiteral ktFunction, LoopResolver parentLoopResolver) {
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        FunctionGenerator functionGenerator = this;
        WritableSlice<PsiElement, SimpleFunctionDescriptor> FUNCTION = BindingContext.FUNCTION;
        Intrinsics.checkNotNullExpressionValue(FUNCTION, "FUNCTION");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) GeneratorKt.getOrFail(functionGenerator, FUNCTION, ktFunction);
        List<? extends KtElement> listEmptyList = CollectionsKt.emptyList();
        IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA local_function_for_lambda = IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE;
        Intrinsics.checkNotNull(simpleFunctionDescriptor);
        KtFunctionLiteral ktFunctionLiteral = ktFunction;
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionInner = declareSimpleFunctionInner(simpleFunctionDescriptor, ktFunctionLiteral, local_function_for_lambda);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionInner);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionInner;
        generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction, ktFunctionLiteral, null, listEmptyList);
        irSimpleFunction.setBody(DeclarationGeneratorKt.createBodyGenerator(functionGenerator, irSimpleFunction.getSymbol(), parentLoopResolver).generateLambdaBody(ktFunction, simpleFunctionDescriptor));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionInner);
        return irSimpleFunction;
    }

    public final IrSimpleFunction generateFakeOverrideFunction(FunctionDescriptor functionDescriptor, KtPureElement ktElement) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        if (Intrinsics.areEqual(functionDescriptor.getVisibilityImpl(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            functionDescriptor = null;
        }
        if (functionDescriptor == null) {
            return null;
        }
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionInner = declareSimpleFunctionInner(functionDescriptor, ktElement, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionInner);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionInner;
        generateFunctionParameterDeclarationsAndReturnType(irSimpleFunction, ktElement, null, CollectionsKt.emptyList());
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionInner);
        return irSimpleFunction;
    }

    private final IrSimpleFunction declareSimpleFunctionInner(FunctionDescriptor descriptor, KtPureElement ktElement, IrDeclarationOrigin origin) {
        SymbolTable symbolTable = getContext().getSymbolTable();
        Integer startOffsetOfFunctionDeclarationKeywordOrNull = DeclarationStartOffsetKt.getStartOffsetOfFunctionDeclarationKeywordOrNull(ktElement);
        return OverridesKt.declareSimpleFunctionWithOverrides(symbolTable, startOffsetOfFunctionDeclarationKeywordOrNull != null ? startOffsetOfFunctionDeclarationKeywordOrNull.intValue() : PsiUtilsKt.getPureStartOffset(ktElement), PsiUtilsKt.getPureEndOffset(ktElement), origin, descriptor);
    }

    public final void generateFunctionParameterDeclarationsAndReturnType(IrFunction irFunction, KtPureElement ktParameterOwner, KtElement ktReceiverParameterElement, List<? extends KtElement> ktContextReceiverParametersElements) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(ktContextReceiverParametersElements, "ktContextReceiverParametersElements");
        List<TypeParameterDescriptor> typeParameters = DescriptorUtilsKt.getPropertyIfAccessor(irFunction.getDescriptor()).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        getDeclarationGenerator().generateScopedTypeParameterDeclarations(irFunction, typeParameters);
        KotlinType returnType = irFunction.getDescriptor().getReturnType();
        Intrinsics.checkNotNull(returnType);
        irFunction.setReturnType(toIrType(returnType));
        generateValueParameterDeclarations$default(this, irFunction, ktParameterOwner, ktReceiverParameterElement, ktContextReceiverParametersElements, false, 16, null);
    }

    public final IrSimpleFunction generatePropertyAccessor(PropertyAccessorDescriptor descriptor, KtVariableDeclaration ktProperty, KtPropertyAccessor ktAccessor) {
        IrBlockBody irBlockBodyGenerateDefaultAccessorBody;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(ktProperty, "ktProperty");
        FunctionGenerator functionGenerator = this;
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionInner = declareSimpleFunctionInner(descriptor, ktAccessor != null ? ktAccessor : ktProperty, (ktAccessor == null || !ktAccessor.hasBody()) ? IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE : IrDeclarationOrigin.DEFINED.INSTANCE);
        SymbolTable symbolTable = functionGenerator.getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionInner);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionInner;
        List<TypeParameterDescriptor> typeParameters = descriptor.getCorrespondingProperty().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        getDeclarationGenerator().generateScopedTypeParameterDeclarations(irSimpleFunction, typeParameters);
        KotlinType returnType = irSimpleFunction.getDescriptor().getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunction.setReturnType(toIrType(returnType));
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        KtPureElement ktPureElement = ktAccessor != null ? ktAccessor : ktProperty;
        KtTypeReference receiverTypeReference = ktProperty.mo7393getReceiverTypeReference();
        List<KtContextReceiver> contextReceivers = ktProperty.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            if (ktTypeReferenceTypeReference != null) {
                arrayList.add(ktTypeReferenceTypeReference);
            }
        }
        generateValueParameterDeclarations$default(this, irSimpleFunction2, ktPureElement, receiverTypeReference, arrayList, false, 16, null);
        KtExpression bodyExpression = ktAccessor != null ? ktAccessor.getBodyExpression() : null;
        if (bodyExpression != null) {
            irBlockBodyGenerateDefaultAccessorBody = DeclarationGeneratorKt.createBodyGenerator$default(this, irSimpleFunction.getSymbol(), null, 2, null).generateFunctionBody(bodyExpression);
        } else {
            irBlockBodyGenerateDefaultAccessorBody = generateDefaultAccessorBody(descriptor, irSimpleFunction);
        }
        irSimpleFunction.setBody(irBlockBodyGenerateDefaultAccessorBody);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionInner);
        return irSimpleFunction;
    }

    public final IrSimpleFunction generateDefaultAccessorForPrimaryConstructorParameter(PropertyAccessorDescriptor descriptor, KtElement ktElement) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionInner = declareSimpleFunctionInner(descriptor, ktElement, IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionInner);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionInner;
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        getDeclarationGenerator().generateScopedTypeParameterDeclarations(irSimpleFunction, typeParameters);
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunction.setReturnType(toIrType(returnType));
        new FunctionGenerator(getDeclarationGenerator()).generateSyntheticFunctionParameterDeclarations(irSimpleFunction);
        irSimpleFunction.setBody(generateDefaultAccessorBody(descriptor, irSimpleFunction));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionInner);
        return irSimpleFunction;
    }

    private final IrBlockBody generateDefaultAccessorBody(PropertyAccessorDescriptor accessor, IrSimpleFunction irAccessor) {
        if (accessor.getModality() == Modality.ABSTRACT || accessor.getCorrespondingProperty().get$isExpect() || getContext().getConfiguration().getSkipBodies()) {
            return null;
        }
        if (accessor instanceof PropertyGetterDescriptor) {
            return generateDefaultGetterBody((PropertyGetterDescriptor) accessor, irAccessor);
        }
        if (accessor instanceof PropertySetterDescriptor) {
            return generateDefaultSetterBody((PropertySetterDescriptor) accessor, irAccessor);
        }
        throw new AssertionError("Should be getter or setter: " + accessor);
    }

    private final IrBlockBody generateDefaultGetterBody(PropertyGetterDescriptor getter, IrSimpleFunction irAccessor) {
        PropertyDescriptor correspondingProperty = getter.getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        int startOffset = irAccessor.getStartOffset();
        int endOffset = irAccessor.getEndOffset();
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(startOffset, endOffset);
        IrExpression irExpressionGenerateReceiverExpressionForDefaultPropertyAccessor = generateReceiverExpressionForDefaultPropertyAccessor(correspondingProperty, irAccessor);
        List<IrStatement> statements = irBlockBodyCreateBlockBody.getStatements();
        IrType nothingType = getContext().getIrBuiltIns().getNothingType();
        IrSimpleFunctionSymbol symbol = irAccessor.getSymbol();
        IrFieldSymbol irFieldSymbolReferenceField = getContext().getSymbolTable().getDescriptorExtension().referenceField(correspondingProperty);
        KotlinType type = correspondingProperty.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        statements.add(new IrReturnImpl(startOffset, endOffset, nothingType, symbol, new IrGetFieldImpl(startOffset, endOffset, irFieldSymbolReferenceField, toIrType(type), irExpressionGenerateReceiverExpressionForDefaultPropertyAccessor, null, null, 96, null)));
        return irBlockBodyCreateBlockBody;
    }

    private final IrBlockBody generateDefaultSetterBody(PropertySetterDescriptor setter, IrSimpleFunction irAccessor) {
        PropertyDescriptor correspondingProperty = setter.getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        int startOffset = irAccessor.getStartOffset();
        int endOffset = irAccessor.getEndOffset();
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(startOffset, endOffset);
        IrExpression irExpressionGenerateReceiverExpressionForDefaultPropertyAccessor = generateReceiverExpressionForDefaultPropertyAccessor(correspondingProperty, irAccessor);
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.single((List) irAccessor.getValueParameters());
        irBlockBodyCreateBlockBody.getStatements().add(new IrSetFieldImpl(startOffset, endOffset, getContext().getSymbolTable().getDescriptorExtension().referenceField(correspondingProperty), irExpressionGenerateReceiverExpressionForDefaultPropertyAccessor, new IrGetValueImpl(startOffset, endOffset, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null), getContext().getIrBuiltIns().getUnitType(), null, null, 192, null));
        return irBlockBodyCreateBlockBody;
    }

    private final IrExpression generateReceiverExpressionForDefaultPropertyAccessor(PropertyDescriptor property, IrSimpleFunction irAccessor) {
        DeclarationDescriptor containingDeclaration = property.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            return null;
        }
        ReceiverParameterDescriptor thisAsReceiverParameter = ((ClassDescriptor) containingDeclaration).getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        int startOffset = irAccessor.getStartOffset();
        int endOffset = irAccessor.getEndOffset();
        KotlinType type = thisAsReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new IrGetValueImpl(startOffset, endOffset, toIrType(type), getContext().getSymbolTable().getDescriptorExtension().referenceValue(thisAsReceiverParameter), null, 16, null);
    }

    public final IrConstructor generatePrimaryConstructor(ClassConstructorDescriptor primaryConstructorDescriptor, KtPureClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(primaryConstructorDescriptor, "primaryConstructorDescriptor");
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
        KtPureElement ktPureElement = primaryConstructor == null ? ktClassOrObject : primaryConstructor;
        List<KtContextReceiver> contextReceivers = ktClassOrObject.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            if (ktTypeReferenceTypeReference != null) {
                arrayList.add(ktTypeReferenceTypeReference);
            }
        }
        ArrayList arrayList2 = arrayList;
        KtPureClassOrObject ktPureClassOrObject = ktClassOrObject;
        Integer startOffsetOfConstructorDeclarationKeywordOrNull = DeclarationStartOffsetKt.getStartOffsetOfConstructorDeclarationKeywordOrNull(ktPureClassOrObject);
        IrConstructor irConstructorDeclareConstructor = getContext().getSymbolTable().getDescriptorExtension().declareConstructor(primaryConstructorDescriptor, new FunctionGenerator$declareConstructor$1(primaryConstructorDescriptor, this, startOffsetOfConstructorDeclarationKeywordOrNull != null ? startOffsetOfConstructorDeclarationKeywordOrNull.intValue() : PsiUtilsKt.getPureStartOffset(ktPureClassOrObject), PsiUtilsKt.getPureEndOffset(ktPureClassOrObject), IrDeclarationOrigin.DEFINED.INSTANCE, arrayList2));
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irConstructorDeclareConstructor);
        IrConstructor irConstructor = irConstructorDeclareConstructor;
        generateValueParameterDeclarations$default(this, irConstructor, ktPureElement, null, arrayList2, false, 16, null);
        if (getContext().getConfiguration().getGenerateBodies()) {
            IrBody irBodyGeneratePrimaryConstructorBody = null;
            BodyGenerator bodyGeneratorCreateBodyGenerator$default = DeclarationGeneratorKt.createBodyGenerator$default(this, irConstructor.getSymbol(), null, 2, null);
            if (!primaryConstructorDescriptor.get$isExpect()) {
                ClassDescriptor constructedClass = primaryConstructorDescriptor.getConstructedClass();
                Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
                if (!DescriptorUtilsKt.isEffectivelyExternal(constructedClass) && !bodyGeneratorCreateBodyGenerator$default.getContext().getConfiguration().getSkipBodies()) {
                    irBodyGeneratePrimaryConstructorBody = bodyGeneratorCreateBodyGenerator$default.generatePrimaryConstructorBody(ktClassOrObject, irConstructor);
                }
            }
            irConstructor.setBody(irBodyGeneratePrimaryConstructorBody);
        }
        KotlinType returnType = primaryConstructorDescriptor.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        irConstructor.setReturnType(toIrType(returnType));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irConstructorDeclareConstructor);
        return irConstructor;
    }

    public final IrConstructor generateSecondaryConstructor(KtSecondaryConstructor ktConstructor, KtPureClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktConstructor, "ktConstructor");
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        FunctionGenerator functionGenerator = this;
        WritableSlice<PsiElement, ConstructorDescriptor> CONSTRUCTOR = BindingContext.CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR, "CONSTRUCTOR");
        Object orFail = GeneratorKt.getOrFail(functionGenerator, CONSTRUCTOR, ktConstructor);
        Intrinsics.checkNotNull(orFail, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) orFail;
        List<KtContextReceiver> contextReceivers = ktClassOrObject.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            if (ktTypeReferenceTypeReference != null) {
                arrayList.add(ktTypeReferenceTypeReference);
            }
        }
        ArrayList arrayList2 = arrayList;
        KtSecondaryConstructor ktSecondaryConstructor = ktConstructor;
        Integer startOffsetOfConstructorDeclarationKeywordOrNull = DeclarationStartOffsetKt.getStartOffsetOfConstructorDeclarationKeywordOrNull(ktSecondaryConstructor);
        IrConstructor irConstructorDeclareConstructor = getContext().getSymbolTable().getDescriptorExtension().declareConstructor(classConstructorDescriptor, new FunctionGenerator$declareConstructor$1(classConstructorDescriptor, this, startOffsetOfConstructorDeclarationKeywordOrNull != null ? startOffsetOfConstructorDeclarationKeywordOrNull.intValue() : PsiUtilsKt.getPureStartOffset(ktSecondaryConstructor), PsiUtilsKt.getPureEndOffset(ktSecondaryConstructor), IrDeclarationOrigin.DEFINED.INSTANCE, arrayList2));
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irConstructorDeclareConstructor);
        IrConstructor irConstructor = irConstructorDeclareConstructor;
        generateValueParameterDeclarations$default(this, irConstructor, ktSecondaryConstructor, null, arrayList2, false, 16, null);
        if (getContext().getConfiguration().getGenerateBodies()) {
            IrBody irBodyGenerateSecondaryConstructorBody = null;
            BodyGenerator bodyGeneratorCreateBodyGenerator$default = DeclarationGeneratorKt.createBodyGenerator$default(functionGenerator, irConstructor.getSymbol(), null, 2, null);
            ClassDescriptor constructedClass = classConstructorDescriptor.getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
            if (!DescriptorUtilsKt.isEffectivelyExternal(constructedClass)) {
                if (KotlinUtilsKt.isConstructorDelegatingToSuper(ktConstructor, bodyGeneratorCreateBodyGenerator$default.getContext().getBindingContext())) {
                    irBodyGenerateSecondaryConstructorBody = bodyGeneratorCreateBodyGenerator$default.generateSecondaryConstructorBodyWithNestedInitializers(ktConstructor);
                } else {
                    irBodyGenerateSecondaryConstructorBody = bodyGeneratorCreateBodyGenerator$default.generateSecondaryConstructorBody(ktConstructor);
                }
            }
            irConstructor.setBody(irBodyGenerateSecondaryConstructorBody);
        }
        KotlinType returnType = classConstructorDescriptor.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        irConstructor.setReturnType(toIrType(returnType));
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irConstructorDeclareConstructor);
        return irConstructor;
    }

    public final void generateSyntheticFunctionParameterDeclarations(IrFunction irFunction) {
        List<TypeParameterDescriptor> typeParameters;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        FunctionDescriptor descriptor = irFunction.getDescriptor();
        if (descriptor instanceof PropertyAccessorDescriptor) {
            typeParameters = ((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty().getTypeParameters();
        } else {
            typeParameters = descriptor.getTypeParameters();
        }
        Intrinsics.checkNotNull(typeParameters);
        getDeclarationGenerator().generateScopedTypeParameterDeclarations(irFunction, typeParameters);
        generateValueParameterDeclarations(irFunction, null, null, CollectionsKt.emptyList(), false);
    }

    static /* synthetic */ void generateValueParameterDeclarations$default(FunctionGenerator functionGenerator, IrFunction irFunction, KtPureElement ktPureElement, KtPureElement ktPureElement2, List list, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = true;
        }
        functionGenerator.generateValueParameterDeclarations(irFunction, ktPureElement, ktPureElement2, list, z);
    }

    private final void generateValueParameterDeclarations(IrFunction irFunction, KtPureElement ktParameterOwner, KtPureElement ktReceiverParameterElement, List<? extends KtPureElement> ktContextReceiverParameterElements, boolean withDefaultValues) {
        KtPureElement ktPureElement;
        IrValueParameter irValueParameterGenerateReceiverParameterDeclaration;
        IrValueParameter irValueParameterGenerateReceiverParameterDeclaration2;
        IrExpressionBody irExpressionBodyCreateExpressionBody;
        KtExpression defaultValue;
        FunctionDescriptor descriptor = irFunction.getDescriptor();
        ReceiverParameterDescriptor dispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            ktPureElement = ktParameterOwner;
            irValueParameterGenerateReceiverParameterDeclaration = generateReceiverParameterDeclaration(dispatchReceiverParameter, ktPureElement, irFunction);
        } else {
            ktPureElement = ktParameterOwner;
            irValueParameterGenerateReceiverParameterDeclaration = null;
        }
        irFunction.setDispatchReceiverParameter(irValueParameterGenerateReceiverParameterDeclaration);
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            irValueParameterGenerateReceiverParameterDeclaration2 = generateReceiverParameterDeclaration(extensionReceiverParameter, ktReceiverParameterElement == null ? ktPureElement : ktReceiverParameterElement, irFunction);
        } else {
            irValueParameterGenerateReceiverParameterDeclaration2 = null;
        }
        irFunction.setExtensionReceiverParameter(irValueParameterGenerateReceiverParameterDeclaration2);
        BodyGenerator bodyGeneratorCreateBodyGenerator$default = DeclarationGeneratorKt.createBodyGenerator$default(this, irFunction.getSymbol(), null, 2, null);
        int size = descriptor.getContextReceiverParameters().size();
        irFunction.setContextReceiverParametersCount(size);
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) obj;
            Intrinsics.checkNotNull(receiverParameterDescriptor);
            ReceiverParameterDescriptor receiverParameterDescriptor2 = receiverParameterDescriptor;
            KtPureElement ktPureElement2 = (KtPureElement) CollectionsKt.getOrNull(ktContextReceiverParameterElements, i2);
            if (ktPureElement2 == null) {
                ktPureElement2 = ktPureElement;
            }
            arrayList.add(declareParameter(receiverParameterDescriptor2, ktPureElement2, irFunction, null, Integer.valueOf(i2)));
            i2 = i3;
        }
        irFunction.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList));
        List<IrValueParameter> valueParameters2 = irFunction.getValueParameters();
        List<ValueParameterDescriptor> valueParameters3 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters3, "getValueParameters(...)");
        List<ValueParameterDescriptor> list2 = valueParameters3;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        int i4 = 0;
        for (Object obj2 : list2) {
            int i5 = i4 + 1;
            if (i4 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj2;
            Intrinsics.checkNotNull(valueParameterDescriptor);
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(valueParameterDescriptor);
            arrayList2.add(declareParameter(valueParameterDescriptor, sourceFromDescriptor instanceof KtParameter ? (KtParameter) sourceFromDescriptor : null, irFunction, null, Integer.valueOf(i4 + size)));
            i4 = i5;
        }
        irFunction.setValueParameters(CollectionsKt.plus((Collection) valueParameters2, (Iterable) arrayList2));
        if (!withDefaultValues) {
            return;
        }
        Iterator it = CollectionsKt.drop(irFunction.getValueParameters(), size).iterator();
        while (true) {
            int i6 = i;
            if (!it.hasNext()) {
                return;
            }
            Object next = it.next();
            i = i6 + 1;
            if (i6 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) next;
            ValueParameterDescriptor valueParameterDescriptor2 = descriptor.getValueParameters().get(i6);
            Intrinsics.checkNotNull(valueParameterDescriptor2);
            PsiElement sourceFromDescriptor2 = DescriptorToSourceUtils.getSourceFromDescriptor(valueParameterDescriptor2);
            KtParameter ktParameter = sourceFromDescriptor2 instanceof KtParameter ? (KtParameter) sourceFromDescriptor2 : null;
            if (ktParameter == null || (defaultValue = ktParameter.getDefaultValue()) == null) {
                irExpressionBodyCreateExpressionBody = null;
            } else {
                CallableDescriptor containingDeclaration = valueParameterDescriptor2.getContainingModule();
                ConstructorDescriptor constructorDescriptor = containingDeclaration instanceof ConstructorDescriptor ? (ConstructorDescriptor) containingDeclaration : null;
                if (constructorDescriptor != null && DescriptorUtilsKt.isAnnotationConstructor(constructorDescriptor)) {
                    Intrinsics.checkNotNull(defaultValue);
                    irExpressionBodyCreateExpressionBody = generateDefaultAnnotationParameterValue(defaultValue, valueParameterDescriptor2);
                } else if (getContext().getConfiguration().getGenerateBodies()) {
                    Intrinsics.checkNotNull(defaultValue);
                    irExpressionBodyCreateExpressionBody = bodyGeneratorCreateBodyGenerator$default.generateExpressionBody(defaultValue);
                } else {
                    IrFactory irFactory = getContext().getIrFactory();
                    Intrinsics.checkNotNull(defaultValue);
                    KtExpression ktExpression = defaultValue;
                    int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
                    int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
                    IrType nothingType = getContext().getIrBuiltIns().getNothingType();
                    String simpleName = defaultValue.getClass().getSimpleName();
                    Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                    irExpressionBodyCreateExpressionBody = IrFactoryHelpersKt.createExpressionBody(irFactory, new IrErrorExpressionImpl(startOffsetSkippingComments, endOffset, nothingType, simpleName));
                }
            }
            irValueParameter.setDefaultValue(irExpressionBodyCreateExpressionBody);
        }
    }

    private final IrValueParameter generateReceiverParameterDeclaration(ReceiverParameterDescriptor receiverParameterDescriptor, KtPureElement ktElement, IrElement irOwnerElement) {
        Name nameIdentifier = null;
        String str = null;
        nameIdentifier = null;
        if (getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.NewCapturedReceiverFieldNamingConvention) && (ktElement instanceof KtFunctionLiteral)) {
            String callLabelForLambdaArgument = getCallLabelForLambdaArgument((KtFunctionLiteral) ktElement, getContext().getBindingContext());
            if (callLabelForLambdaArgument != null) {
                str = Name.isValidIdentifier(callLabelForLambdaArgument) ? callLabelForLambdaArgument : null;
                if (str == null) {
                    str = AsmUtil.RECEIVER_PARAMETER_NAME;
                }
            }
            nameIdentifier = Name.identifier(AsmUtil.LABELED_THIS_PARAMETER + str);
        }
        return declareParameter$default(this, receiverParameterDescriptor, ktElement, irOwnerElement, nameIdentifier, null, 16, null);
    }

    private final String getCallLabelForLambdaArgument(KtFunctionLiteral declaration, BindingContext bindingContext) {
        KtCallExpression ktCallExpression;
        String name2;
        PsiElement parent = declaration.getLightParent();
        KtLambdaExpression ktLambdaExpression = parent instanceof KtLambdaExpression ? (KtLambdaExpression) parent : null;
        if (ktLambdaExpression == null) {
            return null;
        }
        PsiElement parent2 = ktLambdaExpression.getLightParent();
        if ((parent2 instanceof KtLabeledExpression) && (name2 = ((KtLabeledExpression) parent2).getName()) != null) {
            return name2;
        }
        PsiElement parent3 = ktLambdaExpression.getLightParent();
        if (parent3 instanceof KtLambdaArgument) {
            PsiElement parent4 = ((KtLambdaArgument) parent3).getLightParent();
            ktCallExpression = parent4 instanceof KtCallExpression ? (KtCallExpression) parent4 : null;
            if (ktCallExpression == null) {
                return null;
            }
        } else {
            if (!(parent3 instanceof KtValueArgument)) {
                return null;
            }
            PsiElement parent5 = ((KtValueArgument) parent3).getLightParent();
            KtValueArgumentList ktValueArgumentList = parent5 instanceof KtValueArgumentList ? (KtValueArgumentList) parent5 : null;
            if (ktValueArgumentList == null) {
                return null;
            }
            PsiElement parent6 = ktValueArgumentList.getLightParent();
            ktCallExpression = parent6 instanceof KtCallExpression ? (KtCallExpression) parent6 : null;
            if (ktCallExpression == null) {
                return null;
            }
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktCallExpression, bindingContext);
        if (resolvedCall == null) {
            return null;
        }
        return resolvedCall.getResultingDescriptor().getShortName().asString();
    }

    static /* synthetic */ IrValueParameter declareParameter$default(FunctionGenerator functionGenerator, ParameterDescriptor parameterDescriptor, KtPureElement ktPureElement, IrElement irElement, Name name2, Integer num, int i, Object obj) {
        return functionGenerator.declareParameter(parameterDescriptor, ktPureElement, irElement, (i & 8) != 0 ? null : name2, (i & 16) != 0 ? null : num);
    }

    private final IrValueParameter declareParameter(ParameterDescriptor descriptor, KtPureElement ktElement, IrElement irOwnerElement, Name name2, Integer index) {
        KotlinType varargElementType;
        IrDeclarationOrigin.DESTRUCTURED_OBJECT_PARAMETER destructured_object_parameter = IrDeclarationOrigin.DEFINED.INSTANCE;
        if (ktElement instanceof KtParameter) {
            if (UnderscoreUtilKt.isSingleUnderscore((KtNamedDeclaration) ktElement)) {
                destructured_object_parameter = IrDeclarationOrigin.UNDERSCORE_PARAMETER.INSTANCE;
            } else if (((KtParameter) ktElement).getDestructuringDeclaration() != null) {
                destructured_object_parameter = IrDeclarationOrigin.DESTRUCTURED_OBJECT_PARAMETER.INSTANCE;
            }
        }
        IrDeclarationOrigin irDeclarationOrigin = destructured_object_parameter;
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        int pureStartOffset = ktElement != null ? PsiUtilsKt.getPureStartOffset(ktElement) : irOwnerElement.getStartOffset();
        int pureEndOffset = ktElement != null ? PsiUtilsKt.getPureEndOffset(ktElement) : irOwnerElement.getEndOffset();
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        ValueParameterDescriptor valueParameterDescriptor = descriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) descriptor : null;
        IrType irType2 = (valueParameterDescriptor == null || (varargElementType = valueParameterDescriptor.getVarargElementType()) == null) ? null : toIrType(varargElementType);
        IrSimpleFunction irSimpleFunction = irOwnerElement instanceof IrSimpleFunction ? (IrSimpleFunction) irOwnerElement : null;
        return DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, pureStartOffset, pureEndOffset, irDeclarationOrigin, descriptor, irType, irType2, name2, index, irSimpleFunction != null && irSimpleFunction.getIsTailrec() && getContext().getExtensions().getParametersAreAssignable(), null, 512, null);
    }

    private final IrExpressionBody generateDefaultAnnotationParameterValue(KtExpression valueExpression, ValueParameterDescriptor valueParameterDescriptor) {
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.INSTANCE.getConstant(valueExpression, getContext().getBindingContext());
        if (constant != null) {
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ConstantValue<?> constantValue = constant.toConstantValue(type);
            if (constantValue != null) {
                IrExpression irExpressionGenerateAnnotationValueAsExpression = getContext().getConstantValueGenerator().generateAnnotationValueAsExpression(-1, -1, constantValue, valueParameterDescriptor);
                if (irExpressionGenerateAnnotationValueAsExpression == null) {
                    throw new IllegalStateException(("Could not convert annotation default " + PsiUtilsKt.getElementTextWithContext(valueExpression)).toString());
                }
                return getContext().getIrFactory().createExpressionBody(-1, -1, irExpressionGenerateAnnotationValueAsExpression);
            }
        }
        throw new IllegalStateException(("Constant value expected for default parameter value in annotation, got " + valueExpression).toString());
    }
}
