package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordField;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass;
import org.jetbrains.kotlin.com.intellij.psi.util.AccessModifier;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiRecordUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class RecordAugmentProvider extends PsiAugmentProvider {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/RecordAugmentProvider";
                break;
            case 6:
                objArr[0] = "recordHeader";
                break;
            case 8:
            case 10:
            case 12:
            case 13:
                objArr[0] = "component";
                break;
            case 9:
            case 11:
                objArr[0] = "factory";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getAugments";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getAccessorsAugments";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/RecordAugmentProvider";
        } else {
            objArr[1] = "getFieldAugments";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
                break;
            case 6:
                objArr[2] = "getCanonicalConstructor";
                break;
            case 8:
            case 9:
                objArr[2] = "createRecordField";
                break;
            case 10:
            case 11:
                objArr[2] = "createRecordMethod";
                break;
            case 12:
                objArr[2] = "hasForbiddenType";
                break;
            case 13:
                objArr[2] = "getTypeText";
                break;
            default:
                objArr[2] = "getAugments";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider
    protected <Psi extends PsiElement> List<Psi> getAugments(PsiElement psiElement, Class<Psi> cls, String str) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement instanceof PsiExtensibleClass) {
            PsiExtensibleClass psiExtensibleClass = (PsiExtensibleClass) psiElement;
            if (!psiExtensibleClass.isRecord()) {
                List<Psi> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(2);
                }
                return listEmptyList;
            }
            if (cls == PsiMethod.class && !(psiElement instanceof PsiCompiledElement)) {
                return getAccessorsAugments(psiExtensibleClass);
            }
            if (cls == PsiField.class) {
                return getFieldAugments(psiExtensibleClass);
            }
        }
        List<Psi> listEmptyList2 = Collections.emptyList();
        if (listEmptyList2 == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList2;
    }

    private static <Psi extends PsiElement> List<Psi> getAccessorsAugments(PsiExtensibleClass psiExtensibleClass) {
        PsiMethod psiMethodCreateRecordMethod;
        PsiRecordHeader recordHeader = psiExtensibleClass.getRecordHeader();
        if (recordHeader == null) {
            List<Psi> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(4);
            }
            return listEmptyList;
        }
        PsiRecordComponent[] recordComponents = psiExtensibleClass.getRecordComponents();
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(psiExtensibleClass.getProject()).getElementFactory();
        ArrayList arrayList = new ArrayList(recordComponents.length);
        List<PsiMethod> ownMethods = psiExtensibleClass.getOwnMethods();
        for (PsiRecordComponent psiRecordComponent : recordComponents) {
            if (shouldGenerateMethod(psiRecordComponent, ownMethods) && (psiMethodCreateRecordMethod = createRecordMethod(psiRecordComponent, elementFactory)) != null) {
                arrayList.add(new LightRecordMethod(psiExtensibleClass.getManager(), psiMethodCreateRecordMethod, psiExtensibleClass, psiRecordComponent));
            }
        }
        PsiMethod canonicalConstructor = getCanonicalConstructor(psiExtensibleClass, ownMethods, recordHeader);
        if (canonicalConstructor != null) {
            arrayList.add(canonicalConstructor);
        }
        return arrayList;
    }

    private static PsiMethod getCanonicalConstructor(PsiExtensibleClass psiExtensibleClass, List<PsiMethod> list, PsiRecordHeader psiRecordHeader) {
        if (psiRecordHeader == null) {
            $$$reportNull$$$0(6);
        }
        String name2 = psiExtensibleClass.getMyName();
        if (name2 == null) {
            return null;
        }
        for (PsiMethod psiMethod : list) {
            if (JavaPsiRecordUtil.isCompactConstructor(psiMethod) || JavaPsiRecordUtil.isExplicitCanonicalConstructor(psiMethod)) {
                return null;
            }
        }
        try {
            PsiMethod psiMethodCreateMethodFromText = JavaPsiFacade.getElementFactory(psiRecordHeader.getProject()).createMethodFromText(name2 + psiRecordHeader.getText() + "{" + StringUtil.join(psiRecordHeader.getRecordComponents(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.RecordAugmentProvider$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return RecordAugmentProvider.lambda$getCanonicalConstructor$0((PsiRecordComponent) obj);
                }
            }, "\n") + "}", psiRecordHeader.getContainingClass());
            PsiModifierList modifierList = psiExtensibleClass.mo6095getModifierList();
            psiMethodCreateMethodFromText.mo6095getModifierList().setModifierProperty((modifierList == null ? AccessModifier.PUBLIC : AccessModifier.fromModifierList(modifierList)).toPsiModifier(), true);
            return new LightRecordCanonicalConstructor(psiMethodCreateMethodFromText, psiExtensibleClass);
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    static /* synthetic */ String lambda$getCanonicalConstructor$0(PsiRecordComponent psiRecordComponent) {
        return "this." + psiRecordComponent.getMyName() + ContainerUtils.KEY_VALUE_DELIMITER + psiRecordComponent.getMyName() + ";";
    }

    private static boolean shouldGenerateMethod(PsiRecordComponent psiRecordComponent, List<PsiMethod> list) {
        String name2 = psiRecordComponent.getMyName();
        if (name2 == null) {
            return false;
        }
        for (PsiMethod psiMethod : list) {
            if (name2.equals(psiMethod.getMyName()) && psiMethod.getParameterList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static <Psi extends PsiElement> List<Psi> getFieldAugments(PsiClass psiClass) {
        PsiRecordComponent[] recordComponents = psiClass.getRecordComponents();
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(psiClass.getProject()).getElementFactory();
        ArrayList arrayList = new ArrayList(recordComponents.length);
        for (PsiRecordComponent psiRecordComponent : recordComponents) {
            PsiField psiFieldCreateRecordField = createRecordField(psiRecordComponent, elementFactory);
            if (psiFieldCreateRecordField != null) {
                arrayList.add(new LightRecordField(psiClass.getManager(), psiFieldCreateRecordField, psiClass, psiRecordComponent));
            }
        }
        return arrayList;
    }

    private static PsiField createRecordField(PsiRecordComponent psiRecordComponent, PsiElementFactory psiElementFactory) {
        String typeText;
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(9);
        }
        String name2 = psiRecordComponent.getMyName();
        if (hasForbiddenType(psiRecordComponent) || (typeText = getTypeText(psiRecordComponent)) == null) {
            return null;
        }
        try {
            return psiElementFactory.createFieldFromText("private final " + typeText + " " + name2 + ";", psiRecordComponent.mo6092getContainingClass());
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    private static PsiMethod createRecordMethod(PsiRecordComponent psiRecordComponent, PsiElementFactory psiElementFactory) {
        String typeText;
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElementFactory == null) {
            $$$reportNull$$$0(11);
        }
        String name2 = psiRecordComponent.getMyName();
        if (name2 == null || hasForbiddenType(psiRecordComponent) || (typeText = getTypeText(psiRecordComponent)) == null) {
            return null;
        }
        try {
            return psiElementFactory.createMethodFromText("public " + typeText + " " + name2 + "(){ return " + name2 + "; }", psiRecordComponent.mo6092getContainingClass());
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    private static boolean hasForbiddenType(PsiRecordComponent psiRecordComponent) {
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(12);
        }
        PsiTypeElement typeElement = psiRecordComponent.getTypeElement();
        return typeElement == null || typeElement.getText().equals(PsiKeyword.RECORD);
    }

    private static String getTypeText(PsiRecordComponent psiRecordComponent) {
        if (psiRecordComponent == null) {
            $$$reportNull$$$0(13);
        }
        PsiTypeElement typeElement = psiRecordComponent.getTypeElement();
        if (typeElement == null) {
            return null;
        }
        String text = typeElement.getText();
        return text.endsWith("...") ? text.substring(0, text.length() - 3) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : text;
    }
}
