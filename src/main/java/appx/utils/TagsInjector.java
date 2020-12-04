package appx.utils;

import appx.bl.dao.TagsDao;
import appx.bl.dao.impl.TagsDaoImpl;
import appx.bl.services.InputService;
import appx.bl.services.TagsService;
import appx.bl.services.impl.InputServiceImpl;
import appx.bl.services.impl.TagsServiceImpl;
import com.google.inject.AbstractModule;

public class TagsInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputService.class).to(InputServiceImpl.class);
        bind(TagsService.class).to(TagsServiceImpl.class);
        bind(TagsDao.class).to(TagsDaoImpl.class);
    }

}
