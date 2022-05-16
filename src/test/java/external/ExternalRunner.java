package external;

import com.intuit.karate.junit5.Karate;

class ExternalRunner {
    
    //De la plantilla
    /*
    @Karate.Test
    Karate testWs() {
        return Karate.run("ws").relativeTo(getClass());
    }
    */
    
    @Karate.Test
    Karate testLogin() {
        return Karate.run("login").relativeTo(getClass());
    }
    
    @Karate.Test
    Karate testSettings() {
        return Karate.run("settings").relativeTo(getClass());
    }

    @Karate.Test
    Karate testAddRecipe() {
        return Karate.run("addRecipe").relativeTo(getClass());
    }
    
    @Karate.Test
    Karate testCheckOut() {
        return Karate.run("checkOut").relativeTo(getClass());
    }
    
    @Karate.Test
    Karate testCommits() {
        return Karate.run("commits").relativeTo(getClass());
    }
    
    @Karate.Test
    Karate testWeekPlan() {
        return Karate.run("weekPlan").relativeTo(getClass());
    }
}