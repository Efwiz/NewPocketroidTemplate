package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: Other.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\nX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaTypeParameter;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "Lorg/jetbrains/kotlin/load/java/structure/ListBasedJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "name", "Lorg/jetbrains/kotlin/name/Name;", "upperBounds", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "hasImplicitObjectClassBound", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/util/Collection;Z)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "getHasImplicitObjectClassBound", "()Z", "isDeprecatedInJavaDoc", "isFromSource", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getUpperBounds", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaTypeParameter implements JavaTypeParameter, ListBasedJavaAnnotationOwner, MutableJavaAnnotationOwner {
    private final Collection<JavaAnnotation> annotations;
    private final boolean hasImplicitObjectClassBound;
    private final boolean isDeprecatedInJavaDoc;
    private final Name name;
    private final Collection<JavaClassifierType> upperBounds;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BinaryJavaTypeParameter(Name name2, Collection<? extends JavaClassifierType> upperBounds, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(upperBounds, "upperBounds");
        this.name = name2;
        this.upperBounds = upperBounds;
        this.hasImplicitObjectClassBound = z;
        this.annotations = new SmartList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameter
    public Collection<JavaClassifierType> getUpperBounds() {
        return this.upperBounds;
    }

    public final boolean getHasImplicitObjectClassBound() {
        return this.hasImplicitObjectClassBound;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc, reason: from getter */
    public boolean getIsDeprecatedInJavaDoc() {
        return this.isDeprecatedInJavaDoc;
    }
}
