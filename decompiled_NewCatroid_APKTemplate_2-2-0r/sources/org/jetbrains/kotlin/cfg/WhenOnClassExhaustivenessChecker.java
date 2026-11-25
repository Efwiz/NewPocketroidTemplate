package org.jetbrains.kotlin.cfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0004J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u0005*\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0014\u0010\u001f\u001a\u00020\u000b*\u00020\f2\u0006\u0010 \u001a\u00020\u0005H\u0002R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0018\u0010\n\u001a\u00020\u000b*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0007¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenOnClassExhaustivenessChecker;", "Lorg/jetbrains/kotlin/cfg/WhenExhaustivenessChecker;", "()V", "deepSealedSubclasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDeepSealedSubclasses", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/util/Set;", "enumEntries", "getEnumEntries", "negated", "", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "getNegated", "(Lorg/jetbrains/kotlin/psi/KtWhenCondition;)Z", "subclasses", "getSubclasses", "createWhenMissingCaseForClassOrEnum", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "classDescriptor", "getMissingClassCases", "", "whenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getCheckedDescriptor", "isRelevant", "checkedDescriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WhenOnClassExhaustivenessChecker implements WhenExhaustivenessChecker {
    private final KtSimpleNameExpression getReference(KtExpression expression) {
        if (expression instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) expression;
        }
        if (expression instanceof KtQualifiedExpression) {
            return getReference(((KtQualifiedExpression) expression).getSelectorExpression());
        }
        return null;
    }

    protected final Set<ClassDescriptor> getEnumEntries(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(classDescriptor.getUnsubstitutedInnerClassesScope());
        Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDescriptors) {
            if (DescriptorUtils.isEnumEntry((DeclarationDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (obj2 instanceof ClassDescriptor) {
                arrayList2.add(obj2);
            }
        }
        return CollectionsKt.toSet(arrayList2);
    }

    protected final Set<ClassDescriptor> getDeepSealedSubclasses(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Collection<ClassDescriptor> sealedSubclasses = classDescriptor.getSealedSubclasses();
        Intrinsics.checkNotNullExpressionValue(sealedSubclasses, "getSealedSubclasses(...)");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ClassDescriptor classDescriptor2 : sealedSubclasses) {
            Intrinsics.checkNotNull(classDescriptor2);
            CollectionsKt.addAll(linkedHashSet, getSubclasses(classDescriptor2));
        }
        return linkedHashSet;
    }

    private final Set<ClassDescriptor> getSubclasses(ClassDescriptor classDescriptor) {
        if (classDescriptor.getModality() == Modality.SEALED) {
            return getDeepSealedSubclasses(classDescriptor);
        }
        return classDescriptor.getKind() == ClassKind.ENUM_CLASS ? getEnumEntries(classDescriptor) : SetsKt.setOf(classDescriptor);
    }

    private final boolean getNegated(KtWhenCondition ktWhenCondition) {
        KtWhenConditionIsPattern ktWhenConditionIsPattern = ktWhenCondition instanceof KtWhenConditionIsPattern ? (KtWhenConditionIsPattern) ktWhenCondition : null;
        if (ktWhenConditionIsPattern != null) {
            return ktWhenConditionIsPattern.isNegated();
        }
        return false;
    }

    private final boolean isRelevant(KtWhenCondition ktWhenCondition, ClassDescriptor classDescriptor) {
        if (ktWhenCondition instanceof KtWhenConditionWithExpression) {
            ClassDescriptor classDescriptor2 = classDescriptor;
            if (!DescriptorUtils.isObject(classDescriptor2) && !DescriptorUtils.isEnumEntry(classDescriptor2)) {
                return false;
            }
        }
        return true;
    }

    private final ClassDescriptor getCheckedDescriptor(KtWhenCondition ktWhenCondition, BindingContext bindingContext) {
        KtExpression expression;
        KtSimpleNameExpression reference;
        if (ktWhenCondition instanceof KtWhenConditionIsPattern) {
            KotlinType kotlinType = (KotlinType) bindingContext.get(BindingContext.TYPE, ((KtWhenConditionIsPattern) ktWhenCondition).getTypeReference());
            if (kotlinType == null) {
                return null;
            }
            return TypeUtils.getClassDescriptor(kotlinType);
        }
        if (!(ktWhenCondition instanceof KtWhenConditionWithExpression) || (expression = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression()) == null || (reference = getReference(expression)) == null) {
            return null;
        }
        Object obj = bindingContext.get(BindingContext.REFERENCE_TARGET, reference);
        if (obj instanceof ClassDescriptor) {
            return (ClassDescriptor) obj;
        }
        return null;
    }

    protected final List<WhenMissingCase> getMissingClassCases(KtWhenExpression whenExpression, Set<? extends ClassDescriptor> subclasses, BindingContext context) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(subclasses, "subclasses");
        Intrinsics.checkNotNullParameter(context, "context");
        if (subclasses.isEmpty()) {
            return CollectionsKt.listOf(WhenMissingCase.Unknown.INSTANCE);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<KtWhenEntry> it = whenExpression.getEntries().iterator();
        while (it.hasNext()) {
            KtWhenCondition[] conditions = it.next().getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                Intrinsics.checkNotNull(ktWhenCondition);
                boolean negated = getNegated(ktWhenCondition);
                ClassDescriptor checkedDescriptor = getCheckedDescriptor(ktWhenCondition, context);
                if (checkedDescriptor != null) {
                    Set<ClassDescriptor> subclasses2 = getSubclasses(checkedDescriptor);
                    Set<ClassDescriptor> set = subclasses2;
                    if (!(set instanceof Collection) || !set.isEmpty()) {
                        Iterator<T> it2 = set.iterator();
                        while (it2.hasNext()) {
                            if (subclasses.contains((ClassDescriptor) it2.next())) {
                                if (!isRelevant(ktWhenCondition, checkedDescriptor)) {
                                    break;
                                }
                                if (negated) {
                                    Set<ClassDescriptor> set2 = subclasses2;
                                    if (!linkedHashSet.containsAll(set2)) {
                                        linkedHashSet.addAll(subclasses);
                                        linkedHashSet.removeAll(set2);
                                        break;
                                    }
                                    return CollectionsKt.emptyList();
                                }
                                linkedHashSet.addAll(subclasses2);
                                break;
                            }
                        }
                    }
                }
            }
        }
        Set setMinus = SetsKt.minus((Set) subclasses, (Iterable) linkedHashSet);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setMinus, 10));
        Iterator it3 = setMinus.iterator();
        while (it3.hasNext()) {
            arrayList.add(createWhenMissingCaseForClassOrEnum((ClassDescriptor) it3.next()));
        }
        return arrayList;
    }

    private final WhenMissingCase createWhenMissingCaseForClassOrEnum(ClassDescriptor classDescriptor) {
        ClassDescriptor classDescriptor2 = classDescriptor;
        ClassId classIdForNonLocalClass = DescriptorUtils.getClassIdForNonLocalClass(classDescriptor2);
        Intrinsics.checkNotNullExpressionValue(classIdForNonLocalClass, "getClassIdForNonLocalClass(...)");
        if (classDescriptor.getKind() != ClassKind.ENUM_ENTRY) {
            ClassId classIdForNonLocalClass2 = DescriptorUtils.getClassIdForNonLocalClass(classDescriptor2);
            Intrinsics.checkNotNullExpressionValue(classIdForNonLocalClass2, "getClassIdForNonLocalClass(...)");
            return new WhenMissingCase.IsTypeCheckIsMissing(classIdForNonLocalClass2, classDescriptor.getKind().isSingleton());
        }
        ClassId outerClassId = classIdForNonLocalClass.getOuterClassId();
        if (outerClassId == null) {
            throw new IllegalStateException("Enum should have class id".toString());
        }
        Name shortClassName = classIdForNonLocalClass.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        return new WhenMissingCase.EnumCheckIsMissing(new CallableId(outerClassId, shortClassName));
    }
}
