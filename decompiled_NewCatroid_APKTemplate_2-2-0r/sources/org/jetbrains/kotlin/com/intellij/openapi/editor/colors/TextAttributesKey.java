package org.jetbrains.kotlin.com.intellij.openapi.editor.colors;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public final class TextAttributesKey implements Comparable<TextAttributesKey> {
    private final TextAttributes myDefaultAttributes;
    private final String myExternalName;
    private final TextAttributesKey myFallbackAttributeKey;
    public static final TextAttributesKey[] EMPTY_ARRAY = new TextAttributesKey[0];
    private static final Logger LOG = Logger.getInstance((Class<?>) TextAttributesKey.class);
    private static final TextAttributes NULL_ATTRIBUTES = new TextAttributes();
    private static final ConcurrentMap<String, TextAttributesKey> ourRegistry = new ConcurrentHashMap();
    private static final NullableLazyValue<TextAttributeKeyDefaultsProvider> ourDefaultsProvider = new VolatileNullableLazyValue<TextAttributeKeyDefaultsProvider>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue, org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
        public TextAttributeKeyDefaultsProvider compute() {
            return (TextAttributeKeyDefaultsProvider) ApplicationManager.getApplication().getService(TextAttributeKeyDefaultsProvider.class);
        }
    };
    private static final ThreadLocal<Set<String>> CALLED_RECURSIVELY = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return TextAttributesKey.lambda$static$2();
        }
    });

    @Deprecated
    static final TextAttributesKey DUMMY_DEPRECATED_ATTRIBUTES = createTextAttributesKey("__deprecated__");

    @FunctionalInterface
    public interface TextAttributeKeyDefaultsProvider {
    }

    /* JADX WARN: Code duplicated, block: B:28:0x003d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0042  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 11 || i == 12) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "fallbackAttributeKey";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/TextAttributesKey";
        } else if (i == 6) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i == 11 || i == 12) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/TextAttributesKey";
        } else if (i != 16) {
            objArr[0] = "externalName";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        if (i == 4) {
            objArr[1] = "find";
        } else if (i == 5) {
            objArr[1] = "getExternalName";
        } else if (i == 11 || i == 12) {
            objArr[1] = "getOrCreate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/TextAttributesKey";
        }
        switch (i) {
            case 1:
                objArr[2] = "checkForCycle";
                break;
            case 2:
            default:
                objArr[2] = "<init>";
                break;
            case 3:
                objArr[2] = "find";
                break;
            case 4:
            case 5:
            case 11:
            case 12:
                break;
            case 6:
                objArr[2] = "compareTo";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createTextAttributesKey";
                break;
            case 10:
                objArr[2] = "getOrCreate";
                break;
            case 13:
                objArr[2] = "mergeKeys";
                break;
            case 14:
                objArr[2] = "createTempTextAttributesKey";
                break;
            case 15:
                objArr[2] = "removeTextAttributesKey";
                break;
            case 16:
                objArr[2] = "isTemp";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private TextAttributesKey(String str, TextAttributes textAttributes, TextAttributesKey textAttributesKey) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myExternalName = str;
        this.myDefaultAttributes = textAttributes;
        this.myFallbackAttributeKey = textAttributesKey;
        if (textAttributesKey != null) {
            checkForCycle(textAttributesKey);
        }
    }

    private void checkForCycle(TextAttributesKey textAttributesKey) {
        if (textAttributesKey == null) {
            $$$reportNull$$$0(1);
        }
        for (TextAttributesKey textAttributesKey2 = textAttributesKey; textAttributesKey2 != null; textAttributesKey2 = textAttributesKey2.myFallbackAttributeKey) {
            if (equals(textAttributesKey2)) {
                throw new IllegalArgumentException("Can't use this fallback key: " + textAttributesKey + ": Cycle detected: " + StringUtil.join(JBIterable.generate(this.myFallbackAttributeKey, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.util.Function
                    public final Object fun(Object obj) {
                        return this.f$0.m6473x29ce9bb3((TextAttributesKey) obj);
                    }
                }), "->"));
            }
        }
    }

    /* JADX INFO: renamed from: lambda$checkForCycle$0$org-jetbrains-kotlin-com-intellij-openapi-editor-colors-TextAttributesKey, reason: not valid java name */
    /* synthetic */ TextAttributesKey m6473x29ce9bb3(TextAttributesKey textAttributesKey) {
        if (textAttributesKey == this) {
            return null;
        }
        return textAttributesKey.myFallbackAttributeKey;
    }

    public static TextAttributesKey find(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        TextAttributesKey textAttributesKeyComputeIfAbsent = ourRegistry.computeIfAbsent(str, new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TextAttributesKey.lambda$find$1((String) obj);
            }
        });
        if (textAttributesKeyComputeIfAbsent == null) {
            $$$reportNull$$$0(4);
        }
        return textAttributesKeyComputeIfAbsent;
    }

    static /* synthetic */ TextAttributesKey lambda$find$1(String str) {
        return new TextAttributesKey(str, null, null);
    }

    public String toString() {
        return this.myExternalName;
    }

    @Override // java.lang.Comparable
    public int compareTo(TextAttributesKey textAttributesKey) {
        if (textAttributesKey == null) {
            $$$reportNull$$$0(6);
        }
        return this.myExternalName.compareTo(textAttributesKey.myExternalName);
    }

    public static TextAttributesKey createTextAttributesKey(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return find(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.myExternalName.equals(((TextAttributesKey) obj).myExternalName);
    }

    public int hashCode() {
        return this.myExternalName.hashCode();
    }

    static /* synthetic */ Set lambda$static$2() {
        return new HashSet();
    }
}
