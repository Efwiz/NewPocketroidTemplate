package org.jetbrains.kotlin.load.java.structure.impl;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource;

/* JADX INFO: compiled from: JavaClassifierTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 %2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0002%&B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u001f\u001a\u00020\u0015H\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001b2\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u0015H\u0002R\u001a\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000eR\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierTypeImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaTypeImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "psiClassTypeSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;)V", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "isRaw", "", "()Z", "presentableText", "getPresentableText", "resolutionResult", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierTypeImpl$ResolutionResult;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "getSubstitutor", "()Lcom/intellij/psi/PsiSubstitutor;", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "computeResolveResult", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "owner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "resolve", "Companion", "ResolutionResult", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassifierTypeImpl extends JavaTypeImpl<PsiClassType> implements JavaClassifierType {
    private static final AtomicReferenceFieldUpdater<JavaClassifierTypeImpl, ResolutionResult> resolutionResultAtomicFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(JavaClassifierTypeImpl.class, ResolutionResult.class, "resolutionResult");
    private volatile ResolutionResult resolutionResult;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaClassifierTypeImpl(JavaElementTypeSource<PsiClassType> psiClassTypeSource) {
        super(psiClassTypeSource);
        Intrinsics.checkNotNullParameter(psiClassTypeSource, "psiClassTypeSource");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifierImpl<?> getClassifier() {
        return resolve().getClassifier();
    }

    public final PsiSubstitutor getSubstitutor() {
        return resolve().getSubstitutor();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        String canonicalText = getPsi().getCanonicalText();
        Intrinsics.checkNotNullExpressionValue(canonicalText, "getCanonicalText(...)");
        return ClassNamesUtilKt.convertCanonicalNameToQName(canonicalText);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        String presentableText = getPsi().getPresentableText();
        Intrinsics.checkNotNullExpressionValue(presentableText, "getPresentableText(...)");
        return presentableText;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        return resolve().getIsRaw();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        JavaClassifierImpl<?> classifier = getClassifier();
        JavaClassImpl javaClassImpl = classifier instanceof JavaClassImpl ? (JavaClassImpl) classifier : null;
        if (javaClassImpl == null) {
            return CollectionsKt.emptyList();
        }
        PsiElement psi = javaClassImpl.getPsi();
        Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
        List<PsiTypeParameter> typeParameters = getTypeParameters((PsiClass) psi);
        PsiSubstitutor substitutor = getSubstitutor();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<PsiTypeParameter> it = typeParameters.iterator();
        while (it.hasNext()) {
            PsiType psiTypeSubstitute = substitutor.substitute(it.next());
            arrayList.add(psiTypeSubstitute != null ? JavaTypeImpl.create((JavaElementTypeSource<? extends PsiType>) createTypeSource(psiTypeSubstitute)) : null);
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: JavaClassifierTypeImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u000e\u001a\u00020\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierTypeImpl$ResolutionResult;", "", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;", "substitutor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "isRaw", "", "(Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;Lcom/intellij/psi/PsiSubstitutor;Z)V", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;", "()Z", "getSubstitutor", "()Lcom/intellij/psi/PsiSubstitutor;", "isValid", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ResolutionResult {
        private final JavaClassifierImpl<?> classifier;
        private final boolean isRaw;
        private final PsiSubstitutor substitutor;

        public ResolutionResult(JavaClassifierImpl<?> javaClassifierImpl, PsiSubstitutor substitutor, boolean z) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            this.classifier = javaClassifierImpl;
            this.substitutor = substitutor;
            this.isRaw = z;
        }

        public final JavaClassifierImpl<?> getClassifier() {
            return this.classifier;
        }

        public final PsiSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        /* JADX INFO: renamed from: isRaw, reason: from getter */
        public final boolean getIsRaw() {
            return this.isRaw;
        }

        public final boolean isValid() {
            return this.substitutor.isValid();
        }
    }

    private final ResolutionResult resolve() {
        ResolutionResult resolutionResult;
        ResolutionResult resolutionResultComputeResolveResult;
        do {
            resolutionResult = this.resolutionResult;
            if (resolutionResult != null && resolutionResult.isValid()) {
                return resolutionResult;
            }
            resolutionResultComputeResolveResult = computeResolveResult();
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(resolutionResultAtomicFieldUpdater, this, resolutionResult, resolutionResultComputeResolveResult));
        return resolutionResultComputeResolveResult;
    }

    private final ResolutionResult computeResolveResult() {
        PsiClassType.ClassResolveResult classResolveResultResolveGenerics = getPsi().resolveGenerics();
        Intrinsics.checkNotNullExpressionValue(classResolveResultResolveGenerics, "resolveGenerics(...)");
        PsiClass element = classResolveResultResolveGenerics.getElement();
        PsiSubstitutor substitutor = classResolveResultResolveGenerics.getSubstitutor();
        Intrinsics.checkNotNullExpressionValue(substitutor, "getSubstitutor(...)");
        return new ResolutionResult(element != null ? JavaClassifierImpl.create(element, getSourceFactory()) : null, substitutor, PsiClassType.isRaw(classResolveResultResolveGenerics));
    }

    private final List<PsiTypeParameter> getTypeParameters(PsiClass owner) {
        List<PsiTypeParameter> list = null;
        for (PsiClass containingClass = owner; containingClass != null; containingClass = containingClass.mo6092getContainingClass()) {
            PsiTypeParameter[] typeParameters = containingClass.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            if (!(typeParameters.length == 0) && (list == null || (list = CollectionsKt.plus((Collection) list, (Object[]) typeParameters)) == null)) {
                list = ArraysKt.toList(typeParameters);
            }
            if (containingClass.hasModifierProperty("static")) {
                break;
            }
        }
        return list == null ? CollectionsKt.emptyList() : list;
    }
}
