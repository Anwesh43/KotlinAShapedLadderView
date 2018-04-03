package ui.anwesome.com.kotlinashapedladderview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.ashapedladderview.AShapedLadderView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AShapedLadderView.create(this)
    }
}
