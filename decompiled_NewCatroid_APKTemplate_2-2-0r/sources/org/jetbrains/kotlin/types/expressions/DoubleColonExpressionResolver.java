package org.jetbrains.kotlin.types.expressions;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.codeFragmentUtil.CodeFragmentUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.PossiblyBareType;
import org.jetbrains.kotlin.resolve.QualifiedExpressionResolver;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolutionContext;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsUtil;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.FunctionTypeResolveUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¦\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 \u008c\u00012\u00020\u0001:\b\u008c\u0001\u008d\u0001\u008e\u0001\u008f\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019J1\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\n\u00108\u001a\u0006\u0012\u0002\b\u0003092\u0006\u0010:\u001a\u00020;H\u0000¢\u0006\u0002\b<J3\u0010=\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u0010>\u001a\u0002072\n\u00108\u001a\u0006\u0012\u0002\b\u0003092\b\b\u0002\u0010?\u001a\u00020@H\u0000¢\u0006\u0002\bAJ(\u0010B\u001a\u0002032\u0006\u0010C\u001a\u00020D2\u0006\u00104\u001a\u00020E2\u0006\u00106\u001a\u0002072\u0006\u0010F\u001a\u00020GH\u0002J%\u0010H\u001a\u0002032\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u00104\u001a\u000205H\u0000¢\u0006\u0002\bMJ2\u0010N\u001a\u0004\u0018\u0001072\u0006\u00104\u001a\u0002052\b\u0010O\u001a\u0004\u0018\u00010G2\f\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010Q2\u0006\u00108\u001a\u00020DH\u0002J\u0010\u0010R\u001a\u00020@2\u0006\u00106\u001a\u000207H\u0002J\u0010\u0010S\u001a\u00020@2\u0006\u0010I\u001a\u00020TH\u0002J2\u0010U\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00108\u001a\u00020D2\b\u0010V\u001a\u0004\u0018\u00010G2\u000e\u0010P\u001a\n\u0012\u0004\u0012\u00020J\u0018\u00010QH\u0002J\u0018\u0010W\u001a\u0002032\u0006\u00104\u001a\u00020X2\u0006\u0010C\u001a\u00020DH\u0002J2\u0010Y\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010G\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010Q0Z2\u0006\u00104\u001a\u0002052\u0006\u00108\u001a\u00020D2\u0006\u0010[\u001a\u00020\\J6\u0010]\u001a\n\u0012\u0004\u0012\u00020J\u0018\u00010Q2\u0006\u00104\u001a\u0002052\b\u0010O\u001a\u0004\u0018\u00010G2\n\u0010C\u001a\u0006\u0012\u0002\b\u0003092\u0006\u0010^\u001a\u00020\\H\u0002J\u001f\u0010_\u001a\u0004\u0018\u00010G2\u0006\u0010`\u001a\u00020X2\u0006\u0010C\u001a\u00020DH\u0000¢\u0006\u0002\baJ\u001a\u0010b\u001a\u0004\u0018\u00010c2\u0006\u00104\u001a\u00020d2\u0006\u0010C\u001a\u00020DH\u0002J\u001a\u0010e\u001a\u0004\u0018\u00010c2\u0006\u00104\u001a\u00020d2\u0006\u0010C\u001a\u00020DH\u0002J\u001a\u0010f\u001a\u0004\u0018\u00010c2\u0006\u00104\u001a\u00020d2\u0006\u0010C\u001a\u00020DH\u0002J\u0018\u0010g\u001a\u00020h2\u0006\u0010`\u001a\u00020X2\u0006\u0010C\u001a\u00020DH\u0002J\"\u0010i\u001a\u0004\u0018\u00010j2\u0006\u00104\u001a\u00020d2\u0006\u0010`\u001a\u00020X2\u0006\u0010C\u001a\u00020DH\u0002J\u0010\u0010k\u001a\u0002032\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010l\u001a\u00020@2\u0006\u00104\u001a\u00020XH\u0002J\u0010\u0010m\u001a\u00020@2\u0006\u00104\u001a\u00020XH\u0002J\u0010\u0010n\u001a\u00020@2\u0006\u00104\u001a\u00020XH\u0002J\u0010\u0010o\u001a\u00020@2\u0006\u00104\u001a\u00020XH\u0002JZ\u0010p\u001a\n\u0012\u0004\u0012\u0002Hr\u0018\u00010q\"\b\b\u0000\u0010r*\u00020G2\u0006\u0010`\u001a\u00020X2\u0006\u00108\u001a\u00020D2\u0012\u0010s\u001a\u000e\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020@0t2\u001a\u0010u\u001a\u0016\u0012\u0004\u0012\u00020d\u0012\u0004\u0012\u00020D\u0012\u0006\u0012\u0004\u0018\u0001Hr0vH\u0002JC\u0010w\u001a\u0004\u0018\u00010x2\u0006\u0010y\u001a\u00020z2\b\u0010{\u001a\u0004\u0018\u00010|2\u0006\u0010}\u001a\u00020~2\n\u0010\u007f\u001a\u0006\u0012\u0002\b\u0003092\u0007\u0010\u0080\u0001\u001a\u00020\\2\b\u0010O\u001a\u0004\u0018\u00010GH\u0002J\u0018\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0006\u00104\u001a\u0002052\u0006\u0010C\u001a\u00020DJ\u0018\u0010\u0083\u0001\u001a\u00030\u0082\u00012\u0006\u00104\u001a\u00020E2\u0006\u0010C\u001a\u00020DJ\u0010\u0010\u0084\u0001\u001a\u0004\u0018\u00010d*\u00030\u0085\u0001H\u0002J\r\u0010\u0086\u0001\u001a\u00020@*\u00020dH\u0002J\r\u0010\u0087\u0001\u001a\u00020@*\u00020dH\u0002J\u000f\u0010\u0088\u0001\u001a\u00020@*\u0004\u0018\u00010dH\u0002J\u0011\u0010\u0089\u0001\u001a\u0004\u0018\u00010z*\u0004\u0018\u00010dH\u0002J\u0016\u0010\u008a\u0001\u001a\u000b\u0012\u0004\u0012\u00020d\u0018\u00010\u008b\u0001*\u00020dH\u0002R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b0\u00101¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver;", "", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "qualifiedExpressionResolver", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "additionalCheckers", "", "Lorg/jetbrains/kotlin/types/expressions/ClassLiteralChecker;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "bigAritySupport", "Lorg/jetbrains/kotlin/types/expressions/FunctionWithBigAritySupport;", "genericArrayClassLiteralSupport", "Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/resolve/calls/CallResolver;Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/types/expressions/FunctionWithBigAritySupport;Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "getAdditionalCheckers", "()Ljava/lang/Iterable;", "getBigAritySupport", "()Lorg/jetbrains/kotlin/types/expressions/FunctionWithBigAritySupport;", "getCallResolver", "()Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "getDataFlowAnalyzer", "()Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "getDataFlowValueFactory", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "getGenericArrayClassLiteralSupport", "()Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getQualifiedExpressionResolver", "()Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "getReflectionTypes", "()Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "getTypeResolver", "()Lorg/jetbrains/kotlin/resolve/TypeResolver;", "bindFunctionReference", "", "expression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "referencedFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "bindFunctionReference$frontend", "bindPropertyReference", "referenceType", "mutable", "", "bindPropertyReference$frontend", "checkClassLiteral", "c", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "result", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "checkReferenceIsToAllowedMember", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkReferenceIsToAllowedMember$frontend", "getCallableReferenceType", "lhs", "resolutionResults", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "isAllowedInClassLiteral", "isMemberExtension", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "reportUnsupportedCallableReferenceIfNeeded", "lhsResult", "reportUnsupportedIfNeeded", "Lorg/jetbrains/kotlin/psi/KtDoubleColonExpression;", "resolveCallableReference", "Lkotlin/Pair;", "resolveArgumentsMode", "Lorg/jetbrains/kotlin/resolve/calls/util/ResolveArgumentsMode;", "resolveCallableReferenceRHS", "mode", "resolveDoubleColonLHS", "doubleColonExpression", "resolveDoubleColonLHS$frontend", "resolveExpressionOnLHS", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Expression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "resolveReservedCallChainOnLHS", "resolveReservedExpressionOnLHS", "resolveReservedExpressionSyntaxOnDoubleColonLHS", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$ReservedDoubleColonLHSResolutionResult;", "resolveTypeOnLHS", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS$Type;", "setExpressionTypingServices", "shouldTryResolveLHSAsExpression", "shouldTryResolveLHSAsReservedCallChain", "shouldTryResolveLHSAsReservedExpression", "shouldTryResolveLHSAsType", "tryResolveLHS", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$LHSResolutionResult;", ExifInterface.GPS_DIRECTION_TRUE, "criterion", "Lkotlin/Function1;", "resolve", "Lkotlin/Function2;", "tryResolveRHSWithReceiver", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$ResolutionResultsAndTraceCommitCallback;", "traceTitle", "", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "reference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "outerContext", "resolutionMode", "visitCallableReferenceExpression", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "visitClassLiteralExpression", "buildNewExpressionForReservedGenericPropertyCallChainResolution", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "canBeConsideredProperExpression", "canBeConsideredProperType", "canBeReservedGenericPropertyCall", "getQualifiedNameStringPart", "getQualifierChainParts", "", "Companion", "LHSResolutionResult", "ReservedDoubleColonLHSResolutionResult", "ResolutionResultsAndTraceCommitCallback", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DoubleColonExpressionResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Iterable<ClassLiteralChecker> additionalCheckers;
    private final FunctionWithBigAritySupport bigAritySupport;
    private final CallResolver callResolver;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final DataFlowValueFactory dataFlowValueFactory;
    private ExpressionTypingServices expressionTypingServices;
    private final GenericArrayClassLiteralSupport genericArrayClassLiteralSupport;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final ReflectionTypes reflectionTypes;
    private final TypeResolver typeResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public DoubleColonExpressionResolver(CallResolver callResolver, QualifiedExpressionResolver qualifiedExpressionResolver, DataFlowAnalyzer dataFlowAnalyzer, ReflectionTypes reflectionTypes, TypeResolver typeResolver, LanguageVersionSettings languageVersionSettings, Iterable<? extends ClassLiteralChecker> additionalCheckers, DataFlowValueFactory dataFlowValueFactory, FunctionWithBigAritySupport bigAritySupport, GenericArrayClassLiteralSupport genericArrayClassLiteralSupport, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(qualifiedExpressionResolver, "qualifiedExpressionResolver");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(additionalCheckers, "additionalCheckers");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(bigAritySupport, "bigAritySupport");
        Intrinsics.checkNotNullParameter(genericArrayClassLiteralSupport, "genericArrayClassLiteralSupport");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.callResolver = callResolver;
        this.qualifiedExpressionResolver = qualifiedExpressionResolver;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.reflectionTypes = reflectionTypes;
        this.typeResolver = typeResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.additionalCheckers = additionalCheckers;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.bigAritySupport = bigAritySupport;
        this.genericArrayClassLiteralSupport = genericArrayClassLiteralSupport;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    public final CallResolver getCallResolver() {
        return this.callResolver;
    }

    public final QualifiedExpressionResolver getQualifiedExpressionResolver() {
        return this.qualifiedExpressionResolver;
    }

    public final DataFlowAnalyzer getDataFlowAnalyzer() {
        return this.dataFlowAnalyzer;
    }

    public final ReflectionTypes getReflectionTypes() {
        return this.reflectionTypes;
    }

    public final TypeResolver getTypeResolver() {
        return this.typeResolver;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final Iterable<ClassLiteralChecker> getAdditionalCheckers() {
        return this.additionalCheckers;
    }

    public final DataFlowValueFactory getDataFlowValueFactory() {
        return this.dataFlowValueFactory;
    }

    public final FunctionWithBigAritySupport getBigAritySupport() {
        return this.bigAritySupport;
    }

    public final GenericArrayClassLiteralSupport getGenericArrayClassLiteralSupport() {
        return this.genericArrayClassLiteralSupport;
    }

    public final KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    @Inject
    public final void setExpressionTypingServices(ExpressionTypingServices expressionTypingServices) {
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        this.expressionTypingServices = expressionTypingServices;
    }

    public final KotlinTypeInfo visitClassLiteralExpression(KtClassLiteralExpression expression, ExpressionTypingContext c) {
        DataFlowInfo dataFlowInfo;
        KotlinType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(c, "c");
        if (expression.isEmptyLHS()) {
            c.trace.report(Errors.UNSUPPORTED_CLASS_LITERALS_WITH_EMPTY_LHS.on(expression));
        } else {
            DoubleColonLHS doubleColonLHSResolveDoubleColonLHS$frontend = resolveDoubleColonLHS$frontend(expression, c);
            if ((c.inferenceSession instanceof BuilderInferenceSession) && doubleColonLHSResolveDoubleColonLHS$frontend != null && (type = doubleColonLHSResolveDoubleColonLHS$frontend.getType()) != null && TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver.visitClassLiteralExpression.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof StubTypeForBuilderInference);
                }
            })) {
                ((BuilderInferenceSession) c.inferenceSession).addExpression(expression);
            }
            if (doubleColonLHSResolveDoubleColonLHS$frontend != null && !KotlinTypeKt.isError(doubleColonLHSResolveDoubleColonLHS$frontend.getType())) {
                KotlinType type2 = doubleColonLHSResolveDoubleColonLHS$frontend.getType();
                boolean z = doubleColonLHSResolveDoubleColonLHS$frontend instanceof DoubleColonLHS.Expression;
                DoubleColonLHS.Expression expression2 = z ? (DoubleColonLHS.Expression) doubleColonLHSResolveDoubleColonLHS$frontend : null;
                if (expression2 == null || (dataFlowInfo = expression2.getDataFlowInfo()) == null) {
                    dataFlowInfo = c.dataFlowInfo;
                    Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
                }
                DataFlowValueFactory dataFlowValueFactory = this.dataFlowValueFactory;
                KtExpression receiverExpression = expression.getReceiverExpression();
                Intrinsics.checkNotNull(receiverExpression);
                ExpressionTypingContext expressionTypingContext = c;
                if (!dataFlowInfo.getStableNullability(dataFlowValueFactory.createDataFlowValue(receiverExpression, type2, expressionTypingContext)).canBeNull()) {
                    type2 = TypeUtilsKt.makeNotNullable(type2);
                }
                checkClassLiteral(c, expression, type2, doubleColonLHSResolveDoubleColonLHS$frontend);
                KotlinTypeInfo kotlinTypeInfoCheckType = this.dataFlowAnalyzer.checkType(TypeInfoFactoryKt.createTypeInfo(this.reflectionTypes.getKClassType(Annotations.INSTANCE.getEMPTY(), type2, (!z || ((DoubleColonLHS.Expression) doubleColonLHSResolveDoubleColonLHS$frontend).getIsObjectQualifier()) ? Variance.INVARIANT : Variance.OUT_VARIANCE), dataFlowInfo), expression, expressionTypingContext);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCheckType, "checkType(...)");
                return kotlinTypeInfoCheckType;
            }
        }
        ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_CLASS_TYPE;
        String text = expression.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return TypeInfoFactoryKt.createTypeInfo(ErrorUtils.createErrorType(errorTypeKind, text), c);
    }

    private final void checkClassLiteral(ExpressionTypingContext c, KtClassLiteralExpression expression, KotlinType type, DoubleColonLHS result) {
        if (result instanceof DoubleColonLHS.Expression) {
            if (((DoubleColonLHS.Expression) result).getIsObjectQualifier()) {
                return;
            }
            SimpleType anyType = TypeUtilsKt.getBuiltIns(type).getAnyType();
            Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
            if (!TypeUtilsKt.isSubtypeOf(type, anyType)) {
                BindingTrace bindingTrace = c.trace;
                DiagnosticFactory1<KtExpression, KotlinType> diagnosticFactory1 = Errors.EXPRESSION_OF_NULLABLE_TYPE_IN_CLASS_LITERAL_LHS;
                KtExpression receiverExpression = expression.getReceiverExpression();
                Intrinsics.checkNotNull(receiverExpression);
                bindingTrace.report(diagnosticFactory1.on(receiverExpression, type));
            }
            reportUnsupportedIfNeeded(expression, c);
            return;
        }
        Intrinsics.checkNotNull(result, "null cannot be cast to non-null type org.jetbrains.kotlin.types.expressions.DoubleColonLHS.Type");
        DoubleColonLHS.Type type2 = (DoubleColonLHS.Type) result;
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (type2.getPossiblyBareType().isBare() && (this$0 instanceof ClassDescriptor) && KotlinBuiltIns.isNonPrimitiveArray((ClassDescriptor) this$0) && !this.languageVersionSettings.supportsFeature(LanguageFeature.BareArrayClassLiteral)) {
            c.trace.report(Errors.ARRAY_CLASS_LITERAL_REQUIRES_ARGUMENT.on(expression));
        }
        if ((type instanceof SimpleType) && !type.getIsMarkedNullable() && (this$0 instanceof TypeParameterDescriptor) && !((TypeParameterDescriptor) this$0).isReified()) {
            c.trace.report(Errors.TYPE_PARAMETER_AS_REIFIED.on(expression, (TypeParameterDescriptor) this$0));
        } else if ((TypeUtils.isNullableType(type) && !(this$0 instanceof TypeParameterDescriptor)) || expression.getHasQuestionMarks()) {
            c.trace.report(Errors.NULLABLE_TYPE_IN_CLASS_LITERAL_LHS.on(expression));
        } else if (!type2.getPossiblyBareType().isBare() && !isAllowedInClassLiteral(type)) {
            c.trace.report(Errors.CLASS_LITERAL_LHS_NOT_A_CLASS.on(expression));
        }
        Iterator<ClassLiteralChecker> it = this.additionalCheckers.iterator();
        while (it.hasNext()) {
            it.next().check(expression, type, c);
        }
    }

    private final boolean canBeConsideredProperExpression(KtExpression ktExpression) {
        if (ktExpression instanceof KtCallExpression) {
            if (DoubleColonExpressionResolverKt.isWithoutValueArguments((KtCallExpression) ktExpression)) {
                return false;
            }
        } else if (ktExpression instanceof KtDotQualifiedExpression) {
            KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) ktExpression;
            if (!canBeConsideredProperExpression(ktDotQualifiedExpression.getReceiverExpression())) {
                return false;
            }
            KtExpression selectorExpression = ktDotQualifiedExpression.getSelectorExpression();
            if (!(selectorExpression != null ? canBeConsideredProperExpression(selectorExpression) : false)) {
                return false;
            }
        }
        return true;
    }

    private final boolean canBeConsideredProperType(KtExpression ktExpression) {
        KtExpression selectorExpression;
        if (ktExpression instanceof KtSimpleNameExpression) {
            return true;
        }
        if (ktExpression instanceof KtCallExpression) {
            return DoubleColonExpressionResolverKt.isWithoutValueArguments((KtCallExpression) ktExpression);
        }
        if (ktExpression instanceof KtDotQualifiedExpression) {
            KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) ktExpression;
            if (canBeConsideredProperType(ktDotQualifiedExpression.getReceiverExpression()) && (selectorExpression = ktDotQualifiedExpression.getSelectorExpression()) != null && canBeConsideredProperType(selectorExpression)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsExpression(KtDoubleColonExpression expression) {
        KtExpression receiverExpression = expression.getReceiverExpression();
        return (receiverExpression == null || !canBeConsideredProperExpression(receiverExpression) || expression.getHasQuestionMarks()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsType(KtDoubleColonExpression expression) {
        KtExpression receiverExpression = expression.getReceiverExpression();
        return receiverExpression != null && canBeConsideredProperType(receiverExpression);
    }

    private final void reportUnsupportedIfNeeded(KtDoubleColonExpression expression, ExpressionTypingContext c) {
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.BoundCallableReferences)) {
            return;
        }
        BindingTrace bindingTrace = c.trace;
        DiagnosticFactory1<PsiElement, Pair<LanguageFeature, LanguageVersionSettings>> diagnosticFactory1 = Errors.UNSUPPORTED_FEATURE;
        KtExpression receiverExpression = expression.getReceiverExpression();
        Intrinsics.checkNotNull(receiverExpression);
        bindingTrace.report(diagnosticFactory1.on(receiverExpression, TuplesKt.to(LanguageFeature.BoundCallableReferences, this.languageVersionSettings)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsReservedExpression(KtDoubleColonExpression expression) {
        KtExpression receiverExpression = expression.getReceiverExpression();
        if (receiverExpression == null) {
            return false;
        }
        return (expression.getHasQuestionMarks() && canBeConsideredProperExpression(receiverExpression)) || ((receiverExpression instanceof KtCallExpression) && canBeReservedGenericPropertyCall(receiverExpression));
    }

    private final List<KtExpression> getQualifierChainParts(KtExpression ktExpression) {
        if (!(ktExpression instanceof KtQualifiedExpression)) {
            return CollectionsKt.listOf(ktExpression);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        do {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) ktExpression;
            if (!Intrinsics.areEqual(ktQualifiedExpression.getOperationSign(), KtTokens.DOT)) {
                return null;
            }
            KtExpression selectorExpression = ktQualifiedExpression.getSelectorExpression();
            if (selectorExpression != null) {
                arrayDeque.push(selectorExpression);
            }
            ktExpression = ktQualifiedExpression.getReceiverExpression();
        } while (ktExpression instanceof KtQualifiedExpression);
        arrayDeque.push(ktExpression);
        return CollectionsKt.toList(arrayDeque);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsReservedCallChain(KtDoubleColonExpression expression) {
        List<KtExpression> qualifierChainParts;
        KtExpression receiverExpression = expression.getReceiverExpression();
        KtQualifiedExpression ktQualifiedExpression = receiverExpression instanceof KtQualifiedExpression ? (KtQualifiedExpression) receiverExpression : null;
        if (ktQualifiedExpression == null || (qualifierChainParts = getQualifierChainParts(ktQualifiedExpression)) == null) {
            return false;
        }
        List<KtExpression> list = qualifierChainParts;
        boolean z = list instanceof Collection;
        if (!z || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!canBeReservedGenericPropertyCall((KtExpression) it.next())) {
                    return false;
                }
            }
        }
        if (z && list.isEmpty()) {
            return false;
        }
        for (KtExpression ktExpression : list) {
            if (ktExpression instanceof KtCallExpression) {
                List<KtTypeProjection> typeArguments = ((KtCallExpression) ktExpression).getTypeArguments();
                Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
                if (!typeArguments.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean canBeReservedGenericPropertyCall(KtExpression ktExpression) {
        return getQualifiedNameStringPart(ktExpression) != null;
    }

    private final String getQualifiedNameStringPart(KtExpression ktExpression) {
        if (ktExpression instanceof KtNameReferenceExpression) {
            return ((KtNameReferenceExpression) ktExpression).getText();
        }
        if (ktExpression instanceof KtCallExpression) {
            KtCallExpression ktCallExpression = (KtCallExpression) ktExpression;
            if (ktCallExpression.getValueArguments().isEmpty()) {
                List<KtTypeProjection> typeArguments = ktCallExpression.getTypeArguments();
                Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
                if (!typeArguments.isEmpty()) {
                    KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
                    KtNameReferenceExpression ktNameReferenceExpression = calleeExpression instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) calleeExpression : null;
                    if (ktNameReferenceExpression != null) {
                        return ktNameReferenceExpression.getText();
                    }
                }
            }
        }
        return null;
    }

    private final KtExpression buildNewExpressionForReservedGenericPropertyCallChainResolution(KtQualifiedExpression ktQualifiedExpression) {
        List<KtExpression> qualifierChainParts = getQualifierChainParts(ktQualifiedExpression);
        if (qualifierChainParts == null) {
            return null;
        }
        List<KtExpression> list = qualifierChainParts;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            String qualifiedNameStringPart = getQualifiedNameStringPart((KtExpression) it.next());
            if (qualifiedNameStringPart == null) {
                return null;
            }
            arrayList.add(qualifiedNameStringPart);
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, ".", null, null, 0, null, null, 62, null);
        Project project = ktQualifiedExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return new KtPsiFactory(project, false).createExpression(strJoinToString$default);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoubleColonLHS.Expression resolveReservedExpressionOnLHS(KtExpression expression, ExpressionTypingContext c) {
        DoubleColonLHS.Expression expressionResolveExpressionOnLHS;
        PsiElement parent = expression.getLightParent();
        KtDoubleColonExpression ktDoubleColonExpression = parent instanceof KtDoubleColonExpression ? (KtDoubleColonExpression) parent : null;
        if (ktDoubleColonExpression == null) {
            return null;
        }
        if (expression instanceof KtCallExpression) {
            KtCallExpression ktCallExpression = (KtCallExpression) expression;
            List<KtTypeProjection> typeArguments = ktCallExpression.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            if (!typeArguments.isEmpty()) {
                KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
                if (calleeExpression == null || (expressionResolveExpressionOnLHS = resolveExpressionOnLHS(calleeExpression, c)) == null) {
                    return null;
                }
                Iterator<KtTypeProjection> it = ktCallExpression.getTypeArguments().iterator();
                while (it.hasNext()) {
                    KtTypeReference typeReference = it.next().getTypeReference();
                    if (typeReference != null) {
                        TypeResolver typeResolver = this.typeResolver;
                        LexicalScope scope = c.scope;
                        Intrinsics.checkNotNullExpressionValue(scope, "scope");
                        BindingTrace trace = c.trace;
                        Intrinsics.checkNotNullExpressionValue(trace, "trace");
                        typeResolver.resolveType(scope, typeReference, trace, true);
                    }
                }
                return expressionResolveExpressionOnLHS;
            }
        }
        if (ktDoubleColonExpression.getHasQuestionMarks()) {
            return resolveExpressionOnLHS(expression, c);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoubleColonLHS.Expression resolveReservedCallChainOnLHS(KtExpression expression, ExpressionTypingContext c) {
        KtExpression ktExpressionBuildNewExpressionForReservedGenericPropertyCallChainResolution;
        if (!(expression instanceof KtQualifiedExpression) || (ktExpressionBuildNewExpressionForReservedGenericPropertyCallChainResolution = buildNewExpressionForReservedGenericPropertyCallChainResolution((KtQualifiedExpression) expression)) == null) {
            return null;
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = c.replaceTraceAndCache(TemporaryTraceAndCache.create(c, "resolve reserved generic property call chain in '::' LHS", ktExpressionBuildNewExpressionForReservedGenericPropertyCallChainResolution)).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        return resolveExpressionOnLHS(expression, expressionTypingContextReplaceContextDependency);
    }

    private final ReservedDoubleColonLHSResolutionResult resolveReservedExpressionSyntaxOnDoubleColonLHS(KtDoubleColonExpression doubleColonExpression, ExpressionTypingContext c) {
        TemporaryTraceAndCache traceAndCache;
        LHSResolutionResult lHSResolutionResultTryResolveLHS = tryResolveLHS(doubleColonExpression, c, new DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedExpr$1(this), new DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedExpr$2(this));
        if (lHSResolutionResultTryResolveLHS != null && ((DoubleColonLHS.Expression) lHSResolutionResultTryResolveLHS.getLhs()) != null) {
            c.trace.report(Errors.RESERVED_SYNTAX_IN_CALLABLE_REFERENCE_LHS.on(lHSResolutionResultTryResolveLHS.getExpression()));
            return new ReservedDoubleColonLHSResolutionResult(true, lHSResolutionResultTryResolveLHS.commit(), lHSResolutionResultTryResolveLHS.getTraceAndCache());
        }
        LHSResolutionResult lHSResolutionResultTryResolveLHS2 = tryResolveLHS(doubleColonExpression, c, new DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedCallChain$1(this), new DoubleColonExpressionResolver$resolveReservedExpressionSyntaxOnDoubleColonLHS$resultForReservedCallChain$2(this));
        if (lHSResolutionResultTryResolveLHS2 != null && ((DoubleColonLHS.Expression) lHSResolutionResultTryResolveLHS2.getLhs()) != null) {
            c.trace.report(Errors.RESERVED_SYNTAX_IN_CALLABLE_REFERENCE_LHS.on(lHSResolutionResultTryResolveLHS2.getExpression()));
            return new ReservedDoubleColonLHSResolutionResult(true, null, lHSResolutionResultTryResolveLHS != null ? lHSResolutionResultTryResolveLHS.getTraceAndCache() : null);
        }
        if (lHSResolutionResultTryResolveLHS == null || (traceAndCache = lHSResolutionResultTryResolveLHS.getTraceAndCache()) == null) {
            traceAndCache = lHSResolutionResultTryResolveLHS2 != null ? lHSResolutionResultTryResolveLHS2.getTraceAndCache() : null;
        }
        return new ReservedDoubleColonLHSResolutionResult(false, null, traceAndCache);
    }

    public final DoubleColonLHS resolveDoubleColonLHS$frontend(final KtDoubleColonExpression doubleColonExpression, ExpressionTypingContext c) {
        DoubleColonLHS.Expression expression;
        Intrinsics.checkNotNullParameter(doubleColonExpression, "doubleColonExpression");
        Intrinsics.checkNotNullParameter(c, "c");
        LHSResolutionResult lHSResolutionResultTryResolveLHS = tryResolveLHS(doubleColonExpression, c, new DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$1(this), new DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$2(this));
        if (lHSResolutionResultTryResolveLHS != null && (expression = (DoubleColonLHS.Expression) lHSResolutionResultTryResolveLHS.getLhs()) != null && !expression.getIsObjectQualifier()) {
            return lHSResolutionResultTryResolveLHS.commit();
        }
        ReservedDoubleColonLHSResolutionResult reservedDoubleColonLHSResolutionResultResolveReservedExpressionSyntaxOnDoubleColonLHS = resolveReservedExpressionSyntaxOnDoubleColonLHS(doubleColonExpression, c);
        boolean isReservedExpressionSyntax = reservedDoubleColonLHSResolutionResultResolveReservedExpressionSyntaxOnDoubleColonLHS.getIsReservedExpressionSyntax();
        DoubleColonLHS lhs = reservedDoubleColonLHSResolutionResultResolveReservedExpressionSyntaxOnDoubleColonLHS.getLhs();
        TemporaryTraceAndCache traceAndCache = reservedDoubleColonLHSResolutionResultResolveReservedExpressionSyntaxOnDoubleColonLHS.getTraceAndCache();
        if (isReservedExpressionSyntax) {
            return lhs;
        }
        LHSResolutionResult lHSResolutionResultTryResolveLHS2 = tryResolveLHS(doubleColonExpression, c, new DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$1(this), new Function2<KtExpression, ExpressionTypingContext, DoubleColonLHS.Type>() { // from class: org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final DoubleColonLHS.Type invoke(KtExpression expression2, ExpressionTypingContext context) {
                Intrinsics.checkNotNullParameter(expression2, "expression");
                Intrinsics.checkNotNullParameter(context, "context");
                DoubleColonLHS.Type typeResolveTypeOnLHS = this.this$0.resolveTypeOnLHS(expression2, doubleColonExpression, context);
                if (typeResolveTypeOnLHS == null) {
                    return null;
                }
                DoubleColonExpressionResolver doubleColonExpressionResolver = this.this$0;
                return new DoubleColonLHS.Type(doubleColonExpressionResolver.getKotlinTypeRefiner().refineType((KotlinTypeMarker) typeResolveTypeOnLHS.getType()), DoubleColonExpressionResolverKt.refineBareType(doubleColonExpressionResolver.getKotlinTypeRefiner(), typeResolveTypeOnLHS.getPossiblyBareType()));
            }
        });
        if (lHSResolutionResultTryResolveLHS2 != null) {
            DoubleColonLHS.Type type = (DoubleColonLHS.Type) lHSResolutionResultTryResolveLHS2.getLhs();
            if (lHSResolutionResultTryResolveLHS != null && type != null) {
                KotlinType type2 = type.getType();
                DoubleColonLHS.Expression expression2 = (DoubleColonLHS.Expression) lHSResolutionResultTryResolveLHS.getLhs();
                if (Intrinsics.areEqual(type2, expression2 != null ? expression2.getType() : null)) {
                    return lHSResolutionResultTryResolveLHS.commit();
                }
            }
            if (type != null) {
                return lHSResolutionResultTryResolveLHS2.commit();
            }
        }
        if (lHSResolutionResultTryResolveLHS != null) {
            return lHSResolutionResultTryResolveLHS.commit();
        }
        if (lHSResolutionResultTryResolveLHS2 != null) {
            return lHSResolutionResultTryResolveLHS2.commit();
        }
        if (traceAndCache != null) {
            traceAndCache.commit();
        }
        return null;
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0007HÆ\u0003J+\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$ReservedDoubleColonLHSResolutionResult;", "", "isReservedExpressionSyntax", "", "lhs", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "traceAndCache", "Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;", "(ZLorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;)V", "()Z", "getLhs", "()Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "getTraceAndCache", "()Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ReservedDoubleColonLHSResolutionResult {
        private final boolean isReservedExpressionSyntax;
        private final DoubleColonLHS lhs;
        private final TemporaryTraceAndCache traceAndCache;

        public static /* synthetic */ ReservedDoubleColonLHSResolutionResult copy$default(ReservedDoubleColonLHSResolutionResult reservedDoubleColonLHSResolutionResult, boolean z, DoubleColonLHS doubleColonLHS, TemporaryTraceAndCache temporaryTraceAndCache, int i, Object obj) {
            if ((i & 1) != 0) {
                z = reservedDoubleColonLHSResolutionResult.isReservedExpressionSyntax;
            }
            if ((i & 2) != 0) {
                doubleColonLHS = reservedDoubleColonLHSResolutionResult.lhs;
            }
            if ((i & 4) != 0) {
                temporaryTraceAndCache = reservedDoubleColonLHSResolutionResult.traceAndCache;
            }
            return reservedDoubleColonLHSResolutionResult.copy(z, doubleColonLHS, temporaryTraceAndCache);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getIsReservedExpressionSyntax() {
            return this.isReservedExpressionSyntax;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final DoubleColonLHS getLhs() {
            return this.lhs;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final TemporaryTraceAndCache getTraceAndCache() {
            return this.traceAndCache;
        }

        public final ReservedDoubleColonLHSResolutionResult copy(boolean isReservedExpressionSyntax, DoubleColonLHS lhs, TemporaryTraceAndCache traceAndCache) {
            return new ReservedDoubleColonLHSResolutionResult(isReservedExpressionSyntax, lhs, traceAndCache);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ReservedDoubleColonLHSResolutionResult)) {
                return false;
            }
            ReservedDoubleColonLHSResolutionResult reservedDoubleColonLHSResolutionResult = (ReservedDoubleColonLHSResolutionResult) other;
            return this.isReservedExpressionSyntax == reservedDoubleColonLHSResolutionResult.isReservedExpressionSyntax && Intrinsics.areEqual(this.lhs, reservedDoubleColonLHSResolutionResult.lhs) && Intrinsics.areEqual(this.traceAndCache, reservedDoubleColonLHSResolutionResult.traceAndCache);
        }

        public int hashCode() {
            int iHashCode = Boolean.hashCode(this.isReservedExpressionSyntax) * 31;
            DoubleColonLHS doubleColonLHS = this.lhs;
            int iHashCode2 = (iHashCode + (doubleColonLHS == null ? 0 : doubleColonLHS.hashCode())) * 31;
            TemporaryTraceAndCache temporaryTraceAndCache = this.traceAndCache;
            return iHashCode2 + (temporaryTraceAndCache != null ? temporaryTraceAndCache.hashCode() : 0);
        }

        public String toString() {
            return "ReservedDoubleColonLHSResolutionResult(isReservedExpressionSyntax=" + this.isReservedExpressionSyntax + ", lhs=" + this.lhs + ", traceAndCache=" + this.traceAndCache + ')';
        }

        public ReservedDoubleColonLHSResolutionResult(boolean z, DoubleColonLHS doubleColonLHS, TemporaryTraceAndCache temporaryTraceAndCache) {
            this.isReservedExpressionSyntax = z;
            this.lhs = doubleColonLHS;
            this.traceAndCache = temporaryTraceAndCache;
        }

        public final boolean isReservedExpressionSyntax() {
            return this.isReservedExpressionSyntax;
        }

        public final DoubleColonLHS getLhs() {
            return this.lhs;
        }

        public final TemporaryTraceAndCache getTraceAndCache() {
            return this.traceAndCache;
        }
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u001f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\r\u0010\u0011\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0015\u0010\u0004\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$LHSResolutionResult;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "", "lhs", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "traceAndCache", "Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;", "(Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getLhs", "()Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "getTraceAndCache", "()Lorg/jetbrains/kotlin/resolve/calls/context/TemporaryTraceAndCache;", "commit", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LHSResolutionResult<T extends DoubleColonLHS> {
        private final KtExpression expression;
        private final T lhs;
        private final TemporaryTraceAndCache traceAndCache;

        public LHSResolutionResult(T t, KtExpression expression, TemporaryTraceAndCache traceAndCache) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(traceAndCache, "traceAndCache");
            this.lhs = t;
            this.expression = expression;
            this.traceAndCache = traceAndCache;
        }

        public final T getLhs() {
            return this.lhs;
        }

        public final KtExpression getExpression() {
            return this.expression;
        }

        public final TemporaryTraceAndCache getTraceAndCache() {
            return this.traceAndCache;
        }

        public final T commit() {
            if (this.lhs != null) {
                TemporaryBindingTrace temporaryBindingTrace = this.traceAndCache.trace;
                WritableSlice<KtExpression, DoubleColonLHS> DOUBLE_COLON_LHS = BindingContext.DOUBLE_COLON_LHS;
                Intrinsics.checkNotNullExpressionValue(DOUBLE_COLON_LHS, "DOUBLE_COLON_LHS");
                temporaryBindingTrace.record(DOUBLE_COLON_LHS, this.expression, this.lhs);
            }
            this.traceAndCache.commit();
            return this.lhs;
        }
    }

    private final <T extends DoubleColonLHS> LHSResolutionResult<T> tryResolveLHS(KtDoubleColonExpression doubleColonExpression, ExpressionTypingContext context, Function1<? super KtDoubleColonExpression, Boolean> criterion, Function2<? super KtExpression, ? super ExpressionTypingContext, ? extends T> resolve) {
        KtExpression receiverExpression = doubleColonExpression.getReceiverExpression();
        if (receiverExpression == null || !criterion.invoke(doubleColonExpression).booleanValue()) {
            return null;
        }
        TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(context, "resolve '::' LHS", doubleColonExpression);
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = context.replaceTraceAndCache(temporaryTraceAndCacheCreate).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
        T tInvoke = resolve.invoke(receiverExpression, expressionTypingContextReplaceContextDependency);
        Intrinsics.checkNotNull(temporaryTraceAndCacheCreate);
        return new LHSResolutionResult<>(tInvoke, receiverExpression, temporaryTraceAndCacheCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoubleColonLHS.Expression resolveExpressionOnLHS(KtExpression expression, ExpressionTypingContext c) {
        ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
        if (expressionTypingServices == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expressionTypingServices");
            expressionTypingServices = null;
        }
        KotlinTypeInfo typeInfo = expressionTypingServices.getTypeInfo(expression, c);
        Intrinsics.checkNotNullExpressionValue(typeInfo, "getTypeInfo(...)");
        if (typeInfo.getType() == null) {
            return null;
        }
        Call call = (Call) c.trace.getContext().get(BindingContext.CALL, KtPsiUtilKt.getQualifiedElementSelector(expression));
        BindingContext bindingContext = c.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(call, bindingContext);
        if (resolvedCall != null) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            if (resultingDescriptor instanceof FakeCallableDescriptorForObject) {
                ClassDescriptor classDescriptor = ((FakeCallableDescriptorForObject) resultingDescriptor).getClassDescriptor();
                if (classDescriptor.mo6346getCompanionObjectDescriptor() != null) {
                    return null;
                }
                ClassDescriptor classDescriptor2 = classDescriptor;
                if (DescriptorUtils.isObject(classDescriptor2) || (!this.languageVersionSettings.supportsFeature(LanguageFeature.BoundCallableReferences) && DescriptorUtils.isEnumEntry(classDescriptor2))) {
                    return new DoubleColonLHS.Expression(typeInfo, true);
                }
            }
            if (canBeConsideredProperType(expression) && !(resultingDescriptor instanceof VariableDescriptor)) {
                return null;
            }
        }
        return new DoubleColonLHS.Expression(typeInfo, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoubleColonLHS.Type resolveTypeOnLHS(KtExpression expression, KtDoubleColonExpression doubleColonExpression, ExpressionTypingContext c) {
        SimpleType actualType;
        QualifiedExpressionResolver qualifiedExpressionResolver = this.qualifiedExpressionResolver;
        LexicalScope scope = c.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        BindingTrace trace = c.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        QualifiedExpressionResolver.TypeQualifierResolutionResult typeQualifierResolutionResultResolveDescriptorForDoubleColonLHS = qualifiedExpressionResolver.resolveDescriptorForDoubleColonLHS(expression, scope, trace, c.isDebuggerContext);
        KtExpression ktExpression = expression;
        TypeResolutionContext typeResolutionContext = new TypeResolutionContext(c.scope, c.trace, true, true, CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(ktExpression));
        ClassifierDescriptor classifierDescriptor = typeQualifierResolutionResultResolveDescriptorForDoubleColonLHS.getClassifierDescriptor();
        if (classifierDescriptor == null) {
            TypeResolver typeResolver = this.typeResolver;
            ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_TYPE;
            String text = expression.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            typeResolver.resolveTypeProjections(typeResolutionContext, ErrorUtils.createErrorType(errorTypeKind, text).getConstructor(), typeQualifierResolutionResultResolveDescriptorForDoubleColonLHS.getAllProjections());
            return null;
        }
        PossiblyBareType possiblyBareTypeResolveTypeForClassifier = this.typeResolver.resolveTypeForClassifier(typeResolutionContext, classifierDescriptor, typeQualifierResolutionResultResolveDescriptorForDoubleColonLHS, ktExpression, Annotations.INSTANCE.getEMPTY());
        if (possiblyBareTypeResolveTypeForClassifier.isBare()) {
            ClassifierDescriptor this$0 = possiblyBareTypeResolveTypeForClassifier.getBareTypeConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor == null) {
                throw new IllegalStateException(("Only classes can produce bare types: " + possiblyBareTypeResolveTypeForClassifier).toString());
            }
            if (doubleColonExpression instanceof KtCallableReferenceExpression) {
                c.trace.report(Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS.on(expression, Integer.valueOf(classDescriptor.getTypeConstructor().getParameters().size()), classDescriptor));
            }
            List<TypeParameterDescriptor> parameters = classDescriptor.getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            List<TypeParameterDescriptor> list = parameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(TypeUtils.makeStarProjection((TypeParameterDescriptor) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
            TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            actualType = KotlinTypeFactory.simpleType$default(empty, typeConstructor, arrayList2, possiblyBareTypeResolveTypeForClassifier.isNullable() || doubleColonExpression.getHasQuestionMarks(), (KotlinTypeRefiner) null, 16, (Object) null);
        } else {
            actualType = possiblyBareTypeResolveTypeForClassifier.getActualType();
            Intrinsics.checkNotNullExpressionValue(actualType, "getActualType(...)");
            if (doubleColonExpression.getHasQuestionMarks()) {
                actualType = TypeUtilsKt.makeNullable(actualType);
            }
        }
        return new DoubleColonLHS.Type(actualType, possiblyBareTypeResolveTypeForClassifier);
    }

    private final boolean isAllowedInClassLiteral(KotlinType type) {
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            if ((this.genericArrayClassLiteralSupport.isEnabled() || !this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitGenericArrayClassLiteral)) && KotlinBuiltIns.isNonPrimitiveArray((ClassDescriptor) this$0)) {
                List<TypeProjection> arguments = type.getArguments();
                if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                    for (TypeProjection typeProjection : arguments) {
                        if (typeProjection.isStarProjection()) {
                            return false;
                        }
                        KotlinType type2 = typeProjection.getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        if (!isAllowedInClassLiteral(type2)) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return type.getArguments().isEmpty();
        }
        if (this$0 instanceof TypeParameterDescriptor) {
            return ((TypeParameterDescriptor) this$0).isReified();
        }
        return false;
    }

    public final KotlinTypeInfo visitCallableReferenceExpression(KtCallableReferenceExpression expression, ExpressionTypingContext c) {
        DataFlowInfo dataFlowInfo;
        KotlinType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(c, "c");
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        boolean z = false;
        if (callableReference.getReferencedName().length() == 0) {
            if (!expression.isEmptyLHS()) {
                resolveDoubleColonLHS$frontend(expression, c);
            }
            c.trace.report(Errors.UNRESOLVED_REFERENCE.on(callableReference, callableReference));
            KotlinTypeInfo kotlinTypeInfoCreateCheckedTypeInfo = this.dataFlowAnalyzer.createCheckedTypeInfo(ErrorUtils.createErrorType(ErrorTypeKind.EMPTY_CALLABLE_REFERENCE, new String[0]), c, expression);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCreateCheckedTypeInfo, "createCheckedTypeInfo(...)");
            return kotlinTypeInfoCreateCheckedTypeInfo;
        }
        Pair<DoubleColonLHS, OverloadResolutionResults<?>> pairResolveCallableReference = resolveCallableReference(expression, c, ResolveArgumentsMode.RESOLVE_FUNCTION_ARGUMENTS);
        DoubleColonLHS doubleColonLHSComponent1 = pairResolveCallableReference.component1();
        OverloadResolutionResults<?> overloadResolutionResultsComponent2 = pairResolveCallableReference.component2();
        KotlinType callableReferenceType = getCallableReferenceType(expression, doubleColonLHSComponent1, overloadResolutionResultsComponent2, c);
        if (overloadResolutionResultsComponent2 != null) {
            Iterable resultingCalls = overloadResolutionResultsComponent2.getResultingCalls();
            Intrinsics.checkNotNullExpressionValue(resultingCalls, "getResultingCalls(...)");
            Iterable iterable = resultingCalls;
            if (!((Collection) iterable).isEmpty()) {
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    ReceiverValue receiverValueMo7305getExtensionReceiver = ((ResolvedCall) it.next()).getExtensionReceiver();
                    if (receiverValueMo7305getExtensionReceiver != null && (type = receiverValueMo7305getExtensionReceiver.getType()) != null) {
                        Intrinsics.checkNotNull(type);
                        if (TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver$visitCallableReferenceExpression$doesSomeExtensionReceiverContainsStubType$1$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(UnwrappedType it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
                            }
                        })) {
                            z = true;
                            break;
                        }
                    }
                }
            }
        }
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.UnrestrictedBuilderInference);
        if (z && !zSupportsFeature) {
            BindingTrace trace = c.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            Diagnostic diagnosticOn = Errors.TYPE_INFERENCE_POSTPONED_VARIABLE_IN_RECEIVER_TYPE.on(expression);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
            return TypeInfoFactoryKt.noTypeInfo(c);
        }
        DoubleColonLHS.Expression expression2 = doubleColonLHSComponent1 instanceof DoubleColonLHS.Expression ? (DoubleColonLHS.Expression) doubleColonLHSComponent1 : null;
        if (expression2 == null || (dataFlowInfo = expression2.getDataFlowInfo()) == null) {
            dataFlowInfo = c.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        }
        if ((c.inferenceSession instanceof BuilderInferenceSession) && callableReferenceType != null && TypeUtilsKt.contains(callableReferenceType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver.visitCallableReferenceExpression.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) {
            ((BuilderInferenceSession) c.inferenceSession).addExpression(expression);
        }
        KotlinTypeInfo kotlinTypeInfoCheckType = this.dataFlowAnalyzer.checkType(TypeInfoFactoryKt.createTypeInfo(callableReferenceType, dataFlowInfo), expression, c);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoCheckType, "checkType(...)");
        return kotlinTypeInfoCheckType;
    }

    private final KotlinType getCallableReferenceType(KtCallableReferenceExpression expression, DoubleColonLHS lhs, OverloadResolutionResults<?> resolutionResults, ExpressionTypingContext context) {
        if (resolutionResults != null && !resolutionResults.isNothing()) {
            ExpressionTypingContext expressionTypingContext = context;
            ResolvedCall resultingCall = OverloadResolutionResultsUtil.getResultingCall(resolutionResults, expressionTypingContext);
            CallableDescriptor resultingDescriptor = resultingCall != null ? resultingCall.getResultingDescriptor() : null;
            if (resultingDescriptor == null) {
                return null;
            }
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            checkReferenceIsToAllowedMember$frontend(resultingDescriptor, trace, expression);
            DeclarationDescriptor ownerDescriptor = context.scope.getOwnerDescriptor();
            Companion companion = INSTANCE;
            KotlinType kotlinTypeCreateKCallableTypeForReference = companion.createKCallableTypeForReference(resultingDescriptor, lhs, this.reflectionTypes, ownerDescriptor);
            if (kotlinTypeCreateKCallableTypeForReference == null) {
                return null;
            }
            if (resultingDescriptor instanceof FunctionDescriptor) {
                bindFunctionReference$frontend(expression, kotlinTypeCreateKCallableTypeForReference, expressionTypingContext, (FunctionDescriptor) resultingDescriptor);
            } else if (resultingDescriptor instanceof PropertyDescriptor) {
                bindPropertyReference$frontend(expression, kotlinTypeCreateKCallableTypeForReference, expressionTypingContext, companion.isMutablePropertyReference((PropertyDescriptor) resultingDescriptor, lhs, ownerDescriptor));
            }
            return kotlinTypeCreateKCallableTypeForReference;
        }
        if (lhs != null || expression.isEmptyLHS()) {
            context.trace.report(Errors.UNRESOLVED_REFERENCE.on(expression.getCallableReference(), expression.getCallableReference()));
        }
        return null;
    }

    public final void checkReferenceIsToAllowedMember$frontend(CallableDescriptor descriptor, BindingTrace trace, KtCallableReferenceExpression expression) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.CallableReferencesToClassMembersWithEmptyLHS) && expression.isEmptyLHS() && (descriptor.mo6753getDispatchReceiverParameter() != null || descriptor.getExtensionReceiverParameter() != null)) {
            trace.report(Errors.UNSUPPORTED_FEATURE.on(callableReference, TuplesKt.to(LanguageFeature.CallableReferencesToClassMembersWithEmptyLHS, this.languageVersionSettings)));
        }
        if ((descriptor instanceof ConstructorDescriptor) && DescriptorUtils.isAnnotationClass(((ConstructorDescriptor) descriptor).getContainingDeclaration())) {
            trace.report(Errors.CALLABLE_REFERENCE_TO_ANNOTATION_CONSTRUCTOR.on(callableReference));
        }
        if ((descriptor instanceof CallableMemberDescriptor) && isMemberExtension((CallableMemberDescriptor) descriptor)) {
            trace.report(Errors.EXTENSION_IN_CLASS_REFERENCE_NOT_ALLOWED.on(callableReference, descriptor));
        }
        if (!(descriptor instanceof VariableDescriptor) || (descriptor instanceof PropertyDescriptor)) {
            return;
        }
        trace.report(Errors.UNSUPPORTED_REFERENCES_TO_VARIABLES_AND_PARAMETERS.on(callableReference));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.descriptors.CallableMemberDescriptor] */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.descriptors.CallableMemberDescriptor] */
    /* JADX WARN: Type inference failed for: r2v5 */
    private final boolean isMemberExtension(CallableMemberDescriptor descriptor) {
        CallableMemberDescriptor callableFromObject;
        ImportedFromObjectCallableDescriptor importedFromObjectCallableDescriptor = descriptor instanceof ImportedFromObjectCallableDescriptor ? (ImportedFromObjectCallableDescriptor) descriptor : null;
        if (importedFromObjectCallableDescriptor != null && (callableFromObject = importedFromObjectCallableDescriptor.getCallableFromObject()) != null) {
            descriptor = callableFromObject;
        }
        return (descriptor.getExtensionReceiverParameter() == null || descriptor.mo6753getDispatchReceiverParameter() == null) ? false : true;
    }

    public final void bindFunctionReference$frontend(KtCallableReferenceExpression expression, KotlinType type, ResolutionContext<?> context, FunctionDescriptor referencedFunction) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(referencedFunction, "referencedFunction");
        AnonymousFunctionDescriptor anonymousFunctionDescriptor = new AnonymousFunctionDescriptor(context.scope.getOwnerDescriptor(), Annotations.INSTANCE.getEMPTY(), CallableMemberDescriptor.Kind.DECLARATION, KotlinSourceElementKt.toSourceElement(expression), ReflectionTypes.INSTANCE.isNumberedKSuspendFunction(type) || referencedFunction.isSuspend());
        anonymousFunctionDescriptor.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (List) FunctionTypeResolveUtilsKt.createValueParametersForInvokeInFunctionType(anonymousFunctionDescriptor, CollectionsKt.dropLast(type.getArguments(), 1)), ((TypeProjection) CollectionsKt.last((List) type.getArguments())).getNullableAnyType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        context.trace.record(BindingContext.FUNCTION, expression, anonymousFunctionDescriptor);
        if (anonymousFunctionDescriptor.getValueParameters().size() < 23 || !this.bigAritySupport.getShouldCheckLanguageVersionSettings() || this.languageVersionSettings.supportsFeature(LanguageFeature.FunctionTypesWithBigArity)) {
            return;
        }
        context.trace.report(Errors.UNSUPPORTED_FEATURE.on(expression, TuplesKt.to(LanguageFeature.FunctionTypesWithBigArity, this.languageVersionSettings)));
    }

    public static /* synthetic */ void bindPropertyReference$frontend$default(DoubleColonExpressionResolver doubleColonExpressionResolver, KtCallableReferenceExpression ktCallableReferenceExpression, KotlinType kotlinType, ResolutionContext resolutionContext, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        doubleColonExpressionResolver.bindPropertyReference$frontend(ktCallableReferenceExpression, kotlinType, resolutionContext, z);
    }

    public final void bindPropertyReference$frontend(KtCallableReferenceExpression expression, KotlinType referenceType, ResolutionContext<?> context, boolean mutable) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(referenceType, "referenceType");
        Intrinsics.checkNotNullParameter(context, "context");
        context.trace.record(BindingContext.VARIABLE, expression, new LocalVariableDescriptor(context.scope.getOwnerDescriptor(), Annotations.INSTANCE.getEMPTY(), SpecialNames.ANONYMOUS, referenceType, mutable, false, KotlinSourceElementKt.toSourceElement(expression)));
    }

    public final Pair<DoubleColonLHS, OverloadResolutionResults<?>> resolveCallableReference(KtCallableReferenceExpression expression, ExpressionTypingContext context, ResolveArgumentsMode resolveArgumentsMode) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolveArgumentsMode, "resolveArgumentsMode");
        DoubleColonLHS doubleColonLHSResolveDoubleColonLHS$frontend = expression.isEmptyLHS() ? null : resolveDoubleColonLHS$frontend(expression, context);
        OverloadResolutionResults<CallableDescriptor> overloadResolutionResultsResolveCallableReferenceRHS = resolveCallableReferenceRHS(expression, doubleColonLHSResolveDoubleColonLHS$frontend, context, resolveArgumentsMode);
        reportUnsupportedCallableReferenceIfNeeded(expression, context, doubleColonLHSResolveDoubleColonLHS$frontend, overloadResolutionResultsResolveCallableReferenceRHS);
        return TuplesKt.to(doubleColonLHSResolveDoubleColonLHS$frontend, overloadResolutionResultsResolveCallableReferenceRHS);
    }

    private final void reportUnsupportedCallableReferenceIfNeeded(KtCallableReferenceExpression expression, ExpressionTypingContext context, DoubleColonLHS lhsResult, OverloadResolutionResults<CallableDescriptor> resolutionResults) {
        CallableDescriptor callableDescriptorMo7437getResultingDescriptor = (resolutionResults == null || !resolutionResults.isSingleResult()) ? null : resolutionResults.mo7437getResultingDescriptor();
        if ((callableDescriptorMo7437getResultingDescriptor instanceof PropertyDescriptor) && CoroutineCallCheckerKt.isBuiltInCoroutineContext((PropertyDescriptor) callableDescriptorMo7437getResultingDescriptor)) {
            context.trace.report(Errors.UNSUPPORTED.on(expression.getCallableReference(), "Callable reference to suspend property"));
        }
        DoubleColonLHS.Expression expression2 = lhsResult instanceof DoubleColonLHS.Expression ? (DoubleColonLHS.Expression) lhsResult : null;
        if (expression2 == null) {
            return;
        }
        if (expression2.getIsObjectQualifier() && (callableDescriptorMo7437getResultingDescriptor instanceof ConstructorDescriptor)) {
            return;
        }
        reportUnsupportedIfNeeded(expression, context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$ResolutionResultsAndTraceCommitCallback;", "", "results", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "commitTrace", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;Lkotlin/jvm/functions/Function0;)V", "getCommitTrace", "()Lkotlin/jvm/functions/Function0;", "getResults", "()Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ResolutionResultsAndTraceCommitCallback {
        private final Function0<Unit> commitTrace;
        private final OverloadResolutionResults<CallableDescriptor> results;

        public ResolutionResultsAndTraceCommitCallback(OverloadResolutionResults<CallableDescriptor> results, Function0<Unit> commitTrace) {
            Intrinsics.checkNotNullParameter(results, "results");
            Intrinsics.checkNotNullParameter(commitTrace, "commitTrace");
            this.results = results;
            this.commitTrace = commitTrace;
        }

        public final OverloadResolutionResults<CallableDescriptor> getResults() {
            return this.results;
        }

        public final Function0<Unit> getCommitTrace() {
            return this.commitTrace;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ResolutionResultsAndTraceCommitCallback tryResolveRHSWithReceiver(String traceTitle, Receiver receiver, final KtSimpleNameExpression reference, final ResolutionContext<?> outerContext, final ResolveArgumentsMode resolutionMode, DoubleColonLHS lhs) {
        ResolutionContext resolutionContextReplaceTraceAndCache;
        Call callMakeCall = (Call) outerContext.trace.get(BindingContext.CALL, reference);
        if (callMakeCall == null) {
            callMakeCall = CallMaker.makeCall(reference, receiver, null, reference, CollectionsKt.emptyList());
        }
        Intrinsics.checkNotNull(callMakeCall);
        final TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(outerContext, traceTitle, reference);
        if (resolutionMode == ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS) {
            resolutionContextReplaceTraceAndCache = outerContext.replaceTraceAndCache(temporaryTraceAndCacheCreate).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.DEPENDENT);
        } else {
            resolutionContextReplaceTraceAndCache = outerContext.replaceTraceAndCache(temporaryTraceAndCacheCreate);
        }
        final OverloadResolutionResults<CallableDescriptor> overloadResolutionResultsResolveCallForMember = this.callResolver.resolveCallForMember(reference, BasicCallResolutionContext.create(resolutionContextReplaceTraceAndCache.replaceCallPosition(new CallPosition.CallableReferenceRhs(lhs)), callMakeCall, CheckArgumentTypesMode.CHECK_CALLABLE_TYPE));
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsResolveCallForMember, "resolveCallForMember(...)");
        if (overloadResolutionResultsResolveCallForMember.isNothing()) {
            return null;
        }
        return new ResolutionResultsAndTraceCommitCallback(overloadResolutionResultsResolveCallForMember, new Function0<Unit>() { // from class: org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver.tryResolveRHSWithReceiver.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                if (!DoubleColonExpressionResolver.this.getLanguageVersionSettings().supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
                    KtSimpleNameExpression ktSimpleNameExpression = reference;
                    BindingTrace trace = outerContext.trace;
                    Intrinsics.checkNotNullExpressionValue(trace, "trace");
                    ReservedCheckingKt.checkReservedYield(ktSimpleNameExpression, trace);
                }
                if (resolutionMode != ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS || overloadResolutionResultsResolveCallForMember.isSuccess()) {
                    temporaryTraceAndCacheCreate.commit();
                }
            }
        });
    }

    private final OverloadResolutionResults<CallableDescriptor> resolveCallableReferenceRHS(KtCallableReferenceExpression expression, DoubleColonLHS lhs, ResolutionContext<?> c, ResolveArgumentsMode mode) {
        ResolutionResultsAndTraceCommitCallback resolutionResultsAndTraceCommitCallbackTryResolveRHSWithReceiver;
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        KotlinType type = lhs != null ? lhs.getType() : null;
        if (type == null) {
            if (!expression.isEmptyLHS() || (resolutionResultsAndTraceCommitCallbackTryResolveRHSWithReceiver = tryResolveRHSWithReceiver("resolve callable reference with empty LHS", null, callableReference, c, mode, lhs)) == null) {
                return null;
            }
            resolutionResultsAndTraceCommitCallbackTryResolveRHSWithReceiver.getCommitTrace().invoke();
            return resolutionResultsAndTraceCommitCallbackTryResolveRHSWithReceiver.getResults();
        }
        resolutionResultsAndTraceCommitCallback = null;
        for (ResolutionResultsAndTraceCommitCallback resolutionResultsAndTraceCommitCallback : SequencesKt.sequence(new DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1(lhs, type, c, expression, this, callableReference, mode, null))) {
            if (resolutionResultsAndTraceCommitCallback.getResults().isSuccess()) {
                break;
            }
        }
        if (resolutionResultsAndTraceCommitCallback == null) {
            return null;
        }
        resolutionResultsAndTraceCommitCallback.getCommitTrace().invoke();
        return resolutionResultsAndTraceCommitCallback.getResults();
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J*\u0010\b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00112\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$Companion;", "", "()V", "contextReceiverTypesFor", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "createKCallableTypeForReference", "lhs", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "scopeOwnerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isMutablePropertyReference", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "receiverTypeFor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final List<KotlinType> contextReceiverTypesFor(CallableDescriptor descriptor) {
            List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
            Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
            List<ReceiverParameterDescriptor> list = contextReceiverParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ReceiverParameterDescriptor) it.next()).getType());
            }
            return arrayList;
        }

        private final KotlinType receiverTypeFor(CallableDescriptor descriptor, DoubleColonLHS lhs) {
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                extensionReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
            }
            if (extensionReceiverParameter == null) {
                return null;
            }
            DoubleColonLHS.Type type = lhs instanceof DoubleColonLHS.Type ? (DoubleColonLHS.Type) lhs : null;
            if (type != null) {
                return type.getType();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isMutablePropertyReference(PropertyDescriptor descriptor, DoubleColonLHS lhs, DeclarationDescriptor scopeOwnerDescriptor) {
            KotlinType kotlinTypeReceiverTypeFor = receiverTypeFor(descriptor, lhs);
            TransientReceiver transientReceiver = kotlinTypeReceiverTypeFor != null ? new TransientReceiver(kotlinTypeReceiverTypeFor) : null;
            PropertySetterDescriptor setter = descriptor.getSetter();
            if (descriptor.isVar()) {
                return setter == null || DescriptorVisibilities.isVisible(transientReceiver, setter, scopeOwnerDescriptor, false);
            }
            return false;
        }

        public final KotlinType createKCallableTypeForReference(CallableDescriptor descriptor, DoubleColonLHS lhs, ReflectionTypes reflectionTypes, DeclarationDescriptor scopeOwnerDescriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
            Intrinsics.checkNotNullParameter(scopeOwnerDescriptor, "scopeOwnerDescriptor");
            List<KotlinType> listContextReceiverTypesFor = contextReceiverTypesFor(descriptor);
            KotlinType kotlinTypeReceiverTypeFor = receiverTypeFor(descriptor, lhs);
            if (descriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
                KotlinType returnType = functionDescriptor.getReturnType();
                if (returnType == null) {
                    return null;
                }
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List<ValueParameterDescriptor> list = valueParameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ValueParameterDescriptor) it.next()).getType());
                }
                ArrayList arrayList2 = arrayList;
                List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                List<ValueParameterDescriptor> list2 = valueParameters2;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((ValueParameterDescriptor) it2.next()).getShortName());
                }
                return reflectionTypes.getKFunctionType(Annotations.INSTANCE.getEMPTY(), kotlinTypeReceiverTypeFor, listContextReceiverTypesFor, arrayList2, arrayList3, returnType, DescriptorUtilsKt.getBuiltIns(descriptor), functionDescriptor.isSuspend());
            }
            if (descriptor instanceof PropertyDescriptor) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
                boolean zIsMutablePropertyReference = isMutablePropertyReference(propertyDescriptor, lhs, scopeOwnerDescriptor);
                Annotations empty = Annotations.INSTANCE.getEMPTY();
                List<? extends KotlinType> listListOfNotNull = CollectionsKt.listOfNotNull(kotlinTypeReceiverTypeFor);
                KotlinType type = propertyDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return reflectionTypes.getKPropertyType(empty, listListOfNotNull, type, zIsMutablePropertyReference);
            }
            if (descriptor instanceof VariableDescriptor) {
                return null;
            }
            throw new UnsupportedOperationException("Callable reference resolved to an unsupported descriptor: " + descriptor);
        }
    }
}
