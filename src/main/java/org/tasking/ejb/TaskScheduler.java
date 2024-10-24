package org.tasking.ejb;

import jakarta.ejb.*;
import java.time.LocalDateTime;

@Singleton
public class TaskScheduler {
    @EJB
    private TaskEJB taskEJB;

    @EJB
    private UserEJB userEJB;

    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    public void dailyTaskCheck() {
        System.out.println("Running daily task check at: " + LocalDateTime.now());
        taskEJB.markTasksAsUnfinished();
        userEJB.resetUserTokens();
    }

    // Exécuter toutes les heures pour vérifier les demandes de changement en attente
    @Schedule(hour = "*", minute = "0", second = "0", persistent = false)
    public void hourlyTokenCheck() {
        System.out.println("Checking pending task change requests at: " + LocalDateTime.now());
        userEJB.checkAndUpdatePendingTokens();
    }

    // Pour tester : exécuter toutes les minutes
    //@Schedule(second = "0", minute = "*", hour = "*", persistent = false)
    public void testSchedule() {
        System.out.println("Test schedule running at: " + LocalDateTime.now());
    }
}