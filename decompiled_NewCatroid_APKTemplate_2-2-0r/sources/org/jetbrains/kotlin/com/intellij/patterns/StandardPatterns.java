package org.jetbrains.kotlin.com.intellij.patterns;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public class StandardPatterns {
    private static final FalsePattern FALSE_PATTERN = new FalsePattern(null);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 5 || i == 6) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "aClass";
        } else if (i == 2) {
            objArr[0] = "classes";
        } else if (i == 3) {
            objArr[0] = "value";
        } else if (i == 5) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/StandardPatterns";
        } else {
            objArr[0] = "patterns";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/StandardPatterns";
                break;
            case 4:
                objArr[1] = "object";
                break;
            case 7:
                objArr[1] = "and";
                break;
            case 8:
                objArr[1] = "alwaysFalse";
                break;
            default:
                objArr[1] = "string";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[2] = "instanceOf";
        } else if (i == 3) {
            objArr[2] = "object";
        } else if (i == 5) {
            objArr[2] = "get";
        } else if (i == 6) {
            objArr[2] = "or";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static StringPattern string() {
        StringPattern stringPattern = StringPattern.STRING_PATTERN;
        if (stringPattern == null) {
            $$$reportNull$$$0(0);
        }
        return stringPattern;
    }

    public static <T> ObjectPattern.Capture<T> instanceOf(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        return new ObjectPattern.Capture<>(cls);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.patterns.StandardPatterns$1, reason: invalid class name */
    class AnonymousClass1 extends InitialPatternCondition {
        final /* synthetic */ Key val$key;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/patterns/StandardPatterns$1", "append"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
        public boolean accepts(Object obj, ProcessingContext processingContext) {
            processingContext.put((Key<Object>) this.val$key, obj);
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
        public void append(StringBuilder sb, String str) {
            if (sb == null) {
                $$$reportNull$$$0(0);
            }
            sb.append("save(").append(this.val$key).append(Constant.AFTER_QUTO);
        }
    }

    @SafeVarargs
    public static <E> ElementPattern<E> or(final ElementPattern<? extends E>... elementPatternArr) {
        if (elementPatternArr == null) {
            $$$reportNull$$$0(6);
        }
        return new ObjectPattern.Capture(new InitialPatternConditionPlus(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.StandardPatterns.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/patterns/StandardPatterns$3", "append"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                for (ElementPattern elementPattern : elementPatternArr) {
                    if (elementPattern.accepts(obj, processingContext)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public void append(StringBuilder sb, String str) {
                if (sb == null) {
                    $$$reportNull$$$0(0);
                }
                ElementPattern[] elementPatternArr2 = elementPatternArr;
                int length = elementPatternArr2.length;
                boolean z = true;
                int i = 0;
                while (i < length) {
                    ElementPattern elementPattern = elementPatternArr2[i];
                    if (!z) {
                        sb.append("\n").append(str);
                    }
                    elementPattern.getCondition().append(sb, str + "  ");
                    i++;
                    z = false;
                }
            }
        });
    }

    public static <E> ObjectPattern.Capture<E> not(final ElementPattern<E> elementPattern) {
        return new ObjectPattern.Capture<>(new InitialPatternConditionPlus(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.StandardPatterns.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "builder", "org/jetbrains/kotlin/com/intellij/patterns/StandardPatterns$5", "append"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public boolean accepts(Object obj, ProcessingContext processingContext) {
                return !elementPattern.accepts(obj, processingContext);
            }

            @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
            public void append(StringBuilder sb, String str) {
                if (sb == null) {
                    $$$reportNull$$$0(0);
                }
                elementPattern.getCondition().append(sb.append("not("), str + "  ");
                sb.append(Constant.AFTER_QUTO);
            }
        });
    }

    private static final class FalsePattern implements ElementPattern {
        @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
        public boolean accepts(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
        public boolean accepts(Object obj, ProcessingContext processingContext) {
            return false;
        }

        private FalsePattern() {
        }

        /* synthetic */ FalsePattern(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // org.jetbrains.kotlin.com.intellij.patterns.ElementPattern
        public ElementPatternCondition getCondition() {
            return new ElementPatternCondition(new InitialPatternCondition(Object.class) { // from class: org.jetbrains.kotlin.com.intellij.patterns.StandardPatterns.FalsePattern.1
                @Override // org.jetbrains.kotlin.com.intellij.patterns.InitialPatternCondition
                public boolean accepts(Object obj, ProcessingContext processingContext) {
                    return false;
                }
            });
        }
    }
}
