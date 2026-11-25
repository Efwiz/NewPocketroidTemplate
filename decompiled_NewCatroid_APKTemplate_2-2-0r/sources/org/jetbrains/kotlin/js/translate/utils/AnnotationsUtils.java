package org.jetbrains.kotlin.js.translate.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptPackageFragment;

/* JADX INFO: loaded from: classes5.dex */
public final class AnnotationsUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final FqName JS_NAME = JsStandardClassIds.Annotations.JsName.asSingleFqName();
    private static final FqName JS_EXPORT = JsStandardClassIds.Annotations.JsExport.asSingleFqName();
    private static final FqName JS_EXPORT_IGNORE = JsStandardClassIds.Annotations.JsExportIgnore.asSingleFqName();
    private static final FqName JS_MODULE_ANNOTATION = JsStandardClassIds.Annotations.JsModule.asSingleFqName();
    private static final FqName JS_NON_MODULE_ANNOTATION = JsStandardClassIds.Annotations.JsNonModule.asSingleFqName();
    private static final FqName JS_QUALIFIER_ANNOTATION = JsStandardClassIds.Annotations.JsQualifier.asSingleFqName();
    private static final FqName JS_EXTERNAL_INHERITORS_ONLY = JsStandardClassIds.Annotations.JsExternalInheritorsOnly.asSingleFqName();
    private static final FqName JS_EXTERNAL_ARGUMENT = JsStandardClassIds.Annotations.JsExternalArgument.asSingleFqName();

    /* JADX WARN: Code duplicated, block: B:33:0x0053  */
    /* JADX WARN: Code duplicated, block: B:34:0x0058  */
    /* JADX WARN: Code duplicated, block: B:35:0x005d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 41:
            case 42:
            case 43:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 41:
            case 42:
            case 43:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "annotation";
        } else if (i == 2) {
            objArr[0] = "declarationDescriptor";
        } else if (i == 3) {
            objArr[0] = "annotation";
        } else if (i == 4) {
            objArr[0] = "declarationDescriptor";
        } else if (i == 5) {
            objArr[0] = "annotation";
        } else if (i == 7) {
            objArr[0] = "bindingContext";
        } else if (i == 9) {
            objArr[0] = "annotation";
        } else if (i == 11) {
            objArr[0] = "bindingContext";
        } else if (i != 21) {
            switch (i) {
                case 23:
                    objArr[0] = "fqName";
                    break;
                case 24:
                    objArr[0] = "property";
                    break;
                case 25:
                case 27:
                case 29:
                case 31:
                case 33:
                case 35:
                case 36:
                case 37:
                    objArr[0] = "declaration";
                    break;
                case 26:
                case 28:
                case 30:
                case 34:
                case 39:
                    objArr[0] = "bindingContext";
                    break;
                case 32:
                    objArr[0] = "annotationFqName";
                    break;
                case 38:
                    objArr[0] = "annotation";
                    break;
                default:
                    switch (i) {
                        case 41:
                        case 42:
                        case 43:
                            objArr[0] = "org/jetbrains/kotlin/js/translate/utils/AnnotationsUtils";
                            break;
                        default:
                            objArr[0] = "descriptor";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "annotation";
        }
        switch (i) {
            case 41:
            case 42:
            case 43:
                objArr[1] = "getContainingFileAnnotations";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/js/translate/utils/AnnotationsUtils";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "getAnnotationStringParameter";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "getNameForAnnotatedObject";
                break;
            case 8:
            case 9:
                objArr[2] = "getAnnotationByName";
                break;
            case 10:
            case 11:
                objArr[2] = "isExportedObject";
                break;
            case 12:
                objArr[2] = "isNativeObject";
                break;
            case 13:
                objArr[2] = "isNativeInterface";
                break;
            case 14:
                objArr[2] = "isEffectivelyExternalMember";
                break;
            case 15:
                objArr[2] = "isLibraryObject";
                break;
            case 16:
                objArr[2] = "getJsName";
                break;
            case 17:
                objArr[2] = "getJsNameAnnotation";
                break;
            case 18:
                objArr[2] = "getJsExportAnnotation";
                break;
            case 19:
                objArr[2] = "isPredefinedObject";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
                objArr[2] = "hasAnnotationOrInsideAnnotatedClass";
                break;
            case 24:
                objArr[2] = "hasJsNameInAccessors";
                break;
            case 25:
                objArr[2] = "getModuleName";
                break;
            case 26:
            case 27:
                objArr[2] = "getFileModuleName";
                break;
            case 28:
            case 29:
                objArr[2] = "getFileQualifier";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "getSingleStringAnnotationArgument";
                break;
            case 33:
                objArr[2] = "isNonModule";
                break;
            case 34:
            case 35:
                objArr[2] = "isFromNonModuleFile";
                break;
            case 36:
                objArr[2] = "isJsExternalInheritorsOnly";
                break;
            case 37:
                objArr[2] = "isJsExternalArgument";
                break;
            case 38:
                objArr[2] = "extractSingleStringArgument";
                break;
            case 39:
            case 40:
                objArr[2] = "getContainingFileAnnotations";
                break;
            case 41:
            case 42:
            case 43:
                break;
            default:
                objArr[2] = "hasAnnotation";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 41:
            case 42:
            case 43:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private AnnotationsUtils() {
    }

    public static boolean hasAnnotation(DeclarationDescriptor declarationDescriptor, PredefinedAnnotation predefinedAnnotation) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (predefinedAnnotation == null) {
            $$$reportNull$$$0(1);
        }
        return getAnnotationByName(declarationDescriptor, predefinedAnnotation) != null;
    }

    private static String getAnnotationStringParameter(DeclarationDescriptor declarationDescriptor, PredefinedAnnotation predefinedAnnotation) {
        ConstantValue<?> next;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (predefinedAnnotation == null) {
            $$$reportNull$$$0(3);
        }
        AnnotationDescriptor annotationByName = getAnnotationByName(declarationDescriptor, predefinedAnnotation);
        if (annotationByName.getAllValueArguments().isEmpty() || (next = annotationByName.getAllValueArguments().values().iterator().next()) == null) {
            return null;
        }
        return (String) next.getValue();
    }

    public static String getNameForAnnotatedObject(DeclarationDescriptor declarationDescriptor, PredefinedAnnotation predefinedAnnotation) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (predefinedAnnotation == null) {
            $$$reportNull$$$0(5);
        }
        if (hasAnnotation(declarationDescriptor, predefinedAnnotation)) {
            return getAnnotationStringParameter(declarationDescriptor, predefinedAnnotation);
        }
        return null;
    }

    public static String getNameForAnnotatedObject(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(7);
        }
        String jsName = getJsName(declarationDescriptor);
        for (PredefinedAnnotation predefinedAnnotation : PredefinedAnnotation.INSTANCE.getWITH_CUSTOM_NAME()) {
            if (hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, predefinedAnnotation)) {
                String nameForAnnotatedObject = getNameForAnnotatedObject(declarationDescriptor, predefinedAnnotation);
                if (nameForAnnotatedObject != null) {
                    jsName = nameForAnnotatedObject;
                }
                return jsName != null ? jsName : declarationDescriptor.getShortName().asString();
            }
        }
        return (jsName == null && (isEffectivelyExternalMember(declarationDescriptor) || isExportedObject(declarationDescriptor, bindingContext))) ? declarationDescriptor.getShortName().asString() : jsName;
    }

    private static AnnotationDescriptor getAnnotationByName(DeclarationDescriptor declarationDescriptor, PredefinedAnnotation predefinedAnnotation) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (predefinedAnnotation == null) {
            $$$reportNull$$$0(9);
        }
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(predefinedAnnotation.getFqName());
    }

    public static boolean isExportedObject(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext) {
        DescriptorVisibility visibility;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(11);
        }
        if ((declarationDescriptor instanceof MemberDescriptor) && (visibility = ((MemberDescriptor) declarationDescriptor).getVisibility()) != DescriptorVisibilities.PUBLIC && visibility != DescriptorVisibilities.PROTECTED) {
            return false;
        }
        FqName fqName = JS_EXPORT_IGNORE;
        if (hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, fqName)) {
            return false;
        }
        if ((declarationDescriptor instanceof PropertyAccessorDescriptor) && ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty().getAnnotations().hasAnnotation(fqName)) {
            return false;
        }
        return hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, JS_EXPORT) || CollectionsKt.any(getContainingFileAnnotations(bindingContext, declarationDescriptor), new Function1() { // from class: org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(AnnotationsUtils.JS_EXPORT.equals(((AnnotationDescriptor) obj).getFqName()));
            }
        });
    }

    public static boolean isNativeObject(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, PredefinedAnnotation.NATIVE) || isEffectivelyExternalMember(declarationDescriptor)) {
            return true;
        }
        if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
            return hasAnnotationOrInsideAnnotatedClass(((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty(), PredefinedAnnotation.NATIVE);
        }
        return false;
    }

    public static boolean isNativeInterface(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        return isNativeObject(declarationDescriptor) && DescriptorUtils.isInterface(declarationDescriptor);
    }

    private static boolean isEffectivelyExternalMember(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        return (declarationDescriptor instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) declarationDescriptor);
    }

    public static boolean isLibraryObject(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        return hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, PredefinedAnnotation.LIBRARY);
    }

    public static String getJsName(DeclarationDescriptor declarationDescriptor) {
        ConstantValue<?> next;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        AnnotationDescriptor jsNameAnnotation = getJsNameAnnotation(declarationDescriptor);
        if (jsNameAnnotation == null || jsNameAnnotation.getAllValueArguments().isEmpty() || (next = jsNameAnnotation.getAllValueArguments().values().iterator().next()) == null) {
            return null;
        }
        Object value = next.getValue();
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public static AnnotationDescriptor getJsNameAnnotation(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(JS_NAME);
    }

    public static AnnotationDescriptor getJsExportAnnotation(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(18);
        }
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(JS_EXPORT);
    }

    public static boolean isPredefinedObject(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(19);
        }
        if (((declarationDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) declarationDescriptor).get$isExpect()) || isEffectivelyExternalMember(declarationDescriptor)) {
            return true;
        }
        for (PredefinedAnnotation predefinedAnnotation : PredefinedAnnotation.values()) {
            if (hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, predefinedAnnotation)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAnnotationOrInsideAnnotatedClass(DeclarationDescriptor declarationDescriptor, PredefinedAnnotation predefinedAnnotation) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (predefinedAnnotation == null) {
            $$$reportNull$$$0(21);
        }
        return hasAnnotationOrInsideAnnotatedClass(declarationDescriptor, predefinedAnnotation.getFqName());
    }

    private static boolean hasAnnotationOrInsideAnnotatedClass(DeclarationDescriptor declarationDescriptor, FqName fqName) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        if (fqName == null) {
            $$$reportNull$$$0(23);
        }
        if (declarationDescriptor.getAnnotations().hasAnnotation(fqName)) {
            return true;
        }
        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(declarationDescriptor);
        return containingClass != null && hasAnnotationOrInsideAnnotatedClass(containingClass, fqName);
    }

    public static boolean hasJsNameInAccessors(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        Iterator<PropertyAccessorDescriptor> it = propertyDescriptor.getAccessors().iterator();
        while (it.hasNext()) {
            if (getJsName(it.next()) != null) {
                return true;
            }
        }
        return false;
    }

    public static String getModuleName(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(JS_MODULE_ANNOTATION);
        if (annotationDescriptorMo6752findAnnotation != null) {
            return extractSingleStringArgument(annotationDescriptorMo6752findAnnotation);
        }
        return null;
    }

    public static String getFileModuleName(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(26);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return getSingleStringAnnotationArgument(bindingContext, declarationDescriptor, JS_MODULE_ANNOTATION);
    }

    public static String getFileQualifier(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(28);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        return getSingleStringAnnotationArgument(bindingContext, declarationDescriptor, JS_QUALIFIER_ANNOTATION);
    }

    private static String getSingleStringAnnotationArgument(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor, FqName fqName) {
        if (bindingContext == null) {
            $$$reportNull$$$0(30);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        if (fqName == null) {
            $$$reportNull$$$0(32);
        }
        for (AnnotationDescriptor annotationDescriptor : getContainingFileAnnotations(bindingContext, declarationDescriptor)) {
            if (fqName.equals(annotationDescriptor.getFqName())) {
                return extractSingleStringArgument(annotationDescriptor);
            }
        }
        return null;
    }

    public static boolean isNonModule(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(JS_NON_MODULE_ANNOTATION) != null;
    }

    public static boolean isFromNonModuleFile(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(34);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(35);
        }
        return CollectionsKt.any(getContainingFileAnnotations(bindingContext, declarationDescriptor), new Function1() { // from class: org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(AnnotationsUtils.JS_NON_MODULE_ANNOTATION.equals(((AnnotationDescriptor) obj).getFqName()));
            }
        });
    }

    public static boolean isJsExternalInheritorsOnly(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(36);
        }
        return classDescriptor.getAnnotations().hasAnnotation(JS_EXTERNAL_INHERITORS_ONLY);
    }

    public static boolean isJsExternalArgument(ValueParameterDescriptor valueParameterDescriptor) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(37);
        }
        return valueParameterDescriptor.getAnnotations().hasAnnotation(JS_EXTERNAL_ARGUMENT);
    }

    private static String extractSingleStringArgument(AnnotationDescriptor annotationDescriptor) {
        ConstantValue<?> next;
        if (annotationDescriptor == null) {
            $$$reportNull$$$0(38);
        }
        if (annotationDescriptor.getAllValueArguments().isEmpty() || (next = annotationDescriptor.getAllValueArguments().values().iterator().next()) == null || !(next.getValue() instanceof String)) {
            return null;
        }
        return (String) next.getValue();
    }

    public static List<AnnotationDescriptor> getContainingFileAnnotations(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(39);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(declarationDescriptor, PackageFragmentDescriptor.class, false);
        if (packageFragmentDescriptor instanceof KotlinJavascriptPackageFragment) {
            List<AnnotationDescriptor> containingFileAnnotations = ((KotlinJavascriptPackageFragment) packageFragmentDescriptor).getContainingFileAnnotations(declarationDescriptor);
            if (containingFileAnnotations == null) {
                $$$reportNull$$$0(41);
            }
            return containingFileAnnotations;
        }
        KtFile file = getFile(declarationDescriptor);
        if (file != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<KtAnnotationEntry> it = file.getAnnotationEntries().iterator();
            while (it.hasNext()) {
                AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) bindingContext.get(BindingContext.ANNOTATION, it.next());
                if (annotationDescriptor != null) {
                    arrayList.add(annotationDescriptor);
                }
            }
            return arrayList;
        }
        List<AnnotationDescriptor> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(43);
        }
        return listEmptyList;
    }

    private static KtFile getFile(DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof DeclarationDescriptorWithSource)) {
            return null;
        }
        SourceFile containingFile = ((DeclarationDescriptorWithSource) declarationDescriptor).getSource().getContainingFile();
        if (!(containingFile instanceof PsiSourceFile)) {
            return null;
        }
        PsiFile psiFile = ((PsiSourceFile) containingFile).getPsiFile();
        if (psiFile instanceof KtFile) {
            return (KtFile) psiFile;
        }
        return null;
    }
}
