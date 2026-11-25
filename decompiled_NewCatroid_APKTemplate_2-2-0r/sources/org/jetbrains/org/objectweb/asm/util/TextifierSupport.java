package org.jetbrains.org.objectweb.asm.util;

import java.util.Map;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: loaded from: classes6.dex */
public interface TextifierSupport {
    void textify(StringBuilder sb, Map<Label, String> map);
}
