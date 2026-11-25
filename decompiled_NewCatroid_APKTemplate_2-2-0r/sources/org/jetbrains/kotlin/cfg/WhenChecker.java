package org.jetbrains.kotlin.cfg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001e\u0010\f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0014\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J&\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0019H\u0007J\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00020!0\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017J&\u0010$\u001a\b\u0012\u0004\u0012\u00020!0\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u0019H\u0007J\u0018\u0010&\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0007J\u001a\u0010'\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010(\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017J \u0010)\u001a\u00020\u0015*\u0006\u0012\u0002\b\u00030*2\u0006\u0010\n\u001a\u00020+2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenChecker;", "", "()V", "exhaustivenessCheckers", "", "Lorg/jetbrains/kotlin/cfg/WhenExhaustivenessChecker;", "checkDeprecatedWhenSyntax", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "checkDuplicatedLabels", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkSealedWhenIsReserved", "sink", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "containsNullCase", "", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getClassDescriptorOfTypeIfEnum", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getClassDescriptorOfTypeIfSealed", "getClassIdForEnumSubject", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassIdForTypeIfEnum", "getEnumMissingCases", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "enumClassDescriptor", "getMissingCases", "getSealedMissingCases", "sealedClassDescriptor", "isWhenExhaustive", "whenSubjectType", "whenSubjectTypeWithoutSmartCasts", "isTrivial", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WhenChecker {
    public static final WhenChecker INSTANCE = new WhenChecker();
    private static final List<WhenExhaustivenessChecker> exhaustivenessCheckers = CollectionsKt.listOf((Object[]) new WhenExhaustivenessChecker[]{WhenOnBooleanExhaustivenessChecker.INSTANCE, WhenOnEnumExhaustivenessChecker.INSTANCE, WhenOnSealedExhaustivenessChecker.INSTANCE});

    private WhenChecker() {
    }

    @JvmStatic
    public static final ClassId getClassIdForEnumSubject(KtWhenExpression expression, BindingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return getClassIdForTypeIfEnum(whenSubjectType(expression, context));
    }

    @JvmStatic
    public static final ClassId getClassIdForTypeIfEnum(KotlinType type) {
        ClassDescriptor classDescriptorOfTypeIfEnum = getClassDescriptorOfTypeIfEnum(type);
        if (classDescriptorOfTypeIfEnum != null) {
            return DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptorOfTypeIfEnum);
        }
        return null;
    }

    @JvmStatic
    public static final ClassDescriptor getClassDescriptorOfTypeIfEnum(KotlinType type) {
        ClassDescriptor classDescriptor;
        if (type == null || (classDescriptor = TypeUtils.getClassDescriptor(type)) == null || classDescriptor.getKind() != ClassKind.ENUM_CLASS) {
            return null;
        }
        return classDescriptor;
    }

    @JvmStatic
    public static final ClassDescriptor getClassDescriptorOfTypeIfSealed(KotlinType type) {
        ClassDescriptor classDescriptor;
        if (type == null || (classDescriptor = TypeUtils.getClassDescriptor(type)) == null || !DescriptorUtils.isSealedClass(classDescriptor)) {
            return null;
        }
        return classDescriptor;
    }

    @JvmStatic
    public static final KotlinType whenSubjectType(KtWhenExpression expression, BindingContext context) {
        KotlinType defaultType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtProperty subjectVariable = expression.getSubjectVariable();
        KtExpression subjectExpression = expression.getSubjectExpression();
        if (subjectVariable != null) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) context.get(BindingContext.VARIABLE, subjectVariable);
            if (variableDescriptor != null) {
                return variableDescriptor.getType();
            }
            return null;
        }
        if (subjectExpression == null) {
            return null;
        }
        ExplicitSmartCasts explicitSmartCasts = (ExplicitSmartCasts) context.get(BindingContext.SMARTCAST, subjectExpression);
        return (explicitSmartCasts == null || (defaultType = explicitSmartCasts.getDefaultType()) == null) ? context.getType(subjectExpression) : defaultType;
    }

    public final KotlinType whenSubjectTypeWithoutSmartCasts(KtWhenExpression expression, BindingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtProperty subjectVariable = expression.getSubjectVariable();
        KtExpression subjectExpression = expression.getSubjectExpression();
        if (subjectVariable == null) {
            if (subjectExpression != null) {
                return context.getType(subjectExpression);
            }
            return null;
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) context.get(BindingContext.VARIABLE, subjectVariable);
        if (variableDescriptor != null) {
            return variableDescriptor.getType();
        }
        return null;
    }

    @JvmStatic
    public static final List<WhenMissingCase> getEnumMissingCases(KtWhenExpression expression, BindingContext context, ClassDescriptor enumClassDescriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(enumClassDescriptor, "enumClassDescriptor");
        return WhenOnEnumExhaustivenessChecker.INSTANCE.getMissingCases(expression, context, enumClassDescriptor, false);
    }

    @JvmStatic
    public static final List<WhenMissingCase> getSealedMissingCases(KtWhenExpression expression, BindingContext context, ClassDescriptor sealedClassDescriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sealedClassDescriptor, "sealedClassDescriptor");
        return WhenOnSealedExhaustivenessChecker.INSTANCE.getMissingCases(expression, context, sealedClassDescriptor, false);
    }

    public final List<WhenMissingCase> getMissingCases(KtWhenExpression expression, BindingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KotlinType kotlinTypeWhenSubjectType = whenSubjectType(expression, context);
        if (kotlinTypeWhenSubjectType == null) {
            return CollectionsKt.listOf(WhenMissingCase.Unknown.INSTANCE);
        }
        boolean isMarkedNullable = kotlinTypeWhenSubjectType.getIsMarkedNullable();
        List<WhenExhaustivenessChecker> list = exhaustivenessCheckers;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((WhenExhaustivenessChecker) obj).isApplicable(kotlinTypeWhenSubjectType)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return CollectionsKt.listOf(WhenMissingCase.Unknown.INSTANCE);
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((WhenExhaustivenessChecker) it.next()).getMissingCases(expression, context, TypeUtils.getClassDescriptor(kotlinTypeWhenSubjectType), isMarkedNullable));
        }
        return CollectionsKt.flatten(arrayList4);
    }

    @JvmStatic
    public static final boolean isWhenExhaustive(KtWhenExpression expression, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        WhenChecker whenChecker = INSTANCE;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        if (!whenChecker.getMissingCases(expression, context).isEmpty()) {
            return false;
        }
        trace.record(BindingContext.EXHAUSTIVE_WHEN, expression);
        return true;
    }

    public final boolean containsNullCase(KtWhenExpression expression, BindingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return WhenOnNullableExhaustivenessChecker.INSTANCE.getMissingCases(expression, context, true).isEmpty();
    }

    public final void checkDuplicatedLabels(KtWhenExpression expression, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        KtWhenConditionIsPattern ktWhenConditionIsPattern;
        KtTypeReference typeReference;
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (expression.getSubjectExpression() == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (KtWhenEntry ktWhenEntry : expression.getEntries()) {
            if (!ktWhenEntry.isElse()) {
                KtWhenCondition[] conditions = ktWhenEntry.getConditions();
                Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
                for (KtWhenCondition ktWhenCondition : conditions) {
                    if (ktWhenCondition instanceof KtWhenConditionWithExpression) {
                        KtExpression expression2 = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression();
                        if (expression2 != null) {
                            ConstantExpressionEvaluator.Companion companion = ConstantExpressionEvaluator.INSTANCE;
                            BindingContext context = trace.getContext();
                            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                            CompileTimeConstant<?> constant = companion.getConstant(expression2, context);
                            if (constant != null) {
                                Boolean bool = (Boolean) linkedHashMap.get(constant);
                                if (Intrinsics.areEqual((Object) bool, (Object) true)) {
                                    checkDuplicatedLabels$report(trace, expression2);
                                } else if (Intrinsics.areEqual((Object) bool, (Object) false)) {
                                    if (isTrivial(constant, expression2, languageVersionSettings)) {
                                        Object objRemove = linkedHashMap2.remove(constant);
                                        Intrinsics.checkNotNull(objRemove);
                                        checkDuplicatedLabels$report(trace, (KtExpression) objRemove);
                                        linkedHashMap.put(constant, true);
                                    } else {
                                        checkDuplicatedLabels$report(trace, expression2);
                                    }
                                } else if (bool == null) {
                                    boolean zIsTrivial = isTrivial(constant, expression2, languageVersionSettings);
                                    linkedHashMap.put(constant, Boolean.valueOf(zIsTrivial));
                                    if (!zIsTrivial) {
                                        linkedHashMap2.put(constant, expression2);
                                    }
                                }
                            }
                        }
                    } else if ((ktWhenCondition instanceof KtWhenConditionIsPattern) && (typeReference = (ktWhenConditionIsPattern = (KtWhenConditionIsPattern) ktWhenCondition).getTypeReference()) != null && (kotlinType = (KotlinType) trace.get(BindingContext.TYPE, typeReference)) != null) {
                        Pair pair = TuplesKt.to(kotlinType, Boolean.valueOf(ktWhenConditionIsPattern.isNegated()));
                        if (hashSet.contains(pair)) {
                            trace.report(Errors.DUPLICATE_LABEL_IN_WHEN.on(typeReference));
                        } else {
                            hashSet.add(pair);
                        }
                    }
                }
            }
        }
    }

    private static final void checkDuplicatedLabels$report(BindingTrace bindingTrace, KtExpression ktExpression) {
        bindingTrace.report(Errors.DUPLICATE_LABEL_IN_WHEN.on(ktExpression));
    }

    private final boolean isTrivial(CompileTimeConstant<?> compileTimeConstant, KtExpression ktExpression, LanguageVersionSettings languageVersionSettings) {
        if (compileTimeConstant.getUsesVariableAsConstant()) {
            return false;
        }
        if (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitSimplificationOfNonTrivialConstBooleanExpressions)) {
            return true;
        }
        return !ConstantExpressionEvaluator.INSTANCE.isComplexBooleanConstant(ktExpression, compileTimeConstant);
    }

    public final void checkDeprecatedWhenSyntax(BindingTrace trace, KtWhenExpression expression) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression.getSubjectExpression() != null) {
            return;
        }
        for (KtWhenEntry ktWhenEntry : expression.getEntries()) {
            if (!ktWhenEntry.isElse()) {
                for (PsiElement firstChild = ktWhenEntry.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                    if (firstChild.getNode().getElementType() == KtTokens.COMMA) {
                        trace.report(Errors.COMMA_IN_WHEN_CONDITION_WITHOUT_ARGUMENT.on(firstChild));
                    }
                    if (firstChild.getNode().getElementType() == KtTokens.ARROW) {
                        break;
                    }
                }
            }
        }
    }

    public final void checkSealedWhenIsReserved(DiagnosticSink sink, PsiElement element) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(element, "element");
        PsiElement previousWord = KtPsiUtil.getPreviousWord(element, "sealed");
        if (previousWord != null) {
            sink.report(Errors.UNSUPPORTED_SEALED_WHEN.on(previousWord));
        }
    }
}
