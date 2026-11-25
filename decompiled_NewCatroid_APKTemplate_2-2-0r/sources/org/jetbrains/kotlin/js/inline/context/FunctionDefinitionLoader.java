package org.jetbrains.kotlin.js.inline.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.facade.TranslationUnit;
import org.jetbrains.kotlin.js.inline.FunctionReader;
import org.jetbrains.kotlin.js.inline.InlineFunctionDefinition;
import org.jetbrains.kotlin.js.inline.InliningScope;
import org.jetbrains.kotlin.js.inline.JsInliner;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FunctionDefinitionLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001*B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u0019H\u0002J\u001a\u0010\u001f\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0007H\u0002J\u001a\u0010!\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001c\u0010\"\u001a\u0004\u0018\u00010\u000e2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010 \u001a\u00020\u0007H\u0002J\u001a\u0010%\u001a\u0004\u0018\u00010\u000e2\u0006\u0010&\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u0007H\u0002J\u0012\u0010'\u001a\u0004\u0018\u00010\u000e2\u0006\u0010(\u001a\u00020\u0017H\u0002J\f\u0010)\u001a\u00020\b*\u00020\u0007H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader;", "", "inliner", "Lorg/jetbrains/kotlin/js/inline/JsInliner;", "(Lorg/jetbrains/kotlin/js/inline/JsInliner;)V", "fragmentInfo", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader$FragmentInfo;", "functionReader", "Lorg/jetbrains/kotlin/js/inline/FunctionReader;", "functionsByFunctionNodes", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "getFunctionsByFunctionNodes", "()Ljava/util/HashMap;", "fragmentByTag", "tag", "", "getFunctionDefinition", "Lorg/jetbrains/kotlin/js/inline/InlineFunctionDefinition;", "call", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "scope", "Lorg/jetbrains/kotlin/js/inline/InliningScope;", "getFunctionDefinitionImpl", "hasFunctionDefinition", "", "lookUpFunctionDirect", "callsiteScope", "lookUpFunctionExternal", "fragment", "lookUpFunctionIndirect", "lookUpStaticFunction", "functionName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "lookUpStaticFunctionByTag", "functionTag", "tryExtractCallableReference", "invocation", "loadInfo", "FragmentInfo", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionDefinitionLoader {
    private final Map<JsProgramFragment, FragmentInfo> fragmentInfo;
    private final FunctionReader functionReader;
    private final HashMap<JsFunction, FunctionWithWrapper> functionsByFunctionNodes;
    private final JsInliner inliner;

    public FunctionDefinitionLoader(JsInliner inliner) {
        Intrinsics.checkNotNullParameter(inliner, "inliner");
        this.inliner = inliner;
        this.functionsByFunctionNodes = new HashMap<>();
        this.functionReader = new FunctionReader(inliner.getReporter(), inliner.getConfig(), inliner.getBindingContext());
        Set<JsProgramFragment> newFragments = inliner.getTranslationResult().getNewFragments();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsProgramFragment jsProgramFragment : newFragments) {
            Pair pair = TuplesKt.to(jsProgramFragment, loadInfo(jsProgramFragment));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.fragmentInfo = linkedHashMap;
    }

    public final InlineFunctionDefinition getFunctionDefinition(JsInvocation call, InliningScope scope) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(scope, "scope");
        InlineFunctionDefinition functionDefinitionImpl = getFunctionDefinitionImpl(call, scope);
        Intrinsics.checkNotNull(functionDefinitionImpl);
        return functionDefinitionImpl;
    }

    public final boolean hasFunctionDefinition(JsInvocation call, InliningScope scope) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return getFunctionDefinitionImpl(call, scope) != null;
    }

    public final HashMap<JsFunction, FunctionWithWrapper> getFunctionsByFunctionNodes() {
        return this.functionsByFunctionNodes;
    }

    private final InlineFunctionDefinition getFunctionDefinitionImpl(JsInvocation call, InliningScope scope) {
        this.fragmentInfo.containsKey(scope.getFragment());
        InlineFunctionDefinition inlineFunctionDefinitionLookUpFunctionDirect = lookUpFunctionDirect(call, scope);
        if (inlineFunctionDefinitionLookUpFunctionDirect != null) {
            return inlineFunctionDefinitionLookUpFunctionDirect;
        }
        InlineFunctionDefinition inlineFunctionDefinitionLookUpFunctionIndirect = lookUpFunctionIndirect(call, scope);
        return inlineFunctionDefinitionLookUpFunctionIndirect == null ? lookUpFunctionExternal(call, scope.getFragment()) : inlineFunctionDefinitionLookUpFunctionIndirect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FunctionDefinitionLoader.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001BA\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\nJ\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003JK\u0010\u0012\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0007HÖ\u0001R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/context/FunctionDefinitionLoader$FragmentInfo;", "", "functions", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "accessors", "", "localAccessors", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getAccessors", "()Ljava/util/Map;", "getFunctions", "getLocalAccessors", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class FragmentInfo {
        private final Map<String, FunctionWithWrapper> accessors;
        private final Map<JsName, FunctionWithWrapper> functions;
        private final Map<CallableDescriptor, FunctionWithWrapper> localAccessors;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FragmentInfo copy$default(FragmentInfo fragmentInfo, Map map, Map map2, Map map3, int i, Object obj) {
            if ((i & 1) != 0) {
                map = fragmentInfo.functions;
            }
            if ((i & 2) != 0) {
                map2 = fragmentInfo.accessors;
            }
            if ((i & 4) != 0) {
                map3 = fragmentInfo.localAccessors;
            }
            return fragmentInfo.copy(map, map2, map3);
        }

        public final Map<JsName, FunctionWithWrapper> component1() {
            return this.functions;
        }

        public final Map<String, FunctionWithWrapper> component2() {
            return this.accessors;
        }

        public final Map<CallableDescriptor, FunctionWithWrapper> component3() {
            return this.localAccessors;
        }

        public final FragmentInfo copy(Map<JsName, FunctionWithWrapper> functions, Map<String, FunctionWithWrapper> accessors, Map<CallableDescriptor, FunctionWithWrapper> localAccessors) {
            Intrinsics.checkNotNullParameter(functions, "functions");
            Intrinsics.checkNotNullParameter(accessors, "accessors");
            Intrinsics.checkNotNullParameter(localAccessors, "localAccessors");
            return new FragmentInfo(functions, accessors, localAccessors);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FragmentInfo)) {
                return false;
            }
            FragmentInfo fragmentInfo = (FragmentInfo) other;
            return Intrinsics.areEqual(this.functions, fragmentInfo.functions) && Intrinsics.areEqual(this.accessors, fragmentInfo.accessors) && Intrinsics.areEqual(this.localAccessors, fragmentInfo.localAccessors);
        }

        public int hashCode() {
            return (((this.functions.hashCode() * 31) + this.accessors.hashCode()) * 31) + this.localAccessors.hashCode();
        }

        public String toString() {
            return "FragmentInfo(functions=" + this.functions + ", accessors=" + this.accessors + ", localAccessors=" + this.localAccessors + ')';
        }

        public FragmentInfo(Map<JsName, FunctionWithWrapper> functions, Map<String, FunctionWithWrapper> accessors, Map<CallableDescriptor, FunctionWithWrapper> localAccessors) {
            Intrinsics.checkNotNullParameter(functions, "functions");
            Intrinsics.checkNotNullParameter(accessors, "accessors");
            Intrinsics.checkNotNullParameter(localAccessors, "localAccessors");
            this.functions = functions;
            this.accessors = accessors;
            this.localAccessors = localAccessors;
        }

        public final Map<JsName, FunctionWithWrapper> getFunctions() {
            return this.functions;
        }

        public final Map<String, FunctionWithWrapper> getAccessors() {
            return this.accessors;
        }

        public final Map<CallableDescriptor, FunctionWithWrapper> getLocalAccessors() {
            return this.localAccessors;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentInfo loadInfo(JsProgramFragment jsProgramFragment) {
        FragmentInfo fragmentInfo = new FragmentInfo(CollectUtilsKt.collectNamedFunctionsAndWrappers(CollectionsKt.listOf(jsProgramFragment)), CollectUtilsKt.collectAccessors(CollectionsKt.listOf(jsProgramFragment)), CollectUtilsKt.collectLocalFunctions((List<JsProgramFragment>) CollectionsKt.listOf(jsProgramFragment)));
        for (FunctionWithWrapper functionWithWrapper : SequencesKt.plus(CollectionsKt.asSequence(fragmentInfo.component1().values()), CollectionsKt.asSequence(fragmentInfo.component2().values()))) {
            this.functionsByFunctionNodes.put(functionWithWrapper.getFunction(), functionWithWrapper);
        }
        return fragmentInfo;
    }

    private final FunctionWithWrapper lookUpStaticFunction(JsName functionName, JsProgramFragment fragment) {
        FragmentInfo fragmentInfo = this.fragmentInfo.get(fragment);
        if (fragmentInfo != null) {
            return fragmentInfo.getFunctions().get(functionName);
        }
        return null;
    }

    private final FunctionWithWrapper lookUpStaticFunctionByTag(String functionTag, JsProgramFragment fragment) {
        FragmentInfo fragmentInfo = this.fragmentInfo.get(fragment);
        if (fragmentInfo != null) {
            return fragmentInfo.getAccessors().get(functionTag);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00aa  */
    private final InlineFunctionDefinition lookUpFunctionIndirect(JsInvocation call, InliningScope scope) {
        JsExpression qualifier;
        FunctionWithWrapper functionWithWrapper;
        FunctionWithWrapper functionWithWrapperLookUpStaticFunction;
        FragmentInfo fragmentInfo;
        FunctionWithWrapper functionWithWrapper2;
        CallableDescriptor descriptor = MetadataProperties.getDescriptor(call);
        if (descriptor != null && (fragmentInfo = this.fragmentInfo.get(scope.getFragment())) != null && (functionWithWrapper2 = fragmentInfo.getLocalAccessors().get(descriptor)) != null) {
            InlineFunctionDefinition inlineFunctionDefinition = new InlineFunctionDefinition(functionWithWrapper2, null);
            this.inliner.process(inlineFunctionDefinition, call, scope.getFragment(), scope);
            return inlineFunctionDefinition;
        }
        if (InvocationUtilsKt.isCallInvocation(call)) {
            JsExpression qualifier2 = call.getQualifier();
            Intrinsics.checkNotNull(qualifier2, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
            qualifier = ((JsNameRef) qualifier2).getQualifier();
            Intrinsics.checkNotNull(qualifier);
            Intrinsics.checkNotNull(qualifier);
        } else {
            qualifier = call.getQualifier();
            Intrinsics.checkNotNull(qualifier);
        }
        JsExpression transitiveStaticRef = InvocationUtilsKt.getTransitiveStaticRef(qualifier);
        if (transitiveStaticRef instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) transitiveStaticRef;
            functionWithWrapper = tryExtractCallableReference(jsInvocation);
            if (functionWithWrapper == null) {
                JsName simpleName = InvocationUtilsKt.getSimpleName(jsInvocation);
                if (simpleName == null || (functionWithWrapperLookUpStaticFunction = lookUpStaticFunction(simpleName, scope.getFragment())) == null) {
                    functionWithWrapper = null;
                } else {
                    if (!FunctionUtilsKt.isFunctionCreator(functionWithWrapperLookUpStaticFunction.getFunction())) {
                        functionWithWrapperLookUpStaticFunction = null;
                    }
                    functionWithWrapper = functionWithWrapperLookUpStaticFunction;
                }
            }
        } else if (transitiveStaticRef instanceof JsNameRef) {
            functionWithWrapper = lookUpStaticFunction(((JsNameRef) transitiveStaticRef).getName(), scope.getFragment());
        } else if (transitiveStaticRef instanceof JsFunction) {
            functionWithWrapper = this.functionsByFunctionNodes.get(transitiveStaticRef);
            if (functionWithWrapper == null) {
                functionWithWrapper = new FunctionWithWrapper((JsFunction) transitiveStaticRef, null);
            }
        } else {
            functionWithWrapper = null;
        }
        if (functionWithWrapper == null) {
            return null;
        }
        InlineFunctionDefinition inlineFunctionDefinition2 = new InlineFunctionDefinition(functionWithWrapper, null);
        this.inliner.process(inlineFunctionDefinition2, call, scope.getFragment(), scope);
        return inlineFunctionDefinition2;
    }

    private final JsProgramFragment fragmentByTag(String tag) {
        TranslationUnit translationUnit = this.inliner.getTranslationResult().getInlineFunctionTagMap().get(tag);
        if (translationUnit == null) {
            return null;
        }
        final JsProgramFragment fragment = this.inliner.getTranslationResult().getTranslationResult(translationUnit).getFragment();
        Map<JsProgramFragment, FragmentInfo> map = this.fragmentInfo;
        final Function1<JsProgramFragment, FragmentInfo> function1 = new Function1<JsProgramFragment, FragmentInfo>() { // from class: org.jetbrains.kotlin.js.inline.context.FunctionDefinitionLoader$fragmentByTag$1$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FunctionDefinitionLoader.FragmentInfo invoke(JsProgramFragment it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.loadInfo(fragment);
            }
        };
        map.computeIfAbsent(fragment, new Function() { // from class: org.jetbrains.kotlin.js.inline.context.FunctionDefinitionLoader$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FunctionDefinitionLoader.fragmentByTag$lambda$15$lambda$14$lambda$13(function1, obj);
            }
        });
        return fragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FragmentInfo fragmentByTag$lambda$15$lambda$14$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (FragmentInfo) tmp0.invoke(obj);
    }

    private final InlineFunctionDefinition lookUpFunctionDirect(JsInvocation call, InliningScope callsiteScope) {
        FunctionWithWrapper functionWithWrapperLookUpStaticFunctionByTag;
        CallableDescriptor descriptor = MetadataProperties.getDescriptor(call);
        if (descriptor == null) {
            return null;
        }
        String functionTag = Namer.getFunctionTag(descriptor, this.inliner.getConfig(), this.inliner.getBindingContext());
        Intrinsics.checkNotNullExpressionValue(functionTag, "getFunctionTag(...)");
        JsProgramFragment jsProgramFragmentFragmentByTag = fragmentByTag(functionTag);
        if (jsProgramFragmentFragmentByTag == null || (functionWithWrapperLookUpStaticFunctionByTag = lookUpStaticFunctionByTag(functionTag, jsProgramFragmentFragmentByTag)) == null) {
            return null;
        }
        InlineFunctionDefinition inlineFunctionDefinition = new InlineFunctionDefinition(functionWithWrapperLookUpStaticFunctionByTag, functionTag);
        this.inliner.process(inlineFunctionDefinition, call, jsProgramFragmentFragmentByTag, callsiteScope);
        return inlineFunctionDefinition;
    }

    private final InlineFunctionDefinition lookUpFunctionExternal(JsInvocation call, JsProgramFragment fragment) {
        FunctionWithWrapper functionWithWrapper;
        CallableDescriptor descriptor = MetadataProperties.getDescriptor(call);
        if (descriptor == null || (functionWithWrapper = this.functionReader.get(descriptor, fragment)) == null) {
            return null;
        }
        return new InlineFunctionDefinition(functionWithWrapper, Namer.getFunctionTag(descriptor, this.inliner.getConfig(), this.inliner.getBindingContext()));
    }

    private final FunctionWithWrapper tryExtractCallableReference(JsInvocation invocation) {
        if (MetadataProperties.isCallableReference(invocation)) {
            JsExpression jsExpression = invocation.getArguments().get(1);
            if (jsExpression instanceof JsFunction) {
                return new FunctionWithWrapper((JsFunction) jsExpression, null);
            }
        }
        return null;
    }
}
