package net.bytebuddy.build;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.FieldPersistence;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.SyntheticState;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class CachedReturnPlugin extends Plugin.ForElementMatcher implements Plugin.Factory {
    private static final String ADVICE_INFIX = "$Advice$";
    private static final MethodDescription.InDefinedShape ENHANCE_VALUE = (MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(Enhance.class).getDeclaredMethods().filter(ElementMatchers.named("value")).getOnly();
    private static final String NAME_INFIX = "_";

    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
    private final Map<TypeDescription, TypeDescription> adviceByType;
    private final ClassFileLocator classFileLocator;
    private final boolean ignoreExistingFields;

    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
    private final RandomString randomString;

    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface CacheField {
    }

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Enhance {
        String value() default "";
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CachedReturnPlugin cachedReturnPlugin = (CachedReturnPlugin) obj;
        return this.ignoreExistingFields == cachedReturnPlugin.ignoreExistingFields && this.classFileLocator.equals(cachedReturnPlugin.classFileLocator);
    }

    @Override // net.bytebuddy.build.Plugin.ForElementMatcher
    public int hashCode() {
        return (((super.hashCode() * 31) + (this.ignoreExistingFields ? 1 : 0)) * 31) + this.classFileLocator.hashCode();
    }

    @Override // net.bytebuddy.build.Plugin.Factory
    public Plugin make() {
        return this;
    }

    public CachedReturnPlugin() {
        this(false);
    }

    public CachedReturnPlugin(boolean z) {
        super(ElementMatchers.declaresMethod(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Enhance.class)));
        this.ignoreExistingFields = z;
        this.randomString = new RandomString();
        ClassFileLocator classFileLocatorOf = ClassFileLocator.ForClassLoader.of(CachedReturnPlugin.class.getClassLoader());
        this.classFileLocator = classFileLocatorOf;
        TypePool typePoolOf = TypePool.Default.of(classFileLocatorOf);
        this.adviceByType = new HashMap();
        Class[] clsArr = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Object.class};
        for (int i = 0; i < 9; i++) {
            Class cls = clsArr[i];
            this.adviceByType.put(TypeDescription.ForLoadedType.of(cls), typePoolOf.describe(CachedReturnPlugin.class.getName() + ADVICE_INFIX + cls.getSimpleName()).resolve());
        }
    }

    @Override // net.bytebuddy.build.Plugin
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        TypeDescription typeDescriptionOf;
        for (MethodDescription.InDefinedShape inDefinedShape : typeDescription.getDeclaredMethods().filter(ElementMatchers.not(ElementMatchers.isBridge()).and(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Enhance.class)))) {
            if (inDefinedShape.isAbstract()) {
                throw new IllegalStateException("Cannot cache the value of an abstract method: " + inDefinedShape);
            }
            if (!inDefinedShape.getParameters().isEmpty()) {
                throw new IllegalStateException("Cannot cache the value of a method with parameters: " + inDefinedShape);
            }
            if (inDefinedShape.getReturnType().represents(Void.TYPE)) {
                throw new IllegalStateException("Cannot cache void result for " + inDefinedShape);
            }
            String str = (String) inDefinedShape.getDeclaredAnnotations().ofType(Enhance.class).getValue(ENHANCE_VALUE).resolve(String.class);
            if (str.length() == 0) {
                str = inDefinedShape.getName() + "_" + this.randomString.nextString();
            } else if (this.ignoreExistingFields && !typeDescription.getDeclaredFields().filter(ElementMatchers.named(str)).isEmpty()) {
                return builder;
            }
            TypeDescription typeDescriptionAsErasure = inDefinedShape.getReturnType().asErasure();
            ModifierContributor.ForField[] forFieldArr = new ModifierContributor.ForField[4];
            forFieldArr[0] = inDefinedShape.isStatic() ? Ownership.STATIC : Ownership.MEMBER;
            forFieldArr[1] = inDefinedShape.isStatic() ? FieldPersistence.PLAIN : FieldPersistence.TRANSIENT;
            forFieldArr[2] = Visibility.PRIVATE;
            forFieldArr[3] = SyntheticState.SYNTHETIC;
            DynamicType.Builder.FieldDefinition.Optional.Valuable<?> valuableDefineField = builder.defineField(str, typeDescriptionAsErasure, forFieldArr);
            Advice.WithCustomMapping withCustomMappingBind = Advice.withCustomMapping().bind(CacheField.class, (Advice.OffsetMapping) new CacheFieldOffsetMapping(str));
            Map<TypeDescription, TypeDescription> map = this.adviceByType;
            if (inDefinedShape.getReturnType().isPrimitive()) {
                typeDescriptionOf = inDefinedShape.getReturnType().asErasure();
            } else {
                typeDescriptionOf = TypeDescription.ForLoadedType.of(Object.class);
            }
            builder = valuableDefineField.visit(withCustomMappingBind.to(map.get(typeDescriptionOf), this.classFileLocator).on(ElementMatchers.is(inDefinedShape)));
        }
        return builder;
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class CacheFieldOffsetMapping implements Advice.OffsetMapping {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f345name;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.f345name.equals(((CacheFieldOffsetMapping) obj).f345name);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.f345name.hashCode();
        }

        protected CacheFieldOffsetMapping(String str) {
            this.f345name = str;
        }

        @Override // net.bytebuddy.asm.Advice.OffsetMapping
        public Advice.OffsetMapping.Target resolve(TypeDescription typeDescription, MethodDescription methodDescription, Assigner assigner, Advice.ArgumentHandler argumentHandler, Advice.OffsetMapping.Sort sort) {
            return new Advice.OffsetMapping.Target.ForField.ReadWrite((FieldDescription) typeDescription.getDeclaredFields().filter(ElementMatchers.named(this.f345name)).getOnly());
        }
    }
}
