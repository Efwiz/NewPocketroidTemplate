package org.jetbrains.kotlin.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.PseudocodeUtil;
import org.jetbrains.kotlin.cfg.pseudocode.PseudocodeUtilsKt;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.LoadUnitValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.WriteValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ConditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.JumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.NondeterministicJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnNoValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.UnconditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.MarkInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineExitInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.Edges;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.PseudocodeTraverserKt;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraversalOrder;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraverseInstructionResult;
import org.jetbrains.kotlin.cfg.variable.BlockScopeVariableInfo;
import org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData;
import org.jetbrains.kotlin.cfg.variable.VariableControlFlowState;
import org.jetbrains.kotlin.cfg.variable.VariableInitReadOnlyControlFlowInfo;
import org.jetbrains.kotlin.cfg.variable.VariableUseState;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtClassInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DeclarationsCheckerKt;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.checkers.CapturingInClosureCheckerKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.IncrementalTrackerUtilKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008e\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u007f2\u00020\u0001:\f~\u007f\u0080\u0001\u0081\u0001\u0082\u0001\u0083\u0001\u0084\u0001B3\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB9\b\u0002\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\u0010J\u001c\u0010 \u001a\u00020\u001c2\n\u0010!\u001a\u00060\"R\u00020\u00002\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020&H\u0002J\b\u0010'\u001a\u00020&H\u0016J\u0018\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002J(\u0010-\u001a\u00020&2\b\u0010.\u001a\u0004\u0018\u00010*2\u0006\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020302H\u0002J\b\u00104\u001a\u00020&H\u0016J\u0010\u00105\u001a\u00020&2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u00106\u001a\u00020&2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u00107\u001a\u00020&H\u0002J\u0010\u00108\u001a\u00020&2\u0006\u0010#\u001a\u00020$H\u0002J\u001c\u00109\u001a\u00020\u001c2\n\u0010!\u001a\u00060\"R\u00020\u00002\u0006\u0010#\u001a\u00020$H\u0002J*\u0010:\u001a\u00020&2\n\u0010!\u001a\u00060\"R\u00020\u00002\u0006\u0010/\u001a\u00020\u00032\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001d0<H\u0002J\b\u0010=\u001a\u00020&H\u0002J2\u0010>\u001a\u00020\u001c2\n\u0010!\u001a\u00060\"R\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010?\u001a\u00020@2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001d0<H\u0002J\b\u0010B\u001a\u00020&H\u0002J\"\u0010C\u001a\u00020&2\b\u0010.\u001a\u0004\u0018\u00010*2\u0006\u0010/\u001a\u0002002\u0006\u0010D\u001a\u00020EH\u0002J\b\u0010F\u001a\u00020GH\u0002J\b\u0010H\u001a\u00020,H\u0002J\u0018\u0010I\u001a\u00020\u001c2\u0006\u0010J\u001a\u00020\u001d2\u0006\u0010?\u001a\u00020@H\u0002J\u0010\u0010K\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u0003H\u0002J\u0010\u0010L\u001a\u00020&2\u0006\u0010M\u001a\u00020NH\u0002J\b\u0010O\u001a\u00020&H\u0002J\b\u0010P\u001a\u00020&H\u0002J\u0010\u0010P\u001a\u00020&2\u0006\u0010Q\u001a\u00020RH\u0002J\u0010\u0010S\u001a\u00020&2\u0006\u0010T\u001a\u00020UH\u0002J\u0010\u0010V\u001a\u00020&2\u0006\u0010T\u001a\u00020UH\u0002J\u0010\u0010W\u001a\u00020&2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010X\u001a\u00020&H\u0002J\b\u0010Y\u001a\u00020&H\u0002J\b\u0010Z\u001a\u00020&H\u0002J\b\u0010[\u001a\u00020&H\u0002J.\u0010\\\u001a\u00020&2\u0006\u0010/\u001a\u00020]2\u0006\u0010J\u001a\u00020\u001d2\n\u0010!\u001a\u00060^R\u00020\u00002\b\u0010_\u001a\u0004\u0018\u00010`H\u0002J$\u0010a\u001a\u00020&2\n\u0010!\u001a\u00060^R\u00020\u00002\u0006\u0010/\u001a\u00020b2\u0006\u0010J\u001a\u00020\u001dH\u0002J\b\u0010c\u001a\u00020&H\u0002J*\u0010c\u001a\u00020&2\u0006\u0010\u000e\u001a\u00020\u000f2\u0018\u0010d\u001a\u0014\u0012\u0004\u0012\u00020U\u0012\n\u0012\b\u0012\u0004\u0012\u00020g0f0eH\u0002J\u001c\u0010h\u001a\u00020&2\u0006\u0010i\u001a\u00020j2\n\u0010!\u001a\u00060kR\u00020\u0000H\u0002J\u0010\u0010l\u001a\u00020&2\u0006\u0010+\u001a\u00020,H\u0002J$\u0010m\u001a\u00020&2\u0006\u0010#\u001a\u00020$2\u0006\u0010J\u001a\u00020\u001d2\n\u0010!\u001a\u00060\"R\u00020\u0000H\u0002J,\u0010n\u001a\u00020&2\u0006\u0010o\u001a\u00020p2\u0006\u0010#\u001a\u00020$2\u0006\u0010J\u001a\u00020q2\n\u0010!\u001a\u00060\"R\u00020\u0000H\u0002JG\u0010r\u001a\u00020&2<\b\u0004\u0010s\u001a6\u0012\u0013\u0012\u00110u¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b(T\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030x¢\u0006\f\bv\u0012\b\bw\u0012\u0004\b\b(y\u0012\u0004\u0012\u00020&0tH\u0082\bJ\f\u0010z\u001a\u00020\u001c*\u00020qH\u0002J\u0010\u0010{\u001a\u00020\u001c*\u00060\"R\u00020\u0000H\u0002J\u0016\u0010|\u001a\u00020\u001c*\u00020u2\b\b\u0002\u0010\r\u001a\u00020\u0003H\u0002J\f\u0010}\u001a\u00020&*\u00020$H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\u00020\u0018*\u00020\u00188BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\u00020\u001c*\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001eR\u0018\u0010\u001f\u001a\u00020\u001c*\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001e¨\u0006\u0085\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider;", "declaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticSuppressor", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;)V", "subroutine", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;)V", "pseudocodeVariablesData", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;", "getPseudocodeVariablesData", "()Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;", "pseudocodeVariablesData$delegate", "Lkotlin/Lazy;", "deparenthesizedParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getDeparenthesizedParent", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;", "isLocalVariableWithDelegate", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;)Z", "isLocalVariableWithProvideDelegate", "checkAssignmentBeforeDeclaration", "ctxt", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableInitContext;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkConstructorConsistency", "", "checkDeclaration", "checkDefiniteReturn", "expectedReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "unreachableCode", "Lorg/jetbrains/kotlin/cfg/UnreachableCode;", "checkExhaustiveWhenStatement", "subjectType", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "missingCases", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "checkForLocalClassOrObjectMode", "checkForSuspendLambdaAndMarkParameters", "checkFunction", "checkIfExpressions", "checkImplicitCastOnConditionalExpression", "checkInitializationForCustomSetter", "checkIsInitialized", "varWithUninitializedErrorGenerated", "", "checkLocalFunctions", "checkValReassignment", "writeValueInstruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;", "varWithValReassignErrorGenerated", "checkWhenExpressions", "checkWhenStatement", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "collectReturnExpressions", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$ReturnedExpressionsInfo;", "collectUnreachableCode", "isCapturedWrite", "variableDescriptor", "isInsideTry", "markAndCheckRecursiveTailCalls", "subroutineDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "markAndCheckTailCalls", "markAnnotationArguments", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "markImplicitReceiverOfSuspendLambda", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "markReadOfSuspendLambdaParameter", "markReadOfSuspendLambdaParameters", "markStatements", "markUninitializedVariables", "markUnusedExpressions", "markUnusedVariables", "processUnusedDeclaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableUseContext;", "variableUseState", "Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "processUnusedParameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "recordInitializedVariables", "initializersMap", "", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableContext;", "reportUnreachableCode", "reportValReassigned", "reportVisibilityWarningForInternalFakeSetterOverride", "setterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "traverseCalls", "onCall", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "Lkotlin/ParameterName;", "name", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "resolvedCall", "isDefinitelyInitialized", "isInitializationBeforeDeclaration", "isTailCall", "recordUsedAsExpression", "AlgebraicTypeKind", "Companion", "Factory", "ReturnedExpressionsInfo", "VariableContext", "VariableInitContext", "VariableUseContext", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowInformationProviderImpl implements ControlFlowInformationProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final PlatformDiagnosticSuppressor diagnosticSuppressor;
    private final EnumWhenTracker enumWhenTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final Pseudocode pseudocode;

    /* JADX INFO: renamed from: pseudocodeVariablesData$delegate, reason: from kotlin metadata */
    private final Lazy pseudocodeVariablesData;
    private final KtElement subroutine;
    private final BindingTrace trace;

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TailRecursionKind.values().length];
            try {
                iArr2[TailRecursionKind.TAIL_CALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[TailRecursionKind.IN_TRY.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[TailRecursionKind.NON_TAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private ControlFlowInformationProviderImpl(KtElement ktElement, BindingTrace bindingTrace, Pseudocode pseudocode, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor platformDiagnosticSuppressor, EnumWhenTracker enumWhenTracker) {
        this.subroutine = ktElement;
        this.trace = bindingTrace;
        this.pseudocode = pseudocode;
        this.languageVersionSettings = languageVersionSettings;
        this.diagnosticSuppressor = platformDiagnosticSuppressor;
        this.enumWhenTracker = enumWhenTracker;
        this.pseudocodeVariablesData = LazyKt.lazy(new Function0<PseudocodeVariablesData>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$pseudocodeVariablesData$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PseudocodeVariablesData invoke() {
                Pseudocode pseudocode2 = this.this$0.pseudocode;
                BindingContext context = this.this$0.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                return new PseudocodeVariablesData(pseudocode2, context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PseudocodeVariablesData getPseudocodeVariablesData() {
        return (PseudocodeVariablesData) this.pseudocodeVariablesData.getValue();
    }

    public /* synthetic */ ControlFlowInformationProviderImpl(KtElement ktElement, BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor platformDiagnosticSuppressor, EnumWhenTracker enumWhenTracker, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktElement, bindingTrace, languageVersionSettings, platformDiagnosticSuppressor, (i & 16) != 0 ? null : enumWhenTracker);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ControlFlowInformationProviderImpl(KtElement declaration, BindingTrace trace, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor diagnosticSuppressor, EnumWhenTracker enumWhenTracker) {
        this(declaration, trace, new ControlFlowProcessor(trace, languageVersionSettings).generatePseudocode(declaration), languageVersionSettings, diagnosticSuppressor, enumWhenTracker);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(diagnosticSuppressor, "diagnosticSuppressor");
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowInformationProvider
    public void checkForLocalClassOrObjectMode() {
        recordInitializedVariables();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowInformationProvider
    public void checkDeclaration() {
        recordInitializedVariables();
        checkLocalFunctions();
        markUninitializedVariables();
        if (this.trace.wantsDiagnostics()) {
            markUnusedVariables();
        }
        checkForSuspendLambdaAndMarkParameters(this.pseudocode);
        markStatements();
        markAnnotationArguments();
        markUnusedExpressions();
        if (this.trace.wantsDiagnostics()) {
            checkIfExpressions();
        }
        checkWhenExpressions();
        checkConstructorConsistency();
    }

    @Override // org.jetbrains.kotlin.cfg.ControlFlowInformationProvider
    public void checkFunction(KotlinType expectedReturnType) {
        UnreachableCode unreachableCodeCollectUnreachableCode = collectUnreachableCode();
        reportUnreachableCode(unreachableCodeCollectUnreachableCode);
        if (this.subroutine instanceof KtFunctionLiteral) {
            return;
        }
        if (expectedReturnType == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            expectedReturnType = NO_EXPECTED_TYPE;
        }
        checkDefiniteReturn(expectedReturnType, unreachableCodeCollectUnreachableCode);
        markAndCheckTailCalls();
    }

    private final ReturnedExpressionsInfo collectReturnExpressions() {
        final HashSet hashSet = CollectionsKt.toHashSet(this.pseudocode.getInstructions());
        SubroutineExitInstruction exitInstruction = this.pseudocode.getExitInstruction();
        final ArrayList arrayList = new ArrayList();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Iterator<Instruction> it = exitInstruction.getPreviousInstructions().iterator();
        while (it.hasNext()) {
            it.next().accept(new InstructionVisitor() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.collectReturnExpressions.1
                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitReturnValue(ReturnValueInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    if (hashSet.contains(instruction)) {
                        arrayList.add(instruction.getElement());
                    }
                    if (instruction.getOwner().getIsInlined()) {
                        booleanRef.element = true;
                    }
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitReturnNoValue(ReturnNoValueInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    if (hashSet.contains(instruction)) {
                        arrayList.add(instruction.getElement());
                    }
                    if (instruction.getOwner().getIsInlined()) {
                        booleanRef.element = true;
                    }
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitUnconditionalJump(UnconditionalJumpInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    redirectToPrevInstructions(instruction);
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitConditionalJump(ConditionalJumpInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    redirectToPrevInstructions(instruction);
                }

                private final void redirectToPrevInstructions(Instruction instruction) {
                    Iterator<Instruction> it2 = instruction.getPreviousInstructions().iterator();
                    while (it2.hasNext()) {
                        it2.next().accept(this);
                    }
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitNondeterministicJump(NondeterministicJumpInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    redirectToPrevInstructions(instruction);
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitMarkInstruction(MarkInstruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    redirectToPrevInstructions(instruction);
                }

                @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor
                public void visitInstruction(Instruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    if (instruction instanceof KtElementInstruction) {
                        arrayList.add(((KtElementInstruction) instruction).getElement());
                        return;
                    }
                    throw new IllegalStateException(instruction + " precedes the exit point");
                }
            });
        }
        return new ReturnedExpressionsInfo(arrayList, booleanRef.element);
    }

    private final void checkLocalFunctions() {
        for (LocalFunctionDeclarationInstruction localFunctionDeclarationInstruction : this.pseudocode.getLocalDeclarations()) {
            KtElement element = localFunctionDeclarationInstruction.getElement();
            if (element instanceof KtDeclarationWithBody) {
                Object obj = this.trace.getContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, element);
                CallableDescriptor callableDescriptor = obj instanceof CallableDescriptor ? (CallableDescriptor) obj : null;
                new ControlFlowInformationProviderImpl(element, this.trace, localFunctionDeclarationInstruction.getBody(), this.languageVersionSettings, this.diagnosticSuppressor, this.enumWhenTracker).checkFunction(callableDescriptor != null ? callableDescriptor.getReturnType() : null);
            }
        }
    }

    private final void checkDefiniteReturn(final KotlinType expectedReturnType, final UnreachableCode unreachableCode) {
        KtElement ktElement = this.subroutine;
        KtDeclarationWithBody ktDeclarationWithBody = ktElement instanceof KtDeclarationWithBody ? (KtDeclarationWithBody) ktElement : null;
        if (ktDeclarationWithBody == null) {
            throw new AssertionError("checkDefiniteReturn is called for " + this.subroutine.getText() + " which is not KtDeclarationWithBody");
        }
        if (ktDeclarationWithBody.hasBody()) {
            ReturnedExpressionsInfo returnedExpressionsInfoCollectReturnExpressions = collectReturnExpressions();
            Collection<KtElement> collectionComponent1 = returnedExpressionsInfoCollectReturnExpressions.component1();
            boolean hasReturnsInInlinedLambda = returnedExpressionsInfoCollectReturnExpressions.getHasReturnsInInlinedLambda();
            final boolean zHasBlockBody = ktDeclarationWithBody.hasBlockBody();
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            Iterator<KtElement> it = collectionComponent1.iterator();
            while (it.hasNext()) {
                it.next().accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.checkDefiniteReturn.1
                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitReturnExpression(KtReturnExpression expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        if (zHasBlockBody) {
                            return;
                        }
                        this.trace.report(Errors.RETURN_IN_FUNCTION_WITH_EXPRESSION_BODY.on(expression));
                    }

                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitKtElement(KtElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        if ((!(element instanceof KtExpression) && !(element instanceof KtWhenCondition)) || !zHasBlockBody || TypeUtils.noExpectedType(expectedReturnType) || KotlinBuiltIns.isUnit(expectedReturnType) || unreachableCode.getElements().contains(element)) {
                            return;
                        }
                        booleanRef.element = true;
                    }
                });
            }
            if (booleanRef.element) {
                if (hasReturnsInInlinedLambda) {
                    this.trace.report(Errors.NO_RETURN_IN_FUNCTION_WITH_BLOCK_BODY_MIGRATION.on(ktDeclarationWithBody));
                } else {
                    this.trace.report(Errors.NO_RETURN_IN_FUNCTION_WITH_BLOCK_BODY.on(ktDeclarationWithBody));
                }
            }
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$ReturnedExpressionsInfo;", "", "returnedExpressions", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "hasReturnsInInlinedLambda", "", "(Ljava/util/Collection;Z)V", "getHasReturnsInInlinedLambda", "()Z", "getReturnedExpressions", "()Ljava/util/Collection;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ReturnedExpressionsInfo {
        private final boolean hasReturnsInInlinedLambda;
        private final Collection<KtElement> returnedExpressions;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ReturnedExpressionsInfo copy$default(ReturnedExpressionsInfo returnedExpressionsInfo, Collection collection, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                collection = returnedExpressionsInfo.returnedExpressions;
            }
            if ((i & 2) != 0) {
                z = returnedExpressionsInfo.hasReturnsInInlinedLambda;
            }
            return returnedExpressionsInfo.copy(collection, z);
        }

        public final Collection<KtElement> component1() {
            return this.returnedExpressions;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasReturnsInInlinedLambda() {
            return this.hasReturnsInInlinedLambda;
        }

        public final ReturnedExpressionsInfo copy(Collection<? extends KtElement> returnedExpressions, boolean hasReturnsInInlinedLambda) {
            Intrinsics.checkNotNullParameter(returnedExpressions, "returnedExpressions");
            return new ReturnedExpressionsInfo(returnedExpressions, hasReturnsInInlinedLambda);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ReturnedExpressionsInfo)) {
                return false;
            }
            ReturnedExpressionsInfo returnedExpressionsInfo = (ReturnedExpressionsInfo) other;
            return Intrinsics.areEqual(this.returnedExpressions, returnedExpressionsInfo.returnedExpressions) && this.hasReturnsInInlinedLambda == returnedExpressionsInfo.hasReturnsInInlinedLambda;
        }

        public int hashCode() {
            return (this.returnedExpressions.hashCode() * 31) + Boolean.hashCode(this.hasReturnsInInlinedLambda);
        }

        public String toString() {
            return "ReturnedExpressionsInfo(returnedExpressions=" + this.returnedExpressions + ", hasReturnsInInlinedLambda=" + this.hasReturnsInInlinedLambda + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ReturnedExpressionsInfo(Collection<? extends KtElement> returnedExpressions, boolean z) {
            Intrinsics.checkNotNullParameter(returnedExpressions, "returnedExpressions");
            this.returnedExpressions = returnedExpressions;
            this.hasReturnsInInlinedLambda = z;
        }

        public final boolean getHasReturnsInInlinedLambda() {
            return this.hasReturnsInInlinedLambda;
        }

        public final Collection<KtElement> getReturnedExpressions() {
            return this.returnedExpressions;
        }
    }

    private final void reportUnreachableCode(UnreachableCode unreachableCode) {
        Iterator<KtElement> it = unreachableCode.getElements().iterator();
        while (it.hasNext()) {
            this.trace.report(Errors.UNREACHABLE_CODE.on(it.next(), unreachableCode.getReachableElements(), unreachableCode.getUnreachableElements()));
        }
    }

    private final UnreachableCode collectUnreachableCode() {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (Instruction instruction : this.pseudocode.getInstructionsIncludingDeadCode()) {
            if ((instruction instanceof KtElementInstruction) && !(instruction instanceof LoadUnitValueInstruction) && !(instruction instanceof MergeInstruction) && (!(instruction instanceof MagicInstruction) || !((MagicInstruction) instruction).getSynthetic())) {
                KtElement element = ((KtElementInstruction) instruction).getElement();
                if (!(instruction instanceof JumpInstruction) || (element instanceof KtBreakExpression) || (element instanceof KtContinueExpression) || (element instanceof KtReturnExpression) || (element instanceof KtThrowExpression)) {
                    if (instruction.getDead()) {
                        hashSet2.add(element);
                    } else {
                        hashSet.add(element);
                    }
                }
            }
        }
        return new UnreachableCodeImpl(hashSet, hashSet2);
    }

    private final void markUninitializedVariables() {
        final HashSet hashSet = new HashSet();
        final HashSet hashSet2 = new HashSet();
        KtElement ktElement = this.subroutine;
        final boolean z = (ktElement instanceof KtClassOrObject) || (ktElement instanceof KtSecondaryConstructor);
        Map<Instruction, Edges<VariableInitReadOnlyControlFlowInfo>> variableInitializers = getPseudocodeVariablesData().getVariableInitializers();
        final Set<VariableDescriptor> declaredVariables = getPseudocodeVariablesData().getDeclaredVariables(this.pseudocode, true);
        final BlockScopeVariableInfo blockScopeVariableInfo = getPseudocodeVariablesData().getBlockScopeVariableInfo();
        final HashMap map = new HashMap();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, variableInitializers, new Function3<Instruction, VariableInitReadOnlyControlFlowInfo, VariableInitReadOnlyControlFlowInfo, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.markUninitializedVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo2) {
                invoke2(instruction, variableInitReadOnlyControlFlowInfo, variableInitReadOnlyControlFlowInfo2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction, VariableInitReadOnlyControlFlowInfo enterData, VariableInitReadOnlyControlFlowInfo exitData) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                Intrinsics.checkNotNullParameter(enterData, "enterData");
                Intrinsics.checkNotNullParameter(exitData, "exitData");
                VariableInitContext variableInitContext = new VariableInitContext(ControlFlowInformationProviderImpl.this, instruction, map, enterData, exitData, blockScopeVariableInfo);
                if (variableInitContext.getVariableDescriptor() == null) {
                    return;
                }
                if (instruction instanceof ReadValueInstruction) {
                    KtElement element = ((ReadValueInstruction) instruction).getElement();
                    if (PseudocodeUtil.isThisOrNoDispatchReceiver((AccessValueInstruction) instruction, ControlFlowInformationProviderImpl.this.trace.getContext()) && declaredVariables.contains(variableInitContext.getVariableDescriptor())) {
                        ControlFlowInformationProviderImpl.this.checkIsInitialized(variableInitContext, element, hashSet);
                        return;
                    }
                    return;
                }
                if (instruction instanceof WriteValueInstruction) {
                    WriteValueInstruction writeValueInstruction = (WriteValueInstruction) instruction;
                    KtElement lValue = writeValueInstruction.getLValue();
                    KtExpression ktExpression = lValue instanceof KtExpression ? (KtExpression) lValue : null;
                    if (ktExpression == null) {
                        return;
                    }
                    boolean zCheckValReassignment = ControlFlowInformationProviderImpl.this.checkValReassignment(variableInitContext, ktExpression, writeValueInstruction, hashSet2);
                    if (!zCheckValReassignment && z) {
                        zCheckValReassignment = ControlFlowInformationProviderImpl.this.checkAssignmentBeforeDeclaration(variableInitContext, ktExpression);
                    }
                    if (zCheckValReassignment || !z) {
                        return;
                    }
                    ControlFlowInformationProviderImpl.this.checkInitializationForCustomSetter(variableInitContext, ktExpression);
                }
            }
        });
    }

    private final void recordInitializedVariables() {
        Pseudocode pseudocode = getPseudocodeVariablesData().getPseudocode();
        Map<Instruction, Edges<VariableInitReadOnlyControlFlowInfo>> variableInitializers = getPseudocodeVariablesData().getVariableInitializers();
        recordInitializedVariables(pseudocode, variableInitializers);
        Iterator<LocalFunctionDeclarationInstruction> it = pseudocode.getLocalDeclarations().iterator();
        while (it.hasNext()) {
            recordInitializedVariables(it.next().getBody(), variableInitializers);
        }
    }

    private final boolean isDefinitelyInitialized(PropertyDescriptor propertyDescriptor) {
        if (Intrinsics.areEqual(this.trace.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor), (Object) true)) {
            return false;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor);
        return ((psiElementDescriptorToDeclaration instanceof KtProperty) && ((KtProperty) psiElementDescriptorToDeclaration).hasDelegate()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkIsInitialized(VariableInitContext ctxt, KtElement element, Collection<VariableDescriptor> varWithUninitializedErrorGenerated) {
        if (element instanceof KtSimpleNameExpression) {
            VariableControlFlowState exitInitState = ctxt.getExitInitState();
            boolean zDefinitelyInitialized = exitInitState != null ? exitInitState.definitelyInitialized() : false;
            VariableDescriptor variableDescriptor$cfg = ctxt.getVariableDescriptor();
            if (!zDefinitelyInitialized && (variableDescriptor$cfg instanceof PropertyDescriptor)) {
                zDefinitelyInitialized = isDefinitelyInitialized((PropertyDescriptor) variableDescriptor$cfg);
            }
            if (zDefinitelyInitialized || CollectionsKt.contains(varWithUninitializedErrorGenerated, variableDescriptor$cfg)) {
                return;
            }
            if (variableDescriptor$cfg instanceof PropertyDescriptor) {
                if (((PropertyDescriptor) variableDescriptor$cfg).isLateInit()) {
                    this.trace.record(BindingContext.MUST_BE_LATEINIT, variableDescriptor$cfg);
                    return;
                }
            } else if (variableDescriptor$cfg != null) {
                varWithUninitializedErrorGenerated.add(variableDescriptor$cfg);
            }
            if (variableDescriptor$cfg instanceof ValueParameterDescriptor) {
                Diagnostic diagnosticOn = Errors.UNINITIALIZED_PARAMETER.on(element, (ValueParameterDescriptor) variableDescriptor$cfg);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                report(diagnosticOn, ctxt);
                return;
            }
            if (variableDescriptor$cfg instanceof FakeCallableDescriptorForObject) {
                ClassDescriptor classDescriptor = ((FakeCallableDescriptorForObject) variableDescriptor$cfg).getClassDescriptor();
                int i = WhenMappings.$EnumSwitchMapping$0[classDescriptor.getKind().ordinal()];
                if (i == 1) {
                    Diagnostic diagnosticOn2 = Errors.UNINITIALIZED_ENUM_ENTRY.on(element, classDescriptor);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                    report(diagnosticOn2, ctxt);
                    return;
                } else {
                    if (i == 2 && classDescriptor.getIsCompanionObject()) {
                        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
                        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                        if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getKind() == ClassKind.ENUM_CLASS && !this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAccessToEnumCompanionMembersInEnumConstructorCall)) {
                            Diagnostic diagnosticOn3 = Errors.UNINITIALIZED_ENUM_COMPANION.on(element, (ClassDescriptor) containingDeclaration);
                            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                            report(diagnosticOn3, ctxt);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            if (variableDescriptor$cfg == null || variableDescriptor$cfg.isLateInit()) {
                return;
            }
            if ((variableDescriptor$cfg instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) variableDescriptor$cfg)) {
                return;
            }
            Diagnostic diagnosticOn4 = Errors.UNINITIALIZED_VARIABLE.on(element, variableDescriptor$cfg);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
            report(diagnosticOn4, ctxt);
        }
    }

    private final boolean isCapturedWrite(VariableDescriptor variableDescriptor, WriteValueInstruction writeValueInstruction) {
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            return false;
        }
        KtDeclaration elementParentDeclaration = CfgContainingDeclarationUtilsKt.getElementParentDeclaration(writeValueInstruction.getElement());
        while (true) {
            BindingContext context = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            if (Intrinsics.areEqual(CfgContainingDeclarationUtilsKt.getDeclarationDescriptorIncludingConstructors(elementParentDeclaration, context), containingDeclaration)) {
                return false;
            }
            if (elementParentDeclaration instanceof KtObjectDeclaration ? true : elementParentDeclaration instanceof KtClassInitializer) {
                elementParentDeclaration = CfgContainingDeclarationUtilsKt.getElementParentDeclaration(elementParentDeclaration);
            } else {
                if (!(elementParentDeclaration instanceof KtDeclarationWithBody)) {
                    return true;
                }
                PsiElement parent = ((KtDeclarationWithBody) elementParentDeclaration).getMyParent();
                if ((parent instanceof KtLambdaExpression) && this.trace.get(BindingContext.LAMBDA_INVOCATIONS, parent) != null) {
                    elementParentDeclaration = CfgContainingDeclarationUtilsKt.getElementParentDeclaration(elementParentDeclaration);
                } else {
                    if ((elementParentDeclaration instanceof KtFunction) && ((KtFunction) elementParentDeclaration).isLocal()) {
                        return true;
                    }
                    return !Intrinsics.areEqual(CfgContainingDeclarationUtilsKt.getDeclarationDescriptorIncludingConstructors(CfgContainingDeclarationUtilsKt.getElementParentDeclaration(elementParentDeclaration), context), containingDeclaration);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:16:0x003b  */
    /* JADX WARN: Code duplicated, block: B:62:0x0117  */
    public final boolean checkValReassignment(VariableInitContext ctxt, KtExpression expression, WriteValueInstruction writeValueInstruction, Collection<VariableDescriptor> varWithValReassignErrorGenerated) {
        Boolean bool;
        KtOperationReferenceExpression operationReference;
        boolean z;
        KotlinType returnType;
        VariableDescriptor variableDescriptor$cfg = ctxt.getVariableDescriptor();
        VariableControlFlowState enterInitState = ctxt.getEnterInitState();
        boolean zMayBeInitialized = enterInitState != null ? enterInitState.mayBeInitialized() : false;
        boolean z2 = variableDescriptor$cfg instanceof PropertyDescriptor;
        PropertyDescriptor propertyDescriptor = z2 ? (PropertyDescriptor) variableDescriptor$cfg : null;
        if (propertyDescriptor != null) {
            bool = (Boolean) this.trace.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
            if (bool == null) {
                bool = false;
            }
            if (bool == null) {
                bool = true;
            }
        } else {
            bool = true;
        }
        boolean zBooleanValue = bool.booleanValue();
        if (z2) {
            PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) variableDescriptor$cfg;
            if (propertyDescriptor2.isVar()) {
                BindingContext context = this.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                KtExpression ktExpression = expression;
                DeclarationDescriptor enclosingDescriptor = BindingContextUtilsKt.getEnclosingDescriptor(context, ktExpression);
                PropertySetterDescriptor setter = propertyDescriptor2.getSetter();
                BindingContext context2 = this.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, context2);
                ReceiverValue dispatchReceiverWithSmartCast = resolvedCall != null ? ResolvedCallUtilKt.getDispatchReceiverWithSmartCast(resolvedCall) : null;
                if (DescriptorVisibilityUtils.isVisible(dispatchReceiverWithSmartCast, variableDescriptor$cfg, enclosingDescriptor, this.languageVersionSettings) && setter != null) {
                    if (!DescriptorVisibilityUtils.isVisible(dispatchReceiverWithSmartCast, setter, enclosingDescriptor, this.languageVersionSettings)) {
                        Diagnostic diagnosticOn = Errors.INVISIBLE_SETTER.on(expression, variableDescriptor$cfg, setter.getVisibility(), setter);
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        report(diagnosticOn, ctxt);
                        return true;
                    }
                    reportVisibilityWarningForInternalFakeSetterOverride(setter, expression, propertyDescriptor2, ctxt);
                }
            }
        }
        boolean zIsThisOrNoDispatchReceiver = PseudocodeUtil.isThisOrNoDispatchReceiver(writeValueInstruction, this.trace.getContext());
        boolean zIsCapturedWrite = variableDescriptor$cfg != null ? isCapturedWrite(variableDescriptor$cfg, writeValueInstruction) : false;
        if ((!zMayBeInitialized && zBooleanValue && zIsThisOrNoDispatchReceiver && !zIsCapturedWrite) || variableDescriptor$cfg == null || variableDescriptor$cfg.isVar()) {
            return false;
        }
        PsiElement parent = expression.getMyParent();
        if (parent instanceof KtBinaryExpression) {
            operationReference = ((KtBinaryExpression) parent).getOperationReference();
        } else {
            operationReference = parent instanceof KtUnaryExpression ? ((KtUnaryExpression) parent).getOperationReference() : null;
        }
        if (operationReference != null) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.get(BindingContext.REFERENCE_TARGET, operationReference);
            if (declarationDescriptor instanceof FunctionDescriptor) {
                KotlinType returnType2 = ((FunctionDescriptor) declarationDescriptor).getReturnType();
                if (returnType2 != null && KotlinBuiltIns.isUnit(returnType2)) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            if (declarationDescriptor == null) {
                Collection<DeclarationDescriptor> collectionEmptyList = (Collection) this.trace.get(BindingContext.AMBIGUOUS_REFERENCE_TARGET, operationReference);
                if (collectionEmptyList == null) {
                    collectionEmptyList = CollectionsKt.emptyList();
                }
                for (DeclarationDescriptor declarationDescriptor2 : collectionEmptyList) {
                    FunctionDescriptor functionDescriptor = declarationDescriptor2 instanceof FunctionDescriptor ? (FunctionDescriptor) declarationDescriptor2 : null;
                    if ((functionDescriptor == null || (returnType = functionDescriptor.getReturnType()) == null || !KotlinBuiltIns.isUnit(returnType)) ? false : true) {
                        z = true;
                    }
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        if (!zIsThisOrNoDispatchReceiver || !varWithValReassignErrorGenerated.contains(variableDescriptor$cfg)) {
            if (zIsCapturedWrite && !zMayBeInitialized && zBooleanValue && zIsThisOrNoDispatchReceiver) {
                if (variableDescriptor$cfg.getContainingDeclaration() instanceof ClassDescriptor) {
                    Diagnostic diagnosticOn2 = Errors.CAPTURED_MEMBER_VAL_INITIALIZATION.on(expression, variableDescriptor$cfg);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                    report(diagnosticOn2, ctxt);
                } else {
                    Diagnostic diagnosticOn3 = Errors.CAPTURED_VAL_INITIALIZATION.on(expression, variableDescriptor$cfg);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                    report(diagnosticOn3, ctxt);
                }
            } else if (DescriptorPsiUtilsKt.isBackingFieldReference(variableDescriptor$cfg)) {
                reportValReassigned(expression, variableDescriptor$cfg, ctxt);
            } else {
                Diagnostic diagnosticOn4 = Errors.VAL_REASSIGNMENT.on(expression, variableDescriptor$cfg);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
                report(diagnosticOn4, ctxt);
            }
        }
        if (zIsThisOrNoDispatchReceiver) {
            varWithValReassignErrorGenerated.add(variableDescriptor$cfg);
        }
        return true;
    }

    private final void reportVisibilityWarningForInternalFakeSetterOverride(PropertySetterDescriptor setterDescriptor, KtExpression expression, PropertyDescriptor variableDescriptor, VariableInitContext ctxt) {
        CallableMemberDescriptor callableMemberDescriptorFirstOverridden$default;
        DiagnosticFactory3<PsiElement, DeclarationDescriptor, DescriptorVisibility, DeclarationDescriptor> diagnosticFactory3;
        if (setterDescriptor.getKind().isReal() || setterDescriptor.getVisibility().isPublicAPI()) {
            return;
        }
        DeclarationDescriptor containingDeclaration = setterDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || (callableMemberDescriptorFirstOverridden$default = DescriptorUtilsKt.firstOverridden$default(setterDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$reportVisibilityWarningForInternalFakeSetterOverride$firstRealOverridden$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getKind().isReal());
            }
        }, 1, null)) == null) {
            return;
        }
        Collection<CallableMemberDescriptor> collectionFilterVisibleFakeOverrides = OverridingUtil.filterVisibleFakeOverrides(classDescriptor, CollectionsKt.listOf(callableMemberDescriptorFirstOverridden$default));
        Intrinsics.checkNotNullExpressionValue(collectionFilterVisibleFakeOverrides, "filterVisibleFakeOverrides(...)");
        if (collectionFilterVisibleFakeOverrides.isEmpty()) {
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAccessToInvisibleSetterFromDerivedClass)) {
                diagnosticFactory3 = Errors.INVISIBLE_SETTER;
            } else {
                diagnosticFactory3 = Errors.INVISIBLE_SETTER_FROM_DERIVED;
            }
            Diagnostic diagnosticOn = diagnosticFactory3.on(expression, variableDescriptor, setterDescriptor.getVisibility(), setterDescriptor);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            report(diagnosticOn, ctxt);
        }
    }

    private final void reportValReassigned(KtExpression expression, VariableDescriptor variableDescriptor, VariableInitContext ctxt) {
        report(Errors.VAL_REASSIGNMENT_VIA_BACKING_FIELD.on(this.languageVersionSettings, expression, variableDescriptor), ctxt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean checkAssignmentBeforeDeclaration(VariableInitContext ctxt, KtExpression expression) {
        if (!isInitializationBeforeDeclaration(ctxt)) {
            return false;
        }
        if (ctxt.getVariableDescriptor() != null) {
            Diagnostic diagnosticOn = Errors.INITIALIZATION_BEFORE_DECLARATION.on(expression, ctxt.getVariableDescriptor());
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            report(diagnosticOn, ctxt);
        }
        return true;
    }

    private final boolean isInitializationBeforeDeclaration(VariableInitContext variableInitContext) {
        VariableControlFlowState exitInitState;
        VariableControlFlowState enterInitState;
        VariableControlFlowState enterInitState2 = variableInitContext.getEnterInitState();
        return (enterInitState2 == null || !enterInitState2.getIsDeclared()) && ((exitInitState = variableInitContext.getExitInitState()) == null || !exitInitState.getIsDeclared()) && ((enterInitState = variableInitContext.getEnterInitState()) == null || !enterInitState.mayBeInitialized());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean checkInitializationForCustomSetter(VariableInitContext ctxt, KtExpression expression) {
        VariableControlFlowState enterInitState;
        VariableControlFlowState exitInitState;
        VariableDescriptor variableDescriptor$cfg = ctxt.getVariableDescriptor();
        if (!(variableDescriptor$cfg instanceof PropertyDescriptor) || (((enterInitState = ctxt.getEnterInitState()) != null && enterInitState.mayBeInitialized()) || (exitInitState = ctxt.getExitInitState()) == null || !exitInitState.mayBeInitialized() || !Intrinsics.areEqual(this.trace.get(BindingContext.BACKING_FIELD_REQUIRED, variableDescriptor$cfg), (Object) true))) {
            return false;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(variableDescriptor$cfg);
        KtProperty ktProperty = psiElementDescriptorToDeclaration instanceof KtProperty ? (KtProperty) psiElementDescriptorToDeclaration : null;
        if (ktProperty == null) {
            throw new AssertionError(variableDescriptor$cfg + " is not related to KtProperty");
        }
        KtPropertyAccessor setter = ktProperty.getSetter();
        if (DeclarationsCheckerKt.getEffectiveModality((PropertyDescriptor) variableDescriptor$cfg, this.languageVersionSettings) == Modality.FINAL && (setter == null || !setter.hasBody())) {
            return false;
        }
        if (expression instanceof KtDotQualifiedExpression) {
            KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) expression;
            if (ktDotQualifiedExpression.getReceiverExpression() instanceof KtThisExpression) {
                expression = ktDotQualifiedExpression.getSelectorExpression();
            }
        }
        if (!(expression instanceof KtSimpleNameExpression)) {
            return false;
        }
        this.trace.record(BindingContext.IS_UNINITIALIZED, variableDescriptor$cfg);
        return true;
    }

    private final void recordInitializedVariables(Pseudocode pseudocode, Map<Instruction, ? extends Edges<? extends VariableInitReadOnlyControlFlowInfo>> initializersMap) {
        VariableControlFlowState orNull;
        Edges<? extends VariableInitReadOnlyControlFlowInfo> edges = initializersMap.get(pseudocode.getExitInstruction());
        if (edges == null) {
            return;
        }
        for (VariableDescriptor variableDescriptor : getPseudocodeVariablesData().getDeclaredVariables(pseudocode, false)) {
            if ((variableDescriptor instanceof PropertyDescriptor) && ((orNull = edges.getIncoming().getOrNull(variableDescriptor)) == null || !orNull.definitelyInitialized())) {
                this.trace.record(BindingContext.IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR, variableDescriptor);
                this.trace.record(BindingContext.IS_UNINITIALIZED, variableDescriptor);
            }
        }
    }

    private final void markUnusedVariables() {
        KtExpression right;
        IElementType referencedNameElementType;
        Map<Instruction, Edges<ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState>>> variableUseStatusData = getPseudocodeVariablesData().getVariableUseStatusData();
        final HashMap map = new HashMap();
        final HashMap map2 = new HashMap();
        final HashSet hashSet = new HashSet();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.BACKWARD, variableUseStatusData, new Function3<Instruction, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState>, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState>, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.markUnusedVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo2) {
                invoke2(instruction, readOnlyControlFlowInfo, readOnlyControlFlowInfo2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> enterData, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                Intrinsics.checkNotNullParameter(enterData, "enterData");
                Intrinsics.checkNotNullParameter(readOnlyControlFlowInfo, "<anonymous parameter 2>");
                VariableUseContext variableUseContext = new VariableUseContext(ControlFlowInformationProviderImpl.this, instruction, map);
                Set<VariableDescriptor> declaredVariables = ControlFlowInformationProviderImpl.this.getPseudocodeVariablesData().getDeclaredVariables(instruction.getOwner(), false);
                VariableDescriptor variableDescriptorExtractVariableDescriptorIfAny = PseudocodeUtil.extractVariableDescriptorIfAny(instruction, ControlFlowInformationProviderImpl.this.trace.getContext());
                if (variableDescriptorExtractVariableDescriptorIfAny != null && declaredVariables.contains(variableDescriptorExtractVariableDescriptorIfAny) && ExpressionTypingUtils.isLocal(variableDescriptorExtractVariableDescriptorIfAny.getContainingDeclaration(), variableDescriptorExtractVariableDescriptorIfAny)) {
                    VariableUseState orNull = enterData.getOrNull(variableDescriptorExtractVariableDescriptorIfAny);
                    if (instruction instanceof WriteValueInstruction) {
                        if (ControlFlowInformationProviderImpl.this.trace.get(BindingContext.CAPTURED_IN_CLOSURE, variableDescriptorExtractVariableDescriptorIfAny) != null) {
                            return;
                        }
                        KtElement element = ((WriteValueInstruction) instruction).getElement();
                        KtExpression ktExpression = element instanceof KtExpression ? (KtExpression) element : null;
                        if (ktExpression == null) {
                            return;
                        }
                        if (orNull != VariableUseState.READ) {
                            map2.put(ktExpression, TuplesKt.to(variableDescriptorExtractVariableDescriptorIfAny, variableUseContext));
                            return;
                        } else {
                            hashSet.add(ktExpression);
                            return;
                        }
                    }
                    if (instruction instanceof VariableDeclarationInstruction) {
                        KtDeclaration variableDeclarationElement = ((VariableDeclarationInstruction) instruction).getVariableDeclarationElement();
                        KtNamedDeclaration ktNamedDeclaration = variableDeclarationElement instanceof KtNamedDeclaration ? (KtNamedDeclaration) variableDeclarationElement : null;
                        if (ktNamedDeclaration == null) {
                            return;
                        }
                        ControlFlowInformationProviderImpl.this.processUnusedDeclaration(ktNamedDeclaration, variableDescriptorExtractVariableDescriptorIfAny, variableUseContext, orNull);
                    }
                }
            }
        });
        map2.keySet().removeAll(hashSet);
        for (Map.Entry entry : map2.entrySet()) {
            KtExpression ktExpression = (KtExpression) entry.getKey();
            Pair pair = (Pair) entry.getValue();
            VariableDescriptor variableDescriptor = (VariableDescriptor) pair.component1();
            VariableUseContext variableUseContext = (VariableUseContext) pair.component2();
            if (ktExpression instanceof KtBinaryExpression) {
                KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) ktExpression;
                if (ktBinaryExpression.getOperationToken() == KtTokens.EQ && (right = ktBinaryExpression.getRight()) != null && !isLocalVariableWithDelegate(variableDescriptor)) {
                    Diagnostic diagnosticOn = Errors.UNUSED_VALUE.on(ktExpression, right, variableDescriptor);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    report(diagnosticOn, variableUseContext);
                }
            } else if ((ktExpression instanceof KtPostfixExpression) && ((referencedNameElementType = ((KtPostfixExpression) ktExpression).getOperationReference().getReferencedNameElementType()) == KtTokens.PLUSPLUS || referencedNameElementType == KtTokens.MINUSMINUS)) {
                Diagnostic diagnosticOn2 = Errors.UNUSED_CHANGED_VALUE.on(ktExpression, ktExpression);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                report(diagnosticOn2, variableUseContext);
            }
        }
    }

    private final boolean isLocalVariableWithDelegate(VariableDescriptor variableDescriptor) {
        return (variableDescriptor instanceof LocalVariableDescriptor) && ((LocalVariableDescriptor) variableDescriptor).getIsDelegated();
    }

    private final boolean isLocalVariableWithProvideDelegate(VariableDescriptor variableDescriptor) {
        return isLocalVariableWithDelegate(variableDescriptor) && (variableDescriptor instanceof VariableDescriptorWithAccessors) && this.trace.getContext().get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, variableDescriptor) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processUnusedDeclaration(KtNamedDeclaration element, VariableDescriptor variableDescriptor, VariableUseContext ctxt, VariableUseState variableUseState) {
        if (element.mo7392getNameIdentifier() == null) {
            return;
        }
        if (!VariableUseState.INSTANCE.isUsed(variableUseState)) {
            if (UnderscoreUtilKt.isSingleUnderscore(element)) {
                return;
            }
            if (element instanceof KtDestructuringDeclarationEntry) {
                PsiElement myParent = ((KtDestructuringDeclarationEntry) element).getMyParent().getMyParent();
                if ((myParent != null ? myParent.getMyParent() : null) instanceof KtParameterList) {
                    Diagnostic diagnosticOn = Errors.UNUSED_DESTRUCTURED_PARAMETER_ENTRY.on(element, variableDescriptor);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    report(diagnosticOn, ctxt);
                    return;
                }
            }
            if (KtPsiUtil.isRemovableVariableDeclaration(element)) {
                if (isLocalVariableWithProvideDelegate(variableDescriptor)) {
                    return;
                }
                Diagnostic diagnosticOn2 = Errors.UNUSED_VARIABLE.on(element, variableDescriptor);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                report(diagnosticOn2, ctxt);
                return;
            }
            if (element instanceof KtParameter) {
                processUnusedParameter(ctxt, (KtParameter) element, variableDescriptor);
                return;
            }
            return;
        }
        if (variableUseState == VariableUseState.ONLY_WRITTEN_NEVER_READ && KtPsiUtil.isRemovableVariableDeclaration(element)) {
            if (isLocalVariableWithDelegate(variableDescriptor)) {
                return;
            }
            Diagnostic diagnosticOn3 = Errors.ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE.on(element, variableDescriptor);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
            report(diagnosticOn3, ctxt);
            return;
        }
        if (variableUseState == VariableUseState.WRITTEN_AFTER_READ && (element instanceof KtVariableDeclaration)) {
            if (element instanceof KtProperty) {
                KtExpression initializer = ((KtProperty) element).getInitializer();
                if (initializer != null) {
                    Diagnostic diagnosticOn4 = Errors.VARIABLE_WITH_REDUNDANT_INITIALIZER.on(initializer, variableDescriptor);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
                    report(diagnosticOn4, ctxt);
                    return;
                }
                return;
            }
            if (element instanceof KtDestructuringDeclarationEntry) {
                Diagnostic diagnosticOn5 = Errors.VARIABLE_WITH_REDUNDANT_INITIALIZER.on(element, variableDescriptor);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn5, "on(...)");
                report(diagnosticOn5, ctxt);
            }
        }
    }

    private final void processUnusedParameter(VariableUseContext ctxt, KtParameter element, VariableDescriptor variableDescriptor) {
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) containingDeclaration;
        if (functionDescriptor.get$isExpect() || functionDescriptor.isActual() || DescriptorUtilsKt.isEffectivelyExternal(functionDescriptor)) {
            return;
        }
        PlatformDiagnosticSuppressor platformDiagnosticSuppressor = this.diagnosticSuppressor;
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        if (platformDiagnosticSuppressor.shouldReportUnusedParameter(variableDescriptor, context) && this.diagnosticSuppressor.shouldReportUnusedParameter(variableDescriptor)) {
            PsiElement myParent = element.getMyParent().getMyParent();
            if (myParent instanceof KtPrimaryConstructor) {
                if (element.hasValOrVar()) {
                    return;
                }
                ClassifierDescriptorWithTypeParameters containingDeclaration2 = ((ConstructorDescriptor) functionDescriptor).getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
                if (DescriptorUtils.isAnnotationClass(containingDeclaration2)) {
                    return;
                }
                Diagnostic diagnosticOn = Errors.UNUSED_PARAMETER.on(element, variableDescriptor);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                report(diagnosticOn, ctxt);
                return;
            }
            if (myParent instanceof KtFunction) {
                boolean z = false;
                boolean z2 = (myParent instanceof KtFunctionLiteral) || ((myParent instanceof KtNamedFunction) && ((KtNamedFunction) myParent).getMyName() == null);
                if (!z2 || this.languageVersionSettings.supportsFeature(LanguageFeature.SingleUnderscoreForParameterName)) {
                    BindingContext context2 = this.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    MainFunctionDetector mainFunctionDetector = new MainFunctionDetector(context2, this.languageVersionSettings);
                    if ((myParent instanceof KtNamedFunction) && MainFunctionDetector.isMain$default(mainFunctionDetector, (KtNamedFunction) myParent, false, false, 6, null)) {
                        z = true;
                    }
                    Name name2 = functionDescriptor.getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (z) {
                        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.ExtendedMainConvention)) {
                            return;
                        }
                        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.WarningOnMainUnusedParameter)) {
                            if (KtPsiUtilKt.getContainingClassOrObject((KtDeclaration) myParent) == null) {
                                this.trace.record(BindingContext.UNUSED_MAIN_PARAMETER, element);
                                return;
                            }
                            return;
                        } else if (KtPsiUtilKt.getContainingClassOrObject((KtDeclaration) myParent) != null) {
                            return;
                        }
                    }
                    if (functionDescriptor.isOperator() && OperatorNameConventions.DELEGATED_PROPERTY_OPERATORS.contains(name2)) {
                        this.trace.record(BindingContext.UNUSED_DELEGATED_PROPERTY_OPERATOR_PARAMETER, variableDescriptor, true);
                        return;
                    }
                    if (ModalityUtilsKt.isOverridableOrOverrides(functionDescriptor) || ((KtFunction) myParent).hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
                        return;
                    }
                    if (z2) {
                        Diagnostic diagnosticOn2 = Errors.UNUSED_ANONYMOUS_PARAMETER.on(element, variableDescriptor);
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                        report(diagnosticOn2, ctxt);
                        return;
                    } else {
                        Diagnostic diagnosticOn3 = Errors.UNUSED_PARAMETER.on(element, variableDescriptor);
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn3, "on(...)");
                        report(diagnosticOn3, ctxt);
                        return;
                    }
                }
                return;
            }
            if (myParent instanceof KtPropertyAccessor) {
                Diagnostic diagnosticOn4 = Errors.UNUSED_PARAMETER.on(element, variableDescriptor);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn4, "on(...)");
                report(diagnosticOn4, ctxt);
            }
        }
    }

    private final void markUnusedExpressions() {
        final HashMap map = new HashMap();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.markUnusedExpressions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                Diagnostic diagnosticOn;
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                if (instruction instanceof KtElementInstruction) {
                    KtElement element = ((KtElementInstruction) instruction).getElement();
                    KtExpression ktExpression = element instanceof KtExpression ? (KtExpression) element : null;
                    if (ktExpression == null) {
                        return;
                    }
                    BindingContext context = ControlFlowInformationProviderImpl.this.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                    if (BindingContextUtilsKt.isUsedAsStatement(ktExpression, context) && PseudocodeUtilsKt.getSideEffectFree(instruction)) {
                        VariableContext variableContext = new VariableContext(ControlFlowInformationProviderImpl.this, instruction, map);
                        ControlFlowInformationProviderImpl controlFlowInformationProviderImpl = ControlFlowInformationProviderImpl.this;
                        if (ktExpression instanceof KtLambdaExpression) {
                            diagnosticOn = Errors.UNUSED_LAMBDA_EXPRESSION.on(ktExpression);
                            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        } else {
                            diagnosticOn = Errors.UNUSED_EXPRESSION.on(ktExpression);
                            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        }
                        controlFlowInformationProviderImpl.report(diagnosticOn, variableContext);
                    }
                }
            }
        });
    }

    private final void markStatements() {
        PseudocodeTraverserKt.traverseIncludingDeadCode(this.pseudocode, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.markStatements.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                InstructionWithValue instructionWithValue = instruction instanceof InstructionWithValue ? (InstructionWithValue) instruction : null;
                PseudoValue resultValue = instructionWithValue != null ? instructionWithValue.getResultValue() : null;
                Pseudocode owner = instruction.getOwner();
                List<Instruction> usages = owner.getUsages(resultValue);
                boolean zIsEmpty = usages.isEmpty();
                boolean z = !zIsEmpty;
                boolean zIsUsedAsResultOfLambda = ControlFlowInformationProviderImpl.INSTANCE.isUsedAsResultOfLambda(usages);
                for (KtElement ktElement : owner.getValueElements(resultValue)) {
                    BindingContextUtilsKt.recordUsedAsExpression(ktElement, ControlFlowInformationProviderImpl.this.trace, z);
                    ControlFlowInformationProviderImpl.this.trace.record(BindingContext.USED_AS_RESULT_OF_LAMBDA, ktElement, Boolean.valueOf(zIsUsedAsResultOfLambda));
                    if (!zIsEmpty) {
                        if (ktElement instanceof KtTryExpression) {
                            ControlFlowInformationProviderImpl controlFlowInformationProviderImpl = ControlFlowInformationProviderImpl.this;
                            KtTryExpression ktTryExpression = (KtTryExpression) ktElement;
                            KtBlockExpression tryBlock = ktTryExpression.getTryBlock();
                            Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
                            controlFlowInformationProviderImpl.recordUsedAsExpression(tryBlock);
                            Iterator<KtCatchClause> it = ktTryExpression.getCatchClauses().iterator();
                            while (it.hasNext()) {
                                KtExpression catchBody = it.next().getCatchBody();
                                if (catchBody != null) {
                                    ControlFlowInformationProviderImpl.this.recordUsedAsExpression(catchBody);
                                }
                            }
                        } else if (ktElement instanceof KtIfExpression) {
                            KtIfExpression ktIfExpression = (KtIfExpression) ktElement;
                            KtExpression then = ktIfExpression.getThen();
                            KtBlockExpression ktBlockExpression = then instanceof KtBlockExpression ? (KtBlockExpression) then : null;
                            if (ktBlockExpression != null) {
                                ControlFlowInformationProviderImpl.this.recordUsedAsExpression(ktBlockExpression);
                            }
                            KtExpression ktExpression = ktIfExpression.getElse();
                            KtBlockExpression ktBlockExpression2 = ktExpression instanceof KtBlockExpression ? (KtBlockExpression) ktExpression : null;
                            if (ktBlockExpression2 != null) {
                                ControlFlowInformationProviderImpl.this.recordUsedAsExpression(ktBlockExpression2);
                            }
                        } else if (ktElement instanceof KtWhenExpression) {
                            Iterator<KtWhenEntry> it2 = ((KtWhenExpression) ktElement).getEntries().iterator();
                            while (it2.hasNext()) {
                                KtExpression expression = it2.next().getExpression();
                                KtBlockExpression ktBlockExpression3 = expression instanceof KtBlockExpression ? (KtBlockExpression) expression : null;
                                if (ktBlockExpression3 != null) {
                                    ControlFlowInformationProviderImpl.this.recordUsedAsExpression(ktBlockExpression3);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordUsedAsExpression(KtExpression ktExpression) {
        BindingContextUtilsKt.recordUsedAsExpression(ktExpression, this.trace, true);
    }

    private final void checkForSuspendLambdaAndMarkParameters(Pseudocode pseudocode) {
        for (Instruction instruction : pseudocode.getInstructionsIncludingDeadCode()) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                LocalFunctionDeclarationInstruction localFunctionDeclarationInstruction = (LocalFunctionDeclarationInstruction) instruction;
                KtElement correspondingElement = localFunctionDeclarationInstruction.getBody().getCorrespondingElement();
                if (correspondingElement instanceof KtFunctionLiteral) {
                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.getContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, correspondingElement);
                    if ((declarationDescriptor instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) declarationDescriptor).isSuspend()) {
                        markReadOfSuspendLambdaParameters(localFunctionDeclarationInstruction.getBody());
                    }
                }
                checkForSuspendLambdaAndMarkParameters(localFunctionDeclarationInstruction.getBody());
            }
        }
    }

    private final void markReadOfSuspendLambdaParameters(Pseudocode pseudocode) {
        for (Instruction instruction : pseudocode.getInstructionsIncludingDeadCode()) {
            if (instruction instanceof LocalFunctionDeclarationInstruction) {
                markReadOfSuspendLambdaParameters(((LocalFunctionDeclarationInstruction) instruction).getBody());
            } else {
                markReadOfSuspendLambdaParameter(instruction);
                markImplicitReceiverOfSuspendLambda(instruction);
            }
        }
    }

    private final void markReadOfSuspendLambdaParameter(Instruction instruction) {
        ValueParameterDescriptor valueParameterDescriptorFindDestructuredVariable;
        if (instruction instanceof ReadValueInstruction) {
            AccessTarget target = ((ReadValueInstruction) instruction).getTarget();
            AccessTarget.Call call = target instanceof AccessTarget.Call ? (AccessTarget.Call) target : null;
            if (call == null) {
                return;
            }
            CallableDescriptor resultingDescriptor = call.getResolvedCall().getResultingDescriptor();
            if (resultingDescriptor instanceof ParameterDescriptor) {
                ParameterDescriptor parameterDescriptor = (ParameterDescriptor) resultingDescriptor;
                DeclarationDescriptor containingDeclaration = parameterDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if ((containingDeclaration instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) containingDeclaration).isSuspend()) {
                    this.trace.record(BindingContext.SUSPEND_LAMBDA_PARAMETER_USED, TuplesKt.to(containingDeclaration, Integer.valueOf(KtDescriptorUtilKt.indexOrMinusOne(parameterDescriptor))));
                    return;
                }
                return;
            }
            if (resultingDescriptor instanceof LocalVariableDescriptor) {
                DeclarationDescriptor containingDeclaration2 = ((LocalVariableDescriptor) resultingDescriptor).getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
                if ((containingDeclaration2 instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) containingDeclaration2).isSuspend() && (valueParameterDescriptorFindDestructuredVariable = CapturingInClosureCheckerKt.findDestructuredVariable((VariableDescriptor) resultingDescriptor, containingDeclaration2)) != null) {
                    this.trace.record(BindingContext.SUSPEND_LAMBDA_PARAMETER_USED, TuplesKt.to(containingDeclaration2, Integer.valueOf(valueParameterDescriptorFindDestructuredVariable.getIndex())));
                }
            }
        }
    }

    private final void markImplicitReceiverOfSuspendLambda(Instruction instruction) {
        ExtensionReceiver extensionReceiver;
        CallableDescriptor declarationDescriptor;
        CallableDescriptor declarationDescriptor2;
        CallableDescriptor declarationDescriptor3;
        CallableDescriptor declarationDescriptor4;
        CallableDescriptor declarationDescriptor5;
        if (instruction instanceof MagicInstruction) {
            MagicInstruction magicInstruction = (MagicInstruction) instruction;
            if (magicInstruction.getKind() == MagicKind.IMPLICIT_RECEIVER || magicInstruction.getKind() == MagicKind.UNBOUND_CALLABLE_REFERENCE) {
                KtElement element = magicInstruction.getElement();
                if (element instanceof KtDestructuringDeclarationEntry ? true : element instanceof KtCallExpression) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    Instruction next = magicInstruction.getNext();
                    if (next != null) {
                        markImplicitReceiverOfSuspendLambda$dfs(linkedHashSet, element, this, next);
                        return;
                    }
                    return;
                }
                if (element instanceof KtNameReferenceExpression ? true : element instanceof KtBinaryExpression ? true : element instanceof KtUnaryExpression) {
                    BindingContext context = this.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                    ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(element, context);
                    if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                        VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = (VariableAsFunctionResolvedCall) resolvedCall;
                        ReceiverValue dispatchReceiver = variableAsFunctionResolvedCall.getVariableCall().getDispatchReceiver();
                        ExtensionReceiver extensionReceiver2 = dispatchReceiver instanceof ExtensionReceiver ? (ExtensionReceiver) dispatchReceiver : null;
                        if (extensionReceiver2 != null && (declarationDescriptor5 = extensionReceiver2.getDeclarationDescriptor()) != null) {
                            markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor5, this);
                        }
                        ReceiverValue extensionReceiver3 = variableAsFunctionResolvedCall.getVariableCall().getExtensionReceiver();
                        ExtensionReceiver extensionReceiver4 = extensionReceiver3 instanceof ExtensionReceiver ? (ExtensionReceiver) extensionReceiver3 : null;
                        if (extensionReceiver4 != null && (declarationDescriptor4 = extensionReceiver4.getDeclarationDescriptor()) != null) {
                            markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor4, this);
                        }
                    }
                    ReceiverValue dispatchReceiver2 = resolvedCall != null ? resolvedCall.getDispatchReceiver() : null;
                    ExtensionReceiver extensionReceiver5 = dispatchReceiver2 instanceof ExtensionReceiver ? (ExtensionReceiver) dispatchReceiver2 : null;
                    if (extensionReceiver5 != null && (declarationDescriptor3 = extensionReceiver5.getDeclarationDescriptor()) != null) {
                        markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor3, this);
                    }
                    ReceiverValue extensionReceiver6 = resolvedCall != null ? resolvedCall.getExtensionReceiver() : null;
                    extensionReceiver = extensionReceiver6 instanceof ExtensionReceiver ? (ExtensionReceiver) extensionReceiver6 : null;
                    if (extensionReceiver == null || (declarationDescriptor2 = extensionReceiver.getDeclarationDescriptor()) == null) {
                        return;
                    }
                    markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor2, this);
                    return;
                }
                if (element instanceof KtCallableReferenceExpression) {
                    KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) element).getCallableReference();
                    BindingContext context2 = this.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    ResolvedCall<? extends CallableDescriptor> resolvedCall2 = CallUtilKt.getResolvedCall(callableReference, context2);
                    ReceiverValue dispatchReceiver3 = resolvedCall2 != null ? resolvedCall2.getDispatchReceiver() : null;
                    extensionReceiver = dispatchReceiver3 instanceof ExtensionReceiver ? (ExtensionReceiver) dispatchReceiver3 : null;
                    if (extensionReceiver == null || (declarationDescriptor = extensionReceiver.getDeclarationDescriptor()) == null) {
                        return;
                    }
                    markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor, this);
                }
            }
        }
    }

    private static final void markImplicitReceiverOfSuspendLambda$markIfNeeded(CallableDescriptor callableDescriptor, ControlFlowInformationProviderImpl controlFlowInformationProviderImpl) {
        if ((callableDescriptor instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) callableDescriptor).isSuspend()) {
            controlFlowInformationProviderImpl.trace.record(BindingContext.SUSPEND_LAMBDA_PARAMETER_USED, TuplesKt.to(callableDescriptor, -1));
        }
    }

    private static final void markImplicitReceiverOfSuspendLambda$dfs(Set<Instruction> set, KtElement ktElement, ControlFlowInformationProviderImpl controlFlowInformationProviderImpl, Instruction instruction) {
        CallableDescriptor declarationDescriptor;
        if (set.add(instruction)) {
            if (instruction instanceof CallInstruction) {
                CallInstruction callInstruction = (CallInstruction) instruction;
                if (Intrinsics.areEqual(callInstruction.getElement(), ktElement)) {
                    Iterator<Map.Entry<PseudoValue, ReceiverValue>> it = callInstruction.getReceiverValues().entrySet().iterator();
                    while (it.hasNext()) {
                        ReceiverValue value = it.next().getValue();
                        ExtensionReceiver extensionReceiver = value instanceof ExtensionReceiver ? (ExtensionReceiver) value : null;
                        if (extensionReceiver != null && (declarationDescriptor = extensionReceiver.getDeclarationDescriptor()) != null) {
                            markImplicitReceiverOfSuspendLambda$markIfNeeded(declarationDescriptor, controlFlowInformationProviderImpl);
                        }
                    }
                }
            }
            Iterator<Instruction> it2 = instruction.getNextInstructions().iterator();
            while (it2.hasNext()) {
                markImplicitReceiverOfSuspendLambda$dfs(set, ktElement, controlFlowInformationProviderImpl, it2.next());
            }
        }
    }

    private final void markAnnotationArguments() {
        if (this.subroutine.getContainingKtFile().getIsCompiled()) {
            return;
        }
        KtElement ktElement = this.subroutine;
        if (ktElement instanceof KtAnnotationEntry) {
            markAnnotationArguments((KtAnnotationEntry) ktElement);
            return;
        }
        PsiElement[] children = ktElement.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        for (PsiElement psiElement : children) {
            Intrinsics.checkNotNull(psiElement);
            final Function1<KtAnnotationEntry, Unit> function1 = new Function1<KtAnnotationEntry, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$markAnnotationArguments$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(KtAnnotationEntry ktAnnotationEntry) {
                    invoke2(ktAnnotationEntry);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KtAnnotationEntry it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.markAnnotationArguments(it);
                }
            };
            PsiUtilsKt.checkDecompiledText(psiElement);
            psiElement.accept(new PsiRecursiveElementVisitor() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$markAnnotationArguments$lambda$16$$inlined$forEachDescendantOfType$1
                @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                public void visitElement(PsiElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    if (!(element instanceof KtDeclaration) || (element instanceof KtParameter)) {
                        super.visitElement(element);
                    }
                    if (element instanceof KtAnnotationEntry) {
                        function1.invoke(element);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void markAnnotationArguments(KtAnnotationEntry entry) {
        Iterator<? extends ValueArgument> it = entry.getValueArguments().iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null) {
                KtExpression ktExpression = argumentExpression;
                final Function1<KtExpression, Unit> function1 = new Function1<KtExpression, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.markAnnotationArguments.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(KtExpression ktExpression2) {
                        invoke2(ktExpression2);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(KtExpression it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        BindingContextUtilsKt.recordUsedAsExpression(it2, ControlFlowInformationProviderImpl.this.trace, true);
                    }
                };
                PsiUtilsKt.checkDecompiledText(ktExpression);
                ktExpression.accept(new PsiRecursiveElementVisitor() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$markAnnotationArguments$$inlined$forEachDescendantOfType$1
                    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                    public void visitElement(PsiElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        super.visitElement(element);
                        if (element instanceof KtExpression) {
                            function1.invoke(element);
                        }
                    }
                });
            }
        }
    }

    private final void checkIfExpressions() {
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.checkIfExpressions.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                InstructionWithValue instructionWithValue = instruction instanceof InstructionWithValue ? (InstructionWithValue) instruction : null;
                for (KtElement ktElement : instruction.getOwner().getValueElements(instructionWithValue != null ? instructionWithValue.getResultValue() : null)) {
                    if (ktElement instanceof KtIfExpression) {
                        KtIfExpression ktIfExpression = (KtIfExpression) ktElement;
                        boolean z = (ktIfExpression.getThen() == null || ktIfExpression.getElse() == null) ? false : true;
                        BindingContext context = ControlFlowInformationProviderImpl.this.trace.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                        if (BindingContextUtilsKt.isUsedAsExpression(ktElement, context)) {
                            if (!z) {
                                ControlFlowInformationProviderImpl.this.trace.report(Errors.INVALID_IF_AS_EXPRESSION.on(ktIfExpression.getIfKeyword()));
                            } else {
                                ControlFlowInformationProviderImpl.this.checkImplicitCastOnConditionalExpression((KtExpression) ktElement);
                            }
                        } else if (!ControlFlowInformationProviderImpl.this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitNonExhaustiveIfInRhsOfElvis) && !z) {
                            PsiElement deparenthesizedParent = ControlFlowInformationProviderImpl.this.getDeparenthesizedParent(ktElement);
                            if ((deparenthesizedParent instanceof KtBinaryExpression) && ((KtBinaryExpression) deparenthesizedParent).getOperationToken() == KtTokens.ELVIS) {
                                ControlFlowInformationProviderImpl.this.trace.report(Errors.INVALID_IF_AS_EXPRESSION_WARNING.on(ktIfExpression.getIfKeyword()));
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkImplicitCastOnConditionalExpression(KtExpression expression) {
        KotlinType type;
        List<KtExpression> listCollectResultingExpressionsOfConditionalExpression = INSTANCE.collectResultingExpressionsOfConditionalExpression(expression);
        KotlinType kotlinType = (KotlinType) this.trace.get(BindingContext.EXPECTED_EXPRESSION_TYPE, expression);
        if ((kotlinType == null || kotlinType == TypeUtils.DONT_CARE) && (type = this.trace.getType(expression)) != null && KotlinBuiltIns.isAnyOrNullableAny(type)) {
            BindingContext context = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            boolean zIsUsedAsResultOfLambda = BindingContextUtilsKt.isUsedAsResultOfLambda(expression, context);
            Iterator it = listCollectResultingExpressionsOfConditionalExpression.iterator();
            while (it.hasNext()) {
                KotlinType type2 = this.trace.getType((KtExpression) it.next());
                if (type2 == null || KotlinBuiltIns.isAnyOrNullableAny(type2)) {
                    return;
                }
                if (zIsUsedAsResultOfLambda && KotlinBuiltIns.isUnitOrNullableUnit(type2)) {
                    return;
                }
            }
            for (KtExpression ktExpression : listCollectResultingExpressionsOfConditionalExpression) {
                KotlinType type3 = this.trace.getType(ktExpression);
                if (type3 != null && !KotlinBuiltIns.isNothing(type3)) {
                    this.trace.report(Errors.IMPLICIT_CAST_TO_ANY.on(INSTANCE.getResultingExpression(ktExpression), type3, type));
                }
            }
        }
    }

    private final void checkWhenExpressions() {
        final Map<Instruction, Edges<VariableInitReadOnlyControlFlowInfo>> variableInitializers = getPseudocodeVariablesData().getVariableInitializers();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.checkWhenExpressions.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                Object next;
                Object next2;
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                if (instruction instanceof MagicInstruction) {
                    MagicInstruction magicInstruction = (MagicInstruction) instruction;
                    if (magicInstruction.getKind() == MagicKind.EXHAUSTIVE_WHEN_ELSE) {
                        Instruction next3 = magicInstruction.getNext();
                        if (next3 instanceof MergeInstruction) {
                            Edges<VariableInitReadOnlyControlFlowInfo> edges = variableInitializers.get(next3);
                            VariableInitReadOnlyControlFlowInfo incoming = edges != null ? edges.getIncoming() : null;
                            Edges<VariableInitReadOnlyControlFlowInfo> edges2 = variableInitializers.get(instruction);
                            VariableInitReadOnlyControlFlowInfo outgoing = edges2 != null ? edges2.getOutgoing() : null;
                            if (incoming != null && outgoing != null) {
                                MergeInstruction mergeInstruction = (MergeInstruction) next3;
                                if ((mergeInstruction.getElement() instanceof KtWhenExpression) && outgoing.checkDefiniteInitializationInWhen(incoming)) {
                                    this.trace.record(BindingContext.IMPLICIT_EXHAUSTIVE_WHEN, mergeInstruction.getElement());
                                }
                            }
                        }
                    }
                }
                InstructionWithValue instructionWithValue = instruction instanceof InstructionWithValue ? (InstructionWithValue) instruction : null;
                for (KtElement ktElement : instruction.getOwner().getValueElements(instructionWithValue != null ? instructionWithValue.getResultValue() : null)) {
                    if (ktElement instanceof KtWhenExpression) {
                        BindingContext context = this.trace.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                        boolean zIsUsedAsExpression = BindingContextUtilsKt.isUsedAsExpression(ktElement, context);
                        if (zIsUsedAsExpression) {
                            this.checkImplicitCastOnConditionalExpression((KtExpression) ktElement);
                        }
                        BindingContext context2 = this.trace.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                        KtWhenExpression ktWhenExpression = (KtWhenExpression) ktElement;
                        List<WhenMissingCase> missingCases = WhenChecker.INSTANCE.getMissingCases(ktWhenExpression, context2);
                        List<KtWhenEntry> entries = ktWhenExpression.getEntries();
                        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
                        Iterator<T> it = entries.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (!((KtWhenEntry) next).isElse());
                        KtWhenEntry ktWhenEntry = (KtWhenEntry) next;
                        KtExpression subjectExpression = ktWhenExpression.getSubjectExpression();
                        if (!zIsUsedAsExpression || missingCases.isEmpty()) {
                            if (subjectExpression != null) {
                                BindingContext context3 = this.trace.getContext();
                                Intrinsics.checkNotNullExpressionValue(context3, "getBindingContext(...)");
                                KotlinType kotlinTypeWhenSubjectType = WhenChecker.whenSubjectType(ktWhenExpression, context3);
                                if (ktWhenEntry == null) {
                                    EnumWhenTracker enumWhenTracker = this.enumWhenTracker;
                                    if (enumWhenTracker != null) {
                                        IncrementalTrackerUtilKt.record(enumWhenTracker, kotlinTypeWhenSubjectType, subjectExpression, ktWhenEntry);
                                    }
                                    if (!zIsUsedAsExpression) {
                                        if (this.languageVersionSettings.supportsFeature(LanguageFeature.WarnAboutNonExhaustiveWhenOnAlgebraicTypes)) {
                                            this.checkExhaustiveWhenStatement(kotlinTypeWhenSubjectType, ktWhenExpression, missingCases);
                                        } else {
                                            this.checkWhenStatement(kotlinTypeWhenSubjectType, ktWhenExpression, context2);
                                        }
                                    }
                                } else if (missingCases.isEmpty() && kotlinTypeWhenSubjectType != null && !FlexibleTypesKt.isFlexible(kotlinTypeWhenSubjectType)) {
                                    ClassifierDescriptor this$0 = kotlinTypeWhenSubjectType.getConstructor().getThis$0();
                                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, instruction.getOwner().getCorrespondingElement());
                                    if (classDescriptor != null && !KotlinBuiltIns.isBooleanOrNullableBoolean(kotlinTypeWhenSubjectType)) {
                                        if (Intrinsics.areEqual(DescriptorUtilsKt.getModule(classDescriptor), declarationDescriptor != null ? DescriptorUtilsKt.getModule(declarationDescriptor) : null)) {
                                        }
                                    }
                                    this.trace.report(Errors.REDUNDANT_ELSE_IN_WHEN.on(ktWhenEntry));
                                }
                            }
                            if (zIsUsedAsExpression && !missingCases.isEmpty() && ktWhenEntry == null && !this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitNonExhaustiveIfInRhsOfElvis)) {
                                KtElement ktElement2 = ktElement;
                                PsiElement deparenthesizedParent = this.getDeparenthesizedParent(ktElement2);
                                if ((deparenthesizedParent instanceof KtBinaryExpression) && ((KtBinaryExpression) deparenthesizedParent).getOperationToken() == KtTokens.ELVIS) {
                                    this.trace.report(Errors.NO_ELSE_IN_WHEN_WARNING.on(ktElement2, missingCases));
                                }
                            }
                        } else if (ktWhenEntry == null) {
                            KtElement ktElement3 = ktElement;
                            this.trace.report(Errors.NO_ELSE_IN_WHEN.on(ktElement3, missingCases));
                            Iterator<T> it2 = missingCases.iterator();
                            do {
                                if (!it2.hasNext()) {
                                    next2 = null;
                                    break;
                                }
                                next2 = it2.next();
                            } while (!(((WhenMissingCase) next2) instanceof WhenMissingCase.ConditionTypeIsExpect));
                            WhenMissingCase whenMissingCase = (WhenMissingCase) next2;
                            if (whenMissingCase != null) {
                                ControlFlowInformationProviderImpl controlFlowInformationProviderImpl = this;
                                if (whenMissingCase instanceof WhenMissingCase.ConditionTypeIsExpect) {
                                    controlFlowInformationProviderImpl.trace.report(Errors.EXPECT_TYPE_IN_WHEN_WITHOUT_ELSE.on(ktElement3, ((WhenMissingCase.ConditionTypeIsExpect) whenMissingCase).getTypeOfDeclaration()));
                                } else {
                                    throw new IllegalArgumentException("Failed requirement.".toString());
                                }
                            }
                            if (zIsUsedAsExpression) {
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkWhenStatement(KotlinType subjectType, KtWhenExpression element, BindingContext context) {
        ClassDescriptor classDescriptorOfTypeIfEnum = WhenChecker.getClassDescriptorOfTypeIfEnum(subjectType);
        if (classDescriptorOfTypeIfEnum != null) {
            List<WhenMissingCase> enumMissingCases = WhenChecker.getEnumMissingCases(element, context, classDescriptorOfTypeIfEnum);
            if (!enumMissingCases.isEmpty()) {
                this.trace.report(Errors.NON_EXHAUSTIVE_WHEN.on(element, enumMissingCases));
            }
        }
        ClassDescriptor classDescriptorOfTypeIfSealed = WhenChecker.getClassDescriptorOfTypeIfSealed(subjectType);
        if (classDescriptorOfTypeIfSealed != null) {
            List<WhenMissingCase> sealedMissingCases = WhenChecker.getSealedMissingCases(element, context, classDescriptorOfTypeIfSealed);
            if (sealedMissingCases.isEmpty()) {
                return;
            }
            this.trace.report(Errors.NON_EXHAUSTIVE_WHEN_ON_SEALED_CLASS.on(element, sealedMissingCases));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkExhaustiveWhenStatement(KotlinType subjectType, KtWhenExpression element, List<? extends WhenMissingCase> missingCases) {
        AlgebraicTypeKind algebraicTypeKind;
        if (missingCases.isEmpty()) {
            return;
        }
        if (WhenChecker.getClassDescriptorOfTypeIfSealed(subjectType) != null) {
            algebraicTypeKind = AlgebraicTypeKind.Sealed;
        } else if (WhenChecker.getClassDescriptorOfTypeIfEnum(subjectType) != null) {
            algebraicTypeKind = AlgebraicTypeKind.Enum;
        } else {
            algebraicTypeKind = (subjectType == null || !TypeUtilsKt.isBooleanOrNullableBoolean(subjectType)) ? null : AlgebraicTypeKind.Boolean;
        }
        if (algebraicTypeKind != null) {
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitNonExhaustiveWhenOnAlgebraicTypes)) {
                this.trace.report(Errors.NO_ELSE_IN_WHEN.on(element, missingCases));
            } else {
                this.trace.report(Errors.NON_EXHAUSTIVE_WHEN_STATEMENT.on(element, algebraicTypeKind.getDisplayName(), missingCases));
            }
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$AlgebraicTypeKind;", "", "displayName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "Sealed", "Enum", "Boolean", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum AlgebraicTypeKind {
        Sealed("sealed class/interface"),
        Enum(PsiKeyword.ENUM),
        Boolean("Boolean");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String displayName;

        public static EnumEntries<AlgebraicTypeKind> getEntries() {
            return $ENTRIES;
        }

        AlgebraicTypeKind(String str) {
            this.displayName = str;
        }

        public final String getDisplayName() {
            return this.displayName;
        }
    }

    private final void checkConstructorConsistency() {
        KtElement ktElement = this.subroutine;
        if (ktElement instanceof KtClassOrObject) {
            ConstructorConsistencyChecker.INSTANCE.check((KtClassOrObject) this.subroutine, this.trace, this.pseudocode, getPseudocodeVariablesData());
        } else if (ktElement instanceof KtSecondaryConstructor) {
            ConstructorConsistencyChecker.INSTANCE.check((KtSecondaryConstructor) this.subroutine, this.trace, this.pseudocode, getPseudocodeVariablesData());
        }
    }

    private final void markAndCheckTailCalls() {
        Object obj = this.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, this.subroutine);
        FunctionDescriptor functionDescriptor = obj instanceof FunctionDescriptor ? (FunctionDescriptor) obj : null;
        if (functionDescriptor == null) {
            return;
        }
        markAndCheckRecursiveTailCalls(functionDescriptor);
    }

    private final void markAndCheckRecursiveTailCalls(final FunctionDescriptor subroutineDescriptor) {
        if (subroutineDescriptor.isTailrec()) {
            KtElement ktElement = this.subroutine;
            if (!(ktElement instanceof KtNamedFunction) || ((KtNamedFunction) ktElement).hasBody()) {
                final HashMap map = new HashMap();
                PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl$markAndCheckRecursiveTailCalls$$inlined$traverseCalls$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                        invoke2(instruction);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Instruction instruction) {
                        Intrinsics.checkNotNullParameter(instruction, "instruction");
                        if (instruction instanceof CallInstruction) {
                            CallInstruction callInstruction = (CallInstruction) instruction;
                            ResolvedCall<?> resolvedCall = callInstruction.getResolvedCall();
                            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
                            if (Intrinsics.areEqual(resultingDescriptor.getOriginal(), subroutineDescriptor)) {
                                if (resolvedCall.getCall().getValueArguments().size() == resultingDescriptor.getValueParameters().size() || resultingDescriptor.getOverriddenDescriptors().isEmpty()) {
                                    KtElement element = callInstruction.getElement();
                                    if (!this.isInsideTry(element)) {
                                        BindingContext context = this.trace.getContext();
                                        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                                        TailRecursionKind tailRecursionKind = (ResolvedCallUtilKt.hasThisOrNoDispatchReceiver(resolvedCall, context) && ControlFlowInformationProviderImpl.isTailCall$default(this, callInstruction, null, 1, null)) ? TailRecursionKind.TAIL_CALL : TailRecursionKind.NON_TAIL;
                                        ControlFlowInformationProviderImpl.KindAndCall kindAndCall = (ControlFlowInformationProviderImpl.KindAndCall) map.get(element);
                                        map.put(element, new ControlFlowInformationProviderImpl.KindAndCall(ControlFlowInformationProviderImpl.INSTANCE.combineKinds(tailRecursionKind, kindAndCall != null ? kindAndCall.getKind() : null), resolvedCall));
                                        return;
                                    }
                                    map.put(element, new ControlFlowInformationProviderImpl.KindAndCall(TailRecursionKind.IN_TRY, resolvedCall));
                                }
                            }
                        }
                    }
                });
                boolean z = false;
                for (Map.Entry entry : map.entrySet()) {
                    KtElement ktElement2 = (KtElement) entry.getKey();
                    KindAndCall kindAndCall = (KindAndCall) entry.getValue();
                    int i = WhenMappings.$EnumSwitchMapping$1[kindAndCall.getKind().ordinal()];
                    if (i == 1) {
                        this.trace.record(BindingContext.TAIL_RECURSION_CALL, kindAndCall.getCall().getCall(), TailRecursionKind.TAIL_CALL);
                        z = true;
                    } else if (i == 2) {
                        this.trace.report(Errors.TAIL_RECURSION_IN_TRY_IS_NOT_SUPPORTED.on(ktElement2));
                    } else if (i == 3) {
                        this.trace.report(Errors.NON_TAIL_RECURSIVE_CALL.on(ktElement2));
                    }
                }
                if (z || !(this.subroutine instanceof KtNamedFunction)) {
                    return;
                }
                this.trace.report(Errors.NO_TAIL_CALLS_FOUND.on(this.subroutine));
            }
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"org/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$markAndCheckRecursiveTailCalls$KindAndCall", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cfg/TailRecursionKind;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Lorg/jetbrains/kotlin/cfg/TailRecursionKind;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getKind", "()Lorg/jetbrains/kotlin/cfg/TailRecursionKind;", "setKind", "(Lorg/jetbrains/kotlin/cfg/TailRecursionKind;)V", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KindAndCall {
        private final ResolvedCall<?> call;
        private TailRecursionKind kind;

        public KindAndCall(TailRecursionKind kind, ResolvedCall<?> call) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(call, "call");
            this.kind = kind;
            this.call = call;
        }

        public final ResolvedCall<?> getCall() {
            return this.call;
        }

        public final TailRecursionKind getKind() {
            return this.kind;
        }

        public final void setKind(TailRecursionKind tailRecursionKind) {
            Intrinsics.checkNotNullParameter(tailRecursionKind, "<set-?>");
            this.kind = tailRecursionKind;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isInsideTry(KtElement element) {
        return PsiTreeUtil.getParentOfType(element, KtTryExpression.class, KtFunction.class, KtAnonymousInitializer.class) instanceof KtTryExpression;
    }

    static /* synthetic */ boolean isTailCall$default(ControlFlowInformationProviderImpl controlFlowInformationProviderImpl, CallInstruction callInstruction, KtElement ktElement, int i, Object obj) {
        if ((i & 1) != 0) {
            ktElement = controlFlowInformationProviderImpl.subroutine;
        }
        return controlFlowInformationProviderImpl.isTailCall(callInstruction, ktElement);
    }

    private final boolean isTailCall(final CallInstruction callInstruction, KtElement ktElement) {
        final TailInstructionDetector tailInstructionDetector = new TailInstructionDetector(ktElement);
        return PseudocodeTraverserKt.traverseFollowingInstructions(callInstruction, new HashSet(), TraversalOrder.FORWARD, new Function1<Instruction, TraverseInstructionResult>() { // from class: org.jetbrains.kotlin.cfg.ControlFlowInformationProviderImpl.isTailCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TraverseInstructionResult invoke(Instruction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!Intrinsics.areEqual(it, callInstruction)) {
                    Object objAccept = it.accept(tailInstructionDetector);
                    Intrinsics.checkNotNullExpressionValue(objAccept, "accept(...)");
                    if (!((Boolean) objAccept).booleanValue()) {
                        return TraverseInstructionResult.HALT;
                    }
                }
                return TraverseInstructionResult.CONTINUE;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void report(Diagnostic diagnostic, VariableContext ctxt) {
        Instruction instruction = ctxt.getInstruction();
        if (instruction.getCopies().isEmpty()) {
            this.trace.report(diagnostic);
            return;
        }
        Map<Instruction, DiagnosticFactory<?>> reportedDiagnosticMap$cfg = ctxt.getReportedDiagnosticMap$cfg();
        reportedDiagnosticMap$cfg.put(instruction, diagnostic.getFactory());
        Iterator<Instruction> it = instruction.getCopies().iterator();
        boolean z = true;
        boolean z2 = false;
        while (it.hasNext()) {
            DiagnosticFactory<?> diagnosticFactory = reportedDiagnosticMap$cfg.get(it.next());
            if (diagnosticFactory != null) {
                z2 = true;
            }
            if (diagnosticFactory != diagnostic.getFactory()) {
                z = false;
            }
        }
        if (INSTANCE.mustBeReportedOnAllCopies(diagnostic.getFactory())) {
            if (z) {
                this.trace.report(diagnostic);
            }
        } else {
            if (z2) {
                return;
            }
            this.trace.report(diagnostic);
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR$\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableContext;", "", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "reportedDiagnosticMap", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Ljava/util/Map;)V", "getInstruction$cfg", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getReportedDiagnosticMap$cfg", "()Ljava/util/Map;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getVariableDescriptor$cfg", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private class VariableContext {
        private final Instruction instruction;
        private final Map<Instruction, DiagnosticFactory<?>> reportedDiagnosticMap;
        final /* synthetic */ ControlFlowInformationProviderImpl this$0;
        private final VariableDescriptor variableDescriptor;

        public VariableContext(ControlFlowInformationProviderImpl controlFlowInformationProviderImpl, Instruction instruction, Map<Instruction, DiagnosticFactory<?>> reportedDiagnosticMap) {
            Intrinsics.checkNotNullParameter(instruction, "instruction");
            Intrinsics.checkNotNullParameter(reportedDiagnosticMap, "reportedDiagnosticMap");
            this.this$0 = controlFlowInformationProviderImpl;
            this.instruction = instruction;
            this.reportedDiagnosticMap = reportedDiagnosticMap;
            this.variableDescriptor = PseudocodeUtil.extractVariableDescriptorFromReference(instruction, controlFlowInformationProviderImpl.trace.getContext());
        }

        /* JADX INFO: renamed from: getInstruction$cfg, reason: from getter */
        public final Instruction getInstruction() {
            return this.instruction;
        }

        public final Map<Instruction, DiagnosticFactory<?>> getReportedDiagnosticMap$cfg() {
            return this.reportedDiagnosticMap;
        }

        /* JADX INFO: renamed from: getVariableDescriptor$cfg, reason: from getter */
        public final VariableDescriptor getVariableDescriptor() {
            return this.variableDescriptor;
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ$\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\tH\u0002R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u000fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableInitContext;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableContext;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "map", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "in", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "out", "blockScopeVariableInfo", "Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "(Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Ljava/util/Map;Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;)V", "enterInitState", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "getEnterInitState$cfg", "()Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "exitInitState", "getExitInitState$cfg", "initialize", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class VariableInitContext extends VariableContext {
        private final VariableControlFlowState enterInitState;
        private final VariableControlFlowState exitInitState;
        final /* synthetic */ ControlFlowInformationProviderImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public VariableInitContext(ControlFlowInformationProviderImpl controlFlowInformationProviderImpl, Instruction instruction, Map<Instruction, DiagnosticFactory<?>> map, VariableInitReadOnlyControlFlowInfo in, VariableInitReadOnlyControlFlowInfo out, BlockScopeVariableInfo blockScopeVariableInfo) {
            super(controlFlowInformationProviderImpl, instruction, map);
            Intrinsics.checkNotNullParameter(instruction, "instruction");
            Intrinsics.checkNotNullParameter(map, "map");
            Intrinsics.checkNotNullParameter(in, "in");
            Intrinsics.checkNotNullParameter(out, "out");
            Intrinsics.checkNotNullParameter(blockScopeVariableInfo, "blockScopeVariableInfo");
            this.this$0 = controlFlowInformationProviderImpl;
            this.enterInitState = initialize(getVariableDescriptor(), blockScopeVariableInfo, in);
            this.exitInitState = initialize(getVariableDescriptor(), blockScopeVariableInfo, out);
        }

        /* JADX INFO: renamed from: getEnterInitState$cfg, reason: from getter */
        public final VariableControlFlowState getEnterInitState() {
            return this.enterInitState;
        }

        /* JADX INFO: renamed from: getExitInitState$cfg, reason: from getter */
        public final VariableControlFlowState getExitInitState() {
            return this.exitInitState;
        }

        private final VariableControlFlowState initialize(VariableDescriptor variableDescriptor, BlockScopeVariableInfo blockScopeVariableInfo, VariableInitReadOnlyControlFlowInfo map) {
            if (variableDescriptor == null) {
                return null;
            }
            VariableControlFlowState orNull = map.getOrNull(variableDescriptor);
            return orNull != null ? orNull : PseudocodeVariablesData.INSTANCE.getDefaultValueForInitializers(variableDescriptor, getInstruction(), blockScopeVariableInfo);
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableUseContext;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$VariableContext;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "map", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;Ljava/util/Map;)V", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class VariableUseContext extends VariableContext {
        final /* synthetic */ ControlFlowInformationProviderImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public VariableUseContext(ControlFlowInformationProviderImpl controlFlowInformationProviderImpl, Instruction instruction, Map<Instruction, DiagnosticFactory<?>> map) {
            super(controlFlowInformationProviderImpl, instruction, map);
            Intrinsics.checkNotNullParameter(instruction, "instruction");
            Intrinsics.checkNotNullParameter(map, "map");
            this.this$0 = controlFlowInformationProviderImpl;
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$Factory;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider$Factory;", "()V", "createControlFlowInformationProvider", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider;", "declaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticSuppressor", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Factory implements ControlFlowInformationProvider.Factory {
        public static final Factory INSTANCE = new Factory();

        private Factory() {
        }

        @Override // org.jetbrains.kotlin.cfg.ControlFlowInformationProvider.Factory
        public ControlFlowInformationProvider createControlFlowInformationProvider(KtElement declaration, BindingTrace trace, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor diagnosticSuppressor, EnumWhenTracker enumWhenTracker) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(diagnosticSuppressor, "diagnosticSuppressor");
            Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
            return new ControlFlowInformationProviderImpl(declaration, trace, languageVersionSettings, diagnosticSuppressor, enumWhenTracker);
        }
    }

    /* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001H\u0002J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\u000bH\u0002J \u0010\r\u001a\u00020\u000e2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010H\u0002J\u001a\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u0016\u0010\u0016\u001a\u00020\u00042\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\nH\u0002J\u0014\u0010\u0019\u001a\u00020\u00042\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0002¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProviderImpl$Companion;", "", "()V", "check", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "x", "y", "collectResultingExpressionsOfConditionalExpression", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expression", "collectResultingExpressionsOfConditionalExpressionRec", "", "resultingExpressions", "", "combineKinds", "Lorg/jetbrains/kotlin/cfg/TailRecursionKind;", Namer.METADATA_CLASS_KIND, "existingKind", "getResultingExpression", "isUsedAsResultOfLambda", "usages", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "mustBeReportedOnAllCopies", "diagnosticFactory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean check(Object a, Object b, Object x, Object y) {
            return (a == x && b == y) || (a == y && b == x);
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isUsedAsResultOfLambda(List<? extends Instruction> usages) {
            for (Instruction instruction : usages) {
                if (instruction instanceof ReturnValueInstruction) {
                    KtElement element = ((ReturnValueInstruction) instruction).getElement();
                    PsiElement parent = element.getMyParent();
                    if (!(element instanceof KtReturnExpression) && (!(parent instanceof KtDeclaration) || (parent instanceof KtFunctionLiteral))) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<KtExpression> collectResultingExpressionsOfConditionalExpression(KtExpression expression) {
            ArrayList arrayList = new ArrayList();
            collectResultingExpressionsOfConditionalExpressionRec(expression, arrayList);
            return arrayList;
        }

        private final void collectResultingExpressionsOfConditionalExpressionRec(KtExpression expression, List<KtExpression> resultingExpressions) {
            if (expression instanceof KtIfExpression) {
                KtIfExpression ktIfExpression = (KtIfExpression) expression;
                collectResultingExpressionsOfConditionalExpressionRec(ktIfExpression.getThen(), resultingExpressions);
                collectResultingExpressionsOfConditionalExpressionRec(ktIfExpression.getElse(), resultingExpressions);
            } else if (expression instanceof KtWhenExpression) {
                Iterator<KtWhenEntry> it = ((KtWhenExpression) expression).getEntries().iterator();
                while (it.hasNext()) {
                    collectResultingExpressionsOfConditionalExpressionRec(it.next().getExpression(), resultingExpressions);
                }
            } else if (expression instanceof Object) {
                KtExpression resultingExpression = getResultingExpression(expression);
                if ((resultingExpression instanceof KtIfExpression) || (resultingExpression instanceof KtWhenExpression)) {
                    collectResultingExpressionsOfConditionalExpressionRec(resultingExpression, resultingExpressions);
                } else {
                    resultingExpressions.add(resultingExpression);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KtExpression getResultingExpression(KtExpression expression) {
            while (true) {
                KtExpression expressionOrLastStatementInBlock = KtPsiUtil.getExpressionOrLastStatementInBlock(KtPsiUtil.deparenthesize(expression));
                if (expressionOrLastStatementInBlock == null || expressionOrLastStatementInBlock == expression) {
                    break;
                }
                expression = expressionOrLastStatementInBlock;
            }
            return expression;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TailRecursionKind combineKinds(TailRecursionKind kind, TailRecursionKind existingKind) {
            if (existingKind == null || existingKind == kind) {
                return kind;
            }
            if (!check(kind, existingKind, TailRecursionKind.IN_TRY, TailRecursionKind.TAIL_CALL) && !check(kind, existingKind, TailRecursionKind.IN_TRY, TailRecursionKind.NON_TAIL)) {
                return TailRecursionKind.NON_TAIL;
            }
            return TailRecursionKind.IN_TRY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean mustBeReportedOnAllCopies(DiagnosticFactory<?> diagnosticFactory) {
            return diagnosticFactory == Errors.UNUSED_VARIABLE || diagnosticFactory == Errors.UNUSED_PARAMETER || diagnosticFactory == Errors.UNUSED_ANONYMOUS_PARAMETER || diagnosticFactory == Errors.UNUSED_CHANGED_VALUE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PsiElement getDeparenthesizedParent(PsiElement psiElement) {
        PsiElement myParent = psiElement.getMyParent();
        while (true) {
            if ((myParent instanceof KtParenthesizedExpression) || (myParent instanceof KtLabeledExpression) || (myParent instanceof KtAnnotatedExpression)) {
                myParent = myParent.getMyParent();
            } else {
                Intrinsics.checkNotNull(myParent);
                return myParent;
            }
        }
    }
}
