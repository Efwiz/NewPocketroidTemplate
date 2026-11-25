package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiModifier {
    public static final String ABSTRACT = "abstract";
    public static final String DEFAULT = "default";
    public static final String FINAL = "final";
    public static final String[] MODIFIERS = {"public", "protected", "private", "static", "abstract", "final", "native", "synchronized", "strictfp", "transient", "volatile", "default", "open", "transitive", "sealed", "non-sealed"};
    public static final String NATIVE = "native";
    public static final String NON_SEALED = "non-sealed";
    public static final String OPEN = "open";
    public static final String PACKAGE_LOCAL = "packageLocal";
    public static final String PRIVATE = "private";
    public static final String PROTECTED = "protected";
    public static final String PUBLIC = "public";
    public static final String SEALED = "sealed";
    public static final String STATIC = "static";
    public static final String STRICTFP = "strictfp";
    public static final String SYNCHRONIZED = "synchronized";
    public static final String TRANSIENT = "transient";
    public static final String TRANSITIVE = "transitive";
    public static final String VOLATILE = "volatile";

    public @interface ModifierConstant {
    }
}
