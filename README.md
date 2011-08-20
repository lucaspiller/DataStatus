# DataStatus

DataStatus is a simple background service for Android, and a Mac OS X menubar widget, which allows you to see what state the data connection is in your Android phone. You are probably thinking, why the hell would you want that? I use it when I am on a train with a usually dodgy 3G signal and have my laptop tethered to my phone. It is quite useful to know whether a website isn't loading because it is down or whether you just have a poor network connection.

## Usage

First download the Android server, and start it up:

[DataStatus-1.0.apk](https://github.com/lucaspiller/DataStatus/DataStatus-1.0.apk/qr_code)

Next download a widget, currently there is only one available for OS X, but feel free to contribute one for your platform!

[DataStatus-OSX-1.0.zip](https://github.com/downloads/lucaspiller/DataStatus/DataStatus-OSX-1.0.zip)

The widget is currently hardcoded to use the IP 192.168.43.1, the IP used when tethering with your phone. If you know Cocoa programming, please suggest how to create a preference pane to enter a custom IP :)

## Current Status

At the moment there is only an OS X menubar widget, however the service exposes a simple HTTP API, so it should be easy to do the same for other platforms. The IP address of the phone is currently hardcoded, however I would prefer if this uses UDP multicasting. I had a bit of a play about with this, and it appears that this isn't supported when tethering to the Android device (over regular wifi it is fine). If anyone knows a way around this it would be most appreciated :)

## Contributing

* Fork the project.
* Make your feature addition or bug fix.
* Send me a pull request. Bonus points for topic branches.

Tests would also be good, there are currently none... :)

## License

<a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/InteractiveResource" property="dct:title" rel="dct:type">DataStatus</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="https://github.com/lucaspiller/DataStatus" property="cc:attributionName" rel="cc:attributionURL">Luca Spiller</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/lucaspiller/DataStatus" rel="dct:source">github.com</a>.<br />Permissions beyond the scope of this license may be available at <a xmlns:cc="http://creativecommons.org/ns#" href="https://github.com/lucaspiller/DataStatus" rel="cc:morePermissions">https://github.com/lucaspiller/DataStatus</a>.
