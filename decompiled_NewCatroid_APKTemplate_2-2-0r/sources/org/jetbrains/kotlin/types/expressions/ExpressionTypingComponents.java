package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.GlobalContext;
import org.jetbrains.kotlin.contracts.EffectSystem;
import org.jetbrains.kotlin.contracts.parsing.ContractParsingServices;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.extensions.internal.TypeResolutionInterceptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.CollectionLiteralResolver;
import org.jetbrains.kotlin.resolve.DeclarationsCheckerBuilder;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.IdentifierChecker;
import org.jetbrains.kotlin.resolve.LocalVariableResolver;
import org.jetbrains.kotlin.resolve.MissingSupertypesResolver;
import org.jetbrains.kotlin.resolve.ModifiersChecker;
import org.jetbrains.kotlin.resolve.OverloadChecker;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallExpressionResolver;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.AssignmentChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionChecker;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionTypingComponents {
    public AnnotationChecker annotationChecker;
    public AnnotationResolver annotationResolver;
    public Iterable<AssignmentChecker> assignmentCheckers;
    public KotlinBuiltIns builtIns;
    public Iterable<CallChecker> callCheckers;
    public KotlinCallComponents callComponents;
    public CallExpressionResolver callExpressionResolver;
    public CallResolver callResolver;
    public CollectionLiteralResolver collectionLiteralResolver;
    public ConstantExpressionEvaluator constantExpressionEvaluator;
    public ContractParsingServices contractParsingServices;
    public ControlStructureTypingUtils controlStructureTypingUtils;
    public DataFlowAnalyzer dataFlowAnalyzer;
    public DataFlowValueFactory dataFlowValueFactory;
    public DeclarationsCheckerBuilder declarationsCheckerBuilder;
    public DeprecationResolver deprecationResolver;
    public DescriptorResolver descriptorResolver;
    public DestructuringDeclarationResolver destructuringDeclarationResolver;
    public DoubleColonExpressionResolver doubleColonExpressionResolver;
    public EffectSystem effectSystem;
    public ExpressionTypingServices expressionTypingServices;
    public FakeCallResolver fakeCallResolver;
    public ForLoopConventionsChecker forLoopConventionsChecker;
    public FunctionDescriptorResolver functionDescriptorResolver;
    public GlobalContext globalContext;
    public IdentifierChecker identifierChecker;
    public NewKotlinTypeChecker kotlinTypeChecker;
    public LanguageVersionSettings languageVersionSettings;
    public LocalClassifierAnalyzer localClassifierAnalyzer;
    public LocalVariableResolver localVariableResolver;
    public LookupTracker lookupTracker;
    public MissingSupertypesResolver missingSupertypesResolver;
    public ModifiersChecker modifiersChecker;
    public ModuleDescriptor moduleDescriptor;
    public OverloadChecker overloadChecker;
    public PlatformSpecificCastChecker platformSpecificCastChecker;
    public PlatformToKotlinClassMapper platformToKotlinClassMapper;
    public Iterable<RttiExpressionChecker> rttiExpressionCheckers;
    public TypeResolutionInterceptor typeResolutionInterceptor;
    public TypeResolver typeResolver;
    public ValueParameterResolver valueParameterResolver;
    public WrappedTypeFactory wrappedTypeFactory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "moduleDescriptor";
                break;
            case 2:
                objArr[0] = "expressionTypingServices";
                break;
            case 3:
                objArr[0] = "callResolver";
                break;
            case 4:
                objArr[0] = "platformToKotlinClassMapper";
                break;
            case 5:
                objArr[0] = "platformSpecificCastChecker";
                break;
            case 6:
                objArr[0] = "controlStructureTypingUtils";
                break;
            case 7:
                objArr[0] = "forLoopConventionsChecker";
                break;
            case 8:
                objArr[0] = "fakeCallResolver";
                break;
            case 9:
                objArr[0] = "builtIns";
                break;
            case 10:
                objArr[0] = "localClassifierAnalyzer";
                break;
            case 11:
                objArr[0] = "constantExpressionEvaluator";
                break;
            case 12:
                objArr[0] = "modifiersChecker";
                break;
            case 13:
                objArr[0] = "identifierChecker";
                break;
            case 14:
                objArr[0] = "dataFlowAnalyzer";
                break;
            case 15:
                objArr[0] = "callCheckers";
                break;
            case 16:
                objArr[0] = "assignmentCheckers";
                break;
            case 17:
                objArr[0] = "declarationsCheckerBuilder";
                break;
            case 18:
                objArr[0] = "localVariableResolver";
                break;
            case 19:
                objArr[0] = "lookupTracker";
                break;
            case 20:
                objArr[0] = "languageVersionSettings";
                break;
            case 21:
                objArr[0] = "rttiExpressionCheckers";
                break;
            case 22:
                objArr[0] = "effectSystem";
                break;
            case 23:
                objArr[0] = "contractParsingServices";
                break;
            case 24:
                objArr[0] = "dataFlowValueFactory";
                break;
            case 25:
                objArr[0] = "kotlinTypeChecker";
                break;
            case 26:
                objArr[0] = "typeResolutionInterceptor";
                break;
            case 27:
                objArr[0] = "missingSupertypesResolver";
                break;
            case 28:
                objArr[0] = "annotationChecker";
                break;
            case 29:
                objArr[0] = "callComponents";
                break;
            default:
                objArr[0] = "globalContext";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingComponents";
        switch (i) {
            case 1:
                objArr[2] = "setModuleDescriptor";
                break;
            case 2:
                objArr[2] = "setExpressionTypingServices";
                break;
            case 3:
                objArr[2] = "setCallResolver";
                break;
            case 4:
            case 5:
                objArr[2] = "setPlatformToKotlinClassMap";
                break;
            case 6:
                objArr[2] = "setControlStructureTypingUtils";
                break;
            case 7:
                objArr[2] = "setForLoopConventionsChecker";
                break;
            case 8:
                objArr[2] = "setFakeCallResolver";
                break;
            case 9:
                objArr[2] = "setBuiltIns";
                break;
            case 10:
                objArr[2] = "setLocalClassifierAnalyzer";
                break;
            case 11:
                objArr[2] = "setConstantExpressionEvaluator";
                break;
            case 12:
                objArr[2] = "setModifiersChecker";
                break;
            case 13:
                objArr[2] = "setIdentifierChecker";
                break;
            case 14:
                objArr[2] = "setDataFlowAnalyzer";
                break;
            case 15:
                objArr[2] = "setCallCheckers";
                break;
            case 16:
                objArr[2] = "setAssignmentCheckers";
                break;
            case 17:
                objArr[2] = "setDeclarationsCheckerBuilder";
                break;
            case 18:
                objArr[2] = "setLocalVariableResolver";
                break;
            case 19:
                objArr[2] = "setLookupTracker";
                break;
            case 20:
                objArr[2] = "setLanguageVersionSettings";
                break;
            case 21:
                objArr[2] = "setRttiExpressionCheckers";
                break;
            case 22:
                objArr[2] = "setEffectSystem";
                break;
            case 23:
                objArr[2] = "setContractParsingServices";
                break;
            case 24:
                objArr[2] = "setDataFlowValueFactory";
                break;
            case 25:
                objArr[2] = "setKotlinTypeChecker";
                break;
            case 26:
                objArr[2] = "setTypeResolutionInterceptor";
                break;
            case 27:
                objArr[2] = "setMissingSupertypesResolver";
                break;
            case 28:
                objArr[2] = "setAnnotationChecker";
                break;
            case 29:
                objArr[2] = "setCallComponents";
                break;
            default:
                objArr[2] = "setGlobalContext";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Inject
    public void setGlobalContext(GlobalContext globalContext) {
        if (globalContext == null) {
            $$$reportNull$$$0(0);
        }
        this.globalContext = globalContext;
    }

    @Inject
    public void setModuleDescriptor(ModuleDescriptor moduleDescriptor) {
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        this.moduleDescriptor = moduleDescriptor;
    }

    @Inject
    public void setExpressionTypingServices(ExpressionTypingServices expressionTypingServices) {
        if (expressionTypingServices == null) {
            $$$reportNull$$$0(2);
        }
        this.expressionTypingServices = expressionTypingServices;
    }

    @Inject
    public void setCallResolver(CallResolver callResolver) {
        if (callResolver == null) {
            $$$reportNull$$$0(3);
        }
        this.callResolver = callResolver;
    }

    @Inject
    public void setPlatformToKotlinClassMap(PlatformToKotlinClassMapper platformToKotlinClassMapper) {
        if (platformToKotlinClassMapper == null) {
            $$$reportNull$$$0(4);
        }
        this.platformToKotlinClassMapper = platformToKotlinClassMapper;
    }

    @Inject
    public void setPlatformToKotlinClassMap(PlatformSpecificCastChecker platformSpecificCastChecker) {
        if (platformSpecificCastChecker == null) {
            $$$reportNull$$$0(5);
        }
        this.platformSpecificCastChecker = platformSpecificCastChecker;
    }

    @Inject
    public void setControlStructureTypingUtils(ControlStructureTypingUtils controlStructureTypingUtils) {
        if (controlStructureTypingUtils == null) {
            $$$reportNull$$$0(6);
        }
        this.controlStructureTypingUtils = controlStructureTypingUtils;
    }

    @Inject
    public void setForLoopConventionsChecker(ForLoopConventionsChecker forLoopConventionsChecker) {
        if (forLoopConventionsChecker == null) {
            $$$reportNull$$$0(7);
        }
        this.forLoopConventionsChecker = forLoopConventionsChecker;
    }

    @Inject
    public void setFakeCallResolver(FakeCallResolver fakeCallResolver) {
        if (fakeCallResolver == null) {
            $$$reportNull$$$0(8);
        }
        this.fakeCallResolver = fakeCallResolver;
    }

    @Inject
    public void setBuiltIns(KotlinBuiltIns kotlinBuiltIns) {
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(9);
        }
        this.builtIns = kotlinBuiltIns;
    }

    @Inject
    public void setLocalClassifierAnalyzer(LocalClassifierAnalyzer localClassifierAnalyzer) {
        if (localClassifierAnalyzer == null) {
            $$$reportNull$$$0(10);
        }
        this.localClassifierAnalyzer = localClassifierAnalyzer;
    }

    @Inject
    public void setFunctionDescriptorResolver(FunctionDescriptorResolver functionDescriptorResolver) {
        this.functionDescriptorResolver = functionDescriptorResolver;
    }

    @Inject
    public void setCallExpressionResolver(CallExpressionResolver callExpressionResolver) {
        this.callExpressionResolver = callExpressionResolver;
    }

    @Inject
    public void setDoubleColonExpressionResolver(DoubleColonExpressionResolver doubleColonExpressionResolver) {
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
    }

    @Inject
    public void setDescriptorResolver(DescriptorResolver descriptorResolver) {
        this.descriptorResolver = descriptorResolver;
    }

    @Inject
    public void setTypeResolver(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Inject
    public void setAnnotationResolver(AnnotationResolver annotationResolver) {
        this.annotationResolver = annotationResolver;
    }

    @Inject
    public void setValueParameterResolver(ValueParameterResolver valueParameterResolver) {
        this.valueParameterResolver = valueParameterResolver;
    }

    @Inject
    public void setDestructuringDeclarationResolver(DestructuringDeclarationResolver destructuringDeclarationResolver) {
        this.destructuringDeclarationResolver = destructuringDeclarationResolver;
    }

    @Inject
    public void setConstantExpressionEvaluator(ConstantExpressionEvaluator constantExpressionEvaluator) {
        if (constantExpressionEvaluator == null) {
            $$$reportNull$$$0(11);
        }
        this.constantExpressionEvaluator = constantExpressionEvaluator;
    }

    @Inject
    public void setModifiersChecker(ModifiersChecker modifiersChecker) {
        if (modifiersChecker == null) {
            $$$reportNull$$$0(12);
        }
        this.modifiersChecker = modifiersChecker;
    }

    @Inject
    public void setIdentifierChecker(IdentifierChecker identifierChecker) {
        if (identifierChecker == null) {
            $$$reportNull$$$0(13);
        }
        this.identifierChecker = identifierChecker;
    }

    @Inject
    public void setDataFlowAnalyzer(DataFlowAnalyzer dataFlowAnalyzer) {
        if (dataFlowAnalyzer == null) {
            $$$reportNull$$$0(14);
        }
        this.dataFlowAnalyzer = dataFlowAnalyzer;
    }

    @Inject
    public void setCallCheckers(Iterable<CallChecker> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(15);
        }
        this.callCheckers = iterable;
    }

    @Inject
    public void setAssignmentCheckers(Iterable<AssignmentChecker> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(16);
        }
        this.assignmentCheckers = iterable;
    }

    @Inject
    public void setDeclarationsCheckerBuilder(DeclarationsCheckerBuilder declarationsCheckerBuilder) {
        if (declarationsCheckerBuilder == null) {
            $$$reportNull$$$0(17);
        }
        this.declarationsCheckerBuilder = declarationsCheckerBuilder;
    }

    @Inject
    public void setLocalVariableResolver(LocalVariableResolver localVariableResolver) {
        if (localVariableResolver == null) {
            $$$reportNull$$$0(18);
        }
        this.localVariableResolver = localVariableResolver;
    }

    @Inject
    public void setLookupTracker(LookupTracker lookupTracker) {
        if (lookupTracker == null) {
            $$$reportNull$$$0(19);
        }
        this.lookupTracker = lookupTracker;
    }

    @Inject
    public void setOverloadChecker(OverloadChecker overloadChecker) {
        this.overloadChecker = overloadChecker;
    }

    @Inject
    public void setLanguageVersionSettings(LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(20);
        }
        this.languageVersionSettings = languageVersionSettings;
    }

    @Inject
    public void setRttiExpressionCheckers(Iterable<RttiExpressionChecker> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(21);
        }
        this.rttiExpressionCheckers = iterable;
    }

    @Inject
    public void setWrappedTypeFactory(WrappedTypeFactory wrappedTypeFactory) {
        this.wrappedTypeFactory = wrappedTypeFactory;
    }

    @Inject
    public void setCollectionLiteralResolver(CollectionLiteralResolver collectionLiteralResolver) {
        this.collectionLiteralResolver = collectionLiteralResolver;
    }

    @Inject
    public void setDeprecationResolver(DeprecationResolver deprecationResolver) {
        this.deprecationResolver = deprecationResolver;
    }

    @Inject
    public void setEffectSystem(EffectSystem effectSystem) {
        if (effectSystem == null) {
            $$$reportNull$$$0(22);
        }
        this.effectSystem = effectSystem;
    }

    @Inject
    public void setContractParsingServices(ContractParsingServices contractParsingServices) {
        if (contractParsingServices == null) {
            $$$reportNull$$$0(23);
        }
        this.contractParsingServices = contractParsingServices;
    }

    @Inject
    public void setDataFlowValueFactory(DataFlowValueFactory dataFlowValueFactory) {
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(24);
        }
        this.dataFlowValueFactory = dataFlowValueFactory;
    }

    @Inject
    public void setKotlinTypeChecker(NewKotlinTypeChecker newKotlinTypeChecker) {
        if (newKotlinTypeChecker == null) {
            $$$reportNull$$$0(25);
        }
        this.kotlinTypeChecker = newKotlinTypeChecker;
    }

    @Inject
    public void setTypeResolutionInterceptor(TypeResolutionInterceptor typeResolutionInterceptor) {
        if (typeResolutionInterceptor == null) {
            $$$reportNull$$$0(26);
        }
        this.typeResolutionInterceptor = typeResolutionInterceptor;
    }

    @Inject
    public void setMissingSupertypesResolver(MissingSupertypesResolver missingSupertypesResolver) {
        if (missingSupertypesResolver == null) {
            $$$reportNull$$$0(27);
        }
        this.missingSupertypesResolver = missingSupertypesResolver;
    }

    @Inject
    public void setAnnotationChecker(AnnotationChecker annotationChecker) {
        if (annotationChecker == null) {
            $$$reportNull$$$0(28);
        }
        this.annotationChecker = annotationChecker;
    }

    @Inject
    public void setCallComponents(KotlinCallComponents kotlinCallComponents) {
        if (kotlinCallComponents == null) {
            $$$reportNull$$$0(29);
        }
        this.callComponents = kotlinCallComponents;
    }
}
