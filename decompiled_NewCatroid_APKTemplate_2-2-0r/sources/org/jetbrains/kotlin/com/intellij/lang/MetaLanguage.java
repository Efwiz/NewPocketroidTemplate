package org.jetbrains.kotlin.com.intellij.lang;

import java.util.Collection;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MetaLanguage extends Language {
    public static final ExtensionPointName<MetaLanguage> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.metaLanguage");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/MetaLanguage";
        } else {
            objArr[0] = SchemaSymbols.ATTVAL_ID;
        }
        if (i == 1) {
            objArr[1] = DevModeOverwritingStrategies.ALL;
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/MetaLanguage";
        } else {
            objArr[1] = "getMatchingLanguages";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract boolean matchesLanguage(Language language);

    public Collection<Language> getMatchingLanguages() {
        List listFilter = ContainerUtil.filter(Language.getRegisteredLanguages(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.lang.MetaLanguage$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return this.f$0.matchesLanguage((Language) obj);
            }
        });
        if (listFilter == null) {
            $$$reportNull$$$0(2);
        }
        return listFilter;
    }
}
