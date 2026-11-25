package org.jetbrains.kotlin.asJava.elements;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightSimpleAnnotation;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: KtToJvmAnnotationsConverter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u001a2\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u001c\u0010\u0014\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0001\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00170\u00160\u0015H\u0000\u001a\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u0001*\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0001H\u0002\u001a\u001c\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0015*\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0001H\u0002\u001a\u001e\u0010\u001b\u001a\u0004\u0018\u00010\u0011*\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u001f\u001a\u0004\u0018\u00010\u0011*\u00020\u0012H\u0000\u001a\"\u0010 \u001a\u0004\u0018\u00010\u0011*\u00020\u00122\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\"H\u0000\u001a\u000e\u0010%\u001a\u0004\u0018\u00010\u0011*\u00020\u0012H\u0000\u001a\u000e\u0010&\u001a\u0004\u0018\u00010\u0011*\u00020\u0012H\u0000\u001a\u000e\u0010'\u001a\u0004\u0018\u00010\u0011*\u00020\u0012H\u0000\"\u0014\u0010\u0000\u001a\u00020\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t0\bj\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000\"*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t0\bj\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"JAVA_LANG_ANNOTATION_REPEATABLE_SHORT_NAME", "", "getJAVA_LANG_ANNOTATION_REPEATABLE_SHORT_NAME", "()Ljava/lang/String;", "KOTLIN_JVM_INTERNAL_REPEATABLE_CONTAINER", "javaAnnotationRetentionPolicyId", "Lorg/jetbrains/kotlin/name/ClassId;", "retentionMapping", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/resolve/constants/EnumValue;", "Lkotlin/collections/HashMap;", "targetMapping", "createRetentionRuntimeAnnotation", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSimpleAnnotation;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "asUltraLightSimpleAnnotation", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "qualifier", "argumentsList", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "extractAnnotationFqName", "attributeName", "extractArrayAnnotationFqNames", "tryConvertAs", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/name/FqName;", "to", "tryConvertAsMustBeDocumented", "tryConvertAsRepeatable", "owner", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "tryConvertAsRepeatableContainer", "tryConvertAsRetention", "tryConvertAsTarget", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtToJvmAnnotationsConverterKt {
    public static final String KOTLIN_JVM_INTERNAL_REPEATABLE_CONTAINER = "kotlin.jvm.internal.RepeatableContainer";
    private static final ClassId javaAnnotationRetentionPolicyId;
    private static final HashMap<String, EnumValue> retentionMapping;
    private static final HashMap<String, EnumValue> targetMapping;

    private static final String extractAnnotationFqName(PsiAnnotation psiAnnotation, String str) {
        Object next;
        List<JvmAnnotationAttribute> attributes = psiAnnotation.getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "getAttributes(...)");
        Iterator<T> it = attributes.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((JvmAnnotationAttribute) next).getAttributeName(), str));
        KtLightPsiNameValuePair ktLightPsiNameValuePair = next instanceof KtLightPsiNameValuePair ? (KtLightPsiNameValuePair) next : null;
        if (ktLightPsiNameValuePair == null) {
            return null;
        }
        PsiAnnotationMemberValue value = ktLightPsiNameValuePair.getValue();
        KtLightPsiLiteral ktLightPsiLiteral = value instanceof KtLightPsiLiteral ? (KtLightPsiLiteral) value : null;
        Object value2 = ktLightPsiLiteral != null ? ktLightPsiLiteral.getValue() : null;
        Pair pair = value2 instanceof Pair ? (Pair) value2 : null;
        if (pair == null) {
            return null;
        }
        Object first = pair.getFirst();
        ClassId classId = first instanceof ClassId ? (ClassId) first : null;
        if (classId == null) {
            return null;
        }
        Object second = pair.getSecond();
        Name name2 = second instanceof Name ? (Name) second : null;
        if (name2 == null) {
            return null;
        }
        return classId.asSingleFqName().asString() + '.' + name2.getIdentifier();
    }

    private static final List<String> extractArrayAnnotationFqNames(PsiAnnotation psiAnnotation, String str) {
        Object next;
        List<JvmAnnotationAttribute> attributes = psiAnnotation.getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "getAttributes(...)");
        Iterator<T> it = attributes.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((JvmAnnotationAttribute) next).getAttributeName(), str));
        JvmAnnotationAttribute jvmAnnotationAttribute = (JvmAnnotationAttribute) next;
        if (jvmAnnotationAttribute == null) {
            return null;
        }
        PsiNameValuePair psiNameValuePair = jvmAnnotationAttribute instanceof PsiNameValuePair ? (PsiNameValuePair) jvmAnnotationAttribute : null;
        if (psiNameValuePair == null) {
            return null;
        }
        PsiAnnotationMemberValue value = psiNameValuePair.getValue();
        PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = value instanceof PsiArrayInitializerMemberValue ? (PsiArrayInitializerMemberValue) value : null;
        if (psiArrayInitializerMemberValue == null) {
            return null;
        }
        PsiAnnotationMemberValue[] initializers = psiArrayInitializerMemberValue.getInitializers();
        Intrinsics.checkNotNullExpressionValue(initializers, "getInitializers(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiAnnotationMemberValue psiAnnotationMemberValue : initializers) {
            if (psiAnnotationMemberValue instanceof KtLightPsiLiteral) {
                arrayList.add(psiAnnotationMemberValue);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((KtLightPsiLiteral) it2.next()).getValue());
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : arrayList3) {
            if (obj instanceof Pair) {
                arrayList4.add(obj);
            }
        }
        ArrayList<Pair> arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        for (Pair pair : arrayList5) {
            arrayList6.add(((ClassId) pair.getFirst()).asSingleFqName().asString() + '.' + ((Name) pair.getSecond()).getIdentifier());
        }
        return arrayList6;
    }

    static {
        ClassId classIdFromString = ClassId.fromString(JvmAnnotationNames.ELEMENT_TYPE_ENUM.asString());
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        Name nameIdentifier = Name.identifier("TYPE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Name nameIdentifier2 = Name.identifier("ANNOTATION_TYPE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        Name nameIdentifier3 = Name.identifier("FIELD");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        Name nameIdentifier4 = Name.identifier("LOCAL_VARIABLE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        Name nameIdentifier5 = Name.identifier("PARAMETER");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        Name nameIdentifier6 = Name.identifier("CONSTRUCTOR");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        Name nameIdentifier7 = Name.identifier("METHOD");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        Name nameIdentifier8 = Name.identifier("METHOD");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        Name nameIdentifier9 = Name.identifier("METHOD");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        Name nameIdentifier10 = Name.identifier("TYPE_PARAMETER");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier10, "identifier(...)");
        Name nameIdentifier11 = Name.identifier("TYPE_USE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier11, "identifier(...)");
        targetMapping = MapsKt.hashMapOf(TuplesKt.to("kotlin.annotation.AnnotationTarget.CLASS", new EnumValue(classIdFromString, nameIdentifier)), TuplesKt.to("kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS", new EnumValue(classIdFromString, nameIdentifier2)), TuplesKt.to("kotlin.annotation.AnnotationTarget.FIELD", new EnumValue(classIdFromString, nameIdentifier3)), TuplesKt.to("kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE", new EnumValue(classIdFromString, nameIdentifier4)), TuplesKt.to("kotlin.annotation.AnnotationTarget.VALUE_PARAMETER", new EnumValue(classIdFromString, nameIdentifier5)), TuplesKt.to("kotlin.annotation.AnnotationTarget.CONSTRUCTOR", new EnumValue(classIdFromString, nameIdentifier6)), TuplesKt.to("kotlin.annotation.AnnotationTarget.FUNCTION", new EnumValue(classIdFromString, nameIdentifier7)), TuplesKt.to("kotlin.annotation.AnnotationTarget.PROPERTY_GETTER", new EnumValue(classIdFromString, nameIdentifier8)), TuplesKt.to("kotlin.annotation.AnnotationTarget.PROPERTY_SETTER", new EnumValue(classIdFromString, nameIdentifier9)), TuplesKt.to("kotlin.annotation.AnnotationTarget.TYPE_PARAMETER", new EnumValue(classIdFromString, nameIdentifier10)), TuplesKt.to("kotlin.annotation.AnnotationTarget.TYPE", new EnumValue(classIdFromString, nameIdentifier11)));
        ClassId classIdFromString2 = ClassId.fromString(JvmAnnotationNames.RETENTION_POLICY_ENUM.asString());
        Intrinsics.checkNotNullExpressionValue(classIdFromString2, "fromString(...)");
        javaAnnotationRetentionPolicyId = classIdFromString2;
        Name nameIdentifier12 = Name.identifier("SOURCE");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier12, "identifier(...)");
        Name nameIdentifier13 = Name.identifier("CLASS");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier13, "identifier(...)");
        Name nameIdentifier14 = Name.identifier("RUNTIME");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier14, "identifier(...)");
        retentionMapping = MapsKt.hashMapOf(TuplesKt.to("kotlin.annotation.AnnotationRetention.SOURCE", new EnumValue(classIdFromString2, nameIdentifier12)), TuplesKt.to("kotlin.annotation.AnnotationRetention.BINARY", new EnumValue(classIdFromString2, nameIdentifier13)), TuplesKt.to("kotlin.annotation.AnnotationRetention.RUNTIME", new EnumValue(classIdFromString2, nameIdentifier14)));
    }

    public static final KtLightAbstractAnnotation tryConvertAsTarget(PsiAnnotation psiAnnotation) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        if (!Intrinsics.areEqual(StandardNames.FqNames.target.asString(), psiAnnotation.getAnnotationFqName())) {
            return null;
        }
        List<String> listExtractArrayAnnotationFqNames = extractArrayAnnotationFqNames(psiAnnotation, "allowedTargets");
        if (listExtractArrayAnnotationFqNames == null) {
            String strExtractAnnotationFqName = extractAnnotationFqName(psiAnnotation, "value");
            listExtractArrayAnnotationFqNames = strExtractAnnotationFqName != null ? CollectionsKt.listOf(strExtractAnnotationFqName) : null;
        }
        if (listExtractArrayAnnotationFqNames == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listExtractArrayAnnotationFqNames.iterator();
        while (it.hasNext()) {
            EnumValue enumValue = targetMapping.get((String) it.next());
            if (enumValue != null) {
                arrayList.add(enumValue);
            }
        }
        return asUltraLightSimpleAnnotation(psiAnnotation, CommonClassNames.JAVA_LANG_ANNOTATION_TARGET, CollectionsKt.listOf(TuplesKt.to("value", new ArrayValue(CollectionsKt.distinct(arrayList), new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt$tryConvertAsTarget$targetAttributes$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                SimpleType defaultType = module.getBuiltIns().getArray().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                return defaultType;
            }
        }))));
    }

    public static final KtUltraLightSimpleAnnotation createRetentionRuntimeAnnotation(PsiElement parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        EnumValue enumValue = retentionMapping.get("kotlin.annotation.AnnotationRetention.RUNTIME");
        Intrinsics.checkNotNull(enumValue);
        return new KtUltraLightSimpleAnnotation(CommonClassNames.JAVA_LANG_ANNOTATION_RETENTION, CollectionsKt.listOf(TuplesKt.to("value", enumValue)), parent, null, 8, null);
    }

    public static final KtLightAbstractAnnotation asUltraLightSimpleAnnotation(final PsiAnnotation psiAnnotation, String qualifier, List<? extends Pair<String, ? extends ConstantValue<?>>> argumentsList) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        Intrinsics.checkNotNullParameter(argumentsList, "argumentsList");
        PsiElement parent = psiAnnotation.getLightParent();
        Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
        return new KtUltraLightSimpleAnnotation(qualifier, argumentsList, parent, new Function0<PsiJavaCodeReferenceElement>() { // from class: org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt.asUltraLightSimpleAnnotation.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiJavaCodeReferenceElement invoke() {
                return psiAnnotation.getNameReferenceElement();
            }
        });
    }

    public static final KtLightAbstractAnnotation tryConvertAsRetention(PsiAnnotation psiAnnotation) {
        String strExtractAnnotationFqName;
        EnumValue enumValue;
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        if (!Intrinsics.areEqual(StandardNames.FqNames.retention.asString(), psiAnnotation.getAnnotationFqName()) || (strExtractAnnotationFqName = extractAnnotationFqName(psiAnnotation, "value")) == null || (enumValue = retentionMapping.get(strExtractAnnotationFqName)) == null) {
            return null;
        }
        return asUltraLightSimpleAnnotation(psiAnnotation, CommonClassNames.JAVA_LANG_ANNOTATION_RETENTION, CollectionsKt.listOf(TuplesKt.to("value", enumValue)));
    }

    public static final KtLightAbstractAnnotation tryConvertAsMustBeDocumented(PsiAnnotation psiAnnotation) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        FqName fqName = StandardNames.FqNames.mustBeDocumented;
        String strAsString = JvmAnnotationNames.DOCUMENTED_ANNOTATION.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return tryConvertAs(psiAnnotation, fqName, strAsString);
    }

    public static final KtLightAbstractAnnotation tryConvertAsRepeatable(final PsiAnnotation psiAnnotation, KtLightElement<? extends KtModifierListOwner, ? extends PsiModifierListOwner> owner) {
        ClassId classId;
        ClassId classIdCreateNestedClassId;
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Pair pair = null;
        if (!Intrinsics.areEqual(StandardNames.FqNames.repeatable.asString(), psiAnnotation.getAnnotationFqName())) {
            return null;
        }
        KtModifierListOwner ktModifierListOwner = (KtModifierListOwner) owner.getKotlinOrigin();
        if (ktModifierListOwner != null) {
            if (!(ktModifierListOwner instanceof KtClass)) {
                ktModifierListOwner = null;
            }
            KtClass ktClass = (KtClass) ktModifierListOwner;
            if (ktClass != null && (classId = ktClass.getClassId()) != null && (classIdCreateNestedClassId = classId.createNestedClassId(Name.identifier(JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME))) != null) {
                pair = TuplesKt.to("value", new KClassValue(classIdCreateNestedClassId, 0));
            }
        }
        List listListOfNotNull = CollectionsKt.listOfNotNull(pair);
        PsiElement parent = psiAnnotation.getLightParent();
        Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
        return new KtUltraLightSimpleAnnotation(CommonClassNames.JAVA_LANG_ANNOTATION_REPEATABLE, listListOfNotNull, parent, new Function0<PsiJavaCodeReferenceElement>() { // from class: org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt.tryConvertAsRepeatable.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiJavaCodeReferenceElement invoke() {
                KtCallElement kotlinOrigin;
                PsiAnnotation psiAnnotation2 = psiAnnotation;
                KtLightPsiJavaCodeReferenceElement ktLightPsiJavaCodeReferenceElement = null;
                if (!(psiAnnotation2 instanceof KtLightAnnotationForSourceEntry)) {
                    psiAnnotation2 = null;
                }
                KtLightAnnotationForSourceEntry ktLightAnnotationForSourceEntry = (KtLightAnnotationForSourceEntry) psiAnnotation2;
                if (ktLightAnnotationForSourceEntry != null && (kotlinOrigin = ktLightAnnotationForSourceEntry.getKotlinOrigin()) != null) {
                    if (!(kotlinOrigin instanceof KtAnnotationEntry)) {
                        kotlinOrigin = null;
                    }
                    KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) kotlinOrigin;
                    if (ktAnnotationEntry != null) {
                        ktLightPsiJavaCodeReferenceElement = new KtLightPsiJavaCodeReferenceElement(ktAnnotationEntry, new Function0<PsiReference>() { // from class: org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt$tryConvertAsRepeatable$1$1$1
                            @Override // kotlin.jvm.functions.Function0
                            public final PsiReference invoke() {
                                return null;
                            }
                        }, KtToJvmAnnotationsConverterKt.getJAVA_LANG_ANNOTATION_REPEATABLE_SHORT_NAME());
                    }
                }
                return ktLightPsiJavaCodeReferenceElement;
            }
        });
    }

    public static final String getJAVA_LANG_ANNOTATION_REPEATABLE_SHORT_NAME() {
        String strAsString = StandardNames.FqNames.repeatable.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    public static final KtLightAbstractAnnotation tryConvertAsRepeatableContainer(PsiAnnotation psiAnnotation) {
        Intrinsics.checkNotNullParameter(psiAnnotation, "<this>");
        return tryConvertAs(psiAnnotation, StandardNames.FqNames.repeatable, KOTLIN_JVM_INTERNAL_REPEATABLE_CONTAINER);
    }

    private static final KtLightAbstractAnnotation tryConvertAs(PsiAnnotation psiAnnotation, FqName fqName, String str) {
        if ((Intrinsics.areEqual(fqName.asString(), psiAnnotation.getAnnotationFqName()) ? psiAnnotation : null) != null) {
            return asUltraLightSimpleAnnotation(psiAnnotation, str, CollectionsKt.emptyList());
        }
        return null;
    }
}
