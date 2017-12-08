package com.brandnew.greatlauncher.viewutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.DatabaseHelper;
import com.brandnew.greatlauncher.util.Utils;

import java.util.List;

/**
 * Created by Георгий on 22.07.2017.
 */

public class DialogHelper {
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
    }

    public void buildDeleteDialog(int position, AppAdapter adapter,
                                  List<AppInfo> list, String tag, DatabaseHelper db) {
        new MaterialDialog.Builder(context)
                .title(R.string.del_current_app)
                .positiveText(R.string.positive_response)
                .negativeText(R.string.negative_response)
                .onPositive((dialog, which) -> {
                    adapter.notifyItemRemoved(position);    //item removed from recylcerview
                    int newId = AppManager.getListId(list, position);
                    if (tag.equals("left")) {
                        db.delRecLeftTable(newId);
                    } else if (tag.equals("right")) {
                        db.delRecRightTable(newId);
                    }
                    list.remove(position);
                })

                .onNegative((dialog, which) -> {
                    adapter.notifyItemRemoved(position + 1);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                })
                .show();
    }

    public void buildErrorDialog() {
        new MaterialDialog.Builder(context)
                .title("Упс, приносим извинения!")
                .content("В данный момент функция не работает")
                .positiveText("Ок")
                .show();
    }

    public void buildAboutDialog() {
        Drawable launcherIcon = context.getResources().getDrawable(R.drawable.icon_dialog_about);
        new MaterialDialog.Builder(context)
                .title("Odd Launcher")
                .icon(launcherIcon)
                .limitIconToDefaultSize()
                .content(R.string.about_dialog_text)
                .positiveText(R.string.rate_app)
                .onPositive((dialog, which) -> Utils.openAppRating(context))
                .show();
    }

    public void buildSeekBarDialog() {
        new MaterialDialog.Builder(context)
                .title(R.string.del_current_app)
                .positiveText(R.string.positive_response)
                .onPositive((dialog, which) ->  {
                        final SeekBar seek = new SeekBar(context);
                        seek.setMax(400);
                        seek.setKeyProgressIncrement(1);
                })
                .show();

    }


}
