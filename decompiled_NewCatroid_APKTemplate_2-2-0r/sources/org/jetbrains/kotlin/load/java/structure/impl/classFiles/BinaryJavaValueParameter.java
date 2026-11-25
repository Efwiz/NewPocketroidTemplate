package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Collection;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: Other.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0018H\u0000¢\u0006\u0002\b\"R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR)\u0010\u000e\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u000b0\u000f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaValueParameter;", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "isVararg", "", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;Z)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "isFromSource", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "updateName", "", "newName", "updateName$resolution_common_jvm", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaValueParameter implements JavaValueParameter, MapBasedJavaAnnotationOwner, MutableJavaAnnotationOwner {
    private final Collection<JavaAnnotation> annotations;

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final boolean isVararg;
    private Name name;
    private final JavaType type;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public boolean isFromSource() {
        return false;
    }

    public BinaryJavaValueParameter(JavaType type, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.isVararg = z;
        this.annotations = new SmartList();
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public JavaType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public Name getName() {
        return this.name;
    }

    public void setName(Name name2) {
        this.name = name2;
    }

    public final void updateName$resolution_common_jvm(Name newName) {
        Intrinsics.checkNotNullParameter(newName, "newName");
        getName();
        setName(newName);
    }
}
