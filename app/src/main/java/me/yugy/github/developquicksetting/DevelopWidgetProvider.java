package me.yugy.github.developquicksetting;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import java.io.IOException;

public class DevelopWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int enabledColor = context.getResources().getColor(android.R.color.white);
        int disabledColor = context.getResources().getColor(android.R.color.tab_indicator_text);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            try {
                //update widget state
                views.setTextColor(R.id.layout_border,
                        DeveloperSettings.isDebugLayoutEnabled() ? enabledColor : disabledColor);
                views.setTextColor(R.id.overdraw,
                        DeveloperSettings.isShowOverdrawEnabled() ? enabledColor : disabledColor);
                views.setTextColor(R.id.gpu_rendering,
                        DeveloperSettings.isShowProfileGPURendering() ? enabledColor : disabledColor);
                views.setTextColor(R.id.destroy_activities,
                        DeveloperSettings.isImmediatelyDestroyActivities(context) ? enabledColor : disabledColor);

                //set widget click listener
                views.setOnClickPendingIntent(R.id.layout_border, WidgetService.getPendingIntent(
                        context, WidgetService.ACTION_SET_SHOW_LAYOUT_BORDER));
                views.setOnClickPendingIntent(R.id.overdraw, WidgetService.getPendingIntent(
                        context, WidgetService.ACTION_SET_DISPLAY_OVERDRAW));
                views.setOnClickPendingIntent(R.id.gpu_rendering, WidgetService.getPendingIntent(
                        context, WidgetService.ACTION_SET_PROFILE_GPU_RENDERING));
                views.setOnClickPendingIntent(R.id.destroy_activities, WidgetService.getPendingIntent(
                        context, WidgetService.ACTION_SET_IMMEDIATELY_DESTROY_ACTIVITIES));
            } catch (IOException e) {
                e.printStackTrace();
            }

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}