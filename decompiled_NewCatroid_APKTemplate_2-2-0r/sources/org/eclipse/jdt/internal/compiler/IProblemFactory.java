package org.eclipse.jdt.internal.compiler;

import java.util.Locale;
import org.eclipse.jdt.core.compiler.CategorizedProblem;

/* JADX INFO: loaded from: classes14.dex */
public interface IProblemFactory {
    CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, int i2, String[] strArr2, int i3, int i4, int i5, int i6, int i7);

    CategorizedProblem createProblem(char[] cArr, int i, String[] strArr, String[] strArr2, int i2, int i3, int i4, int i5, int i6);

    Locale getLocale();

    String getLocalizedMessage(int i, int i2, String[] strArr);

    String getLocalizedMessage(int i, String[] strArr);
}
