package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassReferenceListStubImpl extends StubBase<PsiReferenceList> implements PsiClassReferenceListStub {
    private final TypeInfo[] myInfos;
    private volatile PsiClassType[] myTypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "names";
                break;
            case 2:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
                objArr[0] = AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiClassReferenceListStubImpl";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getReferencedTypes";
                break;
            case 5:
            case 6:
                objArr[1] = "createTypes";
                break;
            case 7:
            case 8:
                objArr[1] = "getReferencedNames";
                break;
            case 9:
            case 10:
                objArr[1] = "getTypes";
                break;
            case 11:
                objArr[1] = "getRole";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiClassReferenceListStubImpl";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiClassReferenceListStubImpl(JavaClassReferenceListElementType javaClassReferenceListElementType, StubElement stubElement, String[] strArr) {
        this(javaClassReferenceListElementType, stubElement, (TypeInfo[]) ContainerUtil.map2Array(strArr, TypeInfo.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return new TypeInfo((String) obj);
            }
        }));
        if (javaClassReferenceListElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (strArr == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiClassReferenceListStubImpl(JavaClassReferenceListElementType javaClassReferenceListElementType, StubElement stubElement, TypeInfo[] typeInfoArr) {
        super(stubElement, javaClassReferenceListElementType);
        if (javaClassReferenceListElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (typeInfoArr == null) {
            $$$reportNull$$$0(3);
        }
        for (TypeInfo typeInfo : typeInfoArr) {
            if (typeInfo == null) {
                throw new IllegalArgumentException();
            }
            if (typeInfo.text == null) {
                throw new IllegalArgumentException();
            }
        }
        this.myInfos = typeInfoArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub
    public PsiClassType[] getReferencedTypes() {
        PsiClassType[] psiClassTypeArrCreateTypes = this.myTypes;
        if (psiClassTypeArrCreateTypes == null) {
            psiClassTypeArrCreateTypes = createTypes();
            this.myTypes = psiClassTypeArrCreateTypes;
        }
        PsiClassType[] psiClassTypeArr = (PsiClassType[]) psiClassTypeArrCreateTypes.clone();
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiClassTypeArr;
    }

    private boolean shouldSkipSoleObject() {
        if (!((JavaClassReferenceListElementType) getStubType()).isCompiled(this)) {
            return false;
        }
        TypeInfo[] typeInfoArr = this.myInfos;
        return typeInfoArr.length == 1 && typeInfoArr[0].text.equals(CommonClassNames.JAVA_LANG_OBJECT) && this.myInfos[0].getTypeAnnotations().isEmpty();
    }

    private PsiClassType[] createTypes() {
        TypeInfo[] typeInfoArr = this.myInfos;
        PsiClassType[] psiClassTypeArr = typeInfoArr.length == 0 ? PsiClassType.EMPTY_ARRAY : new PsiClassType[typeInfoArr.length];
        int i = 0;
        if (((JavaClassReferenceListElementType) getStubType()).isCompiled(this)) {
            if (shouldSkipSoleObject()) {
                PsiClassType[] psiClassTypeArr2 = PsiClassType.EMPTY_ARRAY;
                if (psiClassTypeArr2 == null) {
                    $$$reportNull$$$0(5);
                }
                return psiClassTypeArr2;
            }
            while (i < psiClassTypeArr.length) {
                TypeInfo typeInfo = this.myInfos[i];
                TypeAnnotationContainer typeAnnotations = typeInfo.getTypeAnnotations();
                ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl = new ClsJavaCodeReferenceElementImpl(getPsi(), typeInfo.text, typeAnnotations);
                psiClassTypeArr[i] = new PsiClassReferenceType(clsJavaCodeReferenceElementImpl, null).annotate(typeAnnotations.getProvider(clsJavaCodeReferenceElementImpl));
                i++;
            }
        } else {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
            PsiReferenceList psi = getPsi();
            int i2 = 0;
            for (int i3 = 0; i3 < psiClassTypeArr.length; i3++) {
                try {
                    PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReferenceFromText = elementFactory.createReferenceFromText(this.myInfos[i3].text, psi);
                    ((PsiJavaCodeReferenceElementImpl) psiJavaCodeReferenceElementCreateReferenceFromText).setKindWhenDummy(PsiJavaCodeReferenceElementImpl.Kind.CLASS_NAME_KIND);
                    psiClassTypeArr[i3] = elementFactory.createType(psiJavaCodeReferenceElementCreateReferenceFromText);
                } catch (IncorrectOperationException unused) {
                    psiClassTypeArr[i3] = null;
                    i2++;
                }
            }
            if (i2 > 0) {
                PsiClassType[] psiClassTypeArr3 = new PsiClassType[psiClassTypeArr.length - i2];
                int length = psiClassTypeArr.length;
                int i4 = 0;
                while (i < length) {
                    PsiClassType psiClassType = psiClassTypeArr[i];
                    if (psiClassType != null) {
                        psiClassTypeArr3[i4] = psiClassType;
                        i4++;
                    }
                    i++;
                }
                psiClassTypeArr = psiClassTypeArr3;
            }
        }
        if (psiClassTypeArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiClassTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub
    public String[] getReferencedNames() {
        if (this.myInfos.length == 0 || shouldSkipSoleObject()) {
            String[] strArr = ArrayUtil.EMPTY_STRING_ARRAY;
            if (strArr == null) {
                $$$reportNull$$$0(7);
            }
            return strArr;
        }
        String[] strArr2 = (String[]) ContainerUtil.map2Array(this.myInfos, String.class, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((TypeInfo) obj).text;
            }
        });
        if (strArr2 == null) {
            $$$reportNull$$$0(8);
        }
        return strArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub
    public TypeInfo[] getTypes() {
        if (this.myInfos.length == 0 || shouldSkipSoleObject()) {
            TypeInfo[] typeInfoArr = TypeInfo.EMPTY_ARRAY;
            if (typeInfoArr == null) {
                $$$reportNull$$$0(9);
            }
            return typeInfoArr;
        }
        TypeInfo[] typeInfoArr2 = (TypeInfo[]) this.myInfos.clone();
        if (typeInfoArr2 == null) {
            $$$reportNull$$$0(10);
        }
        return typeInfoArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub
    public PsiReferenceList.Role getRole() {
        PsiReferenceList.Role roleElementTypeToRole = JavaClassReferenceListElementType.elementTypeToRole(getStubType());
        if (roleElementTypeToRole == null) {
            $$$reportNull$$$0(11);
        }
        return roleElementTypeToRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiRefListStub[" + getRole() + ':' + String.join(", ", getReferencedNames()) + ']';
    }
}
