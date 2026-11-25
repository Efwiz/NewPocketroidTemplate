package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaValueParameter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\tH\u0016R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaValueParameter;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "reflectAnnotations", "", "", "reflectName", "", "isVararg", "", "(Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;[Ljava/lang/annotation/Annotation;Ljava/lang/String;Z)V", "annotations", "", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotation;", "getAnnotations", "()Ljava/util/List;", "isDeprecatedInJavaDoc", "()Z", "isFromSource", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "[Ljava/lang/annotation/Annotation;", "getType", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaType;", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "toString", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaValueParameter extends ReflectJavaElement implements JavaValueParameter {
    private final boolean isVararg;
    private final Annotation[] reflectAnnotations;
    private final String reflectName;
    private final ReflectJavaType type;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public boolean isFromSource() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public ReflectJavaType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    public ReflectJavaValueParameter(ReflectJavaType type, Annotation[] reflectAnnotations, String str, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(reflectAnnotations, "reflectAnnotations");
        this.type = type;
        this.reflectAnnotations = reflectAnnotations;
        this.reflectName = str;
        this.isVararg = z;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public List<ReflectJavaAnnotation> mo7252getAnnotations() {
        return ReflectJavaAnnotationOwnerKt.getAnnotations(this.reflectAnnotations);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public ReflectJavaAnnotation mo7251findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return ReflectJavaAnnotationOwnerKt.findAnnotation(this.reflectAnnotations, fqName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public Name getName() {
        String str = this.reflectName;
        if (str != null) {
            return Name.guessByFirstCharacter(str);
        }
        return null;
    }

    public String toString() {
        return getClass().getName() + ": " + (getIsVararg() ? "vararg " : "") + getName() + ": " + getType();
    }
}
