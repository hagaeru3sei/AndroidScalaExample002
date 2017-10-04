package org.nmochizuki.scalaexample002

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity extends AppCompatActivity {

  val TAG = "MainActivity"

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate")
  }

}
