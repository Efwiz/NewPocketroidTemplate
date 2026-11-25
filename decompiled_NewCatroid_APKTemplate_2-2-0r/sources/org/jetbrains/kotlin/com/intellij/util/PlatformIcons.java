package org.jetbrains.kotlin.com.intellij.util;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;

/* JADX INFO: loaded from: classes3.dex */
public interface PlatformIcons {
    public static final Icon COMBOBOX_ARROW_ICON;
    public static final Icon CONTENT_ROOT_ICON_CLOSED;

    @Deprecated
    public static final Icon DIRECTORY_CLOSED_ICON;

    @Deprecated
    public static final Icon DIRECTORY_OPEN_ICON;
    public static final Icon EXPORT_ICON;
    public static final Icon FOLDER_ICON;
    public static final Icon IMPORT_ICON;
    public static final Icon INVALID_ENTRY_ICON;
    public static final Icon MODULES_SOURCE_FOLDERS_ICON;
    public static final Icon MODULES_TEST_SOURCE_FOLDER;
    public static final Icon SOURCE_FOLDERS_ICON;
    public static final Icon TEST_SOURCE_FOLDER;
    public static final Icon UP_DOWN_ARROWS;
    public static final Icon PUBLIC_ICON = AllIcons.Nodes.C_public;
    public static final Icon LOCKED_ICON = AllIcons.Nodes.Locked;
    public static final Icon SYMLINK_ICON = AllIcons.Nodes.Symlink;
    public static final Icon PRIVATE_ICON = AllIcons.Nodes.C_private;
    public static final Icon PROTECTED_ICON = AllIcons.Nodes.C_protected;
    public static final Icon PACKAGE_LOCAL_ICON = AllIcons.Nodes.C_plocal;
    public static final Icon PACKAGE_ICON = AllIcons.Nodes.Package;
    public static final Icon CLASS_ICON = AllIcons.Nodes.Class;
    public static final Icon EXCEPTION_CLASS_ICON = AllIcons.Nodes.ExceptionClass;
    public static final Icon ANONYMOUS_CLASS_ICON = AllIcons.Nodes.AnonymousClass;
    public static final Icon ABSTRACT_CLASS_ICON = AllIcons.Nodes.AbstractClass;
    public static final Icon ANNOTATION_TYPE_ICON = AllIcons.Nodes.Annotationtype;
    public static final Icon ENUM_ICON = AllIcons.Nodes.Enum;
    public static final Icon RECORD_ICON = AllIcons.Nodes.Record;
    public static final Icon INTERFACE_ICON = AllIcons.Nodes.Interface;
    public static final Icon METHOD_ICON = AllIcons.Nodes.Method;
    public static final Icon FUNCTION_ICON = AllIcons.Nodes.Function;
    public static final Icon ABSTRACT_METHOD_ICON = AllIcons.Nodes.AbstractMethod;
    public static final Icon FIELD_ICON = AllIcons.Nodes.Field;
    public static final Icon PARAMETER_ICON = AllIcons.Nodes.Parameter;
    public static final Icon VARIABLE_ICON = AllIcons.Nodes.Variable;
    public static final Icon XML_TAG_ICON = AllIcons.Nodes.Tag;
    public static final Icon LIBRARY_ICON = AllIcons.Nodes.PpLib;
    public static final Icon WEB_ICON = AllIcons.Nodes.PpWeb;
    public static final Icon JAR_ICON = AllIcons.Nodes.PpJar;
    public static final Icon FILE_ICON = AllIcons.Nodes.Folder;
    public static final Icon VARIABLE_READ_ACCESS = AllIcons.Nodes.ReadAccess;
    public static final Icon VARIABLE_WRITE_ACCESS = AllIcons.Nodes.WriteAccess;
    public static final Icon VARIABLE_RW_ACCESS = AllIcons.Nodes.RwAccess;
    public static final Icon CUSTOM_FILE_ICON = AllIcons.FileTypes.Custom;
    public static final Icon PROPERTY_ICON = AllIcons.Nodes.Property;
    public static final Icon NEW_PARAMETER = AllIcons.Hierarchy.Supertypes;
    public static final Icon ASPECT_ICON = AllIcons.Nodes.Aspect;
    public static final Icon ERROR_INTRODUCTION_ICON = AllIcons.Nodes.ErrorIntroduction;
    public static final Icon WARNING_INTRODUCTION_ICON = AllIcons.Nodes.WarningIntroduction;
    public static final Icon JAVA_OUTSIDE_SOURCE_ICON = AllIcons.FileTypes.JavaOutsideSource;
    public static final Icon EXCLUDED_FROM_COMPILE_ICON = AllIcons.Nodes.ExcludedFromCompile;
    public static final Icon PROJECT_ICON = AllIcons.Toolwindows.ToolWindowProject;
    public static final Icon UI_FORM_ICON = AllIcons.FileTypes.UiForm;
    public static final Icon JSP_ICON = AllIcons.FileTypes.Jsp;
    public static final Icon SMALL_VCS_CONFIGURABLE = AllIcons.Actions.ShowAsTree;
    public static final Icon GROUP_BY_PACKAGES = AllIcons.Actions.GroupByPackage;
    public static final Icon ADD_ICON = AllIcons.General.Add;
    public static final Icon DELETE_ICON = AllIcons.General.Remove;
    public static final Icon COPY_ICON = AllIcons.Actions.Copy;
    public static final Icon EDIT = AllIcons.Actions.Edit;
    public static final Icon SELECT_ALL_ICON = AllIcons.Actions.Selectall;
    public static final Icon UNSELECT_ALL_ICON = AllIcons.Actions.Unselectall;
    public static final Icon PROPERTIES_ICON = AllIcons.Actions.Properties;
    public static final Icon SYNCHRONIZE_ICON = AllIcons.Actions.Refresh;
    public static final Icon SHOW_SETTINGS_ICON = AllIcons.General.Settings;
    public static final Icon CHECK_ICON = AllIcons.Actions.Checked;
    public static final Icon CHECK_ICON_SELECTED = AllIcons.Actions.Checked_selected;
    public static final Icon CHECK_ICON_SMALL = AllIcons.Actions.Checked;
    public static final Icon CHECK_ICON_SMALL_SELECTED = AllIcons.Actions.Checked_selected;
    public static final Icon FLATTEN_PACKAGES_ICON = AllIcons.ObjectBrowser.FlattenPackages;
    public static final Icon EDIT_IN_SECTION_ICON = AllIcons.Actions.Edit;
    public static final Icon CLASS_INITIALIZER = AllIcons.Nodes.ClassInitializer;
    public static final Icon CLOSED_MODULE_GROUP_ICON = AllIcons.Nodes.ModuleGroup;

    static {
        Icon icon = AllIcons.Nodes.Folder;
        FOLDER_ICON = icon;
        SOURCE_FOLDERS_ICON = AllIcons.Nodes.Package;
        TEST_SOURCE_FOLDER = AllIcons.Nodes.TestSourceFolder;
        INVALID_ENTRY_ICON = AllIcons.Nodes.PpInvalid;
        MODULES_SOURCE_FOLDERS_ICON = AllIcons.Modules.SourceRoot;
        MODULES_TEST_SOURCE_FOLDER = AllIcons.Modules.TestRoot;
        CONTENT_ROOT_ICON_CLOSED = AllIcons.Nodes.Module;
        UP_DOWN_ARROWS = AllIcons.Ide.UpDown;
        COMBOBOX_ARROW_ICON = AllIcons.General.ArrowDown;
        EXPORT_ICON = AllIcons.ToolbarDecorator.Export;
        IMPORT_ICON = AllIcons.ToolbarDecorator.Import;
        DIRECTORY_CLOSED_ICON = icon;
        DIRECTORY_OPEN_ICON = icon;
    }
}
