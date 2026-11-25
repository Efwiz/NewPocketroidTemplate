package org.eclipse.jdt.internal.compiler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes14.dex */
public class ManifestAnalyzer {
    private static final char[] CLASSPATH_HEADER_TOKEN = "Class-Path:".toCharArray();
    private static final int CONTINUING = 5;
    private static final int IN_CLASSPATH_HEADER = 1;
    private static final int PAST_CLASSPATH_HEADER = 2;
    private static final int READING_JAR = 4;
    private static final int SKIPPING_WHITESPACE = 3;
    private static final int SKIP_LINE = 6;
    private static final int START = 0;
    private ArrayList calledFilesNames;
    private int classpathSectionsCount;

    public boolean analyzeManifestContents(InputStream inputStream) throws IOException {
        char[] inputStreamAsCharArray = Util.getInputStreamAsCharArray(inputStream, -1, "UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        this.classpathSectionsCount = 0;
        this.calledFilesNames = null;
        int length = inputStreamAsCharArray.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            while (true) {
                if (i < length) {
                    int i4 = i + 1;
                    char c = inputStreamAsCharArray[i];
                    if (c != '\r' || i4 >= length) {
                        i = i4;
                    } else {
                        i += 2;
                        c = inputStreamAsCharArray[i4];
                    }
                    switch (i2) {
                        case 0:
                            if (c == CLASSPATH_HEADER_TOKEN[0]) {
                            }
                            i2 = 6;
                            break;
                        case 1:
                            if (c == '\n') {
                                i2 = 0;
                            } else {
                                char[] cArr = CLASSPATH_HEADER_TOKEN;
                                int i5 = i3 + 1;
                                if (c != cArr[i3]) {
                                    i2 = 6;
                                } else if (i5 == cArr.length) {
                                    i2 = 2;
                                }
                                i3 = i5;
                            }
                            break;
                        case 2:
                            if (c != ' ') {
                                return false;
                            }
                            this.classpathSectionsCount++;
                            i2 = 3;
                            break;
                            break;
                        case 3:
                            if (c == '\n') {
                                i2 = 5;
                            } else if (c != ' ') {
                                stringBuffer.append(c);
                                i2 = 4;
                            } else {
                                addCurrentTokenJarWhenNecessary(stringBuffer);
                            }
                            break;
                        case 4:
                            if (c == '\n') {
                                i2 = 5;
                            } else if (c != ' ') {
                                stringBuffer.append(c);
                            } else {
                                addCurrentTokenJarWhenNecessary(stringBuffer);
                                i2 = 3;
                            }
                            break;
                        case 5:
                            if (c == '\n') {
                                addCurrentTokenJarWhenNecessary(stringBuffer);
                            } else if (c != ' ') {
                                if (c == CLASSPATH_HEADER_TOKEN[0]) {
                                    addCurrentTokenJarWhenNecessary(stringBuffer);
                                }
                                if (this.calledFilesNames == null) {
                                    addCurrentTokenJarWhenNecessary(stringBuffer);
                                } else {
                                    addCurrentTokenJarWhenNecessary(stringBuffer);
                                    i2 = 6;
                                }
                            } else {
                                i2 = 3;
                            }
                            i2 = 0;
                            break;
                        case 6:
                            if (c == '\n') {
                                i2 = 0;
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    if (i2 == 2) {
                        return false;
                    }
                    if (i2 == 3) {
                        addCurrentTokenJarWhenNecessary(stringBuffer);
                        return true;
                    }
                    if (i2 == 4) {
                        return false;
                    }
                    if (i2 != 5) {
                        return (i2 == 6 && this.classpathSectionsCount != 0 && this.calledFilesNames == null) ? false : true;
                    }
                    addCurrentTokenJarWhenNecessary(stringBuffer);
                    return true;
                }
            }
            i2 = 1;
        }
    }

    private boolean addCurrentTokenJarWhenNecessary(StringBuffer stringBuffer) {
        if (stringBuffer == null || stringBuffer.length() <= 0) {
            return false;
        }
        if (this.calledFilesNames == null) {
            this.calledFilesNames = new ArrayList();
        }
        this.calledFilesNames.add(stringBuffer.toString());
        stringBuffer.setLength(0);
        return true;
    }

    public int getClasspathSectionsCount() {
        return this.classpathSectionsCount;
    }

    public List getCalledFileNames() {
        return this.calledFilesNames;
    }
}
