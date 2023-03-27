package com.org.moocapp.activaty;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.ProblemCommentAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.dialog.WMEditTextDialog;
import com.org.moocapp.entity.find.IsMyQuestionResponse;
import com.org.moocapp.entity.find.ProblemCommentEntity;
import com.org.moocapp.entity.find.ProblemCommentListResponse;
import com.org.moocapp.entity.find.ProblemEntity;
import com.org.moocapp.entity.find.ProblemResponse;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.RichTextUtil;
import com.org.moocapp.view.BottomScrollView;
import com.squareup.picasso.Picasso;
import com.widemouth.library.toolitem.WMToolImage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProblemDetailActivity extends BaseActivity {
    private WMEditTextDialog wmEditTextDialog;//对话框
    private WebView webView;//加载显示网页
    private Button publish_problem_comment_btn_dialog;

    private Button problemDetail_back;//退出页面
    private TextView problem_title;
    private ImageView problem_author_header;
    private TextView problem_author;
    private TextView problem_detail_comment_num;
    private TextView problem_detail_like_num;
    private ImageView problem_img_like;

    private BottomScrollView my_scrollView;
    private ListView noScrollListView;//评论列表
    private ProblemCommentAdapter problemCommentAdapter;


    private List<ProblemCommentEntity> datas = new ArrayList<>();
    private String comment = "";        //记录对话框中的内容
    private ProblemEntity problemEntity;
    private Long questionId;
    private int isMy;
    float mLastY;
    boolean isSvToBottom = false;
    /**
     * listview竖向滑动的阈值
     */
    private static final int THRESHOLD_Y_LIST_VIEW = 20;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    problemCommentAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    problemCommentAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    problemCommentAdapter.setIsMy(isMy);
                    //通知recyclerView刷新页面
                    problemCommentAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_problem_detail;
    }

    @Override
    protected void initView() {
        publish_problem_comment_btn_dialog = findViewById(R.id.publish_problem_comment_btn_dialog);
        problemDetail_back = findViewById(R.id.problemDetail_back);
        problem_title = findViewById(R.id.problem_title);
        problem_author_header = findViewById(R.id.problem_author_header);
        problem_author = findViewById(R.id.problem_author);
        problem_detail_comment_num = findViewById(R.id.problem_detail_comment_num);
        problem_detail_like_num = findViewById(R.id.problem_detail_like_num);
        problem_img_like = findViewById(R.id.problem_img_like);


        my_scrollView = findViewById(R.id.my_scrollView);
        noScrollListView = findViewById(R.id.problemCommentList);

        MyClickListener myClickListener = new MyClickListener();
        problemDetail_back.setOnClickListener(myClickListener);
        publish_problem_comment_btn_dialog.setOnClickListener(myClickListener);

        problemCommentAdapter = new ProblemCommentAdapter(this);
        noScrollListView.setAdapter(problemCommentAdapter);

        my_scrollView.setScrollToBottomListener(new BottomScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollToBottom() {
                isSvToBottom = true;
            }

            @Override
            public void onNotScrollToBottom() {
                isSvToBottom = false;
            }
        });

        noScrollListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mLastY = event.getY();
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    int top = noScrollListView.getChildAt(0).getTop();
                    float nowY = event.getY();
                    if (!isSvToBottom) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        noScrollListView.requestDisallowInterceptTouchEvent(false);
                    } else if (top == 0 && nowY - mLastY > THRESHOLD_Y_LIST_VIEW) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        noScrollListView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        // 不允许scrollview拦截点击事件， listView滑动
                        noScrollListView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            problemEntity = (ProblemEntity) bundle.getSerializable("problemEntity");
            questionId = bundle.getLong("questionId");
            getProblem(questionId);
            //写一个接口判断是否是自己的问题
            isMyQuestion(questionId);
            getQuestionCommentList(questionId);

        }
        problemCommentAdapter.setOnTakeClickListener(new ProblemCommentAdapter.OnTakeClickListener() {
            @Override
            public void OnTakeClick(Serializable obj) {
                ProblemCommentEntity problemCommentEntity = (ProblemCommentEntity) obj;
                if (problemCommentEntity.getBest() == true) {
                    takeProblemComment(problemCommentEntity.getId(), 0);
                } else {
                    takeProblemComment(problemCommentEntity.getId(), 1);
                }
            }
        });
        initWebView();
        wmEditTextDialog = new WMEditTextDialog(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getQuestionCommentList(questionId);
        getProblem(questionId);
        //接口判断是否是自己的问题
        isMyQuestion(questionId);
    }


    /**
     * 处理图片
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WMToolImage.ALBUM_CHOOSE && resultCode == RESULT_OK) {
            ((WMToolImage) wmEditTextDialog.getToolImage()).onActivityResult(data);
        }
    }

    /**
     *
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.problem_detail_content_webView);
        WebSettings settings = webView.getSettings();
        // 设置WebView支持JavaScript
        settings.setJavaScriptEnabled(true);
        //支持自动适配
        settings.setUseWideViewPort(true);//将图片调整到适合webView的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setSupportZoom(true);  //支持放大缩小
        settings.setBuiltInZoomControls(true); //显示缩放按钮
        settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSaveFormData(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);/// 支持通过JS打开新窗口
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //系统默认会通过手机浏览器打开网页,设置不让其跳转浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        // 添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        // mWebView.loadUrl(TEXTURL);

        //不加这个图片显示不出来
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setBlockNetworkImage(false);

        //允许cookie 不然有的网站无法登陆
        CookieManager mCookieManager = CookieManager.getInstance();
        mCookieManager.setAcceptCookie(true);
        mCookieManager.setAcceptThirdPartyCookies(webView, true);

        String content = "&lt;p&gt;&lt;span style=&quot;color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;; font-size: 15px; background-color: rgb(255, 255, 255);&quot;&gt;富文本内容&lt;/span&gt;&lt;/p&gt;";
        if (content.contains("&")) {
            content = content.replace("&#039;", "'");
            content = content.replace("&quot;", "\"");
            content = content.replace("&lt;", "<");
            content = content.replace("&gt;", ">");
            content = content.replace("&amp;", "&");
        }
//        webView.loadDataWithBaseURL(null, getHtmlData(IMAGE3), "text/html", "utf-8", null);
    }

    /**
     * 根据问题Id查询是否是User的问题
     */
    private void isMyQuestion(Long questionId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("questionId", questionId);
        Api.config(ApiConfig.PROBLEM_IS_MY, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                //将json字符串转换为map集合
//                Type type = new TypeToken<Map<String, Object>>() {
//                }.getType();
//                Gson gson = new Gson();
//                Map<String, Object> response = gson.fromJson(res, type);
//                if (response != null && (Double) response.get("code") == 200.0) {
//                    isMy = (Double) response.get("data");
//                    mHandler.sendEmptyMessage(1);
//                }
                IsMyQuestionResponse response = new Gson().fromJson(res, IsMyQuestionResponse.class);
                if (response != null && response.getCode() == 200) {
                    isMy = response.getData();
                    mHandler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 获取问题详情
     */
    private void getProblem(Long questionId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("questionId", questionId);
        Api.config(ApiConfig.PROBLEM_GET_BY_Id, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ProblemResponse response = new Gson().fromJson(res, ProblemResponse.class);
                if (response != null && response.getCode() == 200) {
                    ProblemEntity data = response.getData();
                    if (data != null) {
                        problemEntity = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                problem_title.setText(String.valueOf(problemEntity.getTitle()));
                                problem_author.setText(String.valueOf(problemEntity.getUsername()));
                                Picasso.with(mContext)
                                        .load(problemEntity.getHeadPic())
                                        .transform(new CircleTransform())
                                        .into(problem_author_header);
//                                problem_content.setText(String.valueOf(problemEntity.getContent()));
                                webView.loadDataWithBaseURL(null, getHtmlData(String.valueOf(problemEntity.getContent())), "text/html", "utf-8", null);
                                problem_detail_comment_num.setText("全部回答(" + String.valueOf(problemEntity.getCommentNum()) + ")");
                                problem_detail_like_num.setText(String.valueOf(problemEntity.getLikeNum()));
                            }
                        });

                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 采纳问题回答
     */
    private void takeProblemComment(Integer id, int isBest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("isBest", isBest);
        Api.config(ApiConfig.PROBLEM_Comment_TAKE, params).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getQuestionCommentList(questionId);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    /**
     * 获取问题评论列表数据
     */
    private void getQuestionCommentList(Long questionId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("questionId", questionId);
        Api.config(ApiConfig.PROBLEM_CommentBy_LIST_ALL, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
//                Log.e("response", res);
                ProblemCommentListResponse response = new Gson().fromJson(res, ProblemCommentListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<ProblemCommentEntity> list = response.getData();
                    if (list != null && list.size() > 0) {
                        datas = list;
                        mHandler.sendEmptyMessage(0);
                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 发表评论
     */
    private void publishProblemComment(Long questionId1) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("questionId", questionId1);
        params.put("content", comment);
        Api.config(ApiConfig.PROBLEM_Comment_ADD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getProblem(questionId);
                getQuestionCommentList(questionId);
                showToastSync("发表成功");
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("发表失败");
            }
        });

    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
//        comment = problem_textInputEditText.getText().toString().trim();
        comment = RichTextUtil.richText(mContext, getBody(wmEditTextDialog.getEditText().getHtml())).toString().trim();
        if (comment.equals("")) {
            showToast("评论不能为空");
            return false;
        }
        wmEditTextDialog.getEditText().setText("");
        return true;
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
     * 输入框回车事件
     */
    class MyOnEditor implements TextInputEditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.ACTION_DOWN) {
                Log.e("TAG", "点击了enter键");
            }
            return false;
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.problem_img_like:    //点赞按钮
                    Integer num = Integer.valueOf((String) problem_detail_like_num.getText());
                    problem_detail_like_num.setText(String.valueOf(num + 1));
                    if (problem_img_like.getVisibility() == View.INVISIBLE) {
                        problem_img_like.setVisibility(View.VISIBLE);
                    } else {
                        problem_img_like.setVisibility(View.INVISIBLE);
                    }

                case R.id.publish_problem_comment_btn_dialog://发表回答按钮
                    wmEditTextDialog.setOnDialogClickListener(new WMEditTextDialog.OnDialogClickListener() {
                        @Override
                        public void onOKClick() {
                            wmEditTextDialog.dismiss();
                            if (isEditEmply()) {        //判断用户是否输入内容
                                publishProblemComment(questionId);
                            }
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    });
                    wmEditTextDialog.setCancelable(true);//点击空白处不消失
                    wmEditTextDialog.show();
                    break;
                case R.id.problemDetail_back:        //退出页面按钮
                    finish();
                    break;
            }

        }
    }
}