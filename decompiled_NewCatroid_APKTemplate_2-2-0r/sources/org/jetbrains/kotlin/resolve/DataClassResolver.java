package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DataClassResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DataClassResolver;", "", "()V", "createComponentName", "Lorg/jetbrains/kotlin/name/Name;", "index", "", "getComponentIndex", "componentName", "", "isComponentLike", "", "name", "isCopy", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassResolver {
    public static final DataClassResolver INSTANCE = new DataClassResolver();

    private DataClassResolver() {
    }

    public final Name createComponentName(int index) {
        Name nameIdentifier = Name.identifier(StandardNames.DATA_CLASS_COMPONENT_PREFIX + index);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    public final int getComponentIndex(String componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        String strSubstring = componentName.substring(StandardNames.DATA_CLASS_COMPONENT_PREFIX.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return Integer.parseInt(strSubstring);
    }

    public final boolean isComponentLike(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return isComponentLike(strAsString);
    }

    public final boolean isComponentLike(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!StringsKt.startsWith$default(name2, StandardNames.DATA_CLASS_COMPONENT_PREFIX, false, 2, (Object) null)) {
            return false;
        }
        try {
            getComponentIndex(name2);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public final boolean isCopy(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(name2, StandardNames.DATA_CLASS_COPY);
    }
}
