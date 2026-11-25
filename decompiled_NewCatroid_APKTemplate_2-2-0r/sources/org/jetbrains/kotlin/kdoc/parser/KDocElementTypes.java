package org.jetbrains.kotlin.kdoc.parser;

import org.jetbrains.kotlin.kdoc.psi.impl.KDocName;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag;

/* JADX INFO: loaded from: classes5.dex */
public class KDocElementTypes {
    public static final KDocElementType KDOC_SECTION = new KDocElementType("KDOC_SECTION", KDocSection.class);
    public static final KDocElementType KDOC_TAG = new KDocElementType("KDOC_TAG", KDocTag.class);
    public static final KDocElementType KDOC_NAME = new KDocElementType("KDOC_NAME", KDocName.class);
}
