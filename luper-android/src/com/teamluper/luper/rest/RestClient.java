
package com.teamluper.luper.rest;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

@Rest(rootUrl = "http://10.0.0.2", converters = StringHttpMessageConverter.class)
public interface RestClient {


    @Get("/")
    public abstract void main();

}
