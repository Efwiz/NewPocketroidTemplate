package com.reandroid.jcommand;

import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.LastArgs;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.utils.CommandUtil;
import com.reandroid.jcommand.utils.ReflectionUtil;
import com.reandroid.jcommand.utils.TwoColumnTable;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class OptionStringBuilder {
    private Field mLastArgs;
    private final List<Field> optionArgFieldsList;
    private final Object optionObject;
    private final TwoColumnTable twoColumnTable;

    public OptionStringBuilder(Object obj) {
        this.optionObject = obj;
        TwoColumnTable twoColumnTable = new TwoColumnTable();
        this.twoColumnTable = twoColumnTable;
        this.optionArgFieldsList = new ArrayList();
        twoColumnTable.setColumnSeparator(" = ");
    }

    public void setMaxWidth(int i) {
        getTable().setMaxWidth(i);
    }

    public void setTab2(String str) {
        getTable().setTab2(str);
    }

    public void setColumnSeparator(String str) {
        getTable().setColumnSeparator(str);
    }

    public void setDrawBorder(boolean z) {
        getTable().setDrawBorder(z);
    }

    public TwoColumnTable getTable() {
        return this.twoColumnTable;
    }

    public String buildTable() {
        TwoColumnTable table = getTable();
        table.clear();
        buildTableRows();
        table.getSpreadSheet().setIndent(0, 2);
        return table.buildTable().toString();
    }

    public String buildCommandArg() {
        String[] strArrBuildArray = buildArray(true);
        StringBuilder sb = new StringBuilder();
        int length = strArrBuildArray.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringUtil.SPACE);
            }
            sb.append(strArrBuildArray[i]);
        }
        return sb.toString();
    }

    private void buildTableRows() {
        Object fieldValue;
        listFields();
        TwoColumnTable table = getTable();
        for (Field field : this.optionArgFieldsList) {
            String argName = ReflectionUtil.getArgName(field);
            if (argName != null && (fieldValue = getFieldValue(field)) != null) {
                if (fieldValue instanceof Collection) {
                    for (Object obj : (Collection) fieldValue) {
                        if (obj != null) {
                            table.addRow(argName, CommandUtil.quoteString(obj.toString()));
                        }
                    }
                } else if (!ReflectionUtil.isFlagArg(field) || Boolean.TRUE.equals(fieldValue)) {
                    table.addRow(argName, CommandUtil.quoteString(fieldValue.toString()));
                }
            }
        }
        table.addLine();
    }

    private String[] buildArray(boolean z) {
        Object fieldValue;
        listFields();
        ArrayList arrayList = new ArrayList();
        for (Field field : this.optionArgFieldsList) {
            String argName = ReflectionUtil.getArgName(field);
            if (argName != null && (fieldValue = getFieldValue(field)) != null) {
                if (fieldValue instanceof Collection) {
                    for (Object obj : (Collection) fieldValue) {
                        if (obj != null) {
                            String string = obj.toString();
                            if (z) {
                                string = CommandUtil.quoteString(string);
                            }
                            arrayList.add(argName);
                            arrayList.add(string);
                        }
                    }
                } else if (ReflectionUtil.isFlagArg(field)) {
                    if (Boolean.TRUE.equals(fieldValue)) {
                        arrayList.add(argName);
                    }
                } else {
                    arrayList.add(argName);
                    String string2 = fieldValue.toString();
                    if (z) {
                        string2 = CommandUtil.quoteString(string2);
                    }
                    arrayList.add(string2);
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private Object getFieldValue(Field field) {
        try {
            field.setAccessible(true);
            return field.get(this.optionObject);
        } catch (Exception unused) {
            return null;
        }
    }

    private void listFields() {
        if (this.optionArgFieldsList.isEmpty() && this.mLastArgs == null) {
            listFields(this.optionObject.getClass());
        }
    }

    private void listFields(Class<?> cls) {
        for (Field field : ReflectionUtil.listInstanceFields(cls)) {
            if (field.getAnnotation(OptionArg.class) != null || field.getAnnotation(ChoiceArg.class) != null) {
                this.optionArgFieldsList.add(field);
            } else if (((LastArgs) field.getAnnotation(LastArgs.class)) != null) {
                this.mLastArgs = field;
            }
        }
    }
}
