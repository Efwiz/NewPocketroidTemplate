package org.jetbrains.kotlin.js.inline.util;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.collectors.InstanceCollector;
import org.jetbrains.kotlin.js.translate.expression.InlineMetadata;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: collectUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000z\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\"\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0002\u001a \u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u001a\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0001\u001a\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000f\u001a\u00020\u0010\u001a,\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0003\"\b\b\u0000\u0010\u0017*\u00020\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u000f\u001a\u00020\u0010\u001a \u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u001a\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a \u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001d0\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u001a\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001d0\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a&\u0010\u001e\u001a\u001a\u0012\u0004\u0012\u00020\u0013\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\u001f0\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a \u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u000b0\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u001a\u001a\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00040\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010$\u001a\u0004\u0018\u00010\u000b2\u0006\u0010%\u001a\u00020\u0004\u001a\u0010\u0010&\u001a\u0004\u0018\u00010\n2\u0006\u0010'\u001a\u00020(\u001a\u001c\u0010)\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u00042\n\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007H\u0002\u001a\u0010\u0010*\u001a\u0004\u0018\u00010\n2\u0006\u0010+\u001a\u00020,\u001a\u0016\u0010-\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020/0\t*\u00020\u0010\u001a\u0010\u00100\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u001d\u001a\u001a\u00101\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u001d2\b\b\u0002\u0010\u0014\u001a\u00020\u0001¨\u00062"}, d2 = {"appendArguments", "", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "collectAccessors", "", "", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "fragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "collectDefinedNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "skipLabelsAndCatches", "collectDefinedNamesInAllScopes", "collectInstances", ExifInterface.GPS_DIRECTION_TRUE, "klass", "Ljava/lang/Class;", "collectLocalFunctions", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "collectNamedFunctions", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "collectNamedFunctionsAndMetadata", "Lkotlin/Pair;", "collectNamedFunctionsAndWrappers", "collectNamedFunctionsOrMetadata", "collectReferencedNames", "collectUsedNames", "extractFunction", "expression", "extractImportTag", "jsVar", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "extractImportTagImpl", "getImportTag", "jsVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "collectBreakContinueTargets", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "collectFreeVariables", "collectLocalVariables", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CollectUtilsKt {
    public static final Set<JsName> collectReferencedNames(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectReferencedNames.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression initExpression = x.getInitExpression();
                if (initExpression != null) {
                    accept(initExpression);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                super.visitNameRef(nameRef);
                JsName name2 = nameRef.getName();
                if (name2 != null) {
                    linkedHashSet.add(name2);
                }
            }
        }.accept(scope);
        return linkedHashSet;
    }

    public static final Set<JsName> collectUsedNames(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectUsedNames.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression initExpression = x.getInitExpression();
                if (initExpression != null) {
                    accept(initExpression);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                super.visitNameRef(nameRef);
                JsName name2 = nameRef.getName();
                if (name2 == null || nameRef.getQualifier() != null) {
                    return;
                }
                linkedHashSet.add(name2);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                CollectionsKt.addAll(linkedHashSet, CollectUtilsKt.collectFreeVariables(x));
            }
        }.accept(scope);
        return linkedHashSet;
    }

    public static final Set<JsName> collectDefinedNames(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return collectDefinedNames(scope, false);
    }

    public static final Set<JsName> collectDefinedNames(JsNode scope, final boolean z) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectDefinedNames.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression initExpression = x.getInitExpression();
                if (initExpression != null) {
                    accept(initExpression);
                }
                linkedHashSet.add(x.getName());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) {
                JsName name2;
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if ((expression instanceof JsFunction) && (name2 = ((JsFunction) expression).getName()) != null) {
                    linkedHashSet.add(name2);
                }
                super.visitExpressionStatement(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                JsName name2;
                Intrinsics.checkNotNullParameter(x, "x");
                if (!z && (name2 = x.getName()) != null) {
                    linkedHashSet.add(name2);
                }
                super.visitLabel(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCatch(JsCatch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (!z) {
                    linkedHashSet.add(x.getParameter().getName());
                }
                super.visitCatch(x);
            }
        }.accept(scope);
        return linkedHashSet;
    }

    public static final Set<JsName> collectDefinedNamesInAllScopes(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectDefinedNamesInAllScopes.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visit(x);
                linkedHashSet.add(x.getName());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitFunction(x);
                Set<JsName> set = linkedHashSet;
                List<JsParameter> parameters = x.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<JsParameter> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    JsName name2 = ((JsParameter) it.next()).getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    arrayList.add(name2);
                }
                CollectionsKt.addAll(set, arrayList);
                JsName name3 = x.getName();
                if (name3 != null) {
                    linkedHashSet.add(name3);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getName();
                if (name2 != null) {
                    linkedHashSet.add(name2);
                }
                super.visitLabel(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCatch(JsCatch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                linkedHashSet.add(x.getParameter().getName());
                super.visitCatch(x);
            }
        }.accept(scope);
        return linkedHashSet;
    }

    public static final Set<JsName> collectFreeVariables(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        JsBlock body = jsFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        Set<JsName> setCollectUsedNames = collectUsedNames(body);
        JsBlock body2 = jsFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
        Set setMinus = SetsKt.minus((Set) setCollectUsedNames, (Iterable) collectDefinedNames(body2));
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            JsName name2 = ((JsParameter) it.next()).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(name2);
        }
        return SetsKt.minus(setMinus, (Iterable) arrayList);
    }

    public static final Set<JsName> collectLocalVariables(JsFunction jsFunction, boolean z) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        JsBlock body = jsFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        Set<JsName> setCollectDefinedNames = collectDefinedNames(body, z);
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            JsName name2 = ((JsParameter) it.next()).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(name2);
        }
        return SetsKt.plus((Set) setCollectDefinedNames, (Iterable) arrayList);
    }

    public static /* synthetic */ Set collectLocalVariables$default(JsFunction jsFunction, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return collectLocalVariables(jsFunction, z);
    }

    public static final Map<JsName, JsFunction> collectNamedFunctions(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Map<JsName, Pair<FunctionWithWrapper, JsExpression>> mapCollectNamedFunctionsAndMetadata = collectNamedFunctionsAndMetadata(scope);
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapCollectNamedFunctionsAndMetadata.size()));
        Iterator<T> it = mapCollectNamedFunctionsAndMetadata.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), ((FunctionWithWrapper) ((Pair) entry.getValue()).getFirst()).getFunction());
        }
        return linkedHashMap;
    }

    public static final Map<JsName, JsExpression> collectNamedFunctionsOrMetadata(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Map<JsName, Pair<FunctionWithWrapper, JsExpression>> mapCollectNamedFunctionsAndMetadata = collectNamedFunctionsAndMetadata(scope);
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapCollectNamedFunctionsAndMetadata.size()));
        Iterator<T> it = mapCollectNamedFunctionsAndMetadata.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), (JsExpression) ((Pair) entry.getValue()).getSecond());
        }
        return linkedHashMap;
    }

    public static final Map<JsName, JsFunction> collectNamedFunctions(List<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsProgramFragment jsProgramFragment : fragments) {
            linkedHashMap.putAll(collectNamedFunctions(jsProgramFragment.getDeclarationBlock()));
            linkedHashMap.putAll(collectNamedFunctions(jsProgramFragment.getInitializerBlock()));
        }
        return linkedHashMap;
    }

    public static final Map<JsName, FunctionWithWrapper> collectNamedFunctionsAndWrappers(List<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsProgramFragment jsProgramFragment : fragments) {
            Map<JsName, Pair<FunctionWithWrapper, JsExpression>> mapCollectNamedFunctionsAndMetadata = collectNamedFunctionsAndMetadata(jsProgramFragment.getDeclarationBlock());
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(mapCollectNamedFunctionsAndMetadata.size()));
            Iterator<T> it = mapCollectNamedFunctionsAndMetadata.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap2.put(entry.getKey(), (FunctionWithWrapper) ((Pair) entry.getValue()).getFirst());
            }
            linkedHashMap.putAll(linkedHashMap2);
            Map<JsName, Pair<FunctionWithWrapper, JsExpression>> mapCollectNamedFunctionsAndMetadata2 = collectNamedFunctionsAndMetadata(jsProgramFragment.getInitializerBlock());
            LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt.mapCapacity(mapCollectNamedFunctionsAndMetadata2.size()));
            Iterator<T> it2 = mapCollectNamedFunctionsAndMetadata2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                linkedHashMap3.put(entry2.getKey(), (FunctionWithWrapper) ((Pair) entry2.getValue()).getFirst());
            }
            linkedHashMap.putAll(linkedHashMap3);
        }
        return linkedHashMap;
    }

    public static final Map<JsName, Pair<FunctionWithWrapper, JsExpression>> collectNamedFunctionsAndMetadata(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        scope.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectNamedFunctionsAndMetadata.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBinaryExpression(JsBinaryOperation x) {
                JsName name2;
                Intrinsics.checkNotNullParameter(x, "x");
                Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(x);
                if (pairDecomposeAssignment != null) {
                    JsExpression jsExpressionComponent1 = pairDecomposeAssignment.component1();
                    JsExpression jsExpressionComponent2 = pairDecomposeAssignment.component2();
                    if ((jsExpressionComponent1 instanceof JsNameRef) && (name2 = ((JsNameRef) jsExpressionComponent1).getName()) != null) {
                        Intrinsics.checkNotNull(jsExpressionComponent2);
                        FunctionWithWrapper functionWithWrapperExtractFunction = CollectUtilsKt.extractFunction(jsExpressionComponent2);
                        if (functionWithWrapperExtractFunction != null) {
                            linkedHashMap.put(name2, new Pair<>(new FunctionWithWrapper(functionWithWrapperExtractFunction.getFunction(), functionWithWrapperExtractFunction.getWrapperBody()), jsExpressionComponent2));
                        }
                    }
                }
                super.visitBinaryExpression(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                FunctionWithWrapper functionWithWrapperExtractFunction;
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression initExpression = x.getInitExpression();
                JsName name2 = x.getName();
                if (initExpression != null && name2 != null && (functionWithWrapperExtractFunction = CollectUtilsKt.extractFunction(initExpression)) != null) {
                    linkedHashMap.put(name2, new Pair<>(functionWithWrapperExtractFunction, initExpression));
                }
                super.visit(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getName();
                if (name2 != null) {
                    linkedHashMap.put(name2, new Pair<>(new FunctionWithWrapper(x, null), x));
                }
                super.visitFunction(x);
            }
        });
        return linkedHashMap;
    }

    public static final Map<String, FunctionWithWrapper> collectAccessors(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final HashMap map = new HashMap();
        scope.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectAccessors.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                InlineMetadata inlineMetadataDecompose = InlineMetadata.INSTANCE.decompose(invocation);
                if (inlineMetadataDecompose != null) {
                    HashMap<String, FunctionWithWrapper> map2 = map;
                    String value = inlineMetadataDecompose.getTag().getValue();
                    Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                    map2.put(value, inlineMetadataDecompose.getFunction());
                }
                super.visitInvocation(invocation);
            }
        });
        return map;
    }

    public static final Map<String, FunctionWithWrapper> collectAccessors(Iterable<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<JsProgramFragment> it = fragments.iterator();
        while (it.hasNext()) {
            linkedHashMap.putAll(collectAccessors(it.next().getDeclarationBlock()));
        }
        return linkedHashMap;
    }

    public static final Map<CallableDescriptor, FunctionWithWrapper> collectLocalFunctions(JsNode scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        final HashMap map = new HashMap();
        scope.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectLocalFunctions.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                FunctionWithWrapper functionWithWrapperTryExtractFunction = InlineMetadata.INSTANCE.tryExtractFunction(invocation);
                if (functionWithWrapperTryExtractFunction != null) {
                    HashMap<CallableDescriptor, FunctionWithWrapper> map2 = map;
                    FunctionDescriptor functionDescriptor = MetadataProperties.getFunctionDescriptor(functionWithWrapperTryExtractFunction.getFunction());
                    if (functionDescriptor != null) {
                        map2.put(functionDescriptor, functionWithWrapperTryExtractFunction);
                    }
                }
                super.visitInvocation(invocation);
            }
        });
        return map;
    }

    public static final Map<CallableDescriptor, FunctionWithWrapper> collectLocalFunctions(List<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<JsProgramFragment> it = fragments.iterator();
        while (it.hasNext()) {
            linkedHashMap.putAll(collectLocalFunctions(it.next().getDeclarationBlock()));
        }
        return linkedHashMap;
    }

    public static final FunctionWithWrapper extractFunction(JsExpression expression) {
        FunctionWithWrapper function;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof JsFunction) {
            return new FunctionWithWrapper((JsFunction) expression, null);
        }
        InlineMetadata inlineMetadataDecompose = InlineMetadata.INSTANCE.decompose(expression);
        return (inlineMetadataDecompose == null || (function = inlineMetadataDecompose.getFunction()) == null) ? InlineMetadata.INSTANCE.tryExtractFunction(expression) : function;
    }

    public static final <T extends JsNode> List<T> collectInstances(Class<T> klass, JsNode scope) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(scope, "scope");
        InstanceCollector instanceCollector = new InstanceCollector(klass, false);
        instanceCollector.accept(scope);
        return instanceCollector.getCollected();
    }

    public static final Map<JsContinue, JsStatement> collectBreakContinueTargets(JsNode jsNode) {
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        jsNode.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt.collectBreakContinueTargets.1
            private Map<JsName, JsStatement> breakTargets = new LinkedHashMap();
            private Map<JsName, JsStatement> continueTargets = new LinkedHashMap();
            private JsStatement defaultBreakTarget;
            private JsStatement defaultContinueTarget;

            public final JsStatement getDefaultBreakTarget() {
                return this.defaultBreakTarget;
            }

            public final void setDefaultBreakTarget(JsStatement jsStatement) {
                this.defaultBreakTarget = jsStatement;
            }

            public final Map<JsName, JsStatement> getBreakTargets() {
                return this.breakTargets;
            }

            public final void setBreakTargets(Map<JsName, JsStatement> map) {
                Intrinsics.checkNotNullParameter(map, "<set-?>");
                this.breakTargets = map;
            }

            public final JsStatement getDefaultContinueTarget() {
                return this.defaultContinueTarget;
            }

            public final void setDefaultContinueTarget(JsStatement jsStatement) {
                this.defaultContinueTarget = jsStatement;
            }

            public final Map<JsName, JsStatement> getContinueTargets() {
                return this.continueTargets;
            }

            public final void setContinueTargets(Map<JsName, JsStatement> map) {
                Intrinsics.checkNotNullParameter(map, "<set-?>");
                this.continueTargets = map;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                Intrinsics.checkNotNullParameter(x, "x");
                final JsStatement statement = x.getStatement();
                if (statement instanceof JsDoWhile) {
                    Intrinsics.checkNotNull(statement);
                    JsStatement body = ((JsDoWhile) statement).getBody();
                    Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                    handleLoop(statement, body, x.getName());
                    return;
                }
                if (statement instanceof JsWhile) {
                    Intrinsics.checkNotNull(statement);
                    JsStatement body2 = ((JsWhile) statement).getBody();
                    Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
                    handleLoop(statement, body2, x.getName());
                    return;
                }
                if (statement instanceof JsFor) {
                    Intrinsics.checkNotNull(statement);
                    JsStatement body3 = ((JsFor) statement).getBody();
                    Intrinsics.checkNotNullExpressionValue(body3, "getBody(...)");
                    handleLoop(statement, body3, x.getName());
                    return;
                }
                if (statement instanceof JsSwitch) {
                    Intrinsics.checkNotNull(statement);
                    handleSwitch((JsSwitch) statement, x.getName());
                } else {
                    JsName name2 = x.getName();
                    JsStatement statement2 = x.getStatement();
                    Intrinsics.checkNotNullExpressionValue(statement2, "getStatement(...)");
                    withBreakAndContinue(name2, statement2, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt$collectBreakContinueTargets$1$visitLabel$1
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
                            accept(statement);
                        }
                    });
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitWhile(JsWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsWhile jsWhile = x;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                handleLoop(jsWhile, body, null);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsDoWhile jsDoWhile = x;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                handleLoop(jsDoWhile, body, null);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFor(JsFor x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsFor jsFor = x;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                handleLoop(jsFor, body, null);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsSwitch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                handleSwitch(x, null);
            }

            private final void handleSwitch(final JsSwitch statement, JsName label) {
                withBreakAndContinue$default(this, label, statement, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt$collectBreakContinueTargets$1$handleSwitch$1
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
                        List<JsSwitchMember> cases = statement.getCases();
                        Intrinsics.checkNotNullExpressionValue(cases, "getCases(...)");
                        CollectUtilsKt.C47681 c47681 = this;
                        Iterator<T> it = cases.iterator();
                        while (it.hasNext()) {
                            c47681.accept((JsSwitchMember) it.next());
                        }
                    }
                }, 4, null);
            }

            private final void handleLoop(JsStatement loop, final JsStatement body, JsName label) {
                withBreakAndContinue(label, loop, loop, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.util.CollectUtilsKt$collectBreakContinueTargets$1$handleLoop$1
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
                        body.accept(this);
                    }
                });
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                JsStatement jsStatement;
                Intrinsics.checkNotNullParameter(x, "x");
                JsNameRef label = x.getLabel();
                JsName name2 = label != null ? label.getName() : null;
                Map<JsContinue, JsStatement> map = linkedHashMap;
                if (name2 == null) {
                    jsStatement = this.defaultBreakTarget;
                    Intrinsics.checkNotNull(jsStatement);
                } else {
                    JsStatement jsStatement2 = this.breakTargets.get(name2);
                    Intrinsics.checkNotNull(jsStatement2);
                    jsStatement = jsStatement2;
                }
                map.put(x, jsStatement);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                JsStatement jsStatement;
                Intrinsics.checkNotNullParameter(x, "x");
                JsNameRef label = x.getLabel();
                JsName name2 = label != null ? label.getName() : null;
                Map<JsContinue, JsStatement> map = linkedHashMap;
                if (name2 == null) {
                    jsStatement = this.defaultContinueTarget;
                    Intrinsics.checkNotNull(jsStatement);
                } else {
                    JsStatement jsStatement2 = this.continueTargets.get(name2);
                    Intrinsics.checkNotNull(jsStatement2);
                    jsStatement = jsStatement2;
                }
                map.put(x, jsStatement);
            }

            static /* synthetic */ void withBreakAndContinue$default(C47681 c47681, JsName jsName, JsStatement jsStatement, JsStatement jsStatement2, Function0 function0, int i, Object obj) {
                if ((i & 4) != 0) {
                    jsStatement2 = null;
                }
                c47681.withBreakAndContinue(jsName, jsStatement, jsStatement2, function0);
            }

            private final void withBreakAndContinue(JsName label, JsStatement breakTargetStatement, JsStatement continueTargetStatement, Function0<Unit> action) {
                Pair pair;
                JsStatement jsStatement = this.defaultBreakTarget;
                JsStatement jsStatement2 = this.defaultContinueTarget;
                if (label != null) {
                    pair = new Pair(this.breakTargets.get(label), this.continueTargets.get(label));
                } else {
                    pair = new Pair(null, null);
                }
                JsStatement jsStatement3 = (JsStatement) pair.component1();
                JsStatement jsStatement4 = (JsStatement) pair.component2();
                this.defaultBreakTarget = breakTargetStatement;
                if (label != null) {
                    this.breakTargets.put(label, breakTargetStatement);
                    this.continueTargets.put(label, continueTargetStatement);
                }
                if (continueTargetStatement != null) {
                    this.defaultContinueTarget = continueTargetStatement;
                }
                action.invoke();
                this.defaultBreakTarget = jsStatement;
                this.defaultContinueTarget = jsStatement2;
                if (label != null) {
                    this.breakTargets.put(label, jsStatement3);
                    this.continueTargets.put(label, jsStatement4);
                }
            }
        });
        return linkedHashMap;
    }

    public static final String getImportTag(JsVars jsVars) {
        Intrinsics.checkNotNullParameter(jsVars, "jsVars");
        if (jsVars.getVars().size() != 1) {
            return null;
        }
        JsVars.JsVar jsVar = jsVars.getVars().get(0);
        JsName name2 = jsVar.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (!MetadataProperties.getImported(name2)) {
            return null;
        }
        Intrinsics.checkNotNull(jsVar);
        return extractImportTag(jsVar);
    }

    public static final String extractImportTag(JsVars.JsVar jsVar) {
        Intrinsics.checkNotNullParameter(jsVar, "jsVar");
        JsExpression initExpression = jsVar.getInitExpression();
        if (initExpression == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if ((initExpression instanceof JsInvocation) || (initExpression instanceof JsNew)) {
            sb.append(jsVar.getName().toString()).append(":");
        }
        if (extractImportTagImpl(initExpression, sb)) {
            return sb.toString();
        }
        return null;
    }

    private static final boolean extractImportTagImpl(JsExpression jsExpression, StringBuilder sb) {
        if (jsExpression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) jsExpression;
            if (jsNameRef.getQualifier() != null) {
                JsExpression qualifier = jsNameRef.getQualifier();
                Intrinsics.checkNotNull(qualifier);
                if (!extractImportTagImpl(qualifier, sb)) {
                    return false;
                }
                sb.append('.');
            }
            sb.append(JsToStringGenerationVisitor.javaScriptString(jsNameRef.getIdent()));
            return true;
        }
        if (jsExpression instanceof JsArrayAccess) {
            JsArrayAccess jsArrayAccess = (JsArrayAccess) jsExpression;
            JsExpression arrayExpression = jsArrayAccess.getArrayExpression();
            Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
            if (!extractImportTagImpl(arrayExpression, sb)) {
                return false;
            }
            sb.append(".");
            JsExpression indexExpression = jsArrayAccess.getIndexExpression();
            JsStringLiteral jsStringLiteral = indexExpression instanceof JsStringLiteral ? (JsStringLiteral) indexExpression : null;
            if (jsStringLiteral == null) {
                return false;
            }
            sb.append(JsToStringGenerationVisitor.javaScriptString(jsStringLiteral.getValue()));
            return true;
        }
        if (jsExpression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) jsExpression;
            JsExpression qualifier2 = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
            if (!extractImportTagImpl(qualifier2, sb)) {
                return false;
            }
            List<JsExpression> arguments = jsInvocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            return appendArguments(arguments, sb);
        }
        if (!(jsExpression instanceof JsNew)) {
            return false;
        }
        JsNew jsNew = (JsNew) jsExpression;
        JsExpression constructorExpression = jsNew.getConstructorExpression();
        Intrinsics.checkNotNullExpressionValue(constructorExpression, "getConstructorExpression(...)");
        if (!extractImportTagImpl(constructorExpression, sb)) {
            return false;
        }
        List<JsExpression> arguments2 = jsNew.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
        return appendArguments(arguments2, sb);
    }

    private static final boolean appendArguments(List<? extends JsExpression> list, StringBuilder sb) {
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            JsExpression jsExpression = (JsExpression) obj;
            if (!(jsExpression instanceof JsIntLiteral)) {
                return false;
            }
            sb.append(i == 0 ? "(" : ",");
            sb.append(((JsIntLiteral) jsExpression).value);
            i = i2;
        }
        sb.append(Constant.AFTER_QUTO);
        return true;
    }
}
