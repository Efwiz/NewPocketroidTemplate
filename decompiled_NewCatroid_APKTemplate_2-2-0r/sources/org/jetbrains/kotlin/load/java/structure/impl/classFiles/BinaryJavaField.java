package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Collection;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: Other.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B7\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R)\u0010\u0018\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0004\u0012\u00020\u00150\u00198VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010#R\u0014\u0010&\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010#R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaField;", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "name", "Lorg/jetbrains/kotlin/name/Name;", "access", "", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "isEnumEntry", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "initializerValue", "", "(Lorg/jetbrains/kotlin/name/Name;ILorg/jetbrains/kotlin/load/java/structure/JavaClass;ZLorg/jetbrains/kotlin/load/java/structure/JavaType;Ljava/lang/Object;)V", "getAccess", "()I", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "hasConstantNotNullInitializer", "getHasConstantNotNullInitializer", "()Z", "getInitializerValue", "()Ljava/lang/Object;", "isFromSource", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaField implements JavaField, MutableJavaAnnotationOwner, BinaryJavaModifierListOwner {
    private final int access;
    private final Collection<JavaAnnotation> annotations;

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final JavaClass containingClass;
    private final Object initializerValue;
    private final boolean isEnumEntry;
    private final Name name;
    private final JavaType type;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    public BinaryJavaField(Name name2, int i, JavaClass containingClass, boolean z, JavaType type, Object obj) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.access = i;
        this.containingClass = containingClass;
        this.isEnumEntry = z;
        this.type = type;
        this.initializerValue = obj;
        this.annotations = new SmartList();
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaModifierListOwner
    public int getAccess() {
        return this.access;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return this.containingClass;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: isEnumEntry, reason: from getter */
    public boolean getIsEnumEntry() {
        return this.isEnumEntry;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public JavaType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public Object getInitializerValue() {
        return this.initializerValue;
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getHasConstantNotNullInitializer */
    public boolean mo7273getHasConstantNotNullInitializer() {
        return getInitializerValue() != null;
    }
}
