## Android Icecast/Shoutcast Broadcast

This project aims to implement an icecast source client that captures realtime audio from microphone, encodes and streams (mp3) to iceserver. 

Repo is still preview and will be packaged as external dependency.

### Native Libraries
Although native libraries are included under jniLibs folder, developers are encouraged to build from source. <br>
* On command line; enter jni folder  line and type. (Note that ndk-build should be on your path or print full path of binary)<br><br>
<code>ndk-build</code>


### Formats
Mp3 is the only encoder format that is currently supported but vorbiss ogg will be added too. Vorbis and ogg native libraries are included in source code and working without limitation.

### Notice
Please update upstream configuration in <code>BroadcastFragment</code> regarding your server conf.

### Help
Code is easy to understand and implementation is straightforward, but if you have additional questions, write me: fatih dot sokmen at gmail dot com 

### Testing Environments that work fine
- <a href="https://github.com/StreamMachine/StreamMachine">StreamMachine</a>
- <a href="http://icecast.org/">IceCast Server</a>


### Licence
- LAME source code licensed under the LGPL.
- This repo uses code from <a href="https://github.com/yhirano/SimpleLameLibForAndroid">this repo</a>
