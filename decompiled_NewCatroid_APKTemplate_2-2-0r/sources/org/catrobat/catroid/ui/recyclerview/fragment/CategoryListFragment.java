package org.catrobat.catroid.ui.recyclerview.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.formulaeditor.CustomFormula;
import org.catrobat.catroid.formulaeditor.CustomFormulaManager;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog;
import org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionAssistantDialog;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.AddUserListDialog;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.eclipse.jgit.transport.WalkEncryption;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CategoryListFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CategoryListFragment extends Fragment implements CategoryListRVAdapter.OnItemClickListener {
    public static final String ACTION_BAR_TITLE_BUNDLE_ARGUMENT = "actionBarTitle";
    public static final int CUSTOM_FUNCTION_ITEM_TYPE_ID = -100;
    private static final List<Integer> F2D_FUNCTIONS;
    private static final List<Integer> F2D_PARAMS;
    private static final List<Integer> FILE_FUNCTIONS;
    private static final List<Integer> FILE_PARAMS;
    public static final String FRAGMENT_TAG_BUNDLE_ARGUMENT = "fragmentTag";
    public static final String FUNCTION_TAG = "functionFragment";
    private static final List<Integer> HTTP_FUNCTIONS;
    private static final List<Integer> HTTP_PARAMS;
    private static final List<Integer> LIST_FUNCTIONS;
    private static final List<Integer> LIST_PARAMS;
    private static final List<Integer> LOGIC_BOOL;
    private static final List<Integer> LOGIC_COMPARISION;
    public static final String LOGIC_TAG = "logicFragment";
    private static final List<Integer> OBJECT_COLOR_COLLISION;
    private static final List<Integer> OBJECT_COLOR_PARAMS;
    public static final String OBJECT_TAG = "objectFragment";
    private static final List<Integer> PT_FUNCTIONS;
    private static final List<Integer> PT_PARAMS;
    private static final List<Integer> SENSORS_ACCELERATION;
    private static final List<Integer> SENSORS_ARDUINO;
    private static final List<Integer> SENSORS_ARDUINO_PARAMS;
    private static final List<Integer> SENSORS_CAST_GAMEPAD;
    private static final List<Integer> SENSORS_COLOR_AT_XY;
    private static final List<Integer> SENSORS_COLOR_AT_XY_PARAMS;
    private static final List<Integer> SENSORS_COLOR_EQUALS_COLOR;
    private static final List<Integer> SENSORS_COLOR_EQUALS_COLOR_PARAMS;
    private static final List<Integer> SENSORS_COMPASS;
    private static final List<Integer> SENSORS_DATE_TIME;
    private static final List<Integer> SENSORS_DEFAULT;
    private static final List<Integer> SENSORS_DRONE;
    private static final List<Integer> SENSORS_EV3;
    private static final List<Integer> SENSORS_FACE_DETECTION;
    private static final List<Integer> SENSORS_FACE_DETECTION_PARAMS;
    private static final List<Integer> SENSORS_GPS;
    private static final List<Integer> SENSORS_INCLINATION;
    private static final List<Integer> SENSORS_NFC;
    private static final List<Integer> SENSORS_NXT;
    private static final List<Integer> SENSORS_OBJECT_DETECTION;
    private static final List<Integer> SENSORS_PHIRO;
    private static final List<Integer> SENSORS_POSE_DETECTION;
    private static final List<Integer> SENSORS_POSE_DETECTION_HUAWEI;
    private static final List<Integer> SENSORS_POSE_DETECTION_PARAMS;
    private static final List<Integer> SENSORS_POSE_DETECTION_PARAMS_HUAWEI;
    private static final List<Integer> SENSORS_RASPBERRY;
    private static final List<Integer> SENSORS_RASPBERRY_PARAMS;
    private static final List<Integer> SENSORS_SPEECH_RECOGNITION;
    private static final List<Integer> SENSORS_TEXT_RECOGNITION;
    private static final List<Integer> SENSORS_TEXT_RECOGNITION_PARAMS;
    private static final List<Integer> SENSORS_TOUCH;
    private static final List<Integer> SENSORS_TOUCH_PARAMS;
    public static final String SENSOR_TAG = "sensorFragment";
    private static final List<Integer> SENSOR_USER_LANGUAGE;
    private static final List<Integer> STRING_PARAMS;
    private static final List<Integer> TABLE_AND_VARS_FUNCTIONS;
    private static final List<Integer> TABLE_AND_VARS_PARAMS;
    public static final String TAG = "CategoryListFragment";
    private static final List<Integer> THREED_FUNCTIONS;
    private static final List<Integer> THREED_PARAMS;
    private static final List<Integer> TWOD_FUNCTIONS;
    private static final List<Integer> TWOD_PARAMS;
    private static final List<Integer> VIEW_FUNCTIONS;
    private static final List<Integer> VIEW_PARAMS;
    private CategoryListRVAdapter adapter;
    private List<CategoryListRVAdapter.CategoryListItem> allItems = new ArrayList();
    public CategoryListRVAdapter.CategoryListItem chosenItem = null;
    private RecyclerView recyclerView;
    private static final List<Integer> OBJECT_GENERAL_PROPERTIES = Arrays.asList(Integer.valueOf(R.string.formula_get_object_name), Integer.valueOf(R.string.formula_editor_object_rotation_look), Integer.valueOf(R.string.formula_editor_object_transparency), Integer.valueOf(R.string.formula_editor_object_brightness), Integer.valueOf(R.string.formula_editor_object_color));
    private static final List<Integer> OBJECT_LOOK = Arrays.asList(Integer.valueOf(R.string.formula_editor_object_look_number), Integer.valueOf(R.string.formula_editor_object_look_name), Integer.valueOf(R.string.formula_editor_object_look_width), Integer.valueOf(R.string.formula_editor_object_look_height), Integer.valueOf(R.string.formula_editor_object_number_of_looks));
    private static final List<Integer> OBJECT_BACKGROUND = Arrays.asList(Integer.valueOf(R.string.formula_editor_object_background_number), Integer.valueOf(R.string.formula_editor_object_background_name), Integer.valueOf(R.string.formula_editor_object_number_of_backgrounds));
    private static final List<Integer> OBJECT_PHYSICAL_1 = Arrays.asList(Integer.valueOf(R.string.formula_editor_object_x), Integer.valueOf(R.string.formula_editor_object_y), Integer.valueOf(R.string.formula_editor_object_size), Integer.valueOf(R.string.formula_editor_object_width), Integer.valueOf(R.string.formula_editor_object_height), Integer.valueOf(R.string.formula_editor_object_rotation), Integer.valueOf(R.string.formula_editor_object_rotation_look), Integer.valueOf(R.string.formula_editor_object_layer));
    private static final List<Integer> OBJECT_PHYSICAL_COLLISION = Collections.singletonList(Integer.valueOf(R.string.formula_editor_function_collision));
    private static final List<Integer> OBJECT_PHYSICAL_2 = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_collides_with_edge), Integer.valueOf(R.string.formula_editor_function_touched), Integer.valueOf(R.string.formula_editor_function_touches_object_by_name), Integer.valueOf(R.string.formula_editor_object_x_velocity), Integer.valueOf(R.string.formula_editor_object_y_velocity), Integer.valueOf(R.string.formula_editor_object_angular_velocity));
    private static final List<Integer> MATH_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_sin), Integer.valueOf(R.string.formula_editor_function_cos), Integer.valueOf(R.string.formula_editor_function_tan), Integer.valueOf(R.string.formula_editor_function_ln), Integer.valueOf(R.string.formula_editor_function_log), Integer.valueOf(R.string.formula_editor_function_pi), Integer.valueOf(R.string.formula_editor_function_sqrt), Integer.valueOf(R.string.formula_editor_function_rand), Integer.valueOf(R.string.formula_editor_function_abs), Integer.valueOf(R.string.formula_editor_function_round), Integer.valueOf(R.string.formula_editor_function_roundto), Integer.valueOf(R.string.formula_editor_function_mod), Integer.valueOf(R.string.formula_editor_function_arcsin), Integer.valueOf(R.string.formula_editor_function_arccos), Integer.valueOf(R.string.formula_editor_function_arctan), Integer.valueOf(R.string.formula_editor_function_arctan2), Integer.valueOf(R.string.formula_editor_function_exp), Integer.valueOf(R.string.formula_editor_function_power), Integer.valueOf(R.string.formula_editor_function_floor), Integer.valueOf(R.string.formula_editor_function_ceil), Integer.valueOf(R.string.formula_editor_function_to_hex), Integer.valueOf(R.string.formula_editor_function_to_dec), Integer.valueOf(R.string.formula_editor_function_max), Integer.valueOf(R.string.formula_editor_function_min), Integer.valueOf(R.string.formula_editor_function_clamp), Integer.valueOf(R.string.formula_editor_function_distan), Integer.valueOf(R.string.formula_editor_function_if_then_else));
    private static final List<Integer> MATH_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_sin_parameter), Integer.valueOf(R.string.formula_editor_function_cos_parameter), Integer.valueOf(R.string.formula_editor_function_tan_parameter), Integer.valueOf(R.string.formula_editor_function_ln_parameter), Integer.valueOf(R.string.formula_editor_function_log_parameter), Integer.valueOf(R.string.formula_editor_function_pi_parameter), Integer.valueOf(R.string.formula_editor_function_sqrt_parameter), Integer.valueOf(R.string.formula_editor_function_rand_parameter), Integer.valueOf(R.string.formula_editor_function_abs_parameter), Integer.valueOf(R.string.formula_editor_function_round_parameter), Integer.valueOf(R.string.formula_editor_function_roundto_parameter), Integer.valueOf(R.string.formula_editor_function_mod_parameter), Integer.valueOf(R.string.formula_editor_function_arcsin_parameter), Integer.valueOf(R.string.formula_editor_function_arccos_parameter), Integer.valueOf(R.string.formula_editor_function_arctan_parameter), Integer.valueOf(R.string.formula_editor_function_arctan2_parameter), Integer.valueOf(R.string.formula_editor_function_exp_parameter), Integer.valueOf(R.string.formula_editor_function_power_parameter), Integer.valueOf(R.string.formula_editor_function_floor_parameter), Integer.valueOf(R.string.formula_editor_function_ceil_parameter), Integer.valueOf(R.string.formula_editor_function_to_hex_parameter), Integer.valueOf(R.string.formula_editor_function_to_dec_parameter), Integer.valueOf(R.string.formula_editor_function_max_parameter), Integer.valueOf(R.string.formula_editor_function_min_parameter), Integer.valueOf(R.string.formula_editor_function_clamp_parameter), Integer.valueOf(R.string.formula_editor_function_distan_parameter), Integer.valueOf(R.string.formula_editor_function_if_then_else_parameter));
    private static final List<Integer> STRING_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_length), Integer.valueOf(R.string.formula_editor_function_letter), Integer.valueOf(R.string.formula_editor_function_subtext), Integer.valueOf(R.string.formula_editor_function_upper), Integer.valueOf(R.string.formula_editor_function_lower), Integer.valueOf(R.string.formula_editor_function_reverse), Integer.valueOf(R.string.formula_editor_function_join), Integer.valueOf(R.string.formula_editor_function_join3), Integer.valueOf(R.string.formula_editor_function_replace), Integer.valueOf(R.string.formula_editor_function_contains_str), Integer.valueOf(R.string.formula_editor_function_repeat), Integer.valueOf(R.string.formula_editor_function_random_str), Integer.valueOf(R.string.formula_editor_function_distance), Integer.valueOf(R.string.formula_editor_function_joinnumber), Integer.valueOf(R.string.formula_editor_function_regex), Integer.valueOf(R.string.formula_editor_function_regex_assistant), Integer.valueOf(R.string.formula_editor_function_flatten), Integer.valueOf(R.string.formula_editor_function_connect), Integer.valueOf(R.string.formula_editor_function_lua), Integer.valueOf(R.string.formula_editor_function_json_get), Integer.valueOf(R.string.formula_editor_function_json_set), Integer.valueOf(R.string.formula_editor_function_json_is_valid), Integer.valueOf(R.string.formula_md5), Integer.valueOf(R.string.formula_sha256));

    static {
        Integer numValueOf = Integer.valueOf(R.string.formula_editor_function_no_parameter);
        STRING_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_length_parameter), Integer.valueOf(R.string.formula_editor_function_letter_parameter), Integer.valueOf(R.string.formula_editor_function_subtext_parameter), Integer.valueOf(R.string.formula_editor_function_upper_parameter), Integer.valueOf(R.string.formula_editor_function_lower_parameter), Integer.valueOf(R.string.formula_editor_function_reverse_parametr), Integer.valueOf(R.string.formula_editor_function_join_parameter), Integer.valueOf(R.string.formula_editor_function_join3_parameter), Integer.valueOf(R.string.formula_editor_function_replace_parameter), Integer.valueOf(R.string.formula_editor_function_contains_str_parameter), Integer.valueOf(R.string.formula_editor_function_repeat_parameter), Integer.valueOf(R.string.formula_editor_function_random_str_parameter), Integer.valueOf(R.string.formula_editor_function_distance_parameter), Integer.valueOf(R.string.formula_editor_function_joinnumber_parameter), Integer.valueOf(R.string.formula_editor_function_regex_parameter), numValueOf, Integer.valueOf(R.string.formula_editor_function_flatten_parameter), Integer.valueOf(R.string.formula_editor_function_connect_parameter), Integer.valueOf(R.string.formula_editor_function_lua_parameter), Integer.valueOf(R.string.formula_editor_function_json_get_parameter), Integer.valueOf(R.string.formula_editor_function_json_set_parameter), Integer.valueOf(R.string.formula_editor_function_json_is_valid_parameter), Integer.valueOf(R.string.formula_md5_param), Integer.valueOf(R.string.formula_sha256_param));
        LIST_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_number_of_items), Integer.valueOf(R.string.formula_editor_function_list_item), Integer.valueOf(R.string.formula_editor_function_contains), Integer.valueOf(R.string.formula_editor_function_index_of_item), Integer.valueOf(R.string.formula_editor_function_flatten), Integer.valueOf(R.string.formula_editor_function_connect), Integer.valueOf(R.string.formula_editor_function_find));
        LIST_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_number_of_items_parameter), Integer.valueOf(R.string.formula_editor_function_list_item_parameter), Integer.valueOf(R.string.formula_editor_function_contains_parameter), Integer.valueOf(R.string.formula_editor_function_index_of_item_parameter), Integer.valueOf(R.string.formula_editor_function_flatten_parameter), Integer.valueOf(R.string.formula_editor_function_connect_parameter), Integer.valueOf(R.string.formula_editor_function_find_parameter));
        PT_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_pt_argmax), Integer.valueOf(R.string.formula_pt_value), Integer.valueOf(R.string.formula_pt_valuend), Integer.valueOf(R.string.formula_pt_shape), Integer.valueOf(R.string.formula_pt_dump), Integer.valueOf(R.string.formula_pt_totalsize));
        PT_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_pt_argmax_param), Integer.valueOf(R.string.formula_pt_value_param), Integer.valueOf(R.string.formula_pt_valuennd_param), Integer.valueOf(R.string.formula_pt_shape_param), Integer.valueOf(R.string.formula_pt_dump_param), Integer.valueOf(R.string.formula_pt_totalsize_param));
        F2D_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_f2d_x), Integer.valueOf(R.string.formula_f2d_y), Integer.valueOf(R.string.formula_f2d_rotation), Integer.valueOf(R.string.formula_f2d_scale_x), Integer.valueOf(R.string.formula_f2d_scale_y), Integer.valueOf(R.string.formula_f2d_r), Integer.valueOf(R.string.formula_f2d_g), Integer.valueOf(R.string.formula_f2d_b), Integer.valueOf(R.string.formula_f2d_alpha), Integer.valueOf(R.string.formula_f2d_texture), Integer.valueOf(R.string.formula_f2d_cam_x), Integer.valueOf(R.string.formula_f2d_cam_y), Integer.valueOf(R.string.formula_f2d_cam_zoom), Integer.valueOf(R.string.formula_f2d_is_touched), Integer.valueOf(R.string.formula_f2d_is_touched_index));
        F2D_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_id_param), Integer.valueOf(R.string.formula_f2d_no_param), Integer.valueOf(R.string.formula_f2d_no_param), Integer.valueOf(R.string.formula_f2d_no_param), Integer.valueOf(R.string.formula_f2d_is_touched_param), Integer.valueOf(R.string.formula_f2d_is_touched_index_param));
        THREED_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_delta), Integer.valueOf(R.string.formula_3d_pos_x), Integer.valueOf(R.string.formula_3d_pos_y), Integer.valueOf(R.string.formula_3d_pos_z), Integer.valueOf(R.string.formula_3d_rot_yaw), Integer.valueOf(R.string.formula_3d_rot_pitch), Integer.valueOf(R.string.formula_3d_rot_roll), Integer.valueOf(R.string.formula_3d_scale_x), Integer.valueOf(R.string.formula_3d_scale_y), Integer.valueOf(R.string.formula_3d_scale_z), Integer.valueOf(R.string.formula_3d_velo_x), Integer.valueOf(R.string.formula_3d_velo_y), Integer.valueOf(R.string.formula_3d_velo_z), Integer.valueOf(R.string.formula_3d_distance), Integer.valueOf(R.string.formula_3d_touches), Integer.valueOf(R.string.formula_3d_intersects), Integer.valueOf(R.string.formula_collision_list), Integer.valueOf(R.string.formula_intersect_list), Integer.valueOf(R.string.formula_cam_pos_x), Integer.valueOf(R.string.formula_cam_pos_y), Integer.valueOf(R.string.formula_cam_pos_z), Integer.valueOf(R.string.formula_cam_dir_x), Integer.valueOf(R.string.formula_cam_dir_y), Integer.valueOf(R.string.formula_cam_dir_z), Integer.valueOf(R.string.formula_cam_rot_pitch), Integer.valueOf(R.string.formula_cam_rot_yaw), Integer.valueOf(R.string.formula_cam_rot_roll), Integer.valueOf(R.string.formula_vector_dir_x), Integer.valueOf(R.string.formula_vector_dir_y), Integer.valueOf(R.string.formula_vector_angle), Integer.valueOf(R.string.formula_ray_did_hit), Integer.valueOf(R.string.formula_ray_distance), Integer.valueOf(R.string.formula_ray_hit_object), Integer.valueOf(R.string.formula_ray_hit_x), Integer.valueOf(R.string.formula_ray_hit_y), Integer.valueOf(R.string.formula_ray_hit_z), Integer.valueOf(R.string.formula_ray_normal_x), Integer.valueOf(R.string.formula_ray_normal_y), Integer.valueOf(R.string.formula_ray_normal_z), Integer.valueOf(R.string.formula_voxel_get_id), Integer.valueOf(R.string.formula_voxel_get_data));
        Integer numValueOf2 = Integer.valueOf(R.string.formula_no_param);
        Integer numValueOf3 = Integer.valueOf(R.string.formula_3d_single_param);
        THREED_PARAMS = Arrays.asList(numValueOf2, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, numValueOf3, Integer.valueOf(R.string.formula_3d_touches_param), Integer.valueOf(R.string.formula_3d_intersects_param), numValueOf3, numValueOf3, Integer.valueOf(R.string.formula_3d_double_param), numValueOf2, numValueOf2, numValueOf2, numValueOf2, numValueOf2, numValueOf2, numValueOf2, numValueOf2, numValueOf2, Integer.valueOf(R.string.formula_vector_dir_param), Integer.valueOf(R.string.formula_vector_dir_param), Integer.valueOf(R.string.formula_vector_angle_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_ray_param), Integer.valueOf(R.string.formula_voxel_get_id_param), Integer.valueOf(R.string.formula_voxel_get_data_param));
        HTTP_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_http_response_text), Integer.valueOf(R.string.formula_http_response_code), Integer.valueOf(R.string.formula_http_response_header), Integer.valueOf(R.string.formula_mqtt_message));
        HTTP_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_http_response_text_param), Integer.valueOf(R.string.formula_http_response_code_param), Integer.valueOf(R.string.formula_http_response_header_param), Integer.valueOf(R.string.formula_mqtt_message_param));
        LOGIC_BOOL = Arrays.asList(Integer.valueOf(R.string.formula_editor_logic_and), Integer.valueOf(R.string.formula_editor_logic_or), Integer.valueOf(R.string.formula_editor_logic_not), Integer.valueOf(R.string.formula_editor_function_true), Integer.valueOf(R.string.formula_editor_function_false));
        LOGIC_COMPARISION = Arrays.asList(Integer.valueOf(R.string.formula_editor_logic_equal), Integer.valueOf(R.string.formula_editor_logic_notequal), Integer.valueOf(R.string.formula_editor_logic_lesserthan), Integer.valueOf(R.string.formula_editor_logic_leserequal), Integer.valueOf(R.string.formula_editor_logic_greaterthan), Integer.valueOf(R.string.formula_editor_logic_greaterequal));
        SENSORS_DEFAULT = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_loudness), Integer.valueOf(R.string.formula_editor_function_touched), Integer.valueOf(R.string.formula_editor_sensor_stage_width), Integer.valueOf(R.string.formula_editor_sensor_stage_height), Integer.valueOf(R.string.formula_editor_sensor_micro), Integer.valueOf(R.string.formula_editor_sensor_micro_freq), Integer.valueOf(R.string.formula_editor_sensor_ip), Integer.valueOf(R.string.formula_editor_sensor_port), Integer.valueOf(R.string.formula_editor_sensor_battary), Integer.valueOf(R.string.formula_editor_sensor_internet), Integer.valueOf(R.string.formula_editor_sensor_architecture), Integer.valueOf(R.string.formula_editor_sensor_fps), Integer.valueOf(R.string.formula_clipboard_text), Integer.valueOf(R.string.formula_keyboard_height));
        OBJECT_COLOR_COLLISION = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_collides_with_color), Integer.valueOf(R.string.formula_editor_function_color_touches_color));
        OBJECT_COLOR_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_collides_with_color_parameter), Integer.valueOf(R.string.formula_editor_function_color_touches_color_parameter));
        SENSORS_COLOR_AT_XY = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_color_at_x_y));
        SENSORS_COLOR_AT_XY_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_color_at_x_y_parameter));
        SENSORS_COLOR_EQUALS_COLOR = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_color_equals_color));
        SENSORS_COLOR_EQUALS_COLOR_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_color_equals_color_parameter));
        SENSORS_ACCELERATION = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_x_acceleration), Integer.valueOf(R.string.formula_editor_sensor_y_acceleration), Integer.valueOf(R.string.formula_editor_sensor_z_acceleration));
        SENSORS_INCLINATION = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_x_inclination), Integer.valueOf(R.string.formula_editor_sensor_y_inclination));
        SENSORS_COMPASS = Collections.singletonList(Integer.valueOf(R.string.formula_editor_sensor_compass_direction));
        SENSORS_GPS = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_latitude), Integer.valueOf(R.string.formula_editor_sensor_longitude), Integer.valueOf(R.string.formula_editor_sensor_location_accuracy), Integer.valueOf(R.string.formula_editor_sensor_altitude));
        SENSOR_USER_LANGUAGE = Collections.singletonList(Integer.valueOf(R.string.formula_editor_sensor_user_language));
        SENSORS_TOUCH = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_finger_x), Integer.valueOf(R.string.formula_editor_function_finger_y), Integer.valueOf(R.string.formula_editor_function_is_finger_touching), Integer.valueOf(R.string.formula_editor_function_multi_finger_x), Integer.valueOf(R.string.formula_editor_function_multi_finger_y), Integer.valueOf(R.string.formula_editor_function_is_multi_finger_touching), Integer.valueOf(R.string.formula_editor_function_index_of_last_finger), Integer.valueOf(R.string.formula_editor_function_number_of_current_touches), Integer.valueOf(R.string.formula_editor_function_index_of_current_touch));
        SENSORS_TOUCH_PARAMS = Arrays.asList(numValueOf, numValueOf, numValueOf, Integer.valueOf(R.string.formula_editor_function_touch_parameter), Integer.valueOf(R.string.formula_editor_function_touch_parameter), Integer.valueOf(R.string.formula_editor_function_touch_parameter), numValueOf, numValueOf, Integer.valueOf(R.string.formula_editor_function_touch_parameter));
        SENSORS_FACE_DETECTION = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_face_detected), Integer.valueOf(R.string.formula_editor_sensor_face_size), Integer.valueOf(R.string.formula_editor_sensor_face_x_position), Integer.valueOf(R.string.formula_editor_sensor_face_y_position), Integer.valueOf(R.string.formula_editor_sensor_second_face_detected), Integer.valueOf(R.string.formula_editor_sensor_second_face_size), Integer.valueOf(R.string.formula_editor_sensor_second_face_x_position), Integer.valueOf(R.string.formula_editor_sensor_second_face_y_position));
        SENSORS_FACE_DETECTION_PARAMS = Arrays.asList(numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf);
        SENSORS_POSE_DETECTION = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_nose_x), Integer.valueOf(R.string.formula_editor_sensor_nose_y), Integer.valueOf(R.string.formula_editor_sensor_left_eye_inner_x), Integer.valueOf(R.string.formula_editor_sensor_left_eye_inner_y), Integer.valueOf(R.string.formula_editor_sensor_left_eye_center_x), Integer.valueOf(R.string.formula_editor_sensor_left_eye_center_y), Integer.valueOf(R.string.formula_editor_sensor_left_eye_outer_x), Integer.valueOf(R.string.formula_editor_sensor_left_eye_outer_y), Integer.valueOf(R.string.formula_editor_sensor_right_eye_inner_x), Integer.valueOf(R.string.formula_editor_sensor_right_eye_inner_y), Integer.valueOf(R.string.formula_editor_sensor_right_eye_center_x), Integer.valueOf(R.string.formula_editor_sensor_right_eye_center_y), Integer.valueOf(R.string.formula_editor_sensor_right_eye_outer_x), Integer.valueOf(R.string.formula_editor_sensor_right_eye_outer_y), Integer.valueOf(R.string.formula_editor_sensor_left_ear_x), Integer.valueOf(R.string.formula_editor_sensor_left_ear_y), Integer.valueOf(R.string.formula_editor_sensor_right_ear_x), Integer.valueOf(R.string.formula_editor_sensor_right_ear_y), Integer.valueOf(R.string.formula_editor_sensor_mouth_left_corner_x), Integer.valueOf(R.string.formula_editor_sensor_mouth_left_corner_y), Integer.valueOf(R.string.formula_editor_sensor_mouth_right_corner_x), Integer.valueOf(R.string.formula_editor_sensor_mouth_right_corner_y), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_x), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_y), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_x), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_y), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_x), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_y), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_x), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_y), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_x), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_y), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_x), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_y), Integer.valueOf(R.string.formula_editor_sensor_left_pinky_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_left_pinky_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_right_pinky_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_right_pinky_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_left_index_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_left_index_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_right_index_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_right_index_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_left_thumb_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_left_thumb_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_right_thumb_knuckle_x), Integer.valueOf(R.string.formula_editor_sensor_right_thumb_knuckle_y), Integer.valueOf(R.string.formula_editor_sensor_left_hip_x), Integer.valueOf(R.string.formula_editor_sensor_left_hip_y), Integer.valueOf(R.string.formula_editor_sensor_right_hip_x), Integer.valueOf(R.string.formula_editor_sensor_right_hip_y), Integer.valueOf(R.string.formula_editor_sensor_left_knee_x), Integer.valueOf(R.string.formula_editor_sensor_left_knee_y), Integer.valueOf(R.string.formula_editor_sensor_right_knee_x), Integer.valueOf(R.string.formula_editor_sensor_right_knee_y), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_x), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_y), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_x), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_y), Integer.valueOf(R.string.formula_editor_sensor_left_heel_x), Integer.valueOf(R.string.formula_editor_sensor_left_heel_y), Integer.valueOf(R.string.formula_editor_sensor_right_heel_x), Integer.valueOf(R.string.formula_editor_sensor_right_heel_y), Integer.valueOf(R.string.formula_editor_sensor_left_foot_index_x), Integer.valueOf(R.string.formula_editor_sensor_left_foot_index_y), Integer.valueOf(R.string.formula_editor_sensor_right_foot_index_x), Integer.valueOf(R.string.formula_editor_sensor_right_foot_index_y));
        SENSORS_POSE_DETECTION_PARAMS = Arrays.asList(numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf);
        SENSORS_POSE_DETECTION_HUAWEI = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_head_top_x), Integer.valueOf(R.string.formula_editor_sensor_head_top_y), Integer.valueOf(R.string.formula_editor_sensor_neck_x), Integer.valueOf(R.string.formula_editor_sensor_neck_y), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_x), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_y), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_x), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_y), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_x), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_y), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_x), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_y), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_x), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_y), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_x), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_y), Integer.valueOf(R.string.formula_editor_sensor_left_hip_x), Integer.valueOf(R.string.formula_editor_sensor_left_hip_y), Integer.valueOf(R.string.formula_editor_sensor_right_hip_x), Integer.valueOf(R.string.formula_editor_sensor_right_hip_y), Integer.valueOf(R.string.formula_editor_sensor_left_knee_x), Integer.valueOf(R.string.formula_editor_sensor_left_knee_y), Integer.valueOf(R.string.formula_editor_sensor_right_knee_x), Integer.valueOf(R.string.formula_editor_sensor_right_knee_y), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_x), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_y), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_x), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_y));
        SENSORS_POSE_DETECTION_PARAMS_HUAWEI = Arrays.asList(numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf, numValueOf);
        SENSORS_TEXT_RECOGNITION = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_text_from_camera), Integer.valueOf(R.string.formula_editor_sensor_text_blocks_number), Integer.valueOf(R.string.formula_editor_function_text_block_x), Integer.valueOf(R.string.formula_editor_function_text_block_y), Integer.valueOf(R.string.formula_editor_function_text_block_size), Integer.valueOf(R.string.formula_editor_function_text_block_from_camera), Integer.valueOf(R.string.formula_editor_function_text_block_language_from_camera));
        SENSORS_TEXT_RECOGNITION_PARAMS = Arrays.asList(numValueOf, numValueOf, Integer.valueOf(R.string.formula_editor_function_text_block_parameter), Integer.valueOf(R.string.formula_editor_function_text_block_parameter), Integer.valueOf(R.string.formula_editor_function_text_block_parameter), Integer.valueOf(R.string.formula_editor_function_text_block_parameter), Integer.valueOf(R.string.formula_editor_function_text_block_parameter));
        SENSORS_OBJECT_DETECTION = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_get_id_of_detected_object), Integer.valueOf(R.string.formula_editor_function_object_with_id_visible));
        SENSORS_DATE_TIME = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_timer), Integer.valueOf(R.string.formula_editor_sensor_date_year), Integer.valueOf(R.string.formula_editor_sensor_date_month), Integer.valueOf(R.string.formula_editor_sensor_date_day), Integer.valueOf(R.string.formula_editor_sensor_date_weekday), Integer.valueOf(R.string.formula_editor_sensor_time_hour), Integer.valueOf(R.string.formula_editor_sensor_time_minute), Integer.valueOf(R.string.formula_editor_sensor_time_second));
        SENSORS_NXT = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_touch), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_sound), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light_active), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_ultrasonic));
        SENSORS_EV3 = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_touch), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_infrared), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_ambient), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_reflected), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_hitechnic_color), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_c), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_f), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_light), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_light_active), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_sound), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_ultrasonic));
        SENSORS_PHIRO = Arrays.asList(Integer.valueOf(R.string.formula_editor_phiro_sensor_front_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_right), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_right), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_left), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_right));
        SENSORS_ARDUINO = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_analog), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_digital));
        SENSORS_ARDUINO_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_pin_default_parameter), Integer.valueOf(R.string.formula_editor_function_pin_default_parameter));
        SENSORS_DRONE = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_drone_battery_status), Integer.valueOf(R.string.formula_editor_sensor_drone_emergency_state), Integer.valueOf(R.string.formula_editor_sensor_drone_flying), Integer.valueOf(R.string.formula_editor_sensor_drone_initialized), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_active), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_remaining_time), Integer.valueOf(R.string.formula_editor_sensor_drone_camera_ready), Integer.valueOf(R.string.formula_editor_sensor_drone_record_ready), Integer.valueOf(R.string.formula_editor_sensor_drone_recording), Integer.valueOf(R.string.formula_editor_sensor_drone_num_frames));
        SENSORS_RASPBERRY = Collections.singletonList(Integer.valueOf(R.string.formula_editor_function_raspi_read_pin_value_digital));
        SENSORS_RASPBERRY_PARAMS = Collections.singletonList(Integer.valueOf(R.string.formula_editor_function_pin_default_parameter));
        SENSORS_NFC = Arrays.asList(Integer.valueOf(R.string.formula_editor_nfc_tag_id), Integer.valueOf(R.string.formula_editor_nfc_tag_message), Integer.valueOf(R.string.formula_editor_sensor_nfc_techs), Integer.valueOf(R.string.formula_editor_sensor_nfc_size), Integer.valueOf(R.string.formula_editor_sensor_nfc_writable), Integer.valueOf(R.string.formula_editor_sensor_nfc_type));
        SENSORS_CAST_GAMEPAD = Arrays.asList(Integer.valueOf(R.string.formula_editor_sensor_gamepad_a_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_b_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_up_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_down_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_left_pressed), Integer.valueOf(R.string.formula_editor_sensor_gamepad_right_pressed));
        FILE_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_file), Integer.valueOf(R.string.formula_editor_function_all_files), Integer.valueOf(R.string.formula_editor_function_files_path), Integer.valueOf(R.string.formula_editor_function_file_size), Integer.valueOf(R.string.formula_read_file), Integer.valueOf(R.string.formula_media_duration), Integer.valueOf(R.string.formula_image_width), Integer.valueOf(R.string.formula_image_height), Integer.valueOf(R.string.formula_file_last_modified), Integer.valueOf(R.string.formula_file_to_base64), Integer.valueOf(R.string.formula_file_md5), Integer.valueOf(R.string.formula_file_sha256));
        FILE_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_file_parameter), numValueOf2, numValueOf2, Integer.valueOf(R.string.formula_editor_function_file_parameter), Integer.valueOf(R.string.formula_read_file_param), Integer.valueOf(R.string.formula_media_duration_param), Integer.valueOf(R.string.formula_image_width_param), Integer.valueOf(R.string.formula_image_height_param), Integer.valueOf(R.string.formula_file_last_modified_param), Integer.valueOf(R.string.formula_file_to_base64_param), Integer.valueOf(R.string.formula_file_md5_param), Integer.valueOf(R.string.formula_file_sha256_param));
        TABLE_AND_VARS_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_var), Integer.valueOf(R.string.formula_editor_function_var_name), Integer.valueOf(R.string.formula_editor_function_var_value), Integer.valueOf(R.string.formula_editor_function_table_x), Integer.valueOf(R.string.formula_editor_function_table_y), Integer.valueOf(R.string.formula_editor_function_table_element), Integer.valueOf(R.string.formula_editor_function_table_join), Integer.valueOf(R.string.formula_editor_function_floatarray));
        TABLE_AND_VARS_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_editor_function_var_parameter), Integer.valueOf(R.string.formula_editor_function_var_name_parameter), Integer.valueOf(R.string.formula_editor_function_var_value_parameter), Integer.valueOf(R.string.formula_editor_function_table_x_parameter), Integer.valueOf(R.string.formula_editor_function_table_y_parameter), Integer.valueOf(R.string.formula_editor_function_table_element_parameter), Integer.valueOf(R.string.formula_editor_function_table_join_parameter), Integer.valueOf(R.string.formula_editor_function_floatarray_parameter));
        VIEW_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.view_x), Integer.valueOf(R.string.view_y), Integer.valueOf(R.string.view_width), Integer.valueOf(R.string.view_height), Integer.valueOf(R.string.is_video_playing), Integer.valueOf(R.string.formula_notification_reply));
        VIEW_PARAMS = Arrays.asList(Integer.valueOf(R.string.view_params), Integer.valueOf(R.string.view_params), Integer.valueOf(R.string.view_params), Integer.valueOf(R.string.view_params), Integer.valueOf(R.string.videoplayer_params), Integer.valueOf(R.string.formula_notification_reply_param));
        TWOD_FUNCTIONS = Arrays.asList(Integer.valueOf(R.string.formula_ray_did_hit2), Integer.valueOf(R.string.formula_ray_hit_sprite_name), Integer.valueOf(R.string.formula_ray_hit_x2), Integer.valueOf(R.string.formula_ray_hit_y2), Integer.valueOf(R.string.formula_ray_hit_distance), Integer.valueOf(R.string.formula_camera_x), Integer.valueOf(R.string.formula_camera_y), Integer.valueOf(R.string.formula_camera_rotation), Integer.valueOf(R.string.formula_camera_zoom));
        TWOD_PARAMS = Arrays.asList(Integer.valueOf(R.string.formula_ray_did_hit_parameter), Integer.valueOf(R.string.formula_ray_hit_sprite_name_parameter), Integer.valueOf(R.string.formula_ray_hit_x_parameter), Integer.valueOf(R.string.formula_ray_hit_y_parameter), Integer.valueOf(R.string.formula_ray_hit_distance_parameter), numValueOf2, numValueOf2, numValueOf2, numValueOf2);
        SENSORS_SPEECH_RECOGNITION = Collections.singletonList(Integer.valueOf(R.string.formula_editor_listening_language_sensor));
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setHasOptionsMenu(true);
        float f = getResources().getDisplayMetrics().density;
        this.recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_background));
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        RecyclerView recyclerView2 = this.recyclerView;
        recyclerView2.setPadding(recyclerView2.getPaddingLeft(), (int) (72.0f * f), this.recyclerView.getPaddingRight(), this.recyclerView.getPaddingBottom());
        this.recyclerView.setClipToPadding(false);
        viewInflate.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        MaterialCardView materialCardView = new MaterialCardView(getContext());
        materialCardView.setRadius(12.0f * f);
        materialCardView.setCardElevation(4.0f * f);
        materialCardView.setStrokeWidth(0);
        materialCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.button_background));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        int i = (int) (16.0f * f);
        int i2 = (int) (f * 8.0f);
        layoutParams.setMargins(i, i2, i, i2);
        materialCardView.setLayoutParams(layoutParams);
        SearchView searchView = new SearchView(getContext());
        searchView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        searchView.setQueryHint(getString(R.string.search) + "...");
        searchView.setIconifiedByDefault(false);
        View viewFindViewById = searchView.findViewById(R.id.search_plate);
        if (viewFindViewById != null) {
            viewFindViewById.setBackground(null);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment.1
            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                CategoryListFragment.this.filterList(query);
                return true;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) {
                CategoryListFragment.this.filterList(newText);
                return true;
            }
        });
        materialCardView.addView(searchView);
        frameLayout.addView(viewInflate);
        frameLayout.addView(materialCardView);
        return frameLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAdapter();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        AppCompatActivity appCompatActivity;
        ActionBar supportActionBar;
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments == null || (appCompatActivity = (AppCompatActivity) getActivity()) == null || (supportActionBar = appCompatActivity.getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.setTitle(arguments.getString(ACTION_BAR_TITLE_BUNDLE_ARGUMENT));
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu == null) {
            return;
        }
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity == null) {
            return;
        }
        appCompatActivity.getMenuInflater().inflate(R.menu.menu_formulareditor_category, menu);
        ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter.OnItemClickListener
    public void onItemClick(CategoryListRVAdapter.CategoryListItem item) {
        if (item.isCustomFunction && item.customFunctionName != null) {
            FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
            if (formulaEditorFragment != null) {
                formulaEditorFragment.addCustomFunctionToActiveFormula(item.customFunctionName);
            }
            getActivity().onBackPressed();
            return;
        }
        int i = item.type;
        if (i != 0) {
            if (i == 1) {
                showSelectSpriteDialog();
                return;
            } else if (i == 2) {
                showLegoSensorPortConfigDialog(item.nameResId, 0);
                return;
            } else {
                if (i != 3) {
                    return;
                }
                showLegoSensorPortConfigDialog(item.nameResId, 1);
                return;
            }
        }
        if (LIST_FUNCTIONS.contains(Integer.valueOf(item.nameResId))) {
            onUserListFunctionSelected(item);
            return;
        }
        if (R.string.formula_editor_function_regex_assistant == item.nameResId) {
            regularExpressionAssistantActivityOnButtonClick();
            return;
        }
        FormulaEditorFragment formulaEditorFragment2 = (FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        if (formulaEditorFragment2 != null) {
            formulaEditorFragment2.setChosenCategoryItem(item);
        }
        getActivity().onBackPressed();
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.wiki_help) {
            return true;
        }
        onOptionsMenuClick(getTag());
        return true;
    }

    public void onOptionsMenuClick(String tag) {
        String language = getLanguage(getActivity());
        tag.hashCode();
        switch (tag) {
            case "logicFragment":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.CATROBAT_LOGIC_WIKI_URL + language)));
                break;
            case "functionFragment":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.CATROBAT_FUNCTIONS_WIKI_URL + language)));
                break;
            case "sensorFragment":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.CATROBAT_SENSORS_WIKI_URL + language)));
                break;
            case "objectFragment":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.CATROBAT_OBJECT_WIKI_URL + language)));
                break;
        }
    }

    public String getHelpUrl(String tag, SpriteActivity activity) {
        String language = getLanguage(activity);
        tag.hashCode();
        switch (tag) {
            case "logicFragment":
                return Constants.CATROBAT_LOGIC_WIKI_URL + language;
            case "functionFragment":
                return Constants.CATROBAT_FUNCTIONS_WIKI_URL + language;
            case "sensorFragment":
                return Constants.CATROBAT_SENSORS_WIKI_URL + language;
            case "objectFragment":
                return Constants.CATROBAT_OBJECT_WIKI_URL + language;
            default:
                return null;
        }
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getLanguage(Activity activity) {
        Locale localeForLanguageTag;
        String string = getSharedPreferences(activity.getApplicationContext()).getString(SharedPreferenceKeys.LANGUAGE_TAG_KEY, "");
        if (!string.equals(SharedPreferenceKeys.DEVICE_LANGUAGE) && Arrays.asList(SharedPreferenceKeys.LANGUAGE_TAGS).contains(string)) {
            localeForLanguageTag = Locale.forLanguageTag(string);
        } else {
            localeForLanguageTag = Locale.forLanguageTag(CatroidApplication.defaultSystemLanguage);
        }
        return "?language=" + localeForLanguageTag.getLanguage();
    }

    private FormulaEditorFragment addResourceToActiveFormulaInFormulaEditor(CategoryListRVAdapter.CategoryListItem categoryListItem) {
        if (getFragmentManager() == null) {
            return null;
        }
        FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        if (formulaEditorFragment == null) {
            return formulaEditorFragment;
        }
        formulaEditorFragment.addResourceToActiveFormula(categoryListItem.nameResId);
        return formulaEditorFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addResourceToActiveFormulaInFormulaEditor(CategoryListRVAdapter.CategoryListItem categoryListItem, UserList lastUserList) {
        addResourceToActiveFormulaInFormulaEditor(categoryListItem).addUserListToActiveFormula(lastUserList.getName());
        getActivity().onBackPressed();
    }

    private void onUserListFunctionSelected(CategoryListRVAdapter.CategoryListItem item) {
        FragmentActivity activity = getActivity();
        insertLastUserListToActiveFormula(item, ProjectManager.getInstance().getCurrentProject().getUserLists(), ProjectManager.getInstance().getCurrentSprite().getUserLists(), activity, new TextInputDialog.Builder(activity));
    }

    public void insertLastUserListToActiveFormula(CategoryListRVAdapter.CategoryListItem categoryListItem, List<UserList> projectUserList, List<UserList> spriteUserList, FragmentActivity activity, TextInputDialog.Builder builder) {
        if (spriteUserList.isEmpty() && projectUserList.isEmpty()) {
            showNewUserListDialog(categoryListItem, projectUserList, spriteUserList, activity, builder);
        } else if (!spriteUserList.isEmpty()) {
            addResourceToActiveFormulaInFormulaEditor(categoryListItem, spriteUserList.get(spriteUserList.size() - 1));
        } else {
            addResourceToActiveFormulaInFormulaEditor(categoryListItem, projectUserList.get(projectUserList.size() - 1));
        }
    }

    private CategoryListRVAdapter.CategoryListItem getRegularExpressionItem() {
        CategoryListRVAdapter.CategoryListItem categoryListItem = null;
        for (CategoryListRVAdapter.CategoryListItem categoryListItem2 : getFunctionItems()) {
            if (categoryListItem2.nameResId == R.string.formula_editor_function_regex) {
                categoryListItem = categoryListItem2;
            }
        }
        return categoryListItem;
    }

    private void regularExpressionAssistantActivityOnButtonClick() {
        FormulaEditorFragment formulaEditorFragment = getFormulaEditorFragment();
        if (formulaEditorFragment != null) {
            int indexOfCorrespondingRegularExpression = formulaEditorFragment.getIndexOfCorrespondingRegularExpression();
            if (indexOfCorrespondingRegularExpression >= 0) {
                formulaEditorFragment.setSelectionToFirstParamOfRegularExpressionAtInternalIndex(indexOfCorrespondingRegularExpression);
            } else {
                addResourceToActiveFormulaInFormulaEditor(getRegularExpressionItem());
            }
            getActivity().onBackPressed();
            openRegularExpressionAssistant();
        }
    }

    private FormulaEditorFragment getFormulaEditorFragment() {
        if (getFragmentManager() != null) {
            return (FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        }
        return null;
    }

    private void openRegularExpressionAssistant() {
        new RegularExpressionAssistantDialog(getContext(), getFragmentManager()).createAssistant();
    }

    private void showNewUserListDialog(final CategoryListRVAdapter.CategoryListItem categoryListItem, final List<UserList> projectUserList, final List<UserList> spriteUserList, final FragmentActivity activity, TextInputDialog.Builder builder) {
        final AddUserListDialog addUserListDialog = new AddUserListDialog(builder);
        addUserListDialog.show(activity.getString(R.string.data_label), activity.getString(R.string.ok), new AddUserListDialog.Callback() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment.2
            @Override // org.catrobat.catroid.utils.AddUserListDialog.Callback
            public void onPositiveButton(DialogInterface dialog, String textInput) {
                UserList userList = new UserList(textInput);
                addUserListDialog.addUserList(dialog, userList, projectUserList, spriteUserList);
                CategoryListFragment.this.addResourceToActiveFormulaInFormulaEditor(categoryListItem, userList);
            }

            @Override // org.catrobat.catroid.utils.AddUserListDialog.Callback
            public void onNegativeButton() {
                activity.onBackPressed();
            }
        });
    }

    private void showLegoSensorPortConfigDialog(int itemNameResId, final int type) {
        new LegoSensorPortConfigDialog.Builder(getContext(), type, itemNameResId).setPositiveButton(getString(R.string.ok), new LegoSensorPortConfigDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, int i, Enum r5) {
                this.f$0.m5777xa8cfe370(type, dialogInterface, i, r5);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showLegoSensorPortConfigDialog$0$org-catrobat-catroid-ui-recyclerview-fragment-CategoryListFragment, reason: not valid java name */
    /* synthetic */ void m5777xa8cfe370(int i, DialogInterface dialogInterface, int i2, Enum r5) {
        if (i == 0) {
            SettingsFragment.setLegoMindstormsNXTSensorMapping(getActivity(), (NXTSensor.Sensor) r5, SettingsFragment.NXT_SENSORS[i2]);
        } else if (i == 1) {
            SettingsFragment.setLegoMindstormsEV3SensorMapping(getActivity(), (EV3Sensor.Sensor) r5, SettingsFragment.EV3_SENSORS[i2]);
        }
        FormulaEditorFragment formulaEditorFragment = (FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        TypedArray typedArrayObtainTypedArray = getResources().obtainTypedArray(i == 0 ? R.array.formula_editor_nxt_ports : R.array.formula_editor_ev3_ports);
        try {
            int resourceId = typedArrayObtainTypedArray.getResourceId(i2, 0);
            if (resourceId != 0) {
                formulaEditorFragment.addResourceToActiveFormula(resourceId);
                formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
            }
            typedArrayObtainTypedArray.recycle();
            getActivity().onBackPressed();
        } catch (Throwable th) {
            typedArrayObtainTypedArray.recycle();
            throw th;
        }
    }

    private void showSelectSpriteDialog() {
        final Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        List<Sprite> spriteList = ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList();
        final ArrayList arrayList = new ArrayList();
        Iterator<Sprite> it = spriteList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        String[] strArr = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = ((Sprite) arrayList.get(i)).getName();
        }
        new AlertDialog.Builder(getContext()).setTitle(R.string.formula_editor_function_collision).setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.CategoryListFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5778x61f757cc(arrayList, currentSprite, dialogInterface, i2);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showSelectSpriteDialog$1$org-catrobat-catroid-ui-recyclerview-fragment-CategoryListFragment, reason: not valid java name */
    /* synthetic */ void m5778x61f757cc(List list, Sprite sprite, DialogInterface dialogInterface, int i) {
        Sprite sprite2 = (Sprite) list.get(i);
        sprite.createCollisionPolygons();
        sprite2.createCollisionPolygons();
        ((FormulaEditorFragment) getFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG)).addCollideFormulaToActiveFormula(sprite2.getName());
        getActivity().onBackPressed();
    }

    private void initializeAdapter() {
        String string = getArguments().getString(FRAGMENT_TAG_BUNDLE_ARGUMENT);
        Log.d("WorkspaceUI", "initializeAdapter called. Argument received: " + string);
        if (OBJECT_TAG.equals(string)) {
            this.allItems = getObjectItems();
        } else if (FUNCTION_TAG.equals(string) || FUNCTION_TAG.equals(string)) {
            this.allItems = getFunctionItems();
        } else if (LOGIC_TAG.equals(string) || LOGIC_TAG.equals(string)) {
            this.allItems = getLogicItems();
        } else if (SENSOR_TAG.equals(string) || SENSOR_TAG.equals(string)) {
            this.allItems = getSensorItems();
        } else {
            Log.e("WorkspaceUI", "Unknown or null fragmentTag argument: " + string);
            this.allItems = new ArrayList();
        }
        Log.d("WorkspaceUI", "Items loaded count: " + this.allItems.size());
        CategoryListRVAdapter categoryListRVAdapter = new CategoryListRVAdapter(new ArrayList(this.allItems), false);
        this.adapter = categoryListRVAdapter;
        categoryListRVAdapter.setOnItemClickListener(this);
        this.recyclerView.setAdapter(this.adapter);
    }

    private List<CategoryListRVAdapter.CategoryListItem> addHeader(List<CategoryListRVAdapter.CategoryListItem> subCategory, String header) {
        subCategory.get(0).header = header;
        return subCategory;
    }

    private List<CategoryListRVAdapter.CategoryListItem> toCategoryListItems(List<Integer> nameResIds) {
        return toCategoryListItems(nameResIds, null, 0);
    }

    private List<CategoryListRVAdapter.CategoryListItem> toCategoryListItems(List<Integer> nameResIds, List<Integer> paramResIds) {
        return toCategoryListItems(nameResIds, paramResIds, 0);
    }

    private List<CategoryListRVAdapter.CategoryListItem> toCategoryListItems(List<Integer> nameResIds, int type) {
        return toCategoryListItems(nameResIds, null, type);
    }

    private List<CategoryListRVAdapter.CategoryListItem> toCategoryListItems(List<Integer> nameResIds, List<Integer> paramResIds, int type) {
        String string;
        if (paramResIds != null && paramResIds.size() != nameResIds.size()) {
            throw new IllegalArgumentException("Sizes of paramResIds and nameResIds parameters do not fit");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < nameResIds.size(); i++) {
            if (paramResIds == null) {
                string = "";
            } else {
                string = getString(paramResIds.get(i).intValue());
            }
            arrayList.add(new CategoryListRVAdapter.CategoryListItem(nameResIds.get(i).intValue(), getString(nameResIds.get(i).intValue()) + string, type));
        }
        return arrayList;
    }

    private List<CategoryListRVAdapter.CategoryListItem> getObjectItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getObjectGeneralPropertiesItems());
        arrayList.addAll(getObjectPhysicalPropertiesItems());
        return arrayList;
    }

    private List<CategoryListRVAdapter.CategoryListItem> getFunctionItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(addHeader(toCategoryListItems(MATH_FUNCTIONS, MATH_PARAMS), getString(R.string.formula_editor_functions_maths)));
        arrayList.addAll(addHeader(toCategoryListItems(STRING_FUNCTIONS, STRING_PARAMS), getString(R.string.formula_editor_functions_strings)));
        arrayList.addAll(addHeader(toCategoryListItems(LIST_FUNCTIONS, LIST_PARAMS), getString(R.string.formula_editor_functions_lists)));
        arrayList.addAll(addHeader(toCategoryListItems(TABLE_AND_VARS_FUNCTIONS, TABLE_AND_VARS_PARAMS), getString(R.string.tables_and_vars)));
        arrayList.addAll(addHeader(toCategoryListItems(VIEW_FUNCTIONS, VIEW_PARAMS), getString(R.string.native_views)));
        arrayList.addAll(addHeader(toCategoryListItems(FILE_FUNCTIONS, FILE_PARAMS), getString(R.string.category_file)));
        arrayList.addAll(addHeader(toCategoryListItems(TWOD_FUNCTIONS, TWOD_PARAMS), "2D"));
        arrayList.addAll(addHeader(toCategoryListItems(F2D_FUNCTIONS, F2D_PARAMS), "Fast 2D"));
        arrayList.addAll(addHeader(toCategoryListItems(THREED_FUNCTIONS, THREED_PARAMS), "3D"));
        arrayList.addAll(addHeader(toCategoryListItems(PT_FUNCTIONS, PT_PARAMS), "PockeTensor"));
        arrayList.addAll(addHeader(toCategoryListItems(HTTP_FUNCTIONS, HTTP_PARAMS), getString(R.string.category_network)));
        List<CustomFormula> formulas = CustomFormulaManager.INSTANCE.getFormulas();
        if (!formulas.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            for (CustomFormula customFormula : formulas) {
                CategoryListRVAdapter.CategoryListItem categoryListItem = new CategoryListRVAdapter.CategoryListItem(0, customFormula.getDisplayName(), 0);
                categoryListItem.isCustomFunction = true;
                categoryListItem.customFunctionName = customFormula.getUniqueName();
                arrayList2.add(categoryListItem);
            }
            arrayList.addAll(addHeader(arrayList2, getString(R.string.formula_editor_custom_functions_header)));
        }
        return arrayList;
    }

    private List<CategoryListRVAdapter.CategoryListItem> getLogicItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(addHeader(toCategoryListItems(LOGIC_BOOL), getString(R.string.formula_editor_logic_boolean)));
        arrayList.addAll(addHeader(toCategoryListItems(LOGIC_COMPARISION), getString(R.string.formula_editor_logic_comparison)));
        return arrayList;
    }

    private List<CategoryListRVAdapter.CategoryListItem> getSensorItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getNxtSensorItems());
        arrayList.addAll(getEv3SensorItems());
        arrayList.addAll(getPhiroSensorItems());
        arrayList.addAll(getArduinoSensorItems());
        arrayList.addAll(getDroneSensorItems());
        arrayList.addAll(getRaspberrySensorItems());
        arrayList.addAll(getNfcItems());
        arrayList.addAll(getCastGamepadSensorItems());
        arrayList.addAll(getSpeechRecognitionItems());
        arrayList.addAll(getFaceSensorItems());
        arrayList.addAll(getPoseSensorItems());
        arrayList.addAll(getTextSensorItems());
        arrayList.addAll(getObjectDetectionSensorItems());
        arrayList.addAll(getDeviceSensorItems());
        arrayList.addAll(getTouchDetectionSensorItems());
        arrayList.addAll(getDateTimeSensorItems());
        return arrayList;
    }

    private List<CategoryListRVAdapter.CategoryListItem> getObjectGeneralPropertiesItems() {
        ArrayList arrayList = new ArrayList(OBJECT_GENERAL_PROPERTIES);
        if (ProjectManager.getInstance().getCurrentSprite().equals(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite())) {
            arrayList.addAll(OBJECT_BACKGROUND);
        } else {
            arrayList.addAll(OBJECT_LOOK);
        }
        List<CategoryListRVAdapter.CategoryListItem> categoryListItems = toCategoryListItems(arrayList);
        categoryListItems.addAll(toCategoryListItems(OBJECT_COLOR_COLLISION.subList(1, 2), OBJECT_COLOR_PARAMS.subList(1, 2)));
        return addHeader(categoryListItems, getString(R.string.formula_editor_object_look));
    }

    private List<CategoryListRVAdapter.CategoryListItem> getObjectPhysicalPropertiesItems() {
        List<CategoryListRVAdapter.CategoryListItem> categoryListItems = toCategoryListItems(OBJECT_PHYSICAL_1);
        categoryListItems.addAll(toCategoryListItems(OBJECT_PHYSICAL_COLLISION, 1));
        categoryListItems.addAll(toCategoryListItems(OBJECT_PHYSICAL_2));
        categoryListItems.addAll(toCategoryListItems(OBJECT_COLOR_COLLISION, OBJECT_COLOR_PARAMS));
        return addHeader(categoryListItems, getString(R.string.formula_editor_object_movement));
    }

    private List<CategoryListRVAdapter.CategoryListItem> getNxtSensorItems() {
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_NXT, 2), getString(R.string.formula_editor_device_lego_nxt));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getEv3SensorItems() {
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_EV3, 3), getString(R.string.formula_editor_device_lego_ev3));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getPhiroSensorItems() {
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_PHIRO), getString(R.string.formula_editor_device_phiro));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getArduinoSensorItems() {
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_ARDUINO, SENSORS_ARDUINO_PARAMS), getString(R.string.formula_editor_device_arduino));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getDroneSensorItems() {
        if (SettingsFragment.isDroneSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_DRONE), getString(R.string.formula_editor_device_drone));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getRaspberrySensorItems() {
        if (RaspberryPiSettingsFragment.isRaspiSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_RASPBERRY, SENSORS_RASPBERRY_PARAMS), getString(R.string.formula_editor_device_raspberry));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getNfcItems() {
        if (SettingsFragment.isNfcSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_NFC), getString(R.string.formula_editor_device_nfc));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getCastGamepadSensorItems() {
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            return addHeader(toCategoryListItems(SENSORS_CAST_GAMEPAD), getString(R.string.formula_editor_device_cast));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getDeviceSensorItems() {
        ArrayList arrayList = new ArrayList(toCategoryListItems(SENSORS_DEFAULT));
        SensorHandler sensorHandler = SensorHandler.getInstance(getActivity());
        arrayList.addAll(toCategoryListItems(SENSORS_COLOR_AT_XY, SENSORS_COLOR_AT_XY_PARAMS));
        arrayList.addAll(toCategoryListItems(SENSORS_COLOR_EQUALS_COLOR, SENSORS_COLOR_EQUALS_COLOR_PARAMS));
        arrayList.addAll(sensorHandler.accelerationAvailable() ? toCategoryListItems(SENSORS_ACCELERATION) : Collections.emptyList());
        arrayList.addAll(sensorHandler.inclinationAvailable() ? toCategoryListItems(SENSORS_INCLINATION) : Collections.emptyList());
        arrayList.addAll(sensorHandler.compassAvailable() ? toCategoryListItems(SENSORS_COMPASS) : Collections.emptyList());
        arrayList.addAll(toCategoryListItems(SENSORS_GPS));
        arrayList.addAll(toCategoryListItems(SENSOR_USER_LANGUAGE));
        return addHeader(arrayList, getString(R.string.formula_editor_device_sensors));
    }

    private List<CategoryListRVAdapter.CategoryListItem> getTouchDetectionSensorItems() {
        return addHeader(toCategoryListItems(SENSORS_TOUCH, SENSORS_TOUCH_PARAMS), getString(R.string.formula_editor_device_touch_detection));
    }

    private List<CategoryListRVAdapter.CategoryListItem> getDateTimeSensorItems() {
        return addHeader(toCategoryListItems(SENSORS_DATE_TIME), getString(R.string.formula_editor_device_date_and_time));
    }

    private List<CategoryListRVAdapter.CategoryListItem> getSpeechRecognitionItems() {
        if (SettingsFragment.isAISpeechRecognitionSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_SPEECH_RECOGNITION), getString(R.string.formula_editor_speech_recognition));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getFaceSensorItems() {
        if (SettingsFragment.isAIFaceDetectionSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_FACE_DETECTION, SENSORS_FACE_DETECTION_PARAMS), getString(R.string.formula_editor_device_face_detection));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getPoseSensorItems() {
        boolean zIsHmsAvailable = ((MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class)).isHmsAvailable(getActivity().getApplicationContext());
        boolean zIsAIPoseDetectionSharedPreferenceEnabled = SettingsFragment.isAIPoseDetectionSharedPreferenceEnabled(getActivity().getApplicationContext());
        if (zIsAIPoseDetectionSharedPreferenceEnabled && zIsHmsAvailable) {
            return addHeader(toCategoryListItems(SENSORS_POSE_DETECTION_HUAWEI, SENSORS_POSE_DETECTION_PARAMS_HUAWEI), getString(R.string.formula_editor_device_pose_detection));
        }
        if (zIsAIPoseDetectionSharedPreferenceEnabled) {
            return addHeader(toCategoryListItems(SENSORS_POSE_DETECTION, SENSORS_POSE_DETECTION_PARAMS), getString(R.string.formula_editor_device_pose_detection));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getTextSensorItems() {
        if (SettingsFragment.isAITextRecognitionSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_TEXT_RECOGNITION, SENSORS_TEXT_RECOGNITION_PARAMS), getString(R.string.formula_editor_device_text_recognition));
        }
        return Collections.emptyList();
    }

    private List<CategoryListRVAdapter.CategoryListItem> getObjectDetectionSensorItems() {
        if (SettingsFragment.isAIObjectDetectionSharedPreferenceEnabled(getActivity().getApplicationContext())) {
            return addHeader(toCategoryListItems(SENSORS_OBJECT_DETECTION), getString(R.string.formula_editor_device_object_recognition));
        }
        return Collections.emptyList();
    }

    private int getLevenshteinDistance(String s1, String s2) {
        int length = s1.length();
        int length2 = s2.length();
        int i = length2 + 1;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        for (int i2 = 0; i2 <= length2; i2++) {
            iArr[i2] = i2;
        }
        for (int i3 = 1; i3 <= length; i3++) {
            iArr2[0] = i3;
            for (int i4 = 1; i4 <= length2; i4++) {
                int i5 = i4 - 1;
                iArr2[i4] = Math.min(Math.min(iArr2[i5] + 1, iArr[i4] + 1), iArr[i5] + (s1.charAt(i3 + (-1)) == s2.charAt(i5) ? 0 : 1));
            }
            System.arraycopy(iArr2, 0, iArr, 0, i);
        }
        return iArr[length2];
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CategoryListFragment$SearchResult.class
      classes14.dex
     */
    private static class SearchResult implements Comparable<SearchResult> {
        CategoryListRVAdapter.CategoryListItem item;
        int score;

        SearchResult(CategoryListRVAdapter.CategoryListItem item, int score) {
            this.item = item;
            this.score = score;
        }

        @Override // java.lang.Comparable
        public int compareTo(SearchResult other) {
            return Integer.compare(this.score, other.score);
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:53:0x00db  */
    /* JADX WARN: Code duplicated, block: B:55:0x00df  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e5  */
    private int calculateRelevanceScore(String text, String query) {
        boolean z;
        boolean z2;
        boolean z3;
        if (text == null || query == null) {
            return Integer.MAX_VALUE;
        }
        String strTrim = text.toLowerCase(Locale.getDefault()).replace('_', StringUtil.SPACE).replace('-', StringUtil.SPACE).trim();
        String strTrim2 = query.toLowerCase(Locale.getDefault()).replace('_', StringUtil.SPACE).replace('-', StringUtil.SPACE).trim();
        if (strTrim2.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int i = 0;
        if (strTrim.equals(strTrim2)) {
            return 0;
        }
        if (strTrim.startsWith(strTrim2)) {
            return 1;
        }
        if (strTrim.contains(strTrim2)) {
            return 2;
        }
        String[] strArrSplit = strTrim2.split(WalkEncryption.Vals.REGEX_WS);
        String[] strArrSplit2 = strTrim.split(WalkEncryption.Vals.REGEX_WS);
        int length = strArrSplit.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z4 = true;
        boolean z5 = true;
        while (true) {
            int i5 = 5;
            if (i2 >= length) {
                break;
            }
            String str = strArrSplit[i2];
            String strSubstring = str.length() > 4 ? str.substring(i, 4) : str;
            int length2 = strArrSplit2.length;
            while (true) {
                if (i < length2) {
                    String str2 = strArrSplit2[i];
                    if (str2.equals(str) || str2.startsWith(str)) {
                        z = true;
                        z3 = true;
                        z2 = false;
                    } else if (str2.contains(strSubstring)) {
                        z = true;
                        z3 = false;
                        z2 = true;
                    } else {
                        if (str.length() >= 3) {
                            int i6 = str.length() >= i5 ? 2 : 1;
                            if (Math.abs(str2.length() - str.length()) <= i6) {
                                int levenshteinDistance = getLevenshteinDistance(str, str2);
                                if (levenshteinDistance <= i6) {
                                    i4 += levenshteinDistance;
                                    z = true;
                                }
                            }
                        }
                        i++;
                        i5 = 5;
                    }
                    if (z) {
                        i3++;
                        if (!z3) {
                            z4 = false;
                        }
                        if (!z2 || z3) {
                        }
                        i2++;
                        i = 0;
                    } else {
                        z4 = false;
                    }
                    z5 = false;
                    i2++;
                    i = 0;
                } else {
                    z = false;
                }
                z3 = false;
                z2 = false;
                if (z) {
                    i3++;
                    if (!z3) {
                        z4 = false;
                    }
                    if (!z2) {
                    }
                    i2++;
                    i = 0;
                } else {
                    z4 = false;
                }
                z5 = false;
                i2++;
                i = 0;
            }
        }
        if (i3 != strArrSplit.length) {
            return Integer.MAX_VALUE;
        }
        if (z4) {
            return 3;
        }
        if (z5) {
            return 4;
        }
        return i4 + 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterList(String query) {
        if (query == null || query.trim().isEmpty()) {
            CategoryListRVAdapter categoryListRVAdapter = new CategoryListRVAdapter(new ArrayList(this.allItems), false);
            this.adapter = categoryListRVAdapter;
            categoryListRVAdapter.setOnItemClickListener(this);
            this.recyclerView.setAdapter(this.adapter);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (CategoryListRVAdapter.CategoryListItem categoryListItem : getAllPossibleItems()) {
            int iCalculateRelevanceScore = calculateRelevanceScore(categoryListItem.text != null ? categoryListItem.text : "", query);
            if (iCalculateRelevanceScore != Integer.MAX_VALUE) {
                arrayList.add(new SearchResult(categoryListItem, iCalculateRelevanceScore));
            }
        }
        Collections.sort(arrayList);
        ArrayList<CategoryListRVAdapter.CategoryListItem> arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CategoryListRVAdapter.CategoryListItem categoryListItem2 = ((SearchResult) it.next()).item;
            CategoryListRVAdapter.CategoryListItem categoryListItem3 = new CategoryListRVAdapter.CategoryListItem(categoryListItem2.nameResId, categoryListItem2.text, categoryListItem2.type);
            categoryListItem3.isCustomFunction = categoryListItem2.isCustomFunction;
            categoryListItem3.customFunctionName = categoryListItem2.customFunctionName;
            categoryListItem3.header = null;
            arrayList2.add(categoryListItem3);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (CategoryListRVAdapter.CategoryListItem categoryListItem4 : arrayList2) {
            String itemHeaderString = getItemHeaderString(categoryListItem4);
            if (!linkedHashMap.containsKey(itemHeaderString)) {
                linkedHashMap.put(itemHeaderString, new ArrayList());
            }
            ((List) linkedHashMap.get(itemHeaderString)).add(categoryListItem4);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            List list = (List) entry.getValue();
            if (!list.isEmpty()) {
                ((CategoryListRVAdapter.CategoryListItem) list.get(0)).header = (String) entry.getKey();
                arrayList3.addAll(list);
            }
        }
        CategoryListRVAdapter categoryListRVAdapter2 = new CategoryListRVAdapter(arrayList3, true);
        this.adapter = categoryListRVAdapter2;
        categoryListRVAdapter2.setOnItemClickListener(this);
        this.recyclerView.setAdapter(this.adapter);
    }

    private List<CategoryListRVAdapter.CategoryListItem> getAllPossibleItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getObjectItems());
        arrayList.addAll(getFunctionItems());
        arrayList.addAll(getLogicItems());
        arrayList.addAll(getSensorItems());
        return arrayList;
    }

    private String getItemHeaderString(CategoryListRVAdapter.CategoryListItem item) {
        if (item.isCustomFunction && item.customFunctionName != null) {
            return getString(R.string.formula_editor_custom_functions_header);
        }
        int i = item.nameResId;
        if (MATH_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_functions_maths);
        }
        if (STRING_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_functions_strings);
        }
        if (LIST_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_functions_lists);
        }
        if (F2D_FUNCTIONS.contains(Integer.valueOf(i))) {
            return "Fast 2D";
        }
        if (THREED_FUNCTIONS.contains(Integer.valueOf(i))) {
            return "3D";
        }
        if (PT_FUNCTIONS.contains(Integer.valueOf(i))) {
            return "PockeTensor";
        }
        if (TWOD_FUNCTIONS.contains(Integer.valueOf(i))) {
            return "2D";
        }
        if (FILE_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.category_file);
        }
        if (VIEW_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.native_views);
        }
        if (TABLE_AND_VARS_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.tables_and_vars);
        }
        if (HTTP_FUNCTIONS.contains(Integer.valueOf(i))) {
            return getString(R.string.category_network);
        }
        if (LOGIC_BOOL.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_logic_boolean);
        }
        if (LOGIC_COMPARISION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_logic_comparison);
        }
        if (OBJECT_GENERAL_PROPERTIES.contains(Integer.valueOf(i)) || OBJECT_BACKGROUND.contains(Integer.valueOf(i)) || OBJECT_LOOK.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_object_look);
        }
        if (OBJECT_PHYSICAL_1.contains(Integer.valueOf(i)) || OBJECT_PHYSICAL_2.contains(Integer.valueOf(i)) || OBJECT_PHYSICAL_COLLISION.contains(Integer.valueOf(i)) || OBJECT_COLOR_COLLISION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_object_movement);
        }
        if (SENSORS_NXT.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_lego_nxt);
        }
        if (SENSORS_EV3.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_lego_ev3);
        }
        if (SENSORS_PHIRO.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_phiro);
        }
        if (SENSORS_ARDUINO.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_arduino);
        }
        if (SENSORS_DRONE.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_drone);
        }
        if (SENSORS_RASPBERRY.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_raspberry);
        }
        if (SENSORS_NFC.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_nfc);
        }
        if (SENSORS_CAST_GAMEPAD.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_cast);
        }
        if (SENSORS_TOUCH.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_touch_detection);
        }
        if (SENSORS_DATE_TIME.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_date_and_time);
        }
        if (SENSORS_SPEECH_RECOGNITION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_speech_recognition);
        }
        if (SENSORS_FACE_DETECTION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_face_detection);
        }
        if (SENSORS_POSE_DETECTION.contains(Integer.valueOf(i)) || SENSORS_POSE_DETECTION_HUAWEI.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_pose_detection);
        }
        if (SENSORS_TEXT_RECOGNITION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_text_recognition);
        }
        if (SENSORS_OBJECT_DETECTION.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_object_recognition);
        }
        if (SENSORS_DEFAULT.contains(Integer.valueOf(i)) || SENSORS_COLOR_AT_XY.contains(Integer.valueOf(i)) || SENSORS_COLOR_EQUALS_COLOR.contains(Integer.valueOf(i)) || SENSORS_ACCELERATION.contains(Integer.valueOf(i)) || SENSORS_INCLINATION.contains(Integer.valueOf(i)) || SENSORS_COMPASS.contains(Integer.valueOf(i)) || SENSORS_GPS.contains(Integer.valueOf(i)) || SENSOR_USER_LANGUAGE.contains(Integer.valueOf(i))) {
            return getString(R.string.formula_editor_device_sensors);
        }
        return "Other";
    }
}
