package com.github.appx.utils;

import com.google.inject.AbstractModule;
import com.github.appx.bl.dao.TagsDao;
import com.github.appx.bl.dao.impl.TagsDaoImpl;
import com.github.appx.bl.services.InputService;
import com.github.appx.bl.services.TagsService;
import com.github.appx.bl.services.impl.InputServiceImpl;
import com.github.appx.bl.services.impl.TagsServiceImpl;

public class TagsInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputService.class).to(InputServiceImpl.class);
        bind(TagsService.class).to(TagsServiceImpl.class);
        bind(TagsDao.class).to(TagsDaoImpl.class);
    }

}
