## Android Icecast/Shoutcast Broadcast

This project aims to implement an icecast source client that captures realtime audio from microphone, encodes and streams (mp3) to iceserver. 

### Native Libraries
Although native libraries are included under jniLibs folder, developers are encouraged to build from source. <br>
* On command line; enter jni folder  line and type. (Note that ndk-build should be on your path or print full path of binary)<br><br>
<code>ndk-build</code>


### Formats
Mp3 is the only encoder format that is currently supported but vorbiss ogg will be added too. Vorbis and ogg native libraries are included in source code and working without limitation.

### Notice
Please update upstream configuration in <code>BroadcastFragment</code> regarding your server conf.

### Testing Environments that work fine
- <a href="https://github.com/StreamMachine/StreamMachine">StreamMachine</a>
- <a href="http://icecast.org/">IceCast Server</a>


### Licences
- LAME encoder is licensed under the LGPL.
- libshout is licensed under the LGPL v2.
- This repo uses code from <a href="https://github.com/yhirano/SimpleLameLibForAndroid">this repo</a>
