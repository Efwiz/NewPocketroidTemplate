package org.jetbrains.kotlin.js.translate.expression;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cfg.WhenChecker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.operation.InOperationTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.mutator.CoercionMutator;
import org.jetbrains.kotlin.js.translate.utils.mutator.LastExpressionMutator;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactoryImpl;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: WhenTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 B2\u00020\u0001:\u0001BB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0094\u0001\u0010\u0016\u001a*\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0018\u0012\u0004\u0012\u00020\u00190\u0017j\u0002`\u001a0\u0018\u0012\u0004\u0012\u00020\u001b0\u0017\"\b\b\u0000\u0010\u001c*\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0018\u0010\u001f\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030!\u0012\u0006\u0012\u0004\u0018\u0001H\u001c0 2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u00020\f0 2\u0014\u0010#\u001a\u0010\u0012\u0004\u0012\u0002H\u001c\u0012\u0006\u0012\u0004\u0018\u00010\n0 H\u0002JN\u0010$\u001a*\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0018\u0012\u0004\u0012\u00020\u00190\u0017j\u0002`\u001a0\u0018\u0012\u0004\u0012\u00020\u001b0\u00172\u0006\u0010\u001d\u001a\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010%\u001a\u00020\u000fH\u0002JT\u0010&\u001a*\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u0018\u0012\u0004\u0012\u00020\u00190\u0017j\u0002`\u001a0\u0018\u0012\u0004\u0012\u00020\u001b0\u00172\u0006\u0010\u001d\u001a\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000f0(H\u0002J\b\u0010)\u001a\u00020*H\u0002J\u001e\u0010+\u001a\u0010\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u00172\u0006\u0010\u001d\u001a\u00020\u001bH\u0002J\u0018\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u00100\u001a\u00020\n2\u0006\u00101\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J \u00102\u001a\u0002032\u0006\u00101\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00104\u001a\u000205H\u0002J\u0018\u00106\u001a\u00020\n2\u0006\u0010.\u001a\u0002072\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u00108\u001a\u00020\n2\u0006\u00109\u001a\u00020:2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J \u0010;\u001a\u00020\n2\u0006\u0010<\u001a\u00020\n2\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u0010=\u001a\u00020\n2\u0006\u0010.\u001a\u00020>2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u0010?\u001a\u00020\n2\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u000e\u0010@\u001a\u0004\u0018\u00010A*\u00020\u000fH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/WhenTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "whenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/psi/KtWhenExpression;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "expressionToMatch", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "isExhaustive", "", "()Z", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "uniqueConstants", "", "", "uniqueEnumNames", "", "collectConstantEntries", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "Lorg/jetbrains/kotlin/js/translate/expression/EntryWithConstants;", "", ExifInterface.GPS_DIRECTION_TRUE, "fromIndex", TypeBlock.NAME_entries, "extractor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "filter", "wrapper", "collectEnumEntries", "expectedType", "collectPrimitiveConstantEntries", "expectedTypes", "", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "translateAsSwitch", "Lorg/jetbrains/kotlin/js/backend/ast/JsSwitch;", "translateCondition", "condition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "translateConditions", "entry", "translateEntryExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "block", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "translateExpressionCondition", "Lorg/jetbrains/kotlin/psi/KtWhenConditionWithExpression;", "translateIsCondition", "conditionIsPattern", "Lorg/jetbrains/kotlin/psi/KtWhenConditionIsPattern;", "translateOrCondition", "leftExpression", "translateRangeCondition", "Lorg/jetbrains/kotlin/psi/KtWhenConditionInRange;", "translateWhenConditionToBooleanExpression", "getEnumClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WhenTranslator extends AbstractTranslator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final DataFlowValueFactory dataFlowValueFactory;
    private final JsExpression expressionToMatch;
    private final KotlinType subjectType;
    private final KotlinType type;
    private final Set<Object> uniqueConstants;
    private final Set<String> uniqueEnumNames;
    private final KtWhenExpression whenExpression;

    public /* synthetic */ WhenTranslator(KtWhenExpression ktWhenExpression, TranslationContext translationContext, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktWhenExpression, translationContext);
    }

    @JvmStatic
    public static final JsNode translate(KtWhenExpression ktWhenExpression, TranslationContext translationContext) {
        return INSTANCE.translate(ktWhenExpression, translationContext);
    }

    private WhenTranslator(KtWhenExpression ktWhenExpression, TranslationContext translationContext) {
        super(translationContext);
        this.whenExpression = ktWhenExpression;
        this.uniqueConstants = new LinkedHashSet();
        this.uniqueEnumNames = new LinkedHashSet();
        LanguageVersionSettings languageVersionSettings = translationContext.getLanguageVersionSettings();
        Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "getLanguageVersionSettings(...)");
        this.dataFlowValueFactory = new DataFlowValueFactoryImpl(languageVersionSettings);
        KtProperty subjectVariable = ktWhenExpression.getSubjectVariable();
        KtExpression subjectExpression = ktWhenExpression.getSubjectExpression();
        if (subjectVariable != null) {
            JsStatement jsStatementTranslateAsStatement = Translation.translateAsStatement(subjectVariable, translationContext);
            Intrinsics.checkNotNull(jsStatementTranslateAsStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsVars");
            JsVars jsVars = (JsVars) jsStatementTranslateAsStatement;
            translationContext.addStatementToCurrentBlock(jsVars);
            DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(translationContext.bindingContext(), subjectVariable);
            CallableDescriptor callableDescriptor = descriptorForElement instanceof CallableDescriptor ? (CallableDescriptor) descriptorForElement : null;
            this.subjectType = callableDescriptor != null ? callableDescriptor.getReturnType() : null;
            List<JsVars.JsVar> vars = jsVars.getVars();
            Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
            this.expressionToMatch = ((JsVars.JsVar) CollectionsKt.first((List) vars)).getName().makeRef();
        } else if (subjectExpression != null) {
            this.subjectType = bindingContext().getType(subjectExpression);
            this.expressionToMatch = translationContext.defineTemporary(Translation.translateAsExpression(subjectExpression, translationContext));
        } else {
            this.subjectType = null;
            this.expressionToMatch = null;
        }
        this.type = bindingContext().getType(ktWhenExpression);
    }

    private final boolean isExhaustive() {
        KotlinType type = bindingContext().getType(this.whenExpression);
        boolean z = (type == null || !KotlinBuiltIns.isUnit(type) || type.getIsMarkedNullable()) ? false : true;
        BindingContext bindingContext = bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        return CodegenUtil.isExhaustive(bindingContext, this.whenExpression, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsNode translate() {
        boolean z;
        Object jsNullLiteral;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Function1<JsStatement, Unit> function1 = new Function1<JsStatement, Unit>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator$translate$setWhenStatement$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JsStatement jsStatement) {
                invoke2(jsStatement);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsStatement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                objectRef.element = it;
            }
        };
        int i = 0;
        while (true) {
            if (i >= this.whenExpression.getEntries().size()) {
                z = false;
                break;
            }
            Pair<JsSwitch, Integer> pairTranslateAsSwitch = translateAsSwitch(i);
            if (pairTranslateAsSwitch != null) {
                final JsSwitch jsSwitchComponent1 = pairTranslateAsSwitch.component1();
                int iIntValue = pairTranslateAsSwitch.component2().intValue();
                function1.invoke(jsSwitchComponent1);
                function1 = new Function1<JsStatement, Unit>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.translate.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(JsStatement jsStatement) {
                        invoke2(jsStatement);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(JsStatement whenStatement) {
                        Intrinsics.checkNotNullParameter(whenStatement, "whenStatement");
                        List<JsSwitchMember> cases = jsSwitchComponent1.getCases();
                        Intrinsics.checkNotNullExpressionValue(cases, "getCases(...)");
                        JsDefault jsDefault = new JsDefault();
                        WhenTranslator whenTranslator = this;
                        List<JsStatement> statements = jsDefault.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                        statements.add(whenStatement);
                        List<JsStatement> statements2 = jsDefault.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                        JsBreak jsBreak = new JsBreak();
                        jsBreak.setSource(whenTranslator.whenExpression);
                        statements2.add(jsBreak);
                        cases.add(jsDefault);
                    }
                };
                i = iIntValue;
            } else {
                int i2 = i + 1;
                KtWhenEntry ktWhenEntry = this.whenExpression.getEntries().get(i);
                JsBlock jsBlock = new JsBlock();
                Intrinsics.checkNotNull(ktWhenEntry);
                TranslationContext translationContextContext = context();
                Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
                JsStatement jsStatementTranslateEntryExpression = translateEntryExpression(ktWhenEntry, translationContextContext, jsBlock);
                if (objectRef.element == 0 && ktWhenEntry.isElse()) {
                    context().addStatementsToCurrentBlockFrom(jsBlock);
                    return jsStatementTranslateEntryExpression;
                }
                JsStatement jsStatementMergeStatementInBlockIfNeeded = JsAstUtils.mergeStatementInBlockIfNeeded(jsStatementTranslateEntryExpression, jsBlock);
                Intrinsics.checkNotNullExpressionValue(jsStatementMergeStatementInBlockIfNeeded, "mergeStatementInBlockIfNeeded(...)");
                JsBlock jsBlock2 = new JsBlock();
                if (ktWhenEntry.isElse()) {
                    function1.invoke(jsStatementMergeStatementInBlockIfNeeded);
                    z = true;
                    break;
                }
                TranslationContext translationContextInnerBlock = context().innerBlock(jsBlock2);
                Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
                final JsIf jsIfNewJsIf = JsAstUtils.newJsIf(translateConditions(ktWhenEntry, translationContextInnerBlock), jsStatementMergeStatementInBlockIfNeeded);
                Intrinsics.checkNotNullExpressionValue(jsIfNewJsIf, "newJsIf(...)");
                jsIfNewJsIf.setSource(ktWhenEntry);
                JsStatement jsStatementMergeStatementInBlockIfNeeded2 = JsAstUtils.mergeStatementInBlockIfNeeded(jsIfNewJsIf, jsBlock2);
                Intrinsics.checkNotNullExpressionValue(jsStatementMergeStatementInBlockIfNeeded2, "mergeStatementInBlockIfNeeded(...)");
                function1.invoke(jsStatementMergeStatementInBlockIfNeeded2);
                function1 = new Function1<JsStatement, Unit>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.translate.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(JsStatement jsStatement) {
                        invoke2(jsStatement);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(JsStatement it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        jsIfNewJsIf.setElseStatement(it);
                    }
                };
                i = i2;
            }
        }
        if (isExhaustive() && !z) {
            JsStatement jsStatementAsSyntheticStatement = JsAstUtils.asSyntheticStatement(new JsInvocation(JsAstUtils.pureFqn("noWhenBranchMatched", Namer.kotlinObject()), new JsExpression[0]));
            Intrinsics.checkNotNullExpressionValue(jsStatementAsSyntheticStatement, "asSyntheticStatement(...)");
            function1.invoke(jsStatementAsSyntheticStatement);
        }
        if (objectRef.element != 0) {
            jsNullLiteral = objectRef.element;
            Intrinsics.checkNotNull(jsNullLiteral);
        } else {
            jsNullLiteral = new JsNullLiteral();
        }
        return (JsNode) jsNullLiteral;
    }

    private final Pair<JsSwitch, Integer> translateAsSwitch(int fromIndex) {
        KtExpression subjectExpression;
        Pair<List<Pair<List<JsExpression>, KtWhenEntry>>, Integer> pairCollectPrimitiveConstantEntries;
        KotlinType kotlinType = this.subjectType;
        if (kotlinType == null || (subjectExpression = this.whenExpression.getSubjectExpression()) == null) {
            return null;
        }
        DataFlowValueFactory dataFlowValueFactory = this.dataFlowValueFactory;
        BindingContext bindingContext = bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        ModuleDescriptor declarationDescriptor = context().getDeclarationDescriptor();
        if (declarationDescriptor == null) {
            declarationDescriptor = context().getCurrentModule();
        }
        Intrinsics.checkNotNull(declarationDescriptor);
        DataFlowValue dataFlowValueCreateDataFlowValue = dataFlowValueFactory.createDataFlowValue(subjectExpression, kotlinType, bindingContext, declarationDescriptor);
        CompilerConfiguration configuration = context().getConfig().getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
        BindingContext bindingContext2 = bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext2, "bindingContext(...)");
        Set<? extends KotlinType> setPlus = SetsKt.plus((Set) BindingContextUtilsKt.getDataFlowInfoBefore(bindingContext2, subjectExpression).getStableTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings), (Iterable) SetsKt.setOf(kotlinType));
        final JsExpression jsExpression = this.expressionToMatch;
        if (jsExpression == null) {
            return null;
        }
        Function0<JsExpression> function0 = new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator$translateAsSwitch$subjectSupplier$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return jsExpression;
            }
        };
        final ClassDescriptor classDescriptor = (ClassDescriptor) SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(setPlus), new Function1<KotlinType, ClassDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator$translateAsSwitch$enumClass$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getEnumClass(it);
            }
        }));
        if (classDescriptor != null) {
            function0 = new Function0<JsNameRef>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.translateAsSwitch.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final JsNameRef invoke() {
                    PropertyDescriptor propertyByName = DescriptorUtils.getPropertyByName(DescriptorUtilsKt.getSuperClassOrAny(classDescriptor).getUnsubstitutedMemberScope(), StandardNames.NAME);
                    Intrinsics.checkNotNullExpressionValue(propertyByName, "getPropertyByName(...)");
                    return new JsNameRef(this.context().getNameForDescriptor(propertyByName), jsExpression);
                }
            };
            List<KtWhenEntry> entries = this.whenExpression.getEntries();
            Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
            SimpleType defaultType = classDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            pairCollectPrimitiveConstantEntries = collectEnumEntries(fromIndex, entries, defaultType);
        } else {
            List<KtWhenEntry> entries2 = this.whenExpression.getEntries();
            Intrinsics.checkNotNullExpressionValue(entries2, "getEntries(...)");
            pairCollectPrimitiveConstantEntries = collectPrimitiveConstantEntries(fromIndex, entries2, setPlus);
        }
        List<Pair<List<JsExpression>, KtWhenEntry>> listComponent1 = pairCollectPrimitiveConstantEntries.component1();
        int iIntValue = pairCollectPrimitiveConstantEntries.component2().intValue();
        List<Pair<List<JsExpression>, KtWhenEntry>> list = listComponent1;
        if (SequencesKt.sumOfInt(SequencesKt.map(CollectionsKt.asSequence(list), new Function1<Pair<? extends List<? extends JsExpression>, ? extends KtWhenEntry>, Integer>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.translateAsSwitch.2
            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(Pair<? extends List<? extends JsExpression>, ? extends KtWhenEntry> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(it.getFirst().size());
            }
        })) <= 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            List list2 = (List) pair.component1();
            KtWhenEntry ktWhenEntry = (KtWhenEntry) pair.component2();
            List<JsExpression> list3 = list2;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            for (JsExpression jsExpression2 : list3) {
                JsCase jsCase = new JsCase();
                jsCase.setCaseExpression(jsExpression2.source((Object) ktWhenEntry));
                arrayList2.add(jsCase);
            }
            ArrayList arrayList3 = arrayList2;
            JsBlock jsBlock = new JsBlock();
            TranslationContext translationContextContext = context();
            Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
            JsStatement jsStatementTranslateEntryExpression = translateEntryExpression(ktWhenEntry, translationContextContext, jsBlock);
            JsCase jsCase2 = (JsCase) CollectionsKt.last((List) arrayList3);
            List<JsStatement> statements = jsCase2.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            List<JsStatement> statements2 = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
            List<JsStatement> statements3 = jsCase2.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            statements3.add(jsStatementTranslateEntryExpression);
            List<JsStatement> statements4 = jsCase2.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            JsBreak jsBreak = new JsBreak();
            jsBreak.setSource(ktWhenEntry);
            statements4.add(jsBreak);
            CollectionsKt.addAll(arrayList, arrayList3);
        }
        JsSwitch jsSwitch = new JsSwitch(function0.invoke(), arrayList);
        jsSwitch.setSource(this.whenExpression);
        return new Pair<>(jsSwitch, Integer.valueOf(iIntValue));
    }

    private final Pair<List<Pair<List<JsExpression>, KtWhenEntry>>, Integer> collectPrimitiveConstantEntries(int fromIndex, List<? extends KtWhenEntry> entries, final Set<? extends KotlinType> expectedTypes) {
        return collectConstantEntries(fromIndex, entries, new Function1<CompileTimeConstant<?>, Object>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectPrimitiveConstantEntries.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(final CompileTimeConstant<?> constant) {
                Intrinsics.checkNotNullParameter(constant, "constant");
                return SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(expectedTypes), new Function1<KotlinType, Object>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectPrimitiveConstantEntries.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return constant.getValue(it);
                    }
                }));
            }
        }, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectPrimitiveConstantEntries.2
            {
                super(1);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(WhenTranslator.this.uniqueConstants.add(it));
            }
        }, new Function1<Object, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectPrimitiveConstantEntries.3
            @Override // kotlin.jvm.functions.Function1
            public final JsExpression invoke(Object it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof String) {
                    return new JsStringLiteral((String) it);
                }
                if (it instanceof Integer) {
                    return new JsIntLiteral(((Number) it).intValue());
                }
                if (it instanceof Short) {
                    return new JsIntLiteral(((Number) it).shortValue());
                }
                if (it instanceof Byte) {
                    return new JsIntLiteral(((Number) it).byteValue());
                }
                if (it instanceof Character) {
                    return new JsIntLiteral(((Character) it).charValue());
                }
                return null;
            }
        });
    }

    private final Pair<List<Pair<List<JsExpression>, KtWhenEntry>>, Integer> collectEnumEntries(int fromIndex, List<? extends KtWhenEntry> entries, final KotlinType expectedType) {
        final ClassId classIdForTypeIfEnum = WhenChecker.getClassIdForTypeIfEnum(expectedType);
        return collectConstantEntries(fromIndex, entries, new Function1<CompileTimeConstant<?>, String>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectEnumEntries.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CompileTimeConstant<?> it) {
                Name enumEntryName;
                Intrinsics.checkNotNullParameter(it, "it");
                ConstantValue<?> constantValue = it.toConstantValue(expectedType);
                EnumValue enumValue = constantValue instanceof EnumValue ? (EnumValue) constantValue : null;
                if (enumValue == null) {
                    return null;
                }
                if (!Intrinsics.areEqual(enumValue.getEnumClassId(), classIdForTypeIfEnum)) {
                    enumValue = null;
                }
                if (enumValue == null || (enumEntryName = enumValue.getEnumEntryName()) == null) {
                    return null;
                }
                return enumEntryName.getIdentifier();
            }
        }, new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectEnumEntries.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(WhenTranslator.this.uniqueEnumNames.add(it));
            }
        }, new Function1<String, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.expression.WhenTranslator.collectEnumEntries.3
            @Override // kotlin.jvm.functions.Function1
            public final JsExpression invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JsStringLiteral(it);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0068  */
    private final <T> Pair<List<Pair<List<JsExpression>, KtWhenEntry>>, Integer> collectConstantEntries(int fromIndex, List<? extends KtWhenEntry> entries, Function1<? super CompileTimeConstant<?>, ? extends T> extractor, Function1<? super T, Boolean> filter, Function1<? super T, ? extends JsExpression> wrapper) {
        ArrayList arrayList = new ArrayList();
        int i = fromIndex;
        while (i < entries.size()) {
            KtWhenEntry ktWhenEntry = entries.get(i);
            if (ktWhenEntry.isElse()) {
                break;
            }
            KtWhenCondition[] conditions = ktWhenEntry.getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            KtWhenCondition[] ktWhenConditionArr = conditions;
            ArrayList arrayList2 = new ArrayList();
            int length = ktWhenConditionArr.length;
            int i2 = 0;
            boolean z = false;
            while (true) {
                T t = null;
                if (i2 >= length) {
                    break;
                }
                KtWhenCondition ktWhenCondition = ktWhenConditionArr[i2];
                KtWhenConditionWithExpression ktWhenConditionWithExpression = ktWhenCondition instanceof KtWhenConditionWithExpression ? (KtWhenConditionWithExpression) ktWhenCondition : null;
                KtExpression expression = ktWhenConditionWithExpression != null ? ktWhenConditionWithExpression.getExpression() : null;
                if (expression != null) {
                    ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
                    BindingContext bindingContext = bindingContext();
                    Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
                    CompileTimeConstant<?> constant = companion.getConstant(expression, bindingContext);
                    if (constant != null) {
                        T tInvoke = extractor.invoke(constant);
                        if (tInvoke != null) {
                            t = tInvoke;
                        }
                    }
                    z = true;
                } else {
                    z = true;
                }
                if (t != null) {
                    arrayList2.add(t);
                }
                i2++;
            }
            ArrayList arrayList3 = arrayList2;
            if (z) {
                break;
            }
            ArrayList arrayList4 = new ArrayList();
            for (T t2 : arrayList3) {
                if (filter.invoke(t2).booleanValue()) {
                    arrayList4.add(t2);
                }
            }
            ArrayList arrayList5 = new ArrayList();
            Iterator<T> it = arrayList4.iterator();
            while (it.hasNext()) {
                JsExpression jsExpressionInvoke = wrapper.invoke(it.next());
                if (jsExpressionInvoke == null) {
                    z = true;
                    jsExpressionInvoke = null;
                }
                if (jsExpressionInvoke != null) {
                    arrayList5.add(jsExpressionInvoke);
                }
            }
            ArrayList arrayList6 = arrayList5;
            if (z) {
                break;
            }
            if (!arrayList6.isEmpty()) {
                arrayList.add(new Pair(arrayList6, ktWhenEntry));
            }
            i++;
        }
        return new Pair<>(arrayList, Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor getEnumClass(KotlinType kotlinType) {
        if (kotlinType.getIsMarkedNullable()) {
            return null;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if ((classDescriptor != null ? classDescriptor.getClassKind() : null) != ClassKind.ENUM_CLASS || classDescriptor.isExternal()) {
            return null;
        }
        return classDescriptor;
    }

    private final JsStatement translateEntryExpression(KtWhenEntry entry, TranslationContext context, JsBlock block) {
        KtExpression expression = entry.getExpression();
        if (expression == null) {
            throw new IllegalStateException("WhenEntry should have whenExpression to execute.".toString());
        }
        JsStatement jsStatementTranslateAsStatement = Translation.translateAsStatement(expression, context, block);
        Intrinsics.checkNotNullExpressionValue(jsStatementTranslateAsStatement, "translateAsStatement(...)");
        if (this.type == null) {
            return jsStatementTranslateAsStatement;
        }
        JsStatement jsStatementMutateLastExpression = LastExpressionMutator.mutateLastExpression(jsStatementTranslateAsStatement, new CoercionMutator(this.type, context));
        Intrinsics.checkNotNull(jsStatementMutateLastExpression);
        return jsStatementMutateLastExpression;
    }

    private final JsExpression translateConditions(KtWhenEntry entry, TranslationContext context) {
        KtWhenCondition[] conditions = entry.getConditions();
        Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
        int length = conditions.length;
        KtWhenCondition ktWhenCondition = conditions[0];
        Intrinsics.checkNotNullExpressionValue(ktWhenCondition, "get(...)");
        JsExpression jsExpressionTranslateCondition = translateCondition(ktWhenCondition, context);
        for (KtWhenCondition ktWhenCondition2 : SequencesKt.drop(ArraysKt.asSequence(conditions), 1)) {
            Intrinsics.checkNotNull(ktWhenCondition2);
            jsExpressionTranslateCondition = translateOrCondition(jsExpressionTranslateCondition, ktWhenCondition2, context);
        }
        return jsExpressionTranslateCondition;
    }

    private final JsExpression translateOrCondition(JsExpression leftExpression, KtWhenCondition condition, TranslationContext context) {
        TranslationContext translationContextInnerBlock = context.innerBlock();
        Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
        JsExpression jsExpressionTranslateCondition = translateCondition(condition, translationContextInnerBlock);
        context.moveVarsFrom(translationContextInnerBlock);
        if (translationContextInnerBlock.currentBlockIsEmpty()) {
            return new JsBinaryOperation(JsBinaryOperator.OR, leftExpression, jsExpressionTranslateCondition);
        }
        boolean z = jsExpressionTranslateCondition instanceof JsNameRef;
        Intrinsics.checkNotNull(jsExpressionTranslateCondition, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
        JsNameRef jsNameRef = (JsNameRef) jsExpressionTranslateCondition;
        JsIf jsIfNewJsIf = JsAstUtils.newJsIf(leftExpression, JsAstUtils.assignment(jsNameRef, new JsBooleanLiteral(true)).makeStmt(), translationContextInnerBlock.getCurrentBlock());
        Intrinsics.checkNotNullExpressionValue(jsIfNewJsIf, "newJsIf(...)");
        jsIfNewJsIf.setSource(condition);
        context.addStatementToCurrentBlock(jsIfNewJsIf);
        return jsNameRef;
    }

    private final JsExpression translateCondition(KtWhenCondition condition, TranslationContext context) {
        JsExpression jsExpressionTranslateWhenConditionToBooleanExpression = translateWhenConditionToBooleanExpression(condition, context);
        if (!INSTANCE.isNegated(condition)) {
            return jsExpressionTranslateWhenConditionToBooleanExpression;
        }
        JsPrefixOperation jsPrefixOperationNot = JsAstUtils.not(jsExpressionTranslateWhenConditionToBooleanExpression);
        Intrinsics.checkNotNullExpressionValue(jsPrefixOperationNot, "not(...)");
        return jsPrefixOperationNot;
    }

    private final JsExpression translateWhenConditionToBooleanExpression(KtWhenCondition condition, TranslationContext context) {
        if (condition instanceof KtWhenConditionIsPattern) {
            return translateIsCondition((KtWhenConditionIsPattern) condition, context);
        }
        if (condition instanceof KtWhenConditionWithExpression) {
            return translateExpressionCondition((KtWhenConditionWithExpression) condition, context);
        }
        if (condition instanceof KtWhenConditionInRange) {
            return translateRangeCondition((KtWhenConditionInRange) condition, context);
        }
        throw new IllegalStateException(("Unsupported when condition " + condition.getClass()).toString());
    }

    private final JsExpression translateIsCondition(KtWhenConditionIsPattern conditionIsPattern, TranslationContext context) {
        JsExpression jsExpression = this.expressionToMatch;
        if (jsExpression == null) {
            throw new IllegalStateException("An is-check is not allowed in when() without subject.".toString());
        }
        KtTypeReference typeReference = conditionIsPattern.getTypeReference();
        if (typeReference == null) {
            throw new IllegalStateException("An is-check must have a type reference.".toString());
        }
        JsBooleanLiteral jsBooleanLiteralTranslateIsCheck = Translation.patternTranslator(context).translateIsCheck(jsExpression, typeReference);
        if (jsBooleanLiteralTranslateIsCheck == null) {
            jsBooleanLiteralTranslateIsCheck = new JsBooleanLiteral(true);
        }
        JsExpression jsExpressionSource = jsBooleanLiteralTranslateIsCheck.source((Object) conditionIsPattern);
        Intrinsics.checkNotNullExpressionValue(jsExpressionSource, "source(...)");
        return jsExpressionSource;
    }

    private final JsExpression translateExpressionCondition(KtWhenConditionWithExpression condition, TranslationContext context) {
        KtExpression expression = condition.getExpression();
        if (expression == null) {
            throw new IllegalStateException("Expression pattern should have an expression.".toString());
        }
        PatternTranslator patternTranslator = Translation.patternTranslator(context);
        Intrinsics.checkNotNullExpressionValue(patternTranslator, "patternTranslator(...)");
        if (this.expressionToMatch == null) {
            JsExpression jsExpressionTranslateExpressionForExpressionPattern = patternTranslator.translateExpressionForExpressionPattern(expression);
            Intrinsics.checkNotNull(jsExpressionTranslateExpressionForExpressionPattern);
            return jsExpressionTranslateExpressionForExpressionPattern;
        }
        KotlinType kotlinType = this.subjectType;
        Intrinsics.checkNotNull(kotlinType);
        JsExpression jsExpressionTranslateExpressionPattern = patternTranslator.translateExpressionPattern(kotlinType, this.expressionToMatch, expression);
        Intrinsics.checkNotNull(jsExpressionTranslateExpressionPattern);
        return jsExpressionTranslateExpressionPattern;
    }

    private final JsExpression translateRangeCondition(KtWhenConditionInRange condition, TranslationContext context) {
        JsExpression jsExpression = this.expressionToMatch;
        if (jsExpression == null) {
            throw new IllegalStateException(("Range pattern is only available for 'when (C) { in ... }'  expressions: " + PsiUtilsKt.getTextWithLocation(condition)).toString());
        }
        HashMap map = new HashMap();
        KtExpression subjectExpression = this.whenExpression.getSubjectExpression();
        Intrinsics.checkNotNull(subjectExpression);
        map.put(subjectExpression, jsExpression);
        TranslationContext translationContextInnerContextWithAliasesForExpressions = context.innerContextWithAliasesForExpressions(map);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithAliasesForExpressions, "innerContextWithAliasesForExpressions(...)");
        boolean z = condition.getOperationReference().getReferencedNameElementType() == KtTokens.NOT_IN;
        KtExpression rangeExpression = condition.getRangeExpression();
        Intrinsics.checkNotNull(rangeExpression);
        JsExpression jsExpressionSource = new InOperationTranslator(translationContextInnerContextWithAliasesForExpressions, jsExpression, rangeExpression, condition.getOperationReference(), z).translate().source((Object) condition);
        Intrinsics.checkNotNullExpressionValue(jsExpressionSource, "source(...)");
        return jsExpressionSource;
    }

    /* JADX INFO: compiled from: WhenTranslator.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/WhenTranslator$Companion;", "", "()V", "isNegated", "", "condition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JsNode translate(KtWhenExpression expression, TranslationContext context) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(context, "context");
            return new WhenTranslator(expression, context, null).translate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isNegated(KtWhenCondition condition) {
            KtWhenConditionIsPattern ktWhenConditionIsPattern = condition instanceof KtWhenConditionIsPattern ? (KtWhenConditionIsPattern) condition : null;
            if (ktWhenConditionIsPattern != null) {
                return ktWhenConditionIsPattern.isNegated();
            }
            return false;
        }
    }
}
