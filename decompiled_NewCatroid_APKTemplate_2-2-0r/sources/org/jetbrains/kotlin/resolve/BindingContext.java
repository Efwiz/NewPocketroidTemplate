package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Pair;
import org.jetbrains.kotlin.cfg.LeakingThisDescriptor;
import org.jetbrains.kotlin.cfg.TailRecursionKind;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallContainer;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ImplicitSmartCasts;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinResolutionCallbacksImpl;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.CaptureKind;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;
import org.jetbrains.kotlin.util.Box;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.RewritePolicy;
import org.jetbrains.kotlin.util.slicedMap.SetSlice;
import org.jetbrains.kotlin.util.slicedMap.SlicedMap;
import org.jetbrains.kotlin.util.slicedMap.Slices;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public interface BindingContext {
    public static final WritableSlice<KtReferenceExpression, Collection<? extends PsiElement>> AMBIGUOUS_LABEL_TARGET;
    public static final WritableSlice<KtBinaryExpressionWithTypeRHS, Boolean> CAST_TYPE_USED_AS_EXPECTED_TYPE;
    public static final WritableSlice<PsiElement, ClassDescriptor> CLASS;
    public static final WritableSlice<PsiElement, ConstructorDescriptor> CONSTRUCTOR;
    public static final WritableSlice<ConstructorDescriptor, ResolvedCall<ConstructorDescriptor>> CONSTRUCTOR_RESOLVED_DELEGATION_CALL;
    public static final WritableSlice<ValueParameterDescriptor, FunctionDescriptor> DATA_CLASS_COMPONENT_FUNCTION;
    public static final WritableSlice<ClassDescriptor, FunctionDescriptor> DATA_CLASS_COPY_FUNCTION;
    public static final WritableSlice[] DECLARATIONS_TO_DESCRIPTORS;
    public static final ReadOnlySlice<PsiElement, DeclarationDescriptor> DECLARATION_TO_DESCRIPTOR;
    public static final WritableSlice<PsiElement, Boolean> DEPRECATED_SHORT_NAME_ACCESS;
    public static final WritableSlice<DeclarationDescriptor, Multimap<String, ReceiverParameterDescriptor>> DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP;
    public static final WritableSlice<FqNameUnsafe, ClassDescriptor> FQNAME_TO_CLASS_DESCRIPTOR;
    public static final WritableSlice<PsiElement, SimpleFunctionDescriptor> FUNCTION;
    public static final WritableSlice<KtReferenceExpression, PsiElement> LABEL_TARGET;
    public static final WritableSlice<KtExpression, Ref<VariableDescriptor>> NEW_INFERENCE_CATCH_EXCEPTION_PARAMETER;
    public static final WritableSlice<PsiElement, Boolean> NEW_INFERENCE_IS_LAMBDA_FOR_OVERLOAD_RESOLUTION_INLINE;
    public static final WritableSlice<KtFunction, KotlinResolutionCallbacksImpl.LambdaInfo> NEW_INFERENCE_LAMBDA_INFO;
    public static final WritableSlice<FqName, Collection<KtFile>> PACKAGE_TO_FILES;
    public static final WritableSlice<PsiElement, PropertyDescriptor> PRIMARY_CONSTRUCTOR_PARAMETER;
    public static final WritableSlice<KtExpression, PrimitiveNumericComparisonInfo> PRIMITIVE_NUMERIC_COMPARISON_INFO;
    public static final WritableSlice<KtPropertyAccessor, PropertyAccessorDescriptor> PROPERTY_ACCESSOR;
    public static final WritableSlice<PsiElement, ScriptDescriptor> SCRIPT;
    public static final WritableSlice<PsiElement, TypeAliasDescriptor> TYPE_ALIAS;
    public static final WritableSlice<KtTypeParameter, TypeParameterDescriptor> TYPE_PARAMETER;
    public static final WritableSlice<KtParameter, VariableDescriptor> VALUE_PARAMETER;
    public static final WritableSlice<ValueParameterDescriptor, PropertyDescriptor> VALUE_PARAMETER_AS_PROPERTY;
    public static final WritableSlice<PsiElement, VariableDescriptor> VARIABLE;

    @Deprecated
    public static final Void _static_initializer;
    public static final BindingContext EMPTY = new BindingContext() { // from class: org.jetbrains.kotlin.resolve.BindingContext.1
        /* JADX WARN: Code duplicated, block: B:30:0x0049  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 2 || i == 4 || i == 5) ? 3 : 2];
            if (i == 2) {
                objArr[0] = "slice";
            } else if (i == 4) {
                objArr[0] = "expression";
            } else if (i != 5) {
                objArr[0] = "org/jetbrains/kotlin/resolve/BindingContext$1";
            } else {
                objArr[0] = "trace";
            }
            if (i == 1) {
                objArr[1] = "getKeys";
            } else if (i == 2) {
                objArr[1] = "org/jetbrains/kotlin/resolve/BindingContext$1";
            } else if (i == 3) {
                objArr[1] = "getSliceContents";
            } else if (i == 4 || i == 5) {
                objArr[1] = "org/jetbrains/kotlin/resolve/BindingContext$1";
            } else {
                objArr[1] = "getDiagnostics";
            }
            if (i == 2) {
                objArr[2] = "getSliceContents";
            } else if (i == 4) {
                objArr[2] = "getType";
            } else if (i == 5) {
                objArr[2] = "addOwnDataTo";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 4 && i != 5) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public void addOwnDataTo(BindingTrace bindingTrace, boolean z) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(5);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public KotlinType getType(KtExpression ktExpression) {
            if (ktExpression != null) {
                return null;
            }
            $$$reportNull$$$0(4);
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public Diagnostics getDiagnostics() {
            Diagnostics empty = Diagnostics.INSTANCE.getEMPTY();
            if (empty == null) {
                $$$reportNull$$$0(0);
            }
            return empty;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(1);
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> readOnlySlice) {
            if (readOnlySlice == null) {
                $$$reportNull$$$0(2);
            }
            ImmutableMap<K, V> immutableMapOf = ImmutableMap.of();
            if (immutableMapOf == null) {
                $$$reportNull$$$0(3);
            }
            return immutableMapOf;
        }
    };
    public static final WritableSlice<KtAnnotationEntry, AnnotationDescriptor> ANNOTATION = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, CompileTimeConstant<?>> COMPILE_TIME_VALUE = new BasicWritableSlice(Slices.COMPILE_TIME_VALUE_REWRITE_POLICY);
    public static final WritableSlice<KtTypeReference, KotlinType> TYPE = Slices.createSimpleSlice();
    public static final WritableSlice<KtTypeReference, KotlinType> ABBREVIATED_TYPE = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, KotlinTypeInfo> EXPRESSION_TYPE_INFO = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, DataFlowInfo> DATA_FLOW_INFO_BEFORE = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, KotlinType> EXPECTED_EXPRESSION_TYPE = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtElement, Computation> EXPRESSION_EFFECTS = Slices.createSimpleSlice();
    public static final WritableSlice<KtElement, Boolean> CONTRACT_NOT_ALLOWED = Slices.createSimpleSlice();
    public static final WritableSlice<KtElement, Boolean> IS_CONTRACT_DECLARATION_BLOCK = Slices.createSimpleSlice();
    public static final WritableSlice<KtFunction, KotlinType> EXPECTED_RETURN_TYPE = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, DataFlowInfo> DATAFLOW_INFO_AFTER_CONDITION = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptor, DataFlowValue> BOUND_INITIALIZER_VALUE = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, LeakingThisDescriptor> LEAKING_THIS = Slices.createSimpleSlice();
    public static final WritableSlice<KtParameter, Boolean> UNUSED_MAIN_PARAMETER = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptor, Boolean> UNUSED_DELEGATED_PROPERTY_OPERATOR_PARAMETER = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, Qualifier> QUALIFIER = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, DoubleColonLHS> DOUBLE_COLON_LHS = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtSuperExpression, KotlinType> THIS_TYPE_FOR_SUPER_EXPRESSION = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtSuperExpression, Boolean> SUPER_EXPRESSION_FROM_ANY_MIGRATION = Slices.createSimpleSlice();
    public static final WritableSlice<KtReferenceExpression, DeclarationDescriptor> REFERENCE_TARGET = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtReferenceExpression, ReceiverParameterDescriptor> THIS_REFERENCE_TARGET = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtReferenceExpression, ClassifierDescriptorWithTypeParameters> SHORT_REFERENCE_TO_COMPANION_OBJECT = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<Call, ResolvedCall<?>> RESOLVED_CALL = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<Call, PartialCallContainer> ONLY_RESOLVED_CALL = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<Call, BasicCallResolutionContext> PARTIAL_CALL_RESOLUTION_CONTEXT = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, Call> DELEGATE_EXPRESSION_TO_PROVIDE_DELEGATE_CALL = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<Call, TailRecursionKind> TAIL_RECURSION_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtElement, ConstraintSystemCompleter> CONSTRAINT_SYSTEM_COMPLETER = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtElement, Call> CALL = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, Collection<? extends DeclarationDescriptor>> AMBIGUOUS_REFERENCE_TARGET = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_ITERATOR_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_HAS_NEXT_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_NEXT_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<Call, FunctionDescriptor> ENCLOSING_SUSPEND_FUNCTION_FOR_SUSPEND_FUNCTION_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<VariableAccessorDescriptor, ResolvedCall<FunctionDescriptor>> DELEGATED_PROPERTY_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<VariableAccessorDescriptor, Call> DELEGATED_PROPERTY_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<VariableDescriptorWithAccessors, Call> PROVIDE_DELEGATE_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtDestructuringDeclarationEntry, ResolvedCall<FunctionDescriptor>> COMPONENT_RESOLVED_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> INDEXED_LVALUE_GET = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> INDEXED_LVALUE_SET = Slices.createSimpleSlice();
    public static final WritableSlice<KtCollectionLiteralExpression, ResolvedCall<FunctionDescriptor>> COLLECTION_LITERAL_CALL = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ExplicitSmartCasts> SMARTCAST = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, ExplicitSmartCasts> UNSTABLE_SMARTCAST = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, Boolean> SMARTCAST_NULL = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, ImplicitSmartCasts> IMPLICIT_RECEIVER_SMARTCAST = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtWhenExpression, Boolean> EXHAUSTIVE_WHEN = Slices.createSimpleSlice();
    public static final WritableSlice<KtWhenExpression, Boolean> IMPLICIT_EXHAUSTIVE_WHEN = Slices.createSimpleSlice();
    public static final WritableSlice<KtElement, LexicalScope> LEXICAL_SCOPE = Slices.createSimpleSlice();
    public static final WritableSlice<ScriptDescriptor, LexicalScope> SCRIPT_SCOPE = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, Boolean> VARIABLE_REASSIGNMENT = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<ValueParameterDescriptor, Boolean> AUTO_CREATED_IT = Slices.createSimpleSetSlice();
    public static final WritableSlice<Pair<AnonymousFunctionDescriptor, Integer>, Boolean> SUSPEND_LAMBDA_PARAMETER_USED = Slices.createSimpleSlice();
    public static final WritableSlice<KtBinaryExpression, Boolean> MARKED_EQUALIY_CALL_PROPER_IN_BUILDER_INFERENCE = Slices.createSimpleSlice();
    public static final WritableSlice<KtExpression, Boolean> PROCESSED = Slices.createSimpleSlice();
    public static final WritableSlice<KtElement, Boolean> USED_AS_EXPRESSION = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtElement, Boolean> USED_AS_RESULT_OF_LAMBDA = Slices.createSimpleSetSlice();
    public static final WritableSlice<VariableDescriptor, CaptureKind> CAPTURED_IN_CLOSURE = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtDeclaration, PreliminaryDeclarationVisitor> PRELIMINARY_VISITOR = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<Box<DeferredType>, Boolean> DEFERRED_TYPE = Slices.createCollectiveSetSlice();
    public static final WritableSlice<PropertyDescriptor, Boolean> BACKING_FIELD_REQUIRED = new BasicWritableSlice<PropertyDescriptor, Boolean>(RewritePolicy.DO_NOTHING) { // from class: org.jetbrains.kotlin.resolve.BindingContext.2
        @Override // org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
        public Boolean computeValue(SlicedMap slicedMap, PropertyDescriptor propertyDescriptor, Boolean bool, boolean z) {
            if (propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
                return false;
            }
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor);
            if (psiElementDescriptorToDeclaration instanceof KtParameter) {
                return Boolean.valueOf(((KtParameter) psiElementDescriptorToDeclaration).hasValOrVar() || bool.booleanValue());
            }
            if (propertyDescriptor.getModality() == Modality.ABSTRACT) {
                return false;
            }
            if ((psiElementDescriptorToDeclaration instanceof KtProperty) && ((KtProperty) psiElementDescriptorToDeclaration).hasDelegate()) {
                return false;
            }
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (getter == null) {
                return true;
            }
            if (propertyDescriptor.isVar() && setter == null) {
                return true;
            }
            if (setter != null && !DescriptorPsiUtilsKt.hasBody(setter) && setter.getModality() != Modality.ABSTRACT) {
                return true;
            }
            if (DescriptorPsiUtilsKt.hasBody(getter) || getter.getModality() == Modality.ABSTRACT) {
                return bool;
            }
            return true;
        }
    };
    public static final WritableSlice<PropertyDescriptor, Boolean> IS_UNINITIALIZED = Slices.createSimpleSetSlice();
    public static final WritableSlice<PropertyDescriptor, Boolean> IS_DEFINITELY_NOT_ASSIGNED_IN_CONSTRUCTOR = Slices.createSimpleSetSlice();
    public static final WritableSlice<PropertyDescriptor, Boolean> MUST_BE_LATEINIT = Slices.createSimpleSetSlice();
    public static final WritableSlice<KtLambdaExpression, EventOccurrencesRange> LAMBDA_INVOCATIONS = Slices.createSimpleSlice();
    public static final WritableSlice<KtLambdaExpression, Boolean> BLOCK = new SetSlice<KtLambdaExpression>(RewritePolicy.DO_NOTHING) { // from class: org.jetbrains.kotlin.resolve.BindingContext.3
        @Override // org.jetbrains.kotlin.util.slicedMap.SetSlice, org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice, org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
        public Boolean computeValue(SlicedMap slicedMap, KtLambdaExpression ktLambdaExpression, Boolean bool, boolean z) {
            boolean z2 = false;
            if (Boolean.valueOf(z ? false : bool.booleanValue()).booleanValue() && !ktLambdaExpression.getFunctionLiteral().hasParameterSpecification()) {
                z2 = true;
            }
            return Boolean.valueOf(z2);
        }
    };

    void addOwnDataTo(BindingTrace bindingTrace, boolean z);

    <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k);

    Diagnostics getDiagnostics();

    <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice);

    <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> readOnlySlice);

    KotlinType getType(KtExpression ktExpression);

    static {
        WritableSlice<PsiElement, ClassDescriptor> writableSliceCreateSimpleSlice = Slices.createSimpleSlice();
        CLASS = writableSliceCreateSimpleSlice;
        WritableSlice<PsiElement, ScriptDescriptor> writableSliceCreateSimpleSlice2 = Slices.createSimpleSlice();
        SCRIPT = writableSliceCreateSimpleSlice2;
        WritableSlice<KtTypeParameter, TypeParameterDescriptor> writableSliceCreateSimpleSlice3 = Slices.createSimpleSlice();
        TYPE_PARAMETER = writableSliceCreateSimpleSlice3;
        WritableSlice<PsiElement, SimpleFunctionDescriptor> writableSliceCreateSimpleSlice4 = Slices.createSimpleSlice();
        FUNCTION = writableSliceCreateSimpleSlice4;
        WritableSlice<PsiElement, ConstructorDescriptor> writableSliceCreateSimpleSlice5 = Slices.createSimpleSlice();
        CONSTRUCTOR = writableSliceCreateSimpleSlice5;
        CONSTRUCTOR_RESOLVED_DELEGATION_CALL = Slices.createSimpleSlice();
        WritableSlice<PsiElement, VariableDescriptor> writableSliceCreateSimpleSlice6 = Slices.createSimpleSlice();
        VARIABLE = writableSliceCreateSimpleSlice6;
        WritableSlice<KtParameter, VariableDescriptor> writableSliceCreateSimpleSlice7 = Slices.createSimpleSlice();
        VALUE_PARAMETER = writableSliceCreateSimpleSlice7;
        WritableSlice<KtPropertyAccessor, PropertyAccessorDescriptor> writableSliceCreateSimpleSlice8 = Slices.createSimpleSlice();
        PROPERTY_ACCESSOR = writableSliceCreateSimpleSlice8;
        WritableSlice<PsiElement, PropertyDescriptor> writableSliceCreateSimpleSlice9 = Slices.createSimpleSlice();
        PRIMARY_CONSTRUCTOR_PARAMETER = writableSliceCreateSimpleSlice9;
        WritableSlice<PsiElement, TypeAliasDescriptor> writableSliceCreateSimpleSlice10 = Slices.createSimpleSlice();
        TYPE_ALIAS = writableSliceCreateSimpleSlice10;
        DEPRECATED_SHORT_NAME_ACCESS = Slices.createSimpleSlice();
        WritableSlice[] writableSliceArr = {writableSliceCreateSimpleSlice, writableSliceCreateSimpleSlice3, writableSliceCreateSimpleSlice4, writableSliceCreateSimpleSlice5, writableSliceCreateSimpleSlice6, writableSliceCreateSimpleSlice7, writableSliceCreateSimpleSlice8, writableSliceCreateSimpleSlice9, writableSliceCreateSimpleSlice2, writableSliceCreateSimpleSlice10};
        DECLARATIONS_TO_DESCRIPTORS = writableSliceArr;
        DECLARATION_TO_DESCRIPTOR = Slices.sliceBuilder().setFurtherLookupSlices(writableSliceArr).build();
        DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP = Slices.createSimpleSlice();
        LABEL_TARGET = Slices.createSimpleSlice();
        AMBIGUOUS_LABEL_TARGET = Slices.createSimpleSlice();
        VALUE_PARAMETER_AS_PROPERTY = Slices.createSimpleSlice();
        DATA_CLASS_COMPONENT_FUNCTION = Slices.createSimpleSlice();
        DATA_CLASS_COPY_FUNCTION = Slices.createSimpleSlice();
        FQNAME_TO_CLASS_DESCRIPTOR = new BasicWritableSlice(RewritePolicy.DO_NOTHING, true);
        PACKAGE_TO_FILES = Slices.createSimpleSlice();
        CAST_TYPE_USED_AS_EXPECTED_TYPE = Slices.createSimpleSlice();
        NEW_INFERENCE_LAMBDA_INFO = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
        PRIMITIVE_NUMERIC_COMPARISON_INFO = Slices.createSimpleSlice();
        NEW_INFERENCE_CATCH_EXCEPTION_PARAMETER = Slices.createSimpleSlice();
        NEW_INFERENCE_IS_LAMBDA_FOR_OVERLOAD_RESOLUTION_INLINE = Slices.createSimpleSlice();
        _static_initializer = BasicWritableSlice.initSliceDebugNames(BindingContext.class);
    }
}
