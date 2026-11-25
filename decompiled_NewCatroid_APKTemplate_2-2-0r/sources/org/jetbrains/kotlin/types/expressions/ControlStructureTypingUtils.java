package org.jetbrains.kotlin.types.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintsUtil;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class ControlStructureTypingUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ControlStructureTypingUtils.class);
    private final CallResolver callResolver;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final ModuleDescriptor moduleDescriptor;
    private final StorageManager storageManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "dataFlowAnalyzer";
                break;
            case 2:
            case 26:
                objArr[0] = "moduleDescriptor";
                break;
            case 3:
            case 27:
                objArr[0] = "storageManager";
                break;
            case 4:
            case 9:
            case 12:
            case 43:
                objArr[0] = "call";
                break;
            case 5:
            case 14:
            case 16:
            case 20:
            case 23:
                objArr[0] = "construct";
                break;
            case 6:
            case 21:
            case 24:
                objArr[0] = "argumentNames";
                break;
            case 7:
            case 22:
            case 25:
                objArr[0] = "isArgumentNullable";
                break;
            case 8:
            case 11:
            case 15:
            case 45:
                objArr[0] = "context";
                break;
            case 10:
                objArr[0] = "catchedExceptions";
                break;
            case 13:
            case 17:
                objArr[0] = "function";
                break;
            case 18:
                objArr[0] = "expectedType";
                break;
            case 19:
                objArr[0] = "languageVersionSettings";
                break;
            case 28:
                objArr[0] = "initialDataFlowInfo";
                break;
            case 29:
                objArr[0] = "dataFlowInfoForArgumentsMap";
                break;
            case 30:
                objArr[0] = "callForIf";
                break;
            case 31:
                objArr[0] = "conditionInfo";
                break;
            case 32:
                objArr[0] = "thenInfo";
                break;
            case 33:
                objArr[0] = "elseInfo";
                break;
            case 34:
                objArr[0] = "callForWhen";
                break;
            case 35:
                objArr[0] = "subjectDataFlowInfo";
                break;
            case 36:
                objArr[0] = "entryDataFlowInfos";
                break;
            case 37:
                objArr[0] = "callForTry";
                break;
            case 38:
                objArr[0] = "dataFlowInfoBeforeTry";
                break;
            case 39:
                objArr[0] = "dataFlowInfoAfterTry";
                break;
            case 40:
                objArr[0] = "expression";
                break;
            case 41:
                objArr[0] = "calleeExpression";
                break;
            case 42:
                objArr[0] = "arguments";
                break;
            case 44:
                objArr[0] = "constructionName";
                break;
            default:
                objArr[0] = "callResolver";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils";
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "resolveSpecialConstructionAsCall";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "resolveTryAsCall";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "createKnownTypeParameterSubstitutorForSpecialCall";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[2] = "createFunctionDescriptorForSpecialConstruction";
                break;
            case 28:
            case 29:
                objArr[2] = "createIndependentDataFlowInfoForArgumentsForCall";
                break;
            case 30:
            case 31:
            case 32:
            case 33:
                objArr[2] = "createDataFlowInfoForArgumentsForIfCall";
                break;
            case 34:
            case 35:
            case 36:
                objArr[2] = "createDataFlowInfoForArgumentsOfWhenCall";
                break;
            case 37:
            case 38:
            case 39:
                objArr[2] = "createDataFlowInfoForArgumentsOfTryCall";
                break;
            case 40:
            case 41:
            case 42:
                objArr[2] = "createCallForSpecialConstruction";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "createTracingForSpecialConstruction";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public enum ResolveConstruct {
        IF(PsiKeyword.IF),
        ELVIS("elvis"),
        EXCL_EXCL("ExclExcl"),
        WHEN("when"),
        TRY(PsiKeyword.TRY);


        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f983name;
        private final Name specialFunctionName;
        private final Name specialTypeParameterName;

        ResolveConstruct(String str) {
            this.f983name = str;
            this.specialFunctionName = Name.identifier("<SPECIAL-FUNCTION-FOR-" + str.toUpperCase() + "-RESOLVE>");
            this.specialTypeParameterName = Name.identifier("<TYPE-PARAMETER-FOR-" + str.toUpperCase() + "-RESOLVE>");
        }

        public String getName() {
            return this.f983name;
        }

        public Name getSpecialFunctionName() {
            return this.specialFunctionName;
        }

        public Name getSpecialTypeParameterName() {
            return this.specialTypeParameterName;
        }
    }

    public ControlStructureTypingUtils(CallResolver callResolver, DataFlowAnalyzer dataFlowAnalyzer, ModuleDescriptor moduleDescriptor, StorageManager storageManager) {
        if (callResolver == null) {
            $$$reportNull$$$0(0);
        }
        if (dataFlowAnalyzer == null) {
            $$$reportNull$$$0(1);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(3);
        }
        this.callResolver = callResolver;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.moduleDescriptor = moduleDescriptor;
        this.storageManager = storageManager;
    }

    ResolvedCall<FunctionDescriptor> resolveSpecialConstructionAsCall(Call call, ResolveConstruct resolveConstruct, List<String> list, List<Boolean> list2, ExpressionTypingContext expressionTypingContext, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (call == null) {
            $$$reportNull$$$0(4);
        }
        if (resolveConstruct == null) {
            $$$reportNull$$$0(5);
        }
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        if (list2 == null) {
            $$$reportNull$$$0(7);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(8);
        }
        return resolveSpecialConstructionAsCall(call, createFunctionDescriptorForSpecialConstruction(resolveConstruct, list, list2), resolveConstruct, expressionTypingContext, mutableDataFlowInfoForArguments);
    }

    ResolvedCall<FunctionDescriptor> resolveTryAsCall(Call call, List<Pair<KtExpression, VariableDescriptor>> list, ExpressionTypingContext expressionTypingContext, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (call == null) {
            $$$reportNull$$$0(9);
        }
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(11);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList("tryBlock");
        ArrayList arrayListNewArrayList2 = Lists.newArrayList(false);
        int i = 0;
        for (Pair<KtExpression, VariableDescriptor> pair : list) {
            arrayListNewArrayList.add("catchBlock" + i);
            arrayListNewArrayList2.add(false);
            expressionTypingContext.trace.record(BindingContext.NEW_INFERENCE_CATCH_EXCEPTION_PARAMETER, pair.getFirst(), Ref.create(pair.getSecond()));
            i++;
        }
        return resolveSpecialConstructionAsCall(call, createFunctionDescriptorForSpecialConstruction(ResolveConstruct.TRY, arrayListNewArrayList, arrayListNewArrayList2), ResolveConstruct.TRY, expressionTypingContext, mutableDataFlowInfoForArguments);
    }

    private ResolvedCall<FunctionDescriptor> resolveSpecialConstructionAsCall(Call call, SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl, ResolveConstruct resolveConstruct, ExpressionTypingContext expressionTypingContext, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (call == null) {
            $$$reportNull$$$0(12);
        }
        if (simpleFunctionDescriptorImpl == null) {
            $$$reportNull$$$0(13);
        }
        if (resolveConstruct == null) {
            $$$reportNull$$$0(14);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(15);
        }
        return this.callResolver.resolveCallWithKnownCandidate(call, createTracingForSpecialConstruction(call, resolveConstruct.getName(), expressionTypingContext), expressionTypingContext, OldResolutionCandidate.create(call, simpleFunctionDescriptorImpl, createKnownTypeParameterSubstitutorForSpecialCall(resolveConstruct, simpleFunctionDescriptorImpl, expressionTypingContext.expectedType, expressionTypingContext.languageVersionSettings)), mutableDataFlowInfoForArguments).mo7436getResultingCall();
    }

    private static TypeSubstitutor createKnownTypeParameterSubstitutorForSpecialCall(ResolveConstruct resolveConstruct, SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl, KotlinType kotlinType, LanguageVersionSettings languageVersionSettings) {
        if (resolveConstruct == null) {
            $$$reportNull$$$0(16);
        }
        if (simpleFunctionDescriptorImpl == null) {
            $$$reportNull$$$0(17);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(18);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(19);
        }
        if (resolveConstruct == ResolveConstruct.ELVIS || TypeUtils.noExpectedType(kotlinType) || TypeUtils.isDontCarePlaceholder(kotlinType) || KotlinBuiltIns.isUnitOrNullableUnit(kotlinType) || KotlinBuiltIns.isAnyOrNullableAny(kotlinType)) {
            return null;
        }
        return TypeSubstitutor.create(ImmutableMap.of(simpleFunctionDescriptorImpl.getTypeParameters().get(0).getTypeConstructor(), new TypeProjectionImpl(kotlinType)));
    }

    private SimpleFunctionDescriptorImpl createFunctionDescriptorForSpecialConstruction(ResolveConstruct resolveConstruct, List<String> list, List<Boolean> list2) {
        if (resolveConstruct == null) {
            $$$reportNull$$$0(20);
        }
        if (list == null) {
            $$$reportNull$$$0(21);
        }
        if (list2 == null) {
            $$$reportNull$$$0(22);
        }
        return createFunctionDescriptorForSpecialConstruction(resolveConstruct, list, list2, this.moduleDescriptor, this.storageManager);
    }

    public static SimpleFunctionDescriptorImpl createFunctionDescriptorForSpecialConstruction(ResolveConstruct resolveConstruct, List<String> list, List<Boolean> list2, ModuleDescriptor moduleDescriptor, StorageManager storageManager) {
        if (resolveConstruct == null) {
            $$$reportNull$$$0(23);
        }
        if (list == null) {
            $$$reportNull$$$0(24);
        }
        if (list2 == null) {
            $$$reportNull$$$0(25);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(27);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(moduleDescriptor, Annotations.INSTANCE.getEMPTY(), resolveConstruct.getSpecialFunctionName(), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        TypeParameterDescriptor typeParameterDescriptorCreateWithDefaultBound = TypeParameterDescriptorImpl.createWithDefaultBound(simpleFunctionDescriptorImplCreate, Annotations.INSTANCE.getEMPTY(), false, Variance.INVARIANT, resolveConstruct.getSpecialTypeParameterName(), 0, storageManager);
        SimpleType defaultType = typeParameterDescriptorCreateWithDefaultBound.getDefaultType();
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(defaultType);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new ValueParameterDescriptorImpl(simpleFunctionDescriptorImplCreate, null, i, Annotations.INSTANCE.getEMPTY(), Name.identifier(list.get(i)), list2.get(i).booleanValue() ? kotlinTypeMakeNullable : defaultType, false, false, false, null, SourceElement.NO_SOURCE));
        }
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, Collections.emptyList(), (List<? extends TypeParameterDescriptor>) Lists.newArrayList(typeParameterDescriptorCreateWithDefaultBound), (List<ValueParameterDescriptor>) arrayList, resolveConstruct != ResolveConstruct.ELVIS ? defaultType : TypeUtilsKt.replaceAnnotations(defaultType, AnnotationsForResolveUtilsKt.getExactInAnnotations()), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        return simpleFunctionDescriptorImplCreate;
    }

    public static class ControlStructureDataFlowInfo extends MutableDataFlowInfoForArguments {
        public final Map<ValueArgument, DataFlowInfo> dataFlowInfoForArgumentsMap;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 6 ? 3 : 2];
            switch (i) {
                case 1:
                    objArr[0] = "map";
                    break;
                case 2:
                case 5:
                    objArr[0] = "valueArgument";
                    break;
                case 3:
                case 4:
                    objArr[0] = "dataFlowInfo";
                    break;
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$ControlStructureDataFlowInfo";
                    break;
                default:
                    objArr[0] = "initialDataFlowInfo";
                    break;
            }
            if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$ControlStructureDataFlowInfo";
            } else {
                objArr[1] = "getInfo";
            }
            if (i == 2 || i == 3) {
                objArr[2] = "updateInfo";
            } else if (i == 4) {
                objArr[2] = "updateResultInfo";
            } else if (i == 5) {
                objArr[2] = "getInfo";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 6) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
        public void updateResultInfo(DataFlowInfo dataFlowInfo) {
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(4);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ControlStructureDataFlowInfo(DataFlowInfo dataFlowInfo, Map<ValueArgument, DataFlowInfo> map) {
            super(dataFlowInfo);
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(0);
            }
            if (map == null) {
                $$$reportNull$$$0(1);
            }
            this.dataFlowInfoForArgumentsMap = map;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments
        public void updateInfo(ValueArgument valueArgument, DataFlowInfo dataFlowInfo) {
            if (valueArgument == null) {
                $$$reportNull$$$0(2);
            }
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(3);
            }
            this.dataFlowInfoForArgumentsMap.put(valueArgument, dataFlowInfo);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
        public DataFlowInfo getInfo(ValueArgument valueArgument) {
            if (valueArgument == null) {
                $$$reportNull$$$0(5);
            }
            DataFlowInfo dataFlowInfo = this.dataFlowInfoForArgumentsMap.get(valueArgument);
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(6);
            }
            return dataFlowInfo;
        }
    }

    private static MutableDataFlowInfoForArguments createIndependentDataFlowInfoForArgumentsForCall(DataFlowInfo dataFlowInfo, Map<ValueArgument, DataFlowInfo> map) {
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(28);
        }
        if (map == null) {
            $$$reportNull$$$0(29);
        }
        return new ControlStructureDataFlowInfo(dataFlowInfo, map);
    }

    public static MutableDataFlowInfoForArguments createDataFlowInfoForArgumentsForIfCall(Call call, DataFlowInfo dataFlowInfo, DataFlowInfo dataFlowInfo2, DataFlowInfo dataFlowInfo3) {
        if (call == null) {
            $$$reportNull$$$0(30);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(31);
        }
        if (dataFlowInfo2 == null) {
            $$$reportNull$$$0(32);
        }
        if (dataFlowInfo3 == null) {
            $$$reportNull$$$0(33);
        }
        HashMap map = new HashMap();
        map.put(call.getValueArguments().get(0), dataFlowInfo2);
        map.put(call.getValueArguments().get(1), dataFlowInfo3);
        return createIndependentDataFlowInfoForArgumentsForCall(dataFlowInfo, map);
    }

    public static MutableDataFlowInfoForArguments createDataFlowInfoForArgumentsOfWhenCall(Call call, DataFlowInfo dataFlowInfo, List<DataFlowInfo> list) {
        if (call == null) {
            $$$reportNull$$$0(34);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(35);
        }
        if (list == null) {
            $$$reportNull$$$0(36);
        }
        HashMap map = new HashMap();
        Iterator<? extends ValueArgument> it = call.getValueArguments().iterator();
        int i = 0;
        while (it.hasNext()) {
            map.put(it.next(), list.get(i));
            i++;
        }
        return createIndependentDataFlowInfoForArgumentsForCall(dataFlowInfo, map);
    }

    public static MutableDataFlowInfoForArguments createDataFlowInfoForArgumentsOfTryCall(Call call, DataFlowInfo dataFlowInfo, DataFlowInfo dataFlowInfo2) {
        if (call == null) {
            $$$reportNull$$$0(37);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(38);
        }
        if (dataFlowInfo2 == null) {
            $$$reportNull$$$0(39);
        }
        HashMap map = new HashMap();
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        map.put(valueArguments.get(0), dataFlowInfo);
        for (int i = 1; i < valueArguments.size(); i++) {
            map.put(valueArguments.get(i), dataFlowInfo2);
        }
        return createIndependentDataFlowInfoForArgumentsForCall(dataFlowInfo, map);
    }

    public static Call createCallForSpecialConstruction(final KtExpression ktExpression, final KtExpression ktExpression2, List<? extends KtExpression> list) {
        if (ktExpression == null) {
            $$$reportNull$$$0(40);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(41);
        }
        if (list == null) {
            $$$reportNull$$$0(42);
        }
        final ArrayList arrayListNewArrayList = Lists.newArrayList();
        Iterator<? extends KtExpression> it = list.iterator();
        while (it.hasNext()) {
            arrayListNewArrayList.add(CallMaker.makeValueArgument(it.next()));
        }
        return new Call() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$1";
                if (i == 1) {
                    objArr[1] = "getFunctionLiteralArguments";
                } else if (i == 2) {
                    objArr[1] = "getTypeArguments";
                } else if (i == 3) {
                    objArr[1] = "getCallElement";
                } else if (i != 4) {
                    objArr[1] = "getValueArguments";
                } else {
                    objArr[1] = "getCallType";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ReceiverValue getDispatchReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ReceiverValue getExplicitReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtTypeArgumentList getTypeArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtValueArgumentList getValueArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ASTNode getCallOperationNode() {
                return ktExpression.getNode();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtExpression getCalleeExpression() {
                return ktExpression2;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<? extends ValueArgument> getValueArguments() {
                List<? extends ValueArgument> list2 = arrayListNewArrayList;
                if (list2 == null) {
                    $$$reportNull$$$0(0);
                }
                return list2;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<LambdaArgument> getFunctionLiteralArguments() {
                List<LambdaArgument> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(1);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<KtTypeProjection> getTypeArguments() {
                List<KtTypeProjection> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(2);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtElement getCallElement() {
                KtExpression ktExpression3 = ktExpression;
                if (ktExpression3 == null) {
                    $$$reportNull$$$0(3);
                }
                return ktExpression3;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public Call.CallType getCallType() {
                Call.CallType callType = Call.CallType.DEFAULT;
                if (callType == null) {
                    $$$reportNull$$$0(4);
                }
                return callType;
            }
        };
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils$1CheckTypeContext, reason: invalid class name */
    class C1CheckTypeContext {
        public KotlinType expectedType;
        final /* synthetic */ ControlStructureTypingUtils this$0;
        public BindingTrace trace;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "trace";
            } else {
                objArr[0] = "expectedType";
            }
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$1CheckTypeContext";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        C1CheckTypeContext(ControlStructureTypingUtils controlStructureTypingUtils, BindingTrace bindingTrace, KotlinType kotlinType) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(0);
            }
            if (kotlinType == null) {
                $$$reportNull$$$0(1);
            }
            this.this$0 = controlStructureTypingUtils;
            this.trace = bindingTrace;
            this.expectedType = kotlinType;
        }

        C1CheckTypeContext makeTypeNullable() {
            return TypeUtils.noExpectedType(this.expectedType) ? this : new C1CheckTypeContext(this.this$0, this.trace, TypeUtils.makeNullable(this.expectedType));
        }
    }

    private TracingStrategy createTracingForSpecialConstruction(final Call call, String str, final ExpressionTypingContext expressionTypingContext) {
        if (call == null) {
            $$$reportNull$$$0(43);
        }
        if (str == null) {
            $$$reportNull$$$0(44);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(45);
        }
        final KtVisitor<Boolean, C1CheckTypeContext> ktVisitor = new KtVisitor<Boolean, C1CheckTypeContext>() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "whenExpression";
                } else if (i != 2) {
                    objArr[0] = "expression";
                } else {
                    objArr[0] = "ifExpression";
                }
                objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$2";
                switch (i) {
                    case 1:
                        objArr[2] = "visitWhenExpression";
                        break;
                    case 2:
                        objArr[2] = "visitIfExpression";
                        break;
                    case 3:
                        objArr[2] = "visitBlockExpression";
                        break;
                    case 4:
                        objArr[2] = "visitPostfixExpression";
                        break;
                    case 5:
                        objArr[2] = "visitBinaryExpression";
                        break;
                    case 6:
                        objArr[2] = "visitExpression";
                        break;
                    default:
                        objArr[2] = "checkExpressionType";
                        break;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            private boolean checkExpressionType(KtExpression ktExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktExpression == null) {
                    $$$reportNull$$$0(0);
                }
                KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktExpression, c1CheckTypeContext.trace.getContext());
                if (recordedTypeInfo == null) {
                    return false;
                }
                Ref<Boolean> refCreate = Ref.create();
                ControlStructureTypingUtils.this.dataFlowAnalyzer.checkType(recordedTypeInfo.getType(), ktExpression, expressionTypingContext.replaceExpectedType(c1CheckTypeContext.expectedType).replaceDataFlowInfo(recordedTypeInfo.getDataFlowInfo()).replaceBindingTrace(c1CheckTypeContext.trace), refCreate, true);
                return refCreate.get().booleanValue();
            }

            private boolean checkExpressionTypeRecursively(KtExpression ktExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktExpression == null) {
                    return false;
                }
                return ((Boolean) ktExpression.accept(this, c1CheckTypeContext)).booleanValue();
            }

            private boolean checkSubExpressions(KtExpression ktExpression, KtExpression ktExpression2, KtExpression ktExpression3, C1CheckTypeContext c1CheckTypeContext, C1CheckTypeContext c1CheckTypeContext2, C1CheckTypeContext c1CheckTypeContext3) {
                return checkExpressionTypeRecursively(ktExpression, c1CheckTypeContext) || checkExpressionTypeRecursively(ktExpression2, c1CheckTypeContext2) || checkExpressionType(ktExpression3, c1CheckTypeContext3);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitWhenExpression(KtWhenExpression ktWhenExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktWhenExpression == null) {
                    $$$reportNull$$$0(1);
                }
                Iterator<KtWhenEntry> it = ktWhenExpression.getEntries().iterator();
                boolean zCheckExpressionTypeRecursively = false;
                while (it.hasNext()) {
                    KtExpression expression = it.next().getExpression();
                    if (expression != null) {
                        zCheckExpressionTypeRecursively |= checkExpressionTypeRecursively(expression, c1CheckTypeContext);
                    }
                }
                return Boolean.valueOf(checkExpressionType(ktWhenExpression, c1CheckTypeContext) | zCheckExpressionTypeRecursively);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitIfExpression(KtIfExpression ktIfExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktIfExpression == null) {
                    $$$reportNull$$$0(2);
                }
                KtExpression then = ktIfExpression.getThen();
                KtExpression ktExpression = ktIfExpression.getElse();
                if (then == null || ktExpression == null) {
                    return Boolean.valueOf(checkExpressionType(ktIfExpression, c1CheckTypeContext));
                }
                return Boolean.valueOf(checkSubExpressions(then, ktExpression, ktIfExpression, c1CheckTypeContext, c1CheckTypeContext, c1CheckTypeContext));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitBlockExpression(KtBlockExpression ktBlockExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktBlockExpression == null) {
                    $$$reportNull$$$0(3);
                }
                if (ktBlockExpression.getStatements().isEmpty()) {
                    return Boolean.valueOf(checkExpressionType(ktBlockExpression, c1CheckTypeContext));
                }
                KtExpression lastStatementInABlock = KtPsiUtil.getLastStatementInABlock(ktBlockExpression);
                if (lastStatementInABlock != null) {
                    return Boolean.valueOf(checkExpressionTypeRecursively(lastStatementInABlock, c1CheckTypeContext));
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitPostfixExpression(KtPostfixExpression ktPostfixExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktPostfixExpression == null) {
                    $$$reportNull$$$0(4);
                }
                if (ktPostfixExpression.getOperationReference().getReferencedNameElementType() == KtTokens.EXCLEXCL) {
                    return Boolean.valueOf(checkExpressionTypeRecursively(ktPostfixExpression.getBaseExpression(), c1CheckTypeContext.makeTypeNullable()));
                }
                return (Boolean) super.visitPostfixExpression(ktPostfixExpression, c1CheckTypeContext);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitBinaryExpression(KtBinaryExpression ktBinaryExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktBinaryExpression == null) {
                    $$$reportNull$$$0(5);
                }
                if (ktBinaryExpression.getOperationReference().getReferencedNameElementType() == KtTokens.ELVIS) {
                    return Boolean.valueOf(checkSubExpressions(ktBinaryExpression.getLeft(), ktBinaryExpression.getRight(), ktBinaryExpression, c1CheckTypeContext.makeTypeNullable(), c1CheckTypeContext, c1CheckTypeContext));
                }
                return (Boolean) super.visitBinaryExpression(ktBinaryExpression, c1CheckTypeContext);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Boolean visitExpression(KtExpression ktExpression, C1CheckTypeContext c1CheckTypeContext) {
                if (ktExpression == null) {
                    $$$reportNull$$$0(6);
                }
                return Boolean.valueOf(checkExpressionType(ktExpression, c1CheckTypeContext));
            }
        };
        return new ThrowingOnErrorTracingStrategy("resolve " + str + " as a call") { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils.3
            static final /* synthetic */ boolean $assertionsDisabled = false;

            /* JADX WARN: Code duplicated, block: B:18:0x002f  */
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "resolvedCall";
                } else if (i == 3) {
                    objArr[0] = "call";
                } else if (i == 9) {
                    objArr[0] = "expectedType";
                } else if (i == 5) {
                    objArr[0] = "resolvedCall";
                } else if (i == 6) {
                    objArr[0] = "context";
                } else if (i != 7) {
                    objArr[0] = "trace";
                } else {
                    objArr[0] = "data";
                }
                objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$3";
                switch (i) {
                    case 2:
                    case 3:
                        objArr[2] = "bindCall";
                        break;
                    case 4:
                    case 5:
                        objArr[2] = "bindResolvedCall";
                        break;
                    case 6:
                    case 7:
                        objArr[2] = "typeInferenceFailed";
                        break;
                    case 8:
                    case 9:
                        objArr[2] = "noTypeCheckingErrorsInExpression";
                        break;
                    default:
                        objArr[2] = "bindReference";
                        break;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
            public <D extends CallableDescriptor> void bindReference(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
                if (bindingTrace == null) {
                    $$$reportNull$$$0(0);
                }
                if (resolvedCall == null) {
                    $$$reportNull$$$0(1);
                }
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
            public void bindCall(BindingTrace bindingTrace, Call call2) {
                if (bindingTrace == null) {
                    $$$reportNull$$$0(2);
                }
                if (call2 == null) {
                    $$$reportNull$$$0(3);
                }
                bindingTrace.record(BindingContext.CALL, call2.getCalleeExpression(), call2);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
            public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
                if (bindingTrace == null) {
                    $$$reportNull$$$0(4);
                }
                if (resolvedCall == null) {
                    $$$reportNull$$$0(5);
                }
                bindingTrace.record(BindingContext.RESOLVED_CALL, call, resolvedCall);
            }

            @Override // org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils.ThrowingOnErrorTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
            public void typeInferenceFailed(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData) {
                if (resolutionContext == null) {
                    $$$reportNull$$$0(6);
                }
                if (inferenceErrorData == null) {
                    $$$reportNull$$$0(7);
                }
                ConstraintSystemStatus status = inferenceErrorData.constraintSystem.getStatus();
                if (status.hasErrorInConstrainingTypes() || status.hasUnknownParameters()) {
                    return;
                }
                KtExpression ktExpression = (KtExpression) call.getCallElement();
                if (status.hasOnlyErrorsDerivedFrom(ConstraintPositionKind.EXPECTED_TYPE_POSITION) || status.hasConflictingConstraints() || status.hasTypeInferenceIncorporationError()) {
                    if (noTypeCheckingErrorsInExpression(ktExpression, resolutionContext.trace, inferenceErrorData.expectedType)) {
                        KtExpression calleeExpression = call.getCalleeExpression();
                        if ((calleeExpression instanceof KtWhenExpression) || (calleeExpression instanceof KtIfExpression)) {
                            if (status.hasConflictingConstraints() || status.hasTypeInferenceIncorporationError()) {
                                resolutionContext.trace.report(Errors.TYPE_INFERENCE_FAILED_ON_SPECIAL_CONSTRUCT.on(ktExpression));
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                KtDeclaration ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(ktExpression, KtNamedDeclaration.class);
                logError("Expression: " + (ktDeclaration != null ? ktDeclaration.getText() : ktExpression.getText()) + "\nConstraint system status: \n" + ConstraintsUtil.getDebugMessageForStatus(status));
            }

            private boolean noTypeCheckingErrorsInExpression(KtExpression ktExpression, BindingTrace bindingTrace, KotlinType kotlinType) {
                if (bindingTrace == null) {
                    $$$reportNull$$$0(8);
                }
                if (kotlinType == null) {
                    $$$reportNull$$$0(9);
                }
                return Boolean.TRUE != ktExpression.accept(ktVisitor, new C1CheckTypeContext(ControlStructureTypingUtils.this, bindingTrace, kotlinType));
            }
        };
    }

    private static abstract class ThrowingOnErrorTracingStrategy implements TracingStrategy {
        private final String debugName;

        /* JADX WARN: Code duplicated, block: B:39:0x006e  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2) {
                objArr[0] = "languageVersionSettings";
            } else if (i == 4 || i == 6) {
                objArr[0] = "candidates";
            } else if (i == 8) {
                objArr[0] = "expectedReceiver";
            } else if (i == 15) {
                objArr[0] = "valueParameter";
            } else if (i == 17) {
                objArr[0] = "descriptor";
            } else if (i == 19) {
                objArr[0] = "resolvedCalls";
            } else if (i == 21 || i == 23) {
                objArr[0] = "descriptors";
            } else if (i == 30) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else if (i == 27) {
                objArr[0] = "classDescriptor";
            } else if (i != 28) {
                switch (i) {
                    case 10:
                        objArr[0] = "receiverParameter";
                        break;
                    case 11:
                        objArr[0] = "receiverArgument";
                        break;
                    case 12:
                        objArr[0] = "c";
                        break;
                    default:
                        switch (i) {
                            case 32:
                                objArr[0] = "descriptor";
                                break;
                            case 33:
                                objArr[0] = "context";
                                break;
                            case 34:
                                objArr[0] = "inferenceErrorData";
                                break;
                            default:
                                objArr[0] = "trace";
                                break;
                        }
                        break;
                }
            } else {
                objArr[0] = "explicitReceiverKind";
            }
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingUtils$ThrowingOnErrorTracingStrategy";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "recursiveType";
                    break;
                case 3:
                case 4:
                    objArr[2] = "unresolvedReferenceWrongReceiver";
                    break;
                case 5:
                case 6:
                    objArr[2] = "recordAmbiguity";
                    break;
                case 7:
                case 8:
                    objArr[2] = "missingReceiver";
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                    objArr[2] = "wrongReceiverType";
                    break;
                case 13:
                    objArr[2] = "noReceiverAllowed";
                    break;
                case 14:
                case 15:
                    objArr[2] = "noValueForParameter";
                    break;
                case 16:
                case 17:
                    objArr[2] = "wrongNumberOfTypeArguments";
                    break;
                case 18:
                case 19:
                    objArr[2] = "ambiguity";
                    break;
                case 20:
                case 21:
                    objArr[2] = "noneApplicable";
                    break;
                case 22:
                case 23:
                    objArr[2] = "cannotCompleteResolve";
                    break;
                case 24:
                    objArr[2] = "instantiationOfAbstractClass";
                    break;
                case 25:
                    objArr[2] = "abstractSuperCall";
                    break;
                case 26:
                case 27:
                case 28:
                    objArr[2] = "nestedClassAccessViaInstanceReference";
                    break;
                case 29:
                case 30:
                    objArr[2] = "unsafeCall";
                    break;
                case 31:
                case 32:
                    objArr[2] = "invisibleMember";
                    break;
                case 33:
                case 34:
                    objArr[2] = "typeInferenceFailed";
                    break;
                default:
                    objArr[2] = "unresolvedReference";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        protected ThrowingOnErrorTracingStrategy(String str) {
            this.debugName = str;
        }

        private void logError() {
            logError(null);
        }

        protected void logError(String str) {
            String str2 = "Resolution error of this type shouldn't occur for " + this.debugName;
            if (str != null) {
                str2 = str2 + ".\n" + str;
            }
            ControlStructureTypingUtils.LOG.error(str2);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unresolvedReference(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(0);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void recursiveType(BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, boolean z) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(1);
            }
            if (languageVersionSettings == null) {
                $$$reportNull$$$0(2);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(3);
            }
            if (collection == null) {
                $$$reportNull$$$0(4);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void recordAmbiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(5);
            }
            if (collection == null) {
                $$$reportNull$$$0(6);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void missingReceiver(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(7);
            }
            if (receiverParameterDescriptor == null) {
                $$$reportNull$$$0(8);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongReceiverType(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverValue receiverValue, ResolutionContext<?> resolutionContext) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(9);
            }
            if (receiverParameterDescriptor == null) {
                $$$reportNull$$$0(10);
            }
            if (receiverValue == null) {
                $$$reportNull$$$0(11);
            }
            if (resolutionContext == null) {
                $$$reportNull$$$0(12);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noReceiverAllowed(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(13);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noValueForParameter(BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(14);
            }
            if (valueParameterDescriptor == null) {
                $$$reportNull$$$0(15);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongNumberOfTypeArguments(BindingTrace bindingTrace, int i, CallableDescriptor callableDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(16);
            }
            if (callableDescriptor == null) {
                $$$reportNull$$$0(17);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void ambiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(18);
            }
            if (collection == null) {
                $$$reportNull$$$0(19);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void noneApplicable(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(20);
            }
            if (collection == null) {
                $$$reportNull$$$0(21);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(22);
            }
            if (collection == null) {
                $$$reportNull$$$0(23);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void instantiationOfAbstractClass(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(24);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void abstractSuperCall(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(25);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void nestedClassAccessViaInstanceReference(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(26);
            }
            if (classDescriptor == null) {
                $$$reportNull$$$0(27);
            }
            if (explicitReceiverKind == null) {
                $$$reportNull$$$0(28);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unsafeCall(BindingTrace bindingTrace, KotlinType kotlinType, boolean z) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(29);
            }
            if (kotlinType == null) {
                $$$reportNull$$$0(30);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void invisibleMember(BindingTrace bindingTrace, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(31);
            }
            if (declarationDescriptorWithVisibility == null) {
                $$$reportNull$$$0(32);
            }
            logError();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void typeInferenceFailed(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData) {
            if (resolutionContext == null) {
                $$$reportNull$$$0(33);
            }
            if (inferenceErrorData == null) {
                $$$reportNull$$$0(34);
            }
            logError();
        }
    }
}
