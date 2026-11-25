package org.jetbrains.kotlin.scripting.definitions;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;

/* JADX INFO: compiled from: asmBasedAnnotationsLoading.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0018\b\u0002\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/TemplateAnnotationVisitor;", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "anns", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/scripting/definitions/BinAnnData;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "getAnns", "()Ljava/util/ArrayList;", "visit", "", "name", "", "value", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TemplateAnnotationVisitor extends AnnotationVisitor {
    private final ArrayList<BinAnnData> anns;

    /* JADX WARN: Multi-variable type inference failed */
    public TemplateAnnotationVisitor() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TemplateAnnotationVisitor(ArrayList<BinAnnData> anns) {
        super(589824);
        Intrinsics.checkNotNullParameter(anns, "anns");
        this.anns = anns;
    }

    public /* synthetic */ TemplateAnnotationVisitor(ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ArrayList() : arrayList);
    }

    public final ArrayList<BinAnnData> getAnns() {
        return this.anns;
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visit(String name2, Object value) {
        ((BinAnnData) CollectionsKt.last((List) this.anns)).getArgs().add(new BinAnnArgData(name2, String.valueOf(value)));
    }
}
