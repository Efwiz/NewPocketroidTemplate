package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.lower.BOUND_VALUE_PARAMETER;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo;
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfoKt;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6AddBoxParameterLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsStaticContext;
import org.jetbrains.kotlin.ir.backend.js.utils.LocalNameGenerator;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithEmbeddedSource;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.config.SourceMapNamesPolicy;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: jsAstUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b2\u0006\u0010\f\u001a\u00020\r\u001a2\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0014\u001a\u001a\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004\u001a\u0018\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0004\u001a\u0018\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u001f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0004\u001a\u000e\u0010 \u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0014\u001a\u0016\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0014\u001a\u0016\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0014\u001a\u001e\u0010&\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010'\u001a\u00020(\u001a4\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010'\u001a\u00020(2\b\b\u0002\u0010,\u001a\u00020\u0016\u001a \u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\b\u0010\u0010\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0007\u001a\u00020\b\u001a@\u00101\u001a\u000202\"\b\b\u0000\u00103*\u000204*\u0002H32\u0006\u00105\u001a\u0002062\u0006\u0010\u0007\u001a\u00020\b2\b\u00107\u001a\u0004\u0018\u0001082\b\u00109\u001a\u0004\u0018\u00010:H\u0082\b¢\u0006\u0002\u0010;\u001a\n\u0010<\u001a\u00020=*\u00020>\u001a\u001e\u0010?\u001a\u00020\u0016*\u0004\u0018\u00010@2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010A\u001a\u00020@H\u0002\u001a\u0018\u0010B\u001a\u0004\u0018\u00010C*\u0004\u0018\u00010C2\u0006\u0010D\u001a\u00020\u0016H\u0002\u001a3\u0010E\u001a\u0004\u0018\u00010F*\u0002062\u0006\u0010G\u001a\u00020H2\u0017\u0010I\u001a\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\r0J¢\u0006\u0002\bKH\u0086\bø\u0001\u0000\u001a\u0014\u0010L\u001a\u0004\u0018\u00010F*\u0002062\u0006\u0010G\u001a\u00020H\u001a\u0014\u0010L\u001a\u0004\u0018\u00010F*\u0002062\u0006\u00109\u001a\u00020:\u001a\u0014\u0010M\u001a\u00020\u0016*\u00020C2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0016\u0010N\u001a\u0004\u0018\u00010\u0011*\u0002082\u0006\u0010O\u001a\u00020PH\u0002\u001a\u001c\u0010Q\u001a\u00020\"*\u00020\"2\u0006\u0010R\u001a\u0002002\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0012\u0010S\u001a\u00020T*\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u001f\u001ae\u0010U\u001a\u0004\u0018\u0001H3\"\b\b\u0000\u00103*\u000204*\u00020V2\u0012\u0010W\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u00020\b0X2\u0006\u0010\u0007\u001a\u00020\b2 \u00105\u001a\u001c\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H3\u0012\u0006\u0012\u0004\u0018\u0001H3\u0012\u0004\u0012\u0002H30Y2\n\b\u0002\u0010Z\u001a\u0004\u0018\u0001H3¢\u0006\u0002\u0010[\u001a\u0010\u0010D\u001a\u00020\u0016*\u0006\u0012\u0002\b\u00030*H\u0002\u001a\n\u0010\f\u001a\u00020\r*\u000200\u001a\u0014\u0010\\\u001a\u00020]*\u00020F2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001aC\u0010^\u001a\u0002H3\"\b\b\u0000\u00103*\u000204*\u0002H32\u0006\u00105\u001a\u0002062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u00107\u001a\u0004\u0018\u0001082\n\b\u0002\u00109\u001a\u0004\u0018\u00010:H\u0000¢\u0006\u0002\u0010_\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006`²\u0006\n\u0010 \u001a\u00020\u0004X\u008a\u0084\u0002"}, d2 = {"nameMappingOriginAllowList", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "argumentsWithVarargAsSingleArray", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "additionalReceiver", "arguments", "", "varargParameterIndex", "", "defineProperty", "obj", "name", "", "getter", "setter", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "isFunctionTypeInvoke", "", "receiver", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "jsAssignment", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "left", "right", "jsElementAccess", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "jsUndefined", "objectCreate", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "prototype", "prototypeOf", "classNameRef", "translateCall", "transformer", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrElementToJsExpressionTransformer;", "translateCallArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "allowDropTailVoids", "translateFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "addSourceInfoIfNeed", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "Lorg/jetbrains/kotlin/ir/IrElement;", "useNameOf", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "asBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "canUseSuperRef", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "superClass", "checkOnNullability", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "validWithNullArgs", "getSourceLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "offsetSelector", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "getStartSourceLocation", "isVoidGetter", "originalNameForUseInSourceMap", "policy", "Lorg/jetbrains/kotlin/js/config/SourceMapNamesPolicy;", "pureIfPossible", "function", "putIntoVariableWitName", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "toJsNode", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", LanguageCodeUtil.TR, "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lkotlin/Function3;", "implicitElse", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;Lkotlin/jvm/functions/Function3;Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "withEmbeddedSource", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithEmbeddedSource;", "withSource", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsAstUtilsKt {
    private static final Set<IrDeclarationOriginImpl> nameMappingOriginAllowList = SetsKt.setOf((Object[]) new IrDeclarationOriginImpl[]{IrDeclarationOrigin.DEFINED.INSTANCE, IrDeclarationOrigin.FOR_LOOP_VARIABLE.INSTANCE, IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE, IrDeclarationOrigin.CONTINUATION.INSTANCE, BOUND_VALUE_PARAMETER.INSTANCE, JsLoweredDeclarationOrigin.JS_SHADOWED_DEFAULT_PARAMETER.INSTANCE});

    /* JADX INFO: compiled from: jsAstUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SourceMapSourceEmbedding.values().length];
            try {
                iArr[SourceMapSourceEmbedding.NEVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SourceMapSourceEmbedding.INLINING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SourceMapSourceEmbedding.ALWAYS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final IrExpression checkOnNullability(IrExpression irExpression, boolean z) {
        return irExpression;
    }

    public static final JsExpression jsUndefined(JsStaticContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        IrExpression irExpression = IrJsUtilsKt.getVoid(context.getBackendContext());
        if (!(irExpression instanceof IrGetField)) {
            return new JsNullLiteral();
        }
        JsNameRef jsNameRefMakeRef = context.getNameForField(((IrGetField) irExpression).getSymbol().getOwner()).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        return jsNameRefMakeRef;
    }

    public static /* synthetic */ JsNode toJsNode$default(IrWhen irWhen, BaseIrElementToJsNodeTransformer baseIrElementToJsNodeTransformer, JsGenerationContext jsGenerationContext, Function3 function3, JsNode jsNode, int i, Object obj) {
        if ((i & 8) != 0) {
            jsNode = null;
        }
        return toJsNode(irWhen, baseIrElementToJsNodeTransformer, jsGenerationContext, function3, jsNode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [T extends org.jetbrains.kotlin.js.backend.ast.JsNode] */
    /* JADX WARN: Type inference failed for: r12v1, types: [T extends org.jetbrains.kotlin.js.backend.ast.JsNode] */
    /* JADX WARN: Type inference failed for: r12v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v6, types: [org.jetbrains.kotlin.js.backend.ast.JsNode] */
    public static final <T extends JsNode> T toJsNode(IrWhen irWhen, BaseIrElementToJsNodeTransformer<? extends T, ? super JsGenerationContext> tr, JsGenerationContext context, Function3<? super JsExpression, ? super T, ? super T, ? extends T> node, T t) {
        Intrinsics.checkNotNullParameter(irWhen, "<this>");
        Intrinsics.checkNotNullParameter(tr, "tr");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(node, "node");
        List<IrBranch> branches = irWhen.getBranches();
        if (!branches.isEmpty()) {
            ListIterator<IrBranch> listIterator = branches.listIterator(branches.size());
            while (listIterator.hasPrevious()) {
                IrBranch irBranchPrevious = listIterator.previous();
                JsNode jsNode = (JsNode) irBranchPrevious.getResult().accept(tr, context);
                t = IrUtilsKt.isElseBranch(irBranchPrevious) ? jsNode : withSource$default(node.invoke((JsExpression) irBranchPrevious.getCondition().accept(new IrElementToJsExpressionTransformer(), context), jsNode, (Object) t), irBranchPrevious, context, null, null, 12, null);
            }
        }
        return (T) t;
    }

    public static final JsExpression jsElementAccess(String name2, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return jsElementAccess(new JsName(name2, false), jsExpression);
    }

    public static final JsVars putIntoVariableWitName(JsExpression jsExpression, JsName name2) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return new JsVars(new JsVars.JsVar(name2, jsExpression));
    }

    public static final JsExpression jsElementAccess(JsName name2, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (jsExpression != null) {
            String ident = name2.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            if (!IdentifierPolicyKt.isValidES5Identifier(ident)) {
                return new JsArrayAccess(jsExpression, new JsStringLiteral(name2.getIdent()));
            }
        }
        return new JsNameRef(name2, jsExpression);
    }

    public static final JsBinaryOperation jsAssignment(JsExpression left, JsExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return new JsBinaryOperation(JsBinaryOperator.ASG, left, right);
    }

    public static final JsInvocation prototypeOf(JsExpression classNameRef, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(classNameRef, "classNameRef");
        Intrinsics.checkNotNullParameter(context, "context");
        return new JsInvocation(context.getNameForStaticFunction(context.getBackendContext().getIntrinsics().getJsPrototypeOfSymbol().getOwner()).makeRef(), classNameRef);
    }

    public static final JsInvocation objectCreate(JsExpression prototype, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(prototype, "prototype");
        Intrinsics.checkNotNullParameter(context, "context");
        return new JsInvocation(context.getNameForStaticFunction(context.getBackendContext().getIntrinsics().getJsObjectCreateSymbol().getOwner()).makeRef(), prototype);
    }

    public static final JsExpression defineProperty(JsExpression obj, String name2, JsExpression jsExpression, JsExpression jsExpression2, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        JsNameRef jsNameRefMakeRef = context.getNameForStaticFunction(context.getBackendContext().getIntrinsics().getJsDefinePropertySymbol().getOwner()).makeRef();
        JsExpression[] jsExpressionArr = new JsExpression[4];
        jsExpressionArr[0] = obj;
        jsExpressionArr[1] = new JsStringLiteral(name2);
        if (jsExpression == null) {
            jsExpression = jsExpression2 != null ? jsUndefined(context) : null;
        }
        jsExpressionArr[2] = jsExpression;
        jsExpressionArr[3] = jsExpression2;
        return new JsInvocation(jsNameRefMakeRef, (List<? extends JsExpression>) CollectionsKt.listOfNotNull((Object[]) jsExpressionArr));
    }

    public static final JsFunction translateFunction(IrFunction declaration, JsName jsName, JsGenerationContext context) {
        IrValueParameter thisReceiver;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        JsFunction jsCodeForFunction = context.getStaticContext().getBackendContext().getJsCodeForFunction(declaration.getSymbol());
        if (jsCodeForFunction != null) {
            jsCodeForFunction.setName(jsName);
            return jsCodeForFunction;
        }
        LocalNameGenerator localNames = context.getLocalNames();
        if (localNames == null) {
            localNames = new LocalNameGenerator(context.getStaticContext().getGlobalNameScope());
            LocalNameGenerator localNameGenerator = localNames;
            IrVisitorsKt.acceptChildrenVoid(declaration, localNameGenerator);
            IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(declaration);
            if (parentClassOrNull != null && (thisReceiver = parentClassOrNull.getThisReceiver()) != null) {
                IrVisitorsKt.acceptVoid(thisReceiver, localNameGenerator);
            }
        }
        JsGenerationContext jsGenerationContextNewDeclaration = context.newDeclaration(declaration, localNames);
        List<IrValueParameter> valueParameters = declaration.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(TuplesKt.to(irValueParameter, jsGenerationContextNewDeclaration.getNameForValueDeclaration(irValueParameter)));
        }
        ArrayList<Pair> arrayList2 = arrayList;
        IrBody body = declaration.getBody();
        JsStatement jsStatement = body != null ? (JsStatement) body.accept(new IrElementToJsStatementTransformer(), jsGenerationContextNewDeclaration) : null;
        JsBlock jsBlock = jsStatement instanceof JsBlock ? (JsBlock) jsStatement : null;
        if (jsBlock == null) {
            jsBlock = new JsBlock();
        }
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), jsBlock, "member function " + (jsName == null ? "annon" : jsName));
        if (ES6ConstructorLoweringKt.isEs6ConstructorReplacement(declaration)) {
            jsFunction.getModifiers().add(JsFunction.Modifier.STATIC);
        }
        JsFunction jsFunction2 = (JsFunction) withSource$default(jsFunction, declaration, context, declaration, null, 8, null);
        jsFunction2.setName(jsName);
        IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            translateFunction$addParameter(jsFunction2, jsGenerationContextNewDeclaration, jsGenerationContextNewDeclaration.getNameForValueDeclaration(extensionReceiverParameter), extensionReceiverParameter);
        }
        for (Pair pair : arrayList2) {
            translateFunction$addParameter(jsFunction2, jsGenerationContextNewDeclaration, (JsName) pair.component2(), (IrValueParameter) pair.component1());
        }
        if (AdditionalIrUtilsKt.isSuspend(declaration)) {
            throw new IllegalStateException("All Suspend functions should be lowered".toString());
        }
        return jsFunction2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void translateFunction$addParameter(JsFunction jsFunction, JsGenerationContext jsGenerationContext, JsName jsName, IrValueParameter irValueParameter) {
        jsFunction.getParameters().add(withSource$default(new JsParameter(jsName), irValueParameter, jsGenerationContext, irValueParameter, null, 8, null));
    }

    private static final boolean isFunctionTypeInvoke(JsExpression jsExpression, IrCall irCall) {
        IrValueParameter dispatchReceiverParameter;
        IrType type;
        if (jsExpression == null || (jsExpression instanceof JsThisRef)) {
            return false;
        }
        IrSimpleFunction owner = irCall.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? owner : null;
        if (irSimpleFunction == null || (dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter()) == null || (type = dispatchReceiverParameter.getType()) == null || irCall.getOrigin() == JsStatementOrigins.EXPLICIT_INVOKE.INSTANCE || !Intrinsics.areEqual(irSimpleFunction.getName(), OperatorNameConventions.INVOKE) || !IrTypeUtilsKt.isFunctionTypeOrSubtype(type)) {
            return false;
        }
        return !IrTypeUtilsKt.isSuspendFunctionTypeOrSubtype(type) || IrTypeUtilsKt.isSuspendFunction(type);
    }

    public static final JsExpression translateCall(IrCall expression, JsGenerationContext context, IrElementToJsExpressionTransformer transformer) {
        JsName nameForMemberFunction;
        JsNameRef jsNameRefJsElementAccess;
        JsInvocation jsInvocation;
        Pair pair;
        JsNameRef jsNameRef;
        List listMemoryOptimizedPlus;
        JsNameRef jsNameRefJsElementAccess2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrSimpleFunction realOverrideTarget = IrResolveUtilsKt.getRealOverrideTarget(expression.getSymbol().getOwner());
        IrFunction currentFunction = context.getCurrentFunction();
        IrClass parentClassOrNull = currentFunction != null ? IrUtilsKt.getParentClassOrNull(currentFunction) : null;
        Function2<IrCall, JsGenerationContext, JsExpression> function2 = context.getStaticContext().getIntrinsics().get(realOverrideTarget.getSymbol());
        if (function2 != null) {
            return function2.invoke(expression, context);
        }
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        JsExpression jsExpression = dispatchReceiver != null ? (JsExpression) dispatchReceiver.accept(transformer, context) : null;
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        JsExpression jsExpression2 = extensionReceiver != null ? (JsExpression) extensionReceiver.accept(transformer, context) : null;
        List listTranslateCallArguments$default = translateCallArguments$default(expression, context, transformer, false, 8, null);
        if (AnnotationUtilsKt.getJsName(realOverrideTarget) == null) {
            IrPropertySymbol correspondingPropertySymbol = realOverrideTarget.getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null && ((IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner) || IrJsUtilsKt.isExportedMember(owner, context.getStaticContext().getBackendContext())) && (realOverrideTarget.getOverriddenSymbols().isEmpty() || JsClassGeneratorKt.overriddenStableProperty(realOverrideTarget, context.getStaticContext().getBackendContext())))) {
                JsName nameForProperty = context.getNameForProperty(owner);
                if (jsExpression == null) {
                    jsNameRefJsElementAccess2 = new JsNameRef(nameForProperty);
                } else {
                    String ident = nameForProperty.getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                    jsNameRefJsElementAccess2 = jsElementAccess(ident, jsExpression);
                }
                if (Intrinsics.areEqual(realOverrideTarget, owner.getGetter())) {
                    return jsNameRefJsElementAccess2;
                }
                if (Intrinsics.areEqual(realOverrideTarget, owner.getSetter())) {
                    return jsAssignment(jsNameRefJsElementAccess2, (JsExpression) CollectionsKt.single(listTranslateCallArguments$default));
                }
                CompilationExceptionKt.compilationException("Function must be an accessor of corresponding property", (IrDeclaration) realOverrideTarget);
                throw null;
            }
        }
        if (isFunctionTypeInvoke(jsExpression, expression) || AnnotationUtilsKt.isJsNativeInvoke(expression.getSymbol().getOwner())) {
            if (jsExpression == null) {
                Intrinsics.checkNotNull(jsExpression2);
                jsExpression = jsExpression2;
            }
            return new JsInvocation(jsExpression, (List<? extends JsExpression>) listTranslateCallArguments$default);
        }
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol != null) {
            if (IrUtilsKt.isInterface(superQualifierSymbol.getOwner())) {
                IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(realOverrideTarget, false, null, 3, null);
                Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irOverridableDeclarationResolveFakeOverride$default;
                pair = new Pair(irSimpleFunction, IrUtilsKt.getParentAsClass(irSimpleFunction));
            } else {
                pair = new Pair(realOverrideTarget, superQualifierSymbol.getOwner());
            }
            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) pair.component1();
            IrClass irClass = (IrClass) pair.component2();
            if (canUseSuperRef(parentClassOrNull, context, irClass)) {
                return new JsInvocation(new JsNameRef(context.getNameForMemberFunction(irSimpleFunction2), new JsSuperRef()), (List<? extends JsExpression>) listTranslateCallArguments$default);
            }
            if (IrUtilsKt.isInterface(irClass)) {
                jsNameRef = new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), new JsNameRef(context.getNameForStaticDeclaration(irSimpleFunction2)));
            } else {
                jsNameRef = new JsNameRef(Namer.INSTANCE.getCALL_FUNCTION(), new JsNameRef(context.getNameForMemberFunction(irSimpleFunction2), prototypeOf(org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt.getClassRef(irClass, context.getStaticContext()), context.getStaticContext())));
            }
            JsNameRef jsNameRef2 = jsNameRef;
            if (jsExpression != null && (listMemoryOptimizedPlus = MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(CollectionsKt.listOf(jsExpression), listTranslateCallArguments$default)) != null) {
                listTranslateCallArguments$default = listMemoryOptimizedPlus;
            }
            return new JsInvocation(jsNameRef2, (List<? extends JsExpression>) listTranslateCallArguments$default);
        }
        IrSimpleFunction irSimpleFunction3 = realOverrideTarget;
        int iVarargParameterIndex = varargParameterIndex(irSimpleFunction3);
        boolean z = IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) realOverrideTarget) && iVarargParameterIndex != -1;
        if (jsExpression == null) {
            nameForMemberFunction = context.getNameForStaticFunction(realOverrideTarget);
        } else {
            nameForMemberFunction = context.getNameForMemberFunction(realOverrideTarget);
        }
        if (jsExpression == null) {
            jsNameRefJsElementAccess = new JsNameRef(nameForMemberFunction);
        } else {
            String ident2 = nameForMemberFunction.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
            jsNameRefJsElementAccess = jsElementAccess(ident2, jsExpression);
        }
        if (z) {
            JsExpression jsExpressionArgumentsWithVarargAsSingleArray = argumentsWithVarargAsSingleArray(expression, context, jsExpression2, listTranslateCallArguments$default, iVarargParameterIndex);
            if (jsExpression != null) {
                if (jsExpressionArgumentsWithVarargAsSingleArray instanceof JsArrayLiteral) {
                    String ident3 = nameForMemberFunction.getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident3, "getIdent(...)");
                    jsInvocation = new JsInvocation(jsElementAccess(ident3, jsExpression), ((JsArrayLiteral) jsExpressionArgumentsWithVarargAsSingleArray).getExpressions());
                } else {
                    JsName jsName = new JsName("$externalVarargReceiverTmp", false);
                    JsNameRef jsNameRefMakeRef = jsName.makeRef();
                    Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                    JsScope emptyScope = JsGenerationContextKt.getEmptyScope();
                    String ident4 = nameForMemberFunction.getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident4, "getIdent(...)");
                    jsInvocation = new JsInvocation(new JsNameRef("call", new JsFunction(emptyScope, new JsBlock(new JsVars(new JsVars.JsVar(jsName, jsExpression)), new JsReturn(new JsInvocation(new JsNameRef("apply", jsElementAccess(ident4, jsNameRefMakeRef)), (List<? extends JsExpression>) CollectionsKt.listOf((Object[]) new JsExpression[]{jsNameRefMakeRef, jsExpressionArgumentsWithVarargAsSingleArray})))), "VarargIIFE")), new JsThisRef());
                }
            } else if (jsExpressionArgumentsWithVarargAsSingleArray instanceof JsArrayLiteral) {
                jsInvocation = new JsInvocation(new JsNameRef(nameForMemberFunction), ((JsArrayLiteral) jsExpressionArgumentsWithVarargAsSingleArray).getExpressions());
            } else {
                jsInvocation = new JsInvocation(new JsNameRef("apply", new JsNameRef(nameForMemberFunction)), (List<? extends JsExpression>) CollectionsKt.listOf((Object[]) new JsExpression[]{new JsNullLiteral(), jsExpressionArgumentsWithVarargAsSingleArray}));
            }
            return jsInvocation;
        }
        return pureIfPossible(new JsInvocation(jsNameRefJsElementAccess, (List<? extends JsExpression>) MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(CollectionsKt.listOfNotNull(jsExpression2), listTranslateCallArguments$default)), irSimpleFunction3, context);
    }

    private static final JsInvocation pureIfPossible(JsInvocation jsInvocation, IrFunction irFunction, JsGenerationContext jsGenerationContext) {
        if (IrJsUtilsKt.isUnitInstanceFunction(irFunction.getSymbol(), jsGenerationContext.getStaticContext().getBackendContext())) {
            MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            MetadataProperties.setSideEffects(qualifier, SideEffectKind.PURE);
        }
        return jsInvocation;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0081  */
    public static final JsExpression argumentsWithVarargAsSingleArray(IrFunctionAccessExpression expression, JsGenerationContext context, JsExpression jsExpression, List<? extends JsExpression> arguments, int i) {
        JsArrayLiteral jsArrayLiteral;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        ArrayList arrayList = new ArrayList();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, jsExpression);
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        for (Object obj : arguments) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            JsInvocation jsInvocation = (JsExpression) obj;
            if (i2 == i) {
                IrExpression valueArgument = expression.getValueArgument(i);
                if (!arrayList.isEmpty()) {
                    arrayList2.add(new JsArrayLiteral(arrayList));
                }
                arrayList = new ArrayList();
                if (jsInvocation instanceof JsArrayLiteral) {
                    jsArrayLiteral = (JsArrayLiteral) jsInvocation;
                } else if (jsInvocation instanceof JsNew) {
                    List<JsExpression> arguments2 = ((JsNew) jsInvocation).getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                    Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) arguments2);
                    if (objFirstOrNull instanceof JsArrayLiteral) {
                        jsArrayLiteral = (JsArrayLiteral) objFirstOrNull;
                    } else {
                        jsArrayLiteral = null;
                    }
                } else {
                    jsArrayLiteral = null;
                }
                if (jsArrayLiteral != null) {
                    jsInvocation = jsArrayLiteral;
                } else if (!(valueArgument instanceof IrCall) || !Intrinsics.areEqual(((IrCall) valueArgument).getSymbol(), context.getStaticContext().getBackendContext().getIntrinsics().getArrayConcat())) {
                    jsInvocation = new JsInvocation(new JsNameRef("call", new JsNameRef("slice", new JsArrayLiteral())), jsInvocation);
                }
                arrayList2.add(jsInvocation);
            } else {
                arrayList.add(jsInvocation);
            }
            i2 = i3;
        }
        if (!arrayList.isEmpty()) {
            arrayList2.add(new JsArrayLiteral(arrayList));
        }
        if (arrayList2.isEmpty()) {
            return new JsArrayLiteral();
        }
        ArrayList<JsExpression> arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                if (!(((JsExpression) it.next()) instanceof JsArrayLiteral)) {
                    if (arrayList2.size() == 1) {
                        return (JsExpression) arrayList2.get(0);
                    }
                    return new JsInvocation(new JsNameRef("concat", (JsExpression) CollectionsKt.first((List) arrayList2)), (List<? extends JsExpression>) CollectionsKt.drop(arrayList3, 1));
                }
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (JsExpression jsExpression2 : arrayList3) {
            Intrinsics.checkNotNull(jsExpression2, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral");
            List<JsExpression> expressions = ((JsArrayLiteral) jsExpression2).getExpressions();
            Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
            arrayList4.addAll(expressions);
        }
        return new JsArrayLiteral(arrayList4);
    }

    public static final int varargParameterIndex(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Iterator<IrValueParameter> it = irFunction.getValueParameters().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getVarargElementType() != null) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static /* synthetic */ List translateCallArguments$default(IrMemberAccessExpression irMemberAccessExpression, JsGenerationContext jsGenerationContext, IrElementToJsExpressionTransformer irElementToJsExpressionTransformer, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return translateCallArguments(irMemberAccessExpression, jsGenerationContext, irElementToJsExpressionTransformer, z);
    }

    public static final List<JsExpression> translateCallArguments(IrMemberAccessExpression<IrFunctionSymbol> expression, final JsGenerationContext context, IrElementToJsExpressionTransformer transformer, boolean z) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        int valueArgumentsCount = expression.getValueArgumentsCount();
        IrFunction owner = ((IrFunctionSymbol) expression.getSymbol()).getOwner();
        int iVarargParameterIndex = varargParameterIndex(IrResolveUtilsKt.getRealOverrideTarget(owner));
        boolean zValidWithNullArgs = validWithNullArgs(expression);
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtilsKt$translateCallArguments$jsUndefined$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return JsAstUtilsKt.jsUndefined(context.getStaticContext());
            }
        });
        IntRange intRangeUntil = RangesKt.until(0, valueArgumentsCount);
        ArrayList arrayList = new ArrayList(valueArgumentsCount);
        Iterator<Integer> it = intRangeUntil.iterator();
        int i = 0;
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                break;
            }
            ((IntIterator) it).nextInt();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrExpression valueArgument = expression.getValueArgument(i);
            if (!zValidWithNullArgs && !ES6AddBoxParameterLoweringKt.isBoxParameter(owner.getValueParameters().get(i))) {
                z2 = false;
            }
            arrayList.add(checkOnNullability(valueArgument, z2));
            i = i2;
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        int i3 = 0;
        for (Object obj : arrayList2) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrExpression irExpression = (IrExpression) obj;
            JsExpression jsExpression = null;
            JsExpression jsExpression2 = ((z && (irExpression == null || isVoidGetter(irExpression, context))) || irExpression == null) ? null : (JsExpression) irExpression.accept(transformer, context);
            if (!(zValidWithNullArgs && iVarargParameterIndex == i3 && (jsExpression2 instanceof JsArrayLiteral) && ((JsArrayLiteral) jsExpression2).getExpressions().isEmpty()) || i3 != valueArgumentsCount - 1) {
                jsExpression = jsExpression2;
            }
            arrayList3.add(jsExpression);
            i3 = i4;
        }
        ArrayList arrayList4 = arrayList3;
        if (!arrayList4.isEmpty()) {
            ListIterator listIterator = arrayList4.listIterator(arrayList4.size());
            while (true) {
                if (listIterator.hasPrevious()) {
                    if (((JsExpression) listIterator.previous()) != null) {
                        listEmptyList = CollectionsKt.take(arrayList4, listIterator.nextIndex() + 1);
                        break;
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
            break;
        }
        List<JsExpression> list = listEmptyList;
        ArrayList arrayList5 = new ArrayList(list.size());
        for (JsExpression jsExpressionTranslateCallArguments$lambda$18 : list) {
            if (jsExpressionTranslateCallArguments$lambda$18 == null) {
                jsExpressionTranslateCallArguments$lambda$18 = translateCallArguments$lambda$18(lazy);
            }
            arrayList5.add(jsExpressionTranslateCallArguments$lambda$18);
        }
        List<JsExpression> listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList5);
        if (IrUtilsKt.isSuspend(expression.getSymbol())) {
            throw new IllegalStateException("Suspend functions should be lowered".toString());
        }
        return listCompactIfPossible;
    }

    private static final JsExpression translateCallArguments$lambda$18(Lazy<? extends JsExpression> lazy) {
        return lazy.getValue();
    }

    private static final boolean isVoidGetter(IrExpression irExpression, JsGenerationContext jsGenerationContext) {
        return (irExpression instanceof IrGetField) && Intrinsics.areEqual(((IrGetField) irExpression).getSymbol().getOwner().getCorrespondingPropertySymbol(), jsGenerationContext.getStaticContext().getBackendContext().getIntrinsics().getVoid());
    }

    private static final boolean validWithNullArgs(IrMemberAccessExpression<?> irMemberAccessExpression) {
        return (irMemberAccessExpression instanceof IrFunctionAccessExpression) && IrUtilsKt.isExternalOrInheritedFromExternal(((IrFunctionAccessExpression) irMemberAccessExpression).getSymbol().getOwner());
    }

    public static final JsBlock asBlock(JsStatement jsStatement) {
        Intrinsics.checkNotNullParameter(jsStatement, "<this>");
        JsBlock jsBlock = jsStatement instanceof JsBlock ? (JsBlock) jsStatement : null;
        return jsBlock == null ? new JsBlock(jsStatement) : jsBlock;
    }

    public static /* synthetic */ JsNode withSource$default(JsNode jsNode, IrElement irElement, JsGenerationContext jsGenerationContext, IrDeclarationWithName irDeclarationWithName, IrDeclaration irDeclaration, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationWithName = null;
        }
        if ((i & 8) != 0) {
            irDeclaration = null;
        }
        return withSource(jsNode, irElement, jsGenerationContext, irDeclarationWithName, irDeclaration);
    }

    private static final JsLocationWithEmbeddedSource withEmbeddedSource(final JsLocation jsLocation, JsGenerationContext jsGenerationContext) {
        return new JsLocationWithEmbeddedSource(jsLocation, null, new Function0<Reader>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsAstUtilsKt.withEmbeddedSource.1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Reader invoke() {
                try {
                    return new InputStreamReader(new FileInputStream(jsLocation.getFile()), StandardCharsets.UTF_8);
                } catch (IOException unused) {
                    return null;
                }
            }
        });
    }

    public static final JsLocation getStartSourceLocation(IrElement irElement, IrDeclaration container) {
        IrFileEntry fileEntry;
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(container, "container");
        IrFile fileOrNull = IrUtilsKt.getFileOrNull(container);
        if (fileOrNull == null || (fileEntry = fileOrNull.getFileEntry()) == null) {
            return null;
        }
        return getStartSourceLocation(irElement, fileEntry);
    }

    public static final JsLocation getSourceLocation(IrElement irElement, IrFileEntry fileEntry, Function1<? super IrElement, Integer> offsetSelector) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(offsetSelector, "offsetSelector");
        if (irElement.getStartOffset() == -1 || irElement.getEndOffset() == -1) {
            return null;
        }
        String name2 = fileEntry.getName();
        int iIntValue = offsetSelector.invoke(irElement).intValue();
        return new JsLocation(name2, fileEntry.getLineNumber(iIntValue), fileEntry.getColumnNumber(iIntValue), null, 8, null);
    }

    private static final String originalNameForUseInSourceMap(IrDeclarationWithName irDeclarationWithName, SourceMapNamesPolicy sourceMapNamesPolicy) {
        FqName fqNameWhenAvailable;
        if (sourceMapNamesPolicy == SourceMapNamesPolicy.NO) {
            return null;
        }
        if (irDeclarationWithName instanceof IrField) {
            IrPropertySymbol correspondingPropertySymbol = ((IrField) irDeclarationWithName).getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol != null) {
                return originalNameForUseInSourceMap(correspondingPropertySymbol.getOwner(), sourceMapNamesPolicy);
            }
        } else if (irDeclarationWithName instanceof IrFunction) {
            if (sourceMapNamesPolicy == SourceMapNamesPolicy.FULLY_QUALIFIED_NAMES && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)) != null) {
                return fqNameWhenAvailable.asString();
            }
        } else if ((irDeclarationWithName instanceof IrValueDeclaration) && !CollectionsKt.contains(nameMappingOriginAllowList, irDeclarationWithName.getOrigin())) {
            return null;
        }
        return irDeclarationWithName.getName().asString();
    }

    private static final boolean canUseSuperRef(IrClass irClass, JsGenerationContext jsGenerationContext, IrClass irClass2) {
        IrClass superClass;
        IrFunction currentFunction = jsGenerationContext.getCurrentFunction();
        if (currentFunction == null || irClass == null || !jsGenerationContext.getStaticContext().getBackendContext().getEs6mode() || IrUtilsKt.isInterface(irClass2) || irClass.getIsInner() || AdditionalIrUtilsKt.isLocal(irClass)) {
            return false;
        }
        IrFunction irFunction = currentFunction;
        if (ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irFunction)) {
            return false;
        }
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction);
        return !Intrinsics.areEqual((parentClassOrNull == null || (superClass = IrUtilsKt.getSuperClass(parentClassOrNull)) == null) ? null : superClass.getSymbol(), jsGenerationContext.getStaticContext().getBackendContext().getCoroutineSymbols().getCoroutineImpl());
    }

    public static final <T extends JsNode> T withSource(T t, IrElement node, JsGenerationContext context, IrDeclarationWithName irDeclarationWithName, IrDeclaration irDeclaration) {
        JsExpression jsExpression;
        JsLocation jsLocationWithEmbeddedSource;
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(context, "context");
        SourceMapsInfo sourceMapsInfo = SourceMapsInfoKt.getSourceMapsInfo(context.getStaticContext().getBackendContext());
        if (sourceMapsInfo != null) {
            JsLocation jsLocationCopy$default = null;
            JsLocation startLocationForIrElement = context.getStartLocationForIrElement(node, irDeclarationWithName != null ? originalNameForUseInSourceMap(irDeclarationWithName, sourceMapsInfo.getNamesPolicy()) : null);
            if (startLocationForIrElement != null) {
                boolean zAreEqual = Intrinsics.areEqual(context.getCurrentFile().getModule().getDescriptor(), context.getStaticContext().getBackendContext().getModule());
                if (t instanceof JsExpressionStatement) {
                    JsExpression expression = ((JsExpressionStatement) t).getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                    jsExpression = expression;
                } else {
                    jsExpression = t;
                }
                if ((jsExpression instanceof JsBlock) && ((node instanceof IrBlockBody) || (node instanceof IrBlock))) {
                    JsBlock jsBlock = (JsBlock) jsExpression;
                    if (irDeclaration instanceof IrConstructor) {
                        jsLocationCopy$default = JsGenerationContext.getStartLocationForIrElement$default(context, node, null, 2, null);
                    } else {
                        JsLocation endLocationForIrElement$default = JsGenerationContext.getEndLocationForIrElement$default(context, node, null, 2, null);
                        if (endLocationForIrElement$default != null && endLocationForIrElement$default.getStartChar() > 0) {
                            jsLocationCopy$default = JsLocation.copy$default(endLocationForIrElement$default, null, 0, endLocationForIrElement$default.getStartChar() - 1, null, 11, null);
                        }
                    }
                    jsBlock.setClosingBraceSource(jsLocationCopy$default);
                }
                int i = WhenMappings.$EnumSwitchMapping$0[sourceMapsInfo.getSourceMapContentEmbedding().ordinal()];
                if (i == 1) {
                    jsLocationWithEmbeddedSource = startLocationForIrElement;
                } else if (i == 2) {
                    jsLocationWithEmbeddedSource = zAreEqual ? startLocationForIrElement : withEmbeddedSource(startLocationForIrElement, context);
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    jsLocationWithEmbeddedSource = withEmbeddedSource(startLocationForIrElement, context);
                }
                jsExpression.setSource(jsLocationWithEmbeddedSource);
            }
        }
        return t;
    }

    public static final JsLocation getStartSourceLocation(IrElement irElement, IrFileEntry fileEntry) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        if (irElement.getStartOffset() == -1 || irElement.getEndOffset() == -1) {
            return null;
        }
        String name2 = fileEntry.getName();
        int startOffset = irElement.getStartOffset();
        return new JsLocation(name2, fileEntry.getLineNumber(startOffset), fileEntry.getColumnNumber(startOffset), null, 8, null);
    }
}
