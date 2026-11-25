package org.jetbrains.kotlin.org.jline.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

/* JADX INFO: loaded from: classes4.dex */
public final class Curses {
    private static Object[] sv = new Object[26];
    private static Object[] dv = new Object[26];

    /* JADX WARN: Code duplicated, block: B:52:0x00aa A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:53:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:55:0x00b4  */
    public static void tputs(Writer writer, String str, Object... objArr) throws IOException {
        int length = str.length();
        Stack stack = new Stack();
        boolean z = true;
        int i = 0;
        while (true) {
            char c = 0;
            while (i < length) {
                int i2 = i + 1;
                char cCharAt = str.charAt(i);
                if (cCharAt != '$') {
                    if (cCharAt == '%') {
                        int i3 = i + 2;
                        char cCharAt2 = str.charAt(i2);
                        if (cCharAt2 != '!') {
                            if (cCharAt2 != '-') {
                                if (cCharAt2 != '/') {
                                    if (cCharAt2 != 'A') {
                                        if (cCharAt2 != '^') {
                                            if (cCharAt2 == 'g') {
                                                i += 3;
                                                char cCharAt3 = str.charAt(i3);
                                                if (cCharAt3 < 'a' || cCharAt3 > 'z') {
                                                    if (cCharAt3 < 'A' || cCharAt3 > 'Z') {
                                                        throw new IllegalArgumentException();
                                                    }
                                                    if (z) {
                                                        stack.push(sv[cCharAt3 - 'A']);
                                                    }
                                                } else if (z) {
                                                    stack.push(dv[cCharAt3 - 'a']);
                                                }
                                            } else if (cCharAt2 == 'i') {
                                                if (objArr.length >= 1) {
                                                    objArr[0] = Integer.valueOf(toInteger(objArr[0]) + 1);
                                                }
                                                if (objArr.length >= 2) {
                                                    objArr[1] = Integer.valueOf(toInteger(objArr[1]) + 1);
                                                }
                                            } else if (cCharAt2 == 'p') {
                                                i += 3;
                                                char cCharAt4 = str.charAt(i3);
                                                if (z) {
                                                    stack.push(objArr[cCharAt4 - '1']);
                                                }
                                            } else if (cCharAt2 == 't') {
                                                if (c != 1 && c != 3) {
                                                    throw new IllegalArgumentException();
                                                }
                                                z = toInteger(stack.pop()) != 0;
                                                i = i3;
                                                c = 2;
                                            } else if (cCharAt2 != '~') {
                                                if (cCharAt2 != '*') {
                                                    if (cCharAt2 != '+') {
                                                        if (cCharAt2 != 'O') {
                                                            if (cCharAt2 == 'P') {
                                                                i += 3;
                                                                char cCharAt5 = str.charAt(i3);
                                                                if (cCharAt5 < 'a' || cCharAt5 > 'z') {
                                                                    if (cCharAt5 < 'A' || cCharAt5 > 'Z') {
                                                                        throw new IllegalArgumentException();
                                                                    }
                                                                    if (z) {
                                                                        sv[cCharAt5 - 'A'] = stack.pop();
                                                                    }
                                                                } else if (z) {
                                                                    dv[cCharAt5 - 'a'] = stack.pop();
                                                                }
                                                            } else if (cCharAt2 == 'd') {
                                                                writer.write(Integer.toString(toInteger(stack.pop())));
                                                            } else if (cCharAt2 == 'e') {
                                                                if (c != 2) {
                                                                    throw new IllegalArgumentException();
                                                                }
                                                                z = !z;
                                                                i = i3;
                                                                c = 3;
                                                            } else if (cCharAt2 != 'l') {
                                                                if (cCharAt2 != 'm') {
                                                                    if (cCharAt2 == '{') {
                                                                        int i4 = i3;
                                                                        while (true) {
                                                                            i = i4 + 1;
                                                                            if (str.charAt(i4) != '}') {
                                                                                i4 = i;
                                                                            } else if (z) {
                                                                                stack.push(Integer.valueOf(Integer.valueOf(str.substring(i3, i4)).intValue()));
                                                                            }
                                                                        }
                                                                    } else if (cCharAt2 != '|') {
                                                                        switch (cCharAt2) {
                                                                            case '%':
                                                                                if (z) {
                                                                                    writer.write(37);
                                                                                }
                                                                                break;
                                                                            case '&':
                                                                                if (z) {
                                                                                    stack.push(Integer.valueOf(toInteger(stack.pop()) & toInteger(stack.pop())));
                                                                                }
                                                                                break;
                                                                            case '\'':
                                                                                int i5 = i + 3;
                                                                                char cCharAt6 = str.charAt(i3);
                                                                                if (z) {
                                                                                    stack.push(Integer.valueOf(cCharAt6));
                                                                                }
                                                                                i += 4;
                                                                                if (str.charAt(i5) != '\'') {
                                                                                    throw new IllegalArgumentException();
                                                                                }
                                                                                break;
                                                                            default:
                                                                                switch (cCharAt2) {
                                                                                    case ';':
                                                                                        if (c == 0 || c == 1) {
                                                                                            throw new IllegalArgumentException();
                                                                                        }
                                                                                        i = i3;
                                                                                        break;
                                                                                    case '<':
                                                                                        if (z) {
                                                                                            stack.push(Boolean.valueOf(toInteger(stack.pop()) < toInteger(stack.pop())));
                                                                                        }
                                                                                        break;
                                                                                    case '=':
                                                                                        if (z) {
                                                                                            stack.push(Boolean.valueOf(toInteger(stack.pop()) == toInteger(stack.pop())));
                                                                                        }
                                                                                        break;
                                                                                    case '>':
                                                                                        if (z) {
                                                                                            stack.push(Boolean.valueOf(toInteger(stack.pop()) > toInteger(stack.pop())));
                                                                                        }
                                                                                        break;
                                                                                    case '?':
                                                                                        if (c != 0) {
                                                                                            throw new IllegalArgumentException();
                                                                                        }
                                                                                        c = 1;
                                                                                        i = i3;
                                                                                        break;
                                                                                        break;
                                                                                    default:
                                                                                        throw new UnsupportedOperationException();
                                                                                }
                                                                                break;
                                                                        }
                                                                    } else if (z) {
                                                                        stack.push(Integer.valueOf(toInteger(stack.pop()) | toInteger(stack.pop())));
                                                                    }
                                                                } else if (z) {
                                                                    stack.push(Integer.valueOf(toInteger(stack.pop()) % toInteger(stack.pop())));
                                                                }
                                                            } else if (z) {
                                                                stack.push(Integer.valueOf(stack.pop().toString().length()));
                                                            }
                                                        } else if (z) {
                                                            stack.push(Boolean.valueOf((toInteger(stack.pop()) == 0 && toInteger(stack.pop()) == 0) ? false : true));
                                                        }
                                                    } else if (z) {
                                                        stack.push(Integer.valueOf(toInteger(stack.pop()) + toInteger(stack.pop())));
                                                    }
                                                } else if (z) {
                                                    stack.push(Integer.valueOf(toInteger(stack.pop()) * toInteger(stack.pop())));
                                                }
                                            } else if (z) {
                                                stack.push(Integer.valueOf(~toInteger(stack.pop())));
                                            }
                                        } else if (z) {
                                            stack.push(Integer.valueOf(toInteger(stack.pop()) ^ toInteger(stack.pop())));
                                        }
                                    } else if (z) {
                                        stack.push(Boolean.valueOf((toInteger(stack.pop()) == 0 || toInteger(stack.pop()) == 0) ? false : true));
                                    }
                                } else if (z) {
                                    stack.push(Integer.valueOf(toInteger(stack.pop()) / toInteger(stack.pop())));
                                }
                            } else if (z) {
                                stack.push(Integer.valueOf(toInteger(stack.pop()) - toInteger(stack.pop())));
                            }
                        } else if (z) {
                            stack.push(Boolean.valueOf(toInteger(stack.pop()) == 0));
                        }
                        i = i3;
                    } else if (cCharAt == '\\') {
                        i += 2;
                        char cCharAt7 = str.charAt(i2);
                        if (cCharAt7 >= '0' && cCharAt7 <= '9') {
                            throw new UnsupportedOperationException();
                        }
                        if (cCharAt7 == ':') {
                            if (z) {
                                writer.write(cCharAt7);
                            }
                        } else if (cCharAt7 == 'E') {
                            if (z) {
                                writer.write(27);
                            }
                        } else if (cCharAt7 == '\\' || cCharAt7 == '^') {
                            if (z) {
                                writer.write(cCharAt7);
                            }
                        } else if (cCharAt7 != 'b') {
                            if (cCharAt7 == 'n') {
                                writer.write(10);
                            } else if (cCharAt7 != 'e') {
                                if (cCharAt7 != 'f') {
                                    switch (cCharAt7) {
                                        case 'r':
                                            if (z) {
                                                writer.write(13);
                                            }
                                            break;
                                        case 's':
                                            if (z) {
                                                writer.write(32);
                                            }
                                            break;
                                        case 't':
                                            if (z) {
                                                writer.write(9);
                                            }
                                            break;
                                        default:
                                            throw new IllegalArgumentException();
                                    }
                                } else if (z) {
                                    writer.write(12);
                                }
                            } else if (z) {
                                writer.write(27);
                            }
                        } else if (z) {
                            writer.write(8);
                        }
                    } else if (cCharAt == '^') {
                        i += 2;
                        char cCharAt8 = str.charAt(i2);
                        if (z) {
                            writer.write(cCharAt8 - '@');
                        }
                    } else if (z) {
                        writer.write(cCharAt);
                    }
                } else if (str.charAt(i2) == '<') {
                    int i6 = 0;
                    while (true) {
                        int i7 = i2 + 1;
                        char cCharAt9 = str.charAt(i7);
                        if (cCharAt9 != '>') {
                            if (cCharAt9 >= '0' && cCharAt9 <= '9') {
                                i6 = (i6 * 10) + (cCharAt9 - '0');
                            }
                            i2 = i7;
                        } else {
                            i2 += 2;
                            try {
                                writer.flush();
                                Thread.sleep(i6);
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                } else if (z) {
                    writer.write(cCharAt);
                }
                i = i2;
            }
            return;
        }
    }

    private static int toInteger(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? 1 : 0;
        }
        return Integer.valueOf(obj.toString()).intValue();
    }
}
