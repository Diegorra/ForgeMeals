package external;

import com.intuit.karate.junit5.Karate;

class ExternalRunner {
     
    @Karate.Test
    Karate testLogin() {
        return Karate.run("login").relativeTo(getClass());
    }

    /* De la plantilla
    @Karate.Test
    Karate testWs() {
        return Karate.run("ws").relativeTo(getClass());
    }
    */

    @Karate.Test
    Karate testSettings() {
        return Karate.run("settings").relativeTo(getClass());
    }

    @Karate.Test
    Karate testAddRecipe() {
        return Karate.run("addRecipe").relativeTo(getClass());
    }
}