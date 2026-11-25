package kotlin.script.experimental.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.api.ScriptExecutionWrapper;
import kotlin.script.experimental.impl.CompilationInternalsKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BasicJvmScriptEvaluator.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0096B¢\u0006\u0002\u0010\nJ&\u0010\u000b\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u000e\u001a\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010H\u0002¨\u0006\u0011²\u0006\n\u0010\u0012\u001a\u00020\tX\u008a\u0084\u0002"}, d2 = {"Lkotlin/script/experimental/jvm/BasicJvmScriptEvaluator;", "Lkotlin/script/experimental/api/ScriptEvaluator;", "()V", "invoke", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/EvaluationResult;", "compiledScript", "Lkotlin/script/experimental/api/CompiledScript;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/CompiledScript;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "evalWithConfigAndOtherScriptsResults", "", "Lkotlin/reflect/KClass;", "refinedEvalConfiguration", "importedScriptsEvalResults", "", PathUtil.KOTLIN_SCRIPTING_JVM_NAME, "configurationForOtherScripts"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicJvmScriptEvaluator implements ScriptEvaluator {

    /* JADX INFO: renamed from: kotlin.script.experimental.jvm.BasicJvmScriptEvaluator$invoke$1, reason: invalid class name */
    /* JADX INFO: compiled from: BasicJvmScriptEvaluator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.jvm.BasicJvmScriptEvaluator", f = "BasicJvmScriptEvaluator.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {19, 32}, m = "invoke$suspendImpl", n = {"$this", "compiledScript", "scriptEvaluationConfiguration", "$this", "compiledScript", "scriptClass", "configurationForOtherScripts$delegate", "sharedConfiguration", "sharedScripts", "reports$iv$iv", "results$iv$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BasicJvmScriptEvaluator.invoke$suspendImpl(BasicJvmScriptEvaluator.this, null, null, this);
        }
    }

    @Override // kotlin.script.experimental.api.ScriptEvaluator
    public Object invoke(CompiledScript compiledScript, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<EvaluationResult>> continuation) {
        return invoke$suspendImpl(this, compiledScript, scriptEvaluationConfiguration, continuation);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x010d A[Catch: all -> 0x023d, TryCatch #1 {all -> 0x023d, blocks: (B:42:0x013a, B:44:0x0149, B:36:0x0107, B:38:0x010d, B:47:0x0165, B:48:0x016e, B:50:0x0172, B:52:0x0191, B:53:0x0199, B:55:0x01a3, B:64:0x01f2, B:66:0x01f9, B:67:0x01ff, B:77:0x0227, B:56:0x01d1, B:59:0x01db, B:61:0x01e3, B:63:0x01ea, B:62:0x01e7, B:68:0x020c, B:70:0x0210, B:72:0x021c, B:73:0x0221, B:74:0x0222, B:78:0x022c, B:79:0x0231, B:45:0x0159, B:20:0x007a, B:26:0x0095, B:28:0x009b, B:30:0x00d9, B:32:0x00e1, B:34:0x00e7, B:35:0x00eb, B:80:0x0232, B:83:0x0237, B:84:0x023c, B:23:0x0086), top: B:91:0x002c, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x0133 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:41:0x0134  */
    /* JADX WARN: Code duplicated, block: B:44:0x0149 A[Catch: all -> 0x023d, TryCatch #1 {all -> 0x023d, blocks: (B:42:0x013a, B:44:0x0149, B:36:0x0107, B:38:0x010d, B:47:0x0165, B:48:0x016e, B:50:0x0172, B:52:0x0191, B:53:0x0199, B:55:0x01a3, B:64:0x01f2, B:66:0x01f9, B:67:0x01ff, B:77:0x0227, B:56:0x01d1, B:59:0x01db, B:61:0x01e3, B:63:0x01ea, B:62:0x01e7, B:68:0x020c, B:70:0x0210, B:72:0x021c, B:73:0x0221, B:74:0x0222, B:78:0x022c, B:79:0x0231, B:45:0x0159, B:20:0x007a, B:26:0x0095, B:28:0x009b, B:30:0x00d9, B:32:0x00e1, B:34:0x00e7, B:35:0x00eb, B:80:0x0232, B:83:0x0237, B:84:0x023c, B:23:0x0086), top: B:91:0x002c, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x001c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0134 -> B:42:0x013a). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    static /* synthetic */ java.lang.Object invoke$suspendImpl(kotlin.script.experimental.jvm.BasicJvmScriptEvaluator r19, kotlin.script.experimental.api.CompiledScript r20, kotlin.script.experimental.api.ScriptEvaluationConfiguration r21, kotlin.coroutines.Continuation<? super kotlin.script.experimental.api.ResultWithDiagnostics<kotlin.script.experimental.api.EvaluationResult>> r22) {
        /*
            Method dump skipped, instruction units count: 605
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.script.experimental.jvm.BasicJvmScriptEvaluator.invoke$suspendImpl(kotlin.script.experimental.jvm.BasicJvmScriptEvaluator, kotlin.script.experimental.api.CompiledScript, kotlin.script.experimental.api.ScriptEvaluationConfiguration, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final ScriptEvaluationConfiguration invoke$lambda$7$lambda$0(Lazy<? extends ScriptEvaluationConfiguration> lazy) {
        return lazy.getValue();
    }

    private final Object evalWithConfigAndOtherScriptsResults(KClass<?> kClass, ScriptEvaluationConfiguration scriptEvaluationConfiguration, List<EvaluationResult> list) {
        boolean zAreEqual;
        Object objInvoke;
        Map map;
        Map map2;
        String str;
        ScriptCompilationConfiguration scriptCompilationConfiguration = (ScriptCompilationConfiguration) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getCompilationConfiguration(ScriptEvaluationConfiguration.INSTANCE));
        if (scriptCompilationConfiguration == null || (str = (String) scriptCompilationConfiguration.get(CompilationInternalsKt.get_languageVersion(ScriptCompilationConfiguration.INSTANCE))) == null) {
            zAreEqual = false;
        } else {
            Boolean boolValueOf = null;
            Integer intOrNull = StringsKt.toIntOrNull(StringsKt.substringBefore$default(str, '.', (String) null, 2, (Object) null));
            if (intOrNull != null) {
                boolValueOf = Boolean.valueOf(intOrNull.intValue() >= 2);
            }
            zAreEqual = Intrinsics.areEqual((Object) boolValueOf, (Object) true);
        }
        final ArrayList arrayList = new ArrayList();
        List list2 = (List) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getPreviousSnippets(ScriptEvaluationConfiguration.INSTANCE));
        if (list2 != null) {
            arrayList.add(list2.toArray(new Object[0]));
        }
        List list3 = (List) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getConstructorArgs(ScriptEvaluationConfiguration.INSTANCE));
        if (list3 != null) {
            arrayList.addAll(list3);
        }
        if (zAreEqual && (map2 = (Map) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getProvidedProperties(ScriptEvaluationConfiguration.INSTANCE))) != null) {
            Iterator it = map2.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(((Map.Entry) it.next()).getValue());
            }
        }
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList.add(((EvaluationResult) it2.next()).getReturnValue().getScriptInstance());
        }
        List list4 = (List) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getImplicitReceivers(ScriptEvaluationConfiguration.INSTANCE));
        if (list4 != null) {
            arrayList.addAll(list4);
        }
        if (!zAreEqual && (map = (Map) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getProvidedProperties(ScriptEvaluationConfiguration.INSTANCE))) != null) {
            Iterator it3 = map.entrySet().iterator();
            while (it3.hasNext()) {
                arrayList.add(((Map.Entry) it3.next()).getValue());
            }
        }
        Constructor<?>[] constructors = JvmClassMappingKt.getJavaClass((KClass) kClass).getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        final Constructor constructor = (Constructor) ArraysKt.single(constructors);
        ScriptExecutionWrapper scriptExecutionWrapper = (ScriptExecutionWrapper) scriptEvaluationConfiguration.get(ScriptEvaluationKt.getScriptExecutionWrapper(ScriptEvaluationConfiguration.INSTANCE));
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(JvmClassMappingKt.getJavaClass((KClass) kClass).getClassLoader());
        try {
            if (scriptExecutionWrapper == null) {
                Object[] array = arrayList.toArray();
                objInvoke = constructor.newInstance(Arrays.copyOf(array, array.length));
            } else {
                objInvoke = scriptExecutionWrapper.invoke(new Function0<Object>() { // from class: kotlin.script.experimental.jvm.BasicJvmScriptEvaluator.evalWithConfigAndOtherScriptsResults.7
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() throws IllegalAccessException, InstantiationException, InvocationTargetException {
                        Constructor<?> constructor2 = constructor;
                        Object[] array2 = arrayList.toArray();
                        Object objNewInstance = constructor2.newInstance(Arrays.copyOf(array2, array2.length));
                        Intrinsics.checkNotNullExpressionValue(objNewInstance, "newInstance(...)");
                        return objNewInstance;
                    }
                });
            }
            Thread.currentThread().setContextClassLoader(contextClassLoader);
            Intrinsics.checkNotNull(objInvoke);
            return objInvoke;
        } catch (Throwable th) {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
            throw th;
        }
    }
}
