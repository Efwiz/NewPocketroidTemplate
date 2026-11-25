package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.Icon;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassLevelDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSyntheticClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.InheritanceImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stub.JavaStubImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassImpl extends JavaStubPsiElement<PsiClassStub<?>> implements Queryable, PsiExtensibleClass {
    private volatile String myCachedName;
    private final ClassInnerStuffCache myInnersCache;

    /* JADX WARN: Code duplicated, block: B:13:0x0019 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:7:0x000c A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1) {
            switch (i) {
                default:
                    switch (i) {
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1) {
            switch (i) {
                default:
                    switch (i) {
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i != 1) {
            switch (i) {
                case 25:
                    objArr[0] = "visitor";
                    break;
                case 26:
                    objArr[0] = "processor";
                    break;
                case 27:
                    objArr[0] = "state";
                    break;
                case 28:
                    objArr[0] = "place";
                    break;
                case 29:
                    objArr[0] = "newName";
                    break;
                case 30:
                    objArr[0] = "baseClass";
                    break;
                default:
                    switch (i) {
                        case 32:
                        case 34:
                            objArr[0] = CompilerOptions.INFO;
                            break;
                        case 33:
                            objArr[0] = "psiClass";
                            break;
                        default:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "name";
        }
        switch (i) {
            case 1:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 32:
            case 33:
            case 34:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiClassImpl";
                break;
            case 2:
                objArr[1] = "getExtendsListTypes";
                break;
            case 3:
                objArr[1] = "getImplementsListTypes";
                break;
            case 4:
                objArr[1] = "getInterfaces";
                break;
            case 5:
                objArr[1] = "getSupers";
                break;
            case 6:
                objArr[1] = "getSuperTypes";
                break;
            case 7:
                objArr[1] = "getVisibleSignatures";
                break;
            case 8:
                objArr[1] = "getFields";
                break;
            case 9:
                objArr[1] = "getMethods";
                break;
            case 10:
                objArr[1] = "getConstructors";
                break;
            case 11:
                objArr[1] = "getInnerClasses";
                break;
            case 12:
                objArr[1] = "getRecordComponents";
                break;
            case 13:
                objArr[1] = "getOwnFields";
                break;
            case 14:
                objArr[1] = "getOwnMethods";
                break;
            case 15:
                objArr[1] = "getOwnInnerClasses";
                break;
            case 16:
                objArr[1] = "getInitializers";
                break;
            case 17:
                objArr[1] = "getTypeParameters";
                break;
            case 18:
                objArr[1] = "getAllFields";
                break;
            case 19:
                objArr[1] = "getAllMethods";
                break;
            case 20:
                objArr[1] = "getAllInnerClasses";
                break;
            case 21:
                objArr[1] = "findMethodsBySignature";
                break;
            case 22:
                objArr[1] = "findMethodsByName";
                break;
            case 23:
                objArr[1] = "findMethodsAndTheirSubstitutorsByName";
                break;
            case 24:
                objArr[1] = "getAllMethodsAndTheirSubstitutors";
                break;
            case 31:
                objArr[1] = "getUseScope";
                break;
            default:
                objArr[1] = "getNode";
                break;
        }
        if (i != 1) {
            switch (i) {
                case 25:
                    objArr[2] = "accept";
                    break;
                case 26:
                case 27:
                case 28:
                    objArr[2] = "processDeclarations";
                    break;
                case 29:
                    objArr[2] = "setName";
                    break;
                case 30:
                    objArr[2] = "isInheritor";
                    break;
                default:
                    switch (i) {
                        case 32:
                        case 33:
                        case 34:
                            objArr[2] = "putInfo";
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "hasModifierProperty";
        }
        String str2 = String.format(str, objArr);
        if (i != 1) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    break;
                default:
                    switch (i) {
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            throw new IllegalStateException(str2);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public PsiClassImpl(PsiClassStub psiClassStub) {
        this(psiClassStub, JavaStubElementTypes.CLASS);
    }

    protected PsiClassImpl(PsiClassStub psiClassStub, IStubElementType iStubElementType) {
        super(psiClassStub, iStubElementType);
        this.myInnersCache = new ClassInnerStuffCache(this);
        addTrace(null);
    }

    public PsiClassImpl(ASTNode aSTNode) {
        super(aSTNode);
        this.myInnersCache = new ClassInnerStuffCache(this);
        addTrace(null);
    }

    private void addTrace(PsiClassStub psiClassStub) {
        String str;
        if (ourTraceStubAstBinding) {
            String str2 = "Creation thread: " + Thread.currentThread() + "\n" + DebugUtil.currentStackTrace();
            if (psiClassStub != null) {
                str2 = str2 + "\nfrom stub " + psiClassStub + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + System.identityHashCode(psiClassStub) + "\n";
                if ((psiClassStub instanceof UserDataHolder) && (str = (String) ((UserDataHolder) psiClassStub).getUserData(CREATION_TRACE)) != null) {
                    str2 = str2 + str;
                }
            }
            putUserData(CREATION_TRACE, str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        dropCaches();
        super.subtreeChanged();
    }

    private void dropCaches() {
        this.myCachedName = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiClassImpl psiClassImpl = (PsiClassImpl) super.clone();
        psiClassImpl.dropCaches();
        return psiClassImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement */
    public PsiElement getFunDelegate() {
        return DumbService.isDumb(getProject()) ? this : (PsiElement) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6649x19c7ba22();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getOriginalElement$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiClassImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6649x19c7ba22() {
        JavaPsiImplementationHelper javaPsiImplementationHelper = JavaPsiImplementationHelper.getInstance(getProject());
        return CachedValueProvider.Result.create(javaPsiImplementationHelper != null ? javaPsiImplementationHelper.getOriginalClass(this) : this, PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(0);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return (PsiIdentifier) getNode().findChildByRoleAsPsiElement(9);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        PsiClassStub psiClassStub = (PsiClassStub) getStub();
        if (psiClassStub != null) {
            return psiClassStub.getParentStub().getPsi();
        }
        for (ASTNode treeParent = getNode().getTreeParent(); treeParent != null; treeParent = treeParent.getTreeParent()) {
            if (treeParent.getElementType() instanceof IStubElementType) {
                return treeParent.getPsi();
            }
        }
        return getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2;
        String str = this.myCachedName;
        if (str != null) {
            return str;
        }
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub == null) {
            PsiIdentifier psiIdentifierMo7392getNameIdentifier = mo7392getNameIdentifier();
            name2 = psiIdentifierMo7392getNameIdentifier == null ? null : psiIdentifierMo7392getNameIdentifier.getText();
        } else {
            name2 = psiClassStub.getName();
        }
        this.myCachedName = name2;
        return name2;
    }

    public String getQualifiedName() {
        String qualifiedName;
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.getQualifiedName();
        }
        PsiElement parent = getLightParent();
        if (parent instanceof PsiJavaFile) {
            return StringUtil.getQualifiedName(((PsiJavaFile) parent).getPackageName(), StringUtil.notNullize(getName()));
        }
        if (!(parent instanceof PsiClass) || (qualifiedName = ((PsiClass) parent).getQualifiedName()) == null) {
            return null;
        }
        return StringUtil.getQualifiedName(qualifiedName, StringUtil.notNullize(getName()));
    }

    public PsiModifierList getModifierList() {
        return (PsiModifierList) getRequiredStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    public PsiReferenceList getExtendsList() {
        return (PsiReferenceList) getStubOrPsiChild(JavaStubElementTypes.EXTENDS_LIST);
    }

    public PsiReferenceList getImplementsList() {
        return (PsiReferenceList) getStubOrPsiChild(JavaStubElementTypes.IMPLEMENTS_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] extendsListTypes = PsiClassImplUtil.getExtendsListTypes(this);
        if (extendsListTypes == null) {
            $$$reportNull$$$0(2);
        }
        return extendsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] implementsListTypes = PsiClassImplUtil.getImplementsListTypes(this);
        if (implementsListTypes == null) {
            $$$reportNull$$$0(3);
        }
        return implementsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getPermitsList() {
        return (PsiReferenceList) getStubOrPsiChild(JavaStubElementTypes.PERMITS_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = PsiClassImplUtil.getInterfaces(this);
        if (interfaces == null) {
            $$$reportNull$$$0(4);
        }
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass[] supers = PsiClassImplUtil.getSupers(this);
        if (supers == null) {
            $$$reportNull$$$0(5);
        }
        return supers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType[] superTypes = PsiClassImplUtil.getSuperTypes(this);
        if (superTypes == null) {
            $$$reportNull$$$0(6);
        }
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            StubElement parentStub = psiClassStub.getParentStub();
            if (parentStub instanceof PsiClassStub) {
                return (PsiClass) ((PsiClassStub) parentStub).getPsi();
            }
            return null;
        }
        PsiElement parent = getLightParent();
        if (parent instanceof PsiClassLevelDeclarationStatement) {
            return (PsiClass) PsiTreeUtil.getParentOfType(this, PsiSyntheticClass.class);
        }
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        return getContext(null);
    }

    public PsiElement getContext(final String str) {
        PsiClassStub psiClassStub = (PsiClassStub) getStub();
        if (psiClassStub == null) {
            return getLightParent();
        }
        if (str == null) {
            return super.getContext();
        }
        StubElement parentStub = psiClassStub.getParentStub();
        while (parentStub != null && !(parentStub instanceof PsiClassStub) && !(parentStub instanceof PsiFileStub)) {
            PsiClass[] psiClassArr = (PsiClass[]) parentStub.getChildrenByType(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY);
            if (psiClassArr.length > 0 && ContainerUtil.exists(psiClassArr, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return str.equals(((PsiClass) obj).getName());
                }
            })) {
                return getLightParent();
            }
            if (parentStub instanceof PsiMethodStub) {
                return parentStub.getPsi();
            }
            parentStub = parentStub.getParentStub();
        }
        return parentStub != null ? parentStub.getPsi() : getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        if (visibleSignatures == null) {
            $$$reportNull$$$0(7);
        }
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] fields = this.myInnersCache.getFields();
        if (fields == null) {
            $$$reportNull$$$0(8);
        }
        return fields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] methods = this.myInnersCache.getMethods();
        if (methods == null) {
            $$$reportNull$$$0(9);
        }
        return methods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        PsiMethod[] constructors = this.myInnersCache.getConstructors();
        if (constructors == null) {
            $$$reportNull$$$0(10);
        }
        return constructors;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] innerClasses = this.myInnersCache.getInnerClasses();
        if (innerClasses == null) {
            $$$reportNull$$$0(11);
        }
        return innerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiRecordComponent[] getRecordComponents() {
        PsiRecordComponent[] recordComponents = this.myInnersCache.getRecordComponents();
        if (recordComponents == null) {
            $$$reportNull$$$0(12);
        }
        return recordComponents;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiRecordHeader getRecordHeader() {
        return (PsiRecordHeader) getStubOrPsiChild(JavaStubElementTypes.RECORD_HEADER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiField> getOwnFields() {
        List<PsiField> listAsList = Arrays.asList((PsiField[]) getStubOrPsiChildren(Constants.FIELD_BIT_SET, PsiField.ARRAY_FACTORY));
        if (listAsList == null) {
            $$$reportNull$$$0(13);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiMethod> getOwnMethods() {
        List<PsiMethod> listAsList = Arrays.asList((PsiMethod[]) getStubOrPsiChildren(Constants.METHOD_BIT_SET, PsiMethod.ARRAY_FACTORY));
        if (listAsList == null) {
            $$$reportNull$$$0(14);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        List<PsiClass> listAsList = Arrays.asList((PsiClass[]) getStubOrPsiChildren(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY));
        if (listAsList == null) {
            $$$reportNull$$$0(15);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] psiClassInitializerArr = (PsiClassInitializer[]) getStubOrPsiChildren(JavaStubElementTypes.CLASS_INITIALIZER, PsiClassInitializer.ARRAY_FACTORY);
        if (psiClassInitializerArr == null) {
            $$$reportNull$$$0(16);
        }
        return psiClassInitializerArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = PsiImplUtil.getTypeParameters(this);
        if (typeParameters == null) {
            $$$reportNull$$$0(17);
        }
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] allFields = PsiClassImplUtil.getAllFields(this);
        if (allFields == null) {
            $$$reportNull$$$0(18);
        }
        return allFields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = PsiClassImplUtil.getAllMethods(this);
        if (allMethods == null) {
            $$$reportNull$$$0(19);
        }
        return allMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = PsiClassImplUtil.getAllInnerClasses(this);
        if (allInnerClasses == null) {
            $$$reportNull$$$0(20);
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
            $$$reportNull$$$0(21);
        }
        return psiMethodArrFindMethodsBySignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String str, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsByName = this.myInnersCache.findMethodsByName(str, z);
        if (psiMethodArrFindMethodsByName == null) {
            $$$reportNull$$$0(22);
        }
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String str, boolean z) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, str, z);
        if (listFindMethodsAndTheirSubstitutorsByName == null) {
            $$$reportNull$$$0(23);
        }
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        if (allWithSubstitutorsByMap == null) {
            $$$reportNull$$$0(24);
        }
        return allWithSubstitutorsByMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String str, boolean z) {
        return this.myInnersCache.findInnerClassByName(str, z);
    }

    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return (PsiTypeParameterList) getRequiredStubOrPsiChild(JavaStubElementTypes.TYPE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return JavaStubImplUtil.isMemberDeprecated(this, (PsiMemberStub) getGreenStub());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub == null || psiClassStub.hasDocComment()) {
            return (PsiDocComment) getNode().findChildByRoleAsPsiElement(7);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getLBrace() {
        return (PsiJavaToken) getNode().findChildByRoleAsPsiElement(18);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiJavaToken getRBrace() {
        return (PsiJavaToken) getNode().findChildByRoleAsPsiElement(19);
    }

    public boolean isInterface() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.isInterface();
        }
        ASTNode aSTNodeFindChildByRole = getNode().findChildByRole(11);
        return aSTNodeFindChildByRole != null && aSTNodeFindChildByRole.getElementType() == JavaTokenType.INTERFACE_KEYWORD;
    }

    public boolean isAnnotationType() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.isAnnotationType();
        }
        return getNode().findChildByRole(247) != null;
    }

    public boolean isEnum() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.isEnum();
        }
        ASTNode aSTNodeFindChildByRole = getNode().findChildByRole(11);
        return aSTNodeFindChildByRole != null && aSTNodeFindChildByRole.getElementType() == JavaTokenType.ENUM_KEYWORD;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isRecord() {
        PsiClassStub psiClassStub = (PsiClassStub) getGreenStub();
        if (psiClassStub != null) {
            return psiClassStub.isRecord();
        }
        ASTNode aSTNodeFindChildByRole = getNode().findChildByRole(11);
        return aSTNodeFindChildByRole != null && aSTNodeFindChildByRole.getElementType() == JavaTokenType.RECORD_KEYWORD;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(25);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitClass(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiClass:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(26);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(27);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(28);
        }
        return PsiClassImplUtil.processDeclarationsInClass(this, psiScopeProcessor, resolveState, null, psiElement, psiElement2, PsiUtil.getLanguageLevel(psiElement2), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(29);
        }
        String name2 = getName();
        boolean zIsRenameFileOnRenaming = isRenameFileOnRenaming();
        PsiImplUtil.setName((PsiElement) Objects.requireNonNull(mo7392getNameIdentifier()), str);
        if (zIsRenameFileOnRenaming) {
            PsiFile psiFile = (PsiFile) getLightParent();
            String name3 = psiFile.getName();
            int iLastIndexOf = name3.lastIndexOf(46);
            psiFile.setName(iLastIndexOf >= 0 ? str + "." + name3.substring(iLastIndexOf + 1) : str);
        }
        for (PsiMethod psiMethod : getMethods()) {
            if (psiMethod.isConstructor() && psiMethod.getName().equals(name2)) {
                psiMethod.setName(str);
            }
        }
        return this;
    }

    private boolean isRenameFileOnRenaming() {
        PsiElement parent = getLightParent();
        if (!(parent instanceof PsiFile)) {
            return false;
        }
        String name2 = ((PsiFile) parent).getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            name2 = name2.substring(0, iLastIndexOf);
        }
        return name2.equals(getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass psiClass, boolean z) {
        if (psiClass == null) {
            $$$reportNull$$$0(30);
        }
        return InheritanceImplUtil.isInheritor(this, psiClass, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass psiClass, PsiClass psiClass2) {
        return InheritanceImplUtil.isInheritorDeep(this, psiClass, psiClass2);
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
            $$$reportNull$$$0(31);
        }
        return classUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(32);
        }
        putInfo(this, map);
    }

    public static void putInfo(PsiClass psiClass, Map<? super String, ? super String> map) {
        if (psiClass == null) {
            $$$reportNull$$$0(33);
        }
        if (map == null) {
            $$$reportNull$$$0(34);
        }
        map.put("className", psiClass.getName());
        map.put("qualifiedClassName", psiClass.getQualifiedName());
        PsiFile containingFile = psiClass.getContainingFile();
        if (containingFile instanceof Queryable) {
            ((Queryable) containingFile).putInfo(map);
        }
    }

    public PsiMethod getValuesMethod() {
        return this.myInnersCache.getValuesMethod();
    }
}
