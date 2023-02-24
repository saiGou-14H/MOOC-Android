package com.org.moocapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.org.moocapp.R;

public class MyDialog extends Dialog {

    private TextView tvTitle,tvMsg,tvConfirm,tvCancel;

    private OnMyDialogClickListener onMyDialogClickListener;

    public MyDialog setOnMyDialogClickListener(OnMyDialogClickListener onMyDialogClickListener) {
        this.onMyDialogClickListener = onMyDialogClickListener;
        return this;
    }

    public MyDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item_mydialog);

        initView();

    }
    /**
     * 初始化
     */
    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvMsg = findViewById(R.id.tv_msg);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onMyDialogClickListener != null){
                    onMyDialogClickListener.onConfirmClick(MyDialog.this);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onMyDialogClickListener != null){
                    onMyDialogClickListener.onCancelClick(MyDialog.this);
                }
            }
        });


        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }



    public MyDialog setTitle(String title){
        tvTitle.setText(title);
        return this;
    }

    public MyDialog setMsg(String msg){
        tvMsg.setText(msg);
        return this;
    }



    @Override
    protected void onStart() {
        super.onStart();
        //设置Dialog显示大小位置动画等操作
        Window window = getWindow();
        Display display = window.getWindowManager().getDefaultDisplay();
        window.setLayout((int) (display.getWidth()*0.9), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public interface OnMyDialogClickListener{
        void onConfirmClick(Dialog dialog);

        void onCancelClick(Dialog dialog);
    }
}
