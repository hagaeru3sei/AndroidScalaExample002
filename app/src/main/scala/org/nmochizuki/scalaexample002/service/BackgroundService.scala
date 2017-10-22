package org.nmochizuki.scalaexample002.service

import android.content.Intent
import android.os.{Binder, IBinder}
import android.util.Log

class BackgroundService(name: String) extends IntentServiceWrapper(name: String) {

  private val TAG = "BackgroundService"

  @volatile private var allowBind = false
  @volatile private var num = 0

  def this() = {
    this("BackgroundService")
  }

  override def onCreate(): Unit = {
    Log.d(TAG, "Start service")
    super.onCreate()
  }

  override def onHandleIntent(intentOpt: Option[Intent]): Unit = {
    intentOpt.map { intent =>
      // TODO: implements
      Log.d(TAG, s"onHandleIntent. intent: $intent")
    }
  }

  override def onBind(intent: Intent): IBinder = {
    Log.d(TAG, "onBind")
    allowBind = false
    BackgroundService.binder
  }

  override def onDestroy(): Unit = {
    Log.d(TAG, "onDestroy")
    super.onDestroy()
    allowBind = true
  }

}

object BackgroundService {

  self =>

  def apply = new BackgroundService
  def apply(name: String): BackgroundService = new BackgroundService(name)

  private val binder = new LocalBinder

  class LocalBinder extends Binder {
    def service = self.apply
  }
}
