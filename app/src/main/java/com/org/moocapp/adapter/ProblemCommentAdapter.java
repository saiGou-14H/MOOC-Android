package com.org.moocapp.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.ProblemCommentEntity;
import com.org.moocapp.entity.find.ProblemEntity;
import com.org.moocapp.entity.find.ProblemResponse;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ProblemCommentAdapter extends BaseAdapter {
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
    private Context mContext;
    private List<ProblemCommentEntity> datas;
    private Double isMy;
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

    public void setIsMy(Double isMy) {
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
//        holder.problem_comment_content.setText(problemCommentEntity.getContent());
//        initWebView(holder.problem_comment_content);
//        holder.problem_comment_content.loadDataWithBaseURL(null, getHtmlData(String.valueOf(problemCommentEntity.getContent())), "text/html", "utf-8", null);
        initRichText(holder.problem_comment_content, problemCommentEntity.getContent());
        if (isMy == 1.0) {
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
    private void initRichText(TextView goods_details, String html) {
        RichText.initCacheDir(mContext); //设置缓存目录，不设置会报错
        RichText.from(html)
                .bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(goods_details);
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
        public TextView problem_comment_content;            //评论内容
        //        public WebView problem_comment_content;            //评论内容,显示网页
        public TextView btn_take_comment;            //采纳按钮

    }


    public interface OnTakeClickListener {
        void OnTakeClick(Serializable obj);
    }
}
