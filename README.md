# About
This fork of libgdx is designed for building corss platform applications with relativly fast refresh rates (compared to a regular app).

It has quite a few hard design changes compared to the original libgdx which require more attention while developing but reward you with a super fast UI response.


# Changes

## Input processing
Input processing is done asynchronous. In the original libgdx each input event is queued up and processed by the rendering thread.
This causes (depending on the frame rate) unnecessary delays and increases the latency of input processing.
In this fork all input events are directly passed to the listeners from the OS input thread.
Therefore you need to make sure your application can properly handle inputs from a non rendering thread.

It is now also possible to change the input processing implementation from without your application - no need to rebuild libgdx.

# Fixes
## Android 6.0 
A bug in proguard and android 6.0 causes crashes in code which can never crash.
This is taken care of in this fork.


## Android Multitouch 
In libgdx are a couple of bugs in the input processing for android which causes three finger tapps not to be handled correctly.
This has been fixed in this fork.

# Removed
The following things have been removed to make the framework a bit lighter and easier to maintain:
- Android live wallpaper 
