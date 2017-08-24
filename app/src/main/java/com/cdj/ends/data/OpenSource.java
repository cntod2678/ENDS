package com.cdj.ends.data;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 24..
 */

public class OpenSource {

    String oss;
    String url;
    List<String> licenses;
    List<String> copyrights;

    public String getOss() {
        return oss;
    }

    public void setOss(String oss) {
        this.oss = oss;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) {
        this.licenses = licenses;
    }

    public List<String> getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(List<String> copyrights) {
        this.copyrights = copyrights;
    }

    @Override
    public String toString() {
        return "OpenSource{" +
                "oss='" + oss + '\'' +
                ", url='" + url + '\'' +
                ", licenses=" + licenses +
                ", copyrights=" + copyrights +
                '}';
    }
}
