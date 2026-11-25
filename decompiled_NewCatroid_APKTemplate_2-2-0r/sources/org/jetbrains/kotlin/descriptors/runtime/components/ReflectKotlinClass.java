package org.jetbrains.kotlin.descriptors.runtime.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ReflectKotlinClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u001b\b\u0002\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u001a\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0010H\u0016J\u001a\u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020!2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "klass", "Ljava/lang/Class;", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "(Ljava/lang/Class;Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;)V", "getClassHeader", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getKlass", "()Ljava/lang/Class;", FirebaseAnalytics.Param.LOCATION, "", "getLocation", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "loadClassAnnotations", "", "visitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "cachedContents", "", "toString", "visitMembers", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MemberVisitor;", "Factory", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectKotlinClass implements KotlinJvmBinaryClass {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinClassHeader classHeader;
    private final Class<?> klass;

    public /* synthetic */ ReflectKotlinClass(Class cls, KotlinClassHeader kotlinClassHeader, DefaultConstructorMarker defaultConstructorMarker) {
        this(cls, kotlinClassHeader);
    }

    private ReflectKotlinClass(Class<?> cls, KotlinClassHeader kotlinClassHeader) {
        this.klass = cls;
        this.classHeader = kotlinClassHeader;
    }

    public final Class<?> getKlass() {
        return this.klass;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public KotlinClassHeader getClassHeader() {
        return this.classHeader;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.descriptors.runtime.components.ReflectKotlinClass$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: ReflectKotlinClass.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass$Factory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass;", "klass", "Ljava/lang/Class;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReflectKotlinClass create(Class<?> klass) throws InvocationTargetException {
            Intrinsics.checkNotNullParameter(klass, "klass");
            ReadKotlinClassHeaderAnnotationVisitor readKotlinClassHeaderAnnotationVisitor = new ReadKotlinClassHeaderAnnotationVisitor();
            ReflectClassStructure.INSTANCE.loadClassAnnotations(klass, readKotlinClassHeaderAnnotationVisitor);
            KotlinClassHeader kotlinClassHeaderCreateHeaderWithDefaultMetadataVersion = readKotlinClassHeaderAnnotationVisitor.createHeaderWithDefaultMetadataVersion();
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (kotlinClassHeaderCreateHeaderWithDefaultMetadataVersion == null) {
                return null;
            }
            return new ReflectKotlinClass(klass, kotlinClassHeaderCreateHeaderWithDefaultMetadataVersion, defaultConstructorMarker);
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public String getLocation() {
        StringBuilder sb = new StringBuilder();
        String name2 = this.klass.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return sb.append(StringsKt.replace$default(name2, '.', '/', false, 4, (Object) null)).append(".class").toString();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public ClassId getClassId() {
        return ReflectClassUtilKt.getClassId(this.klass);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public void loadClassAnnotations(KotlinJvmBinaryClass.AnnotationVisitor visitor, byte[] cachedContents) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        ReflectClassStructure.INSTANCE.loadClassAnnotations(this.klass, visitor);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public void visitMembers(KotlinJvmBinaryClass.MemberVisitor visitor, byte[] cachedContents) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        ReflectClassStructure.INSTANCE.visitMembers(this.klass, visitor);
    }

    public boolean equals(Object other) {
        return (other instanceof ReflectKotlinClass) && Intrinsics.areEqual(this.klass, ((ReflectKotlinClass) other).klass);
    }

    public int hashCode() {
        return this.klass.hashCode();
    }

    public String toString() {
        return getClass().getName() + ": " + this.klass;
    }
}
