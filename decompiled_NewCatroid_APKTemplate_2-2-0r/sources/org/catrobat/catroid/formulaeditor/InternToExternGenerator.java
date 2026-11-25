package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.utils.FormatNumberUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternToExternGenerator.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternToExternGenerator {
    private static final HashMap<String, Integer> INTERN_EXTERN_LANGUAGE_CONVERTER_MAP;
    private static final String TAG = "InternToExternGenerator";
    private Context context;
    private String generatedExternFormulaString = "";
    private ExternInternRepresentationMapping generatedExternInternRepresentationMapping = new ExternInternRepresentationMapping();

    static {
        HashMap<String, Integer> map = new HashMap<>();
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP = map;
        map.put(Operators.DIVIDE.name(), Integer.valueOf(R.string.formula_editor_operator_divide));
        map.put(Operators.MULT.name(), Integer.valueOf(R.string.formula_editor_operator_mult));
        map.put(Operators.MINUS.name(), Integer.valueOf(R.string.formula_editor_operator_minus));
        map.put(Operators.PLUS.name(), Integer.valueOf(R.string.formula_editor_operator_plus));
        map.put(".", Integer.valueOf(R.string.formula_editor_decimal_mark));
        map.put(Functions.SIN.name(), Integer.valueOf(R.string.formula_editor_function_sin));
        map.put(Functions.COS.name(), Integer.valueOf(R.string.formula_editor_function_cos));
        map.put(Functions.TAN.name(), Integer.valueOf(R.string.formula_editor_function_tan));
        map.put(Functions.LN.name(), Integer.valueOf(R.string.formula_editor_function_ln));
        map.put(Functions.LOG.name(), Integer.valueOf(R.string.formula_editor_function_log));
        map.put(Functions.PI.name(), Integer.valueOf(R.string.formula_editor_function_pi));
        map.put(Functions.SQRT.name(), Integer.valueOf(R.string.formula_editor_function_sqrt));
        map.put(Functions.RAND.name(), Integer.valueOf(R.string.formula_editor_function_rand));
        map.put(Functions.ABS.name(), Integer.valueOf(R.string.formula_editor_function_abs));
        map.put(Functions.ROUND.name(), Integer.valueOf(R.string.formula_editor_function_round));
        map.put(Functions.ROUNDTO.name(), Integer.valueOf(R.string.formula_editor_function_roundto));
        map.put(Functions.MOD.name(), Integer.valueOf(R.string.formula_editor_function_mod));
        map.put(Functions.ARCSIN.name(), Integer.valueOf(R.string.formula_editor_function_arcsin));
        map.put(Functions.ARCCOS.name(), Integer.valueOf(R.string.formula_editor_function_arccos));
        map.put(Functions.ARCTAN.name(), Integer.valueOf(R.string.formula_editor_function_arctan));
        map.put(Functions.ARCTAN2.name(), Integer.valueOf(R.string.formula_editor_function_arctan2));
        map.put(Functions.EXP.name(), Integer.valueOf(R.string.formula_editor_function_exp));
        map.put(Functions.POWER.name(), Integer.valueOf(R.string.formula_editor_function_power));
        map.put(Functions.FLOOR.name(), Integer.valueOf(R.string.formula_editor_function_floor));
        map.put(Functions.CEIL.name(), Integer.valueOf(R.string.formula_editor_function_ceil));
        map.put(Functions.MAX.name(), Integer.valueOf(R.string.formula_editor_function_max));
        map.put(Functions.MIN.name(), Integer.valueOf(R.string.formula_editor_function_min));
        map.put(Functions.CLAMP.name(), Integer.valueOf(R.string.formula_editor_function_clamp));
        map.put(Functions.DISTAN.name(), Integer.valueOf(R.string.formula_editor_function_distan));
        map.put(Functions.IF_THEN_ELSE.name(), Integer.valueOf(R.string.formula_editor_function_if_then_else));
        map.put(Functions.TRUE.name(), Integer.valueOf(R.string.formula_editor_function_true));
        map.put(Functions.FALSE.name(), Integer.valueOf(R.string.formula_editor_function_false));
        map.put(Functions.LENGTH.name(), Integer.valueOf(R.string.formula_editor_function_length));
        map.put(Functions.NUMBER_OF_ITEMS.name(), Integer.valueOf(R.string.formula_editor_function_number_of_items));
        map.put(Functions.LETTER.name(), Integer.valueOf(R.string.formula_editor_function_letter));
        map.put(Functions.SUBTEXT.name(), Integer.valueOf(R.string.formula_editor_function_subtext));
        map.put(Functions.FILE.name(), Integer.valueOf(R.string.formula_editor_function_file));
        map.put(Functions.FILES_PATH.name(), Integer.valueOf(R.string.formula_editor_function_files_path));
        map.put(Functions.ALL_FILES.name(), Integer.valueOf(R.string.formula_editor_function_all_files));
        map.put(Functions.FILE_SIZE.name(), Integer.valueOf(R.string.formula_editor_function_file_size));
        map.put(Functions.LUA.name(), Integer.valueOf(R.string.formula_editor_function_lua));
        map.put(Sensors.MOUSE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_mouse_x));
        map.put(Sensors.MOUSE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_mouse_y));
        map.put(Sensors.MOUSE_DELTA_X.name(), Integer.valueOf(R.string.formula_editor_sensor_mouse_delta_x));
        map.put(Sensors.MOUSE_DELTA_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_mouse_delta_y));
        map.put(Sensors.MOUSE_SCROLL.name(), Integer.valueOf(R.string.formula_editor_sensor_mouse_scroll));
        map.put(Functions.IS_MOUSE_BUTTON_DOWN.name(), Integer.valueOf(R.string.formula_editor_function_is_mouse_button_down));
        map.put(Functions.TO_HEX.name(), Integer.valueOf(R.string.formula_editor_function_to_hex));
        map.put(Functions.TO_DEC.name(), Integer.valueOf(R.string.formula_editor_function_to_dec));
        map.put(Functions.RANDOM_STR.name(), Integer.valueOf(R.string.formula_editor_function_random_str));
        map.put(Functions.REPEAT.name(), Integer.valueOf(R.string.formula_editor_function_repeat));
        map.put(Functions.CONTAINS_STR.name(), Integer.valueOf(R.string.formula_editor_function_contains_str));
        map.put(Functions.REPLACE.name(), Integer.valueOf(R.string.formula_editor_function_replace));
        map.put(Functions.TOUCHES_OBJECT_BY_NAME.name(), Integer.valueOf(R.string.formula_editor_function_touches_object_by_name));
        map.put(Functions.UPPER.name(), Integer.valueOf(R.string.formula_editor_function_upper));
        map.put(Functions.LOWER.name(), Integer.valueOf(R.string.formula_editor_function_lower));
        map.put(Functions.REVERSE.name(), Integer.valueOf(R.string.formula_editor_function_reverse));
        map.put(Functions.VAR.name(), Integer.valueOf(R.string.formula_editor_function_var));
        map.put(Functions.VARNAME.name(), Integer.valueOf(R.string.formula_editor_function_var_name));
        map.put(Functions.VARVALUE.name(), Integer.valueOf(R.string.formula_editor_function_var_value));
        map.put(Functions.JOIN.name(), Integer.valueOf(R.string.formula_editor_function_join));
        map.put(Functions.JOIN3.name(), Integer.valueOf(R.string.formula_editor_function_join3));
        map.put(Functions.DISTANCE.name(), Integer.valueOf(R.string.formula_editor_function_distance));
        map.put(Functions.JOINNUMBER.name(), Integer.valueOf(R.string.formula_editor_function_joinnumber));
        map.put(Functions.REGEX.name(), Integer.valueOf(R.string.formula_editor_function_regex));
        map.put(Functions.FLATTEN.name(), Integer.valueOf(R.string.formula_editor_function_flatten));
        map.put(Functions.CONNECT.name(), Integer.valueOf(R.string.formula_editor_function_connect));
        map.put(Functions.FIND.name(), Integer.valueOf(R.string.formula_editor_function_find));
        map.put(Functions.TABLE_X.name(), Integer.valueOf(R.string.formula_editor_function_table_x));
        map.put(Functions.TABLE_Y.name(), Integer.valueOf(R.string.formula_editor_function_table_y));
        map.put(Functions.VIEW_X.name(), Integer.valueOf(R.string.view_x));
        map.put(Functions.VIEW_Y.name(), Integer.valueOf(R.string.view_y));
        map.put(Functions.VIEW_WIDTH.name(), Integer.valueOf(R.string.view_width));
        map.put(Functions.VIEW_HEIGHT.name(), Integer.valueOf(R.string.view_height));
        map.put(Functions.VIDEO_PLAYING.name(), Integer.valueOf(R.string.is_video_playing));
        map.put(Functions.VIDEO_TIME.name(), Integer.valueOf(R.string.video_time));
        map.put(Functions.FLOATARRAY.name(), Integer.valueOf(R.string.formula_editor_function_floatarray));
        map.put(Functions.TABLE_ELEMENT.name(), Integer.valueOf(R.string.formula_editor_function_table_element));
        map.put(Functions.TABLE_JOIN.name(), Integer.valueOf(R.string.formula_editor_function_table_join));
        map.put(Functions.GET_DIRECTION_X.name(), Integer.valueOf(R.string.formula_vector_dir_x));
        map.put(Functions.GET_DIRECTION_Y.name(), Integer.valueOf(R.string.formula_vector_dir_y));
        map.put(Functions.GET_ANGLE.name(), Integer.valueOf(R.string.formula_vector_angle));
        map.put(Functions.PT_VALUE.name(), Integer.valueOf(R.string.formula_pt_value));
        map.put(Functions.GET_3D_POSITION_X.name(), Integer.valueOf(R.string.formula_3d_pos_x));
        map.put(Functions.PT_TOTALSIZE.name(), Integer.valueOf(R.string.formula_pt_totalsize));
        map.put(Functions.PT_DUMP.name(), Integer.valueOf(R.string.formula_pt_dump));
        map.put(Functions.PT_ARGMAX.name(), Integer.valueOf(R.string.formula_pt_argmax));
        map.put(Functions.F2D_IS_TOUCHED.name(), Integer.valueOf(R.string.formula_f2d_is_touched));
        map.put(Functions.F2D_IS_TOUCHED_INDEX.name(), Integer.valueOf(R.string.formula_f2d_is_touched_index));
        map.put(Functions.F2D_X.name(), Integer.valueOf(R.string.formula_f2d_x));
        map.put(Functions.F2D_Y.name(), Integer.valueOf(R.string.formula_f2d_y));
        map.put(Functions.F2D_ROTATION.name(), Integer.valueOf(R.string.formula_f2d_rotation));
        map.put(Functions.F2D_SCALE_X.name(), Integer.valueOf(R.string.formula_f2d_scale_x));
        map.put(Functions.F2D_SCALE_Y.name(), Integer.valueOf(R.string.formula_f2d_scale_y));
        map.put(Functions.F2D_COLOR_R.name(), Integer.valueOf(R.string.formula_f2d_r));
        map.put(Functions.F2D_COLOR_G.name(), Integer.valueOf(R.string.formula_f2d_g));
        map.put(Functions.F2D_COLOR_B.name(), Integer.valueOf(R.string.formula_f2d_b));
        map.put(Functions.F2D_ALPHA.name(), Integer.valueOf(R.string.formula_f2d_alpha));
        map.put(Functions.F2D_TEXTURE.name(), Integer.valueOf(R.string.formula_f2d_texture));
        map.put(Functions.F2D_CAM_X.name(), Integer.valueOf(R.string.formula_f2d_cam_x));
        map.put(Functions.F2D_CAM_Y.name(), Integer.valueOf(R.string.formula_f2d_cam_y));
        map.put(Functions.F2D_CAM_ZOOM.name(), Integer.valueOf(R.string.formula_f2d_cam_zoom));
        map.put(Functions.PT_SHAPE.name(), Integer.valueOf(R.string.pt_shape));
        map.put(Functions.PT_VALUEND.name(), Integer.valueOf(R.string.formula_pt_valuend));
        map.put(Functions.INTERSECT_LIST.name(), Integer.valueOf(R.string.formula_intersect_list));
        map.put(Functions.COLLISION_LIST.name(), Integer.valueOf(R.string.formula_collision_list));
        map.put(Functions.DELTA.name(), Integer.valueOf(R.string.formula_delta));
        map.put(Functions.OBJECT_TOUCHES_OBJECT.name(), Integer.valueOf(R.string.formula_3d_touches));
        map.put(Functions.OBJECT_INTERSECTS_OBJECT.name(), Integer.valueOf(R.string.formula_3d_intersects));
        map.put(Functions.GET_CAMERA_ROTATION_YAW.name(), Integer.valueOf(R.string.formula_cam_rot_yaw));
        map.put(Functions.GET_CAMERA_ROTATION_ROLL.name(), Integer.valueOf(R.string.formula_cam_rot_roll));
        map.put(Functions.GET_CAMERA_ROTATION_PITCH.name(), Integer.valueOf(R.string.formula_cam_rot_pitch));
        map.put(Functions.GET_3D_POSITION_Y.name(), Integer.valueOf(R.string.formula_3d_pos_y));
        map.put(Functions.GET_CAMERA_POS_X.name(), Integer.valueOf(R.string.formula_cam_pos_x));
        map.put(Functions.GET_3D_VELOCITY_X.name(), Integer.valueOf(R.string.formula_3d_velo_x));
        map.put(Functions.GET_3D_VELOCITY_Y.name(), Integer.valueOf(R.string.formula_3d_velo_y));
        map.put(Functions.GET_3D_VELOCITY_Z.name(), Integer.valueOf(R.string.formula_3d_velo_z));
        map.put(Functions.GET_CAMERA_POS_Y.name(), Integer.valueOf(R.string.formula_cam_pos_y));
        map.put(Functions.GET_CAMERA_POS_Z.name(), Integer.valueOf(R.string.formula_cam_pos_z));
        map.put(Functions.GET_CAMERA_DIR_X.name(), Integer.valueOf(R.string.formula_cam_dir_x));
        map.put(Functions.GET_CAMERA_DIR_Y.name(), Integer.valueOf(R.string.formula_cam_dir_y));
        map.put(Functions.GET_CAMERA_DIR_Z.name(), Integer.valueOf(R.string.formula_cam_dir_z));
        map.put(Functions.GET_3D_POSITION_Z.name(), Integer.valueOf(R.string.formula_3d_pos_z));
        map.put(Functions.GET_3D_ROTATION_YAW.name(), Integer.valueOf(R.string.formula_3d_rot_yaw));
        map.put(Functions.GET_3D_ROTATION_PITCH.name(), Integer.valueOf(R.string.formula_3d_rot_pitch));
        map.put(Functions.GET_3D_ROTATION_ROLL.name(), Integer.valueOf(R.string.formula_3d_rot_roll));
        map.put(Functions.GET_3D_SCALE_X.name(), Integer.valueOf(R.string.formula_3d_scale_x));
        map.put(Functions.GET_3D_SCALE_Y.name(), Integer.valueOf(R.string.formula_3d_scale_y));
        map.put(Functions.GET_3D_SCALE_Z.name(), Integer.valueOf(R.string.formula_3d_scale_z));
        map.put(Functions.GET_3D_DISTANCE.name(), Integer.valueOf(R.string.formula_3d_distance));
        map.put(Functions.GET_RAY_DISTANCE.name(), Integer.valueOf(R.string.formula_ray_distance));
        map.put(Functions.GET_RAY_HIT_OBJECT.name(), Integer.valueOf(R.string.formula_ray_hit_object));
        map.put(Functions.RAY_DID_HIT.name(), Integer.valueOf(R.string.formula_ray_did_hit));
        map.put(Functions.GET_RAY_HIT_X.name(), Integer.valueOf(R.string.formula_ray_hit_x));
        map.put(Functions.GET_RAY_HIT_Y.name(), Integer.valueOf(R.string.formula_ray_hit_y));
        map.put(Functions.GET_RAY_HIT_Z.name(), Integer.valueOf(R.string.formula_ray_hit_z));
        map.put(Functions.GET_RAY_HIT_NORMAL_X.name(), Integer.valueOf(R.string.formula_ray_normal_x));
        map.put(Functions.GET_RAY_HIT_NORMAL_Y.name(), Integer.valueOf(R.string.formula_ray_normal_y));
        map.put(Functions.GET_RAY_HIT_NORMAL_Z.name(), Integer.valueOf(R.string.formula_ray_normal_z));
        map.put(Sensors.DRONE_BATTERY_STATUS.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_battery_status));
        map.put(Sensors.DRONE_EMERGENCY_STATE.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_emergency_state));
        map.put(Sensors.DRONE_FLYING.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_flying));
        map.put(Sensors.DRONE_INITIALIZED.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_initialized));
        map.put(Sensors.DRONE_USB_ACTIVE.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_active));
        map.put(Sensors.DRONE_USB_REMAINING_TIME.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_usb_remaining_time));
        map.put(Sensors.DRONE_CAMERA_READY.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_camera_ready));
        map.put(Sensors.DRONE_RECORD_READY.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_record_ready));
        map.put(Sensors.DRONE_RECORDING.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_recording));
        map.put(Sensors.DRONE_NUM_FRAMES.name(), Integer.valueOf(R.string.formula_editor_sensor_drone_num_frames));
        map.put(Functions.ARDUINODIGITAL.name(), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_digital));
        map.put(Functions.ARDUINOANALOG.name(), Integer.valueOf(R.string.formula_editor_function_arduino_read_pin_value_analog));
        map.put(Functions.RASPIDIGITAL.name(), Integer.valueOf(R.string.formula_editor_function_raspi_read_pin_value_digital));
        map.put(Sensors.FINGER_X.name(), Integer.valueOf(R.string.formula_editor_function_finger_x));
        map.put(Sensors.FINGER_Y.name(), Integer.valueOf(R.string.formula_editor_function_finger_y));
        map.put(Sensors.FINGER_TOUCHED.name(), Integer.valueOf(R.string.formula_editor_function_is_finger_touching));
        map.put(Functions.MULTI_FINGER_X.name(), Integer.valueOf(R.string.formula_editor_function_multi_finger_x));
        map.put(Functions.MULTI_FINGER_Y.name(), Integer.valueOf(R.string.formula_editor_function_multi_finger_y));
        map.put(Functions.MULTI_FINGER_TOUCHED.name(), Integer.valueOf(R.string.formula_editor_function_is_multi_finger_touching));
        map.put(Functions.INDEX_CURRENT_TOUCH.name(), Integer.valueOf(R.string.formula_editor_function_index_of_current_touch));
        map.put(Sensors.LAST_FINGER_INDEX.name(), Integer.valueOf(R.string.formula_editor_function_index_of_last_finger));
        map.put(Sensors.NUMBER_CURRENT_TOUCHES.name(), Integer.valueOf(R.string.formula_editor_function_number_of_current_touches));
        map.put(Functions.LIST_ITEM.name(), Integer.valueOf(R.string.formula_editor_function_list_item));
        map.put(Functions.CONTAINS.name(), Integer.valueOf(R.string.formula_editor_function_contains));
        map.put(Functions.INDEX_OF_ITEM.name(), Integer.valueOf(R.string.formula_editor_function_index_of_item));
        map.put(Functions.COLOR_AT_XY.name(), Integer.valueOf(R.string.formula_editor_sensor_color_at_x_y));
        map.put(Functions.COLOR_EQUALS_COLOR.name(), Integer.valueOf(R.string.formula_editor_sensor_color_equals_color));
        map.put(Sensors.X_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_x_acceleration));
        map.put(Sensors.Y_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_y_acceleration));
        map.put(Sensors.Z_ACCELERATION.name(), Integer.valueOf(R.string.formula_editor_sensor_z_acceleration));
        map.put(Sensors.COMPASS_DIRECTION.name(), Integer.valueOf(R.string.formula_editor_sensor_compass_direction));
        map.put(Sensors.LATITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_latitude));
        map.put(Sensors.LONGITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_longitude));
        map.put(Sensors.LOCATION_ACCURACY.name(), Integer.valueOf(R.string.formula_editor_sensor_location_accuracy));
        map.put(Sensors.ALTITUDE.name(), Integer.valueOf(R.string.formula_editor_sensor_altitude));
        map.put(Sensors.USER_LANGUAGE.name(), Integer.valueOf(R.string.formula_editor_sensor_user_language));
        map.put(Sensors.X_INCLINATION.name(), Integer.valueOf(R.string.formula_editor_sensor_x_inclination));
        map.put(Sensors.Y_INCLINATION.name(), Integer.valueOf(R.string.formula_editor_sensor_y_inclination));
        map.put(Sensors.FACE_DETECTED.name(), Integer.valueOf(R.string.formula_editor_sensor_face_detected));
        map.put(Sensors.FACE_SIZE.name(), Integer.valueOf(R.string.formula_editor_sensor_face_size));
        map.put(Sensors.FACE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_face_x_position));
        map.put(Sensors.FACE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_face_y_position));
        map.put(Sensors.SECOND_FACE_DETECTED.name(), Integer.valueOf(R.string.formula_editor_sensor_second_face_detected));
        map.put(Sensors.SECOND_FACE_SIZE.name(), Integer.valueOf(R.string.formula_editor_sensor_second_face_size));
        map.put(Sensors.SECOND_FACE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_second_face_x_position));
        map.put(Sensors.SECOND_FACE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_second_face_y_position));
        map.put(Sensors.HEAD_TOP_X.name(), Integer.valueOf(R.string.formula_editor_sensor_head_top_x));
        map.put(Sensors.HEAD_TOP_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_head_top_y));
        map.put(Sensors.NECK_X.name(), Integer.valueOf(R.string.formula_editor_sensor_neck_x));
        map.put(Sensors.NECK_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_neck_y));
        map.put(Sensors.NOSE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_nose_x));
        map.put(Sensors.NOSE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_nose_y));
        map.put(Sensors.LEFT_EYE_INNER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_inner_x));
        map.put(Sensors.LEFT_EYE_INNER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_inner_y));
        map.put(Sensors.LEFT_EYE_CENTER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_center_x));
        map.put(Sensors.LEFT_EYE_CENTER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_center_y));
        map.put(Sensors.LEFT_EYE_OUTER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_outer_x));
        map.put(Sensors.LEFT_EYE_OUTER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_eye_outer_y));
        map.put(Sensors.RIGHT_EYE_INNER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_inner_x));
        map.put(Sensors.RIGHT_EYE_INNER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_inner_y));
        map.put(Sensors.RIGHT_EYE_CENTER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_center_x));
        map.put(Sensors.RIGHT_EYE_CENTER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_center_y));
        map.put(Sensors.RIGHT_EYE_OUTER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_outer_x));
        map.put(Sensors.RIGHT_EYE_OUTER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_eye_outer_y));
        map.put(Sensors.LEFT_EAR_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_ear_x));
        map.put(Sensors.LEFT_EAR_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_ear_y));
        map.put(Sensors.RIGHT_EAR_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_ear_x));
        map.put(Sensors.RIGHT_EAR_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_ear_y));
        map.put(Sensors.MOUTH_LEFT_CORNER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_mouth_left_corner_x));
        map.put(Sensors.MOUTH_LEFT_CORNER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_mouth_left_corner_y));
        map.put(Sensors.MOUTH_RIGHT_CORNER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_mouth_right_corner_x));
        map.put(Sensors.MOUTH_RIGHT_CORNER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_mouth_right_corner_y));
        map.put(Sensors.LEFT_SHOULDER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_x));
        map.put(Sensors.LEFT_SHOULDER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_shoulder_y));
        map.put(Functions.NOTIFICATION_REPLY.name(), Integer.valueOf(R.string.formula_notification_reply));
        map.put(Sensors.RIGHT_SHOULDER_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_x));
        map.put(Sensors.RIGHT_SHOULDER_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_shoulder_y));
        map.put(Sensors.LEFT_ELBOW_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_x));
        map.put(Sensors.LEFT_ELBOW_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_elbow_y));
        map.put(Sensors.RIGHT_ELBOW_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_x));
        map.put(Sensors.RIGHT_ELBOW_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_elbow_y));
        map.put(Sensors.LEFT_WRIST_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_x));
        map.put(Sensors.LEFT_WRIST_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_wrist_y));
        map.put(Sensors.RIGHT_WRIST_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_x));
        map.put(Sensors.RIGHT_WRIST_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_wrist_y));
        map.put(Sensors.LEFT_PINKY_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_pinky_knuckle_x));
        map.put(Sensors.LEFT_PINKY_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_pinky_knuckle_y));
        map.put(Sensors.RIGHT_PINKY_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_pinky_knuckle_x));
        map.put(Sensors.RIGHT_PINKY_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_pinky_knuckle_y));
        map.put(Sensors.LEFT_INDEX_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_index_knuckle_x));
        map.put(Sensors.LEFT_INDEX_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_index_knuckle_y));
        map.put(Sensors.RIGHT_INDEX_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_index_knuckle_x));
        map.put(Sensors.RIGHT_INDEX_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_index_knuckle_y));
        map.put(Sensors.LEFT_THUMB_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_thumb_knuckle_x));
        map.put(Sensors.LEFT_THUMB_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_thumb_knuckle_y));
        map.put(Sensors.RIGHT_THUMB_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_thumb_knuckle_x));
        map.put(Sensors.RIGHT_THUMB_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_thumb_knuckle_y));
        map.put(Sensors.LEFT_HIP_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_hip_x));
        map.put(Sensors.LEFT_HIP_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_hip_y));
        map.put(Sensors.RIGHT_HIP_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_hip_x));
        map.put(Sensors.RIGHT_HIP_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_hip_y));
        map.put(Sensors.LEFT_KNEE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_knee_x));
        map.put(Sensors.LEFT_KNEE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_knee_y));
        map.put(Sensors.RIGHT_KNEE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_knee_x));
        map.put(Sensors.RIGHT_KNEE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_knee_y));
        map.put(Sensors.LEFT_ANKLE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_x));
        map.put(Sensors.LEFT_ANKLE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_ankle_y));
        map.put(Sensors.RIGHT_ANKLE_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_x));
        map.put(Sensors.RIGHT_ANKLE_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_ankle_y));
        map.put(Sensors.LEFT_HEEL_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_heel_x));
        map.put(Sensors.LEFT_HEEL_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_heel_y));
        map.put(Sensors.RIGHT_HEEL_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_heel_x));
        map.put(Sensors.RIGHT_HEEL_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_heel_y));
        map.put(Sensors.LEFT_FOOT_INDEX_X.name(), Integer.valueOf(R.string.formula_editor_sensor_left_foot_index_x));
        map.put(Sensors.LEFT_FOOT_INDEX_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_left_foot_index_y));
        map.put(Sensors.RIGHT_FOOT_INDEX_X.name(), Integer.valueOf(R.string.formula_editor_sensor_right_foot_index_x));
        map.put(Sensors.RIGHT_FOOT_INDEX_Y.name(), Integer.valueOf(R.string.formula_editor_sensor_right_foot_index_y));
        map.put(Functions.CAMERA_X.name(), Integer.valueOf(R.string.formula_camera_x));
        map.put(Functions.CAMERA_Y.name(), Integer.valueOf(R.string.formula_camera_y));
        map.put(Functions.CAMERA_ROTATION.name(), Integer.valueOf(R.string.formula_camera_rotation));
        map.put(Functions.CAMERA_ZOOM.name(), Integer.valueOf(R.string.formula_camera_zoom));
        map.put(Sensors.TEXT_FROM_CAMERA.name(), Integer.valueOf(R.string.formula_editor_sensor_text_from_camera));
        map.put(Sensors.TEXT_BLOCKS_NUMBER.name(), Integer.valueOf(R.string.formula_editor_sensor_text_blocks_number));
        map.put(Sensors.TEXT_BLOCK_X.name(), Integer.valueOf(R.string.formula_editor_function_text_block_x));
        map.put(Sensors.TEXT_BLOCK_Y.name(), Integer.valueOf(R.string.formula_editor_function_text_block_y));
        map.put(Sensors.TEXT_BLOCK_SIZE.name(), Integer.valueOf(R.string.formula_editor_function_text_block_size));
        map.put(Sensors.TEXT_BLOCK_FROM_CAMERA.name(), Integer.valueOf(R.string.formula_editor_function_text_block_from_camera));
        map.put(Sensors.TEXT_BLOCK_LANGUAGE_FROM_CAMERA.name(), Integer.valueOf(R.string.formula_editor_function_text_block_language_from_camera));
        map.put(Sensors.FPS.name(), Integer.valueOf(R.string.formula_editor_sensor_fps));
        map.put(Sensors.GAMEPAD_A_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_a_pressed));
        map.put(Sensors.GAMEPAD_B_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_b_pressed));
        map.put(Sensors.GAMEPAD_UP_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_up_pressed));
        map.put(Sensors.GAMEPAD_DOWN_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_down_pressed));
        map.put(Sensors.GAMEPAD_LEFT_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_left_pressed));
        map.put(Sensors.GAMEPAD_RIGHT_PRESSED.name(), Integer.valueOf(R.string.formula_editor_sensor_gamepad_right_pressed));
        map.put(Sensors.NXT_SENSOR_1.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_1));
        map.put(Sensors.NXT_SENSOR_2.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_2));
        map.put(Sensors.NXT_SENSOR_3.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_3));
        map.put(Sensors.NXT_SENSOR_4.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_4));
        map.put(Sensors.EV3_SENSOR_1.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_1));
        map.put(Sensors.EV3_SENSOR_2.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_2));
        map.put(Sensors.EV3_SENSOR_3.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_3));
        map.put(Sensors.EV3_SENSOR_4.name(), Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_4));
        map.put(Sensors.PHIRO_FRONT_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_left));
        map.put(Sensors.PHIRO_FRONT_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_front_right));
        map.put(Sensors.PHIRO_SIDE_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_left));
        map.put(Sensors.PHIRO_SIDE_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_side_right));
        map.put(Sensors.PHIRO_BOTTOM_LEFT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_left));
        map.put(Sensors.PHIRO_BOTTOM_RIGHT.name(), Integer.valueOf(R.string.formula_editor_phiro_sensor_bottom_right));
        map.put(Sensors.NFC_TAG_ID.name(), Integer.valueOf(R.string.formula_editor_nfc_tag_id));
        map.put(Sensors.NFC_TAG_MESSAGE.name(), Integer.valueOf(R.string.formula_editor_nfc_tag_message));
        map.put(Sensors.LOUDNESS.name(), Integer.valueOf(R.string.formula_editor_sensor_loudness));
        map.put(Functions.READ_FILE.name(), Integer.valueOf(R.string.formula_read_file));
        map.put(Functions.MEDIA_DURATION.name(), Integer.valueOf(R.string.formula_media_duration));
        map.put(Functions.IMAGE_WIDTH.name(), Integer.valueOf(R.string.formula_image_width));
        map.put(Functions.IMAGE_HEIGHT.name(), Integer.valueOf(R.string.formula_image_height));
        map.put(Functions.FILE_LAST_MODIFIED.name(), Integer.valueOf(R.string.formula_file_last_modified));
        map.put(Sensors.STAGE_WIDTH.name(), Integer.valueOf(R.string.formula_editor_sensor_stage_width));
        map.put(Sensors.STAGE_HEIGHT.name(), Integer.valueOf(R.string.formula_editor_sensor_stage_height));
        map.put(Sensors.BATTARY.name(), Integer.valueOf(R.string.formula_editor_sensor_battary));
        map.put(Sensors.MICRO.name(), Integer.valueOf(R.string.formula_editor_sensor_micro));
        map.put(Sensors.FREQ.name(), Integer.valueOf(R.string.formula_editor_sensor_micro_freq));
        map.put(Sensors.IP.name(), Integer.valueOf(R.string.formula_editor_sensor_ip));
        map.put(Sensors.PORT.name(), Integer.valueOf(R.string.formula_editor_sensor_port));
        map.put(Sensors.CLIPBOARD_TEXT.name(), Integer.valueOf(R.string.formula_clipboard_text));
        map.put(Sensors.KEYBOARD_HEIGHT.name(), Integer.valueOf(R.string.formula_keyboard_height));
        map.put(Sensors.INTERNET.name(), Integer.valueOf(R.string.formula_editor_sensor_internet));
        map.put(Sensors.ARCH.name(), Integer.valueOf(R.string.formula_editor_sensor_architecture));
        map.put(Functions.JSON_GET.name(), Integer.valueOf(R.string.formula_editor_function_json_get));
        map.put(Functions.JSON_SET.name(), Integer.valueOf(R.string.formula_editor_function_json_set));
        map.put(Functions.JSON_IS_VALID.name(), Integer.valueOf(R.string.formula_editor_function_json_is_valid));
        map.put(Sensors.TIMER.name(), Integer.valueOf(R.string.formula_editor_sensor_timer));
        map.put(Sensors.DATE_YEAR.name(), Integer.valueOf(R.string.formula_editor_sensor_date_year));
        map.put(Sensors.DATE_MONTH.name(), Integer.valueOf(R.string.formula_editor_sensor_date_month));
        map.put(Sensors.DATE_DAY.name(), Integer.valueOf(R.string.formula_editor_sensor_date_day));
        map.put(Sensors.DATE_WEEKDAY.name(), Integer.valueOf(R.string.formula_editor_sensor_date_weekday));
        map.put(Sensors.TIME_HOUR.name(), Integer.valueOf(R.string.formula_editor_sensor_time_hour));
        map.put(Sensors.TIME_MINUTE.name(), Integer.valueOf(R.string.formula_editor_sensor_time_minute));
        map.put(Sensors.TIME_SECOND.name(), Integer.valueOf(R.string.formula_editor_sensor_time_second));
        map.put(Functions.RAY_DID_HIT2.name(), Integer.valueOf(R.string.formula_ray_did_hit2));
        map.put(Functions.RAY_HIT_SPRITE_NAME.name(), Integer.valueOf(R.string.formula_ray_hit_sprite_name));
        map.put(Functions.RAY_HIT_X.name(), Integer.valueOf(R.string.formula_ray_hit_x2));
        map.put(Functions.RAY_HIT_Y.name(), Integer.valueOf(R.string.formula_ray_hit_y2));
        map.put(Functions.RAY_HIT_DISTANCE.name(), Integer.valueOf(R.string.formula_ray_hit_distance));
        map.put(Sensors.OBJECT_X.name(), Integer.valueOf(R.string.formula_editor_object_x));
        map.put(Functions.VOXEL_GET_ID.name(), Integer.valueOf(R.string.formula_voxel_get_id));
        map.put(Functions.VOXEL_GET_DATA.name(), Integer.valueOf(R.string.formula_voxel_get_data));
        map.put(Functions.HTTP_RESPONSE_TEXT.name(), Integer.valueOf(R.string.formula_http_response_text));
        map.put(Functions.HTTP_RESPONSE_CODE.name(), Integer.valueOf(R.string.formula_http_response_code));
        map.put(Functions.HTTP_RESPONSE_HEADER.name(), Integer.valueOf(R.string.formula_http_response_header));
        map.put(Sensors.OBJECT_Y.name(), Integer.valueOf(R.string.formula_editor_object_y));
        map.put(Sensors.OBJECT_TRANSPARENCY.name(), Integer.valueOf(R.string.formula_editor_object_transparency));
        map.put(Sensors.OBJECT_BRIGHTNESS.name(), Integer.valueOf(R.string.formula_editor_object_brightness));
        map.put(Sensors.OBJECT_COLOR.name(), Integer.valueOf(R.string.formula_editor_object_color));
        map.put(Sensors.OBJECT_SIZE.name(), Integer.valueOf(R.string.formula_editor_object_size));
        map.put(Sensors.OBJECT_WIDTH.name(), Integer.valueOf(R.string.formula_editor_object_width));
        map.put(Sensors.OBJECT_HEIGHT.name(), Integer.valueOf(R.string.formula_editor_object_height));
        map.put(Sensors.MOTION_DIRECTION.name(), Integer.valueOf(R.string.formula_editor_object_rotation));
        map.put(Sensors.LOOK_DIRECTION.name(), Integer.valueOf(R.string.formula_editor_object_rotation_look));
        map.put(Sensors.OBJECT_LAYER.name(), Integer.valueOf(R.string.formula_editor_object_layer));
        map.put(Sensors.NFC_TECH_LIST.name(), Integer.valueOf(R.string.formula_editor_sensor_nfc_techs));
        map.put(Sensors.NFC_MEMORY_SIZE.name(), Integer.valueOf(R.string.formula_editor_sensor_nfc_size));
        map.put(Sensors.NFC_IS_WRITABLE.name(), Integer.valueOf(R.string.formula_editor_sensor_nfc_writable));
        map.put(Sensors.NFC_TAG_TYPE.name(), Integer.valueOf(R.string.formula_editor_sensor_nfc_type));
        map.put(Sensors.COLLIDES_WITH_EDGE.name(), Integer.valueOf(R.string.formula_editor_function_collides_with_edge));
        map.put(Sensors.COLLIDES_WITH_FINGER.name(), Integer.valueOf(R.string.formula_editor_function_touched));
        map.put(Functions.COLLIDES_WITH_COLOR.name(), Integer.valueOf(R.string.formula_editor_function_collides_with_color));
        map.put(Functions.COLOR_TOUCHES_COLOR.name(), Integer.valueOf(R.string.formula_editor_function_color_touches_color));
        map.put(Sensors.OBJECT_X_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_x_velocity));
        map.put(Sensors.OBJECT_Y_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_y_velocity));
        map.put(Functions.MQTT_MESSAGE.name(), Integer.valueOf(R.string.formula_mqtt_message));
        map.put(Sensors.OBJECT_ANGULAR_VELOCITY.name(), Integer.valueOf(R.string.formula_editor_object_angular_velocity));
        map.put(Functions.FILE_TO_BASE64.name(), Integer.valueOf(R.string.formula_file_to_base64));
        map.put(Functions.MD5.name(), Integer.valueOf(R.string.formula_md5));
        map.put(Functions.SHA256.name(), Integer.valueOf(R.string.formula_sha256));
        map.put(Functions.FILE_MD5.name(), Integer.valueOf(R.string.formula_file_md5));
        map.put(Functions.FILE_SHA256.name(), Integer.valueOf(R.string.formula_file_sha256));
        map.put(Sensors.OBJECT_LOOK_NUMBER.name(), Integer.valueOf(R.string.formula_editor_object_look_number));
        map.put(Sensors.OBJECT_LOOK_WIDTH.name(), Integer.valueOf(R.string.formula_editor_object_look_width));
        map.put(Sensors.OBJECT_LOOK_HEIGHT.name(), Integer.valueOf(R.string.formula_editor_object_look_height));
        map.put(Sensors.OBJECT_LOOK_NAME.name(), Integer.valueOf(R.string.formula_editor_object_look_name));
        map.put(Sensors.OBJECT_NAME.name(), Integer.valueOf(R.string.formula_get_object_name));
        map.put(Sensors.OBJECT_NUMBER_OF_LOOKS.name(), Integer.valueOf(R.string.formula_editor_object_number_of_looks));
        map.put(Sensors.OBJECT_BACKGROUND_NUMBER.name(), Integer.valueOf(R.string.formula_editor_object_background_number));
        map.put(Sensors.OBJECT_BACKGROUND_NAME.name(), Integer.valueOf(R.string.formula_editor_object_background_name));
        map.put(Sensors.OBJECT_DISTANCE_TO.name(), Integer.valueOf(R.string.formula_editor_object_distance_to));
        map.put(Operators.LOGICAL_NOT.name(), Integer.valueOf(R.string.formula_editor_logic_not));
        map.put(Operators.NOT_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_notequal));
        map.put(Operators.EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_equal));
        map.put(Operators.GREATER_OR_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_greaterequal));
        map.put(Operators.GREATER_THAN.name(), Integer.valueOf(R.string.formula_editor_logic_greaterthan));
        map.put(Operators.LOGICAL_AND.name(), Integer.valueOf(R.string.formula_editor_logic_and));
        map.put(Operators.LOGICAL_OR.name(), Integer.valueOf(R.string.formula_editor_logic_or));
        map.put(Operators.SMALLER_OR_EQUAL.name(), Integer.valueOf(R.string.formula_editor_logic_leserequal));
        map.put(Operators.SMALLER_THAN.name(), Integer.valueOf(R.string.formula_editor_logic_lesserthan));
        map.put(Sensors.SPEECH_RECOGNITION_LANGUAGE.name(), Integer.valueOf(R.string.formula_editor_listening_language_sensor));
        map.put(Functions.ID_OF_DETECTED_OBJECT.name(), Integer.valueOf(R.string.formula_editor_function_get_id_of_detected_object));
        map.put(Functions.OBJECT_WITH_ID_VISIBLE.name(), Integer.valueOf(R.string.formula_editor_function_object_with_id_visible));
    }

    public InternToExternGenerator(Context context) {
        this.context = context;
    }

    public void generateExternStringAndMapping(List<InternToken> internTokenFormula) {
        generateStringAndMappingInternal(internTokenFormula, false);
    }

    public void trimExternString(List<InternToken> internTokenFormula) {
        generateStringAndMappingInternal(internTokenFormula, true);
    }

    private void generateStringAndMappingInternal(List<InternToken> internTokenFormula, boolean trimNumbers) {
        Log.i(TAG, "generateExternStringAndMapping:enter");
        LinkedList linkedList = new LinkedList();
        Iterator<InternToken> it = internTokenFormula.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next());
        }
        this.generatedExternInternRepresentationMapping = new ExternInternRepresentationMapping();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        InternToken internToken = null;
        InternToken internToken2 = null;
        while (!linkedList.isEmpty()) {
            if (appendWhiteSpace(internToken, internToken2)) {
                sb.append(StringUtil.SPACE);
            }
            int length = sb.length();
            InternToken internToken3 = (InternToken) linkedList.get(0);
            InternToken internToken4 = linkedList.size() < 2 ? null : (InternToken) linkedList.get(1);
            sb.append(generateExternStringFromToken(internToken3, trimNumbers));
            this.generatedExternInternRepresentationMapping.putMapping(length, sb.length(), i);
            linkedList.remove(0);
            i++;
            internToken = internToken3;
            internToken2 = internToken4;
        }
        sb.append(StringUtil.SPACE);
        this.generatedExternFormulaString = sb.toString();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternToExternGenerator$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.InternToExternGenerator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType;

        static {
            int[] iArr = new int[InternTokenType.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType = iArr;
            try {
                iArr[InternTokenType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.OPERATOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_NAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_OPEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_CLOSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETER_DELIMITER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_VARIABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_LIST.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_DEFINED_BRICK_INPUT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.STRING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.COLLISION_FORMULA.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    private String generateExternStringFromToken(InternToken internToken, boolean trimNumbers) {
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()]) {
            case 1:
                return getExternStringForNumber(internToken.getTokenStringValue(), trimNumbers);
            case 2:
                String tokenStringValue = internToken.getTokenStringValue();
                String externStringForInternTokenValue = getExternStringForInternTokenValue(tokenStringValue, this.context);
                return externStringForInternTokenValue == null ? tokenStringValue : externStringForInternTokenValue;
            case 3:
                String tokenStringValue2 = internToken.getTokenStringValue();
                String externStringForInternTokenValue2 = getExternStringForInternTokenValue(tokenStringValue2, this.context);
                if (externStringForInternTokenValue2 != null) {
                    return externStringForInternTokenValue2;
                }
                CustomFormula formulaByUniqueName = CustomFormulaManager.INSTANCE.getFormulaByUniqueName(tokenStringValue2);
                return formulaByUniqueName != null ? formulaByUniqueName.getDisplayName() : tokenStringValue2;
            case 4:
            case 5:
                return "(";
            case 6:
            case 7:
                return Constant.AFTER_QUTO;
            case 8:
                return ",";
            case 9:
                return "\"" + internToken.getTokenStringValue() + "\"";
            case 10:
                return "*" + internToken.getTokenStringValue() + "*";
            case 11:
                return "[" + internToken.getTokenStringValue() + "]";
            case 12:
                return FormatHelper.QUOTE + internToken.getTokenStringValue() + FormatHelper.QUOTE;
            case 13:
                return CatroidApplication.getAppContext().getString(R.string.formula_editor_function_collision) + "(" + internToken.getTokenStringValue() + Constant.AFTER_QUTO;
            default:
                return getExternStringForInternTokenValue(internToken.getTokenStringValue(), this.context);
        }
    }

    private String getExternStringForNumber(String number, boolean trimNumbers) {
        if (trimNumbers) {
            number = getNumberExponentRepresentation(number);
        }
        if (!number.contains(".")) {
            return number;
        }
        return number.substring(0, number.indexOf(46)) + getExternStringForInternTokenValue(".", this.context) + number.substring(number.indexOf(46) + 1);
    }

    private String getNumberExponentRepresentation(String number) {
        Double dValueOf = Double.valueOf(Double.parseDouble(number));
        String strValueOf = String.valueOf(dValueOf);
        return (dValueOf.doubleValue() >= 1.0d || !strValueOf.contains(ExifInterface.LONGITUDE_EAST)) ? FormatNumberUtil.cutTrailingZeros(number) : strValueOf;
    }

    private boolean appendWhiteSpace(InternToken currentToken, InternToken nextToken) {
        if (currentToken == null) {
            return false;
        }
        return nextToken == null || AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[nextToken.getInternTokenType().ordinal()] != 5;
    }

    public String getGeneratedExternFormulaString() {
        return this.generatedExternFormulaString;
    }

    public ExternInternRepresentationMapping getGeneratedExternInternRepresentationMapping() {
        return this.generatedExternInternRepresentationMapping;
    }

    private String getExternStringForInternTokenValue(String internTokenValue, Context context) {
        Integer num = INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.get(internTokenValue);
        if (num == null) {
            return null;
        }
        return context.getString(num.intValue());
    }

    public static int getMappedString(String token) {
        return INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.get(token).intValue();
    }

    public static void setInternExternLanguageConverterMap(Sensors sensor, Integer output) {
        INTERN_EXTERN_LANGUAGE_CONVERTER_MAP.put(sensor.name(), output);
    }
}
