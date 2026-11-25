package org.jetbrains.kotlin.com.intellij.patterns;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PatternCondition<T> {
    private static final Logger LOG = Logger.getInstance((Class<?>) PatternCondition.class);
    private final String myDebugMethodName;

    public abstract boolean accepts(T t, ProcessingContext processingContext);

    public PatternCondition(String str) {
        this.myDebugMethodName = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendValue(StringBuilder sb, String str, Object obj) {
        if (obj instanceof ElementPattern) {
            ((ElementPattern) obj).getCondition().append(sb, str + "  ");
            return;
        }
        if (obj instanceof Object[]) {
            appendArray(sb, str, (Object[]) obj);
            return;
        }
        if (obj instanceof Collection) {
            appendArray(sb, str, ((Collection) obj).toArray());
        } else if (obj instanceof String) {
            sb.append('\"').append(obj).append('\"');
        } else {
            sb.append(obj);
        }
    }

    protected static void appendArray(StringBuilder sb, String str, Object[] objArr) {
        sb.append("[");
        int length = objArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            if (!z) {
                sb.append(", ");
            }
            appendValue(sb, str, obj);
            i++;
            z = false;
        }
        sb.append("]");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        append(sb, "");
        return sb.toString();
    }

    public void append(StringBuilder sb, String str) {
        sb.append(this.myDebugMethodName);
        sb.append("(");
        appendParams(sb, str);
        sb.append(Constant.AFTER_QUTO);
    }

    private void appendParams(final StringBuilder sb, final String str) {
        processParameters(new PairProcessor<String, Object>() { // from class: org.jetbrains.kotlin.com.intellij.patterns.PatternCondition.1
            int count;
            String prevName;
            int prevOffset;

            @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
            public boolean process(String str2, Object obj) {
                int i = this.count + 1;
                this.count = i;
                if (i == 2) {
                    sb.insert(this.prevOffset, this.prevName + ContainerUtils.KEY_VALUE_DELIMITER);
                }
                if (this.count > 1) {
                    sb.append(", ");
                }
                this.prevOffset = sb.length();
                if (this.count > 1) {
                    sb.append(str2).append(ContainerUtils.KEY_VALUE_DELIMITER);
                }
                PatternCondition.appendValue(sb, str, obj);
                this.prevName = str2;
                return true;
            }
        });
    }

    public boolean processParameters(PairProcessor<? super String, Object> pairProcessor) {
        for (Class<?> superclass = getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && ((!field.isSynthetic() && !superclass.equals(PatternCondition.class)) || field.getName().startsWith("val$"))) {
                    String name2 = field.getName();
                    if (name2.startsWith("val$")) {
                        name2 = name2.substring("val$".length());
                    }
                    if (!pairProcessor.process(name2, getFieldValue(field))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Object getFieldValue(Field field) {
        boolean zIsAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return field.get(this);
        } catch (IllegalAccessException e) {
            LOG.error((Throwable) e);
            return null;
        } finally {
            field.setAccessible(zIsAccessible);
        }
    }
}
