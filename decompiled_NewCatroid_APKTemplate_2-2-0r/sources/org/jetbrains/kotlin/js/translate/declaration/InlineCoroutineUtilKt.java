package org.jetbrains.kotlin.js.translate.declaration;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.InlineUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: inlineCoroutineUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001d\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u001d\u0010\u0005\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\f\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0002¨\u0006\t"}, d2 = {"transformCoroutineMetadataToSpecialFunctions", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "transformSpecialFunctionsToCoroutineMetadata", "ref", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineCoroutineUtilKt {
    public static final <T extends JsNode> T transformCoroutineMetadataToSpecialFunctions(T node) {
        Intrinsics.checkNotNullParameter(node, "node");
        T t = (T) new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.translate.declaration.InlineCoroutineUtilKt$transformCoroutineMetadataToSpecialFunctions$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<? super JsExpression> ctx) {
                SpecialFunction specialFunction;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.getCoroutineController(x)) {
                    specialFunction = SpecialFunction.COROUTINE_CONTROLLER;
                } else if (MetadataProperties.getCoroutineReceiver(x)) {
                    specialFunction = SpecialFunction.COROUTINE_RECEIVER;
                } else {
                    specialFunction = MetadataProperties.getCoroutineResult(x) ? SpecialFunction.COROUTINE_RESULT : null;
                }
                if (specialFunction != null) {
                    JsExpression[] jsExpressionArr = (JsExpression[]) CollectionsKt.listOfNotNull(x.getQualifier()).toArray(new JsExpression[0]);
                    JsInvocation jsInvocation = new JsInvocation(InlineCoroutineUtilKt.ref(specialFunction), (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
                    JsInvocation jsInvocation2 = jsInvocation;
                    JsNameRef jsNameRef = x;
                    MetadataProperties.setSynthetic(jsInvocation2, MetadataProperties.getSynthetic(jsNameRef));
                    MetadataProperties.setSideEffects(jsInvocation2, MetadataProperties.getSideEffects(jsNameRef));
                    jsInvocation.setSource(x.getSource());
                    ctx.replaceMe(jsInvocation);
                    return;
                }
                super.endVisit(x, (JsContext) ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsExpression x, JsContext<? super JsExpression> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.isSuspend(x)) {
                    MetadataProperties.setSuspend(x, false);
                    ctx.replaceMe(new JsInvocation(InlineCoroutineUtilKt.ref(SpecialFunction.SUSPEND_CALL), x).source(x.getSource()));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsBinaryOperation x, JsContext<? super JsExpression> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsExpression arg1 = x.getArg1();
                if (arg1 instanceof JsNameRef) {
                    JsNameRef jsNameRef = (JsNameRef) arg1;
                    if (MetadataProperties.getCoroutineResult(jsNameRef)) {
                        List listListOf = CollectionsKt.listOf(accept(x.getArg2()));
                        JsExpression qualifier = jsNameRef.getQualifier();
                        JsInvocation jsInvocation = new JsInvocation(InlineCoroutineUtilKt.ref(SpecialFunction.SET_COROUTINE_RESULT), (List<? extends JsExpression>) CollectionsKt.plus((Collection) listListOf, (Iterable) CollectionsKt.listOfNotNull(qualifier != null ? (JsExpression) accept(qualifier) : null)));
                        JsInvocation jsInvocation2 = jsInvocation;
                        JsBinaryOperation jsBinaryOperation = x;
                        MetadataProperties.setSynthetic(jsInvocation2, MetadataProperties.getSynthetic(jsBinaryOperation));
                        MetadataProperties.setSideEffects(jsInvocation2, MetadataProperties.getSideEffects(jsBinaryOperation));
                        jsInvocation.setSource(x.getSource());
                        ctx.replaceMe(jsInvocation);
                        return false;
                    }
                }
                return super.visit(x, ctx);
            }
        }.accept(node);
        Intrinsics.checkNotNullExpressionValue(t, "accept(...)");
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsNameRef ref(SpecialFunction specialFunction) {
        JsName jsNameDeclareName = JsDynamicScope.INSTANCE.declareName(specialFunction.getSuggestedName());
        Intrinsics.checkNotNull(jsNameDeclareName);
        MetadataProperties.setSpecialFunction(jsNameDeclareName, specialFunction);
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(jsNameDeclareName, Namer.kotlinObject());
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        return jsNameRefPureFqn;
    }

    public static final <T extends JsNode> T transformSpecialFunctionsToCoroutineMetadata(T node) {
        Intrinsics.checkNotNullParameter(node, "node");
        T t = (T) new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.translate.declaration.InlineCoroutineUtilKt$transformSpecialFunctionsToCoroutineMetadata$visitor$1

            /* JADX INFO: compiled from: inlineCoroutineUtil.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[SpecialFunction.values().length];
                    try {
                        iArr[SpecialFunction.COROUTINE_CONTROLLER.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[SpecialFunction.COROUTINE_RECEIVER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[SpecialFunction.COROUTINE_RESULT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[SpecialFunction.SUSPEND_CALL.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[SpecialFunction.SET_COROUTINE_RESULT.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation x, JsContext<? super JsExpression> ctx) {
                SpecialFunction specialFunction;
                JsNameRef jsNameRefAssignment;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsName name2 = InlineUtils.getName(x.getQualifier());
                if (name2 == null || (specialFunction = MetadataProperties.getSpecialFunction(name2)) == null) {
                    return;
                }
                int i = WhenMappings.$EnumSwitchMapping$0[specialFunction.ordinal()];
                if (i == 1) {
                    List<JsExpression> arguments = x.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                    JsNameRef jsNameRef = new JsNameRef("$$controller$$", (JsExpression) CollectionsKt.getOrNull(arguments, 0));
                    MetadataProperties.setCoroutineController(jsNameRef, true);
                    jsNameRefAssignment = jsNameRef;
                } else if (i == 2) {
                    List<JsExpression> arguments2 = x.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                    JsNameRef jsNameRef2 = new JsNameRef(AsmUtil.LABELED_THIS_PARAMETER, (JsExpression) CollectionsKt.getOrNull(arguments2, 0));
                    MetadataProperties.setCoroutineReceiver(jsNameRef2, true);
                    jsNameRefAssignment = jsNameRef2;
                } else if (i == 3) {
                    List<JsExpression> arguments3 = x.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
                    JsNameRef jsNameRef3 = new JsNameRef("$result$", (JsExpression) CollectionsKt.getOrNull(arguments3, 0));
                    MetadataProperties.setCoroutineResult(jsNameRef3, true);
                    jsNameRefAssignment = jsNameRef3;
                } else if (i == 4) {
                    jsNameRefAssignment = x.getArguments().get(0);
                    Intrinsics.checkNotNull(jsNameRefAssignment);
                    MetadataProperties.setSuspend(jsNameRefAssignment, true);
                } else if (i != 5) {
                    jsNameRefAssignment = null;
                } else {
                    List<JsExpression> arguments4 = x.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments4, "getArguments(...)");
                    JsNameRef jsNameRef4 = new JsNameRef("$result$", (JsExpression) CollectionsKt.getOrNull(arguments4, 1));
                    MetadataProperties.setCoroutineResult(jsNameRef4, true);
                    jsNameRefAssignment = JsAstUtils.assignment(jsNameRef4, x.getArguments().get(0));
                }
                if (jsNameRefAssignment != null) {
                    jsNameRefAssignment.setSource(x.getSource());
                    JsExpression jsExpression = jsNameRefAssignment;
                    JsInvocation jsInvocation = x;
                    MetadataProperties.setSideEffects(jsExpression, MetadataProperties.getSideEffects(jsInvocation));
                    MetadataProperties.setSynthetic(jsExpression, MetadataProperties.getSynthetic(jsInvocation));
                    ctx.replaceMe(jsNameRefAssignment);
                }
            }
        }.accept(node);
        Intrinsics.checkNotNullExpressionValue(t, "accept(...)");
        return t;
    }
}
