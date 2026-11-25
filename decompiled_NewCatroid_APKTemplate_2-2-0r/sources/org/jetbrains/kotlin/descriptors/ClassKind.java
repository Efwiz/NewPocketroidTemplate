package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClassKind.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ClassKind;", "", "codeRepresentation", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getCodeRepresentation", "()Ljava/lang/String;", "isSingleton", "", "()Z", "CLASS", Namer.CLASS_KIND_INTERFACE, "ENUM_CLASS", "ENUM_ENTRY", "ANNOTATION_CLASS", Namer.CLASS_KIND_OBJECT, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ClassKind {
    CLASS("class"),
    INTERFACE(PsiKeyword.INTERFACE),
    ENUM_CLASS("enum class"),
    ENUM_ENTRY(null),
    ANNOTATION_CLASS("annotation class"),
    OBJECT("object");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String codeRepresentation;

    public static EnumEntries<ClassKind> getEntries() {
        return $ENTRIES;
    }

    ClassKind(String str) {
        this.codeRepresentation = str;
    }

    public final String getCodeRepresentation() {
        return this.codeRepresentation;
    }

    public final boolean isSingleton() {
        return this == OBJECT || this == ENUM_ENTRY;
    }
}
