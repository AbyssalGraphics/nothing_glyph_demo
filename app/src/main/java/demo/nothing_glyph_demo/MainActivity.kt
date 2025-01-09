package demo.nothing_glyph_demo

import NothingPhoneModel
import android.content.ComponentName
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nothing.ketchum.Common
import com.nothing.ketchum.Glyph
import com.nothing.ketchum.GlyphException
import com.nothing.ketchum.GlyphFrame
import com.nothing.ketchum.GlyphManager
import demo.nothing.NothingPhoneAnimation


class MainActivity : AppCompatActivity() {
    private lateinit var appName: String
    private lateinit var mGM: GlyphManager
    private lateinit var mCallback: GlyphManager.Callback
    private var phoneModel: NothingPhoneModel? = null
    private var phoneAnimator: NothingPhoneAnimation? = null

    private fun init() {
        mCallback = object : GlyphManager.Callback {
            override fun onServiceConnected(componentName: ComponentName) {
                if (Common.is20111()) {
                    mGM.register(Common.DEVICE_20111)
                    phoneModel = NothingPhoneModel.NOTHING_PHONE_1
                }
                if (Common.is22111()) {
                    mGM.register(Common.DEVICE_22111)
                    phoneModel = NothingPhoneModel.NOTHING_PHONE_2
                }
                if (Common.is23111()) {
                    mGM.register(Common.DEVICE_23111)
                    phoneModel = NothingPhoneModel.NOTHING_PHONE_2A
                }
                if (Common.is23113()) {
                    mGM.register(Common.DEVICE_23113)
                    phoneModel = NothingPhoneModel.NOTHING_PHONE_2A_PLUS
                }
                if(null != phoneModel) {
                    Log.i(appName, "${phoneModel!!.modelName} detected")
                    try {
                        //mGM.openSession()
                        phoneAnimator = NothingPhoneAnimation(phoneModel, mGM)
                    } catch (e: GlyphException) {
                        Log.e(appName, e.message!!)
                    }
                }
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                mGM.closeSession()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appName = getString(R.string.app_name)
        Log.i(appName, "creation");

        this.init();
        mGM = GlyphManager.getInstance(applicationContext);
        mGM.init(mCallback);


        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val textView = TextView(this)
        textView.text = appName
        layout.addView(textView)

        setContentView(layout)

        /*Handler(Looper.getMainLooper()).post {
            Log.i(appName, "created, this.glyph() execution")
            this.glyph()
        }*/
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).post {
            Log.i(appName, "resumed, this.glyph() execution")
            if(null != phoneAnimator)
                phoneAnimator!!.animation()
        }
    }

    public override fun onDestroy() {
        try {
            mGM.closeSession()
        } catch (e: GlyphException) {
            Log.e(appName, e.message!!)
        }
        mGM.unInit()
        Log.i(appName, "Destroyed");
        super.onDestroy()
    }
}