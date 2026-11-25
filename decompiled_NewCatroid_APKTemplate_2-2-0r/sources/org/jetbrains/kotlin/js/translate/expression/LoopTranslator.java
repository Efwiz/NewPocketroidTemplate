package org.jetbrains.kotlin.js.translate.expression;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TemporaryVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtWhileExpressionBase;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: LoopTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"downToFunctionName", "Lorg/jetbrains/kotlin/name/FqName;", "indicesFqName", "intProgressionName", "intRangeName", "rangeToFunctionName", "sequenceFqName", "sequenceWithIndexFqName", "stepFunctionName", "untilFunctionName", "withIndexFqName", "createWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "doWhile", "", "expression", "Lorg/jetbrains/kotlin/psi/KtWhileExpressionBase;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "translateForExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LoopTranslator {
    private static final FqName rangeToFunctionName = new FqName("kotlin.Int.rangeTo");
    private static final FqName untilFunctionName = new FqName("kotlin.ranges.until");
    private static final FqName downToFunctionName = new FqName("kotlin.ranges.downTo");
    private static final FqName stepFunctionName = new FqName("kotlin.ranges.step");
    private static final FqName intRangeName = new FqName(RangeUtilKt.INT_RANGE_FQN);
    private static final FqName intProgressionName = new FqName(RangeUtilKt.INT_PROGRESSION_FQN);
    private static final FqName withIndexFqName = new FqName("kotlin.collections.withIndex");
    private static final FqName sequenceWithIndexFqName = new FqName("kotlin.sequences.withIndex");
    private static final FqName indicesFqName = new FqName("kotlin.collections.indices");
    private static final FqName sequenceFqName = new FqName("kotlin.sequences.Sequence");

    /* JADX INFO: compiled from: LoopTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RangeType.values().length];
            try {
                iArr[RangeType.RANGE_TO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RangeType.UNTIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RangeType.DOWN_TO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[WithIndexReceiverType.values().length];
            try {
                iArr2[WithIndexReceiverType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[WithIndexReceiverType.ITERABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[WithIndexReceiverType.SEQUENCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[WithIndexReceiverType.COLLECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final JsNode createWhile(boolean z, KtWhileExpressionBase expression, TranslationContext context) {
        JsEmpty jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtExpression condition = expression.getCondition();
        if (condition == null) {
            throw new IllegalArgumentException("condition expression should not be null: " + expression.getText());
        }
        JsBlock jsBlock = new JsBlock();
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(condition, context, jsBlock);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
        KtExpression body = expression.getBody();
        if (body != null) {
            jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded = Translation.translateAsStatementAndMergeInBlockIfNeeded(body, context);
        } else {
            jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded = JsEmpty.INSTANCE;
        }
        Intrinsics.checkNotNull(jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded);
        if (!jsBlock.isEmpty()) {
            JsPrefixOperation jsPrefixOperationNot = JsAstUtils.not(jsExpressionTranslateAsExpression);
            JsBreak jsBreak = new JsBreak();
            jsBreak.setSource(expression);
            JsIf jsIf = new JsIf(jsPrefixOperationNot, jsBreak);
            jsIf.setSource(expression);
            JsBlock jsBlockConvertToBlock = JsAstUtils.convertToBlock(jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded);
            Intrinsics.checkNotNullExpressionValue(jsBlockConvertToBlock, "convertToBlock(...)");
            JsBooleanLiteral jsBooleanLiteral = new JsBooleanLiteral(true);
            if (z) {
                JsExpression jsExpressionDefineTemporary = context.defineTemporary(new JsBooleanLiteral(false).source((Object) expression));
                Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
                jsBlock.getStatements().add(jsIf);
                JsIf jsIf2 = new JsIf(jsExpressionDefineTemporary, jsBlock, JsAstUtils.assignment(jsExpressionDefineTemporary, new JsBooleanLiteral(true)).source((Object) expression).makeStmt());
                List<JsStatement> statements = jsBlockConvertToBlock.getStatements();
                jsIf2.setSource(expression);
                Unit unit = Unit.INSTANCE;
                statements.add(0, jsIf2);
            } else {
                jsBlock.getStatements().add(jsIf);
                List<JsStatement> statements2 = jsBlockConvertToBlock.getStatements();
                List<JsStatement> statements3 = jsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                statements2.addAll(0, statements3);
            }
            JsBlock jsBlock2 = jsBlockConvertToBlock;
            jsExpressionTranslateAsExpression = jsBooleanLiteral;
            jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded = jsBlock2;
        }
        JsDoWhile jsDoWhile = z ? new JsDoWhile() : new JsWhile();
        jsDoWhile.setCondition(jsExpressionTranslateAsExpression);
        jsDoWhile.setBody(jsEmptyTranslateAsStatementAndMergeInBlockIfNeeded);
        JsNode jsNodeSource = jsDoWhile.source(expression);
        Intrinsics.checkNotNullExpressionValue(jsNodeSource, "source(...)");
        return jsNodeSource;
    }

    public static final JsStatement translateForExpression(KtForExpression expression, TranslationContext context) {
        JsName jsNameDeclareTemporary;
        JsStatement jsStatementTranslateForExpression$translateForOverIterator;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtExpression loopRange = PsiUtils.getLoopRange(expression);
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(loopRange);
        Intrinsics.checkNotNull(ktExpressionDeparenthesize);
        KtExpression ktExpression = ktExpressionDeparenthesize instanceof KtStringTemplateExpression ? loopRange : ktExpressionDeparenthesize;
        Intrinsics.checkNotNullExpressionValue(ktExpression, "let(...)");
        KotlinType typeForExpression = BindingUtils.getTypeForExpression(context.bindingContext(), ktExpression);
        Intrinsics.checkNotNullExpressionValue(typeForExpression, "getTypeForExpression(...)");
        KtParameter loopParameter = expression.getLoopParameter();
        Intrinsics.checkNotNull(loopParameter);
        KtDestructuringDeclaration destructuringDeclaration = loopParameter.getDestructuringDeclaration();
        if (destructuringDeclaration == null) {
            jsNameDeclareTemporary = context.getNameForElement(loopParameter);
        } else {
            jsNameDeclareTemporary = JsScope.declareTemporary();
        }
        Intrinsics.checkNotNull(jsNameDeclareTemporary);
        RangeLiteral rangeLiteralTranslateForExpression$extractForOverRangeLiteral = translateForExpression$extractForOverRangeLiteral(typeForExpression, ktExpression, context);
        WithIndexInfo withIndexInfoTranslateForExpression$extractWithIndexCall = translateForExpression$extractWithIndexCall(ktExpression, context, destructuringDeclaration, loopParameter);
        if (rangeLiteralTranslateForExpression$extractForOverRangeLiteral != null) {
            jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverLiteralRange(context, jsNameDeclareTemporary, expression, destructuringDeclaration, loopParameter, rangeLiteralTranslateForExpression$extractForOverRangeLiteral);
        } else if (withIndexInfoTranslateForExpression$extractWithIndexCall != null) {
            int i = WhenMappings.$EnumSwitchMapping$1[withIndexInfoTranslateForExpression$extractWithIndexCall.getReceiverType().ordinal()];
            if (i == 1) {
                jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverArrayWithIndex(context, expression, withIndexInfoTranslateForExpression$extractWithIndexCall);
            } else if (i == 2 || i == 3 || i == 4) {
                if (withIndexInfoTranslateForExpression$extractWithIndexCall.getValue() == null && withIndexInfoTranslateForExpression$extractWithIndexCall.getReceiverType() == WithIndexReceiverType.COLLECTION) {
                    jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverCollectionIndices(context, expression, withIndexInfoTranslateForExpression$extractWithIndexCall);
                } else {
                    jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverCollectionWithIndex(context, expression, withIndexInfoTranslateForExpression$extractWithIndexCall);
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
        } else if (translateForExpression$isForOverRange(typeForExpression)) {
            jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverRange(context, ktExpression, jsNameDeclareTemporary, expression, destructuringDeclaration, loopParameter);
        } else if (translateForExpression$isForOverArray(typeForExpression)) {
            jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverArray(context, ktExpression, expression, destructuringDeclaration, loopParameter, jsNameDeclareTemporary);
        } else {
            jsStatementTranslateForExpression$translateForOverIterator = translateForExpression$translateForOverIterator(context, ktExpression, expression, destructuringDeclaration, loopParameter, jsNameDeclareTemporary);
        }
        jsStatementTranslateForExpression$translateForOverIterator.setSource(expression);
        return jsStatementTranslateForExpression$translateForOverIterator;
    }

    private static final boolean translateForExpression$isForOverRange(KotlinType kotlinType) {
        FqName fqNameSafe;
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(this$0)) == null) {
            return false;
        }
        return Intrinsics.areEqual(fqNameSafe, intRangeName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x007f, code lost:
    
        if (r3 == null) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final org.jetbrains.kotlin.js.translate.expression.RangeLiteral translateForExpression$extractForOverRangeLiteral(org.jetbrains.kotlin.types.KotlinType r3, org.jetbrains.kotlin.psi.KtExpression r4, org.jetbrains.kotlin.js.translate.context.TranslationContext r5) {
        /*
            Method dump skipped, instruction units count: 241
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.js.translate.expression.LoopTranslator.translateForExpression$extractForOverRangeLiteral(org.jetbrains.kotlin.types.KotlinType, org.jetbrains.kotlin.psi.KtExpression, org.jetbrains.kotlin.js.translate.context.TranslationContext):org.jetbrains.kotlin.js.translate.expression.RangeLiteral");
    }

    private static final boolean translateForExpression$isForOverArray(KotlinType kotlinType) {
        return KotlinBuiltIns.isArray(kotlinType) || KotlinBuiltIns.isPrimitiveArray(kotlinType);
    }

    private static final VariableDescriptor translateForExpression$extractDescriptor(KtDeclaration ktDeclaration, TranslationContext translationContext) {
        VariableDescriptor variableDescriptor = (VariableDescriptor) translationContext.bindingContext().get(BindingContext.VARIABLE, ktDeclaration);
        if (variableDescriptor == null || variableDescriptor.getShortName().isSpecial()) {
            return null;
        }
        return variableDescriptor;
    }

    private static final WithIndexInfo translateForExpression$extractWithIndexCall(KtExpression ktExpression, TranslationContext translationContext, KtDestructuringDeclaration ktDestructuringDeclaration, KtParameter ktParameter) {
        Pair pair;
        WithIndexReceiverType withIndexReceiverType;
        JsExpression dispatchReceiver;
        KotlinType type;
        TypeConstructor constructor;
        BindingContext bindingContext = translationContext.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, bindingContext);
        if (resolvedCall == null) {
            return null;
        }
        FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(resolvedCall.getResultingDescriptor());
        if (Intrinsics.areEqual(fqNameSafe, withIndexFqName) ? true : Intrinsics.areEqual(fqNameSafe, sequenceWithIndexFqName)) {
            if (ktDestructuringDeclaration == null) {
                return null;
            }
            List<KtDestructuringDeclarationEntry> entries = ktDestructuringDeclaration.getEntries();
            KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry = entries.get(0);
            Intrinsics.checkNotNullExpressionValue(ktDestructuringDeclarationEntry, "get(...)");
            VariableDescriptor variableDescriptorTranslateForExpression$extractDescriptor = translateForExpression$extractDescriptor(ktDestructuringDeclarationEntry, translationContext);
            KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = entries.get(1);
            Intrinsics.checkNotNullExpressionValue(ktDestructuringDeclarationEntry2, "get(...)");
            pair = new Pair(variableDescriptorTranslateForExpression$extractDescriptor, translateForExpression$extractDescriptor(ktDestructuringDeclarationEntry2, translationContext));
        } else {
            if (!Intrinsics.areEqual(fqNameSafe, indicesFqName) || ktDestructuringDeclaration != null) {
                return null;
            }
            Object obj = translationContext.bindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktParameter);
            VariableDescriptor variableDescriptor = obj instanceof VariableDescriptor ? (VariableDescriptor) obj : null;
            if (variableDescriptor == null) {
                return null;
            }
            pair = new Pair(variableDescriptor, null);
        }
        VariableDescriptor variableDescriptor2 = (VariableDescriptor) pair.component1();
        VariableDescriptor variableDescriptor3 = (VariableDescriptor) pair.component2();
        ReceiverParameterDescriptor extensionReceiverParameter = resolvedCall.getResultingDescriptor().getExtensionReceiverParameter();
        ClassifierDescriptor this$0 = (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || (constructor = type.getConstructor()) == null) ? null : constructor.getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return null;
        }
        if (KotlinBuiltIns.isArrayOrPrimitiveArray(classDescriptor)) {
            withIndexReceiverType = WithIndexReceiverType.ARRAY;
        } else if (KotlinBuiltIns.isCollectionOrNullableCollection(classDescriptor.getDefaultType())) {
            withIndexReceiverType = WithIndexReceiverType.COLLECTION;
        } else {
            if (!KotlinBuiltIns.isIterableOrNullableIterable(classDescriptor.getDefaultType())) {
                if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), sequenceFqName)) {
                    withIndexReceiverType = WithIndexReceiverType.SEQUENCE;
                }
                return null;
            }
            withIndexReceiverType = WithIndexReceiverType.ITERABLE;
        }
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        if (extensionReceiver == null) {
            return null;
        }
        if (!(extensionReceiver instanceof ExpressionReceiver)) {
            if (extensionReceiver instanceof ImplicitReceiver) {
                dispatchReceiver = translationContext.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForReceiver(extensionReceiver));
            }
            return null;
        }
        dispatchReceiver = Translation.translateAsExpression(((ExpressionReceiver) extensionReceiver).getExpression(), translationContext);
        Intrinsics.checkNotNull(dispatchReceiver);
        return new WithIndexInfo(withIndexReceiverType, variableDescriptor2, variableDescriptor3, dispatchReceiver);
    }

    private static final JsStatement translateForExpression$translateBody(KtForExpression ktForExpression, KtDestructuringDeclaration ktDestructuringDeclaration, TranslationContext translationContext, KtParameter ktParameter, JsName jsName, JsExpression jsExpression) {
        JsVars jsVarsTranslate;
        List<JsStatement> listListOfNotNull;
        KtExpression body = ktForExpression.getBody();
        JsStatement jsStatementTranslateAsStatementAndMergeInBlockIfNeeded = body != null ? Translation.translateAsStatementAndMergeInBlockIfNeeded(body, translationContext) : null;
        if (jsExpression == null && ktDestructuringDeclaration == null) {
            return jsStatementTranslateAsStatementAndMergeInBlockIfNeeded;
        }
        JsBlock jsBlock = new JsBlock();
        if (ktDestructuringDeclaration == null) {
            DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(translationContext.bindingContext(), ktParameter);
            Intrinsics.checkNotNull(descriptorForElement, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
            SimpleType returnType = ((CallableDescriptor) descriptorForElement).getReturnType();
            if (returnType == null) {
                returnType = translationContext.getCurrentModule().getBuiltIns().getAnyType();
            }
            Intrinsics.checkNotNull(returnType);
            jsVarsTranslate = JsAstUtils.newVar(jsName, jsExpression != null ? TranslationUtils.coerce(translationContext, jsExpression, returnType) : null);
            jsVarsTranslate.setSource(ktForExpression.getLoopRange());
        } else {
            TranslationContext translationContextInnerBlock = translationContext.innerBlock(jsBlock);
            Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
            if (jsExpression != null) {
                JsVars jsVarsNewVar = JsAstUtils.newVar(jsName, jsExpression);
                jsVarsNewVar.setSource(ktForExpression.getLoopRange());
                Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
                translationContextInnerBlock.addStatementToCurrentBlock(jsVarsNewVar);
            }
            jsVarsTranslate = DestructuringDeclarationTranslator.translate(ktDestructuringDeclaration, JsAstUtils.pureFqn(jsName, (JsExpression) null), translationContextInnerBlock);
        }
        Intrinsics.checkNotNull(jsVarsTranslate);
        List<JsStatement> statements = jsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(jsVarsTranslate);
        List<JsStatement> statements2 = jsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        List<JsStatement> list = statements2;
        if (jsStatementTranslateAsStatementAndMergeInBlockIfNeeded instanceof JsBlock) {
            listListOfNotNull = ((JsBlock) jsStatementTranslateAsStatementAndMergeInBlockIfNeeded).getStatements();
            Intrinsics.checkNotNullExpressionValue(listListOfNotNull, "getStatements(...)");
        } else {
            listListOfNotNull = CollectionsKt.listOfNotNull(jsStatementTranslateAsStatementAndMergeInBlockIfNeeded);
        }
        CollectionsKt.addAll(list, listListOfNotNull);
        return jsBlock;
    }

    private static final JsStatement translateForExpression$translateForOverLiteralRange(TranslationContext translationContext, JsName jsName, KtForExpression ktForExpression, KtDestructuringDeclaration ktDestructuringDeclaration, KtParameter ktParameter, RangeLiteral rangeLiteral) {
        JsBinaryOperation jsBinaryOperationLessThanEq;
        JsBinaryOperator jsBinaryOperator;
        JsExpression jsExpressionSource;
        JsUnaryOperator jsUnaryOperator;
        JsBlock jsBlock = new JsBlock();
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(rangeLiteral.getFirst(), translationContext, jsBlock);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
        JsBlock jsBlock2 = new JsBlock();
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(rangeLiteral.getSecond(), translationContext, jsBlock2);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression2, "translateAsExpression(...)");
        JsBlock jsBlock3 = new JsBlock();
        KtExpression step = rangeLiteral.getStep();
        JsExpression jsExpressionTranslateAsExpression3 = step != null ? Translation.translateAsExpression(step, translationContext, jsBlock3) : null;
        translationContext.addStatementsToCurrentBlockFrom(jsBlock);
        JsExpression jsExpressionCacheExpressionIfNeeded = translationContext.cacheExpressionIfNeeded(jsExpressionTranslateAsExpression);
        Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded, "cacheExpressionIfNeeded(...)");
        translationContext.addStatementsToCurrentBlockFrom(jsBlock2);
        JsExpression jsExpressionDefineTemporary = translationContext.defineTemporary(jsExpressionTranslateAsExpression2);
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
        translationContext.addStatementsToCurrentBlockFrom(jsBlock3);
        JsExpression jsExpressionDefineTemporary2 = jsExpressionTranslateAsExpression3 != null ? translationContext.defineTemporary(jsExpressionTranslateAsExpression3) : null;
        JsStatement jsStatementTranslateForExpression$translateBody = translateForExpression$translateBody(ktForExpression, ktDestructuringDeclaration, translationContext, ktParameter, jsName, null);
        int i = WhenMappings.$EnumSwitchMapping$0[rangeLiteral.getType().ordinal()];
        if (i == 1) {
            jsBinaryOperationLessThanEq = JsAstUtils.lessThanEq(jsName.makeRef(), jsExpressionDefineTemporary);
        } else if (i == 2) {
            jsBinaryOperationLessThanEq = JsAstUtils.lessThan(jsName.makeRef(), jsExpressionDefineTemporary);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            jsBinaryOperationLessThanEq = JsAstUtils.greaterThanEq(jsName.makeRef(), jsExpressionDefineTemporary);
        }
        JsExpression jsExpressionSource2 = jsBinaryOperationLessThanEq.source((Object) ktForExpression);
        if (jsExpressionDefineTemporary2 == null) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[rangeLiteral.getType().ordinal()];
            if (i2 == 1 || i2 == 2) {
                jsUnaryOperator = JsUnaryOperator.INC;
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                jsUnaryOperator = JsUnaryOperator.DEC;
            }
            jsExpressionSource = new JsPostfixOperation(jsUnaryOperator, jsName.makeRef()).source((Object) ktForExpression);
        } else {
            int i3 = WhenMappings.$EnumSwitchMapping$0[rangeLiteral.getType().ordinal()];
            if (i3 == 1 || i3 == 2) {
                jsBinaryOperator = JsBinaryOperator.ASG_ADD;
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                jsBinaryOperator = JsBinaryOperator.ASG_SUB;
            }
            jsExpressionSource = new JsBinaryOperation(jsBinaryOperator, jsName.makeRef(), jsExpressionDefineTemporary2).source((Object) ktForExpression);
        }
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsName, jsExpressionCacheExpressionIfNeeded);
        jsVarsNewVar.setSource(ktForExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
        return new JsFor(jsVarsNewVar, jsExpressionSource2, jsExpressionSource, jsStatementTranslateForExpression$translateBody);
    }

    private static final JsStatement translateForExpression$translateForOverRange(TranslationContext translationContext, KtExpression ktExpression, JsName jsName, KtForExpression ktForExpression, KtDestructuringDeclaration ktDestructuringDeclaration, KtParameter ktParameter) {
        JsExpression jsExpressionDefineTemporary = translationContext.defineTemporary(Translation.translateAsExpression(ktExpression, translationContext));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
        JsExpression jsExpressionDefineTemporary2 = translationContext.defineTemporary(translateForExpression$translateForOverRange$getProperty(jsExpressionDefineTemporary, ktExpression, "first"));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary2, "defineTemporary(...)");
        JsExpression jsExpressionDefineTemporary3 = translationContext.defineTemporary(translateForExpression$translateForOverRange$getProperty(jsExpressionDefineTemporary, ktExpression, "last"));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary3, "defineTemporary(...)");
        JsExpression jsExpressionDefineTemporary4 = translationContext.defineTemporary(translateForExpression$translateForOverRange$getProperty(jsExpressionDefineTemporary, ktExpression, "step"));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary4, "defineTemporary(...)");
        JsStatement jsStatementTranslateForExpression$translateBody = translateForExpression$translateBody(ktForExpression, ktDestructuringDeclaration, translationContext, ktParameter, jsName, null);
        JsExpression jsExpressionSource = JsAstUtils.lessThanEq(jsName.makeRef(), jsExpressionDefineTemporary3).source((Object) ktForExpression);
        JsExpression jsExpressionSource2 = JsAstUtils.addAssign(jsName.makeRef(), jsExpressionDefineTemporary4).source((Object) ktForExpression);
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsName, jsExpressionDefineTemporary2);
        jsVarsNewVar.setSource(ktForExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
        return new JsFor(jsVarsNewVar, jsExpressionSource, jsExpressionSource2, jsStatementTranslateForExpression$translateBody);
    }

    private static final JsExpression translateForExpression$translateForOverRange$getProperty(JsExpression jsExpression, KtExpression ktExpression, String str) {
        JsExpression jsExpressionSource = new JsNameRef(str, jsExpression).source((Object) ktExpression);
        Intrinsics.checkNotNullExpressionValue(jsExpressionSource, "source(...)");
        return jsExpressionSource;
    }

    private static final JsStatement translateForExpression$translateForOverArray(TranslationContext translationContext, KtExpression ktExpression, KtForExpression ktForExpression, KtDestructuringDeclaration ktDestructuringDeclaration, KtParameter ktParameter, JsName jsName) {
        JsExpression jsExpressionDefineTemporary = translationContext.defineTemporary(Translation.translateAsExpression(ktExpression, translationContext));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
        JsExpression jsExpressionApply = ArrayFIF.LENGTH_PROPERTY_INTRINSIC.apply(jsExpressionDefineTemporary, CollectionsKt.emptyList(), translationContext);
        Intrinsics.checkNotNullExpressionValue(jsExpressionApply, "apply(...)");
        JsExpression jsExpressionDefineTemporary2 = translationContext.defineTemporary(jsExpressionApply);
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary2, "defineTemporary(...)");
        TemporaryVariable temporaryVariableDeclareTemporary = translationContext.declareTemporary(new JsIntLiteral(0), ktForExpression);
        Intrinsics.checkNotNullExpressionValue(temporaryVariableDeclareTemporary, "declareTemporary(...)");
        return new JsFor(JsAstUtils.assignment(temporaryVariableDeclareTemporary.reference(), new JsIntLiteral(0)).source((Object) ktForExpression), JsAstUtils.inequality(temporaryVariableDeclareTemporary.reference(), jsExpressionDefineTemporary2).source((Object) ktForExpression), new JsPrefixOperation(JsUnaryOperator.INC, temporaryVariableDeclareTemporary.reference()).source((Object) ktForExpression), translateForExpression$translateBody(ktForExpression, ktDestructuringDeclaration, translationContext, ktParameter, jsName, new JsArrayAccess(jsExpressionDefineTemporary, temporaryVariableDeclareTemporary.reference()).source((Object) ktForExpression)));
    }

    private static final JsStatement translateForExpression$translateForOverArrayWithIndex(TranslationContext translationContext, KtForExpression ktForExpression, WithIndexInfo withIndexInfo) {
        JsName jsNameDeclareTemporary;
        JsExpression jsExpressionCacheExpressionIfNeeded = translationContext.cacheExpressionIfNeeded(withIndexInfo.getRange());
        Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded, "cacheExpressionIfNeeded(...)");
        VariableDescriptor index = withIndexInfo.getIndex();
        if (index == null || (jsNameDeclareTemporary = translationContext.getNameForDescriptor(index)) == null) {
            jsNameDeclareTemporary = JsScope.declareTemporary();
        }
        Intrinsics.checkNotNull(jsNameDeclareTemporary);
        VariableDescriptor value = withIndexInfo.getValue();
        JsName nameForDescriptor = value != null ? translationContext.getNameForDescriptor(value) : null;
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsNameDeclareTemporary, new JsIntLiteral(0));
        jsVarsNewVar.setSource(ktForExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
        JsExpression jsExpressionSource = JsAstUtils.inequality(jsNameDeclareTemporary.makeRef(), new JsNameRef("length", jsExpressionCacheExpressionIfNeeded)).source((Object) ktForExpression);
        JsExpression jsExpressionSource2 = new JsPrefixOperation(JsUnaryOperator.INC, jsNameDeclareTemporary.makeRef()).source((Object) ktForExpression);
        JsBlock jsBlock = new JsBlock();
        if (nameForDescriptor != null) {
            List<JsStatement> statements = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsVars jsVarsNewVar2 = JsAstUtils.newVar(nameForDescriptor, new JsArrayAccess(jsExpressionCacheExpressionIfNeeded, jsNameDeclareTemporary.makeRef()));
            jsVarsNewVar2.setSource(ktForExpression);
            statements.add(jsVarsNewVar2);
        }
        KtExpression body = ktForExpression.getBody();
        if (body != null) {
            List<JsStatement> statements2 = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(Translation.translateAsStatement(body, translationContext.innerBlock(jsBlock)));
        }
        return new JsFor(jsVarsNewVar, jsExpressionSource, jsExpressionSource2, jsBlock);
    }

    private static final ClassDescriptor translateForExpression$findCollection(TranslationContext translationContext) {
        ModuleDescriptor currentModule = translationContext.getCurrentModule();
        Intrinsics.checkNotNullExpressionValue(currentModule, "getCurrentModule(...)");
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.collection);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(currentModule, classId);
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    private static final JsStatement translateForExpression$translateForOverCollectionIndices(TranslationContext translationContext, KtForExpression ktForExpression, WithIndexInfo withIndexInfo) {
        JsName jsNameDeclareTemporary;
        JsExpression jsExpressionCacheExpressionIfNeeded = translationContext.cacheExpressionIfNeeded(withIndexInfo.getRange());
        Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded, "cacheExpressionIfNeeded(...)");
        VariableDescriptor index = withIndexInfo.getIndex();
        if (index == null || (jsNameDeclareTemporary = translationContext.getNameForDescriptor(index)) == null) {
            jsNameDeclareTemporary = JsScope.declareTemporary();
        }
        Intrinsics.checkNotNull(jsNameDeclareTemporary);
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsNameDeclareTemporary, new JsIntLiteral(0));
        jsVarsNewVar.setSource(ktForExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "apply(...)");
        PropertyDescriptor propertyByName = DescriptorUtils.getPropertyByName(translateForExpression$findCollection(translationContext).getUnsubstitutedMemberScope(), Name.identifier("size"));
        Intrinsics.checkNotNullExpressionValue(propertyByName, "getPropertyByName(...)");
        JsName nameForDescriptor = translationContext.getNameForDescriptor(propertyByName);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        JsExpression jsExpressionSource = JsAstUtils.inequality(jsNameDeclareTemporary.makeRef(), new JsNameRef(nameForDescriptor, jsExpressionCacheExpressionIfNeeded)).source((Object) ktForExpression);
        JsExpression jsExpressionSource2 = new JsPrefixOperation(JsUnaryOperator.INC, jsNameDeclareTemporary.makeRef()).source((Object) ktForExpression);
        JsBlock jsBlock = new JsBlock();
        KtExpression body = ktForExpression.getBody();
        if (body != null) {
            List<JsStatement> statements = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(Translation.translateAsStatement(body, translationContext.innerBlock(jsBlock)));
        }
        return new JsFor(jsVarsNewVar, jsExpressionSource, jsExpressionSource2, jsBlock);
    }

    private static final ClassDescriptor translateForExpression$findIterable(TranslationContext translationContext) {
        ModuleDescriptor currentModule = translationContext.getCurrentModule();
        Intrinsics.checkNotNullExpressionValue(currentModule, "getCurrentModule(...)");
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.iterable);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(currentModule, classId);
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    private static final ClassDescriptor translateForExpression$findSequence(TranslationContext translationContext) {
        ModuleDescriptor currentModule = translationContext.getCurrentModule();
        Intrinsics.checkNotNullExpressionValue(currentModule, "getCurrentModule(...)");
        ClassId classId = ClassId.topLevel(sequenceFqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(currentModule, classId);
        Intrinsics.checkNotNull(classDescriptorFindClassAcrossModuleDependencies);
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    private static final JsStatement translateForExpression$translateForOverCollectionWithIndex(TranslationContext translationContext, KtForExpression ktForExpression, WithIndexInfo withIndexInfo) {
        JsVars jsVarsAsSyntheticStatement;
        JsExpression jsExpressionCacheExpressionIfNeeded = translationContext.cacheExpressionIfNeeded(withIndexInfo.getRange());
        Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded, "cacheExpressionIfNeeded(...)");
        VariableDescriptor index = withIndexInfo.getIndex();
        JsName nameForDescriptor = index != null ? translationContext.getNameForDescriptor(index) : null;
        VariableDescriptor value = withIndexInfo.getValue();
        JsName nameForDescriptor2 = value != null ? translationContext.getNameForDescriptor(value) : null;
        if (nameForDescriptor != null) {
            JsVars jsVarsNewVar = JsAstUtils.newVar(nameForDescriptor, new JsIntLiteral(0));
            jsVarsNewVar.setSource(ktForExpression);
            translationContext.addStatementToCurrentBlock(jsVarsNewVar);
        }
        JsName jsNameDeclareTemporary = JsScope.declareTemporary();
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporary, "declareTemporary(...)");
        FunctionDescriptor functionByName = DescriptorUtils.getFunctionByName((withIndexInfo.getReceiverType() == WithIndexReceiverType.SEQUENCE ? translateForExpression$findSequence(translationContext) : translateForExpression$findIterable(translationContext)).getUnsubstitutedMemberScope(), Name.identifier("iterator"));
        Intrinsics.checkNotNullExpressionValue(functionByName, "getFunctionByName(...)");
        JsName nameForDescriptor3 = translationContext.getNameForDescriptor(functionByName);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor3, "getNameForDescriptor(...)");
        JsVars jsVarsNewVar2 = JsAstUtils.newVar(jsNameDeclareTemporary, new JsInvocation(JsAstUtils.pureFqn(nameForDescriptor3, jsExpressionCacheExpressionIfNeeded), new JsExpression[0]));
        jsVarsNewVar2.setSource(ktForExpression);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar2, "apply(...)");
        KotlinType returnType = functionByName.getReturnType();
        Intrinsics.checkNotNull(returnType);
        ClassifierDescriptor this$0 = returnType.getConstructor().getThis$0();
        Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) this$0;
        FunctionDescriptor functionByName2 = DescriptorUtils.getFunctionByName(classDescriptor.getUnsubstitutedMemberScope(), Name.identifier("hasNext"));
        Intrinsics.checkNotNullExpressionValue(functionByName2, "getFunctionByName(...)");
        JsName nameForDescriptor4 = translationContext.getNameForDescriptor(functionByName2);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor4, "getNameForDescriptor(...)");
        JsExpression jsExpressionSource = new JsInvocation(JsAstUtils.pureFqn(nameForDescriptor4, jsNameDeclareTemporary.makeRef()), new JsExpression[0]).source((Object) ktForExpression);
        FunctionDescriptor functionByName3 = DescriptorUtils.getFunctionByName(classDescriptor.getUnsubstitutedMemberScope(), Name.identifier(ES6Iterator.NEXT_METHOD));
        Intrinsics.checkNotNullExpressionValue(functionByName3, "getFunctionByName(...)");
        JsName nameForDescriptor5 = translationContext.getNameForDescriptor(functionByName3);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor5, "getNameForDescriptor(...)");
        JsExpression jsExpressionSource2 = new JsInvocation(JsAstUtils.pureFqn(nameForDescriptor5, jsNameDeclareTemporary.makeRef()), new JsExpression[0]).source((Object) ktForExpression);
        JsExpression jsExpressionSource3 = nameForDescriptor != null ? new JsPrefixOperation(JsUnaryOperator.INC, nameForDescriptor.makeRef()).source((Object) ktForExpression) : null;
        JsBlock jsBlock = new JsBlock();
        List<JsStatement> statements = jsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        if (nameForDescriptor2 != null) {
            JsVars jsVarsNewVar3 = JsAstUtils.newVar(nameForDescriptor2, jsExpressionSource2);
            jsVarsNewVar3.setSource(ktForExpression);
            jsVarsAsSyntheticStatement = jsVarsNewVar3;
        } else {
            jsVarsAsSyntheticStatement = JsAstUtils.asSyntheticStatement(jsExpressionSource2);
        }
        list.add(jsVarsAsSyntheticStatement);
        KtExpression body = ktForExpression.getBody();
        if (body != null) {
            List<JsStatement> statements2 = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(Translation.translateAsStatement(body, translationContext.innerBlock(jsBlock)));
        }
        JsFor jsFor = new JsFor(jsVarsNewVar2, jsExpressionSource, jsExpressionSource3);
        jsFor.setBody(jsBlock);
        return jsFor;
    }

    private static final JsExpression translateForExpression$translateForOverIterator$translateMethodInvocation(TranslationContext translationContext, JsExpression jsExpression, ResolvedCall<FunctionDescriptor> resolvedCall, JsBlock jsBlock) {
        TranslationContext translationContextInnerBlock = translationContext.innerBlock(jsBlock);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
        return CallTranslator.translate(translationContextInnerBlock, resolvedCall, jsExpression);
    }

    private static final JsExpression translateForExpression$translateForOverIterator$iteratorMethodInvocation(KtExpression ktExpression, TranslationContext translationContext) {
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktExpression, translationContext);
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
        ResolvedCall<FunctionDescriptor> iteratorFunction = BindingUtils.getIteratorFunction(translationContext.bindingContext(), ktExpression);
        Intrinsics.checkNotNullExpressionValue(iteratorFunction, "getIteratorFunction(...)");
        return CallTranslator.translate(translationContext, iteratorFunction, jsExpressionTranslateAsExpression);
    }

    private static final JsStatement translateForExpression$translateForOverIterator(TranslationContext translationContext, KtExpression ktExpression, KtForExpression ktForExpression, KtDestructuringDeclaration ktDestructuringDeclaration, KtParameter ktParameter, JsName jsName) {
        JsExpression jsExpressionDefineTemporary = translationContext.defineTemporary(translateForExpression$translateForOverIterator$iteratorMethodInvocation(ktExpression, translationContext));
        Intrinsics.checkNotNullExpressionValue(jsExpressionDefineTemporary, "defineTemporary(...)");
        JsBlock jsBlock = new JsBlock();
        JsBooleanLiteral jsBooleanLiteralTranslateForExpression$translateForOverIterator$hasNextMethodInvocation = translateForExpression$translateForOverIterator$hasNextMethodInvocation(translationContext, ktExpression, jsExpressionDefineTemporary, jsBlock);
        JsBlock jsBlock2 = new JsBlock();
        ResolvedCall<FunctionDescriptor> nextFunction = BindingUtils.getNextFunction(translationContext.bindingContext(), ktExpression);
        Intrinsics.checkNotNullExpressionValue(nextFunction, "getNextFunction(...)");
        JsExpression jsExpressionTranslateForExpression$translateForOverIterator$translateMethodInvocation = translateForExpression$translateForOverIterator$translateMethodInvocation(translationContext, jsExpressionDefineTemporary, nextFunction, jsBlock2);
        ArrayList arrayList = new ArrayList();
        if (!jsBlock.isEmpty()) {
            ArrayList arrayList2 = arrayList;
            List<JsStatement> statements = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            CollectionsKt.addAll(arrayList2, statements);
            JsExpression jsExpressionNotOptimized = JsAstUtils.notOptimized(jsBooleanLiteralTranslateForExpression$translateForOverIterator$hasNextMethodInvocation);
            JsBreak jsBreak = new JsBreak();
            jsBreak.setSource(ktForExpression);
            JsIf jsIf = new JsIf(jsExpressionNotOptimized, jsBreak);
            jsIf.setSource(ktForExpression);
            arrayList2.add(jsIf);
            jsBooleanLiteralTranslateForExpression$translateForOverIterator$hasNextMethodInvocation = new JsBooleanLiteral(true);
        }
        JsExpression jsExpression = jsBooleanLiteralTranslateForExpression$translateForOverIterator$hasNextMethodInvocation;
        ArrayList arrayList3 = arrayList;
        List<JsStatement> statements2 = jsBlock2.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        CollectionsKt.addAll(arrayList3, statements2);
        JsStatement jsStatementTranslateForExpression$translateBody = translateForExpression$translateBody(ktForExpression, ktDestructuringDeclaration, translationContext, ktParameter, jsName, jsExpressionTranslateForExpression$translateForOverIterator$translateMethodInvocation);
        List<JsStatement> listFlattenStatement = jsStatementTranslateForExpression$translateBody != null ? JsAstUtils.flattenStatement(jsStatementTranslateForExpression$translateBody) : null;
        if (listFlattenStatement == null) {
            listFlattenStatement = CollectionsKt.emptyList();
        }
        CollectionsKt.addAll(arrayList3, listFlattenStatement);
        JsBlock jsBlock3 = (JsStatement) CollectionsKt.singleOrNull((List) arrayList);
        if (jsBlock3 == null) {
            jsBlock3 = new JsBlock(arrayList);
        }
        return new JsWhile(jsExpression, jsBlock3);
    }

    private static final JsExpression translateForExpression$translateForOverIterator$hasNextMethodInvocation(TranslationContext translationContext, KtExpression ktExpression, JsExpression jsExpression, JsBlock jsBlock) {
        ResolvedCall<FunctionDescriptor> hasNextCallable = BindingUtils.getHasNextCallable(translationContext.bindingContext(), ktExpression);
        Intrinsics.checkNotNullExpressionValue(hasNextCallable, "getHasNextCallable(...)");
        return translateForExpression$translateForOverIterator$translateMethodInvocation(translationContext, jsExpression, hasNextCallable, jsBlock);
    }
}
