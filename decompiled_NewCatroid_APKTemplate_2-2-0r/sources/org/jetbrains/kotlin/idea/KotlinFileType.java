package org.jetbrains.kotlin.idea;

import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinFileType extends LanguageFileType {
    public static final String DOT_DEFAULT_EXTENSION = ".kt";
    public static final String EXTENSION = "kt";
    public static final KotlinFileType INSTANCE = new KotlinFileType();
    private final NotNullLazyValue<Icon> myIcon;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/idea/KotlinFileType", "getDescription"));
    }

    private KotlinFileType() {
        super(KotlinLanguage.INSTANCE);
        this.myIcon = NotNullLazyValue.lazy(new Supplier() { // from class: org.jetbrains.kotlin.idea.KotlinFileType$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return KotlinIconProviderService.getInstance().getFileIcon();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getName() {
        return "Kotlin";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDescription() {
        String name2 = getName();
        if (name2 == null) {
            $$$reportNull$$$0(0);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType
    public Icon getIcon() {
        return this.myIcon.getValue();
    }
}
