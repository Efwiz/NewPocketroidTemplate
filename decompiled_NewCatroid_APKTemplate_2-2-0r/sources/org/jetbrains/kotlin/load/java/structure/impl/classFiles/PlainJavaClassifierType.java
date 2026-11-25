package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0011R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaClassifierType;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/JavaPlainType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "classifierComputation", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "(Lkotlin/jvm/functions/Function0;Ljava/util/List;)V", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "classifierResolverResult", "getClassifierResolverResult", "()Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "classifierResolverResult$delegate", "Lkotlin/Lazy;", "isRaw", "", "()Z", "presentableText", "getPresentableText", "getTypeArguments", "()Ljava/util/List;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaClassifierType extends JavaPlainType implements JavaClassifierType {

    /* JADX INFO: renamed from: classifierResolverResult$delegate, reason: from kotlin metadata */
    private final Lazy classifierResolverResult;
    private final List<JavaType> typeArguments;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        return this.typeArguments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlainJavaClassifierType(Function0<ClassifierResolutionContext.Result> classifierComputation, List<? extends JavaType> typeArguments) {
        Intrinsics.checkNotNullParameter(classifierComputation, "classifierComputation");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        this.typeArguments = typeArguments;
        this.classifierResolverResult = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) classifierComputation);
    }

    private final ClassifierResolutionContext.Result getClassifierResolverResult() {
        return (ClassifierResolutionContext.Result) this.classifierResolverResult.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifier getClassifier() {
        return getClassifierResolverResult().getClassifier();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        List<JavaTypeParameter> typeParameters;
        if (mo7270getTypeArguments().isEmpty()) {
            JavaClassifier classifier = getClassifierResolverResult().getClassifier();
            JavaClass javaClass = classifier instanceof JavaClass ? (JavaClass) classifier : null;
            if (javaClass != null && (typeParameters = javaClass.mo7258getTypeParameters()) != null && (!typeParameters.isEmpty())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        return getClassifierResolverResult().getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        return getClassifierQualifiedName();
    }
}
