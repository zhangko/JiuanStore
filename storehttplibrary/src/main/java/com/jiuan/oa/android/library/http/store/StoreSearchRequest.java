package com.jiuan.oa.android.library.http.store;

import com.loopj.android.http.RequestParams;

public class StoreSearchRequest extends StoreRequest {

    public final static String PATH = "http://service.oa.jiuan-roa.com/AppStoreAPI/iemylife/search_apps.ashx";

    private String searchKeyWords;

    @Override
    public RequestParams getRequestParams() {
        RequestParams params = super.getRequestParams();
        params.put("SearchKeyWords", searchKeyWords);
        return params;
    }

    public String getSearchKeyWords() {
        return searchKeyWords;
    }

    public void setSearchKeyWords(String searchKeyWords) {
        this.searchKeyWords = searchKeyWords;
    }
}
