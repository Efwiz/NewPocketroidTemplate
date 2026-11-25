package org.jetbrains.kotlin.resolve.calls.inference.components;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedCallableReferenceMarker;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: PostponedArgumentInputTypesResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0007\u0018\u0000 O2\u00020\u0001:\u0003OPQB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ>\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\b\u0010\u001a\u001a\u00020\fH\u0002JR\u0010\u001b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00112\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00162\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0006\u0012\u0004\u0018\u00010\u00010!j\u0002`#J\u001a\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u00010\u00112\u0006\u0010%\u001a\u00020&H\u0002J8\u0010'\u001a\u0004\u0018\u00010\u001f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\u0080\u0001\u0010)\u001a$\u0012\u0014\u0012\u0012\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u0011\u0018\u00010\u0018\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020+0**\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\f2\u0014\u0010-\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u0011\u0018\u00010\u00182\u0010\u0010/\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001f\u0018\u00010\u0011H\u0002J0\u00100\u001a\u000201*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u00102\u001a\u00020+2\u0006\u00103\u001a\u0002042\u0006\u0010\u0002\u001a\u00020\u0003H\u0002JP\u00105\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020+06\u0018\u00010\u0011*\u00060\u000ej\u0002`\u000f2\u0006\u00107\u001a\u00020\u001f2\u0006\u00108\u001a\u0002012\u001a\b\u0002\u00109\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020+060:H\u0002J \u0010;\u001a\u00020\"*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010<\u001a\u00020+H\u0002J\u0018\u0010=\u001a\u00020\"*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J4\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u0011*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\u0014\u0010@\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010.0\u00110\u0011H\u0002J0\u0010A\u001a\u0004\u0018\u00010&*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001c\u001a\u00020\u00122\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010(\u001a\u00020\u0016H\u0002J&\u0010B\u001a\b\u0012\u0004\u0012\u00020.0\u0011*\u00060\u000ej\u0002`\u000f2\u0006\u0010C\u001a\u00020D2\u0006\u0010(\u001a\u00020\u0016H\u0002JP\u0010E\u001a\u00020\f*\u00060\u000ej\u0002`\u000f2\u0006\u00107\u001a\u00020\u001f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00112\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00162\u0018\u0010F\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0006\u0012\u0004\u0018\u00010\u00010!j\u0002`#H\u0002J&\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00190H*\u00060\u000ej\u0002`\u000f2\u0006\u00107\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u0016H\u0002J.\u0010G\u001a\u00020I*\u00060\u000ej\u0002`\u000f2\u0006\u00107\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u00162\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00190KH\u0002J0\u0010L\u001a\u0004\u0018\u00010\u0019*\u00060\u000ej\u0002`\u000f2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u00108\u001a\u0002012\u0006\u0010(\u001a\u00020\u0016H\u0002J\u0010\u0010N\u001a\u0004\u0018\u00010.*\u0004\u0018\u00010\u001fH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver;", "", "resultTypeResolver", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "variableFixationFinder", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "resolutionTypeSystemContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "collectParameterTypesAndBuildNewExpectedTypes", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/Context;", "postponedArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "dependencyProvider", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDependencyInformationProvider;", "topLevelTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "considerExtensionReceiverFromConstrainsInLambda", "fixNextReadyVariableForParameterTypeIfNeeded", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "topLevelType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolvedAtomProvider", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResolvedAtomProvider;", "getDeclaredParametersConsideringExtensionFunctionsPresence", "parameterTypesInfo", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver$ParameterTypesInfo;", "buildNewFunctionalExpectedType", "variableDependencyProvider", "computeParameterInfoFromRelatedLambdas", "Lkotlin/Triple;", "", "extensionFunctionTypePresentInConstraints", "parameterTypesFromConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver$TypeWithKind;", "parameterTypesFromDeclaration", "computeResultingFunctionalConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "parametersNumber", "functionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "computeTypeVariablePathInsideGivenType", "Lkotlin/Pair;", ModuleXmlParser.TYPE, "targetVariable", "path", "Ljava/util/Stack;", "createTypeVariableForParameterType", "index", "createTypeVariableForReturnType", "createTypeVariablesForParameters", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "parameterTypes", "extractParameterTypesInfo", "findFunctionalTypesInConstraints", "variable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "fixNextReadyVariableForParameterType", "resolvedAtomByTypeVariableProvider", "getAllDeeplyRelatedTypeVariables", "", "", "typeVariableCollector", "", "selectFirstRelatedVariable", "variables", "wrapToTypeWithKind", "Companion", "ParameterTypesInfo", "TypeWithKind", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostponedArgumentInputTypesResolver {
    public static final String TYPE_VARIABLE_NAME_FOR_CR_RETURN_TYPE = "_Q";
    public static final String TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE = "_R";
    public static final String TYPE_VARIABLE_NAME_PREFIX_FOR_CR_PARAMETER_TYPE = "_QP";
    public static final String TYPE_VARIABLE_NAME_PREFIX_FOR_LAMBDA_PARAMETER_TYPE = "_RP";
    private final LanguageVersionSettings languageVersionSettings;
    private final ConstraintSystemUtilContext resolutionTypeSystemContext;
    private final ResultTypeResolver resultTypeResolver;
    private final VariableFixationFinder variableFixationFinder;

    /* JADX INFO: compiled from: PostponedArgumentInputTypesResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public PostponedArgumentInputTypesResolver(ResultTypeResolver resultTypeResolver, VariableFixationFinder variableFixationFinder, ConstraintSystemUtilContext resolutionTypeSystemContext, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(resultTypeResolver, "resultTypeResolver");
        Intrinsics.checkNotNullParameter(variableFixationFinder, "variableFixationFinder");
        Intrinsics.checkNotNullParameter(resolutionTypeSystemContext, "resolutionTypeSystemContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.resultTypeResolver = resultTypeResolver;
        this.variableFixationFinder = variableFixationFinder;
        this.resolutionTypeSystemContext = resolutionTypeSystemContext;
        this.languageVersionSettings = languageVersionSettings;
    }

    /* JADX INFO: compiled from: PostponedArgumentInputTypesResolver.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001Bm\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\u0016\u0010\u0005\u001a\u0012\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003\u0018\u00010\u0006\u0012\u0014\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0003\u0018\u00010\u0006\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\f¢\u0006\u0002\u0010\u0010R\u0019\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0015R\u001f\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R!\u0010\u0005\u001a\u0012\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver$ParameterTypesInfo;", "", "parametersFromDeclaration", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "parametersFromDeclarationOfRelatedLambdas", "", "parametersFromConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver$TypeWithKind;", "annotations", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "isExtensionFunction", "", "functionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "isNullable", "(Ljava/util/List;Ljava/util/Set;Ljava/util/Set;Ljava/util/List;ZLorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;Z)V", "getAnnotations", "()Ljava/util/List;", "getFunctionTypeKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "()Z", "getParametersFromConstraints", "()Ljava/util/Set;", "getParametersFromDeclaration", "getParametersFromDeclarationOfRelatedLambdas", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ParameterTypesInfo {
        private final List<AnnotationMarker> annotations;
        private final FunctionTypeKind functionTypeKind;
        private final boolean isExtensionFunction;
        private final boolean isNullable;
        private final Set<List<TypeWithKind>> parametersFromConstraints;
        private final List<KotlinTypeMarker> parametersFromDeclaration;
        private final Set<List<KotlinTypeMarker>> parametersFromDeclarationOfRelatedLambdas;

        /* JADX WARN: Multi-variable type inference failed */
        public ParameterTypesInfo(List<? extends KotlinTypeMarker> list, Set<? extends List<? extends KotlinTypeMarker>> set, Set<? extends List<TypeWithKind>> set2, List<? extends AnnotationMarker> list2, boolean z, FunctionTypeKind functionTypeKind, boolean z2) {
            Intrinsics.checkNotNullParameter(functionTypeKind, "functionTypeKind");
            this.parametersFromDeclaration = list;
            this.parametersFromDeclarationOfRelatedLambdas = set;
            this.parametersFromConstraints = set2;
            this.annotations = list2;
            this.isExtensionFunction = z;
            this.functionTypeKind = functionTypeKind;
            this.isNullable = z2;
        }

        public final List<KotlinTypeMarker> getParametersFromDeclaration() {
            return this.parametersFromDeclaration;
        }

        public final Set<List<KotlinTypeMarker>> getParametersFromDeclarationOfRelatedLambdas() {
            return this.parametersFromDeclarationOfRelatedLambdas;
        }

        public final Set<List<TypeWithKind>> getParametersFromConstraints() {
            return this.parametersFromConstraints;
        }

        public final List<AnnotationMarker> getAnnotations() {
            return this.annotations;
        }

        /* JADX INFO: renamed from: isExtensionFunction, reason: from getter */
        public final boolean getIsExtensionFunction() {
            return this.isExtensionFunction;
        }

        public final FunctionTypeKind getFunctionTypeKind() {
            return this.functionTypeKind;
        }

        /* JADX INFO: renamed from: isNullable, reason: from getter */
        public final boolean getIsNullable() {
            return this.isNullable;
        }
    }

    /* JADX INFO: compiled from: PostponedArgumentInputTypesResolver.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/PostponedArgumentInputTypesResolver$TypeWithKind;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;)V", "getDirection", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class TypeWithKind {
        private final ConstraintKind direction;
        private final KotlinTypeMarker type;

        public static /* synthetic */ TypeWithKind copy$default(TypeWithKind typeWithKind, KotlinTypeMarker kotlinTypeMarker, ConstraintKind constraintKind, int i, Object obj) {
            if ((i & 1) != 0) {
                kotlinTypeMarker = typeWithKind.type;
            }
            if ((i & 2) != 0) {
                constraintKind = typeWithKind.direction;
            }
            return typeWithKind.copy(kotlinTypeMarker, constraintKind);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KotlinTypeMarker getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ConstraintKind getDirection() {
            return this.direction;
        }

        public final TypeWithKind copy(KotlinTypeMarker type, ConstraintKind direction) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(direction, "direction");
            return new TypeWithKind(type, direction);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TypeWithKind)) {
                return false;
            }
            TypeWithKind typeWithKind = (TypeWithKind) other;
            return Intrinsics.areEqual(this.type, typeWithKind.type) && this.direction == typeWithKind.direction;
        }

        public int hashCode() {
            return (this.type.hashCode() * 31) + this.direction.hashCode();
        }

        public String toString() {
            return "TypeWithKind(type=" + this.type + ", direction=" + this.direction + ')';
        }

        public TypeWithKind(KotlinTypeMarker type, ConstraintKind direction) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(direction, "direction");
            this.type = type;
            this.direction = direction;
        }

        public final KotlinTypeMarker getType() {
            return this.type;
        }

        public /* synthetic */ TypeWithKind(KotlinTypeMarker kotlinTypeMarker, ConstraintKind constraintKind, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(kotlinTypeMarker, (i & 2) != 0 ? ConstraintKind.UPPER : constraintKind);
        }

        public final ConstraintKind getDirection() {
            return this.direction;
        }
    }

    private static final List<TypeWithKind> findFunctionalTypesInConstraints$extractFunctionalTypes(List<Constraint> list, ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        ArrayList arrayList = new ArrayList();
        for (Constraint constraint : list) {
            arrayList.add(new TypeWithKind(constraintSystemCompletionContext.getFunctionTypeFromSupertypes(constraint.getType()), constraint.getKind()));
        }
        return arrayList;
    }

    private final List<TypeWithKind> findFunctionalTypesInConstraints(ConstraintSystemCompletionContext constraintSystemCompletionContext, VariableWithConstraints variableWithConstraints, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider) {
        List<TypeWithKind> listEmptyList;
        List<Constraint> constraints;
        TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = constraintSystemCompletionContext.freshTypeConstructor(variableWithConstraints.getTypeVariable());
        Set<TypeConstructorMarker> shallowlyDependentVariables = typeVariableDependencyInformationProvider.getShallowlyDependentVariables(typeConstructorMarkerFreshTypeConstructor);
        if (shallowlyDependentVariables == null) {
            shallowlyDependentVariables = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus(shallowlyDependentVariables, typeConstructorMarkerFreshTypeConstructor);
        ArrayList arrayList = new ArrayList();
        Iterator it = setPlus.iterator();
        while (it.hasNext()) {
            VariableWithConstraints variableWithConstraints2 = constraintSystemCompletionContext.getNotFixedTypeVariables().get((TypeConstructorMarker) it.next());
            if (variableWithConstraints2 == null || (constraints = variableWithConstraints2.getConstraints()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : constraints) {
                    if (constraintSystemCompletionContext.isBuiltinFunctionTypeOrSubtype(((Constraint) obj).getType())) {
                        arrayList2.add(obj);
                    }
                }
                listEmptyList = findFunctionalTypesInConstraints$extractFunctionalTypes(arrayList2, constraintSystemCompletionContext);
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }

    private final ParameterTypesInfo extractParameterTypesInfo(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, List<? extends PostponedAtomWithRevisableExpectedType> list, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider) {
        VariableWithConstraints variableWithConstraints;
        boolean z;
        boolean z2;
        FunctionTypeKind functionTypeKind;
        FunctionTypeKind functionTypeKind2;
        KotlinTypeMarker expectedType = postponedAtomWithRevisableExpectedType.getExpectedType();
        if (expectedType == null || (variableWithConstraints = constraintSystemCompletionContext.getNotFixedTypeVariables().get(constraintSystemCompletionContext.typeConstructor(expectedType))) == null) {
            return null;
        }
        List<TypeWithKind> listFindFunctionalTypesInConstraints = findFunctionalTypesInConstraints(constraintSystemCompletionContext, variableWithConstraints, typeVariableDependencyInformationProvider);
        List<TypeWithKind> list2 = listFindFunctionalTypesInConstraints;
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (hashSet.add(Integer.valueOf(constraintSystemCompletionContext.argumentsCount(((TypeWithKind) obj).getType())))) {
                arrayList.add(obj);
            }
        }
        if (arrayList.size() > 1) {
            return null;
        }
        List<KotlinTypeMarker> parameterTypesFromDeclaration = postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeMarker ? ((LambdaWithTypeVariableAsExpectedTypeMarker) postponedAtomWithRevisableExpectedType).getParameterTypesFromDeclaration() : null;
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        for (TypeWithKind typeWithKind : list2) {
            List<KotlinTypeMarker> listExtractArgumentsForFunctionTypeOrSubtype = constraintSystemCompletionContext.extractArgumentsForFunctionTypeOrSubtype(typeWithKind.getType());
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listExtractArgumentsForFunctionTypeOrSubtype, 10));
            Iterator<T> it = listExtractArgumentsForFunctionTypeOrSubtype.iterator();
            while (it.hasNext()) {
                arrayList2.add(new TypeWithKind((KotlinTypeMarker) it.next(), typeWithKind.getDirection().opposite()));
            }
            smartSetCreate.add(arrayList2);
        }
        SmartSet smartSet = smartSetCreate;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(constraintSystemCompletionContext.getAttributes(((TypeWithKind) it2.next()).getType()));
        }
        List listDistinct = CollectionsKt.distinct(CollectionsKt.flatten(arrayList3));
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it3 = list2.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z = false;
                    break;
                }
                if (constraintSystemCompletionContext.isExtensionFunctionType(((TypeWithKind) it3.next()).getType())) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        SmartSet smartSet2 = smartSet;
        Triple<Set<List<KotlinTypeMarker>>, Boolean, Integer> tripleComputeParameterInfoFromRelatedLambdas = computeParameterInfoFromRelatedLambdas(constraintSystemCompletionContext, postponedAtomWithRevisableExpectedType, list, typeVariableDependencyInformationProvider, z, smartSet2, parameterTypesFromDeclaration);
        Set<List<KotlinTypeMarker>> setComponent1 = tripleComputeParameterInfoFromRelatedLambdas.component1();
        boolean zBooleanValue = tripleComputeParameterInfoFromRelatedLambdas.component2().booleanValue();
        int iIntValue = tripleComputeParameterInfoFromRelatedLambdas.component3().intValue();
        if (listFindFunctionalTypesInConstraints.isEmpty()) {
            z2 = false;
            functionTypeKind = null;
        } else {
            boolean z3 = true;
            functionTypeKind = null;
            for (TypeWithKind typeWithKind2 : listFindFunctionalTypesInConstraints) {
                if (functionTypeKind == null && (functionTypeKind2 = constraintSystemCompletionContext.functionTypeKind(typeWithKind2.getType())) != null) {
                    if (FunctionTypeKindKt.isBasicFunctionOrKFunction(functionTypeKind2)) {
                        functionTypeKind2 = null;
                    }
                    if (functionTypeKind2 != null) {
                        functionTypeKind = functionTypeKind2;
                    }
                }
                if (z3 && !constraintSystemCompletionContext.isMarkedNullable(typeWithKind2.getType())) {
                    z3 = false;
                }
                if (functionTypeKind != null && !z3) {
                    break;
                }
            }
            z2 = z3;
        }
        boolean zIsLambda = this.resolutionTypeSystemContext.isLambda(postponedAtomWithRevisableExpectedType);
        boolean z4 = zBooleanValue || z;
        if (parameterTypesFromDeclaration != null && zIsLambda && parameterTypesFromDeclaration.size() + 1 == iIntValue && z4 && considerExtensionReceiverFromConstrainsInLambda()) {
            parameterTypesFromDeclaration = CollectionsKt.plus((Collection) CollectionsKt.listOf((Object) null), (Iterable) parameterTypesFromDeclaration);
        }
        return new ParameterTypesInfo(parameterTypesFromDeclaration, setComponent1, smartSet2, listDistinct, z4, functionTypeKind == null ? FunctionTypeKind.Function.INSTANCE : functionTypeKind, z2);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0077  */
    /* JADX WARN: Code duplicated, block: B:40:0x00c9  */
    private final Triple<Set<List<KotlinTypeMarker>>, Boolean, Integer> computeParameterInfoFromRelatedLambdas(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, List<? extends PostponedAtomWithRevisableExpectedType> list, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, boolean z, Set<? extends List<TypeWithKind>> set, List<? extends KotlinTypeMarker> list2) {
        int iIntValue;
        KotlinTypeMarker expectedType;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor;
        KotlinTypeMarker expectedType2;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor2;
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        boolean zIsFunctionExpressionWithReceiver = false;
        while (true) {
            Pair pair = null;
            if (!it.hasNext()) {
                break;
            }
            PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType2 = (PostponedAtomWithRevisableExpectedType) it.next();
            if (postponedAtomWithRevisableExpectedType2 instanceof LambdaWithTypeVariableAsExpectedTypeMarker) {
                LambdaWithTypeVariableAsExpectedTypeMarker lambdaWithTypeVariableAsExpectedTypeMarker = (LambdaWithTypeVariableAsExpectedTypeMarker) postponedAtomWithRevisableExpectedType2;
                if (lambdaWithTypeVariableAsExpectedTypeMarker.getParameterTypesFromDeclaration() != null) {
                    if (!Intrinsics.areEqual(postponedAtomWithRevisableExpectedType2, postponedAtomWithRevisableExpectedType) && (expectedType = postponedAtomWithRevisableExpectedType.getExpectedType()) != null && (typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(expectedType)) != null && (expectedType2 = postponedAtomWithRevisableExpectedType2.getExpectedType()) != null && (typeConstructorMarkerTypeConstructor2 = constraintSystemCompletionContext.typeConstructor(expectedType2)) != null) {
                        boolean zAreVariablesDependentShallowly = typeVariableDependencyInformationProvider.areVariablesDependentShallowly(typeConstructorMarkerTypeConstructor, typeConstructorMarkerTypeConstructor2);
                        zIsFunctionExpressionWithReceiver |= constraintSystemUtilContext.isFunctionExpressionWithReceiver(postponedAtomWithRevisableExpectedType2);
                        List<KotlinTypeMarker> parameterTypesFromDeclaration = lambdaWithTypeVariableAsExpectedTypeMarker.getParameterTypesFromDeclaration();
                        if (zAreVariablesDependentShallowly && parameterTypesFromDeclaration != null) {
                            pair = new Pair(parameterTypesFromDeclaration, Boolean.valueOf(constraintSystemUtilContext.isLambda(postponedAtomWithRevisableExpectedType2)));
                        }
                    }
                }
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        ArrayList arrayList2 = arrayList;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (set != null) {
            Set<? extends List<TypeWithKind>> set2 = set;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
            Iterator<T> it2 = set2.iterator();
            while (it2.hasNext()) {
                arrayList3.add(Integer.valueOf(((List) it2.next()).size()));
            }
            Integer num = (Integer) CollectionsKt.maxOrNull((Iterable) arrayList3);
            if (num != null) {
                iIntValue = num.intValue();
            } else {
                iIntValue = 0;
            }
        } else {
            iIntValue = 0;
        }
        ArrayList<Pair> arrayList4 = arrayList2;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            arrayList5.add(Integer.valueOf(((List) ((Pair) it3.next()).getFirst()).size()));
        }
        Integer num2 = (Integer) CollectionsKt.maxOrNull((Iterable) arrayList5);
        int iMax = Math.max(iIntValue, Math.max(num2 != null ? num2.intValue() : 0, list2 != null ? list2.size() : 0));
        boolean zConsiderExtensionReceiverFromConstrainsInLambda = considerExtensionReceiverFromConstrainsInLambda();
        for (Pair pair2 : arrayList4) {
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            List listPlus = (List) pair2.component1();
            boolean zBooleanValue = ((Boolean) pair2.component2()).booleanValue();
            if (zConsiderExtensionReceiverFromConstrainsInLambda && zBooleanValue && ((z || zIsFunctionExpressionWithReceiver) && listPlus.size() + 1 == iMax)) {
                listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf((Object) null), (Iterable) listPlus);
            }
            linkedHashSet2.add(listPlus);
        }
        return new Triple<>(linkedHashSet, Boolean.valueOf(zIsFunctionExpressionWithReceiver), Integer.valueOf(iMax));
    }

    private final boolean considerExtensionReceiverFromConstrainsInLambda() {
        return this.resolutionTypeSystemContext.isForcedConsiderExtensionReceiverFromConstrainsInLambda() || this.languageVersionSettings.supportsFeature(LanguageFeature.ConsiderExtensionReceiverFromConstrainsInLambda);
    }

    private final TypeVariableMarker createTypeVariableForReturnType(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        TypeVariableMarker typeVariableMarkerCreateTypeVariableForCallableReferenceReturnType;
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        if (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeMarker) {
            typeVariableMarkerCreateTypeVariableForCallableReferenceReturnType = constraintSystemUtilContext.createTypeVariableForLambdaReturnType();
        } else {
            if (!(postponedAtomWithRevisableExpectedType instanceof PostponedCallableReferenceMarker)) {
                throw new IllegalStateException("Unsupported postponed argument type of " + postponedAtomWithRevisableExpectedType);
            }
            typeVariableMarkerCreateTypeVariableForCallableReferenceReturnType = constraintSystemUtilContext.createTypeVariableForCallableReferenceReturnType();
        }
        constraintSystemCompletionContext.getBuilder().registerVariable(typeVariableMarkerCreateTypeVariableForCallableReferenceReturnType);
        return typeVariableMarkerCreateTypeVariableForCallableReferenceReturnType;
    }

    private final TypeVariableMarker createTypeVariableForParameterType(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, int i) {
        TypeVariableMarker typeVariableMarkerCreateTypeVariableForCallableReferenceParameterType;
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        if (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeMarker) {
            typeVariableMarkerCreateTypeVariableForCallableReferenceParameterType = constraintSystemUtilContext.createTypeVariableForLambdaParameterType(postponedAtomWithRevisableExpectedType, i);
        } else {
            if (!(postponedAtomWithRevisableExpectedType instanceof PostponedCallableReferenceMarker)) {
                throw new IllegalStateException("Unsupported postponed argument type of " + postponedAtomWithRevisableExpectedType);
            }
            typeVariableMarkerCreateTypeVariableForCallableReferenceParameterType = constraintSystemUtilContext.createTypeVariableForCallableReferenceParameterType(postponedAtomWithRevisableExpectedType, i);
        }
        constraintSystemCompletionContext.getBuilder().registerVariable(typeVariableMarkerCreateTypeVariableForCallableReferenceParameterType);
        return typeVariableMarkerCreateTypeVariableForCallableReferenceParameterType;
    }

    private final List<TypeArgumentMarker> createTypeVariablesForParameters(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, List<? extends List<TypeWithKind>> list) {
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        ConstraintSystemBuilder builder = constraintSystemCompletionContext.getBuilder();
        IntRange indices = CollectionsKt.getIndices((Collection) CollectionsKt.first((List) list));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(indices, 10));
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            List<? extends List<TypeWithKind>> list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add((TypeWithKind) CollectionsKt.getOrNull((List) it2.next(), iNextInt));
            }
            arrayList.add(arrayList2);
        }
        ArrayList arrayList3 = arrayList;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        int i = 0;
        for (Object obj : arrayList3) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeVariableMarker typeVariableMarkerCreateTypeVariableForParameterType = createTypeVariableForParameterType(constraintSystemCompletionContext, postponedAtomWithRevisableExpectedType, i);
            TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = constraintSystemCompletionContext.freshTypeConstructor(typeVariableMarkerCreateTypeVariableForParameterType);
            for (TypeWithKind typeWithKind : (List) obj) {
                if (constraintSystemCompletionContext.getFixedTypeVariables().containsKey(typeConstructorMarkerFreshTypeConstructor)) {
                    break;
                }
                if (typeWithKind != null) {
                    int i3 = WhenMappings.$EnumSwitchMapping$0[typeWithKind.getDirection().ordinal()];
                    if (i3 == 1) {
                        builder.addEqualityConstraint(constraintSystemCompletionContext.defaultType(typeVariableMarkerCreateTypeVariableForParameterType), typeWithKind.getType(), constraintSystemUtilContext.createArgumentConstraintPosition(postponedAtomWithRevisableExpectedType));
                    } else if (i3 == 2) {
                        builder.addSubtypeConstraint(constraintSystemCompletionContext.defaultType(typeVariableMarkerCreateTypeVariableForParameterType), typeWithKind.getType(), constraintSystemUtilContext.createArgumentConstraintPosition(postponedAtomWithRevisableExpectedType));
                    } else if (i3 == 3) {
                        builder.addSubtypeConstraint(typeWithKind.getType(), constraintSystemCompletionContext.defaultType(typeVariableMarkerCreateTypeVariableForParameterType), constraintSystemUtilContext.createArgumentConstraintPosition(postponedAtomWithRevisableExpectedType));
                    }
                }
            }
            SimpleTypeMarker simpleTypeMarkerDefaultType = constraintSystemCompletionContext.getFixedTypeVariables().get(typeConstructorMarkerFreshTypeConstructor);
            if (simpleTypeMarkerDefaultType == null) {
                simpleTypeMarkerDefaultType = constraintSystemCompletionContext.defaultType(typeVariableMarkerCreateTypeVariableForParameterType);
            }
            arrayList4.add(constraintSystemCompletionContext.asTypeArgument(simpleTypeMarkerDefaultType));
            i = i2;
        }
        return arrayList4;
    }

    private final TypeConstructorMarker computeResultingFunctionalConstructor(ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, int i, FunctionTypeKind functionTypeKind, ResultTypeResolver resultTypeResolver) {
        KotlinTypeMarker expectedType = postponedAtomWithRevisableExpectedType.getExpectedType();
        if (expectedType == null) {
            throw new IllegalStateException("Postponed argument's expected type must not be null");
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(expectedType);
        if (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeMarker) {
            return constraintSystemCompletionContext.getNonReflectFunctionTypeConstructor(i, functionTypeKind);
        }
        if (postponedAtomWithRevisableExpectedType instanceof PostponedCallableReferenceMarker) {
            KotlinTypeMarker kotlinTypeMarkerFindResultType = resultTypeResolver.findResultType(constraintSystemCompletionContext, (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), typeConstructorMarkerTypeConstructor), TypeVariableDirectionCalculator.ResolveDirection.TO_SUPERTYPE);
            if (constraintSystemCompletionContext.isBuiltinFunctionTypeOrSubtype(kotlinTypeMarkerFindResultType) && constraintSystemCompletionContext.argumentsCount(kotlinTypeMarkerFindResultType) > 1) {
                return constraintSystemCompletionContext.typeConstructor(kotlinTypeMarkerFindResultType);
            }
            return constraintSystemCompletionContext.getReflectFunctionTypeConstructor(i, functionTypeKind);
        }
        throw new IllegalStateException("Unsupported postponed argument type of " + postponedAtomWithRevisableExpectedType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List computeTypeVariablePathInsideGivenType$default(PostponedArgumentInputTypesResolver postponedArgumentInputTypesResolver, ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeConstructorMarker typeConstructorMarker, Stack stack, int i, Object obj) {
        if ((i & 4) != 0) {
            stack = new Stack();
        }
        return postponedArgumentInputTypesResolver.computeTypeVariablePathInsideGivenType(constraintSystemCompletionContext, kotlinTypeMarker, typeConstructorMarker, stack);
    }

    private final List<Pair<TypeConstructorMarker, Integer>> computeTypeVariablePathInsideGivenType(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeConstructorMarker typeConstructorMarker, Stack<Pair<TypeConstructorMarker, Integer>> stack) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(kotlinTypeMarker);
        if (Intrinsics.areEqual(typeConstructorMarkerTypeConstructor, typeConstructorMarker)) {
            return CollectionsKt.emptyList();
        }
        int iArgumentsCount = constraintSystemCompletionContext.argumentsCount(kotlinTypeMarker);
        for (int i = 0; i < iArgumentsCount; i++) {
            KotlinTypeMarker type = constraintSystemCompletionContext.getType(constraintSystemCompletionContext.getArgument(kotlinTypeMarker, i));
            if (Intrinsics.areEqual(constraintSystemCompletionContext.typeConstructor(type), typeConstructorMarker)) {
                return CollectionsKt.plus((Collection<? extends Pair>) CollectionsKt.toList(stack), TuplesKt.to(typeConstructorMarkerTypeConstructor, Integer.valueOf(i)));
            }
            if (constraintSystemCompletionContext.argumentsCount(type) != 0) {
                stack.push(TuplesKt.to(typeConstructorMarkerTypeConstructor, Integer.valueOf(i)));
                List<Pair<TypeConstructorMarker, Integer>> listComputeTypeVariablePathInsideGivenType = computeTypeVariablePathInsideGivenType(constraintSystemCompletionContext, type, typeConstructorMarker, stack);
                if (listComputeTypeVariablePathInsideGivenType != null) {
                    return listComputeTypeVariablePathInsideGivenType;
                }
                stack.pop();
            }
        }
        return null;
    }

    private final TypeVariableTypeConstructorMarker selectFirstRelatedVariable(ConstraintSystemCompletionContext constraintSystemCompletionContext, Set<? extends TypeVariableTypeConstructorMarker> set, TypeConstructorMarker typeConstructorMarker, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider) {
        Object next;
        Set<TypeConstructorMarker> deeplyDependentVariables = typeVariableDependencyInformationProvider.getDeeplyDependentVariables(typeConstructorMarker);
        if (deeplyDependentVariables == null) {
            deeplyDependentVariables = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus((Set<? extends Set<TypeConstructorMarker>>) deeplyDependentVariables, typeVariableDependencyInformationProvider.getShallowlyDependentVariables(typeConstructorMarker));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            next = it.next();
            TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker = (TypeVariableTypeConstructorMarker) next;
            if (setPlus.contains(typeVariableTypeConstructorMarker) && constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(typeVariableTypeConstructorMarker)) {
                return (TypeVariableTypeConstructorMarker) next;
            }
        }
        next = null;
        return (TypeVariableTypeConstructorMarker) next;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x009b  */
    private final KotlinTypeMarker buildNewFunctionalExpectedType(final ConstraintSystemCompletionContext constraintSystemCompletionContext, PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType, ParameterTypesInfo parameterTypesInfo, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, Set<? extends TypeVariableTypeConstructorMarker> set) {
        List<? extends Pair<? extends TypeConstructorMarker, Integer>> listComputeTypeVariablePathInsideGivenType$default;
        boolean z;
        boolean isExtensionFunction;
        Set<List<TypeWithKind>> set2;
        ArrayList arrayList;
        Object next;
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        KotlinTypeMarker expectedType = postponedAtomWithRevisableExpectedType.getExpectedType();
        if (expectedType == null) {
            return null;
        }
        final TypeConstructorMarker typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(expectedType);
        if (!constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(typeConstructorMarkerTypeConstructor)) {
            return null;
        }
        TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarkerSelectFirstRelatedVariable = selectFirstRelatedVariable(constraintSystemCompletionContext, set, typeConstructorMarkerTypeConstructor, typeVariableDependencyInformationProvider);
        if (typeVariableTypeConstructorMarkerSelectFirstRelatedVariable != null) {
            List<Constraint> constraints = ((VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), typeVariableTypeConstructorMarkerSelectFirstRelatedVariable)).getConstraints();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(constraints, 10));
            Iterator<T> it = constraints.iterator();
            while (it.hasNext()) {
                arrayList2.add(((Constraint) it.next()).getType());
            }
            Iterator it2 = CollectionsKt.toSet(arrayList2).iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!constraintSystemCompletionContext.contains((KotlinTypeMarker) next, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.PostponedArgumentInputTypesResolver$buildNewFunctionalExpectedType$1$pathFromRelatedTopLevelVariable$containingType$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(constraintSystemCompletionContext.typeConstructor(it3), typeConstructorMarkerTypeConstructor));
                }
            }));
            KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) next;
            if (kotlinTypeMarker != null) {
                listComputeTypeVariablePathInsideGivenType$default = computeTypeVariablePathInsideGivenType$default(this, constraintSystemCompletionContext, kotlinTypeMarker, typeConstructorMarkerTypeConstructor, null, 4, null);
            } else {
                listComputeTypeVariablePathInsideGivenType$default = null;
            }
        } else {
            listComputeTypeVariablePathInsideGivenType$default = null;
        }
        if (listComputeTypeVariablePathInsideGivenType$default != null && typeVariableTypeConstructorMarkerSelectFirstRelatedVariable != null) {
            KotlinTypeMarker builtFunctionalExpectedTypeForPostponedArgument = constraintSystemCompletionContext.getBuilder().getBuiltFunctionalExpectedTypeForPostponedArgument(typeVariableTypeConstructorMarkerSelectFirstRelatedVariable, listComputeTypeVariablePathInsideGivenType$default);
            if (builtFunctionalExpectedTypeForPostponedArgument != null) {
                return builtFunctionalExpectedTypeForPostponedArgument;
            }
        } else {
            KotlinTypeMarker builtFunctionalExpectedTypeForPostponedArgument2 = constraintSystemCompletionContext.getBuilder().getBuiltFunctionalExpectedTypeForPostponedArgument(typeConstructorMarkerTypeConstructor);
            if (builtFunctionalExpectedTypeForPostponedArgument2 != null) {
                return builtFunctionalExpectedTypeForPostponedArgument2;
            }
        }
        Set<List<TypeWithKind>> parametersFromConstraints = parameterTypesInfo.getParametersFromConstraints();
        List<KotlinTypeMarker> declaredParametersConsideringExtensionFunctionsPresence = getDeclaredParametersConsideringExtensionFunctionsPresence(parameterTypesInfo);
        List<KotlinTypeMarker> list = declaredParametersConsideringExtensionFunctionsPresence;
        if (list == null || list.isEmpty()) {
            z = false;
        } else {
            List<KotlinTypeMarker> list2 = declaredParametersConsideringExtensionFunctionsPresence;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it3 = list2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        if (((KotlinTypeMarker) it3.next()) == null) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        }
        boolean isExtensionFunction2 = parameterTypesInfo.getIsExtensionFunction();
        Set<List<KotlinTypeMarker>> parametersFromDeclarationOfRelatedLambdas = parameterTypesInfo.getParametersFromDeclarationOfRelatedLambdas();
        if (parametersFromDeclarationOfRelatedLambdas == null) {
            parametersFromDeclarationOfRelatedLambdas = SetsKt.emptySet();
        }
        Set setPlus = SetsKt.plus((Set<? extends List<KotlinTypeMarker>>) parametersFromDeclarationOfRelatedLambdas, declaredParametersConsideringExtensionFunctionsPresence);
        if (z && !isExtensionFunction2 && !constraintSystemUtilContext.isFunctionExpression(postponedAtomWithRevisableExpectedType)) {
            return null;
        }
        Set<List<TypeWithKind>> setEmptySet = parametersFromConstraints == null ? SetsKt.emptySet() : parametersFromConstraints;
        Set set3 = setPlus;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set3, 10));
        for (Iterator it4 = set3.iterator(); it4.hasNext(); it4 = it4) {
            List list3 = (List) it4.next();
            if (list3 != null) {
                List list4 = list3;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                Iterator it5 = list4.iterator();
                while (it5.hasNext()) {
                    arrayList4.add(wrapToTypeWithKind((KotlinTypeMarker) it5.next()));
                }
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            arrayList3.add(arrayList);
        }
        List<? extends List<TypeWithKind>> listFilterNotNull = CollectionsKt.filterNotNull(SetsKt.plus((Set) setEmptySet, (Iterable) arrayList3));
        if (listFilterNotNull.isEmpty()) {
            return null;
        }
        List<TypeArgumentMarker> listCreateTypeVariablesForParameters = createTypeVariablesForParameters(constraintSystemCompletionContext, postponedAtomWithRevisableExpectedType, listFilterNotNull);
        TypeVariableMarker typeVariableMarkerCreateTypeVariableForReturnType = createTypeVariableForReturnType(constraintSystemCompletionContext, postponedAtomWithRevisableExpectedType);
        TypeConstructorMarker typeConstructorMarkerComputeResultingFunctionalConstructor = computeResultingFunctionalConstructor(constraintSystemCompletionContext, postponedAtomWithRevisableExpectedType, listCreateTypeVariablesForParameters.size(), parameterTypesInfo.getFunctionTypeKind(), this.resultTypeResolver);
        boolean z2 = isExtensionFunction2 && z && (list != null && !list.isEmpty() && (set2 = parametersFromConstraints) != null && !set2.isEmpty() && declaredParametersConsideringExtensionFunctionsPresence.size() == ((List) CollectionsKt.first(parametersFromConstraints)).size());
        List<? extends TypeArgumentMarker> listPlus = CollectionsKt.plus((Collection<? extends TypeArgumentMarker>) listCreateTypeVariablesForParameters, constraintSystemCompletionContext.asTypeArgument(constraintSystemCompletionContext.defaultType(typeVariableMarkerCreateTypeVariableForReturnType)));
        boolean isNullable = parameterTypesInfo.getIsNullable();
        if (z2) {
            isExtensionFunction = false;
        } else {
            isExtensionFunction = constraintSystemUtilContext.isFunctionExpressionWithReceiver(postponedAtomWithRevisableExpectedType) ? true : parameterTypesInfo.getIsExtensionFunction();
        }
        SimpleTypeMarker simpleTypeMarkerCreateSimpleType = constraintSystemCompletionContext.createSimpleType(typeConstructorMarkerComputeResultingFunctionalConstructor, listPlus, isNullable, isExtensionFunction, parameterTypesInfo.getAnnotations());
        constraintSystemCompletionContext.getBuilder().addSubtypeConstraint(simpleTypeMarkerCreateSimpleType, expectedType, constraintSystemUtilContext.createArgumentConstraintPosition(postponedAtomWithRevisableExpectedType));
        if (listComputeTypeVariablePathInsideGivenType$default != null && typeVariableTypeConstructorMarkerSelectFirstRelatedVariable != null) {
            constraintSystemCompletionContext.getBuilder().putBuiltFunctionalExpectedTypeForPostponedArgument(typeVariableTypeConstructorMarkerSelectFirstRelatedVariable, listComputeTypeVariablePathInsideGivenType$default, simpleTypeMarkerCreateSimpleType);
        } else {
            constraintSystemCompletionContext.getBuilder().putBuiltFunctionalExpectedTypeForPostponedArgument(typeConstructorMarkerTypeConstructor, simpleTypeMarkerCreateSimpleType);
        }
        return simpleTypeMarkerCreateSimpleType;
    }

    public final boolean collectParameterTypesAndBuildNewExpectedTypes(ConstraintSystemCompletionContext c, List<? extends PostponedAtomWithRevisableExpectedType> postponedArguments, ConstraintSystemCompletionMode completionMode, TypeVariableDependencyInformationProvider dependencyProvider, Set<? extends TypeVariableTypeConstructorMarker> topLevelTypeVariables) {
        ParameterTypesInfo parameterTypesInfoExtractParameterTypesInfo;
        KotlinTypeMarker kotlinTypeMarkerBuildNewFunctionalExpectedType;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(dependencyProvider, "dependencyProvider");
        Intrinsics.checkNotNullParameter(topLevelTypeVariables, "topLevelTypeVariables");
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        for (PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType : postponedArguments) {
            if (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeMarker) {
                LambdaWithTypeVariableAsExpectedTypeMarker lambdaWithTypeVariableAsExpectedTypeMarker = (LambdaWithTypeVariableAsExpectedTypeMarker) postponedAtomWithRevisableExpectedType;
                if (lambdaWithTypeVariableAsExpectedTypeMarker.getParameterTypesFromDeclaration() == null) {
                    lambdaWithTypeVariableAsExpectedTypeMarker.updateParameterTypesFromDeclaration(constraintSystemUtilContext.extractLambdaParameterTypesFromDeclaration(postponedAtomWithRevisableExpectedType));
                }
            }
        }
        List<? extends PostponedAtomWithRevisableExpectedType> list = postponedArguments;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType2 : list) {
            if (completionMode != ConstraintSystemCompletionMode.PARTIAL || constraintSystemUtilContext.isFunctionExpression(postponedAtomWithRevisableExpectedType2)) {
                if (postponedAtomWithRevisableExpectedType2.getRevisedExpectedType() == null && (parameterTypesInfoExtractParameterTypesInfo = extractParameterTypesInfo(c, postponedAtomWithRevisableExpectedType2, postponedArguments, dependencyProvider)) != null && (kotlinTypeMarkerBuildNewFunctionalExpectedType = buildNewFunctionalExpectedType(c, postponedAtomWithRevisableExpectedType2, parameterTypesInfoExtractParameterTypesInfo, dependencyProvider, topLevelTypeVariables)) != null) {
                    postponedAtomWithRevisableExpectedType2.reviseExpectedType(kotlinTypeMarkerBuildNewFunctionalExpectedType);
                    return true;
                }
            }
        }
        return false;
    }

    private final Collection<TypeVariableTypeConstructorMarker> getAllDeeplyRelatedTypeVariables(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        getAllDeeplyRelatedTypeVariables(constraintSystemCompletionContext, kotlinTypeMarker, typeVariableDependencyInformationProvider, linkedHashSet);
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void getAllDeeplyRelatedTypeVariables(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, Set<TypeVariableTypeConstructorMarker> set) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = constraintSystemCompletionContext.typeConstructor(kotlinTypeMarker);
        if (typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker) {
            Set<TypeConstructorMarker> deeplyDependentVariables = typeVariableDependencyInformationProvider.getDeeplyDependentVariables(typeConstructorMarkerTypeConstructor);
            if (deeplyDependentVariables == null) {
                deeplyDependentVariables = SetsKt.emptySet();
            }
            set.add(typeConstructorMarkerTypeConstructor);
            ArrayList arrayList = new ArrayList();
            for (Object obj : deeplyDependentVariables) {
                if (obj instanceof TypeVariableTypeConstructorMarker) {
                    arrayList.add(obj);
                }
            }
            set.addAll(arrayList);
            return;
        }
        if (constraintSystemCompletionContext.argumentsCount(kotlinTypeMarker) > 0) {
            Iterator<TypeArgumentMarker> it = constraintSystemCompletionContext.iterator(constraintSystemCompletionContext.asArgumentList(constraintSystemCompletionContext.lowerBoundIfFlexible(kotlinTypeMarker)));
            while (it.hasNext()) {
                TypeArgumentMarker next = it.next();
                if (!constraintSystemCompletionContext.isStarProjection(next)) {
                    getAllDeeplyRelatedTypeVariables(constraintSystemCompletionContext, constraintSystemCompletionContext.getType(next), typeVariableDependencyInformationProvider, set);
                }
            }
        }
    }

    private final List<KotlinTypeMarker> getDeclaredParametersConsideringExtensionFunctionsPresence(ParameterTypesInfo parameterTypesInfo) {
        Set<List<TypeWithKind>> parametersFromConstraints;
        List<KotlinTypeMarker> parametersFromDeclaration;
        if (considerExtensionReceiverFromConstrainsInLambda() || (parametersFromConstraints = parameterTypesInfo.getParametersFromConstraints()) == null || parametersFromConstraints.isEmpty() || (parametersFromDeclaration = parameterTypesInfo.getParametersFromDeclaration()) == null || parametersFromDeclaration.isEmpty()) {
            return parameterTypesInfo.getParametersFromDeclaration();
        }
        if (((List) CollectionsKt.first(parameterTypesInfo.getParametersFromConstraints())).size() == parameterTypesInfo.getParametersFromDeclaration().size() + 1 && parameterTypesInfo.getIsExtensionFunction()) {
            return CollectionsKt.plus((Collection) CollectionsKt.listOf((Object) null), (Iterable) parameterTypesInfo.getParametersFromDeclaration());
        }
        return parameterTypesInfo.getParametersFromDeclaration();
    }

    public final boolean fixNextReadyVariableForParameterTypeIfNeeded(ConstraintSystemCompletionContext c, PostponedResolvedAtomMarker argument, List<? extends PostponedResolvedAtomMarker> postponedArguments, KotlinTypeMarker topLevelType, TypeVariableDependencyInformationProvider dependencyProvider, Function1<? super TypeVariableMarker, ? extends Object> resolvedAtomProvider) {
        KotlinTypeMarker expectedType;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(dependencyProvider, "dependencyProvider");
        Intrinsics.checkNotNullParameter(resolvedAtomProvider, "resolvedAtomProvider");
        PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType = argument instanceof PostponedAtomWithRevisableExpectedType ? (PostponedAtomWithRevisableExpectedType) argument : null;
        if (postponedAtomWithRevisableExpectedType == null || (expectedType = postponedAtomWithRevisableExpectedType.getRevisedExpectedType()) == null) {
            expectedType = argument.getExpectedType();
        }
        KotlinTypeMarker kotlinTypeMarker = expectedType;
        return kotlinTypeMarker != null && c.isFunctionOrKFunctionWithAnySuspendability(kotlinTypeMarker) && fixNextReadyVariableForParameterType(c, kotlinTypeMarker, postponedArguments, topLevelType, dependencyProvider, resolvedAtomProvider);
    }

    private final boolean fixNextReadyVariableForParameterType(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, List<? extends PostponedResolvedAtomMarker> list, KotlinTypeMarker kotlinTypeMarker2, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, Function1<? super TypeVariableMarker, ? extends Object> function1) {
        ConstraintSystemUtilContext constraintSystemUtilContext = this.resolutionTypeSystemContext;
        List<KotlinTypeMarker> listExtractArgumentsForFunctionTypeOrSubtype = constraintSystemCompletionContext.extractArgumentsForFunctionTypeOrSubtype(kotlinTypeMarker);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listExtractArgumentsForFunctionTypeOrSubtype.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getAllDeeplyRelatedTypeVariables(constraintSystemCompletionContext, (KotlinTypeMarker) it.next(), typeVariableDependencyInformationProvider));
        }
        VariableFixationFinder.VariableForFixation variableForFixationFindFirstVariableForFixation = this.variableFixationFinder.findFirstVariableForFixation(constraintSystemCompletionContext, arrayList, list, ConstraintSystemCompletionMode.FULL, kotlinTypeMarker2);
        if (variableForFixationFindFirstVariableForFixation == null || !variableForFixationFindFirstVariableForFixation.getHasProperConstraint()) {
            return false;
        }
        VariableWithConstraints variableWithConstraints = (VariableWithConstraints) MapsKt.getValue(constraintSystemCompletionContext.getNotFixedTypeVariables(), variableForFixationFindFirstVariableForFixation.getVariable());
        KotlinTypeMarker kotlinTypeMarkerFindResultType = this.resultTypeResolver.findResultType(constraintSystemCompletionContext, variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN);
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        constraintSystemCompletionContext.fixVariable(typeVariable, kotlinTypeMarkerFindResultType, constraintSystemUtilContext.createFixVariableConstraintPosition(typeVariable, function1.invoke(typeVariable)));
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final TypeWithKind wrapToTypeWithKind(KotlinTypeMarker kotlinTypeMarker) {
        ConstraintKind constraintKind = null;
        Object[] objArr = 0;
        if (kotlinTypeMarker != null) {
            return new TypeWithKind(kotlinTypeMarker, constraintKind, 2, objArr == true ? 1 : 0);
        }
        return null;
    }
}
