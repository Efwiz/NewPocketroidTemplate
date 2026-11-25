package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.bytebuddy.description.type.TypeDescription;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.AtomicNullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.one.util.streamex.StreamEx;

/* JADX INFO: loaded from: classes3.dex */
public class ClsTypeElementImpl extends ClsElementImpl implements PsiTypeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final TypeAnnotationContainer myAnnotations;
    private final NotNullLazyValue<PsiType> myCachedType;
    private final NullableLazyValue<ClsElementImpl> myChild;
    private final PsiElement myParent;
    private final String myTypeText;
    private final char myVariance;

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 5 && i != 24) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 5 && i != 24) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "typeText";
                break;
            case 2:
                objArr[0] = "typeInfo";
                break;
            case 4:
                objArr[0] = "annotations";
                break;
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 24:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeElementImpl";
                break;
            case 6:
                objArr[0] = "buffer";
                break;
            case 7:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 21:
                objArr[0] = "visitor";
                break;
            case 22:
            case 23:
                objArr[0] = "qualifiedName";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 5) {
            objArr[1] = "getChildren";
        } else if (i != 24) {
            switch (i) {
                case 8:
                    objArr[1] = "getType";
                    break;
                case 9:
                    objArr[1] = "createArrayType";
                    break;
                case 10:
                    objArr[1] = "calculateType";
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    objArr[1] = "calculateBaseType";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeElementImpl";
                    break;
            }
        } else {
            objArr[1] = "getApplicableAnnotations";
        }
        switch (i) {
            case 5:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 24:
                break;
            case 6:
                objArr[2] = "appendMirrorText";
                break;
            case 7:
                objArr[2] = "setMirror";
                break;
            case 21:
                objArr[2] = "accept";
                break;
            case 22:
                objArr[2] = "findAnnotation";
                break;
            case 23:
                objArr[2] = "addAnnotation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 24) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiJavaCodeReferenceElement getInnermostComponentReferenceElement() {
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClsTypeElementImpl(PsiElement psiElement, String str, char c) {
        this(psiElement, str, c, TypeAnnotationContainer.EMPTY);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    ClsTypeElementImpl(PsiElement psiElement, TypeInfo typeInfo) {
        this(psiElement, (String) Objects.requireNonNull(TypeInfo.createTypeText(typeInfo)), (char) 0, typeInfo.getTypeAnnotations());
        if (typeInfo == null) {
            $$$reportNull$$$0(2);
        }
    }

    ClsTypeElementImpl(PsiElement psiElement, String str, char c, TypeAnnotationContainer typeAnnotationContainer) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationContainer == null) {
            $$$reportNull$$$0(4);
        }
        this.myParent = psiElement;
        this.myTypeText = TypeInfo.internFrequentType(str);
        this.myVariance = c;
        this.myAnnotations = typeAnnotationContainer;
        this.myChild = new AtomicNullableLazyValue<ClsElementImpl>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeElementImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            public ClsElementImpl compute() {
                return ClsTypeElementImpl.this.calculateChild();
            }
        };
        this.myCachedType = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeElementImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6602x2f95581();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ClsElementImpl value = this.myChild.getValue();
        PsiElement[] psiElementArr = value != null ? new PsiElement[]{value} : PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return decorateTypeText(PsiNameHelper.getShortClassName(this.myTypeText));
    }

    private String decorateTypeText(String str) {
        char c = this.myVariance;
        if (c == 0) {
            return str;
        }
        if (c == '-') {
            return PsiWildcardType.SUPER_PREFIX + str;
        }
        if (c == '*') {
            return TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        if (c != '+') {
            return null;
        }
        return PsiWildcardType.EXTENDS_PREFIX + str;
    }

    public String getCanonicalText() {
        return decorateTypeText(this.myTypeText);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(6);
        }
        sb.append(getType().getCanonicalText(true));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(7);
        }
        setMirrorCheckingType(treeElement, JavaElementType.TYPE);
        ClsElementImpl value = this.myChild.getValue();
        if (value != null) {
            value.setMirror(treeElement.getFirstChildNode());
        }
    }

    private boolean isArray() {
        return this.myTypeText.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
    }

    private boolean isVarArgs() {
        return this.myTypeText.endsWith("...");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement
    public PsiType getType() {
        PsiType value = this.myCachedType.getValue();
        if (value == null) {
            $$$reportNull$$$0(8);
        }
        return value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ClsElementImpl calculateChild() {
        ClsJavaCodeReferenceElementImpl clsJavaCodeReferenceElementImpl = null;
        if (PsiJavaParserFacadeImpl.getPrimitiveType(this.myTypeText) != null) {
            return null;
        }
        if (isArray()) {
            if (this.myVariance == 0) {
                return getDeepestArrayElement();
            }
            return new ClsTypeElementImpl(this, this.myTypeText, (char) 0, this.myAnnotations.forBound());
        }
        if (isVarArgs()) {
            return getDeepestArrayElement();
        }
        if (this.myVariance != '*') {
            clsJavaCodeReferenceElementImpl = new ClsJavaCodeReferenceElementImpl(this, this.myTypeText, this.myVariance == 0 ? this.myAnnotations : this.myAnnotations.forBound());
        }
        return clsJavaCodeReferenceElementImpl;
    }

    int getArrayDepth() {
        boolean zIsVarArgs = isVarArgs();
        if (!zIsVarArgs && !isArray()) {
            return 0;
        }
        int length = this.myTypeText.length() - (zIsVarArgs ? 3 : 2);
        int i = 1;
        while (length > 2 && this.myTypeText.startsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, length - 2)) {
            length -= 2;
            i++;
        }
        return i;
    }

    private ClsElementImpl getDeepestArrayElement() {
        int arrayDepth = getArrayDepth();
        int length = (this.myTypeText.length() - (arrayDepth * 2)) - (isVarArgs() ? 1 : 0);
        TypeAnnotationContainer typeAnnotationContainerForArrayElement = this.myAnnotations;
        for (int i = 0; i < arrayDepth; i++) {
            typeAnnotationContainerForArrayElement = typeAnnotationContainerForArrayElement.forArrayElement();
        }
        return new ClsTypeElementImpl(this, this.myTypeText.substring(0, length), this.myVariance, typeAnnotationContainerForArrayElement);
    }

    private PsiType createArrayType(PsiTypeElement psiTypeElement) {
        PsiArrayType psiArrayTypeCreateArrayType;
        int arrayDepth = getArrayDepth();
        List<T> list = ((StreamEx) StreamEx.iterate(this.myAnnotations, (UnaryOperator<TypeAnnotationContainer>) new UnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeElementImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeAnnotationContainer) obj).forArrayElement();
            }
        }).limit(arrayDepth)).toList();
        PsiType type = psiTypeElement.getType();
        for (int i = arrayDepth - 1; i >= 0; i--) {
            if (i == 0 && isVarArgs()) {
                psiArrayTypeCreateArrayType = new PsiEllipsisType(type);
            } else {
                psiArrayTypeCreateArrayType = type.createArrayType();
            }
            type = psiArrayTypeCreateArrayType.annotate(((TypeAnnotationContainer) list.get(i)).getProvider(this));
        }
        if (type == null) {
            $$$reportNull$$$0(9);
        }
        return type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: calculateType, reason: merged with bridge method [inline-methods] */
    public PsiType m6602x2f95581() {
        PsiType psiTypeAnnotate = calculateBaseType().annotate(this.myAnnotations.getProvider(this));
        if (psiTypeAnnotate == null) {
            $$$reportNull$$$0(10);
        }
        return psiTypeAnnotate;
    }

    private PsiType calculateBaseType() {
        PsiPrimitiveType primitiveType = PsiJavaParserFacadeImpl.getPrimitiveType(this.myTypeText);
        if (primitiveType != null) {
            if (primitiveType == null) {
                $$$reportNull$$$0(11);
            }
            return primitiveType;
        }
        ClsElementImpl value = this.myChild.getValue();
        if (value instanceof ClsTypeElementImpl) {
            if (isArray()) {
                char c = this.myVariance;
                if (c == 0) {
                    return createArrayType((PsiTypeElement) value);
                }
                if (c == '+') {
                    PsiWildcardType psiWildcardTypeCreateExtends = PsiWildcardType.createExtends(getManager(), ((PsiTypeElement) value).getType());
                    if (psiWildcardTypeCreateExtends == null) {
                        $$$reportNull$$$0(12);
                    }
                    return psiWildcardTypeCreateExtends;
                }
                if (c == '-') {
                    PsiWildcardType psiWildcardTypeCreateSuper = PsiWildcardType.createSuper(getManager(), ((PsiTypeElement) value).getType());
                    if (psiWildcardTypeCreateSuper == null) {
                        $$$reportNull$$$0(13);
                    }
                    return psiWildcardTypeCreateSuper;
                }
                $$$reportNull$$$0(14);
                return null;
            }
            return createArrayType((PsiTypeElement) value);
        }
        if (value instanceof ClsJavaCodeReferenceElementImpl) {
            PsiClassReferenceType psiClassReferenceType = new PsiClassReferenceType((PsiJavaCodeReferenceElement) value, null);
            char c2 = this.myVariance;
            if (c2 == 0) {
                return psiClassReferenceType;
            }
            if (c2 == '-') {
                PsiWildcardType psiWildcardTypeCreateSuper2 = PsiWildcardType.createSuper(getManager(), psiClassReferenceType.annotate(this.myAnnotations.forBound().getProvider(value)));
                if (psiWildcardTypeCreateSuper2 == null) {
                    $$$reportNull$$$0(17);
                }
                return psiWildcardTypeCreateSuper2;
            }
            if (c2 == '*') {
                PsiWildcardType psiWildcardTypeCreateUnbounded = PsiWildcardType.createUnbounded(getManager());
                if (psiWildcardTypeCreateUnbounded == null) {
                    $$$reportNull$$$0(18);
                }
                return psiWildcardTypeCreateUnbounded;
            }
            if (c2 == '+') {
                PsiWildcardType psiWildcardTypeCreateExtends2 = PsiWildcardType.createExtends(getManager(), psiClassReferenceType.annotate(this.myAnnotations.forBound().getProvider(value)));
                if (psiWildcardTypeCreateExtends2 == null) {
                    $$$reportNull$$$0(16);
                }
                return psiWildcardTypeCreateExtends2;
            }
            $$$reportNull$$$0(19);
            return null;
        }
        PsiWildcardType psiWildcardTypeCreateUnbounded2 = PsiWildcardType.createUnbounded(getManager());
        if (psiWildcardTypeCreateUnbounded2 == null) {
            $$$reportNull$$$0(20);
        }
        return psiWildcardTypeCreateUnbounded2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(21);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getAnnotations() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation findAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        return PsiImplUtil.findAnnotation(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getType().getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(24);
        }
        return annotations;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeElement:" + getText();
    }
}
