package org.jetbrains.kotlin.psi2ir.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions;
import org.jetbrains.kotlin.psi2ir.generators.OperatorConventionsKt;
import org.jetbrains.kotlin.psi2ir.generators.SamConversionsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionBase;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: InsertImplicitCasts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009c\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\tH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u000e\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J\u0014\u0010(\u001a\u00020)2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030+H\u0016J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020)2\u0006\u0010*\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020-2\u0006\u0010.\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020-2\u0006\u0010.\u001a\u000205H\u0016J\u0010\u00106\u001a\u00020)2\u0006\u0010*\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020)2\u0006\u00109\u001a\u00020:H\u0016J\u0014\u0010;\u001a\u00020)2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030<H\u0016J\u0010\u0010=\u001a\u00020)2\u0006\u0010*\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020)2\u0006\u0010*\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020)2\u0006\u0010*\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020)2\u0006\u0010*\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020)2\u0006\u0010F\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020)2\u0006\u0010*\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020)2\u0006\u0010*\u001a\u00020KH\u0016J\u0010\u0010L\u001a\u00020M2\u0006\u0010.\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020)2\u0006\u0010*\u001a\u00020OH\u0016J\f\u0010P\u001a\u00020Q*\u00020\u001dH\u0002J\u0014\u0010R\u001a\u00020)*\u00020)2\u0006\u0010S\u001a\u00020\u0014H\u0002J\"\u0010R\u001a\u00020)*\u00020)2\b\u0010T\u001a\u0004\u0018\u00010\u001d2\n\b\u0002\u0010U\u001a\u0004\u0018\u00010\u001dH\u0002J\u0014\u0010V\u001a\u00020 *\u00020W2\u0006\u0010X\u001a\u00020\u001dH\u0002J\u0014\u0010Y\u001a\u00020)*\u00020)2\u0006\u0010Z\u001a\u00020\u001dH\u0002J\f\u0010[\u001a\u00020)*\u00020)H\u0002J\u000e\u0010\\\u001a\u0004\u0018\u00010\u001d*\u00020\u001dH\u0002J\f\u0010]\u001a\u00020Q*\u00020\u001dH\u0002J\u001c\u0010^\u001a\u00020)*\u00020)2\u0006\u0010Z\u001a\u00020\u001d2\u0006\u0010_\u001a\u00020`H\u0002J\u001c\u0010a\u001a\u00020)*\u00020)2\u0006\u0010b\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020\u001dH\u0002J\u001c\u0010c\u001a\u00020)*\u00020)2\u0006\u0010Z\u001a\u00020\u001d2\u0006\u0010d\u001a\u00020eH\u0002J\u001c\u0010f\u001a\u00020)*\u00020)2\u0006\u0010Z\u001a\u00020\u001d2\u0006\u0010d\u001a\u00020eH\u0002J\f\u0010g\u001a\u00020Q*\u00020\u001dH\u0002J\f\u0010h\u001a\u00020Q*\u00020)H\u0002J\f\u0010i\u001a\u00020Q*\u00020jH\u0002J\u001c\u0010k\u001a\u00020 *\u00020l2\u0006\u0010X\u001a\u00020\u001d2\u0006\u0010U\u001a\u00020\u001dH\u0002J\f\u0010m\u001a\u00020\u0014*\u00020\u001dH\u0002J\f\u0010n\u001a\u00020\u001d*\u00020\u001dH\u0002J\u0018\u0010o\u001a\u00020 *\u0006\u0012\u0002\b\u00030<2\u0006\u0010\u0019\u001a\u00020\tH\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0011\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0019\u001a\u00020\t*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006p"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/transformations/InsertImplicitCasts;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "callToSubstitutedDescriptorMap", "", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generatorExtensions", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Ljava/util/Map;Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "expectedFunctionExpressionReturnType", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lkotlin/collections/HashMap;", "operatorsWithDeprecatedIntegerValueTypeLiteralConversion", "", "Lorg/jetbrains/kotlin/name/Name;", "substitutedDescriptor", "getSubstitutedDescriptor", "(Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getEffectiveDispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptor", "postprocessReturnExpressions", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "run", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "acceptsNullValues", "", "cast", "irType", "possiblyNonDenotableExpectedType", "originalExpectedType", "coerceInnerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expectedType", "coerceIntToAnotherIntegerType", "targetType", "coerceToUnit", "getFunctionReturnTypeOrNull", "hasEnhancedNullability", "implicitCast", "typeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "implicitNonNull", "valueType", "invokeIntegerCoercionFunction", "coercionFunName", "", "invokeUnsignedIntegerCoercionFunction", "isBuiltInIntegerType", "isSamConversion", "preventDeprecatedIntegerValueTypeLiteralConversion", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "recordExpectedLambdaReturnTypeIfAppropriate", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "toIrType", "toNonIrBased", "transformReceiverArguments", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InsertImplicitCasts extends IrElementTransformerVoid {
    private final Map<IrDeclarationReference, CallableDescriptor> callToSubstitutedDescriptorMap;
    private final HashMap<FunctionDescriptor, IrType> expectedFunctionExpressionReturnType;
    private final IrFile file;
    private final GeneratorExtensions generatorExtensions;
    private final IrBuiltIns irBuiltIns;
    private final Set<Name> operatorsWithDeprecatedIntegerValueTypeLiteralConversion;
    private final SymbolTable symbolTable;
    private final TypeTranslator typeTranslator;

    /* JADX INFO: compiled from: InsertImplicitCasts.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.SAM_CONVERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InsertImplicitCasts(IrBuiltIns irBuiltIns, TypeTranslator typeTranslator, Map<IrDeclarationReference, ? extends CallableDescriptor> callToSubstitutedDescriptorMap, GeneratorExtensions generatorExtensions, SymbolTable symbolTable, IrFile file) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        Intrinsics.checkNotNullParameter(callToSubstitutedDescriptorMap, "callToSubstitutedDescriptorMap");
        Intrinsics.checkNotNullParameter(generatorExtensions, "generatorExtensions");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(file, "file");
        this.irBuiltIns = irBuiltIns;
        this.typeTranslator = typeTranslator;
        this.callToSubstitutedDescriptorMap = callToSubstitutedDescriptorMap;
        this.generatorExtensions = generatorExtensions;
        this.symbolTable = symbolTable;
        this.file = file;
        this.expectedFunctionExpressionReturnType = new HashMap<>();
        OperatorNameConventions operatorNameConventions = OperatorNameConventions.INSTANCE;
        this.operatorsWithDeprecatedIntegerValueTypeLiteralConversion = SetsKt.setOf((Object[]) new Name[]{OperatorNameConventions.PLUS, OperatorNameConventions.MINUS, OperatorNameConventions.TIMES, OperatorNameConventions.DIV, OperatorNameConventions.REM, OperatorNameConventions.UNARY_PLUS, OperatorNameConventions.UNARY_MINUS, OperatorNameConventions.SHL, OperatorNameConventions.SHR, OperatorNameConventions.USHR, OperatorNameConventions.AND, OperatorNameConventions.OR, OperatorNameConventions.XOR, OperatorNameConventions.INV});
    }

    public final void run(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        IrElementTransformerVoidKt.transformChildrenVoid(element, this);
        postprocessReturnExpressions(element);
    }

    private final void postprocessReturnExpressions(IrElement element) {
        IrVisitorsKt.acceptChildrenVoid(element, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts.postprocessReturnExpressions.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
                visitCall(irCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                visitConst((IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
                visitElement(irElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, expression);
                IrType irType = (IrType) InsertImplicitCasts.this.expectedFunctionExpressionReturnType.get(expression.getReturnTargetSymbol().getDescriptor());
                if (irType == null) {
                    return;
                }
                expression.setValue(InsertImplicitCasts.this.cast(expression.getValue(), irType));
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(final IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                InsertImplicitCasts.this.typeTranslator.buildWithScope(declaration, new Function0<Unit>() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts$postprocessReturnExpressions$1$visitClass$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        IrElementVisitorVoid.DefaultImpls.visitClass(this.this$0, declaration);
                    }
                });
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(final IrFunction declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                InsertImplicitCasts.this.typeTranslator.buildWithScope(declaration, new Function0<Unit>() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts$postprocessReturnExpressions$1$visitFunction$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        IrElementVisitorVoid.DefaultImpls.visitFunction(this.this$0, declaration);
                    }
                });
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element2) {
                Intrinsics.checkNotNullParameter(element2, "element");
                IrVisitorsKt.acceptChildrenVoid(element2, this);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrVisitorsKt.acceptChildrenVoid(expression, this);
            }
        });
    }

    private final IrType toIrType(KotlinType kotlinType) {
        return this.typeTranslator.translateType(kotlinType);
    }

    private final CallableDescriptor getSubstitutedDescriptor(IrDeclarationReference irDeclarationReference) {
        CallableDescriptor callableDescriptor = this.callToSubstitutedDescriptorMap.get(irDeclarationReference);
        if (callableDescriptor != null) {
            return callableDescriptor;
        }
        DeclarationDescriptor descriptor = irDeclarationReference.getSymbol().getDescriptor();
        Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        return (CallableDescriptor) descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCallableReference(IrCallableReference<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        CallableDescriptor substitutedDescriptor = getSubstitutedDescriptor(expression);
        IrCallableReference<?> irCallableReference = expression;
        transformChildrenVoid(irCallableReference);
        transformReceiverArguments(irCallableReference, substitutedDescriptor);
        return irCallableReference;
    }

    private final void transformReceiverArguments(IrMemberAccessExpression<?> irMemberAccessExpression, CallableDescriptor callableDescriptor) {
        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
        irMemberAccessExpression.setDispatchReceiver(dispatchReceiver != null ? cast$default(this, dispatchReceiver, getEffectiveDispatchReceiverType(callableDescriptor), null, 2, null) : null);
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        ReceiverParameterDescriptor extensionReceiverParameter2 = callableDescriptor.getOriginal().getExtensionReceiverParameter();
        KotlinType type2 = extensionReceiverParameter2 != null ? extensionReceiverParameter2.getType() : null;
        IrExpression extensionReceiver = irMemberAccessExpression.getExtensionReceiver();
        irMemberAccessExpression.setExtensionReceiver(extensionReceiver != null ? cast(extensionReceiver, type, type2) : null);
    }

    private final KotlinType getEffectiveDispatchReceiverType(CallableDescriptor descriptor) {
        if (!(descriptor instanceof CallableMemberDescriptor)) {
            return null;
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
        if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (!(containingDeclaration instanceof ClassDescriptor)) {
                throw new AssertionError("Containing declaration for " + descriptor + " should be a class: " + containingDeclaration);
            }
            SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            return TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType);
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = callableMemberDescriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            return dispatchReceiverParameter.getType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        CallableDescriptor substitutedDescriptor = getSubstitutedDescriptor(expression);
        IrMemberAccessExpression<?> irMemberAccessExpression = expression;
        transformChildrenVoid(irMemberAccessExpression);
        IrMemberAccessExpression<?> irMemberAccessExpression2 = irMemberAccessExpression;
        transformReceiverArguments(irMemberAccessExpression2, substitutedDescriptor);
        int size = substitutedDescriptor.getValueParameters().size();
        for (int i = 0; i < size; i++) {
            int size2 = substitutedDescriptor.getContextReceiverParameters().size() + i;
            IrExpression valueArgument = irMemberAccessExpression2.getValueArgument(size2);
            if (valueArgument != null) {
                KotlinType type = substitutedDescriptor.getValueParameters().get(i).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                KotlinType type2 = substitutedDescriptor.getOriginal().getValueParameters().get(i).getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (isSamConversion(valueArgument) && KotlinBuiltIns.isNothing(type)) {
                    KotlinType type3 = substitutedDescriptor.getOriginal().getValueParameters().get(i).getType();
                    Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                    type = TypeUtilsKt.replaceArgumentsWithNothing(type3);
                }
                irMemberAccessExpression2.putValueArgument(size2, cast(valueArgument, type, type2));
            }
        }
        return irMemberAccessExpression;
    }

    private final boolean isSamConversion(IrExpression irExpression) {
        return (irExpression instanceof IrTypeOperatorCall) && ((IrTypeOperatorCall) irExpression).getOperator() == IrTypeOperator.SAM_CONVERSION;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBody visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        IrBlockBody irBlockBody = body;
        transformChildrenVoid(irBlockBody);
        int i = 0;
        for (Object obj : irBlockBody.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (irStatement instanceof IrExpression) {
                body.getStatements().set(i, coerceToUnit((IrExpression) irStatement));
            }
            i = i2;
        }
        return irBlockBody;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        IrExpression irExpressionCoerceToUnit;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrContainerExpression irContainerExpression = expression;
        transformChildrenVoid(irContainerExpression);
        IrContainerExpression irContainerExpression2 = irContainerExpression;
        if (irContainerExpression2.getStatements().isEmpty()) {
            return irContainerExpression2;
        }
        int lastIndex = CollectionsKt.getLastIndex(irContainerExpression2.getStatements());
        int i = 0;
        for (Object obj : irContainerExpression2.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (irStatement instanceof IrExpression) {
                List<IrStatement> statements = irContainerExpression2.getStatements();
                if (i == lastIndex) {
                    irExpressionCoerceToUnit = cast((IrExpression) irStatement, irContainerExpression2.getType());
                } else {
                    irExpressionCoerceToUnit = coerceToUnit((IrExpression) irStatement);
                }
                statements.set(i, irExpressionCoerceToUnit);
            }
            i = i2;
        }
        return irContainerExpression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        IrExpression irExpressionCast$default;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrReturn irReturn = expression;
        transformChildrenVoid(irReturn);
        IrReturn irReturn2 = irReturn;
        if (expression.getReturnTargetSymbol() instanceof IrConstructorSymbol) {
            irExpressionCast$default = coerceToUnit(irReturn2.getValue());
        } else {
            irExpressionCast$default = cast$default(this, irReturn2.getValue(), expression.getReturnTargetSymbol().getDescriptor().getReturnType(), null, 2, null);
        }
        irReturn2.setValue(irExpressionCast$default);
        return irReturn;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSetValue irSetValue = expression;
        transformChildrenVoid(irSetValue);
        IrSetValue irSetValue2 = irSetValue;
        irSetValue2.setValue(cast(irSetValue2.getValue(), expression.getSymbol().getOwner().getType()));
        return irSetValue;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrGetField irGetField = expression;
        transformChildrenVoid(irGetField);
        IrGetField irGetField2 = irGetField;
        IrExpression receiver = irGetField2.getReceiver();
        irGetField2.setReceiver(receiver != null ? cast$default(this, receiver, getEffectiveDispatchReceiverType(getSubstitutedDescriptor(expression)), null, 2, null) : null);
        return irGetField;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSetField irSetField = expression;
        transformChildrenVoid(irSetField);
        IrSetField irSetField2 = irSetField;
        CallableDescriptor substitutedDescriptor = getSubstitutedDescriptor(expression);
        Intrinsics.checkNotNull(substitutedDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) substitutedDescriptor;
        IrExpression receiver = irSetField2.getReceiver();
        irSetField2.setReceiver(receiver != null ? cast$default(this, receiver, getEffectiveDispatchReceiverType(propertyDescriptor), null, 2, null) : null);
        irSetField2.setValue(cast$default(this, irSetField2.getValue(), propertyDescriptor.getType(), null, 2, null));
        return irSetField;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrVariable visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrVariable irVariable = declaration;
        transformChildrenVoid(irVariable);
        IrVariable irVariable2 = irVariable;
        IrExpression initializer = irVariable2.getInitializer();
        irVariable2.setInitializer(initializer != null ? cast(initializer, declaration.getType()) : null);
        return irVariable2;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitField(final IrField declaration) {
        PropertyDescriptor descriptor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        TypeTranslator typeTranslator = this.typeTranslator;
        IrPropertySymbol correspondingPropertySymbol = declaration.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (descriptor = correspondingPropertySymbol.getDescriptor()) == null) {
            descriptor = declaration.getDescriptor();
        }
        return (IrStatement) typeTranslator.withTypeErasure(descriptor, new Function0<IrField>() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts.visitField.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                InsertImplicitCasts insertImplicitCasts = InsertImplicitCasts.this;
                IrField irField = declaration;
                insertImplicitCasts.transformChildrenVoid(irField);
                IrField irField2 = irField;
                IrExpressionBody initializer = irField2.getInitializer();
                if (initializer != null) {
                    KotlinType type = irField2.getDescriptor().getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    insertImplicitCasts.coerceInnerExpression(initializer, type);
                }
                return irField2;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitFunction(final IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (IrStatement) this.typeTranslator.buildWithScope(declaration, new Function0<IrFunction>() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts.visitFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunction invoke() {
                InsertImplicitCasts insertImplicitCasts = InsertImplicitCasts.this;
                IrFunction irFunction = declaration;
                insertImplicitCasts.transformChildrenVoid(irFunction);
                IrFunction irFunction2 = irFunction;
                for (IrValueParameter irValueParameter : irFunction2.getValueParameters()) {
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    if (defaultValue != null) {
                        KotlinType type = irValueParameter.getDescriptor().getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        insertImplicitCasts.coerceInnerExpression(defaultValue, type);
                    }
                }
                return irFunction2;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(final IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (IrStatement) this.typeTranslator.buildWithScope(declaration, new Function0<IrStatement>() { // from class: org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCasts.visitClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrStatement invoke() {
                return InsertImplicitCasts.super.visitClass(declaration);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrWhen irWhen = expression;
        transformChildrenVoid(irWhen);
        IrWhen irWhen2 = irWhen;
        for (IrBranch irBranch : irWhen2.getBranches()) {
            irBranch.setCondition(cast(irBranch.getCondition(), this.irBuiltIns.getBooleanType()));
            irBranch.setResult(cast(irBranch.getResult(), irWhen2.getType()));
        }
        return irWhen;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrLoop irLoop = loop;
        transformChildrenVoid(irLoop);
        IrLoop irLoop2 = irLoop;
        irLoop2.setCondition(cast(irLoop2.getCondition(), this.irBuiltIns.getBooleanType()));
        IrExpression body = irLoop2.getBody();
        irLoop2.setBody(body != null ? coerceToUnit(body) : null);
        return irLoop;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrThrow irThrow = expression;
        transformChildrenVoid(irThrow);
        IrThrow irThrow2 = irThrow;
        irThrow2.setValue(cast(irThrow2.getValue(), this.irBuiltIns.getThrowableType()));
        return irThrow;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        IrTry irTry = aTry;
        transformChildrenVoid(irTry);
        IrTry irTry2 = irTry;
        irTry2.setTryResult(cast(irTry2.getTryResult(), irTry2.getType()));
        for (IrCatch irCatch : irTry2.getCatches()) {
            irCatch.setResult(cast(irCatch.getResult(), irTry2.getType()));
        }
        IrExpression finallyExpression = irTry2.getFinallyExpression();
        irTry2.setFinallyExpression(finallyExpression != null ? coerceToUnit(finallyExpression) : null);
        return irTry;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()];
        if (i != 1) {
            if (i == 2) {
                transformChildrenVoid(expression);
                return cast(expression.getArgument(), expression.getTypeOperand());
            }
            return super.visitTypeOperator(expression);
        }
        IrTypeOperatorCall irTypeOperatorCall = expression;
        transformChildrenVoid(irTypeOperatorCall);
        IrTypeOperatorCall irTypeOperatorCall2 = irTypeOperatorCall;
        IrExpression argument = irTypeOperatorCall2.getArgument();
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irTypeOperatorCall2.getTypeOperand());
        Intrinsics.checkNotNull(originalKotlinType);
        irTypeOperatorCall2.setArgument(cast$default(this, argument, SamConversionsKt.getSubstitutedFunctionTypeForSamType(originalKotlinType), null, 2, null));
        return irTypeOperatorCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVararg irVararg = expression;
        transformChildrenVoid(irVararg);
        IrVararg irVararg2 = irVararg;
        int i = 0;
        for (Object obj : irVararg2.getElements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrVarargElement irVarargElement = (IrVarargElement) obj;
            if (irVarargElement instanceof IrSpreadElement) {
                IrSpreadElement irSpreadElement = (IrSpreadElement) irVarargElement;
                irSpreadElement.setExpression(cast(irSpreadElement.getExpression(), expression.getType()));
            } else if (irVarargElement instanceof IrExpression) {
                IrExpressionsKt.putElement(irVararg2, i, cast((IrExpression) irVarargElement, irVararg2.getVarargElementType()));
            }
            i = i2;
        }
        return irVararg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void coerceInnerExpression(IrExpressionBody irExpressionBody, KotlinType kotlinType) {
        irExpressionBody.setExpression(cast$default(this, irExpressionBody.getExpression(), kotlinType, null, 2, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression cast(IrExpression irExpression, IrType irType) {
        return cast$default(this, irExpression, IrTypeBaseKt.getOriginalKotlinType(irType), null, 2, null);
    }

    private final KotlinType getFunctionReturnTypeOrNull(KotlinType kotlinType) {
        if (FunctionTypesKt.isFunctionType(kotlinType) || FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getNullableAnyType();
        }
        return null;
    }

    static /* synthetic */ IrExpression cast$default(InsertImplicitCasts insertImplicitCasts, IrExpression irExpression, KotlinType kotlinType, KotlinType kotlinType2, int i, Object obj) {
        if ((i & 2) != 0) {
            kotlinType2 = kotlinType;
        }
        return insertImplicitCasts.cast(irExpression, kotlinType, kotlinType2);
    }

    private final IrExpression cast(IrExpression irExpression, KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null || KotlinTypeKt.isError(kotlinType)) {
            return irExpression;
        }
        KotlinType kotlinTypeApproximate = this.typeTranslator.approximate(kotlinType);
        if ((irExpression instanceof IrFunctionExpression) && kotlinType2 != null) {
            recordExpectedLambdaReturnTypeIfAppropriate((IrFunctionExpression) irExpression, kotlinTypeApproximate, kotlinType2);
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(kotlinTypeApproximate);
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irExpression.getType());
        if (originalKotlinType == null) {
            throw new IllegalStateException(("Expecting original kotlin type for IrType " + RenderIrElementKt.render$default(irExpression.getType(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (TypeUtilsKt.isUnit(kotlinTypeApproximate)) {
            return coerceToUnit(irExpression);
        }
        if (DynamicTypesKt.isDynamic(originalKotlinType) && !DynamicTypesKt.isDynamic(kotlinTypeApproximate)) {
            return TypeUtilsKt.isNullableAny(kotlinTypeApproximate) ? irExpression : implicitCast(irExpression, kotlinTypeApproximate, IrTypeOperator.IMPLICIT_DYNAMIC_CAST);
        }
        if (FlexibleTypesKt.isNullabilityFlexible(originalKotlinType) && KotlinUtilsKt.containsNull(originalKotlinType) && !acceptsNullValues(kotlinTypeApproximate)) {
            return implicitNonNull(irExpression, originalKotlinType, kotlinTypeApproximate);
        }
        if (hasEnhancedNullability(originalKotlinType) && !acceptsNullValues(kotlinTypeApproximate)) {
            return implicitNonNull(irExpression, originalKotlinType, kotlinTypeApproximate);
        }
        if (KotlinTypeChecker.DEFAULT.isSubtypeOf(toNonIrBased(originalKotlinType), TypeUtilsKt.makeNullable(toNonIrBased(kotlinTypeApproximate)))) {
            return irExpression;
        }
        if (KotlinBuiltIns.isInt(originalKotlinType) && isBuiltInIntegerType(kotlinTypeMakeNotNullable)) {
            return coerceIntToAnotherIntegerType(irExpression, kotlinTypeMakeNotNullable);
        }
        if (!KotlinUtilsKt.containsNull(originalKotlinType)) {
            kotlinTypeApproximate = kotlinTypeMakeNotNullable;
        }
        return implicitCast(irExpression, kotlinTypeApproximate, IrTypeOperator.IMPLICIT_CAST);
    }

    private final void recordExpectedLambdaReturnTypeIfAppropriate(IrFunctionExpression irFunctionExpression, KotlinType kotlinType, KotlinType kotlinType2) {
        KotlinType functionReturnTypeOrNull = getFunctionReturnTypeOrNull(kotlinType);
        if (functionReturnTypeOrNull == null) {
            return;
        }
        KotlinType functionReturnTypeOrNull2 = getFunctionReturnTypeOrNull(kotlinType2);
        if (functionReturnTypeOrNull2 == null || !TypeUtilsKt.isTypeParameter(functionReturnTypeOrNull2)) {
            this.expectedFunctionExpressionReturnType.put(irFunctionExpression.getFunction().getDescriptor(), toIrType(functionReturnTypeOrNull));
        }
    }

    private final boolean acceptsNullValues(KotlinType kotlinType) {
        return KotlinUtilsKt.containsNull(kotlinType) || hasEnhancedNullability(kotlinType);
    }

    private final boolean hasEnhancedNullability(KotlinType kotlinType) {
        return this.generatorExtensions.getEnhancedNullability().hasEnhancedNullability(kotlinType);
    }

    private final IrExpression implicitNonNull(IrExpression irExpression, KotlinType kotlinType, KotlinType kotlinType2) {
        return cast$default(this, implicitCast(irExpression, this.generatorExtensions.getEnhancedNullability().stripEnhancedNullability(TypeUtilsKt.makeNotNullable(FlexibleTypesKt.upperIfFlexible(kotlinType))), IrTypeOperator.IMPLICIT_NOTNULL), kotlinType2, null, 2, null);
    }

    private final IrExpression implicitCast(IrExpression irExpression, KotlinType kotlinType, IrTypeOperator irTypeOperator) {
        IrType irType = toIrType(kotlinType);
        return new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, irTypeOperator, irType, irExpression);
    }

    private final IrExpression coerceIntToAnotherIntegerType(IrExpression irExpression, KotlinType kotlinType) {
        IrExpression irExpressionM7212long;
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irExpression.getType());
        Intrinsics.checkNotNull(originalKotlinType);
        if (!TypeUtilsKt.isInt(originalKotlinType)) {
            throw new AssertionError("Expression of type 'kotlin.Int' expected: " + irExpression);
        }
        if (TypeUtilsKt.isInt(kotlinType)) {
            return irExpression;
        }
        if (this.generatorExtensions.getShouldPreventDeprecatedIntegerValueTypeLiteralConversion() && (irExpression instanceof IrCall) && preventDeprecatedIntegerValueTypeLiteralConversion((IrCall) irExpression)) {
            return irExpression;
        }
        if (irExpression instanceof IrConst) {
            Object value = ((IrConst) irExpression).getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) value).intValue();
            IrType irType = toIrType(kotlinType);
            if (TypeUtilsKt.isByte(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7207byte(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, (byte) iIntValue);
            } else if (TypeUtilsKt.isShort(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7213short(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, (short) iIntValue);
            } else if (TypeUtilsKt.isLong(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7212long(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, iIntValue);
            } else if (KotlinBuiltIns.isUByte(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7207byte(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, (byte) iIntValue);
            } else if (KotlinBuiltIns.isUShort(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7213short(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, (short) iIntValue);
            } else if (KotlinBuiltIns.isUInt(kotlinType)) {
                irExpressionM7212long = IrConstImpl.INSTANCE.m7211int(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, iIntValue);
            } else {
                if (!KotlinBuiltIns.isULong(kotlinType)) {
                    throw new AssertionError("Unexpected target type for integer coercion: " + kotlinType);
                }
                irExpressionM7212long = IrConstImpl.INSTANCE.m7212long(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, iIntValue);
            }
            return irExpressionM7212long;
        }
        if (TypeUtilsKt.isByte(kotlinType)) {
            return invokeIntegerCoercionFunction(irExpression, kotlinType, "toByte");
        }
        if (TypeUtilsKt.isShort(kotlinType)) {
            return invokeIntegerCoercionFunction(irExpression, kotlinType, "toShort");
        }
        if (TypeUtilsKt.isLong(kotlinType)) {
            return invokeIntegerCoercionFunction(irExpression, kotlinType, "toLong");
        }
        if (KotlinBuiltIns.isUByte(kotlinType)) {
            return invokeUnsignedIntegerCoercionFunction(irExpression, kotlinType, "toUByte");
        }
        if (KotlinBuiltIns.isUShort(kotlinType)) {
            return invokeUnsignedIntegerCoercionFunction(irExpression, kotlinType, "toUShort");
        }
        if (KotlinBuiltIns.isUInt(kotlinType)) {
            return invokeUnsignedIntegerCoercionFunction(irExpression, kotlinType, Namer.UINT_FROM_INT);
        }
        if (KotlinBuiltIns.isULong(kotlinType)) {
            return invokeUnsignedIntegerCoercionFunction(irExpression, kotlinType, "toULong");
        }
        throw new AssertionError("Unexpected target type for integer coercion: " + kotlinType);
    }

    private final boolean preventDeprecatedIntegerValueTypeLiteralConversion(IrCall irCall) {
        KotlinType type;
        FunctionDescriptor descriptor = irCall.getSymbol().getDescriptor();
        if (!this.operatorsWithDeprecatedIntegerValueTypeLiteralConversion.contains(descriptor.getShortName()) || CollectionsKt.contains(OperatorConventionsKt.getOPERATORS_DESUGARED_TO_CALLS(), irCall.getOrigin())) {
            return false;
        }
        if (descriptor.isInfix()) {
            IrFileEntry fileEntry = this.file.getFileEntry();
            PsiIrFileEntry psiIrFileEntry = fileEntry instanceof PsiIrFileEntry ? (PsiIrFileEntry) fileEntry : null;
            if ((psiIrFileEntry != null ? psiIrFileEntry.findPsiElement(irCall) : null) instanceof KtBinaryExpression) {
                return false;
            }
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        return (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null || !KotlinBuiltIns.isPrimitiveType(type)) ? false : true;
    }

    private final IrExpression invokeIntegerCoercionFunction(IrExpression irExpression, KotlinType kotlinType, String str) {
        MemberScope unsubstitutedMemberScope = this.irBuiltIns.getIntClass().getDescriptor().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), toIrType(kotlinType), this.symbolTable.getDescriptorExtension().referenceSimpleFunction(KotlinUtilsKt.findSingleFunction(unsubstitutedMemberScope, nameIdentifier)), 0, 0, null, null, 192, null);
        irCallImpl.setDispatchReceiver(irExpression);
        return irCallImpl;
    }

    private final IrExpression invokeUnsignedIntegerCoercionFunction(IrExpression irExpression, KotlinType kotlinType, String str) {
        Object next;
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        Intrinsics.checkNotNull(this$0);
        MemberScope memberScope = DescriptorUtilsKt.getModule(this$0).getPackage(StandardNames.BUILT_INS_PACKAGE_FQ_NAME).getMemberScope();
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Iterator<T> it = memberScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            ReceiverParameterDescriptor extensionReceiverParameter = ((SimpleFunctionDescriptor) next).getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                KotlinType type = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (TypeUtilsKt.isInt(type)) {
                    break;
                }
            }
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
        if (simpleFunctionDescriptor == null) {
            throw new AssertionError("Coercion function '" + str + "' not found");
        }
        IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), toIrType(kotlinType), this.symbolTable.getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor), 0, 0, null, null, 192, null);
        irCallImpl.setExtensionReceiver(irExpression);
        return irCallImpl;
    }

    private final boolean isBuiltInIntegerType(KotlinType kotlinType) {
        return KotlinBuiltIns.isByte(kotlinType) || KotlinBuiltIns.isShort(kotlinType) || KotlinBuiltIns.isInt(kotlinType) || KotlinBuiltIns.isLong(kotlinType) || KotlinBuiltIns.isUByte(kotlinType) || KotlinBuiltIns.isUShort(kotlinType) || KotlinBuiltIns.isUInt(kotlinType) || KotlinBuiltIns.isULong(kotlinType);
    }

    private final IrExpression coerceToUnit(IrExpression irExpression) {
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(IrTypesKt.toKotlinType(irExpression.getType()), IrTypesKt.toKotlinType(this.irBuiltIns.getUnitType())) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), this.irBuiltIns.getUnitType(), IrTypeOperator.IMPLICIT_COERCION_TO_UNIT, this.irBuiltIns.getUnitType(), irExpression);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.jetbrains.kotlin.descriptors.ClassifierDescriptor] */
    /* JADX WARN: Type inference failed for: r6v2 */
    private final KotlinType toNonIrBased(KotlinType kotlinType) {
        DeclarationSymbolMarker this$0;
        ?? r0;
        TypeProjectionBase typeProjectionImpl;
        if (!(kotlinType instanceof SimpleType) || KotlinTypeKt.isError(kotlinType) || (this$0 = kotlinType.getConstructor().getThis$0()) == null) {
            return kotlinType;
        }
        if (this$0 instanceof IrBasedDeclarationDescriptor) {
            IrBasedDeclarationDescriptor irBasedDeclarationDescriptor = (IrBasedDeclarationDescriptor) this$0;
            if (irBasedDeclarationDescriptor.getOwner().getSymbol().getHasDescriptor()) {
                r0 = this$0;
                r0 = this$0;
                DeclarationDescriptor descriptor = irBasedDeclarationDescriptor.getOwner().getSymbol().getDescriptor();
                Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptor");
                r0 = (ClassifierDescriptor) descriptor;
            }
        }
        if (r0 == 0) {
            return kotlinType;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i = 0;
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            if (typeProjection.isStarProjection()) {
                TypeParameterDescriptor typeParameterDescriptor = ((ClassDescriptor) r0).getTypeConstructor().getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
                typeProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
            } else {
                Variance projectionKind = typeProjection.getProjectionKind();
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                typeProjectionImpl = new TypeProjectionImpl(projectionKind, toNonIrBased(nullableAnyType));
            }
            arrayList.add(typeProjectionImpl);
            i = i2;
        }
        SimpleType defaultType = r0.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return TypeSubstitutionKt.replace$default(defaultType, arrayList, null, 2, null).makeNullableAsSpecified(kotlinType.getIsMarkedNullable());
    }
}
