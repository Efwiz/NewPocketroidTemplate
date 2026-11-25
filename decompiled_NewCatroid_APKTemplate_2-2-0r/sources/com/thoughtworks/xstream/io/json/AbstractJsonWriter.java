package com.thoughtworks.xstream.io.json;

import com.android.dex.DexFormat;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.io.AbstractWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.slf4j.Logger;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/AbstractJsonWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractJsonWriter extends AbstractWriter {
    public static final int DROP_ROOT_MODE = 1;
    public static final int EXPLICIT_MODE = 4;
    public static final int IEEE_754_MODE = 8;
    private static final Set NUMBER_TYPES;
    private static final int STATE_END_ATTRIBUTES = 32;
    private static final int STATE_END_ELEMENTS = 256;
    private static final int STATE_END_OBJECT = 2;
    private static final int STATE_NEXT_ATTRIBUTE = 16;
    private static final int STATE_NEXT_ELEMENT = 128;
    private static final int STATE_ROOT = 1;
    private static final int STATE_SET_VALUE = 512;
    private static final int STATE_START_ATTRIBUTES = 8;
    private static final int STATE_START_ELEMENTS = 64;
    private static final int STATE_START_OBJECT = 4;
    public static final int STRICT_MODE = 2;
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;
    static /* synthetic */ Class class$java$io$Externalizable;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$math$BigInteger;
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$java$util$Map$Entry;
    private int expectedStates;
    private int mode;
    private FastStack stack;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/AbstractJsonWriter$Type.class
      classes11.dex
     */
    public static class Type {
        public static Type NULL = new Type();
        public static Type STRING = new Type();
        public static Type NUMBER = new Type();
        public static Type BOOLEAN = new Type();
    }

    protected abstract void addLabel(String str);

    protected abstract void addValue(String str, Type type);

    protected abstract void endArray();

    protected abstract void endObject();

    protected abstract void nextElement();

    protected abstract void startArray();

    protected abstract void startObject();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/AbstractJsonWriter$StackElement.class
      classes11.dex
     */
    private static class StackElement {
        int status;
        final Class type;

        public StackElement(Class cls, int i) {
            this.type = cls;
            this.status = i;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/AbstractJsonWriter$IllegalWriterStateException.class
      classes11.dex
     */
    private static class IllegalWriterStateException extends IllegalStateException {
        public IllegalWriterStateException(int i, int i2, String str) {
            super(new StringBuffer("Cannot turn from state ").append(getState(i)).append(" into state ").append(getState(i2)).append(str == null ? "" : new StringBuffer(" for property ").append(str).toString()).toString());
        }

        private static String getState(int i) {
            if (i == 1) {
                return Logger.ROOT_LOGGER_NAME;
            }
            if (i == 2) {
                return "END_OBJECT";
            }
            if (i == 4) {
                return "START_OBJECT";
            }
            if (i == 8) {
                return "START_ATTRIBUTES";
            }
            if (i == 16) {
                return "NEXT_ATTRIBUTE";
            }
            if (i == 32) {
                return "END_ATTRIBUTES";
            }
            if (i == 64) {
                return "START_ELEMENTS";
            }
            if (i == 128) {
                return "NEXT_ELEMENT";
            }
            if (i == 256) {
                return "END_ELEMENTS";
            }
            if (i == 512) {
                return "SET_VALUE";
            }
            throw new IllegalArgumentException(new StringBuffer("Unknown state provided: ").append(i).append(", cannot create message for IllegalWriterStateException").toString());
        }
    }

    static {
        Class[] clsArr = new Class[14];
        clsArr[0] = Byte.TYPE;
        Class clsClass$ = class$java$lang$Byte;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_BYTE);
            class$java$lang$Byte = clsClass$;
        }
        clsArr[1] = clsClass$;
        clsArr[2] = Short.TYPE;
        Class clsClass$2 = class$java$lang$Short;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_SHORT);
            class$java$lang$Short = clsClass$2;
        }
        clsArr[3] = clsClass$2;
        clsArr[4] = Integer.TYPE;
        Class clsClass$3 = class$java$lang$Integer;
        if (clsClass$3 == null) {
            clsClass$3 = class$(CommonClassNames.JAVA_LANG_INTEGER);
            class$java$lang$Integer = clsClass$3;
        }
        clsArr[5] = clsClass$3;
        clsArr[6] = Long.TYPE;
        Class clsClass$4 = class$java$lang$Long;
        if (clsClass$4 == null) {
            clsClass$4 = class$(CommonClassNames.JAVA_LANG_LONG);
            class$java$lang$Long = clsClass$4;
        }
        clsArr[7] = clsClass$4;
        clsArr[8] = Float.TYPE;
        Class clsClass$5 = class$java$lang$Float;
        if (clsClass$5 == null) {
            clsClass$5 = class$(CommonClassNames.JAVA_LANG_FLOAT);
            class$java$lang$Float = clsClass$5;
        }
        clsArr[9] = clsClass$5;
        clsArr[10] = Double.TYPE;
        Class clsClass$6 = class$java$lang$Double;
        if (clsClass$6 == null) {
            clsClass$6 = class$(CommonClassNames.JAVA_LANG_DOUBLE);
            class$java$lang$Double = clsClass$6;
        }
        clsArr[11] = clsClass$6;
        Class clsClass$7 = class$java$math$BigInteger;
        if (clsClass$7 == null) {
            clsClass$7 = class$("java.math.BigInteger");
            class$java$math$BigInteger = clsClass$7;
        }
        clsArr[12] = clsClass$7;
        Class clsClass$8 = class$java$math$BigDecimal;
        if (clsClass$8 == null) {
            clsClass$8 = class$("java.math.BigDecimal");
            class$java$math$BigDecimal = clsClass$8;
        }
        clsArr[13] = clsClass$8;
        NUMBER_TYPES = new HashSet(Arrays.asList(clsArr));
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public AbstractJsonWriter() {
        this(new NoNameCoder());
    }

    public AbstractJsonWriter(int i) {
        this(i, new NoNameCoder());
    }

    public AbstractJsonWriter(NameCoder nameCoder) {
        this(0, nameCoder);
    }

    public AbstractJsonWriter(int i, NameCoder nameCoder) {
        super(nameCoder);
        FastStack fastStack = new FastStack(16);
        this.stack = fastStack;
        this.mode = (i & 4) > 0 ? 4 : i;
        fastStack.push(new StackElement(null, 1));
        this.expectedStates = 4;
    }

    @Override // com.thoughtworks.xstream.io.AbstractWriter, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        if (str == null) {
            throw new NullPointerException("name");
        }
        this.stack.push(new StackElement(cls, ((StackElement) this.stack.peek()).status));
        handleCheckedStateTransition(4, str, null);
        this.expectedStates = 661;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        startNode(str, null);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        handleCheckedStateTransition(16, str, str2);
        this.expectedStates = 661;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) throws Throwable {
        Class cls = ((StackElement) this.stack.peek()).type;
        Class clsClass$ = class$java$lang$Character;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_CHARACTER);
            class$java$lang$Character = clsClass$;
        }
        if ((cls == clsClass$ || cls == Character.TYPE) && "".equals(str)) {
            str = DexFormat.MAGIC_SUFFIX;
        }
        handleCheckedStateTransition(512, null, str);
        this.expectedStates = 129;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        int size = this.stack.size();
        int i = size > 2 ? 128 : 1;
        handleCheckedStateTransition(i, null, null);
        this.stack.pop();
        ((StackElement) this.stack.peek()).status = i;
        this.expectedStates = 4;
        if (size > 2) {
            this.expectedStates = 4 | 129;
        }
    }

    private void handleCheckedStateTransition(int i, String str, String str2) {
        StackElement stackElement = (StackElement) this.stack.peek();
        if ((this.expectedStates & i) == 0) {
            throw new IllegalWriterStateException(stackElement.status, i, str);
        }
        stackElement.status = handleStateTransition(stackElement.status, i, str, str2);
    }

    /* JADX WARN: Code duplicated, block: B:122:0x0189  */
    /* JADX WARN: Code duplicated, block: B:255:0x0338  */
    /* JADX WARN: Code duplicated, block: B:85:0x0113  */
    private int handleStateTransition(int i, int i2, String str, String str2) throws Throwable {
        String str3;
        int i3;
        int i4;
        int i5;
        int i6;
        int size = this.stack.size();
        Class cls = ((StackElement) this.stack.peek()).type;
        boolean z = false;
        boolean z2 = size > 1 && isArray(cls);
        if (size > 1 && isArray(((StackElement) this.stack.get(size - 2)).type)) {
            z = true;
        }
        if (i == 1) {
            if (i2 == 4) {
                handleStateTransition(64, 4, str, null);
                return i2;
            }
            throw new IllegalWriterStateException(i, i2, str);
        }
        if (i == 2) {
            if (i2 == 1) {
                int i7 = this.mode;
                if (((i7 & 1) == 0 || size > 2) && (i7 & 4) == 0) {
                    endObject();
                }
                return i2;
            }
            if (i2 == 4) {
                handleStateTransition(handleStateTransition(i, 128, null, null), 4, str, null);
                return i2;
            }
            if (i2 == 128) {
                nextElement();
                return i2;
            }
            throw new IllegalWriterStateException(i, i2, str);
        }
        if (i == 4) {
            if (i2 != 1 && i2 != 4) {
                if (i2 == 8) {
                    if ((this.mode & 4) != 0) {
                        startArray();
                    }
                    return i2;
                }
                if (i2 == 16) {
                    if ((this.mode & 4) == 0 && z2) {
                        return 4;
                    }
                    handleStateTransition(handleStateTransition(i, 8, null, null), 16, str, str2);
                    return i2;
                }
                if (i2 != 128 && i2 != 512) {
                    throw new IllegalWriterStateException(i, i2, str);
                }
            }
            if (z && (this.mode & 4) == 0) {
                i3 = 1;
                str3 = null;
            } else {
                str3 = null;
                handleStateTransition(handleStateTransition(i, 8, null, null), 32, null, null);
                i3 = 1;
            }
            if (i2 == i3) {
                handleStateTransition(handleStateTransition(64, 512, str3, str3), i2, str3, str3);
            } else if (i2 == 4) {
                handleStateTransition(64, 4, str, str3);
            } else if (i2 == 128) {
                handleStateTransition(handleStateTransition(64, 512, str3, str3), i2, str3, str3);
            } else if (i2 == 512) {
                handleStateTransition(64, 512, str3, str2);
            }
            return i2;
        }
        if (i != 8) {
            if (i != 16) {
                if (i == 32) {
                    if (i2 == 2) {
                        handleStateTransition(handleStateTransition(64, 256, null, null), 2, null, null);
                    } else if (i2 == 64) {
                        if ((this.mode & 4) == 0) {
                            nextElement();
                        }
                    } else {
                        throw new IllegalWriterStateException(i, i2, str);
                    }
                    return i2;
                }
                if (i != 64) {
                    if (i != 128) {
                        if (i == 256) {
                            if (i2 == 2) {
                                if ((this.mode & 4) != 0) {
                                    endArray();
                                    endArray();
                                    endObject();
                                }
                                return i2;
                            }
                            throw new IllegalWriterStateException(i, i2, str);
                        }
                        if (i != 512) {
                            throw new IllegalWriterStateException(i, i2, str);
                        }
                        if (i2 == 1) {
                            handleStateTransition(handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null), 1, null, null);
                            return i2;
                        }
                        if (i2 == 128) {
                            handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null);
                            return i2;
                        }
                        if (i2 == 256) {
                            if ((this.mode & 4) == 0 && z2) {
                                endArray();
                            }
                            return i2;
                        }
                        throw new IllegalWriterStateException(i, i2, str);
                    }
                    if (i2 == 1) {
                        handleStateTransition(handleStateTransition(i, 2, null, null), 1, null, null);
                        return i2;
                    }
                    if (i2 != 2) {
                        if (i2 == 4) {
                            nextElement();
                            if (z) {
                                i5 = 4;
                            } else {
                                if ((this.mode & 4) == 0) {
                                    addLabel(encodeNode(str));
                                    if ((this.mode & 4) == 0 && z2) {
                                        startArray();
                                    }
                                    return i2;
                                }
                                i5 = 4;
                            }
                        } else if (i2 != 128) {
                            if (i2 == 256) {
                                if ((this.mode & 4) == 0 && z2) {
                                    endArray();
                                }
                                return i2;
                            }
                            throw new IllegalWriterStateException(i, i2, str);
                        }
                    }
                    handleStateTransition(handleStateTransition(i, 256, null, null), 2, null, null);
                    if ((this.mode & 4) == 0 && !z2) {
                        endObject();
                    }
                    return i2;
                }
                i5 = 4;
                if (i2 == i5) {
                    int i8 = this.mode;
                    if ((i8 & 1) == 0 || size > 2) {
                        if (!z || (i8 & 4) != 0) {
                            if (!"".equals(str2)) {
                                startObject();
                            }
                            addLabel(encodeNode(str));
                        }
                        i6 = 4;
                        if ((this.mode & 4) != 0) {
                            startArray();
                        }
                    } else {
                        i6 = 4;
                    }
                    if ((this.mode & i6) == 0 && z2) {
                        startArray();
                    }
                    return i2;
                }
                if (i2 == 128 || i2 == 256) {
                    if ((this.mode & 4) == 0) {
                        if (z2) {
                            endArray();
                        } else {
                            endObject();
                        }
                    }
                    return i2;
                }
                if (i2 == 512) {
                    int i9 = this.mode;
                    if ((i9 & 2) != 0 && size == 2) {
                        throw new ConversionException("Single value cannot be root element");
                    }
                    if (str2 == null) {
                        Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
                        if (clsClass$ == null) {
                            clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                            class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
                        }
                        if (cls == clsClass$) {
                            addValue("null", Type.NULL);
                        } else if ((this.mode & 4) == 0 && !z2) {
                            startObject();
                            endObject();
                        }
                    } else if ((i9 & 8) != 0) {
                        if (cls != Long.TYPE) {
                            Class clsClass$2 = class$java$lang$Long;
                            if (clsClass$2 == null) {
                                clsClass$2 = class$(CommonClassNames.JAVA_LANG_LONG);
                                class$java$lang$Long = clsClass$2;
                            }
                            if (cls != clsClass$2) {
                                addValue(str2, getType(cls));
                            }
                        }
                        long j = Long.parseLong(str2);
                        if (j > 9007199254740992L || j < -9007199254740992L) {
                            addValue(str2, Type.STRING);
                        } else {
                            addValue(str2, getType(cls));
                        }
                    } else {
                        addValue(str2, getType(cls));
                    }
                    return i2;
                }
                throw new IllegalWriterStateException(i, i2, str);
            }
        } else if (i2 == 16) {
            if (str != null) {
                String string = new StringBuffer().append((this.mode & 4) == 0 ? PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT : "").append(str).toString();
                startObject();
                addLabel(encodeAttribute(string));
                addValue(str2, Type.STRING);
            }
            return i2;
        }
        if (i2 != 1) {
            if (i2 != 4) {
                if (i2 == 16) {
                    if (z2) {
                        i4 = 4;
                        if ((this.mode & 4) != 0) {
                        }
                        return i2;
                    }
                    i4 = 4;
                    nextElement();
                    addLabel(encodeAttribute(new StringBuffer().append((i4 & this.mode) == 0 ? PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT : "").append(str).toString()));
                    addValue(str2, Type.STRING);
                    return i2;
                }
                if (i2 == 32) {
                    if ((this.mode & 4) != 0) {
                        if (i == 16) {
                            endObject();
                        }
                        endArray();
                        nextElement();
                        startArray();
                    }
                    return i2;
                }
                if (i2 == 128) {
                    handleStateTransition(handleStateTransition(i, 32, null, null), 2, null, null);
                    return i2;
                }
                if (i2 != 512) {
                    throw new IllegalWriterStateException(i, i2, str);
                }
            }
            int iHandleStateTransition = handleStateTransition(handleStateTransition(i, 32, null, null), 64, null, null);
            if (i2 == 2) {
                handleStateTransition(handleStateTransition(iHandleStateTransition, 512, null, null), 2, null, null);
            } else if (i2 == 4) {
                handleStateTransition(iHandleStateTransition, 4, str, (this.mode & 4) == 0 ? "" : null);
            } else if (i2 == 512) {
                if ((this.mode & 4) == 0) {
                    addLabel(encodeNode("$"));
                }
                handleStateTransition(iHandleStateTransition, 512, null, str2);
                if ((this.mode & 4) == 0) {
                    endObject();
                }
            }
            return i2;
        }
        handleStateTransition(handleStateTransition(handleStateTransition(i, 32, null, null), 2, null, null), 1, null, null);
        return i2;
    }

    protected Type getType(Class cls) throws Throwable {
        Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
        if (clsClass$ == null) {
            clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
            class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
        }
        if (cls == clsClass$) {
            return Type.NULL;
        }
        Class clsClass$2 = class$java$lang$Boolean;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_BOOLEAN);
            class$java$lang$Boolean = clsClass$2;
        }
        if (cls == clsClass$2 || cls == Boolean.TYPE) {
            return Type.BOOLEAN;
        }
        return NUMBER_TYPES.contains(cls) ? Type.NUMBER : Type.STRING;
    }

    protected boolean isArray(Class cls) throws Throwable {
        if (cls != null) {
            if (!cls.isArray()) {
                Class clsClass$ = class$java$util$Collection;
                if (clsClass$ == null) {
                    clsClass$ = class$(CommonClassNames.JAVA_UTIL_COLLECTION);
                    class$java$util$Collection = clsClass$;
                }
                if (!clsClass$.isAssignableFrom(cls)) {
                    Class clsClass$2 = class$java$io$Externalizable;
                    if (clsClass$2 == null) {
                        clsClass$2 = class$(CommonClassNames.JAVA_IO_EXTERNALIZABLE);
                        class$java$io$Externalizable = clsClass$2;
                    }
                    if (!clsClass$2.isAssignableFrom(cls)) {
                        Class clsClass$3 = class$java$util$Map;
                        if (clsClass$3 == null) {
                            clsClass$3 = class$(CommonClassNames.JAVA_UTIL_MAP);
                            class$java$util$Map = clsClass$3;
                        }
                        if (!clsClass$3.isAssignableFrom(cls)) {
                            Class clsClass$4 = class$java$util$Map$Entry;
                            if (clsClass$4 == null) {
                                clsClass$4 = class$("java.util.Map$Entry");
                                class$java$util$Map$Entry = clsClass$4;
                            }
                            if (clsClass$4.isAssignableFrom(cls)) {
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
