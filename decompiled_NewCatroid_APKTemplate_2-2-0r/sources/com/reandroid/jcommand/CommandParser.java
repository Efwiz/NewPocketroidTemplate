package com.reandroid.jcommand;

import com.reandroid.jcommand.annotations.CommandOptions;
import com.reandroid.jcommand.annotations.MainCommand;
import com.reandroid.jcommand.annotations.OnOptionSelected;
import com.reandroid.jcommand.annotations.OtherOption;
import com.reandroid.jcommand.exceptions.CommandException;
import com.reandroid.jcommand.utils.ReflectionUtil;
import java.lang.reflect.Method;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class CommandParser {
    private final Class<?> mainCommandClass;

    public CommandParser(Class<?> cls) {
        this.mainCommandClass = cls;
    }

    public void parse(Object obj, String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new CommandException(CommandStrings.empty_command_args_exception, new Object[0]);
        }
        String str = strArr[0];
        Method otherOptionMethod = getOtherOptionMethod(str, obj == null);
        if (otherOptionMethod != null) {
            try {
                otherOptionMethod.setAccessible(true);
                otherOptionMethod.invoke(obj, new Object[0]);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Class<?> optionClass = getOptionClass(str);
        if (optionClass != null) {
            int length = strArr.length;
            String[] strArr2 = new String[length - 1];
            for (int i = 1; i < length; i++) {
                strArr2[i - 1] = strArr[i];
            }
            parseSubCommand(obj, optionClass, strArr2);
            return;
        }
        throw new CommandException(CommandStrings.unknown_command_exception, str);
    }

    private void parseSubCommand(Object obj, Class<?> cls, String[] strArr) {
        Object obj2 = SubCommandParser.parse((Class<Object>) cls, strArr);
        boolean z = strArr.length == 0;
        Method onOptionSelectedMethod = getOnOptionSelectedMethod(obj == null);
        try {
            onOptionSelectedMethod.setAccessible(true);
            onOptionSelectedMethod.invoke(obj, obj2, Boolean.valueOf(z));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getOptionClass(String str) {
        for (Class<?> cls : ((MainCommand) this.mainCommandClass.getAnnotation(MainCommand.class)).options()) {
            CommandOptions commandOptions = (CommandOptions) cls.getAnnotation(CommandOptions.class);
            if (commandOptions.name().equals(str)) {
                return cls;
            }
            for (String str2 : commandOptions.alternates()) {
                if (str2.equals(str)) {
                    return cls;
                }
            }
        }
        return null;
    }

    private Method getOtherOptionMethod(String str, boolean z) {
        OtherOption otherOption;
        for (Method method : ReflectionUtil.listMethods(this.mainCommandClass)) {
            if (((method.getModifiers() & 8) == 8) == z && (otherOption = (OtherOption) method.getAnnotation(OtherOption.class)) != null) {
                for (String str2 : otherOption.names()) {
                    if (str2.equals(str)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    private Method getOnOptionSelectedMethod(boolean z) {
        for (Method method : ReflectionUtil.listMethods(this.mainCommandClass)) {
            if (((method.getModifiers() & 8) == 8) == z && ((OnOptionSelected) method.getAnnotation(OnOptionSelected.class)) != null) {
                return method;
            }
        }
        if (z) {
            throw new RuntimeException("No static method annotated with OnOptionSelected in class: '" + this.mainCommandClass + FormatHelper.QUOTE);
        }
        throw new RuntimeException("No instance method annotated with OnOptionSelected in class: '" + this.mainCommandClass + FormatHelper.QUOTE);
    }
}
