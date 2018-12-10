package com.guowei.diverse.model.learn;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Writer：GuoWei_aoj on 2018/12/4 0004 16:44
 * description:
 */
public class NewestModel {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"xiaoyang","chapterId":360,"chapterName":"小编发布","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":7603,"link":"http://www.wanandroid.com/blog/show/2442","niceDate":"7小时前","origin":"","projectLink":"","publishTime":1543884889000,"superChapterId":298,"superChapterName":"原创文章","tags":[],"title":"玩 Android TODO open API v2","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":7604,"link":"https://mp.weixin.qq.com/s/cCZKmqKrdCn63eWTbOuANw","niceDate":"22小时前","origin":"","projectLink":"","publishTime":1543830090000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"在 Retrofit 和 OkHttp 中使用网络缓存，提高访问效率","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7605,"link":"https://mp.weixin.qq.com/s/r3AWeYafyMEc1-g8BWEHBg","niceDate":"1天前","origin":"","projectLink":"","publishTime":1543766400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"非 UI 线程能调用 View.invalidate()？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7606,"link":"https://mp.weixin.qq.com/s/uRT_1gUF_GFUu7-IE_KkDQ","niceDate":"1天前","origin":"","projectLink":"","publishTime":1543766400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"响应式编程在Android 中的一些探索","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"互联网侦察","chapterId":421,"chapterName":"互联网侦察","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7607,"link":"https://mp.weixin.qq.com/s/yWEqIMUc3FHnYj-cedr-4w","niceDate":"2天前","origin":"","projectLink":"","publishTime":1543680000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/421/1"}],"title":"【面试现场】如何设计可自学习的五子棋AI？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"辰之猫","chapterId":100,"chapterName":"RecyclerView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7602,"link":"https://www.jianshu.com/p/e54db232df62","niceDate":"2018-12-01","origin":"","projectLink":"","publishTime":1543645513000,"superChapterId":179,"superChapterName":"5.+高新技术","tags":[],"title":"让你明明白白的使用RecyclerView&mdash;&mdash;SnapHelper详解","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"张旭童","chapterId":100,"chapterName":"RecyclerView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7601,"link":"https://blog.csdn.net/zxt0601/article/details/52562770","niceDate":"2018-12-01","origin":"","projectLink":"","publishTime":1543645499000,"superChapterId":179,"superChapterName":"5.+高新技术","tags":[],"title":"【Android】RecyclerView的好伴侣：详解DiffUtil","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7598,"link":"https://mp.weixin.qq.com/s/bNFcDEPuR4-RAMQAyI9T6w","niceDate":"2018-11-30","origin":"","projectLink":"","publishTime":1543507200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android关于Color你所知道的和不知道的一切","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7599,"link":"https://mp.weixin.qq.com/s/xisEYrgAeTlW8b5BMucRmA","niceDate":"2018-11-30","origin":"","projectLink":"","publishTime":1543507200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"手把手教你实现一个ImageLoader框架","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"承香墨影","chapterId":411,"chapterName":"承香墨影","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7600,"link":"https://mp.weixin.qq.com/s/YF5LEKSaKEYGTFas9uF3-Q","niceDate":"2018-11-30","origin":"","projectLink":"","publishTime":1543507200000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/411/1"}],"title":"是什么拖慢了你的 App 启动？干掉它！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"dengyuhan","chapterId":382,"chapterName":"音视频&amp;相机","collect":false,"courseId":13,"desc":"可以替代MediaMetadataRetriever的兼容方案\r\n可以获取图片、视频、音频文件的媒体信息、视频缩略图","envelopePic":"http://wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":7594,"link":"http://www.wanandroid.com/blog/show/2441","niceDate":"2018-11-29","origin":"","projectLink":"https://github.com/dengyuhan/MediaMetadataRetrieverCompat","publishTime":1543497346000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=382"}],"title":"MediaMetadataRetrieverCompat - 多媒体元数据兼容方案","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"serge66","chapterId":385,"chapterName":"架构","collect":false,"courseId":13,"desc":"你是否遇到过Activity/Fragment中成百上千行代码,完全无法维护,看着头疼?\r\n\r\n你是否遇到过因后台接口还未写而你不能先写代码逻辑的情况?\r\n\r\n你是否遇到过用MVC架构写的项目进行单元测试时的深深无奈?","envelopePic":"http://wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":7593,"link":"http://www.wanandroid.com/blog/show/2440","niceDate":"2018-11-29","origin":"","projectLink":"https://github.com/serge66/MVPDemo","publishTime":1543497308000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=385"}],"title":"Android MVP架构从入门到精通-真枪实弹","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"xuehuayous","chapterId":314,"chapterName":"RV列表动效","collect":false,"courseId":13,"desc":"一种优雅的方式来使用RecyclerView，把你从复杂的多类型多样式中解放出来！","envelopePic":"http://wanandroid.com/blogimgs/1d6ed12c-1507-4826-878a-3737dff82daa.png","fresh":false,"id":7592,"link":"http://www.wanandroid.com/blog/show/2439","niceDate":"2018-11-29","origin":"","projectLink":"https://github.com/xuehuayous/DelegationAdapter","publishTime":1543497285000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=314"}],"title":"一种优雅的方式来使用RecyclerView","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"dengyuhan","chapterId":363,"chapterName":"创意汇","collect":false,"courseId":13,"desc":"可以给Github项目添加标签的Chrome插件，支持按标签搜索，数据导入与导出","envelopePic":"http://wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":7591,"link":"http://www.wanandroid.com/blog/show/2438","niceDate":"2018-11-29","origin":"","projectLink":"https://github.com/dengyuhan/github-tags","publishTime":1543497246000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=363"}],"title":"GithubTags - 给Github项目添加标签的Chrome插件","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"mandypig","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7590,"link":"https://www.jianshu.com/p/7241ed34346a","niceDate":"2018-11-29","origin":"","projectLink":"","publishTime":1543495476000,"superChapterId":94,"superChapterName":"自定义控件","tags":[],"title":"不到100行代码实现左右对齐TextView","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7595,"link":"https://mp.weixin.qq.com/s/Vr9Z661RGEMWqf2_AWJxIA","niceDate":"2018-11-29","origin":"","projectLink":"","publishTime":1543420800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"封装bilibili播放器 , 仿抖音视频播放效果","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7596,"link":"https://mp.weixin.qq.com/s/qLOD5C9S315VB-mLne1-6Q","niceDate":"2018-11-29","origin":"","projectLink":"","publishTime":1543420800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"Flutter自定义折线图并添加点击事件","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"黄铭1991","chapterId":295,"chapterName":"混淆","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7589,"link":"https://www.jianshu.com/p/fb4e6fb03c47","niceDate":"2018-11-28","origin":"","projectLink":"","publishTime":1543418390000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android 开发应该掌握的 Proguard 技巧","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"青蛙要fly","chapterId":69,"chapterName":"okhttp","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7588,"link":"https://www.jianshu.com/p/65c423d6a8eb","niceDate":"2018-11-28","origin":"","projectLink":"","publishTime":1543418323000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"Android技能树 &mdash; 网络小结之 OkHttp超超超超超超超详细解析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"黄俊彬 ","chapterId":302,"chapterName":"ANR","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7587,"link":"https://www.jianshu.com/p/24eb823edebc","niceDate":"2018-11-28","origin":"","projectLink":"","publishTime":1543418299000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"APK瘦身三步曲","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 288
     * size : 20
     * total : 5757
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Parcelable{
        /**
         * apkLink :
         * author : xiaoyang
         * chapterId : 360
         * chapterName : 小编发布
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : true
         * id : 7603
         * link : http://www.wanandroid.com/blog/show/2442
         * niceDate : 7小时前
         * origin :
         * projectLink :
         * publishTime : 1543884889000
         * superChapterId : 298
         * superChapterName : 原创文章
         * tags : []
         * title : 玩 Android TODO open API v2
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<?> tags;

        protected DatasBean(Parcel in) {
            apkLink = in.readString();
            author = in.readString();
            chapterId = in.readInt();
            chapterName = in.readString();
            collect = in.readByte() != 0;
            courseId = in.readInt();
            desc = in.readString();
            envelopePic = in.readString();
            fresh = in.readByte() != 0;
            id = in.readInt();
            link = in.readString();
            niceDate = in.readString();
            origin = in.readString();
            projectLink = in.readString();
            publishTime = in.readLong();
            superChapterId = in.readInt();
            superChapterName = in.readString();
            title = in.readString();
            type = in.readInt();
            userId = in.readInt();
            visible = in.readInt();
            zan = in.readInt();
        }

        public static final Creator<DatasBean> CREATOR = new Creator<DatasBean>() {
            @Override
            public DatasBean createFromParcel(Parcel in) {
                return new DatasBean(in);
            }

            @Override
            public DatasBean[] newArray(int size) {
                return new DatasBean[size];
            }
        };

        public DatasBean() {

        }

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(apkLink);
            dest.writeString(author);
            dest.writeInt(chapterId);
            dest.writeString(chapterName);
            dest.writeByte((byte) (collect ? 1 : 0));
            dest.writeInt(courseId);
            dest.writeString(desc);
            dest.writeString(envelopePic);
            dest.writeByte((byte) (fresh ? 1 : 0));
            dest.writeInt(id);
            dest.writeString(link);
            dest.writeString(niceDate);
            dest.writeString(origin);
            dest.writeString(projectLink);
            dest.writeLong(publishTime);
            dest.writeInt(superChapterId);
            dest.writeString(superChapterName);
            dest.writeString(title);
            dest.writeInt(type);
            dest.writeInt(userId);
            dest.writeInt(visible);
            dest.writeInt(zan);
        }
    }
}
