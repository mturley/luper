package com.teamluper.luper;

// This is my dumb implementation of Lambda in Java.
// because java doesn't cleanly support passing a function as a parameter,
// which is useful for things like taking some action after a request you made
// is complete (asynchronous behavior).

// With this implementation, anywhere you want to pass a function,
// you can pass a new Lambda.VoidCallback (or other type of callback,
// depending on whether you need the callback to take any input)
// like this:
// someFunction("someOtherParam", new Lambda.VoidCallback() {
//   public void go() {
//     // do something
//   }
// });

// then in someFunction, or anywhere else you need to accept a function
// as a parameter (good examples are in DialogFactory, where the callback
// gets called as soon as the dialog is closed) you can just add a parameter:
//
// public void someFunction(String something, Lambda.VoidCallback callback)
// 
// and then in the body of that function, just make sure to call callback.go().

// VoidCallback is for plain old callbacks, just a function you need called.
// StringCallback is for when you want to call some method that will get a string
// (such as DialogFactory.prompt, which takes user input) and then pass it a
// method to be called with that string as a parameter. (do something with the
// string the user entered).

public class Lambda {
  public static interface VoidCallback {
    void go();
  }
  public static interface StringCallback {
    void go(String value);
  }
}