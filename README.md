# NOTHING Phones Glyphs simple demo


This program plays a little animation on Nothing Phones' Glyphs.

It's just to show how to simply use the Ketchum Library, as there seem to be some strange behaviors when I tried, like the toggle doesn't seem to turn off glyphs from previous frames.
Also, builder.buildChannel(channel: Int, light: Int) doesn't seem to be in the documentation, but it's the most efficient way to ensure which glyphs are turned on and off.

> [!NOTE]
> You may find the built .apk here: 
> https://github.com/AbyssalGraphics/nothing_glyph_demo/tree/main/app/build/outputs/apk/debug

> [!WARNING]
> I've made code for all Nothing Phones, but I could only test it on the Nothing Phone (2) as it's the only one I have.

> [!IMPORTANT] 
> As described on the official git page, you must enable debugging via adb command:
> 
> `adb shell settings put global nt_glyph_interface_debug_enable 1`
> 
> I personally experience recurrent notifications from Ketchum that won't go.
> To disable them, I use the following commands (don't know exactly which one desactivate the debuggng):
> 
> `adb shell settings put global nt_glyph_interface_debug_enable 0`
> 
> `adb shell settings put global nt_glyph_interface_debug_disable 1`
> 
> The library was found on: https://github.com/Nothing-Developer-Programme/Glyph-Developer-Kit (The .jar files are in app/libs)
