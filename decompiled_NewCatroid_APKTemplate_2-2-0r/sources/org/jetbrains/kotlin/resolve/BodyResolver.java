package org.jetbrains.kotlin.resolve;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.ScopeUtils;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;
import org.jetbrains.kotlin.types.expressions.ValueParameterResolver;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.Box;
import org.jetbrains.kotlin.util.ReenteringLazyValueComputationException;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class BodyResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AnalyzerExtensions analyzerExtensions;
    private final AnnotationChecker annotationChecker;
    private final AnnotationResolver annotationResolver;
    private final BodyResolveCache bodyResolveCache;
    private final KotlinBuiltIns builtIns;
    private final CallResolver callResolver;
    private final ControlFlowAnalyzer controlFlowAnalyzer;
    private final DeclarationsChecker declarationsChecker;
    private final DelegatedPropertyResolver delegatedPropertyResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final LanguageVersionSettings languageVersionSettings;
    private final OverloadChecker overloadChecker;
    private final Project project;
    private final ObservableBindingTrace trace;
    private final ValueParameterResolver valueParameterResolver;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 46 || i == 50 || i == 86) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 46 || i == 50 || i == 86) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotationResolver";
                break;
            case 2:
                objArr[0] = "bodyResolveCache";
                break;
            case 3:
                objArr[0] = "callResolver";
                break;
            case 4:
                objArr[0] = "controlFlowAnalyzer";
                break;
            case 5:
                objArr[0] = "declarationsChecker";
                break;
            case 6:
                objArr[0] = "delegatedPropertyResolver";
                break;
            case 7:
                objArr[0] = "expressionTypingServices";
                break;
            case 8:
                objArr[0] = "analyzerExtensions";
                break;
            case 9:
            case 18:
            case 23:
            case 51:
            case 89:
            case 94:
            case 99:
                objArr[0] = "trace";
                break;
            case 10:
                objArr[0] = "valueParameterResolver";
                break;
            case 11:
                objArr[0] = "annotationChecker";
                break;
            case 12:
                objArr[0] = "builtIns";
                break;
            case 13:
                objArr[0] = "overloadChecker";
                break;
            case 14:
                objArr[0] = "languageVersionSettings";
                break;
            case 15:
            case 16:
            case 31:
            case 32:
            case 57:
            case 63:
            case 64:
            case 67:
            case 68:
            case 71:
            case 84:
            case 87:
                objArr[0] = "c";
                break;
            case 17:
            case 22:
            case 33:
            case 43:
            case 58:
            case 74:
            case 79:
            case 88:
            case 93:
            case 98:
                objArr[0] = "outerDataFlowInfo";
                break;
            case 19:
            case 25:
            case 30:
            case 52:
            case 100:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 20:
            case 26:
            case 35:
            case 38:
            case 45:
            case 47:
            case 70:
                objArr[0] = "descriptor";
                break;
            case 21:
            case 92:
            case 102:
                objArr[0] = "declaringScope";
                break;
            case 24:
            case 62:
            case 97:
                objArr[0] = "scope";
                break;
            case 27:
            case 101:
                objArr[0] = "constructorDescriptor";
                break;
            case 28:
                objArr[0] = "visitedConstructors";
                break;
            case 29:
                objArr[0] = "startConstructor";
                break;
            case 34:
                objArr[0] = "ktClass";
                break;
            case 36:
            case 39:
                objArr[0] = "scopeForConstructorResolution";
                break;
            case 37:
                objArr[0] = "scopeForMemberResolution";
                break;
            case 40:
                objArr[0] = "ktEnumEntry";
                break;
            case 41:
                objArr[0] = "enumEntryDescriptor";
                break;
            case 42:
                objArr[0] = "scopeForConstructor";
                break;
            case 44:
                objArr[0] = "primaryConstructorDelegationCall";
                break;
            case 46:
            case 50:
            case 86:
                objArr[0] = "org/jetbrains/kotlin/resolve/BodyResolver";
                break;
            case 48:
            case 55:
                objArr[0] = "supertypes";
                break;
            case 49:
            case 56:
                objArr[0] = "ktClassOrObject";
                break;
            case 53:
                objArr[0] = "call";
                break;
            case 54:
                objArr[0] = "supertypeOwner";
                break;
            case 59:
                objArr[0] = "anonymousInitializer";
                break;
            case 60:
                objArr[0] = "classDescriptor";
                break;
            case 61:
                objArr[0] = "owner";
                break;
            case 65:
            case 72:
            case 75:
            case 80:
            case 85:
                objArr[0] = "property";
                break;
            case 66:
            case 73:
            case 76:
            case 81:
                objArr[0] = "propertyDescriptor";
                break;
            case 69:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
            case 77:
                objArr[0] = "delegateExpression";
                break;
            case 78:
                objArr[0] = "propertyHeaderScope";
                break;
            case 82:
                objArr[0] = "initializer";
                break;
            case 83:
                objArr[0] = "propertyHeader";
                break;
            case 90:
            case 95:
                objArr[0] = "function";
                break;
            case 91:
            case 96:
                objArr[0] = "functionDescriptor";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 46) {
            objArr[1] = "getConstructorForEmptyArgumentsList";
        } else if (i == 50) {
            objArr[1] = "getAllowedFinalSupertypes";
        } else if (i != 86) {
            objArr[1] = "org/jetbrains/kotlin/resolve/BodyResolver";
        } else {
            objArr[1] = "getScopeForProperty";
        }
        switch (i) {
            case 15:
                objArr[2] = "resolveBehaviorDeclarationBodies";
                break;
            case 16:
                objArr[2] = "resolveSecondaryConstructors";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "resolveSecondaryConstructorBody";
                break;
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = "resolveSecondaryConstructorDelegationCall";
                break;
            case 27:
            case 28:
                objArr[2] = "checkCyclicConstructorDelegationCall";
                break;
            case 29:
                objArr[2] = "reportEachConstructorOnCycle";
                break;
            case 30:
                objArr[2] = "getDelegatedConstructor";
                break;
            case 31:
                objArr[2] = "resolveBodies";
                break;
            case 32:
                objArr[2] = "resolveSuperTypeEntryLists";
                break;
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = "resolveSuperTypeEntryList";
                break;
            case 38:
            case 39:
                objArr[2] = "checkRedeclarationsInClassHeaderWithoutPrimaryConstructor";
                break;
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                objArr[2] = "resolveConstructorCallForEnumEntryWithoutInitializer";
                break;
            case 45:
                objArr[2] = "getConstructorForEmptyArgumentsList";
                break;
            case 46:
            case 50:
            case 86:
                break;
            case 47:
            case 48:
            case 49:
                objArr[2] = "getAllowedFinalSupertypes";
                break;
            case 51:
            case 52:
            case 53:
                objArr[2] = "recordConstructorDelegationCall";
                break;
            case 54:
            case 55:
            case 56:
                objArr[2] = "checkSupertypeList";
                break;
            case 57:
                objArr[2] = "resolveAnonymousInitializers";
                break;
            case 58:
            case 59:
            case 60:
                objArr[2] = "resolveAnonymousInitializer";
                break;
            case 61:
            case 62:
                objArr[2] = "processModifiersOnInitializer";
                break;
            case 63:
                objArr[2] = "resolvePrimaryConstructorParameters";
                break;
            case 64:
            case 65:
            case 66:
                objArr[2] = "resolveProperty";
                break;
            case 67:
                objArr[2] = "resolvePropertyDeclarationBodies";
                break;
            case 68:
            case 69:
            case 70:
                objArr[2] = "makeScopeForPropertyAccessor";
                break;
            case 71:
            case 72:
            case 73:
                objArr[2] = "resolvePropertyAccessors";
                break;
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "resolvePropertyDelegate";
                break;
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
                objArr[2] = "resolvePropertyInitializer";
                break;
            case 84:
            case 85:
                objArr[2] = "getScopeForProperty";
                break;
            case 87:
                objArr[2] = "resolveFunctionBodies";
                break;
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
                objArr[2] = "resolveFunctionBody";
                break;
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
                objArr[2] = "resolveConstructorParameterDefaultValues";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 46 && i != 50 && i != 86) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public BodyResolver(Project project, AnnotationResolver annotationResolver, BodyResolveCache bodyResolveCache, CallResolver callResolver, ControlFlowAnalyzer controlFlowAnalyzer, DeclarationsChecker declarationsChecker, DelegatedPropertyResolver delegatedPropertyResolver, ExpressionTypingServices expressionTypingServices, AnalyzerExtensions analyzerExtensions, BindingTrace bindingTrace, ValueParameterResolver valueParameterResolver, AnnotationChecker annotationChecker, KotlinBuiltIns kotlinBuiltIns, OverloadChecker overloadChecker, LanguageVersionSettings languageVersionSettings) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (annotationResolver == null) {
            $$$reportNull$$$0(1);
        }
        if (bodyResolveCache == null) {
            $$$reportNull$$$0(2);
        }
        if (callResolver == null) {
            $$$reportNull$$$0(3);
        }
        if (controlFlowAnalyzer == null) {
            $$$reportNull$$$0(4);
        }
        if (declarationsChecker == null) {
            $$$reportNull$$$0(5);
        }
        if (delegatedPropertyResolver == null) {
            $$$reportNull$$$0(6);
        }
        if (expressionTypingServices == null) {
            $$$reportNull$$$0(7);
        }
        if (analyzerExtensions == null) {
            $$$reportNull$$$0(8);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(9);
        }
        if (valueParameterResolver == null) {
            $$$reportNull$$$0(10);
        }
        if (annotationChecker == null) {
            $$$reportNull$$$0(11);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(12);
        }
        if (overloadChecker == null) {
            $$$reportNull$$$0(13);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(14);
        }
        this.project = project;
        this.annotationResolver = annotationResolver;
        this.bodyResolveCache = bodyResolveCache;
        this.callResolver = callResolver;
        this.controlFlowAnalyzer = controlFlowAnalyzer;
        this.declarationsChecker = declarationsChecker;
        this.delegatedPropertyResolver = delegatedPropertyResolver;
        this.expressionTypingServices = expressionTypingServices;
        this.analyzerExtensions = analyzerExtensions;
        this.annotationChecker = annotationChecker;
        this.overloadChecker = overloadChecker;
        this.trace = new ObservableBindingTrace(bindingTrace);
        this.valueParameterResolver = valueParameterResolver;
        this.builtIns = kotlinBuiltIns;
        this.languageVersionSettings = languageVersionSettings;
    }

    private void resolveBehaviorDeclarationBodies(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(15);
        }
        resolveSuperTypeEntryLists(bodiesResolveContext);
        resolvePropertyDeclarationBodies(bodiesResolveContext);
        resolveAnonymousInitializers(bodiesResolveContext);
        resolvePrimaryConstructorParameters(bodiesResolveContext);
        resolveSecondaryConstructors(bodiesResolveContext);
        resolveFunctionBodies(bodiesResolveContext);
        if (bodiesResolveContext.getTopDownAnalysisMode().getIsLocalDeclarations()) {
            return;
        }
        computeDeferredTypes();
    }

    private void resolveSecondaryConstructors(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(16);
        }
        for (Map.Entry<KtSecondaryConstructor, ClassConstructorDescriptor> entry : bodiesResolveContext.getSecondaryConstructors().entrySet()) {
            resolveSecondaryConstructorBody(bodiesResolveContext.getOuterDataFlowInfo(), this.trace, entry.getKey(), entry.getValue(), bodiesResolveContext.getDeclaringScope(entry.getKey()), bodiesResolveContext.getLocalContext());
        }
        if (bodiesResolveContext.getSecondaryConstructors().isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        Iterator<Map.Entry<KtSecondaryConstructor, ClassConstructorDescriptor>> it = bodiesResolveContext.getSecondaryConstructors().entrySet().iterator();
        while (it.hasNext()) {
            checkCyclicConstructorDelegationCall(it.next().getValue(), hashSet);
        }
    }

    public void resolveSecondaryConstructorBody(final DataFlowInfo dataFlowInfo, final BindingTrace bindingTrace, final KtSecondaryConstructor ktSecondaryConstructor, final ClassConstructorDescriptor classConstructorDescriptor, LexicalScope lexicalScope, final ExpressionTypingContext expressionTypingContext) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(17);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(18);
        }
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(19);
        }
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(21);
        }
        ForceResolveUtil.forceResolveAllContents(classConstructorDescriptor.getAnnotations());
        resolveFunctionBody(dataFlowInfo, bindingTrace, ktSecondaryConstructor, classConstructorDescriptor, lexicalScope, new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7412x30a48f54(dataFlowInfo, bindingTrace, ktSecondaryConstructor, classConstructorDescriptor, expressionTypingContext, (LexicalScope) obj);
            }
        }, new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BodyResolver.lambda$resolveSecondaryConstructorBody$1(classConstructorDescriptor, (LexicalScope) obj);
            }
        }, expressionTypingContext);
    }

    /* JADX INFO: renamed from: lambda$resolveSecondaryConstructorBody$0$org-jetbrains-kotlin-resolve-BodyResolver, reason: not valid java name */
    /* synthetic */ DataFlowInfo m7412x30a48f54(DataFlowInfo dataFlowInfo, BindingTrace bindingTrace, KtSecondaryConstructor ktSecondaryConstructor, ClassConstructorDescriptor classConstructorDescriptor, ExpressionTypingContext expressionTypingContext, LexicalScope lexicalScope) {
        return resolveSecondaryConstructorDelegationCall(dataFlowInfo, bindingTrace, lexicalScope, ktSecondaryConstructor, classConstructorDescriptor, expressionTypingContext != null ? expressionTypingContext.inferenceSession : null);
    }

    static /* synthetic */ LexicalScope lambda$resolveSecondaryConstructorBody$1(ClassConstructorDescriptor classConstructorDescriptor, LexicalScope lexicalScope) {
        return new LexicalScopeImpl(lexicalScope, classConstructorDescriptor, lexicalScope.getIsOwnerDescriptorAccessibleByLabel(), lexicalScope.getImplicitReceiver(), lexicalScope.getContextReceiversGroup(), LexicalScopeKind.CONSTRUCTOR_HEADER);
    }

    private DataFlowInfo resolveSecondaryConstructorDelegationCall(DataFlowInfo dataFlowInfo, BindingTrace bindingTrace, LexicalScope lexicalScope, KtSecondaryConstructor ktSecondaryConstructor, ClassConstructorDescriptor classConstructorDescriptor, InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(22);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(23);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(24);
        }
        if (ktSecondaryConstructor == null) {
            $$$reportNull$$$0(25);
        }
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        if (classConstructorDescriptor.get$isExpect() || DescriptorUtilsKt.isEffectivelyExternal(classConstructorDescriptor)) {
            return DataFlowInfo.INSTANCE.getEMPTY();
        }
        OverloadResolutionResults<ConstructorDescriptor> overloadResolutionResultsResolveConstructorDelegationCall = this.callResolver.resolveConstructorDelegationCall(bindingTrace, lexicalScope, dataFlowInfo, classConstructorDescriptor, ktSecondaryConstructor.getDelegationCall(), inferenceSession);
        if (overloadResolutionResultsResolveConstructorDelegationCall == null || !overloadResolutionResultsResolveConstructorDelegationCall.isSingleResult()) {
            return null;
        }
        ResolvedCall<D> resolvedCallMo7436getResultingCall = overloadResolutionResultsResolveConstructorDelegationCall.mo7436getResultingCall();
        recordConstructorDelegationCall(bindingTrace, classConstructorDescriptor, resolvedCallMo7436getResultingCall);
        return resolvedCallMo7436getResultingCall.getDataFlowInfoForArguments().getResultInfo();
    }

    private void checkCyclicConstructorDelegationCall(ConstructorDescriptor constructorDescriptor, Set<ConstructorDescriptor> set) {
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        if (set == null) {
            $$$reportNull$$$0(28);
        }
        if (set.contains(constructorDescriptor)) {
            return;
        }
        HashSet hashSet = new HashSet();
        ConstructorDescriptor delegatedConstructor = constructorDescriptor;
        do {
            ProgressManager.checkCanceled();
            hashSet.add(delegatedConstructor);
            delegatedConstructor = getDelegatedConstructor(delegatedConstructor);
            if (delegatedConstructor != null && constructorDescriptor.getContainingModule().equals(delegatedConstructor.getContainingModule()) && !delegatedConstructor.isPrimary() && !set.contains(delegatedConstructor)) {
            }
            set.addAll(hashSet);
        } while (!hashSet.contains(delegatedConstructor));
        reportEachConstructorOnCycle(delegatedConstructor);
        set.addAll(hashSet);
    }

    private void reportEachConstructorOnCycle(ConstructorDescriptor constructorDescriptor) {
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        ConstructorDescriptor delegatedConstructor = constructorDescriptor;
        do {
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(delegatedConstructor);
            if (psiElementDescriptorToDeclaration != null) {
                this.trace.report(Errors.CYCLIC_CONSTRUCTOR_DELEGATION_CALL.on(((KtSecondaryConstructor) psiElementDescriptorToDeclaration).getDelegationCall().getCalleeExpression()));
            }
            delegatedConstructor = getDelegatedConstructor(delegatedConstructor);
        } while (constructorDescriptor != delegatedConstructor);
    }

    private ConstructorDescriptor getDelegatedConstructor(ConstructorDescriptor constructorDescriptor) {
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        ResolvedCall resolvedCall = (ResolvedCall) this.trace.get(BindingContext.CONSTRUCTOR_RESOLVED_DELEGATION_CALL, constructorDescriptor);
        if (resolvedCall == null || !resolvedCall.getStatus().isSuccess()) {
            return null;
        }
        return ((ConstructorDescriptor) resolvedCall.getResultingDescriptor()).getOriginal();
    }

    public void resolveBodies(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(31);
        }
        resolveBehaviorDeclarationBodies(bodiesResolveContext);
        this.controlFlowAnalyzer.process(bodiesResolveContext);
        this.declarationsChecker.process(bodiesResolveContext);
        this.analyzerExtensions.process(bodiesResolveContext);
    }

    private void resolveSuperTypeEntryLists(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(32);
        }
        for (Map.Entry<KtClassOrObject, ClassDescriptorWithResolutionScopes> entry : bodiesResolveContext.getDeclaredClasses().entrySet()) {
            KtClassOrObject key = entry.getKey();
            ClassDescriptorWithResolutionScopes value = entry.getValue();
            ExpressionTypingContext localContext = bodiesResolveContext.getLocalContext();
            resolveSuperTypeEntryList(bodiesResolveContext.getOuterDataFlowInfo(), key, value, value.mo6347getUnsubstitutedPrimaryConstructor(), value.getScopeForConstructorHeaderResolution(), value.getScopeForMemberDeclarationResolution(), localContext != null ? localContext.inferenceSession : null);
        }
    }

    public void resolveSuperTypeEntryList(final DataFlowInfo dataFlowInfo, KtClassOrObject ktClassOrObject, final ClassDescriptor classDescriptor, final ConstructorDescriptor constructorDescriptor, LexicalScope lexicalScope, final LexicalScope lexicalScope2, final InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(33);
        }
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(34);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(35);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(36);
        }
        if (lexicalScope2 == null) {
            $$$reportNull$$$0(37);
        }
        ProgressManager.checkCanceled();
        final LexicalScope functionInnerScope = constructorDescriptor == null ? null : FunctionDescriptorUtil.getFunctionInnerScope(lexicalScope, constructorDescriptor, this.trace, this.overloadChecker);
        if (constructorDescriptor == null) {
            checkRedeclarationsInClassHeaderWithoutPrimaryConstructor(classDescriptor, lexicalScope);
        }
        final ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
        final LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        final ResolvedCall<?>[] resolvedCallArr = new ResolvedCall[1];
        KtVisitorVoid ktVisitorVoid = new KtVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.BodyResolver.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "call";
                } else if (i != 3) {
                    objArr[0] = "specifier";
                } else {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                }
                objArr[1] = "org/jetbrains/kotlin/resolve/BodyResolver$1";
                if (i == 1) {
                    objArr[2] = "visitSuperTypeCallEntry";
                } else if (i == 2) {
                    objArr[2] = "visitSuperTypeEntry";
                } else if (i != 3) {
                    objArr[2] = "visitDelegatedSuperTypeEntry";
                } else {
                    objArr[2] = "visitKtElement";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            private void recordSupertype(KtTypeReference ktTypeReference, KotlinType kotlinType) {
                if (kotlinType == null) {
                    return;
                }
                linkedHashMapNewLinkedHashMap.put(ktTypeReference, kotlinType);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry) {
                if (ktDelegatedSuperTypeEntry == null) {
                    $$$reportNull$$$0(0);
                }
                if (classDescriptor.getClassKind() == ClassKind.INTERFACE) {
                    BodyResolver.this.trace.report(Errors.DELEGATION_IN_INTERFACE.on(ktDelegatedSuperTypeEntry));
                }
                KotlinType kotlinType = (KotlinType) BodyResolver.this.trace.getContext().get(BindingContext.TYPE, ktDelegatedSuperTypeEntry.getTypeReference());
                recordSupertype(ktDelegatedSuperTypeEntry.getTypeReference(), kotlinType);
                if (kotlinType != null) {
                    ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                    if ((this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).getClassKind() != ClassKind.INTERFACE) {
                        BodyResolver.this.trace.report(Errors.DELEGATION_NOT_TO_INTERFACE.on(ktDelegatedSuperTypeEntry.getTypeReference()));
                    }
                }
                KtExpression delegateExpression = ktDelegatedSuperTypeEntry.getDelegateExpression();
                if (delegateExpression != null) {
                    LexicalScope lexicalScope3 = functionInnerScope;
                    if (lexicalScope3 == null) {
                        lexicalScope3 = lexicalScope2;
                    }
                    LexicalScope lexicalScope4 = lexicalScope3;
                    if (kotlinType == null) {
                        kotlinType = TypeUtils.NO_EXPECTED_TYPE;
                    }
                    KotlinType kotlinType2 = kotlinType;
                    ExpressionTypingServices expressionTypingServices2 = expressionTypingServices;
                    DataFlowInfo dataFlowInfo2 = dataFlowInfo;
                    InferenceSession inferenceSession2 = inferenceSession;
                    if (inferenceSession2 == null) {
                        inferenceSession2 = InferenceSession.INSTANCE.getDefault();
                    }
                    expressionTypingServices2.getType(lexicalScope4, delegateExpression, kotlinType2, dataFlowInfo2, inferenceSession2, BodyResolver.this.trace);
                }
                if (classDescriptor.get$isExpect()) {
                    BodyResolver.this.trace.report(Errors.IMPLEMENTATION_BY_DELEGATION_IN_EXPECT_CLASS.on(ktDelegatedSuperTypeEntry));
                } else if (constructorDescriptor == null) {
                    BodyResolver.this.trace.report(Errors.UNSUPPORTED.on(ktDelegatedSuperTypeEntry, "Delegation without primary constructor is not supported"));
                }
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSuperTypeCallEntry(KtSuperTypeCallEntry ktSuperTypeCallEntry) {
                if (ktSuperTypeCallEntry == null) {
                    $$$reportNull$$$0(1);
                }
                KtElementImplStub valueArgumentList = ktSuperTypeCallEntry.getValueArgumentList();
                if (valueArgumentList == null) {
                    valueArgumentList = ktSuperTypeCallEntry;
                }
                if (classDescriptor.getClassKind() == ClassKind.INTERFACE) {
                    BodyResolver.this.trace.report(Errors.SUPERTYPE_INITIALIZED_IN_INTERFACE.on(valueArgumentList));
                }
                if (classDescriptor.get$isExpect()) {
                    BodyResolver.this.trace.report(Errors.SUPERTYPE_INITIALIZED_IN_EXPECTED_CLASS.on(valueArgumentList));
                }
                KtTypeReference typeReference = ktSuperTypeCallEntry.getTypeReference();
                if (typeReference == null) {
                    return;
                }
                if (constructorDescriptor == null) {
                    if (classDescriptor.getClassKind() != ClassKind.INTERFACE) {
                        BodyResolver.this.trace.report(Errors.SUPERTYPE_INITIALIZED_WITHOUT_PRIMARY_CONSTRUCTOR.on(ktSuperTypeCallEntry));
                    }
                    recordSupertype(typeReference, (KotlinType) BodyResolver.this.trace.getContext().get(BindingContext.TYPE, typeReference));
                    return;
                }
                OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFunctionCall = BodyResolver.this.callResolver.resolveFunctionCall(BodyResolver.this.trace, functionInnerScope, CallMaker.makeConstructorCallWithoutTypeArguments(ktSuperTypeCallEntry), TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, false, inferenceSession);
                if (!overloadResolutionResultsResolveFunctionCall.isSingleResult()) {
                    recordSupertype(typeReference, (KotlinType) BodyResolver.this.trace.getContext().get(BindingContext.TYPE, typeReference));
                    return;
                }
                KotlinType returnType = ((FunctionDescriptor) overloadResolutionResultsResolveFunctionCall.mo7437getResultingDescriptor()).getReturnType();
                recordSupertype(typeReference, returnType);
                if (TypeUtils.getClassDescriptor(returnType) != null) {
                    ResolvedCall[] resolvedCallArr2 = resolvedCallArr;
                    if (resolvedCallArr2[0] == null) {
                        resolvedCallArr2[0] = overloadResolutionResultsResolveFunctionCall.mo7436getResultingCall();
                    } else {
                        resolvedCallArr2[0] = null;
                    }
                }
                BodyResolver.this.trace.record(BindingContext.PROCESSED, ktSuperTypeCallEntry.getCalleeExpression(), true);
                BodyResolver.this.trace.record(BindingContext.EXPRESSION_TYPE_INFO, ktSuperTypeCallEntry.getCalleeExpression(), TypeInfoFactoryKt.noTypeInfo(overloadResolutionResultsResolveFunctionCall.mo7436getResultingCall().getDataFlowInfoForArguments().getResultInfo()));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSuperTypeEntry(KtSuperTypeEntry ktSuperTypeEntry) {
                ClassDescriptor classDescriptor2;
                if (ktSuperTypeEntry == null) {
                    $$$reportNull$$$0(2);
                }
                KtTypeReference typeReference = ktSuperTypeEntry.getTypeReference();
                KotlinType kotlinType = (KotlinType) BodyResolver.this.trace.getContext().get(BindingContext.TYPE, typeReference);
                recordSupertype(typeReference, kotlinType);
                if (kotlinType == null || (classDescriptor2 = TypeUtils.getClassDescriptor(kotlinType)) == null || classDescriptor2.getClassKind().isSingleton() || classDescriptor.getClassKind() == ClassKind.INTERFACE || classDescriptor.mo6347getUnsubstitutedPrimaryConstructor() == null || classDescriptor2.getClassKind() == ClassKind.INTERFACE || classDescriptor.get$isExpect() || DescriptorUtilsKt.isEffectivelyExternal(classDescriptor) || ErrorUtils.isError(classDescriptor2)) {
                    return;
                }
                BodyResolver.this.trace.report(Errors.SUPERTYPE_NOT_INITIALIZED.on(ktSuperTypeEntry));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement ktElement) {
                if (ktElement == null) {
                    $$$reportNull$$$0(3);
                }
                throw new UnsupportedOperationException(ktElement.getText() + " : " + ktElement);
            }
        };
        if ((ktClassOrObject instanceof KtEnumEntry) && DescriptorUtils.isEnumEntry(classDescriptor) && ktClassOrObject.getSuperTypeListEntries().isEmpty()) {
            resolveConstructorCallForEnumEntryWithoutInitializer((KtEnumEntry) ktClassOrObject, classDescriptor, functionInnerScope, dataFlowInfo, resolvedCallArr, inferenceSession);
        }
        for (KtSuperTypeListEntry ktSuperTypeListEntry : ktClassOrObject.getSuperTypeListEntries()) {
            ProgressManager.checkCanceled();
            ktSuperTypeListEntry.accept(ktVisitorVoid);
        }
        if (DescriptorUtils.isAnnotationClass(classDescriptor) && ktClassOrObject.getSuperTypeList() != null) {
            this.trace.report(Errors.SUPERTYPES_FOR_ANNOTATION_CLASS.on(ktClassOrObject.getSuperTypeList()));
        }
        ResolvedCall<?> resolvedCall = resolvedCallArr[0];
        if (resolvedCall != null && constructorDescriptor != null) {
            recordConstructorDelegationCall(this.trace, constructorDescriptor, resolvedCall);
        }
        checkSupertypeList(classDescriptor, linkedHashMapNewLinkedHashMap, ktClassOrObject);
    }

    private void checkRedeclarationsInClassHeaderWithoutPrimaryConstructor(final ClassDescriptor classDescriptor, LexicalScope lexicalScope) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(38);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(39);
        }
        new LexicalScopeImpl(lexicalScope, classDescriptor, true, null, Collections.emptyList(), LexicalScopeKind.CLASS_HEADER, new TraceBasedLocalRedeclarationChecker(this.trace, this.overloadChecker), new Function1<LexicalScopeImpl.InitializeHandler, Unit>() { // from class: org.jetbrains.kotlin.resolve.BodyResolver.2
            @Override // kotlin.jvm.functions.Function1
            public Unit invoke(LexicalScopeImpl.InitializeHandler initializeHandler) {
                Iterator<TypeParameterDescriptor> it = classDescriptor.getDeclaredTypeParameters().iterator();
                while (it.hasNext()) {
                    initializeHandler.addClassifierDescriptor(it.next());
                }
                return Unit.INSTANCE;
            }
        });
    }

    private void resolveConstructorCallForEnumEntryWithoutInitializer(KtEnumEntry ktEnumEntry, ClassDescriptor classDescriptor, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, ResolvedCall<?>[] resolvedCallArr, InferenceSession inferenceSession) {
        if (ktEnumEntry == null) {
            $$$reportNull$$$0(40);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(41);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(42);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(43);
        }
        if (resolvedCallArr == null) {
            $$$reportNull$$$0(44);
        }
        ClassDescriptor classDescriptor2 = (ClassDescriptor) classDescriptor.getContainingModule();
        if (classDescriptor2.getClassKind() == ClassKind.ENUM_CLASS && !classDescriptor2.get$isExpect()) {
            if (getConstructorForEmptyArgumentsList(classDescriptor2).size() != 1) {
                this.trace.report(Errors.ENUM_ENTRY_SHOULD_BE_INITIALIZED.on(ktEnumEntry));
                return;
            }
            KtSuperTypeCallEntry ktSuperTypeCallEntry = (KtSuperTypeCallEntry) new KtPsiFactory(this.project, false).createEnumEntryInitializerList().getInitializers().get(0);
            Call callMakeConstructorCallWithoutTypeArguments = CallMaker.makeConstructorCallWithoutTypeArguments(ktSuperTypeCallEntry);
            this.trace.record(BindingContext.TYPE, ktSuperTypeCallEntry.getTypeReference(), classDescriptor2.getDefaultType());
            this.trace.record(BindingContext.CALL, ktEnumEntry, callMakeConstructorCallWithoutTypeArguments);
            OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFunctionCall = this.callResolver.resolveFunctionCall(this.trace, lexicalScope, callMakeConstructorCallWithoutTypeArguments, TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, false, inferenceSession);
            if (resolvedCallArr[0] == null) {
                resolvedCallArr[0] = overloadResolutionResultsResolveFunctionCall.mo7436getResultingCall();
            }
        }
    }

    private static List<ClassConstructorDescriptor> getConstructorForEmptyArgumentsList(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(45);
        }
        List<ClassConstructorDescriptor> listFilter = CollectionsKt.filter(classDescriptor.getConstructors(), new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(CollectionsKt.all(((ClassConstructorDescriptor) obj).getValueParameters(), new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj2;
                        return Boolean.valueOf(valueParameterDescriptor.declaresDefaultValue() || valueParameterDescriptor.getVarargElementType() != null);
                    }
                }));
            }
        });
        if (listFilter == null) {
            $$$reportNull$$$0(46);
        }
        return listFilter;
    }

    private Set<TypeConstructor> getAllowedFinalSupertypes(ClassDescriptor classDescriptor, Map<KtTypeReference, KotlinType> map, KtClassOrObject ktClassOrObject) {
        Set<TypeConstructor> setSingleton;
        if (classDescriptor == null) {
            $$$reportNull$$$0(47);
        }
        if (map == null) {
            $$$reportNull$$$0(48);
        }
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(49);
        }
        Set<TypeConstructor> setEmptySet = Collections.emptySet();
        if (ktClassOrObject instanceof KtEnumEntry) {
            setSingleton = Collections.singleton(((ClassDescriptor) classDescriptor.getContainingModule()).getTypeConstructor());
        } else {
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.TopLevelSealedInheritance) && DescriptorUtils.isTopLevelDeclaration(classDescriptor)) {
                Iterator<KotlinType> it = map.values().iterator();
                while (it.hasNext()) {
                    ClassifierDescriptor this$0 = it.next().getConstructor().getThis$0();
                    if (DescriptorUtils.isSealedClass(this$0) && DescriptorUtils.isTopLevelDeclaration(this$0)) {
                        setEmptySet = Collections.singleton(this$0.getTypeConstructor());
                    }
                }
            } else {
                while (classDescriptor.getContainingModule() instanceof ClassDescriptor) {
                    classDescriptor = (ClassDescriptor) classDescriptor.getContainingModule();
                    if (DescriptorUtils.isSealedClass(classDescriptor)) {
                        if (setEmptySet.isEmpty()) {
                            setEmptySet = new HashSet();
                        }
                        setEmptySet.add(classDescriptor.getTypeConstructor());
                        if (classDescriptor.get$isExpect()) {
                            for (MemberDescriptor memberDescriptor : ExpectedActualResolverKt.findCompatibleActualsForExpected(classDescriptor, DescriptorUtilsKt.getModule(classDescriptor))) {
                                if (memberDescriptor instanceof TypeAliasDescriptor) {
                                    setEmptySet.add(((TypeAliasDescriptor) memberDescriptor).getExpandedType().getConstructor());
                                }
                            }
                        }
                    }
                }
            }
            setSingleton = setEmptySet;
        }
        if (setSingleton == null) {
            $$$reportNull$$$0(50);
        }
        return setSingleton;
    }

    private static void recordConstructorDelegationCall(BindingTrace bindingTrace, ConstructorDescriptor constructorDescriptor, ResolvedCall<?> resolvedCall) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(51);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(52);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(53);
        }
        bindingTrace.record(BindingContext.CONSTRUCTOR_RESOLVED_DELEGATION_CALL, constructorDescriptor, resolvedCall);
    }

    /* JADX WARN: Code duplicated, block: B:76:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:77:0x01b6  */
    private void checkSupertypeList(ClassDescriptor classDescriptor, Map<KtTypeReference, KotlinType> map, KtClassOrObject ktClassOrObject) {
        String str;
        boolean z;
        if (classDescriptor == null) {
            $$$reportNull$$$0(54);
        }
        if (map == null) {
            $$$reportNull$$$0(55);
        }
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(56);
        }
        Set<TypeConstructor> allowedFinalSupertypes = getAllowedFinalSupertypes(classDescriptor, map, ktClassOrObject);
        HashSet hashSet = new HashSet();
        boolean z2 = false;
        for (Map.Entry<KtTypeReference, KotlinType> entry : map.entrySet()) {
            KtTypeReference key = entry.getKey();
            KotlinType value = entry.getValue();
            KtTypeElement typeElement = key.getTypeElement();
            if (typeElement instanceof KtFunctionType) {
                Iterator<KtParameter> it = ((KtFunctionType) typeElement).getParameters().iterator();
                while (it.hasNext()) {
                    PsiElement psiElementMo7392getNameIdentifier = it.next().mo7392getNameIdentifier();
                    if (psiElementMo7392getNameIdentifier != null) {
                        this.trace.report(Errors.UNSUPPORTED.on(psiElementMo7392getNameIdentifier, "named parameter in function type in supertype position"));
                    }
                }
            }
            ClassDescriptor classDescriptor2 = TypeUtils.getClassDescriptor(value);
            boolean z3 = true;
            if (classDescriptor2 != null) {
                if (ErrorUtils.isError(classDescriptor2)) {
                    continue;
                } else {
                    if (FunctionTypesKt.isExtensionFunctionType(value) && !this.languageVersionSettings.supportsFeature(LanguageFeature.FunctionalTypeWithExtensionAsSupertype)) {
                        this.trace.report(Errors.SUPERTYPE_IS_EXTENSION_FUNCTION_TYPE.on(key));
                    } else if (FunctionTypesKt.isSuspendExtensionFunctionType(value) && !this.languageVersionSettings.supportsFeature(LanguageFeature.FunctionalTypeWithExtensionAsSupertype) && this.languageVersionSettings.supportsFeature(LanguageFeature.SuspendFunctionAsSupertype)) {
                        this.trace.report(Errors.SUPERTYPE_IS_SUSPEND_EXTENSION_FUNCTION_TYPE.on(key));
                    } else if (FunctionTypesKt.isSuspendFunctionType(value) && !this.languageVersionSettings.supportsFeature(LanguageFeature.SuspendFunctionAsSupertype)) {
                        this.trace.report(Errors.SUPERTYPE_IS_SUSPEND_FUNCTION_TYPE.on(key));
                    } else if (FunctionTypesKt.isKSuspendFunctionType(value) && !this.languageVersionSettings.supportsFeature(LanguageFeature.SuspendFunctionAsSupertype)) {
                        this.trace.report(Errors.SUPERTYPE_IS_KSUSPEND_FUNCTION_TYPE.on(key));
                    }
                    if (classDescriptor2.getClassKind() != ClassKind.INTERFACE) {
                        if (classDescriptor.getClassKind() == ClassKind.ENUM_CLASS) {
                            this.trace.report(Errors.CLASS_IN_SUPERTYPE_FOR_ENUM.on(key));
                        } else if (classDescriptor.getClassKind() == ClassKind.INTERFACE && !z2 && !DynamicTypesKt.isDynamic(value)) {
                            this.trace.report(Errors.INTERFACE_WITH_SUPERCLASS.on(key));
                        } else if (ktClassOrObject.hasModifier(KtTokens.DATA_KEYWORD) && !this.languageVersionSettings.supportsFeature(LanguageFeature.DataClassInheritance)) {
                            this.trace.report(Errors.DATA_CLASS_CANNOT_HAVE_CLASS_SUPERTYPES.on(key));
                        } else {
                            if (DescriptorUtils.isSubclass(classDescriptor2, this.builtIns.getThrowable())) {
                                if (!classDescriptor.getDeclaredTypeParameters().isEmpty()) {
                                    this.trace.report(Errors.GENERIC_THROWABLE_SUBCLASS.on(ktClassOrObject.mo7395getTypeParameterList()));
                                } else if (!classDescriptor.getTypeConstructor().getParameters().isEmpty()) {
                                    if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitInnerClassesOfGenericClassExtendingThrowable)) {
                                        this.trace.report(Errors.INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS.on(ktClassOrObject));
                                    } else {
                                        this.trace.report(Errors.INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS_WARNING.on(ktClassOrObject));
                                    }
                                }
                            }
                            z = true;
                            if (z2) {
                                this.trace.report(Errors.MANY_CLASSES_IN_SUPERTYPE_LIST.on(key));
                            } else {
                                z2 = true;
                            }
                            z3 = z;
                        }
                        z = false;
                        if (z2) {
                            this.trace.report(Errors.MANY_CLASSES_IN_SUPERTYPE_LIST.on(key));
                        } else {
                            z2 = true;
                        }
                        z3 = z;
                    }
                }
            } else {
                this.trace.report(Errors.SUPERTYPE_NOT_A_CLASS_OR_INTERFACE.on(key));
            }
            TypeConstructor constructor = value.getConstructor();
            if (z3 && !hashSet.add(constructor)) {
                this.trace.report(Errors.SUPERTYPE_APPEARS_TWICE.on(key));
            }
            if (classDescriptor2 == null) {
                return;
            }
            if (classDescriptor2.getClassKind().isSingleton()) {
                if (!DescriptorUtils.isEnumEntry(classDescriptor2)) {
                    this.trace.report(Errors.SINGLETON_IN_SUPERTYPE.on(key));
                }
            } else if (!allowedFinalSupertypes.contains(constructor)) {
                if (DescriptorUtils.isSealedClass(classDescriptor2)) {
                    DeclarationDescriptor declarationDescriptor = classDescriptor.getContainingModule();
                    while (declarationDescriptor != null && declarationDescriptor != classDescriptor2) {
                        declarationDescriptor = declarationDescriptor.getContainingModule();
                    }
                    if (declarationDescriptor == null) {
                        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage) || DescriptorUtils.isLocal(classDescriptor)) {
                            this.trace.report(Errors.SEALED_SUPERTYPE.on(key));
                        }
                    } else {
                        if (classDescriptor.getShortName() == SpecialNames.NO_NAME_PROVIDED) {
                            str = "Anonymous object";
                        } else {
                            str = "Local class";
                        }
                        this.trace.report(Errors.SEALED_SUPERTYPE_IN_LOCAL_CLASS.on(key, str, classDescriptor2.getClassKind()));
                    }
                } else if (ModalityUtilsKt.isFinalOrEnum(classDescriptor2)) {
                    this.trace.report(Errors.FINAL_SUPERTYPE.on(key));
                } else if (KotlinBuiltIns.isEnum(classDescriptor2)) {
                    this.trace.report(Errors.CLASS_CANNOT_BE_EXTENDED_DIRECTLY.on(key, classDescriptor2));
                }
            }
        }
    }

    private void resolveAnonymousInitializers(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(57);
        }
        for (Map.Entry<KtAnonymousInitializer, ClassDescriptorWithResolutionScopes> entry : bodiesResolveContext.getAnonymousInitializers().entrySet()) {
            KtAnonymousInitializer key = entry.getKey();
            ClassDescriptorWithResolutionScopes value = entry.getValue();
            ExpressionTypingContext localContext = bodiesResolveContext.getLocalContext();
            resolveAnonymousInitializer(bodiesResolveContext.getOuterDataFlowInfo(), key, value, localContext != null ? localContext.inferenceSession : null);
        }
    }

    public void resolveAnonymousInitializer(DataFlowInfo dataFlowInfo, KtAnonymousInitializer ktAnonymousInitializer, ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes, InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(58);
        }
        if (ktAnonymousInitializer == null) {
            $$$reportNull$$$0(59);
        }
        if (classDescriptorWithResolutionScopes == null) {
            $$$reportNull$$$0(60);
        }
        ProgressManager.checkCanceled();
        LexicalScope scopeForInitializerResolution = classDescriptorWithResolutionScopes.getScopeForInitializerResolution();
        KtExpression body = ktAnonymousInitializer.getBody();
        if (body != null) {
            PreliminaryDeclarationVisitor.INSTANCE.createForDeclaration((KtDeclaration) ktAnonymousInitializer.getLightParent().getLightParent(), this.trace, this.languageVersionSettings);
            ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
            SimpleType simpleType = TypeUtils.NO_EXPECTED_TYPE;
            if (inferenceSession == null) {
                inferenceSession = InferenceSession.INSTANCE.getDefault();
            }
            expressionTypingServices.getTypeInfo(scopeForInitializerResolution, body, simpleType, dataFlowInfo, inferenceSession, this.trace, true);
        }
        processModifiersOnInitializer(ktAnonymousInitializer, scopeForInitializerResolution);
        if (classDescriptorWithResolutionScopes.getConstructors().isEmpty()) {
            this.trace.report(Errors.ANONYMOUS_INITIALIZER_IN_INTERFACE.on(ktAnonymousInitializer));
        }
        if (classDescriptorWithResolutionScopes.get$isExpect()) {
            this.trace.report(Errors.EXPECTED_DECLARATION_WITH_BODY.on(ktAnonymousInitializer));
        }
    }

    private void processModifiersOnInitializer(KtModifierListOwner ktModifierListOwner, LexicalScope lexicalScope) {
        if (ktModifierListOwner == null) {
            $$$reportNull$$$0(61);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(62);
        }
        this.annotationChecker.check(ktModifierListOwner, this.trace, null);
        ModifierCheckerCore.INSTANCE.check(ktModifierListOwner, this.trace, null, this.languageVersionSettings);
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList == null) {
            return;
        }
        this.annotationResolver.resolveAnnotationsWithArguments(lexicalScope, modifierList, this.trace);
    }

    private void resolvePrimaryConstructorParameters(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(63);
        }
        for (Map.Entry<KtClassOrObject, ClassDescriptorWithResolutionScopes> entry : bodiesResolveContext.getDeclaredClasses().entrySet()) {
            KtClassOrObject key = entry.getKey();
            ClassDescriptorWithResolutionScopes value = entry.getValue();
            ClassConstructorDescriptor unsubstitutedPrimaryConstructor = value.mo6347getUnsubstitutedPrimaryConstructor();
            if (unsubstitutedPrimaryConstructor != null) {
                ForceResolveUtil.forceResolveAllContents(unsubstitutedPrimaryConstructor.getAnnotations());
                ExpressionTypingContext localContext = bodiesResolveContext.getLocalContext();
                this.valueParameterResolver.resolveValueParameters(key.getPrimaryConstructorParameters(), unsubstitutedPrimaryConstructor.getValueParameters(), getPrimaryConstructorParametersScope(value.getScopeForConstructorHeaderResolution(), unsubstitutedPrimaryConstructor), bodiesResolveContext.getOuterDataFlowInfo(), this.trace, localContext != null ? localContext.inferenceSession : null);
                resolveConstructorPropertyDescriptors(key);
            }
        }
    }

    private void resolveConstructorPropertyDescriptors(KtClassOrObject ktClassOrObject) {
        Iterator<KtParameter> it = ktClassOrObject.getPrimaryConstructorParameters().iterator();
        while (it.hasNext()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) this.trace.getContext().get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, it.next());
            if (propertyDescriptor != null) {
                ForceResolveUtil.forceResolveAllContents(propertyDescriptor.getAnnotations());
                if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets)) {
                    PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                    if (getter != null) {
                        ForceResolveUtil.forceResolveAllContents(getter.getAnnotations());
                    }
                    PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                    if (setter != null) {
                        ForceResolveUtil.forceResolveAllContents(setter.getAnnotations());
                    }
                }
            }
        }
    }

    private static LexicalScope getPrimaryConstructorParametersScope(LexicalScope lexicalScope, final ConstructorDescriptor constructorDescriptor) {
        return new LexicalScopeImpl(lexicalScope, constructorDescriptor, false, null, Collections.emptyList(), LexicalScopeKind.DEFAULT_VALUE, LocalRedeclarationChecker.DO_NOTHING.INSTANCE, new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BodyResolver.lambda$getPrimaryConstructorParametersScope$4(constructorDescriptor, (LexicalScopeImpl.InitializeHandler) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$getPrimaryConstructorParametersScope$4(ConstructorDescriptor constructorDescriptor, LexicalScopeImpl.InitializeHandler initializeHandler) {
        Iterator<ValueParameterDescriptor> it = constructorDescriptor.getValueParameters().iterator();
        while (it.hasNext()) {
            initializeHandler.addVariableDescriptor(it.next());
        }
        return Unit.INSTANCE;
    }

    public void resolveProperty(BodiesResolveContext bodiesResolveContext, KtProperty ktProperty, PropertyDescriptor propertyDescriptor) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(64);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(65);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(66);
        }
        computeDeferredType(propertyDescriptor.getReturnType());
        PreliminaryDeclarationVisitor.INSTANCE.createForDeclaration(ktProperty, this.trace, this.languageVersionSettings);
        KtExpression initializer = ktProperty.getInitializer();
        LexicalScope lexicalScopeMakeScopeForPropertyHeader = ScopeUtils.makeScopeForPropertyHeader(getScopeForProperty(bodiesResolveContext, ktProperty), propertyDescriptor);
        ExpressionTypingContext localContext = bodiesResolveContext.getLocalContext();
        if (initializer != null) {
            resolvePropertyInitializer(bodiesResolveContext.getOuterDataFlowInfo(), ktProperty, propertyDescriptor, initializer, lexicalScopeMakeScopeForPropertyHeader, localContext != null ? localContext.inferenceSession : null);
        }
        KtExpression delegateExpression = ktProperty.getDelegateExpression();
        if (delegateExpression != null) {
            resolvePropertyDelegate(bodiesResolveContext.getOuterDataFlowInfo(), ktProperty, propertyDescriptor, delegateExpression, lexicalScopeMakeScopeForPropertyHeader, localContext != null ? localContext.inferenceSession : null);
        }
        resolvePropertyAccessors(bodiesResolveContext, ktProperty, propertyDescriptor);
        ForceResolveUtil.forceResolveAllContents(propertyDescriptor.getAnnotations());
        FieldDescriptor backingField = propertyDescriptor.getBackingField();
        if (backingField != null) {
            ForceResolveUtil.forceResolveAllContents(backingField.getAnnotations());
        }
    }

    private void resolvePropertyDeclarationBodies(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(67);
        }
        HashSet hashSet = new HashSet();
        for (Map.Entry<KtClassOrObject, ClassDescriptorWithResolutionScopes> entry : bodiesResolveContext.getDeclaredClasses().entrySet()) {
            if (entry.getKey() instanceof KtClass) {
                KtClass ktClass = (KtClass) entry.getKey();
                entry.getValue();
                for (KtProperty ktProperty : ktClass.getProperties()) {
                    resolveProperty(bodiesResolveContext, ktProperty, bodiesResolveContext.getProperties().get(ktProperty));
                    hashSet.add(ktProperty);
                }
            }
        }
        for (Map.Entry<KtProperty, PropertyDescriptor> entry2 : bodiesResolveContext.getProperties().entrySet()) {
            KtProperty key = entry2.getKey();
            if (!hashSet.contains(key)) {
                resolveProperty(bodiesResolveContext, key, entry2.getValue());
            }
        }
    }

    private static LexicalScope makeScopeForPropertyAccessor(BodiesResolveContext bodiesResolveContext, KtPropertyAccessor ktPropertyAccessor, PropertyDescriptor propertyDescriptor) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(68);
        }
        if (ktPropertyAccessor == null) {
            $$$reportNull$$$0(69);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(70);
        }
        return new LexicalScopeImpl(ScopeUtils.makeScopeForPropertyHeader(bodiesResolveContext.getDeclaringScope(ktPropertyAccessor), propertyDescriptor), propertyDescriptor, true, propertyDescriptor.getExtensionReceiverParameter(), propertyDescriptor.getContextReceiverParameters(), LexicalScopeKind.PROPERTY_ACCESSOR_BODY);
    }

    private void resolvePropertyAccessors(BodiesResolveContext bodiesResolveContext, KtProperty ktProperty, PropertyDescriptor propertyDescriptor) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(71);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(72);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(73);
        }
        ObservableBindingTrace observableBindingTraceCreateFieldTrackingTrace = createFieldTrackingTrace(propertyDescriptor);
        KtPropertyAccessor getter = ktProperty.getGetter();
        PropertyGetterDescriptor getter2 = propertyDescriptor.getGetter();
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitErroneousExpressionsInAnnotationsWithUseSiteTargets);
        if (getter2 != null) {
            if (getter != null) {
                resolveFunctionBody(bodiesResolveContext.getOuterDataFlowInfo(), observableBindingTraceCreateFieldTrackingTrace, getter, getter2, makeScopeForPropertyAccessor(bodiesResolveContext, getter, propertyDescriptor), bodiesResolveContext.getLocalContext());
            }
            if (getter != null || zSupportsFeature) {
                ForceResolveUtil.forceResolveAllContents(getter2.getAnnotations());
            }
        }
        KtPropertyAccessor setter = ktProperty.getSetter();
        PropertySetterDescriptor setter2 = propertyDescriptor.getSetter();
        if (setter2 != null) {
            if (setter != null) {
                resolveFunctionBody(bodiesResolveContext.getOuterDataFlowInfo(), observableBindingTraceCreateFieldTrackingTrace, setter, setter2, makeScopeForPropertyAccessor(bodiesResolveContext, setter, propertyDescriptor), bodiesResolveContext.getLocalContext());
            }
            if (setter != null || zSupportsFeature) {
                ForceResolveUtil.forceResolveAllContents(setter2.getAnnotations());
            }
        }
    }

    private ObservableBindingTrace createFieldTrackingTrace(final PropertyDescriptor propertyDescriptor) {
        return new ObservableBindingTrace(this.trace).addHandler(BindingContext.REFERENCE_TARGET, new ObservableBindingTrace.RecordHandler() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.resolve.ObservableBindingTrace.RecordHandler
            public final void handleRecord(WritableSlice writableSlice, Object obj, Object obj2) {
                this.f$0.m7411x6a02a88f(propertyDescriptor, writableSlice, (KtReferenceExpression) obj, (DeclarationDescriptor) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createFieldTrackingTrace$5$org-jetbrains-kotlin-resolve-BodyResolver, reason: not valid java name */
    /* synthetic */ void m7411x6a02a88f(PropertyDescriptor propertyDescriptor, WritableSlice writableSlice, KtReferenceExpression ktReferenceExpression, DeclarationDescriptor declarationDescriptor) {
        if ((ktReferenceExpression instanceof KtSimpleNameExpression) && (declarationDescriptor instanceof SyntheticFieldDescriptor)) {
            this.trace.record(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
        }
    }

    private void resolvePropertyDelegate(DataFlowInfo dataFlowInfo, KtProperty ktProperty, PropertyDescriptor propertyDescriptor, KtExpression ktExpression, LexicalScope lexicalScope, InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(74);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(75);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(76);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(77);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(78);
        }
        DelegatedPropertyResolver delegatedPropertyResolver = this.delegatedPropertyResolver;
        if (inferenceSession == null) {
            inferenceSession = InferenceSession.INSTANCE.getDefault();
        }
        delegatedPropertyResolver.resolvePropertyDelegate(dataFlowInfo, ktProperty, propertyDescriptor, ktExpression, lexicalScope, inferenceSession, this.trace);
    }

    private void resolvePropertyInitializer(DataFlowInfo dataFlowInfo, KtProperty ktProperty, PropertyDescriptor propertyDescriptor, KtExpression ktExpression, LexicalScope lexicalScope, InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(79);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(80);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(81);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(82);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(83);
        }
        LexicalScope lexicalScopeMakeScopeForPropertyInitializer = ScopeUtils.makeScopeForPropertyInitializer(lexicalScope, propertyDescriptor);
        KotlinType type = ktProperty.mo7396getTypeReference() != null ? propertyDescriptor.getType() : TypeUtils.NO_EXPECTED_TYPE;
        if (propertyDescriptor.mo6754getCompileTimeInitializer() == null) {
            ExpressionTypingServices expressionTypingServices = this.expressionTypingServices;
            if (inferenceSession == null) {
                inferenceSession = InferenceSession.INSTANCE.getDefault();
            }
            expressionTypingServices.getType(lexicalScopeMakeScopeForPropertyInitializer, ktExpression, type, dataFlowInfo, inferenceSession, this.trace);
        }
    }

    private static LexicalScope getScopeForProperty(BodiesResolveContext bodiesResolveContext, KtProperty ktProperty) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(84);
        }
        if (ktProperty == null) {
            $$$reportNull$$$0(85);
        }
        LexicalScope declaringScope = bodiesResolveContext.getDeclaringScope(ktProperty);
        if (declaringScope == null) {
            $$$reportNull$$$0(86);
        }
        return declaringScope;
    }

    private void resolveFunctionBodies(BodiesResolveContext bodiesResolveContext) {
        if (bodiesResolveContext == null) {
            $$$reportNull$$$0(87);
        }
        for (Map.Entry<KtNamedFunction, SimpleFunctionDescriptor> entry : bodiesResolveContext.getFunctions().entrySet()) {
            KtNamedFunction key = entry.getKey();
            LexicalScope declaringScope = bodiesResolveContext.getDeclaringScope(key);
            if (!bodiesResolveContext.getTopDownAnalysisMode().getIsLocalDeclarations() && !(this.bodyResolveCache instanceof BodyResolveCache.ThrowException) && this.expressionTypingServices.getStatementFilter() != StatementFilter.NONE) {
                this.bodyResolveCache.resolveFunctionBody(key).addOwnDataTo(this.trace, true);
            } else {
                resolveFunctionBody(bodiesResolveContext.getOuterDataFlowInfo(), this.trace, key, entry.getValue(), declaringScope, bodiesResolveContext.getLocalContext());
            }
        }
    }

    public void resolveFunctionBody(DataFlowInfo dataFlowInfo, BindingTrace bindingTrace, KtDeclarationWithBody ktDeclarationWithBody, FunctionDescriptor functionDescriptor, LexicalScope lexicalScope, ExpressionTypingContext expressionTypingContext) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(88);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(89);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(90);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(91);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(92);
        }
        computeDeferredType(functionDescriptor.getReturnType());
        resolveFunctionBody(dataFlowInfo, bindingTrace, ktDeclarationWithBody, functionDescriptor, lexicalScope, null, null, expressionTypingContext);
    }

    private void resolveFunctionBody(DataFlowInfo dataFlowInfo, BindingTrace bindingTrace, KtDeclarationWithBody ktDeclarationWithBody, FunctionDescriptor functionDescriptor, LexicalScope lexicalScope, Function1<LexicalScope, DataFlowInfo> function1, Function1<LexicalScope, LexicalScope> function2, ExpressionTypingContext expressionTypingContext) {
        LexicalScope lexicalScope2;
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(93);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(94);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(95);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(96);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(97);
        }
        ProgressManager.checkCanceled();
        PreliminaryDeclarationVisitor.INSTANCE.createForDeclaration(ktDeclarationWithBody, bindingTrace, this.languageVersionSettings);
        LexicalScope functionInnerScope = FunctionDescriptorUtil.getFunctionInnerScope(lexicalScope, functionDescriptor, bindingTrace, this.overloadChecker);
        List<KtParameter> valueParameters = ktDeclarationWithBody.getValueParameters();
        List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getValueParameters();
        LexicalScope lexicalScopeInvoke = function2 != null ? function2.invoke(functionInnerScope) : functionInnerScope;
        this.valueParameterResolver.resolveValueParameters(valueParameters, valueParameters2, lexicalScopeInvoke, dataFlowInfo, bindingTrace, expressionTypingContext != null ? expressionTypingContext.inferenceSession : null);
        if ((functionDescriptor instanceof PropertyAccessorDescriptor) && functionDescriptor.getExtensionReceiverParameter() == null && functionDescriptor.getContextReceiverParameters().isEmpty()) {
            final SyntheticFieldDescriptor syntheticFieldDescriptor = new SyntheticFieldDescriptor((PropertyAccessorDescriptor) functionDescriptor, KotlinSourceElementKt.toSourceElement((KtProperty) ktDeclarationWithBody.getLightParent()));
            LexicalScopeImpl lexicalScopeImpl = new LexicalScopeImpl(functionInnerScope, functionDescriptor, true, null, Collections.emptyList(), LexicalScopeKind.PROPERTY_ACCESSOR_BODY, LocalRedeclarationChecker.DO_NOTHING.INSTANCE, new Function1() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return BodyResolver.lambda$resolveFunctionBody$6(syntheticFieldDescriptor, (LexicalScopeImpl.InitializeHandler) obj);
                }
            });
            for (KtParameter ktParameter : ktDeclarationWithBody.getValueParameters()) {
                if (SyntheticFieldDescriptor.NAME.equals(ktParameter.getNameAsName())) {
                    bindingTrace.report(Errors.ACCESSOR_PARAMETER_NAME_SHADOWING.on(ktParameter));
                }
            }
            lexicalScope2 = lexicalScopeImpl;
        } else {
            lexicalScope2 = functionInnerScope;
        }
        DataFlowInfo dataFlowInfoInvoke = r14 != 0 ? function1.invoke(lexicalScopeInvoke) : null;
        if (ktDeclarationWithBody.hasBody()) {
            this.expressionTypingServices.checkFunctionReturnType(lexicalScope2, ktDeclarationWithBody, functionDescriptor, dataFlowInfoInvoke != null ? dataFlowInfoInvoke : dataFlowInfo, null, bindingTrace, expressionTypingContext);
        }
    }

    static /* synthetic */ Unit lambda$resolveFunctionBody$6(SyntheticFieldDescriptor syntheticFieldDescriptor, LexicalScopeImpl.InitializeHandler initializeHandler) {
        initializeHandler.addVariableDescriptor(syntheticFieldDescriptor);
        return Unit.INSTANCE;
    }

    public void resolveConstructorParameterDefaultValues(DataFlowInfo dataFlowInfo, BindingTrace bindingTrace, KtPrimaryConstructor ktPrimaryConstructor, ConstructorDescriptor constructorDescriptor, LexicalScope lexicalScope, InferenceSession inferenceSession) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(98);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(99);
        }
        if (ktPrimaryConstructor == null) {
            $$$reportNull$$$0(100);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(101);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(102);
        }
        this.valueParameterResolver.resolveValueParameters(ktPrimaryConstructor.getValueParameters(), constructorDescriptor.getValueParameters(), getPrimaryConstructorParametersScope(lexicalScope, constructorDescriptor), dataFlowInfo, bindingTrace, inferenceSession);
    }

    public static void computeDeferredType(KotlinType kotlinType) {
        if (kotlinType instanceof DeferredType) {
            DeferredType deferredType = (DeferredType) kotlinType;
            if (deferredType.isComputed()) {
                return;
            }
            deferredType.getDelegate();
        }
    }

    private void computeDeferredTypes() {
        Collection keys = this.trace.getKeys(BindingContext.DEFERRED_TYPE);
        if (keys.isEmpty()) {
            return;
        }
        final ArrayDeque arrayDeque = new ArrayDeque(keys.size() + 1);
        this.trace.addHandler(BindingContext.DEFERRED_TYPE, new ObservableBindingTrace.RecordHandler() { // from class: org.jetbrains.kotlin.resolve.BodyResolver$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.resolve.ObservableBindingTrace.RecordHandler
            public final void handleRecord(WritableSlice writableSlice, Object obj, Object obj2) {
                arrayDeque.offerLast(((Box) obj).getData());
            }
        });
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            arrayDeque.offerLast(((Box) it.next()).getData());
        }
        while (!arrayDeque.isEmpty()) {
            DeferredType deferredType = (DeferredType) arrayDeque.pollFirst();
            if (!deferredType.isComputed()) {
                try {
                    deferredType.getDelegate();
                } catch (ReenteringLazyValueComputationException unused) {
                }
            }
        }
    }
}
