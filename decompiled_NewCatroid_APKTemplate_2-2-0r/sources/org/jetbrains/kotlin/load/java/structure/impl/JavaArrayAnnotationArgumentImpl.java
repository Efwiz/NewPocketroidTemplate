package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerMemberValue;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: annotationArgumentsImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaArrayAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationArgumentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayAnnotationArgument;", "psiValueSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiArrayInitializerMemberValue;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;Lorg/jetbrains/kotlin/name/Name;)V", "getElements", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaArrayAnnotationArgumentImpl extends JavaAnnotationArgumentImpl implements JavaArrayAnnotationArgument {
    private final JavaElementPsiSource<PsiArrayInitializerMemberValue> psiValueSource;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaArrayAnnotationArgumentImpl(JavaElementPsiSource<PsiArrayInitializerMemberValue> psiValueSource, Name name2) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(psiValueSource, "psiValueSource");
        this.psiValueSource = psiValueSource;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument
    public List<JavaAnnotationArgument> getElements() {
        PsiAnnotationMemberValue[] initializers = ((PsiArrayInitializerMemberValue) this.psiValueSource.getPsi()).getInitializers();
        Intrinsics.checkNotNullExpressionValue(initializers, "getInitializers(...)");
        PsiAnnotationMemberValue[] psiAnnotationMemberValueArr = initializers;
        ArrayList arrayList = new ArrayList(psiAnnotationMemberValueArr.length);
        for (PsiAnnotationMemberValue psiAnnotationMemberValue : psiAnnotationMemberValueArr) {
            JavaAnnotationArgumentImpl.Companion companion = JavaAnnotationArgumentImpl.INSTANCE;
            Intrinsics.checkNotNull(psiAnnotationMemberValue);
            arrayList.add(companion.create(psiAnnotationMemberValue, null, this.psiValueSource.getFactory()));
        }
        return arrayList;
    }
}
