package org.jetbrains.kotlin.psi.stubs.elements;

import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes6.dex */
public interface KtTokenSets {
    public static final TokenSet DECLARATION_TYPES = TokenSet.create(KtStubElementTypes.CLASS, KtStubElementTypes.OBJECT_DECLARATION, KtStubElementTypes.FUNCTION, KtStubElementTypes.PROPERTY, KtStubElementTypes.TYPEALIAS, KtStubElementTypes.CLASS_INITIALIZER, KtStubElementTypes.SECONDARY_CONSTRUCTOR, KtStubElementTypes.ENUM_ENTRY);
    public static final TokenSet SUPER_TYPE_LIST_ENTRIES = TokenSet.create(KtStubElementTypes.DELEGATED_SUPER_TYPE_ENTRY, KtStubElementTypes.SUPER_TYPE_CALL_ENTRY, KtStubElementTypes.SUPER_TYPE_ENTRY);
    public static final TokenSet TYPE_ELEMENT_TYPES = TokenSet.create(KtStubElementTypes.USER_TYPE, KtStubElementTypes.NULLABLE_TYPE, KtStubElementTypes.FUNCTION_TYPE, KtStubElementTypes.DYNAMIC_TYPE, KtStubElementTypes.INTERSECTION_TYPE);
    public static final TokenSet INSIDE_DIRECTIVE_EXPRESSIONS = TokenSet.create(KtStubElementTypes.REFERENCE_EXPRESSION, KtStubElementTypes.DOT_QUALIFIED_EXPRESSION);
}
