package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmAdditionalClassPartsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¨\u0006\u0004"}, d2 = {"isJavaLangRecordType", "", "it", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmAdditionalClassPartsProviderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJavaLangRecordType(KotlinType kotlinType) {
        return KotlinBuiltIns.isConstructedFromGivenClass(kotlinType, JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME());
    }
}
