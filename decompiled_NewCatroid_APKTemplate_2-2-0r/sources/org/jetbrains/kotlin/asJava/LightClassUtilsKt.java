package org.jetbrains.kotlin.asJava;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.analysis.project.structure.KtSourceModule;
import org.jetbrains.kotlin.asJava.classes.KtFakeLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMember;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtLightParameter;
import org.jetbrains.kotlin.asJava.elements.LightParameter;
import org.jetbrains.kotlin.asJava.elements.PsiElementWithOrigin;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmModifier;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: lightClassUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u000205\u001a\u0010\u00106\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u000202\u001a\u0018\u00107\u001a\u00020\u00042\u0010\u00108\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020:09\u001a\u0014\u0010;\u001a\b\u0012\u0004\u0012\u0002020<2\u0006\u00103\u001a\u000202\u001a\u0010\u0010=\u001a\u00020\u00042\u0006\u0010>\u001a\u00020?H\u0002\u001a\u0012\u0010@\u001a\u00020\u00042\b\u0010A\u001a\u0004\u0018\u00010BH\u0002\u001a\u0016\u0010C\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u0010D\u001a\u00020E\u001a\u0018\u0010F\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u000205\u001a\n\u0010G\u001a\u00020\u0004*\u000205\u001a\n\u0010H\u001a\u00020I*\u00020I\u001a\f\u0010J\u001a\u0004\u0018\u00010K*\u00020L\u001a\f\u0010M\u001a\u0004\u0018\u00010\u0005*\u00020\u001f\u001a\u0016\u0010\"\u001a\u00020\u0004*\u00020\u00052\b\b\u0002\u0010N\u001a\u00020\u0004H\u0002\u001a\n\u0010O\u001a\u00020I*\u00020I\u001a\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010%*\u00020\u00052\b\b\u0002\u0010N\u001a\u00020\u0004\u001a\u000e\u0010P\u001a\u0004\u0018\u00010\u0005*\u00020QH\u0002\u001a\n\u0010R\u001a\u00020S*\u00020\u0016\u001a\f\u0010T\u001a\u0004\u0018\u00010U*\u00020V\u001a\f\u0010W\u001a\u0004\u0018\u00010K*\u00020\u0016\u001a\f\u0010W\u001a\u0004\u0018\u00010K*\u00020X\u001a\f\u0010Y\u001a\u0004\u0018\u00010Z*\u00020\u0016\u001a\u0010\u0010[\u001a\b\u0012\u0004\u0012\u00020\u001e0<*\u00020V\u001a\f\u0010\\\u001a\u0004\u0018\u00010\u0005*\u00020Q\u001a\u0010\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00050<*\u00020\u001f\u001a\f\u0010^\u001a\u0004\u0018\u00010\u0005*\u00020Q\u001a\u0010\u0010_\u001a\b\u0012\u0004\u0012\u00020`0%*\u00020Q\u001a\u0010\u0010a\u001a\b\u0012\u0004\u0012\u00020b0<*\u00020c\u001a\u0012\u0010d\u001a\b\u0012\u0004\u0012\u00020U0e*\u00020UH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0018\u0010\b\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007\"\u0015\u0010\n\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007\"\u0015\u0010\f\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007\"\u0015\u0010\u000e\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007\"\u0015\u0010\u0010\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007\"\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u0001*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0015\u0010\u0015\u001a\u00020\u0004*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\"\u0015\u0010\u0019\u001a\u00020\u0004*\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018\"\u0018\u0010\u001b\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0007\"\u0018\u0010\u001c\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0007\"\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b \u0010!\"\u0018\u0010\"\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0007\" \u0010$\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010%*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'\"\u001b\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010%*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010'\"\u0017\u0010*\u001a\u0004\u0018\u00010\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b+\u0010\u0014\"\u001d\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010%*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b-\u0010'\"\u0017\u0010.\u001a\u0004\u0018\u00010\u001f*\u00020\u001f8F¢\u0006\u0006\u001a\u0004\b/\u00100¨\u0006f"}, d2 = {"DEFAULT_IMPLS_CLASS_NAME", "Lorg/jetbrains/kotlin/name/Name;", "REPEATABLE_ANNOTATION_CONTAINER_NAME", "canBeGetter", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "getCanBeGetter", "(Lcom/intellij/psi/PsiMethod;)Z", "canBeSetter", "getCanBeSetter", "canHaveOverride", "getCanHaveOverride", "canHaveSyntheticAccessors", "getCanHaveSyntheticAccessors", "canHaveSyntheticGetter", "getCanHaveSyntheticGetter", "canHaveSyntheticSetter", "getCanHaveSyntheticSetter", "getterName", "getGetterName", "(Lcom/intellij/psi/PsiMethod;)Lorg/jetbrains/kotlin/name/Name;", "hasInterfaceDefaultImpls", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getHasInterfaceDefaultImpls", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)Z", "hasRepeatableAnnotationContainer", "getHasRepeatableAnnotationContainer", "isFinalProperty", "isTopLevelDeclaration", "namedUnwrappedElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNamedElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getNamedUnwrappedElement", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiNamedElement;", "probablyCanHaveSyntheticAccessors", "getProbablyCanHaveSyntheticAccessors", "setterNames", "", "getSetterNames", "(Lcom/intellij/psi/PsiMethod;)Ljava/util/Collection;", "syntheticAccessors", "getSyntheticAccessors", "syntheticGetter", "getSyntheticGetter", "syntheticSetters", "getSyntheticSetters", "unwrapped", "getUnwrapped", "(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;", "accessorNameByPropertyName", "", "name", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "demangleInternalName", "fastCheckIsNullabilityApplied", "lightElement", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierListOwner;", "getAccessorNamesCandidatesByPropertyName", "", "hasNonAbstractMembers", "ktInterface", "Lorg/jetbrains/kotlin/psi/KtClass;", "isNonAbstractMember", "member", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "mangleInternalName", "module", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "propertyNameByAccessor", "checkIsMangled", "defaultImplsChild", "Lorg/jetbrains/kotlin/name/FqName;", "findFacadeClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "Lorg/jetbrains/kotlin/psi/KtFile;", "getRepresentativeLightMethod", "withoutOverrideCheck", "repeatableAnnotationContainerChild", "toAnnotationLightMethod", "Lorg/jetbrains/kotlin/psi/KtParameter;", "toFakeLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "toLightAnnotation", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "Lorg/jetbrains/kotlin/psi/KtElement;", "toLightClass", "Lorg/jetbrains/kotlin/psi/KtScript;", "toLightClassWithBuiltinMapping", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "toLightElements", "toLightGetter", "toLightMethods", "toLightSetter", "toPsiParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "toPsiTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "withNestedAnnotations", "Lkotlin/sequences/Sequence;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightClassUtilsKt {
    private static final Name DEFAULT_IMPLS_CLASS_NAME;
    private static final Name REPEATABLE_ANNOTATION_CONTAINER_NAME;

    public static final KtLightClass toLightClass(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        KotlinAsJavaSupport.Companion companion = KotlinAsJavaSupport.INSTANCE;
        Project project = ktClassOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).getLightClass(ktClassOrObject);
    }

    public static final PsiClass toLightClassWithBuiltinMapping(KtClassOrObject ktClassOrObject) {
        FqName fqNameAsSingleFqName;
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        KtLightClass lightClass = toLightClass(ktClassOrObject);
        if (lightClass != null) {
            return lightClass;
        }
        FqName fqName = ktClassOrObject.mo7391getFqName();
        if (fqName == null) {
            return null;
        }
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = fqName.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (classIdMapKotlinToJava == null || (fqNameAsSingleFqName = classIdMapKotlinToJava.asSingleFqName()) == null) {
            return null;
        }
        SearchScope useScope = ktClassOrObject.getUseScope();
        GlobalSearchScope globalSearchScope = useScope instanceof GlobalSearchScope ? (GlobalSearchScope) useScope : null;
        if (globalSearchScope == null) {
            return null;
        }
        return JavaPsiFacade.getInstance(ktClassOrObject.getProject()).findClass(fqNameAsSingleFqName.asString(), globalSearchScope);
    }

    public static final KtFakeLightClass toFakeLightClass(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        KotlinAsJavaSupport.Companion companion = KotlinAsJavaSupport.INSTANCE;
        Project project = ktClassOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).getFakeLightClass(ktClassOrObject);
    }

    public static final KtLightClass findFacadeClass(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "<this>");
        KotlinAsJavaSupport.Companion companion = KotlinAsJavaSupport.INSTANCE;
        Project project = ktFile.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).getLightFacade(ktFile);
    }

    public static final KtLightClass toLightClass(KtScript ktScript) {
        Intrinsics.checkNotNullParameter(ktScript, "<this>");
        KotlinAsJavaSupport.Companion companion = KotlinAsJavaSupport.INSTANCE;
        Project project = ktScript.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).getLightClassForScript(ktScript);
    }

    public static final List<PsiNamedElement> toLightElements(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtClassOrObject) {
            return CollectionsKt.listOfNotNull(toLightClass((KtClassOrObject) ktElement));
        }
        if (ktElement instanceof KtNamedFunction ? true : ktElement instanceof KtConstructor) {
            return LightClassUtil.INSTANCE.getLightClassMethods((KtFunction) ktElement);
        }
        if (ktElement instanceof KtProperty) {
            return LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtProperty) ktElement).getAllDeclarations();
        }
        if (ktElement instanceof KtPropertyAccessor) {
            return CollectionsKt.listOfNotNull(LightClassUtil.INSTANCE.getLightClassAccessorMethod((KtPropertyAccessor) ktElement));
        }
        if (ktElement instanceof KtParameter) {
            ArrayList arrayList = new ArrayList();
            KtParameter ktParameter = (KtParameter) ktElement;
            ArrayList arrayList2 = arrayList;
            CollectionsKt.toCollection(toPsiParameters(ktParameter), arrayList2);
            CollectionsKt.toCollection(LightClassUtil.INSTANCE.getLightClassPropertyMethods(ktParameter), arrayList2);
            PsiMethod annotationLightMethod = toAnnotationLightMethod(ktParameter);
            if (annotationLightMethod != null) {
                arrayList.add(annotationLightMethod);
            }
            return arrayList;
        }
        if (ktElement instanceof KtTypeParameter) {
            return toPsiTypeParameters((KtTypeParameter) ktElement);
        }
        return ktElement instanceof KtFile ? CollectionsKt.listOfNotNull(findFacadeClass((KtFile) ktElement)) : CollectionsKt.emptyList();
    }

    public static final List<PsiMethod> toLightMethods(PsiElement psiElement) {
        PsiMethod[] constructors;
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtFunction) {
            return LightClassUtil.INSTANCE.getLightClassMethods((KtFunction) psiElement);
        }
        if (psiElement instanceof KtProperty) {
            return CollectionsKt.toList(LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtProperty) psiElement));
        }
        if (psiElement instanceof KtParameter) {
            return CollectionsKt.toList(LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtParameter) psiElement));
        }
        if (psiElement instanceof KtPropertyAccessor) {
            return LightClassUtil.INSTANCE.getLightClassAccessorMethods((KtPropertyAccessor) psiElement);
        }
        if (!(psiElement instanceof KtClass)) {
            return psiElement instanceof PsiMethod ? CollectionsKt.listOf(psiElement) : CollectionsKt.emptyList();
        }
        KtLightClass lightClass = toLightClass((KtClassOrObject) psiElement);
        return CollectionsKt.listOfNotNull((lightClass == null || (constructors = lightClass.getConstructors()) == null) ? null : (PsiMethod) ArraysKt.firstOrNull(constructors));
    }

    public static final PsiMethod getRepresentativeLightMethod(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtFunction) {
            return LightClassUtil.INSTANCE.getLightClassMethod((KtFunction) psiElement);
        }
        if (psiElement instanceof KtProperty) {
            return LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtProperty) psiElement).getGetter();
        }
        if (psiElement instanceof KtParameter) {
            return LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtParameter) psiElement).getGetter();
        }
        if (psiElement instanceof KtPropertyAccessor) {
            return LightClassUtil.INSTANCE.getLightClassAccessorMethod((KtPropertyAccessor) psiElement);
        }
        if (psiElement instanceof PsiMethod) {
            return (PsiMethod) psiElement;
        }
        return null;
    }

    public static final Collection<PsiParameter> toPsiParameters(KtParameter ktParameter) {
        List<PsiMethod> lightClassAccessorMethods;
        Intrinsics.checkNotNullParameter(ktParameter, "<this>");
        KtParameterList ktParameterList = (KtParameterList) PsiTreeUtil.getParentOfType(ktParameter, KtParameterList.class, false);
        if (ktParameterList == null) {
            return CollectionsKt.emptyList();
        }
        int iIndexOf = ktParameterList.getParameters().indexOf(ktParameter);
        if (iIndexOf < 0) {
            return CollectionsKt.emptyList();
        }
        PsiElement lightParent = ktParameterList.getLightParent();
        if ((lightParent instanceof KtDeclaration) && KtPsiUtilKt.isExtensionDeclaration(lightParent)) {
            iIndexOf++;
        }
        if (lightParent instanceof KtFunction) {
            LightClassUtil lightClassUtil = LightClassUtil.INSTANCE;
            Intrinsics.checkNotNull(lightParent);
            lightClassAccessorMethods = lightClassUtil.getLightClassMethods((KtFunction) lightParent);
        } else if (lightParent instanceof KtPropertyAccessor) {
            LightClassUtil lightClassUtil2 = LightClassUtil.INSTANCE;
            Intrinsics.checkNotNull(lightParent);
            lightClassAccessorMethods = lightClassUtil2.getLightClassAccessorMethods((KtPropertyAccessor) lightParent);
        } else {
            lightClassAccessorMethods = null;
        }
        if (lightClassAccessorMethods == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = lightClassAccessorMethods.iterator();
        while (it.hasNext()) {
            PsiParameter[] parameters = ((PsiMethod) it.next()).getParameterList().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            PsiParameter psiParameter = (PsiParameter) ArraysKt.getOrNull(parameters, iIndexOf);
            if (psiParameter != null) {
                arrayList.add(psiParameter);
            }
        }
        return arrayList;
    }

    private static final PsiMethod toAnnotationLightMethod(KtParameter ktParameter) {
        KtDeclarationWithBody ownerFunction = ktParameter.getOwnerFunction();
        KtPrimaryConstructor ktPrimaryConstructor = ownerFunction instanceof KtPrimaryConstructor ? (KtPrimaryConstructor) ownerFunction : null;
        if (ktPrimaryConstructor != null && ktPrimaryConstructor.getContainingClassOrObject().isAnnotation()) {
            return LightClassUtil.INSTANCE.getLightClassMethod(ktParameter);
        }
        return null;
    }

    public static final PsiMethod toLightGetter(KtParameter ktParameter) {
        Intrinsics.checkNotNullParameter(ktParameter, "<this>");
        return LightClassUtil.INSTANCE.getLightClassPropertyMethods(ktParameter).getGetter();
    }

    public static final PsiMethod toLightSetter(KtParameter ktParameter) {
        Intrinsics.checkNotNullParameter(ktParameter, "<this>");
        return LightClassUtil.INSTANCE.getLightClassPropertyMethods(ktParameter).getSetter();
    }

    public static final List<PsiTypeParameter> toPsiTypeParameters(KtTypeParameter ktTypeParameter) {
        PsiTypeParameter[] typeParameters;
        Intrinsics.checkNotNullParameter(ktTypeParameter, "<this>");
        KtTypeParameterList ktTypeParameterList = (KtTypeParameterList) PsiTreeUtil.getParentOfType(ktTypeParameter, KtTypeParameterList.class, false);
        if (ktTypeParameterList == null) {
            return CollectionsKt.emptyList();
        }
        int iIndexOf = ktTypeParameterList.getParameters().indexOf(ktTypeParameter);
        KtDeclaration ktDeclaration = (KtDeclaration) PsiTreeUtil.getParentOfType(ktTypeParameterList, KtDeclaration.class, false);
        if (ktDeclaration == null) {
            return CollectionsKt.emptyList();
        }
        List<PsiNamedElement> lightElements = toLightElements(ktDeclaration);
        ArrayList arrayList = new ArrayList();
        for (PsiNamedElement psiNamedElement : lightElements) {
            PsiTypeParameter psiTypeParameter = null;
            PsiTypeParameterListOwner psiTypeParameterListOwner = psiNamedElement instanceof PsiTypeParameterListOwner ? (PsiTypeParameterListOwner) psiNamedElement : null;
            if (psiTypeParameterListOwner != null && (typeParameters = psiTypeParameterListOwner.getTypeParameters()) != null) {
                Intrinsics.checkNotNull(typeParameters);
                psiTypeParameter = (PsiTypeParameter) ArraysKt.getOrNull(typeParameters, iIndexOf);
            }
            if (psiTypeParameter != null) {
                arrayList.add(psiTypeParameter);
            }
        }
        return arrayList;
    }

    public static final PsiElement getUnwrapped(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof PsiElementWithOrigin) {
            return ((PsiElementWithOrigin) psiElement).getOrigin();
        }
        if (psiElement instanceof KtLightElement) {
            return ((KtLightElement) psiElement).getKotlinOrigin();
        }
        return psiElement instanceof KtLightElementBase ? ((KtLightElementBase) psiElement).getKotlinOrigin() : psiElement;
    }

    public static final PsiNamedElement getNamedUnwrappedElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        PsiElement unwrapped = getUnwrapped(psiElement);
        if (unwrapped != null) {
            return (PsiNamedElement) PsiTreeUtil.getParentOfType(unwrapped, PsiNamedElement.class, false);
        }
        return null;
    }

    public static final boolean getHasInterfaceDefaultImpls(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        if (ktClassOrObject instanceof KtClass) {
            KtClass ktClass = (KtClass) ktClassOrObject;
            if (ktClass.isInterface() && hasNonAbstractMembers(ktClass)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean getHasRepeatableAnnotationContainer(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        if (!(ktClassOrObject instanceof KtClass) || !ktClassOrObject.isAnnotation()) {
            return false;
        }
        boolean z = false;
        for (KtAnnotationEntry ktAnnotationEntry : ((KtClass) ktClassOrObject).getAnnotationEntries()) {
            Name shortName = ktAnnotationEntry.getShortName();
            String strAsString = shortName != null ? shortName.asString() : null;
            if (strAsString != null) {
                int iHashCode = strAsString.hashCode();
                if (iHashCode == -1625845546) {
                    if (strAsString.equals("JvmRepeatable")) {
                        return false;
                    }
                } else if (iHashCode == -1141548651 && strAsString.equals("Repeatable")) {
                    if (ktAnnotationEntry.getValueArgumentList() != null) {
                        return false;
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    private static final boolean hasNonAbstractMembers(KtClass ktClass) {
        List<KtDeclaration> declarations = ktClass.getDeclarations();
        if ((declarations instanceof Collection) && declarations.isEmpty()) {
            return false;
        }
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            if (isNonAbstractMember((KtDeclaration) it.next())) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isNonAbstractMember(KtDeclaration ktDeclaration) {
        if (!(ktDeclaration instanceof KtNamedFunction) || !((KtNamedFunction) ktDeclaration).hasBody()) {
            if (!(ktDeclaration instanceof KtProperty)) {
                return false;
            }
            KtProperty ktProperty = (KtProperty) ktDeclaration;
            if (!ktProperty.hasDelegateExpressionOrInitializer()) {
                KtPropertyAccessor getter = ktProperty.getGetter();
                if (!(getter != null ? getter.hasBody() : false)) {
                    KtPropertyAccessor setter = ktProperty.getSetter();
                    if (!(setter != null ? setter.hasBody() : false)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static {
        Name nameIdentifier = Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DEFAULT_IMPLS_CLASS_NAME = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        REPEATABLE_ANNOTATION_CONTAINER_NAME = nameIdentifier2;
    }

    public static final FqName defaultImplsChild(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        FqName fqNameChild = fqName.child(DEFAULT_IMPLS_CLASS_NAME);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public static final FqName repeatableAnnotationContainerChild(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        FqName fqNameChild = fqName.child(REPEATABLE_ANNOTATION_CONTAINER_NAME);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public static final PsiAnnotation toLightAnnotation(KtElement ktElement) {
        PsiModifierList psiModifierListMo6095getModifierList;
        PsiAnnotation[] annotations;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        KtModifierList ktModifierList = (KtModifierList) PsiTreeUtil.getParentOfType(ktElement, KtModifierList.class, true);
        PsiElement parent = ktModifierList != null ? ktModifierList.getLightParent() : null;
        KtDeclaration ktDeclaration = parent instanceof KtDeclaration ? (KtDeclaration) parent : null;
        if (ktDeclaration == null) {
            return null;
        }
        for (PsiNamedElement psiNamedElement : toLightElements(ktDeclaration)) {
            if ((psiNamedElement instanceof PsiModifierListOwner) && (psiModifierListMo6095getModifierList = ((PsiModifierListOwner) psiNamedElement).mo6095getModifierList()) != null && (annotations = psiModifierListMo6095getModifierList.getAnnotations()) != null) {
                for (PsiAnnotation psiAnnotation : annotations) {
                    Intrinsics.checkNotNull(psiAnnotation);
                    for (PsiAnnotation psiAnnotation2 : withNestedAnnotations(psiAnnotation)) {
                        if ((psiAnnotation2 instanceof KtLightElement) && Intrinsics.areEqual(((KtLightElement) psiAnnotation2).getKotlinOrigin(), ktElement)) {
                            return psiAnnotation2;
                        }
                    }
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<PsiAnnotation> withNestedAnnotations$handleValue(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        if (!(psiAnnotationMemberValue instanceof PsiArrayInitializerMemberValue)) {
            return psiAnnotationMemberValue instanceof PsiAnnotation ? withNestedAnnotations((PsiAnnotation) psiAnnotationMemberValue) : SequencesKt.emptySequence();
        }
        PsiAnnotationMemberValue[] initializers = ((PsiArrayInitializerMemberValue) psiAnnotationMemberValue).getInitializers();
        Intrinsics.checkNotNullExpressionValue(initializers, "getInitializers(...)");
        return SequencesKt.flatMap(ArraysKt.asSequence(initializers), new Function1<PsiAnnotationMemberValue, Sequence<? extends PsiAnnotation>>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtilsKt$withNestedAnnotations$handleValue$1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<PsiAnnotation> invoke(PsiAnnotationMemberValue psiAnnotationMemberValue2) {
                return LightClassUtilsKt.withNestedAnnotations$handleValue(psiAnnotationMemberValue2);
            }
        });
    }

    private static final Sequence<PsiAnnotation> withNestedAnnotations(PsiAnnotation psiAnnotation) {
        Sequence sequenceSequenceOf = SequencesKt.sequenceOf(psiAnnotation);
        PsiNameValuePair[] attributes = psiAnnotation.getParameterList().getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "getAttributes(...)");
        return SequencesKt.plus(sequenceSequenceOf, SequencesKt.flatMap(ArraysKt.asSequence(attributes), new Function1<PsiNameValuePair, Sequence<? extends PsiAnnotation>>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtilsKt.withNestedAnnotations.1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<PsiAnnotation> invoke(PsiNameValuePair psiNameValuePair) {
                return LightClassUtilsKt.withNestedAnnotations$handleValue(psiNameValuePair.getValue());
            }
        }));
    }

    public static final String demangleInternalName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) name2, '$', 0, false, 6, (Object) null);
        if (iIndexOf$default < 0) {
            return null;
        }
        String strSubstring = name2.substring(0, iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public static final String mangleInternalName(String name2, KtSourceModule module) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(module, "module");
        String stableModuleName = module.getStableModuleName();
        if (stableModuleName == null) {
            stableModuleName = module.getModuleName();
        }
        return name2 + '$' + NameUtils.sanitizeAsJavaIdentifier(StringsKt.removeSurrounding(stableModuleName, (CharSequence) "<", (CharSequence) ">"));
    }

    public static final boolean checkIsMangled(KtLightMethod ktLightMethod) {
        Intrinsics.checkNotNullParameter(ktLightMethod, "<this>");
        String name2 = ktLightMethod.getMyName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String strDemangleInternalName = demangleInternalName(name2);
        if (strDemangleInternalName == null) {
            return false;
        }
        String strPropertyNameByAccessor = propertyNameByAccessor(strDemangleInternalName, ktLightMethod);
        if (strPropertyNameByAccessor != null) {
            strDemangleInternalName = strPropertyNameByAccessor;
        }
        KtDeclaration ktDeclaration = (KtDeclaration) ktLightMethod.getKotlinOrigin();
        return Intrinsics.areEqual(strDemangleInternalName, ktDeclaration != null ? ktDeclaration.getMyName() : null);
    }

    public static final String propertyNameByAccessor(String name2, KtLightMethod accessor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        KtDeclaration ktDeclaration = (KtDeclaration) accessor.getKotlinOrigin();
        if (ktDeclaration == null) {
            return null;
        }
        if (!(ktDeclaration instanceof KtProperty) && !(ktDeclaration instanceof KtParameter)) {
            return null;
        }
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(name2);
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
        String name3 = ktDeclaration.getMyName();
        if (name3 == null) {
            name3 = "";
        }
        if (JvmAbi.isGetterName(name2)) {
            nameGuessByFirstCharacter = PropertiesConventionUtilKt.propertyNameByGetMethodName(nameGuessByFirstCharacter);
        } else if (JvmAbi.isSetterName(name2)) {
            nameGuessByFirstCharacter = PropertiesConventionUtilKt.propertyNameBySetMethodName(nameGuessByFirstCharacter, StringsKt.startsWith$default(name3, LanguageCodeUtil.IS, false, 2, (Object) null));
        }
        if (nameGuessByFirstCharacter != null) {
            return nameGuessByFirstCharacter.asString();
        }
        return null;
    }

    public static final String accessorNameByPropertyName(String name2, KtLightMethod accessor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        String name3 = accessor.getMyName();
        Intrinsics.checkNotNull(name3);
        if (JvmAbi.isGetterName(name3)) {
            return JvmAbi.getterName(name2);
        }
        if (JvmAbi.isSetterName(name3)) {
            return JvmAbi.setterName(name2);
        }
        return null;
    }

    public static final List<String> getAccessorNamesCandidatesByPropertyName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.listOf((Object[]) new String[]{JvmAbi.setterName(name2), JvmAbi.getterName(name2)});
    }

    public static final boolean fastCheckIsNullabilityApplied(KtLightElement<?, ? extends PsiModifierListOwner> lightElement) {
        Intrinsics.checkNotNullParameter(lightElement, "lightElement");
        boolean z = false;
        if (!(lightElement instanceof KtLightMember) && !(lightElement instanceof LightParameter)) {
            return false;
        }
        KtElement kotlinOrigin = lightElement.getKotlinOrigin();
        if (kotlinOrigin == null) {
            return true;
        }
        if ((kotlinOrigin instanceof KtClass) && ((KtClass) kotlinOrigin).isData()) {
            return true;
        }
        if ((lightElement instanceof KtLightField) && (kotlinOrigin instanceof KtProperty) && ((KtProperty) kotlinOrigin).hasModifier(KtTokens.LATEINIT_KEYWORD)) {
            return false;
        }
        if (lightElement instanceof KtLightMethod) {
            KtModifierListOwner ktModifierListOwner = kotlinOrigin instanceof KtModifierListOwner ? (KtModifierListOwner) kotlinOrigin : null;
            if (ktModifierListOwner != null && KtPsiUtilKt.isPrivate(ktModifierListOwner)) {
                return false;
            }
        }
        if (kotlinOrigin instanceof KtParameter) {
            KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject((KtDeclaration) kotlinOrigin);
            if (containingClassOrObject != null && containingClassOrObject.isAnnotation()) {
                return false;
            }
            KtClass ktClass = containingClassOrObject instanceof KtClass ? (KtClass) containingClassOrObject : null;
            if (ktClass != null && ktClass.isEnum() && (((KtParameter) kotlinOrigin).getLightParent().getLightParent() instanceof KtPrimaryConstructor)) {
                return false;
            }
            PsiElement lightParent = ((KtParameter) kotlinOrigin).getLightParent().getLightParent();
            if (lightParent instanceof KtConstructor) {
                if ((lightElement instanceof KtLightParameter) && KtPsiUtilKt.isPrivate((KtModifierListOwner) lightParent)) {
                    return false;
                }
            } else {
                if (lightParent instanceof KtNamedFunction) {
                    return !KtPsiUtilKt.isPrivate((KtModifierListOwner) lightParent);
                }
                if (lightParent instanceof KtPropertyAccessor) {
                    PsiElement parent = ((KtPropertyAccessor) lightParent).getLightParent();
                    KtProperty ktProperty = parent instanceof KtProperty ? (KtProperty) parent : null;
                    if (ktProperty != null && KtPsiUtilKt.isPrivate(ktProperty)) {
                        z = true;
                    }
                    return !z;
                }
            }
        }
        return true;
    }

    private static final boolean getCanBeGetter(PsiMethod psiMethod) {
        PsiTypeElement returnTypeElement;
        String name2 = psiMethod.getMyName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (JvmAbi.isGetterName(name2)) {
            JvmParameter[] parameters = psiMethod.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            if (parameters.length == 0 && ((returnTypeElement = psiMethod.getReturnTypeElement()) == null || !returnTypeElement.textMatches(PsiKeyword.VOID))) {
                return true;
            }
        }
        return false;
    }

    private static final boolean getCanBeSetter(PsiMethod psiMethod) {
        PsiTypeElement returnTypeElement;
        String name2 = psiMethod.getMyName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return JvmAbi.isSetterName(name2) && psiMethod.getParameters().length == 1 && ((returnTypeElement = psiMethod.getReturnTypeElement()) == null || returnTypeElement.textMatches(PsiKeyword.VOID));
    }

    private static final boolean getProbablyCanHaveSyntheticAccessors(PsiMethod psiMethod) {
        return probablyCanHaveSyntheticAccessors$default(psiMethod, false, 1, null);
    }

    static /* synthetic */ boolean probablyCanHaveSyntheticAccessors$default(PsiMethod psiMethod, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return probablyCanHaveSyntheticAccessors(psiMethod, z);
    }

    private static final boolean probablyCanHaveSyntheticAccessors(PsiMethod psiMethod, boolean z) {
        return ((!z && !getCanHaveOverride(psiMethod)) || psiMethod.hasTypeParameters() || isFinalProperty(psiMethod)) ? false : true;
    }

    private static final Name getGetterName(PsiMethod psiMethod) {
        Name nameIdentifier = Name.identifier(psiMethod.getMyName());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return PropertiesConventionUtilKt.propertyNameByGetMethodName(nameIdentifier);
    }

    private static final Collection<Name> getSetterNames(PsiMethod psiMethod) {
        Name nameIdentifier = Name.identifier(psiMethod.getMyName());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        List<Name> listPropertyNamesBySetMethodName = PropertiesConventionUtilKt.propertyNamesBySetMethodName(nameIdentifier);
        if (listPropertyNamesBySetMethodName.isEmpty()) {
            listPropertyNamesBySetMethodName = null;
        }
        return listPropertyNamesBySetMethodName;
    }

    private static final boolean isFinalProperty(PsiMethod psiMethod) {
        PsiElement unwrapped = getUnwrapped(psiMethod);
        KtProperty ktProperty = unwrapped instanceof KtProperty ? (KtProperty) unwrapped : null;
        if (ktProperty == null || ktProperty.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
            return false;
        }
        KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(ktProperty);
        if (containingClassOrObject == null) {
            return true;
        }
        return containingClassOrObject instanceof KtObjectDeclaration;
    }

    private static final boolean isTopLevelDeclaration(PsiMethod psiMethod) {
        PsiElement unwrapped = getUnwrapped(psiMethod);
        return unwrapped != null && KtPsiUtilKt.isTopLevelKtOrJavaMember(unwrapped);
    }

    public static final Collection<Name> getSyntheticAccessors(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        return syntheticAccessors$default(psiMethod, false, 1, null);
    }

    public static /* synthetic */ Collection syntheticAccessors$default(PsiMethod psiMethod, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return syntheticAccessors(psiMethod, z);
    }

    public static final Collection<Name> syntheticAccessors(PsiMethod psiMethod, boolean z) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        if (!probablyCanHaveSyntheticAccessors(psiMethod, z)) {
            return CollectionsKt.emptyList();
        }
        if (getCanBeGetter(psiMethod)) {
            return CollectionsKt.listOfNotNull(getGetterName(psiMethod));
        }
        if (!getCanBeSetter(psiMethod)) {
            return CollectionsKt.emptyList();
        }
        Collection<Name> setterNames = getSetterNames(psiMethod);
        return setterNames == null ? CollectionsKt.emptyList() : setterNames;
    }

    public static final boolean getCanHaveSyntheticAccessors(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        return getProbablyCanHaveSyntheticAccessors(psiMethod) && (getCanBeGetter(psiMethod) || getCanBeSetter(psiMethod));
    }

    public static final boolean getCanHaveSyntheticGetter(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        return getProbablyCanHaveSyntheticAccessors(psiMethod) && getCanBeGetter(psiMethod);
    }

    public static final boolean getCanHaveSyntheticSetter(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        return getProbablyCanHaveSyntheticAccessors(psiMethod) && getCanBeSetter(psiMethod);
    }

    public static final Name getSyntheticGetter(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        if (getCanHaveSyntheticGetter(psiMethod)) {
            return getGetterName(psiMethod);
        }
        return null;
    }

    public static final Collection<Name> getSyntheticSetters(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        if (getCanHaveSyntheticSetter(psiMethod)) {
            return getSetterNames(psiMethod);
        }
        return null;
    }

    public static final boolean getCanHaveOverride(PsiMethod psiMethod) {
        Intrinsics.checkNotNullParameter(psiMethod, "<this>");
        return (psiMethod.hasModifier(JvmModifier.STATIC) || psiMethod.isConstructor() || isTopLevelDeclaration(psiMethod)) ? false : true;
    }
}
