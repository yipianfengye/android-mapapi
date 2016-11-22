package xiaomi.mich.com.android_mapapi;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Desc: 全局的弹窗工具类
 */
public class DialogUtil {

    private static DialogUtil instance;
    public static Activity mActivity;
    private static MaterialDialog materialDialog;

    private DialogUtil() {
    }

    /**
     * 返回一个弹窗实例
     *
     * @param activity
     * @return
     */
    public synchronized static DialogUtil getInstance(Activity activity) {
        if (mActivity != activity) {
            mActivity = activity;
            instance = new DialogUtil();
        }
        return instance;
    }

    /**
     * 显示一个MD风格的弹窗,不包含Title
     *
     * @param content
     * @param leftBtnText
     * @param rightBtnText
     * @param onBtnLeftClickL
     * @param onBtnRightClickL
     */
    public Dialog showMaterialDialogNoTitle(String content, String leftBtnText, String rightBtnText, final View.OnClickListener onBtnLeftClickL, final View.OnClickListener onBtnRightClickL) {
        return showMaterialDialog(null, content, leftBtnText, rightBtnText, onBtnLeftClickL, onBtnRightClickL);
    }


    /**
     * 显示一个MD风格的弹窗，包含标题、内容、左边按钮、右边按钮，以及按钮的处理事件
     *
     * @param title
     * @param content
     * @param leftBtnText
     * @param rightBtnText
     * @param onBtnLeftClickL
     * @param onBtnRightClickL
     */
    public Dialog showMaterialDialog(String title, String content, String leftBtnText, String rightBtnText, final View.OnClickListener onBtnLeftClickL, final View.OnClickListener onBtnRightClickL) {

        materialDialog = new MaterialDialog.Builder(mActivity)
                .title(title)
                .content(content)
                .positiveText(rightBtnText)
                .negativeText(leftBtnText)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        if (onBtnRightClickL != null) {
                            onBtnRightClickL.onClick(null);
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        if (onBtnLeftClickL != null) {
                            onBtnLeftClickL.onClick(null);
                        }
                    }
                })
                .positiveColorRes(R.color.dialog_btn_color)
                .negativeColorRes(R.color.dialog_btn_color)
                .theme(Theme.LIGHT)
                .show();
        return materialDialog;
    }

    /**
     * 显示一个提示弹窗，包含，标题、内容、一个按钮及按钮的处理事件
     *
     * @param title
     * @param content
     * @param btnText
     * @param onBtnClickL
     */
    public Dialog showMaterialTipDialog(String title, String content, String btnText, final View.OnClickListener onBtnClickL) {
        materialDialog = new MaterialDialog.Builder(mActivity)
                .title(title)
                .content(content)
                .positiveText(btnText)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        if (onBtnClickL != null) {
                            onBtnClickL.onClick(null);
                        }
                    }

                })
                .positiveColorRes(R.color.dialog_btn_color)
                .theme(Theme.LIGHT)
                .show();
        return materialDialog;
    }

    /**
     * 显示一个提示弹窗，没有标题，只有一个按钮
     *
     * @param content
     * @param btnText
     * @param onBtnClickL
     */
    public Dialog showMaterialTipDialogNoTitle(String content, String btnText, final View.OnClickListener onBtnClickL) {
        return showMaterialTipDialog(null, content, btnText, onBtnClickL);
    }


    /**
     * 显示一个提示弹窗，包含自定义布局
     *
     * @param customView
     * @param wrapInScrollView
     */
    public Dialog showMaterialCustomDialog(View customView, boolean wrapInScrollView) {
        materialDialog = new MaterialDialog.Builder(mActivity)
                // .title(title)
                .customView(customView, wrapInScrollView)
                .theme(Theme.LIGHT)
                .show();
        return materialDialog;
    }

    /**
     * 关闭对话框
     */
    public static void closeDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
            materialDialog = null;
        }
        if (mActivity != null) {
            mActivity = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    public static MaterialDialog getDialog() {
        return materialDialog;
    }
}
