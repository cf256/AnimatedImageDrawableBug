package no.cf.linchausen.giftest

import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    var animatedImageDrawable: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val file = File("$cacheDir/test")

        val asset = assets.open("cat.gif")
        val size = asset.available()
        val buffer = ByteArray(size)
        asset.read(buffer)
        asset.close()
        val fos = FileOutputStream(file)
        fos.write(buffer)
        fos.close()

        /**
         * Create source with File
         * This works
         */
//        GlobalScope.launch(Dispatchers.Default) {
//            ImageDecoder.createSource(file).also { source ->
//                ImageDecoder.decodeDrawable(source).also {
//                    animatedImageDrawable = it
//                    start()
//                }
//            }
//        }

        /**
         * Create source with ByteBuffer
         * This does not work
         */
//        GlobalScope.launch(Dispatchers.Default) {
//            ImageDecoder.createSource(ByteBuffer.wrap(file.readBytes())).also { source ->
//                ImageDecoder.decodeDrawable(source).also {
//                    animatedImageDrawable = it
//                    start()
//                }
//            }
//        }

        /**
         * Create source with an asset
         * This works
         */
//        GlobalScope.launch(Dispatchers.Default) {
//            ImageDecoder.createSource(assets, "cat.gif").also { source ->
//                ImageDecoder.decodeDrawable(source).also {
//                    animatedImageDrawable = it
//                    start()
//                }
//            }
//        }
    }

    private fun start() {
        GlobalScope.launch(Dispatchers.Main) {
            animatedImageDrawable?.let {
                image.setImageDrawable(it)
                (it as? AnimatedImageDrawable)?.start()
            }
        }
    }
}
