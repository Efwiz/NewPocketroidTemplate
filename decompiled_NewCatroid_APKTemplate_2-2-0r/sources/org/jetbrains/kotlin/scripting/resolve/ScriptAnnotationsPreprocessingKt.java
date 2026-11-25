package org.jetbrains.kotlin.scripting.resolve;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.ArrayFqNames;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.utils.ParametersMapKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptAnnotationsPreprocessing.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a*\u0010\u0013\u001a\u00020\u0014*\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000\u001a\u0018\u0010\u001d\u001a\u00020\b*\u0004\u0018\u00010\b2\b\b\u0002\u0010\u001e\u001a\u00020\bH\u0000\u001a\u0012\u0010\u001f\u001a\u0004\u0018\u00010 *\u0006\u0012\u0002\b\u00030!H\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\""}, d2 = {"ARRAY_OF_METHODS", "", "Lorg/jetbrains/kotlin/name/Name;", "isArrayCall", "", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "(Lorg/jetbrains/kotlin/psi/KtCallExpression;)Z", "typeName", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getTypeName", "(Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;)Ljava/lang/String;", "constructAnnotation", "", "psi", "targetClass", "Lkotlin/reflect/KClass;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "evaluateToConstantArrayValue", "Lorg/jetbrains/kotlin/resolve/constants/ArrayValue;", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "elementExpressions", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "expectedElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "orAnonymous", Namer.METADATA_CLASS_KIND, "toRuntimeValue", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptAnnotationsPreprocessingKt {
    private static final Set<Name> ARRAY_OF_METHODS = SetsKt.plus(SetsKt.setOf((Object[]) new Name[]{ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION(), ArrayFqNames.INSTANCE.getEMPTY_ARRAY()}), (Iterable) CollectionsKt.toSet(ArrayFqNames.INSTANCE.getPRIMITIVE_TYPE_TO_ARRAY().values()));

    private static final boolean isArrayCall(KtCallExpression ktCallExpression) {
        Set<Name> set = ARRAY_OF_METHODS;
        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
        Intrinsics.checkNotNull(calleeExpression);
        return set.contains(Name.identifier(calleeExpression.getText()));
    }

    public static final String getTypeName(KtAnnotationEntry ktAnnotationEntry) {
        Intrinsics.checkNotNullParameter(ktAnnotationEntry, "<this>");
        KtTypeReference typeReference = ktAnnotationEntry.getTypeReference();
        KtTypeElement typeElement = typeReference != null ? typeReference.getTypeElement() : null;
        KtUserType ktUserType = typeElement instanceof KtUserType ? (KtUserType) typeElement : null;
        return orAnonymous$default(ktUserType != null ? ktUserType.getReferencedName() : null, null, 1, null);
    }

    public static /* synthetic */ String orAnonymous$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return orAnonymous(str, str2);
    }

    public static final String orAnonymous(String str, String kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (str == null) {
            return "<anonymous" + (!StringsKt.isBlank(kind) ? " " + kind : "") + '>';
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x00f9  */
    public static final Annotation constructAnnotation(KtAnnotationEntry psi, KClass<? extends Annotation> targetClass, Project project) {
        ArrayValue arrayValueEvaluateToConstantValue;
        Name asName;
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        Intrinsics.checkNotNullParameter(project, "project");
        Name nameSpecial = Name.special("<script-annotations-preprocessing>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        ConstantExpressionEvaluator constantExpressionEvaluator = new ConstantExpressionEvaluator(new ModuleDescriptorImpl(nameSpecial, new LockBasedStorageManager("scriptAnnotationsPreprocessing", new Runnable() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptAnnotationsPreprocessingKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProgressManager.checkCanceled();
            }
        }, new Function1<InterruptedException, Unit>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptAnnotationsPreprocessingKt$constructAnnotation$module$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InterruptedException interruptedException) {
                invoke2(interruptedException);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InterruptedException interruptedException) {
                throw new ProcessCanceledException(interruptedException);
            }
        }), DefaultBuiltIns.INSTANCE.getInstance(), null, null, null, 56, null), LanguageVersionSettingsImpl.DEFAULT, project, null, 8, null);
        BindingTraceContext bindingTraceContext = new BindingTraceContext();
        List<? extends ValueArgument> valueArguments = psi.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueArgument valueArgument : list) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            Intrinsics.checkNotNull(argumentExpression);
            if (argumentExpression instanceof KtCollectionLiteralExpression) {
                List<KtExpression> innerExpressions = ((KtCollectionLiteralExpression) argumentExpression).getInnerExpressions();
                Intrinsics.checkNotNullExpressionValue(innerExpressions, "getInnerExpressions(...)");
                SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
                Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
                arrayValueEvaluateToConstantValue = evaluateToConstantArrayValue(constantExpressionEvaluator, innerExpressions, bindingTraceContext, NO_EXPECTED_TYPE);
            } else if (argumentExpression instanceof KtCallExpression) {
                KtCallExpression ktCallExpression = (KtCallExpression) argumentExpression;
                if (isArrayCall(ktCallExpression)) {
                    List<KtValueArgument> valueArguments2 = ktCallExpression.getValueArguments();
                    Intrinsics.checkNotNullExpressionValue(valueArguments2, "getValueArguments(...)");
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<T> it = valueArguments2.iterator();
                    while (it.hasNext()) {
                        KtExpression argumentExpression2 = ((KtValueArgument) it.next()).getArgumentExpression();
                        if (argumentExpression2 != null) {
                            arrayList2.add(argumentExpression2);
                        }
                    }
                    SimpleType NO_EXPECTED_TYPE2 = TypeUtils.NO_EXPECTED_TYPE;
                    Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE2, "NO_EXPECTED_TYPE");
                    arrayValueEvaluateToConstantValue = evaluateToConstantArrayValue(constantExpressionEvaluator, arrayList2, bindingTraceContext, NO_EXPECTED_TYPE2);
                } else {
                    KtExpression argumentExpression3 = valueArgument.getArgumentExpression();
                    Intrinsics.checkNotNull(argumentExpression3);
                    SimpleType NO_EXPECTED_TYPE3 = TypeUtils.NO_EXPECTED_TYPE;
                    Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE3, "NO_EXPECTED_TYPE");
                    arrayValueEvaluateToConstantValue = constantExpressionEvaluator.evaluateToConstantValue(argumentExpression3, bindingTraceContext, NO_EXPECTED_TYPE3);
                }
            } else {
                KtExpression argumentExpression4 = valueArgument.getArgumentExpression();
                Intrinsics.checkNotNull(argumentExpression4);
                SimpleType NO_EXPECTED_TYPE4 = TypeUtils.NO_EXPECTED_TYPE;
                Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE4, "NO_EXPECTED_TYPE");
                arrayValueEvaluateToConstantValue = constantExpressionEvaluator.evaluateToConstantValue(argumentExpression4, bindingTraceContext, NO_EXPECTED_TYPE4);
            }
            ValueArgumentName argumentName = valueArgument.getArgumentName();
            Object runtimeValue = null;
            String string = (argumentName == null || (asName = argumentName.getAsName()) == null) ? null : asName.toString();
            if (arrayValueEvaluateToConstantValue != null) {
                runtimeValue = toRuntimeValue(arrayValueEvaluateToConstantValue);
            }
            arrayList.add(TuplesKt.to(string, runtimeValue));
        }
        ArrayList arrayList3 = arrayList;
        Map<KParameter, ? extends Object> mapTryCreateCallableMappingFromNamedArgs = ParametersMapKt.tryCreateCallableMappingFromNamedArgs((KCallable) CollectionsKt.first(targetClass.getConstructors()), arrayList3);
        if (mapTryCreateCallableMappingFromNamedArgs == null) {
            return new InvalidScriptResolverAnnotation(getTypeName(psi), arrayList3, null, 4, null);
        }
        try {
            KFunction primaryConstructor = KClasses.getPrimaryConstructor(targetClass);
            Intrinsics.checkNotNull(primaryConstructor);
            return (Annotation) primaryConstructor.callBy(mapTryCreateCallableMappingFromNamedArgs);
        } catch (Exception e) {
            return new InvalidScriptResolverAnnotation(getTypeName(psi), arrayList3, e);
        }
    }

    public static final ArrayValue evaluateToConstantArrayValue(ConstantExpressionEvaluator constantExpressionEvaluator, List<? extends KtExpression> elementExpressions, BindingTrace trace, KotlinType expectedElementType) {
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "<this>");
        Intrinsics.checkNotNullParameter(elementExpressions, "elementExpressions");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expectedElementType, "expectedElementType");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = elementExpressions.iterator();
        while (it.hasNext()) {
            CompileTimeConstant<?> compileTimeConstantEvaluateExpression = constantExpressionEvaluator.evaluateExpression((KtExpression) it.next(), trace, expectedElementType);
            ConstantValue<?> constantValue = compileTimeConstantEvaluateExpression != null ? compileTimeConstantEvaluateExpression.toConstantValue(expectedElementType) : null;
            if (constantValue != null) {
                arrayList.add(constantValue);
            }
        }
        ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        return constantValueFactory.createArrayValue(arrayList, NO_EXPECTED_TYPE);
    }

    private static final Object toRuntimeValue(ConstantValue<?> constantValue) {
        if (!(constantValue instanceof ArrayValue)) {
            return constantValue.getValue();
        }
        List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(value, 10));
        Iterator<T> it = value.iterator();
        while (it.hasNext()) {
            arrayList.add(toRuntimeValue((ConstantValue) it.next()));
        }
        return arrayList.toArray(new Object[0]);
    }
}
