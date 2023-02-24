package com.org.moocapp.activaty;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.MessageCommentAdapter;
import com.org.moocapp.adapter.MessageCommentRecyclerViewAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MessageCommentEntity;
import com.org.moocapp.entity.find.MessageCommentListResponse;
import com.org.moocapp.entity.find.MessageEntity;
import com.org.moocapp.util.Base64Util;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.HtmlJsoup;
import com.org.moocapp.util.RealPathFromUriUtils;
import com.org.moocapp.view.NoScrollListView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.squareup.picasso.Picasso;
import com.widemouth.library.span.WMImageSpan;
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
import com.widemouth.library.wmview.WMTextEditor;
import com.widemouth.library.wmview.WMToolContainer;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class MessageDetailActivity extends BaseActivity {
    private static final String IMAGE3 = "<div data-nav=\"posts\" class=\"theme-box wp-posts-content\">\n" +
            "            \n" +
            "<p>在今年苹果的秋季新品发布会上，苹果隆重介绍了在iPhone 14中引进的“卫星紧急求援”功能，该功能就是在无WiFi或蜂窝移动网络覆盖的情况下，帮助用户通过卫星发送紧急求救信息。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>苹果表示，卫星通讯专为在天空开阔的场景下使用而设计的，如果附近有树木或建筑物遮挡，实际性能体验可能会受到影响。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<figure class=\"wp-block-image size-full\"><img title=\"81b57f7ba2061712.png\" alt=\"iPhone14卫星紧急求援将于月底上线！&nbsp;\" width=\"1080\" height=\"719\" data-srcset=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png 1080w, https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712-768x511.png 768w\" data-src=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png\" data-sizes=\"(max-width: 1080px) 100vw, 1080px\" class=\"wp-image-625 lazyloaded\" src=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png\" sizes=\"(max-width: 1080px) 100vw, 1080px\" srcset=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png 1080w, https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712-768x511.png 768w\" imgbox-id=\"swiper-imgbox-16518\" imgbox-index=\"0\"><noscript><img title=\"81b57f7ba2061712.png\" alt=\"iPhone14卫星紧急求援将于月底上线！ \"  width=\"1080\" height=\"719\" src=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png\"  class=\"wp-image-625\" srcset=\"https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712.png 1080w, https://lizll.cn/wp-content/uploads/2022/11/81b57f7ba2061712-768x511.png 768w\" sizes=\"(max-width: 1080px) 100vw, 1080px\" /></noscript></figure>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>苹果目前还为披露这项卫星SOS求救功能的具体收费标准，仅表示只有iPhone 14系列用户可以免费体验两年。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>据最新消息<strong>，苹果公司该项卫星功能将于“本月晚些时候”登陆</strong>，并于<strong>美国和加拿大地区率先推出</strong>。<strong>而扩展到更多国家/地区则会在明年年底前完成</strong>，但苹果没有详细说明这些计划。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<figure class=\"wp-block-image size-full\"><img title=\"e10d0e2694061811.jpeg\" alt=\"iPhone14卫星紧急求援将于月底上线！&nbsp;\" width=\"640\" height=\"360\" data-src=\"https://lizll.cn/wp-content/uploads/2022/11/e10d0e2694061811.jpeg\" class=\"wp-image-626 ls-is-cached lazyloaded\" src=\"https://lizll.cn/wp-content/uploads/2022/11/e10d0e2694061811.jpeg\" imgbox-id=\"swiper-imgbox-16518\" imgbox-index=\"1\"><noscript><img title=\"e10d0e2694061811.jpeg\" alt=\"iPhone14卫星紧急求援将于月底上线！ \"  width=\"640\" height=\"360\" src=\"https://lizll.cn/wp-content/uploads/2022/11/e10d0e2694061811.jpeg\"  class=\"wp-image-626\"/></noscript></figure>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>需要注意的是，该功能无法在纬度超过62° 的地方使用 —— 例如加拿大北部和阿拉斯加地区，不知道什么原因苹果没有采用星链的卫星，星链完全可以覆盖更广的范围，马斯克也在苹果推出卫星服务后，伸出了橄榄枝。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p></p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p></p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>苹果透露，为了支持iPhone 14的“卫星紧急求援”已经向Globalstar等公司支付了4.5亿美元的费用。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>其中大部分费用都用在了Globalstar卫星网络和位于阿拉斯加、佛罗里达、夏威夷、内华达、波多黎各和得克萨斯州的地面站提供关键的增强功能，以确保 iPhone 14 / Pro 用户在离开网络时能够连接到紧急服务。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<figure class=\"wp-block-image size-full\"><img title=\"c09fc91d91062040.jpeg\" alt=\"iPhone14卫星紧急求援将于月底上线！&nbsp;\" width=\"800\" height=\"448\" data-srcset=\"https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040.jpeg 800w, https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040-768x430.jpeg 768w\" data-src=\"https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040.jpeg\" data-sizes=\"(max-width: 800px) 100vw, 800px\" class=\"wp-image-627 lazyload\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" imgbox-id=\"swiper-imgbox-16518\" imgbox-index=\"2\"><noscript><img title=\"c09fc91d91062040.jpeg\" alt=\"iPhone14卫星紧急求援将于月底上线！ \"  width=\"800\" height=\"448\" src=\"https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040.jpeg\"  class=\"wp-image-627\" srcset=\"https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040.jpeg 800w, https://lizll.cn/wp-content/uploads/2022/11/c09fc91d91062040-768x430.jpeg 768w\" sizes=\"(max-width: 800px) 100vw, 800px\" /></noscript></figure>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>作为投资的结果，Globalstar的地面站已经升级，专门为苹果设计和制造了新型高功率天线。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>Globalstar公司总部位于路易斯安那州，是一家卫星服务提供商，也是此次iPhone 14“卫星紧急救援”功能的主要合作伙伴。虽然目前苹果还未入股该公司，但它承诺将购买其设备和服务的运营。</p>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<p>毕竟<strong>这项功能并不是完全自动化的，而是通过人工呼叫中心来完成的</strong>，会有超过300名Globalstar的员工参与到其中。</p>\n" +
            "                    </div>";
    private WebView webView;//加载显示网页

    private Button messageDetail_back;//退出页面
    private TextView tvMessage_title;
    private ImageView imImg_header;
    private TextView tvMessage_author;
    private TextView tvMessage_content;
    private TextView tvPublish_comment;
    private LinearLayout Like_MMessage;//点赞按钮
    private ImageView message_img_like;//点赞按钮图片
    private TextView message_like_num;//点赞数量
    private TextView message_detail_comment_num;//点赞数量
    private EditText commentEdit;        //评论输入框
    private String comment = "";        //记录对话框中的内容
    private TextInputEditText textInputEditText;

    private List<MessageCommentEntity> datas = new ArrayList<>();

    private MessageCommentAdapter messageCommentAdapter;
    private MessageCommentRecyclerViewAdapter messageCommentRecyclerViewAdapter;
    private NoScrollListView NoScrollListView_messageCommentList;//评论列表
    private ScrollView my_scrollView;//
    private NestedScrollView my_NestedScrollView;//
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private MessageEntity messageEntity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    messageCommentAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    messageCommentAdapter.notifyDataSetChanged();

//                    messageCommentRecyclerViewAdapter.setDatas(datas);
//                    messageCommentRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView() {
        //获得控件
        webView = (WebView) findViewById(R.id.message_content_webView);

        messageDetail_back = findViewById(R.id.messageDetail_back);
        tvMessage_title = findViewById(R.id.message_title);
        imImg_header = findViewById(R.id.img_header);
        tvMessage_author = findViewById(R.id.message_author);
//        tvMessage_content = findViewById(R.id.message_content);
        tvPublish_comment = findViewById(R.id.publish_omment);
        commentEdit = findViewById(R.id.comment_Edit);
        Like_MMessage = findViewById(R.id.Like_MMessage);
        message_img_like = findViewById(R.id.message_img_like);
        message_like_num = findViewById(R.id.message_like_num);
        message_detail_comment_num = findViewById(R.id.message_detail_comment_num);

        MyClickListener myClickListener = new MyClickListener();
        messageDetail_back.setOnClickListener(myClickListener);
        tvPublish_comment.setOnClickListener(myClickListener);
        Like_MMessage.setOnClickListener(myClickListener);

        textInputEditText = findViewById(R.id.comment_Edit);
        textInputEditText.setOnEditorActionListener(new MyOnEditor());
        /**
         * messageCommentAdapter
         */
        my_scrollView = findViewById(R.id.my_scrollView);
        NoScrollListView_messageCommentList = findViewById(R.id.NoScrollListView_messageCommentList);
        messageCommentAdapter = new MessageCommentAdapter(this);
        NoScrollListView_messageCommentList.setAdapter(messageCommentAdapter);
        /**
         * messageCommentRecyclerViewAdapter
         */
//        recyclerView = findViewById(R.id.recyclerView_messageCommentList);
//        //布局管理器
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //设置线性布局
//        recyclerView.setLayoutManager(linearLayoutManager);
//        messageCommentRecyclerViewAdapter = new MessageCommentRecyclerViewAdapter(this);
//        recyclerView.setAdapter(messageCommentRecyclerViewAdapter);
//        messageCommentRecyclerViewAdapter.setDatas(datas);
//        messageCommentRecyclerViewAdapter.notifyDataSetChanged();
//        recyclerView.setNestedScrollingEnabled(false);


    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            messageEntity = (MessageEntity) bundle.getSerializable("messageEntity");
            tvMessage_title.setText(messageEntity.getTitle());
            tvMessage_author.setText(messageEntity.getAuthor());
            Picasso.with(mContext)
                    .load(messageEntity.getHeadPic())
                    .transform(new CircleTransform())
                    .into(imImg_header);
//            tvMessage_content.setText(messageEntity.getContent());
            webView.loadDataWithBaseURL(null, getHtmlData(messageEntity.getContent()), "text/html", "utf-8", null);
//            webView.loadDataWithBaseURL(null, getHtmlData(IMAGE3), "text/html", "utf-8", null);
            message_like_num.setText(String.valueOf(messageEntity.getMessageLike()));
            message_detail_comment_num.setText("评论(" + messageEntity.getCommentNum() + ")");
            getMessageCommentList(messageEntity.getId());
        }
        initWebView();
//        initRichText();
//        ScrollView();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//            content://media/external/images/media/17
//            String myImageUrl = "content://media/external/images/media/***";
//            String myImageUrl = "content://media/external/images/media/17";
//            Uri uri = Uri.parse(myImageUrl);
//            String imgUrl = RealPathFromUriUtils.getRealPathFromUri(this, uri);

//            System.out.println("realPathFromUrl:" + imgUrl);
//            String base64 = "data:image/png;base64," + Base64Util.imageToBase64(imgUrl);
//            System.out.println(base64);
    }

    /**
     * 完美解决Android中，ScrollView嵌套ListView的冲突。
     */
//    private void ScrollView() {
//        my_scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        NoScrollListView_messageCommentList.requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        NoScrollListView_messageCommentList.requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//                return false;
//            }
//        });
//    }

    /**
     * 初始化RichText
     */
    private void initRichText() {
        TextView goods_details = findViewById(R.id.goods_details);
        RichText.initCacheDir(this); //设置缓存目录，不设置会报错
        RichText.from(IMAGE3)
                .bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(goods_details);
    }


    /**
     * 获取html
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 设置WebView支持JavaScript
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
     * 获取资讯详情消息
     */
    private void getMessageDetail() {

    }

    /**
     * 获取资讯是否点赞
     */
    private void getIsMessageLike() {
        boolean likeStatus = true;
        if (likeStatus) {
            message_img_like.setVisibility(View.VISIBLE);
        } else {
            message_img_like.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 用户点赞或者取消点赞资讯
     */
    private void LikeMMessageByUserId() {
        getIsMessageLike();
        boolean likeStatus = true;
        if (likeStatus) {
            message_img_like.setVisibility(View.VISIBLE);
        } else {
            message_img_like.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 获取评论列表数据
     */
    private void getMessageCommentList(Long messageId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("messageId", messageId);
        Api.config(ApiConfig.MessageCommentBy_LIST_ALL, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("response", res);
                MessageCommentListResponse response = new Gson().fromJson(res, MessageCommentListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MessageCommentEntity> list = response.getData();
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
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = commentEdit.getText().toString().trim();
        if (comment.equals("")) {
            showToast("评论不能为空");
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /**
     * 发表评论
     */
    private void publishComment(Long messageId, View view) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("messageId", messageId);
        params.put("content", comment);
        params.put("isLike", 0);
        Api.config(ApiConfig.MessageComment_ADD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("response", res);
                getMessageCommentList(messageEntity.getId());
                closeSoftKeyboard(view);
                showToastSync("发表成功");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 事件监听
     */
    class MyOnEditor implements TextInputEditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.ACTION_DOWN) {
                Log.e("TAG", "点击了enter键");
                closeSoftKeyboard(new View(mContext));
            }
            return false;
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Like_MMessage:    //点赞按钮
                    Integer num = Integer.valueOf((String) message_like_num.getText());
                    message_like_num.setText(String.valueOf(num + 1));
                    if (message_img_like.getVisibility() == View.INVISIBLE) {
                        message_img_like.setVisibility(View.VISIBLE);
                    } else {
                        message_img_like.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.publish_omment:    //发表评论按钮
                    //判断用户是否输入内容
                    if (isEditEmply()) {
                        publishComment(messageEntity.getId(), view);
                    }
                    break;
                case R.id.messageDetail_back:        //退出页面按钮
                    finish();
                    break;
            }

        }
    }
}