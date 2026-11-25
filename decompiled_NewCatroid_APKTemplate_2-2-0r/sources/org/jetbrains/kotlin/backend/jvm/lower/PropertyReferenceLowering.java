package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInlineKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
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
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: PropertyReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ø\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002:\u0004mnopB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J$\u0010=\u001a\u00020>2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)2\u0006\u0010@\u001a\u00020,2\u0006\u0010A\u001a\u00020,H\u0002J\u0014\u0010B\u001a\u00020\u00182\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0014\u0010C\u001a\u00020,2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0014\u0010D\u001a\u00020\u00182\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0014\u0010E\u001a\u00020\u00182\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0010\u0010F\u001a\u00020>2\u0006\u0010G\u001a\u00020HH\u0016J$\u0010I\u001a\u00020J2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)2\u0006\u0010K\u001a\u00020\u00132\u0006\u0010L\u001a\u00020MH\u0002J\u0014\u0010N\u001a\u00020J2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0010\u0010O\u001a\u00020\u00182\u0006\u0010?\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020,H\u0016J\u0010\u0010T\u001a\u00020R2\u0006\u0010S\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020\u00182\u0006\u0010?\u001a\u00020WH\u0016J\u0010\u0010X\u001a\u00020\u00182\u0006\u0010?\u001a\u00020YH\u0016J\u0014\u0010Z\u001a\u00020\u0007*\u00020,2\u0006\u0010[\u001a\u00020\u0007H\u0002J9\u0010\\\u001a\u00020\u0007*\u00020,2\u0006\u0010[\u001a\u00020\u00072#\u0010]\u001a\u001f\u0012\u0004\u0012\u00020_\u0012\n\u0012\b\u0012\u0004\u0012\u00020a0`\u0012\u0004\u0012\u00020\u00180^¢\u0006\u0002\bbH\u0002J\u0018\u0010c\u001a\u00020\u0018*\u00020d2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0002J*\u0010e\u001a\u00020>*\u00020_2\u0006\u0010f\u001a\u00020g2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030)2\b\u0010h\u001a\u0004\u0018\u00010\u0018H\u0002J\f\u0010i\u001a\u00020,*\u00020WH\u0002J\u0012\u0010j\u001a\u0004\u0018\u00010\u0018*\u0006\u0012\u0002\b\u00030)H\u0002J\u0014\u0010k\u001a\u00020\u0013*\u00020\u00072\u0006\u0010l\u001a\u00020MH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0018\u00010\u000bR\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00130\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u0006\u0012\u0002\b\u00030\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0018\u0010\u001c\u001a\u00020\u001d*\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001e\u0010!\u001a\u0004\u0018\u00010\"*\u0006\u0012\u0002\b\u00030\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\u0004\u0018\u00010\u0011*\u0006\u0012\u0002\b\u00030\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001c\u0010(\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010*R\u0018\u0010+\u001a\u00020\u0013*\u00020,8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b+\u0010-R\u001e\u0010.\u001a\b\u0012\u0004\u0012\u0002000/*\u0002008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u001c\u00103\u001a\u000204*\u0006\u0012\u0002\b\u00030\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u001c\u00107\u001a\u000208*\u0006\u0012\u0002\b\u00030)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u001e\u0010;\u001a\u0004\u0018\u00010\u0011*\u0006\u0012\u0002\b\u00030\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b<\u0010'¨\u0006q"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "arrayItemGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "currentClassData", "Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$ClassData;", "kPropertiesFieldType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "kPropertyStarType", "Lorg/jetbrains/kotlin/ir/types/impl/IrSimpleTypeImpl;", "signatureStringIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "useOptimizedSuperClass", "", "usesPropertyParameterCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "constInitializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "getConstInitializer", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "fakeGetterSignature", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getFakeGetterSignature", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)Ljava/lang/String;", "field", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getField", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getter", "getGetter", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isJavaSyntheticPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;)Z", "isSynthetic", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "parentsWithSelf", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getParentsWithSelf", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lkotlin/sequences/Sequence;", "propertyContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getPropertyContainer", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "receiverType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getReceiverType", "(Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;)Lorg/jetbrains/kotlin/ir/types/IrType;", "setter", "getSetter", "addConstructor", "", "expression", "referenceClass", "superClass", "cachedKProperty", "createKPropertySubclass", "createReflectedKProperty", "createSpecializedKProperty", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "propertyReferenceKind", "Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$PropertyReferenceKind;", "mutable", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "", "propertyReferenceKindFor", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "addFakeOverride", "method", "addOverride", "buildBody", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lkotlin/ExtensionFunctionType;", "computeSignatureString", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "fillReflectedPropertyArguments", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "receiver", "findClassOwner", "getBoundReceiver", "usesParameter", "index", "ClassData", "PropertyInstance", "PropertyReferenceKind", "REFLECTED_PROPERTY_REFERENCE", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyReferenceLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final IrSimpleFunction arrayItemGetter;
    private final JvmBackendContext context;
    private ClassData currentClassData;
    private final IrSimpleType kPropertiesFieldType;
    private final IrSimpleTypeImpl kPropertyStarType;
    private final IrSimpleFunctionSymbol signatureStringIntrinsic;
    private final boolean useOptimizedSuperClass;
    private final ConcurrentHashMap<IrSymbol, Boolean> usesPropertyParameterCache;

    public PropertyReferenceLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(context.getIr().getSymbols().getArray().getOwner())) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), "get")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            this.arrayItemGetter = irSimpleFunction;
            this.signatureStringIntrinsic = this.context.getIr().getSymbols().getSignatureStringIntrinsic();
            IrSimpleTypeImpl irSimpleTypeImpl = new IrSimpleTypeImpl((IrClassifierSymbol) this.context.getIrBuiltIns().getKPropertyClass(), false, CollectionsKt.listOf(IrSimpleTypeImplKt.makeTypeProjection(this.context.getIrBuiltIns().getAnyNType(), Variance.OUT_VARIANCE)), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
            this.kPropertyStarType = irSimpleTypeImpl;
            this.kPropertiesFieldType = IrTypesKt.createType(this.context.getIr().getSymbols().getArray(), false, CollectionsKt.listOf(IrSimpleTypeImplKt.makeTypeProjection(irSimpleTypeImpl, Variance.OUT_VARIANCE)));
            this.useOptimizedSuperClass = this.context.getConfig().getGenerateOptimizedCallableReferenceSuperClasses();
            this.usesPropertyParameterCache = new ConcurrentHashMap<>();
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: PropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$REFLECTED_PROPERTY_REFERENCE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class REFLECTED_PROPERTY_REFERENCE extends IrStatementOriginImpl {
        public static final REFLECTED_PROPERTY_REFERENCE INSTANCE = new REFLECTED_PROPERTY_REFERENCE();

        private REFLECTED_PROPERTY_REFERENCE() {
            super("REFLECTED_PROPERTY_REFERENCE");
        }
    }

    private final IrSimpleFunctionSymbol getGetter(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrSimpleFunctionSymbol getter;
        IrPropertyReference irPropertyReference = irMemberAccessExpression instanceof IrPropertyReference ? (IrPropertyReference) irMemberAccessExpression : null;
        if (irPropertyReference != null && (getter = irPropertyReference.getGetter()) != null) {
            return getter;
        }
        IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference = irMemberAccessExpression instanceof IrLocalDelegatedPropertyReference ? (IrLocalDelegatedPropertyReference) irMemberAccessExpression : null;
        if (irLocalDelegatedPropertyReference != null) {
            return irLocalDelegatedPropertyReference.getGetter();
        }
        return null;
    }

    private final IrSimpleFunctionSymbol getSetter(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrSimpleFunctionSymbol setter;
        IrPropertyReference irPropertyReference = irMemberAccessExpression instanceof IrPropertyReference ? (IrPropertyReference) irMemberAccessExpression : null;
        if (irPropertyReference != null && (setter = irPropertyReference.getSetter()) != null) {
            return setter;
        }
        IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference = irMemberAccessExpression instanceof IrLocalDelegatedPropertyReference ? (IrLocalDelegatedPropertyReference) irMemberAccessExpression : null;
        if (irLocalDelegatedPropertyReference != null) {
            return irLocalDelegatedPropertyReference.getSetter();
        }
        return null;
    }

    private final IrFieldSymbol getField(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrPropertyReference irPropertyReference = irMemberAccessExpression instanceof IrPropertyReference ? (IrPropertyReference) irMemberAccessExpression : null;
        if (irPropertyReference != null) {
            return irPropertyReference.getField();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression getConstInitializer(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrField backingField;
        IrExpressionBody initializer;
        IrExpression expression;
        if (!(irMemberAccessExpression instanceof IrPropertyReference)) {
            return null;
        }
        IrPropertyReference irPropertyReference = (IrPropertyReference) irMemberAccessExpression;
        if (irPropertyReference.getField() == null) {
            IrProperty owner = irPropertyReference.getSymbol().getOwner();
            if (!owner.getIsConst()) {
                owner = null;
            }
            IrProperty irProperty = owner;
            backingField = irProperty != null ? irProperty.getBackingField() : null;
        } else {
            IrFieldSymbol field = irPropertyReference.getField();
            Intrinsics.checkNotNull(field);
            IrField owner2 = field.getOwner();
            IrField irField = owner2;
            if (!irField.getIsFinal() || !irField.getIsStatic()) {
                owner2 = null;
            }
            backingField = owner2;
        }
        if (backingField == null || (initializer = backingField.getInitializer()) == null || (expression = initializer.getExpression()) == null) {
            return null;
        }
        return IrUtilsKt.shallowCopyOrNull(expression);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isSynthetic(IrClass irClass) {
        return ((irClass.getMetadata() instanceof MetadataSource.File) || (irClass.getMetadata() instanceof MetadataSource.Class) || (irClass.getMetadata() instanceof MetadataSource.Script)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrDeclarationParent getPropertyContainer(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrField owner;
        IrSimpleFunction owner2;
        IrDeclarationParent parent;
        if (irMemberAccessExpression instanceof IrLocalDelegatedPropertyReference) {
            return findClassOwner((IrLocalDelegatedPropertyReference) irMemberAccessExpression);
        }
        IrSimpleFunctionSymbol getter = getGetter(irMemberAccessExpression);
        if (getter != null && (owner2 = getter.getOwner()) != null && (parent = owner2.getParent()) != null) {
            return parent;
        }
        IrFieldSymbol field = getField(irMemberAccessExpression);
        if (field == null || (owner = field.getOwner()) == null) {
            throw new IllegalStateException(("Property without getter or field: " + DumpIrTreeKt.dump$default(irMemberAccessExpression, null, 1, null)).toString());
        }
        return owner.getParent();
    }

    private final String getFakeGetterSignature(IrField irField) {
        StringBuilder sb = new StringBuilder();
        String strAsString = irField.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return sb.append(JvmAbi.getterName(strAsString)).append("()").append(MethodSignatureMapper.mapReturnType$default(this.context.getDefaultMethodSignatureMapper(), irField, null, false, 6, null)).toString();
    }

    private final Sequence<IrDeclaration> getParentsWithSelf(IrDeclaration irDeclaration) {
        return SequencesKt.generateSequence(irDeclaration, new Function1<IrDeclaration, IrDeclaration>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            public final IrDeclaration invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclarationParent parent = it.getParent();
                if (parent instanceof IrDeclaration) {
                    return (IrDeclaration) parent;
                }
                return null;
            }
        });
    }

    private final IrClass findClassOwner(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
        IrAttributeContainer originalBeforeInline = irLocalDelegatedPropertyReference.getOriginalBeforeInline();
        Object obj = null;
        if (originalBeforeInline != null) {
            if (!(originalBeforeInline instanceof IrLocalDelegatedPropertyReference)) {
                throw new IllegalArgumentException(("Original for local delegated property " + RenderIrElementKt.render$default(irLocalDelegatedPropertyReference, (DumpIrTreeOptions) null, 1, (Object) null) + " has another type: " + RenderIrElementKt.render$default(originalBeforeInline, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return findClassOwner((IrLocalDelegatedPropertyReference) originalBeforeInline);
        }
        Sequence sequenceFilter = SequencesKt.filter(getParentsWithSelf(irLocalDelegatedPropertyReference.getSymbol().getOwner()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$findClassOwner$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj2) {
                return Boolean.valueOf(obj2 instanceof IrClass);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        for (Object obj2 : sequenceFilter) {
            if (!isSynthetic((IrClass) obj2)) {
                obj = obj2;
                break;
            }
        }
        IrClass irClass = (IrClass) obj;
        return irClass == null ? (IrClass) SequencesKt.first(sequenceFilter) : irClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression computeSignatureString(IrBuilderWithScope irBuilderWithScope, IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrProperty owner;
        Integer numLocalPropertyIndex;
        InlineClassAbi.UNMANGLED_FUNCTION_REFERENCE unmangled_function_reference = null;
        if (irMemberAccessExpression instanceof IrLocalDelegatedPropertyReference) {
            ClassData classData = this.currentClassData;
            if (classData == null || (numLocalPropertyIndex = classData.localPropertyIndex(((IrLocalDelegatedPropertyReference) irMemberAccessExpression).getGetter())) == null) {
                throw new AssertionError("local property reference before declaration: " + RenderIrElementKt.render$default(irMemberAccessExpression, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            return ExpressionHelpersKt.irString(irBuilderWithScope, "<v#" + numLocalPropertyIndex.intValue() + '>');
        }
        IrSimpleFunctionSymbol getter = getGetter(irMemberAccessExpression);
        if (getter == null) {
            IrFieldSymbol field = getField(irMemberAccessExpression);
            Intrinsics.checkNotNull(field);
            return ExpressionHelpersKt.irString(irBuilderWithScope, getFakeGetterSignature(field.getOwner()));
        }
        IrPropertySymbol correspondingPropertySymbol = getter.getOwner().getCorrespondingPropertySymbol();
        if ((correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && !JvmIrUtilsKt.needsAccessor(owner, getter.getOwner())) || (InlineClassAbiKt.isInlineClassFieldGetter(getter.getOwner()) && Intrinsics.areEqual(getter.getOwner().getVisibility(), DescriptorVisibilities.INTERNAL))) {
            unmangled_function_reference = InlineClassAbi.UNMANGLED_FUNCTION_REFERENCE.INSTANCE;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = getter;
        IrFunctionReferenceImpl irFunctionReferenceImplFromSymbolOwner = IrFunctionReferenceImpl.INSTANCE.fromSymbolOwner(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irMemberAccessExpression.getType(), irSimpleFunctionSymbol, getter.getOwner().getTypeParameters().size(), irSimpleFunctionSymbol, unmangled_function_reference);
        Iterator<IrTypeParameter> it = getter.getOwner().getTypeParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            irFunctionReferenceImplFromSymbolOwner.putTypeArgument(i, IrUtilsKt.getDefaultType(JvmIrTypeUtilsKt.getErasedUpperBound(it.next())));
            i++;
        }
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.signatureStringIntrinsic);
        irCall.putValueArgument(0, irFunctionReferenceImplFromSymbolOwner);
        return irCall;
    }

    /* JADX INFO: compiled from: PropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$PropertyReferenceKind;", "", "interfaceSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "implSymbol", "wrapper", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "getImplSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getInterfaceSymbol", "getWrapper", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PropertyReferenceKind {
        private final IrClassSymbol implSymbol;
        private final IrClassSymbol interfaceSymbol;
        private final IrFunction wrapper;

        public PropertyReferenceKind(IrClassSymbol interfaceSymbol, IrClassSymbol implSymbol, IrFunction wrapper) {
            Intrinsics.checkNotNullParameter(interfaceSymbol, "interfaceSymbol");
            Intrinsics.checkNotNullParameter(implSymbol, "implSymbol");
            Intrinsics.checkNotNullParameter(wrapper, "wrapper");
            this.interfaceSymbol = interfaceSymbol;
            this.implSymbol = implSymbol;
            this.wrapper = wrapper;
        }

        public final IrClassSymbol getInterfaceSymbol() {
            return this.interfaceSymbol;
        }

        public final IrClassSymbol getImplSymbol() {
            return this.implSymbol;
        }

        public final IrFunction getWrapper() {
            return this.wrapper;
        }
    }

    private final PropertyReferenceKind propertyReferenceKind(IrCallableReference<?> expression, boolean mutable, int i) {
        IrSimpleFunction irSimpleFunction = null;
        if (i < 0 || i >= 3) {
            throw new IllegalStateException(("Incorrect number of receivers (" + i + ") for property reference: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        JvmSymbols symbols = this.context.getIr().getSymbols();
        boolean z = false;
        IrClassSymbol propertyReferenceClass = symbols.getPropertyReferenceClass(mutable, i, false);
        IrClassSymbol propertyReferenceClass2 = symbols.getPropertyReferenceClass(mutable, i, true);
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(symbols.getReflection().getOwner())) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), (mutable ? new StringBuilder("mutableProperty") : new StringBuilder("property")).append(i).toString())) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            return new PropertyReferenceKind(propertyReferenceClass, propertyReferenceClass2, irSimpleFunction);
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final PropertyReferenceKind propertyReferenceKindFor(IrCallableReference<?> expression) {
        IrField owner;
        IrSimpleFunction owner2;
        IrCallableReference<?> irCallableReference = expression;
        IrSimpleFunctionSymbol getter = getGetter(irCallableReference);
        int i = 0;
        if (getter != null && (owner2 = getter.getOwner()) != null) {
            int size = CollectionsKt.listOfNotNull((Object[]) new IrExpression[]{expression.getDispatchReceiver(), expression.getExtensionReceiver()}).size();
            int size2 = CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{owner2.getDispatchReceiverParameter(), owner2.getExtensionReceiverParameter()}).size();
            if (size >= 2 || (expression.getExtensionReceiver() != null && size2 >= 2)) {
                throw new IllegalStateException(("Property reference with two receivers is not supported: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            PropertyReferenceKind propertyReferenceKind = propertyReferenceKind(expression, getSetter(irCallableReference) != null, size2 - size);
            if (propertyReferenceKind != null) {
                return propertyReferenceKind;
            }
        }
        IrFieldSymbol field = getField(irCallableReference);
        if (field != null && (owner = field.getOwner()) != null) {
            boolean z = !owner.getIsFinal();
            if (!owner.getIsStatic() && expression.getDispatchReceiver() == null) {
                i = 1;
            }
            return propertyReferenceKind(expression, z, i);
        }
        throw new AssertionError("property has no getter and no field: " + DumpIrTreeKt.dump$default(expression, null, 1, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$PropertyInstance;", "", "initializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "index", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;I)V", "getIndex", "()I", "getInitializer", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class PropertyInstance {
        private final int index;
        private final IrExpression initializer;

        public static /* synthetic */ PropertyInstance copy$default(PropertyInstance propertyInstance, IrExpression irExpression, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                irExpression = propertyInstance.initializer;
            }
            if ((i2 & 2) != 0) {
                i = propertyInstance.index;
            }
            return propertyInstance.copy(irExpression, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrExpression getInitializer() {
            return this.initializer;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getIndex() {
            return this.index;
        }

        public final PropertyInstance copy(IrExpression initializer, int index) {
            Intrinsics.checkNotNullParameter(initializer, "initializer");
            return new PropertyInstance(initializer, index);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PropertyInstance)) {
                return false;
            }
            PropertyInstance propertyInstance = (PropertyInstance) other;
            return Intrinsics.areEqual(this.initializer, propertyInstance.initializer) && this.index == propertyInstance.index;
        }

        public int hashCode() {
            return (this.initializer.hashCode() * 31) + Integer.hashCode(this.index);
        }

        public String toString() {
            return "PropertyInstance(initializer=" + this.initializer + ", index=" + this.index + ')';
        }

        public PropertyInstance(IrExpression initializer, int i) {
            Intrinsics.checkNotNullParameter(initializer, "initializer");
            this.initializer = initializer;
            this.index = i;
        }

        public final int getIndex() {
            return this.index;
        }

        public final IrExpression getInitializer() {
            return this.initializer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0018\u00010\u0000R\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001e\u001a\u00020\u000b¢\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00190\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0018\u00010\u0000R\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$ClassData;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parent", "Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering;", "(Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$ClassData;)V", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "kProperties", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$PropertyInstance;", "getKProperties", "()Ljava/util/Map;", "kPropertiesField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getKPropertiesField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "localProperties", "", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getLocalProperties", "()Ljava/util/List;", "localPropertyIndices", "", "getLocalPropertyIndices", "getParent", "()Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceLowering$ClassData;", "localPropertyIndex", "getter", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Ljava/lang/Integer;", "rememberLocalProperty", "", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ClassData {
        private final IrClass irClass;
        private final Map<IrSymbol, PropertyInstance> kProperties;
        private final IrField kPropertiesField;
        private final List<IrLocalDelegatedPropertySymbol> localProperties;
        private final Map<IrSymbol, Integer> localPropertyIndices;
        private final ClassData parent;
        final /* synthetic */ PropertyReferenceLowering this$0;

        public ClassData(PropertyReferenceLowering propertyReferenceLowering, IrClass irClass, ClassData classData) {
            DescriptorVisibility descriptorVisibility;
            String str;
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            this.this$0 = propertyReferenceLowering;
            this.irClass = irClass;
            this.parent = classData;
            this.kProperties = new LinkedHashMap();
            IrFactory irFactory = propertyReferenceLowering.getContext().getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            Name nameIdentifier = Name.identifier(JvmAbi.DELEGATED_PROPERTIES_ARRAY_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(propertyReferenceLowering.kPropertiesFieldType);
            irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            if (IrUtilsKt.isInterface(irClass) && propertyReferenceLowering.getContext().getConfig().getJvmDefaultMode().getForAllMethodsWithBody()) {
                descriptorVisibility = DescriptorVisibilities.PUBLIC;
                str = "PUBLIC";
            } else {
                descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
                str = "PACKAGE_VISIBILITY";
            }
            Intrinsics.checkNotNullExpressionValue(descriptorVisibility, str);
            irFieldBuilder.setVisibility(descriptorVisibility);
            this.kPropertiesField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            this.localProperties = new ArrayList();
            this.localPropertyIndices = new LinkedHashMap();
        }

        public final IrClass getIrClass() {
            return this.irClass;
        }

        public final ClassData getParent() {
            return this.parent;
        }

        public final Map<IrSymbol, PropertyInstance> getKProperties() {
            return this.kProperties;
        }

        public final IrField getKPropertiesField() {
            return this.kPropertiesField;
        }

        public final List<IrLocalDelegatedPropertySymbol> getLocalProperties() {
            return this.localProperties;
        }

        public final Map<IrSymbol, Integer> getLocalPropertyIndices() {
            return this.localPropertyIndices;
        }

        public final Integer localPropertyIndex(IrSymbol getter) {
            Intrinsics.checkNotNullParameter(getter, "getter");
            Integer num = this.localPropertyIndices.get(getter);
            if (num != null) {
                return num;
            }
            ClassData classData = this.parent;
            if (classData != null) {
                return classData.localPropertyIndex(getter);
            }
            return null;
        }

        public final void rememberLocalProperty(IrLocalDelegatedProperty property) {
            Object next;
            Intrinsics.checkNotNullParameter(property, "property");
            Sequence sequenceGenerateSequence = SequencesKt.generateSequence(this, new Function1<ClassData, ClassData>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$ClassData$rememberLocalProperty$metadataOwner$1
                @Override // kotlin.jvm.functions.Function1
                public final PropertyReferenceLowering.ClassData invoke(PropertyReferenceLowering.ClassData it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getParent();
                }
            });
            PropertyReferenceLowering propertyReferenceLowering = this.this$0;
            Iterator it = sequenceGenerateSequence.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (propertyReferenceLowering.isSynthetic(((ClassData) next).irClass));
            ClassData classData = (ClassData) next;
            if (classData == null) {
                classData = this;
            }
            classData.localPropertyIndices.put(property.getGetter().getSymbol(), Integer.valueOf(classData.localProperties.size()));
            classData.localProperties.add(property.getSymbol());
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ClassData classData = new ClassData(this, declaration, this.currentClassData);
        this.currentClassData = classData;
        transformChildrenVoid(declaration);
        this.currentClassData = classData.getParent();
        if (!classData.getKProperties().isEmpty()) {
            List<IrDeclaration> declarations = declaration.getDeclarations();
            IrField kPropertiesField = classData.getKPropertiesField();
            kPropertiesField.setParent(declaration);
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, classData.getKPropertiesField().getSymbol(), 0, 0, 6, null);
            List listSortedWith = CollectionsKt.sortedWith(classData.getKProperties().values(), new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$visitClassNew$lambda$21$lambda$20$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Integer.valueOf(((PropertyReferenceLowering.PropertyInstance) t).getIndex()), Integer.valueOf(((PropertyReferenceLowering.PropertyInstance) t2).getIndex()));
                }
            });
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
            Iterator it = listSortedWith.iterator();
            while (it.hasNext()) {
                arrayList.add(((PropertyInstance) it.next()).getInitializer());
            }
            kPropertiesField.setInitializer(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, IrArrayBuilderKt.irArrayOf(jvmIrBuilderCreateJvmIrBuilder$default, this.kPropertiesFieldType, arrayList)));
            Unit unit = Unit.INSTANCE;
            declarations.add(0, kPropertiesField);
        }
        if (!classData.getLocalProperties().isEmpty()) {
            this.context.getLocalDelegatedProperties().put(declaration.getAttributeOwnerId(), classData.getLocalProperties());
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ClassData classData = this.currentClassData;
        Intrinsics.checkNotNull(classData);
        classData.rememberLocalProperty(declaration);
        return super.visitLocalDelegatedProperty(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return cachedKProperty(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return cachedKProperty(expression);
    }

    private final boolean usesParameter(final IrSimpleFunction irSimpleFunction, final int i) {
        ClassId parentClassId = JvmIrUtilsKt.getParentClassId(irSimpleFunction);
        if (parentClassId != null) {
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            IrSimpleFunction replacementFunction = this.context.getInlineClassReplacements().getReplacementFunction(irSimpleFunction2);
            if (replacementFunction == null) {
                replacementFunction = irSimpleFunction;
            }
            JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = this.context.getDefaultMethodSignatureMapper().mapSignatureSkipGeneric(replacementFunction);
            List<JvmMethodParameterSignature> valueParameters = jvmMethodSignatureMapSignatureSkipGeneric.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            Iterator it = CollectionsKt.take(valueParameters, i + (replacementFunction.getExtensionReceiverParameter() != null ? 1 : 0)).iterator();
            int size = 0;
            while (it.hasNext()) {
                size += ((JvmMethodParameterSignature) it.next()).getAsmType().getSize();
            }
            int i2 = replacementFunction.getDispatchReceiverParameter() == null ? 0 : 1;
            Method asmMethod = jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod();
            Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
            return RedundantNullCheckMethodTransformerKt.usesLocalExceptParameterNullCheck(SourceCompilerForInlineKt.loadCompiledInlineFunction(parentClassId, asmMethod, irSimpleFunction.getIsSuspend(), InlineClassAbiKt.getHasMangledReturnType(irSimpleFunction2), this.context.getState()).getNode(), size + i2);
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        irSimpleFunction.acceptChildren(new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$usesParameter$$inlined$hasChild$1
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
            public void visitCall(IrCall irCall) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
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
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass irClass) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
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
            public void visitFunction(IrFunction irFunction) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
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
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
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
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction3, Object obj) {
                visitSimpleFunction(irSimpleFunction3, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction3) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction3);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction3, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction3, r2);
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
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (booleanRef.element) {
                    return;
                }
                if ((element instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) element).getSymbol(), irSimpleFunction.getValueParameters().get(i).getSymbol())) {
                    booleanRef.element = true;
                } else {
                    element.acceptChildren(this, null);
                }
            }
        }, null);
        return booleanRef.element;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!expression.getSymbol().getOwner().getIsInline()) {
            return super.visitCall(expression);
        }
        int size = expression.getSymbol().getOwner().getValueParameters().size();
        for (int i = 0; i < size; i++) {
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument instanceof IrCallableReference) {
                IrCallableReference irCallableReference = (IrCallableReference) valueArgument;
                if (Intrinsics.areEqual(irCallableReference.getOrigin(), IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE.INSTANCE)) {
                    IrSimpleFunction owner = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(expression.getSymbol().getOwner(), false, null, 3, null);
                    if (owner == null) {
                        owner = expression.getSymbol().getOwner();
                    }
                    ConcurrentHashMap<IrSymbol, Boolean> concurrentHashMap = this.usesPropertyParameterCache;
                    IrSimpleFunctionSymbol symbol = owner.getSymbol();
                    Boolean bool = concurrentHashMap.get(symbol);
                    if (bool == null) {
                        Boolean boolValueOf = Boolean.valueOf(usesParameter(owner, i));
                        Boolean boolPutIfAbsent = concurrentHashMap.putIfAbsent(symbol, boolValueOf);
                        bool = boolPutIfAbsent == null ? boolValueOf : boolPutIfAbsent;
                    }
                    if (!bool.booleanValue()) {
                        expression.putValueArgument(i, IrConstImpl.INSTANCE.constNull(irCallableReference.getStartOffset(), irCallableReference.getEndOffset(), irCallableReference.getType()));
                    }
                }
            }
        }
        return super.visitCall(expression);
    }

    private final IrExpression cachedKProperty(IrCallableReference<?> expression) {
        IrCallableReference<?> irCallableReference = expression;
        transformChildrenVoid(irCallableReference);
        if (Intrinsics.areEqual(expression.getOrigin(), REFLECTED_PROPERTY_REFERENCE.INSTANCE)) {
            return createReflectedKProperty(expression);
        }
        if (!Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.PROPERTY_REFERENCE_FOR_DELEGATE.INSTANCE)) {
            return createSpecializedKProperty(expression);
        }
        ClassData classData = this.currentClassData;
        if (classData == null) {
            throw new AssertionError("property reference not in class: " + RenderIrElementKt.render$default(irCallableReference, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(jvmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), expression.getStartOffset(), expression.getEndOffset());
        Map<IrSymbol, PropertyInstance> kProperties = classData.getKProperties();
        IrSymbol symbol = expression.getSymbol();
        PropertyInstance propertyInstance = kProperties.get(symbol);
        if (propertyInstance == null) {
            propertyInstance = new PropertyInstance(createReflectedKProperty(expression), classData.getKProperties().size());
            kProperties.put(symbol, propertyInstance);
        }
        int index = propertyInstance.getIndex();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, this.arrayItemGetter);
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGetField$default(declarationIrBuilder, null, classData.getKPropertiesField(), null, 4, null));
        irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(declarationIrBuilder, index, null, 2, null));
        return irFunctionAccessExpressionIrCall;
    }

    private final IrExpression createReflectedKProperty(IrCallableReference<?> expression) {
        int i;
        IrExpression boundReceiver = getBoundReceiver(expression);
        if (boundReceiver != null && !this.useOptimizedSuperClass) {
            return createSpecializedKProperty(expression);
        }
        PropertyReferenceKind propertyReferenceKindPropertyReferenceKindFor = propertyReferenceKindFor(expression);
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope, expression);
        if (boundReceiver != null) {
            i = 5;
        } else {
            i = this.useOptimizedSuperClass ? 4 : 3;
        }
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrConstructorSymbol irConstructorSymbol = null;
        boolean z = false;
        for (IrConstructorSymbol irConstructorSymbol2 : IrUtilsKt.getConstructors(propertyReferenceKindPropertyReferenceKindFor.getImplSymbol())) {
            if (irConstructorSymbol2.getOwner().getValueParameters().size() == i) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irConstructorSymbol = irConstructorSymbol2;
            }
        }
        if (z) {
            IrConstructorCall irConstructorCallIrCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, irConstructorSymbol);
            fillReflectedPropertyArguments(jvmIrBuilderCreateJvmIrBuilder, irConstructorCallIrCall, expression, boundReceiver);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, propertyReferenceKindPropertyReferenceKindFor.getWrapper());
            irFunctionAccessExpressionIrCall.putValueArgument(0, irConstructorCallIrCall);
            return irFunctionAccessExpressionIrCall;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final void fillReflectedPropertyArguments(JvmIrBuilder jvmIrBuilder, IrFunctionAccessExpression irFunctionAccessExpression, IrCallableReference<?> irCallableReference, IrExpression irExpression) {
        int i;
        IrCallableReference<?> irCallableReference2 = irCallableReference;
        IrDeclarationParent propertyContainer = getPropertyContainer(irCallableReference2);
        IrCall irCallKClassToJavaClass = this.useOptimizedSuperClass ? JvmIrUtilsKt.kClassToJavaClass(jvmIrBuilder, FunctionReferenceLowering.INSTANCE.calculateOwnerKClass$backend_jvm_lower(jvmIrBuilder, propertyContainer)) : FunctionReferenceLowering.INSTANCE.calculateOwner$backend_jvm_lower(jvmIrBuilder, propertyContainer);
        int i2 = 1;
        if (irExpression != null) {
            irFunctionAccessExpression.putValueArgument(0, irExpression);
            i = 1;
        } else {
            i = 0;
        }
        irFunctionAccessExpression.putValueArgument(i, irCallKClassToJavaClass);
        int i3 = i + 2;
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
        IrSymbolOwner owner = irCallableReference.getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        String strAsString = ((IrDeclarationWithName) owner).getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        irFunctionAccessExpression.putValueArgument(i + 1, ExpressionHelpersKt.irString(jvmIrBuilder2, strAsString));
        int i4 = i + 3;
        irFunctionAccessExpression.putValueArgument(i3, computeSignatureString(jvmIrBuilder2, irCallableReference2));
        if (this.useOptimizedSuperClass) {
            if ((!(propertyContainer instanceof IrClass) || !IrUtilsKt.isFileClass((IrDeclaration) propertyContainer)) && !(propertyContainer instanceof IrPackageFragment)) {
                i2 = 0;
            }
            irFunctionAccessExpression.putValueArgument(i4, ExpressionHelpersKt.irInt$default(jvmIrBuilder2, i2 | (isJavaSyntheticPropertyReference(irCallableReference) ? 2 : 0), null, 2, null));
        }
    }

    private final boolean isJavaSyntheticPropertyReference(IrCallableReference<?> irCallableReference) {
        IrSymbolOwner owner = irCallableReference.getSymbol().getOwner();
        if (owner instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) owner;
            if (irProperty.getBackingField() == null && (Intrinsics.areEqual(irProperty.getOrigin(), IrDeclarationOrigin.SYNTHETIC_JAVA_PROPERTY_DELEGATE.INSTANCE) || Intrinsics.areEqual(irProperty.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE))) {
                return true;
            }
        }
        return false;
    }

    private final IrExpression createSpecializedKProperty(IrCallableReference<?> expression) {
        IrClass irClassCreateKPropertySubclass = createKPropertySubclass(expression);
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(jvmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), expression.getStartOffset(), expression.getEndOffset());
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
        irBlockBuilder.unaryPlus(irClassCreateKPropertySubclass);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBuilder, (IrFunction) SequencesKt.single(IrUtilsKt.getConstructors(irClassCreateKPropertySubclass)));
        IrExpression boundReceiver = getBoundReceiver(expression);
        if (boundReceiver != null) {
            irFunctionAccessExpressionIrCall.putValueArgument(0, boundReceiver);
        }
        irBlockBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrClass createKPropertySubclass(final IrCallableReference<?> expression) {
        IrSimpleFunction next;
        IrSimpleFunction next2;
        IrSimpleFunction next3;
        final IrSimpleFunction owner;
        final IrSimpleFunction owner2;
        PropertyReferenceKind propertyReferenceKindPropertyReferenceKindFor = propertyReferenceKindFor(expression);
        IrClass owner3 = (this.useOptimizedSuperClass ? propertyReferenceKindPropertyReferenceKindFor.getImplSymbol() : propertyReferenceKindPropertyReferenceKindFor.getInterfaceSymbol()).getOwner();
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        IrElementBuilderKt.setSourceRange(irClassBuilder, expression);
        irClassBuilder.setName(SpecialNames.NO_NAME_PROVIDED);
        irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE);
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irClassBuilder.setVisibility(LOCAL);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        IrDeclarationParent currentDeclarationParent = getCurrentDeclarationParent();
        Intrinsics.checkNotNull(currentDeclarationParent);
        irClassBuildClass.setParent(currentDeclarationParent);
        irClassBuildClass.setSuperTypes(CollectionsKt.listOf(IrUtilsKt.getDefaultType(owner3)));
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        final IrClass irClass = (IrClass) IrDeclarationsKt.copyAttributes(irClassBuildClass, expression);
        addConstructor(expression, irClass, owner3);
        if (!this.useOptimizedSuperClass) {
            boolean z = false;
            IrSimpleFunction irSimpleFunction = null;
            boolean z2 = false;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(owner3)) {
                if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), "getName")) {
                    if (z2) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction = irSimpleFunction2;
                    z2 = true;
                }
            }
            if (z2) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                IrSimpleFunction irSimpleFunction4 = null;
                boolean z3 = false;
                for (IrSimpleFunction irSimpleFunction5 : IrUtilsKt.getFunctions(owner3)) {
                    if (Intrinsics.areEqual(irSimpleFunction5.getName().asString(), "getOwner")) {
                        if (z3) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z3 = true;
                        irSimpleFunction4 = irSimpleFunction5;
                    }
                }
                if (z3) {
                    IrSimpleFunction irSimpleFunction6 = irSimpleFunction4;
                    IrSimpleFunction irSimpleFunction7 = null;
                    for (IrSimpleFunction irSimpleFunction8 : IrUtilsKt.getFunctions(owner3)) {
                        if (Intrinsics.areEqual(irSimpleFunction8.getName().asString(), "getSignature")) {
                            if (z) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            z = true;
                            irSimpleFunction7 = irSimpleFunction8;
                        }
                    }
                    if (z) {
                        addOverride(irClass, irSimpleFunction3, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering.createKPropertySubclass.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> it) {
                                Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                                Intrinsics.checkNotNullParameter(it, "it");
                                IrSymbolOwner owner4 = expression.getSymbol().getOwner();
                                Intrinsics.checkNotNull(owner4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
                                String strAsString = ((IrDeclarationWithName) owner4).getName().asString();
                                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                                return ExpressionHelpersKt.irString(addOverride, strAsString);
                            }
                        });
                        addOverride(irClass, irSimpleFunction6, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering.createKPropertySubclass.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> it) {
                                Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                                Intrinsics.checkNotNullParameter(it, "it");
                                return FunctionReferenceLowering.INSTANCE.calculateOwner$backend_jvm_lower(addOverride, PropertyReferenceLowering.this.getPropertyContainer(expression));
                            }
                        });
                        addOverride(irClass, irSimpleFunction7, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering.createKPropertySubclass.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> it) {
                                Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                                Intrinsics.checkNotNullParameter(it, "it");
                                return PropertyReferenceLowering.this.computeSignatureString(addOverride, expression);
                            }
                        });
                    } else {
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                } else {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            } else {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
        }
        final IrExpression boundReceiver = getBoundReceiver(expression);
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(owner3).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(next.getName().asString(), "get"));
        IrSimpleFunction irSimpleFunction9 = next;
        Iterator<IrSimpleFunction> it2 = IrUtilsKt.getFunctions(owner3).iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
        } while (!Intrinsics.areEqual(next2.getName().asString(), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX));
        IrSimpleFunction irSimpleFunction10 = next2;
        Iterator<IrSimpleFunction> it3 = IrUtilsKt.getFunctions(owner3).iterator();
        do {
            if (!it3.hasNext()) {
                next3 = null;
                break;
            }
            next3 = it3.next();
        } while (!Intrinsics.areEqual(next3.getName().asString(), "invoke"));
        IrSimpleFunction irSimpleFunction11 = next3;
        IrCallableReference<?> irCallableReference = expression;
        IrFieldSymbol field = getField(irCallableReference);
        final IrField owner4 = field != null ? field.getOwner() : null;
        if (owner4 == null) {
            IrSimpleFunctionSymbol getter = getGetter(irCallableReference);
            if (getter != null && (owner2 = getter.getOwner()) != null) {
                Intrinsics.checkNotNull(irSimpleFunction9);
                addOverride(irClass, irSimpleFunction9, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$createKPropertySubclass$4$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> arguments) {
                        Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                        Intrinsics.checkNotNullParameter(arguments, "arguments");
                        IrExpression constInitializer = this.this$0.getConstInitializer(expression);
                        if (constInitializer != null) {
                            return constInitializer;
                        }
                        JvmIrBuilder jvmIrBuilder = addOverride;
                        IrCall irCallIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, owner2.getReturnType(), null, owner2.getSymbol());
                        PropertyReferenceLowering.createKPropertySubclass$setCallArguments(jvmIrBuilder, boundReceiver, this.this$0, expression, irClass, irCallIrGet, arguments);
                        return irCallIrGet;
                    }
                });
                Intrinsics.checkNotNull(irSimpleFunction11);
                addFakeOverride(irClass, irSimpleFunction11);
            }
            IrSimpleFunctionSymbol setter = getSetter(irCallableReference);
            if (setter != null && (owner = setter.getOwner()) != null) {
                Intrinsics.checkNotNull(irSimpleFunction10);
                addOverride(irClass, irSimpleFunction10, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering$createKPropertySubclass$5$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> arguments) {
                        Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                        Intrinsics.checkNotNullParameter(arguments, "arguments");
                        JvmIrBuilder jvmIrBuilder = addOverride;
                        IrCall irCallIrSet = ExpressionHelpersKt.irSet(jvmIrBuilder, owner.getReturnType(), null, owner.getSymbol(), ExpressionHelpersKt.irGet(jvmIrBuilder, (IrValueDeclaration) CollectionsKt.last((List) arguments)));
                        PropertyReferenceLowering.createKPropertySubclass$setCallArguments(jvmIrBuilder, boundReceiver, this, expression, irClass, irCallIrSet, arguments);
                        return irCallIrSet;
                    }
                });
            }
        } else {
            Intrinsics.checkNotNull(irSimpleFunction9);
            addOverride(irClass, irSimpleFunction9, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering.createKPropertySubclass.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> arguments) {
                    Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                    Intrinsics.checkNotNullParameter(arguments, "arguments");
                    IrExpression constInitializer = PropertyReferenceLowering.this.getConstInitializer(expression);
                    if (constInitializer != null) {
                        return constInitializer;
                    }
                    JvmIrBuilder jvmIrBuilder = addOverride;
                    return ExpressionHelpersKt.irGetField$default(jvmIrBuilder, PropertyReferenceLowering.createKPropertySubclass$fieldReceiver(jvmIrBuilder, owner4, expression, PropertyReferenceLowering.this, irClass, arguments), owner4, null, 4, null);
                }
            });
            if (!owner4.getIsFinal()) {
                Intrinsics.checkNotNull(irSimpleFunction10);
                addOverride(irClass, irSimpleFunction10, new Function2<JvmIrBuilder, List<? extends IrValueParameter>, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceLowering.createKPropertySubclass.7
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final IrExpression invoke(JvmIrBuilder addOverride, List<? extends IrValueParameter> arguments) {
                        Intrinsics.checkNotNullParameter(addOverride, "$this$addOverride");
                        Intrinsics.checkNotNullParameter(arguments, "arguments");
                        JvmIrBuilder jvmIrBuilder = addOverride;
                        return ExpressionHelpersKt.irSetField$default(jvmIrBuilder, PropertyReferenceLowering.createKPropertySubclass$fieldReceiver(jvmIrBuilder, owner4, expression, this, irClass, arguments), owner4, ExpressionHelpersKt.irGet(jvmIrBuilder, (IrValueDeclaration) CollectionsKt.last((List) arguments)), null, 8, null);
                    }
                });
            }
        }
        return irClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createKPropertySubclass$setCallArguments(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, PropertyReferenceLowering propertyReferenceLowering, IrCallableReference<?> irCallableReference, IrClass irClass, IrCall irCall, List<? extends IrValueParameter> list) {
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrImplicitCast;
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrImplicitCast2;
        IrTypeOperatorCallImpl irTypeOperatorCallImpl = null;
        IrTypeOperatorCallImpl irTypeOperatorCallImplIrImplicitCast3 = irExpression != null ? ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGetField$default(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(0)), FunctionReferenceLowering.INSTANCE.getReceiverField$backend_jvm_lower(irClass, propertyReferenceLowering.context), null, 4, null), irExpression.getType()) : null;
        if (propertyReferenceLowering.isJavaSyntheticPropertyReference(irCallableReference)) {
            irCall.getTypeArgumentsCount();
        } else {
            IrExpressionsKt.copyTypeArgumentsFrom$default(irCall, irCallableReference, 0, 2, null);
        }
        if (irCall.getSymbol().getOwner().getDispatchReceiverParameter() != null) {
            irTypeOperatorCallImplIrImplicitCast = irTypeOperatorCallImplIrImplicitCast3 == null ? ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(1)), propertyReferenceLowering.getReceiverType(irCallableReference)) : irTypeOperatorCallImplIrImplicitCast3;
        } else {
            irTypeOperatorCallImplIrImplicitCast = null;
        }
        irCall.setDispatchReceiver(irTypeOperatorCallImplIrImplicitCast);
        IrValueParameter extensionReceiverParameter = irCall.getSymbol().getOwner().getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            if (irCall.getSymbol().getOwner().getDispatchReceiverParameter() != null) {
                irTypeOperatorCallImplIrImplicitCast2 = ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(irTypeOperatorCallImplIrImplicitCast3 != null ? 1 : 2)), extensionReceiverParameter.getType());
            } else if (irTypeOperatorCallImplIrImplicitCast3 == null) {
                irTypeOperatorCallImplIrImplicitCast2 = ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(1)), extensionReceiverParameter.getType());
            } else {
                irTypeOperatorCallImpl = irTypeOperatorCallImplIrImplicitCast3;
            }
            irTypeOperatorCallImpl = irTypeOperatorCallImplIrImplicitCast2;
        }
        irCall.setExtensionReceiver(irTypeOperatorCallImpl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrTypeOperatorCallImpl createKPropertySubclass$fieldReceiver(IrBuilderWithScope irBuilderWithScope, IrField irField, IrCallableReference<?> irCallableReference, PropertyReferenceLowering propertyReferenceLowering, IrClass irClass, List<? extends IrValueParameter> list) {
        if (irField.getIsStatic()) {
            return null;
        }
        if (irCallableReference.getDispatchReceiver() != null) {
            return ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGetField$default(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(0)), FunctionReferenceLowering.INSTANCE.getReceiverField$backend_jvm_lower(irClass, propertyReferenceLowering.context), null, 4, null), propertyReferenceLowering.getReceiverType(irCallableReference));
        }
        return ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, list.get(1)), propertyReferenceLowering.getReceiverType(irCallableReference));
    }

    private final void addConstructor(IrCallableReference<?> expression, IrClass referenceClass, IrClass superClass) {
        boolean z = false;
        int i = getBoundReceiver(expression) != null ? 1 : 0;
        int i2 = (this.useOptimizedSuperClass ? 4 : 0) + i;
        IrConstructor irConstructor = null;
        for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(superClass)) {
            if (irConstructor2.getValueParameters().size() == i2) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irConstructor = irConstructor2;
            }
        }
        if (z) {
            IrConstructor irConstructor3 = irConstructor;
            if (!this.useOptimizedSuperClass) {
                IrUtilsKt.addSimpleDelegatingConstructor$default(referenceClass, irConstructor3, this.context.getIrBuiltIns(), true, null, 8, null);
                return;
            }
            IrFactory factory = referenceClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(referenceClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            referenceClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(referenceClass);
            IrValueParameter irValueParameterAddValueParameter$default = i != 0 ? DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "receiver", this.context.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null) : null;
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, null);
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, irConstructor3);
            fillReflectedPropertyArguments(jvmIrBuilderCreateJvmIrBuilder$default, irDelegatingConstructorCall, expression, irValueParameterAddValueParameter$default != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterAddValueParameter$default) : null);
            irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
            irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), referenceClass.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrType getReceiverType(IrCallableReference<?> irCallableReference) {
        IrType type;
        IrExpression dispatchReceiver = irCallableReference.getDispatchReceiver();
        if (dispatchReceiver != null && (type = dispatchReceiver.getType()) != null) {
            return type;
        }
        IrType type2 = irCallableReference.getType();
        Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        Object objFirst = CollectionsKt.first((List<? extends Object>) ((IrSimpleType) type2).getArguments());
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
        return ((IrTypeProjection) objFirst).getType();
    }

    private final IrExpression getBoundReceiver(IrCallableReference<?> irCallableReference) {
        IrSymbolOwner owner = irCallableReference.getSymbol().getOwner();
        if (owner instanceof IrDeclaration) {
            IrDeclaration irDeclaration = (IrDeclaration) owner;
            if (JvmStaticAnnotationLoweringKt.isJvmStaticInObject(irDeclaration)) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(irDeclaration);
                return new IrGetObjectValueImpl(-1, -1, IrTypesKt.typeWith(parentAsClass, new IrType[0]), parentAsClass.getSymbol());
            }
        }
        IrExpression dispatchReceiver = irCallableReference.getDispatchReceiver();
        return dispatchReceiver == null ? irCallableReference.getExtensionReceiver() : dispatchReceiver;
    }

    private final IrSimpleFunction addOverride(IrClass irClass, IrSimpleFunction irSimpleFunction, Function2<? super JvmIrBuilder, ? super List<? extends IrValueParameter>, ? extends IrExpression> function2) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrElementBuilderKt.setSourceRange(irFunctionBuilder, irClass);
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
        irFunctionBuilder.setVisibility(irSimpleFunction.getVisibility());
        irFunctionBuilder.setModality(Modality.OPEN);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionBuildFunction.getOverriddenSymbols(), irSimpleFunction.getSymbol()));
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = arrayList;
            arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
            arrayList = arrayList2;
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.context, irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder, function2.invoke(jvmIrBuilderCreateJvmIrBuilder, CollectionsKt.plus((Collection) CollectionsKt.listOf(dispatchReceiverParameter), (Iterable) irSimpleFunctionBuildFunction.getValueParameters()))));
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction addFakeOverride(IrClass irClass, IrSimpleFunction irSimpleFunction) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
        irFunctionBuilder.setVisibility(irSimpleFunction.getVisibility());
        irFunctionBuilder.setFakeOverride(true);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionBuildFunction.getOverriddenSymbols(), irSimpleFunction.getSymbol()));
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = arrayList;
            arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) it.next(), irSimpleFunction2, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
            arrayList = arrayList2;
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        return irSimpleFunctionBuildFunction;
    }
}
