package org.jetbrains.kotlin.com.intellij.psi;

import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmWildcardType;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiWildcardType extends PsiType.Stub implements JvmWildcardType {
    public static final String EXTENDS_PREFIX = "? extends ";
    public static final String SUPER_PREFIX = "? super ";
    private final PsiType myBound;
    private final boolean myIsExtending;
    private final PsiManager myManager;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiWildcardType.class);
    private static final Key<PsiWildcardType> UNBOUNDED_WILDCARD = new Key<>("UNBOUNDED_WILDCARD");

    /* JADX WARN: Code duplicated, block: B:23:0x003b  */
    /* JADX WARN: Code duplicated, block: B:24:0x0040  */
    /* JADX WARN: Code duplicated, block: B:8:0x0010 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 14) {
            switch (i) {
                default:
                    switch (i) {
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 14) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
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
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiWildcardType";
        } else if (i != 4) {
            switch (i) {
                case 6:
                    objArr[0] = "bound";
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 14:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiWildcardType";
                    break;
                case 13:
                    objArr[0] = "text";
                    break;
                case 15:
                    objArr[0] = "visitor";
                    break;
                default:
                    objArr[0] = "manager";
                    break;
            }
        } else {
            objArr[0] = "bound";
        }
        if (i == 2) {
            objArr[1] = "createUnbounded";
        } else if (i != 14) {
            switch (i) {
                case 7:
                    objArr[1] = "getPresentableText";
                    break;
                case 8:
                    objArr[1] = "getCanonicalText";
                    break;
                case 9:
                    objArr[1] = "getInternalCanonicalText";
                    break;
                case 10:
                case 11:
                    objArr[1] = "getResolveScope";
                    break;
                case 12:
                    objArr[1] = "getSuperTypes";
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                            objArr[1] = "getExtendsBound";
                            break;
                        case 18:
                            objArr[1] = "getSuperBound";
                            break;
                        case 19:
                            objArr[1] = "upperBound";
                            break;
                        case 20:
                            objArr[1] = "lowerBound";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiWildcardType";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getManager";
        }
        switch (i) {
            case 1:
                objArr[2] = "createUnbounded";
                break;
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                break;
            case 3:
            case 4:
                objArr[2] = "createExtends";
                break;
            case 5:
            case 6:
                objArr[2] = "createSuper";
                break;
            case 13:
                objArr[2] = "equalsToText";
                break;
            case 15:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 14) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    switch (i) {
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PsiWildcardType(PsiManager psiManager, boolean z, PsiType psiType) {
        super(TypeAnnotationProvider.EMPTY);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = psiManager;
        this.myIsExtending = z;
        this.myBound = psiType;
    }

    public static PsiWildcardType createUnbounded(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        Key<PsiWildcardType> key = UNBOUNDED_WILDCARD;
        PsiWildcardType psiWildcardType = (PsiWildcardType) psiManager.getUserData(key);
        if (psiWildcardType == null) {
            psiWildcardType = (PsiWildcardType) psiManager.putUserDataIfAbsent(key, new PsiWildcardType(psiManager, false, null));
        }
        if (psiWildcardType == null) {
            $$$reportNull$$$0(2);
        }
        return psiWildcardType;
    }

    public static PsiWildcardType createExtends(PsiManager psiManager, PsiType psiType) {
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        LOG.assertTrue(((psiType instanceof PsiWildcardType) || psiType == PsiType.NULL) ? false : true, psiType);
        return new PsiWildcardType(psiManager, true, psiType);
    }

    public static PsiWildcardType createSuper(PsiManager psiManager, PsiType psiType) {
        if (psiManager == null) {
            $$$reportNull$$$0(5);
        }
        if (psiType == null) {
            $$$reportNull$$$0(6);
        }
        LOG.assertTrue(((psiType instanceof PsiWildcardType) || psiType == PsiType.NULL) ? false : true, psiType);
        return new PsiWildcardType(psiManager, false, psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        PsiType psiType = this.myBound;
        String text = getText(false, z, psiType == null ? null : psiType.getPresentableText(z));
        if (text == null) {
            $$$reportNull$$$0(7);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        PsiType psiType = this.myBound;
        String text = getText(true, z, psiType == null ? null : psiType.getCanonicalText(z));
        if (text == null) {
            $$$reportNull$$$0(8);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        PsiType psiType = this.myBound;
        String text = getText(true, true, psiType == null ? null : psiType.getInternalCanonicalText());
        if (text == null) {
            $$$reportNull$$$0(9);
        }
        return text;
    }

    private String getText(boolean z, boolean z2, String str) {
        PsiAnnotation[] annotations = z2 ? getAnnotations() : PsiAnnotation.EMPTY_ARRAY;
        if (annotations.length == 0 && str == null) {
            return TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        StringBuilder sb = new StringBuilder();
        if (z2) {
            PsiNameHelper.appendAnnotations(sb, annotations, z);
        }
        if (str == null) {
            sb.append(MangleConstant.Q_MARK);
        } else {
            sb.append(this.myIsExtending ? EXTENDS_PREFIX : SUPER_PREFIX);
            sb.append(str);
        }
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope;
        PsiType psiType = this.myBound;
        if (psiType != null && (resolveScope = psiType.getResolveScope()) != null) {
            if (resolveScope == null) {
                $$$reportNull$$$0(10);
            }
            return resolveScope;
        }
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(this.myManager.getProject());
        if (globalSearchScopeAllScope == null) {
            $$$reportNull$$$0(11);
        }
        return globalSearchScopeAllScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        return new PsiType[]{getExtendsBound()};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (this.myBound == null) {
            return TypeDescription.Generic.OfWildcardType.SYMBOL.equals(str);
        }
        if (this.myIsExtending) {
            return str.startsWith(EXTENDS_PREFIX) && this.myBound.equalsToText(str.substring(EXTENDS_PREFIX.length()));
        }
        return str.startsWith(SUPER_PREFIX) && this.myBound.equalsToText(str.substring(SUPER_PREFIX.length()));
    }

    public PsiManager getManager() {
        PsiManager psiManager = this.myManager;
        if (psiManager == null) {
            $$$reportNull$$$0(14);
        }
        return psiManager;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PsiWildcardType)) {
            return false;
        }
        PsiWildcardType psiWildcardType = (PsiWildcardType) obj;
        PsiType psiType = this.myBound;
        if (psiType == null && psiWildcardType.myBound != null) {
            return psiWildcardType.isExtends() && psiWildcardType.myBound.equalsToText(CommonClassNames.JAVA_LANG_OBJECT);
        }
        if (psiType == null || psiWildcardType.myBound != null) {
            return this.myIsExtending == psiWildcardType.myIsExtending && Comparing.equal(psiType, psiWildcardType.myBound);
        }
        return isExtends() && this.myBound.equalsToText(CommonClassNames.JAVA_LANG_OBJECT);
    }

    public int hashCode() {
        boolean z = this.myIsExtending;
        PsiType psiType = this.myBound;
        return (z ? 1 : 0) + (psiType != null ? psiType.hashCode() : 0);
    }

    public PsiType getBound() {
        return this.myBound;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(15);
        }
        return psiTypeVisitor.visitWildcardType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        PsiType psiType = this.myBound;
        return psiType == null || psiType.isValid();
    }

    public boolean isExtends() {
        return this.myBound != null && this.myIsExtending;
    }

    public boolean isSuper() {
        return (this.myBound == null || this.myIsExtending) ? false : true;
    }

    public boolean isBounded() {
        return this.myBound != null;
    }

    public PsiType getExtendsBound() {
        PsiType psiType = this.myBound;
        if (psiType != null && this.myIsExtending) {
            if (psiType == null) {
                $$$reportNull$$$0(17);
            }
            return psiType;
        }
        PsiClassType javaLangObject = getJavaLangObject(this.myManager, getResolveScope());
        if (javaLangObject == null) {
            $$$reportNull$$$0(16);
        }
        return javaLangObject;
    }

    public PsiType getSuperBound() {
        PsiType psiType = this.myBound;
        if (psiType == null || this.myIsExtending) {
            psiType = NULL;
        }
        if (psiType == null) {
            $$$reportNull$$$0(18);
        }
        return psiType;
    }

    public JvmType upperBound() {
        PsiType extendsBound = getExtendsBound();
        if (extendsBound == null) {
            $$$reportNull$$$0(19);
        }
        return extendsBound;
    }

    public JvmType lowerBound() {
        PsiType superBound = getSuperBound();
        if (superBound == null) {
            $$$reportNull$$$0(20);
        }
        return superBound;
    }
}
