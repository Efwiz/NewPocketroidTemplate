package kotlin.script.experimental.api;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a%\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0016\u0010\u0015\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0016\"\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010\u0017\"/\u0010\u0000\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"+\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00020\u0001*\u00020\u000b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\f\u0010\r\"+\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0011\u0010\u0006¨\u0006\u0018"}, d2 = {"collectedAnnotations", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/ScriptSourceAnnotation;", "Lkotlin/script/experimental/api/ScriptCollectedDataKeys;", "getCollectedAnnotations", "(Lkotlin/script/experimental/api/ScriptCollectedDataKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "collectedAnnotations$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "commandLineArgs", "", "Lkotlin/script/experimental/api/ScriptEvaluationContextDataKeys;", "getCommandLineArgs", "(Lkotlin/script/experimental/api/ScriptEvaluationContextDataKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "commandLineArgs$delegate", "foundAnnotations", "", "getFoundAnnotations", "foundAnnotations$delegate", "merge", "Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "contexts", "", "([Lkotlin/script/experimental/api/ScriptEvaluationContextData;)Lkotlin/script/experimental/api/ScriptEvaluationContextData;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptDataKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptDataKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "foundAnnotations", "getFoundAnnotations(Lkotlin/script/experimental/api/ScriptCollectedDataKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptDataKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "collectedAnnotations", "getCollectedAnnotations(Lkotlin/script/experimental/api/ScriptCollectedDataKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptDataKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "commandLineArgs", "getCommandLineArgs(Lkotlin/script/experimental/api/ScriptEvaluationContextDataKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate foundAnnotations$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate collectedAnnotations$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Function1) new Function1<PropertiesCollection, List<? extends ScriptSourceAnnotation<?>>>() { // from class: kotlin.script.experimental.api.ScriptDataKt$collectedAnnotations$2
        @Override // kotlin.jvm.functions.Function1
        public final List<ScriptSourceAnnotation<?>> invoke(PropertiesCollection key) {
            Intrinsics.checkNotNullParameter(key, "$this$key");
            List list = (List) key.get(ScriptDataKt.getFoundAnnotations(ScriptCollectedData.INSTANCE));
            if (list == null) {
                return null;
            }
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(new ScriptSourceAnnotation((Annotation) it.next(), null));
            }
            return arrayList;
        }
    }, false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate commandLineArgs$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);

    public static final PropertiesCollection.Key<List<Annotation>> getFoundAnnotations(ScriptCollectedDataKeys scriptCollectedDataKeys) {
        Intrinsics.checkNotNullParameter(scriptCollectedDataKeys, "<this>");
        return foundAnnotations$delegate.getValue(scriptCollectedDataKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<List<ScriptSourceAnnotation<?>>> getCollectedAnnotations(ScriptCollectedDataKeys scriptCollectedDataKeys) {
        Intrinsics.checkNotNullParameter(scriptCollectedDataKeys, "<this>");
        return collectedAnnotations$delegate.getValue(scriptCollectedDataKeys, $$delegatedProperties[1]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ScriptEvaluationContextData merge(ScriptEvaluationContextData... contexts) {
        Intrinsics.checkNotNullParameter(contexts, "contexts");
        ArrayList arrayList = new ArrayList();
        for (ScriptEvaluationContextData scriptEvaluationContextData : contexts) {
            if (scriptEvaluationContextData != null && !scriptEvaluationContextData.isEmpty()) {
                arrayList.add(scriptEvaluationContextData);
            }
        }
        Function1 function1 = null;
        Object[] objArr = 0;
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.size() == 1) {
            return (ScriptEvaluationContextData) CollectionsKt.first((List) arrayList);
        }
        return new ScriptEvaluationContextData((Iterable) arrayList, function1, 2, (DefaultConstructorMarker) (objArr == true ? 1 : 0));
    }

    public static final PropertiesCollection.Key<List<String>> getCommandLineArgs(ScriptEvaluationContextDataKeys scriptEvaluationContextDataKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationContextDataKeys, "<this>");
        return commandLineArgs$delegate.getValue(scriptEvaluationContextDataKeys, $$delegatedProperties[2]);
    }
}
