package org.nmochizuki.scalaexample002

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.{Context, Intent}
import android.util.Log
import android.widget.{RemoteViews, Toast}


/**
  * Implementation of App Widget functionality.
  */
class NewAppWidget extends AppWidgetProvider {

  val TAG = "NewAppWidget"
  val UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
  val INTENT_CLICK_BUTTON = "org.nmochizuki.scalaexample002.CLICK_ACTION"

  def updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText: CharSequence = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views: RemoteViews = new RemoteViews(context.getPackageName, R.layout.new_app_widget)

    val intent: Intent = new Intent(INTENT_CLICK_BUTTON)
    views.setOnClickPendingIntent(R.id.button, PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
  }

  override def onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: Array[Int]) {
    // There may be multiple widgets active, so update all of them
    Log.d(TAG, "onUpdate")
    appWidgetIds.foreach { id =>
      updateAppWidget(context, appWidgetManager, id)
    }
  }

  override def onReceive(context: Context, intent: Intent): Unit = {
    super.onReceive(context, intent)
    Log.d(TAG, "onReceive")
    Log.d(TAG, s"action: ${intent.getAction}")
    intent.getAction match {
      case UPDATE =>
        Toast.makeText(context, "UPDATE", Toast.LENGTH_SHORT).show()
      case INTENT_CLICK_BUTTON =>
        Toast.makeText(context, "click_action", Toast.LENGTH_SHORT).show()
        val views: RemoteViews = new RemoteViews(context.getPackageName, R.layout.new_app_widget)
        views.setProgressBar(0, 100, 10, true)
    }

  }

  override def onEnabled(context: Context): Unit = {
    // Enter relevant functionality for when the first widget is created
    Log.d(TAG, "onEnabled")
  }

  override def onDisabled(context: Context): Unit = {
    // Enter relevant functionality for when the last widget is disabled
    Log.d(TAG, "onDisabled")
  }
}


