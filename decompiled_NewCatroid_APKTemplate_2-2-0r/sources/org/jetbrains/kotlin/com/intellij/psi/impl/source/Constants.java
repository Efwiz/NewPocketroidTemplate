package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface Constants extends ElementType {
    public static final TokenSet CLASS_BIT_SET = TokenSet.create(JavaElementType.CLASS, JavaElementType.ANONYMOUS_CLASS, JavaElementType.ENUM_CONSTANT_INITIALIZER);
    public static final TokenSet FIELD_BIT_SET = TokenSet.create(JavaElementType.FIELD, JavaElementType.ENUM_CONSTANT);
    public static final TokenSet METHOD_BIT_SET = TokenSet.create(JavaElementType.METHOD, JavaElementType.ANNOTATION_METHOD);
    public static final TokenSet CLASS_INITIALIZER_BIT_SET = TokenSet.create(JavaElementType.CLASS_INITIALIZER);
    public static final TokenSet PARAMETER_BIT_SET = TokenSet.create(JavaElementType.PARAMETER);
    public static final TokenSet CATCH_SECTION_BIT_SET = TokenSet.create(JavaElementType.CATCH_SECTION);
    public static final TokenSet JAVA_CODE_REFERENCE_BIT_SET = TokenSet.create(JavaElementType.JAVA_CODE_REFERENCE);
    public static final TokenSet NAME_VALUE_PAIR_BIT_SET = TokenSet.create(JavaElementType.NAME_VALUE_PAIR);
    public static final TokenSet ANNOTATION_BIT_SET = TokenSet.create(JavaElementType.ANNOTATION);
}
