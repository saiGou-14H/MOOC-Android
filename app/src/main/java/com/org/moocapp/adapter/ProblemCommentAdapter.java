package com.org.moocapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.ProblemCommentEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ProblemCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProblemCommentEntity> datas;
    private int isMy;
    /**
     * 点击事件
     */
    private static OnTakeClickListener monTakeClickListener;

    public void setOnTakeClickListener(OnTakeClickListener onTakeClickListener) {
        monTakeClickListener = onTakeClickListener;
    }

    public void setDatas(List<ProblemCommentEntity> datas) {
        this.datas = datas;
    }

    public void setIsMy(int isMy) {
        this.isMy = isMy;
    }

    public ProblemCommentAdapter(Context context) {
        this.mContext = context;
    }

    public ProblemCommentAdapter(Context context, List<ProblemCommentEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ProblemCommentEntity problemCommentEntity = datas.get(i);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_problem_comment, null);
            holder.problem_comment_img = convertView.findViewById(R.id.problem_comment_img);
            holder.problem_comment_username = convertView.findViewById(R.id.problem_comment_username);
            holder.problem_comment_time = convertView.findViewById(R.id.problem_comment_time);
            holder.problem_comment_content = convertView.findViewById(R.id.problem_comment_content);
            holder.btn_take_comment = convertView.findViewById(R.id.btn_take_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         *
         *         InputStream is = mContext.getResources().openRawResource(messageCommentEntity.getId());
         *         Bitmap bitmap = BitmapFactory.decodeStream(is);
         *         holder.commentItemImg.setImageBitmap(bitmap);
         */
        Picasso.with(mContext)
                .load(problemCommentEntity.getHeadPic())
                .transform(new CircleTransform())
                .into(holder.problem_comment_img);
        holder.problem_comment_username.setText(problemCommentEntity.getUsername());
        holder.problem_comment_time.setText(problemCommentEntity.getDate());
        initWebView(holder.problem_comment_content);
        holder.problem_comment_content.loadDataWithBaseURL(null, getHtmlData(String.valueOf(problemCommentEntity.getContent())), "text/html", "utf-8", null);
        if (isMy == 1) {
            holder.btn_take_comment.setVisibility(View.VISIBLE);
            if (problemCommentEntity.getBest() == true) {
                holder.btn_take_comment.setText("已采纳");
            } else {
                holder.btn_take_comment.setText("采纳");
            }
            holder.btn_take_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProblemCommentEntity problemCommentEntity = datas.get(i);
                    monTakeClickListener.OnTakeClick(problemCommentEntity);

                }
            });
        } else {
            holder.btn_take_comment.setVisibility(View.GONE);
        }
        //如果需要继续布局回复评论，加adapter
        return convertView;
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

    private void initWebView(WebView webView) {
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
    }

    private final class ViewHolder {
        public ImageView problem_comment_img;            //评论人图片
        public TextView problem_comment_username;            //评论人昵称
        public TextView problem_comment_time;            //评论时间
        public WebView problem_comment_content;            //评论内容,显示网页
        public TextView btn_take_comment;            //采纳按钮
    }


    public interface OnTakeClickListener {
        void OnTakeClick(Serializable obj);
    }
}
