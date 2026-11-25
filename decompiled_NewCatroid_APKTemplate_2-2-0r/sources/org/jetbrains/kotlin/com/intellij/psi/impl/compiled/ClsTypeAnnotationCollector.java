package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeAnnotationContainer;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes3.dex */
class ClsTypeAnnotationCollector extends TypeAnnotationContainer.Collector {
    private final FirstPassData myFirstPassData;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CompilerOptions.INFO;
        } else {
            objArr[0] = "classInfo";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeAnnotationCollector";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ClsTypeAnnotationCollector(TypeInfo typeInfo, FirstPassData firstPassData) {
        super(typeInfo);
        if (typeInfo == null) {
            $$$reportNull$$$0(0);
        }
        if (firstPassData == null) {
            $$$reportNull$$$0(1);
        }
        this.myFirstPassData = firstPassData;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: add, reason: merged with bridge method [inline-methods] */
    public void m6601xb1f50699(TypePath typePath, String str) {
        byte[] bArrTranslatePath = translatePath(typePath);
        if (bArrTranslatePath != null) {
            add(bArrTranslatePath, str);
        }
    }

    AnnotationVisitor collect(final TypePath typePath, String str) {
        return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsTypeAnnotationCollector$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
            public final void consume(Object obj) {
                this.f$0.m6601xb1f50699(typePath, (String) obj);
            }
        });
    }

    private byte[] translatePath(TypePath typePath) {
        String strSubstring = this.myTypeInfo.text;
        int i = this.myTypeInfo.arrayCount + (this.myTypeInfo.isEllipsis ? 1 : 0);
        int innerDepth = this.myFirstPassData.getInnerDepth(PsiNameHelper.getQualifiedClassName(strSubstring, false));
        if (typePath == null) {
            if (innerDepth == 0 || i > 0) {
                return ArrayUtil.EMPTY_BYTE_ARRAY;
            }
            byte[] bArr = new byte[innerDepth];
            Arrays.fill(bArr, (byte) 1);
            return bArr;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = typePath.getLength();
        int i2 = 0;
        boolean z = false;
        while (i2 < length) {
            byte step = (byte) typePath.getStep(i2);
            if (step != 0) {
                if (step != 1) {
                    if (step != 2) {
                        if (step != 3) {
                            continue;
                        } else {
                            if (z || i > 0) {
                                return null;
                            }
                            while (true) {
                                int i3 = innerDepth - 1;
                                if (innerDepth <= 0) {
                                    break;
                                }
                                byteArrayOutputStream.write(1);
                                strSubstring = PsiNameHelper.getOuterClassReference(strSubstring);
                                innerDepth = i3;
                            }
                            int stepArgument = typePath.getStepArgument(i2);
                            String[] classParametersText = PsiNameHelper.getClassParametersText(strSubstring);
                            if (stepArgument >= classParametersText.length) {
                                return null;
                            }
                            TypeInfo typeInfoFromString = TypeInfo.fromString(classParametersText[stepArgument], false);
                            byte b = typeInfoFromString.arrayCount;
                            strSubstring = typeInfoFromString.text;
                            if (strSubstring.startsWith(PsiWildcardType.EXTENDS_PREFIX)) {
                                strSubstring = strSubstring.substring(PsiWildcardType.EXTENDS_PREFIX.length());
                            } else {
                                if (strSubstring.startsWith(PsiWildcardType.SUPER_PREFIX)) {
                                    z = z;
                                    strSubstring = strSubstring.substring(PsiWildcardType.SUPER_PREFIX.length());
                                }
                                z = z;
                                int innerDepth2 = this.myFirstPassData.getInnerDepth(PsiNameHelper.getQualifiedClassName(strSubstring, false));
                                byteArrayOutputStream.write(3);
                                byteArrayOutputStream.write(stepArgument);
                                i = b;
                                innerDepth = innerDepth2;
                            }
                            z = true;
                            z = z;
                            int innerDepth3 = this.myFirstPassData.getInnerDepth(PsiNameHelper.getQualifiedClassName(strSubstring, false));
                            byteArrayOutputStream.write(3);
                            byteArrayOutputStream.write(stepArgument);
                            i = b;
                            innerDepth = innerDepth3;
                        }
                    } else {
                        if (!z) {
                            return null;
                        }
                        byteArrayOutputStream.write(2);
                        z = false;
                    }
                } else {
                    if (innerDepth == 0) {
                        return null;
                    }
                    innerDepth--;
                }
            } else {
                if (i <= 0 || z) {
                    return null;
                }
                i--;
                byteArrayOutputStream.write(0);
            }
            i2++;
            z = z;
        }
        if (!z && i == 0) {
            while (true) {
                int i4 = innerDepth - 1;
                if (innerDepth <= 0) {
                    break;
                }
                byteArrayOutputStream.write(1);
                innerDepth = i4;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
