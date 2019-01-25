package com.neiko.smswidget.Widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.neiko.smswidget.R

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [AppMakrosWidgetConfigureActivity]
 */
class AppMakrosWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            AppMakrosWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val widgetText = AppMakrosWidgetConfigureActivity.loadTitlePref(context, appWidgetId)

            val views = RemoteViews(context.packageName, R.layout.app_makros_widget)
            views.setTextViewText(R.id.name_text_view, widgetText)
            views.setTextViewText(R.id.phone_text_view, widgetText)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

