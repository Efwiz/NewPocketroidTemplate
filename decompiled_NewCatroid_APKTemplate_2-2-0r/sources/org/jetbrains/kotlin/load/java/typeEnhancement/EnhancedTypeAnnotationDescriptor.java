package org.jetbrains.kotlin.load.java.typeEnhancement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R$\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/EnhancedTypeAnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "()V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "throwError", "", "toString", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class EnhancedTypeAnnotationDescriptor implements AnnotationDescriptor {
    public static final EnhancedTypeAnnotationDescriptor INSTANCE = new EnhancedTypeAnnotationDescriptor();

    private EnhancedTypeAnnotationDescriptor() {
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public FqName getFqName() {
        return AnnotationDescriptor.DefaultImpls.getFqName(this);
    }

    private final Void throwError() {
        throw new IllegalStateException("No methods should be called on this descriptor. Only its presence matters".toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public KotlinType getType() {
        throwError();
        throw null;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        throwError();
        throw null;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public SourceElement getSource() {
        throwError();
        throw null;
    }

    public String toString() {
        return "[EnhancedType]";
    }
}
