package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.brandnew.greatlauncher.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * Created by Георгий on 22.07.2017.
 */

public class SettingsHelper extends View {
    private Drawable normal, other, red, rhombLeft, rhombRight, ovalLeft, ovalRight;
    private static int COLOR_VALUE;
    private static int SIZE_VALUE;
    private int progress;

    public static String CONST_PREF;

    //prefs
    public static final String PREF_LEFT_LIST = "pref_left_list";
    public static final String PREF_RIGHT_LIST = "pref_right_list";
    public static final String PREF_SEARCHBAR = "pref_searchbar";
    public static final String PREF_SEARCHLIST = "pref_searchlist";
    public static final String PREF_MENU = "pref_menu";
    public static final String PREF_LEFT_LIST_TEXT = "pref_left_list_text";
    public static final String PREF_SEEKBAR_SIZE_MAIN = "pref_seekbar";
    public static final String PREF_SEEKBAR_ALPHA_MAIN = "pref_seekbar_alpha_main";
    public static final String PREF_SEEKBAR_ALPHA_SEARCH_BUTTON = "pref_seekbar_alpha_search";
    public static final String PREF_URL = "pref_search_url";

    //keys
    public static final String KEY_LEFT_LIST = "key_left_list";
    public static final String KEY_RIGHT_LIST = "key_right_list";
    public static final String KEY_SEARCHBAR = "key_searchbar";
    public static final String KEY_SEARCHLIST = "key_searchlist";
    public static final String KEY_MENU = "key_menu";
    public static final String KEY_LEFT_LIST_TEXT = "key_left_list_text";
    public static final String KEY_SEEKBAR_SIZE_MAIN = "key_seekbar";
    public static final String KEY_SEEKBAR_ALPHA_MAIN = "key_seekbar_alpha_main";
    public static final String KEY_SEEKBAR_ALPHA_SEARCH_BUTTON = "pref_seekbar_alpha_search";
    public static final String KEY_URL = "key_url";


    public SettingsHelper(Context context) {
        super(context);
    }

    public void setColorLeft(String newColorKey, ImageButton imageButton) {
        normal = (ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector));
        red = (ContextCompat.getDrawable(getContext(), R.drawable.left_2_red));

        if (newColorKey.equals(getContext().getString(R.string.pref_color_red_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector_red));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_initial_value))) {
            imageButton.setBackground(normal);
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_orange_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector_orange));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_yellow_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector_yellow));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_grey_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector_grey));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_rhomb_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_rhomb_selector));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_hexagon_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_hexagon_selector));
        }
    }

    public void setColorRight(String newColorKey, ImageButton imageButton) {
        normal = (ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector));
        red = (ContextCompat.getDrawable(getContext(), R.drawable.right_2_red));

        if (newColorKey.equals(getContext().getString(R.string.pref_color_red_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector_red));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_initial_value))) {
            imageButton.setBackground(normal);
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_orange_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector_orange));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_yellow_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector_yellow));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_grey_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector_grey));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_rhomb_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_rhomb_selector));
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_label_hexagon_value))) {
            imageButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_hexagon_selector));
        }
    }

    public void setSizeMainButtons(ImageButton imageButton, int scale) {
        ViewGroup.LayoutParams params = imageButton.getLayoutParams();
        params.width = (int) (scale / 2.4);
        params.height = scale;
        imageButton.setLayoutParams(params);
    }

    public void setSizeOtherViews(View view, int scale) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = scale;
        params.height = scale;
        view.setLayoutParams(params);

    }

    public void setAlphaMainButtons(ImageView view, float alpha) {
        view.setAlpha(alpha / 10);
    }

    public void setAlphaSearchButton(ImageView btn, float alpha) {
        btn.setAlpha(alpha / 10);
    }


    //later
    public void setForm(String newFormKey, ImageButton imageButtonLeft, ImageButton imageButtonRight) {
        ovalLeft = (ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector));
        ovalRight = (ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector));

        rhombLeft = (ContextCompat.getDrawable(getContext(), R.drawable.left_rhomb));
        rhombRight = (ContextCompat.getDrawable(getContext(), R.drawable.right_rhomb));

        if (newFormKey.equals(getContext().getString(R.string.pref_form_oval_value))) {
            imageButtonLeft.setBackground(ovalLeft);
            imageButtonRight.setBackground(ovalRight);
        }
        if (newFormKey.equals(getContext().getString(R.string.pref_form_hexagon_value))) {
            imageButtonLeft.setBackground(rhombLeft);
            imageButtonRight.setBackground(rhombRight);
        }
//        if (newFormKey.equals(getContext().getString(R.string.pref_color_orange_value))) {
//            imageButton.setBackground(rhombLeft);
//        }

    }

    public void setColorForViews(final String key) {
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Выберите цвет")
                .initialColor(R.color.colorPrimary)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("Ок", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        setColorValue(selectedColor);
                        putIntValueInSharedPreference(getPref(), getContext(), key, getColorValue());
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void setSeekbarEndpointValue(final String key, String title, int maxValue) {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(getContext());
        final SeekBar seek = new SeekBar(getContext());
        seek.setMax(maxValue);
        seek.setKeyProgressIncrement(1);
        popDialog.setTitle(title);
        popDialog.setView(seek);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress = seekBar.getProgress();
                setSizeValue(progress);
            }
        });
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        putIntValueInSharedPreference(getPref(), getContext(), key, getSizeValue());
                    }
                });
        popDialog.create();
        popDialog.show();
    }


    public static void setSizeValue(int size) {
        SIZE_VALUE = size;
    }

    public static int getSizeValue() {
        return SIZE_VALUE;
    }


    public void setColorValue(int selectedColor) { //was static
        COLOR_VALUE = selectedColor;
    }

    public int getColorValue() {
        return COLOR_VALUE;
    }

    public static void putIntValueInSharedPreference(String pref, Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setPref(String pref) {
        CONST_PREF = pref;
    }

    public String getPref() {
        return CONST_PREF;
    }


//    later


    public void setAnim(String newColorKey, RecyclerView recyclerView) {
        normal = (ContextCompat.getDrawable(getContext(), R.drawable.left_oval_selector));
        other = (ContextCompat.getDrawable(getContext(), R.drawable.right_oval_selector));

        if (newColorKey.equals(getContext().getString(R.string.pref_anim_standard_value))) {
//            recyclerView.set
        } else if (newColorKey.equals(getContext().getString(R.string.pref_anim_twist_value))) {
        }

    }

}
