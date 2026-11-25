package net.bytebuddy.build;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.attribute.AnnotationValueFilter;
import net.bytebuddy.implementation.attribute.TypeAttributeAppender;
import net.bytebuddy.jar.asm.AnnotationVisitor;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class RepeatedAnnotationPlugin extends Plugin.ForElementMatcher {
    private static final MethodDescription.InDefinedShape VALUE = (MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(Enhance.class).getDeclaredMethods().filter(ElementMatchers.named("value")).getOnly();

    @Target({ElementType.ANNOTATION_TYPE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Enhance {
        Class<? extends Annotation> value();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // net.bytebuddy.build.Plugin.ForElementMatcher
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    @Override // net.bytebuddy.build.Plugin.ForElementMatcher
    public int hashCode() {
        return super.hashCode();
    }

    public RepeatedAnnotationPlugin() {
        super(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Enhance.class));
    }

    @Override // net.bytebuddy.build.Plugin
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        TypeDescription typeDescription2 = (TypeDescription) typeDescription.getDeclaredAnnotations().ofType(Enhance.class).getValue(VALUE).resolve(TypeDescription.class);
        if (!typeDescription2.isAnnotation()) {
            throw new IllegalStateException("Expected " + typeDescription2 + " to be an annotation type");
        }
        if (typeDescription2.getDeclaredMethods().size() != 1 || typeDescription2.getDeclaredMethods().filter(ElementMatchers.named("value")).size() != 1 || !((MethodDescription.InDefinedShape) typeDescription2.getDeclaredMethods().filter(ElementMatchers.named("value")).getOnly()).getReturnType().isArray() || !((MethodDescription.InDefinedShape) typeDescription2.getDeclaredMethods().filter(ElementMatchers.named("value")).getOnly()).getReturnType().getComponentType().asErasure().equals(typeDescription)) {
            throw new IllegalStateException("Expected " + typeDescription2 + " to declare exactly one property named value of an array type");
        }
        return builder.attribute(new RepeatedAnnotationAppender(typeDescription2));
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class RepeatedAnnotationAppender implements TypeAttributeAppender {
        private final TypeDescription target;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.target.equals(((RepeatedAnnotationAppender) obj).target);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.target.hashCode();
        }

        protected RepeatedAnnotationAppender(TypeDescription typeDescription) {
            this.target = typeDescription;
        }

        @Override // net.bytebuddy.implementation.attribute.TypeAttributeAppender
        public void apply(ClassVisitor classVisitor, TypeDescription typeDescription, AnnotationValueFilter annotationValueFilter) {
            AnnotationVisitor annotationVisitorVisitAnnotation = classVisitor.visitAnnotation("Ljava/lang/annotation/Repeatable;", true);
            if (annotationVisitorVisitAnnotation != null) {
                annotationVisitorVisitAnnotation.visit("value", Type.getType(this.target.getDescriptor()));
                annotationVisitorVisitAnnotation.visitEnd();
            }
        }
    }
}
