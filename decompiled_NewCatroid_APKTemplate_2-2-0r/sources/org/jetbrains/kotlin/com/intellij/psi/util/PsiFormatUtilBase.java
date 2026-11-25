package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiFormatUtilBase {
    public static final int JAVADOC_MODIFIERS_ONLY = 16384;
    public static final int MAX_PARAMS_TO_SHOW = 7;
    public static final int MODIFIERS_AFTER = 16;
    public static final int SHOW_ANONYMOUS_CLASS_VERBOSE = 32768;
    public static final int SHOW_CONTAINING_CLASS = 4096;
    public static final int SHOW_EXTENDS_IMPLEMENTS = 1024;
    public static final int SHOW_FQ_CLASS_NAMES = 8192;
    public static final int SHOW_FQ_NAME = 2048;
    public static final int SHOW_INITIALIZER = 128;
    public static final int SHOW_MODIFIERS = 8;
    public static final int SHOW_NAME = 1;
    public static final int SHOW_PACKAGE_LOCAL = 64;
    public static final int SHOW_PARAMETERS = 256;
    public static final int SHOW_RAW_NON_TOP_TYPE = 131072;
    public static final int SHOW_RAW_TYPE = 65536;
    public static final int SHOW_REDUNDANT_MODIFIERS = 32;
    public static final int SHOW_THROWS = 512;
    public static final int SHOW_TYPE = 2;
    public static final int TYPE_AFTER = 4;
    public static final int USE_INTERNAL_CANONICAL_TEXT = 262144;

    protected static void appendSpaceIfNeeded(StringBuilder sb) {
        if (sb.length() == 0 || StringUtil.endsWithChar(sb, io.netty.util.internal.StringUtil.SPACE)) {
            return;
        }
        sb.append(io.netty.util.internal.StringUtil.SPACE);
    }
}
