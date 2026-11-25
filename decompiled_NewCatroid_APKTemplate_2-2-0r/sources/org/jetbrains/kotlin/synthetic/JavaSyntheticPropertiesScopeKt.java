package org.jetbrains.kotlin.synthetic;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: compiled from: JavaSyntheticPropertiesScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"canBePropertyAccessor", "", "identifier", "", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaSyntheticPropertiesScopeKt {
    public static final boolean canBePropertyAccessor(String identifier) {
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        return StringsKt.startsWith$default(identifier, "get", false, 2, (Object) null) || StringsKt.startsWith$default(identifier, LanguageCodeUtil.IS, false, 2, (Object) null) || StringsKt.startsWith$default(identifier, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, false, 2, (Object) null);
    }
}
