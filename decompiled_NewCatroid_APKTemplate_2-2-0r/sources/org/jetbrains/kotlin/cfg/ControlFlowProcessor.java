package org.jetbrains.kotlin.cfg;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cfg.pseudocode.ControlFlowInstructionsGenerator;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartFMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtInitializerList;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtScriptInitializer;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStatementExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.TowerLevelsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ControlFlowProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0016\u0017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0015H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "builder", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "subroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "generateImplicitReturnValue", "", "bodyExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generatePseudocode", "processLocalDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "CFPVisitor", "CatchFinallyLabels", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowProcessor {
    private final ControlFlowBuilder builder;
    private final LanguageVersionSettings languageVersionSettings;
    private final BindingTrace trace;

    public ControlFlowProcessor(BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.trace = trace;
        this.languageVersionSettings = languageVersionSettings;
        this.builder = new ControlFlowInstructionsGenerator();
    }

    public final Pseudocode generatePseudocode(KtElement subroutine) {
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Pseudocode pseudocodeGenerate = generate(subroutine, null);
        Intrinsics.checkNotNull(pseudocodeGenerate, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.PseudocodeImpl");
        ((PseudocodeImpl) pseudocodeGenerate).postProcess();
        return pseudocodeGenerate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pseudocode generate(KtElement subroutine, EventOccurrencesRange eventOccurrencesRange) {
        this.builder.enterSubroutine(subroutine, eventOccurrencesRange);
        CFPVisitor cFPVisitor = new CFPVisitor(this, this.builder);
        if ((subroutine instanceof KtDeclarationWithBody) && !(subroutine instanceof KtSecondaryConstructor)) {
            KtDeclarationWithBody ktDeclarationWithBody = (KtDeclarationWithBody) subroutine;
            List<KtParameter> valueParameters = ktDeclarationWithBody.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            Iterator<KtParameter> it = valueParameters.iterator();
            while (it.hasNext()) {
                cFPVisitor.generateInstructions(it.next());
            }
            KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
            if (bodyExpression != null) {
                cFPVisitor.generateInstructions(bodyExpression);
                if (!ktDeclarationWithBody.hasBlockBody()) {
                    generateImplicitReturnValue(bodyExpression, subroutine);
                }
            }
        } else {
            cFPVisitor.generateInstructions(subroutine);
        }
        return this.builder.exitSubroutine(subroutine, eventOccurrencesRange);
    }

    private final void generateImplicitReturnValue(KtExpression bodyExpression, KtElement subroutine) {
        PseudoValue boundValue;
        CallableDescriptor callableDescriptor = (CallableDescriptor) this.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, subroutine);
        if (callableDescriptor == null) {
            return;
        }
        KotlinType returnType = callableDescriptor.getReturnType();
        if ((returnType != null && KotlinBuiltIns.isUnit(returnType) && (callableDescriptor instanceof AnonymousFunctionDescriptor)) || (boundValue = this.builder.getBoundValue(bodyExpression)) == null) {
            return;
        }
        this.builder.returnValue(bodyExpression, boundValue, subroutine);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processLocalDeclaration(KtDeclaration subroutine) {
        Label labelCreateUnboundLabel = this.builder.createUnboundLabel("after local declaration");
        KtDeclaration ktDeclaration = subroutine;
        this.builder.nondeterministicJump(labelCreateUnboundLabel, ktDeclaration, null);
        generate(ktDeclaration, null);
        this.builder.bindLabel(labelCreateUnboundLabel);
    }

    /* JADX INFO: compiled from: ControlFlowProcessor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CatchFinallyLabels;", "", "onException", "Lorg/jetbrains/kotlin/cfg/Label;", "toFinally", "tryExpression", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "(Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/psi/KtTryExpression;)V", "getOnException", "()Lorg/jetbrains/kotlin/cfg/Label;", "getToFinally", "getTryExpression", "()Lorg/jetbrains/kotlin/psi/KtTryExpression;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CatchFinallyLabels {
        private final Label onException;
        private final Label toFinally;
        private final KtTryExpression tryExpression;

        public CatchFinallyLabels(Label label, Label label2, KtTryExpression ktTryExpression) {
            this.onException = label;
            this.toFinally = label2;
            this.tryExpression = ktTryExpression;
        }

        public final Label getOnException() {
            return this.onException;
        }

        public final Label getToFinally() {
            return this.toFinally;
        }

        public final KtTryExpression getTryExpression() {
            return this.tryExpression;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ControlFlowProcessor.kt */
    @Metadata(d1 = {"\u0000Ã\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\t\b\u0082\u0004\u0018\u00002\u00020\u0001:\u0002ï\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u001a\u0010\u001f\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u00162\u0006\u0010!\u001a\u00020\u0016H\u0002J(\u0010\"\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u00162\u000e\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160#2\u0006\u0010$\u001a\u00020%H\u0002J5\u0010\"\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u00162\u0006\u0010$\u001a\u00020%2\u0016\u0010 \u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00160&\"\u0004\u0018\u00010\u0016H\u0002¢\u0006\u0002\u0010'J1\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u00162\u0006\u0010$\u001a\u00020%2\u0012\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00160&\"\u00020\u0016H\u0002¢\u0006\u0002\u0010'J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,H\u0002J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001c0#2\u000e\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160#H\u0002J\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010+\u001a\u00020/H\u0002J\u001e\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u0002022\f\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0013H\u0002J\u0016\u00103\u001a\b\u0012\u0004\u0012\u00020/0#2\u0006\u00101\u001a\u000202H\u0002J\u0010\u00104\u001a\u00020\u000e2\u0006\u00101\u001a\u000202H\u0002J(\u00105\u001a\u00020\u000e2\u0006\u00106\u001a\u0002022\u000e\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c082\u0006\u00109\u001a\u00020/H\u0002J\u0010\u0010:\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020;H\u0002J\u0010\u0010=\u001a\u00020\u00112\u0006\u0010>\u001a\u00020\u0016H\u0002J\u0014\u0010=\u001a\u00020?2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0002J\u0010\u0010@\u001a\u00020\u000e2\u0006\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020\u000e2\u0006\u0010D\u001a\u00020EH\u0002J\u0010\u0010F\u001a\u00020\u000e2\u0006\u0010D\u001a\u00020EH\u0002J\u0018\u0010G\u001a\u00020\u000e2\u0006\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u001cH\u0002J\u0010\u0010K\u001a\u00020\u000e2\u0006\u0010D\u001a\u00020LH\u0002J\u0010\u0010M\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\b\u0010N\u001a\u00020\u000eH\u0002J,\u0010O\u001a\u00020\u000e2\b\u0010P\u001a\u0004\u0018\u00010/2\u0018\u0010Q\u001a\u0014\u0012\u0004\u0012\u00020/\u0012\n\u0012\b\u0012\u0004\u0012\u00020S0\u00130RH\u0002J\u0018\u0010T\u001a\u00020\u00112\u0006\u0010+\u001a\u00020/2\u0006\u0010U\u001a\u00020VH\u0002J\u0012\u0010W\u001a\u0004\u0018\u00010X2\u0006\u0010+\u001a\u00020YH\u0002J8\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\\0[2\u0006\u0010]\u001a\u00020^2\u0006\u0010_\u001a\u00020\\2\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\\0[H\u0002J2\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\\0[2\u000e\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c082\f\u0010b\u001a\b\u0012\u0004\u0012\u00020S0\u0013H\u0002J\u0014\u0010c\u001a\u0004\u0018\u00010\u001c2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0012\u0010d\u001a\u0004\u0018\u00010e2\u0006\u0010+\u001a\u00020fH\u0002J\u0012\u0010g\u001a\u0004\u0018\u00010e2\u0006\u0010+\u001a\u00020/H\u0002J\u0010\u0010h\u001a\u00020i2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001a\u0010j\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c082\b\u0010+\u001a\u0004\u0018\u00010/H\u0002J\u0012\u0010k\u001a\u0004\u0018\u00010e2\u0006\u0010+\u001a\u00020/H\u0002J:\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020m0[2\u0006\u0010>\u001a\u00020\u00162\b\u0010n\u001a\u0004\u0018\u00010m2\u0012\u0010o\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020m0[H\u0002J \u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020m0p2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0002J\u0012\u0010q\u001a\u00020i2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u001a\u0010r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c082\b\u0010s\u001a\u0004\u0018\u00010\u001cH\u0002J\u0010\u0010t\u001a\u00020\u00112\u0006\u0010+\u001a\u00020uH\u0002J\u0010\u0010v\u001a\u00020\u00112\u0006\u0010w\u001a\u00020xH\u0002J\u0018\u0010y\u001a\u00020\u00112\u0006\u0010z\u001a\u00020f2\u0006\u0010{\u001a\u00020|H\u0002J\u0018\u0010}\u001a\u00020\u00112\u0006\u0010z\u001a\u00020f2\u0006\u0010{\u001a\u00020eH\u0002J\u0010\u0010~\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001e\u0010\u007f\u001a\u00020\u000e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020/0#2\u0006\u0010!\u001a\u00020/H\u0002J\u0012\u0010\u0080\u0001\u001a\u00020\u000e2\u0007\u0010\u0081\u0001\u001a\u00020EH\u0002J\u0018\u0010\u0082\u0001\u001a\u00020\u000e2\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u001e0#H\u0002J\u0011\u0010\u0084\u0001\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0012\u0010\u0085\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u0086\u0001H\u0016J\u0013\u0010\u0087\u0001\u001a\u00020\u000e2\b\u0010\u0088\u0001\u001a\u00030\u0089\u0001H\u0016J\u0011\u0010\u008a\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u000202H\u0016J+\u0010\u008b\u0001\u001a\u00020\u000e2\b\u00106\u001a\u0004\u0018\u00010/2\u000e\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c082\u0006\u00109\u001a\u00020/H\u0002J\u0011\u0010\u008c\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020;H\u0016J\u0012\u0010\u008d\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u008e\u0001H\u0016J\u0011\u0010\u008f\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020uH\u0016J\u0012\u0010\u0090\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u0091\u0001H\u0016J\u0012\u0010\u0092\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u0093\u0001H\u0016J\u0013\u0010\u0094\u0001\u001a\u00020\u000e2\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0016J\u0012\u0010\u0097\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u0098\u0001H\u0016J\u0012\u0010\u0099\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030\u009a\u0001H\u0016J\u0013\u0010\u009b\u0001\u001a\u00020\u000e2\b\u0010\u009c\u0001\u001a\u00030\u009d\u0001H\u0016J\u0012\u0010\u009e\u0001\u001a\u00020\u000e2\u0007\u0010H\u001a\u00030\u009f\u0001H\u0016J\u001b\u0010\u009e\u0001\u001a\u00020\u000e2\u0007\u0010H\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020\u0011H\u0002J\u0012\u0010¡\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030¢\u0001H\u0016J\u0012\u0010£\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030¤\u0001H\u0016J\u0011\u0010¥\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,H\u0016J!\u0010¦\u0001\u001a\u00020\u000e2\b\u0010§\u0001\u001a\u00030¨\u00012\f\b\u0002\u0010©\u0001\u001a\u0005\u0018\u00010ª\u0001H\u0002J\u0012\u0010«\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030¬\u0001H\u0016J\u0013\u0010\u00ad\u0001\u001a\u00020\u000e2\b\u0010®\u0001\u001a\u00030¯\u0001H\u0016J\u001d\u0010°\u0001\u001a\u00020\u000e2\b\u0010±\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001H\u0002J\u0012\u0010²\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030³\u0001H\u0016J\u0011\u0010´\u0001\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010µ\u0001\u001a\u00020\u000e2\b\u0010¶\u0001\u001a\u00030·\u0001H\u0016J\u0012\u0010¸\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030¹\u0001H\u0016J\u0013\u0010º\u0001\u001a\u00020\u000e2\b\u0010»\u0001\u001a\u00030¼\u0001H\u0016J\u0013\u0010½\u0001\u001a\u00020\u000e2\b\u0010§\u0001\u001a\u00030¾\u0001H\u0016J\u0013\u0010¿\u0001\u001a\u00020\u000e2\b\u0010À\u0001\u001a\u00030Á\u0001H\u0016J\u0012\u0010Â\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030Ã\u0001H\u0016J\u0011\u0010Ä\u0001\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010Å\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030Æ\u0001H\u0016J\u0013\u0010Ç\u0001\u001a\u00020\u000e2\b\u0010È\u0001\u001a\u00030É\u0001H\u0016J\u0013\u0010Ê\u0001\u001a\u00020\u000e2\b\u0010Ë\u0001\u001a\u00030Ì\u0001H\u0016J\u0012\u0010Í\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030Î\u0001H\u0016J\u0011\u0010Ï\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u0019H\u0016J\u0013\u0010Ð\u0001\u001a\u00020\u000e2\b\u0010Ñ\u0001\u001a\u00030Ò\u0001H\u0016J\u0013\u0010Ó\u0001\u001a\u00020\u000e2\b\u0010Ô\u0001\u001a\u00030Õ\u0001H\u0016J\u0012\u0010Ö\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030×\u0001H\u0016J\u0012\u0010Ø\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030Ù\u0001H\u0016J\u0012\u0010Ú\u0001\u001a\u00020\u000e2\u0007\u0010A\u001a\u00030Û\u0001H\u0016J\u0013\u0010Ü\u0001\u001a\u00020\u000e2\b\u0010\u009c\u0001\u001a\u00030Ý\u0001H\u0016J\u0013\u0010Þ\u0001\u001a\u00020\u000e2\b\u0010®\u0001\u001a\u00030ß\u0001H\u0016J\u0012\u0010à\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030á\u0001H\u0016J\u0012\u0010â\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030ã\u0001H\u0016J\u0011\u0010ä\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020YH\u0016J\u0013\u0010å\u0001\u001a\u00020\u000e2\b\u0010æ\u0001\u001a\u00030ç\u0001H\u0016J\u0012\u0010è\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030é\u0001H\u0016J\u0012\u0010ê\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030ë\u0001H\u0016J\u0012\u0010ì\u0001\u001a\u00020\u000e2\u0007\u0010+\u001a\u00030í\u0001H\u0016J\u0011\u0010î\u0001\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\rj\u0002`\u000f0\f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006ð\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CFPVisitor;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "builder", "Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;", "(Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor;Lorg/jetbrains/kotlin/cfg/ControlFlowBuilder;)V", "catchFinallyStack", "Ljava/util/Stack;", "Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CatchFinallyLabels;", "conditionVisitor", "org/jetbrains/kotlin/cfg/ControlFlowProcessor$CFPVisitor$conditionVisitor$1", "Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CFPVisitor$conditionVisitor$1;", "deferredGeneratorsStack", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/cfg/DeferredGenerator;", "checkAndGenerateCall", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "checkNothingType", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "checkReturnLabelTarget", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "labeledElement", "computePseudoValueForParameter", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "copyValue", TypedValues.TransitionType.S_FROM, "to", "createNonSyntheticValue", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;[Lorg/jetbrains/kotlin/psi/KtElement;)Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "createSyntheticValue", "instructionElement", "declareLoopParameter", "expression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "elementsToValues", "generateAndGetReceiverIfAny", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generateArrayAccess", "arrayAccessExpression", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "generateArrayAccessArguments", "generateArrayAccessWithoutCall", "generateArrayAssignment", "lhs", "rhsDeferredValue", "Lkotlin/Function0;", "parentExpression", "generateBooleanOperation", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "generateBothArgumentsAndMark", "generateCall", "callElement", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "generateCallOrMarkUnresolved", "call", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "generateDeclarationForLocalClassOrObjectIfNeeded", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "generateHeaderDelegationSpecifiers", "generateInitializer", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "initValue", "generateInitializersForClassOrObject", "Lorg/jetbrains/kotlin/psi/KtDeclarationContainer;", "generateInstructions", "generateJumpsToCatchAndFinally", "generateLoopConventionCall", "loopRange", "callSlice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateQualifier", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;", "generateTryAndCatches", "Lorg/jetbrains/kotlin/cfg/Label;", "Lorg/jetbrains/kotlin/psi/KtTryExpression;", "generateValueArgument", "Lorg/jetbrains/kotlin/com/intellij/util/SmartFMap;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "parameterDescriptor", "parameterValuesArg", "getArraySetterArguments", "setResolvedCall", "getBoundOrUnreachableValue", "getCorrespondingLoop", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionWithLabel;", "getCorrespondingLoopWithoutLabel", "getDeclarationAccessTarget", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "getDeferredValue", "getNearestLoopExpression", "getReceiverValues", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "receiver", "receiverValuesArg", "", "getResolvedCallAccessTarget", "getValueAsFunction", "value", "isBlockInDoWhile", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "isIncrementOrDecrement", "operationType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "jumpCrossesTryCatchBoundary", "jumpExpression", "jumpTarget", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "jumpDoesNotCrossFunctionBoundary", "mark", "mergeValues", "processEntryOrObject", "entryOrObject", "processParameters", "parameters", "returnCrossesTryCatchBoundary", "visitAnnotatedExpression", "Lorg/jetbrains/kotlin/psi/KtAnnotatedExpression;", "visitAnonymousInitializer", "classInitializer", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "visitArrayAccessExpression", "visitAssignment", "visitBinaryExpression", "visitBinaryWithTypeRHSExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "visitBlockExpression", "visitBreakExpression", "Lorg/jetbrains/kotlin/psi/KtBreakExpression;", "visitCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "visitClass", "klass", "Lorg/jetbrains/kotlin/psi/KtClass;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "visitContinueExpression", "Lorg/jetbrains/kotlin/psi/KtContinueExpression;", "visitDelegatedSuperTypeEntry", "specifier", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", "visitDestructuringDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "generateWriteForEntries", "visitDoWhileExpression", "Lorg/jetbrains/kotlin/psi/KtDoWhileExpression;", "visitDoubleColonExpression", "Lorg/jetbrains/kotlin/psi/KtDoubleColonExpression;", "visitForExpression", "visitFunction", "function", "Lorg/jetbrains/kotlin/psi/KtFunction;", "eventOccurrencesRange", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "visitIfExpression", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "visitInitializerList", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/psi/KtInitializerList;", "visitInlinedFunction", "lambdaFunctionLiteral", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "visitKtElement", "visitKtFile", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "visitLabeledExpression", "Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "visitLambdaExpression", "lambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "visitNamedFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitObjectDeclaration", "objectDeclaration", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "visitObjectLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtObjectLiteralExpression;", "visitParameter", "visitParenthesizedExpression", "Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;", "visitProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "visitPropertyAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "visitQualifiedExpression", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "visitReturnExpression", "visitScript", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "visitSecondaryConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "visitSimpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "visitStringTemplateExpression", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "visitSuperTypeCallEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeCallEntry;", "visitSuperTypeEntry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeEntry;", "visitSuperTypeList", "Lorg/jetbrains/kotlin/psi/KtSuperTypeList;", "visitThisExpression", "Lorg/jetbrains/kotlin/psi/KtThisExpression;", "visitThrowExpression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "visitTryExpression", "visitTypeProjection", "typeProjection", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "visitUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "visitWhenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "visitWhileExpression", "Lorg/jetbrains/kotlin/psi/KtWhileExpression;", "writeLoopParameterAssignment", "FinallyBlockGenerator", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class CFPVisitor extends KtVisitorVoid {
        private final ControlFlowBuilder builder;
        private final Stack<CatchFinallyLabels> catchFinallyStack;
        private final ControlFlowProcessor$CFPVisitor$conditionVisitor$1 conditionVisitor;
        private final Stack<List<Function1<ControlFlowBuilder, Unit>>> deferredGeneratorsStack;
        final /* synthetic */ ControlFlowProcessor this$0;

        /* JADX INFO: compiled from: ControlFlowProcessor.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ExplicitReceiverKind.values().length];
                try {
                    iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitSuperTypeEntry(KtSuperTypeEntry specifier) {
            Intrinsics.checkNotNullParameter(specifier, "specifier");
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitTypeProjection(KtTypeProjection typeProjection) {
            Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        }

        /* JADX WARN: Type inference failed for: r2v3, types: [org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$conditionVisitor$1] */
        public CFPVisitor(ControlFlowProcessor controlFlowProcessor, ControlFlowBuilder builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            this.this$0 = controlFlowProcessor;
            this.builder = builder;
            this.catchFinallyStack = new Stack<>();
            this.deferredGeneratorsStack = new Stack<>();
            this.conditionVisitor = new KtVisitorVoid() { // from class: org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$conditionVisitor$1
                private final KtExpression getSubjectExpression(KtWhenCondition condition) {
                    KtWhenExpression ktWhenExpression = (KtWhenExpression) PsiTreeUtil.getParentOfType(condition, KtWhenExpression.class, true);
                    if (ktWhenExpression != null) {
                        return ktWhenExpression.getSubjectExpression();
                    }
                    return null;
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitWhenConditionInRange(KtWhenConditionInRange condition) {
                    Intrinsics.checkNotNullParameter(condition, "condition");
                    ControlFlowProcessor.CFPVisitor cFPVisitor = this.this$0;
                    KtOperationReferenceExpression operationReference = condition.getOperationReference();
                    Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                    if (cFPVisitor.generateCall(operationReference)) {
                        return;
                    }
                    KtExpression rangeExpression = condition.getRangeExpression();
                    this.this$0.generateInstructions(rangeExpression);
                    this.this$0.createNonSyntheticValue(condition, MagicKind.UNRESOLVED_CALL, rangeExpression);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitWhenConditionIsPattern(KtWhenConditionIsPattern condition) {
                    Intrinsics.checkNotNullParameter(condition, "condition");
                    KtWhenConditionIsPattern ktWhenConditionIsPattern = condition;
                    this.this$0.mark(ktWhenConditionIsPattern);
                    this.this$0.createNonSyntheticValue(ktWhenConditionIsPattern, MagicKind.IS, getSubjectExpression(condition));
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitWhenConditionWithExpression(KtWhenConditionWithExpression condition) {
                    Intrinsics.checkNotNullParameter(condition, "condition");
                    KtWhenConditionWithExpression ktWhenConditionWithExpression = condition;
                    this.this$0.mark(ktWhenConditionWithExpression);
                    KtExpression expression = condition.getExpression();
                    KtExpression ktExpression = expression;
                    this.this$0.generateInstructions(ktExpression);
                    KtExpression subjectExpression = getSubjectExpression(condition);
                    if (subjectExpression != null) {
                        this.this$0.createNonSyntheticValue(ktWhenConditionWithExpression, MagicKind.EQUALS_IN_WHEN_CONDITION, subjectExpression, expression);
                    } else {
                        this.this$0.copyValue(ktExpression, ktWhenConditionWithExpression);
                    }
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitKtElement(KtElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    throw new UnsupportedOperationException("[ControlFlowProcessor] " + element);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void mark(KtElement element) {
            this.builder.mark(element);
        }

        public final void generateInstructions(KtElement element) {
            if (element == null) {
                return;
            }
            element.accept(this);
            checkNothingType(element);
        }

        private final void checkNothingType(KtElement element) {
            KtExpression ktExpressionDeparenthesize;
            KotlinType type;
            if (!(element instanceof KtExpression) || (ktExpressionDeparenthesize = KtPsiUtil.deparenthesize((KtExpression) element)) == null || (ktExpressionDeparenthesize instanceof KtStatementExpression) || (ktExpressionDeparenthesize instanceof KtTryExpression) || (ktExpressionDeparenthesize instanceof KtIfExpression) || (ktExpressionDeparenthesize instanceof KtWhenExpression) || (type = this.this$0.trace.getContext().getType(ktExpressionDeparenthesize)) == null || !KotlinBuiltIns.isNothing(type)) {
                return;
            }
            this.builder.jumpToError(ktExpressionDeparenthesize);
        }

        private final PseudoValue createSyntheticValue(KtElement instructionElement, MagicKind kind, KtElement... from) {
            return this.builder.magic(instructionElement, null, elementsToValues(ArraysKt.asList(from)), kind).getResultValue();
        }

        private final PseudoValue createNonSyntheticValue(KtElement to, List<? extends KtElement> from, MagicKind kind) {
            return this.builder.magic(to, to, elementsToValues(from), kind).getResultValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PseudoValue createNonSyntheticValue(KtElement to, MagicKind kind, KtElement... from) {
            return createNonSyntheticValue(to, ArraysKt.asList(from), kind);
        }

        private final void mergeValues(List<? extends KtExpression> from, KtExpression to) {
            this.builder.merge(to, elementsToValues(from));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void copyValue(KtElement from, KtElement to) {
            PseudoValue boundOrUnreachableValue = getBoundOrUnreachableValue(from);
            if (boundOrUnreachableValue != null) {
                this.builder.bindValue(boundOrUnreachableValue, to);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PseudoValue getBoundOrUnreachableValue(KtElement element) {
            if (element == null) {
                return null;
            }
            PseudoValue boundValue = this.builder.getBoundValue(element);
            return (boundValue != null || (element instanceof KtDeclaration)) ? boundValue : this.builder.newValue(element);
        }

        private final List<PseudoValue> elementsToValues(List<? extends KtElement> from) {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = from.iterator();
            while (it.hasNext()) {
                PseudoValue boundOrUnreachableValue = getBoundOrUnreachableValue((KtElement) it.next());
                if (boundOrUnreachableValue != null) {
                    arrayList.add(boundOrUnreachableValue);
                }
            }
            return arrayList;
        }

        private final void generateInitializer(KtDeclaration declaration, PseudoValue initValue) {
            KtDeclaration ktDeclaration = declaration;
            this.builder.write(ktDeclaration, ktDeclaration, initValue, getDeclarationAccessTarget(ktDeclaration), MapsKt.emptyMap());
        }

        private final AccessTarget getResolvedCallAccessTarget(KtElement element) {
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(element, context);
            return resolvedCall != null ? new AccessTarget.Call(resolvedCall) : AccessTarget.BlackBox.INSTANCE;
        }

        private final AccessTarget getDeclarationAccessTarget(KtElement element) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.this$0.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, element);
            if (declarationDescriptor instanceof VariableDescriptor) {
                return new AccessTarget.Declaration((VariableDescriptor) declarationDescriptor);
            }
            return AccessTarget.BlackBox.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitParenthesizedExpression(KtParenthesizedExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtParenthesizedExpression ktParenthesizedExpression = expression;
            mark(ktParenthesizedExpression);
            KtExpression expression2 = expression.getExpression();
            if (expression2 != null) {
                KtExpression ktExpression = expression2;
                generateInstructions(ktExpression);
                copyValue(ktExpression, ktParenthesizedExpression);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitAnnotatedExpression(KtAnnotatedExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtExpression baseExpression = expression.getBaseExpression();
            if (baseExpression != null) {
                KtExpression ktExpression = baseExpression;
                generateInstructions(ktExpression);
                copyValue(ktExpression, expression);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitThisExpression(KtThisExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtThisExpression ktThisExpression = expression;
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktThisExpression, context);
            if (resolvedCall == null) {
                createNonSyntheticValue(ktThisExpression, MagicKind.UNRESOLVED_CALL, new KtElement[0]);
                return;
            }
            if (resolvedCall.getResultingDescriptor() instanceof ReceiverParameterDescriptor) {
                this.builder.readVariable(expression, resolvedCall, getReceiverValues(resolvedCall));
            }
            KtReferenceExpression instanceReference = expression.getInstanceReference();
            Intrinsics.checkNotNullExpressionValue(instanceReference, "getInstanceReference(...)");
            copyValue(ktThisExpression, instanceReference);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitConstantExpression(KtConstantExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
            KtConstantExpression ktConstantExpression = expression;
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            this.builder.loadConstant(ktConstantExpression, companion.getConstant(ktConstantExpression, context));
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitSimpleNameExpression(KtSimpleNameExpression expression) {
            KtSimpleNameExpression ktSimpleNameExpression;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtSimpleNameExpression ktSimpleNameExpression2 = expression;
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression2, context);
            if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                generateCall(((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall());
                return;
            }
            if (resolvedCall == null) {
                boolean zSupportsFeature = this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitQualifiedAccessToUninitializedEnumEntry);
                if (zSupportsFeature) {
                    NavigationItem topmostParentQualifiedExpressionForSelector = KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector(expression);
                    ktSimpleNameExpression = topmostParentQualifiedExpressionForSelector != null ? (KtExpression) topmostParentQualifiedExpressionForSelector : expression;
                } else if (!zSupportsFeature) {
                    ktSimpleNameExpression = expression;
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                Qualifier qualifier = (Qualifier) this.this$0.trace.getContext().get(BindingContext.QUALIFIER, ktSimpleNameExpression);
                if (qualifier != null && generateQualifier(expression, qualifier)) {
                    return;
                }
            }
            if (generateCall(ktSimpleNameExpression2) || (expression.getLightParent() instanceof KtCallExpression)) {
                return;
            }
            createNonSyntheticValue(ktSimpleNameExpression2, MagicKind.UNRESOLVED_CALL, generateAndGetReceiverIfAny(expression));
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitLabeledExpression(KtLabeledExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtLabeledExpression ktLabeledExpression = expression;
            mark(ktLabeledExpression);
            KtExpression baseExpression = expression.getBaseExpression();
            if (baseExpression != null) {
                KtExpression ktExpression = baseExpression;
                generateInstructions(ktExpression);
                copyValue(ktExpression, ktLabeledExpression);
            }
            KtSimpleNameExpression targetLabel = expression.getTargetLabel();
            if (targetLabel != null) {
                KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression);
                if ((ktExpressionDeparenthesize instanceof KtLambdaExpression) || (ktExpressionDeparenthesize instanceof KtLoopExpression) || (ktExpressionDeparenthesize instanceof KtNamedFunction)) {
                    return;
                }
                this.this$0.trace.report(Errors.REDUNDANT_LABEL_WARNING.on(targetLabel));
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitBinaryExpression(KtBinaryExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtOperationReferenceExpression operationReference = expression.getOperationReference();
            Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
            IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
            KtExpression left = expression.getLeft();
            KtExpression right = expression.getRight();
            if (referencedNameElementType == KtTokens.ANDAND || referencedNameElementType == KtTokens.OROR) {
                generateBooleanOperation(expression);
                return;
            }
            if (referencedNameElementType == KtTokens.EQ) {
                visitAssignment(left, getDeferredValue(right), expression);
                return;
            }
            ImmutableBiMap<KtSingleValueToken, Name> ASSIGNMENT_OPERATIONS = OperatorConventions.ASSIGNMENT_OPERATIONS;
            Intrinsics.checkNotNullExpressionValue(ASSIGNMENT_OPERATIONS, "ASSIGNMENT_OPERATIONS");
            if (ASSIGNMENT_OPERATIONS.containsKey(referencedNameElementType)) {
                BindingContext context = this.this$0.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context);
                if (resolvedCall != null) {
                    PseudoValue resultValue = generateCall(resolvedCall).getResultValue();
                    IElementType operationToken = expression.getOperationToken();
                    Intrinsics.checkNotNull(operationToken, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtToken");
                    if (Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), OperatorConventions.getNameForOperationSymbol((KtToken) operationToken))) {
                        return;
                    }
                    visitAssignment(left, getValueAsFunction(resultValue), expression);
                    return;
                }
                generateBothArgumentsAndMark(expression);
                return;
            }
            if (referencedNameElementType == KtTokens.ELVIS) {
                KtExpression ktExpression = left;
                generateInstructions(ktExpression);
                KtBinaryExpression ktBinaryExpression = expression;
                mark(ktBinaryExpression);
                Label labelCreateUnboundLabel = this.builder.createUnboundLabel("after elvis operator");
                ControlFlowBuilder controlFlowBuilder = this.builder;
                controlFlowBuilder.jumpOnTrue(labelCreateUnboundLabel, ktBinaryExpression, controlFlowBuilder.getBoundValue(ktExpression));
                KtExpression ktExpression2 = right;
                generateInstructions(ktExpression2);
                this.builder.bindLabel(labelCreateUnboundLabel);
                mergeValues(CollectionsKt.listOfNotNull((Object[]) new KtExpression[]{left, right}), expression);
                if (right == null || !this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitNonExhaustiveIfInRhsOfElvis)) {
                    return;
                }
                BindingContextUtilsKt.recordUsedAsExpression(ktExpression2, this.this$0.trace, true);
                return;
            }
            if (generateCall(expression)) {
                return;
            }
            generateBothArgumentsAndMark(expression);
        }

        private final void generateBooleanOperation(KtBinaryExpression expression) {
            IElementType referencedNameElementType = expression.getOperationReference().getReferencedNameElementType();
            KtExpression left = expression.getLeft();
            KtExpression right = expression.getRight();
            Label labelCreateUnboundLabel = this.builder.createUnboundLabel("result of boolean operation");
            KtExpression ktExpression = left;
            generateInstructions(ktExpression);
            if (referencedNameElementType == KtTokens.ANDAND) {
                ControlFlowBuilder controlFlowBuilder = this.builder;
                controlFlowBuilder.jumpOnFalse(labelCreateUnboundLabel, expression, controlFlowBuilder.getBoundValue(ktExpression));
            } else {
                ControlFlowBuilder controlFlowBuilder2 = this.builder;
                controlFlowBuilder2.jumpOnTrue(labelCreateUnboundLabel, expression, controlFlowBuilder2.getBoundValue(ktExpression));
            }
            generateInstructions(right);
            this.builder.bindLabel(labelCreateUnboundLabel);
            this.builder.predefinedOperation(expression, referencedNameElementType == KtTokens.ANDAND ? ControlFlowBuilder.PredefinedOperation.AND : ControlFlowBuilder.PredefinedOperation.OR, elementsToValues(CollectionsKt.listOfNotNull((Object[]) new KtExpression[]{left, right})));
        }

        private final Function0<PseudoValue> getValueAsFunction(final PseudoValue value) {
            return new Function0<PseudoValue>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$getValueAsFunction$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PseudoValue invoke() {
                    return value;
                }
            };
        }

        private final Function0<PseudoValue> getDeferredValue(final KtExpression expression) {
            return new Function0<PseudoValue>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$getDeferredValue$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PseudoValue invoke() {
                    this.this$0.generateInstructions(expression);
                    return this.this$0.getBoundOrUnreachableValue(expression);
                }
            };
        }

        private final void generateBothArgumentsAndMark(KtBinaryExpression expression) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression.getLeft());
            if (ktExpressionDeparenthesize != null) {
                generateInstructions(ktExpressionDeparenthesize);
            }
            KtExpression right = expression.getRight();
            if (right != null) {
                generateInstructions(right);
            }
            KtBinaryExpression ktBinaryExpression = expression;
            mark(ktBinaryExpression);
            createNonSyntheticValue(ktBinaryExpression, MagicKind.UNRESOLVED_CALL, ktExpressionDeparenthesize, right);
        }

        private final void visitAssignment(KtExpression lhs, Function0<? extends PseudoValue> rhsDeferredValue, KtExpression parentExpression) {
            List<? extends PseudoValue> listEmptyList;
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(lhs);
            if (ktExpressionDeparenthesize == null) {
                PseudoValue pseudoValueInvoke = rhsDeferredValue.invoke();
                if (pseudoValueInvoke == null || (listEmptyList = CollectionsKt.listOf(pseudoValueInvoke)) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                KtExpression ktExpression = parentExpression;
                this.builder.magic(ktExpression, ktExpression, listEmptyList, MagicKind.UNSUPPORTED_ELEMENT);
                return;
            }
            if (ktExpressionDeparenthesize instanceof KtArrayAccessExpression) {
                generateArrayAssignment((KtArrayAccessExpression) ktExpressionDeparenthesize, rhsDeferredValue, parentExpression);
                return;
            }
            SmartFMap smartFMapEmptyMap = SmartFMap.emptyMap();
            Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "emptyMap(...)");
            SmartFMap receiverValues = smartFMapEmptyMap;
            AccessTarget.BlackBox resolvedCallAccessTarget = AccessTarget.BlackBox.INSTANCE;
            if ((ktExpressionDeparenthesize instanceof KtSimpleNameExpression) || (ktExpressionDeparenthesize instanceof KtQualifiedExpression)) {
                resolvedCallAccessTarget = getResolvedCallAccessTarget(KtPsiUtilKt.getQualifiedElementSelector(ktExpressionDeparenthesize));
                if (resolvedCallAccessTarget instanceof AccessTarget.Call) {
                    receiverValues = getReceiverValues(((AccessTarget.Call) resolvedCallAccessTarget).getResolvedCall());
                }
            } else if (ktExpressionDeparenthesize instanceof KtProperty) {
                resolvedCallAccessTarget = getDeclarationAccessTarget(ktExpressionDeparenthesize);
            }
            Map<PseudoValue, ReceiverValue> map = receiverValues;
            AccessTarget accessTarget = resolvedCallAccessTarget;
            if (accessTarget == AccessTarget.BlackBox.INSTANCE && !(ktExpressionDeparenthesize instanceof KtProperty)) {
                KtExpression ktExpression2 = ktExpressionDeparenthesize;
                generateInstructions(ktExpression2);
                createSyntheticValue(ktExpression2, MagicKind.VALUE_CONSUMER, ktExpressionDeparenthesize);
            }
            PseudoValue pseudoValueInvoke2 = rhsDeferredValue.invoke();
            if (pseudoValueInvoke2 == null) {
                pseudoValueInvoke2 = createSyntheticValue(parentExpression, MagicKind.UNRECOGNIZED_WRITE_RHS, new KtElement[0]);
            }
            this.builder.write(parentExpression, ktExpressionDeparenthesize, pseudoValueInvoke2, accessTarget, map);
        }

        private final void generateArrayAssignment(KtArrayAccessExpression lhs, Function0<? extends PseudoValue> rhsDeferredValue, KtExpression parentExpression) {
            ResolvedCall<?> resolvedCall = (ResolvedCall) this.this$0.trace.get(BindingContext.INDEXED_LVALUE_SET, lhs);
            if (resolvedCall == null) {
                generateArrayAccess(lhs, null);
                KtExpression ktExpression = parentExpression;
                this.builder.magic(ktExpression, ktExpression, CollectionsKt.listOfNotNull((Object[]) new PseudoValue[]{getBoundOrUnreachableValue(lhs), rhsDeferredValue.invoke()}), MagicKind.UNRESOLVED_CALL);
            } else {
                Intrinsics.checkNotNull(parentExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtOperationExpression");
                if (((KtOperationExpression) parentExpression).getOperationReference().getReferencedNameElementType() == KtTokens.EQ) {
                    mark(lhs);
                }
                generateInstructions(lhs.getArrayExpression());
                this.builder.call(parentExpression, resolvedCall, getReceiverValues(resolvedCall), getArraySetterArguments(rhsDeferredValue, resolvedCall));
            }
        }

        private final SmartFMap<PseudoValue, ValueParameterDescriptor> getArraySetterArguments(Function0<? extends PseudoValue> rhsDeferredValue, ResolvedCall<FunctionDescriptor> setResolvedCall) {
            List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) setResolvedCall.getResultingDescriptor()).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = valueParameters.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ResolvedValueArgument resolvedValueArgument = setResolvedCall.getValueArguments().get((ValueParameterDescriptor) it.next());
                List<ValueArgument> arguments = resolvedValueArgument != null ? resolvedValueArgument.getArguments() : null;
                if (arguments == null) {
                    arguments = CollectionsKt.emptyList();
                } else {
                    Intrinsics.checkNotNull(arguments);
                }
                CollectionsKt.addAll(arrayList, arguments);
            }
            ArrayList<ValueArgument> arrayList2 = arrayList;
            ValueArgument valueArgument = (ValueArgument) CollectionsKt.lastOrNull((List) arrayList2);
            SmartFMap<PseudoValue, ValueParameterDescriptor> smartFMapEmptyMap = SmartFMap.emptyMap();
            Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "emptyMap(...)");
            for (ValueArgument valueArgument2 : arrayList2) {
                ArgumentMapping argumentMapping = setResolvedCall.getArgumentMapping(valueArgument2);
                ArgumentMatch argumentMatch = argumentMapping instanceof ArgumentMatch ? (ArgumentMatch) argumentMapping : null;
                if (argumentMatch != null) {
                    ValueParameterDescriptor valueParameter = argumentMatch.getValueParameter();
                    if (valueArgument2 != valueArgument) {
                        Intrinsics.checkNotNull(valueArgument2);
                        smartFMapEmptyMap = generateValueArgument(valueArgument2, valueParameter, smartFMapEmptyMap);
                    } else {
                        PseudoValue pseudoValueInvoke = rhsDeferredValue.invoke();
                        if (pseudoValueInvoke != null) {
                            smartFMapEmptyMap = smartFMapEmptyMap.plus(pseudoValueInvoke, valueParameter);
                            Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "plus(...)");
                        }
                    }
                }
            }
            return smartFMapEmptyMap;
        }

        private final void generateArrayAccess(KtArrayAccessExpression arrayAccessExpression, ResolvedCall<?> resolvedCall) {
            KtArrayAccessExpression ktArrayAccessExpression = arrayAccessExpression;
            if (this.builder.getBoundValue(ktArrayAccessExpression) != null) {
                return;
            }
            mark(ktArrayAccessExpression);
            if (checkAndGenerateCall(resolvedCall)) {
                return;
            }
            generateArrayAccessWithoutCall(arrayAccessExpression);
        }

        private final void generateArrayAccessWithoutCall(KtArrayAccessExpression arrayAccessExpression) {
            createNonSyntheticValue(arrayAccessExpression, generateArrayAccessArguments(arrayAccessExpression), MagicKind.UNRESOLVED_CALL);
        }

        private final List<KtExpression> generateArrayAccessArguments(KtArrayAccessExpression arrayAccessExpression) {
            ArrayList arrayList = new ArrayList();
            KtExpression arrayExpression = arrayAccessExpression.getArrayExpression();
            if (arrayExpression != null) {
                arrayList.add(arrayExpression);
            }
            generateInstructions(arrayExpression);
            for (KtExpression ktExpression : arrayAccessExpression.getIndexExpressions()) {
                generateInstructions(ktExpression);
                arrayList.add(ktExpression);
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitUnaryExpression(KtUnaryExpression expression) {
            PseudoValue pseudoValueCreateNonSyntheticValue;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtSimpleNameExpression operationReference = expression.getOperationReference();
            Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
            IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
            KtExpression baseExpression = expression.getBaseExpression();
            if (baseExpression == null) {
                return;
            }
            if (KtTokens.EXCLEXCL == referencedNameElementType) {
                generateInstructions(baseExpression);
                this.builder.predefinedOperation(expression, ControlFlowBuilder.PredefinedOperation.NOT_NULL_ASSERTION, elementsToValues(CollectionsKt.listOf(baseExpression)));
                return;
            }
            boolean zIsIncrementOrDecrement = isIncrementOrDecrement(referencedNameElementType);
            KtUnaryExpression ktUnaryExpression = expression;
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktUnaryExpression, context);
            if (resolvedCall != null) {
                pseudoValueCreateNonSyntheticValue = generateCall(resolvedCall).getResultValue();
            } else {
                generateInstructions(baseExpression);
                pseudoValueCreateNonSyntheticValue = createNonSyntheticValue(ktUnaryExpression, MagicKind.UNRESOLVED_CALL, baseExpression);
            }
            if (!zIsIncrementOrDecrement || resolvedCall == null) {
                return;
            }
            visitAssignment(baseExpression, getValueAsFunction(pseudoValueCreateNonSyntheticValue), expression);
            if (expression instanceof KtPostfixExpression) {
                copyValue(baseExpression, ktUnaryExpression);
            }
        }

        private final boolean isIncrementOrDecrement(IElementType operationType) {
            return operationType == KtTokens.PLUSPLUS || operationType == KtTokens.MINUSMINUS;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitIfExpression(KtIfExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtIfExpression ktIfExpression = expression;
            mark(ktIfExpression);
            ArrayList arrayList = new ArrayList(2);
            KtExpression condition = expression.getCondition();
            generateInstructions(condition);
            Label labelCreateUnboundLabel = this.builder.createUnboundLabel("else branch");
            ControlFlowBuilder controlFlowBuilder = this.builder;
            controlFlowBuilder.jumpOnFalse(labelCreateUnboundLabel, ktIfExpression, controlFlowBuilder.getBoundValue(condition));
            KtExpression then = expression.getThen();
            if (then != null) {
                arrayList.add(then);
                generateInstructions(then);
            } else {
                this.builder.loadUnit(expression);
            }
            Label labelCreateUnboundLabel2 = this.builder.createUnboundLabel("'if' expression result");
            this.builder.jump(labelCreateUnboundLabel2, ktIfExpression);
            this.builder.bindLabel(labelCreateUnboundLabel);
            KtExpression ktExpression = expression.getElse();
            if (ktExpression != null) {
                arrayList.add(ktExpression);
                generateInstructions(ktExpression);
            } else {
                this.builder.loadUnit(expression);
            }
            this.builder.bindLabel(labelCreateUnboundLabel2);
            mergeValues(arrayList, expression);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: compiled from: ControlFlowProcessor.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CFPVisitor$FinallyBlockGenerator;", "", "finallyBlock", "Lorg/jetbrains/kotlin/psi/KtFinallySection;", "(Lorg/jetbrains/kotlin/cfg/ControlFlowProcessor$CFPVisitor;Lorg/jetbrains/kotlin/psi/KtFinallySection;)V", "finishFinally", "Lorg/jetbrains/kotlin/cfg/Label;", "startFinally", CompilerOptions.GENERATE, "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
        final class FinallyBlockGenerator {
            private final KtFinallySection finallyBlock;
            private Label finishFinally;
            private Label startFinally;

            public FinallyBlockGenerator(KtFinallySection ktFinallySection) {
                this.finallyBlock = ktFinallySection;
            }

            public final void generate() {
                KtFinallySection ktFinallySection = this.finallyBlock;
                KtBlockExpression finalExpression = ktFinallySection != null ? ktFinallySection.getFinalExpression() : null;
                if (finalExpression == null) {
                    return;
                }
                CFPVisitor.this.catchFinallyStack.push(new CatchFinallyLabels(null, null, null));
                Label label = this.startFinally;
                if (label != null) {
                    CFPVisitor cFPVisitor = CFPVisitor.this;
                    ControlFlowBuilder controlFlowBuilder = cFPVisitor.builder;
                    Label label2 = this.finishFinally;
                    Intrinsics.checkNotNull(label2);
                    controlFlowBuilder.repeatPseudocode(label, label2);
                    cFPVisitor.catchFinallyStack.pop();
                    return;
                }
                Label labelCreateUnboundLabel = CFPVisitor.this.builder.createUnboundLabel("start finally");
                CFPVisitor cFPVisitor2 = CFPVisitor.this;
                this.startFinally = labelCreateUnboundLabel;
                cFPVisitor2.builder.bindLabel(labelCreateUnboundLabel);
                CFPVisitor.this.generateInstructions(finalExpression);
                Label labelCreateUnboundLabel2 = CFPVisitor.this.builder.createUnboundLabel("finish finally");
                CFPVisitor cFPVisitor3 = CFPVisitor.this;
                this.finishFinally = labelCreateUnboundLabel2;
                cFPVisitor3.builder.bindLabel(labelCreateUnboundLabel2);
                CFPVisitor.this.catchFinallyStack.pop();
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitTryExpression(KtTryExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtTryExpression ktTryExpression = expression;
            mark(ktTryExpression);
            KtFinallySection finallyBlock = expression.getFinallyBlock();
            final FinallyBlockGenerator finallyBlockGenerator = new FinallyBlockGenerator(finallyBlock);
            boolean z = finallyBlock != null;
            if (z) {
                this.builder.enterTryFinally(new GenerationTrigger() { // from class: org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$visitTryExpression$1
                    private boolean working;

                    @Override // org.jetbrains.kotlin.cfg.GenerationTrigger
                    public void generate() {
                        if (this.working) {
                            return;
                        }
                        this.working = true;
                        finallyBlockGenerator.generate();
                        this.working = false;
                    }
                });
            }
            Label labelGenerateTryAndCatches = generateTryAndCatches(expression);
            if (z) {
                this.builder.exitTryFinally();
                Label labelCreateUnboundLabel = this.builder.createUnboundLabel("skipFinallyToErrorBlock");
                this.builder.jump(labelCreateUnboundLabel, ktTryExpression);
                ControlFlowBuilder controlFlowBuilder = this.builder;
                Intrinsics.checkNotNull(labelGenerateTryAndCatches);
                controlFlowBuilder.bindLabel(labelGenerateTryAndCatches);
                finallyBlockGenerator.generate();
                this.builder.jumpToError(ktTryExpression);
                this.builder.bindLabel(labelCreateUnboundLabel);
                finallyBlockGenerator.generate();
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(expression.getTryBlock());
            Iterator<KtCatchClause> it = expression.getCatchClauses().iterator();
            while (it.hasNext()) {
                KtExpression catchBody = it.next().getCatchBody();
                if (catchBody != null) {
                    arrayList.add(catchBody);
                }
            }
            mergeValues(arrayList, expression);
        }

        private final Label generateTryAndCatches(KtTryExpression expression) {
            Label labelCreateUnboundLabel;
            List<KtCatchClause> catchClauses = expression.getCatchClauses();
            Intrinsics.checkNotNullExpressionValue(catchClauses, "getCatchClauses(...)");
            boolean zIsEmpty = catchClauses.isEmpty();
            Label label = null;
            if (zIsEmpty) {
                labelCreateUnboundLabel = null;
            } else {
                labelCreateUnboundLabel = this.builder.createUnboundLabel("onException");
                this.builder.nondeterministicJump(labelCreateUnboundLabel, expression, null);
            }
            if (expression.getFinallyBlock() != null) {
                Label labelCreateUnboundLabel2 = this.builder.createUnboundLabel("onExceptionToFinallyBlock");
                this.builder.nondeterministicJump(labelCreateUnboundLabel2, expression, null);
                label = labelCreateUnboundLabel2;
            }
            KtBlockExpression tryBlock = expression.getTryBlock();
            Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
            this.catchFinallyStack.push(new CatchFinallyLabels(labelCreateUnboundLabel, label, expression));
            generateInstructions(tryBlock);
            generateJumpsToCatchAndFinally();
            this.catchFinallyStack.pop();
            if (!zIsEmpty && labelCreateUnboundLabel != null) {
                Label labelCreateUnboundLabel3 = this.builder.createUnboundLabel("afterCatches");
                KtTryExpression ktTryExpression = expression;
                this.builder.jump(labelCreateUnboundLabel3, ktTryExpression);
                this.builder.bindLabel(labelCreateUnboundLabel);
                LinkedList linkedList = new LinkedList();
                boolean z = true;
                int size = catchClauses.size() - 1;
                for (int i = 0; i < size; i++) {
                    linkedList.add(this.builder.createUnboundLabel("catch " + i));
                }
                if (!linkedList.isEmpty()) {
                    this.builder.nondeterministicJump(linkedList, ktTryExpression);
                }
                for (KtCatchClause ktCatchClause : catchClauses) {
                    ControlFlowBuilder controlFlowBuilder = this.builder;
                    Intrinsics.checkNotNull(ktCatchClause);
                    KtCatchClause ktCatchClause2 = ktCatchClause;
                    controlFlowBuilder.enterBlockScope(ktCatchClause2);
                    if (z) {
                        z = false;
                    } else {
                        ControlFlowBuilder controlFlowBuilder2 = this.builder;
                        Object objRemove = linkedList.remove();
                        Intrinsics.checkNotNullExpressionValue(objRemove, "remove(...)");
                        controlFlowBuilder2.bindLabel((Label) objRemove);
                    }
                    KtParameter catchParameter = ktCatchClause.getCatchParameter();
                    if (catchParameter != null) {
                        this.builder.declareParameter(catchParameter);
                        generateInitializer(catchParameter, createSyntheticValue(catchParameter, MagicKind.FAKE_INITIALIZER, new KtElement[0]));
                    }
                    generateInstructions(ktCatchClause.getCatchBody());
                    this.builder.jump(labelCreateUnboundLabel3, ktTryExpression);
                    this.builder.exitBlockScope(ktCatchClause2);
                }
                this.builder.bindLabel(labelCreateUnboundLabel3);
            }
            return label;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitWhileExpression(KtWhileExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtWhileExpression ktWhileExpression = expression;
            LoopInfo loopInfoEnterLoop = this.builder.enterLoop(ktWhileExpression);
            this.builder.bindLabel(loopInfoEnterLoop.getConditionEntryPoint());
            KtExpression condition = expression.getCondition();
            KtExpression ktExpression = condition;
            generateInstructions(ktExpression);
            KtWhileExpression ktWhileExpression2 = expression;
            mark(ktWhileExpression2);
            if (!CompileTimeConstantUtils.canBeReducedToBooleanConstant(condition, this.this$0.trace.getContext(), true)) {
                this.builder.jumpOnFalse(loopInfoEnterLoop.getExitPoint(), ktWhileExpression2, this.builder.getBoundValue(ktExpression));
            } else {
                Intrinsics.checkNotNull(condition);
                createSyntheticValue(ktExpression, MagicKind.VALUE_CONSUMER, condition);
            }
            this.builder.enterLoopBody(ktWhileExpression);
            generateInstructions(expression.getBody());
            this.builder.jump(loopInfoEnterLoop.getEntryPoint(), ktWhileExpression2);
            this.builder.exitLoopBody(ktWhileExpression);
            this.builder.bindLabel(loopInfoEnterLoop.getExitPoint());
            this.builder.loadUnit(expression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitDoWhileExpression(KtDoWhileExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtDoWhileExpression ktDoWhileExpression = expression;
            this.builder.enterBlockScope(ktDoWhileExpression);
            mark(ktDoWhileExpression);
            KtDoWhileExpression ktDoWhileExpression2 = expression;
            LoopInfo loopInfoEnterLoop = this.builder.enterLoop(ktDoWhileExpression2);
            this.builder.enterLoopBody(ktDoWhileExpression2);
            generateInstructions(expression.getBody());
            this.builder.exitLoopBody(ktDoWhileExpression2);
            this.builder.bindLabel(loopInfoEnterLoop.getConditionEntryPoint());
            KtExpression condition = expression.getCondition();
            KtExpression ktExpression = condition;
            generateInstructions(ktExpression);
            this.builder.exitBlockScope(ktDoWhileExpression);
            if (!CompileTimeConstantUtils.canBeReducedToBooleanConstant(condition, this.this$0.trace.getContext(), true)) {
                this.builder.jumpOnTrue(loopInfoEnterLoop.getEntryPoint(), ktDoWhileExpression, this.builder.getBoundValue(expression.getCondition()));
            } else {
                Intrinsics.checkNotNull(condition);
                createSyntheticValue(ktExpression, MagicKind.VALUE_CONSUMER, condition);
                this.builder.jump(loopInfoEnterLoop.getEntryPoint(), ktDoWhileExpression);
            }
            this.builder.bindLabel(loopInfoEnterLoop.getExitPoint());
            this.builder.loadUnit(expression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitForExpression(KtForExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtForExpression ktForExpression = expression;
            this.builder.enterBlockScope(ktForExpression);
            KtExpression loopRange = expression.getLoopRange();
            generateInstructions(loopRange);
            WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_ITERATOR_RESOLVED_CALL = BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL;
            Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_ITERATOR_RESOLVED_CALL, "LOOP_RANGE_ITERATOR_RESOLVED_CALL");
            generateLoopConventionCall(loopRange, LOOP_RANGE_ITERATOR_RESOLVED_CALL);
            declareLoopParameter(expression);
            KtForExpression ktForExpression2 = expression;
            LoopInfo loopInfoEnterLoop = this.builder.enterLoop(ktForExpression2);
            this.builder.bindLabel(loopInfoEnterLoop.getConditionEntryPoint());
            WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_HAS_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL;
            Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_HAS_NEXT_RESOLVED_CALL, "LOOP_RANGE_HAS_NEXT_RESOLVED_CALL");
            generateLoopConventionCall(loopRange, LOOP_RANGE_HAS_NEXT_RESOLVED_CALL);
            this.builder.nondeterministicJump(loopInfoEnterLoop.getExitPoint(), ktForExpression, null);
            WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL;
            Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_NEXT_RESOLVED_CALL, "LOOP_RANGE_NEXT_RESOLVED_CALL");
            generateLoopConventionCall(loopRange, LOOP_RANGE_NEXT_RESOLVED_CALL);
            writeLoopParameterAssignment(expression);
            mark(ktForExpression);
            this.builder.enterLoopBody(ktForExpression2);
            generateInstructions(expression.getBody());
            this.builder.jump(loopInfoEnterLoop.getEntryPoint(), ktForExpression);
            this.builder.exitLoopBody(ktForExpression2);
            this.builder.bindLabel(loopInfoEnterLoop.getExitPoint());
            this.builder.loadUnit(expression);
            this.builder.exitBlockScope(ktForExpression);
        }

        private final void generateLoopConventionCall(KtExpression loopRange, ReadOnlySlice<KtExpression, ResolvedCall<FunctionDescriptor>> callSlice) {
            ResolvedCall<?> resolvedCall;
            if (loopRange == null || (resolvedCall = (ResolvedCall) this.this$0.trace.getContext().get(callSlice, loopRange)) == null) {
                return;
            }
            generateCall(resolvedCall);
        }

        private final void declareLoopParameter(KtForExpression expression) {
            KtParameter loopParameter = expression.getLoopParameter();
            if (loopParameter != null) {
                KtDestructuringDeclaration destructuringDeclaration = loopParameter.getDestructuringDeclaration();
                if (destructuringDeclaration != null) {
                    visitDestructuringDeclaration(destructuringDeclaration, false);
                } else {
                    this.builder.declareParameter(loopParameter);
                }
            }
        }

        private final void writeLoopParameterAssignment(KtForExpression expression) {
            KtParameter loopParameter = expression.getLoopParameter();
            KtExpression loopRange = expression.getLoopRange();
            ControlFlowBuilder controlFlowBuilder = this.builder;
            KtForExpression ktForExpression = loopRange == null ? expression : loopRange;
            List<? extends PseudoValue> listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(controlFlowBuilder.getBoundValue(loopRange));
            Intrinsics.checkNotNullExpressionValue(listCreateMaybeSingletonList, "createMaybeSingletonList(...)");
            PseudoValue resultValue = controlFlowBuilder.magic(ktForExpression, null, listCreateMaybeSingletonList, MagicKind.LOOP_RANGE_ITERATION).getResultValue();
            if (loopParameter != null) {
                KtDestructuringDeclaration destructuringDeclaration = loopParameter.getDestructuringDeclaration();
                if (destructuringDeclaration != null) {
                    for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : destructuringDeclaration.getEntries()) {
                        Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                        generateInitializer(ktDestructuringDeclarationEntry, resultValue);
                    }
                    return;
                }
                generateInitializer(loopParameter, resultValue);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitBreakExpression(KtBreakExpression expression) {
            Label loopExitPoint;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtBreakExpression ktBreakExpression = expression;
            KtLoopExpression correspondingLoop = getCorrespondingLoop(ktBreakExpression);
            if (correspondingLoop != null) {
                if (jumpCrossesTryCatchBoundary(ktBreakExpression, correspondingLoop)) {
                    generateJumpsToCatchAndFinally();
                }
                if (!jumpDoesNotCrossFunctionBoundary(ktBreakExpression, correspondingLoop) || (loopExitPoint = this.builder.getLoopExitPoint(correspondingLoop)) == null) {
                    return;
                }
                this.builder.jump(loopExitPoint, expression);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitContinueExpression(KtContinueExpression expression) {
            Label loopConditionEntryPoint;
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtContinueExpression ktContinueExpression = expression;
            KtLoopExpression correspondingLoop = getCorrespondingLoop(ktContinueExpression);
            if (correspondingLoop != null) {
                if (jumpCrossesTryCatchBoundary(ktContinueExpression, correspondingLoop)) {
                    generateJumpsToCatchAndFinally();
                }
                if (!jumpDoesNotCrossFunctionBoundary(ktContinueExpression, correspondingLoop) || (loopConditionEntryPoint = this.builder.getLoopConditionEntryPoint(correspondingLoop)) == null) {
                    return;
                }
                this.builder.jump(loopConditionEntryPoint, expression);
            }
        }

        private final KtLoopExpression getNearestLoopExpression(KtExpression expression) {
            return (KtLoopExpression) PsiTreeUtil.getParentOfType(expression, KtLoopExpression.class, true);
        }

        private final KtLoopExpression getCorrespondingLoopWithoutLabel(KtExpression expression) {
            KtLoopExpression nearestLoopExpression = getNearestLoopExpression(expression);
            if (nearestLoopExpression == null) {
                return null;
            }
            KtExpression body = nearestLoopExpression.getBody();
            return (body == null || !body.getTextRange().contains(expression.getTextRange())) ? getNearestLoopExpression(nearestLoopExpression) : nearestLoopExpression;
        }

        private final KtLoopExpression getCorrespondingLoop(KtExpressionWithLabel expression) {
            KtLoopExpression correspondingLoopWithoutLabel;
            KtExpression body;
            if (expression.getLabelName() != null) {
                KtSimpleNameExpression targetLabel = expression.getTargetLabel();
                Intrinsics.checkNotNull(targetLabel);
                PsiElement psiElement = (PsiElement) this.this$0.trace.get(BindingContext.LABEL_TARGET, targetLabel);
                if (!(psiElement instanceof KtLoopExpression)) {
                    this.this$0.trace.report(Errors.NOT_A_LOOP_LABEL.on(expression, targetLabel.getText()));
                    correspondingLoopWithoutLabel = null;
                } else {
                    correspondingLoopWithoutLabel = (KtLoopExpression) psiElement;
                }
            } else {
                correspondingLoopWithoutLabel = getCorrespondingLoopWithoutLabel(expression);
                if (correspondingLoopWithoutLabel == null) {
                    this.this$0.trace.report(Errors.BREAK_OR_CONTINUE_OUTSIDE_A_LOOP.on(expression));
                } else if (!this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.AllowBreakAndContinueInsideWhen)) {
                    KtExpressionWithLabel ktExpressionWithLabel = expression;
                    if (((KtWhenExpression) PsiTreeUtil.getParentOfType((PsiElement) ktExpressionWithLabel, KtWhenExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{KtLoopExpression.class})) != null) {
                        this.this$0.trace.report(Errors.BREAK_OR_CONTINUE_IN_WHEN.on(ktExpressionWithLabel));
                    }
                }
            }
            if (correspondingLoopWithoutLabel != null && (body = correspondingLoopWithoutLabel.getBody()) != null) {
                ControlFlowProcessor controlFlowProcessor = this.this$0;
                if (!body.getTextRange().contains(expression.getTextRange())) {
                    controlFlowProcessor.trace.report(Errors.BREAK_OR_CONTINUE_OUTSIDE_A_LOOP.on(expression));
                    return null;
                }
            }
            return correspondingLoopWithoutLabel;
        }

        private final boolean returnCrossesTryCatchBoundary(KtReturnExpression returnExpression) {
            PsiElement psiElement;
            KtSimpleNameExpression targetLabel = returnExpression.getTargetLabel();
            if (targetLabel == null || (psiElement = (PsiElement) this.this$0.trace.get(BindingContext.LABEL_TARGET, targetLabel)) == null) {
                return true;
            }
            return jumpCrossesTryCatchBoundary(returnExpression, psiElement);
        }

        private final boolean jumpCrossesTryCatchBoundary(KtExpressionWithLabel jumpExpression, PsiElement jumpTarget) {
            for (PsiElement parent = jumpExpression.getLightParent(); !Intrinsics.areEqual(parent, jumpTarget); parent = parent.getLightParent()) {
                if (parent instanceof KtTryExpression) {
                    return true;
                }
            }
            return false;
        }

        private final boolean jumpDoesNotCrossFunctionBoundary(KtExpressionWithLabel jumpExpression, KtLoopExpression jumpTarget) {
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            boolean zSupportsFeature = this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.BreakContinueInInlineLambdas);
            KtExpressionWithLabel ktExpressionWithLabel = jumpExpression;
            FunctionDescriptor enclosingFunctionDescriptor = BindingContextUtilsKt.getEnclosingFunctionDescriptor(context, ktExpressionWithLabel, zSupportsFeature);
            KtLoopExpression ktLoopExpression = jumpTarget;
            if (enclosingFunctionDescriptor == BindingContextUtilsKt.getEnclosingFunctionDescriptor(context, ktLoopExpression, zSupportsFeature)) {
                return true;
            }
            if (this.builder.getLoopExitPoint(jumpTarget) != null || ((enclosingFunctionDescriptor instanceof ConstructorDescriptor) && !((ConstructorDescriptor) enclosingFunctionDescriptor).isPrimary())) {
                if (zSupportsFeature || !Intrinsics.areEqual(BindingContextUtilsKt.getEnclosingFunctionDescriptor(context, ktExpressionWithLabel, true), BindingContextUtilsKt.getEnclosingFunctionDescriptor(context, ktLoopExpression, true))) {
                    this.this$0.trace.report(Errors.BREAK_OR_CONTINUE_JUMPS_ACROSS_FUNCTION_BOUNDARY.on(jumpExpression));
                } else {
                    this.this$0.trace.report(Errors.UNSUPPORTED_FEATURE.on(jumpExpression, TuplesKt.to(LanguageFeature.BreakContinueInInlineLambdas, this.this$0.languageVersionSettings)));
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitReturnExpression(KtReturnExpression expression) {
            KtElement returnSubroutine;
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (returnCrossesTryCatchBoundary(expression)) {
                generateJumpsToCatchAndFinally();
            }
            KtExpression returnedExpression = expression.getReturnedExpression();
            if (returnedExpression != null) {
                generateInstructions(returnedExpression);
            }
            KtSimpleNameExpression targetLabel = expression.getTargetLabel();
            String labelName = expression.getLabelName();
            if (targetLabel != null && labelName != null) {
                PsiElement psiElement = (PsiElement) this.this$0.trace.get(BindingContext.LABEL_TARGET, targetLabel);
                if (psiElement != null) {
                    returnSubroutine = (KtElement) psiElement;
                    checkReturnLabelTarget(expression, returnSubroutine);
                } else {
                    returnSubroutine = null;
                }
            } else {
                returnSubroutine = this.builder.getReturnSubroutine();
            }
            if ((returnSubroutine instanceof KtFunction) || (returnSubroutine instanceof KtPropertyAccessor)) {
                PseudoValue boundValue = returnedExpression != null ? this.builder.getBoundValue(returnedExpression) : null;
                if (boundValue == null) {
                    this.builder.returnNoValue(expression, returnSubroutine);
                    return;
                } else {
                    this.builder.returnValue(expression, boundValue, returnSubroutine);
                    return;
                }
            }
            createNonSyntheticValue(expression, MagicKind.UNSUPPORTED_ELEMENT, returnedExpression);
        }

        private final void checkReturnLabelTarget(KtReturnExpression returnExpression, KtElement labeledElement) {
            if ((labeledElement instanceof KtFunctionLiteral) || (labeledElement instanceof KtNamedFunction)) {
                return;
            }
            if (this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.RestrictReturnStatementTarget)) {
                this.this$0.trace.report(Errors.NOT_A_FUNCTION_LABEL.on(returnExpression));
            } else {
                this.this$0.trace.report(Errors.NOT_A_FUNCTION_LABEL_WARNING.on(returnExpression));
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitParameter(KtParameter parameter) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            this.builder.declareParameter(parameter);
            KtExpression defaultValue = parameter.getDefaultValue();
            if (defaultValue != null) {
                ControlFlowBuilder controlFlowBuilder = this.builder;
                StringBuilder sb = new StringBuilder("after default value for parameter ");
                String myName = parameter.getName();
                if (myName == null) {
                    myName = SpecialNames.ANONYMOUS_STRING;
                }
                Label labelCreateUnboundLabel = controlFlowBuilder.createUnboundLabel(sb.append(myName).toString());
                KtExpression ktExpression = defaultValue;
                this.builder.nondeterministicJump(labelCreateUnboundLabel, ktExpression, null);
                generateInstructions(ktExpression);
                this.builder.bindLabel(labelCreateUnboundLabel);
            }
            generateInitializer(parameter, computePseudoValueForParameter(parameter));
            KtDestructuringDeclaration destructuringDeclaration = parameter.getDestructuringDeclaration();
            if (destructuringDeclaration != null) {
                visitDestructuringDeclaration(destructuringDeclaration, true);
            }
        }

        private final PseudoValue computePseudoValueForParameter(KtParameter parameter) {
            PseudoValue pseudoValueCreateSyntheticValue = createSyntheticValue(parameter, MagicKind.FAKE_INITIALIZER, new KtElement[0]);
            PseudoValue boundValue = this.builder.getBoundValue(parameter.getDefaultValue());
            return boundValue == null ? pseudoValueCreateSyntheticValue : this.builder.merge(parameter, CollectionsKt.arrayListOf(boundValue, pseudoValueCreateSyntheticValue)).getResultValue();
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitBlockExpression(KtBlockExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            boolean zIsBlockInDoWhile = isBlockInDoWhile(expression);
            if (!zIsBlockInDoWhile) {
                this.builder.enterBlockScope(expression);
            }
            KtBlockExpression ktBlockExpression = expression;
            mark(ktBlockExpression);
            List<KtExpression> statements = expression.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            for (KtExpression ktExpression : statements) {
                Label labelCreateUnboundLabel = (ktExpression instanceof KtClassOrObject ? (KtClassOrObject) ktExpression : null) != null ? this.builder.createUnboundLabel("after local class") : null;
                if (labelCreateUnboundLabel != null) {
                    ControlFlowBuilder controlFlowBuilder = this.builder;
                    Intrinsics.checkNotNull(ktExpression);
                    controlFlowBuilder.nondeterministicJump(labelCreateUnboundLabel, ktExpression, null);
                }
                generateInstructions(ktExpression);
                if (labelCreateUnboundLabel != null) {
                    this.builder.bindLabel(labelCreateUnboundLabel);
                }
            }
            if (statements.isEmpty()) {
                this.builder.loadUnit(expression);
            } else {
                copyValue((KtElement) CollectionsKt.lastOrNull((List) statements), ktBlockExpression);
            }
            if (zIsBlockInDoWhile) {
                return;
            }
            this.builder.exitBlockScope(ktBlockExpression);
        }

        private final boolean isBlockInDoWhile(KtBlockExpression expression) {
            return expression.getLightParent().getLightParent() instanceof KtDoWhileExpression;
        }

        static /* synthetic */ void visitFunction$default(CFPVisitor cFPVisitor, KtFunction ktFunction, EventOccurrencesRange eventOccurrencesRange, int i, Object obj) {
            if ((i & 2) != 0) {
                eventOccurrencesRange = null;
            }
            cFPVisitor.visitFunction(ktFunction, eventOccurrencesRange);
        }

        private final void visitFunction(KtFunction function, EventOccurrencesRange eventOccurrencesRange) {
            if (eventOccurrencesRange == null) {
                this.this$0.processLocalDeclaration(function);
            } else {
                visitInlinedFunction(function, eventOccurrencesRange);
            }
            if ((function instanceof KtFunctionLiteral) || function.getName() == null || (function.isLocal() && !(function.getLightParent() instanceof KtBlockExpression))) {
                this.builder.createLambda(function);
            }
        }

        private final void visitInlinedFunction(final KtFunction lambdaFunctionLiteral, final EventOccurrencesRange eventOccurrencesRange) {
            List<Function1<ControlFlowBuilder, Unit>> listPeek = this.deferredGeneratorsStack.peek();
            final ControlFlowProcessor controlFlowProcessor = this.this$0;
            listPeek.add(new Function1<ControlFlowBuilder, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowProcessor$CFPVisitor$visitInlinedFunction$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ControlFlowBuilder controlFlowBuilder) {
                    invoke2(controlFlowBuilder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ControlFlowBuilder builder) {
                    Intrinsics.checkNotNullParameter(builder, "builder");
                    Label labelCreateUnboundLabel = builder.createUnboundLabel("before inlined declaration");
                    Label labelCreateUnboundLabel2 = builder.createUnboundLabel("after inlined declaration");
                    builder.bindLabel(labelCreateUnboundLabel);
                    if (!EventOccurrencesRangeKt.isDefinitelyVisited(eventOccurrencesRange)) {
                        builder.nondeterministicJump(labelCreateUnboundLabel2, lambdaFunctionLiteral, null);
                    }
                    controlFlowProcessor.generate(lambdaFunctionLiteral, eventOccurrencesRange);
                    if (EventOccurrencesRangeKt.canBeRevisited(eventOccurrencesRange)) {
                        builder.nondeterministicJump(labelCreateUnboundLabel, lambdaFunctionLiteral, null);
                    }
                    builder.bindLabel(labelCreateUnboundLabel2);
                }
            });
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitNamedFunction(KtNamedFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            visitFunction$default(this, function, null, 2, null);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitLambdaExpression(KtLambdaExpression lambdaExpression) {
            Intrinsics.checkNotNullParameter(lambdaExpression, "lambdaExpression");
            KtLambdaExpression ktLambdaExpression = lambdaExpression;
            mark(ktLambdaExpression);
            KtFunctionLiteral functionLiteral = lambdaExpression.getFunctionLiteral();
            Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
            visitFunction(functionLiteral, (EventOccurrencesRange) this.this$0.trace.get(BindingContext.LAMBDA_INVOCATIONS, lambdaExpression));
            copyValue(functionLiteral, ktLambdaExpression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitQualifiedExpression(KtQualifiedExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtQualifiedExpression ktQualifiedExpression = expression;
            mark(ktQualifiedExpression);
            KtExpression selectorExpression = expression.getSelectorExpression();
            KtExpression receiverExpression = expression.getReceiverExpression();
            boolean z = expression instanceof KtSafeQualifiedExpression;
            if ((selectorExpression instanceof KtCallExpression) || (selectorExpression instanceof KtSimpleNameExpression)) {
                if (!z) {
                    generateInstructions(selectorExpression);
                } else {
                    Label labelCreateUnboundLabel = this.builder.createUnboundLabel("result of call");
                    this.builder.jumpOnFalse(labelCreateUnboundLabel, ktQualifiedExpression, null);
                    generateInstructions(selectorExpression);
                    this.builder.bindLabel(labelCreateUnboundLabel);
                }
                copyValue(selectorExpression, ktQualifiedExpression);
                return;
            }
            generateInstructions(receiverExpression);
            createNonSyntheticValue(ktQualifiedExpression, MagicKind.UNSUPPORTED_ELEMENT, receiverExpression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitCallExpression(KtCallExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtCallExpression ktCallExpression = expression;
            if (generateCall(ktCallExpression)) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<KtValueArgument> it = expression.getValueArguments().iterator();
            while (it.hasNext()) {
                KtExpression argumentExpression = it.next().getArgumentExpression();
                if (argumentExpression != null) {
                    generateInstructions(argumentExpression);
                    arrayList.add(argumentExpression);
                }
            }
            KtExpression calleeExpression = expression.getCalleeExpression();
            generateInstructions(calleeExpression);
            if (calleeExpression != null) {
                arrayList.add(calleeExpression);
                KtExpression ktExpressionGenerateAndGetReceiverIfAny = generateAndGetReceiverIfAny(expression);
                if (ktExpressionGenerateAndGetReceiverIfAny != null) {
                    arrayList.add(ktExpressionGenerateAndGetReceiverIfAny);
                }
            }
            mark(ktCallExpression);
            createNonSyntheticValue(ktCallExpression, arrayList, MagicKind.UNRESOLVED_CALL);
        }

        private final KtExpression generateAndGetReceiverIfAny(KtExpression expression) {
            PsiElement parent = expression.getLightParent();
            KtQualifiedExpression ktQualifiedExpression = parent instanceof KtQualifiedExpression ? (KtQualifiedExpression) parent : null;
            if (ktQualifiedExpression == null || ktQualifiedExpression.getSelectorExpression() != expression) {
                return null;
            }
            KtExpression receiverExpression = ktQualifiedExpression.getReceiverExpression();
            generateInstructions(receiverExpression);
            return receiverExpression;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitProperty(KtProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            this.builder.declareVariable(property);
            KtExpression initializer = property.getInitializer();
            if (initializer != null) {
                KtProperty ktProperty = property;
                visitAssignment(ktProperty, getDeferredValue(initializer), ktProperty);
            }
            KtExpression delegateExpression = property.getDelegateExpression();
            if (delegateExpression != null) {
                KtProperty ktProperty2 = property;
                visitAssignment(ktProperty2, getDeferredValue(null), ktProperty2);
                KtExpression ktExpression = delegateExpression;
                generateInstructions(ktExpression);
                if (property.isLocal()) {
                    generateInitializer(property, createSyntheticValue(property, MagicKind.FAKE_INITIALIZER, new KtElement[0]));
                }
                if (this.builder.getBoundValue(ktExpression) != null) {
                    createSyntheticValue(property, MagicKind.VALUE_CONSUMER, delegateExpression);
                }
            }
            if (KtPsiUtil.isLocal(property)) {
                Iterator<KtPropertyAccessor> it = property.getAccessors().iterator();
                while (it.hasNext()) {
                    generateInstructions(it.next());
                }
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitDestructuringDeclaration(KtDestructuringDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            visitDestructuringDeclaration(declaration, true);
        }

        private final void visitDestructuringDeclaration(KtDestructuringDeclaration declaration, boolean generateWriteForEntries) {
            PseudoValue pseudoValueCreateSyntheticValue;
            KtExpression initializer = declaration.getInitializer();
            generateInstructions(initializer);
            for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : declaration.getEntries()) {
                ControlFlowBuilder controlFlowBuilder = this.builder;
                Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                controlFlowBuilder.declareVariable(ktDestructuringDeclarationEntry);
                ResolvedCall<?> resolvedCall = (ResolvedCall) this.this$0.trace.get(BindingContext.COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry);
                if (resolvedCall != null) {
                    pseudoValueCreateSyntheticValue = this.builder.call(ktDestructuringDeclarationEntry, resolvedCall, getReceiverValues(resolvedCall), MapsKt.emptyMap()).getResultValue();
                } else {
                    pseudoValueCreateSyntheticValue = initializer != null ? createSyntheticValue(ktDestructuringDeclarationEntry, MagicKind.UNRESOLVED_CALL, initializer) : null;
                }
                if (generateWriteForEntries) {
                    KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktDestructuringDeclarationEntry;
                    if (pseudoValueCreateSyntheticValue == null) {
                        pseudoValueCreateSyntheticValue = createSyntheticValue(ktDestructuringDeclarationEntry, MagicKind.FAKE_INITIALIZER, new KtElement[0]);
                    }
                    generateInitializer(ktDestructuringDeclarationEntry2, pseudoValueCreateSyntheticValue);
                }
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitPropertyAccessor(KtPropertyAccessor accessor) {
            Intrinsics.checkNotNullParameter(accessor, "accessor");
            this.this$0.processLocalDeclaration(accessor);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS = expression;
            mark(ktBinaryExpressionWithTypeRHS);
            IElementType referencedNameElementType = expression.getOperationReference().getReferencedNameElementType();
            KtExpression left = expression.getLeft();
            Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
            if (referencedNameElementType == KtTokens.AS_KEYWORD || referencedNameElementType == KtTokens.AS_SAFE) {
                KtExpression ktExpression = left;
                generateInstructions(ktExpression);
                if (getBoundOrUnreachableValue(ktExpression) != null) {
                    createNonSyntheticValue(ktBinaryExpressionWithTypeRHS, MagicKind.CAST, left);
                    return;
                }
                return;
            }
            visitKtElement(ktBinaryExpressionWithTypeRHS);
            createNonSyntheticValue(ktBinaryExpressionWithTypeRHS, MagicKind.UNSUPPORTED_ELEMENT, left);
        }

        private final void generateJumpsToCatchAndFinally() {
            if (this.catchFinallyStack.isEmpty()) {
                return;
            }
            CatchFinallyLabels catchFinallyLabelsPeek = this.catchFinallyStack.peek();
            if (catchFinallyLabelsPeek.getTryExpression() != null) {
                Label onException = catchFinallyLabelsPeek.getOnException();
                if (onException != null) {
                    this.builder.nondeterministicJump(onException, catchFinallyLabelsPeek.getTryExpression(), null);
                }
                Label toFinally = catchFinallyLabelsPeek.getToFinally();
                if (toFinally != null) {
                    this.builder.nondeterministicJump(toFinally, catchFinallyLabelsPeek.getTryExpression(), null);
                }
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitThrowExpression(KtThrowExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            mark(expression);
            generateJumpsToCatchAndFinally();
            KtExpression thrownExpression = expression.getThrownExpression();
            if (thrownExpression == null) {
                return;
            }
            KtExpression ktExpression = thrownExpression;
            generateInstructions(ktExpression);
            PseudoValue boundValue = this.builder.getBoundValue(ktExpression);
            if (boundValue == null) {
                return;
            }
            this.builder.throwException(expression, boundValue);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitArrayAccessExpression(KtArrayAccessExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            generateArrayAccess(expression, (ResolvedCall) this.this$0.trace.get(BindingContext.INDEXED_LVALUE_GET, expression));
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitIsExpression(KtIsExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtIsExpression ktIsExpression = expression;
            mark(ktIsExpression);
            KtExpression leftHandSide = expression.getLeftHandSide();
            Intrinsics.checkNotNullExpressionValue(leftHandSide, "getLeftHandSide(...)");
            generateInstructions(leftHandSide);
            createNonSyntheticValue(ktIsExpression, MagicKind.IS, leftHandSide);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitWhenExpression(KtWhenExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtWhenExpression ktWhenExpression = expression;
            mark(ktWhenExpression);
            KtExpression subjectExpression = expression.getSubjectExpression();
            if (subjectExpression != null) {
                generateInstructions(subjectExpression);
            }
            ArrayList arrayList = new ArrayList();
            Label labelCreateUnboundLabel = this.builder.createUnboundLabel("after 'when' expression");
            Iterator<KtWhenEntry> it = expression.getEntries().iterator();
            Label labelCreateUnboundLabel2 = null;
            while (it.hasNext()) {
                KtWhenEntry next = it.next();
                Intrinsics.checkNotNull(next);
                mark(next);
                boolean zIsElse = next.isElse();
                if (zIsElse && it.hasNext()) {
                    this.this$0.trace.report(Errors.ELSE_MISPLACED_IN_WHEN.on(next));
                }
                Label labelCreateUnboundLabel3 = this.builder.createUnboundLabel("'when' entry body");
                KtWhenCondition[] conditions = next.getConditions();
                Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
                int length = conditions.length;
                int i = 0;
                while (i < length) {
                    KtWhenCondition ktWhenCondition = conditions[i];
                    ktWhenCondition.accept(this.conditionVisitor);
                    i++;
                    if (i < conditions.length) {
                        ControlFlowBuilder controlFlowBuilder = this.builder;
                        controlFlowBuilder.nondeterministicJump(labelCreateUnboundLabel3, ktWhenExpression, controlFlowBuilder.getBoundValue(ktWhenCondition));
                    }
                }
                if (!zIsElse) {
                    labelCreateUnboundLabel2 = this.builder.createUnboundLabel("next 'when' entry");
                    KtWhenCondition ktWhenCondition2 = (KtWhenCondition) ArraysKt.lastOrNull(conditions);
                    ControlFlowBuilder controlFlowBuilder2 = this.builder;
                    controlFlowBuilder2.nondeterministicJump(labelCreateUnboundLabel2, ktWhenExpression, controlFlowBuilder2.getBoundValue(ktWhenCondition2));
                }
                this.builder.bindLabel(labelCreateUnboundLabel3);
                KtExpression expression2 = next.getExpression();
                if (expression2 != null) {
                    generateInstructions(expression2);
                    arrayList.add(expression2);
                }
                this.builder.jump(labelCreateUnboundLabel, ktWhenExpression);
                if (!zIsElse && labelCreateUnboundLabel2 != null) {
                    this.builder.bindLabel(labelCreateUnboundLabel2);
                    if (!it.hasNext() && WhenChecker.isWhenExhaustive(expression, this.this$0.trace)) {
                        this.builder.magic(ktWhenExpression, null, CollectionsKt.emptyList(), MagicKind.EXHAUSTIVE_WHEN_ELSE);
                    }
                }
            }
            this.builder.bindLabel(labelCreateUnboundLabel);
            mergeValues(arrayList, expression);
            WhenChecker.INSTANCE.checkDuplicatedLabels(expression, this.this$0.trace, this.this$0.languageVersionSettings);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitObjectLiteralExpression(KtObjectLiteralExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            mark(expression);
            KtObjectDeclaration objectDeclaration = expression.getObjectDeclaration();
            Intrinsics.checkNotNullExpressionValue(objectDeclaration, "getObjectDeclaration(...)");
            generateInstructions(objectDeclaration);
            this.builder.createAnonymousObject(expression);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitObjectDeclaration(KtObjectDeclaration objectDeclaration) {
            Intrinsics.checkNotNullParameter(objectDeclaration, "objectDeclaration");
            KtObjectDeclaration ktObjectDeclaration = objectDeclaration;
            generateHeaderDelegationSpecifiers(ktObjectDeclaration);
            generateInitializersForClassOrObject(objectDeclaration);
            generateDeclarationForLocalClassOrObjectIfNeeded(ktObjectDeclaration);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitStringTemplateExpression(KtStringTemplateExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            mark(expression);
            ArrayList arrayList = new ArrayList();
            KtStringTemplateEntry[] entries = expression.getEntries();
            Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
            for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
                if (ktStringTemplateEntry instanceof KtStringTemplateEntryWithExpression) {
                    KtExpression expression2 = ktStringTemplateEntry.getExpression();
                    generateInstructions(expression2);
                    if (expression2 != null) {
                        arrayList.add(expression2);
                    }
                }
            }
            this.builder.loadStringTemplate(expression, elementsToValues(arrayList));
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitAnonymousInitializer(KtAnonymousInitializer classInitializer) {
            Intrinsics.checkNotNullParameter(classInitializer, "classInitializer");
            generateInstructions(classInitializer.getBody());
        }

        private final void generateHeaderDelegationSpecifiers(KtClassOrObject classOrObject) {
            Iterator<KtSuperTypeListEntry> it = classOrObject.getSuperTypeListEntries().iterator();
            while (it.hasNext()) {
                generateInstructions(it.next());
            }
        }

        private final void generateInitializersForClassOrObject(KtDeclarationContainer classOrObject) {
            for (KtDeclaration ktDeclaration : classOrObject.getDeclarations()) {
                if ((ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtAnonymousInitializer)) {
                    generateInstructions(ktDeclaration);
                }
            }
        }

        private final void processEntryOrObject(KtClassOrObject entryOrObject) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.this$0.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, entryOrObject);
            if (declarationDescriptor instanceof ClassDescriptor) {
                this.builder.declareEntryOrObject(entryOrObject);
                KtClassOrObject ktClassOrObject = entryOrObject;
                this.builder.write(ktClassOrObject, ktClassOrObject, createSyntheticValue(ktClassOrObject, MagicKind.FAKE_INITIALIZER, new KtElement[0]), new AccessTarget.Declaration(new FakeCallableDescriptorForObject((ClassDescriptor) declarationDescriptor)), MapsKt.emptyMap());
                generateInstructions(ktClassOrObject);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitClass(KtClass klass) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            if (klass.hasPrimaryConstructor()) {
                processParameters(klass.getPrimaryConstructorParameters());
                generateHeaderDelegationSpecifiers(klass);
                generateInitializersForClassOrObject(klass);
            }
            generateDeclarationForLocalClassOrObjectIfNeeded(klass);
            if (klass.isEnum()) {
                for (KtDeclaration ktDeclaration : klass.getDeclarations()) {
                    if (ktDeclaration instanceof KtEnumEntry) {
                        processEntryOrObject((KtClassOrObject) ktDeclaration);
                    } else if ((ktDeclaration instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktDeclaration).isCompanion()) {
                        processEntryOrObject((KtClassOrObject) ktDeclaration);
                    }
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:10:0x002d  */
        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitScript(KtScript script) {
            KtScriptInitializer ktScriptInitializer;
            Intrinsics.checkNotNullParameter(script, "script");
            KtBlockExpression ktBlockExpression = (KtBlockExpression) PsiTreeUtil.getChildOfType(script, KtBlockExpression.class);
            boolean z = false;
            if (ktBlockExpression == null) {
                ktScriptInitializer = null;
            } else {
                KtScriptInitializer[] childrenOfType = PsiTreeUtil.getChildrenOfType(ktBlockExpression, KtScriptInitializer.class);
                if (childrenOfType == null) {
                    childrenOfType = new KtScriptInitializer[0];
                }
                KtScriptInitializer[] ktScriptInitializerArr = (KtScriptInitializer[]) childrenOfType;
                if (ktScriptInitializerArr != null) {
                    ktScriptInitializer = (KtScriptInitializer) ArraysKt.lastOrNull(ktScriptInitializerArr);
                } else {
                    ktScriptInitializer = null;
                }
            }
            KtExpression ktExpression = ktScriptInitializer != null ? (KtExpression) PsiTreeUtil.getChildOfType(ktScriptInitializer, KtExpression.class) : null;
            KotlinType type = ktExpression != null ? this.this$0.trace.getContext().getType(ktExpression) : null;
            if (type != null && !TypeUtilsKt.isUnit(type) && !TypeUtilsKt.isNothing(type)) {
                z = true;
            }
            for (KtDeclaration ktDeclaration : script.getDeclarations()) {
                if (ktDeclaration instanceof KtAnonymousInitializer) {
                    generateInstructions(ktDeclaration);
                    if (z && Intrinsics.areEqual(ktDeclaration, ktScriptInitializer) && ktExpression != null) {
                        BindingContextUtilsKt.recordUsedAsExpression(ktExpression, this.this$0.trace, true);
                    }
                } else if ((ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtDestructuringDeclaration)) {
                    generateInstructions(ktDeclaration);
                }
            }
        }

        private final void generateDeclarationForLocalClassOrObjectIfNeeded(KtClassOrObject classOrObject) {
            if (classOrObject.isLocal()) {
                for (KtDeclaration ktDeclaration : classOrObject.getDeclarations()) {
                    if (!(ktDeclaration instanceof KtSecondaryConstructor) && !(ktDeclaration instanceof KtProperty) && !(ktDeclaration instanceof KtAnonymousInitializer)) {
                        generateInstructions(ktDeclaration);
                    }
                }
            }
        }

        private final void processParameters(List<? extends KtParameter> parameters) {
            Iterator<? extends KtParameter> it = parameters.iterator();
            while (it.hasNext()) {
                generateInstructions(it.next());
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitSecondaryConstructor(KtSecondaryConstructor constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            KtClassOrObject ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(constructor, KtClassOrObject.class);
            if (ktClassOrObject == null) {
                throw new IllegalStateException("Guaranteed by parsing contract".toString());
            }
            processParameters(constructor.getValueParameters());
            generateCallOrMarkUnresolved(constructor.getDelegationCall());
            if (!constructor.getDelegationCall().isCallToThis()) {
                generateInitializersForClassOrObject(ktClassOrObject);
            }
            generateInstructions(constructor.getBodyExpression());
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitSuperTypeCallEntry(KtSuperTypeCallEntry call) {
            Intrinsics.checkNotNullParameter(call, "call");
            generateCallOrMarkUnresolved(call);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitInitializerList(KtInitializerList list) {
            Intrinsics.checkNotNullParameter(list, "list");
            list.acceptChildren(this);
        }

        private final void generateCallOrMarkUnresolved(KtCallElement call) {
            KtCallElement ktCallElement = call;
            if (generateCall(ktCallElement)) {
                return;
            }
            List<? extends ValueArgument> valueArguments = call.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = valueArguments.iterator();
            while (it.hasNext()) {
                KtExpression argumentExpression = ((ValueArgument) it.next()).getArgumentExpression();
                if (argumentExpression != null) {
                    arrayList.add(argumentExpression);
                }
            }
            ArrayList arrayList2 = arrayList;
            Iterator<? extends KtElement> it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                generateInstructions((KtExpression) it2.next());
            }
            createNonSyntheticValue(ktCallElement, arrayList2, MagicKind.UNRESOLVED_CALL);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry specifier) {
            Intrinsics.checkNotNullParameter(specifier, "specifier");
            KtExpression delegateExpression = specifier.getDelegateExpression();
            generateInstructions(delegateExpression);
            if (delegateExpression != null) {
                createSyntheticValue(specifier, MagicKind.VALUE_CONSUMER, delegateExpression);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitSuperTypeList(KtSuperTypeList list) {
            Intrinsics.checkNotNullParameter(list, "list");
            list.acceptChildren(this);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtFile(KtFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            for (KtDeclaration ktDeclaration : file.getDeclarations()) {
                if (ktDeclaration instanceof KtProperty) {
                    generateInstructions(ktDeclaration);
                }
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitDoubleColonExpression(KtDoubleColonExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            KtDoubleColonExpression ktDoubleColonExpression = expression;
            mark(ktDoubleColonExpression);
            KtExpression receiverExpression = expression.getReceiverExpression();
            if (receiverExpression != null && (this.this$0.trace.getContext().get(BindingContext.DOUBLE_COLON_LHS, receiverExpression) instanceof DoubleColonLHS.Expression)) {
                generateInstructions(receiverExpression);
                createNonSyntheticValue(ktDoubleColonExpression, MagicKind.BOUND_CALLABLE_REFERENCE, receiverExpression);
            } else {
                createNonSyntheticValue(ktDoubleColonExpression, MagicKind.UNBOUND_CALLABLE_REFERENCE, new KtElement[0]);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtElement(KtElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            createNonSyntheticValue(element, MagicKind.UNSUPPORTED_ELEMENT, new KtElement[0]);
        }

        private final boolean generateQualifier(KtExpression expression, Qualifier qualifier) {
            FakeCallableDescriptorForObject fakeDescriptorForObject;
            DeclarationDescriptor descriptor = qualifier.getDescriptor();
            if (!(descriptor instanceof ClassDescriptor) || (fakeDescriptorForObject = TowerLevelsKt.getFakeDescriptorForObject((ClassifierDescriptor) descriptor)) == null) {
                return false;
            }
            KtExpression ktExpression = expression;
            mark(ktExpression);
            this.builder.read(ktExpression, new AccessTarget.Declaration(fakeDescriptorForObject), MapsKt.emptyMap());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean generateCall(KtElement callElement) {
            Call call;
            KtElement callElement2;
            BindingContext context = this.this$0.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(callElement, context);
            if (resolvedCall == null || (call = resolvedCall.getCall()) == null || (callElement2 = call.getCallElement()) == null || PsiUtilsKt.isAncestor(callElement, callElement2, true)) {
                return false;
            }
            return checkAndGenerateCall(resolvedCall);
        }

        private final boolean checkAndGenerateCall(ResolvedCall<?> resolvedCall) {
            if (resolvedCall == null) {
                return false;
            }
            generateCall(resolvedCall);
            return true;
        }

        private final InstructionWithValue generateCall(ResolvedCall<?> resolvedCall) {
            InstructionWithValue instructionWithValueCall;
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            Map<PseudoValue, ReceiverValue> receiverValues = getReceiverValues(resolvedCall);
            this.deferredGeneratorsStack.push(new ArrayList());
            SmartFMap<PseudoValue, ValueParameterDescriptor> smartFMapEmptyMap = SmartFMap.emptyMap();
            Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "emptyMap(...)");
            for (ValueArgument valueArgument : resolvedCall.getCall().getValueArguments()) {
                ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping(valueArgument);
                Intrinsics.checkNotNullExpressionValue(argumentMapping, "getArgumentMapping(...)");
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentMapping instanceof ArgumentMatch) {
                    Intrinsics.checkNotNull(valueArgument);
                    smartFMapEmptyMap = generateValueArgument(valueArgument, ((ArgumentMatch) argumentMapping).getValueParameter(), smartFMapEmptyMap);
                } else if (argumentExpression != null) {
                    KtExpression ktExpression = argumentExpression;
                    generateInstructions(ktExpression);
                    createSyntheticValue(ktExpression, MagicKind.VALUE_CONSUMER, argumentExpression);
                }
            }
            if (resolvedCall.getResultingDescriptor() instanceof VariableDescriptor) {
                KtExpression ktExpression2 = callElement instanceof KtExpression ? (KtExpression) callElement : null;
                if (ktExpression2 == null) {
                    throw new IllegalStateException(("Variable-based call without callee expression: " + callElement.getText()).toString());
                }
                smartFMapEmptyMap.isEmpty();
                instructionWithValueCall = this.builder.readVariable(ktExpression2, resolvedCall, receiverValues);
            } else {
                KtElement callElement2 = resolvedCall.getCall().getCallElement();
                Intrinsics.checkNotNullExpressionValue(callElement2, "getCallElement(...)");
                mark(callElement2);
                instructionWithValueCall = this.builder.call(callElement, resolvedCall, receiverValues, smartFMapEmptyMap);
            }
            List<Function1<ControlFlowBuilder, Unit>> listPop = this.deferredGeneratorsStack.pop();
            Intrinsics.checkNotNullExpressionValue(listPop, "pop(...)");
            Iterator<T> it = listPop.iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).invoke(this.builder);
            }
            return instructionWithValueCall;
        }

        private final Map<PseudoValue, ReceiverValue> getReceiverValues(ResolvedCall<?> resolvedCall) {
            PseudoValue resultValue;
            ReceiverValue dispatchReceiver = null;
            if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                resultValue = generateCall(((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall()).getResultValue();
                int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
                if (i == 1) {
                    dispatchReceiver = resolvedCall.getDispatchReceiver();
                } else if (i == 2 || i == 3) {
                    dispatchReceiver = resolvedCall.getExtensionReceiver();
                }
            } else {
                resultValue = null;
            }
            SmartFMap<PseudoValue, ReceiverValue> smartFMapEmptyMap = SmartFMap.emptyMap();
            Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "emptyMap(...)");
            if (dispatchReceiver != null && resultValue != null) {
                smartFMapEmptyMap = smartFMapEmptyMap.plus(resultValue, dispatchReceiver);
                Intrinsics.checkNotNullExpressionValue(smartFMapEmptyMap, "plus(...)");
            }
            KtElement callElement = resolvedCall.getCall().getCallElement();
            Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
            return getReceiverValues(callElement, resolvedCall.getExtensionReceiver(), getReceiverValues(callElement, resolvedCall.getDispatchReceiver(), smartFMapEmptyMap));
        }

        private final SmartFMap<PseudoValue, ReceiverValue> getReceiverValues(KtElement callElement, ReceiverValue receiver, SmartFMap<PseudoValue, ReceiverValue> receiverValuesArg) {
            if (receiver == null || receiverValuesArg.containsValue(receiver)) {
                return receiverValuesArg;
            }
            if (receiver instanceof ImplicitReceiver) {
                if (callElement instanceof KtCallExpression) {
                    DeclarationDescriptor declarationDescriptor = ((ImplicitReceiver) receiver).getDeclarationDescriptor();
                    if (declarationDescriptor instanceof ClassDescriptor) {
                        FakeCallableDescriptorForObject fakeDescriptorForObject = TowerLevelsKt.getFakeDescriptorForObject((ClassifierDescriptor) declarationDescriptor);
                        KtExpression calleeExpression = ((KtCallExpression) callElement).getCalleeExpression();
                        if (fakeDescriptorForObject != null && calleeExpression != null) {
                            this.builder.read(calleeExpression, new AccessTarget.Declaration(fakeDescriptorForObject), MapsKt.emptyMap());
                        }
                    }
                }
                SmartFMap<PseudoValue, ReceiverValue> smartFMapPlus = receiverValuesArg.plus(createSyntheticValue(callElement, MagicKind.IMPLICIT_RECEIVER, new KtElement[0]), receiver);
                Intrinsics.checkNotNullExpressionValue(smartFMapPlus, "plus(...)");
                return smartFMapPlus;
            }
            if (receiver instanceof ExpressionReceiver) {
                KtExpression expression = ((ExpressionReceiver) receiver).getExpression();
                if (this.builder.getBoundValue(expression) == null) {
                    generateInstructions(expression);
                }
                PseudoValue boundOrUnreachableValue = getBoundOrUnreachableValue(expression);
                if (boundOrUnreachableValue == null) {
                    return receiverValuesArg;
                }
                SmartFMap<PseudoValue, ReceiverValue> smartFMapPlus2 = receiverValuesArg.plus(boundOrUnreachableValue, receiver);
                Intrinsics.checkNotNullExpressionValue(smartFMapPlus2, "plus(...)");
                return smartFMapPlus2;
            }
            if (receiver instanceof TransientReceiver) {
                return receiverValuesArg;
            }
            throw new IllegalArgumentException("Unknown receiver kind: " + receiver);
        }

        private final SmartFMap<PseudoValue, ValueParameterDescriptor> generateValueArgument(ValueArgument valueArgument, ValueParameterDescriptor parameterDescriptor, SmartFMap<PseudoValue, ValueParameterDescriptor> parameterValuesArg) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression == null) {
                return parameterValuesArg;
            }
            if (!valueArgument.isExternal()) {
                generateInstructions(argumentExpression);
            }
            PseudoValue boundOrUnreachableValue = getBoundOrUnreachableValue(argumentExpression);
            if (boundOrUnreachableValue == null) {
                return parameterValuesArg;
            }
            SmartFMap<PseudoValue, ValueParameterDescriptor> smartFMapPlus = parameterValuesArg.plus(boundOrUnreachableValue, parameterDescriptor);
            Intrinsics.checkNotNullExpressionValue(smartFMapPlus, "plus(...)");
            return smartFMapPlus;
        }
    }
}
