##Android Icecast/Shoutcast Broadcast##
This project aims to implement an icecast source client that captures realtime audio from microphone, encodes and stream to ice server. 
Code implements basic ice protocol and is mostly tested with <a href="https://github.com/StreamMachine/StreamMachine">StreamMachine</a>, as ice input source.
Repo is still preview and will be packaged as library.

###Native Libraries###
Although native libraries are included in jniLibs folder, developers are encourages to build from source files. Easy way to generate native libs<br>
* Enter jni folder on command line and call. Note that ndk-build should be on your path or print full path of binary<br><br>
<code>ndk-build</code>


###Formats###
Mp3 is the only format that is currently supported but vorbiss ogg will be added as an alternative container/encoder. Vorbis and ogg native libraries are also included and working without limitation.

###Help###
Code is easy to understand and implementation is straightforward, but if you have additional questions, write me: fatih dot sokmen at gmail dot com 



