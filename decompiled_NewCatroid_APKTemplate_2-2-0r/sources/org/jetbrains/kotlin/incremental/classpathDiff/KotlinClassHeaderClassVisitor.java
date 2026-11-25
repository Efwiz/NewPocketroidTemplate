package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: compiled from: BasicClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassHeaderClassVisitor;", "Lorg/jetbrains/org/objectweb/asm/ClassVisitor;", "()V", "kotlinClassHeaderAnnotationVisitor", "Lorg/jetbrains/kotlin/load/kotlin/header/ReadKotlinClassHeaderAnnotationVisitor;", "getKotlinClassHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "descriptor", "", "visible", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KotlinClassHeaderClassVisitor extends ClassVisitor {
    private final ReadKotlinClassHeaderAnnotationVisitor kotlinClassHeaderAnnotationVisitor;

    public KotlinClassHeaderClassVisitor() {
        super(589824);
        this.kotlinClassHeaderAnnotationVisitor = new ReadKotlinClassHeaderAnnotationVisitor();
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return FileBasedKotlinClass.convertAnnotationVisitor(this.kotlinClassHeaderAnnotationVisitor, descriptor, new FileBasedKotlinClass.InnerClassesInfo());
    }

    public final KotlinClassHeader getKotlinClassHeader() {
        return this.kotlinClassHeaderAnnotationVisitor.createHeaderWithDefaultMetadataVersion();
    }
}
