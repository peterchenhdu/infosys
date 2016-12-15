package cn.edu.hdu.infosys.model;

/**
 *
 *
 *
 * @author    Pi Chen
 * @version   infosys V1.0.0, 2016年12月10日
 * @see
 * @since     infosys V1.0.0
 */
public class News
{
    private String url;
    private String title;
    private String contentText;
    private String contentHtml;
    private String srcUrl;
    private String srcName;
    private String publishTime;
    private String crawlerName;
    private String crawlerSrc;
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getContentText()
    {
        return contentText;
    }
    public void setContentText(String contentText)
    {
        this.contentText = contentText;
    }
    public String getContentHtml()
    {
        return contentHtml;
    }
    public void setContentHtml(String contentHtml)
    {
        this.contentHtml = contentHtml;
    }
    public String getSrcUrl()
    {
        return srcUrl;
    }
    public void setSrcUrl(String srcUrl)
    {
        this.srcUrl = srcUrl;
    }
    public String getSrcName()
    {
        return srcName;
    }
    public void setSrcName(String srcName)
    {
        this.srcName = srcName;
    }
    public String getPublishTime()
    {
        return publishTime;
    }
    public void setPublishTime(String publishTime)
    {
        this.publishTime = publishTime;
    }
    public String getCrawlerSrc()
    {
        return crawlerSrc;
    }
    public void setCrawlerSrc(String crawlerSrc)
    {
        this.crawlerSrc = crawlerSrc;
    }
    /**
     * @return the crawlerName
     */
    public String getCrawlerName()
    {
        return crawlerName;
    }
    /**
     * @param crawlerName the crawlerName to set
     */
    public void setCrawlerName(String crawlerName)
    {
        this.crawlerName = crawlerName;
    }

}
