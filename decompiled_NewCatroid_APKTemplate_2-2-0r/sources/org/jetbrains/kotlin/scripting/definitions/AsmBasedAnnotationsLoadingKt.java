package org.jetbrains.kotlin.scripting.definitions;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.org.objectweb.asm.ClassReader;

/* JADX INFO: compiled from: asmBasedAnnotationsLoading.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a \u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006\u0006"}, d2 = {"loadAnnotationsFromClass", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/scripting/definitions/BinAnnData;", "Lkotlin/collections/ArrayList;", "fileContents", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AsmBasedAnnotationsLoadingKt {
    public static final ArrayList<BinAnnData> loadAnnotationsFromClass(byte[] fileContents) {
        Intrinsics.checkNotNullParameter(fileContents, "fileContents");
        TemplateClassVisitor templateClassVisitor = new TemplateClassVisitor(new TemplateAnnotationVisitor(null, 1, null));
        new ClassReader(fileContents).accept(templateClassVisitor, 7);
        return templateClassVisitor.getAnnVisitor().getAnns();
    }
}
