# About
This fork of libgdx is designed for building cross platform applications (not games) which have a high requirement on battery lifetime and refresh rate.

It has a few hard design changes compared to the original libgdx which require more attention while developing but reward you with a super fast UI response.


# Additions
- Windows multitouch support
- Asynchronous input processing

# Changes

## Input processing
Input processing is done asynchronous. In the original libgdx each input event is queued up and processed by the rendering thread.
This causes (depending on the frame rate) unnecessary delays and increases the latency of input processing.
In this fork all input events are directly passed to the listeners from the OS input thread.
Therefore you need to make sure your application can properly handle inputs from a non rendering thread.

It is now also possible to change the input processing implementation from within your application - no need to rebuild libgdx.


### Contributing to the Codebase
libGDX benefits greatly from contributions made by our dedicated developer community. We appreciate any assistance in making libGDX even better. Check out the [CONTRIBUTING.md](https://github.com/libgdx/libgdx/blob/master/.github/CONTRIBUTING.md) file for details on how to contribute. Note that contributing involves working directly with libGDX's source code, a process that regular users do not typically undertake. Refer to the [Working with the Source](https://libgdx.com/dev/from-source/) article for guidance.

You can also support our infrastructure (build server, web server, test devices) by contributing financially through our [Patreon](https://patreon.com/libgdx)!
