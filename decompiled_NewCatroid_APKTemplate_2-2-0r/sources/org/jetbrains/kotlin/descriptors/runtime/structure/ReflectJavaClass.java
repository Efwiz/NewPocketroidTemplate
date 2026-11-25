package org.jetbrains.kotlin.descriptors.runtime.structure;

import com.reandroid.arsc.value.CompoundEntry;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0011\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010@\u001a\u00020\u001b2\b\u0010A\u001a\u0004\u0018\u00010BH\u0096\u0002J\u0012\u0010C\u001a\u0004\u0018\u00010\u00002\u0006\u0010-\u001a\u00020\u0018H\u0016J\b\u0010D\u001a\u00020\u001bH\u0016J\b\u0010E\u001a\u00020*H\u0016J\u0010\u0010F\u001a\u00020\u001b2\u0006\u0010G\u001a\u00020HH\u0002J\b\u0010I\u001a\u00020JH\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\fR\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001cR\u0014\u0010\u001e\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0014\u0010\u001f\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001cR\u0014\u0010 \u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u001cR\u0014\u0010!\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u001cR\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\fR\u0014\u0010)\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u00010\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u001a\u00103\u001a\b\u0012\u0004\u0012\u000205048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u001a\u00108\u001a\b\u0012\u0004\u0012\u000209048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u00107R\u001a\u0010;\u001a\b\u0012\u0004\u0012\u000205048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u00107R\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010\f¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaClass;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaElement;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "klass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "constructors", "", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaConstructor;", "getConstructors", "()Ljava/util/List;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getElement", "()Ljava/lang/Class;", "fields", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaField;", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "isAnnotationType", "", "()Z", "isEnum", "isFromSource", "isInterface", "isRecord", "isSealed", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaMethod;", "getMethods", "modifiers", "", "getModifiers", "()I", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaClass;", "permittedTypes", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "()Ljava/util/Collection;", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaTypeParameter;", "getTypeParameters", Namer.EQUALS_METHOD_NAME, "other", "", "findInnerClass", "hasDefaultConstructor", "hashCode", "isEnumValuesOrValueOf", "method", "Ljava/lang/reflect/Method;", "toString", "", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaClass extends ReflectJavaElement implements ReflectJavaAnnotationOwner, ReflectJavaModifierListOwner, JavaClass {
    private final Class<?> klass;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    public ReflectJavaClass(Class<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaAnnotationOwner
    public Class<?> getElement() {
        return this.klass;
    }

    @Override // org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaModifierListOwner
    public int getModifiers() {
        return this.klass.getModifiers();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public List<Name> getInnerClassNames() {
        Class<?>[] declaredClasses = this.klass.getDeclaredClasses();
        Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
        return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.filterNot(ArraysKt.asSequence(declaredClasses), new Function1<Class<?>, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass$innerClassNames$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Class<?> cls) {
                String simpleName = cls.getSimpleName();
                Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                return Boolean.valueOf(simpleName.length() == 0);
            }
        }), new Function1<Class<?>, Name>() { // from class: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass$innerClassNames$2
            @Override // kotlin.jvm.functions.Function1
            public final Name invoke(Class<?> cls) {
                String simpleName = cls.getSimpleName();
                if (!Name.isValidIdentifier(simpleName)) {
                    simpleName = null;
                }
                if (simpleName != null) {
                    return Name.identifier(simpleName);
                }
                return null;
            }
        }));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public ReflectJavaClass findInnerClass(Name name2) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Class<?>[] declaredClasses = this.klass.getDeclaredClasses();
        Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
        Iterator it = ArraysKt.asSequence(declaredClasses).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((Class) next).getSimpleName(), name2.asString()));
        Class cls = (Class) next;
        if (cls != null) {
            return new ReflectJavaClass(cls);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        FqName fqNameAsSingleFqName = ReflectClassUtilKt.getClassId(this.klass).asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return fqNameAsSingleFqName;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public ReflectJavaClass getOuterClass() {
        Class<?> declaringClass = this.klass.getDeclaringClass();
        if (declaringClass != null) {
            return new ReflectJavaClass(declaringClass);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        if (Intrinsics.areEqual(this.klass, Object.class)) {
            return CollectionsKt.emptyList();
        }
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        Class genericSuperclass = this.klass.getGenericSuperclass();
        if (genericSuperclass == null) {
        }
        spreadBuilder.add(genericSuperclass);
        Type[] genericInterfaces = this.klass.getGenericInterfaces();
        Intrinsics.checkNotNullExpressionValue(genericInterfaces, "getGenericInterfaces(...)");
        spreadBuilder.addSpread(genericInterfaces);
        List listListOf = CollectionsKt.listOf(spreadBuilder.toArray(new Type[spreadBuilder.size()]));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new ReflectJavaClassifierType((Type) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public List<ReflectJavaMethod> mo7255getMethods() {
        Method[] declaredMethods = this.klass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filter(ArraysKt.asSequence(declaredMethods), new Function1<Method, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass$methods$1
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:9:0x001c  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Method method) {
                boolean z = false;
                if (!method.isSynthetic()) {
                    if (this.this$0.mo7263isEnum()) {
                        ReflectJavaClass reflectJavaClass = this.this$0;
                        Intrinsics.checkNotNull(method);
                        if (!reflectJavaClass.isEnumValuesOrValueOf(method)) {
                            z = true;
                        }
                    } else {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        }), ReflectJavaClass$methods$2.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEnumValuesOrValueOf(Method method) {
        String name2 = method.getName();
        if (Intrinsics.areEqual(name2, CompoundEntry.NAME_values)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Intrinsics.checkNotNullExpressionValue(parameterTypes, "getParameterTypes(...)");
            if (parameterTypes.length == 0) {
                return true;
            }
        } else if (Intrinsics.areEqual(name2, "valueOf")) {
            return Arrays.equals(method.getParameterTypes(), new Class[]{String.class});
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public List<ReflectJavaField> mo7254getFields() {
        Field[] declaredFields = this.klass.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(ArraysKt.asSequence(declaredFields), ReflectJavaClass$fields$1.INSTANCE), ReflectJavaClass$fields$2.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public List<ReflectJavaConstructor> mo7253getConstructors() {
        Constructor<?>[] declaredConstructors = this.klass.getDeclaredConstructors();
        Intrinsics.checkNotNullExpressionValue(declaredConstructors, "getDeclaredConstructors(...)");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(ArraysKt.asSequence(declaredConstructors), ReflectJavaClass$constructors$1.INSTANCE), ReflectJavaClass$constructors$2.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        if (this.klass.isAnonymousClass()) {
            String name2 = this.klass.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Name nameIdentifier = Name.identifier(StringsKt.substringAfterLast$default(name2, ".", (String) null, 2, (Object) null));
            Intrinsics.checkNotNull(nameIdentifier);
            return nameIdentifier;
        }
        Name nameIdentifier2 = Name.identifier(this.klass.getSimpleName());
        Intrinsics.checkNotNull(nameIdentifier2);
        return nameIdentifier2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<ReflectJavaTypeParameter> mo7258getTypeParameters() {
        TypeVariable<Class<?>>[] typeParameters = this.klass.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        TypeVariable<Class<?>>[] typeVariableArr = typeParameters;
        ArrayList arrayList = new ArrayList(typeVariableArr.length);
        for (TypeVariable<Class<?>> typeVariable : typeVariableArr) {
            arrayList.add(new ReflectJavaTypeParameter(typeVariable));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
        return this.klass.isInterface();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return this.klass.isAnnotation();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return this.klass.isEnum();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isRecord */
    public boolean mo7266isRecord() throws IllegalAccessException, InvocationTargetException {
        Boolean boolLoadIsRecord = Java16SealedRecordLoader.INSTANCE.loadIsRecord(this.klass);
        if (boolLoadIsRecord != null) {
            return boolLoadIsRecord.booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public Collection<JavaRecordComponent> mo7257getRecordComponents() {
        Object[] objArrLoadGetRecordComponents = Java16SealedRecordLoader.INSTANCE.loadGetRecordComponents(this.klass);
        if (objArrLoadGetRecordComponents == null) {
            objArrLoadGetRecordComponents = new Object[0];
        }
        ArrayList arrayList = new ArrayList(objArrLoadGetRecordComponents.length);
        for (Object obj : objArrLoadGetRecordComponents) {
            arrayList.add(new ReflectJavaRecordComponent(obj));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public boolean isSealed() throws IllegalAccessException, InvocationTargetException {
        Boolean boolLoadIsSealed = Java16SealedRecordLoader.INSTANCE.loadIsSealed(this.klass);
        if (boolLoadIsSealed != null) {
            return boolLoadIsSealed.booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() throws IllegalAccessException, InvocationTargetException {
        Class<?>[] clsArrLoadGetPermittedSubclasses = Java16SealedRecordLoader.INSTANCE.loadGetPermittedSubclasses(this.klass);
        if (clsArrLoadGetPermittedSubclasses == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(clsArrLoadGetPermittedSubclasses.length);
        for (Class<?> cls : clsArrLoadGetPermittedSubclasses) {
            arrayList.add(new ReflectJavaClassifierType(cls));
        }
        return arrayList;
    }

    public boolean equals(Object other) {
        return (other instanceof ReflectJavaClass) && Intrinsics.areEqual(this.klass, ((ReflectJavaClass) other).klass);
    }

    public int hashCode() {
        return this.klass.hashCode();
    }

    public String toString() {
        return getClass().getName() + ": " + this.klass;
    }
}
