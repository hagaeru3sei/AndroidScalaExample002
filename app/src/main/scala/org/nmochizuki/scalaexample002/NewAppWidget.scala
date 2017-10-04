package org.nmochizuki.scalaexample002

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews


/**
  * Implementation of App Widget functionality.
  */
class NewAppWidget extends AppWidgetProvider {

  val TAG = "NewAppWidget"

  def updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText: CharSequence = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views: RemoteViews = new RemoteViews(context.getPackageName, R.layout.new_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

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

  override def onEnabled(context: Context): Unit = {
    // Enter relevant functionality for when the first widget is created
    Log.d(TAG, "onEnabled")
  }

  override def onDisabled(context: Context): Unit = {
    // Enter relevant functionality for when the last widget is disabled
    Log.d(TAG, "onDisabled")
  }
}


