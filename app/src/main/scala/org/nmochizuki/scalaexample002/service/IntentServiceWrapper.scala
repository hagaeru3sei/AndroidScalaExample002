package org.nmochizuki.scalaexample002.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

/** IntentService のラッパークラス.
  *
  * @param name サービス名.
  */
class IntentServiceWrapper(name: String) extends IntentService(name) {

  private val TAG = "IntentServiceWrapper"

  override protected def onHandleIntent(intent: Intent): Unit = {
    Log.d(TAG, "onHandleIntent")
    if (intent != null) {
      onHandleIntent(Some(intent))
    } else {
      onHandleIntent(None)
    }
  }

  /** For override
    *
    * @param intent Intent.
    */
  protected def onHandleIntent(intent: Option[Intent]): Unit = {
    // Do implementation for child classes.
  }

}
