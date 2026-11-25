package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.AnnotationTargetUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.ModifierFlags;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;

/* JADX INFO: loaded from: classes3.dex */
public class PsiModifierListImpl extends JavaStubPsiElement<PsiModifierListStub> implements PsiModifierList {
    private static final Map<IElementType, String> KEYWORD_TYPE_TO_NAME_MAP;
    private static final Map<String, IElementType> NAME_TO_KEYWORD_TYPE_MAP;
    private volatile ModifierCache myModifierCache;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8) ? 2 : 3];
        switch (i) {
            case 4:
            case 5:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiModifierListImpl";
                break;
            case 6:
            case 7:
                objArr[0] = "qualifiedName";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 4) {
            objArr[1] = "getAnnotations";
        } else if (i == 5) {
            objArr[1] = "getApplicableAnnotations";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiModifierListImpl";
        } else {
            objArr[1] = "addAnnotation";
        }
        switch (i) {
            case 1:
                objArr[2] = "hasExplicitModifier";
                break;
            case 2:
                objArr[2] = "setModifierProperty";
                break;
            case 3:
                objArr[2] = "checkSetModifierProperty";
                break;
            case 4:
            case 5:
            case 8:
                break;
            case 6:
                objArr[2] = "findAnnotation";
                break;
            case 7:
                objArr[2] = "addAnnotation";
                break;
            case 9:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "hasModifierProperty";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        HashMap map = new HashMap();
        NAME_TO_KEYWORD_TYPE_MAP = map;
        map.put("public", JavaTokenType.PUBLIC_KEYWORD);
        map.put("protected", JavaTokenType.PROTECTED_KEYWORD);
        map.put("private", JavaTokenType.PRIVATE_KEYWORD);
        map.put("static", JavaTokenType.STATIC_KEYWORD);
        map.put("abstract", JavaTokenType.ABSTRACT_KEYWORD);
        map.put("final", JavaTokenType.FINAL_KEYWORD);
        map.put("native", JavaTokenType.NATIVE_KEYWORD);
        map.put("synchronized", JavaTokenType.SYNCHRONIZED_KEYWORD);
        map.put("strictfp", JavaTokenType.STRICTFP_KEYWORD);
        map.put("transient", JavaTokenType.TRANSIENT_KEYWORD);
        map.put("volatile", JavaTokenType.VOLATILE_KEYWORD);
        map.put("default", JavaTokenType.DEFAULT_KEYWORD);
        map.put("open", JavaTokenType.OPEN_KEYWORD);
        map.put("transitive", JavaTokenType.TRANSITIVE_KEYWORD);
        map.put("sealed", JavaTokenType.SEALED_KEYWORD);
        map.put("non-sealed", JavaTokenType.NON_SEALED_KEYWORD);
        KEYWORD_TYPE_TO_NAME_MAP = new HashMap();
        for (String str : map.keySet()) {
            KEYWORD_TYPE_TO_NAME_MAP.put(NAME_TO_KEYWORD_TYPE_MAP.get(str), str);
        }
    }

    public PsiModifierListImpl(PsiModifierListStub psiModifierListStub) {
        super(psiModifierListStub, JavaStubElementTypes.MODIFIER_LIST);
    }

    public PsiModifierListImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        ModifierCache modifierCacheCalcModifiers = this.myModifierCache;
        if (modifierCacheCalcModifiers == null || !modifierCacheCalcModifiers.isUpToDate()) {
            modifierCacheCalcModifiers = calcModifiers();
            this.myModifierCache = modifierCacheCalcModifiers;
        }
        return modifierCacheCalcModifiers.modifiers.contains(str);
    }

    private ModifierCache calcModifiers() {
        Set<String> setCalcExplicitModifiers = calcExplicitModifiers();
        setCalcExplicitModifiers.addAll(calcImplicitModifiers(setCalcExplicitModifiers));
        if (!setCalcExplicitModifiers.contains("public") && !setCalcExplicitModifiers.contains("protected") && !setCalcExplicitModifiers.contains("private")) {
            setCalcExplicitModifiers.add(PsiModifier.PACKAGE_LOCAL);
        }
        PsiFile containingFile = getContainingFile();
        return new ModifierCache(containingFile, PsiAugmentProvider.transformModifierProperties(this, containingFile.getProject(), setCalcExplicitModifiers));
    }

    private Set<String> calcExplicitModifiers() {
        HashSet hashSet = new HashSet();
        PsiModifierListStub psiModifierListStub = (PsiModifierListStub) getGreenStub();
        int i = 0;
        if (psiModifierListStub != null) {
            int modifiersMask = psiModifierListStub.getModifiersMask();
            while (i < 31) {
                int i2 = 1 << i;
                if (BitUtil.isSet(modifiersMask, i2)) {
                    ContainerUtil.addIfNotNull(hashSet, ModifierFlags.MODIFIER_FLAG_TO_NAME_MAP.get(i2));
                }
                i++;
            }
        } else {
            ASTNode[] children = getNode().getChildren(null);
            int length = children.length;
            while (i < length) {
                ContainerUtil.addIfNotNull(hashSet, KEYWORD_TYPE_TO_NAME_MAP.get(children[i].getElementType()));
                i++;
            }
        }
        return hashSet;
    }

    private Set<String> calcImplicitModifiers(Set<String> set) {
        List<PsiMethod> listAsList;
        HashSet hashSet = new HashSet();
        PsiElement parent = getLightParent();
        if (parent instanceof PsiClass) {
            PsiElement context = parent.getContext();
            if ((context instanceof PsiClass) && ((PsiClass) context).isInterface()) {
                Collections.addAll(hashSet, "public", "static");
            }
            PsiClass psiClass = (PsiClass) parent;
            if (psiClass.isInterface()) {
                hashSet.add("abstract");
                if (!(context instanceof PsiFile)) {
                    hashSet.add("static");
                }
            }
            if (psiClass.isRecord()) {
                if (!(context instanceof PsiFile)) {
                    hashSet.add("static");
                }
                hashSet.add("final");
            }
            if (psiClass.isEnum()) {
                if (!(context instanceof PsiFile)) {
                    hashSet.add("static");
                }
                boolean z = parent instanceof PsiExtensibleClass;
                if (ContainerUtil.find(z ? ((PsiExtensibleClass) parent).getOwnFields() : Arrays.asList(psiClass.getFields()), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiModifierListImpl$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return PsiModifierListImpl.lambda$calcImplicitModifiers$0((PsiField) obj);
                    }
                }) != null) {
                    hashSet.add("sealed");
                } else {
                    hashSet.add("final");
                }
                if (z) {
                    listAsList = ((PsiExtensibleClass) parent).getOwnMethods();
                } else {
                    listAsList = Arrays.asList(psiClass.getMethods());
                }
                Iterator<PsiMethod> it = listAsList.iterator();
                while (it.hasNext()) {
                    if (it.next().hasModifierProperty("abstract")) {
                        hashSet.add("abstract");
                        break;
                    }
                }
            }
        } else if (parent instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) parent;
            PsiClass containingClass = psiMethod.getContainingClass();
            if (containingClass != null && containingClass.isInterface()) {
                if (!set.contains("private")) {
                    hashSet.add("public");
                    if (!set.contains("default") && !set.contains("static")) {
                        hashSet.add("abstract");
                    }
                }
            } else if (containingClass != null && containingClass.isEnum() && psiMethod.isConstructor()) {
                hashSet.add("private");
            }
        } else if (parent instanceof PsiRecordComponent) {
            hashSet.add("final");
        } else if (parent instanceof PsiField) {
            if (parent instanceof PsiEnumConstant) {
                Collections.addAll(hashSet, "public", "static", "final");
            } else {
                PsiClass containingClass2 = ((PsiField) parent).getContainingClass();
                if (containingClass2 != null && containingClass2.isInterface()) {
                    Collections.addAll(hashSet, "public", "static", "final");
                }
            }
        } else if (((parent instanceof PsiParameter) && (parent.getLightParent() instanceof PsiCatchSection) && (((PsiParameter) parent).getType() instanceof PsiDisjunctionType)) || (parent instanceof PsiResourceVariable)) {
            Collections.addAll(hashSet, "final");
        } else if ((parent instanceof PsiPatternVariable) && !PsiUtil.isLanguageLevel16OrHigher(parent)) {
            Collections.addAll(hashSet, "final");
        }
        return hashSet;
    }

    static /* synthetic */ boolean lambda$calcImplicitModifiers$0(PsiField psiField) {
        return (psiField instanceof PsiEnumConstant) && ((PsiEnumConstant) psiField).getInitializingClass() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasExplicitModifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        PsiModifierListStub psiModifierListStub = (PsiModifierListStub) getGreenStub();
        if (psiModifierListStub != null) {
            return BitUtil.isSet(psiModifierListStub.getModifiersMask(), ModifierFlags.NAME_TO_MODIFIER_FLAG_MAP.getInt(str));
        }
        CompositeElement compositeElement = (CompositeElement) getNode();
        IElementType iElementType = NAME_TO_KEYWORD_TYPE_MAP.get(str);
        return (iElementType == null || compositeElement.findChildByType(iElementType) == null) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void setModifierProperty(String str, boolean z) throws IncorrectOperationException {
        ASTNode node;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        checkSetModifierProperty(str, z);
        PsiElement parent = getLightParent();
        PsiElement lightParent = parent != null ? parent.getLightParent() : null;
        IElementType iElementType = NAME_TO_KEYWORD_TYPE_MAP.get(str);
        CompositeElement compositeElement = (CompositeElement) getNode();
        boolean z2 = parent instanceof PsiMethod;
        if (z2 && (node = ((PsiMethod) parent).getParameterList().getNode()) != null) {
            CodeEditUtil.markToReformat(node, true);
        }
        if (!z) {
            if (iElementType == null) {
                throw new IncorrectOperationException("Cannot reset package-private modifier.");
            }
            ASTNode aSTNodeFindChildByType = compositeElement.findChildByType(iElementType);
            if (aSTNodeFindChildByType != null) {
                SourceTreeToPsiMap.treeToPsiNotNull(aSTNodeFindChildByType).delete();
                return;
            }
            return;
        }
        if (iElementType == JavaTokenType.PUBLIC_KEYWORD || iElementType == JavaTokenType.PRIVATE_KEYWORD || iElementType == JavaTokenType.PROTECTED_KEYWORD || iElementType == null) {
            if (iElementType != JavaTokenType.PUBLIC_KEYWORD) {
                setModifierProperty("public", false);
            }
            if (iElementType != JavaTokenType.PRIVATE_KEYWORD) {
                setModifierProperty("private", false);
            }
            if (iElementType != JavaTokenType.PROTECTED_KEYWORD) {
                setModifierProperty("protected", false);
            }
            if (iElementType == null) {
                return;
            }
        }
        if (iElementType == JavaTokenType.SEALED_KEYWORD || iElementType == JavaTokenType.FINAL_KEYWORD || iElementType == JavaTokenType.NON_SEALED_KEYWORD) {
            if (iElementType != JavaTokenType.SEALED_KEYWORD) {
                setModifierProperty("sealed", false);
            }
            if (iElementType != JavaTokenType.NON_SEALED_KEYWORD) {
                setModifierProperty("non-sealed", false);
            }
            if (iElementType != JavaTokenType.FINAL_KEYWORD) {
                setModifierProperty("final", false);
            }
        }
        if ((parent instanceof PsiField) && (lightParent instanceof PsiClass) && ((PsiClass) lightParent).isInterface()) {
            if (iElementType == JavaTokenType.PUBLIC_KEYWORD || iElementType == JavaTokenType.STATIC_KEYWORD || iElementType == JavaTokenType.FINAL_KEYWORD) {
                return;
            }
        } else if (z2 && (lightParent instanceof PsiClass) && ((PsiClass) lightParent).isInterface()) {
            if (iElementType == JavaTokenType.PUBLIC_KEYWORD || iElementType == JavaTokenType.ABSTRACT_KEYWORD) {
                return;
            }
        } else if ((parent instanceof PsiClass) && (lightParent instanceof PsiClass) && ((PsiClass) lightParent).isInterface()) {
            if (iElementType == JavaTokenType.PUBLIC_KEYWORD) {
                return;
            }
        } else if ((parent instanceof PsiAnnotationMethod) && (lightParent instanceof PsiClass) && ((PsiClass) lightParent).isAnnotationType() && (iElementType == JavaTokenType.PUBLIC_KEYWORD || iElementType == JavaTokenType.ABSTRACT_KEYWORD)) {
            return;
        }
        if (compositeElement.findChildByType(iElementType) == null) {
            LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(iElementType, str, null, getManager());
            compositeElement.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, null, null);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void checkSetModifierProperty(String str, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        CheckUtil.checkWritable(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) ArrayUtil.mergeArrayAndCollection((PsiAnnotation[]) getStubOrPsiChildren(JavaStubElementTypes.ANNOTATION, PsiAnnotation.ARRAY_FACTORY), PsiAugmentProvider.collectAugments(this, PsiAnnotation.class, null), PsiAnnotation.ARRAY_FACTORY);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        final PsiAnnotation.TargetType[] targetsForLocation = AnnotationTargetUtil.getTargetsForLocation(this);
        PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) ContainerUtil.findAll(getAnnotations(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiModifierListImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiModifierListImpl.lambda$getApplicableAnnotations$1(targetsForLocation, (PsiAnnotation) obj);
            }
        }).toArray(PsiAnnotation.EMPTY_ARRAY);
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiAnnotationArr;
    }

    static /* synthetic */ boolean lambda$getApplicableAnnotations$1(PsiAnnotation.TargetType[] targetTypeArr, PsiAnnotation psiAnnotation) {
        PsiAnnotation.TargetType targetTypeFindAnnotationTarget = AnnotationTargetUtil.findAnnotationTarget(psiAnnotation, targetTypeArr);
        return (targetTypeFindAnnotationTarget == null || targetTypeFindAnnotationTarget == PsiAnnotation.TargetType.UNKNOWN) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return PsiImplUtil.findAnnotation(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        PsiAnnotation psiAnnotation = (PsiAnnotation) addAfter(JavaPsiFacade.getElementFactory(getProject()).createAnnotationFromText(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + str, this), null);
        if (psiAnnotation == null) {
            $$$reportNull$$$0(8);
        }
        return psiAnnotation;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModifierList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiModifierList:" + getText();
    }

    private static class ModifierCache {
        static final Interner<List<String>> ourInterner = Interner.createWeakInterner();
        final PsiFile file;
        final long modCount;
        final List<String> modifiers;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "file";
            } else {
                objArr[0] = "modifiers";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiModifierListImpl$ModifierCache";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        ModifierCache(PsiFile psiFile, Set<String> set) {
            if (psiFile == null) {
                $$$reportNull$$$0(0);
            }
            if (set == null) {
                $$$reportNull$$$0(1);
            }
            this.file = psiFile;
            ArrayList arrayList = new ArrayList(set);
            Collections.sort(arrayList);
            this.modifiers = ourInterner.intern(arrayList);
            this.modCount = getModCount();
        }

        private long getModCount() {
            return this.file.getManager().getModificationTracker().getModificationCount() + this.file.getModificationStamp();
        }

        boolean isUpToDate() {
            return getModCount() == this.modCount;
        }
    }
}
