
package com.teamluper.luper.rest;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

// a lot of AndroidAnnotations magic is happening here.
// see: https://github.com/excilys/androidannotations/wiki/Rest%20API

@Rest(rootUrl = "http://www.teamluper.com", converters = StringHttpMessageConverter.class)
public interface LuperRestClient {

  @Get("/api/test")
  String getTestString();

  @Get("/api/null")
  public abstract void main();

  // TODO figure out how POST works and how to submit key/values in the body

}
