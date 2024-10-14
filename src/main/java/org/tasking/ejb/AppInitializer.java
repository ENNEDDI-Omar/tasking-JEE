package org.tasking.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class AppInitializer
{
    @EJB
    private RoleEJB roleEJB;

    @PostConstruct
    public void init() {
        roleEJB.init();
    }
}
