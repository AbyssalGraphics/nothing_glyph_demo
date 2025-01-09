package demo.nothing

import NothingPhoneModel
import android.util.Log
import com.nothing.ketchum.Glyph
import com.nothing.ketchum.GlyphManager
import kotlin.math.PI
import kotlin.math.cos

class NothingPhoneAnimation(private val phoneModel: NothingPhoneModel?, private val mGM: GlyphManager) {
    private val maxLight = 4000 // Could go higher (10000 tested on Phone (2)) but seems that natural max is 4000, don't know what happens if is set too high

    fun animation() {
        when (phoneModel) {
            null -> Log.i("NothingPhoneAnimation", "Invalid phone")
            NothingPhoneModel.NOTHING_PHONE_1 -> handlePhone1() // Not tested as I've only Phone (2)
            NothingPhoneModel.NOTHING_PHONE_2 -> handlePhone2()
            NothingPhoneModel.NOTHING_PHONE_2A,
            NothingPhoneModel.NOTHING_PHONE_2A_PLUS -> handlePhone2a() // Not tested as I've only Phone (2)
        }
        if(null != phoneModel) Log.i("NothingPhoneAnimation", "Glyphs Should have blinked")
    }

    private fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun progress(i: Int, inf: Int, sup: Int): Double {
        if(i<inf)
            return 0.0
        if(i>sup)
            return 1.0
        val p = i-inf
        val m = sup-inf
        return p.toDouble()/m.toDouble()
    }

    private fun handlePhone1() { // Not tested as I've only Phone (2)
        mGM.openSession()
        val builder = mGM.glyphFrameBuilder

        val frame1 = builder.buildChannel(Glyph.Code_20111.E1, maxLight/2)
        mGM.toggle(frame1.build())
        sleep(100)
        val frame2 = builder.buildChannel(Glyph.Code_20111.E1, 0)
        mGM.toggle(frame2.build())
        sleep(100)
        val frame3 = builder.buildChannel(Glyph.Code_20111.E1, maxLight)
        mGM.toggle(frame3.build())
        sleep(500)

        for (i in 0..2000 step 4) {
            sleep(25)
            val angleInDegrees = i.toDouble()*(i.toDouble()/200.0)
            val angleInRadians = angleInDegrees * PI / 180.0
            val result = (cos(angleInRadians) + 1.0)/2.0
            val lightA1 = (if(i>=2000) maxLight else (maxLight - (result * maxLight.toDouble()).toInt()))
            val lightB1 = (if(i<=192) 0 else if(i>=1992) maxLight else (result * maxLight.toDouble()).toInt())

            val frameC = builder
                .buildChannelD()
                .buildChannel(Glyph.Code_20111.A1, lightA1)
                .buildChannel(Glyph.Code_20111.B1, lightB1)
                .buildChannel(Glyph.Code_20111.C1, (if (i < 1600) 0 else maxLight))
                .buildChannel(Glyph.Code_20111.C2, (if (i < 1200) 0 else maxLight))
                .buildChannel(Glyph.Code_20111.C3, (if (i < 800) 0 else maxLight))
                .buildChannel(Glyph.Code_20111.C4, (if (i < 400) 0 else maxLight))

            mGM.displayProgressAndToggle(frameC.build(), i/20, false)
        }
        sleep(1000)

        val frame4 = builder.buildChannel(Glyph.Code_20111.E1, 0)
        mGM.toggle(frame4.build())
        sleep(100)
        val frame5 = builder.buildChannel(Glyph.Code_20111.E1, maxLight)
        mGM.toggle(frame5.build())
        sleep(100)
        val frame6 = builder.buildChannel(Glyph.Code_20111.E1, 0)
        mGM.toggle(frame6.build())
        sleep(100)
        val frame7 = builder.buildChannel(Glyph.Code_22111.E1, maxLight)
        mGM.toggle(frame7.build())
        sleep(100)
        mGM.turnOff()
        mGM.closeSession()
    }

    private fun handlePhone2() {
        mGM.openSession()
        val builder = mGM.glyphFrameBuilder

        val frame1 = builder.buildChannel(Glyph.Code_22111.E1, maxLight/2)
        mGM.toggle(frame1.build())
        sleep(100)
        val frame2 = builder.buildChannel(Glyph.Code_22111.E1, 0)
        mGM.toggle(frame2.build())
        sleep(100)
        val frame3 = builder.buildChannel(Glyph.Code_22111.E1, maxLight)
        mGM.toggle(frame3.build())
        sleep(500)

        for (i in 0..100 step 4) {
            sleep(100)
            val frameD = builder.buildChannelD()
            mGM.displayProgressAndToggle(frameD.build(), i, false)
        }

        val frameD = builder.buildChannel(Glyph.Code_22111.E1, maxLight).buildChannel(Glyph.Code_22111.D1_1, maxLight).buildChannel(Glyph.Code_22111.D1_2, maxLight).buildChannel(Glyph.Code_22111.D1_3, maxLight).buildChannel(Glyph.Code_22111.D1_4, maxLight).buildChannel(Glyph.Code_22111.D1_5, maxLight).buildChannel(Glyph.Code_22111.D1_6, maxLight).buildChannel(Glyph.Code_22111.D1_7, maxLight).buildChannel(Glyph.Code_22111.D1_8, maxLight)
        mGM.toggle(frameD.build())
        sleep(500)

        for (i in 0..2000 step 4) {//Unable to displayProgress on Glyph C, if Glyph D was already used for progress
            sleep(25)
            val angleInDegrees = i.toDouble()*(i.toDouble()/200.0)
            val angleInRadians = angleInDegrees * PI / 180.0
            val result = (cos(angleInRadians) + 1.0)/2.0
            val lightA1 = (if(i>=2000) maxLight else (maxLight - (result * maxLight.toDouble()).toInt()))
            val lightA2 = (if(i<=192) 0 else if(i>=1992) maxLight else (result * maxLight.toDouble()).toInt())

            val frameC = builder
                .buildChannel(Glyph.Code_22111.A1, lightA1)
                .buildChannel(Glyph.Code_22111.A2, lightA2)

                .buildChannel(Glyph.Code_22111.C1_1, (progress(i, 0, 500)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_2, (progress(i, 100, 600)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_3, (progress(i, 200, 700)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_4, (progress(i, 300, 800)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_5, (progress(i, 400, 900)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_6, (progress(i, 500, 1000)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_7, (progress(i, 600, 1100)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_8, (progress(i, 700, 1200)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_9, (progress(i, 800, 1300)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_10, (progress(i, 900, 1400)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_11, (progress(i, 1000, 1500)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_12, (progress(i, 1100, 1600)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_13, (progress(i, 1200, 1700)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_14, (progress(i, 1300, 1800)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_15, (progress(i, 1400, 1900)*maxLight).toInt())
                .buildChannel(Glyph.Code_22111.C1_16, (progress(i, 1500, 2000)*maxLight).toInt())

                .buildChannel(Glyph.Code_22111.C2, (if (i < 1500) 0 else maxLight))
                .buildChannel(Glyph.Code_22111.C3, (if (i < 1200) 0 else maxLight))
                .buildChannel(Glyph.Code_22111.C4, (if (i < 900) 0 else maxLight))
                .buildChannel(Glyph.Code_22111.C5, (if (i < 600) 0 else maxLight))
                .buildChannel(Glyph.Code_22111.C6, (if (i < 300) 0 else maxLight))
            mGM.toggle(frameC.build())
        }
        val frameB = builder.buildChannel(Glyph.Code_22111.B1, maxLight)
        mGM.toggle(frameB.build())
        sleep(1000)

        val frame4 = builder.buildChannel(Glyph.Code_22111.B1, 0)
        mGM.toggle(frame4.build())
        sleep(100)
        val frame5 = builder.buildChannel(Glyph.Code_22111.B1, maxLight)
        mGM.toggle(frame5.build())
        sleep(100)
        val frame6 = builder.buildChannel(Glyph.Code_22111.B1, 0)
        mGM.toggle(frame6.build())
        sleep(100)
        val frame7 = builder.buildChannel(Glyph.Code_22111.B1, maxLight)
        mGM.toggle(frame7.build())
        sleep(100)
        mGM.turnOff()
        mGM.closeSession()
    }

    private fun handlePhone2a() { // Not tested as I've only Phone (2)
        mGM.openSession()
        val builder = mGM.glyphFrameBuilder

        val frame1 = builder.buildChannelC()
        mGM.toggle(frame1.build())
        sleep(100)
        mGM.turnOff()
        sleep(100)
        val frame2 = builder.buildChannelC()
        mGM.toggle(frame2.build())
        sleep(100)
        mGM.turnOff()
        sleep(500)

        for (i in 0..2000 step 4) {
            sleep(25)
            val angleInDegrees = i.toDouble()*(i.toDouble()/200.0)
            val angleInRadians = angleInDegrees * PI / 180.0
            val result = (cos(angleInRadians) + 1.0)/2.0
            val lightA = (if(i<=192) 0 else if(i>=1992) maxLight else (result * maxLight.toDouble()).toInt())
            val lightB = (if(i>=2000) maxLight else (maxLight - (result * maxLight.toDouble()).toInt()))

            val frameC = builder.buildChannelC()
                .buildChannel(Glyph.Code_23111.A, lightA)
                .buildChannel(Glyph.Code_23111.B, lightB)

            mGM.displayProgressAndToggle(frameC.build(), i/20, false)
        }
        sleep(750)

        val frame3 = builder.buildChannel(Glyph.Code_23111.B, 0)
        mGM.toggle(frame3.build())
        sleep(100)
        val frame4 = builder.buildChannel(Glyph.Code_23111.B, maxLight)
        mGM.toggle(frame4.build())
        sleep(100)
        val frame5 = builder.buildChannel(Glyph.Code_23111.B, 0)
        mGM.toggle(frame5.build())
        sleep(100)
        val frame6 = builder.buildChannel(Glyph.Code_23111.B, maxLight)
        mGM.toggle(frame6.build())
        sleep(100)
        mGM.turnOff()
        mGM.closeSession()
    }
}