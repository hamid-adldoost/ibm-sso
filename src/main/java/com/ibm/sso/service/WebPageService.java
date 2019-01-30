package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.ibm.sso.dao.WebPageDao;
import com.ibm.sso.dto.WebPageDto;
import com.ibm.sso.model.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebPageService extends GeneralServiceImpl<WebPageDto, WebPage, Long> {


    private final WebPageDao webPageDao;

    @Autowired
    public WebPageService(WebPageDao webPageDao) {
        this.webPageDao = webPageDao;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return webPageDao;
    }

    @Override
    public WebPageDto getDtoInstance() {
        return new WebPageDto();
    }
}
