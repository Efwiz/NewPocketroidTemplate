package org.jetbrains.kotlin.psi.stubs.elements;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtProperty;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtStubElementType<StubT extends StubElement<?>, PsiT extends KtElementImplStub<?>> extends IStubElementType<StubT, PsiT> {
    private final ArrayFactory<PsiT> arrayFactory;
    private final Constructor<PsiT> byNodeConstructor;
    private final Constructor<PsiT> byStubConstructor;
    private final PsiT[] emptyArray;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 10 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 10 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "psiClass";
                break;
            case 2:
                objArr[0] = "stubClass";
                break;
            case 3:
                objArr[0] = "node";
                break;
            case 4:
            case 6:
            case 7:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/psi/stubs/elements/KtStubElementType";
                break;
            case 5:
            case 8:
                objArr[0] = "stub";
                break;
            case 9:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        if (i == 4) {
            objArr[1] = "createPsiFromAst";
        } else if (i == 10) {
            objArr[1] = "getArrayFactory";
        } else if (i == 6) {
            objArr[1] = "createPsi";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtStubElementType";
        } else {
            objArr[1] = "getExternalId";
        }
        switch (i) {
            case 3:
                objArr[2] = "createPsiFromAst";
                break;
            case 4:
            case 6:
            case 7:
            case 10:
                break;
            case 5:
                objArr[2] = "createPsi";
                break;
            case 8:
            case 9:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 10 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(StubT stubt, IndexSink indexSink) {
        if (stubt == null) {
            $$$reportNull$$$0(8);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(9);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStubElementType(String str, final Class<PsiT> cls, Class<?> cls2) {
        super(str, KotlinLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (cls2 == null) {
            $$$reportNull$$$0(2);
        }
        try {
            this.byNodeConstructor = cls.getConstructor(ASTNode.class);
            this.byStubConstructor = cls.getConstructor(cls2);
            this.emptyArray = (PsiT[]) ((KtElementImplStub[]) Array.newInstance((Class<?>) cls, 0));
            this.arrayFactory = new ArrayFactory() { // from class: org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
                public final Object[] create(int i) {
                    return this.f$0.m7401xbac5acaf(cls, i);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Stub element type declaration for " + cls.getSimpleName() + " is missing required constructors", e);
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-psi-stubs-elements-KtStubElementType, reason: not valid java name */
    /* synthetic */ KtElementImplStub[] m7401xbac5acaf(Class cls, int i) {
        return i == 0 ? this.emptyArray : (KtElementImplStub[]) Array.newInstance((Class<?>) cls, i);
    }

    public PsiT createPsiFromAst(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        PsiT psit = (PsiT) ReflectionUtil.createInstance(this.byNodeConstructor, aSTNode);
        if (psit == null) {
            $$$reportNull$$$0(4);
        }
        return psit;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public PsiT createPsi(StubT stubt) {
        if (stubt == null) {
            $$$reportNull$$$0(5);
        }
        PsiT psit = (PsiT) ReflectionUtil.createInstance(this.byStubConstructor, stubt);
        if (psit == null) {
            $$$reportNull$$$0(6);
        }
        return psit;
    }

    public String getExternalId() {
        String str = "kotlin." + getDebugName();
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode aSTNode) {
        PsiElement psi = aSTNode.getPsi();
        if ((psi instanceof KtClassOrObject) || (psi instanceof KtFunction)) {
            return true;
        }
        if (psi instanceof KtProperty) {
            return !((KtProperty) psi).isLocal();
        }
        return createStubDependingOnParent(aSTNode);
    }

    private static boolean createStubDependingOnParent(ASTNode aSTNode) {
        ASTNode treeParent = aSTNode.getTreeParent();
        IElementType elementType = treeParent.getElementType();
        if (elementType instanceof IStubElementType) {
            return ((IStubElementType) elementType).shouldCreateStub(treeParent);
        }
        return elementType instanceof IStubFileElementType;
    }

    public ArrayFactory<PsiT> getArrayFactory() {
        ArrayFactory<PsiT> arrayFactory = this.arrayFactory;
        if (arrayFactory == null) {
            $$$reportNull$$$0(10);
        }
        return arrayFactory;
    }
}
