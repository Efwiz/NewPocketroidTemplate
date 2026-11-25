package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JsIntrinsicTransformers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010\u0014\u001a+\u0012\u0004\u0012\u00020\r\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u0012\u0018\u00010\fj\u0004\u0018\u0001`\u00132\u0006\u0010\u0015\u001a\u00020\u000bH\u0086\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR?\u0010\t\u001a3\u0012\u0004\u0012\u00020\u000b\u0012)\u0012'\u0012\u0004\u0012\u00020\r\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00120\fj\u0002`\u00130\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIntrinsicTransformers;", "", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "icUtils", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "getIcUtils", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "transformers", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "Lkotlin/ParameterName;", "name", "context", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrCallTransformer;", "get", NativeSymbol.TYPE_NAME, "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIntrinsicTransformers {
    private final JsInlineClassesUtils icUtils;
    private final Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> transformers;

    public JsIntrinsicTransformers(final JsIrBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.icUtils = backendContext.getInlineClassesUtils();
        final JsIntrinsics intrinsics = backendContext.getIntrinsics();
        HashMap map = new HashMap();
        this.transformers = map;
        HashMap map2 = map;
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsEqeqeq(), JsBinaryOperator.REF_EQ);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsNotEqeq(), JsBinaryOperator.REF_NEQ);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsEqeq(), JsBinaryOperator.EQ);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsNotEq(), JsBinaryOperator.NEQ);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsGt(), JsBinaryOperator.GT);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsGtEq(), JsBinaryOperator.GTE);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsLt(), JsBinaryOperator.LT);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsLtEq(), JsBinaryOperator.LTE);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsNot(), JsUnaryOperator.NOT);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsAnd(), JsBinaryOperator.AND);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsOr(), JsBinaryOperator.OR);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsUnaryPlus(), JsUnaryOperator.POS);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsUnaryMinus(), JsUnaryOperator.NEG);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsPrefixInc(), JsUnaryOperator.INC);
        JsIntrinsicTransformersKt.postfixOp(map2, intrinsics.getJsPostfixInc(), JsUnaryOperator.INC);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsPrefixDec(), JsUnaryOperator.DEC);
        JsIntrinsicTransformersKt.postfixOp(map2, intrinsics.getJsPostfixDec(), JsUnaryOperator.DEC);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsDelete(), JsUnaryOperator.DELETE);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsPlus(), JsBinaryOperator.ADD);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsMinus(), JsBinaryOperator.SUB);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsMult(), JsBinaryOperator.MUL);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsDiv(), JsBinaryOperator.DIV);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsMod(), JsBinaryOperator.MOD);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsPlusAssign(), JsBinaryOperator.ASG_ADD);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsMinusAssign(), JsBinaryOperator.ASG_SUB);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsMultAssign(), JsBinaryOperator.ASG_MUL);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsDivAssign(), JsBinaryOperator.ASG_DIV);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsModAssign(), JsBinaryOperator.ASG_MOD);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitAnd(), JsBinaryOperator.BIT_AND);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitOr(), JsBinaryOperator.BIT_OR);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitXor(), JsBinaryOperator.BIT_XOR);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsBitNot(), JsUnaryOperator.BIT_NOT);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitShiftR(), JsBinaryOperator.SHR);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitShiftRU(), JsBinaryOperator.SHRU);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsBitShiftL(), JsBinaryOperator.SHL);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsInstanceOf(), JsBinaryOperator.INSTANCEOF);
        JsIntrinsicTransformersKt.binOp(map2, intrinsics.getJsIn(), JsBinaryOperator.INOP);
        JsIntrinsicTransformersKt.prefixOp(map2, intrinsics.getJsTypeOf(), JsUnaryOperator.TYPEOF);
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsIsEs6(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                return new JsBooleanLiteral(backendContext.getEs6mode());
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsObjectCreateSymbol(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$2
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                IrType typeArgument = call.getTypeArgument(0);
                Intrinsics.checkNotNull(typeArgument);
                IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(typeArgument).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return JsAstUtilsKt.objectCreate(JsAstUtilsKt.prototypeOf(IrTypeUtilsKt.getClassRef((IrClass) owner, context.getStaticContext()), context.getStaticContext()), context.getStaticContext());
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsClass(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$3
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                JsExpression classRef;
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                IrType typeArgument = call.getTypeArgument(0);
                if (typeArgument != null && (classRef = IrTypeUtilsKt.getClassRef(typeArgument, context.getStaticContext())) != null) {
                    return classRef;
                }
                CompilationExceptionKt.compilationException("Type argument of jsClass must be statically known class", typeArgument);
                throw null;
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsNewTarget(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$4
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                return new JsNameRef(new JsName("target", false), new JsNameRef(new JsName(PsiKeyword.NEW, false)));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsOpenInitializerBox(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$5
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsInvocation(new JsNameRef("Object.assign"), (List<? extends JsExpression>) JsIntrinsicTransformersKt.translateCallArguments(call, context));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsEmptyObject(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$6
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                return new JsObjectLiteral();
            }
        });
        JsIntrinsicTransformersKt.addIfNotNull(map2, intrinsics.getJsCode(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$7
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                CompilationExceptionKt.compilationException("Should not be called", call);
                throw null;
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsArrayLength(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$8
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsNameRef("length", (JsExpression) JsIntrinsicTransformersKt.translateCallArguments(call, context).get(0));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsArrayGet(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$9
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                List listTranslateCallArguments = JsIntrinsicTransformersKt.translateCallArguments(call, context);
                return new JsArrayAccess((JsExpression) listTranslateCallArguments.get(0), (JsExpression) listTranslateCallArguments.get(1));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsArraySet(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$10
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                List listTranslateCallArguments = JsIntrinsicTransformersKt.translateCallArguments(call, context);
                JsExpression jsExpression = (JsExpression) listTranslateCallArguments.get(0);
                JsExpression jsExpression2 = (JsExpression) listTranslateCallArguments.get(1);
                return new JsBinaryOperation(JsBinaryOperator.ASG, new JsArrayAccess(jsExpression, jsExpression2), (JsExpression) listTranslateCallArguments.get(2));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getArrayLiteral(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$11
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context));
            }
        });
        IrSimpleFunctionSymbol[] irSimpleFunctionSymbolArr = {intrinsics.getJsArrayLike2Array(), intrinsics.getJsSliceArrayLikeFromIndex(), intrinsics.getJsSliceArrayLikeFromIndexToIndex()};
        for (int i = 0; i < 3; i++) {
            JsIntrinsicTransformersKt.add(map2, irSimpleFunctionSymbolArr[i], new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$12
                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    Intrinsics.checkNotNullParameter(context, "context");
                    return new JsInvocation(new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), new JsNameRef(Namer.INSTANCE.getSLICE_FUNCTION(), new JsArrayLiteral())), (List<? extends JsExpression>) JsIntrinsicTransformersKt.translateCallArguments(call, context));
                }
            });
        }
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsArraySlice(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$13
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsInvocation(new JsNameRef(Namer.INSTANCE.getSLICE_FUNCTION(), (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context))), new JsExpression[0]);
            }
        });
        for (Map.Entry<PrimitiveType, String> entry : intrinsics.getPrimitiveToTypedArrayMap().entrySet()) {
            PrimitiveType key = entry.getKey();
            final String value = entry.getValue();
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = intrinsics.getPrimitiveToSizeConstructor().get(key);
            Intrinsics.checkNotNull(irSimpleFunctionSymbol);
            JsIntrinsicTransformersKt.add(map2, irSimpleFunctionSymbol, new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$14
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    Intrinsics.checkNotNullParameter(context, "context");
                    return new JsNew(new JsNameRef(value + "Array"), JsIntrinsicTransformersKt.translateCallArguments(call, context));
                }
            });
            IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = intrinsics.getPrimitiveToLiteralConstructor().get(key);
            Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
            JsIntrinsicTransformersKt.add(map2, irSimpleFunctionSymbol2, new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$15
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    Intrinsics.checkNotNullParameter(context, "context");
                    return new JsNew(new JsNameRef(value + "Array"), JsIntrinsicTransformersKt.translateCallArguments(call, context));
                }
            });
        }
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsBoxIntrinsic(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$16
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                JsExpression jsExpression = (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context));
                JsInlineClassesUtils icUtils = this.this$0.getIcUtils();
                boolean z = false;
                IrType typeArgument = call.getTypeArgument(0);
                Intrinsics.checkNotNull(typeArgument);
                IrClass inlinedClass = icUtils.getInlinedClass(typeArgument);
                Intrinsics.checkNotNull(inlinedClass);
                List<IrDeclaration> declarations = inlinedClass.getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrConstructor) {
                        arrayList.add(obj);
                    }
                }
                Object obj2 = null;
                for (Object obj3 : arrayList) {
                    if (((IrConstructor) obj3).getIsPrimary()) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        obj2 = obj3;
                        z = true;
                    }
                }
                if (z) {
                    JsNew jsNew = new JsNew(IrTypeUtilsKt.getConstructorRef((IrConstructor) obj2, context.getStaticContext()), CollectionsKt.listOf(jsExpression));
                    MetadataProperties.setInlineClassBoxing(jsNew, true);
                    return jsNew;
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsUnboxIntrinsic(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$17
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                JsExpression jsExpression = (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context));
                JsInlineClassesUtils icUtils = this.this$0.getIcUtils();
                IrType typeArgument = call.getTypeArgument(1);
                Intrinsics.checkNotNull(typeArgument);
                IrClass inlinedClass = icUtils.getInlinedClass(typeArgument);
                Intrinsics.checkNotNull(inlinedClass);
                JsNameRef jsNameRef = new JsNameRef(context.getNameForField(InlineClassesKt.getInlineClassBackingField(inlinedClass)), jsExpression);
                MetadataProperties.setInlineClassUnboxing(jsNameRef, true);
                return jsNameRef;
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsCall(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$18
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                List listTranslateCallArguments = JsIntrinsicTransformersKt.translateCallArguments(call, context);
                JsExpression jsExpression = (JsExpression) listTranslateCallArguments.get(0);
                JsExpression jsExpression2 = (JsExpression) listTranslateCallArguments.get(1);
                Object obj = listTranslateCallArguments.get(2);
                JsArrayLiteral jsArrayLiteral = obj instanceof JsArrayLiteral ? (JsArrayLiteral) obj : null;
                if (jsArrayLiteral == null) {
                    throw new IllegalStateException("Expect to have JsArrayLiteral, because of vararg with dynamic element type".toString());
                }
                JsNameRef jsNameRef = new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), jsExpression2);
                SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                spreadBuilder.add(jsExpression);
                List<JsExpression> expressions = jsArrayLiteral.getExpressions();
                Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
                spreadBuilder.addSpread(expressions.toArray(new JsExpression[0]));
                return new JsInvocation(jsNameRef, (JsExpression[]) spreadBuilder.toArray(new JsExpression[spreadBuilder.size()]));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsBind(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$19
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                JsNameRef jsNameRef;
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                IrExpression valueArgument = call.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                JsExpression jsExpression = (JsExpression) valueArgument.accept(new IrElementToJsExpressionTransformer(), context);
                IrExpression valueArgument2 = call.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument2);
                if (valueArgument2 instanceof IrFunctionReference) {
                    IrClassSymbol superQualifierSymbol = call.getSuperQualifierSymbol();
                    Intrinsics.checkNotNull(superQualifierSymbol);
                    IrFunction owner = ((IrFunctionReference) valueArgument2).getSymbol().getOwner();
                    Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                    jsNameRef = new JsNameRef(context.getNameForMemberFunction((IrSimpleFunction) owner), JsAstUtilsKt.prototypeOf(IrTypeUtilsKt.getClassRef(superQualifierSymbol.getOwner(), context.getStaticContext()), context.getStaticContext()));
                } else {
                    if (!(valueArgument2 instanceof IrFunctionExpression)) {
                        CompilationExceptionKt.compilationException("The 'target' argument of 'jsBind' must be either IrFunctionReference or IrFunctionExpression", call);
                        throw null;
                    }
                    jsNameRef = (JsExpression) valueArgument2.accept(new IrElementToJsExpressionTransformer(), context);
                }
                return new JsInvocation(new JsNameRef(Namer.INSTANCE.getBIND_FUNCTION(), jsNameRef), jsExpression);
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsContexfulRef(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$20
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                IrExpression valueArgument = call.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                JsExpression jsExpression = (JsExpression) valueArgument.accept(new IrElementToJsExpressionTransformer(), context);
                IrExpression valueArgument2 = call.getValueArgument(1);
                Intrinsics.checkNotNull(valueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference");
                IrFunction owner = ((IrRawFunctionReference) valueArgument2).getSymbol().getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                return new JsNameRef(context.getNameForMemberFunction((IrSimpleFunction) owner), jsExpression);
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getUnreachable(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$21
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                return new JsInvocation(new JsNameRef(Namer.INSTANCE.getUNREACHABLE_NAME()), new JsExpression[0]);
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getCreateSharedBox(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$22
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsObjectLiteral((List<JsPropertyInitializer>) CollectionsKt.listOf(new JsPropertyInitializer(new JsNameRef(Namer.SHARED_BOX_V), (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context)))));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getReadSharedBox(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$23
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsNameRef(Namer.SHARED_BOX_V, (JsExpression) CollectionsKt.single(JsIntrinsicTransformersKt.translateCallArguments(call, context)));
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getWriteSharedBox(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$24
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                List listTranslateCallArguments = JsIntrinsicTransformersKt.translateCallArguments(call, context);
                JsExpression jsExpression = (JsExpression) listTranslateCallArguments.get(0);
                return JsAstUtilsKt.jsAssignment(new JsNameRef(Namer.SHARED_BOX_V, jsExpression), (JsExpression) listTranslateCallArguments.get(1));
            }
        });
        JsIntrinsicTransformers$1$suspendInvokeTransform$1 jsIntrinsicTransformers$1$suspendInvokeTransform$1 = new Function2<IrCall, JsGenerationContext, JsInvocation>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$suspendInvokeTransform$1
            @Override // kotlin.jvm.functions.Function2
            public final JsInvocation invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                JsName nameForMemberFunction = context.getNameForMemberFunction(MiscKt.invokeFunForLambda(call));
                IrExpression extensionReceiver = call.getExtensionReceiver();
                JsExpression jsExpression = extensionReceiver != null ? (JsExpression) extensionReceiver.accept(new IrElementToJsExpressionTransformer(), context) : null;
                Intrinsics.checkNotNull(jsExpression);
                return new JsInvocation(new JsNameRef(nameForMemberFunction, jsExpression), (List<? extends JsExpression>) JsIntrinsicTransformersKt.translateCallArguments(call, context));
            }
        };
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsInvokeSuspendSuperType(), jsIntrinsicTransformers$1$suspendInvokeTransform$1);
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsInvokeSuspendSuperTypeWithReceiver(), jsIntrinsicTransformers$1$suspendInvokeTransform$1);
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsInvokeSuspendSuperTypeWithReceiverAndParam(), jsIntrinsicTransformers$1$suspendInvokeTransform$1);
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsArguments(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$25
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext jsGenerationContext) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(jsGenerationContext, "<anonymous parameter 1>");
                return Namer.INSTANCE.getARGUMENTS();
            }
        });
        JsIntrinsicTransformersKt.add(map2, intrinsics.getJsNewAnonymousClass(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$26
            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                Object objSingle = CollectionsKt.single((List<? extends Object>) JsIntrinsicTransformersKt.translateCallArguments(call, context));
                Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
                return new JsClass(null, (JsNameRef) objSingle, null, null, 13, null);
            }
        });
        IrSimpleFunction getter = intrinsics.getVoid().getOwner().getGetter();
        Intrinsics.checkNotNull(getter);
        JsIntrinsicTransformersKt.add(map2, getter.getSymbol(), new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers$1$27
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall irCall, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(irCall, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(context, "context");
                IrField backingField = intrinsics.getVoid().getOwner().getBackingField();
                Intrinsics.checkNotNull(backingField);
                return new JsNameRef(context.getNameForField(backingField));
            }
        });
    }

    public final JsInlineClassesUtils getIcUtils() {
        return this.icUtils;
    }

    public final Function2<IrCall, JsGenerationContext, JsExpression> get(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.transformers.get(symbol);
    }
}
