package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: asmBasedAnnotationsLoading.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/TemplateClassVisitor;", "Lorg/jetbrains/org/objectweb/asm/ClassVisitor;", "annVisitor", "Lorg/jetbrains/kotlin/scripting/definitions/TemplateAnnotationVisitor;", "(Lorg/jetbrains/kotlin/scripting/definitions/TemplateAnnotationVisitor;)V", "getAnnVisitor", "()Lorg/jetbrains/kotlin/scripting/definitions/TemplateAnnotationVisitor;", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "", "visible", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TemplateClassVisitor extends ClassVisitor {
    private final TemplateAnnotationVisitor annVisitor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateClassVisitor(TemplateAnnotationVisitor annVisitor) {
        super(589824);
        Intrinsics.checkNotNullParameter(annVisitor, "annVisitor");
        this.annVisitor = annVisitor;
    }

    public final TemplateAnnotationVisitor getAnnVisitor() {
        return this.annVisitor;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        String internalName = Type.getType(desc).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        String strSubstringAfterLast$default = StringsKt.substringAfterLast$default(internalName, "/", (String) null, 2, (Object) null);
        if (!StringsKt.startsWith$default(strSubstringAfterLast$default, "KotlinScript", false, 2, (Object) null) && !StringsKt.startsWith$default(strSubstringAfterLast$default, "ScriptTemplate", false, 2, (Object) null)) {
            return null;
        }
        this.annVisitor.getAnns().add(new BinAnnData(strSubstringAfterLast$default, null, 2, null));
        return this.annVisitor;
    }
}
