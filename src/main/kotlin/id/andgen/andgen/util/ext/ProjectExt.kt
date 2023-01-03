package id.andgen.andgen.util.ext

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

fun Project?.showNotification(
    notificationGroup: String = "AndGen",
    title: String?,
    content: String?,
    type: NotificationType = NotificationType.INFORMATION,
) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup(notificationGroup)
        .createNotification(
            title = title.orEmpty(),
            content = content.orEmpty(),
            type = type,
        )
        .notify(this)
}