package com.smile.ch.article;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.smile.ch.article.bean.ArticleListBean;
import com.smile.ch.article.bean.BannerBean;
import com.smile.ch.article.contract.ArticleContract;
import com.smile.ch.article.presenter.ArticlePresenterImpl;
import com.smile.ch.article.util.GlideImageLoad;
import com.smile.ch.common.BaseAdapter.CommonAdapter;
import com.smile.ch.common.BaseAdapter.MultiItemTypeAdapter;
import com.smile.ch.common.BaseAdapter.base.ViewHolder;
import com.smile.ch.common.base.BaseMvpActivity;
import com.smile.ch.common.utils.LogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/article/index")
public class ArticleListActivity extends BaseMvpActivity<ArticleContract.BannerView, ArticlePresenterImpl> implements ArticleContract.BannerView, OnBannerListener {
    private Banner banner;
    private RecyclerView recyclerView;
    private CommonAdapter listAdapter;
    private List<String> bannerImgLists;
    private List<ArticleListBean.DatasBean> mDatas;
    private List<BannerBean> bannerLists;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected ArticlePresenterImpl createPresenter() {
        return new ArticlePresenterImpl();
    }

    @Override
    protected void findViews() {
        banner = findViewById(R.id.index_banner);
        recyclerView = findViewById(R.id.article_rv);
    }

    @Override
    protected void setViews() {
        mDatas = new ArrayList<>();
        listAdapter = new CommonAdapter<ArticleListBean.DatasBean>(this, R.layout.item_article_list_rv, mDatas) {
            @Override
            protected void convert(ViewHolder holder, ArticleListBean.DatasBean bean, int position) {
                TextView tvTitle = holder.getView(R.id.item_article_title);
                TextView tvTags = holder.getView(R.id.item_article_tags);
                TextView tvAuthor = holder.getView(R.id.item_article_author);
                TextView tvCategory = holder.getView(R.id.item_article_category);
                TextView tvDate = holder.getView(R.id.item_article_date);

                tvTitle.setText(bean.getTitle());
                tvAuthor.setText(bean.getAuthor());
                tvCategory.setText(bean.getSuperChapterName() + " / " + bean.getChapterName());
                tvDate.setText(bean.getNiceDate());
                if (bean.getTags().size() > 0) {
                    tvTags.setText(bean.getTags().get(0).getName());
                    tvTags.setVisibility(View.VISIBLE);
                } else {
                    tvTags.setVisibility(View.GONE);
                }
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        initBanner();
        LogUtils.log("测试日志打印1");
        LogUtils.log("tett","测试日志打印1");
    }

    @Override
    protected void initListener() {
        listAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mDatas != null && mDatas.size() > 0) {
                    ARouter.getInstance()
                            .build("/detail/banner")
                            .withString("bannerUrl", mDatas.get(position).getLink())
                            .navigation();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoad());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置是否自动轮播
        banner.isAutoPlay(true);
        //设置轮播间隔时间
        banner.setDelayTime(2000);
        /*//设置数据集合
        banner.setImages(lists);*/
        //设置banner点击事件
        banner.setOnBannerListener(this);

    }

    @Override
    protected void getData() {
        getPresenter().requestBannerImgs(this, 1);
    }

    @Override
    public void onBannerSuccess(List<BannerBean> lists) {
        if (lists != null && lists.size() > 0) {
            bannerLists = new ArrayList<>();
            bannerLists.addAll(lists);
            bannerImgLists = new ArrayList<>();
            for (BannerBean bean : lists) {
                bannerImgLists.add(bean.getImagePath());
            }
            banner.setImages(bannerImgLists);
            banner.start();
        }
    }

    @Override
    public void onBannerFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleListSuccess(List<ArticleListBean.DatasBean> lists) {
        if (lists != null && lists.size() > 0) {
            mDatas.addAll(lists);
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onArticleListFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnBannerClick(int position) {
        if (bannerLists != null && bannerLists.size() > 0) {
            ARouter.getInstance()
                    .build("/detail/banner")
                    .withString("bannerUrl", bannerLists.get(position).getUrl())
                    .navigation();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
