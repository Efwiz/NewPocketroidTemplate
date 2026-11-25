package org.jetbrains.kotlin.js.inline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;
import org.jetbrains.kotlin.js.translate.declaration.InlineCoroutineUtilKt;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: InliningScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H$J\u001a\u0010\u0011\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0012\u001a\u00020\u0013H$J(\u0010\u0014\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u0017H\u0002J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000bH$J\u000e\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0013H\u0014R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0001\u0002 !¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InliningScope;", "", "()V", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "localFunctionCache", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "publicFunctionCache", "", "addImport", "", "tag", "vars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "addInlinedDeclaration", "declaration", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "computeIfAbsent", "function", "fn", "Lkotlin/Function0;", "hasImport", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "name", "importFunctionDefinition", "definition", "Lorg/jetbrains/kotlin/js/inline/InlineFunctionDefinition;", "preprocess", "statement", "Lorg/jetbrains/kotlin/js/inline/ImportIntoFragmentInliningScope;", "Lorg/jetbrains/kotlin/js/inline/ImportIntoWrapperInliningScope;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InliningScope {
    private final Map<JsFunction, JsFunction> localFunctionCache;
    private final Map<String, JsFunction> publicFunctionCache;

    public /* synthetic */ InliningScope(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    protected abstract void addImport(String tag, JsVars vars);

    protected abstract void addInlinedDeclaration(String tag, JsStatement declaration);

    public abstract JsProgramFragment getFragment();

    protected abstract JsName hasImport(JsName name2, String tag);

    protected void preprocess(JsStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
    }

    private InliningScope() {
        this.publicFunctionCache = new LinkedHashMap();
        this.localFunctionCache = new LinkedHashMap();
    }

    private final JsFunction computeIfAbsent(String tag, JsFunction function, final Function0<JsFunction> fn) {
        if (tag == null) {
            Map<JsFunction, JsFunction> map = this.localFunctionCache;
            final Function1<JsFunction, JsFunction> function1 = new Function1<JsFunction, JsFunction>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope.computeIfAbsent.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsFunction invoke(JsFunction it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return fn.invoke();
                }
            };
            JsFunction jsFunctionComputeIfAbsent = map.computeIfAbsent(function, new Function() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return InliningScope.computeIfAbsent$lambda$0(function1, obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(jsFunctionComputeIfAbsent, "computeIfAbsent(...)");
            return jsFunctionComputeIfAbsent;
        }
        Map<String, JsFunction> map2 = this.publicFunctionCache;
        final Function1<String, JsFunction> function2 = new Function1<String, JsFunction>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope.computeIfAbsent.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JsFunction invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return fn.invoke();
            }
        };
        JsFunction jsFunctionComputeIfAbsent2 = map2.computeIfAbsent(tag, new Function() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InliningScope.computeIfAbsent$lambda$1(function2, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(jsFunctionComputeIfAbsent2, "computeIfAbsent(...)");
        return jsFunctionComputeIfAbsent2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsFunction computeIfAbsent$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (JsFunction) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsFunction computeIfAbsent$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (JsFunction) tmp0.invoke(obj);
    }

    public final JsFunction importFunctionDefinition(final InlineFunctionDefinition definition) {
        Intrinsics.checkNotNullParameter(definition, "definition");
        JsFunction jsFunctionDeepCopy = computeIfAbsent(definition.getTag(), definition.getFn().getFunction(), new Function0<JsFunction>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$importFunctionDefinition$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsFunction invoke() {
                JsVars jsVars;
                String importTag;
                final HashMap map = new HashMap();
                ArrayList arrayList = new ArrayList();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                JsBlock wrapperBody = definition.getFn().getWrapperBody();
                if (wrapperBody != null) {
                    InliningScope inliningScope = this;
                    List<JsStatement> statements = wrapperBody.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    for (JsStatement jsStatement : SequencesKt.map(SequencesKt.filterNot(CollectionsKt.asSequence(statements), new Function1<JsStatement, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$importFunctionDefinition$result$1$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(JsStatement jsStatement2) {
                            return Boolean.valueOf(jsStatement2 instanceof JsReturn);
                        }
                    }), new Function1<JsStatement, JsStatement>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$importFunctionDefinition$result$1$1$2
                        @Override // kotlin.jvm.functions.Function1
                        public final JsStatement invoke(JsStatement jsStatement2) {
                            return jsStatement2.deepCopy();
                        }
                    })) {
                        Intrinsics.checkNotNull(jsStatement);
                        inliningScope.preprocess(jsStatement);
                        if ((jsStatement instanceof JsVars) && (importTag = CollectUtilsKt.getImportTag((jsVars = (JsVars) jsStatement))) != null) {
                            JsName name2 = jsVars.getVars().get(0).getName();
                            Intrinsics.checkNotNull(name2);
                            JsName jsNameHasImport = inliningScope.hasImport(name2, importTag);
                            if (jsNameHasImport == null) {
                                jsNameHasImport = JsScope.declareTemporaryName(name2.getIdent());
                                jsNameHasImport.copyMetadataFrom(name2);
                                linkedHashMap.put(jsStatement, importTag);
                                arrayList.add(jsStatement);
                                Intrinsics.checkNotNullExpressionValue(jsNameHasImport, "also(...)");
                            }
                            if (name2 != jsNameHasImport) {
                                JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(jsNameHasImport, (JsExpression) null);
                                Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
                                map.put(name2, jsNameRefPureFqn);
                            }
                        } else {
                            arrayList.add(jsStatement);
                        }
                    }
                }
                ArrayList<JsStatement> arrayList2 = arrayList;
                for (JsName jsName : SequencesKt.filter(SequencesKt.flatMap(CollectionsKt.asSequence(arrayList2), new Function1<JsStatement, Sequence<? extends JsName>>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$importFunctionDefinition$result$1.2
                    @Override // kotlin.jvm.functions.Function1
                    public final Sequence<JsName> invoke(JsStatement node) {
                        Intrinsics.checkNotNullParameter(node, "node");
                        return CollectionsKt.asSequence(CollectUtilsKt.collectDefinedNamesInAllScopes(node));
                    }
                }), new Function1<JsName, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.InliningScope$importFunctionDefinition$result$1.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(JsName name3) {
                        Intrinsics.checkNotNullParameter(name3, "name");
                        return Boolean.valueOf(!map.containsKey(name3));
                    }
                })) {
                    JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(jsName.getIdent());
                    Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
                    jsNameDeclareTemporaryName.copyMetadataFrom(jsName);
                    JsNameRef jsNameRefPureFqn2 = JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null);
                    Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn2, "pureFqn(...)");
                    map.put(jsName, jsNameRefPureFqn2);
                }
                JsBlock jsBlock = new JsBlock(arrayList);
                HashMap map2 = map;
                RewriteUtilsKt.replaceNames(jsBlock, map2);
                for (Map.Entry<JsName, JsFunction> entry : CollectUtilsKt.collectNamedFunctions(jsBlock).entrySet()) {
                    JsName key = entry.getKey();
                    JsFunction value = entry.getValue();
                    if (MetadataProperties.getStaticRef(key) instanceof JsFunction) {
                        MetadataProperties.setStaticRef(key, value);
                    }
                }
                InliningScope inliningScope2 = this;
                InlineFunctionDefinition inlineFunctionDefinition = definition;
                for (JsStatement jsStatement2 : arrayList2) {
                    if ((jsStatement2 instanceof JsVars) && linkedHashMap.containsKey(jsStatement2)) {
                        Object obj = linkedHashMap.get(jsStatement2);
                        Intrinsics.checkNotNull(obj);
                        inliningScope2.addImport((String) obj, (JsVars) jsStatement2);
                    } else {
                        inliningScope2.addInlinedDeclaration(inlineFunctionDefinition.getTag(), jsStatement2);
                    }
                }
                JsFunction jsFunctionDeepCopy2 = definition.getFn().getFunction().deepCopy();
                Intrinsics.checkNotNullExpressionValue(jsFunctionDeepCopy2, "deepCopy(...)");
                RewriteUtilsKt.replaceNames(jsFunctionDeepCopy2, map2);
                jsFunctionDeepCopy2.setBody((JsBlock) InlineCoroutineUtilKt.transformSpecialFunctionsToCoroutineMetadata(jsFunctionDeepCopy2.getBody()));
                return jsFunctionDeepCopy2;
            }
        }).deepCopy();
        Intrinsics.checkNotNullExpressionValue(jsFunctionDeepCopy, "deepCopy(...)");
        List<JsParameter> parameters = jsFunctionDeepCopy.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (JsParameter jsParameter : list) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(jsParameter.getName().getIdent());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            JsName name2 = jsParameter.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            jsNameDeclareTemporaryName.copyMetadataFrom(name2);
            Pair pair = TuplesKt.to(jsParameter.getName(), JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        RewriteUtilsKt.replaceNames(jsFunctionDeepCopy, linkedHashMap);
        return jsFunctionDeepCopy;
    }
}
