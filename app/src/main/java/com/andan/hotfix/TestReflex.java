package com.andan.hotfix;

import sj.mblog.L;

/**
 * Created by nongyudi on 2017/11/12.
 */

public class TestReflex {
    String one;
    boolean tweo;


    public TestReflex(String one, boolean tweo) {
        this.one = one;
        this.tweo = tweo;
    }

    public String getOne() {
        L.e(one);
        return one;
    }

    public void setOne(String one) {
        L.e(one);
        this.one = one;
    }

    public boolean isTweo() {
        L.e(tweo);
        return tweo;
    }

    public void setTweo(boolean tweo) {
        L.e(tweo);
        this.tweo = tweo;
    }
}
