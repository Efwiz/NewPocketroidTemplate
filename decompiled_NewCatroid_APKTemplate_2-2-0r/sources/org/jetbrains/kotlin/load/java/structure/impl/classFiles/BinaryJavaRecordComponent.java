package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Other.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0012\u0004\u0012\u00020\u00120\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001dR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaRecordComponent;", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaModifierListOwner;", "name", "Lorg/jetbrains/kotlin/name/Name;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "isVararg", "", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/load/java/structure/JavaType;Z)V", "access", "", "getAccess", "()I", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "isFromSource", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaRecordComponent implements JavaRecordComponent, BinaryJavaModifierListOwner {
    private final JavaClass containingClass;
    private final boolean isVararg;
    private final Name name;
    private final JavaType type;

    @Override // org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaModifierListOwner
    public int getAccess() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    public BinaryJavaRecordComponent(Name name2, JavaClass containingClass, JavaType type, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.containingClass = containingClass;
        this.type = type;
        this.isVararg = z;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    public JavaType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return MapsKt.emptyMap();
    }
}
