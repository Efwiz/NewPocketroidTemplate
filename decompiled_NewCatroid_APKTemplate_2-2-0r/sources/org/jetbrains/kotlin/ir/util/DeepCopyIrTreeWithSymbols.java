package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrScriptImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCatchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantArrayImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantObjectImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstantPrimitiveImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorCallExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetClassImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInlinedFunctionBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrLocalDelegatedPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnableBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSyntheticBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrThrowImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¸\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\fH\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0004J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0017\u001a\u0004\u0018\u00010\u0019H\u0004J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010#\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0016J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*H\u0016J\u0010\u0010,\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0016J\u0010\u00100\u001a\u0002012\u0006\u0010\u0012\u001a\u000201H\u0016J\u0010\u00102\u001a\u0002032\u0006\u0010\u001c\u001a\u000203H\u0016J\u0010\u00104\u001a\u0002052\u0006\u0010\u001c\u001a\u000205H\u0016J\u0018\u00106\u001a\u0006\u0012\u0002\b\u0003072\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u000307H\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010\u001c\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u0002092\u0006\u0010\u001c\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u0002092\u0006\u0010\u001c\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020@2\u0006\u0010\u0012\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020B2\u0006\u0010\u001c\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020D2\u0006\u0010+\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010\u0012\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020I2\u0006\u0010\u001c\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020KH\u0016J\u0010\u0010M\u001a\u00020N2\u0006\u0010\u001c\u001a\u00020NH\u0016J\u0010\u0010O\u001a\u00020P2\u0006\u0010\u001c\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020RH\u0016J\u0010\u0010T\u001a\u00020U2\u0006\u0010(\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020W2\u0006\u0010\u001c\u001a\u00020WH\u0016J\u0010\u0010X\u001a\u00020Y2\u0006\u0010\u0012\u001a\u00020YH\u0016J\u0010\u0010Z\u001a\u00020[2\u0006\u0010\u001c\u001a\u00020[H\u0016J\u0010\u0010\\\u001a\u00020]2\u0006\u0010\u0012\u001a\u00020]H\u0016J\u0010\u0010^\u001a\u00020_2\u0006\u0010\u001c\u001a\u00020_H\u0016J\u0010\u0010`\u001a\u00020a2\u0006\u0010\u001c\u001a\u00020aH\u0016J\u0010\u0010b\u001a\u00020c2\u0006\u0010#\u001a\u00020cH\u0016J\u0010\u0010d\u001a\u00020e2\u0006\u0010\u0012\u001a\u00020eH\u0016J\u0010\u0010f\u001a\u00020g2\u0006\u0010\u0012\u001a\u00020gH\u0016J\u0010\u0010h\u001a\u00020i2\u0006\u0010\u0012\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u00020k2\u0006\u0010\u001c\u001a\u00020kH\u0016J\u0010\u0010l\u001a\u00020m2\u0006\u0010\u001c\u001a\u00020mH\u0016J\u0010\u0010n\u001a\u00020o2\u0006\u0010\u001c\u001a\u00020oH\u0016J\u0010\u0010p\u001a\u00020q2\u0006\u0010\u001c\u001a\u00020qH\u0016J\u0010\u0010r\u001a\u00020s2\u0006\u0010\u001c\u001a\u00020sH\u0016J\u0010\u0010t\u001a\u00020u2\u0006\u0010\u001c\u001a\u00020uH\u0016J\u0010\u0010v\u001a\u00020w2\u0006\u0010\u001c\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u00020y2\u0006\u0010\u001c\u001a\u00020yH\u0016J\u0010\u0010z\u001a\u00020{2\u0006\u0010\u0012\u001a\u00020{H\u0016J\u0010\u0010|\u001a\u00020}2\u0006\u0010\u001c\u001a\u00020}H\u0016J\u0010\u0010~\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u0012\u0010\u007f\u001a\u00030\u0080\u00012\u0007\u0010\u0012\u001a\u00030\u0080\u0001H\u0016J\u0013\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u001c\u001a\u00030\u0082\u0001H\u0016J\u0013\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0007\u0010\u001c\u001a\u00030\u0084\u0001H\u0016J\u0013\u0010\u0085\u0001\u001a\u00030\u0086\u00012\u0007\u0010\u001c\u001a\u00030\u0086\u0001H\u0016J\u0012\u0010\u0087\u0001\u001a\u00020F2\u0007\u0010\u0012\u001a\u00030\u0088\u0001H\u0016J\u0013\u0010\u0089\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u001c\u001a\u00030\u008a\u0001H\u0016J\u0013\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0007\u0010\u001c\u001a\u00030\u008c\u0001H\u0016J\u0013\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0007\u0010\u0012\u001a\u00030\u008e\u0001H\u0016J\u0014\u0010\u008f\u0001\u001a\u00030\u0090\u00012\b\u0010\u0091\u0001\u001a\u00030\u0090\u0001H\u0016J\u0013\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u001c\u001a\u00030\u0093\u0001H\u0016J\u0013\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0007\u0010#\u001a\u00030\u0095\u0001H\u0016J\u0013\u0010\u0096\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u001c\u001a\u00030\u0097\u0001H\u0016J\u0014\u0010\u0098\u0001\u001a\u00030\u0099\u00012\b\u0010\u009a\u0001\u001a\u00030\u0099\u0001H\u0016J\u0013\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0007\u0010\u0012\u001a\u00030\u009c\u0001H\u0016J\u0013\u0010\u009d\u0001\u001a\u00030\u009e\u00012\u0007\u0010\u001c\u001a\u00030\u009e\u0001H\u0016J\u0011\u0010\u009f\u0001\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\u0013\u0010 \u0001\u001a\u00030¡\u00012\u0007\u0010\u0012\u001a\u00030¡\u0001H\u0016J\u0013\u0010¢\u0001\u001a\u00030£\u00012\u0007\u0010\u001c\u001a\u00030£\u0001H\u0016J\u0013\u0010¤\u0001\u001a\u00030¥\u00012\u0007\u0010\u0012\u001a\u00030¥\u0001H\u0016J\u0013\u0010¦\u0001\u001a\u00030§\u00012\u0007\u0010\u001c\u001a\u00030§\u0001H\u0016J\u0013\u0010¨\u0001\u001a\u00030©\u00012\u0007\u0010L\u001a\u00030©\u0001H\u0016J!\u0010ª\u0001\u001a\u00030«\u0001*\u0007\u0012\u0002\b\u00030¬\u00012\f\u0010\u00ad\u0001\u001a\u0007\u0012\u0002\b\u00030¬\u0001H\u0002J\u0019\u0010®\u0001\u001a\u00030«\u0001*\u00030¯\u00012\b\u0010\u00ad\u0001\u001a\u00030¯\u0001H\u0004J\u0018\u0010°\u0001\u001a\u00030±\u0001*\u00020\u00032\b\u0010²\u0001\u001a\u00030±\u0001H\u0002J\u000f\u0010³\u0001\u001a\u00030´\u0001*\u00030´\u0001H\u0004J#\u0010µ\u0001\u001a\u0003H¶\u0001\"\u000b\b\u0000\u0010¶\u0001\u0018\u0001*\u00020R*\u0003H¶\u0001H\u0084\b¢\u0006\u0003\u0010·\u0001J+\u0010µ\u0001\u001a\n\u0012\u0005\u0012\u0003H¶\u00010¸\u0001\"\u000b\b\u0000\u0010¶\u0001\u0018\u0001*\u00020R*\n\u0012\u0005\u0012\u0003H¶\u00010¸\u0001H\u0084\bJ\u0018\u0010¹\u0001\u001a\u00030«\u0001*\u00030º\u00012\u0007\u0010\u0012\u001a\u00030»\u0001H\u0004J2\u0010¼\u0001\u001a\n\u0012\u0005\u0012\u00030¾\u00010½\u0001\"\n\b\u0000\u0010¶\u0001*\u00030¿\u0001*\u0003H¶\u00012\b\u0010À\u0001\u001a\u0003H¶\u0001H\u0004¢\u0006\u0003\u0010Á\u0001J*\u0010Â\u0001\u001a\u0003H¶\u0001\"\n\b\u0000\u0010¶\u0001*\u00030Ã\u0001*\u0003H¶\u00012\u0007\u0010\u0012\u001a\u0003H¶\u0001H\u0002¢\u0006\u0003\u0010Ä\u0001J/\u0010Å\u0001\u001a\u0003H¶\u0001\"\u000e\b\u0000\u0010¶\u0001*\u0007\u0012\u0002\b\u00030¬\u0001*\u0003H¶\u00012\b\u0010Æ\u0001\u001a\u0003H¶\u0001H\u0002¢\u0006\u0003\u0010Ç\u0001J<\u0010È\u0001\u001a\n\u0012\u0005\u0012\u0003H¶\u00010½\u0001\"\u000b\b\u0000\u0010¶\u0001\u0018\u0001*\u00020R*\n\u0012\u0005\u0012\u0003H¶\u00010¸\u00012\u000f\u0010À\u0001\u001a\n\u0012\u0005\u0012\u0003H¶\u00010½\u0001H\u0084\bJ/\u0010É\u0001\u001a\u00030«\u0001\"\u000e\b\u0000\u0010¶\u0001*\u0007\u0012\u0002\b\u00030¬\u0001*\u0003H¶\u00012\b\u0010Æ\u0001\u001a\u0003H¶\u0001H\u0002¢\u0006\u0003\u0010Ê\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Ë\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;)V", "symbolRenamer", "Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;", "(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;)V", "transformedLoops", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lkotlin/collections/HashMap;", "transformedModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "copyTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "declaration", "getTransformedLoop", "irLoop", "mapDeclarationOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "origin", "mapStatementOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "shallowCopyCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "expression", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "body", "visitBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", ConfigConstants.CONFIG_BRANCH_SECTION, "visitBreak", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "jump", "visitCall", "visitCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "aCatch", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstantArray", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "visitConstantObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "visitConstantPrimitive", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "loop", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "visitElement", "Lorg/jetbrains/kotlin/ir/IrElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitElseBranch", "Lorg/jetbrains/kotlin/ir/expressions/IrElseBranch;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitErrorCallExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "visitErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "visitGetClass", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "visitGetEnumValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitModuleFragment", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitSpreadElement", "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "spread", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitSyntheticBody", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "aTry", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitTypeParameter", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "copyRemappedTypeArgumentsFrom", "", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "other", "copyTypeParametersFrom", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "getReferencedReturnTarget", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "returnTarget", "remapType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "transform", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/IrElement;)Lorg/jetbrains/kotlin/ir/IrElement;", "", "transformAnnotations", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "transformDeclarationsTo", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", FirebaseAnalytics.Param.DESTINATION, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)Ljava/util/List;", "transformFunctionChildren", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "transformReceiverArguments", "original", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "transformTo", "transformValueArguments", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeepCopyIrTreeWithSymbols extends IrElementTransformerVoid {
    private final SymbolRemapper symbolRemapper;
    private final SymbolRenamer symbolRenamer;
    private final HashMap<IrLoop, IrLoop> transformedLoops;
    private IrModuleFragment transformedModule;
    private final TypeRemapper typeRemapper;

    protected final IrDeclarationOrigin mapDeclarationOrigin(IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        return origin;
    }

    protected final IrStatementOrigin mapStatementOrigin(IrStatementOrigin origin) {
        return origin;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public /* bridge */ /* synthetic */ IrExpression visitConst(IrConst irConst) {
        return visitConst((IrConst<?>) irConst);
    }

    public DeepCopyIrTreeWithSymbols(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper, SymbolRenamer symbolRenamer) {
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(symbolRenamer, "symbolRenamer");
        this.symbolRemapper = symbolRemapper;
        this.typeRemapper = typeRemapper;
        this.symbolRenamer = symbolRenamer;
        DeepCopyTypeRemapper deepCopyTypeRemapper = typeRemapper instanceof DeepCopyTypeRemapper ? (DeepCopyTypeRemapper) typeRemapper : null;
        if (deepCopyTypeRemapper != null) {
            deepCopyTypeRemapper.setDeepCopy(this);
        }
        this.transformedLoops = new HashMap<>();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeepCopyIrTreeWithSymbols(SymbolRemapper symbolRemapper, TypeRemapper typeRemapper) {
        this(symbolRemapper, typeRemapper, SymbolRenamer.DEFAULT.INSTANCE);
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
    }

    protected final /* synthetic */ <T extends IrElement> T transform(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) t.transform(this, null);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return t2;
    }

    protected final /* synthetic */ <T extends IrElement> List<T> transform(List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends T> list2 = list;
        ArrayList arrayList = new ArrayList(list2.size());
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
            arrayList.add(irElementTransform);
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    protected final /* synthetic */ <T extends IrElement> List<T> transformTo(List<? extends T> list, List<T> destination) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
            destination.add(irElementTransform);
        }
        return destination;
    }

    protected final <T extends IrDeclarationContainer> List<IrDeclaration> transformDeclarationsTo(T t, T destination) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        List<IrDeclaration> declarations = t.getDeclarations();
        List<IrDeclaration> declarations2 = destination.getDeclarations();
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            List<IrDeclaration> list = declarations2;
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                list.add((IrDeclaration) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
            }
        }
        return declarations2;
    }

    protected final IrType remapType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return this.typeRemapper.remapType(irType);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrElement visitElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new IllegalArgumentException("Unsupported element type: " + element);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrModuleFragment visitModuleFragment(IrModuleFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrModuleFragmentImpl irModuleFragmentImpl = new IrModuleFragmentImpl(declaration.getDescriptor(), declaration.getIrBuiltins(), null, 4, null);
        IrModuleFragmentImpl irModuleFragmentImpl2 = irModuleFragmentImpl;
        this.transformedModule = irModuleFragmentImpl2;
        List<IrFile> files = irModuleFragmentImpl.getFiles();
        List<IrFile> files2 = declaration.getFiles();
        ArrayList arrayList = new ArrayList(files2.size());
        Iterator<T> it = files2.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrFile) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
            }
        }
        kotlin.collections.CollectionsKt.addAll(files, CollectionsKt.compactIfPossible(arrayList));
        this.transformedModule = null;
        return irModuleFragmentImpl2;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExternalPackageFragment visitExternalPackageFragment(IrExternalPackageFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrExternalPackageFragmentImpl irExternalPackageFragmentImpl = new IrExternalPackageFragmentImpl(this.symbolRemapper.getDeclaredExternalPackageFragment(declaration.getSymbol()), this.symbolRenamer.getExternalPackageFragmentName(declaration.getSymbol()));
        transformDeclarationsTo(declaration, irExternalPackageFragmentImpl);
        return irExternalPackageFragmentImpl;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFile visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFileEntry fileEntry = declaration.getFileEntry();
        IrFileSymbol declaredFile = this.symbolRemapper.getDeclaredFile(declaration.getSymbol());
        FqName fileName = this.symbolRenamer.getFileName(declaration.getSymbol());
        IrModuleFragment module = this.transformedModule;
        if (module == null) {
            module = declaration.getModule();
        }
        IrFileImpl irFileImpl = new IrFileImpl(fileEntry, declaredFile, fileName, module);
        transformAnnotations(irFileImpl, declaration);
        transformDeclarationsTo(declaration, irFileImpl);
        return irFileImpl;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitDeclaration(IrDeclarationBase declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        throw new IllegalArgumentException("Unsupported declaration type: " + declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitScript(IrScript declaration) {
        IrValueParameter irValueParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrScriptImpl irScriptImpl = new IrScriptImpl(this.symbolRemapper.getDeclaredScript(declaration.getSymbol()), declaration.getName(), declaration.getFactory(), declaration.getStartOffset(), declaration.getEndOffset());
        IrValueParameter thisReceiver = declaration.getThisReceiver();
        if (thisReceiver != null) {
            IrElement irElementTransform = thisReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter = (IrValueParameter) irElementTransform;
        } else {
            irValueParameter = null;
        }
        irScriptImpl.setThisReceiver(irValueParameter);
        List<IrStatement> statements = declaration.getStatements();
        List<IrStatement> statements2 = irScriptImpl.getStatements();
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform2 = ((IrStatement) it.next()).transform(this, null);
            if (irElementTransform2 != null) {
                statements2.add((IrStatement) irElementTransform2);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            }
        }
        irScriptImpl.setImportedScripts(declaration.getImportedScripts());
        irScriptImpl.setEarlierScripts(declaration.getEarlierScripts());
        irScriptImpl.setEarlierScriptsParameter(declaration.getEarlierScriptsParameter());
        List<IrVariable> explicitCallParameters = declaration.getExplicitCallParameters();
        ArrayList arrayList = new ArrayList(explicitCallParameters.size());
        Iterator<T> it2 = explicitCallParameters.iterator();
        while (it2.hasNext()) {
            IrElement irElementTransform3 = ((IrVariable) it2.next()).transform(this, null);
            if (irElementTransform3 != null) {
                arrayList.add((IrVariable) irElementTransform3);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
            }
        }
        irScriptImpl.setExplicitCallParameters(CollectionsKt.compactIfPossible(arrayList));
        List<IrValueParameter> implicitReceiversParameters = declaration.getImplicitReceiversParameters();
        ArrayList arrayList2 = new ArrayList(implicitReceiversParameters.size());
        Iterator<T> it3 = implicitReceiversParameters.iterator();
        while (it3.hasNext()) {
            IrElement irElementTransform4 = ((IrValueParameter) it3.next()).transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform4 != null) {
                arrayList2.add((IrValueParameter) irElementTransform4);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        irScriptImpl.setImplicitReceiversParameters(CollectionsKt.compactIfPossible(arrayList2));
        List<IrValueParameter> providedPropertiesParameters = declaration.getProvidedPropertiesParameters();
        ArrayList arrayList3 = new ArrayList(providedPropertiesParameters.size());
        Iterator<T> it4 = providedPropertiesParameters.iterator();
        while (it4.hasNext()) {
            IrElement irElementTransform5 = ((IrValueParameter) it4.next()).transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform5 != null) {
                arrayList3.add((IrValueParameter) irElementTransform5);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        irScriptImpl.setProvidedPropertiesParameters(CollectionsKt.compactIfPossible(arrayList3));
        return irScriptImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrClass visitClass(IrClass declaration) {
        IrValueParameter irValueParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(declaration.getFactory(), declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getClassName(declaration.getSymbol()), declaration.getVisibility(), this.symbolRemapper.getDeclaredClass(declaration.getSymbol()), declaration.getKind(), declaration.getModality(), declaration.getIsExternal(), declaration.getIsCompanion(), declaration.getIsInner(), declaration.getIsData(), declaration.getIsValue(), declaration.getIsExpect(), declaration.getIsFun(), (SourceElement) null, 32768, (Object) null);
        transformAnnotations(irClassCreateClass$default, declaration);
        copyTypeParametersFrom(irClassCreateClass$default, declaration);
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList(superTypes.size());
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        irClassCreateClass$default.setSuperTypes(CollectionsKt.compactIfPossible(arrayList));
        List<IrClassSymbol> sealedSubclasses = declaration.getSealedSubclasses();
        ArrayList arrayList2 = new ArrayList(sealedSubclasses.size());
        Iterator<T> it2 = sealedSubclasses.iterator();
        while (it2.hasNext()) {
            arrayList2.add(this.symbolRemapper.getReferencedClass((IrClassSymbol) it2.next()));
        }
        irClassCreateClass$default.setSealedSubclasses(CollectionsKt.compactIfPossible(arrayList2));
        IrValueParameter thisReceiver = declaration.getThisReceiver();
        if (thisReceiver != null) {
            IrElement irElementTransform = thisReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter = (IrValueParameter) irElementTransform;
        } else {
            irValueParameter = null;
        }
        irClassCreateClass$default.setThisReceiver(irValueParameter);
        ValueClassRepresentation<IrSimpleType> valueClassRepresentation = declaration.getValueClassRepresentation();
        irClassCreateClass$default.setValueClassRepresentation(valueClassRepresentation != null ? valueClassRepresentation.mapUnderlyingType(new Function1<IrSimpleType, IrSimpleType>() { // from class: org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols$visitClass$1$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleType invoke(IrSimpleType it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                IrType irTypeRemapType = this.this$0.remapType(it3);
                Intrinsics.checkNotNull(irTypeRemapType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return (IrSimpleType) irTypeRemapType;
            }
        }) : null);
        transformDeclarationsTo(declaration, irClassCreateClass$default);
        return (IrClass) IrDeclarationsKt.copyAttributes(irClassCreateClass$default, declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSimpleFunction visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction = declaration.getFactory().createSimpleFunction(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getFunctionName(declaration.getSymbol()), declaration.getVisibility(), declaration.getIsInline(), declaration.getIsExpect(), declaration.getReturnType(), declaration.getModality(), this.symbolRemapper.getDeclaredFunction(declaration.getSymbol()), declaration.getIsTailrec(), declaration.getIsSuspend(), declaration.getIsOperator(), declaration.getIsInfix(), declaration.getIsExternal(), declaration.getContainerSource(), declaration.getIsFakeOverride());
        List<IrSimpleFunctionSymbol> overriddenSymbols = declaration.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(overriddenSymbols.size());
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            IrFunctionSymbol referencedFunction = this.symbolRemapper.getReferencedFunction((IrSimpleFunctionSymbol) it.next());
            Intrinsics.checkNotNull(referencedFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
            arrayList.add((IrSimpleFunctionSymbol) referencedFunction);
        }
        irSimpleFunctionCreateSimpleFunction.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList));
        irSimpleFunctionCreateSimpleFunction.setContextReceiverParametersCount(declaration.getContextReceiverParametersCount());
        IrDeclarationsKt.copyAttributes(irSimpleFunctionCreateSimpleFunction, declaration);
        transformFunctionChildren(irSimpleFunctionCreateSimpleFunction, declaration);
        return irSimpleFunctionCreateSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstructor visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrConstructor irConstructorCreateConstructor = declaration.getFactory().createConstructor(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), declaration.getName(), declaration.getVisibility(), declaration.getIsInline(), declaration.getIsExpect(), declaration.getReturnType(), this.symbolRemapper.getDeclaredConstructor(declaration.getSymbol()), declaration.getIsPrimary(), declaration.getIsExternal(), declaration.getContainerSource());
        transformFunctionChildren(irConstructorCreateConstructor, declaration);
        return irConstructorCreateConstructor;
    }

    private final <T extends IrFunction> T transformFunctionChildren(T t, T t2) {
        IrValueParameter irValueParameter;
        IrValueParameter irValueParameter2;
        transformAnnotations(t, t2);
        T t3 = t;
        copyTypeParametersFrom(t3, t2);
        TypeRemapper typeRemapper = this.typeRemapper;
        typeRemapper.enterScope(t3);
        IrValueParameter dispatchReceiverParameter = t2.getDispatchReceiverParameter();
        IrBody irBody = null;
        if (dispatchReceiverParameter != null) {
            IrElement irElementTransform = dispatchReceiverParameter.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter = (IrValueParameter) irElementTransform;
        } else {
            irValueParameter = null;
        }
        t.setDispatchReceiverParameter(irValueParameter);
        IrValueParameter extensionReceiverParameter = t2.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            IrElement irElementTransform2 = extensionReceiverParameter.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
            irValueParameter2 = (IrValueParameter) irElementTransform2;
        } else {
            irValueParameter2 = null;
        }
        t.setExtensionReceiverParameter(irValueParameter2);
        t.setReturnType(this.typeRemapper.remapType(t2.getReturnType()));
        List<IrValueParameter> valueParameters = t2.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform3 = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform3 != null) {
                arrayList.add((IrValueParameter) irElementTransform3);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            }
        }
        t.setValueParameters(CollectionsKt.compactIfPossible(arrayList));
        IrBody body = t2.getBody();
        if (body != null) {
            IrElement irElementTransform4 = body.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
            }
            irBody = (IrBody) irElementTransform4;
        }
        t.setBody(irBody);
        Unit unit = Unit.INSTANCE;
        typeRemapper.leaveScope();
        return t;
    }

    protected final void transformAnnotations(IrMutableAnnotationContainer irMutableAnnotationContainer, IrAnnotationContainer declaration) {
        Intrinsics.checkNotNullParameter(irMutableAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<IrConstructorCall> annotations = declaration.getAnnotations();
        ArrayList arrayList = new ArrayList(annotations.size());
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrConstructorCall) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            }
        }
        irMutableAnnotationContainer.setAnnotations(CollectionsKt.compactIfPossible(arrayList));
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0095  */
    /* JADX WARN: Code duplicated, block: B:21:0x00c2  */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrProperty visitProperty(IrProperty declaration) {
        IrField irField;
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(declaration.getFactory(), declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), declaration.getName(), declaration.getVisibility(), declaration.getModality(), this.symbolRemapper.getDeclaredProperty(declaration.getSymbol()), declaration.getIsVar(), declaration.getIsConst(), declaration.getIsLateinit(), declaration.getIsDelegated(), declaration.getIsExternal(), declaration.getContainerSource(), declaration.getIsExpect(), false, 16384, (Object) null);
        transformAnnotations(irPropertyCreateProperty$default, declaration);
        IrDeclarationsKt.copyAttributes(irPropertyCreateProperty$default, declaration);
        IrField backingField = declaration.getBackingField();
        IrSimpleFunction irSimpleFunction2 = null;
        if (backingField == null) {
            irField = null;
        } else {
            IrElement irElementTransform = backingField.transform(this, null);
            if (irElementTransform != null) {
                irField = (IrField) irElementTransform;
                if (irField != null) {
                    irField.setCorrespondingPropertySymbol(irPropertyCreateProperty$default.getSymbol());
                } else {
                    irField = null;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrField");
            }
        }
        irPropertyCreateProperty$default.setBackingField(irField);
        IrSimpleFunction getter = declaration.getGetter();
        if (getter == null) {
            irSimpleFunction = null;
        } else {
            IrElement irElementTransform2 = getter.transform(this, null);
            if (irElementTransform2 != null) {
                irSimpleFunction = (IrSimpleFunction) irElementTransform2;
                if (irSimpleFunction != null) {
                    irSimpleFunction.setCorrespondingPropertySymbol(irPropertyCreateProperty$default.getSymbol());
                } else {
                    irSimpleFunction = null;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            }
        }
        irPropertyCreateProperty$default.setGetter(irSimpleFunction);
        IrSimpleFunction setter = declaration.getSetter();
        if (setter != null) {
            IrElement irElementTransform3 = setter.transform(this, null);
            if (irElementTransform3 != null) {
                IrSimpleFunction irSimpleFunction3 = (IrSimpleFunction) irElementTransform3;
                if (irSimpleFunction3 != null) {
                    irSimpleFunction3.setCorrespondingPropertySymbol(irPropertyCreateProperty$default.getSymbol());
                    irSimpleFunction2 = irSimpleFunction3;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            }
        }
        irPropertyCreateProperty$default.setSetter(irSimpleFunction2);
        List<IrPropertySymbol> overriddenSymbols = declaration.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(overriddenSymbols.size());
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolRemapper.getReferencedProperty((IrPropertySymbol) it.next()));
        }
        irPropertyCreateProperty$default.setOverriddenSymbols(CollectionsKt.compactIfPossible(arrayList));
        return irPropertyCreateProperty$default;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrField visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrField irFieldCreateField = declaration.getFactory().createField(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getFieldName(declaration.getSymbol()), declaration.getVisibility(), this.symbolRemapper.getDeclaredField(declaration.getSymbol()), remapType(declaration.getType()), declaration.getIsFinal(), declaration.getIsStatic(), declaration.getIsExternal());
        transformAnnotations(irFieldCreateField, declaration);
        IrExpressionBody initializer = declaration.getInitializer();
        IrExpressionBody irExpressionBody = null;
        if (initializer != null) {
            IrElement irElementTransform = initializer.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
            }
            irExpressionBody = (IrExpressionBody) irElementTransform;
        }
        irFieldCreateField.setInitializer(irExpressionBody);
        return irFieldCreateField;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrLocalDelegatedProperty visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrLocalDelegatedProperty irLocalDelegatedPropertyCreateLocalDelegatedProperty = declaration.getFactory().createLocalDelegatedProperty(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), declaration.getName(), this.symbolRemapper.getDeclaredLocalDelegatedProperty(declaration.getSymbol()), remapType(declaration.getType()), declaration.getIsVar());
        transformAnnotations(irLocalDelegatedPropertyCreateLocalDelegatedProperty, declaration);
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrSimpleFunction irSimpleFunction = null;
        IrElement irElementTransform = declaration.getDelegate().transform(deepCopyIrTreeWithSymbols, null);
        if (irElementTransform != null) {
            irLocalDelegatedPropertyCreateLocalDelegatedProperty.setDelegate((IrVariable) irElementTransform);
            IrElement irElementTransform2 = declaration.getGetter().transform(deepCopyIrTreeWithSymbols, null);
            if (irElementTransform2 != null) {
                irLocalDelegatedPropertyCreateLocalDelegatedProperty.setGetter((IrSimpleFunction) irElementTransform2);
                IrSimpleFunction setter = declaration.getSetter();
                if (setter != null) {
                    IrElement irElementTransform3 = setter.transform(deepCopyIrTreeWithSymbols, null);
                    if (irElementTransform3 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                    }
                    irSimpleFunction = (IrSimpleFunction) irElementTransform3;
                }
                irLocalDelegatedPropertyCreateLocalDelegatedProperty.setSetter(irSimpleFunction);
                return irLocalDelegatedPropertyCreateLocalDelegatedProperty;
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrEnumEntry visitEnumEntry(IrEnumEntry declaration) {
        IrClass irClass;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrEnumEntry irEnumEntryCreateEnumEntry = declaration.getFactory().createEnumEntry(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getEnumEntryName(declaration.getSymbol()), this.symbolRemapper.getDeclaredEnumEntry(declaration.getSymbol()));
        transformAnnotations(irEnumEntryCreateEnumEntry, declaration);
        IrClass correspondingClass = declaration.getCorrespondingClass();
        IrExpressionBody irExpressionBody = null;
        if (correspondingClass != null) {
            IrElement irElementTransform = correspondingClass.transform(this, null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            }
            irClass = (IrClass) irElementTransform;
        } else {
            irClass = null;
        }
        irEnumEntryCreateEnumEntry.setCorrespondingClass(irClass);
        IrExpressionBody initializerExpression = declaration.getInitializerExpression();
        if (initializerExpression != null) {
            IrElement irElementTransform2 = initializerExpression.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
            }
            irExpressionBody = (IrExpressionBody) irElementTransform2;
        }
        irEnumEntryCreateEnumEntry.setInitializerExpression(irExpressionBody);
        return irEnumEntryCreateEnumEntry;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrAnonymousInitializer visitAnonymousInitializer(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer$default = IrFactory.DefaultImpls.createAnonymousInitializer$default(declaration.getFactory(), declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), new IrAnonymousInitializerSymbolImpl(declaration.getDescriptor()), false, 16, null);
        transformAnnotations(irAnonymousInitializerCreateAnonymousInitializer$default, declaration);
        IrElement irElementTransform = declaration.getBody().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            irAnonymousInitializerCreateAnonymousInitializer$default.setBody((IrBlockBody) irElementTransform);
            return irAnonymousInitializerCreateAnonymousInitializer$default;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrVariable visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrVariableImpl irVariableImpl = new IrVariableImpl(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRemapper.getDeclaredVariable(declaration.getSymbol()), this.symbolRenamer.getVariableName(declaration.getSymbol()), remapType(declaration.getType()), declaration.getIsVar(), declaration.getIsConst(), declaration.getIsLateinit());
        transformAnnotations(irVariableImpl, declaration);
        IrExpression initializer = declaration.getInitializer();
        IrExpression irExpression = null;
        if (initializer != null) {
            IrElement irElementTransform = initializer.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression = (IrExpression) irElementTransform;
        }
        irVariableImpl.setInitializer(irExpression);
        return irVariableImpl;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTypeParameter visitTypeParameter(IrTypeParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrTypeParameter irTypeParameterCopyTypeParameter = copyTypeParameter(declaration);
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList(superTypes.size());
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapType((IrType) it.next()));
        }
        irTypeParameterCopyTypeParameter.setSuperTypes(CollectionsKt.compactIfPossible(arrayList));
        return irTypeParameterCopyTypeParameter;
    }

    private final IrTypeParameter copyTypeParameter(IrTypeParameter declaration) {
        IrTypeParameter irTypeParameterCreateTypeParameter = declaration.getFactory().createTypeParameter(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getTypeParameterName(declaration.getSymbol()), this.symbolRemapper.getDeclaredTypeParameter(declaration.getSymbol()), declaration.getVariance(), declaration.getIndex(), declaration.getIsReified());
        transformAnnotations(irTypeParameterCreateTypeParameter, declaration);
        return irTypeParameterCreateTypeParameter;
    }

    protected final void copyTypeParametersFrom(IrTypeParametersContainer irTypeParametersContainer, IrTypeParametersContainer other) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        List<IrTypeParameter> typeParameters = other.getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(copyTypeParameter((IrTypeParameter) it.next()));
        }
        irTypeParametersContainer.setTypeParameters(CollectionsKt.compactIfPossible(arrayList));
        TypeRemapper typeRemapper = this.typeRemapper;
        typeRemapper.enterScope(irTypeParametersContainer);
        for (Pair pair : kotlin.collections.CollectionsKt.zip(irTypeParametersContainer.getTypeParameters(), other.getTypeParameters())) {
            IrTypeParameter irTypeParameter = (IrTypeParameter) pair.component1();
            List<IrType> superTypes = ((IrTypeParameter) pair.component2()).getSuperTypes();
            ArrayList arrayList2 = new ArrayList(superTypes.size());
            Iterator<T> it2 = superTypes.iterator();
            while (it2.hasNext()) {
                arrayList2.add(this.typeRemapper.remapType((IrType) it2.next()));
            }
            irTypeParameter.setSuperTypes(CollectionsKt.compactIfPossible(arrayList2));
        }
        Unit unit = Unit.INSTANCE;
        typeRemapper.leaveScope();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrValueParameter visitValueParameter(IrValueParameter declaration) {
        IrExpressionBody irExpressionBody;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFactory factory = declaration.getFactory();
        int startOffset = declaration.getStartOffset();
        int endOffset = declaration.getEndOffset();
        IrDeclarationOrigin irDeclarationOriginMapDeclarationOrigin = mapDeclarationOrigin(declaration.getOrigin());
        Name valueParameterName = this.symbolRenamer.getValueParameterName(declaration.getSymbol());
        IrType irTypeRemapType = remapType(declaration.getType());
        boolean zIsAssignable = declaration.getIsAssignable();
        IrValueParameterSymbol declaredValueParameter = this.symbolRemapper.getDeclaredValueParameter(declaration.getSymbol());
        int index = declaration.getIndex();
        IrType varargElementType = declaration.getVarargElementType();
        IrValueParameter irValueParameterCreateValueParameter = factory.createValueParameter(startOffset, endOffset, irDeclarationOriginMapDeclarationOrigin, valueParameterName, irTypeRemapType, zIsAssignable, declaredValueParameter, index, varargElementType != null ? remapType(varargElementType) : null, declaration.getIsCrossinline(), declaration.getIsNoinline(), declaration.getIsHidden());
        transformAnnotations(irValueParameterCreateValueParameter, declaration);
        IrExpressionBody defaultValue = declaration.getDefaultValue();
        if (defaultValue != null) {
            IrElement irElementTransform = defaultValue.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
            }
            irExpressionBody = (IrExpressionBody) irElementTransform;
        } else {
            irExpressionBody = null;
        }
        irValueParameterCreateValueParameter.setDefaultValue(irExpressionBody);
        return irValueParameterCreateValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTypeAlias visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrTypeAlias irTypeAliasCreateTypeAlias = declaration.getFactory().createTypeAlias(declaration.getStartOffset(), declaration.getEndOffset(), mapDeclarationOrigin(declaration.getOrigin()), this.symbolRenamer.getTypeAliasName(declaration.getSymbol()), declaration.getVisibility(), this.symbolRemapper.getDeclaredTypeAlias(declaration.getSymbol()), declaration.getIsActual(), remapType(declaration.getExpandedType()));
        transformAnnotations(irTypeAliasCreateTypeAlias, declaration);
        copyTypeParametersFrom(irTypeAliasCreateTypeAlias, declaration);
        return irTypeAliasCreateTypeAlias;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBody visitBody(IrBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        throw new IllegalArgumentException("Unsupported body type: " + body);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpressionBody visitExpressionBody(IrExpressionBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        IrFactory factory = body.getFactory();
        IrElement irElementTransform = body.getExpression().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return IrFactoryHelpersKt.createExpressionBody(factory, (IrExpression) irElementTransform);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBlockBody visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        IrFactory factory = body.getFactory();
        int startOffset = body.getStartOffset();
        int endOffset = body.getEndOffset();
        List<IrStatement> statements = body.getStatements();
        ArrayList arrayList = new ArrayList(statements.size());
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrStatement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrStatement) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            }
        }
        return IrFactoryHelpersKt.createBlockBody(factory, startOffset, endOffset, (List<? extends IrStatement>) CollectionsKt.compactIfPossible(arrayList));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSyntheticBody visitSyntheticBody(IrSyntheticBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return new IrSyntheticBodyImpl(body.getStartOffset(), body.getEndOffset(), body.getKind());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        throw new IllegalArgumentException("Unsupported expression type: " + expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConst<?> visitConst(IrConst<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrConst) IrDeclarationsKt.copyAttributes(IrUtilsKt.shallowCopy((IrConst) expression), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstantValue visitConstantObject(IrConstantObject expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrConstructorSymbol referencedConstructor = this.symbolRemapper.getReferencedConstructor(expression.getConstructor());
        List<IrConstantValue> valueArguments = expression.getValueArguments();
        ArrayList arrayList = new ArrayList(valueArguments.size());
        Iterator<T> it = valueArguments.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrConstantValue) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstantValue");
            }
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        List<IrType> typeArguments = expression.getTypeArguments();
        ArrayList arrayList2 = new ArrayList(typeArguments.size());
        Iterator<T> it2 = typeArguments.iterator();
        while (it2.hasNext()) {
            arrayList2.add(remapType((IrType) it2.next()));
        }
        return (IrConstantValue) IrDeclarationsKt.copyAttributes(new IrConstantObjectImpl(startOffset, endOffset, referencedConstructor, listCompactIfPossible, CollectionsKt.compactIfPossible(arrayList2), remapType(expression.getType())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstantValue visitConstantPrimitive(IrConstantPrimitive expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrElement irElementTransform = expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrConstantValue) IrDeclarationsKt.copyAttributes(new IrConstantPrimitiveImpl(startOffset, endOffset, (IrConst) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstantValue visitConstantArray(IrConstantArray expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        List<IrConstantValue> elements = expression.getElements();
        ArrayList arrayList = new ArrayList(elements.size());
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrConstantValue) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstantValue");
            }
        }
        return (IrConstantValue) IrDeclarationsKt.copyAttributes(new IrConstantArrayImpl(startOffset, endOffset, irTypeRemapType, CollectionsKt.compactIfPossible(arrayList)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrVararg visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrType irTypeRemapType2 = remapType(expression.getVarargElementType());
        List<IrVarargElement> elements = expression.getElements();
        ArrayList arrayList = new ArrayList(elements.size());
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrVarargElement) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrVarargElement");
            }
        }
        return (IrVararg) IrDeclarationsKt.copyAttributes(new IrVarargImpl(startOffset, endOffset, irTypeRemapType, irTypeRemapType2, CollectionsKt.compactIfPossible(arrayList)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSpreadElement visitSpreadElement(IrSpreadElement spread) {
        Intrinsics.checkNotNullParameter(spread, "spread");
        int startOffset = spread.getStartOffset();
        int endOffset = spread.getEndOffset();
        IrElement irElementTransform = spread.getExpression().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return new IrSpreadElementImpl(startOffset, endOffset, (IrExpression) irElementTransform);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBlock visitBlock(IrBlock expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof IrReturnableBlock) {
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrType irTypeRemapType = remapType(expression.getType());
            IrReturnableBlockSymbol referencedReturnableBlock = this.symbolRemapper.getReferencedReturnableBlock(((IrReturnableBlock) expression).getSymbol());
            IrStatementOrigin irStatementOriginMapStatementOrigin = mapStatementOrigin(expression.getOrigin());
            List<IrStatement> statements = expression.getStatements();
            ArrayList arrayList = new ArrayList(statements.size());
            Iterator<T> it = statements.iterator();
            while (it.hasNext()) {
                IrElement irElementTransform = ((IrStatement) it.next()).transform(this, null);
                if (irElementTransform != null) {
                    arrayList.add((IrStatement) irElementTransform);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
                }
            }
            return (IrBlock) IrDeclarationsKt.copyAttributes(new IrReturnableBlockImpl(startOffset, endOffset, irTypeRemapType, referencedReturnableBlock, irStatementOriginMapStatementOrigin, CollectionsKt.compactIfPossible(arrayList)), expression);
        }
        if (expression instanceof IrInlinedFunctionBlock) {
            int startOffset2 = expression.getStartOffset();
            int endOffset2 = expression.getEndOffset();
            IrType irTypeRemapType2 = remapType(expression.getType());
            IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) expression;
            IrFunctionAccessExpression inlineCall = irInlinedFunctionBlock.getInlineCall();
            IrElement inlinedElement = irInlinedFunctionBlock.getInlinedElement();
            IrStatementOrigin irStatementOriginMapStatementOrigin2 = mapStatementOrigin(expression.getOrigin());
            List<IrStatement> statements2 = expression.getStatements();
            ArrayList arrayList2 = new ArrayList(statements2.size());
            Iterator<T> it2 = statements2.iterator();
            while (it2.hasNext()) {
                IrElement irElementTransform2 = ((IrStatement) it2.next()).transform(this, null);
                if (irElementTransform2 != null) {
                    arrayList2.add((IrStatement) irElementTransform2);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
                }
            }
            return (IrBlock) IrDeclarationsKt.copyAttributes(new IrInlinedFunctionBlockImpl(startOffset2, endOffset2, irTypeRemapType2, inlineCall, inlinedElement, irStatementOriginMapStatementOrigin2, CollectionsKt.compactIfPossible(arrayList2)), expression);
        }
        int startOffset3 = expression.getStartOffset();
        int endOffset3 = expression.getEndOffset();
        IrType irTypeRemapType3 = remapType(expression.getType());
        IrStatementOrigin irStatementOriginMapStatementOrigin3 = mapStatementOrigin(expression.getOrigin());
        List<IrStatement> statements3 = expression.getStatements();
        ArrayList arrayList3 = new ArrayList(statements3.size());
        Iterator<T> it3 = statements3.iterator();
        while (it3.hasNext()) {
            IrElement irElementTransform3 = ((IrStatement) it3.next()).transform(this, null);
            if (irElementTransform3 != null) {
                arrayList3.add((IrStatement) irElementTransform3);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            }
        }
        return (IrBlock) IrDeclarationsKt.copyAttributes(new IrBlockImpl(startOffset3, endOffset3, irTypeRemapType3, irStatementOriginMapStatementOrigin3, CollectionsKt.compactIfPossible(arrayList3)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrComposite visitComposite(IrComposite expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrStatementOrigin irStatementOriginMapStatementOrigin = mapStatementOrigin(expression.getOrigin());
        List<IrStatement> statements = expression.getStatements();
        ArrayList arrayList = new ArrayList(statements.size());
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrStatement) it.next()).transform(this, null);
            if (irElementTransform != null) {
                arrayList.add((IrStatement) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
            }
        }
        return (IrComposite) IrDeclarationsKt.copyAttributes(new IrCompositeImpl(startOffset, endOffset, irTypeRemapType, irStatementOriginMapStatementOrigin, CollectionsKt.compactIfPossible(arrayList)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStringConcatenation visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        List<IrExpression> arguments = expression.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrExpression) it.next()).transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform != null) {
                arrayList.add((IrExpression) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
        }
        return (IrStringConcatenation) IrDeclarationsKt.copyAttributes(new IrStringConcatenationImpl(startOffset, endOffset, irTypeRemapType, CollectionsKt.compactIfPossible(arrayList)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetObjectValue visitGetObjectValue(IrGetObjectValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrGetObjectValue) IrDeclarationsKt.copyAttributes(new IrGetObjectValueImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedClass(expression.getSymbol())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetEnumValue visitGetEnumValue(IrGetEnumValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrGetEnumValue) IrDeclarationsKt.copyAttributes(new IrGetEnumValueImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedEnumEntry(expression.getSymbol())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetValue visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrGetValue) IrDeclarationsKt.copyAttributes(new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedValue(expression.getSymbol()), mapStatementOrigin(expression.getOrigin())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSetValue visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrValueSymbol referencedValue = this.symbolRemapper.getReferencedValue(expression.getSymbol());
        IrElement irElementTransform = expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrSetValue) IrDeclarationsKt.copyAttributes(new IrSetValueImpl(startOffset, endOffset, irTypeRemapType, referencedValue, (IrExpression) irElementTransform, mapStatementOrigin(expression.getOrigin())), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetField visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrFieldSymbol referencedField = this.symbolRemapper.getReferencedField(expression.getSymbol());
        IrType irTypeRemapType = remapType(expression.getType());
        IrExpression receiver = expression.getReceiver();
        IrExpression irExpression = null;
        if (receiver != null) {
            IrElement irElementTransform = receiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression = (IrExpression) irElementTransform;
        }
        return (IrGetField) IrDeclarationsKt.copyAttributes(new IrGetFieldImpl(startOffset, endOffset, referencedField, irTypeRemapType, irExpression, mapStatementOrigin(expression.getOrigin()), this.symbolRemapper.getReferencedClassOrNull(expression.getSuperQualifierSymbol())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrSetField visitSetField(IrSetField expression) {
        IrExpression irExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrFieldSymbol referencedField = this.symbolRemapper.getReferencedField(expression.getSymbol());
        IrExpression receiver = expression.getReceiver();
        if (receiver != null) {
            IrElement irElementTransform = receiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression = (IrExpression) irElementTransform;
        } else {
            irExpression = null;
        }
        IrElement irElementTransform2 = expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform2 != null) {
            return (IrSetField) IrDeclarationsKt.copyAttributes(new IrSetFieldImpl(startOffset, endOffset, referencedField, irExpression, (IrExpression) irElementTransform2, remapType(expression.getType()), mapStatementOrigin(expression.getOrigin()), this.symbolRemapper.getReferencedClassOrNull(expression.getSuperQualifierSymbol())), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrCall visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrCall irCallShallowCopyCall = shallowCopyCall(expression);
        IrCall irCall = irCallShallowCopyCall;
        IrCall irCall2 = expression;
        copyRemappedTypeArgumentsFrom(irCall, irCall2);
        transformValueArguments(irCall, irCall2);
        return irCallShallowCopyCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrConstructorCall visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedConstructor(expression.getSymbol()), expression.getTypeArgumentsCount(), expression.getConstructorTypeArgumentsCount(), expression.getValueArgumentsCount(), mapStatementOrigin(expression.getOrigin()), null, 256, null);
        IrConstructorCallImpl irConstructorCallImpl2 = irConstructorCallImpl;
        IrConstructorCall irConstructorCall = expression;
        copyRemappedTypeArgumentsFrom(irConstructorCallImpl2, irConstructorCall);
        transformValueArguments(irConstructorCallImpl2, irConstructorCall);
        return (IrConstructorCall) IrDeclarationsKt.copyAttributes(irConstructorCallImpl, expression);
    }

    private final void copyRemappedTypeArgumentsFrom(IrMemberAccessExpression<?> irMemberAccessExpression, IrMemberAccessExpression<?> irMemberAccessExpression2) {
        irMemberAccessExpression.getTypeArgumentsCount();
        irMemberAccessExpression2.getTypeArgumentsCount();
        int typeArgumentsCount = irMemberAccessExpression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            IrType typeArgument = irMemberAccessExpression2.getTypeArgument(i);
            irMemberAccessExpression.putTypeArgument(i, typeArgument != null ? remapType(typeArgument) : null);
        }
    }

    private final IrCall shallowCopyCall(IrCall expression) {
        IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedSimpleFunction(expression.getSymbol()), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), mapStatementOrigin(expression.getOrigin()), this.symbolRemapper.getReferencedClassOrNull(expression.getSuperQualifierSymbol()));
        copyRemappedTypeArgumentsFrom(irCallImpl, expression);
        return (IrCall) IrDeclarationsKt.copyAttributes(irCallImpl, expression);
    }

    private final <T extends IrMemberAccessExpression<?>> T transformReceiverArguments(T t, T t2) {
        IrExpression irExpression;
        IrExpression dispatchReceiver = t2.getDispatchReceiver();
        IrExpression irExpression2 = null;
        if (dispatchReceiver != null) {
            IrElement irElementTransform = dispatchReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression = (IrExpression) irElementTransform;
        } else {
            irExpression = null;
        }
        t.setDispatchReceiver(irExpression);
        IrExpression extensionReceiver = t2.getExtensionReceiver();
        if (extensionReceiver != null) {
            IrElement irElementTransform2 = extensionReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression2 = (IrExpression) irElementTransform2;
        }
        t.setExtensionReceiver(irExpression2);
        return t;
    }

    private final <T extends IrMemberAccessExpression<?>> void transformValueArguments(T t, T t2) {
        transformReceiverArguments(t, t2);
        int valueArgumentsCount = t2.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = t2.getValueArgument(i);
            IrExpression irExpression = null;
            if (valueArgument != null) {
                IrElement irElementTransform = valueArgument.transform((IrElementTransformer<? super Object>) this, (Object) null);
                if (irElementTransform == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
                irExpression = (IrExpression) irElementTransform;
            }
            t.putValueArgument(i, irExpression);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrDelegatingConstructorCall visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedConstructor(expression.getSymbol()), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount());
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl2 = irDelegatingConstructorCallImpl;
        IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
        copyRemappedTypeArgumentsFrom(irDelegatingConstructorCallImpl2, irDelegatingConstructorCall);
        transformValueArguments(irDelegatingConstructorCallImpl2, irDelegatingConstructorCall);
        return (IrDelegatingConstructorCall) IrDeclarationsKt.copyAttributes(irDelegatingConstructorCallImpl, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrEnumConstructorCall visitEnumConstructorCall(IrEnumConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrEnumConstructorCallImpl irEnumConstructorCallImpl = new IrEnumConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedConstructor(expression.getSymbol()), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount());
        IrEnumConstructorCallImpl irEnumConstructorCallImpl2 = irEnumConstructorCallImpl;
        IrEnumConstructorCall irEnumConstructorCall = expression;
        copyRemappedTypeArgumentsFrom(irEnumConstructorCallImpl2, irEnumConstructorCall);
        transformValueArguments(irEnumConstructorCallImpl2, irEnumConstructorCall);
        return (IrEnumConstructorCall) IrDeclarationsKt.copyAttributes(irEnumConstructorCallImpl, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrGetClass visitGetClass(IrGetClass expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrElement irElementTransform = expression.getArgument().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrGetClass) IrDeclarationsKt.copyAttributes(new IrGetClassImpl(startOffset, endOffset, irTypeRemapType, (IrExpression) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFunctionReference visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFunctionSymbol referencedFunction = this.symbolRemapper.getReferencedFunction(expression.getSymbol());
        IrFunctionSymbol reflectionTarget = expression.getReflectionTarget();
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), referencedFunction, expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), reflectionTarget != null ? this.symbolRemapper.getReferencedFunction(reflectionTarget) : null, mapStatementOrigin(expression.getOrigin()));
        IrFunctionReferenceImpl irFunctionReferenceImpl2 = irFunctionReferenceImpl;
        IrFunctionReference irFunctionReference = expression;
        copyRemappedTypeArgumentsFrom(irFunctionReferenceImpl2, irFunctionReference);
        transformValueArguments(irFunctionReferenceImpl2, irFunctionReference);
        return (IrFunctionReference) IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrRawFunctionReference visitRawFunctionReference(IrRawFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrRawFunctionReference) IrDeclarationsKt.copyAttributes(new IrRawFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedFunction(expression.getSymbol())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrPropertyReference visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrPropertySymbol referencedProperty = this.symbolRemapper.getReferencedProperty(expression.getSymbol());
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        IrFieldSymbol field = expression.getField();
        IrFieldSymbol referencedField = field != null ? this.symbolRemapper.getReferencedField(field) : null;
        IrSimpleFunctionSymbol getter = expression.getGetter();
        IrSimpleFunctionSymbol referencedSimpleFunction = getter != null ? this.symbolRemapper.getReferencedSimpleFunction(getter) : null;
        IrSimpleFunctionSymbol setter = expression.getSetter();
        IrPropertyReferenceImpl irPropertyReferenceImpl = new IrPropertyReferenceImpl(startOffset, endOffset, irTypeRemapType, referencedProperty, typeArgumentsCount, referencedField, referencedSimpleFunction, setter != null ? this.symbolRemapper.getReferencedSimpleFunction(setter) : null, mapStatementOrigin(expression.getOrigin()));
        IrPropertyReferenceImpl irPropertyReferenceImpl2 = irPropertyReferenceImpl;
        IrPropertyReference irPropertyReference = expression;
        copyRemappedTypeArgumentsFrom(irPropertyReferenceImpl2, irPropertyReference);
        transformReceiverArguments(irPropertyReferenceImpl2, irPropertyReference);
        return (IrPropertyReference) IrDeclarationsKt.copyAttributes(irPropertyReferenceImpl, expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrLocalDelegatedPropertyReference visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrLocalDelegatedPropertySymbol referencedLocalDelegatedProperty = this.symbolRemapper.getReferencedLocalDelegatedProperty(expression.getSymbol());
        IrVariableSymbol referencedVariable = this.symbolRemapper.getReferencedVariable(expression.getDelegate());
        IrSimpleFunctionSymbol referencedSimpleFunction = this.symbolRemapper.getReferencedSimpleFunction(expression.getGetter());
        IrSimpleFunctionSymbol setter = expression.getSetter();
        return (IrLocalDelegatedPropertyReference) IrDeclarationsKt.copyAttributes(new IrLocalDelegatedPropertyReferenceImpl(startOffset, endOffset, irTypeRemapType, referencedLocalDelegatedProperty, referencedVariable, referencedSimpleFunction, setter != null ? this.symbolRemapper.getReferencedSimpleFunction(setter) : null, mapStatementOrigin(expression.getOrigin())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrFunctionExpression visitFunctionExpression(IrFunctionExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrElement irElementTransform = expression.getFunction().transform(this, null);
        if (irElementTransform != null) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irElementTransform;
            IrStatementOrigin irStatementOriginMapStatementOrigin = mapStatementOrigin(expression.getOrigin());
            Intrinsics.checkNotNull(irStatementOriginMapStatementOrigin);
            return (IrFunctionExpression) IrDeclarationsKt.copyAttributes(new IrFunctionExpressionImpl(startOffset, endOffset, irTypeRemapType, irSimpleFunction, irStatementOriginMapStatementOrigin), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrClassReference visitClassReference(IrClassReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrClassReference) IrDeclarationsKt.copyAttributes(new IrClassReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), this.symbolRemapper.getReferencedClassifier(expression.getSymbol()), remapType(expression.getClassType())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrInstanceInitializerCall visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrInstanceInitializerCall) IrDeclarationsKt.copyAttributes(new IrInstanceInitializerCallImpl(expression.getStartOffset(), expression.getEndOffset(), this.symbolRemapper.getReferencedClass(expression.getClassSymbol()), remapType(expression.getType())), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTypeOperatorCall visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrTypeOperator operator = expression.getOperator();
        IrType irTypeRemapType2 = remapType(expression.getTypeOperand());
        IrElement irElementTransform = expression.getArgument().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrTypeOperatorCall) IrDeclarationsKt.copyAttributes(new IrTypeOperatorCallImpl(startOffset, endOffset, irTypeRemapType, operator, irTypeRemapType2, (IrExpression) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrWhen visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrStatementOrigin irStatementOriginMapStatementOrigin = mapStatementOrigin(expression.getOrigin());
        List<IrBranch> branches = expression.getBranches();
        ArrayList arrayList = new ArrayList(branches.size());
        Iterator<T> it = branches.iterator();
        while (it.hasNext()) {
            IrElement irElementTransform = ((IrBranch) it.next()).transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform != null) {
                arrayList.add((IrBranch) irElementTransform);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBranch");
            }
        }
        return (IrWhen) IrDeclarationsKt.copyAttributes(new IrWhenImpl(startOffset, endOffset, irTypeRemapType, irStatementOriginMapStatementOrigin, CollectionsKt.compactIfPossible(arrayList)), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBranch visitBranch(IrBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        int startOffset = branch.getStartOffset();
        int endOffset = branch.getEndOffset();
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrElement irElementTransform = branch.getCondition().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        }
        IrExpression irExpression = (IrExpression) irElementTransform;
        IrElement irElementTransform2 = branch.getResult().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform2 != null) {
            return new IrBranchImpl(startOffset, endOffset, irExpression, (IrExpression) irElementTransform2);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrElseBranch visitElseBranch(IrElseBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        int startOffset = branch.getStartOffset();
        int endOffset = branch.getEndOffset();
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrElement irElementTransform = branch.getCondition().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        }
        IrExpression irExpression = (IrExpression) irElementTransform;
        IrElement irElementTransform2 = branch.getResult().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform2 != null) {
            return new IrElseBranchImpl(startOffset, endOffset, irExpression, (IrExpression) irElementTransform2);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    private final IrLoop getTransformedLoop(IrLoop irLoop) {
        IrLoop orDefault = this.transformedLoops.getOrDefault(irLoop, irLoop);
        Intrinsics.checkNotNullExpressionValue(orDefault, "getOrDefault(...)");
        return orDefault;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrWhileLoop visitWhileLoop(IrWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(loop.getStartOffset(), loop.getEndOffset(), remapType(loop.getType()), mapStatementOrigin(loop.getOrigin()));
        this.transformedLoops.put(loop, irWhileLoopImpl);
        irWhileLoopImpl.setLabel(loop.getLabel());
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrExpression irExpression = null;
        IrElement irElementTransform = loop.getCondition().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform != null) {
            irWhileLoopImpl.setCondition((IrExpression) irElementTransform);
            IrExpression body = loop.getBody();
            if (body != null) {
                IrElement irElementTransform2 = body.transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
                if (irElementTransform2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
                irExpression = (IrExpression) irElementTransform2;
            }
            irWhileLoopImpl.setBody(irExpression);
            return (IrWhileLoop) IrDeclarationsKt.copyAttributes(irWhileLoopImpl, loop);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrDoWhileLoop visitDoWhileLoop(IrDoWhileLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(loop.getStartOffset(), loop.getEndOffset(), remapType(loop.getType()), mapStatementOrigin(loop.getOrigin()));
        this.transformedLoops.put(loop, irDoWhileLoopImpl);
        irDoWhileLoopImpl.setLabel(loop.getLabel());
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrExpression irExpression = null;
        IrElement irElementTransform = loop.getCondition().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform != null) {
            irDoWhileLoopImpl.setCondition((IrExpression) irElementTransform);
            IrExpression body = loop.getBody();
            if (body != null) {
                IrElement irElementTransform2 = body.transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
                if (irElementTransform2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
                irExpression = (IrExpression) irElementTransform2;
            }
            irDoWhileLoopImpl.setBody(irExpression);
            return (IrDoWhileLoop) IrDeclarationsKt.copyAttributes(irDoWhileLoopImpl, loop);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBreak visitBreak(IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        IrBreakImpl irBreakImpl = new IrBreakImpl(jump.getStartOffset(), jump.getEndOffset(), remapType(jump.getType()), getTransformedLoop(jump.getLoop()));
        irBreakImpl.setLabel(jump.getLabel());
        return (IrBreak) IrDeclarationsKt.copyAttributes(irBreakImpl, jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrContinue visitContinue(IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        IrContinueImpl irContinueImpl = new IrContinueImpl(jump.getStartOffset(), jump.getEndOffset(), remapType(jump.getType()), getTransformedLoop(jump.getLoop()));
        irContinueImpl.setLabel(jump.getLabel());
        return (IrContinue) IrDeclarationsKt.copyAttributes(irContinueImpl, jump);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrTry visitTry(IrTry aTry) {
        IrExpression irExpression;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        int startOffset = aTry.getStartOffset();
        int endOffset = aTry.getEndOffset();
        IrType irTypeRemapType = remapType(aTry.getType());
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrElement irElementTransform = aTry.getTryResult().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform != null) {
            IrExpression irExpression2 = (IrExpression) irElementTransform;
            List<IrCatch> catches = aTry.getCatches();
            ArrayList arrayList = new ArrayList(catches.size());
            Iterator<T> it = catches.iterator();
            while (it.hasNext()) {
                IrElement irElementTransform2 = ((IrCatch) it.next()).transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
                if (irElementTransform2 != null) {
                    arrayList.add((IrCatch) irElementTransform2);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCatch");
                }
            }
            List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
            IrExpression finallyExpression = aTry.getFinallyExpression();
            if (finallyExpression != null) {
                IrElement irElementTransform3 = finallyExpression.transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
                if (irElementTransform3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
                irExpression = (IrExpression) irElementTransform3;
            } else {
                irExpression = null;
            }
            return (IrTry) IrDeclarationsKt.copyAttributes(new IrTryImpl(startOffset, endOffset, irTypeRemapType, irExpression2, listCompactIfPossible, irExpression), aTry);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrCatch visitCatch(IrCatch aCatch) {
        Intrinsics.checkNotNullParameter(aCatch, "aCatch");
        int startOffset = aCatch.getStartOffset();
        int endOffset = aCatch.getEndOffset();
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrElement irElementTransform = aCatch.getCatchParameter().transform(deepCopyIrTreeWithSymbols, null);
        if (irElementTransform == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
        }
        IrVariable irVariable = (IrVariable) irElementTransform;
        IrElement irElementTransform2 = aCatch.getResult().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform2 != null) {
            return new IrCatchImpl(startOffset, endOffset, irVariable, (IrExpression) irElementTransform2);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrReturn visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrReturnTargetSymbol referencedReturnTarget = getReferencedReturnTarget(this.symbolRemapper, expression.getReturnTargetSymbol());
        IrElement irElementTransform = expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrReturn) IrDeclarationsKt.copyAttributes(new IrReturnImpl(startOffset, endOffset, irTypeRemapType, referencedReturnTarget, (IrExpression) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    private final IrReturnTargetSymbol getReferencedReturnTarget(SymbolRemapper symbolRemapper, IrReturnTargetSymbol irReturnTargetSymbol) {
        if (irReturnTargetSymbol instanceof IrFunctionSymbol) {
            return symbolRemapper.getReferencedFunction((IrFunctionSymbol) irReturnTargetSymbol);
        }
        if (irReturnTargetSymbol instanceof IrReturnableBlockSymbol) {
            return symbolRemapper.getReferencedReturnableBlock((IrReturnableBlockSymbol) irReturnTargetSymbol);
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrThrow visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        IrElement irElementTransform = expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrThrow) IrDeclarationsKt.copyAttributes(new IrThrowImpl(startOffset, endOffset, irTypeRemapType, (IrExpression) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrDynamicOperatorExpression visitDynamicOperatorExpression(IrDynamicOperatorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), expression.getOperator());
        DeepCopyIrTreeWithSymbols deepCopyIrTreeWithSymbols = this;
        IrElement irElementTransform = expression.getReceiver().transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
        if (irElementTransform != null) {
            irDynamicOperatorExpressionImpl.setReceiver((IrExpression) irElementTransform);
            List<IrExpression> arguments = expression.getArguments();
            List<IrExpression> arguments2 = irDynamicOperatorExpressionImpl.getArguments();
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                IrElement irElementTransform2 = ((IrExpression) it.next()).transform((IrElementTransformer<? super Object>) deepCopyIrTreeWithSymbols, (Object) null);
                if (irElementTransform2 != null) {
                    arguments2.add((IrExpression) irElementTransform2);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
            }
            return (IrDynamicOperatorExpression) IrDeclarationsKt.copyAttributes(irDynamicOperatorExpressionImpl, expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrDynamicMemberExpression visitDynamicMemberExpression(IrDynamicMemberExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType irTypeRemapType = remapType(expression.getType());
        String memberName = expression.getMemberName();
        IrElement irElementTransform = expression.getReceiver().transform((IrElementTransformer<? super Object>) this, (Object) null);
        if (irElementTransform != null) {
            return (IrDynamicMemberExpression) IrDeclarationsKt.copyAttributes(new IrDynamicMemberExpressionImpl(startOffset, endOffset, irTypeRemapType, memberName, (IrExpression) irElementTransform), expression);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrErrorDeclaration visitErrorDeclaration(IrErrorDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return declaration.getFactory().createErrorDeclaration(declaration.getStartOffset(), declaration.getEndOffset(), declaration.getDescriptor());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrErrorExpression visitErrorExpression(IrErrorExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return (IrErrorExpression) IrDeclarationsKt.copyAttributes(new IrErrorExpressionImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), expression.getDescription()), expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrErrorCallExpression visitErrorCallExpression(IrErrorCallExpression expression) {
        IrExpression irExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrErrorCallExpressionImpl irErrorCallExpressionImpl = new IrErrorCallExpressionImpl(expression.getStartOffset(), expression.getEndOffset(), remapType(expression.getType()), expression.getDescription());
        IrExpression explicitReceiver = expression.getExplicitReceiver();
        if (explicitReceiver != null) {
            IrElement irElementTransform = explicitReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null);
            if (irElementTransform == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
            irExpression = (IrExpression) irElementTransform;
        } else {
            irExpression = null;
        }
        irErrorCallExpressionImpl.setExplicitReceiver(irExpression);
        List<IrExpression> arguments = expression.getArguments();
        List<IrExpression> arguments2 = irErrorCallExpressionImpl.getArguments();
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            List<IrExpression> list = arguments2;
            IrElement irElementTransform2 = ((IrElement) it.next()).transform(this, null);
            if (irElementTransform2 != null) {
                list.add((IrExpression) irElementTransform2);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            }
        }
        return (IrErrorCallExpression) IrDeclarationsKt.copyAttributes(irErrorCallExpressionImpl, expression);
    }
}
