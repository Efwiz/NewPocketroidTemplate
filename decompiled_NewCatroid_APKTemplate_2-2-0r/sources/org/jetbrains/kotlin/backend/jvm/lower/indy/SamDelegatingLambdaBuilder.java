package org.jetbrains.kotlin.backend.jvm.lower.indy;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSubstitutor;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: SamDelegatingLambdaBlock.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ(\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0002J&\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\nH\u0002J.\u0010\u001e\u001a\u00020\u0017*\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020$H\u0002J\u001c\u0010%\u001a\u00020&*\u00020'2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBuilder;", "", "jvmContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBlock;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "superType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "scopeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "buildNullableDelegatingLambda", "buildRegularDelegatingLambda", "createDelegatingLambda", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", Constants.TMP_DIRECTORY_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "createLambdaValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "superMethod", "lambda", "typeSubstitutor", "Lorg/jetbrains/kotlin/ir/types/IrTypeSubstitutor;", "createTypeSubstitutor", "irType", "copySubstituted", "function", "substitutor", "newIndex", "", "newName", "Lorg/jetbrains/kotlin/name/Name;", "createDelegatingLambdaReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamDelegatingLambdaBuilder {
    private final JvmBackendContext jvmContext;

    public SamDelegatingLambdaBuilder(JvmBackendContext jvmContext) {
        Intrinsics.checkNotNullParameter(jvmContext, "jvmContext");
        this.jvmContext = jvmContext;
    }

    public final SamDelegatingLambdaBlock build(IrExpression expression, IrType superType, IrSymbol scopeSymbol, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(scopeSymbol, "scopeSymbol");
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (IrTypeUtilsKt.isNullable(superType) && IrTypeUtilsKt.isNullable(expression.getType())) {
            return buildNullableDelegatingLambda(expression, superType, scopeSymbol, parent);
        }
        return buildRegularDelegatingLambda(expression, superType, scopeSymbol, parent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [T, org.jetbrains.kotlin.ir.expressions.IrFunctionReference] */
    private final SamDelegatingLambdaBlock buildRegularDelegatingLambda(IrExpression expression, IrType superType, IrSymbol scopeSymbol, IrDeclarationParent parent) {
        IrFunctionReference irFunctionReference;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.jvmContext, scopeSymbol, expression);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrStatementOrigin.LAMBDA lambda = IrStatementOrigin.LAMBDA.INSTANCE;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), lambda, null, false, 64, null);
        IrSimpleFunction irSimpleFunctionCreateDelegatingLambda = createDelegatingLambda(expression, superType, ExpressionHelpersKt.irTemporary$default(irBlockBuilder, expression, null, null, false, null, 30, null), parent);
        irBlockBuilder.unaryPlus(irSimpleFunctionCreateDelegatingLambda);
        ?? CreateDelegatingLambdaReference = createDelegatingLambdaReference(jvmIrBuilderCreateJvmIrBuilder, expression, irSimpleFunctionCreateDelegatingLambda);
        irBlockBuilder.unaryPlus((IrStatement) CreateDelegatingLambdaReference);
        objectRef.element = CreateDelegatingLambdaReference;
        IrContainerExpression irBlockBody = irBlockBuilder.getIrBlockBody();
        irBlockBody.setType(expression.getType());
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("ref");
            irFunctionReference = null;
        } else {
            irFunctionReference = (IrFunctionReference) objectRef.element;
        }
        return new RegularDelegatingLambdaBlock(irBlockBody, irFunctionReference);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [T, org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl] */
    /* JADX WARN: Type inference failed for: r2v5, types: [T, org.jetbrains.kotlin.ir.expressions.IrContainerExpression] */
    /* JADX WARN: Type inference failed for: r3v4, types: [T, org.jetbrains.kotlin.ir.expressions.IrFunctionReference] */
    private final SamDelegatingLambdaBlock buildNullableDelegatingLambda(IrExpression expression, IrType superType, IrSymbol scopeSymbol, IrDeclarationParent parent) {
        IrContainerExpression irContainerExpression;
        IrFunctionReference irFunctionReference;
        IrExpression irExpression;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.jvmContext, scopeSymbol, expression);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), IrStatementOrigin.LAMBDA.INSTANCE, null, false, 64, null);
        IrContainerExpression irContainerExpression2 = null;
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, expression, null, null, false, null, 30, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(irBlockBuilder2.getContext(), irBlockBuilder2.getScope(), irBlockBuilder2.getStartOffset(), irBlockBuilder2.getEndOffset(), null, null, false, 64, null);
        IrSimpleFunction irSimpleFunctionCreateDelegatingLambda = createDelegatingLambda(expression, superType, irVariableIrTemporary$default, parent);
        irBlockBuilder3.unaryPlus(irSimpleFunctionCreateDelegatingLambda);
        ?? CreateDelegatingLambdaReference = createDelegatingLambdaReference(jvmIrBuilderCreateJvmIrBuilder, expression, irSimpleFunctionCreateDelegatingLambda);
        irBlockBuilder3.unaryPlus((IrStatement) CreateDelegatingLambdaReference);
        objectRef.element = CreateDelegatingLambdaReference;
        objectRef3.element = irBlockBuilder3.getIrBlockBody();
        IrType type = expression.getType();
        IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default);
        IrConstImpl irConstImplIrNull = ExpressionHelpersKt.irNull(irBlockBuilder2);
        if (objectRef3.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("ifNotNullBlock");
            irContainerExpression = null;
        } else {
            irContainerExpression = (IrContainerExpression) objectRef3.element;
        }
        ?? IrIfNull = ExpressionHelpersKt.irIfNull(irBlockBuilder2, type, irGetValueImplIrGet, irConstImplIrNull, irContainerExpression);
        irBlockBuilder.unaryPlus((IrStatement) IrIfNull);
        objectRef2.element = IrIfNull;
        IrContainerExpression irBlockBody = irBlockBuilder.getIrBlockBody();
        irBlockBody.setType(expression.getType());
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("ref");
            irFunctionReference = null;
        } else {
            irFunctionReference = (IrFunctionReference) objectRef.element;
        }
        if (objectRef2.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("ifExpr");
            irExpression = null;
        } else {
            irExpression = (IrExpression) objectRef2.element;
        }
        if (objectRef3.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("ifNotNullBlock");
        } else {
            irContainerExpression2 = (IrContainerExpression) objectRef3.element;
        }
        return new NullableDelegatingLambdaBlock(irBlockBody, irFunctionReference, irExpression, irContainerExpression2);
    }

    private final IrSimpleFunction createDelegatingLambda(IrExpression expression, IrType superType, IrVariable tmp, IrDeclarationParent parent) {
        IrSimpleFunction singleAbstractMethod;
        IrClass owner;
        IrClass irClass = IrTypesKt.getClass(superType);
        int i = 1;
        if (irClass == null || (singleAbstractMethod = JvmIrUtilsKt.getSingleAbstractMethod(irClass)) == null) {
            throw new AssertionError("SAM type expected: " + RenderIrElementKt.render$default(superType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        int i2 = 0;
        int size = singleAbstractMethod.getValueParameters().size() + (singleAbstractMethod.getExtensionReceiverParameter() == null ? 0 : 1);
        if (singleAbstractMethod.getIsSuspend()) {
            owner = this.jvmContext.getIr().getSymbols().suspendFunctionN(size).getOwner();
        } else {
            owner = this.jvmContext.getIr().getSymbols().functionN(size).getOwner();
        }
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner)) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.INVOKE)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
            IrTypeSubstitutor irTypeSubstitutorCreateTypeSubstitutor = createTypeSubstitutor(superType);
            IrFactory irFactory = this.jvmContext.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameSpecial = Name.special(SpecialNames.ANONYMOUS_STRING);
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            irFunctionBuilder.setName(nameSpecial);
            irFunctionBuilder.setReturnType(irTypeSubstitutorCreateTypeSubstitutor.substitute(singleAbstractMethod.getReturnType()));
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            irFunctionBuilder.setVisibility(LOCAL);
            irFunctionBuilder.setModality(Modality.FINAL);
            irFunctionBuilder.setOrigin(IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE);
            irFunctionBuilder.setSuspend(singleAbstractMethod.getIsSuspend());
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(null);
            irSimpleFunctionBuildFunction.setExtensionReceiverParameter(null);
            irSimpleFunctionBuildFunction.setValueParameters(createLambdaValueParameters(singleAbstractMethod, irSimpleFunctionBuildFunction, irTypeSubstitutorCreateTypeSubstitutor));
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.jvmContext, irSimpleFunctionBuildFunction.getSymbol(), expression);
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunction3);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irImplicitCast(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, tmp), IrTypesKt.typeWith(owner, new IrType[0])));
            if (irSimpleFunction3.getExtensionReceiverParameter() != null) {
                irFunctionAccessExpressionIrCall.setExtensionReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irSimpleFunctionBuildFunction.getValueParameters().get(0)));
            } else {
                i = 0;
            }
            int size2 = irSimpleFunction3.getValueParameters().size();
            while (i2 < size2) {
                irFunctionAccessExpressionIrCall.putValueArgument(i2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irSimpleFunctionBuildFunction.getValueParameters().get(i)));
                i2++;
                i++;
            }
            Unit unit = Unit.INSTANCE;
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irFunctionAccessExpressionIrCall));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
            irSimpleFunctionBuildFunction.setParent(parent);
            return irSimpleFunctionBuildFunction;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final List<IrValueParameter> createLambdaValueParameters(IrSimpleFunction superMethod, IrSimpleFunction lambda, IrTypeSubstitutor typeSubstitutor) {
        ArrayList arrayList = new ArrayList();
        IrValueParameter extensionReceiverParameter = superMethod.getExtensionReceiverParameter();
        int i = 0;
        if (extensionReceiverParameter != null) {
            Name nameIdentifier = Name.identifier(AsmUtil.RECEIVER_PARAMETER_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            arrayList.add(copySubstituted(extensionReceiverParameter, lambda, typeSubstitutor, 0, nameIdentifier));
            i = 1;
        }
        Iterator<T> it = superMethod.getValueParameters().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return arrayList;
            }
            i = i2 + 1;
            arrayList.add(copySubstituted$default(this, (IrValueParameter) it.next(), lambda, typeSubstitutor, i2, null, 8, null));
        }
    }

    static /* synthetic */ IrValueParameter copySubstituted$default(SamDelegatingLambdaBuilder samDelegatingLambdaBuilder, IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, IrTypeSubstitutor irTypeSubstitutor, int i, Name name2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            name2 = irValueParameter.getName();
        }
        return samDelegatingLambdaBuilder.copySubstituted(irValueParameter, irSimpleFunction, irTypeSubstitutor, i, name2);
    }

    private final IrFunctionReference createDelegatingLambdaReference(JvmIrBuilder jvmIrBuilder, IrExpression irExpression, IrSimpleFunction irSimpleFunction) {
        return new IrFunctionReferenceImpl(jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), irExpression.getType(), irSimpleFunction.getSymbol(), 0, irSimpleFunction.getValueParameters().size(), null, IrStatementOrigin.LAMBDA.INSTANCE);
    }

    private final IrTypeSubstitutor createTypeSubstitutor(IrType irType) {
        if (!(irType instanceof IrSimpleType)) {
            throw new AssertionError("Simple type expected: " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null) {
            throw new AssertionError("Class type expected: " + RenderIrElementKt.render$default((IrSimpleType) irType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        List<IrTypeParameter> typeParameters = classOrNull.getOwner().getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        return new IrTypeSubstitutor(arrayList, ((IrSimpleType) irType).getArguments(), this.jvmContext.getIrBuiltIns(), false, 8, null);
    }

    private final IrValueParameter copySubstituted(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, IrTypeSubstitutor irTypeSubstitutor, int i, Name name2) {
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setName(name2);
        irValueParameterBuilder.setIndex(i);
        irValueParameterBuilder.setType(irTypeSubstitutor.substitute(irValueParameter.getType()));
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        return DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2);
    }
}
