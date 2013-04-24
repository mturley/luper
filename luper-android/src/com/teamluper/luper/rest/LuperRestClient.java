
package com.teamluper.luper.rest;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

// a lot of AndroidAnnotations magic is happening here.
// see: https://github.com/excilys/androidannotations/wiki/Rest%20API

@Rest(rootUrl = "http://www.teamluper.com", converters = StringHttpMessageConverter.class)
public interface LuperRestClient {

  @Get("/api/null")
  public abstract void main();

  @Get("/api/test")
  String getTestString();

  @Post("/api/auth-register")
  String registerNewAccount(String requestJSON);
  // requestJSON must contain: email, password, username

  @Post("/api/auth-challenge")
  String getLoginChallengeSalt(String requestJSON);
  // requestJSON must contain: email

  @Post("/api/auth-login")
  String validateLoginAttempt(String requestJSON);
  // requestJSON must contain: email, attemptHash

  @Post("/api/auth-passwd")
  String changeUserPassword(String requestJSON);
  // requestJSON must contain: email, oldPasswordAttemptHash, newPassword

}
