package com.org.moocapp.activaty;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.util.RichTextUtil;
import com.widemouth.library.toolitem.WMToolAlignment;
import com.widemouth.library.toolitem.WMToolBackgroundColor;
import com.widemouth.library.toolitem.WMToolBold;
import com.widemouth.library.toolitem.WMToolImage;
import com.widemouth.library.toolitem.WMToolItalic;
import com.widemouth.library.toolitem.WMToolItem;
import com.widemouth.library.toolitem.WMToolListBullet;
import com.widemouth.library.toolitem.WMToolListClickToSwitch;
import com.widemouth.library.toolitem.WMToolListNumber;
import com.widemouth.library.toolitem.WMToolQuote;
import com.widemouth.library.toolitem.WMToolSplitLine;
import com.widemouth.library.toolitem.WMToolStrikethrough;
import com.widemouth.library.toolitem.WMToolTextColor;
import com.widemouth.library.toolitem.WMToolTextSize;
import com.widemouth.library.toolitem.WMToolUnderline;
import com.widemouth.library.wmview.WMEditText;
import com.widemouth.library.wmview.WMToolContainer;

import java.util.HashMap;

public class PublishProblemActivity extends BaseActivity {
    private WMEditText editText;
    private WMToolContainer toolContainer;
    private WMToolItem toolBold = new WMToolBold();
    private WMToolItem toolItalic = new WMToolItalic();
    private WMToolItem toolUnderline = new WMToolUnderline();
    private WMToolItem toolStrikethrough = new WMToolStrikethrough();
    private WMToolItem toolImage = new WMToolImage();
    private WMToolItem toolTextColor = new WMToolTextColor();
    private WMToolItem toolBackgroundColor = new WMToolBackgroundColor();
    private WMToolItem toolTextSize = new WMToolTextSize();
    private WMToolItem toolListNumber = new WMToolListNumber();
    private WMToolItem toolListBullet = new WMToolListBullet();
    private WMToolItem toolAlignment = new WMToolAlignment();
    private WMToolItem toolQuote = new WMToolQuote();
    private WMToolItem toolListClickToSwitch = new WMToolListClickToSwitch();
    private WMToolItem toolSplitLine = new WMToolSplitLine();

    private TextInputEditText publish_problem_title;
    private Button publish_problem;
    private Button publish_problem_back;
    String problem_title = "";
    String problem_content = "";

    @Override
    protected int initLayout() {
        return R.layout.activity_publish_problem;
    }

    @Override
    protected void initView() {
        publish_problem_title = findViewById(R.id.publish_problem_title);
        publish_problem = findViewById(R.id.publish_problem);
        publish_problem_back = findViewById(R.id.publish_problem_back);
        MyClickListener myClickListener = new MyClickListener();
        publish_problem.setOnClickListener(myClickListener);
        publish_problem_back.setOnClickListener(myClickListener);

    }

    @Override
    protected void initData() {
        initWMTextEditor();
//        textEditor.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                System.out.println("onTouch");
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        System.out.println("ACTION_UP");
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("ACTION_DOWN");
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
        closeSoftKeyboard(new View(this));
    }


    /**
     * 关闭软键盘
     *
     * @param view 当前页面上任意一个可用的view
     */
    public void closeSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     *
     */
    public String getBody(String val) {
        String start = "<body>";
        String end = "</body>";
        int s = val.indexOf(start) + start.length();
        int e = val.indexOf(end);
        return val.substring(s, e);
    }

    /**
     *
     */
    private void initWMTextEditor() {
        editText = findViewById(R.id.WMEditText);
        toolContainer = findViewById(R.id.WMToolContainer);

        toolContainer.addToolItem(toolImage);
        toolContainer.addToolItem(toolTextColor);
        toolContainer.addToolItem(toolBackgroundColor);
        toolContainer.addToolItem(toolTextSize);
        toolContainer.addToolItem(toolBold);
        toolContainer.addToolItem(toolItalic);
        toolContainer.addToolItem(toolUnderline);
        toolContainer.addToolItem(toolStrikethrough);
        toolContainer.addToolItem(toolListNumber);
        toolContainer.addToolItem(toolListBullet);
        toolContainer.addToolItem(toolAlignment);
        toolContainer.addToolItem(toolQuote);
        toolContainer.addToolItem(toolListClickToSwitch);
        toolContainer.addToolItem(toolSplitLine);
        editText.setupWithToolContainer(toolContainer);
    }

    /**
     * 处理图片
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WMToolImage.ALBUM_CHOOSE && resultCode == RESULT_OK) {
            ((WMToolImage) toolImage).onActivityResult(data);
        }
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        problem_title = publish_problem_title.getText().toString().trim();
        if (problem_title.equals("")) {
            showToast("问题标题不能为空");
            return false;
        }
        publish_problem_title.setText("");

        problem_content = RichTextUtil.richText(this, getBody(editText.getHtml()));
        if (problem_content.equals("")) {
            showToast("问题内容不能为空");
            return false;
        }
        editText.setText("");
        return true;
    }

    /**
     * 发表问题
     */
    private void publishProblem() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("title", problem_title);
        params.put("content", problem_content);
        params.put("resourceUrl", "");
        params.put("auId", "");
        params.put("createTime", "");
        params.put("isQuestion", "");
        params.put("isSelect", "false");
        params.put("ip", "");
        Api.config(ApiConfig.PROBLEM_ADD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                showToastSync("发表成功");
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.publish_problem:    //发表问题按钮
                    closeSoftKeyboard(view);
                    if (isEditEmply()) {//判断用户是否输入内容
                        publishProblem();
                        finish();
                    }
                    break;
                case R.id.publish_problem_back:        //退出页面按钮
                    closeSoftKeyboard(view);
                    finish();
                    break;
            }

        }
    }
}