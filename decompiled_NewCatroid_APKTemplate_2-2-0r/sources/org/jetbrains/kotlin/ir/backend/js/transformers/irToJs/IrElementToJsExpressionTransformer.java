package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
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
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: IrElementToJsExpressionTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u001c\u0010\u001a\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u001b2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010\u001c\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010\u001e\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u001f2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010 \u001a\u00020\u00022\u0006\u0010\t\u001a\u00020!2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\"\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010&\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020'2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010(\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020)2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010*\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020+2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010,\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020-2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u001c\u0010.\u001a\u00020\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030/2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u00100\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002012\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u00102\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002032\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u00104\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002052\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u00106\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002072\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u00108\u001a\u00020\u00022\u0006\u0010\t\u001a\u0002092\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010:\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020;2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0018\u0010<\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020=2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J\u0014\u0010>\u001a\u00020?*\u00020\u00032\u0006\u0010@\u001a\u00020AH\u0002J\f\u0010B\u001a\u00020?*\u00020CH\u0002¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrElementToJsExpressionTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "()V", "binaryOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "operator", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "data", "postfixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPostfixOperation;", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "prefixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "toDoubleConst", "", "f", "", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDynamicMemberExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "visitDynamicOperatorExpression", "visitExpressionBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitGetObjectValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "isClassInlineLike", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isThisReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrElementToJsExpressionTransformer implements BaseIrElementToJsNodeTransformer<JsExpression, JsGenerationContext> {

    /* JADX INFO: compiled from: IrElementToJsExpressionTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[IrDynamicOperator.values().length];
            try {
                iArr2[IrDynamicOperator.UNARY_PLUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[IrDynamicOperator.UNARY_MINUS.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[IrDynamicOperator.EXCL.ordinal()] = 3;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[IrDynamicOperator.PREFIX_INCREMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[IrDynamicOperator.PREFIX_DECREMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[IrDynamicOperator.POSTFIX_INCREMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[IrDynamicOperator.POSTFIX_DECREMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[IrDynamicOperator.BINARY_PLUS.ordinal()] = 8;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[IrDynamicOperator.BINARY_MINUS.ordinal()] = 9;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[IrDynamicOperator.MUL.ordinal()] = 10;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[IrDynamicOperator.DIV.ordinal()] = 11;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[IrDynamicOperator.MOD.ordinal()] = 12;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[IrDynamicOperator.GT.ordinal()] = 13;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[IrDynamicOperator.LT.ordinal()] = 14;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[IrDynamicOperator.GE.ordinal()] = 15;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[IrDynamicOperator.LE.ordinal()] = 16;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[IrDynamicOperator.EQEQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr2[IrDynamicOperator.EXCLEQ.ordinal()] = 18;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[IrDynamicOperator.EQEQEQ.ordinal()] = 19;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[IrDynamicOperator.EXCLEQEQ.ordinal()] = 20;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[IrDynamicOperator.ANDAND.ordinal()] = 21;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[IrDynamicOperator.OROR.ordinal()] = 22;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[IrDynamicOperator.EQ.ordinal()] = 23;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[IrDynamicOperator.PLUSEQ.ordinal()] = 24;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[IrDynamicOperator.MINUSEQ.ordinal()] = 25;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[IrDynamicOperator.MULEQ.ordinal()] = 26;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[IrDynamicOperator.DIVEQ.ordinal()] = 27;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[IrDynamicOperator.MODEQ.ordinal()] = 28;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[IrDynamicOperator.ARRAY_ACCESS.ordinal()] = 29;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[IrDynamicOperator.INVOKE.ordinal()] = 30;
            } catch (NoSuchFieldError unused31) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public JsExpression visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBlock2(IrBlock irBlock, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlock(this, irBlock, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBlockBody2(IrBlockBody irBlockBody, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBody2(IrBody irBody, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBody(this, irBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBranch2(IrBranch irBranch, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBranch(this, irBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBreak2(IrBreak irBreak, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreak(this, irBreak, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitBreakContinue2(IrBreakContinue irBreakContinue, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (JsGenerationContext) obj);
    }

    public JsExpression visitCallableReference(IrCallableReference<?> irCallableReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public JsExpression visitCatch2(IrCatch irCatch, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCatch(this, irCatch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public JsExpression visitClass2(IrClass irClass, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClass(this, irClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitClassReference2(IrClassReference irClassReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClassReference(this, irClassReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (JsGenerationContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstantArray2(IrConstantArray irConstantArray, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstantObject2(IrConstantObject irConstantObject, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstantValue2(IrConstantValue irConstantValue, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstructor2(IrConstructor irConstructor, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructor(this, irConstructor, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitContainerExpression2(IrContainerExpression irContainerExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitContinue2(IrContinue irContinue, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContinue(this, irContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDeclaration2(IrDeclarationBase irDeclarationBase, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public JsExpression visitElseBranch2(IrElseBranch irElseBranch, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsExpression visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public JsExpression visitEnumEntry2(IrEnumEntry irEnumEntry, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public JsExpression visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitErrorExpression2(IrErrorExpression irErrorExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitExpression2(IrExpression irExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpression(this, irExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsExpression visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public JsExpression visitField2(IrField irField, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitField(this, irField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFile2(IrFile irFile, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFile(this, irFile, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFunction2(IrFunction irFunction, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunction(this, irFunction, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFunctionReference2(IrFunctionReference irFunctionReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public JsExpression visitGetClass2(IrGetClass irGetClass, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetClass(this, irGetClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public JsExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public JsExpression visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public JsExpression visitLoop2(IrLoop irLoop, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLoop(this, irLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (JsGenerationContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public JsExpression visitModuleFragment2(IrModuleFragment irModuleFragment, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsExpression visitPackageFragment2(IrPackageFragment irPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public JsExpression visitProperty2(IrProperty irProperty, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitProperty(this, irProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitPropertyReference2(IrPropertyReference irPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public JsExpression visitReturn2(IrReturn irReturn, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitReturn(this, irReturn, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public JsExpression visitScript2(IrScript irScript, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitScript(this, irScript, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSimpleFunction2(IrSimpleFunction irSimpleFunction, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSpreadElement2(IrSpreadElement irSpreadElement, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSyntheticBody2(IrSyntheticBody irSyntheticBody, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public JsExpression visitThrow2(IrThrow irThrow, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitThrow(this, irThrow, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public JsExpression visitTry2(IrTry irTry, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTry(this, irTry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public JsExpression visitTypeAlias2(IrTypeAlias irTypeAlias, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public JsExpression visitTypeParameter2(IrTypeParameter irTypeParameter, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public JsExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public JsExpression visitValueParameter2(IrValueParameter irValueParameter, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public JsExpression visitVariable2(IrVariable irVariable, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVariable(this, irVariable, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsExpression visitWhileLoop2(IrWhileLoop irWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, jsGenerationContext);
    }

    private final boolean isClassInlineLike(JsGenerationContext jsGenerationContext, IrClass irClass) {
        return jsGenerationContext.getStaticContext().getBackendContext().getInlineClassesUtils().isClassInlineLike(irClass);
    }

    public JsExpression visitMemberAccess(IrMemberAccessExpression<?> expression, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        JsExpression jsExpression = (JsExpression) BaseIrElementToJsNodeTransformer.DefaultImpls.visitMemberAccess(this, expression, data);
        MetadataProperties.setSynthetic(jsExpression, Intrinsics.areEqual(expression.getOrigin(), JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE));
        return jsExpression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public JsExpression visitComposite2(IrComposite expression, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        int size = expression.getStatements().size();
        if (size == 0) {
            throw new NotImplementedError("An operation is not implemented: Empty IrComposite is not supported");
        }
        IrElementToJsExpressionTransformer irElementToJsExpressionTransformer = this;
        JsBinaryOperation jsBinaryOperation = (JsExpression) expression.getStatements().get(0).accept(irElementToJsExpressionTransformer, data);
        if (size == 1) {
            return jsBinaryOperation;
        }
        Iterator<T> it = expression.getStatements().iterator();
        while (it.hasNext()) {
            jsBinaryOperation = new JsBinaryOperation(JsBinaryOperator.COMMA, jsBinaryOperation, (JsExpression) ((IrStatement) it.next()).accept(irElementToJsExpressionTransformer, data));
        }
        return jsBinaryOperation;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public JsExpression visitVararg2(IrVararg expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        List<IrVarargElement> elements = expression.getElements();
        if (!(elements instanceof Collection) || !elements.isEmpty()) {
            Iterator<T> it = elements.iterator();
            while (it.hasNext() && !(((IrVarargElement) it.next()) instanceof IrSpreadElement)) {
            }
        }
        List<IrVarargElement> elements2 = expression.getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
        Iterator<T> it2 = elements2.iterator();
        while (it2.hasNext()) {
            arrayList.add((JsExpression) ((IrVarargElement) it2.next()).accept(this, context));
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(new JsArrayLiteral(MemoryOptimizedCollectionUtilKt.toSmartList(arrayList)), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public JsExpression visitExpressionBody2(IrExpressionBody body, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(context, "context");
        return (JsExpression) body.getExpression().accept(this, context);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitFunctionExpression2(IrFunctionExpression expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Object objAccept = expression.getFunction().accept(new IrFunctionToJsTransformer(), context);
        ((JsFunction) objAccept).setName(null);
        return (JsExpression) objAccept;
    }

    public JsExpression visitConst(IrConst<?> expression, JsGenerationContext context) {
        JsDoubleLiteral jsDoubleLiteral;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrConstKind<?> kind = expression.getKind();
        if (kind instanceof IrConstKind.String) {
            jsDoubleLiteral = new JsStringLiteral(((IrConstKind.String) kind).valueOf(expression));
        } else if (kind instanceof IrConstKind.Null) {
            jsDoubleLiteral = new JsNullLiteral();
        } else if (kind instanceof IrConstKind.Boolean) {
            jsDoubleLiteral = new JsBooleanLiteral(((IrConstKind.Boolean) kind).valueOf(expression).booleanValue());
        } else if (kind instanceof IrConstKind.Byte) {
            jsDoubleLiteral = new JsIntLiteral(((IrConstKind.Byte) kind).valueOf(expression).byteValue());
        } else if (kind instanceof IrConstKind.Short) {
            jsDoubleLiteral = new JsIntLiteral(((IrConstKind.Short) kind).valueOf(expression).shortValue());
        } else if (kind instanceof IrConstKind.Int) {
            jsDoubleLiteral = new JsIntLiteral(((IrConstKind.Int) kind).valueOf(expression).intValue());
        } else {
            if (kind instanceof IrConstKind.Long) {
                CompilationExceptionKt.compilationException("Long const should have been lowered at this point", expression);
                throw null;
            }
            if (kind instanceof IrConstKind.Char) {
                CompilationExceptionKt.compilationException("Char const should have been lowered at this point", expression);
                throw null;
            }
            if (kind instanceof IrConstKind.Float) {
                jsDoubleLiteral = new JsDoubleLiteral(toDoubleConst(((IrConstKind.Float) kind).valueOf(expression).floatValue()));
            } else {
                if (!(kind instanceof IrConstKind.Double)) {
                    throw new NoWhenBranchMatchedException();
                }
                jsDoubleLiteral = new JsDoubleLiteral(((IrConstKind.Double) kind).valueOf(expression).doubleValue());
            }
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(jsDoubleLiteral, expression, context, null, null, 12, null);
    }

    private final double toDoubleConst(float f) {
        return (Float.isInfinite(f) || Float.isNaN(f)) ? f : Double.parseDouble(String.valueOf(f));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public JsExpression visitStringConcatenation2(IrStringConcatenation expression, JsGenerationContext context) {
        Pair pair;
        IrType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrExpression irExpression = (IrExpression) CollectionsKt.firstOrNull((List) expression.getArguments());
        if (irExpression != null && (type = irExpression.getType()) != null && IrTypePredicatesKt.isString(type)) {
            pair = new Pair(irExpression.accept(this, context), SequencesKt.drop(CollectionsKt.asSequence(expression.getArguments()), 1));
        } else {
            pair = new Pair(new JsStringLiteral(""), CollectionsKt.asSequence(expression.getArguments()));
        }
        JsBinaryOperation jsBinaryOperation = (JsExpression) pair.component1();
        Iterator it = ((Sequence) pair.component2()).iterator();
        while (it.hasNext()) {
            jsBinaryOperation = new JsBinaryOperation(JsBinaryOperator.ADD, jsBinaryOperation, (JsExpression) ((IrExpression) it.next()).accept(this, context));
        }
        return jsBinaryOperation;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public JsExpression visitGetField2(IrGetField expression, JsGenerationContext context) {
        JsExpression jsExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrField owner = expression.getSymbol().getOwner();
        IrDeclarationParent parent = owner.getParent();
        boolean z = parent instanceof IrClass;
        if (z && IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner)) {
            IrUtilsKt.isEnumClass((IrClass) parent);
            IrExpression receiver = expression.getReceiver();
            if (receiver == null || (jsExpression = (JsExpression) receiver.accept(this, context)) == null) {
                CompilationExceptionKt.compilationException("Expect expression.receiver to not be null", expression);
                throw null;
            }
            return (JsExpression) JsAstUtilsKt.withSource$default(new JsNameRef(AnnotationUtilsKt.getJsNameOrKotlinName(owner).getIdentifier(), jsExpression), expression, context, null, null, 12, null);
        }
        if (z && isClassInlineLike(context, (IrClass) parent)) {
            IrExpression receiver2 = expression.getReceiver();
            Intrinsics.checkNotNull(receiver2);
            return (JsExpression) JsAstUtilsKt.withSource$default((JsNode) receiver2.accept(this, context), expression, context, null, null, 12, null);
        }
        JsName nameForField = context.getNameForField(owner);
        IrExpression receiver3 = expression.getReceiver();
        JsExpression jsExpression2 = (JsExpression) JsAstUtilsKt.withSource$default(JsAstUtilsKt.jsElementAccess(nameForField, receiver3 != null ? (JsExpression) receiver3.accept(this, context) : null), expression, context, null, null, 12, null);
        if (Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE) && Intrinsics.areEqual(expression.getOrigin(), JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE)) {
            JsExpression jsExpression3 = jsExpression2;
            MetadataProperties.setSynthetic(jsExpression3, true);
            MetadataProperties.setSideEffects(jsExpression3, SideEffectKind.PURE);
        }
        return jsExpression2;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitGetValue2(IrGetValue expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrValueDeclaration owner = expression.getSymbol().getOwner();
        if (isThisReceiver(owner)) {
            return (JsExpression) JsAstUtilsKt.withSource$default(new JsThisRef(), expression, context, null, null, 12, null);
        }
        JsNode jsNodeWithSource$default = JsAstUtilsKt.withSource$default(context.getNameForValueDeclaration(owner).makeRef(), expression, context, null, null, 12, null);
        Intrinsics.checkNotNull(jsNodeWithSource$default);
        return (JsExpression) jsNodeWithSource$default;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitGetObjectValue2(IrGetObjectValue expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrClass owner = expression.getSymbol().getOwner();
        owner.getKind();
        ClassKind classKind = ClassKind.OBJECT;
        IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner);
        return (JsExpression) JsAstUtilsKt.withSource$default(context.getRefForExternalClass(owner), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSetField2(IrSetField expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        String ident = context.getNameForField(expression.getSymbol().getOwner()).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        IrExpression receiver = expression.getReceiver();
        return (JsExpression) JsAstUtilsKt.withSource$default(JsAstUtilsKt.jsAssignment(JsAstUtilsKt.jsElementAccess(ident, receiver != null ? (JsExpression) receiver.accept(this, context) : null), (JsExpression) expression.getValue().accept(this, context)), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public JsExpression visitSetValue2(IrSetValue expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return (JsExpression) JsAstUtilsKt.withSource$default(new JsBinaryOperation(JsBinaryOperator.ASG, new JsNameRef(context.getNameForValueDeclaration(expression.getSymbol().getOwner())), (JsExpression) expression.getValue().accept(this, context)), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, JsGenerationContext context) {
        JsExpression jsExpressionMakeRef;
        JsInvocation jsInvocation;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        JsNameRef jsNameRef = new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), IrTypeUtilsKt.getConstructorRef(expression.getSymbol().getOwner(), context.getStaticContext()));
        if (context.getCurrentFunction() instanceof IrConstructor) {
            jsExpressionMakeRef = new JsThisRef();
        } else {
            IrFunction currentFunction = context.getCurrentFunction();
            Intrinsics.checkNotNull(currentFunction);
            jsExpressionMakeRef = context.getNameForValueDeclaration((IrDeclarationWithName) CollectionsKt.last((List) currentFunction.getValueParameters())).makeRef();
            Intrinsics.checkNotNullExpressionValue(jsExpressionMakeRef, "makeRef(...)");
        }
        JsExpression jsExpression = jsExpressionMakeRef;
        List listTranslateCallArguments$default = JsAstUtilsKt.translateCallArguments$default(expression, context, this, false, 8, null);
        IrConstructor owner = expression.getSymbol().getOwner();
        if (isClassInlineLike(context, IrUtilsKt.getParentAsClass(owner))) {
            owner.getIsPrimary();
            return (JsExpression) JsAstUtilsKt.withSource$default(new JsBinaryOperation(JsBinaryOperator.ASG, jsExpression, (JsExpression) CollectionsKt.single(listTranslateCallArguments$default)), expression, context, null, null, 12, null);
        }
        if (context.getStaticContext().getBackendContext().getEs6mode()) {
            jsInvocation = new JsInvocation(new JsSuperRef(), (List<? extends JsExpression>) listTranslateCallArguments$default);
        } else {
            jsInvocation = new JsInvocation(jsNameRef, (List<? extends JsExpression>) MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(CollectionsKt.listOf(jsExpression), listTranslateCallArguments$default));
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(jsInvocation, expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsExpression visitConstructorCall2(IrConstructorCall expression, JsGenerationContext context) {
        JsNew jsNew;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrConstructor owner = expression.getSymbol().getOwner();
        List listTranslateCallArguments$default = JsAstUtilsKt.translateCallArguments$default(expression, context, this, false, 8, null);
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner);
        if (isClassInlineLike(context, parentAsClass)) {
            throw new IllegalArgumentException("All inline class constructor calls must be lowered to static function calls".toString());
        }
        if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) parentAsClass)) {
            JsExpression classRef = IrTypeUtilsKt.getClassRef(parentAsClass, context.getStaticContext());
            int iVarargParameterIndex = JsAstUtilsKt.varargParameterIndex(expression.getSymbol().getOwner());
            if (iVarargParameterIndex == -1) {
                jsNew = new JsNew(classRef, listTranslateCallArguments$default);
            } else {
                jsNew = new JsNew(new JsInvocation(new JsNameRef("apply", new JsNameRef("bind", new JsNameRef("Function"))), classRef, JsAstUtilsKt.argumentsWithVarargAsSingleArray(expression, context, new JsNullLiteral(), listTranslateCallArguments$default, iVarargParameterIndex)), CollectionsKt.emptyList());
            }
        } else {
            jsNew = new JsNew(IrTypeUtilsKt.getClassRef(parentAsClass, context.getStaticContext()), listTranslateCallArguments$default);
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(jsNew, expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public JsExpression visitCall2(IrCall expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.checkIfJsCode(expression.getSymbol()) || context.checkIfHasAssociatedJsCode(expression.getSymbol())) {
            return new JsCallTransformer(expression, context).generateExpression();
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(JsAstUtilsKt.translateCall(expression, context, this), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public JsExpression visitWhen2(IrWhen expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        IrBranch irBranch = (IrBranch) CollectionsKt.lastOrNull((List) expression.getBranches());
        JsPrefixOperation jsPrefixOperation = (irBranch == null || !IrUtilsKt.isElseBranch(irBranch)) ? new JsPrefixOperation(JsUnaryOperator.VOID, new JsIntLiteral(0)) : null;
        if (jsPrefixOperation != null) {
            IrTypePredicatesKt.isUnit(expression.getType());
        }
        JsNode jsNode = JsAstUtilsKt.toJsNode(expression, this, context, AnonymousClass2.INSTANCE, jsPrefixOperation);
        Intrinsics.checkNotNull(jsNode);
        return (JsExpression) jsNode;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsExpressionTransformer$visitWhen$2, reason: invalid class name */
    /* JADX INFO: compiled from: IrElementToJsExpressionTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function3<JsExpression, JsExpression, JsExpression, JsConditional> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JsConditional.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final JsConditional invoke(JsExpression jsExpression, JsExpression jsExpression2, JsExpression jsExpression3) {
            return new JsConditional(jsExpression, jsExpression2, jsExpression3);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public JsExpression visitTypeOperator2(IrTypeOperatorCall expression, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (WhenMappings.$EnumSwitchMapping$0[expression.getOperator().ordinal()] != 1) {
            CompilationExceptionKt.compilationException("All type operator calls except REINTERPRET_CAST should be lowered at this point", expression);
            throw null;
        }
        return (JsExpression) JsAstUtilsKt.withSource$default((JsExpression) expression.getArgument().accept(this, data), expression, data, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDynamicMemberExpression2(IrDynamicMemberExpression expression, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return (JsExpression) JsAstUtilsKt.withSource$default(JsAstUtilsKt.jsElementAccess(expression.getMemberName(), (JsExpression) expression.getReceiver().accept(this, data)), expression, data, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public JsExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression expression, JsGenerationContext data) {
        JsPrefixOperation jsPrefixOperationPrefixOperation;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        switch (WhenMappings.$EnumSwitchMapping$1[expression.getOperator().ordinal()]) {
            case 1:
                jsPrefixOperationPrefixOperation = prefixOperation(JsUnaryOperator.POS, expression, data);
                break;
            case 2:
                jsPrefixOperationPrefixOperation = prefixOperation(JsUnaryOperator.NEG, expression, data);
                break;
            case 3:
                jsPrefixOperationPrefixOperation = prefixOperation(JsUnaryOperator.NOT, expression, data);
                break;
            case 4:
                jsPrefixOperationPrefixOperation = prefixOperation(JsUnaryOperator.INC, expression, data);
                break;
            case 5:
                jsPrefixOperationPrefixOperation = prefixOperation(JsUnaryOperator.DEC, expression, data);
                break;
            case 6:
                jsPrefixOperationPrefixOperation = postfixOperation(JsUnaryOperator.INC, expression, data);
                break;
            case 7:
                jsPrefixOperationPrefixOperation = postfixOperation(JsUnaryOperator.DEC, expression, data);
                break;
            case 8:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ADD, expression, data);
                break;
            case 9:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.SUB, expression, data);
                break;
            case 10:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.MUL, expression, data);
                break;
            case 11:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.DIV, expression, data);
                break;
            case 12:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.MOD, expression, data);
                break;
            case 13:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.GT, expression, data);
                break;
            case 14:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.LT, expression, data);
                break;
            case 15:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.GTE, expression, data);
                break;
            case 16:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.LTE, expression, data);
                break;
            case 17:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.EQ, expression, data);
                break;
            case 18:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.NEQ, expression, data);
                break;
            case 19:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.REF_EQ, expression, data);
                break;
            case 20:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.REF_NEQ, expression, data);
                break;
            case 21:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.AND, expression, data);
                break;
            case 22:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.OR, expression, data);
                break;
            case 23:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG, expression, data);
                break;
            case 24:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG_ADD, expression, data);
                break;
            case 25:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG_SUB, expression, data);
                break;
            case 26:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG_MUL, expression, data);
                break;
            case 27:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG_DIV, expression, data);
                break;
            case 28:
                jsPrefixOperationPrefixOperation = binaryOperation(JsBinaryOperator.ASG_MOD, expression, data);
                break;
            case 29:
                IrElementToJsExpressionTransformer irElementToJsExpressionTransformer = this;
                jsPrefixOperationPrefixOperation = new JsArrayAccess((JsExpression) IrExpressionsKt.getLeft(expression).accept(irElementToJsExpressionTransformer, data), (JsExpression) IrExpressionsKt.getRight(expression).accept(irElementToJsExpressionTransformer, data));
                break;
            case 30:
                IrElementToJsExpressionTransformer irElementToJsExpressionTransformer2 = this;
                JsExpression jsExpression = (JsExpression) expression.getReceiver().accept(irElementToJsExpressionTransformer2, data);
                List<IrExpression> arguments = expression.getArguments();
                ArrayList arrayList = new ArrayList(arguments.size());
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    arrayList.add((JsExpression) ((IrExpression) it.next()).accept(irElementToJsExpressionTransformer2, data));
                }
                jsPrefixOperationPrefixOperation = new JsInvocation(jsExpression, (List<? extends JsExpression>) org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
                break;
            default:
                CompilationExceptionKt.compilationException("Unexpected operator " + expression.getOperator(), expression);
                throw null;
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(jsPrefixOperationPrefixOperation, expression, data, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsExpression visitRawFunctionReference2(IrRawFunctionReference expression, JsGenerationContext data) {
        JsNameRef constructorRef;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IrFunction owner = expression.getSymbol().getOwner();
        if (owner instanceof IrConstructor) {
            constructorRef = IrTypeUtilsKt.getConstructorRef((IrConstructor) owner, data.getStaticContext());
        } else {
            if (!(owner instanceof IrSimpleFunction)) {
                CompilationExceptionKt.compilationException("Unexpected function kind", expression);
                throw null;
            }
            JsNameRef jsNameRefMakeRef = data.getNameForStaticFunction((IrSimpleFunction) owner).makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            constructorRef = jsNameRefMakeRef;
        }
        return (JsExpression) JsAstUtilsKt.withSource$default(constructorRef, expression, data, null, null, 12, null);
    }

    private final JsPrefixOperation prefixOperation(JsUnaryOperator operator, IrDynamicOperatorExpression expression, JsGenerationContext data) {
        return new JsPrefixOperation(operator, (JsExpression) expression.getReceiver().accept(this, data));
    }

    private final JsPostfixOperation postfixOperation(JsUnaryOperator operator, IrDynamicOperatorExpression expression, JsGenerationContext data) {
        return new JsPostfixOperation(operator, (JsExpression) expression.getReceiver().accept(this, data));
    }

    private final JsBinaryOperation binaryOperation(JsBinaryOperator operator, IrDynamicOperatorExpression expression, JsGenerationContext data) {
        IrElementToJsExpressionTransformer irElementToJsExpressionTransformer = this;
        return new JsBinaryOperation(operator, (JsExpression) IrExpressionsKt.getLeft(expression).accept(irElementToJsExpressionTransformer, data), (JsExpression) IrExpressionsKt.getRight(expression).accept(irElementToJsExpressionTransformer, data));
    }

    private final boolean isThisReceiver(IrValueDeclaration irValueDeclaration) {
        if (!(irValueDeclaration instanceof IrVariable)) {
            IrDeclarationParent parent = irValueDeclaration.getParent();
            if (!(parent instanceof IrSimpleFunction) ? !(!(parent instanceof IrClass) || irValueDeclaration != ((IrClass) parent).getThisReceiver()) : irValueDeclaration == ((IrSimpleFunction) parent).getDispatchReceiverParameter()) {
                return true;
            }
        }
        return false;
    }
}
