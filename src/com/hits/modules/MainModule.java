package com.hits.modules;


import com.hits.core.StartSetup;
import com.hits.core.UrlMappingSet;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * Created by Numbgui on 2015/5/19.
 */
@Modules(scanPackage = true)
@Ok("raw")
@Fail("http:500")
@IocBy(type=ComboIocProvider.class,args={
        "*org.nutz.ioc.loader.json.JsonLoader","config",
        "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","com.hits"})
@SetupBy(value=StartSetup.class)
@UrlMappingBy(UrlMappingSet.class)
public class MainModule {

}
