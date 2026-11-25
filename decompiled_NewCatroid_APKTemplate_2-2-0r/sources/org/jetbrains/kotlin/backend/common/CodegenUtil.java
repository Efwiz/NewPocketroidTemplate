package org.jetbrains.kotlin.backend.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.util.DeclarationUtilKt;
import org.jetbrains.kotlin.util.ExceptionUtilKt;
import org.jetbrains.kotlin.util.SourceCodeAnalysisException;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: CodegenUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007JJ\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001a\u001a\u00020\u000bH\u0007J\"\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007J \u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010\u001a\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010'H\u0007J\u001f\u0010(\u001a\u0004\u0018\u00010\b2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0018H\u0007¢\u0006\u0002\u0010,J\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020.0$2\u0006\u0010/\u001a\u000200H\u0007J\u001e\u00101\u001a\b\u0012\u0004\u0012\u0002020$2\u0006\u0010/\u001a\u0002002\u0006\u0010!\u001a\u00020\"H\u0007JF\u00103\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00104\u001a\u0002052\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\u0018072\u0018\u00109\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0$\u0012\u0004\u0012\u00020\u001807J&\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u001a\u001a\u00020 2\b\b\u0002\u0010;\u001a\u00020\u0018H\u0007J\u001a\u0010<\u001a\u0004\u0018\u00010 2\u0006\u0010=\u001a\u00020>2\u0006\u0010!\u001a\u00020\"H\u0007J \u0010?\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0018H\u0007J\u001a\u0010C\u001a\u00020\u00182\b\u0010D\u001a\u0004\u0018\u00010\u001c2\u0006\u0010!\u001a\u00020\"H\u0007J$\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010F\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002JR\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u0002052\b\u0010L\u001a\u0004\u0018\u0001052\n\b\u0002\u0010M\u001a\u0004\u0018\u0001052\"\b\u0002\u0010N\u001a\u001c\u0012\u0004\u0012\u00020\b\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010O07H\u0007J\f\u0010P\u001a\u00020\u0018*\u00020%H\u0002¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CodegenUtil;", "", "()V", "constructFakeFunctionCall", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "arity", "", "copyFunctions", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "inheritedMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "traitMember", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "findExpectedFunctionForActual", "descriptor", "getDelegatePropertyIfAny", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getFunctionParametersForDefaultValueGeneration", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "trace", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "getLineNumberForElement", "statement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "markEndOffset", "(Lcom/intellij/psi/PsiElement;Z)Ljava/lang/Integer;", "getMemberDeclarationsToGenerate", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getMemberDescriptorsToGenerate", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "getMemberToGenerate", "name", "", "isReturnTypeOk", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "areParametersOk", "getNonPrivateTraitMethods", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getSuperClassBySuperTypeListEntry", "specifier", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "isExhaustive", "whenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "isStatement", "isFinalPropertyWithBackingField", "propertyDescriptor", "mapMembers", "inherited", "reportBackendException", "", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", TypedValues.CycleType.S_WAVE_PHASE, FirebaseAnalytics.Param.LOCATION, "additionalMessage", "linesMapping", "Lkotlin/Pair;", "declaresOrInheritsDefaultValue", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodegenUtil {
    public static final CodegenUtil INSTANCE = new CodegenUtil();

    @JvmStatic
    public static final Map<FunctionDescriptor, FunctionDescriptor> getNonPrivateTraitMethods(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return getNonPrivateTraitMethods$default(descriptor, false, 2, null);
    }

    private CodegenUtil() {
    }

    @JvmStatic
    public static final PropertyDescriptor getDelegatePropertyIfAny(KtExpression expression, ClassDescriptor classDescriptor, BindingContext bindingContext) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        KtSimpleNameExpression ktSimpleNameExpression = expression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) expression : null;
        if (ktSimpleNameExpression != null && (resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression, bindingContext)) != null) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            ValueParameterDescriptor valueParameterDescriptor = resultingDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) resultingDescriptor : null;
            if (valueParameterDescriptor != null && (valueParameterDescriptor.getContainingDeclaration() instanceof ConstructorDescriptor) && valueParameterDescriptor.getContainingDeclaration().getContainingDeclaration() == classDescriptor) {
                return (PropertyDescriptor) bindingContext.get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor);
            }
        }
        return null;
    }

    @JvmStatic
    public static final boolean isFinalPropertyWithBackingField(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (propertyDescriptor == null || propertyDescriptor.isVar()) {
            return false;
        }
        Boolean bool = (Boolean) bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
        return bool == null ? false : bool.booleanValue();
    }

    public static /* synthetic */ Map getNonPrivateTraitMethods$default(ClassDescriptor classDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return getNonPrivateTraitMethods(classDescriptor, z);
    }

    @JvmStatic
    public static final Map<FunctionDescriptor, FunctionDescriptor> getNonPrivateTraitMethods(ClassDescriptor descriptor, boolean copy) {
        Map<FunctionDescriptor, FunctionDescriptor> mapMapMembers;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : DeclarationUtilKt.getNonPrivateTraitMembersForDelegation$default(descriptor, false, 2, (Object) null).entrySet()) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) entry.getKey();
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) entry.getValue();
            callableMemberDescriptor2.getModality();
            Modality modality = Modality.ABSTRACT;
            if (copy) {
                CodegenUtil codegenUtil = INSTANCE;
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                Modality modality2 = callableMemberDescriptor2.getModality();
                Intrinsics.checkNotNullExpressionValue(modality2, "getModality(...)");
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                mapMapMembers = codegenUtil.copyFunctions(callableMemberDescriptor, callableMemberDescriptor2, containingDeclaration, modality2, PUBLIC, CallableMemberDescriptor.Kind.DECLARATION, true);
            } else {
                mapMapMembers = INSTANCE.mapMembers(callableMemberDescriptor, callableMemberDescriptor2);
            }
            linkedHashMap.putAll(mapMapMembers);
        }
        return linkedHashMap;
    }

    public final Map<FunctionDescriptor, FunctionDescriptor> copyFunctions(CallableMemberDescriptor inheritedMember, CallableMemberDescriptor traitMember, DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        Intrinsics.checkNotNullParameter(inheritedMember, "inheritedMember");
        Intrinsics.checkNotNullParameter(traitMember, "traitMember");
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(kind, "kind");
        CallableMemberDescriptor callableMemberDescriptorCopy = inheritedMember.copy(newOwner, modality, visibility, kind, copyOverrides);
        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorCopy, "copy(...)");
        return mapMembers(callableMemberDescriptorCopy, traitMember);
    }

    private final Map<FunctionDescriptor, FunctionDescriptor> mapMembers(CallableMemberDescriptor inherited, CallableMemberDescriptor traitMember) {
        if (traitMember instanceof SimpleFunctionDescriptor) {
            Intrinsics.checkNotNull(inherited, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            return MapsKt.mapOf(TuplesKt.to(traitMember, (FunctionDescriptor) inherited));
        }
        if (traitMember instanceof PropertyDescriptor) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (PropertyAccessorDescriptor propertyAccessorDescriptor : ((PropertyDescriptor) traitMember).getAccessors()) {
                Intrinsics.checkNotNull(inherited, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                for (PropertyAccessorDescriptor propertyAccessorDescriptor2 : ((PropertyDescriptor) inherited).getAccessors()) {
                    if ((propertyAccessorDescriptor2 instanceof PropertyGetterDescriptor) == (propertyAccessorDescriptor instanceof PropertyGetterDescriptor)) {
                        Intrinsics.checkNotNull(propertyAccessorDescriptor);
                        Intrinsics.checkNotNull(propertyAccessorDescriptor2);
                        linkedHashMap.put(propertyAccessorDescriptor, propertyAccessorDescriptor2);
                    }
                }
            }
            return linkedHashMap;
        }
        throw new IllegalStateException(("Unexpected member: " + inherited).toString());
    }

    @JvmStatic
    public static final ClassDescriptor getSuperClassBySuperTypeListEntry(KtSuperTypeListEntry specifier, BindingContext bindingContext) {
        TypeConstructor constructor;
        Intrinsics.checkNotNullParameter(specifier, "specifier");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        WritableSlice<KtTypeReference, KotlinType> writableSlice = BindingContext.TYPE;
        KtTypeReference typeReference = specifier.getTypeReference();
        Intrinsics.checkNotNull(typeReference);
        KotlinType kotlinType = (KotlinType) bindingContext.get(writableSlice, typeReference);
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = (kotlinType == null || (constructor = kotlinType.getConstructor()) == null) ? null : constructor.getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
        }
        return null;
    }

    @JvmStatic
    public static final Integer getLineNumberForElement(PsiElement statement, boolean z) {
        Document document;
        Intrinsics.checkNotNullParameter(statement, "statement");
        PsiFile containingFile = statement.getContainingFile();
        if ((containingFile instanceof KtFile) && KtPsiFactoryKt.getDoNotAnalyze((KtFile) containingFile) != null) {
            return null;
        }
        if (((statement instanceof KtConstructorDelegationReferenceExpression) && ((KtConstructorDelegationReferenceExpression) statement).getTextLength() == 0) || (document = containingFile.getViewProvider().getDocument()) == null) {
            return null;
        }
        return Integer.valueOf(document.getLineNumber(z ? statement.getTextRange().getEndOffset() : statement.getTextOffset()) + 1);
    }

    public final FunctionDescriptor getMemberToGenerate(ClassDescriptor classDescriptor, String name2, Function1<? super KotlinType, Boolean> isReturnTypeOk, Function1<? super List<? extends ValueParameterDescriptor>, Boolean> areParametersOk) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(isReturnTypeOk, "isReturnTypeOk");
        Intrinsics.checkNotNullParameter(areParametersOk, "areParametersOk");
        MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : unsubstitutedMemberScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND)) {
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) obj3;
            CallableMemberDescriptor.Kind kind = simpleFunctionDescriptor.getKind();
            if (kind == CallableMemberDescriptor.Kind.SYNTHESIZED || kind == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                if (simpleFunctionDescriptor.getModality() != Modality.FINAL) {
                    List<ValueParameterDescriptor> valueParameters = simpleFunctionDescriptor.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    if (areParametersOk.invoke(valueParameters).booleanValue() && simpleFunctionDescriptor.getReturnType() != null) {
                        KotlinType returnType = simpleFunctionDescriptor.getReturnType();
                        Intrinsics.checkNotNull(returnType);
                        if (isReturnTypeOk.invoke(returnType).booleanValue() && simpleFunctionDescriptor.getExtensionReceiverParameter() == null) {
                            if (z) {
                                return (FunctionDescriptor) obj;
                            }
                            z = true;
                            obj2 = obj3;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        if (z) {
            obj = obj2;
        }
        return (FunctionDescriptor) obj;
    }

    @JvmStatic
    public static final boolean isExhaustive(BindingContext bindingContext, KtWhenExpression whenExpression, boolean isStatement) {
        WritableSlice<KtWhenExpression, Boolean> writableSlice;
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        if (isStatement && !BindingContextUtilsKt.isUsedAsExpression(whenExpression, bindingContext)) {
            writableSlice = BindingContext.IMPLICIT_EXHAUSTIVE_WHEN;
        } else {
            writableSlice = BindingContext.EXHAUSTIVE_WHEN;
        }
        return Intrinsics.areEqual(bindingContext.get(writableSlice, whenExpression), (Object) true);
    }

    @JvmStatic
    public static final KtCallExpression constructFakeFunctionCall(Project project, int arity) {
        Intrinsics.checkNotNullParameter(project, "project");
        KtExpression ktExpressionCreateExpression = new KtPsiFactory(project, false).createExpression(CollectionsKt.joinToString$default(new IntRange(1, arity), ", ", "callableReferenceFakeCall(", Constant.AFTER_QUTO, 0, null, new Function1<Integer, CharSequence>() { // from class: org.jetbrains.kotlin.backend.common.CodegenUtil$constructFakeFunctionCall$fakeFunctionCall$1
            public final CharSequence invoke(int i) {
                return "p" + i;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Integer num) {
                return invoke(num.intValue());
            }
        }, 24, null));
        Intrinsics.checkNotNull(ktExpressionCreateExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtCallExpression");
        return (KtCallExpression) ktExpressionCreateExpression;
    }

    @JvmStatic
    public static final List<KtDeclaration> getMemberDeclarationsToGenerate(final KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        List list = (List) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.backend.common.CodegenUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return CodegenUtil.getMemberDeclarationsToGenerate$lambda$4(file);
            }
        });
        Intrinsics.checkNotNull(list);
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            KtDeclaration ktDeclaration = (KtDeclaration) obj;
            if (!PsiUtilsKt.hasExpectModifier(ktDeclaration) && ((ktDeclaration instanceof KtNamedFunction) || (ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtTypeAlias))) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List getMemberDeclarationsToGenerate$lambda$4(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "$file");
        return file.getDeclarations();
    }

    @JvmStatic
    public static final List<MemberDescriptor> getMemberDescriptorsToGenerate(KtFile file, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        List<KtDeclaration> memberDeclarationsToGenerate = getMemberDeclarationsToGenerate(file);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = memberDeclarationsToGenerate.iterator();
        while (it.hasNext()) {
            MemberDescriptor memberDescriptor = (MemberDescriptor) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, (KtDeclaration) it.next());
            if (memberDescriptor != null) {
                arrayList.add(memberDescriptor);
            }
        }
        return arrayList;
    }

    @JvmStatic
    public static final FunctionDescriptor findExpectedFunctionForActual(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return (FunctionDescriptor) CollectionsKt.firstOrNull((List) ExpectedActualResolverKt.findCompatibleExpectsForActual(descriptor, ModuleFilterUtilsKt.onlyFromThisModule(DescriptorUtilsKt.getModule(descriptor))));
    }

    @JvmStatic
    public static final List<ValueParameterDescriptor> getFunctionParametersForDefaultValueGeneration(FunctionDescriptor descriptor, DiagnosticSink trace) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.isActual()) {
            List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (ValueParameterDescriptor valueParameterDescriptor : list) {
                    CodegenUtil codegenUtil = INSTANCE;
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    if (codegenUtil.declaresOrInheritsDefaultValue(valueParameterDescriptor)) {
                        return valueParameters;
                    }
                }
            }
            FunctionDescriptor functionDescriptorFindExpectedFunctionForActual = findExpectedFunctionForActual(descriptor);
            if (functionDescriptorFindExpectedFunctionForActual != null) {
                List<ValueParameterDescriptor> valueParameters2 = functionDescriptorFindExpectedFunctionForActual.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                List<ValueParameterDescriptor> list2 = valueParameters2;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        if (((ValueParameterDescriptor) it.next()).declaresDefaultValue()) {
                            if (DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptorFindExpectedFunctionForActual) != null) {
                                List<ValueParameterDescriptor> valueParameters3 = functionDescriptorFindExpectedFunctionForActual.getValueParameters();
                                Intrinsics.checkNotNullExpressionValue(valueParameters3, "getValueParameters(...)");
                                return valueParameters3;
                            }
                            if (trace != null) {
                                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
                                if (psiElementDescriptorToDeclaration == null) {
                                    throw new IllegalStateException(("Not a source declaration: " + descriptor).toString());
                                }
                                trace.report(Errors.EXPECTED_FUNCTION_SOURCE_WITH_DEFAULT_ARGUMENTS_NOT_FOUND.on(psiElementDescriptorToDeclaration));
                            }
                            return valueParameters;
                        }
                    }
                }
            }
        }
        List<ValueParameterDescriptor> valueParameters4 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters4, "getValueParameters(...)");
        return valueParameters4;
    }

    private final boolean declaresOrInheritsDefaultValue(ValueParameterDescriptor valueParameterDescriptor) {
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(valueParameterDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.backend.common.CodegenUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return CodegenUtil.declaresOrInheritsDefaultValue$lambda$9((ValueParameterDescriptor) obj);
            }
        }, new Function1<ValueParameterDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.CodegenUtil.declaresOrInheritsDefaultValue.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ValueParameterDescriptor valueParameterDescriptor2) {
                return Boolean.valueOf(valueParameterDescriptor2.declaresDefaultValue());
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable declaresOrInheritsDefaultValue$lambda$9(ValueParameterDescriptor valueParameterDescriptor) {
        Collection<ValueParameterDescriptor> overriddenDescriptors = valueParameterDescriptor.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenDescriptors, 10));
        Iterator<T> it = overriddenDescriptors.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getOriginal());
        }
        return arrayList;
    }

    public static /* synthetic */ Void reportBackendException$default(Throwable th, String str, String str2, String str3, Function1 function1, int i, Object obj) {
        if ((i & 8) != 0) {
            str3 = null;
        }
        if ((i & 16) != 0) {
            function1 = new Function1() { // from class: org.jetbrains.kotlin.backend.common.CodegenUtil.reportBackendException.1
                public final Void invoke(int i2) {
                    return null;
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    return invoke(((Number) obj2).intValue());
                }
            };
        }
        return reportBackendException(th, str, str2, str3, function1);
    }

    @JvmStatic
    public static final Void reportBackendException(Throwable exception, String phase, String location, String additionalMessage, Function1<? super Integer, Pair<Integer, Integer>> linesMapping) throws Throwable {
        Pair<Integer, Integer> pairInvoke;
        Intrinsics.checkNotNullParameter(exception, "exception");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(linesMapping, "linesMapping");
        if ((exception instanceof KotlinExceptionWithAttachments) || (exception instanceof ProcessCanceledException)) {
            throw exception;
        }
        if (location != null) {
            SourceCodeAnalysisException sourceCodeAnalysisException = exception instanceof SourceCodeAnalysisException ? (SourceCodeAnalysisException) exception : null;
            if (sourceCodeAnalysisException != null && (pairInvoke = linesMapping.invoke(Integer.valueOf(sourceCodeAnalysisException.getSource().getStartOffset()))) != null) {
                String str = location + ':' + (pairInvoke.component1().intValue() + 1) + ':' + (pairInvoke.component2().intValue() + 1);
                if (str != null) {
                    location = str;
                }
            }
        }
        StringBuilder sbAppend = new StringBuilder().append(ExceptionUtilKt.getExceptionMessage("Backend", "Exception during " + phase, exception, location));
        String str2 = additionalMessage != null ? "\n" + additionalMessage : null;
        if (str2 == null) {
            str2 = "";
        }
        throw new BackendException(sbAppend.append(str2).toString(), exception);
    }
}
