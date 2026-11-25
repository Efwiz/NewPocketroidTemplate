package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001c\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/DeclarationKind;", "", "displayName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "CLASS", "INNER_CLASS", "DATA_CLASS", "VALUE_CLASS", Namer.CLASS_KIND_INTERFACE, "FUN_INTERFACE", "ENUM_CLASS", "ENUM_ENTRY", "ENUM_ENTRY_CLASS", "ANNOTATION_CLASS", Namer.CLASS_KIND_OBJECT, "ANONYMOUS_OBJECT", "COMPANION_OBJECT", "VARIABLE", "VALUE_PARAMETER", "FIELD", "FIELD_OF_PROPERTY", "PROPERTY", "PROPERTY_ACCESSOR", "LAMBDA", "FUNCTION", "CONSTRUCTOR", "TYPE_PARAMETER", "OTHER_DECLARATION", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
enum DeclarationKind {
    CLASS("class"),
    INNER_CLASS("inner class"),
    DATA_CLASS("data class"),
    VALUE_CLASS("value class"),
    INTERFACE(PsiKeyword.INTERFACE),
    FUN_INTERFACE("fun interface"),
    ENUM_CLASS("enum class"),
    ENUM_ENTRY("enum entry"),
    ENUM_ENTRY_CLASS("enum entry class"),
    ANNOTATION_CLASS("annotation class"),
    OBJECT("object"),
    ANONYMOUS_OBJECT("anonymous object"),
    COMPANION_OBJECT("companion object"),
    VARIABLE("variable"),
    VALUE_PARAMETER("value parameter"),
    FIELD("field"),
    FIELD_OF_PROPERTY("backing field of property"),
    PROPERTY("property"),
    PROPERTY_ACCESSOR("property accessor"),
    LAMBDA("lambda"),
    FUNCTION("function"),
    CONSTRUCTOR(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME),
    TYPE_PARAMETER("type parameter"),
    OTHER_DECLARATION("declaration");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String displayName;

    public static EnumEntries<DeclarationKind> getEntries() {
        return $ENTRIES;
    }

    DeclarationKind(String str) {
        this.displayName = str;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}
