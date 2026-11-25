package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaPackageAccessibilityStatementElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiPackageAccessibilityStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPackageAccessibilityStatementStubImpl extends StubBase<PsiPackageAccessibilityStatement> implements PsiPackageAccessibilityStatementStub {
    private final String myPackageName;
    private final List<String> myTargets;

    public PsiPackageAccessibilityStatementStubImpl(StubElement stubElement, IStubElementType iStubElementType, String str, List<String> list) {
        super(stubElement, iStubElementType);
        this.myPackageName = (String) ObjectUtils.notNull(str, "");
        this.myTargets = (list == null || list.isEmpty()) ? Collections.emptyList() : Collections.unmodifiableList(list);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiPackageAccessibilityStatementStub
    public String getPackageName() {
        return this.myPackageName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiPackageAccessibilityStatementStub
    public List<String> getTargets() {
        return this.myTargets;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiPackageAccessibilityStatementStub[");
        sb.append(JavaPackageAccessibilityStatementElementType.typeToRole(getStubType())).append("]:").append(this.myPackageName);
        Iterator<String> it = this.myTargets.iterator();
        while (it.hasNext()) {
            sb.append(':').append(it.next());
        }
        return sb.toString();
    }
}
