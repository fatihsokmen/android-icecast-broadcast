##Android Icecast/Shoutcast broadcast##
Captures realtime audio from microphone, encodes and stream to ice server. 
Code implements basic ice broadcast protocol and is tested with <a href="https://github.com/StreamMachine/StreamMachine">StreamMachine</a>, not icecast or shoutcast
<br>
Code is still preview and will be packaged as library.

###Native Libraries###
Although native libraries are included in jniLibs folder, developers are encourages to build from source files. Easy way to generate native libs<br>
* Enter jni folder on command line and call<br><br>
<code>ndk-build</code>

This will generate also native libs.

###Formats###
Mp3 is the only format that is currently supported but vorbiss ogg will be added as an alternative container/encoder. Vorbis and ogg native libraries included and working without limitation.


