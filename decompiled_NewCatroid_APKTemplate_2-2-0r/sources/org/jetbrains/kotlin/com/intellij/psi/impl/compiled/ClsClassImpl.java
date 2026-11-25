package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.Icon;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordHeaderStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.ClassInnerStuffCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodsProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ClsClassImpl extends ClsMemberImpl<PsiClassStub<?>> implements Queryable, PsiExtensibleClass {
    public static final Key<PsiClass> DELEGATE_KEY = Key.create("DELEGATE");
    private final ClassInnerStuffCache myInnersCache;

    /* JADX WARN: Code duplicated, block: B:12:0x0018  */
    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 44) {
            switch (i) {
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 2 && i != 44) {
            switch (i) {
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "name";
        } else if (i != 44) {
            switch (i) {
                case 32:
                    objArr[0] = "buffer";
                    break;
                case 33:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 34:
                    objArr[0] = "visitor";
                    break;
                case 35:
                    objArr[0] = "processor";
                    break;
                case 36:
                    objArr[0] = "state";
                    break;
                case 37:
                    objArr[0] = "place";
                    break;
                case 38:
                    objArr[0] = "baseClass";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassImpl";
                    break;
            }
        } else {
            objArr[0] = CompilerOptions.INFO;
        }
        switch (i) {
            case 1:
                objArr[1] = "getTypeParameterList";
                break;
            case 2:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 44:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassImpl";
                break;
            case 3:
                objArr[1] = "getExtendsList";
                break;
            case 4:
                objArr[1] = "getImplementsList";
                break;
            case 5:
                objArr[1] = "getExtendsListTypes";
                break;
            case 6:
                objArr[1] = "getImplementsListTypes";
                break;
            case 7:
                objArr[1] = "getInterfaces";
                break;
            case 8:
            case 9:
                objArr[1] = "getSupers";
                break;
            case 10:
            case 11:
                objArr[1] = "getSuperTypes";
                break;
            case 12:
                objArr[1] = "getVisibleSignatures";
                break;
            case 13:
                objArr[1] = "getFields";
                break;
            case 14:
                objArr[1] = "getMethods";
                break;
            case 15:
                objArr[1] = "getConstructors";
                break;
            case 16:
                objArr[1] = "getInnerClasses";
                break;
            case 17:
                objArr[1] = "getOwnFields";
                break;
            case 18:
                objArr[1] = "getOwnMethods";
                break;
            case 19:
            case 20:
            case 21:
                objArr[1] = "getOwnInnerClasses";
                break;
            case 22:
                objArr[1] = "getRecordComponents";
                break;
            case 23:
                objArr[1] = "getInitializers";
                break;
            case 24:
                objArr[1] = "getTypeParameters";
                break;
            case 25:
                objArr[1] = "getAllFields";
                break;
            case 26:
                objArr[1] = "getAllMethods";
                break;
            case 27:
                objArr[1] = "getAllInnerClasses";
                break;
            case 28:
                objArr[1] = "findMethodsBySignature";
                break;
            case 29:
                objArr[1] = "findMethodsByName";
                break;
            case 30:
                objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 31:
                objArr[1] = "getAllMethodsAndTheirSubstitutors";
                break;
            case 39:
            case 40:
            case 41:
            case 42:
                objArr[1] = "getNavigationElement";
                break;
            case 43:
                objArr[1] = "getUseScope";
                break;
            default:
                objArr[1] = "getChildren";
                break;
        }
        if (i == 2) {
            objArr[2] = "hasModifierProperty";
        } else if (i != 44) {
            switch (i) {
                case 32:
                    objArr[2] = "appendMirrorText";
                    break;
                case 33:
                    objArr[2] = "setMirror";
                    break;
                case 34:
                    objArr[2] = "accept";
                    break;
                case 35:
                case 36:
                case 37:
                    objArr[2] = "processDeclarations";
                    break;
                case 38:
                    objArr[2] = "isInheritor";
                    break;
            }
        } else {
            objArr[2] = "putInfo";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 44) {
            switch (i) {
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getRBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ Object setName(String str) {
        return super.setName(str);
    }

    public ClsClassImpl(PsiClassStub psiClassStub) {
        super(psiClassStub);
        this.myInnersCache = new ClassInnerStuffCache(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ArrayList arrayList = new ArrayList();
        ContainerUtil.addAll(arrayList, getChildren(mo6091getDocComment(), getModifierListInternal(), mo7392getNameIdentifier(), getExtendsList(), getImplementsList()));
        arrayList.addAll(getOwnFields());
        arrayList.addAll(getOwnMethods());
        arrayList.addAll(getOwnInnerClasses());
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(0);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        PsiTypeParameterList psi = ((PsiTypeParameterListStub) Objects.requireNonNull((PsiTypeParameterListStub) ((PsiClassStub) getStub()).findChildStubByType(JavaStubElementTypes.TYPE_PARAMETER_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(1);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return ((PsiClassStub) getStub()).getQualifiedName();
    }

    private boolean isLocalClass() {
        PsiClassStub psiClassStub = (PsiClassStub) getStub();
        return (psiClassStub instanceof PsiClassStubImpl) && ((PsiClassStubImpl) psiClassStub).isLocalClassInner();
    }

    private boolean isAnonymousOrLocalClass() {
        return (this instanceof PsiAnonymousClass) || isLocalClass();
    }

    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return getModifierListInternal();
    }

    private PsiModifierList getModifierListInternal() {
        return ((PsiModifierListStub) Objects.requireNonNull((PsiModifierListStub) ((PsiClassStub) getStub()).findChildStubByType(JavaStubElementTypes.MODIFIER_LIST))).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return getModifierListInternal().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        PsiReferenceList psi = ((PsiClassReferenceListStub) Objects.requireNonNull((PsiClassReferenceListStub) ((PsiClassStub) getStub()).findChildStubByType(JavaStubElementTypes.EXTENDS_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(3);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        PsiReferenceList psi = ((PsiClassReferenceListStub) Objects.requireNonNull((PsiClassReferenceListStub) ((PsiClassStub) getStub()).findChildStubByType(JavaStubElementTypes.IMPLEMENTS_LIST))).getPsi();
        if (psi == null) {
            $$$reportNull$$$0(4);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] extendsListTypes = PsiClassImplUtil.getExtendsListTypes(this);
        if (extendsListTypes == null) {
            $$$reportNull$$$0(5);
        }
        return extendsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] implementsListTypes = PsiClassImplUtil.getImplementsListTypes(this);
        if (implementsListTypes == null) {
            $$$reportNull$$$0(6);
        }
        return implementsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = PsiClassImplUtil.getInterfaces(this);
        if (interfaces == null) {
            $$$reportNull$$$0(7);
        }
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(getQualifiedName())) {
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(8);
            }
            return psiClassArr;
        }
        PsiClass[] supers = PsiClassImplUtil.getSupers(this);
        if (supers == null) {
            $$$reportNull$$$0(9);
        }
        return supers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        if (CommonClassNames.JAVA_LANG_OBJECT.equals(getQualifiedName())) {
            PsiClassType[] psiClassTypeArr = PsiClassType.EMPTY_ARRAY;
            if (psiClassTypeArr == null) {
                $$$reportNull$$$0(10);
            }
            return psiClassTypeArr;
        }
        PsiClassType[] superTypes = PsiClassImplUtil.getSuperTypes(this);
        if (superTypes == null) {
            $$$reportNull$$$0(11);
        }
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        PsiElement parent = getMyParent();
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        if (visibleSignatures == null) {
            $$$reportNull$$$0(12);
        }
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] fields = this.myInnersCache.getFields();
        if (fields == null) {
            $$$reportNull$$$0(13);
        }
        return fields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] methods = this.myInnersCache.getMethods();
        if (methods == null) {
            $$$reportNull$$$0(14);
        }
        return methods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        PsiMethod[] constructors = this.myInnersCache.getConstructors();
        if (constructors == null) {
            $$$reportNull$$$0(15);
        }
        return constructors;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] innerClasses = this.myInnersCache.getInnerClasses();
        if (innerClasses == null) {
            $$$reportNull$$$0(16);
        }
        return innerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiField> getOwnFields() {
        List<PsiField> listAsList = Arrays.asList((PsiField[]) ((PsiClassStub) getStub()).getChildrenByType(Constants.FIELD_BIT_SET, PsiField.ARRAY_FACTORY));
        if (listAsList == null) {
            $$$reportNull$$$0(17);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiMethod> getOwnMethods() {
        List<PsiMethod> listAsList = Arrays.asList((PsiMethod[]) ((PsiClassStub) getStub()).getChildrenByType(Constants.METHOD_BIT_SET, PsiMethod.ARRAY_FACTORY));
        if (listAsList == null) {
            $$$reportNull$$$0(18);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        PsiClass[] psiClassArr = (PsiClass[]) ((PsiClassStub) getStub()).getChildrenByType(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY);
        if (psiClassArr.length == 0) {
            List<PsiClass> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(19);
            }
            return listEmptyList;
        }
        int i = 0;
        for (PsiClass psiClass : psiClassArr) {
            if ((psiClass instanceof ClsClassImpl) && ((ClsClassImpl) psiClass).isAnonymousOrLocalClass()) {
                i++;
            }
        }
        if (i == 0) {
            List<PsiClass> listAsList = Arrays.asList(psiClassArr);
            if (listAsList == null) {
                $$$reportNull$$$0(20);
            }
            return listAsList;
        }
        ArrayList arrayList = new ArrayList(psiClassArr.length - i);
        for (PsiClass psiClass2 : psiClassArr) {
            if (!(psiClass2 instanceof ClsClassImpl) || !((ClsClassImpl) psiClass2).isAnonymousOrLocalClass()) {
                arrayList.add(psiClass2);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiRecordComponent[] getRecordComponents() {
        PsiRecordHeader recordHeader = getRecordHeader();
        PsiRecordComponent[] recordComponents = recordHeader == null ? PsiRecordComponent.EMPTY_ARRAY : recordHeader.getRecordComponents();
        if (recordComponents == null) {
            $$$reportNull$$$0(22);
        }
        return recordComponents;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiRecordHeader getRecordHeader() {
        PsiRecordHeaderStub psiRecordHeaderStub = (PsiRecordHeaderStub) ((PsiClassStub) getStub()).findChildStubByType(JavaStubElementTypes.RECORD_HEADER);
        if (psiRecordHeaderStub == null) {
            return null;
        }
        return psiRecordHeaderStub.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] psiClassInitializerArr = PsiClassInitializer.EMPTY_ARRAY;
        if (psiClassInitializerArr == null) {
            $$$reportNull$$$0(23);
        }
        return psiClassInitializerArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = PsiImplUtil.getTypeParameters(this);
        if (typeParameters == null) {
            $$$reportNull$$$0(24);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] allFields = PsiClassImplUtil.getAllFields(this);
        if (allFields == null) {
            $$$reportNull$$$0(25);
        }
        return allFields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = PsiClassImplUtil.getAllMethods(this);
        if (allMethods == null) {
            $$$reportNull$$$0(26);
        }
        return allMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = PsiClassImplUtil.getAllInnerClasses(this);
        if (allInnerClasses == null) {
            $$$reportNull$$$0(27);
        }
        return allInnerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField findFieldByName(String str, boolean z) {
        return this.myInnersCache.findFieldByName(str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod findMethodBySignature(PsiMethod psiMethod, boolean z) {
        return PsiClassImplUtil.findMethodBySignature(this, psiMethod, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsBySignature(PsiMethod psiMethod, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsBySignature = PsiClassImplUtil.findMethodsBySignature(this, psiMethod, z);
        if (psiMethodArrFindMethodsBySignature == null) {
            $$$reportNull$$$0(28);
        }
        return psiMethodArrFindMethodsBySignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String str, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsByName = this.myInnersCache.findMethodsByName(str, z);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(29);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(30);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(31);
        }
        return allWithSubstitutorsByMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String str, boolean z) {
        return this.myInnersCache.findInnerClassByName(str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return ((PsiClassStub) getStub()).isDeprecated() || PsiImplUtil.isDeprecatedByAnnotation(this);
    }

    public String getSourceFileName() {
        String sourceFileName = ((PsiClassStub) getStub()).getSourceFileName();
        return sourceFileName != null ? sourceFileName : obtainSourceFileNameFromClassFileName();
    }

    private String obtainSourceFileNameFromClassFileName() {
        String name2 = getContainingFile().getMyName();
        int iIndexOf = name2.indexOf(36);
        if (iIndexOf < 0 && (iIndexOf = name2.indexOf(46)) < 0) {
            iIndexOf = name2.length();
        }
        return name2.substring(0, iIndexOf) + SuffixConstants.SUFFIX_STRING_java;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return ((PsiClassStub) getStub()).isInterface();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return ((PsiClassStub) getStub()).isAnnotationType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return ((PsiClassStub) getStub()).isEnum();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isRecord() {
        return ((PsiClassStub) getStub()).isRecord();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        String str;
        if (sb == null) {
            $$$reportNull$$$0(32);
        }
        appendText(mo6091getDocComment(), i, sb, "go_to_next_line_and_indent");
        appendText(getModifierListInternal(), i, sb);
        if (isEnum()) {
            str = "enum ";
        } else if (isAnnotationType()) {
            str = "@interface ";
        } else if (isInterface()) {
            str = "interface ";
        } else {
            str = isRecord() ? "record " : ExternalAnnotationProvider.CLASS_PREFIX;
        }
        sb.append(str);
        PsiRecordHeader recordHeader = getRecordHeader();
        if (recordHeader == null) {
            appendText(mo7392getNameIdentifier(), i, sb, " ");
        } else {
            appendText(mo7392getNameIdentifier(), i, sb, "");
            appendText(recordHeader, i, sb, " ");
        }
        appendText(mo6093getTypeParameterList(), i, sb, " ");
        appendText(getExtendsList(), i, sb, " ");
        appendText(getImplementsList(), i, sb, " ");
        sb.append('{');
        int indentSize = getIndentSize() + i;
        List<PsiField> ownFields = getOwnFields();
        List<PsiMethod> ownMethods = getOwnMethods();
        List<PsiClass> ownInnerClasses = getOwnInnerClasses();
        if (!ownFields.isEmpty()) {
            goNextLine(indentSize, sb);
            for (int i2 = 0; i2 < ownFields.size(); i2++) {
                PsiField psiField = ownFields.get(i2);
                appendText(psiField, indentSize, sb);
                if (psiField instanceof ClsEnumConstantImpl) {
                    if (i2 < ownFields.size() - 1 && (ownFields.get(i2 + 1) instanceof ClsEnumConstantImpl)) {
                        sb.append(", ");
                    } else {
                        sb.append(';');
                        if (i2 < ownFields.size() - 1) {
                            sb.append('\n');
                            goNextLine(indentSize, sb);
                        }
                    }
                } else if (i2 < ownFields.size() - 1) {
                    goNextLine(indentSize, sb);
                }
            }
        } else if (isEnum() && ownMethods.size() + ownInnerClasses.size() > 0) {
            goNextLine(indentSize, sb);
            sb.append(";");
        }
        if (!ownMethods.isEmpty()) {
            if (isEnum() || !ownFields.isEmpty()) {
                sb.append('\n');
            }
            goNextLine(indentSize, sb);
            for (int i3 = 0; i3 < ownMethods.size(); i3++) {
                appendText(ownMethods.get(i3), indentSize, sb);
                if (i3 < ownMethods.size() - 1) {
                    sb.append('\n');
                    goNextLine(indentSize, sb);
                }
            }
        }
        if (!ownInnerClasses.isEmpty()) {
            if (ownFields.size() + ownMethods.size() > 0) {
                sb.append('\n');
            }
            goNextLine(indentSize, sb);
            for (int i4 = 0; i4 < ownInnerClasses.size(); i4++) {
                appendText(ownInnerClasses.get(i4), indentSize, sb);
                if (i4 < ownInnerClasses.size() - 1) {
                    sb.append('\n');
                    goNextLine(indentSize, sb);
                }
            }
        }
        goNextLine(i, sb);
        sb.append('}');
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(33);
        }
        setMirrorCheckingType(treeElement, null);
        PsiClass psiClass = (PsiClass) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirrorIfPresent(mo6091getDocComment(), psiClass.mo6091getDocComment());
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        if (psiModifierListMo6095getModifierList != null) {
            setMirror(psiModifierListMo6095getModifierList, psiClass.mo6095getModifierList());
        }
        setMirror(mo7392getNameIdentifier(), psiClass.mo7392getNameIdentifier());
        setMirror(mo6093getTypeParameterList(), psiClass.mo6093getTypeParameterList());
        setMirror(getExtendsList(), psiClass.getExtendsList());
        setMirror(getImplementsList(), psiClass.getImplementsList());
        if (psiClass instanceof PsiExtensibleClass) {
            PsiExtensibleClass psiExtensibleClass = (PsiExtensibleClass) psiClass;
            setMirrors(getOwnFields(), psiExtensibleClass.getOwnFields());
            setMirrors(getOwnMethods(), psiExtensibleClass.getOwnMethods());
            setMirrors(getOwnInnerClasses(), psiExtensibleClass.getOwnInnerClasses());
            return;
        }
        setMirrors(getOwnFields(), Arrays.asList(psiClass.getFields()));
        setMirrors(getOwnMethods(), Arrays.asList(psiClass.getMethods()));
        setMirrors(getOwnInnerClasses(), Arrays.asList(psiClass.getInnerClasses()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(34);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitClass(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiClass:" + getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(35);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(36);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(37);
        }
        return PsiClassImplUtil.processDeclarationsInClass(this, psiScopeProcessor, resolveState, null, psiElement, psiElement2, psiScopeProcessor instanceof MethodsProcessor ? ((MethodsProcessor) psiScopeProcessor).getLanguageLevel() : PsiUtil.getLanguageLevel(psiElement2), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getMyParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2) {
        return InheritanceImplUtil.isInheritorDeep(this, psiClass, psiClass2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(38);
        }
        return InheritanceImplUtil.isInheritor(this, psiClass, z);
    }

    public PsiClass getSourceMirrorClass() {
        PsiClassOwner psiClassOwner;
        PsiClass psiClass = (PsiClass) getUserData(DELEGATE_KEY);
        if (psiClass instanceof ClsClassImpl) {
            return ((ClsClassImpl) psiClass).getSourceMirrorClass();
        }
        String name2 = getMyName();
        PsiElement parent = getMyParent();
        int i = 0;
        if (parent instanceof PsiFile) {
            if (!(parent instanceof PsiClassOwner) || (psiClassOwner = (PsiClassOwner) parent.getNavigationElement()) == parent) {
                return null;
            }
            PsiClass[] classes = psiClassOwner.getClasses();
            int length = classes.length;
            while (i < length) {
                PsiClass psiClass2 = classes[i];
                if (name2.equals(psiClass2.getMyName())) {
                    return psiClass2;
                }
                i++;
            }
        } else if (parent != null) {
            PsiClass sourceMirrorClass = ((ClsClassImpl) parent).getSourceMirrorClass();
            if (sourceMirrorClass == null) {
                return null;
            }
            PsiClass[] innerClasses = sourceMirrorClass.getInnerClasses();
            int length2 = innerClasses.length;
            while (i < length2) {
                PsiClass psiClass3 = innerClasses[i];
                if (name2.equals(psiClass3.getMyName())) {
                    return psiClass3;
                }
                i++;
            }
        } else {
            throw new PsiInvalidElementAccessException(this);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        Iterator<ClsCustomNavigationPolicy> it = ClsCustomNavigationPolicy.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            try {
                PsiElement navigationElement = it.next().getNavigationElement(this);
                if (navigationElement != null) {
                    if (navigationElement == null) {
                        $$$reportNull$$$0(39);
                    }
                    return navigationElement;
                }
            } catch (IndexNotReadyException unused) {
            }
        }
        try {
            PsiClass sourceMirrorClass = getSourceMirrorClass();
            if (sourceMirrorClass != null) {
                PsiElement navigationElement2 = sourceMirrorClass.getNavigationElement();
                if (navigationElement2 == null) {
                    $$$reportNull$$$0(40);
                }
                return navigationElement2;
            }
            if ("package-info".equals(getMyName())) {
                PsiElement parent = getMyParent();
                if (parent instanceof ClsFileImpl) {
                    PsiElement navigationElement3 = parent.getNavigationElement();
                    if (navigationElement3 instanceof PsiJavaFile) {
                        if (navigationElement3 == null) {
                            $$$reportNull$$$0(41);
                        }
                        return navigationElement3;
                    }
                }
            }
            return this;
        } catch (IndexNotReadyException unused2) {
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return PsiClassImplUtil.getClassIcon(i, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isClassEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope classUseScope = PsiClassImplUtil.getClassUseScope(this);
        if (classUseScope == null) {
            $$$reportNull$$$0(43);
        }
        return classUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(44);
        }
        PsiClassImpl.putInfo(this, map);
    }
}
