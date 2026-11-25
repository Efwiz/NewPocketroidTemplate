package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiAnnotationStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeParameterStubImpl extends StubBase<PsiTypeParameter> implements PsiTypeParameterStub {
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiTypeParameterStubImpl", "getAnnotations"));
    }

    public PsiTypeParameterStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.TYPE_PARAMETER);
        this.myName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiTypeParameter[" + this.myName + ']';
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterStub
    public List<PsiAnnotationStub> getAnnotations() {
        List<PsiAnnotationStub> listMapNotNull = ContainerUtil.mapNotNull(getChildrenStubs(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiTypeParameterStubImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return PsiTypeParameterStubImpl.lambda$getAnnotations$0((StubElement) obj);
            }
        });
        if (listMapNotNull == null) {
            $$$reportNull$$$0(0);
        }
        return listMapNotNull;
    }

    static /* synthetic */ PsiAnnotationStub lambda$getAnnotations$0(StubElement stubElement) {
        if (stubElement instanceof PsiAnnotationStub) {
            return (PsiAnnotationStub) stubElement;
        }
        return null;
    }
}
