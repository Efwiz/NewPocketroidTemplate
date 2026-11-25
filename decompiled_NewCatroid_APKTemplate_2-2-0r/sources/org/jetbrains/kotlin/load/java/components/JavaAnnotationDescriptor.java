package org.jetbrains.kotlin.load.java.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: JavaAnnotationMapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR$\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 ¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaAnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/load/java/descriptors/PossiblyExternalAnnotationDescriptor;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "annotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;Lorg/jetbrains/kotlin/name/FqName;)V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", "firstArgument", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getFirstArgument", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "isIdeExternalAnnotation", "", "()Z", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/SimpleType;", "getType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "type$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JavaAnnotationDescriptor implements AnnotationDescriptor, PossiblyExternalAnnotationDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaAnnotationDescriptor.class), ModuleXmlParser.TYPE, "getType()Lorg/jetbrains/kotlin/types/SimpleType;"))};
    private final JavaAnnotationArgument firstArgument;
    private final FqName fqName;
    private final boolean isIdeExternalAnnotation;
    private final SourceElement source;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue type;

    public JavaAnnotationDescriptor(final LazyJavaResolverContext c, JavaAnnotation javaAnnotation, FqName fqName) {
        JavaSourceElement NO_SOURCE;
        Collection<JavaAnnotationArgument> arguments;
        JavaSourceElement javaSourceElementSource;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
        if (javaAnnotation == null || (javaSourceElementSource = c.getComponents().getSourceElementFactory().source(javaAnnotation)) == null) {
            NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        } else {
            NO_SOURCE = javaSourceElementSource;
        }
        this.source = NO_SOURCE;
        this.type = c.getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.load.java.components.JavaAnnotationDescriptor$type$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                SimpleType defaultType = c.getModule().getBuiltIns().getBuiltInClassByFqName(this.getFqName()).getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                return defaultType;
            }
        });
        this.firstArgument = (javaAnnotation == null || (arguments = javaAnnotation.getArguments()) == null) ? null : (JavaAnnotationArgument) CollectionsKt.firstOrNull(arguments);
        boolean z = false;
        if (javaAnnotation != null && javaAnnotation.isIdeExternalAnnotation()) {
            z = true;
        }
        this.isIdeExternalAnnotation = z;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public SourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public SimpleType getType() {
        return (SimpleType) StorageKt.getValue(this.type, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    protected final JavaAnnotationArgument getFirstArgument() {
        return this.firstArgument;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.load.java.descriptors.PossiblyExternalAnnotationDescriptor
    /* JADX INFO: renamed from: isIdeExternalAnnotation, reason: from getter */
    public boolean getIsIdeExternalAnnotation() {
        return this.isIdeExternalAnnotation;
    }
}
